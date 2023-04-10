package com.example.exploreworld.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.Customerdata;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.Model.Slider;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.DomesticAdapter;
import com.example.exploreworld.adapters.InternationalAdapter;
import com.example.exploreworld.adapters.PopularAdapter;
import com.example.exploreworld.adapters.ReligiousAdapter;
import com.example.exploreworld.adapters.Slideradapter;
import com.example.exploreworld.authentication.Admin_login;
import com.example.exploreworld.authentication.Customer_login;
import com.example.exploreworld.menu.Booking_Detail;
import com.example.exploreworld.menu.ContactUs_Activity;
import com.example.exploreworld.menu.Facilities_Activity;
import com.example.exploreworld.viewall.Domestic_Viewall;
import com.example.exploreworld.viewall.Fort_Load;
import com.example.exploreworld.viewall.International_viewall;
import com.example.exploreworld.viewall.Leh_Ladakh;
import com.example.exploreworld.viewall.Popular_viewall;
import com.example.exploreworld.viewall.Religious_viewall;
import com.example.exploreworld.viewall.Wonders_Load;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class Customer_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    private ViewPager mainPager;
    Slideradapter slideradapter;
    TabLayout tabIndicator;
    private List<Slider> mList = new ArrayList<>();
    private int currentposition = 0;
    private Timer timer;
    TextView textView1,textView2,textView3,textView4,domestic,international,popular,other;
    Dialog dialog,dialog2,dialog3,dialog4;
    Pattern lowercase=Pattern.compile("[a-z]");
    Pattern uppercase=Pattern.compile("[A-Z]");
    Pattern digit=Pattern.compile("[0-9]");
    Pattern specialchar=Pattern.compile("[@#$%^&+=]");

    List<PackageData> mydomesticlist,myinternationallist,mypopularlist,myreligiouslist;
    PackageData packageData;
    DomesticAdapter domesticAdapter;
    InternationalAdapter internationalAdapter;
    PopularAdapter popularAdapter;
    ReligiousAdapter religiousAdapter;
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3,databaseReference4;
    private ValueEventListener valueEventListener;
    AlertDialog alertDialog;
    ImageView wonders,fort,leh;
    private long backPressedTime;
    private Toast backToast;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_drawer);
        wonders=findViewById(R.id.imagepic1);
        leh=findViewById(R.id.imagepic2);
        fort=findViewById(R.id.imagepic3);
        textView1=findViewById(R.id.viewall);
        textView2=findViewById(R.id.viewall2);
        textView3=findViewById(R.id.viewall3);
        textView4=findViewById(R.id.viewall4);


        domestic=findViewById(R.id.domestic);
        international=findViewById(R.id.International);
        popular=findViewById(R.id.Popular);
        other=findViewById(R.id.Other);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Typeface typeface=Typeface.createFromAsset(getAssets(),"Times New Roman.ttf");
        Typeface typeface2= Typeface.createFromAsset(getAssets(),"brandon_bld.ttf");
        domestic.setTypeface(typeface);
        international.setTypeface(typeface);
        popular.setTypeface(typeface);
        other.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);


        dialog=new Dialog(Customer_drawer.this);
        dialog2=new Dialog(Customer_drawer.this);
        dialog3=new Dialog(Customer_drawer.this);
        dialog4=new Dialog(Customer_drawer.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Customer_drawer.this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Customer_drawer.this,drawer,toolbar
                ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null)
        {
            navigationView.setCheckedItem(R.id.nav_home);
        }
        recyclerView=findViewById(R.id.recycle_view);
        recyclerView2=findViewById(R.id.recycle_view2);
        recyclerView3=findViewById(R.id.recycle_view3);
        recyclerView4=findViewById(R.id.recycle_view4);
        //slider
        mList.add(new Slider("Best Tourist Places", "Travel makes one modest, you see what a tiny place you occupy in the world.", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F28%2F2018%2F10%2Fhot-air-balloons-festival-cappadocia-turkey-HOTAIR0605.jpg&amp;q=85"));
        mList.add(new Slider("Deligious food", "More than 250 restaurant around the tourist places, ", "https://discover.luxury/wp-content/uploads/2017/09/The-World%E2%80%99s-Most-Exclusive-Events-for-Food-and-Wine-Lovers-feature-1024x581.jpg"));
        mList.add(new Slider("Best activities", "Extraordinary five-star outdoor activities","https://wildhawk.in/wp-content/uploads/2019/05/Rafting_in_rishikesh-850x487.jpg"));
        mList.add(new Slider("Great luxurious hotels", "Book cheapest and most convenient hotels around you.", "https://rimghtlak.mmtcdn.com/CollectionImages/201609091139127239/3_woobar.jpg"));
        // setup viewpager
        mainPager = findViewById(R.id.imageSlider);
        tabIndicator = findViewById(R.id.Tab);
        slideradapter = new Slideradapter(Customer_drawer.this, mList);
        mainPager.setAdapter(slideradapter);
        createSlideShow();
        String uri="https://images.memphistours.com/large/a3681e58ff971daf48ea05e717a21dca.jpg";
        String uri2="https://www.rajasthantourplanner.com/images/leh-Ladakh-planner.jpg";
        String uri3="https://i.pinimg.com/originals/d7/fe/f4/d7fef4f1954a984f999996ff5f9aaf45.jpg";
        Glide.with(this).load(uri).into(wonders);
        Glide.with(this).load(uri2).into(leh);
        Glide.with(this).load(uri3).into(fort);

        // setup tablayout with viewpager
        alertDialog=new SpotsDialog.Builder().setContext(Customer_drawer.this).build();
        alertDialog.setMessage("Loading Packages...");
        alertDialog.setCanceledOnTouchOutside(false);

        tabIndicator.setupWithViewPager(mainPager);

        mydomesticlist=new ArrayList<>();
        myinternationallist=new ArrayList<>();
        mypopularlist=new ArrayList<>();
        myreligiouslist=new ArrayList<>();
        domesticAdapter=new DomesticAdapter(this,mydomesticlist);
        internationalAdapter=new InternationalAdapter(this,myinternationallist);
        popularAdapter=new PopularAdapter(this,mypopularlist);
        religiousAdapter=new ReligiousAdapter(this,myreligiouslist);
        recyclerView.setAdapter(domesticAdapter);
        recyclerView2.setAdapter(internationalAdapter);
        recyclerView3.setAdapter(popularAdapter);
        recyclerView4.setAdapter(religiousAdapter);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        LinearLayoutManager gridLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(gridLayoutManager2);
        LinearLayoutManager gridLayoutManager3= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(gridLayoutManager3);
        LinearLayoutManager gridLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView4.setLayoutManager(gridLayoutManager4);
        //domestic package
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        alertDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("tourpackages").child("domesticpackage");
        Query query=databaseReference.limitToFirst(4);
        valueEventListener=query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mydomesticlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    PackageData packageData=snapshot.getValue(PackageData.class);
                    mydomesticlist.add(packageData);
                }
                domesticAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

        databaseReference2=FirebaseDatabase.getInstance().getReference("tourpackages").child("internationalpackage");
        Query query2=databaseReference2.limitToFirst(4);
        valueEventListener=query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myinternationallist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    PackageData packageData=snapshot.getValue(PackageData.class);
                   myinternationallist.add(packageData);
                }
                internationalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

        databaseReference3=FirebaseDatabase.getInstance().getReference("tourpackages").child("popularpackage");
        Query query3=databaseReference3.limitToFirst(4);
        valueEventListener=query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mypopularlist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    PackageData packageData=snapshot.getValue(PackageData.class);
                    mypopularlist.add(packageData);
                }
                popularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

        databaseReference4=FirebaseDatabase.getInstance().getReference("tourpackages").child("religiouspackage");
        Query query4=databaseReference4.limitToFirst(4);
        valueEventListener=query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myreligiouslist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())

                {
                    PackageData packageData=snapshot.getValue(PackageData.class);
                    myreligiouslist.add(packageData);
                }
                religiousAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                alertDialog.dismiss();
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Domestic_Viewall.class));
                finish();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), International_viewall.class));
                finish();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Popular_viewall.class));
                finish();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Religious_viewall.class));
                finish();
            }
        });
        wonders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Wonders_Load.class));
                finish();
            }
        });
            leh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Leh_Ladakh.class));
                    finish();
                }
            });
       fort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Fort_Load.class));
                finish();
            }
        });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    private void createSlideShow()
    {
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable()
        {
            @Override
            public void run() {
                if(currentposition==mList.size())

                    currentposition=0;
                mainPager.setCurrentItem(currentposition++,true);
            }


        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                handler.post(runnable);
            }
        },1000,5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                showpopup();
                break;
            case R.id.item3:
                showreport();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showreport() {
        TextView closetxt;
        final EditText editText;
        Button btnsend;
        dialog2.setContentView(R.layout.report_layout);
        closetxt=(TextView)dialog2.findViewById(R.id.reportclose);
        editText=(EditText)dialog2.findViewById(R.id.typereport);
        btnsend=(Button) dialog2.findViewById(R.id.reportbtn);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
        closetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=editText.getText().toString();
                if(str.isEmpty())
                {
                    editText.setText("Type something");
                }
                else {
                    Intent i=new Intent(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_EMAIL,new String[]{"rsprojects3470@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT,"Explore App report");
                    i.putExtra(Intent.EXTRA_TEXT,str);
                    i.setType("message/rfc822");
                    startActivity(Intent.createChooser(i,"Select Email App "));
                    dialog2.dismiss();
                }
            }
        });
    }

    private void showpopup() {
        final TextView txtclose, txtname, txtemail, txtphone, txtreset, txtdelete;
        final CircleImageView circleImageView;
        Button btnsignout;
        dialog.setContentView(R.layout.profile_layout);
        txtclose = (TextView) dialog.findViewById(R.id.closetxt);
        txtdelete = (TextView) dialog.findViewById(R.id.deleteact);
        txtreset = (TextView) dialog.findViewById(R.id.resetpassword);
        txtemail = (TextView) dialog.findViewById(R.id.custemail);
        txtname = (TextView) dialog.findViewById(R.id.custname);
        txtphone = (TextView) dialog.findViewById(R.id.custphone);
        circleImageView = (CircleImageView) dialog.findViewById(R.id.custprofile);
        btnsignout = (Button) dialog.findViewById(R.id.signoutact);

        if (user != null) {
            DatabaseReference reference = firebaseDatabase.getReference("User").child("customer").child(user.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Customerdata customerdata = dataSnapshot.getValue(Customerdata.class);
                    txtname.setText(customerdata.getFullname());
                    txtemail.setText(customerdata.getUsername());
                    txtphone.setText(customerdata.getPhone());
                    Picasso.get().load(customerdata.getImageuri()).into(circleImageView);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Customer_drawer.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(Customer_drawer.this, "Please register", Toast.LENGTH_SHORT).show();
        }
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        txtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog4.show();
                dialog4.setContentView(R.layout.delete_acct_layout);
                dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog4.setCanceledOnTouchOutside(false);
                final EditText editpass=(EditText)dialog4.findViewById(R.id.deletepass);
                final TextView textViewdlt=(TextView) dialog4.findViewById(R.id.deleteclose);
                final TextView dltemail=(TextView) dialog4.findViewById(R.id.deleteemail);
                dltemail.setText(user.getEmail());
                Button btndelete=(Button)dialog4.findViewById(R.id.deleteacctbtn);
                textViewdlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog3.dismiss();
                    }
                });
                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pas=editpass.getText().toString();
                        if(pas.isEmpty())
                        {
                           editpass.setError("Enter your password");
                        }
                        else {
                            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),pas );
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                                finish();
                                                                Toast.makeText(getApplicationContext(), "Account Deleted successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });                                        }
                                    });

                        }
                    }
                });
            }
        });
        txtreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog3.show();
                dialog3.setContentView(R.layout.resetpassword_layout);
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog3.setCanceledOnTouchOutside(false);
                final EditText editText1=(EditText)dialog3.findViewById(R.id.rsoldpass);
                final EditText editText2=(EditText)dialog3.findViewById(R.id.rsnewpass);
                final TextView textViewrs=(TextView) dialog3.findViewById(R.id.resetclose);
                Button btnset=(Button)dialog3.findViewById(R.id.resetpassbtn);
                textViewrs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog3.dismiss();
                    }
                });
                btnset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPassword=editText1.getText().toString();
                        final String newPassword=editText2.getText().toString();

                        if(oldPassword.isEmpty())
                        {
                            editText1.setError("Enter old password");
                        }
                        else if(newPassword.isEmpty())
                        {
                            editText2.setError("Enter new password");
                        }
                        else if(newPassword.equals(oldPassword))
                        {
                            editText2.setError("Password must be different from old");
                        }
                        else if(newPassword.length()<8)
                        {
                            editText2.setError("password must 8 to 16 character");
                        }
                        else if(!lowercase.matcher(newPassword).find())
                        {
                            editText2.setError("Aleast one lowercase letter");
                        }
                        else if(!digit.matcher(newPassword).find())
                        {
                            editText2.setError("Aleast one number");
                        }
                        else if(!specialchar.matcher(newPassword).find())
                        {
                            editText2.setError("Aleast one special character");
                        }
                        else if(!uppercase.matcher(newPassword).find())
                        {
                            editText2.setError("Aleast one uppercase letter");
                        }
                        else
                        {
                            AuthCredential credential=EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
                          user.reauthenticate(credential)
                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                          user.updatePassword(newPassword)
                                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<Void> task) {
                                                          if (task.isSuccessful()) {
                                                              FirebaseAuth.getInstance().signOut();
                                                              startActivity(new Intent(getApplicationContext(),Customer_login.class));
                                                              finish();
                                                              Toast.makeText(getApplicationContext(), "password updated successfully", Toast.LENGTH_SHORT).show();
                                                          }
                                                          else
                                                          {
                                                              StyleableToast.makeText(getApplicationContext(),"Failed to update your password!",R.style.errorToast).show();
                                                          }
                                                      }
                                                  });
                                      }
                                  });
                        }
                    }
                });

            }
        });

    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(backPressedTime+1000>System.currentTimeMillis()) {
                backToast.cancel();
                finish();
            }
            else
            {
                backToast=Toast.makeText(Customer_drawer.this, "Press again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_facilities:
                startActivity(new Intent(Customer_drawer.this, Facilities_Activity.class));
                finish();
                break;
            case R.id.nav_Booking:
                startActivity(new Intent(Customer_drawer.this, Booking_Detail.class));
                finish();
                break;
            case R.id.nav_contactus:
                startActivity(new Intent(Customer_drawer.this, ContactUs_Activity.class));
                finish();
                break;
            case R.id.nav_switch:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Admin_login.class));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
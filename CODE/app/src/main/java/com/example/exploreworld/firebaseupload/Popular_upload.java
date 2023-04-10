package com.example.exploreworld.firebaseupload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Admin_drawer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class Popular_upload extends AppCompatActivity {
    ImageView imgpackage,imagepackage1,imagepackage2,imagepackage3,imagepackage4,imagepackage5,imagepackage6,imagepackage7,imagepackage8,imagepackage9,imagepackage10;
    Uri uri,uri1,uri2,uri3,uri4,uri5,uri6,uri7,uri8,uri9,uri10;
    Button upload;
    ScrollView scrollView;
    EditText txt_title,txt_location,txt_day,txt_price,txt_description,txt_tips,txt_activity1,txt_activity2,txt_activity3,txt_activity4,txt_resorttitle,txt_resortlocation,txt_resortdescription,txt_resortprice,txt_resorttitle2,txt_resortlocation2,txt_resortdescription2,txt_resortprice2,txt_resortrating,txt_resortrating2;
    private static final int GALLERY_REQUEST_CODE=0;
    private static final int GALLERY_REQUEST_CODE1=1;
    private static final int GALLERY_REQUEST_CODE2=2;
    private static final int GALLERY_REQUEST_CODE3=3;
    private static final int GALLERY_REQUEST_CODE4=4;
    private static final int GALLERY_REQUEST_CODE5=5;
    private static final int GALLERY_REQUEST_CODE6=6;
    private static final int GALLERY_REQUEST_CODE7=7;
    private static final int GALLERY_REQUEST_CODE8=8;
    private static final int GALLERY_REQUEST_CODE9=9;
    private static final int GALLERY_REQUEST_CODE10=10;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    private String mUri,mUri1,mUri2,mUri3,mUri4,mUri5,mUri6,mUri7,mUri8,mUri9,mUri10;
    AlertDialog alertDialog;
    private FirebaseAuth mAuth;
    ImageView back;
    private DatabaseReference myRef,myratingref,myratingdulex,myratingsemidulex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_upload);
        imgpackage=findViewById(R.id.packageimg);
        upload=findViewById(R.id.uppopulardata);
        scrollView=findViewById(R.id.popularuploadview);
        back=findViewById(R.id.uppopularback);

        txt_title=findViewById(R.id.uppopulartitle);
        txt_location=findViewById(R.id.uppopularlocation);
        txt_day=findViewById(R.id.uppopularday);
        txt_price=findViewById(R.id.uppopularprice);
        txt_description=findViewById(R.id.uppopulardescription);
        txt_tips=findViewById(R.id.uppopulartips);
        imagepackage1=findViewById(R.id.uppopularimg1);
        imagepackage2=findViewById(R.id.uppopularimg2);
        imagepackage3=findViewById(R.id.uppopularimg3);
        imagepackage4=findViewById(R.id.uppopularimg4);
        imagepackage5=findViewById(R.id.uppopularimg5);
        imagepackage6=findViewById(R.id.uppopularimg6);
        imagepackage7=findViewById(R.id.uppopularimg7);
        imagepackage8=findViewById(R.id.uppopularimg8);
        imagepackage9=findViewById(R.id.uppopularimg9);
        imagepackage10=findViewById(R.id.uppopularimg10);

        txt_activity1=findViewById(R.id.popularactivitytitle1);
        txt_activity2=findViewById(R.id.popularactivitytitle2);
        txt_activity3=findViewById(R.id.popularactivitytitle3);
        txt_activity4=findViewById(R.id.popularactivitytitle4);
        txt_resorttitle=findViewById(R.id.popularresorttitle);
        txt_resortlocation=findViewById(R.id.popularresortlocation);
        txt_resortdescription=findViewById(R.id.popularresortdetails);
        txt_resortprice=findViewById(R.id.popularresortprice);
        txt_resorttitle2=findViewById(R.id.popularresorttitle2);
        txt_resortlocation2=findViewById(R.id.popularresortlocation2);
        txt_resortdescription2=findViewById(R.id.popularresortdetails2);
        txt_resortprice2=findViewById(R.id.popularresortprice2);
        txt_resortrating=findViewById(R.id.popularresortrating);
        txt_resortrating2=findViewById(R.id.popularresortrating2);
        myRef= FirebaseDatabase.getInstance().getReference("tourpackages");
        myratingref= FirebaseDatabase.getInstance().getReference("Rating");
        myratingdulex= FirebaseDatabase.getInstance().getReference("Rating");
        myratingsemidulex= FirebaseDatabase.getInstance().getReference("Rating");
        mAuth=FirebaseAuth.getInstance();
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_drawer.class));
                finish();
            }
        });
        imgpackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE);
            }
        });
        imagepackage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE1);
            }
        });
        imagepackage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE2);
            }
        });
        imagepackage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE3);
            }
        });
        imagepackage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE4);
            }
        });
        imagepackage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE5);
            }
        });
        imagepackage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE6);
            }
        });
        imagepackage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE7);
            }
        });
        imagepackage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE8);
            }
        });
        imagepackage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE9);
            }
        });
        imagepackage10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img1=new Intent();
                img1.setType("image/*");
                img1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(img1,"Pick an image"),GALLERY_REQUEST_CODE10);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txt_title.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(Popular_upload.this, "Please enter title", Toast.LENGTH_SHORT).show();
                } else {
                    storageReference = FirebaseStorage.getInstance().getReference().child("tourpackages").child("popularpackage").child(title);
                    alertDialog=new SpotsDialog.Builder().setContext(Popular_upload.this).build();
                    alertDialog.setMessage("Uploading...");
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    uploadImage();
                    uploadImage1();
                    uploadImage2();
                    uploadImage3();
                    uploadImage4();
                    uploadImage5();
                    uploadImage6();
                    uploadImage7();
                    uploadImage8();
                    uploadImage9();
                    uploadImage10();
                }
            }
        });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data !=null) {
            uri = data.getData();
            Glide.with(this).load(uri).into(imgpackage);
        }
        if(requestCode == GALLERY_REQUEST_CODE1 && resultCode == RESULT_OK && data !=null) {
            uri1 = data.getData();
            Glide.with(this).load(uri1).into(imagepackage1);
        }
        if(requestCode == GALLERY_REQUEST_CODE2 && resultCode == RESULT_OK && data !=null) {
            uri2 = data.getData();
            Glide.with(this).load(uri2).into(imagepackage2);
        }
        if(requestCode == GALLERY_REQUEST_CODE3 && resultCode == RESULT_OK && data !=null) {
            uri3 = data.getData();
            Glide.with(this).load(uri3).into(imagepackage3);
        }
        if(requestCode == GALLERY_REQUEST_CODE4 && resultCode == RESULT_OK && data !=null) {
            uri4 = data.getData();
            Glide.with(this).load(uri4).into(imagepackage4);
        }
        if(requestCode == GALLERY_REQUEST_CODE5 && resultCode == RESULT_OK && data !=null) {
            uri5 = data.getData();
            Glide.with(this).load(uri5).into(imagepackage5);
        }
        if(requestCode == GALLERY_REQUEST_CODE6 && resultCode == RESULT_OK && data !=null) {
            uri6 = data.getData();
            Glide.with(this).load(uri6).into(imagepackage6);
        }
        if(requestCode == GALLERY_REQUEST_CODE7 && resultCode == RESULT_OK && data !=null) {
            uri7 = data.getData();
            Glide.with(this).load(uri7).into(imagepackage7);
        }
        if(requestCode == GALLERY_REQUEST_CODE8 && resultCode == RESULT_OK && data !=null) {
            uri8 = data.getData();
            Glide.with(this).load(uri8).into(imagepackage8);
        }
        if(requestCode == GALLERY_REQUEST_CODE9 && resultCode == RESULT_OK && data !=null) {
            uri9 = data.getData();
            Glide.with(this).load(uri9).into(imagepackage9);
        }
        if(requestCode == GALLERY_REQUEST_CODE10 && resultCode == RESULT_OK && data !=null) {
            uri10 = data.getData();
            Glide.with(this).load(uri10).into(imagepackage10);
        }
        else {
            Toast.makeText(Popular_upload.this, "Please select images", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        if(uri==null)
        {
            Toast.makeText(getApplicationContext(), "select package image", Toast.LENGTH_SHORT).show();
        }
        else {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "0." + getFileExtension(uri));
            uploadTask = fileReference.putFile(uri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mUri = downloadUri.toString();

                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void uploadImage1() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "1." + getFileExtension(uri1));
        uploadTask = fileReference.putFile(uri1);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri1 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage2() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "2." + getFileExtension(uri2));
        uploadTask = fileReference.putFile(uri2);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri2 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage3() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "3." + getFileExtension(uri3));
        uploadTask = fileReference.putFile(uri3);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri3 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage4() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "4." + getFileExtension(uri4));
        uploadTask = fileReference.putFile(uri4);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri4 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage5() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "5." + getFileExtension(uri5));
        uploadTask = fileReference.putFile(uri5);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri5 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage6() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "6." + getFileExtension(uri6));
        uploadTask = fileReference.putFile(uri6);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri6 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage7() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "7." + getFileExtension(uri7));
        uploadTask = fileReference.putFile(uri7);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri7 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage8() {

        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "8." + getFileExtension(uri8));
        uploadTask = fileReference.putFile(uri8);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri8 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage9() {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "9." + getFileExtension(uri9));
        uploadTask = fileReference.putFile(uri9);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri9 = downloadUri.toString();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadImage10() {

        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "10." + getFileExtension(uri10));
        uploadTask = fileReference.putFile(uri10);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUri10 = downloadUri.toString();
                    String title = txt_title.getText().toString();
                    String location = txt_location.getText().toString();
                    String description = txt_description.getText().toString();
                    String price = txt_price.getText().toString();
                    String day = txt_day.getText().toString();
                    String tips = txt_tips.getText().toString();
                    String activity1 = txt_activity1.getText().toString();
                    String activity2 = txt_activity2.getText().toString();
                    String activity3 = txt_activity3.getText().toString();
                    String activity4 = txt_activity4.getText().toString();
                    String hotelname = txt_resorttitle.getText().toString();
                    String hotellocation = txt_resortlocation.getText().toString();
                    String hoteldescription = txt_resortdescription.getText().toString();
                    String hotelprice = txt_resortprice.getText().toString();
                    String hotelname2 = txt_resorttitle2.getText().toString();
                    String hotellocation2 = txt_resortlocation2.getText().toString();
                    String hoteldescription2 = txt_resortdescription2.getText().toString();
                    String hotelprice2 = txt_resortprice2.getText().toString();
                    String hotelrating=txt_resortrating.getText().toString();
                    String hotelrating2=txt_resortrating2.getText().toString();
                    String img1=mUri;
                    if (title.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title", Toast.LENGTH_SHORT).show();
                    } else if (location.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter location", Toast.LENGTH_SHORT).show();
                    } else if (description.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                    } else if (price.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter price", Toast.LENGTH_SHORT).show();
                    } else if (day.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter no. of day/night", Toast.LENGTH_SHORT).show();
                    } else if (activity1.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of 1st activity", Toast.LENGTH_SHORT).show();
                    } else if (activity2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of 2nd activity", Toast.LENGTH_SHORT).show();
                    } else if (activity3.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of 3rd activity", Toast.LENGTH_SHORT).show();
                    } else if (activity4.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of 4th activity", Toast.LENGTH_SHORT).show();
                    } else if (hotelname.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of resort", Toast.LENGTH_SHORT).show();
                    } else if (hotellocation.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter location of resort", Toast.LENGTH_SHORT).show();
                    } else if (hoteldescription.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter some description about resort", Toast.LENGTH_SHORT).show();
                    } else if (hotelprice.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter price of resort per room", Toast.LENGTH_SHORT).show();
                    } else if (hotelname2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter title of semi resort", Toast.LENGTH_SHORT).show();
                    } else if (hotellocation2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter location of semi resort", Toast.LENGTH_SHORT).show();
                    } else if (hoteldescription2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter some description about semi resort", Toast.LENGTH_SHORT).show();
                    }
                    else if (hotelprice2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter price of semi resort per room", Toast.LENGTH_SHORT).show();
                    }
                    else if(hotelrating.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "Enter a resort rating", Toast.LENGTH_SHORT).show();
                    }
                    else if(hotelrating2.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "Enter a semiresort rating", Toast.LENGTH_SHORT).show();
                    }
                    else if(img1.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri1==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri2==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri3==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri4==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri5==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri6==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri7==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri8==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else if(mUri9==null)
                    {
                        Toast.makeText(getApplicationContext(), "select package image.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        HashMap<String, Object> profleMap = new HashMap<>();
                        HashMap<String, String> maprate=new HashMap<>();
                        maprate.put("ratevalue","0");
                        maprate.put("packageId",title);
                        profleMap.put("image", mUri);
                        profleMap.put("title", title);
                        profleMap.put("location", location);
                        profleMap.put("price", price);
                        profleMap.put("days", day);
                        profleMap.put("description", description);
                        profleMap.put("tour_tips", tips);
                        profleMap.put("photo1",  mUri1);
                        profleMap.put("photo2", mUri2);
                        profleMap.put("photo3", mUri3);
                        profleMap.put("photo4", mUri4);
                        profleMap.put("activity1", mUri5);
                        profleMap.put("activity2", mUri6);
                        profleMap.put("activity3", mUri7);
                        profleMap.put("activity4", mUri8);
                        profleMap.put("resort", mUri9);
                        profleMap.put("semiresort", mUri10);
                        profleMap.put("activitytitle1", activity1);
                        profleMap.put("activitytitle2", activity2);
                        profleMap.put("activitytitle3", activity3);
                        profleMap.put("activitytitle4", activity4);
                        profleMap.put("resort_name", hotelname);
                        profleMap.put("resort_location", hotellocation);
                        profleMap.put("resort_description", hoteldescription);
                        profleMap.put("resort_price", hotelprice);
                        profleMap.put("resortrating", hotelrating);
                        profleMap.put("semiresort_name", hotelname2);
                        profleMap.put("semiresort_location", hotellocation2);
                        profleMap.put("semiresort_description", hoteldescription2);
                        profleMap.put("semiresort_price", hotelprice2);
                        profleMap.put("semiresortrating", hotelrating2);
                        myRef.child("popularpackage").child(title).setValue(profleMap);
                        myratingref.child("packagerating").child(title).setValue(maprate);
                        alertDialog.dismiss();
                        Snackbar snackbar=Snackbar.make(scrollView,"Package successfully uploaded.",Snackbar.LENGTH_INDEFINITE);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.setBackgroundTint(Color.GREEN);
                        snackbar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(),Admin_drawer.class));
                                finish();
                            }
                        },3000);

                    }

                } else {
                    String message = task.getException().toString();
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Admin_drawer.class));
        finish();
    }
}



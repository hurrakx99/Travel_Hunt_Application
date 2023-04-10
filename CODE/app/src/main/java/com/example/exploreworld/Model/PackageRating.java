package com.example.exploreworld.Model;

public class PackageRating {
    private String userId;
    private String packageId;
    private String ratevalue;

    public PackageRating() {
    }

    public PackageRating(String userId, String packageId, String ratevalue) {
        this.userId = userId;
        this.packageId = packageId;
        this.ratevalue = ratevalue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getRatevalue() {
        return ratevalue;
    }

    public void setRatevalue(String ratevalue) {
        this.ratevalue = ratevalue;
    }
}

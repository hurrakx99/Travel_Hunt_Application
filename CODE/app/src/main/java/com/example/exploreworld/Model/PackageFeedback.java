package com.example.exploreworld.Model;

public class PackageFeedback {
    private String userId;
    private String packageId;
    private String feedback;

    public PackageFeedback() {
    }

    public PackageFeedback(String userId, String packageId, String feedback) {
        this.userId = userId;
        this.packageId = packageId;
        this.feedback = feedback;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

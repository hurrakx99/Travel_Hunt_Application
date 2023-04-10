package com.example.exploreworld.Model;

public class PackageComment {
    private String userId;
    private String packageId;
    private String content;

    public PackageComment() {
    }

    public PackageComment(String userId, String packageId, String content) {
        this.userId = userId;
        this.packageId = packageId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

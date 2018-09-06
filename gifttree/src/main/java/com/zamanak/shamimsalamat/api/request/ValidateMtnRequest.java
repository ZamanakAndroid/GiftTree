package com.zamanak.shamimsalamat.api.request;

public class ValidateMtnRequest {

    private String packageName;
    private String subscriptionId;
    private String token;

    public ValidateMtnRequest(String packageName, String subscriptionId, String token) {
        this.packageName = packageName;
        this.subscriptionId = subscriptionId;
        this.token = token;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package com.github.willwbowen.customerservice.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String CORRELATION_ID = "salonapi-correlation-id";
    public static final String AUTH_TOKEN = "salonapi-auth-token";
    public static final String USER_ID = "salonapi-user-id";
    public static final String SALON_ID = "salonapi-salon-id";

    private String correlationId = new String();
    private String authToken = new String();
    private String userId = new String();
    private String salonId = new String();

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalonId() {
        return salonId;
    }

    public void setSalonId(String salonId) {
        this.salonId = salonId;
    }
}

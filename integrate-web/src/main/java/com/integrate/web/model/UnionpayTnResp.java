package com.integrate.web.model;

public class UnionpayTnResp {
    private String orderId;
    private String tn;

    public UnionpayTnResp(String orderId, String tn) {
        this.orderId = orderId;
        this.tn = tn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}

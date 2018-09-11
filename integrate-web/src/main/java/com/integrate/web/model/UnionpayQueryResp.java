package com.integrate.web.model;

public class UnionpayQueryResp {
    private int status;     // 0.未回调，可轮询  1.充值成功 2.充值失败
    private String msgDesc;

    public UnionpayQueryResp(int status, String msgDesc) {
        this.status = status;
        this.msgDesc = msgDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }
}

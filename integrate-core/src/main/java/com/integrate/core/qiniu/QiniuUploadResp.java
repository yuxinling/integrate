package com.integrate.core.qiniu;

public class QiniuUploadResp {
    private String key;
    private String hash;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

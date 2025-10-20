/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.helper;

public class BIRequestData {
    private byte[] imageByte;
    private String requestMessage;

    public byte[] GetiImageByte() {
        return this.imageByte;
    }

    public String GetRequestMessage() {
        return this.requestMessage;
    }

    public BIRequestData(byte[] imageByte, String requestMessage) {
        this.imageByte = imageByte;
        this.requestMessage = requestMessage;
    }
}


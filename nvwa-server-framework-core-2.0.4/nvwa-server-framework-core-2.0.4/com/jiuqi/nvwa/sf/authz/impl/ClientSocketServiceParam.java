/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.authz.impl;

public class ClientSocketServiceParam {
    private String authzCenterAddress;
    private String productId;
    private String productVersion;
    private String machineCode;
    private String serverPort;
    private String serverIp;

    public String getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthzCenterAddress() {
        return this.authzCenterAddress;
    }

    public void setAuthzCenterAddress(String authzCenterAddress) {
        this.authzCenterAddress = authzCenterAddress;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getMachineCode() {
        return this.machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String toString() {
        return "ClientSocketServiceParam{authzCenterAddress='" + this.authzCenterAddress + '\'' + ", productId='" + this.productId + '\'' + ", productVersion='" + this.productVersion + '\'' + ", machineCode='" + this.machineCode + '\'' + ", serverPort='" + this.serverPort + '\'' + ", serverIp='" + this.serverIp + '\'' + '}';
    }
}


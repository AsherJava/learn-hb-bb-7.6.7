/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import com.jiuqi.gcreport.clbrbill.dto.ClbrBillExtendFieldDTO;

public class ClbrBillMasterDTO
extends ClbrBillExtendFieldDTO {
    private String clbrCode;
    private String contractCode;
    private String contractName;
    private String currency;
    private String digest;
    private String initiateOrg;
    private String initiateUser;
    private String receiveOrg;
    private String receiveUser;

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return this.contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getInitiateOrg() {
        return this.initiateOrg;
    }

    public void setInitiateOrg(String initiateOrg) {
        this.initiateOrg = initiateOrg;
    }

    public String getInitiateUser() {
        return this.initiateUser;
    }

    public void setInitiateUser(String initiateUser) {
        this.initiateUser = initiateUser;
    }

    public String getReceiveOrg() {
        return this.receiveOrg;
    }

    public void setReceiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
    }

    public String getReceiveUser() {
        return this.receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }
}


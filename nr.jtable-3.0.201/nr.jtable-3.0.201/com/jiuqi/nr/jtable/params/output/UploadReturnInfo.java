/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.io.Serializable;
import java.util.List;

public class UploadReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ReturnInfo> message;
    private int successEntityNum;
    private int errorEntityNum;
    private List<ReturnInfo> successEntity;
    private int otherErrorNum;
    private int otherErrorFromNum;
    private int otherErrorFromGroupNum;
    private int successFormNums;
    private int errorFormNums;
    private int successFromGroupNums;
    private int errorFromGroupNums;
    private int allEntityNums;
    private int allFormGroupNums;
    private int allFormNums;

    public List<ReturnInfo> getMessage() {
        return this.message;
    }

    public void setMessage(List<ReturnInfo> message) {
        this.message = message;
    }

    public int getErrorEntityNum() {
        return this.errorEntityNum;
    }

    public void setErrorEntityNum(int errorEntityNum) {
        this.errorEntityNum = errorEntityNum;
    }

    public int getSuccessEntityNum() {
        return this.successEntityNum;
    }

    public void setSuccessEntityNum(int successEntityNum) {
        this.successEntityNum = successEntityNum;
    }

    public void addSuccessNum() {
        ++this.successEntityNum;
    }

    public void addErrorNum() {
        ++this.errorEntityNum;
    }

    public int getOtherErrorNum() {
        return this.otherErrorNum;
    }

    public void setOtherErrorNum(int otherErrorNum) {
        this.otherErrorNum = otherErrorNum;
    }

    public void addOtherErrorNum() {
        ++this.otherErrorNum;
    }

    public int getSuccessFormNums() {
        return this.successFormNums;
    }

    public void setSuccessFormNums(int successFormNums) {
        this.successFormNums = successFormNums;
    }

    public int getErrorFormNums() {
        return this.errorFormNums;
    }

    public void setErrorFormNums(int errorFormNums) {
        this.errorFormNums = errorFormNums;
    }

    public void addSuccessFormNums() {
        ++this.successFormNums;
    }

    public void addSomeFormNums(int nums) {
        this.successFormNums += nums;
    }

    public void addErrorFormNums() {
        ++this.errorFormNums;
    }

    public void addSomeErrorFormNums(int nums) {
        this.errorFormNums += nums;
    }

    public void addSuccessFormGroupNums() {
        ++this.successFromGroupNums;
    }

    public void addErrorFormGroupNums() {
        ++this.errorFromGroupNums;
    }

    public void addSomeErrorFormGroupNums(int nums) {
        this.errorFromGroupNums += nums;
    }

    public List<ReturnInfo> getSuccessEntity() {
        return this.successEntity;
    }

    public void setSuccessEntity(List<ReturnInfo> successEntity) {
        this.successEntity = successEntity;
    }

    public int getSuccessFromGroupNums() {
        return this.successFromGroupNums;
    }

    public void setSuccessFromGroupNums(int successFromGroupNums) {
        this.successFromGroupNums = successFromGroupNums;
    }

    public int getErrorFromGroupNums() {
        return this.errorFromGroupNums;
    }

    public void setErrorFromGroupNums(int errorFromGroupNums) {
        this.errorFromGroupNums = errorFromGroupNums;
    }

    public int getOtherErrorFromNum() {
        return this.otherErrorFromNum;
    }

    public void setOtherErrorFromNum(int otherErrorFromNum) {
        this.otherErrorFromNum = otherErrorFromNum;
    }

    public int getOtherErrorFromGroupNum() {
        return this.otherErrorFromGroupNum;
    }

    public void setOtherErrorFromGroupNum(int otherErrorFromGroupNum) {
        this.otherErrorFromGroupNum = otherErrorFromGroupNum;
    }

    public void addOtherErrorFromGroupNum() {
        ++this.otherErrorFromGroupNum;
    }

    public void addotherErrorFromNum() {
        ++this.otherErrorFromNum;
    }

    public int getAllEntityNums() {
        return this.allEntityNums;
    }

    public void setAllEntityNums(int allEntityNums) {
        this.allEntityNums = allEntityNums;
    }

    public void addAllEntityNums() {
        ++this.allEntityNums;
    }

    public int getAllFormGroupNums() {
        return this.allFormGroupNums;
    }

    public void setAllFormGroupNums(int allFormGroupNums) {
        this.allFormGroupNums = allFormGroupNums;
    }

    public void addAllFormGroupNums(int n) {
        this.allFormGroupNums += n;
    }

    public int getAllFormNums() {
        return this.allFormNums;
    }

    public void setAllFormNums(int allFormNums) {
        this.allFormNums += allFormNums;
    }

    public void addAllFormNums() {
        ++this.allFormNums;
    }
}


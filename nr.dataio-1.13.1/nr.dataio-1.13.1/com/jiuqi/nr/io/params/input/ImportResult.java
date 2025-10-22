/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ImportResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String regionTitle;
    private Set<String> successOrgs = new HashSet<String>();
    private int successOrgNum = 0;
    private int successDataNum = 0;
    private Set<String> failureOrgs = new HashSet<String>();
    private int failureOrgNum = 0;
    private int failureDataNum = 0;
    private int amendDataNum = 0;

    public ImportResult() {
    }

    public ImportResult(int successOrgNum, int successDataNum, int failureOrgNum, int failureDataNum, int amendDataNum) {
        this.successOrgNum = successOrgNum;
        this.successDataNum = successDataNum;
        this.failureOrgNum = failureOrgNum;
        this.failureDataNum = failureDataNum;
        this.amendDataNum = amendDataNum;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRegionTitle() {
        return this.regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public Set<String> getSuccessOrgs() {
        return this.successOrgs;
    }

    public void setSuccessOrgs(Set<String> successOrgs) {
        this.successOrgs = successOrgs;
        this.successOrgNum = successOrgs.size();
    }

    public int getSuccessOrgNum() {
        return this.successOrgNum;
    }

    public void setSuccessOrgNum(int successOrgNum) {
        this.successOrgNum = successOrgNum;
    }

    public int getSuccessDataNum() {
        return this.successDataNum;
    }

    public void setSuccessDataNum(int successDataNum) {
        this.successDataNum = successDataNum;
    }

    public Set<String> getFailureOrgs() {
        return this.failureOrgs;
    }

    public void setFailureOrgs(Set<String> failureOrgs) {
        this.failureOrgs = failureOrgs;
        this.failureOrgNum = failureOrgs.size();
    }

    public int getFailureOrgNum() {
        return this.failureOrgNum;
    }

    public void setFailureOrgNum(int failureOrgNum) {
        this.failureOrgNum = failureOrgNum;
    }

    public int getFailureDataNum() {
        return this.failureDataNum;
    }

    public void setFailureDataNum(int failureDataNum) {
        this.failureDataNum = failureDataNum;
    }

    public int getAmendDataNum() {
        return this.amendDataNum;
    }

    public void setAmendDataNum(int amendDataNum) {
        this.amendDataNum = amendDataNum;
    }

    public void addSuccessOrgNum(int number) {
        this.successOrgNum += number;
    }

    public void addSuccessDataNum(int number) {
        this.successDataNum += number;
    }

    public void addFailureOrgNum(int number) {
        this.failureOrgNum += number;
    }

    public void addFailureDataNum(int number) {
        this.failureDataNum += number;
    }

    public void addAmendDataNum(int number) {
        this.amendDataNum += number;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.file;

public class SingleFileHead {
    public static final short HEADLENGTH = 86;
    private short headSize;
    private short version;
    private String usageSign;
    private long fileSize;
    private int infoSize;
    private String jobName;
    private long jobID;
    private double jobTime;
    private byte jobType;
    private int diskNo;
    private byte haveNext;
    private byte havePW;
    private String pW;

    public short getHeadSize() {
        return this.headSize;
    }

    public void setHeadSize(short value) {
        this.headSize = value;
    }

    public short getVersion() {
        return this.version;
    }

    public void setVersion(short value) {
        this.version = value;
    }

    public String getUsageSign() {
        return this.usageSign;
    }

    public void setUsageSign(String value) {
        this.usageSign = value;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long value) {
        this.fileSize = value;
    }

    public int getInfoSize() {
        return this.infoSize;
    }

    public void setInfoSize(int value) {
        this.infoSize = value;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String value) {
        this.jobName = value;
    }

    public long getJobID() {
        return this.jobID;
    }

    public void setJobID(long value) {
        this.jobID = value;
    }

    public double getJobTime() {
        return this.jobTime;
    }

    public void setJobTime(double value) {
        this.jobTime = value;
    }

    public byte getJobType() {
        return this.jobType;
    }

    public void setJobType(byte value) {
        this.jobType = value;
    }

    public int getDiskNo() {
        return this.diskNo;
    }

    public void setDiskNo(int value) {
        this.diskNo = value;
    }

    public boolean getHaveNext() {
        return this.haveNext == 1;
    }

    public void setHaveNext(boolean value) {
        this.haveNext = value ? (byte)1 : 0;
    }

    public void setHaveNext(byte haveNext) {
        this.haveNext = haveNext;
    }

    public byte getHavePW() {
        return this.havePW;
    }

    public void setHavePW(byte value) {
        this.havePW = value;
    }

    public String getPW() {
        return this.pW;
    }

    public void setPW(String value) {
        this.pW = value;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import java.util.List;

public class ParameterVO {
    private String exchangeMode;
    private boolean showTask;
    private List<String> taskKeys;
    private boolean showMidstore;
    private List<String> midstoreList;
    private boolean multiplePeriod;
    private String orgLink;

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public List<String> getMidstoreList() {
        return this.midstoreList;
    }

    public void setMidstoreList(List<String> midstoreList) {
        this.midstoreList = midstoreList;
    }

    public boolean isMultiplePeriod() {
        return this.multiplePeriod;
    }

    public void setMultiplePeriod(boolean multiplePeriod) {
        this.multiplePeriod = multiplePeriod;
    }

    public boolean isShowMidstore() {
        return this.showMidstore;
    }

    public void setShowMidstore(boolean showMidstore) {
        this.showMidstore = showMidstore;
    }

    public boolean isShowTask() {
        return this.showTask;
    }

    public void setShowTask(boolean showTask) {
        this.showTask = showTask;
    }

    public List<String> getTaskKeys() {
        return this.taskKeys;
    }

    public void setTaskKeys(List<String> taskKeys) {
        this.taskKeys = taskKeys;
    }

    public String getOrgLink() {
        return this.orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }
}


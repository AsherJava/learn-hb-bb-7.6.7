/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UploadRecordNew
 *  com.jiuqi.nr.bpm.setting.pojo.ShowResult
 *  com.jiuqi.nr.jtable.params.output.EntityData
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.ArrayList;
import java.util.List;

public class UploadActionInfo {
    private String id;
    private String code;
    private String title;
    private boolean leaf;
    private List<UploadRecordNew> actions = new ArrayList<UploadRecordNew>();
    public ShowResult showResult;
    private boolean isDefaultFlow = false;
    private int uploadedCount = 0;
    private int submitedCount = 0;
    private int returnedCount = 0;
    private int confirmedCount = 0;
    private int rejectedCount = 0;

    public boolean isDefaultFlow() {
        return this.isDefaultFlow;
    }

    public void setDefaultFlow(boolean isDefaultFlow) {
        this.isDefaultFlow = isDefaultFlow;
    }

    public ShowResult getShowResult() {
        return this.showResult;
    }

    public void setShowResult(ShowResult showResult) {
        this.showResult = showResult;
    }

    public UploadActionInfo(EntityData entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.leaf = entity.isLeaf();
    }

    public UploadActionInfo(String id, String code, String title, boolean leaf) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.leaf = leaf;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<UploadRecordNew> getActions() {
        return this.actions;
    }

    public void setActions(List<UploadRecordNew> actions) {
        this.actions = actions;
        this.countActions();
    }

    public int getUploadedCount() {
        return this.uploadedCount;
    }

    public void setUploadedCount(int uploadedCount) {
        this.uploadedCount = uploadedCount;
    }

    public int getSubmitedCount() {
        return this.submitedCount;
    }

    public void setSubmitedCount(int submitedCount) {
        this.submitedCount = submitedCount;
    }

    public int getReturnedCount() {
        return this.returnedCount;
    }

    public void setReturnedCount(int returnedCount) {
        this.returnedCount = returnedCount;
    }

    public int getConfirmedCount() {
        return this.confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getRejectedCount() {
        return this.rejectedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    private void countActions() {
        this.uploadedCount = 0;
        this.submitedCount = 0;
        this.returnedCount = 0;
        this.confirmedCount = 0;
        this.rejectedCount = 0;
        for (UploadRecordNew action : this.actions) {
            switch (action.getAction()) {
                case "act_submit": 
                case "cus_submit": {
                    ++this.submitedCount;
                    break;
                }
                case "act_return": 
                case "cus_return": {
                    ++this.returnedCount;
                    break;
                }
                case "act_upload": 
                case "cus_upload": {
                    ++this.uploadedCount;
                    break;
                }
                case "act_confirm": 
                case "cus_confirm": {
                    ++this.confirmedCount;
                    break;
                }
                case "act_reject": 
                case "cus_reject": {
                    ++this.rejectedCount;
                    break;
                }
            }
        }
    }
}


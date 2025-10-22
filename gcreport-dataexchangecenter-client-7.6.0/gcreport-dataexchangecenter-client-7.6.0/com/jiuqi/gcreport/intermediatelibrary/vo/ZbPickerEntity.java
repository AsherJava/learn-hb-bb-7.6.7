/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerDataLinkEntity;
import java.util.ArrayList;
import java.util.List;

public class ZbPickerEntity {
    private String formKey;
    private String schemeKey;
    private List<ZbPickerDataLinkEntity> selections = new ArrayList<ZbPickerDataLinkEntity>();
    private String taskId;
    private List<String> zbs = new ArrayList<String>();

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public List<ZbPickerDataLinkEntity> getSelections() {
        return this.selections;
    }

    public void setSelections(List<ZbPickerDataLinkEntity> selections) {
        this.selections = selections;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getZbs() {
        return this.zbs;
    }

    public void setZbs(List<String> zbs) {
        this.zbs = zbs;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.ArrayList;
import java.util.List;

public class CopyReturnInfo {
    private EntityData copyEntity;
    private FormData copyForm;
    private List<String> message = new ArrayList<String>();
    private String dataTime;
    private String status;

    public EntityData getCopyEntity() {
        return this.copyEntity;
    }

    public void setCopyEntity(EntityData copyEntity) {
        this.copyEntity = copyEntity;
    }

    public FormData getCopyForm() {
        return this.copyForm;
    }

    public void setCopyForm(FormData copyForm) {
        this.copyForm = copyForm;
    }

    public List<String> getMessage() {
        return this.message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


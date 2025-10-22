/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.snapshot.message.DataRange
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.snapshot.message.DataRange;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSnapshotParam
implements Serializable,
INRContext {
    private JtableContext context;
    private String snapshotId;
    private String title;
    private String describe;
    private List<String> formKeys;
    private List<String> snapshotIds;
    private DataRange dataRange;
    private String periodCode;
    private List<String> periodCodeList;
    private boolean compareAllField;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public boolean isCompareAllField() {
        return this.compareAllField;
    }

    public void setCompareAllField(boolean compareAllField) {
        this.compareAllField = compareAllField;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getSnapshotIds() {
        return this.snapshotIds;
    }

    public void setSnapshotIds(List<String> snapshotIds) {
        this.snapshotIds = snapshotIds;
    }

    public DataRange getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(DataRange dataRange) {
        this.dataRange = dataRange;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public List<String> getPeriodCodeList() {
        return this.periodCodeList;
    }

    public void setPeriodCodeList(List<String> periodCodeList) {
        this.periodCodeList = periodCodeList;
    }
}


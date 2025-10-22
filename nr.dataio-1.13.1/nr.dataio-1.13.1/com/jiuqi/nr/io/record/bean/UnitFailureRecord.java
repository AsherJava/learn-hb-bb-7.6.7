/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.io.record.bean;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.math.BigDecimal;
import java.util.List;

@DBAnno.DBTable(dbTable="NR_DX_UNIT_FAIL_RECORD")
public class UnitFailureRecord {
    @DBAnno.DBField(dbField="UNIT_FAIL_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="REC_KEY")
    private String recKey;
    @DBAnno.DBField(dbField="FACTORY_ID")
    private String factoryId;
    @DBAnno.DBField(dbField="DW_KEY")
    private String dwKey;
    @DBAnno.DBField(dbField="DW_CODE")
    private String dwCode;
    @DBAnno.DBField(dbField="DW_TITLE")
    private String dwTitle;
    @DBAnno.DBField(dbField="DW_ORDER")
    private BigDecimal dwOrder;
    private List<String> subRecords;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getFactoryId() {
        return this.factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getDwKey() {
        return this.dwKey;
    }

    public void setDwKey(String dwKey) {
        this.dwKey = dwKey;
    }

    public String getDwCode() {
        return this.dwCode;
    }

    public void setDwCode(String dwCode) {
        this.dwCode = dwCode;
    }

    public String getDwTitle() {
        return this.dwTitle;
    }

    public void setDwTitle(String dwTitle) {
        this.dwTitle = dwTitle;
    }

    public BigDecimal getDwOrder() {
        return this.dwOrder;
    }

    public void setDwOrder(BigDecimal dwOrder) {
        this.dwOrder = dwOrder;
    }

    public List<String> getSubRecords() {
        return this.subRecords;
    }

    public void setSubRecords(List<String> subRecords) {
        this.subRecords = subRecords;
    }
}


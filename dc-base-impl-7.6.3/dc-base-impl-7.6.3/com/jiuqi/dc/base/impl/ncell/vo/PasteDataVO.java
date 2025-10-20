/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.ncell.vo;

import com.jiuqi.dc.base.impl.ncell.vo.ColumnDefineVO;
import java.util.Map;

public class PasteDataVO {
    private String orgType;
    private String orgVerCode;
    private String orgVerName;
    private String columnValue;
    private ColumnDefineVO columnDefine;
    private Map<String, Object> extParams;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVerCode() {
        return this.orgVerCode;
    }

    public void setOrgVerCode(String orgVerCode) {
        this.orgVerCode = orgVerCode;
    }

    public String getOrgVerName() {
        return this.orgVerName;
    }

    public void setOrgVerName(String orgVerName) {
        this.orgVerName = orgVerName;
    }

    public String getColumnValue() {
        return this.columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public ColumnDefineVO getColumnDefine() {
        return this.columnDefine;
    }

    public void setColumnDefine(ColumnDefineVO columnDefine) {
        this.columnDefine = columnDefine;
    }

    public Map<String, Object> getExtParams() {
        return this.extParams;
    }

    public void setExtParams(Map<String, Object> extParams) {
        this.extParams = extParams;
    }
}


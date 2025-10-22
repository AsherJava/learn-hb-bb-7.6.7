/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam
 */
package com.jiuqi.gcreport.org.impl.tablepaste.vo;

import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.ColumnDefineVO;
import java.util.Map;

public class PasteDataVO
extends GcOrgVerApiParam {
    private String columnValue;
    private ColumnDefineVO columnDefine;
    private Map<String, Object> extParams;

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


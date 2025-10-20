/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.mobile.approval.vo;

import com.jiuqi.gcreport.mobile.approval.vo.FieldDataInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class FormDataReturnInfo {
    @ApiModelProperty(value="\u67e5\u8be2\u4fe1\u606f", name="queryInfo")
    private FormDataQueryInfo queryInfo;
    @ApiModelProperty(value="\u6307\u6807\u6570\u636e", name="fieldDatas")
    private List<List<FieldDataInfo>> fieldDatas;

    public FormDataQueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public void setQueryInfo(FormDataQueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public List<List<FieldDataInfo>> getFieldDatas() {
        return this.fieldDatas;
    }

    public void setFieldDatas(List<List<FieldDataInfo>> fieldDatas) {
        this.fieldDatas = fieldDatas;
    }
}


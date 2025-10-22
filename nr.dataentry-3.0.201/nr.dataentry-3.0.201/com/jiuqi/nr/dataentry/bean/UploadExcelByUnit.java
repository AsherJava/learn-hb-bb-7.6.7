/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.bean.ZipExcelDimensionObject;
import java.io.Serializable;

public class UploadExcelByUnit
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ZipExcelDimensionObject zipExcelDimensionObject;
    private UploadParam param;

    public ZipExcelDimensionObject getZipExcelDimensionObject() {
        return this.zipExcelDimensionObject;
    }

    public void setZipExcelDimensionObject(ZipExcelDimensionObject zipExcelDimensionObject) {
        this.zipExcelDimensionObject = zipExcelDimensionObject;
    }

    public UploadParam getParam() {
        return this.param;
    }

    public void setParam(UploadParam param) {
        this.param = param;
    }
}


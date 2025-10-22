/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.journalsingle.entity;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u8fc7\u8d26\u65b9\u6848\u9879\u76ee\u6570\u636e")
public class JournalSingleSchemeExcelModel {
    @ExcelColumn(index=0, title={"\u5e8f\u53f7"})
    private String index;
    @ExcelColumn(index=1, title={"\u9879\u76ee\u7f16\u7801"})
    private String code;
    @ExcelColumn(index=2, title={"\u9879\u76ee\u540d\u79f0"})
    private String title;
    @ExcelColumn(index=3, title={"\u501f\u8d37\u65b9\u5411"})
    private String orient;
    @ExcelColumn(index=4, title={"\u5173\u8054\u8c03\u6574\u524d\u6307\u6807"})
    private String beforeZbCode;
    @ExcelColumn(index=5, title={"\u5173\u8054\u8c03\u6574\u540e\u6307\u6807"})
    private String afterZbCode;
    @ExcelColumn(index=6, title={"\u662f\u5426\u663e\u793a"})
    private String needShow;

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

    public String getOrient() {
        return this.orient;
    }

    public void setOrient(String orient) {
        this.orient = orient;
    }

    public String getBeforeZbCode() {
        return this.beforeZbCode;
    }

    public void setBeforeZbCode(String beforeZbCode) {
        this.beforeZbCode = beforeZbCode;
    }

    public String getAfterZbCode() {
        return this.afterZbCode;
    }

    public void setAfterZbCode(String afterZbCode) {
        this.afterZbCode = afterZbCode;
    }

    public String getNeedShow() {
        return this.needShow;
    }

    public void setNeedShow(String needShow) {
        this.needShow = needShow;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}


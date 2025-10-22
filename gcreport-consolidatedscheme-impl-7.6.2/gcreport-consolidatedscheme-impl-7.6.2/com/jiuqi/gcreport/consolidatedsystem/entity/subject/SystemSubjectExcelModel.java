/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.subject;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u4f53\u7cfb\u79d1\u76ee")
public class SystemSubjectExcelModel {
    @ExcelColumn(index=0, title={"\u79d1\u76ee\u7f16\u7801"})
    private String code;
    @ExcelColumn(index=1, title={"\u79d1\u76ee\u540d\u79f0"})
    private String title;
    @ExcelColumn(index=2, title={"\u4e0a\u7ea7\u79d1\u76ee\u7f16\u7801"})
    private String parentid;
    @ExcelColumn(index=3, title={"\u542f\u7528/\u505c\u7528"})
    private String status;
    @ExcelColumn(index=4, title={"\u5408\u5e76\u65b9\u5f0f"})
    private String consolidationType;
    @ExcelColumn(index=5, title={"\u501f\u8d37\u65b9\u5411"})
    private String orient;
    @ExcelColumn(index=6, title={"\u9879\u76ee\u5927\u7c7b"})
    private String attri;
    @ExcelColumn(index=7, title={"\u5173\u8054\u6307\u6807"})
    private String boundIndexPath;

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

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsolidationType() {
        return this.consolidationType;
    }

    public void setConsolidationType(String consolidationType) {
        this.consolidationType = consolidationType;
    }

    public String getOrient() {
        return this.orient;
    }

    public void setOrient(String orient) {
        this.orient = orient;
    }

    public String getAttri() {
        return this.attri;
    }

    public void setAttri(String attri) {
        this.attri = attri;
    }

    public String getBoundIndexPath() {
        return this.boundIndexPath;
    }

    public void setBoundIndexPath(String boundIndexPath) {
        this.boundIndexPath = boundIndexPath;
    }
}


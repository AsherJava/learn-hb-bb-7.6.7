/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.clbr.executor.model;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u4e1a\u52a1\u534f\u540c\u65b9\u6848Excel\u4fe1\u606f")
public class ClbrSchemeExcelModel {
    @ExcelColumn(index=0, title={"\u7236\u7ea7\u5206\u7ec4\u540d\u79f0"})
    private String parentId;
    @ExcelColumn(index=1, title={"\u65b9\u6848\u540d\u79f0"})
    private String title;
    @ExcelColumn(index=2, title={"\u53d1\u8d77\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801(*\u5fc5\u586b)"})
    private String clbrTypes;
    @ExcelColumn(index=3, title={"\u53d1\u8d77\u65b9\u4e1a\u52a1\u7c7b\u578b\u540d\u79f0"})
    private String clbrTypesName;
    @ExcelColumn(index=4, title={"\u53d1\u8d77\u65b9\u4ee3\u7801(*\u5fc5\u586b)"})
    private String relations;
    @ExcelColumn(index=5, title={"\u53d1\u8d77\u65b9\u540d\u79f0"})
    private String relationsName;
    @ExcelColumn(index=6, title={"\u63a5\u6536\u65b9\u4ee3\u7801(*\u5fc5\u586b)"})
    private String oppRelations;
    @ExcelColumn(index=7, title={"\u63a5\u6536\u65b9\u540d\u79f0"})
    private String oppRelationsName;
    @ExcelColumn(index=8, title={"\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801(*\u5fc5\u586b)"})
    private String oppClbrTypes;
    @ExcelColumn(index=9, title={"\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u540d\u79f0"})
    private String oppClbrTypesName;
    @ExcelColumn(index=10, title={"\u6d41\u7a0b\u63a7\u5236\u7c7b\u578b"})
    private String flowControlType;
    @ExcelColumn(index=11, title={"\u51ed\u8bc1\u63a7\u5236"})
    private String vchrControlType;

    public String getClbrTypes() {
        return this.clbrTypes;
    }

    public void setClbrTypes(String clbrTypes) {
        this.clbrTypes = clbrTypes;
    }

    public String getRelations() {
        return this.relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getOppRelations() {
        return this.oppRelations;
    }

    public void setOppRelations(String oppRelations) {
        this.oppRelations = oppRelations;
    }

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public String getClbrTypesName() {
        return this.clbrTypesName;
    }

    public void setClbrTypesName(String clbrTypesName) {
        this.clbrTypesName = clbrTypesName;
    }

    public String getRelationsName() {
        return this.relationsName;
    }

    public void setRelationsName(String relationsName) {
        this.relationsName = relationsName;
    }

    public String getOppRelationsName() {
        return this.oppRelationsName;
    }

    public void setOppRelationsName(String oppRelationsName) {
        this.oppRelationsName = oppRelationsName;
    }

    public String getOppClbrTypesName() {
        return this.oppClbrTypesName;
    }

    public void setOppClbrTypesName(String oppClbrTypesName) {
        this.oppClbrTypesName = oppClbrTypesName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}


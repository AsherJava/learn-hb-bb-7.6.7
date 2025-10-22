/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.archive.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ARCHIVECONFIG", title="\u62a5\u8868\u5f52\u6863\u65b9\u6848\u914d\u7f6e\u8868", inStorage=true, indexs={})
public class ArchiveConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ARCHIVECONFIG";
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="ORGTYPE", title="\u53e3\u5f84", dbType=DBColumn.DBType.Varchar, length=36, isRequired=false)
    private String orgType;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="EXCEL_FORMINFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String excelFormInfos;
    @DBColumn(nameInDB="PDF_FORMINFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String pdfFormInfos;
    @DBColumn(nameInDB="OFD_FORMINFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String ofdFormInfos;
    @DBColumn(nameInDB="ATTACHMENT_FORMINFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String attachmentFormInfos;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public static String getTABLENAME() {
        return TABLENAME;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getExcelFormInfos() {
        return this.excelFormInfos;
    }

    public void setExcelFormInfos(String excelFormInfos) {
        this.excelFormInfos = excelFormInfos;
    }

    public String getPdfFormInfos() {
        return this.pdfFormInfos;
    }

    public void setPdfFormInfos(String pdfFormInfos) {
        this.pdfFormInfos = pdfFormInfos;
    }

    public String getAttachmentFormInfos() {
        return this.attachmentFormInfos;
    }

    public void setAttachmentFormInfos(String attachmentFormInfos) {
        this.attachmentFormInfos = attachmentFormInfos;
    }

    public String getOfdFormInfos() {
        return this.ofdFormInfos;
    }

    public void setOfdFormInfos(String ofdFormInfos) {
        this.ofdFormInfos = ofdFormInfos;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="IDOC_CAPTURE_BILL_ATTACH", title="\u9644\u4ef6\u5173\u8054\u8868", inStorage=true, indexs={})
public class IdocCaptureBillAttachEo
extends DefaultTableEntity {
    public static final String TABLENAME = "IDOC_CAPTURE_BILL_ATTACH";
    @DBColumn(nameInDB="BILLPK", title="\u5f53\u524d\u51ed\u8bc1\u6216\u5355\u636e\u539f\u59cb\u4e3b\u952e", dbType=DBColumn.DBType.Varchar, isRequired=true, length=200)
    private String billPk;
    @DBColumn(nameInDB="DOCTYPE", title="\u5f53\u524d\u51ed\u8bc1\u6216\u5355\u636e\u7684\u7c7b\u578b\u7f16\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String docType;
    @DBColumn(nameInDB="FILENAME", title="\u9644\u4ef6\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=500, isRequired=true)
    private String fileName;
    @DBColumn(nameInDB="FILEURL", title="\u9644\u4ef6\u6587\u4ef6\u5730\u5740", dbType=DBColumn.DBType.Varchar, length=500, isRequired=true)
    private String fileUrl;
    @DBColumn(nameInDB="FILESIZE", title="\u9644\u4ef6\u6587\u4ef6\u5927\u5c0f\uff1aB", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer fileSize;
    @DBColumn(nameInDB="DIGITALDIGEST", title="\u6570\u5b57\u6458\u8981", dbType=DBColumn.DBType.Varchar, length=100, isRequired=true)
    private String digitalDigest;
    @DBColumn(nameInDB="FILEDESC", title="\u9644\u4ef6\u63cf\u8ff0", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String fileDesc;
    @DBColumn(nameInDB="TS", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String ts;
    @DBColumn(nameInDB="ATTACHTYPE", title="\u9644\u4ef6\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer attachType;
    @DBColumn(nameInDB="ATTACHJSONDATA", title="\u53d1\u7968\u6216\u56de\u5355\u6570\u636eJSON", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String attachJsonData;
    @DBColumn(nameInDB="ORGCODE", title="\u4f01\u4e1a\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String orgCode;
    @DBColumn(nameInDB="BBORGCODE", title="\u62a5\u8868\u5355\u4f4d\u7f16\u7801", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String bbOrgCode;
    @DBColumn(nameInDB="ORGNAME", title="\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String orgName;

    public String getBillPk() {
        return this.billPk;
    }

    public void setBillPk(String billPk) {
        this.billPk = billPk;
    }

    public String getDocType() {
        return this.docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getDigitalDigest() {
        return this.digitalDigest;
    }

    public void setDigitalDigest(String digitalDigest) {
        this.digitalDigest = digitalDigest;
    }

    public String getFileDesc() {
        return this.fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Integer getAttachType() {
        return this.attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public String getAttachJsonData() {
        return this.attachJsonData;
    }

    public void setAttachJsonData(String attachJsonData) {
        this.attachJsonData = attachJsonData;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBbOrgCode() {
        return this.bbOrgCode;
    }

    public void setBbOrgCode(String bbOrgCode) {
        this.bbOrgCode = bbOrgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}


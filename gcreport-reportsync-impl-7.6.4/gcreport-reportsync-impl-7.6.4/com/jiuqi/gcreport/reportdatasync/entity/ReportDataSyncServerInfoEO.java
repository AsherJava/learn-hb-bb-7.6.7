/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_DATASYNC_SERVERINFO", title="\u56fd\u8d44\u59d4\u6570\u636e\u540c\u6b65\u670d\u52a1\u4fe1\u606f")
public class ReportDataSyncServerInfoEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATASYNC_SERVERINFO";
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=60)
    private String targetTitle;
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668URL", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String targetUrl;
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String targetUserName;
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668\u5bc6\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String targetPwd;
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668\u5bc6\u7801\u52a0\u5bc6\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=100)
    private String targetEncryptType;
    @DBColumn(title="\u4e0a\u62a5\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String orgTitle;
    @DBColumn(title="\u5bf9\u5e94\u56fd\u8d44\u59d4\u7aef\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true, length=60)
    private String orgCode;
    @DBColumn(title="\u672c\u5730\u670d\u52a1\u5668URL", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String url;
    @DBColumn(title="\u672c\u5730\u670d\u52a1\u5668\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String userName;
    @DBColumn(title="\u672c\u5730\u670d\u52a1\u5668\u5bc6\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String pwd;
    @DBColumn(title="\u672c\u5730\u670d\u52a1\u5668\u5bc6\u7801\u52a0\u5bc6\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=100)
    private String encryptType;
    @DBColumn(title="\u5355\u4f4d\u8054\u7cfb\u4eba", dbType=DBColumn.DBType.Varchar, length=200)
    private String contacts;
    @DBColumn(title="\u5355\u4f4d\u8054\u7cfb\u4ebaID", dbType=DBColumn.DBType.Varchar, length=500)
    private String contactIds;
    @DBColumn(title="\u8054\u7cfb\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar, length=300)
    private String contactInfos;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date modifyTime;
    @DBColumn(title="\u540c\u6b65\u53c2\u6570\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncParamFlag;
    @DBColumn(title="\u662f\u5426\u5141\u8bb8\u4fee\u6539\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncModifyFlag;

    public String getTargetEncryptType() {
        return this.targetEncryptType;
    }

    public void setTargetEncryptType(String targetEncryptType) {
        this.targetEncryptType = targetEncryptType;
    }

    public String getTargetTitle() {
        return this.targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetUserName() {
        return this.targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getTargetPwd() {
        return this.targetPwd;
    }

    public void setTargetPwd(String targetPwd) {
        this.targetPwd = targetPwd;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactIds() {
        return this.contactIds;
    }

    public void setContactIds(String contactIds) {
        this.contactIds = contactIds;
    }

    public String getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(String contactInfos) {
        this.contactInfos = contactInfos;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getSyncParamFlag() {
        return this.syncParamFlag;
    }

    public void setSyncParamFlag(Integer syncParamFlag) {
        this.syncParamFlag = syncParamFlag;
    }

    public Integer getSyncModifyFlag() {
        return this.syncModifyFlag;
    }

    public void setSyncModifyFlag(Integer syncModifyFlag) {
        this.syncModifyFlag = syncModifyFlag;
    }
}


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

@DBTable(name="GC_DATASYNC_SERVERLIST", title="\u56fd\u8d44\u59d4\u6570\u636e\u540c\u6b65\u670d\u52a1\u4fe1\u606f\u5217\u8868")
public class ReportDataSyncServerListEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATASYNC_SERVERLIST";
    @DBColumn(title="\u670d\u52a1\u5668URL", dbType=DBColumn.DBType.Varchar, length=100)
    private String url;
    @DBColumn(title="\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=100)
    private String userName;
    @DBColumn(title="\u5bc6\u7801", dbType=DBColumn.DBType.Varchar, length=100)
    private String pwd;
    @DBColumn(title="\u52a0\u5bc6\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=100)
    private String encryptType;
    @DBColumn(title="\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.Varchar, isRequired=true, length=100)
    private String orgTitle;
    @DBColumn(title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true, length=60)
    private String orgCode;
    @DBColumn(title="\u517c\u7ba1\u673a\u6784", dbType=DBColumn.DBType.Varchar, length=500)
    private String manageOrgCodes;
    @DBColumn(title="\u5355\u4f4d\u8054\u7cfb\u4eba", dbType=DBColumn.DBType.Varchar, length=200)
    private String contacts;
    @DBColumn(title="\u5355\u4f4d\u8054\u7cfb\u4ebaId", dbType=DBColumn.DBType.Varchar, length=200)
    private String contactIds;
    @DBColumn(title="\u8054\u7cfb\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar, length=300)
    private String contactInfos;
    @DBColumn(title="\u672c\u7ea7\u8d1f\u8d23\u4eba", dbType=DBColumn.DBType.Varchar, length=200)
    private String manageUsers;
    @DBColumn(title="\u672c\u7ea7\u8d1f\u8d23\u4ebaID", dbType=DBColumn.DBType.Varchar, length=500)
    private String manageUserIds;
    @DBColumn(title="\u542f\u7528\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer startFlag;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date modifyTime;
    @DBColumn(title="\u4f01\u4e1a\u7aef\u652f\u6301\u901a\u4fe1\u529f\u80fd", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer supportAll;
    @DBColumn(title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=100)
    private String sourceType;
    @DBColumn(title="\u670d\u52a1\u6ce8\u518c\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=20)
    private String serverType;
    @DBColumn(title="\u540c\u6b65\u53c2\u6570\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncParamFlag;
    @DBColumn(title="\u662f\u5426\u5141\u8bb8\u4fee\u6539\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncModifyFlag;
    @DBColumn(title="\u76ee\u6807\u670d\u52a1\u5668\u7c7b\u578b", nameInDB="syncMethod", dbType=DBColumn.DBType.Varchar, length=50)
    private String syncMethod;
    @DBColumn(title="\u8bbe\u7f6e\u5185\u5bb9", nameInDB="CONTENT", dbType=DBColumn.DBType.NText)
    private String content;
    @DBColumn(title="\u540c\u6b65\u7c7b\u578b", nameInDB="syncType", dbType=DBColumn.DBType.Varchar, length=50)
    private String syncType;
    @DBColumn(title="\u6587\u4ef6\u683c\u5f0f", nameInDB="fileFormat", dbType=DBColumn.DBType.Varchar, length=20)
    private String fileFormat;

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getManageOrgCodes() {
        return this.manageOrgCodes;
    }

    public void setManageOrgCodes(String manageOrgCodes) {
        this.manageOrgCodes = manageOrgCodes;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactInfos() {
        return this.contactInfos;
    }

    public void setContactInfos(String contactInfos) {
        this.contactInfos = contactInfos;
    }

    public String getManageUsers() {
        return this.manageUsers;
    }

    public void setManageUsers(String manageUsers) {
        this.manageUsers = manageUsers;
    }

    public String getManageUserIds() {
        return this.manageUserIds;
    }

    public void setManageUserIds(String manageUserIds) {
        this.manageUserIds = manageUserIds;
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

    public Integer getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Integer startFlag) {
        this.startFlag = startFlag;
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

    public Integer getSupportAll() {
        return this.supportAll;
    }

    public void setSupportAll(Integer supportAll) {
        this.supportAll = supportAll;
    }

    public String getServerType() {
        return this.serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileFormat() {
        return this.fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getSyncMethod() {
        return this.syncMethod;
    }

    public void setSyncMethod(String syncMethod) {
        this.syncMethod = syncMethod;
    }

    public String getSyncType() {
        return this.syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getContactIds() {
        return this.contactIds;
    }

    public void setContactIds(String contactIds) {
        this.contactIds = contactIds;
    }
}


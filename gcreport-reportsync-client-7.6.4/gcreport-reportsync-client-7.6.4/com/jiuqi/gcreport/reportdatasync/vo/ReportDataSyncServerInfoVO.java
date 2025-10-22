/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportDataSyncServerInfoVO {
    private String id;
    private String targetTitle;
    private String targetUrl;
    private String targetUserName;
    private String targetPwd;
    private String targetEncryptType;
    private String url;
    private String userName;
    private String pwd;
    private String encryptType;
    private String orgTitle;
    private String orgCode;
    private List<String> manageOrgCodes;
    private List<Map<String, String>> manageOrgMap;
    private String contacts;
    private String contactIds;
    private String contactInfos;
    private String createTime;
    private String modifyTime;
    private String manageUsers;
    private String manageUserIds;
    private Boolean startFlag = Boolean.TRUE;
    private Boolean supportAll = Boolean.TRUE;
    private String sourceType;
    private String serverType;
    private Boolean syncParamFlag = Boolean.FALSE;
    private Boolean syncModifyFlag = Boolean.FALSE;
    private List<String> syncType;
    private String fileFormat;
    private String syncMethod;
    private String content;

    public String getTargetEncryptType() {
        return this.targetEncryptType;
    }

    public void setTargetEncryptType(String targetEncryptType) {
        this.targetEncryptType = targetEncryptType;
    }

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetTitle() {
        return this.targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
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

    public List<String> getManageOrgCodes() {
        return this.manageOrgCodes == null ? new ArrayList() : this.manageOrgCodes;
    }

    public void setManageOrgCodes(List<String> manageOrgCodes) {
        this.manageOrgCodes = manageOrgCodes;
    }

    public List<Map<String, String>> getManageOrgMap() {
        return this.manageOrgMap;
    }

    public void setManageOrgMap(List<Map<String, String>> manageOrgMap) {
        this.manageOrgMap = manageOrgMap;
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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public Boolean getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public Boolean getSupportAll() {
        return this.supportAll;
    }

    public void setSupportAll(Boolean supportAll) {
        this.supportAll = supportAll;
    }

    public String getServerType() {
        return this.serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Boolean getSyncParamFlag() {
        return this.syncParamFlag;
    }

    public void setSyncParamFlag(Boolean syncParamFlag) {
        this.syncParamFlag = syncParamFlag;
    }

    public Boolean getSyncModifyFlag() {
        return this.syncModifyFlag;
    }

    public void setSyncModifyFlag(Boolean syncModifyFlag) {
        this.syncModifyFlag = syncModifyFlag;
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

    public List<String> getSyncType() {
        return this.syncType == null ? new ArrayList() : this.syncType;
    }

    public void setSyncType(List<String> syncType) {
        this.syncType = syncType;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.gcreport.oauth2.pojo;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GcSsoBuildDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String frontAddress;
    private String userName;
    private String jumpType = "app";
    private String appName;
    private String router;
    private String scope;
    private String expose;
    private String appConfig;
    private String openConfig;
    private String loginUnit;
    private String loginDate;
    private String extInfo;
    private String pv = "nvwa-cer";
    private boolean addTokenId = true;
    @Deprecated
    private String uiCode;
    private String scheme;
    private String title;

    public String getUserName() {
        return this.userName;
    }

    public String getJumpType() {
        return this.jumpType;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getScope() {
        return this.scope;
    }

    public String getExpose() {
        return this.expose;
    }

    public String getAppConfig() {
        return this.appConfig;
    }

    public String getOpenConfig() {
        return this.openConfig;
    }

    public String getLoginUnit() {
        return this.loginUnit;
    }

    public String getLoginDate() {
        return this.loginDate;
    }

    public String getExtInfo() {
        return this.extInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setExpose(String expose) {
        this.expose = expose;
    }

    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }

    public void setOpenConfig(String openConfig) {
        this.openConfig = openConfig;
    }

    public void setLoginUnit(String loginUnit) {
        this.loginUnit = loginUnit;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getPv() {
        return this.pv;
    }

    public boolean isAddTokenId() {
        return this.addTokenId;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public void setAddTokenId(boolean addTokenId) {
        this.addTokenId = addTokenId;
    }

    public String getRouter() {
        return this.router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public String getFrontAddress() {
        return this.frontAddress;
    }

    public void setFrontAddress(String frontAddress) {
        this.frontAddress = frontAddress;
    }

    public String getUiCode() {
        return this.uiCode;
    }

    public void setUiCode(String uiCode) {
        this.uiCode = uiCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


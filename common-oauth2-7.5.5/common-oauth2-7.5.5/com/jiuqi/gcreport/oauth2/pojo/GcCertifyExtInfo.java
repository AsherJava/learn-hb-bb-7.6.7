/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.gcreport.oauth2.pojo;

import com.jiuqi.gcreport.oauth2.pojo.CustomParam;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GcCertifyExtInfo {
    private String authUrl;
    private String tokenUrl;
    private String userInfoUrl;
    private String redirectUri;
    private String jumpType = "go";
    private String appName;
    private String usernameField;
    private Boolean blockLoginPage = true;
    private String thirdPartyLoginPage;
    private String logoutPage;
    private Boolean autoAddUser = false;
    private Boolean hashModeRouter = true;
    private Boolean encodeRedirectUri = false;
    private Boolean stateIsUiCode = false;
    private String noUiSchemePermissionsTips = "\u5bf9\u4e0d\u8d77\uff0c\u60a8\u65e0\u6b64\u5e73\u53f0\u8bbf\u95ee\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u4e3a\u60a8\u589e\u52a0\u6743\u9650";
    private String loginPageBlockedTips = "\u5f53\u524d\u7cfb\u7edf\u5df2\u5c4f\u853d\u9ed8\u8ba4\u767b\u5f55\u9875\uff0c\u8bf7\u4f7f\u7528\u5355\u70b9\u767b\u5f55\uff0c\u8c22\u8c22\u3002";
    private String userNotFoundTipsTemplate = "\u60a8\u7684\u7528\u6237[%s]\u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002";
    private Boolean checkFuncRoutes = true;
    private String hasNoFuncRoutesTipsTemplate = "\u60a8\u7684\u7528\u6237[%s]\u6ca1\u6709\u529f\u80fd\u83dc\u5355\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002";
    private String accessTokenInFormKey;
    private String reverseLookupUserAttrName;
    private String accessTokenField = "access_token";
    private Map<String, CustomParam> customParams;
    private String unionUserLoginFailTips;
    private Boolean strongSecurity = false;

    public String getAuthUrl() {
        return this.authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getTokenUrl() {
        return this.tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getUserInfoUrl() {
        return this.userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getUsernameField() {
        return this.usernameField;
    }

    public void setUsernameField(String usernameField) {
        this.usernameField = usernameField;
    }

    public String getJumpType() {
        return this.jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean getBlockLoginPage() {
        return this.blockLoginPage;
    }

    public void setBlockLoginPage(Boolean blockLoginPage) {
        this.blockLoginPage = blockLoginPage;
    }

    public String getLogoutPage() {
        return this.logoutPage;
    }

    public void setLogoutPage(String logoutPage) {
        this.logoutPage = logoutPage;
    }

    public Boolean getHashModeRouter() {
        return this.hashModeRouter;
    }

    public void setHashModeRouter(Boolean hashModeRouter) {
        this.hashModeRouter = hashModeRouter;
    }

    public Boolean getEncodeRedirectUri() {
        return this.encodeRedirectUri;
    }

    public void setEncodeRedirectUri(Boolean encodeRedirectUri) {
        this.encodeRedirectUri = encodeRedirectUri;
    }

    public Boolean getStateIsUiCode() {
        return this.stateIsUiCode;
    }

    public void setStateIsUiCode(Boolean stateIsUiCode) {
        this.stateIsUiCode = stateIsUiCode;
    }

    public String getNoUiSchemePermissionsTips() {
        return this.noUiSchemePermissionsTips;
    }

    public void setNoUiSchemePermissionsTips(String noUiSchemePermissionsTips) {
        this.noUiSchemePermissionsTips = noUiSchemePermissionsTips;
    }

    public Boolean getAutoAddUser() {
        return this.autoAddUser;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public String getLoginPageBlockedTips() {
        return this.loginPageBlockedTips;
    }

    public void setLoginPageBlockedTips(String loginPageBlockedTips) {
        this.loginPageBlockedTips = loginPageBlockedTips;
    }

    public String getThirdPartyLoginPage() {
        return this.thirdPartyLoginPage;
    }

    public void setThirdPartyLoginPage(String thirdPartyLoginPage) {
        this.thirdPartyLoginPage = thirdPartyLoginPage;
    }

    public String getUserNotFoundTipsTemplate() {
        return this.userNotFoundTipsTemplate;
    }

    public void setUserNotFoundTipsTemplate(String userNotFoundTipsTemplate) {
        this.userNotFoundTipsTemplate = userNotFoundTipsTemplate;
    }

    public Boolean getCheckFuncRoutes() {
        return this.checkFuncRoutes;
    }

    public void setCheckFuncRoutes(Boolean checkFuncRoutes) {
        this.checkFuncRoutes = checkFuncRoutes;
    }

    public String getHasNoFuncRoutesTipsTemplate() {
        return this.hasNoFuncRoutesTipsTemplate;
    }

    public void setHasNoFuncRoutesTipsTemplate(String hasNoFuncRoutesTipsTemplate) {
        this.hasNoFuncRoutesTipsTemplate = hasNoFuncRoutesTipsTemplate;
    }

    public Map<String, CustomParam> getCustomParams() {
        return this.customParams;
    }

    public void setCustomParams(Map<String, CustomParam> customParams) {
        this.customParams = customParams;
    }

    public String getAccessTokenInFormKey() {
        return this.accessTokenInFormKey;
    }

    public void setAccessTokenInFormKey(String accessTokenInFormKey) {
        this.accessTokenInFormKey = accessTokenInFormKey;
    }

    public String getReverseLookupUserAttrName() {
        return this.reverseLookupUserAttrName;
    }

    public void setReverseLookupUserAttrName(String reverseLookupUserAttrName) {
        this.reverseLookupUserAttrName = reverseLookupUserAttrName;
    }

    public String getAccessTokenField() {
        return this.accessTokenField;
    }

    public void setAccessTokenField(String accessTokenField) {
        this.accessTokenField = accessTokenField;
    }

    public String getUnionUserLoginFailTips() {
        return this.unionUserLoginFailTips;
    }

    public void setUnionUserLoginFailTips(String unionUserLoginFailTips) {
        this.unionUserLoginFailTips = unionUserLoginFailTips;
    }

    public Boolean getStrongSecurity() {
        return this.strongSecurity;
    }

    public void setStrongSecurity(Boolean strongSecurity) {
        this.strongSecurity = strongSecurity;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


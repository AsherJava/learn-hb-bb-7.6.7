/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk;

import com.jiuqi.gcreport.oauth2.pojo.GcSsoBuildDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DingtalkCertifyExtInfo
extends GcSsoBuildDTO {
    private static final long serialVersionUID = 180468864082753440L;
    private String unionId = "me";
    private String reverseLookupUserField;
    private String redirectUri;
    private String usernameField;
    private Boolean blockLoginPage = true;
    private String thirdPartyLoginPage;
    private String logoutPage;
    private Boolean hashModeRouter = true;
    private String noUiSchemePermissionsTips = "\u5bf9\u4e0d\u8d77\uff0c\u60a8\u65e0\u6b64\u5e73\u53f0\u8bbf\u95ee\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u4e3a\u60a8\u589e\u52a0\u6743\u9650";
    private String loginPageBlockedTips = "\u5f53\u524d\u7cfb\u7edf\u5df2\u5c4f\u853d\u9ed8\u8ba4\u767b\u5f55\u9875\uff0c\u8bf7\u4f7f\u7528\u5355\u70b9\u767b\u5f55\uff0c\u8c22\u8c22\u3002";
    private String userNotFoundTipsTemplate = "\u60a8\u7684\u7528\u6237[%s]\u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002";
    private String hasNoFuncRoutesTipsTemplate = "\u60a8\u7684\u7528\u6237[%s]\u6ca1\u6709\u529f\u80fd\u83dc\u5355\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002";
    private String reverseLookupUserAttrName;
    private String unionUserLoginFailTips;

    public String getUsernameField() {
        return this.usernameField;
    }

    public void setUsernameField(String usernameField) {
        this.usernameField = usernameField;
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

    public String getNoUiSchemePermissionsTips() {
        return this.noUiSchemePermissionsTips;
    }

    public void setNoUiSchemePermissionsTips(String noUiSchemePermissionsTips) {
        this.noUiSchemePermissionsTips = noUiSchemePermissionsTips;
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

    public String getHasNoFuncRoutesTipsTemplate() {
        return this.hasNoFuncRoutesTipsTemplate;
    }

    public void setHasNoFuncRoutesTipsTemplate(String hasNoFuncRoutesTipsTemplate) {
        this.hasNoFuncRoutesTipsTemplate = hasNoFuncRoutesTipsTemplate;
    }

    public String getReverseLookupUserAttrName() {
        return this.reverseLookupUserAttrName;
    }

    public void setReverseLookupUserAttrName(String reverseLookupUserAttrName) {
        this.reverseLookupUserAttrName = reverseLookupUserAttrName;
    }

    public String getUnionUserLoginFailTips() {
        return this.unionUserLoginFailTips;
    }

    public void setUnionUserLoginFailTips(String unionUserLoginFailTips) {
        this.unionUserLoginFailTips = unionUserLoginFailTips;
    }

    public String getUnionId() {
        return this.unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public static long getSerialversionuid() {
        return 180468864082753440L;
    }

    public String getReverseLookupUserField() {
        return this.reverseLookupUserField;
    }

    public void setReverseLookupUserField(String reverseLookupUserField) {
        this.reverseLookupUserField = reverseLookupUserField;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


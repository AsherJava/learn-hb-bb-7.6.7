/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class NvwaLoginUserDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String pwd;
    private boolean encrypted = false;
    private String encryptType = "";
    private String tenant;
    private boolean checkPwd = true;
    private String loginUnit;
    private Date loginDate;
    private Long loginUTCDate;
    private Map<String, Object> extInfo = null;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isEncrypted() {
        if (null != this.encryptType && !"".equals(this.encryptType)) {
            return true;
        }
        return this.encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getTenant() {
        return this.tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public boolean isCheckPwd() {
        return this.checkPwd;
    }

    public void setCheckPwd(boolean checkPwd) {
        this.checkPwd = checkPwd;
    }

    public String getLoginUnit() {
        return this.loginUnit;
    }

    public void setLoginUnit(String loginUnit) {
        this.loginUnit = loginUnit;
    }

    public Long getLoginUTCDate() {
        return this.loginUTCDate;
    }

    public void setLoginUTCDate(Long loginUTCDate) {
        this.loginUTCDate = loginUTCDate;
    }

    @Deprecated
    public Date getLoginDate() {
        if (this.loginDate == null && this.loginUTCDate != null) {
            this.loginDate = new Date(this.loginUTCDate);
        }
        return this.loginDate;
    }

    @Deprecated
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        this.loginUTCDate = null != loginDate ? Long.valueOf(loginDate.getTime()) : null;
    }

    public void setLoginDate(String loginDate) {
        if (!StringUtils.hasLength(loginDate)) {
            return;
        }
        if (loginDate.length() > 10) {
            this.loginUTCDate = Long.parseLong(loginDate);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(loginDate);
                TimeZone defaultZone = TimeZone.getDefault();
                Calendar ca = Calendar.getInstance(defaultZone);
                ca.setTime(date);
                TimeZone timeZone = LocaleContextHolder.getTimeZone();
                if (!defaultZone.getDisplayName().equals(timeZone.getDisplayName())) {
                    ca.add(14, defaultZone.getRawOffset() - timeZone.getRawOffset());
                }
                this.loginUTCDate = ca.getTimeInMillis();
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public Object getExtInfo(String key) {
        if (this.extInfo == null) {
            return null;
        }
        return this.extInfo.get(key);
    }

    public void addExtInfo(String key, Object value) {
        if (this.extInfo == null) {
            this.extInfo = new HashMap<String, Object>();
        }
        this.extInfo.put(key, value);
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }
}


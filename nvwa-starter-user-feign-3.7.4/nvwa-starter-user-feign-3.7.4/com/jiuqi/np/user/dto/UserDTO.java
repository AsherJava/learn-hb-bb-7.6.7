/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.np.user.AttrEncryptType
 *  com.jiuqi.np.user.CertificateType
 *  com.jiuqi.np.user.Sex
 *  com.jiuqi.np.user.User
 */
package com.jiuqi.np.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.np.user.AttrEncryptType;
import com.jiuqi.np.user.CertificateType;
import com.jiuqi.np.user.Sex;
import com.jiuqi.np.user.User;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class UserDTO
implements User {
    private static final long serialVersionUID = 1143900866291911309L;
    private String id;
    private String name;
    private String fullname;
    private String nickname;
    private String alias;
    private byte[] avatar;
    private String telephone;
    private String wechat;
    private String email;
    private CertificateType certificateType = CertificateType.UNKNOW;
    private String certificateNumber;
    private Sex sex = Sex.UNKNOW;
    private LocalDate birthday;
    private String description;
    private Instant createTime;
    private Instant modifyTime;
    private Boolean locked;
    private Boolean enabled;
    private Boolean pwdLocked;
    private Boolean infoLocked;
    private String creator;
    private String orgCode;
    private String securityLevel;
    private int userType;
    private Boolean markDelete = Boolean.FALSE;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Instant expiryTime;
    private AttrEncryptType userEncryptType;
    private Map<String, Object> attributes;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;
    private Instant loginTime;
    private String modifier;
    private String sign;
    private String language;
    private Boolean digitalEmploy;

    public Instant getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte[] getAvatar() {
        return this.avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWechat() {
        return this.wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CertificateType getCertificateType() {
        return this.certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return this.certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getPwdLocked() {
        return this.pwdLocked;
    }

    public void setPwdLocked(Boolean pwdLocked) {
        this.pwdLocked = pwdLocked;
    }

    public Boolean getInfoLocked() {
        return this.infoLocked;
    }

    public void setInfoLocked(Boolean infoLocked) {
        this.infoLocked = infoLocked;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSecurityLevel() {
        return this.securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Boolean getMarkDelete() {
        return this.markDelete;
    }

    public void setMarkDelete(Boolean markDelete) {
        this.markDelete = markDelete;
    }

    public AttrEncryptType getUserEncryptType() {
        return this.userEncryptType;
    }

    public void setUserEncryptType(AttrEncryptType encryptType) {
        this.userEncryptType = encryptType;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public LocalDateTime getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Instant getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Instant loginTime) {
        this.loginTime = loginTime;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getDigitalEmploy() {
        return this.digitalEmploy;
    }

    public void setDigitalEmploy(Boolean digitalEmploy) {
        this.digitalEmploy = digitalEmploy;
    }
}


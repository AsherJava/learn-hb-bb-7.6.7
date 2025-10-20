/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import java.util.List;

public class DataSyncNvwaUserDTO {
    private String id;
    private String username;
    private String fullName;
    private String nickName;
    private String telephone;
    private String wechat;
    private String email;
    private int certificateType;
    private String certificateNumber;
    private int sex;
    private String birthday;
    private boolean enabled = true;
    private String orgCode;
    private int dataStatus;
    private String lastModifyTime;
    private List<DataSyncNvwaAttributeDTO> attributes;
    private List<String> grantedOrgS;

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getWechat() {
        return this.wechat;
    }

    public String getEmail() {
        return this.email;
    }

    public int getCertificateType() {
        return this.certificateType;
    }

    public String getCertificateNumber() {
        return this.certificateNumber;
    }

    public int getSex() {
        return this.sex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCertificateType(int certificateType) {
        this.certificateType = certificateType;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getDataStatus() {
        return this.dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<DataSyncNvwaAttributeDTO> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<DataSyncNvwaAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public List<String> getGrantedOrgS() {
        return this.grantedOrgS;
    }

    public void setGrantedOrgS(List<String> grantedOrgS) {
        this.grantedOrgS = grantedOrgS;
    }
}


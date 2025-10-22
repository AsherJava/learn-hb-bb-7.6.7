/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.bean.daoObject;

import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordViewObject;
import com.jiuqi.nr.sensitive.common.SensitiveWordType;

public class SensitiveWordDaoObject {
    private String sensitiveWordKey;
    private String sensitiveCode;
    private Integer sensitiveType;
    private Integer sensitiveWordType;
    private String sensitiveInfo;
    private String sensitiveDescription;
    private Integer isEffective;
    private String modifyTime;
    private String modifyUser;

    public SensitiveWordDaoObject() {
    }

    public SensitiveWordDaoObject(SensitiveWordViewObject sensitiveWordViewObject) {
        this.sensitiveType = sensitiveWordViewObject.getSensitiveType().equals("expressionType") ? SensitiveWordType.REGULAR_EXPRESSION.getValue() : SensitiveWordType.STRING.getValue();
        this.sensitiveWordType = sensitiveWordViewObject.getSensitiveWordType();
        this.sensitiveInfo = sensitiveWordViewObject.getSensitiveInfo();
        this.sensitiveDescription = sensitiveWordViewObject.getSensitiveDescription();
        this.isEffective = sensitiveWordViewObject.isEffective() ? Integer.valueOf(0) : Integer.valueOf(1);
    }

    public String getSensitiveWordKey() {
        return this.sensitiveWordKey;
    }

    public void setSensitiveWordKey(String sensitiveWordKey) {
        this.sensitiveWordKey = sensitiveWordKey;
    }

    public Integer getIsEffective() {
        return this.isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public String getSensitiveCode() {
        return this.sensitiveCode;
    }

    public void setSensitiveCode(String sensitiveCode) {
        this.sensitiveCode = sensitiveCode;
    }

    public Integer getSensitiveType() {
        return this.sensitiveType;
    }

    public void setSensitiveType(Integer sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

    public String getSensitiveInfo() {
        return this.sensitiveInfo;
    }

    public void setSensitiveInfo(String sensitiveInfo) {
        this.sensitiveInfo = sensitiveInfo;
    }

    public String getSensitiveDescription() {
        return this.sensitiveDescription;
    }

    public void setSensitiveDescription(String sensitiveDescription) {
        this.sensitiveDescription = sensitiveDescription;
    }

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public Integer getSensitiveWordType() {
        return this.sensitiveWordType;
    }

    public void setSensitiveWordType(Integer sensitiveWordType) {
        this.sensitiveWordType = sensitiveWordType;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}


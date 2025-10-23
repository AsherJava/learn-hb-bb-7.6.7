/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nvwa.encryption.desensitization.bean.DesensitizationInfo;

public class DesensitizationInfoVO {
    private String code;
    private String name;

    public DesensitizationInfoVO() {
    }

    public DesensitizationInfoVO(DesensitizationInfo desensitizationInfo) {
        if (desensitizationInfo != null) {
            this.code = desensitizationInfo.getCode();
            this.name = desensitizationInfo.getName();
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


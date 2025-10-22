/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.exception.ParameterUtils;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import java.io.Serializable;

public class BusinessKeyImpl
implements BusinessKey,
Serializable {
    private static final long serialVersionUID = 1L;
    protected String formSchemeKey;
    protected MasterEntity masterEntity;
    protected String period;
    protected String formKey;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public MasterEntityInfo getMasterEntityInfo() {
        return this.masterEntity;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public BusinessKey setFormSchemeKey(String formSchemeKey) {
        ParameterUtils.AssertNotNull("formSchemeKey", formSchemeKey);
        this.formSchemeKey = formSchemeKey;
        return this;
    }

    @Override
    public BusinessKey setMasterEntity(MasterEntityInfo masterEntityInfo) {
        ParameterUtils.AssertNotNull("masterEntityInfo", masterEntityInfo);
        this.masterEntity = masterEntityInfo instanceof MasterEntity ? (MasterEntity)masterEntityInfo : new MasterEntityImpl(masterEntityInfo);
        return this;
    }

    @Override
    public BusinessKey setPeriod(String period) {
        this.period = period;
        return this;
    }

    @Override
    public BusinessKey setFormKey(String formKey) {
        this.formKey = formKey;
        return this;
    }

    @Override
    public MasterEntity getMasterEntity() {
        return this.masterEntity;
    }

    @Override
    public String toString() {
        return BusinessKeyFormatter.formatToString(this);
    }
}


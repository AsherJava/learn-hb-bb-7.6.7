/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import com.jiuqi.nr.bpm.exception.ParameterUtils;
import java.util.Set;

public class BusinessKeySetImpl
implements BusinessKeySet {
    protected String formSchemeKey;
    protected MasterEntitySet masterEntitySet;
    protected String period;
    protected Set<String> formKeys;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public MasterEntitySetInfo getMasterEntitySetInfo() {
        return this.masterEntitySet;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    @Override
    public Set<String> getFormKey() {
        return this.formKeys;
    }

    @Override
    public BusinessKeySet setFormSchemeKey(String formSchemeKey) {
        ParameterUtils.AssertNotNull("formSchemeKey", formSchemeKey);
        this.formSchemeKey = formSchemeKey;
        return this;
    }

    @Override
    public BusinessKeySet setMasterEntitySet(MasterEntitySetInfo masterEntitySetInfo) {
        ParameterUtils.AssertNotNull("masterEntitySetInfo", masterEntitySetInfo);
        this.masterEntitySet = masterEntitySetInfo instanceof MasterEntitySet ? (MasterEntitySet)masterEntitySetInfo : new MasterEntitySetImpl(masterEntitySetInfo);
        return this;
    }

    @Override
    public BusinessKeySet setPeriod(String period) {
        this.period = period;
        return this;
    }

    @Override
    public BusinessKeySet setFormKey(Set<String> formKey) {
        this.formKeys = formKey;
        return this;
    }

    @Override
    public MasterEntitySet getMasterEntitySet() {
        return this.masterEntitySet;
    }
}


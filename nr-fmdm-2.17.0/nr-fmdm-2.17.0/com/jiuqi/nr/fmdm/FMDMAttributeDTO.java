/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.nr.fmdm.domain.FMDMAttributeDO;
import org.springframework.util.Assert;

public class FMDMAttributeDTO
extends FMDMAttributeDO {
    public void setAttributeCode(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        super.setCode(code);
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' can not be empty.");
        super.setFormSchemeKey(formSchemeKey);
    }
}


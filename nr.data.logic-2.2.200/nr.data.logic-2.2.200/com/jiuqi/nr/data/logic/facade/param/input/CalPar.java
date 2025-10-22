/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import java.io.Serializable;

public class CalPar
implements Serializable {
    private static final long serialVersionUID = -750968439225760959L;
    private String formSchemeKey;
    private BaseFmlFactoryParam baseFmlFactoryParam;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public BaseFmlFactoryParam getBaseFmlFactoryParam() {
        return this.baseFmlFactoryParam;
    }

    public void setBaseFmlFactoryParam(BaseFmlFactoryParam baseFmlFactoryParam) {
        this.baseFmlFactoryParam = baseFmlFactoryParam;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidator;
import com.jiuqi.nr.data.logic.internal.obj.CKDCheckSetting;

@Deprecated
public abstract class DefCKDValidator
implements ICheckDesValidator {
    protected final CKDCheckSetting ckdCheckSetting;

    public DefCKDValidator(CKDCheckSetting ckdCheckSetting) {
        this.ckdCheckSetting = ckdCheckSetting;
    }
}


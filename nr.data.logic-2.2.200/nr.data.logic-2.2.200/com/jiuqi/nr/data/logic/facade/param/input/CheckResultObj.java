/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;

public class CheckResultObj {
    private final FmlCheckResultEntity fmlCheckResultEntity;
    private final CheckDescription checkDescription;

    public CheckResultObj(FmlCheckResultEntity fmlCheckResultEntity, CheckDescription checkDescription) {
        this.fmlCheckResultEntity = fmlCheckResultEntity;
        this.checkDescription = checkDescription;
    }

    public FmlCheckResultEntity getFmlCheckResultEntity() {
        return this.fmlCheckResultEntity;
    }

    public CheckDescription getCheckDescription() {
        return this.checkDescription;
    }
}


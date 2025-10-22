/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.nr.data.logic.facade.extend.param.AutoCalStrategy;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseCalFormFmlParam;

public class AutoCalFormFmlParam
extends BaseCalFormFmlParam {
    private static final long serialVersionUID = 9005172881860668219L;

    @Override
    public AutoCalStrategy getStrategy() {
        return AutoCalStrategy.FIND_FML;
    }

    @Override
    public AutoCalStrategy getDowngradeStrategy() {
        return AutoCalStrategy.BJ_FORM;
    }
}


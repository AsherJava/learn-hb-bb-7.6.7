/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.data.AbstractData;

public class UnmodifiedAbstractData
extends AbstractData {
    public static final int UNMODIFIED_TYPES = 999999999;

    protected UnmodifiedAbstractData() {
        super(999999999, false);
    }

    public boolean getAsNull() {
        return false;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelException;

public interface IDataSetModelProvider {
    public DSModel findModel(String var1) throws DSModelException;
}


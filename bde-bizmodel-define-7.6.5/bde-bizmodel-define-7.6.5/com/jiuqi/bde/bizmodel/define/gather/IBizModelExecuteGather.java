/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define.gather;

import com.jiuqi.bde.bizmodel.define.IBizModelExecute;

public interface IBizModelExecuteGather {
    public IBizModelExecute getModelExecuteByCode(String var1);

    public void reload() throws Exception;
}


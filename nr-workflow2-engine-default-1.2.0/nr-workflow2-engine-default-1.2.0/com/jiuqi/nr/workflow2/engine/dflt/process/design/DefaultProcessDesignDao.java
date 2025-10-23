/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDO;

public interface DefaultProcessDesignDao {
    public boolean addDefaultProcessConfig(String var1, String var2);

    public boolean deleteDefaultProcessConfig(String var1);

    public boolean updateDefaultProcessConfig(String var1, String var2);

    public DefaultProcessDO queryDefaultProcessConfig(String var1);
}


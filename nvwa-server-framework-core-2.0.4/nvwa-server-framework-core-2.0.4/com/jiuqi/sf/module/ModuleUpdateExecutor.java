/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.sf.module.ModuleContext
 */
package com.jiuqi.sf.module;

import com.jiuqi.sf.module.ModuleContext;
import java.sql.Connection;

public interface ModuleUpdateExecutor {
    public void execute(Connection var1, ModuleContext var2) throws Exception;
}


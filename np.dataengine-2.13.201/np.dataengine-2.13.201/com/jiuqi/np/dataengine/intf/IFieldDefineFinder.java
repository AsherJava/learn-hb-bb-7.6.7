/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;

public interface IFieldDefineFinder {
    public FieldDefine findFieldDefine(ExecutorContext var1, String var2) throws Exception;

    public FieldDefine findFieldDefine(ExecutorContext var1, String var2, int var3) throws Exception;

    public FieldDefine findFieldDefine(ExecutorContext var1, String var2, String var3) throws Exception;
}


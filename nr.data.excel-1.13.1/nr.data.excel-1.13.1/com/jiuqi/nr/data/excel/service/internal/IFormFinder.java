/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.List;

public interface IFormFinder {
    public FormDefine findByKey(String var1);

    public FormDefine findByCode(String var1);

    public List<FormDefine> findByTitle(String var1);

    public List<FormDefine> findBySerialNum(String var1);
}


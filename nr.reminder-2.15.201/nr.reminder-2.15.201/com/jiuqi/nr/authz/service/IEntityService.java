/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.authz.service;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IEntityService {
    public List<IEntityRow> getDirectChildrenData(String var1, String var2, String var3, String var4);

    public List<IEntityRow> getAllChildrenData(String var1, String var2, String var3, String var4);

    public List<String> getDirectChildrens(String var1, String var2, String var3, String var4);

    public List<String> getAllChildrens(String var1, String var2, String var3, String var4);

    public List<IEntityRow> searchEntityRow(String var1, String var2, String var3, String var4);
}


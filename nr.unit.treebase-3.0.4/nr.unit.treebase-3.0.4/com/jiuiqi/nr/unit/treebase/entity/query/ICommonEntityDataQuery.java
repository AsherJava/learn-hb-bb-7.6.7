/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import java.util.List;

public interface ICommonEntityDataQuery {
    public IEntityTable makeIEntityTable(String var1);

    public IEntityTable makeIEntityTable(String var1, String ... var2);

    public IEntityTable makeIEntityTable(String var1, List<String> var2);

    public IEntityTable makeIEntityTable(String var1, List<String> var2, String ... var3);

    public IEntityTable makeIEntityTable(String var1, List<String> var2, AuthorityType var3);

    public IEntityTable makeFullTreeData(String var1);

    public IEntityTable makeRangeFullTreeData(String var1, List<String> var2);
}


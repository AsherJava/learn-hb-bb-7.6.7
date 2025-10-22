/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.intf.IModifierEntityTable;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.definition.facade.EntityViewDefine;

@Deprecated
public interface IEntityQuery
extends ICommonQuery {
    public void setEntityView(EntityViewDefine var1);

    public void setAuthorityOperations(AuthorityType var1);

    public void setIgnoreViewFilter(boolean var1);

    public void setIgnoreParentView(boolean var1);

    public void setReloadTreeInfo(ReloadTreeInfo var1);

    public void setAllVersionQuery(boolean var1);

    public void setIgnoreRecovery(boolean var1);

    @Override
    public IEntityTable executeReader(ExecutorContext var1) throws Exception;

    public IEntityTable executeReader(ExecutorContext var1, boolean var2) throws Exception;

    public IModifierEntityTable executeQuery(ExecutorContext var1) throws Exception;
}


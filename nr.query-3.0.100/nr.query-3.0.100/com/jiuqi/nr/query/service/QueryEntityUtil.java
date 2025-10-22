/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nvwa.definition.facade.TableModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.query.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nvwa.definition.facade.TableModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface QueryEntityUtil
extends IEntityUpgrader {
    public IEntityTable getEntityTable(EntityViewDefine var1, DimensionValueSet var2, String var3, ReloadTreeInfo var4, boolean var5, AuthorityType var6);

    public TableModelDefine getEntityTablelDefineByView(String var1);

    public TableModel getEntityTablelModelByEntityId(String var1);

    public TableKind getEntityTablelKindByView(String var1);

    public String getDicTreeStructByView(String var1);

    public List<TableDefine> getAllTableDefinesByTableKind(TableKind var1);

    public TableDefine queryTableDefineByCode(String var1);

    public String getTableTitleByViewKey(String var1);

    public boolean isPeriodUnit(String var1) throws JQException;

    @Deprecated
    public String getTableKeyByView(String var1) throws JQException;

    public IEntityTable getEntityTableUseCache(EntityViewDefine var1, DimensionValueSet var2, AuthorityType var3);
}


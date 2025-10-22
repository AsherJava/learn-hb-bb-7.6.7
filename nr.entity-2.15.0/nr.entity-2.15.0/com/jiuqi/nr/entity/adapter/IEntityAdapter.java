/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.adapter;

import com.jiuqi.nr.entity.adapter.EntityDataAdapter;
import com.jiuqi.nr.entity.adapter.EntityGroupAdapter;
import com.jiuqi.nr.entity.adapter.EntityIOAdapter;
import com.jiuqi.nr.entity.adapter.IEntityAuthorityAdapter;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.model.IEntityCategory;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Date;
import java.util.List;

public interface IEntityAdapter
extends EntityDataAdapter,
EntityIOAdapter,
EntityGroupAdapter,
IEntityAuthorityAdapter,
IEntityCategory {
    public List<IEntityDefine> getEntities(int var1);

    public IEntityDefine getEntity(String var1);

    public IEntityDefine getEntityByCode(String var1);

    public IEntityModel getEntityModel(String var1);

    public List<IEntityRefer> getEntityRefer(String var1);

    public List<String> getPath(String var1);

    public List<IEntityDefine> fuzzySearchEntityByKeyWords(EntitySearchBO var1);

    public TableModelDefine getTableByEntityId(String var1);

    public List<IEntityDefine> getSubTreeEntities(String var1);

    public IEntityDefine getBaseEntityBySubTreeEntityId(String var1);

    public String getDimensionName(String var1);

    public String getEntityCode(String var1);

    public String getDimensionNameByCode(String var1);

    public String getEntityIdByCode(String var1);

    public boolean isDBMode(String var1, Date var2);
}


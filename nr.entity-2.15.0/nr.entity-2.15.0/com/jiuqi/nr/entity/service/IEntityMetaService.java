/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.service;

import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface IEntityMetaService {
    public List<IEntityGroup> getRootEntityGroups();

    public List<IEntityGroup> getChildrenGroup(String var1);

    public List<IEntityDefine> getEntitiesInGroup(String var1);

    public List<IEntityDefine> getEntities(int var1);

    public IEntityDefine queryEntity(String var1);

    public IEntityDefine queryEntityByCode(String var1);

    public IEntityDefine queryBaseTreeEntityBySubTreeEntityId(String var1);

    public List<IEntityDefine> getSubTreeEntities(String var1);

    public List<IEntityDefine> fuzzySearchEntity(EntitySearchBO var1);

    public TableModelDefine getTableModel(String var1);

    public List<IEntityRefer> getEntityRefer(String var1);

    public IEntityModel getEntityModel(String var1);

    public List<String> getPath(String var1);

    public boolean estimateEntityRefer(String var1, String var2);

    public String getDimensionName(String var1);

    public String getEntityCode(String var1);

    public String getDimensionNameByCode(String var1);

    public String getEntityIdByCode(String var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;

public interface IEntityUtils
extends IEntityUpgrader {
    public IEntityTable buildQueryer(EntityViewDefine var1, DimensionValueSet var2, boolean var3);

    public IEntityTable queryEntityTable(EntityViewDefine var1, DimensionValueSet var2) throws Exception;

    public IEntityTable buildIEntityTable(String var1, FieldDefine var2) throws Exception;

    public IEntityTable buildIEntityTable(EntityViewDefine var1, FieldDefine var2, Boolean var3) throws Exception;

    public EntityViewDefine getDefaultEntityViewByTable(String var1) throws JQException;

    public String getTableKey(String var1) throws JQException;

    public List<IEntityDefine> getAllEntityDefinesByDimensionFlag(int var1);

    public List<IEntityDefine> getAllEntityDefines();

    public List<IEntityDefine> getEntityDefinesInRanges(List<String> var1);

    public IEntityDefine getEntityDefine(String var1);

    public EntityViewDefine getDefaultEntityViewByEntityId(String var1) throws JQException;

    public List<IPeriodEntity> getPeriodEntitysInRanges(List<String> var1);

    public IPeriodEntity getPeriodEntity(String var1);

    public List<IPeriodEntity> getAllPeriodEntitys();

    public EntityViewDefine getPeriodViewByEntityKey(String var1);

    public IPeriodProvider getPeriodProvider(String var1);

    public void setPeriodEntityType(DimensionObj var1);

    public List<IEntityDefine> fuzzySearchEntity(String var1, int var2);
}


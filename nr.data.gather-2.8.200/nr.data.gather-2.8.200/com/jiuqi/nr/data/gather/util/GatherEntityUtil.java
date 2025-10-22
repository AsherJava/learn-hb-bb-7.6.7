/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.gather.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GatherEntityUtil {
    private static final Logger logger = LoggerFactory.getLogger(GatherEntityUtil.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;

    public IEntityDefine getEntityDefine(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, (String)"formScheme is must not be null!");
        String entityID = formScheme.getDw();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
        return entityDefine;
    }

    public IPeriodEntity getPeriodEntityDefine(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, (String)"formScheme is must not be null!");
        String entityID = formScheme.getDw();
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityID);
        return periodEntity;
    }

    public boolean canGather(DimensionValueSet valueSetCollection, DimensionValueSet valueSet, String entityDimension, String periodDimension) {
        List accessEntityDimValueList;
        Object entityDimValue = valueSet.getValue(entityDimension);
        Object accessEntityDimValue = valueSetCollection.getValue(entityDimension);
        Object periodDimValue = valueSet.getValue(periodDimension);
        Object accessPeriodDimValue = valueSetCollection.getValue(periodDimension);
        if (accessEntityDimValue instanceof List ? !(accessEntityDimValueList = (List)accessEntityDimValue).contains(entityDimValue) : entityDimValue == null || !entityDimValue.equals(accessEntityDimValue)) {
            return false;
        }
        if (periodDimValue instanceof List) {
            List periodDimValueList = (List)periodDimValue;
            return periodDimValueList.contains(accessPeriodDimValue);
        }
        return periodDimValue != null && periodDimValue.equals(accessPeriodDimValue);
    }
}


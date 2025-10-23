/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.singlequeryimport.utils;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataQueryUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataQueryUtil.class);
    @Autowired
    private IEntityViewController entityCotroller;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    IEntityViewRunTimeController npRuntimeEntityController;

    public String getMainDimName(String formSchemeKey) {
        EntityViewDefine viewDefine = this.getEntityViewDefine(formSchemeKey);
        try {
            return this.entityCotroller.getDimensionNameByViewKey(viewDefine.getEntityId());
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public DimensionValueSet buildDimension(String formSchemeKey, String period, List<String> dimension) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        String mainDimName = this.getMainDimName(formSchemeKey);
        dimensionValueSet.setValue(mainDimName, dimension);
        return dimensionValueSet;
    }

    public EntityViewDefine getEntityViewDefine(String formSchemeId) {
        try {
            String entitiesKey = this.getMasterEntitiesKey(formSchemeId);
            String[] entitiesKeyArr = entitiesKey.split(";");
            EntityViewDefine entityView = null;
            for (String entitiy : entitiesKeyArr) {
                if (this.periodEntityAdapter.isPeriodEntity(entitiy)) continue;
                return this.getEntityViewDefineByKey(entitiy);
            }
            return entityView;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getMasterEntitiesKey(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (null != formScheme) {
                String masterEntitiesKey = formScheme.getMasterEntitiesKey();
                return masterEntitiesKey;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public EntityViewDefine getEntityViewDefineByKey(String entityKey) {
        return this.npRuntimeEntityController.buildEntityView(entityKey);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.time.setting.util.TimeEntityUpgraderImpl;
import com.jiuqi.util.StringUtils;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TdUtils
extends TimeEntityUpgraderImpl {
    private static final Logger logger = LoggerFactory.getLogger(TdUtils.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private EntityIdentityService entityIdentityService1;
    @Autowired
    private IEntityAuthorityService entityIdentityService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    public IEntityTable entityQuerySet(String entityId, DimensionValueSet dimValueSet, String formSchemeKey) {
        IEntityQuery query = this.buildIEntityQuery(formSchemeKey);
        query.setMasterKeys(dimValueSet);
        return this.entityQuerySet(query, this.executorContext(formSchemeKey, dimValueSet));
    }

    public IEntityTable entityFullQuerySet(String entityId, DimensionValueSet dimValueSet, String formSchemeKey) {
        IEntityQuery query = this.buildIEntityQuery(formSchemeKey);
        query.setMasterKeys(dimValueSet);
        return this.entityFullQuerySet(query, this.executorContext(formSchemeKey, dimValueSet));
    }

    public IEntityTable entityQuerySet(String formSchemeKey, String period) {
        IEntityQuery query = this.buildIEntityQuery(formSchemeKey);
        DimensionValueSet dimValueSet = new DimensionValueSet();
        dimValueSet.setValue("DATATIME", (Object)period);
        query.setMasterKeys(dimValueSet);
        query.setAuthorityOperations(AuthorityType.None);
        return this.entityQuerySet(query, this.executorContext(formSchemeKey, dimValueSet));
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        try {
            return this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getEntityDimName(String entityId) {
        String dimensionName = null;
        if (StringUtils.isNotEmpty((String)entityId)) {
            if (this.periodAdapter.isPeriodEntity(entityId)) {
                IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityId);
                dimensionName = periodEntity.getDimensionName();
            } else if ("ADJUST".equals(entityId)) {
                dimensionName = "ADJUST";
            } else {
                IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
                dimensionName = entityDefine.getDimensionName();
            }
        }
        return dimensionName;
    }

    public String getMainDimEntityId(String formSchemeKey) {
        FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
        if (null != formScheme) {
            return formScheme.getDw();
        }
        return null;
    }

    public String getDwMainDimName(String formSchemeKey) {
        String mainDimEntityId = this.getMainDimEntityId(formSchemeKey);
        String mainDimName = this.getEntityDimName(mainDimEntityId);
        return mainDimName;
    }

    public DimensionValueSet buildDimension(String formSchemeKey, List<String> ids, String period) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String mainDimName = this.getDwMainDimName(formSchemeKey);
        dimensionValueSet.setValue(mainDimName, ids);
        dimensionValueSet.setValue(NrPeriodConst.DATETIME, (Object)period);
        return dimensionValueSet;
    }

    public Map<String, Collection<String>> getGrantedEntityKeys(String currUserId) {
        return this.entityIdentityService1.getGrantedEntityKeys(currUserId);
    }

    public boolean canOperateEntityData(String formSchemeKey, String period, String unitId) {
        try {
            String mainDimEntityId = this.getMainDimEntityId(formSchemeKey);
            Date[] dateRange = this.parseFromPeriod(period, formSchemeKey, mainDimEntityId);
            return this.entityIdentityService.canAuditEntity(mainDimEntityId, unitId, dateRange[0], dateRange[1]);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public Date[] parseFromPeriod(String periodString, String formSchemeKey, String mainDimEntityId) throws ParseException {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(formScheme.getDateTime());
        return periodProvider.getPeriodDateRegion(periodString);
    }

    public EntityViewDefine getEntityView(String entityKey) {
        try {
            return this.entityViewRunTimeController.buildEntityView(entityKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public EntityViewDefine getDwEntityView(String formSchemeKey) {
        try {
            return this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.facade.FDimensionState;
import com.jiuqi.nr.dataentry.bean.impl.DimensionStateImpl;
import com.jiuqi.nr.dataentry.service.IPermission;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service(value="DataEntryUnitPermissionImpl")
public class UnitPermissionImpl
implements IPermission {
    private static final Logger log = LoggerFactory.getLogger(UnitPermissionImpl.class);
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;

    @Override
    public boolean isReadable(JtableContext context) {
        return true;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FDimensionState isWriteable(JtableContext context) {
        String formSchemeKey = context.getFormSchemeKey();
        EntityViewData unitEntity = this.jtableParamService.getDwEntity(formSchemeKey);
        DimensionValue unitDimValue = (DimensionValue)context.getDimensionSet().get(unitEntity.getDimensionName());
        String unitValue = unitDimValue.getValue();
        EntityViewData periodEntity = this.jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
        Date queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        Date queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (periodEntity != null) {
            DimensionValue periodDimValue = (DimensionValue)context.getDimensionSet().get(periodEntity.getDimensionName());
            String periodValue = periodDimValue.getValue();
            if (8 == periodDimValue.getType()) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                JtableContext jtableContext = new JtableContext();
                entityQueryInfo.setContext(jtableContext);
                jtableContext.setFormSchemeKey(context.getFormSchemeKey());
                jtableContext.setTaskKey(context.getTaskKey());
                entityQueryInfo.setEntityViewKey(periodEntity.getKey());
                EntityReturnInfo queryEntityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
                for (EntityData entityData : queryEntityData.getEntitys()) {
                    List data;
                    if (!entityData.getId().equals(periodValue) || (data = entityData.getData()).size() <= 1) continue;
                    Date[] startParseFromPeriod = DataEntryUtil.parseFromPeriod((String)data.get(0));
                    Date[] endParseFromPeriod = DataEntryUtil.parseFromPeriod((String)data.get(1));
                    if (null != startParseFromPeriod && startParseFromPeriod.length > 1) {
                        queryVersionStartDate = startParseFromPeriod[0];
                    }
                    if (null == endParseFromPeriod || endParseFromPeriod.length <= 1) continue;
                    queryVersionEndDate = endParseFromPeriod[1];
                }
            } else {
                Date[] dates = DataEntryUtil.parseFromPeriod(periodValue);
                if (dates == null) {
                    return new DimensionStateImpl(unitEntity.getDimensionName(), false);
                }
                if (dates.length > 1) {
                    queryVersionStartDate = dates[0];
                    queryVersionEndDate = dates[1];
                }
            }
        }
        boolean canWriteEntity = false;
        if (StringUtils.isNotEmpty((String)unitValue)) {
            boolean systemIdentity = this.entityAuthorityService.isSystemIdentity();
            if (!systemIdentity) {
                try {
                    canWriteEntity = this.entityAuthorityService.isEnableAuthority(unitEntity.getKey()) ? this.entityAuthorityService.canWriteEntity(unitEntity.getKey(), unitValue, queryVersionStartDate, queryVersionEndDate) : true;
                }
                catch (UnauthorizedEntityException e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                canWriteEntity = true;
            }
        }
        return new DimensionStateImpl(unitEntity.getDimensionName(), canWriteEntity);
    }
}


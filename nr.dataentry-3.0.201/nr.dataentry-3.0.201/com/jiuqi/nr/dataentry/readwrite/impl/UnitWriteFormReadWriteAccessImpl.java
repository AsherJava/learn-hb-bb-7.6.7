/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.EntityBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=15)
@Component
@Deprecated
public class UnitWriteFormReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(UnitWriteFormReadWriteAccessImpl.class);
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IJtableParamService jtableParamService;

    @Override
    public String getName() {
        return "unitWriteForm";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) {
        boolean unitWriteFormDataPerm = false;
        try {
            unitWriteFormDataPerm = this.funcExecuteService.getUnitWriteFormDataPerm(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u5355\u4f4d\u5bf9\u62a5\u8868\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        String unwriteableDesc = "";
        if (!unitWriteFormDataPerm) {
            unwriteableDesc = DataEntryUtil.isChinese() ? "\u8be5\u5355\u4f4d\u5bf9\u62a5\u8868\u6ca1\u6709\u6743\u9650\u4e0d\u53ef\u7f16\u8f91" : "The form cannot be edited without permission";
        }
        return new ReadWriteAccessDesc(unitWriteFormDataPerm, unwriteableDesc);
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_WRITE || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            String periodValue;
            Date[] dates;
            JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
            Map allDimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            List entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
            EntityViewData unitEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            String unitMapKey = unitEntity.getDimensionName();
            EntityViewData periodEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
            String periodMapKey = periodEntity.getDimensionName();
            DimensionValue periodDimensionValue = (DimensionValue)allDimensionSet.get(periodMapKey);
            DimensionValue unitDimensionValue = (DimensionValue)allDimensionSet.get(unitMapKey);
            String units = unitDimensionValue.getValue();
            String[] unitsSplit = units.split(";");
            Date queryVersionStartDate = null;
            Date queryVersionEndDate = null;
            queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
            queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
            if (periodEntity != null && (dates = DataEntryUtil.parseFromPeriod(periodValue = periodDimensionValue.getValue())) != null && dates.length >= 2) {
                queryVersionStartDate = dates[0];
                queryVersionEndDate = dates[1];
            }
            Set canWriteEntityKeys = new HashSet();
            EntityBatchAuthCache authCache = new EntityBatchAuthCache();
            boolean isAdmin = this.systemIdentityService.isAdmin();
            if (isAdmin || !this.entityAuthorityService.isEnableAuthority(unitEntity.getKey())) {
                authCache.setIgnoreAuth(true);
                return authCache;
            }
            try {
                canWriteEntityKeys = this.entityAuthorityService.getCanWriteEntityKeys(unitEntity.getKey(), queryVersionStartDate, queryVersionEndDate);
            }
            catch (UnauthorizedEntityException e) {
                logger.error(e.getMessage(), e);
            }
            int index = 0;
            boolean adjustDimension = false;
            HashSet<DimensionCacheKey> cacheKeys = new HashSet<DimensionCacheKey>();
            for (String unitValue : unitsSplit) {
                if (!canWriteEntityKeys.contains(unitValue)) continue;
                HashMap<String, DimensionValue> oneDimensionSet = new HashMap<String, DimensionValue>();
                DimensionValue oneUnitDimensionValue = new DimensionValue();
                oneUnitDimensionValue.setName(unitMapKey);
                oneUnitDimensionValue.setType(unitDimensionValue.getType());
                oneUnitDimensionValue.setValue(unitValue);
                oneDimensionSet.put(unitMapKey, oneUnitDimensionValue);
                oneDimensionSet.put(periodMapKey, periodDimensionValue);
                if (index == 0) {
                    adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(oneDimensionSet, entityList);
                }
                Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, oneDimensionSet, entityList);
                DimensionCacheKey cacheKey = new DimensionCacheKey(currentDimension);
                cacheKeys.add(cacheKey);
                ++index;
            }
            ArrayList<String> dimKeys = new ArrayList<String>();
            dimKeys.add(periodMapKey);
            dimKeys.add(unitMapKey);
            authCache.setDimKeys(dimKeys);
            authCache.setNotWriteEntitys(cacheKeys);
            return authCache;
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (formAccessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE || formAccessLevel == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            EntityBatchAuthCache authCache = (EntityBatchAuthCache)cacheObj;
            if (authCache.isIgnoreAuth()) {
                return null;
            }
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, authCache.getDimKeys());
            HashSet<DimensionCacheKey> cacheKeys = authCache.getNotWriteEntitys();
            boolean canWrite = cacheKeys == null ? false : cacheKeys.contains(simpleKey);
            return new ReadWriteAccessDesc(canWrite, canWrite ? "" : "\u8be5\u5355\u4f4d\u5bf9\u62a5\u8868\u6ca1\u6709\u6743\u9650\u4e0d\u53ef\u7f16\u8f91");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "UNIT";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (formAccessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE) {
            String unitMapKey = accessCacheManager.getUnitEntity().getDimensionName();
            String periodMapKey = accessCacheManager.getPeriodEntity().getDimensionName();
            HashMap<String, DimensionValue> oneDimensionSet = new HashMap<String, DimensionValue>();
            DimensionValue periodDimensionValue = dimensionSet.get(periodMapKey);
            DimensionValue unitDimensionValue = dimensionSet.get(unitMapKey);
            oneDimensionSet.put(unitMapKey, unitDimensionValue);
            oneDimensionSet.put(periodMapKey, periodDimensionValue);
            return ReadWriteAccessCacheManager.getStatusKey(oneDimensionSet, null, entityList);
        }
        return null;
    }
}


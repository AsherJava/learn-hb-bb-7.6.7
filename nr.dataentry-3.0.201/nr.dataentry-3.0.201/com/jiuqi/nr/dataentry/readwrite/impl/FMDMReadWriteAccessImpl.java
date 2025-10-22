/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchReadWriteCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FMDMReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) throws Exception {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == readWriteAccessCacheParams.getFormAccessLevel() || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == readWriteAccessCacheParams.getFormAccessLevel()) {
            List<String> formKeys = readWriteAccessCacheParams.getFormKeys();
            String fmdmKey = "";
            for (String key : formKeys) {
                FormDefine formDefine = this.runtimeView.queryFormById(key);
                if (formDefine == null || !FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) continue;
                fmdmKey = formDefine.getKey();
                break;
            }
            if (!StringUtils.isEmpty((String)fmdmKey)) {
                JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
                List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((Map)jtableContext.getDimensionSet());
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
                EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
                EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
                String periodValue = ((DimensionValue)jtableContext.getDimensionSet().get(periodEntityInfo.getDimensionName())).getValue();
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityInfo.getKey());
                Date[] periodDateRegion = periodProvider.getPeriodDateRegion(periodValue);
                Date startDate = periodDateRegion[0];
                Date endDate = periodDateRegion[1];
                FormBatchReadWriteCache batchCache = new FormBatchReadWriteCache();
                HashMap<DimensionCacheKey, HashSet<String>> cacheItems = new HashMap<DimensionCacheKey, HashSet<String>>();
                batchCache.setCanAccess(false);
                batchCache.setCacheMap(cacheItems);
                boolean hasInit = false;
                ArrayList<String> dimKeys = new ArrayList<String>(((Map)dimensionSetList.get(0)).keySet());
                List<EntityViewData> entityList = readWriteAccessCacheParams.getEntityList();
                boolean adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension((Map)dimensionSetList.get(0), entityList);
                Set canEditEntityKeys = this.entityAuthorityService.getCanEditEntityKeys(dwEntityInfo.getKey(), startDate, endDate);
                for (Map map : dimensionSetList) {
                    DimensionCacheKey cacheKey;
                    HashSet<String> formKeySet;
                    String unitKey = ((DimensionValue)map.get(dwEntityInfo.getDimensionName())).getValue();
                    boolean canEdit = false;
                    if (canEditEntityKeys != null && canEditEntityKeys.contains(unitKey)) {
                        canEdit = true;
                    }
                    Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, map, entityList);
                    if (!hasInit) {
                        dimKeys = new ArrayList<String>(currentDimension.keySet());
                        hasInit = true;
                    }
                    if ((formKeySet = (HashSet<String>)cacheItems.get(cacheKey = new DimensionCacheKey(currentDimension))) == null) {
                        formKeySet = new HashSet<String>();
                        cacheItems.put(cacheKey, formKeySet);
                    }
                    if (canEdit) continue;
                    formKeySet.add(fmdmKey);
                }
                batchCache.setDimKeys(dimKeys);
                return batchCache;
            }
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            HashSet<String> formKeys;
            FormBatchReadWriteCache batchReadWriteCache = (FormBatchReadWriteCache)cacheObj;
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, batchReadWriteCache.getDimKeys());
            Map<DimensionCacheKey, HashSet<String>> cacheItems = batchReadWriteCache.getCacheMap();
            HashSet<String> hashSet = formKeys = cacheItems == null ? null : cacheItems.get(simpleKey);
            if (formKeys == null) {
                return null;
            }
            boolean fmdmAuth = formKeys.contains(formKey);
            String message = "\u8be5\u8282\u70b9\u6ca1\u6709\u7ba1\u7406\u6743\u9650\uff0c\u5c01\u9762\u4ee3\u7801\u4e0d\u53ef\u7f16\u8f91";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("NODE_NO_ADMIN_FMDM_NO_EDIT"))) {
                message = this.i18nHelper.getMessage("NODE_NO_ADMIN_FMDM_NO_EDIT");
            }
            return new ReadWriteAccessDesc(!fmdmAuth, fmdmAuth ? message : "");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel) {
            return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, entityList);
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        FormDefine formDefine = this.runtimeView.queryFormById(context.getFormKey());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(context.getFormSchemeKey());
        if (FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
            EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            String periodValue = ((DimensionValue)context.getDimensionSet().get(periodEntityInfo.getDimensionName())).getValue();
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityInfo.getKey());
            Date[] periodDateRegion = periodProvider.getPeriodDateRegion(periodValue);
            Date startDate = periodDateRegion[0];
            Date endDate = periodDateRegion[1];
            String unitKey = ((DimensionValue)context.getDimensionSet().get(dwEntityInfo.getDimensionName())).getValue();
            boolean canEdit = this.entityAuthorityService.canEditEntity(dwEntityInfo.getKey(), unitKey, startDate, endDate);
            if (canEdit) {
                return new ReadWriteAccessDesc(true, "");
            }
            String message = "\u5355\u4f4d\u6811\u6ca1\u6709\u7ba1\u7406\u6743\u9650";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("UNIT_TREE_NO_ADMIN"))) {
                message = this.i18nHelper.getMessage("UNIT_TREE_NO_ADMIN");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public String getName() {
        return "fmdm";
    }
}


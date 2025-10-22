/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.exception.DimensionNotSingleException;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class ReadWriteAccessCacheManager {
    private static final Logger logger = LoggerFactory.getLogger(ReadWriteAccessCacheManager.class);
    @Autowired
    private List<IBatchDimensionReadWriteAccess> accessCaches;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IFormSchemeService formSchemeCommonService;
    private Map<String, Object> cache = new HashMap<String, Object>();
    private Map<String, Map<String, Object>> multiplexingCache = new HashMap<String, Map<String, Object>>();
    private List<EntityViewData> entityList;
    private EntityViewData unitEntity;
    private EntityViewData periodEntity;
    private List<String> allFormkeys;
    private List<String> allGroupKeys = new ArrayList<String>();
    private IBatchAccessResult batchResult;

    public void initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        this.entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        this.unitEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        this.periodEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        Map dimension = jtableContext.getDimensionSet();
        DimensionValueSet currDimensionValue = DimensionValueSetUtil.getDimensionValueSet((Map)dimension);
        DimensionCollection currDimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)currDimensionValue, (String)jtableContext.getFormSchemeKey());
        this.initCache(readWriteAccessCacheParams, currDimensionCollection);
    }

    public void initSingleDimCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        this.entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        this.unitEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        this.periodEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        Map dimension = jtableContext.getDimensionSet();
        DimensionValueSet currDimensionValue = DimensionValueSetUtil.getDimensionValueSet((Map)dimension);
        DimensionCollection currDimensionCollection = this.getSingleCollection(currDimensionValue, jtableContext.getFormSchemeKey());
        if (currDimensionCollection == null) {
            throw new DimensionNotSingleException(null);
        }
        this.initCache(readWriteAccessCacheParams, currDimensionCollection);
    }

    private void initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams, DimensionCollection currDimensionCollection) {
        IBatchAccessResult batchResult;
        List rootFormGroups;
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        List<String> allFormkeys = this.readWriteAccessProvider.getAllFormKeys(jtableContext.getFormSchemeKey());
        if (null == readWriteAccessCacheParams.getFormKeys() || 0 == readWriteAccessCacheParams.getFormKeys().size()) {
            readWriteAccessCacheParams.setFormKeys(allFormkeys);
        }
        if ((rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(jtableContext.getFormSchemeKey())) != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                this.allGroupKeys.add(formGroupDefine.getKey());
            }
        }
        this.allFormkeys = allFormkeys;
        List<String> formKeys = readWriteAccessCacheParams.getFormKeys();
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
        Consts.FormAccessLevel accessLevel = readWriteAccessCacheParams.getFormAccessLevel();
        this.batchResult = batchResult = accessLevel == Consts.FormAccessLevel.FORM_READ ? dataAccessService.getVisitAccess(currDimensionCollection, formKeys) : (accessLevel == Consts.FormAccessLevel.FORM_DATA_READ ? dataAccessService.getReadAccess(currDimensionCollection, formKeys) : (accessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE || accessLevel == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE ? dataAccessService.getWriteAccess(currDimensionCollection, formKeys) : null));
    }

    public DimensionCollection getSingleCollection(DimensionValueSet currDimensionValue, String formSchemeKey) {
        int size = currDimensionValue.size();
        boolean enableAdjust = this.formSchemeCommonService.enableAdjustPeriod(formSchemeKey);
        if (enableAdjust) {
            --size;
        }
        if (size != this.entityList.size()) {
            return null;
        }
        Map dimensionSetMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)currDimensionValue);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (int i = 0; i < size; ++i) {
            EntityViewData entityViewData = this.entityList.get(i);
            String name = entityViewData.getDimensionName();
            DimensionValue dimensionValue = (DimensionValue)dimensionSetMap.get(name);
            if (dimensionValue == null || dimensionValue.getValue() == null || StringUtils.isEmpty((String)dimensionValue.getValue())) {
                return null;
            }
            String value = dimensionValue.getValue();
            String[] values = value.split(";");
            if (values.length > 1) {
                return null;
            }
            if (name.equalsIgnoreCase(this.unitEntity.getDimensionName())) {
                builder.setDWValue(name, entityViewData.getKey(), new Object[]{value});
                continue;
            }
            if ("MD_CURRENCY".equals(name) && ("PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value))) {
                return null;
            }
            builder.setEntityValue(name, entityViewData.getKey(), new Object[]{value});
        }
        if (enableAdjust) {
            DimensionValue adjustDim = (DimensionValue)dimensionSetMap.get("ADJUST");
            if (Objects.isNull(adjustDim)) {
                builder.setEntityValue("ADJUST", "ADJUST", new Object[]{0});
            } else {
                String adjust = adjustDim.getValue();
                builder.setEntityValue("ADJUST", "ADJUST", new Object[]{adjust});
            }
        }
        return builder.getCollection();
    }

    public boolean canUseReadTempTable(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
        boolean canUse = true;
        for (IBatchDimensionReadWriteAccess readWriteAccessBase : this.accessCaches) {
            if (!readWriteAccessBase.isEnable(jtableContext)) continue;
            try {
                boolean currentValue = readWriteAccessBase.canUseReadTempTable(readWriteAccessCacheParams);
                if (currentValue) continue;
                canUse = false;
                break;
            }
            catch (Exception e) {
                return false;
            }
        }
        return canUse;
    }

    public static String getStatusKey(Map<String, DimensionValue> dimensionSet, String formKey, List<EntityViewData> entityList) {
        StringBuilder statusKeyBuf = new StringBuilder();
        if (null != dimensionSet && dimensionSet.size() > 0) {
            for (EntityViewData entity : entityList) {
                DimensionValue dimensionValue = dimensionSet.get(entity.getDimensionName());
                if (null == dimensionValue) continue;
                statusKeyBuf.append(dimensionValue.getValue()).append(":");
            }
        }
        if (null != formKey && !"".equals(formKey)) {
            statusKeyBuf.append(formKey).append(":");
        }
        return statusKeyBuf.toString();
    }

    public Object getAccessCache(String type) {
        Object map = this.cache.get(type);
        return map;
    }

    public Object getMultiplexingCacheObj(String type, String cacheKey) {
        Map<String, Object> map = this.multiplexingCache.get(type);
        if (null != map) {
            return map.get(cacheKey);
        }
        return null;
    }

    public void setMultiplexingCacheObj(String type, String cacheKey, Object object) {
        Map<String, Object> map = this.multiplexingCache.get(type);
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        map.put(cacheKey, object);
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public EntityViewData getUnitEntity() {
        return this.unitEntity;
    }

    public EntityViewData getPeriodEntity() {
        return this.periodEntity;
    }

    public List<String> getAllFormkeys() {
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.addAll(this.allFormkeys);
        return tempList;
    }

    public List<String> getAllGroupKeys() {
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.addAll(this.allGroupKeys);
        return tempList;
    }

    public static boolean checkAdjustDimension(Map<String, DimensionValue> currentDimension, List<EntityViewData> entityList) {
        boolean adjustDimension = false;
        Set dimensionSet = entityList.stream().map(t -> t.getDimensionName()).collect(Collectors.toSet());
        for (String dimensionName : currentDimension.keySet()) {
            if (dimensionSet.contains(dimensionName)) continue;
            adjustDimension = true;
            break;
        }
        return adjustDimension;
    }

    public static Map<String, DimensionValue> getDimensionValue(boolean adjustDimension, Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList) {
        if (!adjustDimension) {
            return dimensionSet;
        }
        HashMap<String, DimensionValue> resultMap = new HashMap<String, DimensionValue>();
        for (EntityViewData entityViewData : entityList) {
            DimensionValue dimensionValue = dimensionSet.get(entityViewData.getDimensionName());
            if (dimensionValue == null) continue;
            resultMap.put(entityViewData.getDimensionName(), dimensionValue);
        }
        return resultMap;
    }

    public static DimensionCacheKey getCacheKey(boolean adjustDimension, DimensionCacheKey cacheKey, List<EntityViewData> entityList) {
        if (!adjustDimension) {
            return cacheKey;
        }
        HashMap<String, DimensionValue> resultMap = new HashMap<String, DimensionValue>();
        Map<String, DimensionValue> dimensionSet = cacheKey.getDimensionSet();
        for (EntityViewData entityViewData : entityList) {
            DimensionValue dimensionValue = dimensionSet.get(entityViewData.getDimensionName());
            if (dimensionValue == null) continue;
            resultMap.put(entityViewData.getDimensionName(), dimensionValue);
        }
        DimensionCacheKey resultKey = new DimensionCacheKey(resultMap);
        return resultKey;
    }

    public static DimensionCacheKey getSimpleKey(DimensionCacheKey cacheKey, List<String> dimKeys) {
        if (dimKeys == null || dimKeys.size() == cacheKey.getDimensionSet().size()) {
            return cacheKey;
        }
        Map<String, DimensionValue> currentMap = cacheKey.getDimensionSet();
        HashMap<String, DimensionValue> dimSet = new HashMap<String, DimensionValue>();
        for (String dimKey : dimKeys) {
            DimensionValue currentValue = currentMap.get(dimKey);
            if (currentValue == null) continue;
            dimSet.put(dimKey, currentValue);
        }
        DimensionCacheKey simpleKey = new DimensionCacheKey(dimSet);
        return simpleKey;
    }

    public IBatchAccessResult getBatchResult() {
        return this.batchResult;
    }
}


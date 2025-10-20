/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcDimensionProcessor {
    private static Logger logger = LoggerFactory.getLogger(GcDimensionProcessor.class);
    private static IJtableParamService jtableParamService = (IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class);
    private static IJtableEntityService jtableEntityService = (IJtableEntityService)SpringContextUtils.getBean(IJtableEntityService.class);
    private static IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
    private static GcDimensionProcessor gcDimensionProcessor;
    private static final String MD_GCADJTYPE = "MD_GCADJTYPE";

    public static GcDimensionProcessor getInstance() {
        if (null == gcDimensionProcessor) {
            gcDimensionProcessor = new GcDimensionProcessor();
        }
        return gcDimensionProcessor;
    }

    public List<DimensionValueSet> listDimensionValueSet(String formSchemeKey, Map<String, List<String>> dimensionMap, String orgType) {
        if (orgType.startsWith("MD_ORG_")) {
            return this.listDimensionValueByOrg(formSchemeKey, dimensionMap, orgType);
        }
        return this.listDimensionValueSetByBase(formSchemeKey, dimensionMap);
    }

    private List<DimensionValueSet> listDimensionValueByOrg(String formSchemeKey, Map<String, List<String>> dimensionMap, String orgType) {
        String periodStr = dimensionMap.get("DATATIME").get(0);
        if (StringUtils.isEmpty((String)periodStr)) {
            logger.error("\u65f6\u671f\u4fe1\u606f\u4e3a\u7a7a\uff0c\u7ef4\u5ea6\u4fe1\u606f\uff1a" + dimensionMap.toString());
            return null;
        }
        List<GcOrgCacheVO> orgValues = this.listOrgValue(formSchemeKey, dimensionMap, orgType, periodStr);
        if (CollectionUtils.isEmpty(orgValues)) {
            logger.error("\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\u6216\u8005\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6b64\u5355\u4f4d\u6743\u9650\uff0c\u7ef4\u5ea6\u4fe1\u606f\uff1a" + dimensionMap);
            return Collections.emptyList();
        }
        Map<String, String> entityReferMap = this.getEntityReferMap(orgType, dimensionMap);
        return this.listDimensionValueSet(dimensionMap, orgValues, entityReferMap);
    }

    private List<DimensionValueSet> listDimensionValueSet(Map<String, List<String>> dimensionMap, List<GcOrgCacheVO> orgValues, Map<String, String> entityReferMap) {
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (String dimName : dimensionMap.keySet()) {
            dimensionValueSet.setValue(dimName, dimensionMap.get(dimName));
        }
        for (GcOrgCacheVO gcOrgCache : orgValues) {
            DimensionValueSet newDimensionValueSet = new DimensionValueSet(dimensionValueSet);
            for (String dimensionName : dimensionMap.keySet()) {
                List<String> dimensionValues = dimensionMap.get(dimensionName);
                this.setDimensionValues(dimensionValues, dimensionName, newDimensionValueSet, gcOrgCache, entityReferMap);
            }
            dimensionValueSetList.add(newDimensionValueSet);
        }
        return dimensionValueSetList;
    }

    private List<String> listDimensionValue(String orgFieldValues, List<String> dimensionValues) {
        ArrayList orgDimValues = CollectionUtils.newArrayList((Object[])orgFieldValues.split(";"));
        List<Object> dimValues = CollectionUtils.isEmpty(dimensionValues) ? orgDimValues : dimensionValues.stream().filter(code -> orgDimValues.contains(code)).collect(Collectors.toList());
        return dimValues;
    }

    private List<GcOrgCacheVO> listOrgValue(String formSchemeKey, Map<String, List<String>> dimensionMap, String orgType, String periodStr) {
        YearPeriodObject yearPeriodObject = new YearPeriodObject(formSchemeKey, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodObject);
        List<Object> orgCaches = new ArrayList();
        List<String> orgValues = dimensionMap.get("MD_ORG");
        if (CollectionUtils.isEmpty(orgValues)) {
            orgCaches = tool.listAllOrgByParentIdContainsSelf(null);
            if (!CollectionUtils.isEmpty(orgCaches)) {
                orgCaches = orgCaches.stream().filter(orgCache -> !orgCache.isStopFlag() && !orgCache.isRecoveryFlag()).collect(Collectors.toList());
            }
        } else {
            for (String orgCode : orgValues) {
                GcOrgCacheVO cacheVO = tool.getOrgByCode(orgCode);
                if (cacheVO == null) continue;
                orgCaches.add(cacheVO);
            }
        }
        return orgCaches;
    }

    private Map<String, String> getEntityReferMap(String orgType, Map<String, List<String>> dimensionMap) {
        IEntityMetaService iEntityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
        String entityId = iEntityMetaService.getEntityIdByCode(orgType);
        List iEntityRefers = iEntityMetaService.getEntityRefer(entityId);
        HashMap<String, String> entityReferMap = new HashMap<String, String>();
        for (IEntityRefer iEntityRefer : iEntityRefers) {
            String dimensionName = iEntityMetaService.getDimensionName(iEntityRefer.getReferEntityId());
            if (entityReferMap.containsKey(dimensionName) || !dimensionMap.containsKey(dimensionName)) continue;
            entityReferMap.put(dimensionName, iEntityRefer.getOwnField());
        }
        return entityReferMap;
    }

    private void setDimensionValues(List<String> dimensionValues, String dimensionName, DimensionValueSet newDimensionValueSet, GcOrgCacheVO gcOrgCache, Map<String, String> entityReferMap) {
        if ("MD_ORG".equals(dimensionName)) {
            ArrayList<String> orgCodes = new ArrayList<String>();
            orgCodes.add(gcOrgCache.getCode());
            newDimensionValueSet.setValue(dimensionName, orgCodes);
        } else if ("DATATIME".equals(dimensionName)) {
            newDimensionValueSet.setValue(dimensionName, dimensionValues);
        } else if (MD_GCADJTYPE.equals(dimensionName)) {
            ArrayList<String> gcadjTypes = new ArrayList<String>();
            gcadjTypes.add(GCAdjTypeEnum.BEFOREADJ.getCode());
            newDimensionValueSet.setValue(dimensionName, gcadjTypes);
        } else {
            this.setDimensionValuesByEntityReferMap(dimensionValues, dimensionName, newDimensionValueSet, gcOrgCache, entityReferMap);
        }
    }

    private void setDimensionValuesByEntityReferMap(List<String> dimensionValues, String dimensionName, DimensionValueSet newDimensionValueSet, GcOrgCacheVO gcOrgCache, Map<String, String> entityReferMap) {
        if (entityReferMap.containsKey(dimensionName)) {
            String ownField = entityReferMap.get(dimensionName);
            String orgFieldValues = StringUtils.toViewString((Object)gcOrgCache.getTypeFieldValue(ownField));
            List<String> dimValues = dimensionValues.size() == 1 && "MD_CURRENCY".equals(dimensionName) ? (dimensionValues.contains("PROVIDER_BASECURRENCY") || dimensionValues.contains("PROVIDER_PBASECURRENCY") ? dimensionValues : this.listDimensionValue(orgFieldValues, dimensionValues)) : this.listDimensionValue(orgFieldValues, dimensionValues);
            newDimensionValueSet.setValue(dimensionName, dimValues);
        } else {
            List<String> dimBaseDataValues = dimensionValues;
            if (CollectionUtils.isEmpty(dimensionValues)) {
                GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
                List baseDatas = baseDataCenterTool.queryBasedataItems(dimensionName);
                dimBaseDataValues = baseDatas.stream().map(GcBaseData::getCode).collect(Collectors.toList());
            }
            newDimensionValueSet.setValue(dimensionName, dimBaseDataValues);
        }
    }

    private List<DimensionValueSet> listDimensionValueSetByBase(String formSchemeKey, Map<String, List<String>> dimensionMap) {
        EntityViewData periodEntity = jtableParamService.getDataTimeEntity(formSchemeKey);
        List<String> periodStrs = dimensionMap.get(periodEntity.getDimensionName());
        if (CollectionUtils.isEmpty(periodStrs)) {
            logger.error("\u65f6\u671f\u4fe1\u606f\u4e3a\u7a7a\uff0c\u7ef4\u5ea6\u4fe1\u606f\uff1a" + dimensionMap.toString());
            return null;
        }
        EntityViewData dwEntity = jtableParamService.getDwEntity(formSchemeKey);
        List dimEntityList = jtableParamService.getDimEntityList(formSchemeKey);
        List<String> dwList = dimensionMap.get(dwEntity.getDimensionName());
        Iterator<String> iterator = periodStrs.iterator();
        if (iterator.hasNext()) {
            String periodStr = iterator.next();
            DimensionValue periodValue = new DimensionValue();
            periodValue.setName(periodEntity.getDimensionName());
            periodValue.setValue(periodStr);
            return this.listDimensionValueSet(periodEntity.getDimensionName(), formSchemeKey, dwList, dwEntity, periodValue, dimEntityList, dimensionMap);
        }
        return null;
    }

    private List<DimensionValueSet> listDimensionValueSet(String periodDimName, String formSchemeKey, List<String> dwList, EntityViewData dwEntity, DimensionValue periodValue, List<EntityViewData> dimEntityList, Map<String, List<String>> dimensionMap) {
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        List<String> selectDwList = this.listDw(periodDimName, formSchemeKey, dwList, dwEntity, periodValue);
        for (String master : selectDwList) {
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            dimensionValueMap.put(periodDimName, periodValue);
            DimensionValue masterValue = new DimensionValue();
            masterValue.setName(dwEntity.getDimensionName());
            masterValue.setValue(master);
            dimensionValueMap.put(dwEntity.getDimensionName(), masterValue);
            ArrayList<String> dimNames = new ArrayList<String>();
            ArrayList<List<String>> dimValues = new ArrayList<List<String>>();
            for (EntityViewData entityViewData : dimEntityList) {
                dimNames.add(entityViewData.getDimensionName());
                List<String> selectDimList = new ArrayList();
                List<String> dimList = dimensionMap.get(entityViewData.getDimensionName());
                List<Object> allDimList = new ArrayList();
                boolean entityRefer = entityMetaService.estimateEntityRefer(dwEntity.getKey(), entityViewData.getKey());
                if (entityRefer) {
                    EntityQueryByViewInfo dimEntityQueryInfo = new EntityQueryByViewInfo();
                    dimEntityQueryInfo.setEntityViewKey(entityViewData.getKey());
                    JtableContext dimContext = new JtableContext();
                    dimContext.setDimensionSet(dimensionValueMap);
                    dimContext.setFormSchemeKey(formSchemeKey);
                    dimEntityQueryInfo.setContext(dimContext);
                    dimEntityQueryInfo.setReadAuth(true);
                    EntityReturnInfo dimEntityReturnInfo = jtableEntityService.queryEntityData(dimEntityQueryInfo);
                    allDimList = GcDimensionProcessor.getAllEntityKey(dimEntityReturnInfo);
                } else {
                    allDimList = jtableEntityService.getAllDimEntityKey(entityViewData.getKey(), dimensionValueMap, formSchemeKey);
                }
                HashSet<String> allDimIDSet = new HashSet<String>();
                for (String dimId : allDimList) {
                    allDimIDSet.add(dimId);
                }
                if (dimList.isEmpty()) {
                    selectDimList = allDimList;
                } else {
                    for (String dimId : dimList) {
                        if (!allDimIDSet.contains(dimId)) continue;
                        selectDimList.add(dimId);
                    }
                }
                dimValues.add(selectDimList);
            }
            boolean continueFlag = false;
            if (dimValues.size() > 0) {
                for (List dimValue : dimValues) {
                    if (dimValue.size() != 0) continue;
                    continueFlag = true;
                }
            }
            if (continueFlag) continue;
            if (dimNames.size() > 0) {
                List<Map<String, DimensionValue>> list = this.getDimensionValueList(dimNames, dimValues, 0);
                for (Map<String, DimensionValue> subDimensionValue : list) {
                    subDimensionValue.put(periodDimName, new DimensionValue(periodValue));
                    subDimensionValue.put(dwEntity.getDimensionName(), new DimensionValue(masterValue));
                    dimensionValueSets.add(DimensionValueSetUtil.getDimensionValueSet(subDimensionValue));
                }
                continue;
            }
            dimensionValueSets.add(DimensionValueSetUtil.getDimensionValueSet(dimensionValueMap));
        }
        return dimensionValueSets;
    }

    private List<String> listDw(String periodDimName, String formSchemeKey, List<String> dwList, EntityViewData dwEntity, DimensionValue periodValue) {
        List<String> allEntityIDs;
        EntityReturnInfo entityReturnInfo;
        EntityQueryByViewInfo entityQueryInfo;
        StringBuffer errorDimensionStr = new StringBuffer();
        LinkedHashMap<String, DimensionValue> queryDimensionValueMap = new LinkedHashMap<String, DimensionValue>();
        queryDimensionValueMap.put(periodDimName, periodValue);
        List<Object> selectDwList = new ArrayList();
        JtableContext context = new JtableContext();
        context.setDimensionSet(queryDimensionValueMap);
        context.setFormSchemeKey(formSchemeKey);
        if (dwList.isEmpty()) {
            entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryInfo.setReadAuth(true);
            entityQueryInfo.setContext(context);
            entityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
            allEntityIDs = GcDimensionProcessor.getAllEntityKey(entityReturnInfo);
        } else {
            entityQueryInfo = new EntityQueryByKeysInfo();
            entityQueryInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryInfo.setReadAuth(true);
            entityQueryInfo.setContext(context);
            entityQueryInfo.setEntityKeys(dwList);
            entityReturnInfo = jtableEntityService.queryEntityDataByKeys((EntityQueryByKeysInfo)entityQueryInfo);
            Map entitys = entityReturnInfo.getEntitys();
            allEntityIDs = entitys.keySet().stream().collect(Collectors.toList());
        }
        HashSet<String> allEntityIDSet = new HashSet<String>();
        for (String entityId : allEntityIDs) {
            allEntityIDSet.add(entityId);
        }
        if (dwList.isEmpty()) {
            selectDwList = allEntityIDs;
        } else {
            for (String entityId : dwList) {
                if (allEntityIDSet.contains(entityId)) {
                    selectDwList.add(entityId);
                    continue;
                }
                errorDimensionStr.append("\u5f53\u524d\u7ef4\u5ea6\uff08");
                for (DimensionValue dimensionValue : queryDimensionValueMap.values()) {
                    errorDimensionStr.append(dimensionValue.getName() + ":" + dimensionValue.getValue() + ";");
                }
                errorDimensionStr.append("\uff09\u5173\u8054\u4e0d\u5230" + dwEntity.getDimensionName() + "\u7ef4\u5ea6:" + entityId + "\u3002");
            }
        }
        if (selectDwList.isEmpty()) {
            errorDimensionStr.append("\u5f53\u524d\u7ef4\u5ea6\uff08");
            for (DimensionValue dimensionValue : queryDimensionValueMap.values()) {
                errorDimensionStr.append(dimensionValue.getName() + ":" + dimensionValue.getValue() + ";");
            }
            errorDimensionStr.append("\uff09\u5173\u8054\u7684" + dwEntity.getDimensionName() + "\u7ef4\u5ea6\u4e3a\u7a7a\u3002");
        }
        if (errorDimensionStr.length() > 0) {
            logger.warn("\u5408\u5e76\u62a5\u8868\u7ef4\u5ea6\u4fe1\u606f\u9002\u914d,\uff0c\u4e3b\u7ef4\u5ea6\u4e3a\u57fa\u7840\u6570\u636e\uff1a" + errorDimensionStr.toString());
        }
        return selectDwList;
    }

    private List<Map<String, DimensionValue>> getDimensionValueList(List<String> dimensionNames, List<List<String>> dimensionValues, int layer) {
        ArrayList<Map<String, DimensionValue>> dimensionValueList;
        block5: {
            List<String> valueList;
            String dimensionName;
            block4: {
                dimensionValueList = new ArrayList<Map<String, DimensionValue>>();
                dimensionName = dimensionNames.get(layer);
                valueList = dimensionValues.get(layer);
                if (layer >= dimensionNames.size() - 1) break block4;
                List<Map<String, DimensionValue>> subDimensionValueList = this.getDimensionValueList(dimensionNames, dimensionValues, layer + 1);
                for (String dimensionValue : valueList) {
                    for (Map<String, DimensionValue> subDimensionValue : subDimensionValueList) {
                        LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                        for (Map.Entry<String, DimensionValue> entry : subDimensionValue.entrySet()) {
                            DimensionValue value = new DimensionValue();
                            value.setName(entry.getValue().getName());
                            value.setValue(entry.getValue().getValue());
                            dimensionValueMap.put(entry.getKey(), value);
                        }
                        DimensionValue value = new DimensionValue();
                        value.setName(dimensionName);
                        value.setValue(dimensionValue);
                        dimensionValueMap.put(dimensionName, value);
                        dimensionValueList.add(dimensionValueMap);
                    }
                }
                break block5;
            }
            if (layer != dimensionNames.size() - 1) break block5;
            for (String dimensionValue : valueList) {
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue value = new DimensionValue();
                value.setName(dimensionName);
                value.setValue(dimensionValue);
                dimensionValueMap.put(dimensionName, value);
                dimensionValueList.add(dimensionValueMap);
            }
        }
        return dimensionValueList;
    }

    private static List<String> getAllEntityKey(EntityReturnInfo queryEntityData) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        for (EntityData entity : queryEntityData.getEntitys()) {
            valueIDList.add(entity.getId());
            valueIDList.addAll(GcDimensionProcessor.getAllEntityKey(entity));
        }
        return valueIDList;
    }

    private static List<String> getAllEntityKey(EntityData parentEntity) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        for (EntityData entity : parentEntity.getChildren()) {
            valueIDList.add(entity.getId());
            valueIDList.addAll(GcDimensionProcessor.getAllEntityKey(entity));
        }
        return valueIDList;
    }
}


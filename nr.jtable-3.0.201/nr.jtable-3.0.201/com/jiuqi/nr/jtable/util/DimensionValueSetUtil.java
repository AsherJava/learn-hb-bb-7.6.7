/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class DimensionValueSetUtil {
    public static DimensionValueSet getDimensionValueSet(JtableContext jtableContext) {
        return DimensionValueSetUtil.getDimensionValueSet(jtableContext, false);
    }

    @Deprecated
    public static DimensionValueSet getBatchCheckDimensionValueSet(JtableContext jtableContext, boolean allLeafChildren) {
        DimensionValue dimensionValue;
        String valueStr;
        Object queryEntityData;
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null || dimensionSet.isEmpty()) {
            return dimensionValueSet;
        }
        if (StringUtils.isEmpty((String)jtableContext.getFormSchemeKey())) {
            return DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        }
        if (dimensionSet.containsKey("VERSIONID")) {
            DimensionValue dimensionValue2 = dimensionSet.get("VERSIONID");
            dimensionValueSet.setValue("VERSIONID", (Object)dimensionValue2.getValue());
        }
        EntityViewData dwEntityView = jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String unitStr = dimensionSet.get(dwEntityView.getDimensionName()).getValue();
        EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String periodStr = dimensionSet.get(periodEntityView.getDimensionName()).getValue();
        List<EntityViewData> dimEntityList = jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        if (StringUtils.isNotEmpty((String)unitStr)) {
            String[] values = unitStr.split(";");
            List<String> valuesAsList = Arrays.asList(values);
            ArrayList<String> valueList = new ArrayList<String>(valuesAsList);
            if (allLeafChildren) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
                entityQueryInfo.setParentKey(unitStr);
                entityQueryInfo.setAllChildren(true);
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
                JtableContext context = new JtableContext(jtableContext);
                context.setDimensionSet(dimensionValueMap);
                entityQueryInfo.setContext(context);
                queryEntityData = jtableEntityService.queryEntityData(entityQueryInfo);
                valueList.addAll(DimensionValueSetUtil.getAllEntityKey((EntityReturnInfo)queryEntityData, true));
            }
            if (valueList.size() == 1) {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList.get(0));
            } else {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList);
            }
        } else {
            List<Object> valueList = new ArrayList();
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            if (periodEntityView != null) {
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
            }
            entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
            JtableContext context = new JtableContext(jtableContext);
            context.setDimensionSet(dimensionValueMap);
            entityQueryInfo.setContext(context);
            EntityReturnInfo entityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
            valueList = DimensionValueSetUtil.getAllEntityKey(entityReturnInfo, false);
            if (!valueList.isEmpty()) {
                if (valueList.size() == 1) {
                    dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList.get(0));
                } else {
                    dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList);
                }
            } else {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), (Object)"");
            }
        }
        if (StringUtils.isNotEmpty((String)periodStr)) {
            String[] periodValues = periodStr.split(";");
            List<String> valueList = Arrays.asList(periodValues);
            if (valueList.size() == 1) {
                dimensionValueSet.setValue(periodEntityView.getDimensionName(), (Object)valueList.get(0));
            } else {
                dimensionValueSet.setValue(periodEntityView.getDimensionName(), valueList);
            }
        } else {
            dimensionValueSet.setValue(periodEntityView.getDimensionName(), (Object)"");
        }
        for (EntityViewData entity : dimEntityList) {
            valueStr = "";
            if (dimensionSet.containsKey(entity.getDimensionName())) {
                dimensionValue = dimensionSet.get(entity.getDimensionName());
                valueStr = dimensionValue.getValue();
            }
            if (StringUtils.isNotEmpty((String)valueStr)) {
                String[] values = valueStr.split(";");
                List<String> valueList = Arrays.asList(values);
                if (valueList.size() == 1) {
                    dimensionValueSet.setValue(entity.getDimensionName(), (Object)valueList.get(0));
                    continue;
                }
                dimensionValueSet.setValue(entity.getDimensionName(), valueList);
                continue;
            }
            ArrayList<String> valueList = new ArrayList<String>();
            Object value = dimensionValueSet.getValue(dwEntityView.getDimensionName());
            ArrayList<String> dwDimValueList = new ArrayList<String>();
            if (value instanceof String) {
                dwDimValueList.add((String)value);
            } else if (value instanceof List) {
                List list = (List)value;
                queryEntityData = list.iterator();
                while (queryEntityData.hasNext()) {
                    Object o = queryEntityData.next();
                    dwDimValueList.add((String)o);
                }
            }
            for (String dwid : dwDimValueList) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(entity.getKey());
                JtableContext context = new JtableContext();
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue((String)dimensionValueSet.getValue(periodEntityView.getDimensionName()));
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
                DimensionValue masterValue = new DimensionValue();
                masterValue.setName(dwEntityView.getDimensionName());
                masterValue.setValue(dwid);
                dimensionValueMap.put(dwEntityView.getDimensionName(), masterValue);
                context.setDimensionSet(dimensionValueMap);
                context.setFormSchemeKey(jtableContext.getFormSchemeKey());
                entityQueryInfo.setContext(context);
                EntityReturnInfo dimEntityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
                List<String> allEntityKey = DimensionValueSetUtil.getAllEntityKey(dimEntityReturnInfo, false);
                valueList.addAll(allEntityKey);
            }
            if (valueList != null && !valueList.isEmpty()) {
                if (valueList.size() == 1) {
                    dimensionValueSet.setValue(entity.getDimensionName(), valueList.get(0));
                    continue;
                }
                HashSet valueSet = new HashSet(valueList);
                ArrayList values = new ArrayList(valueSet);
                dimensionValueSet.setValue(entity.getDimensionName(), values);
                continue;
            }
            dimensionValueSet.setValue(entity.getDimensionName(), (Object)"");
        }
        for (String dimName : dimensionSet.keySet()) {
            if (dimensionValueSet.hasValue(dimName)) continue;
            valueStr = "";
            if (dimensionSet.containsKey(dimName)) {
                dimensionValue = dimensionSet.get(dimName);
                valueStr = dimensionValue.getValue();
            }
            if (!StringUtils.isNotEmpty((String)valueStr)) continue;
            dimensionValueSet.setValue(dimName, (Object)valueStr);
        }
        return dimensionValueSet;
    }

    public static DimensionValueSet getDimensionValueSet(JtableContext jtableContext, boolean allLeafChildren) {
        DimensionValue dimensionValue;
        String valueStr;
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null || dimensionSet.isEmpty()) {
            return dimensionValueSet;
        }
        if (StringUtils.isEmpty((String)jtableContext.getFormSchemeKey())) {
            return DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        }
        if (dimensionSet.containsKey("VERSIONID")) {
            DimensionValue dimensionValue2 = dimensionSet.get("VERSIONID");
            dimensionValueSet.setValue("VERSIONID", (Object)dimensionValue2.getValue());
        }
        EntityViewData dwEntityView = jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String unitStr = dimensionSet.get(dwEntityView.getDimensionName()).getValue();
        EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String periodStr = dimensionSet.get(periodEntityView.getDimensionName()).getValue();
        List<EntityViewData> dimEntityList = jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        if (StringUtils.isNotEmpty((String)unitStr)) {
            String[] values = unitStr.split(";");
            List<String> valuesAsList = Arrays.asList(values);
            ArrayList<String> valueList = new ArrayList<String>(valuesAsList);
            if (allLeafChildren) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
                entityQueryInfo.setParentKey(unitStr);
                entityQueryInfo.setAllChildren(true);
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
                JtableContext context = new JtableContext(jtableContext);
                context.setDimensionSet(dimensionValueMap);
                entityQueryInfo.setContext(context);
                EntityReturnInfo queryEntityData = jtableEntityService.queryEntityData(entityQueryInfo);
                valueList.addAll(DimensionValueSetUtil.getAllEntityKey(queryEntityData, true));
            }
            DimensionValueSetUtil.addDimensionValue(dimensionValueSet, dwEntityView, valueList);
        } else {
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            if (periodEntityView != null) {
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
            }
            entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
            JtableContext context = new JtableContext(jtableContext);
            context.setDimensionSet(dimensionValueMap);
            entityQueryInfo.setContext(context);
            EntityReturnInfo entityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
            List<String> valueList = DimensionValueSetUtil.getAllEntityKey(entityReturnInfo, false);
            DimensionValueSetUtil.addDimensionValue(dimensionValueSet, dwEntityView, valueList);
        }
        List<String> periodValueList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)periodStr)) {
            String[] periodValues = periodStr.split(";");
            periodValueList = Arrays.asList(periodValues);
        }
        DimensionValueSetUtil.addDimensionValue(dimensionValueSet, periodEntityView, periodValueList);
        for (EntityViewData entity : dimEntityList) {
            valueStr = "";
            if (dimensionSet.containsKey(entity.getDimensionName())) {
                dimensionValue = dimensionSet.get(entity.getDimensionName());
                valueStr = dimensionValue.getValue();
            }
            if (StringUtils.isNotEmpty((String)valueStr)) {
                String[] values = valueStr.split(";");
                List<String> valueList = Arrays.asList(values);
                DimensionValueSetUtil.addDimensionValue(dimensionValueSet, entity, valueList);
                continue;
            }
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setEntityViewKey(entity.getKey());
            JtableContext context = new JtableContext(jtableContext);
            entityQueryInfo.setContext(context);
            EntityReturnInfo entityReturnInfo = jtableEntityService.queryDimEntityData(entityQueryInfo);
            List<String> valueList = DimensionValueSetUtil.getAllEntityKey(entityReturnInfo, false);
            DimensionValueSetUtil.addDimensionValue(dimensionValueSet, entity, valueList);
        }
        for (String dimName : dimensionSet.keySet()) {
            if (dimensionValueSet.hasValue(dimName)) continue;
            valueStr = "";
            if (dimensionSet.containsKey(dimName)) {
                dimensionValue = dimensionSet.get(dimName);
                valueStr = dimensionValue.getValue();
            }
            if (!StringUtils.isNotEmpty((String)valueStr)) continue;
            dimensionValueSet.setValue(dimName, (Object)valueStr);
        }
        return dimensionValueSet;
    }

    private static void addDimensionValue(DimensionValueSet dimensionValueSet, EntityViewData entityViewData, List<String> valueList) {
        if (CollectionUtils.isEmpty(valueList)) {
            dimensionValueSet.setValue(entityViewData.getDimensionName(), (Object)"");
        } else if (valueList.size() == 1) {
            dimensionValueSet.setValue(entityViewData.getDimensionName(), (Object)valueList.get(0));
        } else {
            dimensionValueSet.setValue(entityViewData.getDimensionName(), valueList);
        }
    }

    public static DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    public static List<DimensionValueSet> getDimensionValueSetList(Map<String, DimensionValue> dimensionSet, int batchSize) {
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionSet == null) {
            return dimensionValueSetList;
        }
        DimensionValueSet allDimValues = new DimensionValueSet();
        String splitDim = null;
        for (DimensionValue value : dimensionSet.values()) {
            String dimName = value.getName();
            String[] values = value.getValue().split(";");
            if (values.length == 1) {
                allDimValues.setValue(dimName, (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            allDimValues.setValue(dimName, valueList);
            if (valueList.size() <= batchSize) continue;
            splitDim = dimName;
        }
        if (splitDim == null) {
            dimensionValueSetList.add(allDimValues);
        } else {
            List allValueList = (List)allDimValues.getValue(splitDim);
            int length = allValueList.size();
            int num = (length + batchSize - 1) / batchSize;
            for (int i = 0; i < num; ++i) {
                int fromIndex = i * batchSize;
                int toIndex = (i + 1) * batchSize < length ? (i + 1) * batchSize : length;
                DimensionValueSet dimensionValueSet = new DimensionValueSet(allDimValues);
                dimensionValueSet.setValue(splitDim, allValueList.subList(fromIndex, toIndex));
                dimensionValueSetList.add(dimensionValueSet);
            }
        }
        return dimensionValueSetList;
    }

    public static List<DimensionValueSet> getDimensionValueSetList(Map<String, DimensionValue> dimensionSet) {
        List<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionSet == null) {
            return dimensionValueSetList;
        }
        ArrayList<String> dimensionNameList = new ArrayList<String>();
        ArrayList<List<Object>> dimensionValueList = new ArrayList<List<Object>>();
        for (DimensionValue value : dimensionSet.values()) {
            String[] values = value.getValue().split(";");
            List<String> valueList = Arrays.asList(values);
            dimensionValueList.add(valueList);
            dimensionNameList.add(value.getName());
        }
        if (dimensionNameList.size() > 0 && dimensionValueList.size() > 0 && dimensionNameList.size() == dimensionValueList.size()) {
            dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, 0);
        }
        return dimensionValueSetList;
    }

    private static List<DimensionValueSet> getDimensionValueSetList(List<String> dimensionNameList, List<List<Object>> dimensionValueList, int layer) {
        ArrayList<DimensionValueSet> dimensionValueSetList;
        block4: {
            block3: {
                dimensionValueSetList = new ArrayList<DimensionValueSet>();
                if (layer >= dimensionNameList.size() - 1) break block3;
                String dimensionName = dimensionNameList.get(layer);
                List<Object> valueList = dimensionValueList.get(layer);
                List<DimensionValueSet> subDimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, layer + 1);
                for (Object dimensionValue : valueList) {
                    for (DimensionValueSet subDimensionValueSet : subDimensionValueSetList) {
                        DimensionValueSet dimensionValueSet = new DimensionValueSet(subDimensionValueSet);
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                        dimensionValueSetList.add(dimensionValueSet);
                    }
                }
                break block4;
            }
            if (layer != dimensionNameList.size() - 1) break block4;
            String dimensionName = dimensionNameList.get(layer);
            List<Object> valueList = dimensionValueList.get(layer);
            for (Object dimensionValue : valueList) {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(dimensionName, dimensionValue);
                dimensionValueSetList.add(dimensionValueSet);
            }
        }
        return dimensionValueSetList;
    }

    public static List<Map<String, DimensionValue>> getDimensionSetList(Map<String, DimensionValue> dimensionSet) {
        ArrayList<Map<String, DimensionValue>> dimensionSetList = new ArrayList<Map<String, DimensionValue>>();
        List<DimensionValueSet> dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionSet);
        for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
            dimensionSetList.add(DimensionValueSetUtil.getDimensionSet(dimensionValueSet));
        }
        return dimensionSetList;
    }

    public static List<String> getAllEntityKey(EntityReturnInfo queryEntityData, boolean leafEntity) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        for (EntityData entity : queryEntityData.getEntitys()) {
            if (leafEntity) {
                if (entity.getChildren().isEmpty()) {
                    valueIDList.add(entity.getId());
                }
            } else {
                valueIDList.add(entity.getId());
            }
            valueIDList.addAll(DimensionValueSetUtil.getAllEntityKey(entity, leafEntity));
        }
        return valueIDList;
    }

    private static List<String> getAllEntityKey(EntityData parentEntity, boolean leafEntity) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        for (EntityData entity : parentEntity.getChildren()) {
            if (leafEntity) {
                if (entity.getChildren().isEmpty()) {
                    valueIDList.add(entity.getId());
                }
            } else {
                valueIDList.add(entity.getId());
            }
            valueIDList.addAll(DimensionValueSetUtil.getAllEntityKey(entity, leafEntity));
        }
        return valueIDList;
    }

    public static Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue value = new DimensionValue();
            value.setName(dimensionValueSet.getName(i));
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue == null) {
                value.setValue("");
            } else if (dimensionValue instanceof Collection) {
                value.setValue(StringUtils.join(((Collection)dimensionValue).iterator(), (String)";"));
            } else {
                value.setValue(dimensionValue.toString());
            }
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    public static Map<String, String> getDimensionTitle(DimensionValueSet dimensionValueSet, String formSchemeKey, Map<String, Map<String, String>> entityTitleMap, Map<String, EntityDataLoader> entityLoaderMap) {
        HashMap<String, String> dimensionTitle = new HashMap<String, String>();
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        EntityViewData dwEntityView = jtableParamService.getDwEntity(formSchemeKey);
        String unitStr = dimensionValueSet.getValue(dwEntityView.getDimensionName()).toString();
        EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(formSchemeKey);
        String periodStr = dimensionValueSet.getValue(periodEntityView.getDimensionName()).toString();
        dimensionTitle.put(periodEntityView.getDimensionName(), periodStr);
        List<EntityViewData> dimEntityList = jtableParamService.getDimEntityList(formSchemeKey);
        dimEntityList.add(dwEntityView);
        for (EntityViewData entity : dimEntityList) {
            IEntityRow er;
            String entityTitle;
            if (!dimensionValueSet.hasValue(entity.getDimensionName())) continue;
            String dimensionValue = dimensionValueSet.getValue(entity.getDimensionName()).toString();
            Map<String, String> entityMap = entityTitleMap.get(entity.getKey());
            if (entityMap == null) {
                entityMap = new HashMap<String, String>();
                entityTitleMap.put(entity.getKey(), entityMap);
            }
            if ((entityTitle = entityMap.get(dimensionValue)) != null) {
                dimensionTitle.put(entity.getDimensionName(), entityTitle);
                continue;
            }
            if (entityMap.containsKey(dimensionValue)) {
                dimensionTitle.put(entity.getDimensionName(), dimensionValue);
                continue;
            }
            EntityDataLoader edl = entityLoaderMap.get(entity.getDimensionName());
            if (edl != null && (er = edl.getEntityRow(dimensionValue)) != null) {
                entityTitle = er.getTitle();
            }
            if (entityTitle == null) {
                dimensionTitle.put(entity.getDimensionName(), dimensionValue);
            } else {
                dimensionTitle.put(entity.getDimensionName(), entityTitle);
            }
            entityMap.put(dimensionValue, entityTitle);
        }
        return dimensionTitle;
    }

    public static boolean isMasterLeaf(JtableContext jtableContext, EntityViewData dwEntityView) {
        boolean isLeaf = false;
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        DimensionValue dimensionValue = jtableContext.getDimensionSet().get(dwEntityView.getDimensionName());
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityViewKey(dwEntityView.getKey());
        entityQueryByKeyInfo.setEntityKey(dimensionValue.getValue());
        entityQueryByKeyInfo.setContext(jtableContext);
        EntityByKeyReturnInfo entityDataByKey = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        if (entityDataByKey.getEntity() == null) {
            return isLeaf;
        }
        isLeaf = entityDataByKey.getEntity().isLeaf();
        return isLeaf;
    }

    public static JtableContext fillDw(JtableContext jtableContext, String dwScope) {
        if (StringUtils.isEmpty((String)dwScope) || dwScope.equals("self")) {
            return jtableContext;
        }
        if (!dwScope.equals("children") && !dwScope.equals("allChildren")) {
            return jtableContext;
        }
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        if (dimensionSet == null || dimensionSet.isEmpty()) {
            return jtableContext;
        }
        EntityViewData dwEntityView = jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String unitStr = dimensionSet.get(dwEntityView.getDimensionName()).getValue();
        EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String periodStr = dimensionSet.get(periodEntityView.getDimensionName()).getValue();
        if (StringUtils.isNotEmpty((String)unitStr)) {
            String[] values = unitStr.split(";");
            List<String> valuesAsList = Arrays.asList(values);
            ArrayList<String> valueList = new ArrayList<String>(valuesAsList);
            HashSet<String> dwSet = new HashSet<String>();
            for (String dw : valueList) {
                dwSet.add(dw);
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
                entityQueryInfo.setParentKey(dw);
                entityQueryInfo.setAllChildren(dwScope.equals("allChildren"));
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
                JtableContext context = new JtableContext(jtableContext);
                context.setDimensionSet(dimensionValueMap);
                entityQueryInfo.setContext(context);
                EntityReturnInfo queryEntityData = jtableEntityService.queryEntityData(entityQueryInfo);
                dwSet.addAll(DimensionValueSetUtil.getAllEntityKey(queryEntityData, true));
            }
            dimensionSet.get(dwEntityView.getDimensionName()).setValue(StringUtils.join(dwSet.iterator(), (String)";"));
        }
        return jtableContext;
    }

    public static Map<String, DimensionValue> mergeDimension(List<Map<String, DimensionValue>> relationDimensions) {
        HashMap<String, DimensionValue> maergeDimension = new HashMap<String, DimensionValue>();
        for (Map<String, DimensionValue> oneMap : relationDimensions) {
            for (Map.Entry<String, DimensionValue> one : oneMap.entrySet()) {
                String[] splitValues;
                DimensionValue dimensionValue = null;
                if (maergeDimension.containsKey(one.getKey())) {
                    dimensionValue = (DimensionValue)maergeDimension.get(one.getKey());
                } else {
                    dimensionValue = new DimensionValue();
                    dimensionValue.setName(one.getKey());
                    dimensionValue.setValue("");
                    maergeDimension.put(one.getKey(), dimensionValue);
                }
                String[] splits = dimensionValue.getValue().split(";");
                DimensionValue oneValue = one.getValue();
                String value = oneValue.getValue();
                for (String key : splitValues = value.split(";")) {
                    boolean notHaveKey = false;
                    for (String newKey : splits) {
                        if (!newKey.equals(key)) continue;
                        notHaveKey = true;
                        break;
                    }
                    if (notHaveKey) continue;
                    String value2 = dimensionValue.getValue();
                    if ("".equals(value2)) {
                        dimensionValue.setValue(key);
                        continue;
                    }
                    dimensionValue.setValue(value2 + ";" + key);
                }
            }
        }
        return maergeDimension;
    }

    public static String getContextStr(JtableContext jtableContext, String regionKey, String linkKey) {
        StringBuffer contextStringBuffer = new StringBuffer();
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        if (StringUtil.isNotEmpty((String)jtableContext.getFormKey())) {
            FormData formData = jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            contextStringBuffer.append("\u5f53\u524d\u62a5\u8868:").append(formData.getCode() + "|" + formData.getTitle() + "|" + formData.getKey()).append(";");
        }
        if (StringUtil.isNotEmpty((String)regionKey)) {
            RegionData region = jtableParamService.getRegion(regionKey);
            contextStringBuffer.append("\u5f53\u524d\u533a\u57df:").append(region.getTitle() + "|" + region.getKey()).append(";");
        }
        if (StringUtil.isNotEmpty((String)linkKey)) {
            LinkData linkData = jtableParamService.getLink(linkKey);
            contextStringBuffer.append("\u5f53\u524d\u94fe\u63a5:").append(linkData.getTitle() + "|" + linkData.getKey()).append(";");
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        contextStringBuffer.append("\u5f53\u524d\u7ef4\u5ea6:").append(dimensionValueSet.toString()).append(";");
        return contextStringBuffer.toString();
    }

    public static Map<String, DimensionValue> getOnlyDimCartesian(Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        List<EntityViewData> dimEntityList = jtableParamService.getDimEntityList(formSchemeKey);
        if (dimEntityList == null || dimEntityList.isEmpty()) {
            return new HashMap<String, DimensionValue>();
        }
        List dimName = dimEntityList.stream().map(EntityViewData::getDimensionName).collect(Collectors.toList());
        HashMap<String, DimensionValue> result = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            String key = entry.getKey();
            DimensionValue value = entry.getValue();
            if (!dimName.contains(key)) continue;
            result.put(key, value);
        }
        return result;
    }
}


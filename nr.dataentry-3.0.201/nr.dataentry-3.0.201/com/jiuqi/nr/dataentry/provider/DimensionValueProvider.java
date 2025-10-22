/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.provider;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IRelationDimensionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DimensionValueProvider {
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired(required=false)
    private IRelationDimensionService relationDimensionService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Value(value="${jiuqi.nr.jio.export.dims:true}")
    private boolean gmsDims;

    public List<Map<String, DimensionValue>> splitDimensionValueList(Map<String, DimensionValue> dimensionSet, String formSchemeKey, List<String> errorDimensionList) {
        return this.splitDimensionValueList(dimensionSet, formSchemeKey, errorDimensionList, true);
    }

    public List<Map<String, DimensionValue>> splitDimensionValueList(Map<String, DimensionValue> dimensionSet, String formSchemeKey, List<String> errorDimensionList, boolean mustContains) {
        JtableContext context = new JtableContext();
        context.setDimensionSet(dimensionSet);
        context.setFormSchemeKey(formSchemeKey);
        return this.splitDimensionValueList(context, errorDimensionList, mustContains);
    }

    public List<Map<String, DimensionValue>> splitDimensionValueList(JtableContext jtableContext, List<String> errorDimensionList, boolean mustContains) {
        Map dimensionSet = jtableContext.getDimensionSet();
        String formSchemeKey = jtableContext.getFormSchemeKey();
        if (errorDimensionList == null) {
            errorDimensionList = new ArrayList<String>();
        }
        List<Map<String, DimensionValue>> dimensionValueList = new ArrayList<Map<String, DimensionValue>>();
        if (dimensionSet == null) {
            return dimensionValueList;
        }
        ArrayList<String> dimensionNames = new ArrayList<String>();
        ArrayList<List<String>> dimensionValues = new ArrayList<List<String>>();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
        String unitStr = ((DimensionValue)dimensionSet.get(dwEntity.getDimensionName())).getValue();
        EntityViewData periodEntity = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        String periodStr = ((DimensionValue)dimensionSet.get(periodEntity.getDimensionName())).getValue();
        List dimEntityList = this.jtableParamService.getDimEntityList(formSchemeKey);
        dimensionNames.add(dwEntity.getDimensionName());
        ArrayList<String> dwIDList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)unitStr)) {
            String[] values = unitStr.split(";");
            dwIDList.addAll(Arrays.asList(values));
        }
        dimensionValues.add(dwIDList);
        dimensionNames.add(periodEntity.getDimensionName());
        ArrayList<String> dataTimeList = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)periodStr)) {
            String[] values = periodStr.split(";");
            dataTimeList.addAll(Arrays.asList(values));
        }
        dimensionValues.add(dataTimeList);
        for (EntityViewData entity : dimEntityList) {
            dimensionNames.add(entity.getDimensionName());
            String dimensionValueStr = "";
            if (dimensionSet.containsKey(entity.getDimensionName())) {
                DimensionValue dimensionValue = (DimensionValue)dimensionSet.get(entity.getDimensionName());
                dimensionValueStr = dimensionValue.getValue();
            }
            List<Object> valueIDList = new ArrayList();
            if (StringUtils.isNotEmpty((String)dimensionValueStr)) {
                String[] values = dimensionValueStr.split(";");
                valueIDList = Arrays.asList(values);
            } else {
                boolean entityRefer = this.entityMetaService.estimateEntityRefer(dwEntity.getKey(), entity.getKey());
                if (!entityRefer) {
                    valueIDList = this.jtableEntityService.getAllDimEntityKey(entity.getKey(), dimensionSet, formSchemeKey);
                }
            }
            dimensionValues.add(valueIDList);
        }
        dimensionValueList = this.getRelationDimensionValueList(jtableContext, dimensionNames, dimensionValues, errorDimensionList, mustContains);
        return dimensionValueList;
    }

    private List<Map<String, DimensionValue>> getRelationDimensionValueList(JtableContext jtableContext, List<String> dimensionNames, List<List<String>> dimensionValues, List<String> errorDimensionList, boolean mustContains) {
        ArrayList<Map<String, DimensionValue>> dimensionValueList = new ArrayList<Map<String, DimensionValue>>();
        String formSchemeKey = jtableContext.getFormSchemeKey();
        if (this.relationDimensionService != null && this.gmsDims) {
            HashMap<String, List<String>> dimValues = new HashMap<String, List<String>>();
            for (int i = 0; i < dimensionNames.size(); ++i) {
                dimValues.put(dimensionNames.get(i), dimensionValues.get(i));
            }
            List<DimensionValueSet> dimensionValueSetList = this.relationDimensionService.getRelationDimensionValueList(formSchemeKey, dimValues);
            if (dimensionValueSetList == null) {
                throw new NotFoundEntityException(new String[]{"\u63a5\u53e3\u4e2d\u6ca1\u6709\u8fd4\u56de\u4e3b\u4f53\u7ef4\u5ea6\u96c6\u5408\uff01"});
            }
            for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<List<String>> values = new ArrayList<List<String>>();
                for (int i = 0; i < dimensionValueSet.size(); ++i) {
                    String name = dimensionValueSet.getName(i);
                    Object value = dimensionValueSet.getValue(name);
                    if (value != null && value instanceof List) {
                        names.add(name);
                        values.add((List)value);
                        continue;
                    }
                    if (value == null || !(value instanceof String)) continue;
                    names.add(name);
                    ArrayList<String> valuess = new ArrayList<String>();
                    valuess.add(value.toString());
                    values.add(valuess);
                }
                List<Map<String, DimensionValue>> subDimensionValueList = this.getDimensionValueList(names, values, 0);
                dimensionValueList.addAll(subDimensionValueList);
            }
            return dimensionValueList;
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
        EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
        EntityViewData periodEntity = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        List dimEntityList = this.jtableParamService.getDimEntityList(formSchemeKey);
        List<String> periodList = dimensionValues.get(dimensionNames.indexOf(periodEntity.getDimensionName()));
        List<String> dwList = dimensionValues.get(dimensionNames.indexOf(dwEntity.getDimensionName()));
        for (String period : periodList) {
            List<String> allEntityIDs;
            LinkedHashMap<String, DimensionValue> queryDimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            DimensionValue periodValue = new DimensionValue();
            periodValue.setName(periodEntity.getDimensionName());
            periodValue.setValue(period);
            queryDimensionValueMap.put(periodEntity.getDimensionName(), periodValue);
            List<Object> selectDwList = new ArrayList();
            JtableContext context = new JtableContext();
            context.setDimensionSet(queryDimensionValueMap);
            context.setFormSchemeKey(formSchemeKey);
            context.setVariableMap(jtableContext.getVariableMap());
            if (dwList.isEmpty()) {
                EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                entityQueryInfo.setSorted(true);
                entityQueryInfo.setEntityViewKey(dwEntity.getKey());
                entityQueryInfo.setReadAuth(true);
                entityQueryInfo.setContext(context);
                EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
                allEntityIDs = DimensionValueProvider.getAllEntityKey(entityReturnInfo);
            } else {
                allEntityIDs = dwList;
            }
            HashSet<String> allEntityIDSet = new HashSet<String>();
            for (String string : allEntityIDs) {
                allEntityIDSet.add(string);
            }
            if (dwList.isEmpty()) {
                selectDwList = allEntityIDs;
            } else {
                for (String string : dwList) {
                    if (allEntityIDSet.contains(string)) {
                        selectDwList.add(string);
                        continue;
                    }
                    StringBuffer errorDimensionStr = new StringBuffer();
                    errorDimensionStr.append("\u5f53\u524d\u7ef4\u5ea6\uff08");
                    for (DimensionValue dimensionValue : queryDimensionValueMap.values()) {
                        errorDimensionStr.append(dimensionValue.getName() + ":" + dimensionValue.getValue() + ";");
                    }
                    errorDimensionStr.append("\uff09\u5173\u8054\u4e0d\u5230" + dwEntity.getDimensionName() + "\u7ef4\u5ea6:" + string + "\u3002");
                    errorDimensionList.add(errorDimensionStr.toString());
                }
            }
            if (selectDwList.isEmpty()) {
                StringBuffer errorDimensionStr = new StringBuffer();
                errorDimensionStr.append("\u5f53\u524d\u7ef4\u5ea6\uff08");
                for (DimensionValue dimensionValue : queryDimensionValueMap.values()) {
                    errorDimensionStr.append(dimensionValue.getName() + ":" + dimensionValue.getValue() + ";");
                }
                errorDimensionStr.append("\uff09\u5173\u8054\u7684" + dwEntity.getDimensionName() + "\u7ef4\u5ea6\u4e3a\u7a7a\u3002");
                errorDimensionList.add(errorDimensionStr.toString());
            }
            for (String string : selectDwList) {
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                dimensionValueMap.put(periodEntity.getDimensionName(), periodValue);
                DimensionValue masterValue = new DimensionValue();
                masterValue.setName(dwEntity.getDimensionName());
                masterValue.setValue(string);
                dimensionValueMap.put(dwEntity.getDimensionName(), masterValue);
                ArrayList<String> dimNames = new ArrayList<String>();
                ArrayList<List<String>> dimValues = new ArrayList<List<String>>();
                for (Object dimEntity : dimEntityList) {
                    dimNames.add(dimEntity.getDimensionName());
                    List<String> selectDimList = new ArrayList();
                    List<String> dimList = dimensionValues.get(dimensionNames.indexOf(dimEntity.getDimensionName()));
                    List<String> allDimList = new ArrayList();
                    boolean entityRefer = this.entityMetaService.estimateEntityRefer(dwEntity.getKey(), dimEntity.getKey());
                    if (entityRefer) {
                        EntityQueryByViewInfo dimEntityQueryInfo = new EntityQueryByViewInfo();
                        dimEntityQueryInfo.setEntityViewKey(dimEntity.getKey());
                        JtableContext dimContext = new JtableContext();
                        dimContext.setDimensionSet(dimensionValueMap);
                        dimContext.setFormSchemeKey(formSchemeKey);
                        dimEntityQueryInfo.setContext(dimContext);
                        dimEntityQueryInfo.setReadAuth(true);
                        EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryDimEntityData(dimEntityQueryInfo);
                        for (EntityData entity : entityReturnInfo.getEntitys()) {
                            allDimList.add(entity.getId());
                        }
                    } else {
                        allDimList = this.jtableEntityService.getAllDimEntityKey(dimEntity.getKey(), dimensionValueMap, formSchemeKey);
                    }
                    HashSet<String> allDimIDSet = new HashSet<String>();
                    for (String dimId : allDimList) {
                        allDimIDSet.add(dimId);
                    }
                    if (dimList.isEmpty()) {
                        selectDimList = allDimList;
                    } else {
                        for (String dimId : dimList) {
                            if (allDimIDSet.contains(dimId)) {
                                selectDimList.add(dimId);
                                continue;
                            }
                            if (mustContains) {
                                StringBuffer errorDimensionStr = new StringBuffer();
                                errorDimensionStr.append("\u5f53\u524d\u7ef4\u5ea6\uff08");
                                for (DimensionValue dimensionValue : dimensionValueMap.values()) {
                                    errorDimensionStr.append(dimensionValue.getName() + ":" + dimensionValue.getValue() + ";");
                                }
                                errorDimensionStr.append("\uff09\u5173\u8054\u4e0d\u5230" + dimEntity.getDimensionName() + "\u7ef4\u5ea6:" + dimId + "\u3002");
                                errorDimensionList.add(errorDimensionStr.toString());
                                continue;
                            }
                            if (!dimId.equals("PROVIDER_BASECURRENCY") && !dimId.equals("PROVIDER_PBASECURRENCY")) continue;
                            selectDimList.add(dimId);
                        }
                    }
                    dimValues.add(selectDimList);
                }
                boolean continueFlag = false;
                if (dimValues.size() > 0) {
                    Object dimEntity;
                    dimEntity = dimValues.iterator();
                    while (dimEntity.hasNext()) {
                        List dimValue = (List)dimEntity.next();
                        if (dimValue.size() != 0) continue;
                        continueFlag = true;
                    }
                }
                if (continueFlag) continue;
                if (dimNames.size() > 0) {
                    List<Map<String, DimensionValue>> subDimensionValueList = this.getDimensionValueList(dimNames, dimValues, 0);
                    for (Map<String, DimensionValue> subDimensionValue : subDimensionValueList) {
                        subDimensionValue.put(periodEntity.getDimensionName(), new DimensionValue(periodValue));
                        subDimensionValue.put(dwEntity.getDimensionName(), new DimensionValue(masterValue));
                    }
                    dimensionValueList.addAll(subDimensionValueList);
                    continue;
                }
                dimensionValueList.add(dimensionValueMap);
            }
        }
        Iterator iterator = dimensionValueList.iterator();
        while (iterator.hasNext()) {
            Map next = (Map)iterator.next();
            if (next.size() >= dimensionNames.size()) continue;
            iterator.remove();
        }
        return dimensionValueList;
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
            valueIDList.addAll(DimensionValueProvider.getAllEntityKey(entity));
        }
        return valueIDList;
    }

    private static List<String> getAllEntityKey(EntityData parentEntity) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        for (EntityData entity : parentEntity.getChildren()) {
            valueIDList.add(entity.getId());
            valueIDList.addAll(DimensionValueProvider.getAllEntityKey(entity));
        }
        return valueIDList;
    }

    public List<DimensionValueFormInfo> mergeDimensionValueList(List<DimensionValueFormInfo> dimensionValueFormInfos, List<EntityViewData> entityList) {
        ArrayList<DimensionValueFormInfo> mergeDimensionValueFormInfos = new ArrayList<DimensionValueFormInfo>();
        LinkedHashMap dimensionValueFormGroup = new LinkedHashMap();
        for (DimensionValueFormInfo info : dimensionValueFormInfos) {
            String formStr = info.getFormStr();
            List<Map<String, DimensionValue>> dimensionValueList = null;
            if (dimensionValueFormGroup.containsKey(formStr)) {
                dimensionValueList = (List)dimensionValueFormGroup.get(formStr);
            } else {
                dimensionValueList = new ArrayList();
                dimensionValueFormGroup.put(formStr, dimensionValueList);
            }
            dimensionValueList.add(info.getDimensionValue());
        }
        for (String formStr : dimensionValueFormGroup.keySet()) {
            List dimensionValueList = (List)dimensionValueFormGroup.get(formStr);
            List<Map<String, DimensionValue>> mergeDimensionValues = this.mergeDimensionValue(dimensionValueList, entityList);
            ArrayList<String> forms = new ArrayList<String>();
            String[] formArray = formStr.split(";");
            for (String form : formArray) {
                forms.add(form);
            }
            for (Map map : mergeDimensionValues) {
                DimensionValueFormInfo info = new DimensionValueFormInfo(map, forms);
                mergeDimensionValueFormInfos.add(info);
            }
        }
        return mergeDimensionValueFormInfos;
    }

    private List<Map<String, DimensionValue>> mergeDimensionValue(List<Map<String, DimensionValue>> dimensionValueList, List<EntityViewData> entityList) {
        List<List<String>> dimensionValueArray = this.getDimensionValueArray(dimensionValueList, entityList);
        List<String> mergeDimensionArray = this.mergeDimensionArray(dimensionValueArray);
        return this.getMergeDimensionValueList(mergeDimensionArray, entityList);
    }

    private List<Map<String, DimensionValue>> getMergeDimensionValueList(List<String> mergeDimensionArray, List<EntityViewData> entityList) {
        ArrayList<Map<String, DimensionValue>> dimensionValueList = new ArrayList<Map<String, DimensionValue>>();
        for (String dimensionVauleStr : mergeDimensionArray) {
            String[] dimensionVaules = dimensionVauleStr.split(":");
            if (dimensionVaules.length != entityList.size()) continue;
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            for (int entityIndex = 0; entityIndex < entityList.size(); ++entityIndex) {
                EntityViewData entity = entityList.get(entityIndex);
                DimensionValue value = new DimensionValue();
                value.setName(entity.getDimensionName());
                value.setValue(dimensionVaules[entityIndex]);
                dimensionValueMap.put(entity.getDimensionName(), value);
            }
            dimensionValueList.add(dimensionValueMap);
        }
        return dimensionValueList;
    }

    private List<String> mergeDimensionArray(List<List<String>> dimensionValueArray) {
        ArrayList<String> mergeDimensionValueArray = new ArrayList<String>();
        List<String> childList = dimensionValueArray.get(0);
        if (childList.size() == 1) {
            StringBuffer lastStr = new StringBuffer();
            for (int i = 0; i < dimensionValueArray.size(); ++i) {
                List<String> subList = dimensionValueArray.get(i);
                if (i != 0) {
                    lastStr.append(";");
                }
                lastStr.append(subList.get(0));
            }
            mergeDimensionValueArray.add(lastStr.toString());
        } else {
            ArrayList<List<String>> dimensionValueGroup;
            LinkedHashMap<String, ArrayList<List<String>>> dimensionGroupMap = new LinkedHashMap<String, ArrayList<List<String>>>();
            for (List<String> dimensionValues : dimensionValueArray) {
                String firstDimensionValue = dimensionValues.get(0);
                dimensionValueGroup = null;
                if (dimensionGroupMap.containsKey(firstDimensionValue)) {
                    dimensionValueGroup = (List)dimensionGroupMap.get(firstDimensionValue);
                } else {
                    dimensionValueGroup = new ArrayList();
                    dimensionGroupMap.put(firstDimensionValue, dimensionValueGroup);
                }
                dimensionValueGroup.add(dimensionValues.subList(1, dimensionValues.size()));
            }
            LinkedHashMap<String, String> mergeDimensionGroupMap = new LinkedHashMap<String, String>();
            for (String firstDimensionValue : dimensionGroupMap.keySet()) {
                dimensionValueGroup = (List)dimensionGroupMap.get(firstDimensionValue);
                List<String> mergeDimensionValueList = this.mergeDimensionArray(dimensionValueGroup);
                for (String mergeDimensionValue : mergeDimensionValueList) {
                    if (mergeDimensionGroupMap.containsKey(mergeDimensionValue)) {
                        String currDimensionValue = (String)mergeDimensionGroupMap.get(mergeDimensionValue);
                        mergeDimensionGroupMap.put(mergeDimensionValue, currDimensionValue + ";" + firstDimensionValue);
                        continue;
                    }
                    mergeDimensionGroupMap.put(mergeDimensionValue, firstDimensionValue);
                }
            }
            for (String subDimensionValue : mergeDimensionGroupMap.keySet()) {
                String currDimensionValue = (String)mergeDimensionGroupMap.get(subDimensionValue);
                mergeDimensionValueArray.add(currDimensionValue + ":" + subDimensionValue);
            }
        }
        return mergeDimensionValueArray;
    }

    private List<List<String>> getDimensionValueArray(List<Map<String, DimensionValue>> dimensionValueList, List<EntityViewData> entityList) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        for (EntityViewData entity : entityList) {
            dimensionNames.add(entity.getDimensionName());
        }
        ArrayList<List<String>> dimensionValueArray = new ArrayList<List<String>>(dimensionValueList.size());
        for (int valueIndex = 0; valueIndex < dimensionValueList.size(); ++valueIndex) {
            Map<String, DimensionValue> dimensionValue = dimensionValueList.get(valueIndex);
            ArrayList<String> dimensionValues = new ArrayList<String>(dimensionNames.size());
            for (int nameIndex = 0; nameIndex < dimensionNames.size(); ++nameIndex) {
                dimensionValues.add(dimensionValue.get(dimensionNames.get(nameIndex)).getValue());
            }
            dimensionValueArray.add(dimensionValues);
        }
        return dimensionValueArray;
    }
}


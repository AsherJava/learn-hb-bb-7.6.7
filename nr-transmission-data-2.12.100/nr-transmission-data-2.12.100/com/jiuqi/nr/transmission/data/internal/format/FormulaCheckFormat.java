/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.output.DescriptionInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  org.json.JSONArray
 */
package com.jiuqi.nr.transmission.data.internal.format;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FormulaCheckFormat {
    private final Map<String, Integer> headerIndex = new HashMap<String, Integer>(16);
    private SimpleDateFormat sdf;

    public FormulaCheckFormat() {
        this.headerIndex.put("desKey", this.getDesKeyIndex());
        this.headerIndex.put("taskKey", this.getTaskKeyIndex());
        this.headerIndex.put("formSchemeKey", this.getFormSchemeKeyIndex());
        this.headerIndex.put("formKey", this.getFormKeyIndex());
        this.headerIndex.put("formulaKey", this.getFormKeyIndex());
        this.headerIndex.put("formulaCode", this.getFormulaCodeIndex());
        this.headerIndex.put("globRow", this.getGlobRowIndex());
        this.headerIndex.put("globCol", this.getGlobColIndex());
        this.headerIndex.put("floatId", this.getFloatIdIndex());
        this.headerIndex.put("dimension", this.getDimensionIndex());
        this.headerIndex.put("userId", this.getUserIdIndex());
        this.headerIndex.put("updateTime", this.getUpdateTimeIndex());
        this.headerIndex.put("userName", this.getUserNameIndex());
        this.headerIndex.put("description", this.getDescriptionIndex());
        this.headerIndex.put("userTitle", this.getUserTitleIndex());
        this.headerIndex.put("formulaSchemeKey", this.getFormulaSchemeKey());
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String serialize(List<FormulaCheckDesInfo> desInfos) {
        Object[] values = new Object[desInfos.size() + 1];
        values[0] = this.serializeHeader();
        if (!CollectionUtils.isEmpty(desInfos)) {
            for (int i = 1; i <= desInfos.size(); ++i) {
                Object[] objects;
                values[i] = objects = this.serializeValue(desInfos.get(i - 1));
            }
        }
        return JacksonUtils.objectToJson((Object)values);
    }

    public List<FormulaCheckDesInfo> deserialize(String value) {
        ArrayList<FormulaCheckDesInfo> infos = new ArrayList<FormulaCheckDesInfo>();
        JSONArray parseArray = new JSONArray(value);
        for (int i = 0; i < parseArray.length(); ++i) {
            if (i == 0) continue;
            FormulaCheckDesInfo info = this.deserializeValue(parseArray.getJSONArray(i));
            infos.add(info);
        }
        return infos;
    }

    public List<FormulaCheckDesInfo> deserialize(String value, Map<String, List<DimensionValueSet>> notNeedImportFormMaps, WorkFlowType flowType, String dimensionName, boolean hasAdjustPeriod, String formSchemeKey) {
        ArrayList<FormulaCheckDesInfo> infos = new ArrayList<FormulaCheckDesInfo>();
        JSONArray parseArray = new JSONArray(value);
        ArrayList entityDimension = new ArrayList();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        String taskKey = runTimeViewController.getFormScheme(formSchemeKey).getTaskKey();
        if (hasAdjustPeriod) {
            if (WorkFlowType.ENTITY.equals((Object)flowType)) {
                if (!CollectionUtils.isEmpty(notNeedImportFormMaps)) {
                    for (String s : notNeedImportFormMaps.keySet()) {
                        if (!StringUtils.hasText(s)) continue;
                        entityDimension.addAll(notNeedImportFormMaps.get(s));
                        break;
                    }
                }
                Map entityToAdjustSet = entityDimension.stream().collect(Collectors.groupingBy(a -> a.getValue(dimensionName).toString(), Collectors.mapping(a -> a.getValue("ADJUST").toString(), Collectors.toList())));
                for (int i = 0; i < parseArray.length(); ++i) {
                    FormulaCheckDesInfo info;
                    if (i == 0 || (info = this.deserializeValue(parseArray.getJSONArray(i), formSchemeKey, taskKey)) == null) continue;
                    Map sourceDimensionSet = info.getDimensionSet();
                    DimensionValue entityValue = (DimensionValue)sourceDimensionSet.get(dimensionName);
                    String entity = entityValue.getValue();
                    DimensionValue adjustValue = (DimensionValue)sourceDimensionSet.get("ADJUST");
                    String adjustPeriod = adjustValue.getValue();
                    if (!CollectionUtils.isEmpty(entityToAdjustSet.get(entity)) && entityToAdjustSet.get(entity).contains(adjustPeriod)) continue;
                    infos.add(info);
                }
            } else {
                String formKey;
                HashMap formToEntityToAdjustSet = new HashMap();
                for (Map.Entry<String, List<DimensionValueSet>> stringListEntry : notNeedImportFormMaps.entrySet()) {
                    formKey = stringListEntry.getKey();
                    Map adjustToEntityForForm = stringListEntry.getValue().stream().collect(Collectors.groupingBy(a -> a.getValue("ADJUST").toString(), Collectors.mapping(a -> a.getValue(dimensionName).toString(), Collectors.toList())));
                    formToEntityToAdjustSet.put(formKey, adjustToEntityForForm);
                }
                for (int i = 0; i < parseArray.length(); ++i) {
                    FormulaCheckDesInfo info;
                    if (i == 0 || (info = this.deserializeValue(parseArray.getJSONArray(i), formSchemeKey, taskKey)) == null) continue;
                    formKey = info.getFormKey();
                    Map sourceDimensionSet = info.getDimensionSet();
                    DimensionValue adjustValue = (DimensionValue)sourceDimensionSet.get("ADJUST");
                    String adjustPeriod = adjustValue.getValue();
                    DimensionValue entityValue = (DimensionValue)sourceDimensionSet.get(dimensionName);
                    String entity = entityValue.getValue();
                    Map adjustToEntityForForm = (Map)formToEntityToAdjustSet.get(formKey);
                    if (!CollectionUtils.isEmpty(adjustToEntityForForm)) {
                        if (!CollectionUtils.isEmpty((Collection)adjustToEntityForForm.get(adjustPeriod)) && ((List)adjustToEntityForForm.get(adjustPeriod)).contains(entity)) continue;
                        infos.add(info);
                        continue;
                    }
                    infos.add(info);
                }
            }
        } else if (WorkFlowType.ENTITY.equals((Object)flowType)) {
            if (!CollectionUtils.isEmpty(notNeedImportFormMaps)) {
                for (String s : notNeedImportFormMaps.keySet()) {
                    if (!StringUtils.hasText(s)) continue;
                    entityDimension.addAll(notNeedImportFormMaps.get(s));
                    break;
                }
            }
            List entityList = entityDimension.stream().map(a -> a.getValue(dimensionName).toString()).collect(Collectors.toList());
            for (int i = 0; i < parseArray.length(); ++i) {
                Map sourceDimensionSet;
                DimensionValue entityValue;
                String entity;
                FormulaCheckDesInfo info;
                if (i == 0 || (info = this.deserializeValue(parseArray.getJSONArray(i), formSchemeKey, taskKey)) == null || entityList.contains(entity = (entityValue = (DimensionValue)(sourceDimensionSet = info.getDimensionSet()).get(dimensionName)).getValue())) continue;
                infos.add(info);
            }
        } else {
            String formKey;
            HashMap formToEntity = new HashMap();
            for (Map.Entry<String, List<DimensionValueSet>> stringListEntry : notNeedImportFormMaps.entrySet()) {
                formKey = stringListEntry.getKey();
                List entitys = stringListEntry.getValue().stream().map(a -> a.getValue(dimensionName).toString()).collect(Collectors.toList());
                formToEntity.put(formKey, entitys);
            }
            for (int i = 0; i < parseArray.length(); ++i) {
                FormulaCheckDesInfo info;
                if (i == 0 || (info = this.deserializeValue(parseArray.getJSONArray(i), formSchemeKey, taskKey)) == null) continue;
                formKey = info.getFormKey();
                Map sourceDimensionSet = info.getDimensionSet();
                DimensionValue entityValue = (DimensionValue)sourceDimensionSet.get(dimensionName);
                String entity = entityValue.getValue();
                if (!CollectionUtils.isEmpty((Collection)formToEntity.get(formKey)) && ((List)formToEntity.get(formKey)).contains(entity)) continue;
                infos.add(info);
            }
        }
        return infos;
    }

    private String[] serializeHeader() {
        String[] header = new String[]{"desKey", "taskKey", "formSchemeKey", "formKey", "formulaKey", "formulaCode", "globRow", "globCol", "floatId", "dimension", "userId", "updateTime", "userName", "description", "userTitle", "formulaSchemeKey"};
        return header;
    }

    private Object[] serializeValue(FormulaCheckDesInfo info) {
        Object[] values = new Object[16];
        values[this.getDesKeyIndex()] = info.getDesKey();
        values[this.getTaskKeyIndex()] = info.getTaskKey();
        values[this.getFormSchemeKeyIndex()] = info.getFormSchemeKey();
        values[this.getFormulaSchemeKey()] = info.getFormulaSchemeKey();
        values[this.getFormKeyIndex()] = info.getFormKey();
        values[this.getFormulaKeyIndex()] = info.getFormulaKey();
        values[this.getFormulaCodeIndex()] = info.getFormulaCode();
        values[this.getGlobRowIndex()] = info.getGlobRow();
        values[this.getGlobColIndex()] = info.getGlobCol();
        values[this.getFloatIdIndex()] = info.getFloatId();
        values[this.getDimensionIndex()] = this.serializeDimension(info.getDimensionSet());
        DescriptionInfo descriptionInfo = info.getDescriptionInfo();
        if (descriptionInfo != null) {
            values[this.getUserIdIndex()] = descriptionInfo.getUserId();
            Date from = Date.from(descriptionInfo.getUpdateTime());
            String time = this.sdf.format(from);
            values[this.getUpdateTimeIndex()] = time;
            values[this.getUserNameIndex()] = descriptionInfo.getUserName();
            values[this.getDescriptionIndex()] = descriptionInfo.getDescription();
            values[this.getUserTitleIndex()] = descriptionInfo.getUserTitle();
        }
        return values;
    }

    private FormulaCheckDesInfo deserializeValue(JSONArray jsonArray) {
        FormulaCheckDesInfo info = new FormulaCheckDesInfo();
        try {
            String formSchemeKey = jsonArray.getString(this.getFormSchemeKeyIndex());
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
            String taskKey = runTimeViewController.getFormScheme(formSchemeKey).getTaskKey();
            info.setTaskKey(taskKey);
            info.setFormulaSchemeKey(jsonArray.getString(this.getFormulaSchemeKey()));
            info.setFormSchemeKey(jsonArray.getString(this.getFormSchemeKeyIndex()));
            info.setFormKey(jsonArray.getString(this.getFormKeyIndex()));
            info.setFormulaKey(jsonArray.getString(this.getFormulaKeyIndex()));
            info.setFormulaCode(jsonArray.getString(this.getFormulaCodeIndex()));
            info.setGlobRow(jsonArray.getInt(this.getGlobRowIndex()));
            info.setGlobCol(jsonArray.getInt(this.getGlobColIndex()));
            info.setFloatId(jsonArray.getString(this.getFloatIdIndex()));
            info.setDimensionSet(this.deserializeDimension(jsonArray.getJSONArray(this.getDimensionIndex())));
            DescriptionInfo desc = new DescriptionInfo();
            desc.setUserId(jsonArray.getString(this.getUserIdIndex()));
            String time = jsonArray.getString(this.getUpdateTimeIndex());
            try {
                Date parse = this.sdf.parse(time);
                desc.setUpdateTime(parse.toInstant());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            desc.setUserName(jsonArray.getString(this.getUserNameIndex()));
            desc.setDescription(jsonArray.getString(this.getDescriptionIndex()));
            desc.setUserTitle(jsonArray.getString(this.getUserTitleIndex()));
            info.setDescriptionInfo(desc);
        }
        catch (Exception e) {
            return null;
        }
        return info;
    }

    private FormulaCheckDesInfo deserializeValue(JSONArray jsonArray, String formSchemeKey, String taskKey) {
        FormulaCheckDesInfo info = new FormulaCheckDesInfo();
        try {
            info.setTaskKey(taskKey);
            info.setFormulaSchemeKey(jsonArray.getString(this.getFormulaSchemeKey()));
            info.setFormSchemeKey(formSchemeKey);
            info.setFormKey(jsonArray.getString(this.getFormKeyIndex()));
            info.setFormulaKey(jsonArray.getString(this.getFormulaKeyIndex()));
            info.setFormulaCode(jsonArray.getString(this.getFormulaCodeIndex()));
            info.setGlobRow(jsonArray.getInt(this.getGlobRowIndex()));
            info.setGlobCol(jsonArray.getInt(this.getGlobColIndex()));
            info.setFloatId(jsonArray.getString(this.getFloatIdIndex()));
            info.setDimensionSet(this.deserializeDimension(jsonArray.getJSONArray(this.getDimensionIndex())));
            DescriptionInfo desc = new DescriptionInfo();
            desc.setUserId(jsonArray.getString(this.getUserIdIndex()));
            String time = jsonArray.getString(this.getUpdateTimeIndex());
            try {
                Date parse = this.sdf.parse(time);
                desc.setUpdateTime(parse.toInstant());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            desc.setDescription(jsonArray.getString(this.getDescriptionIndex()));
            desc.setUserTitle(jsonArray.getString(this.getUserTitleIndex()));
            info.setDescriptionInfo(desc);
        }
        catch (Exception e) {
            return null;
        }
        return info;
    }

    private List<List<String>> serializeDimension(Map<String, DimensionValue> dimensionSet) {
        ArrayList<List<String>> dimension = new ArrayList<List<String>>();
        List sortedDimensionNames = dimensionSet.keySet().stream().filter(e -> !"VERSIONID".equals(e)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        ArrayList<String> values = new ArrayList<String>(sortedDimensionNames.size());
        for (String sortedDimensionName : sortedDimensionNames) {
            String value = dimensionSet.get(sortedDimensionName).getValue();
            values.add(value);
        }
        dimension.add(sortedDimensionNames);
        dimension.add(values);
        return dimension;
    }

    private Map<String, DimensionValue> deserializeDimension(JSONArray dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        if (!dimensionValueSet.isEmpty()) {
            JSONArray dimensionNameArray = dimensionValueSet.getJSONArray(0);
            JSONArray dimensionValueArray = dimensionValueSet.getJSONArray(1);
            for (int i = 0; i < dimensionNameArray.length(); ++i) {
                String dimensionName = dimensionNameArray.getString(i);
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionName);
                dimensionValue.setValue(dimensionValueArray.getString(i));
                dimensionSet.put(dimensionName, dimensionValue);
            }
        }
        return dimensionSet;
    }

    private int getDesKeyIndex() {
        return 0;
    }

    private int getTaskKeyIndex() {
        return 1;
    }

    private int getFormSchemeKeyIndex() {
        return 2;
    }

    private int getFormKeyIndex() {
        return 3;
    }

    private int getFormulaKeyIndex() {
        return 4;
    }

    private int getFormulaCodeIndex() {
        return 5;
    }

    private int getGlobRowIndex() {
        return 6;
    }

    private int getGlobColIndex() {
        return 7;
    }

    private int getFloatIdIndex() {
        return 8;
    }

    private int getDimensionIndex() {
        return 9;
    }

    private int getUserIdIndex() {
        return 10;
    }

    private int getUpdateTimeIndex() {
        return 11;
    }

    private int getUserNameIndex() {
        return 12;
    }

    private int getDescriptionIndex() {
        return 13;
    }

    private int getUserTitleIndex() {
        return 14;
    }

    private int getFormulaSchemeKey() {
        return 15;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MappingParam {
    Map<String, String> formulaSchemeTitleToKey = new HashMap<String, String>();
    Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings = new HashMap<String, Map<String, List<FormulaMapping>>>();
    Map<String, Map<String, FormulaMapping>> formulaSchemeToFormulaMappings = new HashMap<String, Map<String, FormulaMapping>>();
    Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = new HashMap<String, Map<String, List<TransmissionZBMapping>>>();
    Map<String, Map<String, TransmissionZBMapping>> dataTableToZBMappings = new HashMap<String, Map<String, TransmissionZBMapping>>();
    Map<String, PeriodMapping> periodToMappings = new HashMap<String, PeriodMapping>();
    Map<String, OrgMapping> orgToMappings = new HashMap<String, OrgMapping>();
    Map<String, BaseDataMapping> baseToMappings = new HashMap<String, BaseDataMapping>();
    Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings = new HashMap<String, Map<String, BaseDataItemMapping>>();

    public MappingParam() {
    }

    public MappingParam(Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings, Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings, Map<String, PeriodMapping> periodToMappings, Map<String, OrgMapping> orgToMappings, Map<String, BaseDataMapping> baseToMappings, Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings) {
        this.formulaSchemeToFormToFormulaMappings = formulaSchemeToFormToFormulaMappings;
        this.formToDataTableToZBMappings = formToDataTableToZBMappings;
        this.periodToMappings = periodToMappings;
        this.orgToMappings = orgToMappings;
        this.baseToMappings = baseToMappings;
        this.baseToDataDataToMappings = baseToDataDataToMappings;
    }

    public Map<String, String> getFormulaSchemeTitleToKey() {
        return this.formulaSchemeTitleToKey;
    }

    public void setFormulaSchemeTitleToKey(Map<String, String> formulaSchemeTitleToKey) {
        this.formulaSchemeTitleToKey = formulaSchemeTitleToKey;
    }

    public Map<String, Map<String, List<FormulaMapping>>> getFormulaSchemeToFormToFormulaMappings() {
        return this.formulaSchemeToFormToFormulaMappings;
    }

    public void setFormulaSchemeToFormToFormulaMappings(Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings) {
        this.formulaSchemeToFormToFormulaMappings = formulaSchemeToFormToFormulaMappings;
        for (Map.Entry<String, Map<String, List<FormulaMapping>>> stringMapEntry : this.formulaSchemeToFormToFormulaMappings.entrySet()) {
            HashMap<String, FormulaMapping> formulaMappings = new HashMap<String, FormulaMapping>();
            Map<String, List<FormulaMapping>> formToFormulaMappings = stringMapEntry.getValue();
            for (Map.Entry<String, List<FormulaMapping>> stringListEntry : formToFormulaMappings.entrySet()) {
                List<FormulaMapping> formulaMappingForForm = stringListEntry.getValue();
                Map<String, FormulaMapping> collect = formulaMappingForForm.stream().collect(Collectors.toMap(FormulaMapping::getFormulaCode, a -> a, (k1, k2) -> k1));
                formulaMappings.putAll(collect);
            }
            this.formulaSchemeToFormulaMappings.put(stringMapEntry.getKey(), formulaMappings);
        }
    }

    public Map<String, Map<String, FormulaMapping>> getFormulaSchemeToFormulaMappings() {
        return this.formulaSchemeToFormulaMappings;
    }

    public void setFormulaSchemeToFormulaMappings(Map<String, Map<String, FormulaMapping>> formulaSchemeToFormulaMappings) {
        this.formulaSchemeToFormulaMappings = formulaSchemeToFormulaMappings;
    }

    public Map<String, Map<String, List<TransmissionZBMapping>>> getFormToDataTableToZBMappings() {
        return this.formToDataTableToZBMappings;
    }

    public void setFormToDataTableToZBMappings(Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings) {
        this.formToDataTableToZBMappings = formToDataTableToZBMappings;
        for (Map.Entry<String, Map<String, List<TransmissionZBMapping>>> stringMapEntry : this.formToDataTableToZBMappings.entrySet()) {
            Map<String, List<TransmissionZBMapping>> dataTableToZBMappingForForm = stringMapEntry.getValue();
            for (Map.Entry<String, List<TransmissionZBMapping>> stringListEntry : dataTableToZBMappingForForm.entrySet()) {
                String dataTableCode = stringListEntry.getKey();
                List<TransmissionZBMapping> dataFieldCodes = stringListEntry.getValue();
                Map<String, TransmissionZBMapping> zbMappingForTable = dataFieldCodes.stream().collect(Collectors.toMap(TransmissionZBMapping::getZbCode, a -> a, (k1, k2) -> k1));
                this.dataTableToZBMappings.computeIfAbsent(dataTableCode, key -> new HashMap()).putAll(zbMappingForTable);
            }
        }
    }

    public Map<String, Map<String, TransmissionZBMapping>> getDataTableToZBMappings() {
        return this.dataTableToZBMappings;
    }

    public void setDataTableToZBMappings(Map<String, Map<String, TransmissionZBMapping>> dataTableToZBMappings) {
        this.dataTableToZBMappings = dataTableToZBMappings;
    }

    public Map<String, PeriodMapping> getPeriodToMappings() {
        return this.periodToMappings;
    }

    public void setPeriodToMappings(Map<String, PeriodMapping> periodToMappings) {
        this.periodToMappings = periodToMappings;
    }

    public Map<String, OrgMapping> getOrgToMappings() {
        return this.orgToMappings;
    }

    public void setOrgToMappings(Map<String, OrgMapping> orgToMappings) {
        this.orgToMappings = orgToMappings;
    }

    public Map<String, BaseDataMapping> getBaseToMappings() {
        return this.baseToMappings;
    }

    public void setBaseToMappings(Map<String, BaseDataMapping> baseToMappings) {
        this.baseToMappings = baseToMappings;
    }

    public Map<String, Map<String, BaseDataItemMapping>> getBaseToDataDataToMappings() {
        return this.baseToDataDataToMappings;
    }

    public void setBaseToDataDataToMappings(Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings) {
        this.baseToDataDataToMappings = baseToDataDataToMappings;
    }

    public String getFormulaSchemeKeyByTitle(String formulaSchemeTitle) {
        return this.formulaSchemeTitleToKey.get(formulaSchemeTitle);
    }

    public String getPeriodMapByCode(String srcPeriod) {
        PeriodMapping periodMapping;
        String result = srcPeriod;
        if (!CollectionUtils.isEmpty(this.periodToMappings) && (periodMapping = this.periodToMappings.get(srcPeriod)) != null) {
            result = StringUtils.hasText(periodMapping.getMapping()) ? periodMapping.getMapping() : result;
        }
        return result;
    }

    public Map<String, String> getFormulaMappingByFormCodes(String formulaSchemeKey, String formCode, List<String> formulaCodes) {
        List<FormulaMapping> formulaMappings;
        Map<String, String> result = formulaCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        Map<String, List<FormulaMapping>> formToFormulaMappings = this.formulaSchemeToFormToFormulaMappings.get(formulaSchemeKey);
        if (!CollectionUtils.isEmpty(formToFormulaMappings) && !CollectionUtils.isEmpty(formulaMappings = formToFormulaMappings.get(formCode))) {
            Map<String, String> formulaMaps = formulaMappings.stream().collect(Collectors.toMap(FormulaMapping::getFormulaCode, FormulaMapping::getmFormulaCode, (k1, k2) -> k1));
            for (String formulaCode : formulaCodes) {
                String formulaMapping = formulaMaps.get(formulaCode);
                if (!StringUtils.hasLength(formulaMapping)) continue;
                result.put(formulaCode, formulaMapping);
            }
        }
        return result;
    }

    public String getFormulaMappingByFormCode(String formulaSchemeKey, String formCode, String formulaCode) {
        List<FormulaMapping> formulaMappings;
        String result = formulaCode;
        Map<String, List<FormulaMapping>> formToFormulaMappings = this.formulaSchemeToFormToFormulaMappings.get(formulaSchemeKey);
        if (!CollectionUtils.isEmpty(formToFormulaMappings) && !CollectionUtils.isEmpty(formulaMappings = formToFormulaMappings.get(formCode))) {
            for (FormulaMapping formulaMapping : formulaMappings) {
                if (!formulaCode.equals(formulaMapping.getFormulaCode())) continue;
                result = formulaMapping.getmFormulaCode();
                break;
            }
        }
        return result;
    }

    public String getFormulaMappingByFormulaCode(String formulaSchemeKey, String formulaCode) {
        FormulaMapping formulaMapping;
        String result = formulaCode;
        Map<String, FormulaMapping> formulaMappings = this.formulaSchemeToFormulaMappings.get(formulaSchemeKey);
        if (!CollectionUtils.isEmpty(formulaMappings) && (formulaMapping = formulaMappings.get(formulaCode)) != null) {
            result = StringUtils.hasText(formulaMapping.getmFormulaCode()) ? formulaMapping.getmFormulaCode() : formulaCode;
        }
        return result;
    }

    public Map<String, String> getFormulaMappingByFormulaCodes(String formulaSchemeKey, List<String> formulaCodes) {
        Map<String, String> result = formulaCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        Map<String, FormulaMapping> formulaMappings = this.formulaSchemeToFormulaMappings.get(formulaSchemeKey);
        if (!CollectionUtils.isEmpty(formulaMappings)) {
            for (String formulaCode : formulaCodes) {
                FormulaMapping formulaMapping = formulaMappings.get(formulaCode);
                if (formulaMapping == null) continue;
                result.put(formulaCode, StringUtils.hasText(formulaMapping.getmFormulaCode()) ? formulaMapping.getmFormulaCode() : formulaCode);
            }
        }
        return result;
    }

    public Map<String, String> getFieldMappingForDataTable(String tableCode, List<String> fieldCodes) {
        Map<String, TransmissionZBMapping> stringZBMappingMap;
        Map<String, String> result = fieldCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.dataTableToZBMappings) && (stringZBMappingMap = this.dataTableToZBMappings.get(tableCode)) != null) {
            for (String fieldCode : fieldCodes) {
                TransmissionZBMapping zbMapping = stringZBMappingMap.get(fieldCode);
                if (zbMapping == null) continue;
                result.put(fieldCode, zbMapping.getMapping());
            }
        }
        return result;
    }

    public Map<String, String> getFieldMappingForFormAndDataTable(String formCode, String tableCode, List<String> fieldCodes) {
        List<TransmissionZBMapping> zbMappings;
        Map<String, List<TransmissionZBMapping>> tableToFields;
        Map<String, String> result = fieldCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.formToDataTableToZBMappings) && (tableToFields = this.formToDataTableToZBMappings.get(formCode)) != null && (zbMappings = tableToFields.get(tableCode)) != null) {
            Map<String, String> zbMappingForTable = zbMappings.stream().collect(Collectors.toMap(TransmissionZBMapping::getZbCode, TransmissionZBMapping::getMapping, (k1, k2) -> k1));
            for (String fieldCode : fieldCodes) {
                String mappingValue = zbMappingForTable.get(fieldCode);
                if (!StringUtils.hasLength(mappingValue)) continue;
                result.put(fieldCode, mappingValue);
            }
        }
        return result;
    }

    public String getOrgMapByCode(String orgCode) {
        OrgMapping orgMapping;
        String result = orgCode;
        if (!CollectionUtils.isEmpty(this.orgToMappings) && (orgMapping = this.orgToMappings.get(orgCode)) != null) {
            result = StringUtils.hasText(orgMapping.getMapping()) ? orgMapping.getMapping() : result;
        }
        return result;
    }

    public String getBaseToMappings(String baseCode) {
        BaseDataMapping baseDataMapping;
        String result = baseCode;
        if (!CollectionUtils.isEmpty(this.baseToMappings) && (baseDataMapping = this.baseToMappings.get(baseCode)) != null) {
            result = StringUtils.hasText(baseDataMapping.getMappingCode()) ? baseDataMapping.getMappingCode() : result;
        }
        return result;
    }

    public Map<String, String> getBaseToDataMappingByBaseAndDatas(String baseCode, List<String> baseDataCodes) {
        Map<String, BaseDataItemMapping> stringBaseDataItemMappingMap;
        Map<String, String> result = baseDataCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.baseToDataDataToMappings) && !CollectionUtils.isEmpty(stringBaseDataItemMappingMap = this.baseToDataDataToMappings.get(baseCode))) {
            for (String baseDataCode : baseDataCodes) {
                BaseDataItemMapping baseDataItemMapping = stringBaseDataItemMappingMap.get(baseDataCode);
                if (baseDataItemMapping == null) continue;
                result.put(baseDataCode, StringUtils.hasText(baseDataItemMapping.getMappingCode()) ? baseDataItemMapping.getMappingCode() : baseDataCode);
            }
        }
        return result;
    }

    public String getBaseToDataMappingByBaseAndData(String baseCode, String baseDataCode) {
        BaseDataItemMapping baseDataItemMapping;
        Map<String, BaseDataItemMapping> stringBaseDataItemMappingMap;
        String result = baseDataCode;
        if (!CollectionUtils.isEmpty(this.baseToDataDataToMappings) && !CollectionUtils.isEmpty(stringBaseDataItemMappingMap = this.baseToDataDataToMappings.get(baseCode)) && (baseDataItemMapping = stringBaseDataItemMappingMap.get(baseDataCode)) != null) {
            result = StringUtils.hasText(baseDataItemMapping.getMappingCode()) ? baseDataItemMapping.getMappingCode() : baseDataCode;
        }
        return result;
    }
}


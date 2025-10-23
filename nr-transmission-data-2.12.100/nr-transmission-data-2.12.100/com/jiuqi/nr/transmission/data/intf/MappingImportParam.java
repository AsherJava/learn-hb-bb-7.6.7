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

public class MappingImportParam {
    Map<String, String> formulaSchemeTitleToKey = new HashMap<String, String>();
    Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings = new HashMap<String, Map<String, List<FormulaMapping>>>();
    Map<String, Map<String, FormulaMapping>> formulaSchemeToFormulaMappings = new HashMap<String, Map<String, FormulaMapping>>();
    Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = new HashMap<String, Map<String, List<TransmissionZBMapping>>>();
    Map<String, Map<String, TransmissionZBMapping>> dataTableToZBMappings = new HashMap<String, Map<String, TransmissionZBMapping>>();
    Map<String, PeriodMapping> periodToMappings = new HashMap<String, PeriodMapping>();
    Map<String, OrgMapping> orgToMappings = new HashMap<String, OrgMapping>();
    Map<String, BaseDataMapping> baseToMappings = new HashMap<String, BaseDataMapping>();
    Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings = new HashMap<String, Map<String, BaseDataItemMapping>>();

    public MappingImportParam() {
    }

    public MappingImportParam(Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings, Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings, Map<String, PeriodMapping> periodToMappings, Map<String, OrgMapping> orgToMappings, Map<String, BaseDataMapping> baseToMappings, Map<String, Map<String, BaseDataItemMapping>> baseToDataDataToMappings) {
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
                Map<String, FormulaMapping> collect = formulaMappingForForm.stream().collect(Collectors.toMap(FormulaMapping::getmFormulaCode, a -> a, (k1, k2) -> k1));
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
        for (Map.Entry<String, Map<String, List<TransmissionZBMapping>>> formMapEntry : this.formToDataTableToZBMappings.entrySet()) {
            Map<String, List<TransmissionZBMapping>> dataTableToZBMappingForForm = formMapEntry.getValue();
            for (Map.Entry<String, List<TransmissionZBMapping>> stringListEntry : dataTableToZBMappingForForm.entrySet()) {
                String srcDataTableCode = stringListEntry.getKey();
                List<TransmissionZBMapping> dataFieldCodes = stringListEntry.getValue();
                Map<String, TransmissionZBMapping> zbMappingForTable = dataFieldCodes.stream().collect(Collectors.toMap(TransmissionZBMapping::getZbCode, a -> a, (k1, k2) -> k1));
                this.dataTableToZBMappings.computeIfAbsent(srcDataTableCode, key -> new HashMap()).putAll(zbMappingForTable);
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
            result = StringUtils.hasText(periodMapping.getPeriod()) ? periodMapping.getPeriod() : result;
        }
        return result;
    }

    public Map<String, String> getFormulaMappingByFormCodes(String srcFormulaSchemeKey, String srcFormCode, List<String> srcFormulaCodes) {
        List<FormulaMapping> formulaMappings;
        Map<String, String> result = srcFormulaCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        Map<String, List<FormulaMapping>> formToFormulaMappings = this.formulaSchemeToFormToFormulaMappings.get(srcFormulaSchemeKey);
        if (!CollectionUtils.isEmpty(formToFormulaMappings) && !CollectionUtils.isEmpty(formulaMappings = formToFormulaMappings.get(srcFormCode))) {
            Map<String, String> formulaMaps = formulaMappings.stream().collect(Collectors.toMap(FormulaMapping::getmFormulaCode, FormulaMapping::getFormulaCode, (k1, k2) -> k1));
            for (String srcFormulaCode : srcFormulaCodes) {
                String desFormulaCode = formulaMaps.get(srcFormulaCode);
                if (desFormulaCode == null) continue;
                result.put(srcFormulaCode, desFormulaCode);
            }
        }
        return result;
    }

    public String getFormulaMappingByFormCode(String srcFormulaSchemeKey, String srcFormCode, String srcFormulaCode) {
        List<FormulaMapping> formulaMappings;
        String result = srcFormulaCode;
        Map<String, List<FormulaMapping>> formToFormulaMappings = this.formulaSchemeToFormToFormulaMappings.get(srcFormulaSchemeKey);
        if (!CollectionUtils.isEmpty(formToFormulaMappings) && !CollectionUtils.isEmpty(formulaMappings = formToFormulaMappings.get(srcFormCode))) {
            for (FormulaMapping formulaMapping : formulaMappings) {
                if (!srcFormulaCode.equals(formulaMapping.getmFormulaCode())) continue;
                result = formulaMapping.getFormulaCode();
                break;
            }
        }
        return result;
    }

    public String getFormulaMappingByFormulaCode(String srcFormulaSchemeKey, String srcFormulaCode) {
        FormulaMapping formulaMapping;
        String result = srcFormulaCode;
        Map<String, FormulaMapping> formulaMappings = this.formulaSchemeToFormulaMappings.get(srcFormulaSchemeKey);
        if (!CollectionUtils.isEmpty(formulaMappings) && (formulaMapping = formulaMappings.get(srcFormulaCode)) != null) {
            result = StringUtils.hasText(formulaMapping.getFormulaCode()) ? formulaMapping.getFormulaCode() : srcFormulaCode;
        }
        return result;
    }

    public Map<String, String> getFormulaMappingByFormulaCodes(String srcFormulaSchemeKey, List<String> srcFormulaCodes) {
        Map<String, String> result = srcFormulaCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        Map<String, FormulaMapping> formulaMappings = this.formulaSchemeToFormulaMappings.get(srcFormulaSchemeKey);
        if (!CollectionUtils.isEmpty(formulaMappings)) {
            for (String srcFormulaCode : srcFormulaCodes) {
                FormulaMapping formulaMapping = formulaMappings.get(srcFormulaCode);
                if (formulaMapping == null) continue;
                result.put(srcFormulaCode, StringUtils.hasText(formulaMapping.getFormulaCode()) ? formulaMapping.getFormulaCode() : srcFormulaCode);
            }
        }
        return result;
    }

    public Map<String, String> getFieldMappingForDataTable(String srcTableCode, List<String> srcFieldCodes) {
        Map<String, TransmissionZBMapping> stringZBMappingMap;
        Map<String, String> result = srcFieldCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.dataTableToZBMappings) && (stringZBMappingMap = this.dataTableToZBMappings.get(srcTableCode)) != null) {
            for (String srcFieldCode : srcFieldCodes) {
                TransmissionZBMapping zbMapping = stringZBMappingMap.get(srcFieldCode);
                if (zbMapping == null) continue;
                result.put(srcFieldCode, zbMapping.getMapping());
            }
        }
        return result;
    }

    public Map<String, String> getFieldMappingForFormAndDataTable(String srcFormCode, String srcTableCode, List<String> srcFieldCodes) {
        List<TransmissionZBMapping> zbMappings;
        Map<String, List<TransmissionZBMapping>> tableToFields;
        Map<String, String> result = srcFieldCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.formToDataTableToZBMappings) && (tableToFields = this.formToDataTableToZBMappings.get(srcFormCode)) != null && (zbMappings = tableToFields.get(srcTableCode)) != null) {
            Map<String, String> zbMappingForTable = zbMappings.stream().collect(Collectors.toMap(TransmissionZBMapping::getZbCode, TransmissionZBMapping::getMapping, (k1, k2) -> k1));
            for (String srcFieldCode : srcFieldCodes) {
                String mappingValue = zbMappingForTable.get(srcFieldCode);
                if (!StringUtils.hasLength(mappingValue)) continue;
                result.put(srcFieldCode, mappingValue);
            }
        }
        return result;
    }

    public String getOrgToMappings(String orgCode) {
        OrgMapping orgMapping;
        String result = orgCode;
        if (!CollectionUtils.isEmpty(this.orgToMappings) && (orgMapping = this.orgToMappings.get(orgCode)) != null) {
            result = StringUtils.hasText(orgMapping.getCode()) ? orgMapping.getCode() : result;
        }
        return result;
    }

    public String getBaseToMappings(String srcBaseCode) {
        BaseDataMapping baseDataMapping;
        String result = srcBaseCode;
        if (!CollectionUtils.isEmpty(this.baseToMappings) && (baseDataMapping = this.baseToMappings.get(srcBaseCode)) != null) {
            result = StringUtils.hasText(baseDataMapping.getBaseDataCode()) ? baseDataMapping.getBaseDataCode() : result;
        }
        return result;
    }

    public Map<String, String> getBaseToDataMappingByBaseAndDatas(String srcBaseCode, List<String> srcBaseDataCodes) {
        Map<String, BaseDataItemMapping> stringBaseDataItemMappingMap;
        Map<String, String> result = srcBaseDataCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        if (!CollectionUtils.isEmpty(this.baseToDataDataToMappings) && !CollectionUtils.isEmpty(stringBaseDataItemMappingMap = this.baseToDataDataToMappings.get(srcBaseCode))) {
            for (String srcBaseDataCode : srcBaseDataCodes) {
                BaseDataItemMapping baseDataItemMapping = stringBaseDataItemMappingMap.get(srcBaseDataCode);
                if (baseDataItemMapping == null) continue;
                result.put(srcBaseDataCode, StringUtils.hasText(baseDataItemMapping.getBaseDataItemCode()) ? baseDataItemMapping.getBaseDataItemCode() : srcBaseDataCode);
            }
        }
        return result;
    }

    public String getBaseToDataMappingByBaseAndData(String srcBaseCode, String srcBaseDataCode) {
        BaseDataItemMapping baseDataItemMapping;
        Map<String, BaseDataItemMapping> stringBaseDataItemMappingMap;
        String result = srcBaseDataCode;
        if (!CollectionUtils.isEmpty(this.baseToDataDataToMappings) && !CollectionUtils.isEmpty(stringBaseDataItemMappingMap = this.baseToDataDataToMappings.get(srcBaseCode)) && (baseDataItemMapping = stringBaseDataItemMappingMap.get(srcBaseDataCode)) != null) {
            result = StringUtils.hasText(baseDataItemMapping.getBaseDataItemCode()) ? baseDataItemMapping.getBaseDataItemCode() : srcBaseDataCode;
        }
        return result;
    }
}


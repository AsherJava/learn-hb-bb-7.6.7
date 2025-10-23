/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 */
package com.jiuqi.nr.transmission.data.internal.mapping;

import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class INrdMappingServiceImpl {
    String mappingSchemeKey;
    Map<String, OrgMapping> orgCodeMappings;
    Map<String, PeriodMapping> periodCodeMappings;
    Map<String, BaseDataMapping> baseDataDefineCodeMapping;
    Map<String, List<BaseDataItemMapping>> baseCodeToDataMappings;
    Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings;
    Map<String, Map<String, List<ZBMapping>>> formToTableToZBMappings;

    public INrdMappingServiceImpl(String mappingSchemeKey) {
    }

    public INrdMappingServiceImpl(String mappingSchemeKey, Map<String, OrgMapping> orgCodeMappings, Map<String, PeriodMapping> periodCodeMappings, Map<String, BaseDataMapping> baseDataDefineCodeMapping, Map<String, List<BaseDataItemMapping>> baseCodeToDataMappings, Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings, Map<String, Map<String, List<ZBMapping>>> formToTableToZBMappings) {
        this.mappingSchemeKey = mappingSchemeKey;
        this.orgCodeMappings = orgCodeMappings;
        this.periodCodeMappings = periodCodeMappings;
        this.baseDataDefineCodeMapping = baseDataDefineCodeMapping;
        this.baseCodeToDataMappings = baseCodeToDataMappings;
        this.formulaSchemeToFormToFormulaMappings = formulaSchemeToFormToFormulaMappings;
        this.formToTableToZBMappings = formToTableToZBMappings;
    }

    OrgMapping getCodeMapping(String srcCode) {
        return this.orgCodeMappings.get(srcCode);
    }

    OrgMapping getOrgCodeMapping(String srcOrgCode) {
        return null;
    }

    PeriodMapping getPeriodMapping(String srcPeriod) {
        return this.periodCodeMappings.get(srcPeriod);
    }

    BaseDataMapping getBaseMapping(String srcBase) {
        return this.baseDataDefineCodeMapping.get(srcBase);
    }

    Map<String, BaseDataItemMapping> getBaseDataItemMapping(String srcBase) {
        List<BaseDataItemMapping> baseDataItemMappingList = this.baseCodeToDataMappings.get(srcBase);
        if (!CollectionUtils.isEmpty(baseDataItemMappingList)) {
            return baseDataItemMappingList.stream().collect(Collectors.toMap(BaseDataItemMapping::getBaseDataItemCode, a -> a, (k1, k2) -> k1));
        }
        return null;
    }

    Map<String, ZBMapping> getFormZBMapping(String srcFormCode) {
        return null;
    }

    Map<String, Map<String, FormulaMapping>> getFormulaMapping(String srcFormulaScheme) {
        Map<String, List<FormulaMapping>> formToFormulaMap = this.formulaSchemeToFormToFormulaMappings.get(srcFormulaScheme);
        return null;
    }

    String getXxxKeyMapping(String srcXxxKey) {
        return null;
    }

    public INrdMappingServiceImpl() {
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public Map<String, Map<String, List<FormulaMapping>>> getFormulaSchemeToFormToFormulaMappings() {
        return this.formulaSchemeToFormToFormulaMappings;
    }

    public void setFormulaSchemeToFormToFormulaMappings(Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings) {
        this.formulaSchemeToFormToFormulaMappings = formulaSchemeToFormToFormulaMappings;
    }

    public Map<String, Map<String, List<ZBMapping>>> getFormToTableToZBMappings() {
        return this.formToTableToZBMappings;
    }

    public void setFormToTableToZBMappings(Map<String, Map<String, List<ZBMapping>>> formToTableToZBMappings) {
        this.formToTableToZBMappings = formToTableToZBMappings;
    }

    public Map<String, PeriodMapping> getPeriodCodeMappings() {
        return this.periodCodeMappings;
    }

    public void setPeriodCodeMappings(Map<String, PeriodMapping> periodCodeMappings) {
        this.periodCodeMappings = periodCodeMappings;
    }

    public Map<String, BaseDataMapping> getBaseDataDefineCodeMapping() {
        return this.baseDataDefineCodeMapping;
    }

    public void setBaseDataDefineCodeMapping(Map<String, BaseDataMapping> baseDataDefineCodeMapping) {
        this.baseDataDefineCodeMapping = baseDataDefineCodeMapping;
    }

    public Map<String, List<BaseDataItemMapping>> getBaseCodeToDataMappings() {
        return this.baseCodeToDataMappings;
    }

    public void setBaseCodeToDataMappings(Map<String, List<BaseDataItemMapping>> baseCodeToDataMappings) {
        this.baseCodeToDataMappings = baseCodeToDataMappings;
    }

    public Map<String, OrgMapping> getOrgCodeMappings() {
        return this.orgCodeMappings;
    }

    public void setOrgCodeMappings(Map<String, OrgMapping> orgCodeMappings) {
        this.orgCodeMappings = orgCodeMappings;
    }
}


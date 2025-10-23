/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FormulaMp
 */
package com.jiuqi.nr.nrdx.data.external;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FormulaMp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NrdxParamsMapping
implements ParamsMapping {
    private final Map<String, String> periodMappingMap = new HashMap<String, String>();
    private final Map<String, String> orgCodeMappingMap = new HashMap<String, String>();
    private final Map<String, String> formCodeMappingMap = new HashMap<String, String>();
    private final Map<String, String> formKeyMappingMap = new HashMap<String, String>();
    private final Map<String, String> formSchemeCodeMappingMap = new HashMap<String, String>();
    private final Map<String, String> formSchemeKeyMappingMap = new HashMap<String, String>();
    private final Map<String, String> taskKeyMappingMap = new HashMap<String, String>();
    private final Map<String, String> taskCodeMappingMap = new HashMap<String, String>();
    private final Map<String, Map<String, String>> baseDataMappingMap = new HashMap<String, Map<String, String>>();
    private final Map<String, Map<String, DataFieldMp>> dataFieldMappingMap = new HashMap<String, Map<String, DataFieldMp>>();
    private final Map<String, Map<String, Map<String, FormulaMp>>> formulaMappingMap = new HashMap<String, Map<String, Map<String, FormulaMp>>>();

    public boolean tryPeriodMap() {
        return !this.periodMappingMap.isEmpty();
    }

    public Map<String, String> getOriginPeriod(List<String> dataTimes) {
        if (!this.tryPeriodMap() || dataTimes.isEmpty()) {
            return this.listToMap(dataTimes);
        }
        return this.getListDataFromMap(this.periodMappingMap, dataTimes);
    }

    public String getOriginPeriod(String dataTime) {
        return Optional.ofNullable(this.periodMappingMap.get(dataTime)).orElse(dataTime);
    }

    public boolean tryOrgCodeMap() {
        return !this.orgCodeMappingMap.isEmpty();
    }

    public Map<String, String> getOriginOrgCode(List<String> dwCodes) {
        if (!this.tryOrgCodeMap() || dwCodes.isEmpty()) {
            return this.listToMap(dwCodes);
        }
        return this.getListDataFromMap(this.orgCodeMappingMap, dwCodes);
    }

    public String getOriginOrgCode(String dwCode) {
        return Optional.ofNullable(this.orgCodeMappingMap.get(dwCode)).orElse(dwCode);
    }

    public boolean tryFormCodeMap() {
        return !this.formCodeMappingMap.isEmpty();
    }

    public Map<String, String> getOriginFormCode(List<String> formCodes) {
        if (!this.tryFormCodeMap() || formCodes.isEmpty()) {
            return this.listToMap(formCodes);
        }
        return this.getListDataFromMap(this.formCodeMappingMap, formCodes);
    }

    public boolean tryFormKeyMap() {
        return !this.formKeyMappingMap.isEmpty();
    }

    public Map<String, String> getOriginFormKey(List<String> formKeys) {
        if (!this.tryFormKeyMap() || formKeys.isEmpty()) {
            return this.listToMap(formKeys);
        }
        return this.getListDataFromMap(this.formKeyMappingMap, formKeys);
    }

    public boolean tryFormSchemeCodeMap() {
        return !this.formSchemeCodeMappingMap.isEmpty();
    }

    public String getOriginFormSchemeCode(String formSchemeCode) {
        return Optional.ofNullable(this.formSchemeCodeMappingMap.get(formSchemeCode)).orElse(formSchemeCode);
    }

    public boolean tryFormSchemeKeyMap() {
        return !this.formSchemeKeyMappingMap.isEmpty();
    }

    public String getOriginFormSchemeKey(String formSchemeKey) {
        return Optional.ofNullable(this.formSchemeKeyMappingMap.get(formSchemeKey)).orElse(formSchemeKey);
    }

    public boolean tryTaskKeyMap() {
        return !this.taskKeyMappingMap.isEmpty();
    }

    public String getOriginTaskKey(String taskKey) {
        return Optional.ofNullable(this.taskKeyMappingMap.get(taskKey)).orElse(taskKey);
    }

    public boolean tryTaskCodeMap() {
        return !this.taskCodeMappingMap.isEmpty();
    }

    public String getOriginTaskCode(String taskCode) {
        return Optional.ofNullable(this.taskCodeMappingMap.get(taskCode)).orElse(taskCode);
    }

    public boolean tryFormulaSchemeCodeMap() {
        return false;
    }

    public Map<String, String> getOriginFormulaScheme(List<String> formulaSchemes) {
        return this.listToMap(formulaSchemes);
    }

    public boolean tryBaseDataMap(String entityId) {
        return Optional.ofNullable(this.baseDataMappingMap.get(entityId)).map(m -> !m.isEmpty()).orElse(false);
    }

    public Map<String, String> getOriginBaseData(String entityId, List<String> baseData) {
        if (!this.tryBaseDataMap(entityId) || baseData.isEmpty()) {
            return this.listToMap(baseData);
        }
        return this.getListDataFromMap(this.baseDataMappingMap.get(entityId), baseData);
    }

    public boolean tryFormulaMap(String formulaScheme) {
        return false;
    }

    public Map<String, String> getOriginFormula(String formulaScheme, List<String> formulaCodes) {
        return this.listToMap(formulaCodes);
    }

    public boolean tryDataFieldMap(String dataTableCode) {
        Map<String, DataFieldMp> dataFieldMapping = this.dataFieldMappingMap.get(dataTableCode);
        return dataFieldMapping != null && !dataFieldMapping.isEmpty();
    }

    public Map<String, DataFieldMp> getOriginDataFieldCode(String dataTableCode, List<String> dataFieldCodes) {
        Map<String, DataFieldMp> dataFieldMapping = this.dataFieldMappingMap.get(dataTableCode);
        if (dataFieldMapping == null || dataFieldMapping.isEmpty() || dataFieldCodes.isEmpty()) {
            HashMap<String, DataFieldMp> returnMap = new HashMap<String, DataFieldMp>();
            dataFieldCodes.forEach(e -> {
                DataFieldMp dataFieldMp = new DataFieldMp();
                dataFieldMp.setCode(e);
                dataFieldMp.setTableCode(dataTableCode);
                returnMap.put((String)e, dataFieldMp);
            });
            return returnMap;
        }
        HashSet<String> dataFieldCodeSet = new HashSet<String>(dataFieldCodes);
        HashMap<String, DataFieldMp> resultMap = new HashMap<String, DataFieldMp>(dataFieldCodeSet.size());
        for (String dataFieldCode : dataFieldCodes) {
            DataFieldMp dataFieldMp = dataFieldMapping.get(dataFieldCode);
            if (dataFieldMp == null) {
                dataFieldMp = new DataFieldMp();
                dataFieldMp.setCode(dataFieldCode);
                dataFieldMp.setTableCode(dataTableCode);
                resultMap.put(dataFieldCode, dataFieldMp);
                continue;
            }
            resultMap.put(dataFieldCode, dataFieldMp);
        }
        return resultMap;
    }

    public FormulaMp getFormula(FormulaMp formula) {
        Map<String, Map<String, FormulaMp>> formFormulaMapping = this.formulaMappingMap.get(formula.getFormulaSchemeTitle());
        if (formFormulaMapping == null || formFormulaMapping.isEmpty()) {
            return formula;
        }
        Map<String, FormulaMp> formulaMapping = formFormulaMapping.get(formula.getFormCode());
        if (formulaMapping == null || formulaMapping.isEmpty()) {
            return formula;
        }
        return Optional.ofNullable(formulaMapping.get(formula.getCode())).orElse(formula);
    }

    public Map<String, String> getPeriodMappingMap() {
        return this.periodMappingMap;
    }

    public Map<String, String> getOrgCodeMappingMap() {
        return this.orgCodeMappingMap;
    }

    public Map<String, String> getFormCodeMappingMap() {
        return this.formCodeMappingMap;
    }

    public Map<String, String> getFormKeyMappingMap() {
        return this.formKeyMappingMap;
    }

    public Map<String, String> getFormSchemeCodeMappingMap() {
        return this.formSchemeCodeMappingMap;
    }

    public Map<String, String> getFormSchemeKeyMappingMap() {
        return this.formSchemeKeyMappingMap;
    }

    public Map<String, String> getTaskKeyMappingMap() {
        return this.taskKeyMappingMap;
    }

    public Map<String, String> getTaskCodeMappingMap() {
        return this.taskCodeMappingMap;
    }

    public Map<String, Map<String, String>> getBaseDataMappingMap() {
        return this.baseDataMappingMap;
    }

    public Map<String, Map<String, DataFieldMp>> getDataFieldMappingMap() {
        return this.dataFieldMappingMap;
    }

    public Map<String, Map<String, Map<String, FormulaMp>>> getFormulaMappingMap() {
        return this.formulaMappingMap;
    }

    private Map<String, String> getListDataFromMap(Map<String, String> allData, List<String> queryData) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        queryData.forEach(e -> resultMap.put((String)e, Optional.ofNullable(allData.get(e)).orElse(e)));
        return resultMap;
    }

    private Map<String, String> listToMap(List<String> queryData) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        queryData.forEach(e -> resultMap.put((String)e, (String)e));
        return resultMap;
    }
}


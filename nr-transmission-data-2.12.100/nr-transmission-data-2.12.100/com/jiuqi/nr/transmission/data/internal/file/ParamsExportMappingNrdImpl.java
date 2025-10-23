/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ParamsExportMappingNrdImpl
implements ParamsMapping {
    MappingParam mappingParam;

    public ParamsExportMappingNrdImpl() {
    }

    public ParamsExportMappingNrdImpl(MappingParam mappingParam) {
        this.mappingParam = mappingParam;
    }

    public boolean tryPeriodMap() {
        Map<String, PeriodMapping> periodToMappings = this.mappingParam.getPeriodToMappings();
        return !CollectionUtils.isEmpty(periodToMappings);
    }

    public Map<String, String> getOriginPeriod(List<String> dataTimes) {
        HashMap<String, String> result = new HashMap<String, String>(dataTimes.size());
        for (String dataTime : dataTimes) {
            result.put(dataTime, this.mappingParam.getPeriodMapByCode(dataTime));
        }
        return result;
    }

    public boolean tryOrgCodeMap() {
        Map<String, OrgMapping> periodToMappings = this.mappingParam.getOrgToMappings();
        return !CollectionUtils.isEmpty(periodToMappings);
    }

    public Map<String, String> getOriginOrgCode(List<String> dwCodes) {
        HashMap<String, String> result = new HashMap<String, String>(dwCodes.size());
        for (String dwCode : dwCodes) {
            result.put(dwCode, this.mappingParam.getOrgMapByCode(dwCode));
        }
        return result;
    }

    public boolean tryFormCodeMap() {
        return true;
    }

    public Map<String, String> getOriginFormCode(List<String> formCodes) {
        Map<String, String> result = formCodes.stream().collect(Collectors.toMap(a -> a, a -> a, (k1, k2) -> k1));
        return result;
    }

    public boolean tryFormSchemeCodeMap() {
        return false;
    }

    public String getOriginFormSchemeCode(String formSchemeCode) {
        return formSchemeCode;
    }

    public boolean tryTaskCodeMap() {
        return true;
    }

    public String getOriginTaskCode(String taskCode) {
        return taskCode;
    }

    public boolean tryFormulaSchemeCodeMap() {
        return false;
    }

    public Map<String, String> getOriginFormulaScheme(List<String> formulaSchemes) {
        return null;
    }

    public boolean tryBaseDataMap(String entityId) {
        Map<String, BaseDataMapping> baseToMappings = this.mappingParam.getBaseToMappings();
        return !CollectionUtils.isEmpty(baseToMappings);
    }

    public Map<String, String> getOriginBaseData(String entityId, List<String> baseData) {
        String thisEntityId = entityId;
        int aiTeIndex = entityId.indexOf("@");
        if (aiTeIndex > -1) {
            thisEntityId = entityId.substring(0, aiTeIndex);
        }
        return this.mappingParam.getBaseToDataMappingByBaseAndDatas(thisEntityId, baseData);
    }

    public boolean tryFormulaMap(String formulaScheme) {
        Map<String, Map<String, List<FormulaMapping>>> formulaSchemeToFormToFormulaMappings = this.mappingParam.getFormulaSchemeToFormToFormulaMappings();
        return !CollectionUtils.isEmpty(formulaSchemeToFormToFormulaMappings);
    }

    public Map<String, String> getOriginFormula(String formulaScheme, List<String> formulaCodes) {
        return this.mappingParam.getFormulaMappingByFormulaCodes(formulaScheme, formulaCodes);
    }

    public boolean tryDataFieldMap(String dataTableCode) {
        Map<String, Map<String, List<TransmissionZBMapping>>> formToDataTableToZBMappings = this.mappingParam.getFormToDataTableToZBMappings();
        return !CollectionUtils.isEmpty(formToDataTableToZBMappings);
    }

    public Map<String, DataFieldMp> getOriginDataFieldCode(String srcTataTableCode, List<String> srcDataFieldCodes) {
        HashMap<String, DataFieldMp> result = new HashMap<String, DataFieldMp>();
        Map<String, TransmissionZBMapping> stringZBMappingMap = this.mappingParam.getDataTableToZBMappings().get(srcTataTableCode);
        if (stringZBMappingMap != null) {
            for (String dataFieldCode : srcDataFieldCodes) {
                TransmissionZBMapping zbMapping = stringZBMappingMap.get(dataFieldCode);
                if (zbMapping == null || !StringUtils.hasText(zbMapping.getMapping())) continue;
                DataFieldMp dataFieldMp = new DataFieldMp();
                dataFieldMp.setTableCode(zbMapping.getDesTable());
                dataFieldMp.setCode(zbMapping.getMapping());
                result.put(dataFieldCode, dataFieldMp);
            }
        }
        return result;
    }
}


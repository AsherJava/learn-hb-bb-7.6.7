/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.checkdes.api.ICKDParamMapping
 *  com.jiuqi.nr.data.checkdes.facade.obj.FieldMappingObj
 *  com.jiuqi.nr.data.checkdes.facade.obj.FormulaMappingObj
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.facade.obj.FieldMappingObj;
import com.jiuqi.nr.data.checkdes.facade.obj.FormulaMappingObj;
import com.jiuqi.nr.transmission.data.intf.MappingImportParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import java.util.Map;
import org.springframework.util.StringUtils;

public class CKDParamImportMappingNrdImpl
implements ICKDParamMapping {
    MappingImportParam mappingParam;

    public CKDParamImportMappingNrdImpl() {
    }

    public CKDParamImportMappingNrdImpl(MappingImportParam mappingParam) {
        this.mappingParam = mappingParam;
    }

    public String getMDCode(String srcMdCode) {
        return this.mappingParam.getOrgToMappings(srcMdCode);
    }

    public String getPeriod(String srcPeriod) {
        return this.mappingParam.getPeriodMapByCode(srcPeriod);
    }

    public String getEntityId(String srcEntityId) {
        String result;
        int aiTeIndex = srcEntityId.indexOf("@");
        String thisSrcEntityId = srcEntityId;
        if (aiTeIndex > -1) {
            thisSrcEntityId = srcEntityId.substring(0, aiTeIndex);
        }
        if (StringUtils.hasLength(result = this.mappingParam.getBaseToMappings(thisSrcEntityId)) && aiTeIndex > -1) {
            result = result + srcEntityId.substring(aiTeIndex);
        }
        return result;
    }

    public String getEntityData(String srcEntityId, String srcEntityData) {
        int aiTeIndex = srcEntityId.indexOf("@");
        if (aiTeIndex > -1) {
            srcEntityId = srcEntityId.substring(0, aiTeIndex);
        }
        return this.mappingParam.getBaseToDataMappingByBaseAndData(srcEntityId, srcEntityData);
    }

    public FormulaMappingObj getFormula(FormulaMappingObj formula) {
        String formulaSchemeKey = this.mappingParam.getFormulaSchemeKeyByTitle(formula.getFormulaSchemeTitle());
        if (StringUtils.hasLength(formulaSchemeKey)) {
            String formulaMappingValue = this.mappingParam.getFormulaMappingByFormCode(formulaSchemeKey, formula.getFormCode(), formula.getCode());
            formula.setCode(formulaMappingValue);
        }
        return formula;
    }

    public FieldMappingObj getField(FieldMappingObj field) {
        TransmissionZBMapping zbMapping;
        Map<String, Map<String, TransmissionZBMapping>> dataTableToZBMappings = this.mappingParam.getDataTableToZBMappings();
        Map<String, TransmissionZBMapping> stringZBMappingMap = dataTableToZBMappings.get(field.getDataTableCode());
        if (stringZBMappingMap != null && (zbMapping = stringZBMappingMap.get(field.getDataFieldCode())) != null) {
            field.setDataFieldCode(zbMapping.getMapping());
            field.setDataTableCode(zbMapping.getDesTable());
        }
        return field;
    }
}


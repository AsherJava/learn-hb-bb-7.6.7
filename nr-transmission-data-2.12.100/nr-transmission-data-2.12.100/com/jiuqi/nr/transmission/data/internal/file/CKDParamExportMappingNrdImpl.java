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
import com.jiuqi.nr.transmission.data.intf.MappingParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionZBMapping;
import java.util.Map;
import org.springframework.util.StringUtils;

public class CKDParamExportMappingNrdImpl
implements ICKDParamMapping {
    MappingParam mappingParam;

    public CKDParamExportMappingNrdImpl() {
    }

    public CKDParamExportMappingNrdImpl(MappingParam mappingParam) {
        this.mappingParam = mappingParam;
    }

    public MappingParam getMappingParam() {
        return this.mappingParam;
    }

    public void setMappingParam(MappingParam mappingParam) {
        this.mappingParam = mappingParam;
    }

    public String getMDCode(String mdCode) {
        return this.mappingParam.getOrgMapByCode(mdCode);
    }

    public String getPeriod(String period) {
        return this.mappingParam.getPeriodMapByCode(period);
    }

    public String getEntityId(String entityId) {
        String result;
        int aiTeIndex = entityId.indexOf("@");
        String thisEntityId = entityId;
        if (aiTeIndex > -1) {
            thisEntityId = entityId.substring(0, aiTeIndex);
        }
        if (StringUtils.hasLength(result = this.mappingParam.getBaseToMappings(thisEntityId)) && aiTeIndex > -1) {
            result = result + entityId.substring(aiTeIndex);
        }
        return result;
    }

    public String getEntityData(String entityId, String entityData) {
        int aiTeIndex = entityId.indexOf("@");
        if (aiTeIndex > -1) {
            entityId = entityId.substring(0, aiTeIndex);
        }
        return this.mappingParam.getBaseToDataMappingByBaseAndData(entityId, entityData);
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


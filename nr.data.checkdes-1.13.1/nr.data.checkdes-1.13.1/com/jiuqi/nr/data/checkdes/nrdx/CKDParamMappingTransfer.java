/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FormulaMp
 */
package com.jiuqi.nr.data.checkdes.nrdx;

import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.facade.obj.FieldMappingObj;
import com.jiuqi.nr.data.checkdes.facade.obj.FormulaMappingObj;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FormulaMp;
import java.util.Collections;
import java.util.Map;

public class CKDParamMappingTransfer
implements ICKDParamMapping {
    private final ParamsMapping paramsMapping;

    public CKDParamMappingTransfer(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }

    @Override
    public String getMDCode(String mdCode) {
        if (this.paramsMapping.tryOrgCodeMap()) {
            Map originOrgCode = this.paramsMapping.getOriginOrgCode(Collections.singletonList(mdCode));
            return (String)originOrgCode.get(mdCode);
        }
        return null;
    }

    @Override
    public String getPeriod(String period) {
        if (this.paramsMapping.tryPeriodMap()) {
            Map originPeriod = this.paramsMapping.getOriginPeriod(Collections.singletonList(period));
            return (String)originPeriod.get(period);
        }
        return null;
    }

    @Override
    public String getEntityId(String entityId) {
        return null;
    }

    @Override
    public String getEntityData(String entityId, String entityData) {
        if (this.paramsMapping.tryBaseDataMap(entityId)) {
            Map originBaseData = this.paramsMapping.getOriginBaseData(entityId, Collections.singletonList(entityData));
            return (String)originBaseData.get(entityData);
        }
        return null;
    }

    @Override
    public FormulaMappingObj getFormula(FormulaMappingObj formula) {
        FormulaMp formulaMp = new FormulaMp(formula.getCode(), formula.getFormCode(), formula.getFormulaSchemeTitle());
        FormulaMp mappedFml = this.paramsMapping.getFormula(formulaMp);
        if (mappedFml != null) {
            return new FormulaMappingObj(mappedFml.getCode(), mappedFml.getFormulaSchemeTitle(), mappedFml.getFormCode(), formula.getGlobRow(), formula.getGlobCol());
        }
        return null;
    }

    @Override
    public FieldMappingObj getField(FieldMappingObj field) {
        Map originDataFieldCode;
        DataFieldMp dataFieldMp;
        if (this.paramsMapping.tryDataFieldMap(field.getDataTableCode()) && (dataFieldMp = (DataFieldMp)(originDataFieldCode = this.paramsMapping.getOriginDataFieldCode(field.getDataTableCode(), Collections.singletonList(field.getDataFieldCode()))).get(field.getDataTableCode())) != null) {
            return new FieldMappingObj(dataFieldMp.getCode(), dataFieldMp.getTableCode());
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import java.util.Map;

@Deprecated
@JsonInclude(value=JsonInclude.Include.NON_NULL)
class CustomFetchExecuteSettingVO
extends ExecuteSettingVO {
    private Map<String, CustomCondition> customConditionMap;
    private SelectField selectField;
    private CustomBizModelDTO bizModel;

    public Map<String, CustomCondition> getCustomConditionMap() {
        return this.customConditionMap;
    }

    public void setCustomConditionMap(Map<String, CustomCondition> customConditionMap) {
        this.customConditionMap = customConditionMap;
    }

    public SelectField getSelectField() {
        return this.selectField;
    }

    public void setSelectField(SelectField selectField) {
        this.selectField = selectField;
    }

    public CustomBizModelDTO getBizModel() {
        return this.bizModel;
    }

    public void setBizModel(CustomBizModelDTO bizModel) {
        this.bizModel = bizModel;
    }

    public String toString() {
        return "CustomFetchExecuteSettingVO [customConditionMap=" + this.customConditionMap + ", selectField=" + this.selectField + ", bizModel=" + this.bizModel + "]";
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class CustomFetchExecuteSetting
extends ExecuteSettingVO {
    private SelectField selectField;
    private Map<String, CustomCondition> customConditionMap;

    public SelectField getSelectField() {
        return this.selectField;
    }

    public void setSelectField(SelectField selectField) {
        this.selectField = selectField;
    }

    public Map<String, CustomCondition> getCustomConditionMap() {
        return this.customConditionMap;
    }

    public void setCustomConditionMap(Map<String, CustomCondition> customConditionMap) {
        this.customConditionMap = customConditionMap;
    }

    public String toString() {
        return "CustomFetchExecuteSetting [selectField=" + this.selectField + ", customConditionMap=" + this.customConditionMap + ", toString()=" + super.toString() + "]";
    }
}


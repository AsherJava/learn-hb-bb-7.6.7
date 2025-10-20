/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.enums;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.Map;

public enum FetchAutomationParameterEnum {
    USER("user", "\u7528\u6237\u540d", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    UNITCODE("unitCode", "\u62a5\u8868\u5355\u4f4d\u4ee3\u7801", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            String unitCode = (String)this.getParameterValue(context);
            if (!StringUtils.isEmpty((String)unitCode)) {
                unitCode = unitCode.replace(",", ";");
            }
            return unitCode;
        }
    }
    ,
    TASKKEY("taskKey", "\u62a5\u8868\u4efb\u52a1\u6807\u8bc6", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    SCHEMEKEY("schemeKey", "\u62a5\u8868\u65b9\u6848\u6807\u8bc6", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    DATATIME("dataTime", "\u65f6\u671f", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    FORMKEY("formKey", "\u62a5\u8868\u6807\u8bc6", AutomationParameterDataTypeEnum.STRING, null, false){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            String formKey = (String)this.getParameterValue(context);
            if (!StringUtils.isEmpty((String)formKey)) {
                formKey = formKey.replace(",", ";");
            }
            return formKey;
        }
    }
    ,
    CURRENCY("currency", "\u5e01\u79cd\u4ee3\u7801", AutomationParameterDataTypeEnum.STRING, null, false){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    ORGTYPE("orgType", "\u5355\u4f4d\u7c7b\u578b", AutomationParameterDataTypeEnum.STRING, null, true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    INCLUDEUNCHARGED("includeUncharged", "\u662f\u5426\u5305\u542b\u672a\u8bb0\u8d26\u51ed\u8bc1", AutomationParameterDataTypeEnum.BOOLEAN, "true", true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    ACCOUNT("account", "\u662f\u5426\u4f7f\u7528\u8c03\u6574\u8d26\u671f", AutomationParameterDataTypeEnum.BOOLEAN, "false", false){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            return this.getParameterValue(context);
        }
    }
    ,
    DIMENSIONSET("dimensionSet", "\u60c5\u666f(\u9884\u7559\u53c2\u6570)", AutomationParameterDataTypeEnum.STRING, "{}", true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            String valStr = (String)this.getParameterValue(context);
            return JsonUtils.readValue((String)valStr, (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        }
    }
    ,
    VARIABLEMAP("variableMap", "\u53d8\u91cf(\u9884\u7559\u53c2\u6570)", AutomationParameterDataTypeEnum.STRING, "{\"writeable\":true,\"DATA_STATE_CHECK_EDIT\":\"\" }", true){

        @Override
        public Object parseVal(ExecuteContext context) throws AutomationException {
            String valStr = (String)this.getParameterValue(context);
            return JsonUtils.readValue((String)valStr, (TypeReference)new TypeReference<Map<String, Object>>(){});
        }
    };

    private final String name;
    private final String title;
    private final AutomationParameterDataTypeEnum dataType;
    private final String defaultValue;
    private final boolean required;

    private FetchAutomationParameterEnum(String name, String title, AutomationParameterDataTypeEnum dataType, String defaultValue, boolean required) {
        this.name = name;
        this.title = title;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public AutomationParameterDataTypeEnum getDataType() {
        return this.dataType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public boolean isRequired() {
        return this.required;
    }

    public abstract Object parseVal(ExecuteContext var1) throws AutomationException;

    Object getParameterValue(ExecuteContext context) throws AutomationException {
        if (AutomationParameterDataTypeEnum.BOOLEAN == this.getDataType()) {
            return context.getParameterValueAsBoolean(this.getName());
        }
        String value = context.getParameterValueAsString(this.getName());
        if (!this.isRequired()) {
            return value;
        }
        if (StringUtils.isEmpty((String)value)) {
            throw new IllegalArgumentException(String.format("\u53c2\u6570%1$s\u3010%2$s\u3011\u4e0d\u80fd\u4e3a\u7a7a", this.getTitle(), this.getName()));
        }
        return value;
    }
}


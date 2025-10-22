/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.TableRelationField;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="EntityExtraQueryInfo", description="\u4e3b\u4f53\u6570\u636e\u6269\u5c55\u67e5\u8be2\u53c2\u6570")
public class RelEntityExtraQueryInfo {
    @ApiModelProperty(value="\u8868\u540d\u5217\u8868", name="tableNames")
    private List<String> tableNames = new ArrayList<String>();
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context")
    private JtableContext context;
    @ApiModelProperty(value="\u67e5\u8be2\u5217\u9650\u5b9a\u503c\uff0ckey\u4e3a\u8868\u540d\uff0cvalue\u4e3a\u8be5\u8868\u540d\u7684\u9650\u5b9a\u503c", name="tableValues")
    private Map<String, String> tableValues = new HashMap<String, String>();
    @ApiModelProperty(value="\u8868\u5173\u8054\u5b57\u6bb5\uff0cparentTableName  childTableName  relationFieldName", name="tableRelationFields")
    private List<TableRelationField> tableRelationFields = new ArrayList<TableRelationField>();
    @ApiModelProperty(value="\u67e5\u8be2\u5217\u8303\u56f4\u503c\uff0ckey\u4e3a\u8868\u540d\uff0cvalue\u4e3a\u8be5\u8868\u540d\u7684\u8303\u56f4\u503c\u5217\u8868", name="tableRangeValues")
    private Map<String, List<String>> tableRangeValues = new HashMap<String, List<String>>();

    public List<String> getTableNames() {
        return this.tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, String> getTableValues() {
        return this.tableValues;
    }

    public void setTableValues(Map<String, String> tableValues) {
        this.tableValues = tableValues;
    }

    public List<TableRelationField> getTableRelationFields() {
        return this.tableRelationFields;
    }

    public void setTableRelationFields(List<TableRelationField> tableRelationFields) {
        this.tableRelationFields = tableRelationFields;
    }

    public Map<String, List<String>> getTableRangeValues() {
        return this.tableRangeValues;
    }

    public void setTableRangeValues(Map<String, List<String>> tableRangeValues) {
        this.tableRangeValues = tableRangeValues;
    }

    public String getFormKey() {
        if (this.context == null) {
            return "";
        }
        return this.context.getFormKey();
    }

    public String getUnitKey() {
        if (this.context == null) {
            return "";
        }
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        EntityViewData unitEntityInfo = jtableParamService.getDwEntity(this.context.getFormSchemeKey());
        Map<String, DimensionValue> dimensionSet = this.context.getDimensionSet();
        if (dimensionSet.containsKey(unitEntityInfo.getDimensionName())) {
            return dimensionSet.get(unitEntityInfo.getDimensionName()).getValue();
        }
        return "";
    }

    public String getPeriod() {
        if (this.context == null) {
            return "";
        }
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        EntityViewData periodEntityInfo = jtableParamService.getDataTimeEntity(this.context.getFormSchemeKey());
        Map<String, DimensionValue> dimensionSet = this.context.getDimensionSet();
        if (dimensionSet.containsKey(periodEntityInfo.getDimensionName())) {
            return dimensionSet.get(periodEntityInfo.getDimensionName()).getValue();
        }
        return "";
    }
}


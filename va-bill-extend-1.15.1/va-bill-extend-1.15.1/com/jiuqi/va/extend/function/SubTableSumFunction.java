/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  org.apache.shiro.util.CollectionUtils
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SubTableSumFunction
extends ModelFunction
implements AggregatedNode {
    private static final Logger logger = LoggerFactory.getLogger(SubTableSumFunction.class);
    private static final long serialVersionUID = 1L;

    public SubTableSumFunction() {
        this.parameters().add(new Parameter("sourceFields", 6, "\u6e90\u5b50\u8868\u7ef4\u5ea6\u6c47\u603b\u53c2\u6570", false));
        this.parameters().add(new Parameter("targetFields", 6, "\u76ee\u6807\u5b50\u8868\u7ef4\u5ea6\u6c47\u603b\u53c2\u6570", false));
        this.parameters().add(new Parameter("triggerFields", 0, "\u89e6\u53d1\u5b57\u6bb5", false));
    }

    public String addDescribe() {
        return "\u5c06\u591a\u4e2a\u5b50\u8868\u5b57\u6bb5\u6839\u636e\u6761\u4ef6\u6c47\u603b\u5230\u76ee\u6807\u5b50\u8868\u4e2d";
    }

    public int[] getAggregatedIndexes(List<IASTNode> parameters) {
        int size = parameters.size();
        if (size <= 2) {
            return null;
        }
        int[] indexes = new int[size - 2];
        for (int i = 2; i < size; ++i) {
            indexes[i - 2] = i;
        }
        return indexes;
    }

    public String name() {
        return "SubTableSummary";
    }

    public String title() {
        return "\u5b50\u8868\u6c47\u603b\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String sourceFields = (String)parameters.get(0).evaluate(context);
        String targetFields = (String)parameters.get(1).evaluate(context);
        ModelDataContext modelDataContext = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)modelDataContext.model;
        if (!StringUtils.hasText((String)targetFields) || !StringUtils.hasText((String)sourceFields)) {
            return false;
        }
        Map<String, Map<String, Object>> srcTableData = this.processSourceTables(model, sourceFields);
        this.handleTargetTable(model, sourceFields, targetFields, srcTableData);
        return true;
    }

    private Map<String, Map<String, Object>> processSourceTables(BillModelImpl model, String sourceFields) {
        String[] srcTables;
        LinkedHashMap<String, Map<String, Object>> srcTableData = new LinkedHashMap<String, Map<String, Object>>();
        for (String srcTable : srcTables = sourceFields.split("#")) {
            String[] parts = srcTable.split("@");
            String tableName = parts[0];
            String[] fieldParts = parts[1].split("&");
            String[] dimFields = fieldParts[0].split(",");
            String[] assignFields = fieldParts[1].split(",");
            DataTable dataTable = model.getTable(tableName);
            NamedContainer fields = dataTable.getFields();
            dataTable.getRows().forEach((index, row) -> {
                DataRowImpl rowImpl = (DataRowImpl)row;
                StringBuilder keyBuilder = this.buildKey(rowImpl, dimFields);
                if (srcTableData.containsKey(keyBuilder.toString())) {
                    this.updateExistingMap((Map)srcTableData.get(keyBuilder.toString()), rowImpl, assignFields, (NamedContainer<? extends DataField>)fields);
                } else {
                    this.createNewMap(srcTableData, keyBuilder, rowImpl, assignFields, (NamedContainer<? extends DataField>)fields);
                }
            });
        }
        return srcTableData;
    }

    private StringBuilder buildKey(DataRowImpl row, String[] dimFields) {
        StringBuilder sb = new StringBuilder();
        for (String dimField : dimFields) {
            String string = row.getString(dimField);
            if (string == null) continue;
            sb.append(string);
        }
        if (!StringUtils.hasText((String)String.valueOf(sb))) {
            sb.append("null-key");
        }
        return sb;
    }

    private void updateExistingMap(Map<String, Object> map, DataRowImpl row, String[] assignFields, NamedContainer<? extends DataField> fields) {
        for (String assignField : assignFields) {
            if (((DataField)fields.get(assignField)).getDefine().isMultiChoice()) {
                this.handleMultiChoice(map, row, assignField);
                continue;
            }
            this.handleSingleValue(map, row, assignField, fields);
        }
    }

    private void handleMultiChoice(Map<String, Object> map, DataRowImpl row, String assignField) {
        Object existing = map.get(assignField);
        if (existing != null) {
            HashSet set = new HashSet();
            set.addAll((List)existing);
            set.addAll(row.getMultiValue(assignField));
            map.put(assignField, new ArrayList(set));
        } else {
            map.put(assignField, row.getMultiValue(assignField));
        }
    }

    private void handleSingleValue(Map<String, Object> map, DataRowImpl row, String assignField, NamedContainer<? extends DataField> fields) {
        Object oldValue = map.get(assignField);
        if (oldValue == null) {
            map.put(assignField, row.getValue(assignField));
            return;
        }
        ValueType valueType = ((DataField)fields.get(assignField)).getDefine().getValueType();
        switch (valueType) {
            case STRING: {
                if (row.getString(assignField) == null) break;
                map.put(assignField, oldValue + "," + row.getString(assignField));
                break;
            }
            case INTEGER: {
                map.put(assignField, (Integer)oldValue + row.getInt(assignField));
                break;
            }
            case LONG: {
                map.put(assignField, (Long)oldValue + row.getLong(assignField));
                break;
            }
            case DOUBLE: {
                map.put(assignField, (Double)oldValue + row.getDouble(assignField));
                break;
            }
            case DECIMAL: {
                BigDecimal bigDecimal = row.getBigDecimal(assignField);
                if (bigDecimal == null) break;
                map.put(assignField, ((BigDecimal)oldValue).add(bigDecimal));
                break;
            }
            default: {
                map.put(assignField, oldValue);
                logger.error("\u4e0d\u652f\u6301\u7684\u5b57\u6bb5\u7c7b\u578b\uff1a{}, {}", (Object)valueType, (Object)assignField);
            }
        }
    }

    private void createNewMap(Map<String, Map<String, Object>> srcTableData, StringBuilder keyBuilder, DataRowImpl row, String[] assignFields, NamedContainer<? extends DataField> fields) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (String assignField : assignFields) {
            if (((DataField)fields.get(assignField)).getDefine().isMultiChoice()) {
                List multiValue = row.getMultiValue(assignField);
                if (CollectionUtils.isEmpty((Collection)multiValue)) continue;
                map.put(assignField, multiValue);
                continue;
            }
            Object value = row.getValue(assignField);
            if (ObjectUtils.isEmpty(value)) continue;
            map.put(assignField, value);
        }
        if (map.isEmpty()) {
            return;
        }
        srcTableData.put(keyBuilder.toString(), map);
    }

    private void handleTargetTable(BillModelImpl model, String sourceFields, String targetFields, Map<String, Map<String, Object>> srcTableData) {
        String[] parts = targetFields.split("@");
        String targetTableName = parts[0];
        String[] fieldParts = parts[1].split("&");
        String[] dimFields = fieldParts[0].split(",");
        String[] assignFields = fieldParts[1].split(",");
        DataTable dataTable = model.getTable(targetTableName);
        String[] sourceSplit = sourceFields.split("&");
        String[] sourceAssignFields = sourceSplit[1].split(",");
        ArrayList removeRows = new ArrayList();
        dataTable.getRows().forEach((index, row) -> {
            DataRowImpl rowImpl = (DataRowImpl)row;
            StringBuilder keyBuilder = this.buildKey(rowImpl, dimFields);
            if (srcTableData.containsKey(keyBuilder.toString())) {
                Map map = (Map)srcTableData.get(keyBuilder.toString());
                for (int i = 0; i < assignFields.length; ++i) {
                    rowImpl.setValue(assignFields[i], map.get(sourceAssignFields[i]));
                }
                srcTableData.remove(keyBuilder.toString());
            } else {
                removeRows.add(row);
            }
        });
        removeRows.forEach(arg_0 -> ((DataTable)dataTable).removeRow(arg_0));
        for (Map.Entry<String, Map<String, Object>> entry : srcTableData.entrySet()) {
            DataRow dataRow = dataTable.appendRow();
            int index2 = 0;
            for (Map.Entry<String, Object> innerEntry : entry.getValue().entrySet()) {
                dataRow.setValue(assignFields[index2], innerEntry.getValue());
                ++index2;
            }
        }
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1) + "\uff1b");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("sourceFields");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u6e90\u8868\u6c47\u603b\u7ef4\u5ea6\u3002\u683c\u5f0f\uff1a\u6e90\u88681@\u7ef4\u5ea6\u5b57\u6bb51,\u7ef4\u5ea6\u5b57\u6bb52&\u6c47\u603b\u5b57\u6bb51,\u6c47\u603b\u5b57\u6bb52#\u6e90\u88682@\u7ef4\u5ea6\u5b57\u6bb51,\u7ef4\u5ea6\u5b57\u6bb52&\u6c47\u603b\u5b57\u6bb51,\u6c47\u603b\u5b57\u6bb52\uff08@\u7b26\u53f7\u524d\u4e3a\u6c47\u603b\u6e90\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u6c47\u603b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b&\u7b26\u53f7\u540e\u9762\u4e3a\u6e90\u8868\u9700\u8981\u6c47\u603b\u5b57\u6bb5\uff0c\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b\u9700\u8981\u4ece\u591a\u5b50\u8868\u6c47\u603b\u6570\u636e\u65f6\u4ee5#\u5206\u9694\u3002");
        parameterDescription.setRequired(Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("targetFields");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u76ee\u6807\u8d4b\u503c\u4fe1\u606f\u3002\u683c\u5f0f\uff1a\u76ee\u6807\u8868@\u7ef4\u5ea6\u5b57\u6bb51,\u7ef4\u5ea6\u5b57\u6bb52&\u6c47\u603b\u5b57\u6bb51,\u6c47\u603b\u5b57\u6bb52\uff08@\u7b26\u53f7\u524d\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b&\u7b26\u53f7\u540e\u9762\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u3002");
        parameterDescription1.setRequired(Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("triggerFields");
        parameterDescription2.setType(DataType.toString((int)0));
        parameterDescription2.setDescription("\u89e6\u53d1\u5b57\u6bb5\uff0c\u5355\u636e\u4e2d\u4efb\u610f\u5b57\u6bb5\uff0c\u89e6\u53d1\u5b57\u6bb5\u53d8\u5316\u65f6\u91cd\u65b0\u8fd0\u7b97\u516c\u5f0f");
        parameterDescription2.setRequired(Boolean.valueOf(false));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<String> notes = new ArrayList<String>();
        notes.add("\u6e90\u8868\u4e3a\u591a\u4e2a\u5b50\u8868\u65f6\uff0c\u591a\u5b50\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e00\u81f4\u3002");
        notes.add("\u6e90\u8868\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6c47\u603b\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e0e\u76ee\u6807\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u8d4b\u503c\u6307\u6807\u5b57\u6bb5\u4e2a\u6570\u4e00\u81f4\u3002");
        notes.add("\u82e5\u60f3\u5728\u89e6\u53d1\u5b57\u6bb5\u6240\u5728\u7684\u8868\u589e\u5220\u884c\u65f6\u540c\u65f6\u91cd\u65b0\u8fd0\u7b97\u516c\u5f0f\uff0c\u9700\u5c06\u516c\u5f0f\u5728\u589e\u5220\u884c\u65f6\u673a\u5904\u589e\u52a0\u4e00\u6761\u76f8\u540c\u516c\u5f0f\u3002");
        notes.add("\u6570\u503c\u578b\u5b57\u6bb5\u6c47\u603b\u540e\u6c42\u548c\uff0c\u5b57\u7b26\u578b\u5b57\u6bb5\u6c47\u603b\u540e\u5c06\u5b57\u7b26\u62fc\u63a5\u6210\u4e00\u4e2a\u5b57\u7b26\u4e32\uff0c\u591a\u9009\u5b57\u6bb5\u6c47\u603b\u540e\u5c06\u591a\u9009\u5b57\u6bb5\u5408\u5e76\u5230\u4e00\u8d77\u3002");
        formulaDescription.setNotes(notes);
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u6e90\u5b50\u8868\u53c2\u80a1\u534f\u8bae\u9644\u4ef6\u5f55\u5165\u5b50\u8868\uff08CG_CGJYFJLRZB\uff09\u6839\u636e\u5e74\u5ea6\u5b57\u6bb5\uff08ND\uff09\u505a\u7ef4\u5ea6\u5c06\u6d89\u53ca\u4e8b\u9879\u5b57\u6bb5\uff08SJSX\uff09\uff08\u591a\u9009\uff09\u548c\u5e74\u5ea6\u5b57\u6bb5\uff08ND\uff09\u6c47\u603b\u5230\u53c2\u80a1\u51b3\u8bae\u5b50\u8868\uff08CG_CGJYZB\uff09\uff0c\u53c2\u80a1\u51b3\u8bae\u5b50\u8868\uff08CG_CGJYZB\uff09\u7ef4\u5ea6\u4e3a\u5e74\u5ea6\u5b57\u6bb5\uff08ND\uff09\uff0c\u6c47\u603b\u5b57\u6bb5\u4e3a\u6d89\u53ca\u4e8b\u9879\u5b57\u6bb5\uff08SJSX\uff09\uff08\u591a\u9009\uff09\u548c\u5e74\u5ea6\u5b57\u6bb5\uff08ND\uff09\uff0c\u4e14\u6e90\u8868\u5e74\u5ea6\u5b57\u6bb5\uff08ND\uff09\u548c\u6d89\u53ca\u4e8b\u9879\u5b57\u6bb5\uff08SJSX\uff09\u53d8\u5316\u65f6\u540c\u6b65\u66f4\u65b0\u516c\u5f0f\u8fd0\u7b97\u7ed3\u679c\u3002");
        formulaExample.setFormula("SubTableSummary('CG_CGJYFJLRZB@ND&SJSX,ND', 'CG_CGJYZB@ND&SJSX,ND', CG_CGJYFJLRZB[ND], CG_CGJYFJLRZB[SJSX])");
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        formulaExample.setReturnValue("true");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}


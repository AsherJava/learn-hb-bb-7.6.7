/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.data.DataEventImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnSetValueCheckMessage;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerDefine;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SetValueAction
extends ActionBase {
    private static final Logger logger = LoggerFactory.getLogger(SetValueAction.class);

    @Override
    public String getName() {
        return "set-value";
    }

    @Override
    public String getTitle() {
        return "\u8bbe\u7f6e\u5b57\u6bb5\u503c";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public boolean isInner() {
        return true;
    }

    @Override
    public boolean before(Model model, ActionRequest request, ActionResponse response) {
        return super.before(model, request, response);
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        DataTable table = this.getTable(model, params);
        int rowIndex = this.getRowIndex(table, params);
        String fieldName = (String)params.get("fieldName");
        DataField dataField = table.getFields().find(fieldName);
        Object value = params.get("value");
        if (((DataFieldDefineImpl)dataField.getDefine()).isBillPenetrate()) {
            return;
        }
        DataRow dataRow = table.getRows().get(rowIndex);
        dataRow.setValueWithCheck(fieldName, value);
        dataRow.setData(Stream.of("$UNSET").collect(Collectors.toMap(o -> o, o -> false)));
        this.handleRelatedFilter(model, dataField, rowIndex);
    }

    private void handleRelatedFilter(Model model, DataField dataField, int rowIndex) {
        DataFieldDefine define = dataField.getDefine();
        String tableName = define.getTable().getName();
        String fieldName = define.getName();
        TypedContainer<Plugin> plugins = model.getPlugins();
        RulerImpl rule = plugins.get(RulerImpl.class);
        Map<String, List<Formula>> fieldFilterMap = rule.getDefine().getFieldFilterMap();
        List<Formula> formulas = fieldFilterMap.get(tableName + "|" + fieldName);
        DataImpl data = plugins.get(DataImpl.class);
        if (formulas == null) {
            return;
        }
        String masterTableName = data.getMasterTable().getName();
        formulas.stream().forEach(o -> {
            DataTableImpl table;
            if (o.getObjectId().equals(define.getId())) {
                return;
            }
            DataFieldDefineImpl field = data.getDefine().getField(o.getObjectId());
            String filterFieldName = field.getName();
            boolean isMuilt = field.getRefTableType() != 0 && field.getRefTableType() != 3 && field.getValueType() == ValueType.IDENTIFY || field.getRefTableType() == 3 && field.isMultiChoiceStore();
            String filterTableName = field.getTable().getName();
            int opt = field.getFilterChangeOpt();
            HashMap<String, ValueType> basedataFieldValeType = new HashMap<String, ValueType>();
            if (opt == 1) {
                IExpression expression = (IExpression)o.getCompiledExpression();
                expression.forEach(node -> {
                    if (node instanceof ModelParamNode) {
                        String name = ((ModelParamNode)((Object)((Object)node))).getName().toUpperCase();
                        ValueType valueType = FormulaUtils.getBaseDataFieldMappingBillFieldType(field.getRefTableName(), name);
                        if (valueType != null) {
                            basedataFieldValeType.put(name, valueType);
                        }
                    }
                });
            }
            if (filterTableName.equals(tableName)) {
                table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(tableName);
                DataRowImpl dataRow = table.getRows().get(rowIndex);
                Object value = isMuilt ? dataRow.getMultiValue(filterFieldName) : dataRow.getValue(filterFieldName);
                if (ObjectUtils.isEmpty(value)) {
                    return;
                }
                if (opt == 0) {
                    dataRow.setValueWithCheck(filterFieldName, null);
                } else if (!this.judge(model, value, isMuilt, table.getName(), field, data, (IExpression)o.getCompiledExpression(), dataRow, basedataFieldValeType)) {
                    dataRow.setValueWithCheck(filterFieldName, null);
                }
                return;
            }
            if (filterTableName.equals(masterTableName)) {
                table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(filterTableName);
                DataRowImpl dataRow = table.getRows().get(0);
                Object value = isMuilt ? dataRow.getMultiValue(filterFieldName) : dataRow.getValue(filterFieldName);
                if (ObjectUtils.isEmpty(value)) {
                    return;
                }
                if (opt == 0) {
                    dataRow.setValueWithCheck(filterFieldName, null);
                } else if (!this.judge(model, value, isMuilt, table.getName(), field, data, (IExpression)o.getCompiledExpression(), dataRow, basedataFieldValeType)) {
                    dataRow.setValueWithCheck(filterFieldName, null);
                }
                return;
            }
            if (tableName.equals(masterTableName)) {
                table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(filterTableName);
                table.getRows().forEach((i, row) -> {
                    Object value = isMuilt ? row.getMultiValue(filterFieldName) : row.getValue(filterFieldName);
                    if (ObjectUtils.isEmpty(value)) {
                        return;
                    }
                    if (opt == 0) {
                        row.setValueWithCheck(filterFieldName, null);
                    } else if (!this.judge(model, value, isMuilt, table.getName(), field, data, (IExpression)o.getCompiledExpression(), (DataRow)row, (Map<String, ValueType>)basedataFieldValeType)) {
                        row.setValueWithCheck(filterFieldName, null);
                    }
                });
                return;
            }
            if (this.isChildren(data.getDefine().getTables(), tableName, filterTableName)) {
                table = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(filterTableName);
                table.getRows().forEach((i, row) -> {
                    Object value = isMuilt ? row.getMultiValue(filterFieldName) : row.getValue(filterFieldName);
                    if (ObjectUtils.isEmpty(value)) {
                        return;
                    }
                    if (opt == 0) {
                        row.setValueWithCheck(filterFieldName, null);
                    } else if (!this.judge(model, value, isMuilt, table.getName(), field, data, (IExpression)o.getCompiledExpression(), (DataRow)row, (Map<String, ValueType>)basedataFieldValeType)) {
                        row.setValueWithCheck(filterFieldName, null);
                    }
                });
            } else {
                DataTableDefineImpl table1 = (DataTableDefineImpl)((DataTableNodeContainerImpl)data.getDefine().getTables()).find(tableName);
                DataTableDefineImpl table2 = (DataTableDefineImpl)((DataTableNodeContainerImpl)data.getDefine().getTables()).find(filterTableName);
                if (!table2.getId().equals(table1.getParentId())) {
                    return;
                }
                DataTableImpl table3 = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(tableName);
                DataRow dataRow = table3.getRows().get(rowIndex);
                dataRow.getGroupId();
                Object groupId = dataRow.getGroupId();
                if (ObjectUtils.isEmpty(groupId)) {
                    return;
                }
                DataTableImpl filterTable = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).get(filterTableName);
                DataRowImpl rowImpl = filterTable.getRows().stream().filter(row -> row.getId().toString().equals(groupId.toString())).findAny().orElse(null);
                Object value = isMuilt ? rowImpl.getMultiValue(filterFieldName) : rowImpl.getValue(filterFieldName);
                if (ObjectUtils.isEmpty(value)) {
                    return;
                }
                if (rowImpl != null) {
                    if (opt == 0) {
                        rowImpl.setValueWithCheck(filterFieldName, null);
                    } else if (!this.judge(model, value, isMuilt, filterTable.getName(), field, data, (IExpression)o.getCompiledExpression(), rowImpl, basedataFieldValeType)) {
                        rowImpl.setValueWithCheck(filterFieldName, null);
                    }
                }
            }
        });
    }

    private boolean judge(Model model, Object value, boolean isMuilt, String tableName, DataFieldDefineImpl fieldDefine, DataImpl data, IExpression expression, DataRow dataRow, Map<String, ValueType> basedataFieldValeType) {
        RefTableDataMap refTableDataMap = model.getRefDataBuffer().getRefTableMap(fieldDefine.getRefTableType(), fieldDefine.getRefTableName(), model.getDimValues(fieldDefine, dataRow));
        if (isMuilt) {
            List<Object> values = value instanceof List ? (List<Object>)value : Arrays.asList(value);
            for (Object e : values) {
                boolean result;
                Map<String, Object> refData = refTableDataMap.find(e.toString());
                HashMap<String, DataRow> rowMap = new HashMap<String, DataRow>();
                rowMap.put(tableName, dataRow);
                FormulaUtils.adjustFormulaRows(data, rowMap);
                ModelDataContext context = new ModelDataContext(model);
                rowMap.forEach((n, v) -> context.put((String)n, v));
                refData.forEach((n, v) -> context.put(n.toUpperCase(), v));
                basedataFieldValeType.forEach((n, v) -> context.setFieldValueType((String)n, (ValueType)((Object)((Object)v))));
                try {
                    result = expression.judge((IContext)context);
                }
                catch (SyntaxException e2) {
                    logger.error(e2.getMessage(), e2);
                    result = false;
                }
                if (result) continue;
                return false;
            }
            return true;
        }
        Map<String, Object> refData = refTableDataMap.find(value.toString());
        HashMap<String, DataRow> rowMap = new HashMap<String, DataRow>();
        rowMap.put(tableName, dataRow);
        FormulaUtils.adjustFormulaRows(data, rowMap);
        ModelDataContext modelDataContext = new ModelDataContext(model);
        rowMap.forEach((n, v) -> context.put((String)n, v));
        refData.forEach((n, v) -> context.put(n.toUpperCase(), v));
        basedataFieldValeType.forEach((n, v) -> context.setFieldValueType((String)n, (ValueType)((Object)((Object)v))));
        try {
            return expression.judge((IContext)modelDataContext);
        }
        catch (SyntaxException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Object executeReturn(Model model, Map<String, Object> params) {
        this.execute(model, params);
        String fieldName = (String)params.get("fieldName");
        DataTable table = this.getTable(model, params);
        DataField field = table.getFields().find(fieldName);
        int rowIndex = this.getRowIndex(table, params);
        DataRow dataRow = table.getRows().get(rowIndex);
        UUID fieldID = table.getFields().find(fieldName).getDefine().getId();
        RulerDefine rulerDefine = model.getDefine().getPlugins().get(RulerDefine.class);
        ArrayList<CheckResult> checkMessages = new ArrayList<CheckResult>();
        rulerDefine.getFormulas().stream().filter(formula -> fieldID.equals(formula.getObjectId()) && formula.isUsed() && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(formula.getPropertyType())).forEach(formula -> {
            FormulaRulerItem formulaRulerItem = new FormulaRulerItem((FormulaImpl)formula);
            ArrayList<DataEventImpl> events = new ArrayList<DataEventImpl>();
            events.add(new DataEventImpl("after-set-value", table, dataRow, field));
            try {
                formulaRulerItem.execute(model, events.stream());
            }
            catch (CheckException e) {
                checkMessages.addAll(e.getCheckMessages());
            }
        });
        if (checkMessages.size() > 0) {
            ActionReturnSetValueCheckMessage returnObject = new ActionReturnSetValueCheckMessage();
            returnObject.setMessages(checkMessages);
            return returnObject;
        }
        return null;
    }

    private boolean isChildren(DataTableNodeContainer<? extends DataTableDefine> tables, String table1, String table2) {
        if (table1.equals(table2)) {
            return true;
        }
        DataTableDefine tableDefine1 = tables.find(table1);
        DataTableDefine tableDefine2 = tables.find(table2);
        if (tableDefine1.getId().equals(tableDefine2.getParentId())) {
            return true;
        }
        tables.stream().forEach(tableDefine -> {
            if (tableDefine.getId().equals(tableDefine2.getParentId())) {
                this.isChildren(tables, table1, tableDefine.getName());
            }
        });
        return false;
    }
}


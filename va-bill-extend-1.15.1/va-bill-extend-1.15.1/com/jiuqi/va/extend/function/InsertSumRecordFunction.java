/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InsertSumRecordFunction
extends ModelFunction {
    private static final long serialVersionUID = 75170184845231922L;
    public static final String SUM = "sum";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String NEGATIVE = "negative";
    public static final String DEFAULT = "default";
    public static final String MULTIPLE = "multiple";
    private final String PARAM_SPLIT = "&@";
    private final String VALUE_MAP_SPLIT = "@&";
    public final String GROUP_SPLIT = "\\|\\|";

    public InsertSumRecordFunction() {
        this.parameters().add(new Parameter("srcFields", 6, "\u6e90\u8868\u7ef4\u5ea6\u6c47\u603b\u53c2\u6570", false));
        this.parameters().add(new Parameter("targetFields", 6, "\u76ee\u6807\u5b50\u8868\u53c2\u6570", false));
        this.parameters().add(new Parameter("delInvalidRowData", 1, "\u5220\u9664\u65e0\u6548\u884c\u6570\u636e", false));
        this.parameters().add(new Parameter("filterField", 6, "\u4e0d\u6c47\u603b\u5b57\u6bb5\uff08boolean\u7c7b\u578b\u5b57\u6bb5\uff09", true));
        this.parameters().add(new Parameter("srcCondition", 6, "\u6e90\u8868\u8fc7\u6ee4\u6761\u4ef6", true));
        this.parameters().add(new Parameter("keepOrder", 1, "\u6309\u7167\u6e90\u8868\u6392\u5e8f\uff0c\u9ed8\u8ba4false", true));
    }

    public String addDescribe() {
        return "\u8868\u793a\u4ece\u591a\u5b50\u8868\u6839\u636e\u7ef4\u5ea6\u5b57\u6bb5\u7684\u503c\u8fdb\u884c\u6307\u6807\u6c47\u603b\uff0c\u628a\u6c47\u603b\u5f97\u5230\u7684\u7ef4\u5ea6\u548c\u6307\u6807\u63d2\u5165\u5230\u76ee\u6807\u8868\u4e2d\uff0c\u591a\u6b21\u6c47\u603b\u65f6\u4f1a\u6839\u636e\u6c47\u603b\u6570\u636e\u4e0e\u76ee\u6807\u8868\u7ef4\u5ea6\u503c\u6bd4\u8f83\uff0c\u5982\u679c\u503c\u76f8\u540c\u5c31\u8986\u76d6\uff0c\u4e0d\u540c\u5219\u65b0\u63d2\u5165\u4e00\u884c\u6570\u636e\u3002";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "InsertSumRecordFunction";
    }

    public String title() {
        return "\u591a\u5b50\u8868\u6c47\u603b\u6570\u636e\u63d2\u5165\u76ee\u6807\u5b50\u8868";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        IASTNode srcFieldsNode = list.get(0);
        String srcFields = (String)srcFieldsNode.evaluate(iContext);
        IASTNode targetFieldsNode = list.get(1);
        String targetFields = (String)targetFieldsNode.evaluate(iContext);
        IASTNode delInvalidRowDataNode = list.get(2);
        boolean delInvalidRowData = (Boolean)delInvalidRowDataNode.evaluate(iContext);
        String filterField = "";
        if (list.size() > 3) {
            IASTNode filterFieldNode = list.get(3);
            filterField = (String)filterFieldNode.evaluate(iContext);
        }
        String srcCondition = list.size() > 4 ? Objects.toString(list.get(4).evaluate(iContext), null) : null;
        boolean keepOrder = list.size() > 5 && Boolean.TRUE.equals(list.get(5).evaluate(iContext));
        LinkedHashMap<String, String[]> paramsMap = new LinkedHashMap<String, String[]>(16);
        if (!StringUtils.hasText(srcFields)) {
            return false;
        }
        if (!StringUtils.hasText(targetFields)) {
            return false;
        }
        String[] srcParam = srcFields.split("#");
        String[] tarParam = targetFields.split("@");
        String[] fields = null;
        String[] tarDimFields = null;
        String[] tarMeasureFields = null;
        String tarTable = tarParam[0];
        if (tarParam.length > 1) {
            fields = tarParam[1].split("&");
        }
        if (fields != null && fields.length > 1) {
            tarDimFields = fields[0].split(",");
            tarMeasureFields = fields[1].split(",");
        }
        BillModel model = (BillModel)((ModelDataContext)iContext).model;
        HashMap<String, NamedContainer<? extends DataFieldDefine>> fieldDefineMap = new HashMap<String, NamedContainer<? extends DataFieldDefine>>();
        for (String param : srcParam) {
            String[] srcDimFields;
            String[] srcField;
            String srcTableName;
            String[] srcPm = param.split("@");
            if (srcPm.length > 1) {
                srcTableName = srcPm[0];
                NamedContainer fieldsDefine = model.getTable(srcTableName).getDefine().getFields();
                fieldDefineMap.put(srcTableName, (NamedContainer<? extends DataFieldDefine>)fieldsDefine);
                srcField = srcPm[1].split("&");
                srcDimFields = srcField[0].split(",");
                assert (tarDimFields != null);
                if (srcDimFields.length != tarDimFields.length) {
                    throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.field.count.not.match"));
                }
            } else {
                throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.field.error.please.check.formula"));
            }
            String srcMeasureField = srcField[1];
            paramsMap.put(srcTableName + "&@" + srcMeasureField, srcDimFields);
        }
        this.fetchInsertData(paramsMap, tarTable, tarDimFields, tarMeasureFields, model, delInvalidRowData, filterField, srcCondition, keepOrder, fieldDefineMap);
        return true;
    }

    private void fetchInsertData(HashMap<String, String[]> paramsMap, String tarTable, String[] tarDimFields, String[] tarMeasureFields, BillModel model, boolean delRowData, String condField, String srcCondition, boolean keepOrder, Map<String, NamedContainer<? extends DataFieldDefine>> fieldDefineMap) {
        LinkedHashMap<String, Map<String, Object>> valueMap = new LinkedHashMap<String, Map<String, Object>>(16);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            String key1 = entry.getKey();
            String[] srcDimFields = entry.getValue();
            String[] param = key1.split("&@");
            String tableName = param[0];
            String[] srcMeasureFields = param[1].split(",");
            NamedContainer<? extends DataFieldDefine> fieldDefine = fieldDefineMap.get(tableName);
            if (model.getTable(tableName) == null) continue;
            ListContainer rows = model.getTable(tableName).getRows();
            AtomicReference jsonRows = new AtomicReference(new ArrayList());
            boolean hasMultiFlag = false;
            int multiFieldCount = 0;
            for (String srcDimField : srcDimFields) {
                GroupTypeResult result = this.getGroupTypeResult(srcDimField);
                if (!MULTIPLE.equalsIgnoreCase(result.groupType)) continue;
                hasMultiFlag = true;
                ++multiFieldCount;
            }
            if (multiFieldCount > 1) {
                throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.support.one.field"));
            }
            boolean finalHasMultiFlag = hasMultiFlag;
            AtomicReference<Object> multiFieldName = new AtomicReference<Object>(null);
            rows.forEach((i, dataRow) -> {
                if (finalHasMultiFlag) {
                    for (String srcDimField : srcDimFields) {
                        GroupTypeResult result = this.getGroupTypeResult(srcDimField);
                        if (!MULTIPLE.equalsIgnoreCase(result.groupType)) continue;
                        multiFieldName.set(result.srcMeasureField);
                        List multiValue = ((DataRowImpl)dataRow).getMultiValue((String)multiFieldName.get());
                        List collect = multiValue.stream().map(value -> {
                            Map data = ((DataRowImpl)dataRow).getData(false);
                            HashMap<Object, String> jsonData = new HashMap<Object, String>();
                            for (String srcField : srcDimFields) {
                                if (srcField.equalsIgnoreCase(multiFieldName + "||" + MULTIPLE)) {
                                    jsonData.put(multiFieldName.get(), (String)value);
                                    continue;
                                }
                                Object o = data.get(srcField);
                                if (o instanceof Map) {
                                    jsonData.put(srcField, (String)((Map)o).get("name"));
                                    continue;
                                }
                                jsonData.put(srcField, (String)data.get(srcField));
                            }
                            for (String srcMeasureField : srcMeasureFields) {
                                if (srcMeasureField.contains("||")) {
                                    srcMeasureField = srcMeasureField.split("\\|\\|")[0];
                                }
                                jsonData.put(srcMeasureField, (String)data.get(srcMeasureField));
                            }
                            jsonData.put("ID", (String)data.get("ID"));
                            return jsonData;
                        }).collect(Collectors.toList());
                        List jsonObjects = (List)jsonRows.get();
                        jsonObjects.addAll(collect);
                    }
                } else {
                    List jsonObjects = (List)jsonRows.get();
                    Map data = ((DataRowImpl)dataRow).getData(false);
                    HashMap jsonData = new HashMap();
                    for (String srcField : srcDimFields) {
                        Object o = data.get(srcField);
                        if (o instanceof Map) {
                            jsonData.put(srcField, ((Map)o).get("name"));
                            continue;
                        }
                        jsonData.put(srcField, data.get(srcField));
                    }
                    for (String srcMeasureField : srcMeasureFields) {
                        if (srcMeasureField.contains("||")) {
                            srcMeasureField = srcMeasureField.split("\\|\\|")[0];
                        }
                        jsonData.put(srcMeasureField, data.get(srcMeasureField));
                    }
                    jsonData.put("ID", data.get("ID"));
                    jsonObjects.add(jsonData);
                }
            });
            ((List)jsonRows.get()).forEach(dataRow -> {
                Optional<DataRow> first;
                boolean fetchFlag = false;
                if (StringUtils.hasText(condField) && (first = rows.stream().filter(row -> row.getString("ID").equals(dataRow.get("ID").toString())).findFirst()).isPresent()) {
                    DataRow row2 = first.get();
                    fetchFlag = row2.getBoolean(condField);
                }
                if (fetchFlag) {
                    return;
                }
                if (srcCondition != null) {
                    DataRow filterFormulaList;
                    for (DataRow formula : filterFormulaList = srcCondition.split("#")) {
                        Boolean result;
                        if (!formula.contains(tableName)) continue;
                        Optional<DataRow> first2 = rows.stream().filter(row -> row.getString("ID").equals(dataRow.get("ID").toString())).findFirst();
                        if (!first2.isPresent()) break;
                        DataRow row3 = first2.get();
                        Map<String, DataRow> rowMap = Stream.of(row3).collect(Collectors.toMap(k -> tableName, v -> v));
                        ModelDataContext context = new ModelDataContext((Model)model);
                        try {
                            IExpression parse = ModelFormulaHandle.getInstance().parse(context, (String)formula, FormulaType.EVALUATE);
                            FormulaUtils.adjustFormulaRows((Data)model.getData(), rowMap);
                            result = (Boolean)FormulaUtils.evaluate((Model)model, (IExpression)parse, rowMap);
                            if (result == null) {
                                result = Boolean.FALSE;
                            }
                            System.out.println(result);
                        }
                        catch (Exception e) {
                            result = true;
                        }
                        if (result.booleanValue()) break;
                        return;
                    }
                }
                int[] count = new int[]{0};
                String key = Arrays.stream(srcDimFields).map(item -> {
                    String fieldName = item.split("\\|\\|")[0];
                    ValueType valueType = ((DataFieldDefine)fieldDefine.find(fieldName)).getValueType();
                    String multiField = (String)multiFieldName.get();
                    String fieldString = multiField != null && fieldName.equalsIgnoreCase(multiField + "||" + MULTIPLE) ? this.getItemData(formatter, (Map<String, Object>)dataRow, multiField, valueType) : this.getItemData(formatter, (Map<String, Object>)dataRow, fieldName, valueType);
                    if (StringUtils.hasText(fieldString)) {
                        return fieldString;
                    }
                    count[0] = count[0] + 1;
                    return "";
                }).collect(Collectors.joining("@&"));
                if (count[0] == srcDimFields.length) {
                    return;
                }
                if (valueMap.isEmpty() || valueMap.get(key) == null) {
                    this.initValueMap(tarMeasureFields, (Map<String, Map<String, Object>>)valueMap, srcMeasureFields, (Map<String, Object>)dataRow, key);
                } else {
                    this.updateValueMap(tarMeasureFields, (Map<String, Map<String, Object>>)valueMap, srcMeasureFields, (Map<String, Object>)dataRow, key, tableName);
                }
            });
        }
        if (!valueMap.isEmpty()) {
            if (keepOrder) {
                this.doInsertDataAndKeepOrder(model, valueMap, tarTable, tarDimFields, delRowData);
            } else {
                this.doInsertData(model, valueMap, tarTable, tarDimFields, delRowData);
            }
        }
    }

    private String getItemData(SimpleDateFormat formatter, Map<String, Object> dataRow, String item, ValueType valueType) {
        Object itemData = dataRow.get(item);
        if (ValueType.DATE.equals((Object)valueType) || ValueType.DATETIME.equals((Object)valueType)) {
            return itemData == null ? null : formatter.format(itemData);
        }
        return itemData == null ? null : itemData.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void updateValueMap(String[] tarMeasureFields, Map<String, Map<String, Object>> valueMap, String[] srcMeasureFields, Map<String, Object> dataRow, String key, String tableName) {
        Map<String, Object> map = valueMap.get(key);
        for (int index = 0; index < tarMeasureFields.length; ++index) {
            Number number;
            Object currValue;
            Number newValue;
            String tarMeasureField = tarMeasureFields[index];
            GroupTypeResult result = this.getGroupTypeResult(srcMeasureFields[index]);
            Object oldValue = map.get(tarMeasureField);
            if (SUM.equalsIgnoreCase(result.groupType)) {
                newValue = (Number)dataRow.get(result.srcMeasureField);
                if (oldValue == null) {
                    oldValue = 0;
                }
                if (newValue == null) {
                    newValue = 0;
                }
                currValue = ((Number)oldValue).doubleValue() + newValue.doubleValue();
            } else if (NEGATIVE.equalsIgnoreCase(result.groupType)) {
                newValue = (Number)dataRow.get(result.srcMeasureField);
                if (oldValue == null) {
                    oldValue = 0;
                }
                if (newValue == null) {
                    newValue = 0;
                }
                currValue = ((Number)oldValue).doubleValue() + newValue.doubleValue() * -1.0;
            } else if (DEFAULT.equalsIgnoreCase(result.groupType)) {
                oldValue = oldValue == null ? Integer.valueOf(0) : oldValue;
                currValue = ((Number)oldValue).doubleValue() + Double.parseDouble(result.srcMeasureField);
            } else if (MAX.equalsIgnoreCase(result.groupType)) {
                currValue = dataRow.get(result.srcMeasureField);
                if (currValue == null || oldValue == null) continue;
                if (currValue instanceof Number) {
                    number = (Number)currValue;
                    currValue = number.doubleValue();
                    currValue = Math.max(((Number)oldValue).doubleValue(), ((Number)currValue).doubleValue());
                } else if (currValue instanceof Date) {
                    currValue = ((Date)oldValue).after((Date)currValue) ? oldValue : currValue;
                } else {
                    if (!(currValue instanceof String)) throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.not.support.max.operator", new Object[]{result.srcMeasureField}));
                    currValue = ((String)oldValue).compareTo((String)currValue) > 0 ? oldValue : currValue;
                }
            } else {
                if (!MIN.equalsIgnoreCase(result.groupType)) throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.error.operator", new Object[]{result.groupType}));
                currValue = dataRow.get(result.srcMeasureField);
                if (currValue == null || oldValue == null) continue;
                if (currValue instanceof Number) {
                    number = (Number)currValue;
                    currValue = number.doubleValue();
                    currValue = Math.min(((Number)oldValue).doubleValue(), ((Number)currValue).doubleValue());
                } else if (currValue instanceof Date) {
                    currValue = ((Date)oldValue).before((Date)currValue) ? oldValue : currValue;
                } else {
                    if (!(currValue instanceof String)) throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.not.support.min.operator", new Object[]{result.srcMeasureField}));
                    currValue = ((String)oldValue).compareTo((String)currValue) < 0 ? oldValue : currValue;
                }
            }
            map.put(tarMeasureField, currValue);
        }
    }

    private GroupTypeResult getGroupTypeResult(String srcMeasureFields) {
        String[] split = srcMeasureFields.split("\\|\\|");
        String srcMeasureField = split[0];
        String groupType = SUM;
        int length = split.length;
        if (length != 1 && length != 2) {
            throw new RuntimeException(BillExtend18nUtil.getMessage("va.billextend.plaese.check.formula.config"));
        }
        if (length > 1) {
            groupType = split[1];
        }
        return new GroupTypeResult(srcMeasureField, groupType);
    }

    private void initValueMap(String[] tarMeasureFields, Map<String, Map<String, Object>> valueMap, String[] srcMeasureFields, Map<String, Object> dataRow, String key) {
        HashMap<String, Object> map = new HashMap<String, Object>(16);
        for (int index = 0; index < tarMeasureFields.length; ++index) {
            Object value;
            GroupTypeResult result = this.getGroupTypeResult(srcMeasureFields[index]);
            if (DEFAULT.equalsIgnoreCase(result.groupType)) {
                value = Double.parseDouble(result.srcMeasureField);
            } else if (NEGATIVE.equalsIgnoreCase(result.groupType)) {
                Number newValue = (Number)dataRow.get(result.srcMeasureField);
                if (newValue == null) {
                    newValue = 0;
                }
                value = newValue.doubleValue() * -1.0;
            } else {
                value = dataRow.get(result.srcMeasureField);
            }
            map.put(tarMeasureFields[index], value);
        }
        valueMap.put(key, map);
    }

    private void doInsertDataAndKeepOrder(BillModel model, Map<String, Map<String, Object>> valueMap, String tarTableName, String[] tarDimFields, boolean delRowData) {
        DataTable table = model.getTable(tarTableName);
        if (table != null) {
            HashMap containedRows = new HashMap();
            ArrayList invalidRows = new ArrayList();
            int size = table.getRows().size();
            HashSet handledKeySet = new HashSet();
            if (size > 0) {
                ListContainer rows = table.getRows();
                ArrayList allRows = new ArrayList();
                ArrayList deleteRow = new ArrayList();
                rows.forEach((i, dataRow) -> {
                    String key = Arrays.stream(tarDimFields).map(item -> {
                        String string = dataRow.getString(item);
                        return StringUtils.hasText(string) ? string : "";
                    }).collect(Collectors.joining("@&"));
                    if (!handledKeySet.contains(key) && valueMap.get(key) != null) {
                        boolean[] dimValueEqualsFlag = new boolean[]{false};
                        valueMap.forEach((groupFields, map) -> {
                            String[] groupField = groupFields.split("@&", -1);
                            for (int j = 0; j < tarDimFields.length; ++j) {
                                if (!this.checkEquals(dataRow.getString(tarDimFields[j]), groupField[j])) continue;
                                dimValueEqualsFlag[0] = true;
                                break;
                            }
                        });
                        if (dimValueEqualsFlag[0]) {
                            Map rowData = dataRow.getData();
                            handledKeySet.add(key);
                            containedRows.put(key, rowData);
                            Map tarSumFieldValues = (Map)valueMap.get(key);
                            rowData.putAll(tarSumFieldValues);
                            rowData.put("ID", UUID.randomUUID().toString());
                        }
                    } else {
                        Map rowData = dataRow.getData();
                        rowData.put("ID", UUID.randomUUID().toString());
                        invalidRows.add(rowData);
                        deleteRow.add(dataRow);
                    }
                    allRows.add(dataRow);
                });
                if (containedRows.isEmpty()) {
                    if (delRowData) {
                        deleteRow.forEach(arg_0 -> ((DataTable)table).deleteRow(arg_0));
                    }
                    return;
                }
                allRows.forEach(arg_0 -> ((DataTable)table).deleteRow(arg_0));
            }
            valueMap.forEach((dimFieldValues, tarFieldMap) -> {
                if (containedRows.containsKey(dimFieldValues)) {
                    Map rowValue = (Map)containedRows.get(dimFieldValues);
                    table.insertRows(Collections.singletonList(rowValue));
                } else {
                    String[] dimValues = dimFieldValues.split("@&", -1);
                    DataRow dataRow = table.appendRow();
                    for (int i = 0; i < tarDimFields.length; ++i) {
                        dataRow.setValue(tarDimFields[i], (Object)dimValues[i]);
                    }
                    for (Map.Entry entry : tarFieldMap.entrySet()) {
                        dataRow.setValue((String)entry.getKey(), entry.getValue());
                    }
                }
            });
            if (!invalidRows.isEmpty() && !delRowData) {
                table.insertRows(invalidRows);
            }
        }
    }

    private void doInsertData(BillModel model, Map<String, Map<String, Object>> valueMap, String tarTableName, String[] tarDimFields, boolean delRowData) {
        DataTable table = model.getTable(tarTableName);
        if (table != null) {
            int size = table.getRows().size();
            if (size > 0) {
                ListContainer rows = table.getRows();
                ArrayList deleteRow = new ArrayList();
                rows.forEach((i, dataRow) -> {
                    String key = Arrays.stream(tarDimFields).map(item -> {
                        String string = dataRow.getString(item);
                        return StringUtils.hasText(string) ? string : "";
                    }).collect(Collectors.joining("@&"));
                    if (valueMap.get(key) != null) {
                        boolean[] dimValueEqualsFlag = new boolean[]{false};
                        valueMap.forEach((groupFields, map) -> {
                            String[] groupField = groupFields.split("@&", -1);
                            for (int j = 0; j < tarDimFields.length; ++j) {
                                if (!this.checkEquals(dataRow.getString(tarDimFields[j]), groupField[j])) continue;
                                dimValueEqualsFlag[0] = true;
                                break;
                            }
                        });
                        if (dimValueEqualsFlag[0]) {
                            Map tarSumFieldValues = (Map)valueMap.get(key);
                            for (Map.Entry entry : tarSumFieldValues.entrySet()) {
                                dataRow.setValue((String)entry.getKey(), entry.getValue());
                            }
                            valueMap.remove(key);
                        }
                    } else if (delRowData) {
                        deleteRow.add(dataRow);
                    }
                });
                deleteRow.forEach(arg_0 -> ((DataTable)table).deleteRow(arg_0));
            }
            valueMap.forEach((dimFieldValues, tarFieldMap) -> {
                String[] dimValues = dimFieldValues.split("@&", -1);
                DataRow dataRow = table.appendRow();
                for (int i = 0; i < tarDimFields.length; ++i) {
                    dataRow.setValue(tarDimFields[i], (Object)dimValues[i]);
                }
                for (Map.Entry entry : tarFieldMap.entrySet()) {
                    dataRow.setValue((String)entry.getKey(), entry.getValue());
                }
            });
        }
    }

    private boolean checkEquals(String tarFieldValue, String groupFieldValue) {
        if (Objects.equals(tarFieldValue, groupFieldValue)) {
            return true;
        }
        return !StringUtils.hasText(tarFieldValue) && !StringUtils.hasText(groupFieldValue);
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("srcFields\uff1a").append(DataType.toString((int)6)).append("\uff1b \u6e90\u8868\u7ef4\u5ea6\u6c47\u603b\u53c2\u6570\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("targetFields\uff1a").append(DataType.toString((int)6)).append("\uff1b \u76ee\u6807\u5b50\u8868\u53c2\u6570\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("delInvalidRowData\uff1a").append(DataType.toString((int)1)).append("\uff1b \u6761\u4ef6\u503c\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("filterField\uff1a").append(DataType.toString((int)6)).append("\uff1b \u4e0d\u6c47\u603b\u5b57\u6bb5\uff08boolean\u7c7b\u578b\u5b57\u6bb5\uff09\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("srcCondition\uff1a").append(DataType.toString((int)6)).append("\uff1b \u6e90\u8868\u8fc7\u6ee4\u6761\u4ef6\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("keepOrder\uff1a").append(DataType.toString((int)1)).append("\uff1b \u6309\u7167\u6e90\u8868\u6392\u5e8f\uff0c\u9ed8\u8ba4false\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a");
        buffer.append("    ").append("InsertSumRecordFunction(\"FO_EXPENSEBILL_ITEM@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY#FO_EXPENSEBILL_ITEMTSFY@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\",\"FO_EXPENSEBILL_ITEMCLF@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\",true,\"SUMFLAG\",\"Len(FO_EXPENSEBILL_ITEM[MEMO]) > 7\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u51fd\u6570\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u53c2\u6570\u4e00\uff1a\u4e3a\u6e90\u8868\u6c47\u603b\u53c2\u6570\uff0c\u53c2\u6570\u683c\u5f0f\uff1aFO_EXPENSEBILL_ITEM@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("@\u7b26\u53f7\u524d\u4e3a\u6c47\u603b\u6e90\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u6c47\u603b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff08\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u201c,\u201d\uff09\uff0c&\u7b26\u53f7\u540e\u9762\u4e3a\u6e90\u8868\u6c47\u603b\u6307\u6807\uff08\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u201c,\u201d\uff09\uff0c").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u9700\u8981\u4ece\u591a\u5b50\u8868\u6c47\u603b\u6570\u636e\u65f6\u4ee5\u4e95\u53f7\u5206\u9694\u201c#\u201d\uff08\u591a\u5b50\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e00\u81f4\uff09,\u65b0\u589e\u652f\u6301\u5206\u7ec4\u64cd\u4f5c\u4f7f\u7528||\u5206\u9694,sum\u6216\u8005\u4e0d\u586b\u5373\u539f\u6765\u7684\u903b\u8f91,default\u53d6||\u5de6\u8fb9\u7684\u6570\u503c\u53c2\u4e0esum,negative\u5c06\u5de6\u8fb9\u5b57\u6bb5\u4e58-1\u53c2\u4e0esum,\u989d\u5916\u652f\u6301max,min\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u53c2\u6570\u4e8c\uff1a\u4e3a\u76ee\u6807\u8d4b\u503c\u53c2\u6570\uff0c\u53c2\u6570\u683c\u5f0f\uff1aFO_EXPENSEBILL_ITEMCLF@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("@\u7b26\u53f7\u524d\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff08\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u201c,\u201d\uff09\uff0c&\u7b26\u53f7\u540e\u9762\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u5b57\u6bb5\u6807\u8bc6\uff08\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u201c,\u201d\uff09\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u6ce8\u610f\uff1a\u6e90\u8868\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6c47\u603b\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e0e\u76ee\u6807\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u8d4b\u503c\u6307\u6807\u5b57\u6bb5\u4e2a\u6570\u4e00\u81f4\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u53c2\u6570\u4e09\uff1a\u591a\u6b21\u6c47\u603b\u65f6\uff0c\u76ee\u6807\u8868\u5b58\u5728\u7684\u65e0\u6548\u884c\u6570\u636e\u662f\u5426\u5220\u9664\uff08\u65e0\u6548\u6570\u636e\uff1a\u6e90\u8868\u7ef4\u5ea6\u503c\u51cf\u5c11\uff0c\u5982\u539f\u6765\u4e09\u4e2a\u8d39\u7528\u9879\u76ee\u51cf\u5c11\u5230\u4e24\u4e2a\u8d39\u7528\u9879\u76ee\uff09\u3002 ").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u53c2\u6570\u56db\uff1a\u589e\u52a0\u8fc7\u6ee4\u5b57\u6bb5\u6807\u8bc6\uff0c\u8fc7\u6ee4\u5b57\u6bb5\u503c\u4e3atrue\u65f6\u4ee3\u8868\u4e0d\u53c2\u6570\u4e0e\u6c47\u603b\uff0cfalse\u4ee3\u8868\u53c2\u4e0e\u6c47\u603b\uff0c\u7f3a\u7701\u5219\u6309\u7167false\u5904\u7406\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u53c2\u6570\u4e94\uff1a\u589e\u52a0\u6e90\u8868\u8fc7\u6ee4\u6761\u4ef6\uff0c\u8fc7\u6ee4\u6761\u4ef6\u89e3\u6790\u7ed3\u679c\u4e3atrue\u65f6\u4ee3\u8868\u6e90\u8868\u8bb0\u5f55\u53c2\u4e0e\u6c47\u603b\uff0cfalse\u4ee3\u8868\u4e0d\u53c2\u4e0e\u6c47\u603b\uff0c\u7f3a\u7701\u5219\u6309\u7167\u7a7a\u503c\u4e0d\u8fc7\u6ee4\u5904\u7406\uff0c\u4e0d\u540c\u5b50\u8868\u7684\u516c\u5f0f\u4f7f\u7528#\u5206\u5272\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u53c2\u6570\u516d\uff1a\u6309\u7167\u6e90\u8868\u6392\u5e8f\u3002\u53c2\u6570\u503c\u4e3afalse\uff0c\u5728\u76ee\u6807\u8868\u6570\u636e\u7684\u57fa\u7840\u4e0a\uff0c\u5df2\u6709\u5219\u66f4\u65b0\uff0c\u65e0\u5219\u65b0\u589e\uff0c\u4e0d\u4fdd\u8bc1\u6570\u636e\u987a\u5e8f\uff1b\u53c2\u6570\u503c\u4e3atrue\uff0c\u6309\u6e90\u8868\u6570\u636e\u987a\u5e8f\uff0c\u5bf9\u76ee\u6807\u8868\u6570\u636e\u5168\u5220\u5168\u63d2\uff0c\u4fdd\u8bc1\u6570\u636e\u987a\u5e8f\uff0c\u4e14\u4e0d\u652f\u6301\u5faa\u73af\u89e6\u53d1\u5230\u540c\u4e00\u4e2a\u8868\u3002\u7f3a\u7701\u6309false\u5904\u7406").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u6ce8\u610f\u4e8b\u9879\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u4e0d\u5141\u8bb8\u5b50\u8868\u9a71\u52a8\u4f7f\u7528\uff0c\u4f8b\u5982\uff1aif FO_EXPENSEBILL_ITEM[BILLMONEY] > 0 then InsertSumRecordFunction \u4f1a\u5bfc\u81f4\u6309\u884c\u6267\u884c\u591a\u6b21").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u5982\u679c\u8fc7\u6ee4\u5b50\u8868\u884c\u662f\u5426\u53c2\u4e0e\u6c47\u603b\u4f7f\u7528\u53c2\u6570\u56dbfilterField\u6765\u8fc7\u6ee4").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u5982\u679c\u6309\u6761\u4ef6\u51b3\u5b9a\u662f\u5426\u6267\u884c\u6c47\u603b\u516c\u5f0f\u8bf7\u4f7f\u7528\u4e3b\u8868\u5b57\u6bb5\u4f5c\u4e3a\u5224\u65ad\u6761\u4ef6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b2\uff1a\u7ef4\u5ea6\u4f7f\u7528\u591a\u9009\u5b57\u6bb5");
        buffer.append("    ").append("InsertSumRecordFunction(\"FO_APARBILL_ITEM@MULTIPLEFIELD||Multiple,PAYMENTNATURECODE&BILLMONEY,TRADEMONEY\",\"FO_APARBILL_EGAS_ITEM@APARTYPECODE,PAYMENTNATURECODE&BILLMONEY,VERIFYMONEY\",false,\"SUMFLAG\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u51fd\u6570\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u53c2\u6570\u4e00\uff1a\u4e3a\u6e90\u8868\u6c47\u603b\u53c2\u6570\uff0c\u53c2\u6570\u683c\u5f0f\uff1aFO_APARBILL_ITEM@MULTIPLEFIELD||Multiple,PAYMENTNATURECODE&BILLMONEY,TRADEMONEY\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u6c47\u603b\u7ef4\u5ea6\u65b0\u589e\u652f\u6301\u591a\u9009\u5b57\u6bb5\uff08MULTIPLEFIELD||Multiple\uff09\u5728\u539f\u6765\u5355\u9009\u5b57\u6bb5\u7684\u57fa\u7840\u4e0a\u589e\u52a0\uff08||Multiple\uff09\u6765\u8868\u793a\u8fd9\u4e2a\u7ef4\u5ea6\u5b57\u6bb5\u4f7f\u7528\u591a\u9009\u5b57\u6bb5\u3002").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("\u6ce8\u610f\u4e8b\u9879\uff1a\u6c47\u603b\u7ef4\u5ea6\u591a\u9009\u5b57\u6bb5\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u591a\u9009\u5b57\u6bb5\uff0c\u5355\u9009\u5b57\u6bb5\u53ef\u4ee5\u4f7f\u7528\uff08PAYMENTNATURECODE||single\uff09\u4e5f\u53ef\u4ee5\u7701\u7565single\u9ed8\u8ba4\uff08PAYMENTNATURECODE\uff09\u5373\u4e3a\u5355\u9009\u5b57\u6bb5\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u6ce8\u610f\u4e8b\u9879\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("srcFields", DataType.toString((int)6), "\u6e90\u8868\u6c47\u603b\u7ef4\u5ea6\u3002\u683c\u5f0f\uff1a\u6e90\u88681@\u5b57\u6bb51,\u5b57\u6bb52&\u6307\u68071,\u6307\u68072#\u6e90\u88682@\u5b57\u6bb51,\u5b57\u6bb52&\u6307\u68071,\u6307\u68072\uff08@\u7b26\u53f7\u524d\u4e3a\u6c47\u603b\u6e90\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u6c47\u603b\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b&\u7b26\u53f7\u540e\u9762\u4e3a\u6e90\u8868\u6c47\u603b\u6307\u6807\uff0c\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b\u9700\u8981\u4ece\u591a\u5b50\u8868\u6c47\u603b\u6570\u636e\u65f6\u4ee5#\u5206\u9694\uff1b\u65b0\u589e\u652f\u6301\u5206\u7ec4\u64cd\u4f5c\u4f7f\u7528||\u5206\u9694,sum\u6216\u8005\u4e0d\u586b\u5373\u539f\u6765\u7684\u903b\u8f91,default\u53d6||\u5de6\u8fb9\u7684\u6570\u503c\u53c2\u4e0esum,\u989d\u5916\u652f\u6301max,min\uff09\n", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("targetFields", DataType.toString((int)6), "\u76ee\u6807\u8d4b\u503c\u4fe1\u606f\u3002\u683c\u5f0f\uff1a\u76ee\u6807\u8868@\u5b57\u6bb51,\u5b57\u6bb52&\u6307\u68071,\u6307\u68072\uff08@\u7b26\u53f7\u524d\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u540d\uff0c@\u7b26\u53f7\u540e\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u7ef4\u5ea6\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u7ef4\u5ea6\u65f6\u4ee5\u9017\u53f7\u5206\u9694\uff1b&\u7b26\u53f7\u540e\u9762\u4e3a\u8d4b\u503c\u76ee\u6807\u8868\u5b57\u6bb5\u6807\u8bc6\uff0c\u591a\u6307\u6807\u65f6\u4ee5\u9017\u53f7\u5206\u9694\u3002\uff09", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("delInvalidRowData", DataType.toString((int)1), "\u6761\u4ef6\u503c\uff0c\u591a\u6b21\u6c47\u603b\u65f6\uff0c\u76ee\u6807\u8868\u5b58\u5728\u7684\u65e0\u6548\u884c\u6570\u636e\u662f\u5426\u5220\u9664\uff08\u65e0\u6548\u6570\u636e\uff1a\u6e90\u8868\u7ef4\u5ea6\u503c\u51cf\u5c11\uff0c\u5982\u539f\u6765\u4e09\u4e2a\u8d39\u7528\u9879\u76ee\u51cf\u5c11\u5230\u4e24\u4e2a\u8d39\u7528\u9879\u76ee\uff09", Boolean.valueOf(true));
        ParameterDescription parameterDescription3 = new ParameterDescription("filterField", DataType.toString((int)6), "\u4e0d\u6c47\u603b\u5b57\u6bb5\u3002\u5b57\u6bb5\u5fc5\u987b\u4e3a\u5e03\u5c14\u5b57\u6bb5\uff0c\u4e3atrue\u65f6\u4ee3\u8868\u4e0d\u53c2\u4e0e\u6c47\u603b\uff0cfalse\u4ee3\u8868\u53c2\u4e0e\u6c47\u603b\uff0c\u7f3a\u7701\u6309false\u5904\u7406", Boolean.valueOf(false));
        ParameterDescription parameterDescription4 = new ParameterDescription("srcCondition", DataType.toString((int)6), "\u6e90\u8868\u8fc7\u6ee4\u6761\u4ef6\u3002\u89e3\u6790\u7ed3\u679c\u4e3atrue\u65f6\u4ee3\u8868\u53c2\u4e0e\u6c47\u603b\uff0cfalse\u4ee3\u8868\u4e0d\u53c2\u4e0e\u6c47\u603b\uff0c\u7f3a\u7701\u6309true\u5904\u7406\u3002", Boolean.valueOf(false));
        ParameterDescription parameterDescription5 = new ParameterDescription("keepOrder", DataType.toString((int)1), "\u6309\u7167\u6e90\u8868\u6392\u5e8f\u3002\u53c2\u6570\u503c\u4e3afalse\uff0c\u5728\u76ee\u6807\u8868\u6570\u636e\u7684\u57fa\u7840\u4e0a\uff0c\u5df2\u6709\u5219\u66f4\u65b0\uff0c\u65e0\u5219\u65b0\u589e\uff0c\u4e0d\u4fdd\u8bc1\u6570\u636e\u987a\u5e8f\uff1b\u53c2\u6570\u503c\u4e3atrue\uff0c\u6309\u6e90\u8868\u6570\u636e\u987a\u5e8f\uff0c\u5bf9\u76ee\u6807\u8868\u6570\u636e\u5168\u5220\u5168\u63d2\uff0c\u4fdd\u8bc1\u6570\u636e\u987a\u5e8f\uff0c\u4e14\u4e0d\u652f\u6301\u5faa\u73af\u89e6\u53d1\u5230\u540c\u4e00\u4e2a\u8868\u3002\u7f3a\u7701\u6309false\u5904\u7406", Boolean.valueOf(false));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription3);
        parameterDescriptions.add(parameterDescription4);
        parameterDescriptions.add(parameterDescription5);
        ArrayList<String> notes = new ArrayList<String>();
        notes.add("\u6e90\u8868\u4e3a\u591a\u4e2a\u5b50\u8868\u65f6\uff0c\u591a\u5b50\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e00\u81f4\u3002");
        notes.add("\u6e90\u8868\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u6c47\u603b\u6307\u6807\u4e2a\u6570\u5fc5\u987b\u4e0e\u76ee\u6807\u8868\u7684\u7ef4\u5ea6\u5b57\u6bb5\u4e2a\u6570\u3001\u8d4b\u503c\u6307\u6807\u5b57\u6bb5\u4e2a\u6570\u4e00\u81f4\u3002");
        notes.add("\u6e90\u8868\u7ef4\u5ea6\u4e3a\u591a\u9009\u5b57\u6bb5\u65f6\uff0c\u4ec5\u652f\u6301\u4e00\u4e2a\u591a\u9009\u5b57\u6bb5\u3002\u591a\u9009\u5b57\u6bb5\u914d\u7f6e\u683c\u5f0f\u4e3a \u7ef4\u5ea6||Multiple\u3002");
        notes.add("\u6e90\u8868\u6c47\u603b\u6307\u6807\u9ed8\u8ba4\u5bf9\u6307\u6807\u6c42\u548csum\uff0c\u4e5f\u652f\u6301max\u548cmin\uff0c\u914d\u7f6e\u683c\u5f0f\u4e3a \u6307\u6807||max\u3001\u6307\u6807||min\u3002");
        notes.add("\u4e0d\u5141\u8bb8\u5b50\u8868\u9a71\u52a8\u4f7f\u7528\uff0c\u4f8b\u5982\uff1aif FO_EXPENSEBILL_ITEM[BILLMONEY] > 0 then InsertSumRecordFunction \u4f1a\u5bfc\u81f4\u6309\u884c\u6267\u884c\u591a\u6b21\u3002");
        formulaDescription.setNotes(notes);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5c06\u6e90\u5b50\u8868\u7684\u6570\u636e\uff0c\u6839\u636e\u6307\u5b9a\u7ef4\u5ea6\u8fdb\u884c\u91d1\u989d\u6c47\u603b\uff0c\u5c06\u6c47\u603b\u540e\u7684\u503c\u63d2\u5165\u5230\u76ee\u6807\u5b50\u8868\uff0c\u4e14\u4ec5\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u53c2\u4e0e\u6c47\u603b", "InsertSumRecordFunction(\"FO_EXPENSEBILL_ITEM@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\",\"FO_EXPENSEBILL_ITEMCLF@DEPARTMENT,KINDID&EXPENSEMONEY,MONEY\",true,\"SUMFLAG\",\"Len(FO_EXPENSEBILL_ITEM[MEMO]) > 7\")", null, "\u5c06\u62a5\u9500\u6c47\u603b\u5b50\u8868\uff08FO_EXPENSEBILL_ITEM\uff09\u7684\u62a5\u9500\u91d1\u989d\uff08EXPENSEMONEY\uff09\u3001\u91d1\u989d\u5408\u8ba1\uff08MONEY\uff09\u6839\u636e\u90e8\u95e8\uff08DEPARTMENT\uff09\u3001\u8d39\u7528\u7c7b\u522b\uff08KINDID\uff09\u7ef4\u5ea6\u6c47\u603b\uff0c\u5c06\u6c47\u603b\u540e\u7684\u4fe1\u606f\u63d2\u5165\u5230\u5dee\u65c5\u8d39\u7528\u5b50\u8868\uff08FO_EXPENSEBILL_ITEMCLF\uff09\u4e2d\u3002\u5982\u679c\u4fee\u6539\u4e86\u62a5\u9500\u6c47\u603b\u5b50\u8868\u7ef4\u5ea6\u91cd\u65b0\u6c47\u603b\uff0c\u9700\u8981\u5220\u9664\u5dee\u65c5\u8d39\u7528\u5b50\u8868\u7684\u65e0\u6548\u6570\u636e\u3002\u62a5\u9500\u6c47\u603b\u5b50\u8868\u4e2d\u4ec5SUMFLAG\u4e3afasle\u7684\u6570\u636e\u53c2\u4e0e\u6c47\u603b\u3002\u62a5\u9500\u6c47\u603b\u5b50\u8868\u4e2dMEMO\u957f\u5ea6\u5927\u4e8e7\u7684\u6570\u636e\u53c2\u4e0e\u6c47\u603b\u3002");
        FormulaExample formulaExample1 = new FormulaExample("\u5c06\u591a\u4e2a\u6e90\u8868\u7684\u6570\u636e\uff0c\u6839\u636e\u6307\u5b9a\u7ef4\u5ea6\u8fdb\u884c\u91d1\u989d\u6c47\u603b\u548c\u65e5\u671f\u6700\u5c0f\u503c\u3001\u6700\u5927\u503c\u53d6\u503c\uff0c\u5c06\u6c47\u603b\u540e\u7684\u503c\u63d2\u5165\u5230\u76ee\u6807\u5b50\u8868", "InsertSumRecordFunction(\"FO_EXPENSEBILL_INVVATITEM@ZCL_STAFFCODE&MONEYTOTAL,INVOICEDATE||min,INVOICEDATE||max#FO_EXPENSEBILL_INVOTHITEM@ZCL_STAFFCODE&TICKETMONEY,BEGINDATE||min,BEGINDATE||max\",\"FO_EXPENSEBILL_SUBSIDYITEM@STAFFCODE&BILLMONEY,ZCL_CCRQ,ZCL_DDRQ\",true)", null, "\u5c06\u589e\u503c\u7a0e\u53d1\u7968\u5b50\u8868\uff08FO_EXPENSEBILL_INVVATITEM\uff09\u7684\u91d1\u989d\u5408\u8ba1\uff08MONEYTOTAL\uff09\u3001\u53d1\u7968\u65e5\u671f\uff08INVOICEDATE\uff09\u6700\u5c0f\u503c\u3001\u53d1\u7968\u65e5\u671f\uff08INVOICEDATE\uff09\u6700\u5927\u503c\u53ca\u5176\u4ed6\u53d1\u7968\u5b50\u8868\uff08FO_EXPENSEBILL_INVOTHITEM\uff09\u7684\u7968\u636e\u91d1\u989d\uff08TICKETMONEY\uff09\u3001\u51fa\u53d1\u65e5\u671f\uff08BEGINDATE\uff09\u6700\u5c0f\u503c\u3001\u51fa\u53d1\u65e5\u671f\uff08BEGINDATE\uff09\u6700\u5927\u503c\uff0c\u6839\u636e\u804c\u5458\uff08ZCL_STAFFCODE\uff09\u7ef4\u5ea6\u6c47\u603b\uff0c\u5c06\u6c47\u603b\u540e\u7684\u4fe1\u606f\u63d2\u5165\u5230\u8865\u52a9\u5b50\u8868\uff08FO_EXPENSEBILL_SUBSIDYITEM\uff09\u4e2d\u3002\u5982\u679c\u4fee\u6539\u4e86\u62a5\u9500\u6c47\u603b\u5b50\u8868\u7ef4\u5ea6\u91cd\u65b0\u6c47\u603b\uff0c\u9700\u8981\u5220\u9664\u5dee\u65c5\u8d39\u7528\u5b50\u8868\u7684\u65e0\u6548\u6570\u636e\u3002");
        FormulaExample formulaExample2 = new FormulaExample("\u6839\u636e\u6e90\u8868\u4e2d\u7684\u6307\u5b9a\u7ef4\u5ea6\uff08\u5b58\u5728\u591a\u9009\u5b57\u6bb5\u7ef4\u5ea6\uff09\u6c47\u603b\u91d1\u989d\uff0c\u5c06\u6c47\u603b\u540e\u7684\u6570\u636e\u63d2\u5165\u5230\u76ee\u6807\u5b50\u8868\u4e2d", "InsertSumRecordFunction(\"FO_EXPENSEBILL_ITEM@BUDSUBJECTCODE,STAFFMULT||Multiple&BILLMONEY\",\"FO_EXPENSEBILL_SUBSIDYITEM@BUDSUBJECTCODE,STAFFCODE&BILLMONEY\", true)", null, "\u5c06\u62a5\u9500\u6c47\u603b\u5b50\u8868\uff08FO_EXPENSEBILL_ITEM\uff09\u7684\u62a5\u9500\u91d1\u989d\uff08BILLMONEY\uff09\u6839\u636e\u6d88\u8d39\u7c7b\u578b\uff08BUDSUBJECTCODE\uff09\u3001\u540c\u884c\u4eba\uff08\u591a\u9009\uff0cSTAFFMULT||Multiple\uff09\u7ef4\u5ea6\u6c47\u603b\uff0c\u5c06\u6c47\u603b\u540e\u7684\u4fe1\u606f\u63d2\u5165\u5230\u8865\u52a9\u5b50\u8868\uff08FO_EXPENSEBILL_SUBSIDYITEM\uff09\u4e2d\u3002");
        FormulaExample formulaExample3 = new FormulaExample("\u5c06\u5355\u636e\u4e3b\u8868\u7684\u591a\u9009\u5b57\u6bb5\uff0c\u63d2\u5165\u5230\u76ee\u6807\u5b50\u8868\u4e2d", "if IsNotNull(FO_APPLOANBILL[TXR]) then InsertSumRecordFunction(\"FO_APPLOANBILL_M@BINDINGVALUE&ORDERNUM\",\"FO_APPLOANBILL_ITEM@STAFFCODE&JE\",true,'','Len(FO_APPLOANBILL_M[BINDINGVALUE]) > 7') else ClearBillDetailTableData(\"FO_APPLOANBILL_ITEM\")", null, "\u5355\u636e\u4e3b\u8868\uff08FO_APPLOANBILL\uff09\u7684\u540c\u884c\u4eba\uff08\u591a\u9009\uff0cTXR\uff09\u4e0d\u4e3a\u7a7a\u65f6\uff0c\u5c06\u540c\u884c\u4eba\u4fe1\u606f\u63d2\u5165\u5230\u51fa\u5dee\u4eba\u4fe1\u606f\u5b50\u8868\uff08FO_APPLOANBILL_ITEM\uff09\u4e2d\u3002\u591a\u9009\u5b50\u8868\uff08FO_APPLOANBILL_M\uff09\u4e2d\u5f15\u7528\u6570\u636e\u503c\uff08BINDINGVALUE\uff09\u957f\u5ea6\u5927\u4e8e7\u7684\u53c2\u4e0e\u6c47\u603b\u3002\u5355\u636e\u4e3b\u8868\uff08FO_APPLOANBILL\uff09\u7684\u540c\u884c\u4eba\uff08\u591a\u9009\uff0cTXR\uff09\u4e3a\u7a7a\u65f6\uff0c\u6e05\u7a7a\u51fa\u5dee\u4eba\u4fe1\u606f\u5b50\u8868\u3002");
        formulaExamples.add(formulaExample);
        formulaExamples.add(formulaExample1);
        formulaExamples.add(formulaExample2);
        formulaExamples.add(formulaExample3);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    private static class GroupTypeResult {
        public final String srcMeasureField;
        public final String groupType;

        public GroupTypeResult(String srcMeasureField, String groupType) {
            this.srcMeasureField = srcMeasureField;
            this.groupType = groupType;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.va.biz.impl.action.ActionManagerImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.ComputePropNode;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FormulaRulerItem
implements RulerItem {
    private static final Logger log = LoggerFactory.getLogger(FormulaRulerItem.class);
    private static final Map<UUID, String> saveAndDelIdMaps = new HashMap<UUID, String>(){
        private static final long serialVersionUID = 1L;
        {
            this.put(Utils.normalizeId("save"), "save");
            this.put(Utils.normalizeId("delete"), "delete");
        }
    };
    final FormulaImpl formula;
    private Set<String> triggerTypes;
    private boolean calculatedTriggerTypes = false;
    private Map<String, Map<String, Boolean>> triggerFields;
    private Map<String, Map<String, Boolean>> assignFields;

    public FormulaRulerItem(FormulaImpl formula) {
        this.formula = formula;
    }

    public FormulaImpl getFormula() {
        return this.formula;
    }

    @Override
    public String getName() {
        if (this.formula.getName() == null) {
            return String.valueOf(this.formula.getId());
        }
        return this.formula.getName();
    }

    @Override
    public String getTitle() {
        return this.formula.getTitle();
    }

    @Override
    public String getRulerType() {
        return "Formula";
    }

    @Override
    public Set<String> getTriggerTypes() {
        if (this.triggerTypes != null || this.calculatedTriggerTypes) {
            return this.triggerTypes;
        }
        if ("field".equals(this.formula.getObjectType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(this.formula.getPropertyType())) {
            this.triggerTypes = Stream.of("after-add-row", "after-del-row", "after-set-value").collect(Collectors.toSet());
            return this.triggerTypes;
        }
        if ("event".equals(this.formula.getObjectType())) {
            if ("AfterSetValue".equals(this.formula.getPropertyType())) {
                this.triggerTypes = Stream.of("after-set-value").collect(Collectors.toSet());
                return this.triggerTypes;
            }
            if ("AfterDelRow".equals(this.formula.getPropertyType())) {
                this.triggerTypes = Stream.of("after-del-row").collect(Collectors.toSet());
                return this.triggerTypes;
            }
            if ("AfterAddRow".equals(this.formula.getPropertyType())) {
                this.triggerTypes = Stream.of("after-add-row").collect(Collectors.toSet());
                return this.triggerTypes;
            }
        }
        if ("action".equals(this.formula.getObjectType()) && "before".equals(this.formula.getPropertyType())) {
            String actionName = saveAndDelIdMaps.get(this.formula.getObjectId());
            if (actionName == null) {
                Action action = ActionManagerImpl.findAction(this.formula.getObjectId());
                if (action == null) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.actionnotfound") + this.formula.getObjectId());
                }
                actionName = action.getName();
            }
            this.triggerTypes = Stream.of("before-" + actionName).collect(Collectors.toSet());
            return this.triggerTypes;
        }
        if ("field".equals(this.formula.getObjectType()) && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(this.formula.getPropertyType())) {
            this.triggerTypes = Stream.of("before-save").collect(Collectors.toSet());
            return this.triggerTypes;
        }
        this.calculatedTriggerTypes = true;
        return null;
    }

    @Override
    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine modelDefine) {
        if (this.triggerFields != null) {
            return this.triggerFields;
        }
        RulerFields defaultFields = RulerFields.build().noTables();
        RulerFields fields = RulerFields.build();
        IExpression expression = null;
        try {
            expression = this.formula.getCompiledExpression();
            IASTNode root = expression.getChild(0);
            if (this.formula.getFormulaType().equals((Object)FormulaType.EXECUTE) && root instanceof Equal) {
                expression = root.getChild(1);
            }
            HashSet aggregatedTable = new HashSet();
            expression.forEach(node -> {
                if (node instanceof FunctionNode && ((FunctionNode)node).getParameters().size() > 0 && ((FunctionNode)node).getParameters().get(0) instanceof ModelNode) {
                    for (ModelFunction function : ModelFunctionProvider.getFunctions()) {
                        if (!node.getToken().toString().equals(function.name()) || !(function instanceof AggregatedNode)) continue;
                        ModelNode modelNode = (ModelNode)((Object)((Object)((FunctionNode)node).getParameters().get(0)));
                        fields.field(modelNode.tableDefine.getName(), modelNode.fieldDefine.getName(), false);
                        aggregatedTable.add(modelNode.tableDefine.getName());
                    }
                } else if (node.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) && node instanceof ModelNode) {
                    ModelNode modelNode = (ModelNode)((Object)node);
                    if (aggregatedTable.contains(modelNode.tableDefine.getName())) {
                        fields.field(modelNode.tableDefine.getName(), modelNode.fieldDefine.getName(), false);
                    } else {
                        fields.field(modelNode.tableDefine.getName(), modelNode.fieldDefine.getName(), true);
                    }
                } else if (node.getNodeType().equals((Object)ASTNodeType.DATA) && node instanceof ModelDataNode) {
                    ModelDataNode modelDataNode = (ModelDataNode)((Object)node);
                    if (node instanceof AggregatedNode) {
                        aggregatedTable.add(modelDataNode.getModelNode().getTableName());
                        fields.field(modelDataNode.getModelNode().tableDefine.getName(), modelDataNode.getModelNode().fieldDefine.getName(), false);
                        if (node instanceof CountDataNode) {
                            CountDataNode countDataNode = (CountDataNode)((Object)node);
                            if (countDataNode.childrenSize() == 0) {
                                return;
                            }
                            countDataNode.getChild(0).forEach(child -> {
                                if (child.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) && child instanceof ModelParamNode) {
                                    ModelParamNode modelParamNode = (ModelParamNode)((Object)((Object)child));
                                    fields.field(modelDataNode.getModelNode().tableDefine.getName(), modelParamNode.getName().toUpperCase(), false);
                                }
                            });
                        }
                    } else {
                        fields.field(modelDataNode.getModelNode().tableDefine.getName(), modelDataNode.getModelNode().fieldDefine.getName(), true);
                    }
                } else if (node.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) && node instanceof ComputePropNode) {
                    ComputePropNode computePropNode = (ComputePropNode)((Object)node);
                    if (aggregatedTable.contains(computePropNode.getTableName())) {
                        fields.field(computePropNode.getTableName(), computePropNode.getFieldName(), false);
                    } else {
                        fields.field(computePropNode.getTableName(), computePropNode.getFieldName(), true);
                    }
                }
            });
        }
        catch (Exception e) {
            String errorMsg = BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.gettriggerfieldexception", new Object[]{this.formula.getName()}) + e.getMessage();
            log.error(errorMsg);
            throw new ModelException(errorMsg, e);
        }
        Map<String, Map<String, Boolean>> map = this.triggerFields = fields.fields().isEmpty() ? defaultFields.fields() : fields.fields();
        if (RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(this.formula.getPropertyType()) && StringUtils.hasText(this.formula.getName()) && this.formula.getName().startsWith("CALC_") && this.formula.getName().endsWith("_MASK")) {
            UUID objectId = this.formula.getObjectId();
            RulerDefineImpl rulerDefine = modelDefine.getPlugins().get(RulerDefineImpl.class);
            String[] strings = rulerDefine.getMaskFieldMap().get(objectId);
            if (strings != null) {
                Map stringBooleanMap = this.triggerFields.computeIfAbsent(strings[0], key -> new HashMap());
                stringBooleanMap.put(strings[1], true);
            }
        }
        return this.triggerFields;
    }

    @Override
    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine modelDefine) {
        if (this.assignFields != null) {
            return this.assignFields;
        }
        RulerFields defaultFields = RulerFields.build().noTables();
        if (this.formula.getFormulaType() != FormulaType.EXECUTE) {
            return defaultFields.fields();
        }
        RulerFields fields = RulerFields.build();
        IExpression expression = null;
        try {
            expression = this.formula.getCompiledExpression();
            IASTNode root = expression.getChild(0);
            IASTNode fristNode = null;
            if (!(root instanceof Equal)) {
                return defaultFields.fields();
            }
            fristNode = root.getChild(0);
            if (!(fristNode instanceof ModelNode)) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.fieldmustbemodelnode"));
            }
            ModelNode modelNode = (ModelNode)fristNode;
            fields.field(modelNode.tableDefine.getName(), modelNode.fieldDefine.getName(), true);
        }
        catch (Exception e) {
            String errorMsg = BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.getassignmentfieldexception", new Object[]{this.formula.getName()}) + e.getMessage();
            log.error(errorMsg);
            throw new ModelException(errorMsg, e);
        }
        this.assignFields = fields.fields().isEmpty() ? defaultFields.fields() : fields.fields();
        return this.assignFields;
    }

    @Override
    public void execute(Model model, Stream<TriggerEvent> events) {
        List<ModelDataContext> contexts = this.getAllContext(model, events);
        RulerImpl impl = new RulerImpl();
        ArrayList<CheckResult> results = new ArrayList<CheckResult>();
        contexts.stream().forEach(context -> {
            List<CheckResult> result = impl.execute((ModelDataContext)context, this.formula);
            if (!result.isEmpty()) {
                results.addAll(result);
            }
        });
        if (!results.isEmpty()) {
            throw new CheckException(this.formula.getTitle(), results);
        }
    }

    public void checkFormulaSyntax(Map<String, Map<String, Boolean>> triggerMap, Map<String, Map<String, Boolean>> assignMap) {
    }

    public List<ModelDataContext> getContexts(Model model, Stream<TriggerEvent> events) {
        return null;
    }

    public List<ModelDataContext> getAllContext(Model model, Stream<TriggerEvent> events) {
        DataImpl dataImpl = model.getPlugins().get(DataImpl.class);
        ArrayList<ModelDataContext> contexts = new ArrayList<ModelDataContext>();
        Map<String, Map<String, Boolean>> triggerMap = this.getTriggerFields(model.getDefine());
        String tableName = "";
        ArrayList<DataFieldDefineImpl> checkField = new ArrayList<DataFieldDefineImpl>();
        if (this.formula.getFormulaType().equals((Object)FormulaType.EXECUTE)) {
            Map<String, Map<String, Boolean>> assignMap = this.getAssignFields(model.getDefine());
            if (assignMap.size() == 0) {
                tableName = this.getCheckRow(dataImpl, triggerMap);
            } else {
                if (assignMap.size() > 1) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.notsupportsetmultipletables"));
                }
                tableName = assignMap.keySet().toArray()[0].toString();
                if (assignMap.get(tableName).isEmpty()) {
                    tableName = "";
                }
            }
        } else if ("field".equals(this.formula.getObjectType()) && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(this.formula.getPropertyType())) {
            UUID id = this.formula.getObjectId();
            DataFieldDefineImpl field = dataImpl.getDefine().getField(id);
            tableName = field.getTable().getName();
            checkField.add(field);
        } else {
            tableName = this.getCheckRow(dataImpl, triggerMap);
        }
        if (!StringUtils.hasText(tableName)) {
            contexts.add(new ModelDataContext(model));
            return contexts;
        }
        List<TriggerEvent> triggerEvents = events.collect(Collectors.toList());
        Map<UUID, DataRow> assignDataRowMap = this.getAssignRow(dataImpl, tableName, triggerEvents, triggerMap);
        String currentTable = tableName;
        if (assignDataRowMap.isEmpty()) {
            DataTableImpl dataTableImpl = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getName().equals(currentTable)).findFirst().get();
            dataTableImpl.getRows().stream().filter(o -> o.getState() != DataRowState.DELETED).forEach(dataRow -> {
                ModelDataContext context = this.getContext(model, dataImpl, currentTable, (DataRow)dataRow, triggerMap, events);
                if ("field".equals(this.formula.getObjectType()) && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(this.formula.getPropertyType())) {
                    dataRow.getData(false).forEach((n, v) -> context.put((String)n, v));
                }
                if (!checkField.isEmpty()) {
                    context.put("model_param_node_type", (Object)((DataFieldDefineImpl)checkField.get(0)).getValueType());
                }
                contexts.add(context);
            });
            return contexts;
        }
        Map<String, Boolean> fields = this.triggerFields.get(tableName);
        if (fields != null && !fields.values().stream().filter(o -> o).findFirst().orElse(false).booleanValue()) {
            DataTableImpl dataTableImpl = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getName().equals(currentTable)).findFirst().get();
            dataTableImpl.getRows().stream().filter(o -> o.getState() != DataRowState.DELETED).forEach(dataRow -> {
                ModelDataContext context = this.getContext(model, dataImpl, currentTable, (DataRow)dataRow, triggerMap, events);
                contexts.add(context);
            });
        } else {
            assignDataRowMap.forEach((k, assignDataRow) -> {
                ModelDataContext context = this.getContext(model, dataImpl, currentTable, (DataRow)assignDataRow, triggerMap, events);
                contexts.add(context);
            });
        }
        return contexts;
    }

    private String getCheckRow(DataImpl dataImpl, Map<String, Map<String, Boolean>> triggerMap) {
        ArrayList tableList = new ArrayList();
        if (triggerMap == null) {
            return null;
        }
        triggerMap.forEach((tableName, rowData) -> {
            if (rowData == null || rowData.isEmpty()) {
                return;
            }
            rowData.forEach((fieldName, currentRow) -> {
                if (currentRow.booleanValue() && !tableList.contains(tableName)) {
                    tableList.add(tableName);
                } else {
                    DataTableImpl childTable = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get((String)tableName);
                    if (childTable.getParentId() == null) {
                        return;
                    }
                    DataTableImpl dataTable = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getId().equals(childTable.getParentId())).findFirst().get();
                    if (!tableList.contains(tableName)) {
                        tableList.add(dataTable.getName());
                    }
                }
            });
        });
        if (tableList.isEmpty()) {
            return null;
        }
        if (tableList.size() == 1) {
            return (String)tableList.get(0);
        }
        tableList.sort((o1, o2) -> {
            if (this.isChildren(dataImpl.getDefine().getTables(), (String)o1, (String)o2)) {
                return 1;
            }
            if (this.isChildren(dataImpl.getDefine().getTables(), (String)o2, (String)o1)) {
                return -1;
            }
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.formulaillegal", new Object[]{o1, o2}));
        });
        return (String)tableList.get(0);
    }

    private Map<UUID, DataRow> getAssignRow(DataImpl dataImpl, String assignTableName, List<TriggerEvent> triggerEvents, Map<String, Map<String, Boolean>> triggerMap) {
        HashMap<UUID, DataRow> dataRowMap = new HashMap<UUID, DataRow>();
        for (int i = triggerEvents.size() - 1; i >= 0; --i) {
            TriggerEvent o = triggerEvents.get(i);
            if (o.getTable() == null || !o.getTable().getName().equals(assignTableName)) continue;
            if (o.getRow() == null) {
                return Collections.emptyMap();
            }
            dataRowMap.put(Convert.cast(o.getRow().getId(), UUID.class), o.getRow());
            triggerEvents.remove(i);
        }
        if (triggerEvents.size() == 0) {
            return dataRowMap;
        }
        if (triggerMap == null) {
            return dataRowMap;
        }
        triggerMap.forEach((tableName, rowMap) -> {
            if (rowMap == null || rowMap.isEmpty()) {
                return;
            }
            rowMap.forEach((fieldName, currentRow) -> {
                TriggerEvent eventOpt = triggerEvents.stream().filter(o -> {
                    if (o.getTable() == null || o.getField() == null) {
                        return false;
                    }
                    return o.getTable().getName().equals(tableName) && o.getField().getName().equals(fieldName);
                }).findFirst().orElse(null);
                if (eventOpt == null) {
                    return;
                }
                if (assignTableName.equals(tableName)) {
                    if (eventOpt.getRow() == null && currentRow.booleanValue()) {
                        throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.formulaillegal_1", new Object[]{tableName, fieldName}));
                    }
                    if (eventOpt.getRow() != null) {
                        dataRowMap.put(Convert.cast(eventOpt.getRow().getId(), UUID.class), eventOpt.getRow());
                    }
                } else if (this.isChildren(dataImpl.getDefine().getTables(), assignTableName, (String)tableName)) {
                    this.getDataRowMap(dataImpl, assignTableName, (String)tableName, eventOpt.getRow(), (Map<UUID, DataRow>)dataRowMap);
                } else if (this.isChildren(dataImpl.getDefine().getTables(), (String)tableName, assignTableName)) {
                    dataRowMap.clear();
                } else if (this.isAllChildren(dataImpl.getDefine().getTables(), (String)tableName, assignTableName)) {
                    dataRowMap.clear();
                } else if (eventOpt.getRow() == null) {
                    dataRowMap.clear();
                } else {
                    dataRowMap.put(Convert.cast(eventOpt.getRow().getId(), UUID.class), eventOpt.getRow());
                }
            });
        });
        return dataRowMap;
    }

    private void getDataRowMap(DataImpl dataImpl, String assignTableName, String tableName, DataRow childDataRow, Map<UUID, DataRow> dataRowMap) {
        DataTableImpl assignTable = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(assignTableName);
        DataTableImpl childTable = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(tableName);
        DataTableImpl masterTable = dataImpl.getMasterTable();
        if (masterTable.getId().equals(childTable.getParentId())) {
            assignTable.getRows().stream().filter(dataRow -> dataRow.getId().equals(childDataRow.getMasterId())).forEach(row -> {
                DataRow cfr_ignored_0 = dataRowMap.put(Convert.cast(row.getId(), UUID.class), (DataRow)row);
            });
        } else {
            assignTable.getRows().stream().filter(dataRow -> dataRow.getId().equals(childDataRow.getGroupId())).forEach(row -> {
                DataRow cfr_ignored_0 = dataRowMap.put(Convert.cast(row.getId(), UUID.class), (DataRow)row);
            });
        }
        if (!dataRowMap.isEmpty()) {
            return;
        }
        DataTableImpl dataTable = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getId().equals(childTable.getParentId())).findFirst().get();
        DataRow pRowData = masterTable.getId().equals(childTable.getParentId()) ? (DataRow)dataTable.getRows().stream().filter(row -> row.getId().equals(childDataRow.getMasterId())).findFirst().orElse(null) : (DataRow)dataTable.getRows().stream().filter(row -> row.getId().equals(childDataRow.getGroupId())).findFirst().orElse(null);
        this.getDataRowMap(dataImpl, assignTableName, dataTable.getName(), pRowData, dataRowMap);
    }

    private boolean isAllChildren(DataTableNodeContainer<? extends DataTableDefine> tables, String table1, String table2) {
        DataTableDefine tableDefine1 = tables.find(table1);
        DataTableDefine tableDefine2 = tables.find(table2);
        return tableDefine1.getParentId() != null && !tableDefine1.getId().equals(tableDefine2.getParentId()) && !tableDefine2.getId().equals(tableDefine1.getParentId()) && tableDefine1.getParentId().equals(tableDefine2.getParentId());
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

    private ModelDataContext getContext(Model model, DataImpl dataImpl, String assignTableName, DataRow assignDataRow, Map<String, Map<String, Boolean>> triggerMap, Stream<TriggerEvent> triggerEvents) {
        ModelDataContext context = new ModelDataContext(model);
        context.put(assignTableName, assignDataRow);
        if (triggerMap == null) {
            return context;
        }
        triggerMap.forEach((tableName, rowMap) -> {
            if (rowMap == null || rowMap.isEmpty() || tableName.equals(assignTableName)) {
                return;
            }
            if (this.isChildren(dataImpl.getDefine().getTables(), (String)tableName, assignTableName)) {
                DataRow dataRow = this.getDataRowByDetail(dataImpl, assignTableName, assignDataRow, (String)tableName);
                context.put((String)tableName, dataRow);
            }
        });
        return context;
    }

    private DataRow getDataRowByDetail(DataImpl dataImpl, String assignTableName, DataRow assignDataRow, String pTableName) {
        DataTableImpl assignTable = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(assignTableName);
        DataTableImpl parentTable = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get(pTableName);
        DataTableImpl masterTable = dataImpl.getMasterTable();
        if (masterTable.getId().equals(parentTable.getId())) {
            return parentTable.getRows().stream().filter(dataRow -> dataRow.getId().equals(assignDataRow.getMasterId())).findFirst().get();
        }
        Optional<DataRowImpl> result = parentTable.getRows().stream().filter(dataRow -> dataRow.getId().equals(assignDataRow.getGroupId())).findFirst();
        if (!result.equals(Optional.empty())) {
            return result.get();
        }
        DataTableImpl dataTable = ((ListContainerImpl)((Object)dataImpl.getTables())).stream().filter(o -> o.getId().equals(assignTable.getParentId())).findFirst().get();
        DataRowImpl pTableRow = dataTable.getRows().stream().filter(dataRow -> dataRow.getId().equals(assignDataRow.getMasterId())).findFirst().get();
        this.getDataRowByDetail(dataImpl, dataTable.getName(), pTableRow, pTableName);
        return null;
    }

    public String toString() {
        return "\u516c\u5f0f\u89c4\u5219\uff1a" + this.formula.getExpression();
    }
}


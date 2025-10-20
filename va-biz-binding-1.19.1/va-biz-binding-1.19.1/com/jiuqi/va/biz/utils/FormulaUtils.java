/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.operator.TernaryOperator
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.tojs.DynamicDataNodeToJS
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.utils;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.operator.TernaryOperator;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.domain.GrammarTreeVO;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDeclare;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTarget;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ComputePropNode;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.RefDataNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.common.consts.ModelDataConsts;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.impl.RulerPluginType;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.utils.BaseDataUtils;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.tojs.DynamicDataNodeToJS;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class FormulaUtils {
    private static final List<String> EXCLUDE_FUNCTIONS = Arrays.asList("GetRefTableDataConditions");
    private static final List<String> EXCLUDE_USERTABLES = Arrays.asList("AUTH_USER", "NP_USER");
    private static final List<String> FIX_BASEDATA_FIELDS = Arrays.asList("CODE", "NAME", "TITLE", "OBJECTCODE", "SHOWTITLE");
    private static final Logger logger = LoggerFactory.getLogger(FormulaUtils.class);

    public static final Object executeFormula(FormulaImpl formulaImpl, Model model) {
        try {
            if (formulaImpl.getCompiledExpression() == null) {
                IExpression expression = ModelFormulaHandle.getInstance().parse(new ModelDataContext(model), formulaImpl.getExpression(), formulaImpl.getFormulaType());
                formulaImpl.setCompiledExpression(expression);
            }
        }
        catch (ParseException e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.parseformulaexception", new Object[]{formulaImpl.getExpression()}));
        }
        List<ModelDataContext> contexts = new FormulaRulerItem(formulaImpl).getAllContext(model, Stream.empty());
        if (contexts.size() == 0) {
            return null;
        }
        RulerImpl impl = new RulerImpl();
        if (formulaImpl.getFormulaType().equals((Object)FormulaType.EVALUATE)) {
            return impl.evaluate(FormulaUtils.reGatherContext(contexts), formulaImpl);
        }
        ArrayList<CheckResult> resuls = new ArrayList<CheckResult>();
        for (ModelDataContext context : contexts) {
            List<CheckResult> result = impl.execute(context, formulaImpl);
            resuls.addAll(result);
        }
        if (!resuls.isEmpty()) {
            throw new CheckException(formulaImpl.getTitle(), resuls);
        }
        return null;
    }

    private static ModelDataContext reGatherContext(List<ModelDataContext> contexts) {
        ModelDataContext context = new ModelDataContext(contexts.get((int)0).model);
        contexts.forEach(o -> {
            Map<String, Object> paramMap = o.getParams();
            if (paramMap != null) {
                paramMap.forEach((key, value) -> {
                    if (context.hasKey((String)key) && value instanceof DataRow) {
                        final Object contextParamValue = context.get((String)key);
                        if (contextParamValue instanceof List) {
                            ((List)contextParamValue).add((DataRow)value);
                        } else {
                            context.put((String)key, new ArrayList<DataRow>(){
                                private static final long serialVersionUID = 1L;
                                {
                                    this.add((DataRow)contextParamValue);
                                    this.add((DataRow)value);
                                }
                            });
                        }
                    } else {
                        context.put((String)key, value);
                    }
                });
            }
        });
        return context;
    }

    public static void adjustFormulaRows(Data data, Map<String, DataRow> rowMap) {
        HashMap adjustRowMap = new HashMap();
        rowMap.forEach((n, v) -> {
            DataTable parent;
            DataTable table = data.getTables().get((String)n);
            UUID parentId = table.getParentId();
            while (parentId != null && !rowMap.containsKey((parent = data.getTables().get(parentId)).getName())) {
                if (parent == data.getMasterTable()) {
                    adjustRowMap.put(parent.getName(), parent.getRowById(v.getMasterId()));
                } else {
                    adjustRowMap.put(parent.getName(), parent.getRowById(v.getGroupId()));
                }
                parentId = parent.getParentId();
            }
        });
        rowMap.putAll(adjustRowMap);
    }

    public static final Object evaluate(Model model, IExpression expression, Map<String, DataRow> rowMap) throws SyntaxException {
        ModelDataContext context = new ModelDataContext(model);
        rowMap.forEach((n, v) -> context.put((String)n, v));
        return expression.evaluate((IContext)context);
    }

    public static final void execute(Model model, IExpression expression, Map<String, DataRow> rowMap) throws SyntaxException {
        ModelDataContext context = new ModelDataContext(model);
        rowMap.forEach((n, v) -> context.put((String)n, v));
        expression.execute((IContext)context);
    }

    public static final boolean check(Model model, IExpression expression, Map<String, DataRow> rowMap) throws SyntaxException {
        ModelDataContext context = new ModelDataContext(model);
        rowMap.forEach((n, v) -> context.put((String)n, v));
        return expression.judge((IContext)context);
    }

    public static Object evaluate(String expression, Model model) {
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setExpression(expression);
        formulaImpl.setFormulaType(FormulaType.EVALUATE);
        return FormulaUtils.executeFormula(formulaImpl, model);
    }

    public static Object execute(String expression, Model model) {
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setExpression(expression);
        formulaImpl.setFormulaType(FormulaType.EXECUTE);
        return FormulaUtils.executeFormula(formulaImpl, model);
    }

    public static Object execute(FormulaImpl formula, Model model) {
        formula.setFormulaType(FormulaType.EXECUTE);
        return FormulaUtils.executeFormula(formula, model);
    }

    public static final int getRefTableType(String refTableName) {
        if (refTableName.equals("AUTH_USER")) {
            return 3;
        }
        if (refTableName.startsWith("MD_ORG_") || refTableName.equals("MD_ORG")) {
            return 4;
        }
        if (refTableName.startsWith("EM_")) {
            return 2;
        }
        if (refTableName.startsWith("MD_")) {
            return 1;
        }
        return 0;
    }

    public static final GrammarTreeVO createGrammarTree(IASTNode node) {
        GrammarTreeVO newNode = null;
        if (node instanceof BinaryOperator || node instanceof TernaryOperator || node instanceof FunctionNode) {
            IfThenElse ifThenElse;
            newNode = new GrammarTreeVO(node.getToken().toString());
            int childrenSize = node.childrenSize();
            for (int i = 0; i < childrenSize; ++i) {
                newNode.addChildren(FormulaUtils.createGrammarTree(node.getChild(i), newNode));
            }
            if (!StringUtils.hasText(newNode.getWarnMsg()) && node instanceof IfThenElse && (ifThenElse = (IfThenElse)node).getChild(2) == null) {
                newNode.setWarnMsg("if...then...else\u8bed\u6cd5\u7f3a\u5c11else\u8bed\u53e5");
            }
        } else {
            newNode = new GrammarTreeVO(node.toString());
        }
        return newNode;
    }

    private static GrammarTreeVO createGrammarTree(IASTNode node, GrammarTreeVO parent) {
        GrammarTreeVO newNode = null;
        if (node instanceof BinaryOperator || node instanceof TernaryOperator || node instanceof FunctionNode) {
            IfThenElse ifThenElse;
            newNode = new GrammarTreeVO(node.getToken().toString());
            int childrenSize = node.childrenSize();
            for (int i = 0; i < childrenSize; ++i) {
                newNode.addChildren(FormulaUtils.createGrammarTree(node.getChild(i), parent));
            }
            if (!StringUtils.hasText(parent.getWarnMsg()) && node instanceof IfThenElse && (ifThenElse = (IfThenElse)node).getChild(2) == null) {
                parent.setWarnMsg("if...then...else\u8bed\u6cd5\u7f3a\u5c11else\u8bed\u53e5");
            }
        } else {
            newNode = new GrammarTreeVO(node.toString());
        }
        return newNode;
    }

    public static void checkFormulaTable(IASTNode curNode) {
        block6: {
            String name;
            block5: {
                if (!(curNode instanceof OperatorNode)) break block5;
                int childrenSize = curNode.childrenSize();
                for (int i = 0; i < childrenSize; ++i) {
                    FormulaUtils.checkFormulaTable(curNode.getChild(i));
                }
                break block6;
            }
            if (curNode instanceof RefDataNode) {
                String tableName = ((RefDataNode)curNode).getTableName().toUpperCase();
                if (tableName.startsWith("EM_") || tableName.startsWith("MD_ORG_") || tableName.equals("MD_ORG") || EXCLUDE_USERTABLES.contains(tableName)) {
                    return;
                }
                BaseDataDefineDTO param = new BaseDataDefineDTO();
                param.setName(tableName);
                R r = ((BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class)).exist(param);
                if ((Integer)r.get((Object)"code") == 0 && ((Boolean)r.get((Object)"exist")).booleanValue()) {
                    return;
                }
                throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.notcurrdefinetable", new Object[]{tableName}));
            }
            if (!(curNode instanceof FunctionNode) || EXCLUDE_FUNCTIONS.contains(name = curNode.getToken().text())) break block6;
            int childrenSize = curNode.childrenSize();
            for (int i = 0; i < childrenSize; ++i) {
                FormulaUtils.checkFormulaTable(curNode.getChild(i));
            }
        }
    }

    public static final String toBaseDataFilterExp(String expressionValue, ModelDefine modelDefine) {
        ModelDataContext context = new ModelDataContext(modelDefine);
        StringBuilder expressionBuilder = new StringBuilder("\"");
        try {
            IExpression iExpression = ModelFormulaHandle.getInstance().parse(context, expressionValue, FormulaType.CHECK);
            expressionBuilder.append(iExpression.interpret((IContext)context, Language.FORMULA, (Object)ModelDataConsts.FormulaType.BASEDATA)).append("\"");
            return expressionBuilder.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.executeformulaexception", new Object[]{expressionValue}), e);
        }
    }

    public static final String toBaseDataFilterExp(IExpression expression, ModelDefine modelDefine) throws InterpretException {
        ModelDataContext context = new ModelDataContext(modelDefine);
        StringBuilder expressionBuilder = new StringBuilder("\"");
        expressionBuilder.append(expression.interpret((IContext)context, Language.FORMULA, (Object)ModelDataConsts.FormulaType.BASEDATA)).append("\"");
        return expressionBuilder.toString();
    }

    public static CheckResultImpl gatherCheckResult(ModelDataContext context, Formula formula, IExpression expression) {
        CheckResultImpl result = new CheckResultImpl();
        DataDefineImpl dataDefine = context.model.getDefine().getPlugins().get(DataDefineImpl.class);
        String masterTableName = ((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).getMasterTable()).getName();
        ArrayList<DataTarget> targetList = new ArrayList<DataTarget>();
        HashSet<String> targetSet = new HashSet<String>();
        result.setFormulaName(formula.getName());
        result.setCheckMessage(formula.getCheckMessage());
        result.setTargetList(targetList);
        if ("field".equals(formula.getObjectType()) && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(formula.getPropertyType())) {
            DataFieldDefineImpl field = dataDefine.getField(formula.getObjectId());
            if (Utils.isEmpty(result.getCheckMessage())) {
                if (RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT.equals(formula.getPropertyType())) {
                    result.setCheckMessage(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.inputformaterror", new Object[]{field.getTitle()}));
                } else {
                    result.setCheckMessage(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.validationfailed", new Object[]{field.getTitle()}));
                }
            }
            DataTableDefineImpl tableDefine = field.getTable();
            String tableName = tableDefine.getName();
            DataTargetImpl dataTarget = new DataTargetImpl();
            if (!field.getTable().getName().equals(masterTableName)) {
                DataRow dataRow = (DataRow)context.get(tableName);
                dataTarget.setRowID(Convert.cast(dataRow.getId(), UUID.class));
            }
            dataTarget.setFieldName(field.getName());
            dataTarget.setTableName(tableName);
            dataTarget.setTargetType(DataTargetType.DATACELL);
            targetList.add(dataTarget);
            targetSet.add(String.format("%s[%s]", field.getTable().getName(), field.getName()));
        }
        if (Utils.isEmpty(result.getCheckMessage())) {
            result.setCheckMessage(BizBindingI18nUtil.getMessage("va.bizbinding.formulautils.auditfailed"));
        }
        if ("action".equals(formula.getObjectType())) {
            String checkMessage = result.getCheckMessage();
            boolean isAggregatedNode = false;
            for (IASTNode next : expression) {
                if (next instanceof AggregatedNode) {
                    isAggregatedNode = true;
                    break;
                }
                if (!(next instanceof FunctionNode) || !AggregatedNode.class.isAssignableFrom(((FunctionNode)next).getDefine().getClass())) continue;
                isAggregatedNode = true;
                break;
            }
            for (IASTNode node : expression) {
                ComputePropNode computePropNode;
                DataRow dataRow;
                DataTargetImpl dataTarget;
                if (node.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) && node instanceof ModelNode) {
                    ModelNode modelNode = (ModelNode)node;
                    if (targetSet.contains(String.format("%s[%s]", modelNode.tableDefine.getName(), modelNode.fieldDefine.getName()))) continue;
                    dataTarget = new DataTargetImpl();
                    dataTarget.setFieldName(modelNode.fieldDefine.getName());
                    dataTarget.setTableName(modelNode.tableDefine.getName());
                    dataTarget.setTargetType(DataTargetType.DATACELL);
                    if (!masterTableName.equals(modelNode.tableDefine.getName()) && !isAggregatedNode) {
                        dataRow = (DataRow)context.get(modelNode.tableDefine.getName());
                        result.setCheckMessage(checkMessage);
                        if (dataRow != null) {
                            dataTarget.setRowID(Convert.cast(dataRow.getId(), UUID.class));
                        }
                    }
                    targetList.add(dataTarget);
                    continue;
                }
                if (!node.getNodeType().equals((Object)ASTNodeType.DYNAMICDATA) || !(node instanceof ComputePropNode) || targetSet.contains(String.format("%s[%s]", (computePropNode = (ComputePropNode)node).getTableName(), computePropNode.getFieldName()))) continue;
                dataTarget = new DataTargetImpl();
                dataTarget.setFieldName(computePropNode.getFieldName());
                dataTarget.setTableName(computePropNode.getTableName());
                dataTarget.setTargetType(DataTargetType.DATACELL);
                if (!masterTableName.equals(computePropNode.getTableName()) && !isAggregatedNode) {
                    dataRow = (DataRow)context.get(computePropNode.getTableName());
                    result.setCheckMessage(checkMessage);
                    if (dataRow != null) {
                        dataTarget.setRowID(Convert.cast(dataRow.getId(), UUID.class));
                    }
                }
                targetList.add(dataTarget);
            }
        }
        return result;
    }

    public static boolean executeFilter(Model model, IExpression expression, Map<String, DataRow> rowMap, Map<String, Object> value, String refTableName) throws SyntaxException {
        ModelDataContext context = new ModelDataContext(model);
        if (StringUtils.hasText(refTableName)) {
            DataModelDO dataModelDO = null;
            for (IASTNode iastNode : expression) {
                ValueType valueType;
                if (!(iastNode instanceof ModelParamNode)) continue;
                String fieldName = ((ModelParamNode)iastNode).getName().toUpperCase();
                if (FIX_BASEDATA_FIELDS.contains(fieldName)) {
                    context.setFieldValueType(fieldName, ValueType.STRING);
                    continue;
                }
                if (dataModelDO == null) {
                    dataModelDO = BaseDataUtils.findBaseDataDefine(refTableName);
                }
                if ((valueType = FormulaUtils.getBaseDataFieldMappingBillFieldType(dataModelDO, refTableName, fieldName)) == null) continue;
                context.setFieldValueType(fieldName, valueType);
            }
        }
        rowMap.forEach((n, v) -> context.put((String)n, v));
        value.forEach((n, v) -> context.put(n.toUpperCase(), v));
        return expression.judge((IContext)context);
    }

    public static ValueType getBaseDataFieldMappingBillFieldType(String tableName, String fieldName) {
        DataModelDO dataModelDO = BaseDataUtils.findBaseDataDefine(tableName);
        return FormulaUtils.getRefDataFieldType(dataModelDO, tableName, fieldName);
    }

    public static ValueType getBaseDataFieldMappingBillFieldType(DataModelDO dataModelDO, String refTableName, String fieldName) {
        return FormulaUtils.getRefDataFieldType(dataModelDO, refTableName, fieldName);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ValueType getRefDataFieldType(DataModelDO dataModelDO, String tableName, String fieldName) {
        ValueType valueType = null;
        if (dataModelDO == null) {
            BaseDataDefineDTO param = new BaseDataDefineDTO();
            param.setName(tableName);
            BaseDataDefineDO baseDataDefine = ((BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class)).get(param);
            if (baseDataDefine == null) {
                logger.error("\u672a\u67e5\u8be2\u5230\u5f53\u524d\u5f15\u7528\u6570\u636e\u6a21\u578b\u5b9a\u4e49:" + tableName);
                return null;
            }
            Object columns = JSONUtil.parseMap((String)baseDataDefine.getDefine()).get("columns");
            if (!ObjectUtils.isEmpty(columns)) {
                Map map;
                List columnsDefine = (List)columns;
                Iterator iterator = columnsDefine.iterator();
                do {
                    if (!iterator.hasNext()) return valueType;
                } while (!(map = (Map)iterator.next()).get("columnName").equals(fieldName));
                return FormulaUtils.getValueType(DataModelType.ColumnType.valueOf((String)((String)map.get("columnType"))));
            }
            logger.error("\u672a\u67e5\u8be2\u5230\u5f53\u524d\u5f15\u7528\u6570\u636e\u6a21\u578b\u5b9a\u4e49\u4e2d\u5b57\u6bb5\u7c7b\u578b:" + tableName);
            return null;
        }
        Optional<DataModelColumn> optional = dataModelDO.getColumns().stream().filter(o -> o.getColumnName().equals(fieldName)).findFirst();
        if (!optional.isPresent()) return valueType;
        valueType = FormulaUtils.getValueType(optional.get().getColumnType());
        if (optional.get().getMappingType() == null) return valueType;
        if (optional.get().getMappingType() != 0) return valueType;
        return ValueType.BOOLEAN;
    }

    private static ValueType getValueType(DataModelType.ColumnType columnType) {
        switch (columnType) {
            case NUMERIC: {
                return ValueType.DECIMAL;
            }
            case UUID: {
                return ValueType.IDENTIFY;
            }
            case NVARCHAR: {
                return ValueType.STRING;
            }
            case INTEGER: {
                return ValueType.INTEGER;
            }
            case DATE: {
                return ValueType.DATE;
            }
            case TIMESTAMP: {
                return ValueType.DATETIME;
            }
            case CLOB: {
                return ValueType.TEXT;
            }
        }
        return null;
    }

    public static String computePropTable(ModelDefine modelDefine, IExpression expression) {
        DataDefineImpl dataDefine = modelDefine.getPlugins().get(DataDefineImpl.class);
        Map<String, Map<String, Boolean>> assignFields = FormulaUtils.getTriggerFields(expression);
        return FormulaUtils.getPropTable(dataDefine, assignFields);
    }

    public static String getPropTable(DataDefineImpl dataDefine, Map<String, Map<String, Boolean>> assignFields) {
        String propTable;
        if (assignFields == null) {
            propTable = ((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).getMasterTable()).getName();
        } else {
            HashMap<String, DataTableDefine> tableMap = new HashMap<String, DataTableDefine>();
            for (Map.Entry<String, Map<String, Boolean>> entry : assignFields.entrySet()) {
                DataTableDefineImpl parentTableDefine;
                Map<String, Boolean> value = entry.getValue();
                String tableName = entry.getKey();
                DataTableDefineImpl curTableDefine = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).find(tableName);
                if (curTableDefine == null) continue;
                if (value == null) {
                    parentTableDefine = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).find(curTableDefine.getParentId());
                    if (parentTableDefine == null) continue;
                    tableMap.put(parentTableDefine.getName(), parentTableDefine);
                    continue;
                }
                if (value.isEmpty()) {
                    tableMap.put(curTableDefine.getName(), curTableDefine);
                    continue;
                }
                if (value.values().iterator().next().booleanValue()) {
                    tableMap.put(curTableDefine.getName(), curTableDefine);
                    continue;
                }
                parentTableDefine = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).find(curTableDefine.getParentId());
                if (parentTableDefine == null) continue;
                tableMap.put(parentTableDefine.getName(), parentTableDefine);
            }
            propTable = tableMap.size() == 0 ? ((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).getMasterTable()).getName() : FormulaUtils.getPropTable(tableMap, dataDefine);
        }
        return propTable;
    }

    public static String getPropTable(Map<String, DataTableDefine> tableMap, DataDefine dataDefine) {
        String propTable = "";
        HashSet tableNames = new HashSet();
        for (DataTableDefine o : tableMap.values()) {
            if (tableNames.contains(o.getName())) continue;
            HashSet<String> tableNames0 = new HashSet<String>();
            DataTableDefine p = o;
            while (p != null) {
                tableNames0.add(p.getName());
                p = dataDefine.getTables().find(p.getParentId());
            }
            if (tableNames0.containsAll(tableNames)) {
                tableNames = tableNames0;
                propTable = o.getName();
                continue;
            }
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.containstwosubtable"));
        }
        return propTable;
    }

    public static Map<String, Map<String, Boolean>> getTriggerFields(IExpression expression) {
        RulerFields defaultFields = RulerFields.build().noTables();
        RulerFields fields = RulerFields.build();
        try {
            HashSet aggregatedTable = new HashSet();
            expression.forEach(node -> {
                if (node instanceof FunctionNode) {
                    FunctionNode functionNode = (FunctionNode)node;
                    if (functionNode.getDefine() instanceof AggregatedNode && functionNode.getParameters().size() > 0) {
                        AggregatedNode formula = (AggregatedNode)functionNode.getDefine();
                        int aggregatedIndex = formula.getAggregatedIndex();
                        if (aggregatedIndex >= functionNode.getParameters().size()) {
                            return;
                        }
                        IASTNode iastNode = (IASTNode)functionNode.getParameters().get(aggregatedIndex);
                        if (!(iastNode instanceof ModelNode)) {
                            return;
                        }
                        ModelNode modelNode = (ModelNode)iastNode;
                        fields.field(modelNode.tableDefine.getName(), modelNode.fieldDefine.getName(), false);
                        aggregatedTable.add(modelNode.getTableName());
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
            String errorMsg = BizBindingI18nUtil.getMessage("va.bizbinding.formularuleritem.gettriggerfieldexception", new Object[]{expression}) + e.getMessage();
            logger.error(errorMsg);
            logger.error(expression.toString());
            throw new ModelException(errorMsg, e);
        }
        return fields.fields().isEmpty() ? defaultFields.fields() : fields.fields();
    }

    public static void handleCalcProps(DataDefineImpl dataDefineImpl, ModelDataContext context, ComputedPropDefineImpl computedPropDefine, FormulaImpl formula, IExpression expression, Map<String, Integer> fieldsLength) {
        String propTable = RulerPluginType.getPropTable(dataDefineImpl, (IASTNode)formula.getCompiledExpression());
        DataTableDefineImpl tableDefineImpl = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefineImpl.getTables()).get(propTable);
        DataFieldDefine[] field = new DataFieldDefine[1];
        DataFieldDeclare<DeclareHost<DataFieldDefineImpl>> fieldDeclare = new DataFieldDeclare<DeclareHost<DataFieldDefineImpl>>(declareImpl -> {
            if (fieldsLength == null) {
                tableDefineImpl.addField((DataFieldDefineImpl)declareImpl);
            } else {
                Integer integer = (Integer)fieldsLength.get(propTable);
                tableDefineImpl.addFieldByIndex((DataFieldDefineImpl)declareImpl, integer);
                fieldsLength.put(propTable, integer + 1);
            }
            field[0] = declareImpl;
        });
        ValueType fieldType = null;
        try {
            int type = expression.getType((IContext)context);
            fieldType = FormulaUtils.getValueType(type);
            computedPropDefine.setResultType(formula.getId(), type);
        }
        catch (SyntaxException e) {
            logger.error("\u8ba1\u7b97\u5c5e\u6027\u63d2\u4ef6\u521d\u59cb\u5316\u516c\u5f0f\u8fd4\u56de\u7c7b\u578b\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        fieldDeclare.setId(formula.getId());
        String fieldName = "CALC_$CMP_" + formula.getName().replaceAll("-", "").toUpperCase();
        fieldDeclare.setName(fieldName);
        fieldDeclare.setTitle("\u8ba1\u7b97\u5c5e\u6027-" + formula.getTitle());
        fieldDeclare.setFieldType(DataFieldType.CALC);
        fieldDeclare.setFieldName(null);
        fieldDeclare.setValueType(fieldType);
        fieldDeclare.endDeclare();
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setName(fieldName);
        formulaImpl.setId(Utils.normalizeId(formulaImpl.getName()));
        formulaImpl.setTitle("\u8ba1\u7b97\u5c5e\u6027-" + formula.getTitle());
        formulaImpl.setExpression(propTable + "[" + fieldName + "]=(" + formula.getExpression() + ")");
        ModelNode left = new ModelNode(null, field[0].getTable(), field[0]);
        IExpression right = formula.getCompiledExpression();
        Equal node = null;
        try {
            node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class, Boolean.TYPE).newInstance(new Object[]{null, left, right, false});
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            try {
                node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(new Object[]{null, left, right});
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        if (node == null) {
            throw new RuntimeException("com.jiuqi.bi.syntax.operator.Equal\u6784\u9020\u65b9\u6cd5\u4f20\u53c2\u9519\u8bef");
        }
        formulaImpl.setCompiledExpression((IExpression)new Expression(formulaImpl.getExpression(), node));
        formulaImpl.setFormulaType(FormulaType.EXECUTE);
        formulaImpl.setObjectType("field");
        formulaImpl.setPropertyType(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE);
        formulaImpl.setObjectId(formula.getId());
        FormulaRulerItem rulerItem = new FormulaRulerItem(formulaImpl);
        computedPropDefine.addItem(rulerItem);
        String js = DynamicDataNodeToJS.toJavaScript((String)propTable, (String)fieldName);
        computedPropDefine.addComputedProps(formula.getObjectId(), RulerPluginType.wrapValue(formula, js));
        computedPropDefine.getCalcFieldExpressionMap().put(formulaImpl.getObjectId(), formulaImpl.getCompiledExpression());
        computedPropDefine.addComputePropNodeMap(formula.getName(), new ComputePropNode(null, formula.getId(), propTable, fieldName));
    }

    public static ValueType getValueType(int dataType) {
        switch (dataType) {
            case 1: {
                return ValueType.BOOLEAN;
            }
            case 2: {
                return ValueType.DATETIME;
            }
            case 33: {
                return ValueType.IDENTIFY;
            }
            case 6: {
                return ValueType.STRING;
            }
            case -1: {
                return ValueType.AUTO;
            }
        }
        return ValueType.AUTO;
    }
}


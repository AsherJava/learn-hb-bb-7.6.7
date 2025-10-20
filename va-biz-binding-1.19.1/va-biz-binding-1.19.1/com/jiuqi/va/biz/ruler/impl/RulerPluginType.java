/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.formula.common.exception.ToFilterException
 *  com.jiuqi.va.formula.common.exception.ToJavaScriptException
 *  com.jiuqi.va.formula.function.model.GetRefTableDataFieldFunction
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.formula.intf.TableFieldNode
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 *  com.jiuqi.va.formula.tofilter.FilterHandle
 *  com.jiuqi.va.formula.tojs.DynamicDataNodeToJS
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.front.FrontFunctionManager;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDeclare;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ComputePropNode;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.EditableFieldsDefineImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.JavaScriptHandle;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.FormulaFeatures;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.function.model.GetRefTableDataFieldFunction;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import com.jiuqi.va.formula.tofilter.FilterHandle;
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
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class RulerPluginType
extends PluginTypeBase {
    public static final String NAME = "ruler";
    public static final String TITLE = "\u89c4\u5219";
    private static final Logger log = LoggerFactory.getLogger(RulerPluginType.class);

    public Class<? extends RulerDefineImpl> getPluginDefineClass() {
        return RulerDefineImpl.class;
    }

    public Class<? extends RulerImpl> getPluginClass() {
        return RulerImpl.class;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine, List<Formula> externalFormulas) {
        super.initPluginDefine(pluginDefine, modelDefine);
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        if (externalFormulas != null && externalFormulas.size() > 0) {
            rulerDefine.addAllFormula(externalFormulas.stream().map(o -> (FormulaImpl)o).collect(Collectors.toList()));
        }
        if (!modelDefine.isInit()) {
            DataDefineImpl dataDefineImpl = modelDefine.getPlugins().get(DataDefineImpl.class);
            this.handleEditableFieldsPluginData(modelDefine, rulerDefine, dataDefineImpl);
            this.initPluginDefineCommon(modelDefine, dataDefineImpl, rulerDefine);
        }
    }

    @Override
    public void pluginDefineLoaded(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.pluginDefineLoaded(pluginDefine, modelDefine);
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        ArrayList<RulerItem> itemList = new ArrayList<RulerItem>(rulerDefine.getItemList());
        DataDefineImpl dataDefine = modelDefine.getPlugins().get(DataDefineImpl.class);
        if (((ListContainerImpl)((Object)dataDefine.getTables())).size() == 0) {
            return;
        }
        itemList.forEach(item -> {
            if (item instanceof FormulaRulerItem) {
                item.getTriggerFields(modelDefine);
                item.getAssignFields(modelDefine);
                return;
            }
            String objectType = item.getObjectType();
            String propertyType = item.getPropertyType();
            UUID objectId = item.getObjectId();
            if (!StringUtils.hasText(objectType) || !StringUtils.hasText(propertyType) || objectId == null) {
                return;
            }
            if (!"control".equals(objectType) && !"table".equals(objectType)) {
                return;
            }
            String propTable = this.getPrpoTableByTriggerFileds(modelDefine, (RulerItem)item, dataDefine);
            Set<String> funcProps = this.getFuncProps(modelDefine, (RulerItem)item, dataDefine);
            DataTableDefineImpl tableDefineImpl = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).get(propTable);
            DataFieldDefine[] field = new DataFieldDefine[1];
            DataFieldDeclare<DeclareHost<DataFieldDefineImpl>> fieldDeclare = new DataFieldDeclare<DeclareHost<DataFieldDefineImpl>>(declareImpl -> {
                tableDefineImpl.addField((DataFieldDefineImpl)declareImpl);
                field[0] = declareImpl;
            });
            fieldDeclare.setId(item.getObjectId());
            String fieldName = "CALC_" + item.getName().replace("-", "").toUpperCase();
            fieldDeclare.setName(fieldName);
            fieldDeclare.setTitle("\u8ba1\u7b97\u5b57\u6bb5-" + item.getTitle());
            fieldDeclare.setFieldType(DataFieldType.CALC);
            fieldDeclare.setFieldName(null);
            fieldDeclare.setValueType(ValueType.AUTO);
            fieldDeclare.endDeclare();
            FormulaImpl formulaImpl = new FormulaImpl();
            formulaImpl.setName("CALC_" + item.getName().replace("-", "").toUpperCase());
            formulaImpl.setId(Utils.normalizeId(formulaImpl.getName()));
            formulaImpl.setTitle("\u8ba1\u7b97\u5b57\u6bb5-" + item.getTitle());
            if (funcProps.size() > 0) {
                if ("control".equals(objectType)) {
                    formulaImpl.setExpression("if CheckActionExit('" + objectId + "') = true then ExecuteRuler('" + item.getName() + "', " + StringUtils.arrayToDelimitedString(funcProps.toArray(), ",") + ")");
                } else {
                    formulaImpl.setExpression("ExecuteRuler('" + item.getName() + "', " + StringUtils.arrayToDelimitedString(funcProps.toArray(), ",") + ")");
                }
            } else if ("control".equals(objectType)) {
                formulaImpl.setExpression("if CheckActionExit('" + objectId + "') = true then ExecuteRuler('" + item.getName() + "')");
            } else {
                formulaImpl.setExpression("ExecuteRuler('" + item.getName() + "')");
            }
            ModelNode left = new ModelNode(null, field[0].getTable(), field[0]);
            IExpression right = null;
            try {
                right = ModelFormulaHandle.getInstance().parse(new ModelDataContext(modelDefine), formulaImpl.getExpression(), FormulaType.EVALUATE);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            Equal node = null;
            try {
                node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class, Boolean.TYPE).newInstance(new Object[]{null, left, right, false});
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                try {
                    node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(new Object[]{null, left, right});
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
            if (node == null) {
                throw new RuntimeException("com.jiuqi.bi.syntax.operator.Equal\u6784\u9020\u65b9\u6cd5\u4f20\u53c2\u9519\u8bef");
            }
            formulaImpl.setCompiledExpression((IExpression)new Expression(formulaImpl.getExpression(), node));
            formulaImpl.setFormulaType(FormulaType.EXECUTE);
            formulaImpl.setObjectType("field");
            formulaImpl.setPropertyType(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE);
            formulaImpl.setObjectId(item.getObjectId());
            FormulaRulerItem rulerItem = new FormulaRulerItem(formulaImpl);
            rulerDefine.addItem(rulerItem);
            String js = DynamicDataNodeToJS.toJavaScript((String)propTable, (String)fieldName);
            rulerDefine.addProp(item.getObjectId(), item.getPropertyType(), js);
            rulerDefine.getCalcFieldExpressionMap().put(formulaImpl.getObjectId(), formulaImpl.getCompiledExpression());
        });
    }

    private Set<String> getFuncProps(ModelDefine modelDefine, RulerItem item, DataDefineImpl dataDefine) {
        HashSet<String> params = new HashSet<String>();
        Map<String, Map<String, Boolean>> assignFields = item.getTriggerFields(modelDefine);
        if (assignFields == null) {
            return params;
        }
        for (Map.Entry<String, Map<String, Boolean>> entry : assignFields.entrySet()) {
            Map<String, Boolean> value = entry.getValue();
            String tableName = entry.getKey();
            DataTableDefineImpl curTableDefine = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).find(tableName);
            if (curTableDefine == null || value == null || value.isEmpty()) continue;
            if (value.values().iterator().next().booleanValue()) {
                for (Map.Entry<String, Boolean> fieldEntry : value.entrySet()) {
                    params.add(String.format("%s[%s]", tableName, fieldEntry.getKey()));
                }
                continue;
            }
            for (Map.Entry<String, Boolean> fieldEntry : value.entrySet()) {
                params.add(String.format("%s[%s, FIRST]", tableName, fieldEntry.getKey()));
            }
        }
        return params;
    }

    private String getPrpoTableByTriggerFileds(ModelDefine modelDefine, RulerItem item, DataDefineImpl dataDefine) {
        Map<String, Map<String, Boolean>> assignFields = item.getTriggerFields(modelDefine);
        return FormulaUtils.getPropTable(dataDefine, assignFields);
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        if (!modelDefine.isInit()) {
            DataDefineImpl dataDefineImpl = modelDefine.getPlugins().get(DataDefineImpl.class);
            this.handleEditableFieldsPluginData(modelDefine, rulerDefine, dataDefineImpl);
            this.initPluginDefineCommon(modelDefine, dataDefineImpl, rulerDefine);
        }
    }

    private void handleEditableFieldsPluginData(ModelDefine modelDefine, RulerDefineImpl rulerDefine, DataDefineImpl dataDefineImpl) {
        EditableFieldsDefineImpl editFieldsDefine = modelDefine.getPlugins().find(EditableFieldsDefineImpl.class);
        if (editFieldsDefine != null && editFieldsDefine.getDefineInfo() != null && editFieldsDefine.getDefineInfo().get("config") != null) {
            List config = (List)editFieldsDefine.getDefineInfo().get("config");
            if (config.size() == 0) {
                return;
            }
            ListContainer<FormulaImpl> formulas = rulerDefine.getFormulas();
            Map<UUID, FormulaImpl> formulaMap = formulas.stream().filter(f -> f.getObjectType() != null && f.getPropertyType() != null && "field".equals(f.getObjectType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_READONLY.equals(f.getPropertyType())).collect(Collectors.toMap(FormulaImpl::getObjectId, o -> o));
            ArrayList fiels = new ArrayList();
            HashMap tableFields = new HashMap();
            HashMap formulaNameMap = new HashMap();
            List<DataTableDefineImpl> tableDefines = dataDefineImpl.getTableList();
            for (DataTableDefineImpl tableDefine : tableDefines) {
                ArrayList fields = new ArrayList();
                tableFields.put(tableDefine.getName(), fields);
                ((NamedContainerImpl)tableDefine.getFields()).forEachName((name, define) -> {
                    UUID id = define.getId();
                    fields.add(id);
                    fiels.add(id);
                    if (!formulaMap.containsKey(id) && define.isReadonly()) {
                        return;
                    }
                    formulaNameMap.put(id, tableDefine.getName() + "_" + name + "_" + RulerConsts.FORMULA_OBJECT_PROP_FIELD_READONLY.toUpperCase());
                });
            }
            HashMap fieldFormulas = new HashMap();
            ComputedPropDefineImpl computedPropDefine = modelDefine.getPlugins().find(ComputedPropDefineImpl.class);
            for (Map prop : config) {
                List uuids;
                List fields;
                Object formulaObj = prop.get("formula");
                Object fieldsObj = prop.get("fields");
                if (ObjectUtils.isEmpty(formulaObj) || ObjectUtils.isEmpty(fieldsObj) || (fields = (List)prop.get("fields")).size() == 0) continue;
                IExpression parse = null;
                try {
                    parse = ModelFormulaHandle.getInstance().parse(new ModelDataContext(modelDefine), formulaObj.toString(), FormulaType.EVALUATE);
                }
                catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                String propTable = RulerPluginType.getPropTable(dataDefineImpl, (IASTNode)parse);
                ArrayList<UUID> ids = new ArrayList<UUID>();
                for (Map field : fields) {
                    Object id = field.get("id");
                    if (ObjectUtils.isEmpty(id)) continue;
                    UUID uid = Convert.cast(id, UUID.class);
                    if (!fiels.contains(uid)) {
                        throw new RuntimeException(String.format("\u521d\u59cb\u5316\u53ef\u7f16\u8f91\u5b57\u6bb5\u5931\u8d25\uff0c%s[%s]\u4e0d\u5b58\u5728", field.get("tableName"), field.get("name")));
                    }
                    ids.add(uid);
                }
                String formula = (String)formulaObj;
                if (!RulerPluginType.validateProp(formula)) {
                    String contains = computedPropDefine.contains(formula);
                    formula = contains != null ? "PROP_[" + contains + "]" : "PROP_[" + this.setNameCalcProps(formula, computedPropDefine, parse, modelDefine, dataDefineImpl, null) + "]";
                }
                String finalFormula = formula;
                if ("editable".equals(prop.get("mode"))) {
                    if (((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefineImpl.getTables()).getMasterTable()).getName().equals(propTable)) {
                        fiels.stream().filter(o -> !ids.contains(o) && formulaNameMap.containsKey(o)).forEach(o -> fieldFormulas.computeIfAbsent(o, v -> new ArrayList()).add(finalFormula));
                        continue;
                    }
                    uuids = (List)tableFields.get(propTable);
                    uuids.stream().filter(o -> !ids.contains(o) && formulaNameMap.containsKey(o)).forEach(o -> fieldFormulas.computeIfAbsent(o, v -> new ArrayList()).add(finalFormula));
                    continue;
                }
                if (((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefineImpl.getTables()).getMasterTable()).getName().equals(propTable)) {
                    ids.stream().filter(o -> formulaNameMap.containsKey(o)).forEach(o -> fieldFormulas.computeIfAbsent(o, v -> new ArrayList()).add(finalFormula));
                    continue;
                }
                uuids = (List)tableFields.get(propTable);
                ids.stream().filter(o -> formulaNameMap.containsKey(o) && uuids.contains(o)).forEach(o -> fieldFormulas.computeIfAbsent(o, v -> new ArrayList()).add(finalFormula));
            }
            ArrayList<FormulaImpl> newFormulas = new ArrayList<FormulaImpl>();
            for (Map.Entry entry : fieldFormulas.entrySet()) {
                String expression;
                UUID id = (UUID)entry.getKey();
                List formulaStrs = (List)entry.getValue();
                if (formulaStrs.size() == 0) continue;
                FormulaImpl formula = formulaMap.get(id);
                if (formula == null) {
                    formula = new FormulaImpl();
                    formula.setUsed(true);
                    formula.setId(UUID.randomUUID());
                    formula.setObjectId(id);
                    formula.setName((String)formulaNameMap.get(id));
                    formula.setObjectType("field");
                    formula.setFormulaType(FormulaType.EVALUATE);
                    formula.setPropertyType(RulerConsts.FORMULA_OBJECT_PROP_FIELD_READONLY);
                    expression = (String)formulaStrs.get(0);
                    for (int i = 1; i < formulaStrs.size(); ++i) {
                        expression = expression + " \uf066 " + (String)formulaStrs.get(i);
                    }
                    formula.setExpression(expression);
                    newFormulas.add(formula);
                    continue;
                }
                if (!formula.isUsed()) {
                    expression = (String)formulaStrs.get(0);
                    for (int i = 1; i < formulaStrs.size(); ++i) {
                        expression = expression + " \uf066 " + (String)formulaStrs.get(i);
                    }
                    formula.setUsed(true);
                } else {
                    expression = formula.getExpression();
                    for (String formulaStr : formulaStrs) {
                        expression = expression + " \uf066 " + formulaStr;
                    }
                }
                formula.setExpression(expression);
            }
            rulerDefine.addAllFormula(newFormulas);
        }
    }

    private static boolean validateProp(String prop) {
        Pattern pattern = Pattern.compile("PROP_\\[\\s*\\w+\\s*\\]");
        Matcher matcher = pattern.matcher(prop);
        return matcher.matches();
    }

    private void deleteErrorFormula(RulerDefineImpl rulerDefine) {
        List<FormulaImpl> formulas = rulerDefine.getFormulaList();
        Iterator<FormulaImpl> iterator = formulas.iterator();
        while (iterator.hasNext()) {
            FormulaImpl formula = iterator.next();
            if (!"backWrite".equals(formula.getObjectType()) || !"field-property".equals(formula.getPropertyType())) continue;
            iterator.remove();
        }
    }

    private void initPluginDefineCommon(ModelDefine modelDefine, DataDefineImpl dataDefineImpl, RulerDefineImpl rulerDefine) {
        this.deleteErrorFormula(rulerDefine);
        Map<String, FormulaFeatures> beansOfType = ApplicationContextRegister.getApplicationContext().getBeansOfType(FormulaFeatures.class);
        beansOfType.values().stream().forEach(o -> {
            List<? extends Formula> declareFormulas = o.declareFormulas(modelDefine);
            if (declareFormulas != null) {
                rulerDefine.addAllFormula(declareFormulas);
            }
        });
        Map<UUID, DataFieldDefineImpl> fieldMap = dataDefineImpl.getTableList().stream().flatMap(o -> ((NamedContainerImpl)o.getFields()).stream()).collect(Collectors.toMap(o -> o.getId(), o -> o));
        ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
        ModelDataContext context = new ModelDataContext(modelDefine);
        rulerDefine.getFormulas().stream().forEach(o -> {
            if (!o.isUsed() || ObjectUtils.isEmpty(o.getExpression())) {
                return;
            }
            try {
                IExpression expression = o.getExpression().contains("\uf066") ? this.handleOr(o.getExpression(), handle, context, (FormulaImpl)o) : handle.parse(context, o.getExpression(), o.getFormulaType());
                if ("field".equals(o.getObjectType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(o.getPropertyType())) {
                    DataFieldDefineImpl field = (DataFieldDefineImpl)fieldMap.get(o.getObjectId());
                    o.setExpression(field.getTable().getName() + "[" + field.getName() + "]=(" + o.getExpression() + ")");
                    ModelNode left = new ModelNode(null, field.getTable(), field);
                    IExpression right = expression;
                    Equal node = null;
                    try {
                        node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class, Boolean.TYPE).newInstance(new Object[]{null, left, right, false});
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                        try {
                            node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(new Object[]{null, left, right});
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e1) {
                            log.error(e1.getMessage(), e1);
                        }
                    }
                    if (node == null) {
                        throw new RuntimeException("com.jiuqi.bi.syntax.operator.Equal\u6784\u9020\u65b9\u6cd5\u4f20\u53c2\u9519\u8bef");
                    }
                    expression = new Expression(o.getExpression(), node);
                    o.setFormulaType(FormulaType.EXECUTE);
                }
                o.setCompiledExpression(expression);
                if ("filterCondition".equals(o.getPropertyType())) {
                    o.setPropTable(FormulaUtils.computePropTable(modelDefine, expression));
                }
            }
            catch (ParseException e) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.parseformulafailed") + o.getExpression(), e);
            }
            catch (Exception e) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.fieldformulaexception") + o.getExpression(), e);
            }
        });
        rulerDefine.initObjectFormulaMap();
        this.initCalcProp(context, modelDefine, dataDefineImpl, rulerDefine);
    }

    private IExpression handleOr(String expression, ModelFormulaHandle handle, ModelDataContext context, FormulaImpl o) {
        String[] split = expression.split("\uf066");
        Or or = null;
        Expression result = null;
        try {
            IExpression left = handle.parse(context, split[0], o.getFormulaType());
            IExpression right = handle.parse(context, split[1], o.getFormulaType());
            or = (Or)Or.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(null, left.getChild(0), right.getChild(0));
            for (int i = 2; i < split.length; ++i) {
                IExpression newRight = handle.parse(context, split[i], o.getFormulaType());
                or = (Or)Or.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(null, or, newRight.getChild(0));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (or == null) {
            throw new RuntimeException("com.jiuqi.bi.syntax.operator.OR\u6784\u9020\u65b9\u6cd5\u4f20\u53c2\u9519\u8bef");
        }
        result = new Expression(null, or);
        return result;
    }

    private void initCalcProp(ModelDataContext context, ModelDefine modelDefine, DataDefineImpl dataDefineImpl, RulerDefineImpl rulerDefine) {
        ArrayList validateFormulaList = new ArrayList();
        ComputedPropDefineImpl computedPropDefine = modelDefine.getPlugins().find(ComputedPropDefineImpl.class);
        HashMap<String, Integer> fieldsLength = new HashMap<String, Integer>();
        DataDefineImpl dataDefine = modelDefine.getPlugins().find(DataDefineImpl.class);
        for (DataTableDefineImpl dataTableDefine : dataDefine.getTableList()) {
            fieldsLength.put(dataTableDefine.getTableName(), ((NamedContainerImpl)dataTableDefine.getFields()).size());
        }
        rulerDefine.getFormulas().stream().forEach(formula -> {
            if (!formula.isUsed()) {
                return;
            }
            if (formula.isExtend()) {
                return;
            }
            if ("wizard".equals(formula.getObjectType()) && !StringUtils.hasText(formula.getExpression())) {
                return;
            }
            if ("field".equals(formula.getObjectType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(formula.getPropertyType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("table".equals(formula.getObjectType()) && ("initRows".equals(formula.getPropertyType()) || "enableFilter".equals(formula.getPropertyType()) || "filterCondition".equals(formula.getPropertyType()))) {
                return;
            }
            if ("backWrite".equals(formula.getObjectType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("event".equals(formula.getObjectType()) && ("AfterSetValue".equals(formula.getPropertyType()) || "AfterDelRow".equals(formula.getPropertyType()) || "AfterAddRow".equals(formula.getPropertyType()))) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("action".equals(formula.getObjectType()) && "before".equals(formula.getPropertyType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("eventFormula".equals(formula.getObjectType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("control".equals(formula.getObjectType()) && "action".equals(formula.getPropertyType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                rulerDefine.addItem(rulerItem);
                return;
            }
            if ("table".equals(formula.getObjectType()) && "condition".equals(formula.getPropertyType())) {
                return;
            }
            if (formula.getFormulaType() == FormulaType.FILTER) {
                String js;
                DataFieldDefineImpl field;
                try {
                    field = dataDefineImpl.getField(formula.getObjectId());
                }
                catch (Exception e) {
                    log.error(String.format("\u8fc7\u6ee4\u516c\u5f0f\u3010%s\u3011\u5bf9\u5e94\u5b57\u6bb5\u4e0d\u5b58\u5728", formula.getExpression()), e);
                    return;
                }
                try {
                    js = FilterHandle.toFilter((IContext)context, (IASTNode)formula.getCompiledExpression(), null);
                }
                catch (ToFilterException e) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.generateexpressionfailed") + formula.getExpression(), e);
                }
                rulerDefine.addProp(formula.getObjectId(), formula.getPropertyType(), js);
                if (field.getFilterChangeOpt() != 2) {
                    FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                    rulerDefine.addItem(rulerItem);
                    Map<String, Map<String, Boolean>> triggerFields = rulerItem.getTriggerFields(modelDefine);
                    if (triggerFields != null) {
                        triggerFields.forEach((tableName, fields) -> {
                            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefineImpl.getTables()).find((String)tableName);
                            if (dataTableDefine == null) {
                                return;
                            }
                            fields.keySet().forEach(fieldName -> {
                                DataFieldDefineImpl fieldDefine = (DataFieldDefineImpl)((NamedContainerImpl)dataTableDefine.getFields()).find((String)fieldName);
                                if (fieldDefine == null) {
                                    return;
                                }
                                this.addFieldFilterMap(rulerDefine, (String)tableName, (String)fieldName, (FormulaImpl)formula);
                            });
                        });
                    }
                }
                return;
            }
            if ("field".equals(formula.getObjectType()) && Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT).contains(formula.getPropertyType())) {
                FormulaRulerItem rulerItem = new FormulaRulerItem((FormulaImpl)formula);
                validateFormulaList.add(rulerItem);
            }
            if (!"single".equals(formula.getPropertyType()) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_MASK.equals(formula.getPropertyType()) && this.canToJs((IASTNode)formula.getCompiledExpression())) {
                String js;
                try {
                    if (ObjectUtils.isEmpty(formula.getCompiledExpression())) {
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.executeformulaerror", new Object[]{formula.getTitle()}));
                    }
                    js = JavaScriptHandle.toJavaScript((IASTNode)formula.getCompiledExpression(), formula.getFormulaType());
                }
                catch (ToJavaScriptException e) {
                    throw new ModelException(e);
                }
                rulerDefine.addProp(formula.getObjectId(), formula.getPropertyType(), RulerPluginType.wrapValue(formula, js));
                return;
            }
            String propTable = RulerPluginType.getPropTable(dataDefineImpl, (IASTNode)formula.getCompiledExpression());
            final DataTableDefineImpl tableDefineImpl = (DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefineImpl.getTables()).get(propTable);
            final DataFieldDefine[] field = new DataFieldDefine[1];
            DataFieldDeclare<1> fieldDeclare = new DataFieldDeclare<1>(new DeclareHost<DataFieldDefineImpl>(){

                @Override
                public void accept(DataFieldDefineImpl declareImpl) {
                    tableDefineImpl.addField(declareImpl);
                    field[0] = declareImpl;
                }
            });
            fieldDeclare.setId(formula.getId());
            String fieldName = "CALC_" + formula.getName().replace("-", "").toUpperCase();
            fieldDeclare.setName(fieldName);
            fieldDeclare.setTitle("\u8ba1\u7b97\u5b57\u6bb5-" + formula.getTitle());
            fieldDeclare.setFieldType(DataFieldType.CALC);
            fieldDeclare.setFieldName(null);
            fieldDeclare.setValueType(ValueType.AUTO);
            fieldDeclare.endDeclare();
            FormulaImpl formulaImpl = new FormulaImpl();
            formulaImpl.setName("CALC_" + formula.getName().replace("-", "").toUpperCase());
            formulaImpl.setId(Utils.normalizeId(formulaImpl.getName()));
            formulaImpl.setTitle("\u8ba1\u7b97\u5b57\u6bb5-" + formula.getTitle());
            formulaImpl.setExpression(propTable + "[" + fieldName + "]=(" + formula.getExpression() + ")");
            ModelNode left = new ModelNode(null, field[0].getTable(), field[0]);
            IExpression right = formula.getCompiledExpression();
            try {
                this.handleExpression(right, modelDefine, computedPropDefine, fieldsLength);
            }
            catch (Exception e) {
                log.error("\u81ea\u52a8\u8f6c\u8ba1\u7b97\u5c5e\u6027\u5f02\u5e38\uff1a" + formula.getName(), e);
            }
            Equal node = null;
            try {
                node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class, Boolean.TYPE).newInstance(new Object[]{null, left, right, false});
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                try {
                    node = (Equal)Equal.class.getConstructor(Token.class, IASTNode.class, IASTNode.class).newInstance(new Object[]{null, left, right});
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e1) {
                    log.error(e1.getMessage(), e1);
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
            rulerDefine.addItem(rulerItem);
            String js = DynamicDataNodeToJS.toJavaScript((String)propTable, (String)fieldName);
            rulerDefine.addProp(formula.getObjectId(), formula.getPropertyType(), RulerPluginType.wrapValue(formula, js));
            rulerDefine.getCalcFieldExpressionMap().put(formulaImpl.getObjectId(), formulaImpl.getCompiledExpression());
            if (RulerConsts.FORMULA_OBJECT_PROP_FIELD_MASK.equals(formula.getPropertyType())) {
                UUID objectId = formula.getObjectId();
                DataFieldDefineImpl maskField = dataDefineImpl.getField(objectId);
                maskField.setMaskFlag(true);
                rulerDefine.addMaskField(formula.getId(), maskField.getTable().getName(), maskField.getName());
                dataDefineImpl.getMaskFieldTableMap().putIfAbsent(objectId, propTable);
            }
        });
        validateFormulaList.forEach(o -> rulerDefine.addItem((RulerItem)o));
    }

    private void handleExpression(IExpression parse, ModelDefine modelDefine, ComputedPropDefineImpl computedPropDefine, Map<String, Integer> fieldsLength) {
        DataDefineImpl dataDefineImpl = modelDefine.getPlugins().get(DataDefineImpl.class);
        this.checkFunctionIsCalc(modelDefine, computedPropDefine, dataDefineImpl, parse.getChild(0), 0, (IASTNode)parse, fieldsLength);
    }

    private void checkFunctionIsCalc(ModelDefine modelDefine, ComputedPropDefineImpl computedPropDefine, DataDefineImpl dataDefineImpl, IASTNode expre, int index, IASTNode parent, Map<String, Integer> fieldsLength) {
        for (int i = 0; i < expre.childrenSize(); ++i) {
            IASTNode child = expre.getChild(i);
            if (!(child instanceof FunctionNode) && !(child instanceof OperatorNode)) continue;
            this.checkFunctionIsCalc(modelDefine, computedPropDefine, dataDefineImpl, expre.getChild(i), i, expre, fieldsLength);
        }
        if (expre instanceof FunctionNode) {
            IASTNode child;
            IFunction define = ((FunctionNode)expre).getDefine();
            if (!(define instanceof ModelFunction)) {
                return;
            }
            if (!((ModelFunction)define).autoOptimize() && expre.childrenSize() != 0) {
                return;
            }
            boolean canCompute = true;
            if (define instanceof GetRefTableDataFieldFunction && expre.childrenSize() > 2) {
                return;
            }
            for (int i = 0; i < expre.childrenSize() && (canCompute = this.checkChildNode(child = expre.getChild(i))); ++i) {
            }
            if (canCompute) {
                this.functionToCalc(modelDefine, computedPropDefine, dataDefineImpl, parent, index, expre, fieldsLength);
            }
        }
    }

    private boolean checkChildNode(IASTNode child) {
        boolean canCompute = true;
        if (!(child instanceof TableFieldNode || child instanceof ModelDataNode || child instanceof DataNode)) {
            if (child instanceof OperatorNode) {
                IASTNode child1;
                for (int i = 0; i < child.childrenSize() && (canCompute = this.checkChildNode(child1 = child.getChild(i))); ++i) {
                }
            } else {
                canCompute = false;
            }
        }
        return canCompute;
    }

    private void functionToCalc(ModelDefine modelDefine, ComputedPropDefineImpl computedPropDefine, DataDefineImpl dataDefineImpl, IASTNode parent, int i, IASTNode node, Map<String, Integer> fieldsLength) {
        String nodeToString = node.toString();
        String contains = computedPropDefine.contains(nodeToString);
        if (contains != null) {
            ComputePropNode right = computedPropDefine.getComputePropNodeMap(contains);
            parent.setChild(i, (IASTNode)right);
        } else {
            String propExpression = this.setNameCalcProps(nodeToString, computedPropDefine, (IExpression)new Expression(nodeToString, node), modelDefine, dataDefineImpl, fieldsLength);
            ComputePropNode right = computedPropDefine.getComputePropNodeMap(propExpression);
            parent.setChild(i, (IASTNode)right);
        }
    }

    private String setNameCalcProps(String formula, ComputedPropDefineImpl computedPropDefine, IExpression parse, ModelDefine modelDefine, DataDefineImpl dataDefineImpl, Map<String, Integer> fieldsLength) {
        UUID nameUid = UUID.nameUUIDFromBytes(formula.getBytes());
        String name = nameUid.toString().replace("-", "").toUpperCase();
        computedPropDefine.getCalcFieldExpressionMap().put(nameUid, parse);
        ModelDataContext context = new ModelDataContext(modelDefine);
        computedPropDefine.addBackCalc(name, formula);
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setId(nameUid);
        formulaImpl.setObjectType("field");
        formulaImpl.setObjectId(nameUid);
        formulaImpl.setName(name);
        formulaImpl.setCompiledExpression(parse);
        formulaImpl.setTitle(name);
        formulaImpl.setExpression(formula);
        computedPropDefine.addItem(formulaImpl);
        FormulaUtils.handleCalcProps(dataDefineImpl, context, computedPropDefine, formulaImpl, parse, fieldsLength);
        return name;
    }

    public static String getPropTable(DataDefine dataDefine, IASTNode expression) {
        HashMap<String, DataTableDefine> tableMap = new HashMap<String, DataTableDefine>();
        try {
            HashSet nodes = new HashSet();
            expression.forEach(node -> {
                if (nodes.contains(node)) {
                    return;
                }
                if (node instanceof ModelDataNode) {
                    DataTableDefine tableDefine = ((ModelDataNode)node).getModelNode().tableDefine;
                    if (tableDefine != null) {
                        tableDefine = dataDefine.getTables().find(tableDefine.getParentId());
                    }
                    if (tableDefine != null) {
                        tableMap.put(tableDefine.getName(), tableDefine);
                    }
                    return;
                }
                if (node instanceof ModelNode) {
                    ModelNode modelNode = (ModelNode)((Object)node);
                    tableMap.put(modelNode.tableDefine.getName(), modelNode.tableDefine);
                } else if (node instanceof ComputePropNode) {
                    ComputePropNode computePropNode = (ComputePropNode)((Object)node);
                    tableMap.put(computePropNode.getTableName(), dataDefine.getTables().find(computePropNode.getTableName()));
                } else if (node instanceof FunctionNode && ((FunctionNode)node).getParameters().size() > 0) {
                    ModelFunction modelFunction = null;
                    for (ModelFunction function : ModelFunctionProvider.getFunctions()) {
                        if (!node.getToken().toString().equals(function.name()) || !(function instanceof AggregatedNode)) continue;
                        modelFunction = function;
                    }
                    if (modelFunction == null) {
                        return;
                    }
                    int index = ((AggregatedNode)modelFunction).getAggregatedIndex();
                    FunctionNode functionNode = (FunctionNode)node;
                    if (!(functionNode.getParameters().get(index) instanceof DataNode)) {
                        return;
                    }
                    try {
                        String tableName = (String)((IASTNode)functionNode.getParameters().get(index)).evaluate(null);
                        DataTableDefine tableDefine = dataDefine.getTables().find(tableName);
                        if (tableDefine != null) {
                            tableDefine = dataDefine.getTables().find(tableDefine.getParentId());
                        }
                        if (tableDefine != null) {
                            tableMap.put(tableDefine.getName(), tableDefine);
                        }
                        node.forEach(o -> nodes.add(o));
                        return;
                    }
                    catch (SyntaxException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            });
        }
        catch (Exception e) {
            throw new ModelException(e);
        }
        if (tableMap.size() == 0) {
            return dataDefine.getTables().getMasterTable().getName();
        }
        return FormulaUtils.getPropTable(tableMap, dataDefine);
    }

    private boolean canToJs(IASTNode expression) {
        Object[] result = new Object[1];
        try {
            if (expression == null) {
                return result[0] == null;
            }
            expression.forEach(node -> {
                if (result[0] != null) {
                    return;
                }
                if (node instanceof FunctionNode && !FrontFunctionManager.isFrontFunction(((FunctionNode)node).getDefine().name())) {
                    result[0] = false;
                    return;
                }
                if (node instanceof AggregatedNode) {
                    result[0] = false;
                    return;
                }
            });
        }
        catch (Exception e) {
            throw new ModelException(e);
        }
        return result[0] == null;
    }

    public static Object wrapValue(FormulaImpl formula, String js) {
        Object value;
        String message = formula.getCheckMessage();
        if (Utils.isNotEmpty(message)) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("js", js);
            map.put("message", message);
            value = map;
        } else {
            value = js;
        }
        return value;
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
    }

    @Override
    public String[] getDependPlugins() {
        return new String[]{"data"};
    }

    public void addFieldFilterMap(RulerDefineImpl rulerDefine, String tableName, String fieldName, FormulaImpl formula) {
        List formulas = rulerDefine.getFieldFilterMap().computeIfAbsent(tableName + "|" + fieldName, k -> new ArrayList());
        formulas.add(formula);
    }
}


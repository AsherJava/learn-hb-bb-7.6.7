/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ComputePropNode;
import com.jiuqi.va.biz.ruler.ModelDataNode;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.RefDataNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.common.consts.ModelDataConsts;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.FunRefDataDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerPluginType;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BaseDataUtils;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ModelNodeProvider
implements IReportDynamicNodeProvider {
    private static final Logger logger = LoggerFactory.getLogger(ModelNodeProvider.class);

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        return new ModelParamNode(token, refName);
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        DynamicNode modelNode;
        if (objPath.size() != 2) {
            throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.modelnodeprovider.unknownsyntaxnode") + token);
        }
        String tableName = objPath.get(0).toUpperCase(Locale.ROOT);
        String fieldName = objPath.get(1).toUpperCase(Locale.ROOT);
        ModelDataContext dataContext = (ModelDataContext)context;
        if (tableName.equalsIgnoreCase("PROP_")) {
            ModelDefine modelDefine = dataContext.modelDefine;
            ComputedPropDefineImpl computeProp = (ComputedPropDefineImpl)modelDefine.getPlugins().get("computedProp");
            ComputePropNode computePropNodeMap = computeProp.getComputePropNodeMap(fieldName);
            if (computePropNodeMap != null) {
                return computePropNodeMap;
            }
            List collect = computeProp.getFormulas().stream().filter(formula -> fieldName.equalsIgnoreCase(formula.getName())).collect(Collectors.toList());
            if (collect.isEmpty()) {
                throw new DynamicNodeException("\u672a\u77e5\u7684\u8ba1\u7b97\u5c5e\u6027\u6807\u8bc6");
            }
            Formula formula2 = (Formula)collect.get(0);
            UUID id = formula2.getId();
            String field = "CALC_$CMP_" + formula2.getName().replaceAll("-", "").toUpperCase();
            IExpression expression = computeProp.getCalcFieldExpressionMap().get(id);
            if (expression == null) {
                ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
                try {
                    expression = handle.parse(dataContext, formula2.getExpression(), formula2.getFormulaType());
                }
                catch (ParseException e) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.parseformulafailed") + formula2.getExpression(), e);
                }
            }
            DataDefineImpl dataDefineImpl = modelDefine.getPlugins().get(DataDefineImpl.class);
            String propTable = RulerPluginType.getPropTable(dataDefineImpl, (IASTNode)expression);
            return new ComputePropNode(token, id, propTable, field);
        }
        if (dataContext.modelDefine == null) {
            DataModelColumn column = BaseDataUtils.findRefObjectColumn(tableName, fieldName);
            RefDataNode modelNode2 = new RefDataNode(token, new FunRefDataDefineImpl(tableName, fieldName, column.getColumnType(), column.getMappingType()));
            return modelNode2;
        }
        DataDefine dataDefine = dataContext.modelDefine.getPlugins().get(DataDefineImpl.class);
        DataTableDefine tableDefine = dataDefine.getTables().find(tableName);
        if (tableDefine != null) {
            DataFieldDefine fieldDefine = tableDefine.getFields().find(fieldName);
            if (fieldDefine == null) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, fieldName));
            }
            modelNode = new ModelNode(token, tableDefine, fieldDefine);
        } else {
            DataModelColumn column = BaseDataUtils.findRefObjectColumn(tableName, fieldName);
            modelNode = new RefDataNode(token, new FunRefDataDefineImpl(tableName, fieldName, column.getColumnType(), column.getMappingType()));
        }
        return modelNode;
    }

    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) {
        return null;
    }

    private IASTNode getConditionNode(List<IASTNode> specs, ModelDataContext cxt) throws SyntaxException {
        if (specs.size() > 1) {
            DataNode conditionNode = (DataNode)specs.get(1);
            String conditionExpression = String.valueOf(conditionNode.evaluate((IContext)cxt));
            String condition = conditionExpression.substring(1, conditionExpression.length() - 1);
            if (!StringUtils.hasText(condition)) {
                throw new SyntaxException(BizBindingI18nUtil.getMessage("va.bizbinding.modelnodeprovider.filterexpressionempty"));
            }
            return ModelFormulaHandle.getInstance().parse(cxt, condition, FormulaType.CHECK);
        }
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> specs) throws DynamicNodeException {
        String tableName = objPath.get(0);
        String refName = objPath.get(1);
        tableName = tableName.toUpperCase(Locale.ROOT);
        String upRefName = refName.toUpperCase(Locale.ROOT);
        ModelDataContext cxt = Convert.cast(context, ModelDataContext.class);
        DataDefine dataDefine = Objects.requireNonNull(cxt).modelDefine.getPlugins().get(DataDefineImpl.class);
        Object modelNode = (IASTNode)Convert.cast(this.find(context, token, Arrays.asList(tableName, upRefName)), ModelNode.class);
        try {
            DataTableDefine tableDefine = dataDefine.getTables().get(tableName);
            DataFieldDefine fieldDefine = tableDefine.getFields().get(upRefName);
            if (fieldDefine == null) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, upRefName));
            }
            modelNode = new ModelNode(token, dataDefine.getTables().get(tableName), fieldDefine);
        }
        catch (MissingObjectException e) {
            DataModelDO modelDO = BaseDataUtils.findBaseDataDefine(tableName);
            if (modelDO == null) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntabledefine") + tableName);
            }
            Optional<DataModelColumn> result = modelDO.getColumns().stream().filter(o -> o.getColumnName().equals(upRefName)).findFirst();
            if (result.equals(Optional.empty())) {
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.basedatautils.unknowntablefield") + String.format("%s[%s]", tableName, upRefName));
            }
            DataModelType.ColumnType dataType = result.get().getColumnType();
            Integer mappingType = result.get().getMappingType();
            modelNode = new RefDataNode(token, new FunRefDataDefineImpl(tableName, upRefName, dataType, mappingType));
        }
        DataNode dataNode = (DataNode)specs.get(0);
        if (6 == dataNode.getType(context)) {
            try {
                String countType = String.valueOf(dataNode.evaluate(context));
                if (!StringUtils.hasText(countType)) {
                    return modelNode;
                }
                countType = countType.toUpperCase();
                if (modelNode instanceof ModelNode && "ALL".equals(countType)) {
                    ((ModelNode)((Object)modelNode)).setRemoveRepeat(false);
                    return modelNode;
                }
                Class<? extends ModelDataNode> modelDataNodeClass = ModelDataConsts.modelDataNodeMap.get(countType);
                if (modelDataNodeClass != null) {
                    int nodeType = modelNode.getType(context);
                    if (!(3 == nodeType || countType.equals("FIRST") || countType.equals("LIST") || 10 == nodeType || 0 == nodeType || "COUNT".equals(countType))) {
                        throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.modelnodeprovider.cannotbesummed", new Object[]{tableName, upRefName}));
                    }
                    IASTNode conditionNode = this.getConditionNode(specs, cxt);
                    Set<String> paramNodes = null;
                    try {
                        paramNodes = this.getParamNodes(conditionNode);
                    }
                    catch (Exception e) {
                        paramNodes = new HashSet<String>();
                        logger.error(e.getMessage(), e);
                    }
                    DataTableDefine dataTableDefine = dataDefine.getTables().get(tableName);
                    NamedContainer<? extends DataFieldDefine> fields = dataTableDefine.getFields();
                    HashMap<String, ValueType> paramNodesType = new HashMap<String, ValueType>();
                    for (String paramNode : paramNodes) {
                        DataFieldDefine dataFieldDefine = fields.get(paramNode);
                        paramNodesType.put(tableName + "_" + paramNode, dataFieldDefine.getValueType());
                    }
                    return modelDataNodeClass.getConstructor(Token.class, ModelNode.class, String.class, IASTNode.class, Map.class).newInstance(token, modelNode, countType, conditionNode, paramNodesType);
                }
                throw new DynamicNodeException(BizBindingI18nUtil.getMessage("va.bizbinding.modelnodeprovider.unrecognizedparam", new Object[]{countType}));
            }
            catch (Exception e) {
                throw new DynamicNodeException(e.getMessage(), (Throwable)e);
            }
        }
        return modelNode;
    }

    private Set<String> getParamNodes(IASTNode conditionNode) {
        if (conditionNode == null) {
            return new HashSet<String>();
        }
        HashSet<String> result = new HashSet<String>();
        conditionNode.forEach(node -> {
            if (node instanceof ModelParamNode) {
                result.add(((ModelParamNode)((Object)node)).getName());
            }
        });
        return result;
    }
}


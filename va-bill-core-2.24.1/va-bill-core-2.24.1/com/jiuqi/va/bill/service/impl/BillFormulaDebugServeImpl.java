/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.TernaryOperator
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.CountDataNode
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.ModelNode
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckException
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.ruler.intf.RulerConsts
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  org.springframework.dao.DuplicateKeyException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.TernaryOperator;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.dao.BillFormulaDebugContextDao;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugContextDO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugContextVO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugInfoVO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugVO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugWhiteListDO;
import com.jiuqi.va.bill.domain.debug.BillFormulaWhiteListConst;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.service.BillFormulaDebugService;
import com.jiuqi.va.bill.service.BillFormulaDebugWhiteListService;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BillFormulaDebugServeImpl
implements BillFormulaDebugService {
    private static final Logger logger = LoggerFactory.getLogger(BillFormulaDebugServeImpl.class);
    @Autowired
    private BillFormulaDebugContextDao billFormulaDebugContextDao;
    @Autowired
    private BillFormulaDebugWhiteListService billFormulaDebugWhiteListService;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private ModelManager modelManager;

    @Override
    public BillFormulaDebugContextVO queryContext(String defineCode) {
        Map stringListMap;
        UUID uuid = UUID.nameUUIDFromBytes(defineCode.getBytes());
        BillFormulaDebugContextDO query = new BillFormulaDebugContextDO();
        query.setId(uuid.toString());
        List select = this.billFormulaDebugContextDao.select((Object)query);
        if (CollectionUtils.isEmpty(select)) {
            return null;
        }
        BillFormulaDebugContextDO billFormulaDebugContextDO = (BillFormulaDebugContextDO)((Object)select.get(0));
        String contextData = billFormulaDebugContextDO.getContextdata();
        BillFormulaDebugContextVO billFormulaDebugContextVO = new BillFormulaDebugContextVO();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            stringListMap = (Map)objectMapper.readValue(contextData, (TypeReference)new TypeReference<Map<String, List<Map<String, Object>>>>(){});
        }
        catch (JsonProcessingException e) {
            logger.error("json\u89e3\u6790\u5931\u8d25" + e.getMessage(), e);
            return null;
        }
        billFormulaDebugContextVO.setContextData(stringListMap);
        billFormulaDebugContextVO.setDefineCode(defineCode);
        return billFormulaDebugContextVO;
    }

    @Override
    @Transactional
    public boolean saveContext(BillFormulaDebugContextVO billFormulaDebugContextVO) {
        int i;
        String defineCode = billFormulaDebugContextVO.getDefineCode();
        if (!StringUtils.hasText(defineCode)) {
            return false;
        }
        String uuid = UUID.nameUUIDFromBytes(defineCode.getBytes()).toString();
        BillFormulaDebugContextDO query = new BillFormulaDebugContextDO();
        query.setId(uuid);
        List select = this.billFormulaDebugContextDao.select((Object)query);
        query.setContextdata(JSONUtil.toJSONString((Object)billFormulaDebugContextVO.getContextData()));
        if (CollectionUtils.isEmpty(select)) {
            try {
                i = this.billFormulaDebugContextDao.insert((Object)query);
            }
            catch (DuplicateKeyException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        } else {
            i = this.billFormulaDebugContextDao.updateByPrimaryKey((Object)query);
        }
        return i != 0;
    }

    @Override
    public List<BillFormulaDebugVO> getRuleDebugInfo(BillFormulaDebugVO billFormulaDebugVO) {
        String defineCode = billFormulaDebugVO.getDefineCode();
        List<FormulaImpl> formulas = billFormulaDebugVO.getFormulas();
        if (CollectionUtils.isEmpty(formulas)) {
            return new ArrayList<BillFormulaDebugVO>();
        }
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(ShiroUtil.getTenantName());
        ModelFormulaHandle instance = ModelFormulaHandle.getInstance();
        IExpression iExpression = null;
        ArrayList<BillFormulaDebugVO> result = new ArrayList<BillFormulaDebugVO>();
        ModelDefine billDefine = billFormulaDebugVO.getModelDefine();
        String billType = billDefine.getModelType();
        ModelType modelType = (ModelType)this.modelManager.find(billType);
        modelType.initModelDefine(billDefine, billDefine.getName());
        BillModelImpl model = (BillModelImpl)this.modelDefineService.createModel((ModelContext)context, billDefine);
        model.getData().create();
        BillFormulaDebugContextVO billFormulaDebugContextVO = this.queryContext(billFormulaDebugVO.getDefineCode());
        if (billFormulaDebugContextVO == null) {
            return null;
        }
        Map oldContextData = (Map)billFormulaDebugContextVO.getContextData();
        ModelDataContext formulaContext = new ModelDataContext((Model)model);
        model.getData().setTablesData(oldContextData);
        for (FormulaImpl formula : billFormulaDebugVO.getFormulas()) {
            String propTable;
            model.getData().getTables().stream().forEach(table -> table.getRows().stream().forEach(row -> {
                Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(o -> table.getDefine().getName(), o -> o));
                FormulaUtils.adjustFormulaRows((Data)model.getData(), rowMap);
                rowMap.forEach((arg_0, arg_1) -> ((ModelDataContext)formulaContext).put(arg_0, arg_1));
            }));
            if (FormulaType.FILTER.equals((Object)formula.getFormulaType())) {
                BillFormulaDebugInfoVO billFormulaDebugInfoVO = new BillFormulaDebugInfoVO(formula.getExpression(), "\u8fc7\u6ee4\u516c\u5f0f\u4e0d\u652f\u6301\u8c03\u8bd5");
                result.add(new BillFormulaDebugVO(defineCode, billFormulaDebugInfoVO, formula.getTitle(), formula.getFormulaType().name()));
                continue;
            }
            try {
                iExpression = instance.parse(formulaContext, formula.getExpression(), formula.getFormulaType());
            }
            catch (ParseException e) {
                BillFormulaDebugInfoVO billFormulaDebugInfoVO = new BillFormulaDebugInfoVO(formula.getExpression(), "\u8c03\u8bd5\u516c\u5f0f\u89e3\u6790\u5931\u8d25" + e.getMessage());
                result.add(new BillFormulaDebugVO(defineCode, billFormulaDebugInfoVO, formula.getTitle(), formula.getFormulaType().name()));
                logger.error("\u8c03\u8bd5\u516c\u5f0f\u89e3\u6790\u5931\u8d25" + e.getMessage(), e);
                continue;
            }
            boolean[] whiteFormula = new boolean[]{false};
            ArrayList whiteFormulaName = new ArrayList();
            iExpression.forEach(curNode -> {
                String name;
                if (curNode instanceof FunctionNode && ((FunctionNode)curNode).getDefine() instanceof ModelFunction && !BillFormulaWhiteListConst.exist(name = curNode.getToken().toString()) && !((ModelFunction)((FunctionNode)curNode).getDefine()).enableDebug()) {
                    BillFormulaDebugWhiteListDO billFormulaDebugWhiteListDO = new BillFormulaDebugWhiteListDO();
                    billFormulaDebugWhiteListDO.setFormulaname(name);
                    boolean b = this.billFormulaDebugWhiteListService.checkExist(billFormulaDebugWhiteListDO);
                    if (!b) {
                        if (!whiteFormulaName.contains(name)) {
                            whiteFormulaName.add(name);
                        }
                        whiteFormula[0] = true;
                    }
                }
            });
            if (whiteFormula[0]) {
                BillFormulaDebugInfoVO billFormulaDebugInfoVO = new BillFormulaDebugInfoVO(formula.getExpression(), StringUtils.collectionToCommaDelimitedString(whiteFormulaName) + "\u51fd\u6570\u4e0d\u53ef\u8c03\u8bd5\uff0c\u8bf7\u7814\u53d1\u786e\u8ba4\u52a0\u5165\u767d\u540d\u5355");
                result.add(new BillFormulaDebugVO(defineCode, billFormulaDebugInfoVO, formula.getTitle(), formula.getFormulaType().name()));
                continue;
            }
            DataDefineImpl dataDefine = (DataDefineImpl)model.getDefine().getPlugins().get(DataDefineImpl.class);
            if (RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(formula.getPropertyType())) {
                DataTableDefine tableName = null;
                DataFieldDefine fieldName = null;
                for (int i = 0; i < dataDefine.getTables().size(); ++i) {
                    for (int i1 = 0; i1 < ((DataTableDefineImpl)dataDefine.getTables().get(i)).getFields().size(); ++i1) {
                        if (!formula.getObjectId().equals(((DataFieldDefineImpl)((DataTableDefineImpl)dataDefine.getTables().get(i)).getFields().get(i1)).getId())) continue;
                        tableName = (DataTableDefine)dataDefine.getTableList().get(i);
                        fieldName = (DataFieldDefine)((DataTableDefineImpl)dataDefine.getTableList().get(i)).getFields().get(i1);
                        break;
                    }
                    if (tableName != null) break;
                }
                Equal equal = new Equal();
                equal.setChild(0, (IASTNode)new ModelNode(null, tableName, fieldName));
                equal.setChild(1, iExpression.getChild(0));
                iExpression = new Expression(equal.toString(), (IASTNode)equal);
            }
            if (!(propTable = FormulaUtils.computePropTable((ModelDefine)model.getDefine(), (IExpression)iExpression)).equals(model.getMasterTable().getName())) {
                ListContainer rows = ((DataTableImpl)model.getData().getTables().get(propTable)).getRows();
                IExpression finalIExpression = iExpression;
                ArrayList<BillFormulaDebugVO> childResult = new ArrayList<BillFormulaDebugVO>();
                BillFormulaDebugVO detailResult = new BillFormulaDebugVO();
                detailResult.setTitle(formula.getTitle());
                detailResult.setFormulaType(formula.getFormulaType().name());
                result.add(detailResult);
                rows.stream().forEach(row -> {
                    Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(o -> propTable, o -> o));
                    FormulaUtils.adjustFormulaRows((Data)model.getData(), rowMap);
                    rowMap.forEach((arg_0, arg_1) -> ((ModelDataContext)formulaContext).put(arg_0, arg_1));
                    this.assembleFormulaData(formula, finalIExpression, formulaContext, defineCode, childResult);
                });
                detailResult.setChildren(childResult);
                continue;
            }
            this.assembleFormulaData(formula, iExpression, formulaContext, defineCode, result);
        }
        return result;
    }

    private void assembleFormulaData(FormulaImpl formula, IExpression iExpression, ModelDataContext formulaContext, String defineCode, List<BillFormulaDebugVO> result) {
        BillFormulaDebugInfoVO billFormulaDebugInfoVO = new BillFormulaDebugInfoVO();
        billFormulaDebugInfoVO.setOutLine(formula.getExpression());
        IASTNode child = iExpression.getChild(0);
        if (FormulaType.EXECUTE.equals((Object)formula.getFormulaType()) || RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(formula.getPropertyType())) {
            if (child.getChild(0) instanceof ModelNode) {
                this.executeFormulaDebugInfo(formulaContext, child, billFormulaDebugInfoVO, formula.getExpression());
                result.add(new BillFormulaDebugVO(defineCode, billFormulaDebugInfoVO, formula.getTitle(), formula.getFormulaType().name()));
                return;
            }
            if (child instanceof IfThenElse) {
                // empty if block
            }
        }
        BillFormulaDebugInfoVO children = new BillFormulaDebugInfoVO();
        result.add(new BillFormulaDebugVO(defineCode, children, formula.getTitle(), formula.getFormulaType().name()));
        billFormulaDebugInfoVO.addChildren(children);
        this.evaluateFormulaDebugInfo(formulaContext, child, children, formula.getExpression(), formula.getFormulaType().name(), !FormulaType.EXECUTE.equals((Object)formula.getFormulaType()));
    }

    private void executeFormulaDebugInfo(ModelDataContext formulaContext, IASTNode child, BillFormulaDebugInfoVO billFormulaDebugInfoVO, String expression) {
        BillFormulaDebugInfoVO left = new BillFormulaDebugInfoVO();
        left.setOutLine(String.valueOf(child.getChild(0)));
        this.execute(left, formulaContext, child.getChild(0));
        BillFormulaDebugInfoVO right = new BillFormulaDebugInfoVO();
        right.setOutLine(String.valueOf(child.getChild(1)));
        billFormulaDebugInfoVO.addChildren(left);
        billFormulaDebugInfoVO.addChildren(right);
        this.evaluateFormulaDebugInfo(formulaContext, child.getChild(1), right, expression, null, true);
        billFormulaDebugInfoVO.setOutLine(left.getOutLine());
        billFormulaDebugInfoVO.setRuleOutPut(right.getRuleOutPut());
        billFormulaDebugInfoVO.setOutType(right.getOutType());
        Expression iastNodes = new Expression(expression, child);
        try {
            iastNodes.execute((IContext)formulaContext);
        }
        catch (SyntaxException e) {
            billFormulaDebugInfoVO.setMessage("\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u5931\u8d25\uff1a" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }

    private void evaluateFormulaDebugInfo(ModelDataContext context, IASTNode curNode, BillFormulaDebugInfoVO info, String expression, String formulaType, boolean executeFlag) {
        if (formulaType == null || !formulaType.equals(FormulaType.EXECUTE.name()) || executeFlag) {
            this.execute(info, context, curNode);
        }
        if (curNode instanceof BinaryOperator || curNode instanceof TernaryOperator || curNode instanceof FunctionNode) {
            info.setOutLine(curNode.toString());
            int childrenSize = curNode.childrenSize();
            if (curNode instanceof IfThenElse) {
                for (int i = 0; i < childrenSize; ++i) {
                    BillFormulaDebugInfoVO children = new BillFormulaDebugInfoVO();
                    info.addChildren(children);
                    this.evaluateFormulaDebugInfo(context, curNode.getChild(i), children, expression, formulaType, i == 0);
                }
            } else {
                for (int i = 0; i < childrenSize; ++i) {
                    BillFormulaDebugInfoVO children = new BillFormulaDebugInfoVO();
                    info.addChildren(children);
                    this.evaluateFormulaDebugInfo(context, curNode.getChild(i), children, expression, formulaType, true);
                }
            }
        } else if (curNode instanceof ModelNode || curNode instanceof CountDataNode) {
            info.setOutLine(curNode.toString());
            try {
                info.setOutType(this.getOutType(curNode.getType((IContext)context)));
            }
            catch (Exception e) {
                info.setMessage(e.getMessage());
                logger.error(e.getMessage(), e);
            }
        } else {
            info.setOutLine(curNode.toString());
            try {
                info.setOutType(this.getOutType(curNode.getType((IContext)context)));
            }
            catch (Exception e) {
                info.setMessage(e.getMessage());
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void execute(BillFormulaDebugInfoVO info, ModelDataContext context, IASTNode curNode) {
        try {
            long l = System.currentTimeMillis();
            Object evaluate = curNode.evaluate((IContext)context);
            if (evaluate instanceof ArrayData) {
                info.setRuleOutPut(evaluate.toString());
            } else {
                info.setRuleOutPut(evaluate == null ? "\u7a7a" : evaluate);
            }
            info.setTime(System.currentTimeMillis() - l);
            info.setOutType(this.getOutType(curNode.getType((IContext)context)));
        }
        catch (CheckException e) {
            List checkMessages = e.getCheckMessages();
            BillModelImpl model = (BillModelImpl)context.model;
            if (model == null) {
                info.setMessage("\u5f53\u524d\u6a21\u578b\u4e3a\u7a7a");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (CheckResult checkMessage : checkMessages) {
                Stream targetList = checkMessage.getTargetList();
                HashMap tableFieldMap = new HashMap();
                targetList.forEach(dataTarget -> {
                    UUID rowID = dataTarget.getRowID();
                    String tableName = dataTarget.getTableName();
                    DataTableImpl dataTable = (DataTableImpl)model.getData().getTables().get(tableName);
                    String tableTitle = dataTable.getTitle();
                    int index = 0;
                    for (DataRowImpl dataRow : dataTable.getRowList()) {
                        if (!rowID.equals(dataRow.getId())) continue;
                        index = dataRow.getIndex();
                        break;
                    }
                    tableFieldMap.put(tableTitle, index + 1);
                });
                for (String tableTitle : tableFieldMap.keySet()) {
                    sb.append(tableTitle);
                    sb.append("\u7b2c").append(tableFieldMap.get(tableTitle)).append("\u884c");
                }
                sb.append(checkMessage.getCheckMessage());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            info.setMessage(sb.toString());
        }
        catch (Exception e) {
            info.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }

    private String getOutType(int nodeType) {
        return DataType.toString((int)nodeType);
    }

    @Override
    public List<FormulaImpl> queryRelationRules(BillFormulaDebugVO billFormulaDebugVO) {
        if (billFormulaDebugVO == null || CollectionUtils.isEmpty(billFormulaDebugVO.getFormulas())) {
            return new ArrayList<FormulaImpl>();
        }
        FormulaImpl formula = billFormulaDebugVO.getFormulas().get(0);
        ModelDefine modelDefine = billFormulaDebugVO.getModelDefine();
        ModelDataContext context = new ModelDataContext(modelDefine);
        IExpression iExpression = this.parseExpression(context, formula);
        RulerDefineImpl ruler = (RulerDefineImpl)modelDefine.getPlugins().get("ruler");
        List formulaList = ruler.getFormulaList();
        DataDefineImpl data = (DataDefineImpl)modelDefine.getPlugins().get("data");
        DataTableNodeContainerImpl tables = data.getTables();
        ArrayList<FormulaImpl> relations = new ArrayList<FormulaImpl>();
        if (iExpression == null) {
            return relations;
        }
        IASTNode child = iExpression.getChild(0);
        HashMap<String, List<String>> tableFieldMap = new HashMap<String, List<String>>();
        if (RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(formula.getPropertyType())) {
            tables.forEach((i, table) -> table.getFields().forEachIndex((j, field) -> {
                if (field.getId().equals(formula.getObjectId())) {
                    tableFieldMap.computeIfAbsent(table.getTableName(), k -> new ArrayList()).add(field.getFieldName());
                }
            }));
        }
        if (FormulaType.EXECUTE.equals((Object)formula.getFormulaType())) {
            if (child.getChild(0) instanceof ModelNode) {
                IASTNode child1 = child.getChild(1);
                this.addRelationRule((DataTableNodeContainerImpl<? extends DataTableDefineImpl>)tables, formulaList, relations, child1, context, tableFieldMap);
            }
        } else {
            this.addRelationRule((DataTableNodeContainerImpl<? extends DataTableDefineImpl>)tables, formulaList, relations, child, context, tableFieldMap);
        }
        HashSet uniqueIds = new HashSet();
        return relations.stream().filter(o -> uniqueIds.add(o.getId())).collect(Collectors.toList());
    }

    private IExpression parseExpression(ModelDataContext context, FormulaImpl formula) {
        IExpression iExpression;
        try {
            iExpression = ModelFormulaHandle.getInstance().parse(context, formula.getExpression(), formula.getFormulaType());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return iExpression;
    }

    private void addRelationRule(DataTableNodeContainerImpl<? extends DataTableDefineImpl> tables, List<FormulaImpl> formulaList, List<FormulaImpl> relations, IASTNode child, ModelDataContext context, Map<String, List<String>> tableFieldMap) {
        block6: {
            block5: {
                if (!(child instanceof BinaryOperator) && !(child instanceof TernaryOperator) && !(child instanceof FunctionNode)) break block5;
                int childrenSize = child.childrenSize();
                for (int i = 0; i < childrenSize; ++i) {
                    this.addRelationRule(tables, formulaList, relations, child.getChild(i), context, tableFieldMap);
                }
                break block6;
            }
            if (!(child instanceof ModelNode) && !(child instanceof CountDataNode)) break block6;
            ModelNode modelNode = child instanceof ModelNode ? (ModelNode)child : ((CountDataNode)child).getModelNode();
            String tableName = modelNode.getTableName();
            String fieldName = modelNode.getFieldName();
            List strings = tableFieldMap.computeIfAbsent(tableName, k -> new ArrayList());
            boolean contains = strings.contains(fieldName);
            if (contains) {
                return;
            }
            strings.add(fieldName);
            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)tables.get(tableName);
            DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)dataTableDefine.getFields().get(fieldName);
            UUID id = dataFieldDefine.getId();
            List collect = formulaList.stream().filter(f -> f.getObjectId().equals(id) && f.isUsed() && RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALUE.equals(f.getPropertyType()) || f.isUsed() && this.checkExecuteNode((FormulaImpl)f, context, tableName, fieldName)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                return;
            }
            relations.addAll(collect);
            for (FormulaImpl formula : collect) {
                IExpression iastNodes = this.parseExpression(context, formula);
                if (iastNodes == null || iastNodes.childrenSize() == 0) {
                    return;
                }
                IASTNode child1 = iastNodes.getChild(0);
                this.addRelationRule(tables, formulaList, relations, child1, context, tableFieldMap);
            }
        }
    }

    private boolean checkExecuteNode(FormulaImpl f, ModelDataContext context, String tableName, String fieldName) {
        if (FormulaType.EXECUTE.equals((Object)f.getFormulaType())) {
            IExpression iastNodes = this.parseExpression(context, f);
            if (iastNodes == null) {
                return false;
            }
            boolean[] result = new boolean[]{false};
            IASTNode child = iastNodes.getChild(0);
            child.forEach(o -> {
                IASTNode child1;
                if (o instanceof Equal && (child1 = o.getChild(0)) instanceof ModelNode) {
                    result[0] = ((ModelNode)child1).tableDefine.getTableName().equals(tableName) && ((ModelNode)child1).fieldDefine.getFieldName().equals(fieldName);
                }
            });
            return result[0];
        }
        return false;
    }
}


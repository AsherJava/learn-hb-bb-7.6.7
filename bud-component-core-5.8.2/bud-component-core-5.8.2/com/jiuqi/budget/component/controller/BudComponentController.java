/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.budget.common.utils.BeanCopyUtil
 *  com.jiuqi.budget.components.ProductNameUtil
 *  com.jiuqi.budget.domain.BudTableModelDefineVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.component.controller;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.budget.common.utils.BeanCopyUtil;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.domain.FunParameter;
import com.jiuqi.budget.component.domain.FunctionNode;
import com.jiuqi.budget.component.domain.FunctionVO;
import com.jiuqi.budget.component.domain.SimpleObject;
import com.jiuqi.budget.component.exception.FormulaParseException;
import com.jiuqi.budget.component.monitor.AdaptCheckMonitor;
import com.jiuqi.budget.components.ProductNameUtil;
import com.jiuqi.budget.domain.BudTableModelDefineVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/component"})
public class BudComponentController {
    @Autowired
    DataModelService dataModelService;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Value(value="${jiuqi.gmc3.component.enableGroup:true}")
    boolean enableGroup;
    private static final String DATATYPE = "dataType";

    @PostMapping(value={"/baseDataDefine/getFields"})
    public R getBaseDataFields(@RequestBody FormulaExeParam formulaExeParam) {
        String code = formulaExeParam.getCode();
        if (!StringUtils.hasLength(code)) {
            return R.error((String)"code\u4e0d\u80fd\u4e3a\u7a7a");
        }
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode(code);
        if (tableDefine == null) {
            return R.error((String)("\u672a\u627e\u5230\u8868" + code + "\u5bf9\u5e94\u7684\u8868\u5b9a\u4e49"));
        }
        List allFieldsInTable = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        ConcurrentHashMap<String, List> map = new ConcurrentHashMap<String, List>(1);
        map.put("data", allFieldsInTable);
        return R.ok(map);
    }

    @GetMapping(value={"/function/list"})
    public FunctionVO getAllFuncList() {
        FunctionVO functionVO = new FunctionVO();
        ArrayList<ITree<FunctionNode>> rootList = new ArrayList<ITree<FunctionNode>>();
        Iterator iterator = ReportFunctionProvider.GLOBAL_PROVIDER.iterator();
        ArrayList<Function> functionList = new ArrayList<Function>();
        while (iterator.hasNext()) {
            functionList.add((Function)iterator.next());
        }
        HashMap<String, ITree> parentItemMap = new HashMap<String, ITree>(16);
        HashMap<String, ITree> groupItemMap = new HashMap<String, ITree>(16);
        ArrayList<String> keyWords = new ArrayList<String>();
        for (IFunction iFunction : functionList) {
            ITree categoryItem;
            ITree groupItem;
            String category = iFunction.category();
            String group = "\u5168\u5c40\u516c\u5f0f";
            String typeName = iFunction.getClass().getTypeName();
            if (typeName.startsWith("com.jiuqi.budget") && this.enableGroup) {
                group = ProductNameUtil.getProductName() + "\u516c\u5f0f";
            }
            if ((groupItem = (ITree)groupItemMap.get(group)) == null) {
                groupItem = new ITree();
                groupItemMap.put(group, groupItem);
                if (group.equals(ProductNameUtil.getProductName() + "\u516c\u5f0f")) {
                    rootList.add(0, (ITree<FunctionNode>)groupItem);
                } else {
                    rootList.add((ITree<FunctionNode>)groupItem);
                }
                groupItem.setKey(group);
                groupItem.setCode(group);
                groupItem.setTitle(group);
                HashMap<String, Object> attributes = new HashMap<String, Object>(1);
                attributes.put(DATATYPE, "GROUP");
                groupItem.setData((INode)new FunctionNode());
                ((FunctionNode)groupItem.getData()).setAttributes(attributes);
            }
            if ((categoryItem = (ITree)parentItemMap.get(category)) == null) {
                categoryItem = new ITree();
                parentItemMap.put(category, categoryItem);
                categoryItem.setKey(category);
                categoryItem.setCode(category);
                categoryItem.setTitle(category);
                HashMap<String, Object> attributes = new HashMap<String, Object>(1);
                attributes.put(DATATYPE, "GROUP");
                categoryItem.setData((INode)new FunctionNode());
                ((FunctionNode)categoryItem.getData()).setAttributes(attributes);
                groupItem.appendChild(categoryItem);
            }
            ITree functionItem = new ITree();
            functionItem.setKey(iFunction.name());
            functionItem.setCode(iFunction.name());
            functionItem.setTitle(iFunction.title() + "(" + iFunction.name() + ")");
            HashMap<String, Object> attributes = new HashMap<String, Object>(8);
            attributes.put("name", iFunction.title());
            attributes.put("description", iFunction.toDescription());
            String functionStr = this.getParamsStr(iFunction.name(), iFunction.parameters());
            keyWords.add(functionStr.toLowerCase());
            attributes.put("func", functionStr);
            attributes.put(DATATYPE, "FUNC");
            try {
                int resultType = iFunction.getResultType(null, null);
                attributes.put("resultType", this.getDataType(resultType));
            }
            catch (SyntaxException e) {
                throw new FormulaParseException("\u516c\u5f0f\u8fd4\u56de\u7c7b\u578b\u9519\u8bef!");
            }
            ArrayList<FunParameter> parameterList = new ArrayList<FunParameter>(iFunction.parameters().size());
            if (!iFunction.parameters().isEmpty()) {
                for (IParameter param : iFunction.parameters()) {
                    FunParameter funParameter = new FunParameter();
                    funParameter.setDataType(this.getDataType(param.dataType()));
                    funParameter.setName(param.name());
                    funParameter.setOmitable(param.isOmitable());
                    funParameter.setTitle(param.title());
                    parameterList.add(funParameter);
                }
            }
            attributes.put("parameterList", parameterList);
            functionItem.setData((INode)new FunctionNode());
            ((FunctionNode)functionItem.getData()).setAttributes(attributes);
            functionItem.setLeaf(true);
            categoryItem.appendChild(functionItem);
        }
        for (ITree iTree : rootList) {
            iTree.setExpanded(true);
        }
        functionVO.setFunctionTree(rootList);
        functionVO.setKeyWords(keyWords);
        return functionVO;
    }

    @PostMapping(value={"/function/parseFormula"})
    public R parseFormula(@RequestBody FormulaExeParam formulaExeParam) {
        ArrayList<Formula> formulaList = new ArrayList<Formula>();
        Formula formula = new Formula();
        formula.setFormula(formulaExeParam.getFormulaExpress());
        formulaList.add(formula);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        String checkErrorMsg = null;
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(1);
        try {
            AdaptCheckMonitor adaptCheckMonitor = new AdaptCheckMonitor();
            DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.EXPRESSION, (IMonitor)adaptCheckMonitor);
            if (StringUtils.hasLength(adaptCheckMonitor.getFormulaCheckResult())) {
                checkErrorMsg = adaptCheckMonitor.getFormulaCheckResult();
            }
        }
        catch (ParseException e) {
            checkErrorMsg = e.getMessage();
        }
        if (StringUtils.hasLength(checkErrorMsg)) {
            map.put("resultInfo", checkErrorMsg);
        }
        return R.ok(map);
    }

    @GetMapping(value={"/org/category/list"})
    public R listCategory() {
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setTenantName(NpContextHolder.getContext().getTenant());
        List orgCategoryList = this.orgCategoryClient.list(orgCatDTO).getRows();
        List simpleObjects = orgCategoryList.stream().map(orgCategory -> {
            SimpleObject simpleObject = new SimpleObject();
            if ("MD_ORG".equals(orgCategory.getName())) {
                simpleObject.setCode("ORG");
            } else {
                simpleObject.setCode(orgCategory.getName());
            }
            simpleObject.setName(orgCategory.getTitle());
            return simpleObject;
        }).collect(Collectors.toList());
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setCode("MD_ORG");
        simpleObject.setName("\u540c\u4efb\u52a1\u5173\u8054\u673a\u6784\u7c7b\u578b\u4e00\u81f4");
        simpleObjects.add(0, simpleObject);
        ConcurrentHashMap map = new ConcurrentHashMap(1);
        map.put("data", simpleObjects);
        return R.ok(map);
    }

    @GetMapping(value={"/baseData/columnInfo/{referTableId}"})
    public BudTableModelDefineVO getColumnInfoByTableId(@PathVariable String referTableId) {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTableId);
        return (BudTableModelDefineVO)BeanCopyUtil.copyObj((Object)tableModelDefine, BudTableModelDefineVO.class);
    }

    private String getDataType(int dataType) {
        String value = "";
        switch (dataType) {
            case 0: {
                value = "\u4efb\u610f\u7c7b\u578b";
                break;
            }
            case 11: {
                value = "\u5b57\u7b26\u4e32\u578b";
                break;
            }
            case 10: {
                value = "\u6570\u503c\u578b";
                break;
            }
            case 1: {
                value = "\u5e03\u5c14\u578b";
                break;
            }
            case 2: {
                value = "\u65e5\u671f\u578b";
                break;
            }
            case 3: {
                value = "\u6d6e\u70b9\u578b";
                break;
            }
            case 6: {
                value = "\u5b57\u7b26\u578b";
                break;
            }
            default: {
                value = "\u672a\u77e5\u7c7b\u578b";
            }
        }
        return value;
    }

    private String getParamsStr(String funcName, List<IParameter> list) {
        StringBuilder f = new StringBuilder(funcName);
        f.append("(");
        if (list != null && !list.isEmpty()) {
            StringBuilder formula = new StringBuilder();
            formula.append((CharSequence)f);
            int flag = 0;
            for (IParameter param : list) {
                if (flag == 0 && funcName.equalsIgnoreCase("GETMDLIST")) {
                    ++flag;
                    formula.append("\"\u8bf7\u53cc\u51fb\u62ec\u53f7\u5de6\u4fa7\u51fd\u6570\u540d\u6253\u5f00\u53ef\u89c6\u5316\u914d\u7f6e\u754c\u9762\",");
                    continue;
                }
                if (param.dataType() == 6) {
                    formula.append("\"\",");
                    continue;
                }
                if (param.dataType() == 1) {
                    formula.append("false,");
                    continue;
                }
                if (param.dataType() != 3 && param.dataType() != 10 && param.dataType() != 1 && param.dataType() != 2 && param.dataType() != 11 && param.dataType() != 0) continue;
                formula.append("NULL,");
            }
            formula.delete(formula.length() - 1, formula.length());
            formula.append(")");
            return formula.toString();
        }
        f.append(")");
        return f.toString();
    }
}


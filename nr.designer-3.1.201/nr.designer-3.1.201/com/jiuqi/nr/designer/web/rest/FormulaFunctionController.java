/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil
 *  com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService
 *  com.jiuqi.nr.function.func.doc.FuncParamDoc
 *  com.jiuqi.nr.function.func.doc.IFuncDoc
 *  com.jiuqi.nr.function.func.doc.service.IFuncDocService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.designer.web.facade.FunctionObj;
import com.jiuqi.nr.designer.web.facade.ParameterObj;
import com.jiuqi.nr.function.func.doc.FuncParamDoc;
import com.jiuqi.nr.function.func.doc.IFuncDoc;
import com.jiuqi.nr.function.func.doc.service.IFuncDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u516c\u5f0f\u7f16\u8f91\u5668\u51fd\u6570"})
public class FormulaFunctionController {
    private static final Logger log = LoggerFactory.getLogger(FormulaFunctionController.class);
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private LanguageTypeUtil languageTypeUtil;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private FuncTypeI18nHelper funcTypeI18nHelper;
    @Autowired
    private IFuncDocService funcDocService;

    @ApiOperation(value="\u67e5\u8be2\u6307\u5b9a\u7c7b\u578b\u7684\u516c\u5f0f\u51fd\u6570 1-\u62a5\u8868 2-excel")
    @RequestMapping(value={"query_formula_function/{functionType}"}, method={RequestMethod.GET})
    public List<FunctionObj> queryAllFormulaVariable(@PathVariable(required=true) int functionType) throws JQException {
        ArrayList<FunctionObj> functionObjs = new ArrayList<FunctionObj>();
        HashMap<String, FunctionObj> storeFunctions = new HashMap<String, FunctionObj>();
        List list = this.funcDocService.list();
        for (IFuncDoc func : list) {
            if (func.deprecated()) continue;
            List params = func.params();
            StringBuilder application = new StringBuilder(func.name()).append("(");
            StringBuilder function = new StringBuilder(func.result() == null ? " " : func.result()).append(" ").append(func.name()).append("(");
            StringBuilder parameterStr = new StringBuilder();
            ArrayList<ParameterObj> parameters = new ArrayList<ParameterObj>();
            if (!CollectionUtils.isEmpty(params)) {
                for (FuncParamDoc param : params) {
                    application.append(param.getSortName()).append(",");
                    function.append(param.getType()).append(" ").append(param.getName()).append(",");
                    ParameterObj obj = new ParameterObj(param.getName(), param.getSortName());
                    parameterStr.append(param.getName()).append(":").append(param.getSortName()).append("\uff1b");
                    parameters.add(obj);
                }
                application.delete(application.lastIndexOf(","), application.length());
                function.delete(function.lastIndexOf(","), function.length());
                parameterStr.delete(parameterStr.lastIndexOf("\uff1b"), parameterStr.length());
            }
            application.append(")");
            function.append(")");
            FunctionObj functionObj = new FunctionObj(func.name().toUpperCase(), func.sortName(), func.category(), parameters, application.toString(), func.example(), function.toString(), parameterStr.toString(), "");
            functionObj.setDescription(func.desc());
            storeFunctions.put(functionObj.getName(), functionObj);
        }
        try {
            boolean isDefaultLang = this.isDefaultLanguage();
            HashMap<String, FunctionObj> functionApplicationMap = this.getFunctionObjs();
            functionApplicationMap.entrySet().stream().forEach(e -> {
                FunctionObj functionObj = (FunctionObj)storeFunctions.get(e.getKey());
                if (functionObj == null) {
                    functionObjs.add((FunctionObj)e.getValue());
                } else if (isDefaultLang) {
                    functionObjs.add(functionObj);
                } else {
                    FunctionObj functionObj1 = (FunctionObj)e.getValue();
                    StringBuffer application = new StringBuffer(((FunctionObj)e.getValue()).getName());
                    application.append("(");
                    for (int i = 0; i < functionObj1.getParameterList().size(); ++i) {
                        application.append(functionObj1.getParameterList().get(i).getTitle());
                        if (i == functionObj1.getParameterList().size() - 1) continue;
                        application.append(",");
                    }
                    application.append(")");
                    functionObj1.setApplication(application.toString());
                    functionObj1.setExample(functionObj.getExample());
                    functionObj1.setFunction(functionObj.getFunction());
                    functionObjs.add(functionObj1);
                }
            });
        }
        catch (ParseException e2) {
            log.error(e2.getMessage(), e2);
        }
        catch (SyntaxException e3) {
            log.error(e3.getMessage(), e3);
        }
        return functionObjs;
    }

    private boolean isDefaultLanguage() {
        return this.languageTypeUtil.getCurrentLanguageType() == this.defaultLanguageService.getDefaultLanguage();
    }

    public HashMap<String, FunctionObj> getFunctionObjs() throws ParseException, SyntaxException {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
        Set functions = formulaParser.allFunctions();
        HashMap<String, FunctionObj> functionObjs = new HashMap<String, FunctionObj>();
        boolean isDefaultLang = this.isDefaultLanguage();
        for (IFunction iFunction : functions) {
            if (iFunction.isDeprecated()) continue;
            String category = this.funcTypeI18nHelper.getFunc(iFunction.category());
            FunctionObj functionObj = this.parseToFuncObj(iFunction, category, isDefaultLang);
            functionObjs.put(iFunction.name().toUpperCase(), functionObj);
        }
        return functionObjs;
    }

    private FunctionObj parseToFuncObj(IFunction iFunction, String category, boolean isDefaultLang) throws ParseException, SyntaxException {
        String funcTitle = this.funcTypeI18nHelper.getDesc(iFunction.name());
        FunctionObj functionObj = new FunctionObj(iFunction.name().toUpperCase(), funcTitle, category, iFunction.toDescription(), iFunction.parameters().size());
        functionObj.setIsInfiniteParameter(iFunction.isInfiniteParameter());
        functionObj.setInfiniteParameterCount(1);
        functionObj.setResultType(iFunction.getResultType(null, null));
        ArrayList<ParameterObj> parameterObjs = new ArrayList<ParameterObj>();
        String params = "";
        for (IParameter parameter : iFunction.parameters()) {
            String title = this.funcTypeI18nHelper.getParam(iFunction.name(), parameter.name());
            String typeStr = DataType.toString((int)parameter.dataType());
            if (!isDefaultLang) {
                typeStr = DataType.toExpression((int)parameter.dataType());
            }
            params = params + parameter.name() + "\uff1a" + typeStr + " " + title + "\uff1b";
            ParameterObj parameterObj = new ParameterObj(parameter.name(), title, parameter.dataType(), parameter.isOmitable());
            parameterObjs.add(parameterObj);
        }
        functionObj.setParameter(params);
        functionObj.setParameterList(parameterObjs);
        functionObj.setParameterCount(parameterObjs.size());
        String desc = "\u51fd\u6570\uff1a" + functionObj.getFunction() + ";\u8bf4\u660e\uff1a" + functionObj.getTitle() + ";\u53c2\u6570\uff1a" + functionObj.getParameter();
        functionObj.setDescription(desc);
        return functionObj;
    }
}


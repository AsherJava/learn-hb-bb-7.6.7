/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.syntax.AdHocParser
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.bi.adhoc.engine.syntax.AdHocParser;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.dafafill.formula.DataFillWorksheet;
import com.jiuqi.nr.dafafill.formula.DatafillCellProvider;
import com.jiuqi.nr.dafafill.formula.DatafillDynamicNodeProvider;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillFormulaCheck;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.web.vo.DataFillFormulaCheckResultVO;
import com.jiuqi.nr.dafafill.web.vo.FormulaFunctionVO;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/fml"})
public class FormulaDesignController {
    private static Logger logger = LoggerFactory.getLogger(FormulaDesignController.class);
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    private static final List<String> categoryList = new ArrayList<String>();

    @GetMapping(value={"/getfunctions"})
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u7f16\u8f91\u5668\u7528\u5230\u7684\u5185\u7f6e\u51fd\u6570")
    public List<FormulaFunctionVO> getFormulaFunctions() {
        ArrayList<FormulaFunctionVO> functionVOList = new ArrayList<FormulaFunctionVO>();
        Set functions = AdHocParser.createSyntaxParser().allFunctions();
        for (IFunction function : functions) {
            if (!categoryList.contains(function.category())) continue;
            functionVOList.add(new FormulaFunctionVO(function));
        }
        return functionVOList;
    }

    @PostMapping(value={"/check"})
    @ApiOperation(value="\u68c0\u67e5\u516c\u5f0f\u7684\u6b63\u786e\u6027")
    public DataFillFormulaCheckResultVO check(@RequestBody DataFillFormulaCheck dataFillFormulaCheck) {
        DataFillFormulaCheckResultVO checkResultVO = new DataFillFormulaCheckResultVO();
        String formula = dataFillFormulaCheck.getFormula();
        DataFillModel model = new DataFillModel();
        model.setQueryFields(dataFillFormulaCheck.getQueryFields());
        model.setAssistFields(new ArrayList<QueryField>());
        DataFillContext context = new DataFillContext();
        context.setModel(model);
        DatafillFormulaContext formulaContext = new DatafillFormulaContext();
        formulaContext.setQueryFieldMap(this.dFDimensionParser.getSimplifyQueryFieldsMap(context));
        formulaContext.setFullQueryFieldMap(this.dFDimensionParser.getQueryFieldsMap(context));
        formulaContext.setCurWorksheet(new DataFillWorksheet());
        formulaContext.setFormulaCheck(true);
        FormulaParser parser = FormulaParser.getInstance();
        parser.registerCellProvider((ICellProvider)new DatafillCellProvider());
        parser.registerDynamicNodeProvider((IDynamicNodeProvider)new DatafillDynamicNodeProvider());
        try {
            IExpression expression = parser.parseEval(formula, (IContext)formulaContext);
            expression.interpret((IContext)formulaContext, Language.EXCEL, null);
            expression.evaluate((IContext)formulaContext);
            checkResultVO.setSuccess(true);
            checkResultVO.setMessage("\u68c0\u67e5\u901a\u8fc7");
        }
        catch (Exception e) {
            logger.error("\u516c\u5f0f\u68c0\u67e5\u62a5\u9519\uff01" + formula, e);
            checkResultVO.setSuccess(false);
            checkResultVO.setMessage(e.getLocalizedMessage());
        }
        return checkResultVO;
    }

    static {
        categoryList.add("\u64cd\u4f5c\u7b26");
        categoryList.add("\u6570\u5b66\u51fd\u6570");
        categoryList.add("\u6587\u672c\u51fd\u6570");
        categoryList.add("\u65e5\u671f\u51fd\u6570");
        categoryList.add("\u903b\u8f91\u51fd\u6570");
    }
}


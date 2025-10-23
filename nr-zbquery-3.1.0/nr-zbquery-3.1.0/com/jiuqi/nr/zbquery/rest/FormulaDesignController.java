/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.syntax.AdHocParser
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser
 *  com.jiuqi.nr.bql.interpret.BiAdaptParam
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.adhoc.engine.syntax.AdHocParser;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.rest.param.AdaptFormulaParam;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeRequestParam;
import com.jiuqi.nr.zbquery.rest.vo.AdaptFormulaFunctionVO;
import com.jiuqi.nr.zbquery.rest.vo.AdaptFormulaValidVo;
import com.jiuqi.nr.zbquery.rest.vo.AdaptFormulaVariableVO;
import com.jiuqi.nr.zbquery.rest.vo.FormulaFunctionVO;
import com.jiuqi.nr.zbquery.rest.vo.ResourceTreeAdaptNodeVo;
import com.jiuqi.nr.zbquery.service.ZBQueryAdaptFormulaFunctionService;
import com.jiuqi.nr.zbquery.service.ZBQueryAdaptTreeService;
import com.jiuqi.nr.zbquery.util.BiAdaptParamUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/formuladesign"})
public class FormulaDesignController {
    private final Logger logger = LoggerFactory.getLogger(FormulaDesignController.class);
    @Autowired
    private BiAdaptFormulaParser biAdaptFormulaParser;
    @Autowired
    private ZBQueryAdaptTreeService adaptTreeService;
    @Autowired
    private ZBQueryAdaptFormulaFunctionService adaptFunctionService;

    @GetMapping(value={"/getfunctions"})
    public List<FormulaFunctionVO> getFormulaFunctions() {
        ArrayList<FormulaFunctionVO> functionVOList = new ArrayList<FormulaFunctionVO>();
        Set functions = AdHocParser.createSyntaxParser().allFunctions();
        for (IFunction function : functions) {
            functionVOList.add(new FormulaFunctionVO(function));
        }
        return functionVOList;
    }

    @GetMapping(value={"/adapt/functions"})
    public List<AdaptFormulaFunctionVO> getAdaptFormulaFunctions() throws Exception {
        return this.adaptFunctionService.getAllFunctions();
    }

    @GetMapping(value={"/adapt/variables"})
    public List<AdaptFormulaVariableVO> getAdaptFormulaVariables() {
        ArrayList<AdaptFormulaVariableVO> variableVos = new ArrayList<AdaptFormulaVariableVO>();
        List variables = this.biAdaptFormulaParser.getAllVariables();
        if (!CollectionUtils.isEmpty(variables)) {
            variables.forEach(var -> variableVos.add(new AdaptFormulaVariableVO((Variable)var)));
        }
        return variableVos;
    }

    @PostMapping(value={"/adapt/checkFormula"})
    @RequiresPermissions(value={"nr:zbquery:querymodel"})
    public AdaptFormulaValidVo checkFormula(@RequestBody AdaptFormulaParam adaptFormulaParam) {
        AdaptFormulaValidVo result = null;
        String formulaStr = adaptFormulaParam.getFormulaStr();
        try {
            BiAdaptParam adaptParam = new BiAdaptParam();
            BiAdaptParamUtils.buildParam(adaptParam, adaptFormulaParam.getZbQueryModel());
            boolean valid = this.biAdaptFormulaParser.checkFormula(adaptParam, formulaStr);
            int resultType = this.biAdaptFormulaParser.getDataType(adaptParam, formulaStr);
            result = valid ? AdaptFormulaValidVo.success(resultType) : AdaptFormulaValidVo.error("\u516c\u5f0f\u6821\u9a8c\u5931\u8d25\uff01");
        }
        catch (SyntaxException e) {
            this.logger.error(e.getMessage(), e);
            result = AdaptFormulaValidVo.error(e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/adapt/resourcetree"})
    public List<ITree<ResourceTreeAdaptNodeVo>> getResourceTree(@RequestBody AdaptTreeRequestParam param) {
        return this.adaptTreeService.initTree(param);
    }

    @PostMapping(value={"/adapt/resourcetree/children"})
    public List<ITree<ResourceTreeAdaptNodeVo>> getResourceTreeChildren(@RequestBody AdaptTreeRequestParam param) {
        return this.adaptTreeService.getChildren(param);
    }

    @PostMapping(value={"/adapt/resourcetable/fields"})
    public List<ResourceTableAdaptNode> getResourceTableFields(@RequestBody AdaptTreeRequestParam param) {
        return this.adaptTreeService.getResourceFields(param);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser
 *  com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper
 *  com.jiuqi.nr.designer.web.facade.ParameterObj
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser;
import com.jiuqi.nr.definition.i18n.FuncTypeI18nHelper;
import com.jiuqi.nr.designer.web.facade.ParameterObj;
import com.jiuqi.nr.zbquery.rest.vo.AdaptFormulaFunctionVO;
import com.jiuqi.nr.zbquery.service.ZBQueryAdaptFormulaFunctionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryAdaptFormulaFunctionServiceImpl
implements ZBQueryAdaptFormulaFunctionService {
    @Autowired
    private BiAdaptFormulaParser biAdaptFormulaParser;
    @Autowired
    private FuncTypeI18nHelper funcTypeI18nHelper;

    @Override
    public List<AdaptFormulaFunctionVO> getAllFunctions() throws Exception {
        ArrayList<AdaptFormulaFunctionVO> functionVOList = new ArrayList<AdaptFormulaFunctionVO>();
        Set functions = this.biAdaptFormulaParser.getAllFunctions();
        for (IFunction iFunction : functions) {
            String category = iFunction.category();
            String funcTitle = iFunction.title();
            if (!this.isDefaultLanguage()) {
                category = this.funcTypeI18nHelper.getFunc(iFunction.category());
                funcTitle = this.funcTypeI18nHelper.getDesc(iFunction.name());
            }
            AdaptFormulaFunctionVO functionObj = new AdaptFormulaFunctionVO(iFunction.name().toUpperCase(), funcTitle, category, iFunction.toDescription(), iFunction.parameters().size());
            functionObj.setIsInfiniteParameter(iFunction.isInfiniteParameter());
            functionObj.setInfiniteParameterCount(1);
            functionObj.setResultType(iFunction.getResultType(null, null));
            ArrayList<ParameterObj> parameterObjs = new ArrayList<ParameterObj>();
            String params = "";
            for (IParameter parameter : iFunction.parameters()) {
                String typeStr = DataType.toString((int)parameter.dataType());
                if (!this.isDefaultLanguage()) {
                    typeStr = DataType.toExpression((int)parameter.dataType());
                }
                String title = this.funcTypeI18nHelper.getParam(iFunction.name(), parameter.name());
                params = params + parameter.name() + "\uff1a" + typeStr + " " + title + "\uff1b";
                ParameterObj parameterObj = new ParameterObj(parameter.name(), parameter.title(), parameter.dataType(), parameter.isOmitable());
                parameterObjs.add(parameterObj);
            }
            functionObj.setParameter(params);
            functionObj.setParameterList(parameterObjs);
            functionObj.setParameterCount(parameterObjs.size());
            String desc = "\u51fd\u6570\uff1a" + functionObj.getFunction() + ";\u8bf4\u660e\uff1a" + functionObj.getTitle() + ";\u53c2\u6570\uff1a" + functionObj.getParameter();
            functionObj.setDescription(desc);
            functionVOList.add(functionObj);
        }
        return functionVOList;
    }

    private boolean isDefaultLanguage() {
        return Locale.SIMPLIFIED_CHINESE.getLanguage().equals(NpContextHolder.getContext().getLocale().getLanguage());
    }
}


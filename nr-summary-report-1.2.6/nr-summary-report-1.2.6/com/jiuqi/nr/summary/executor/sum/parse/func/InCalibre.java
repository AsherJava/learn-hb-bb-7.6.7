/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 */
package com.jiuqi.nr.summary.executor.sum.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import java.util.HashSet;
import java.util.List;

public class InCalibre
extends Function {
    private static final long serialVersionUID = -4562771098865625223L;

    public InCalibre() {
        this.parameters().add(new Parameter("text", 6, "\u5f85\u5224\u65ad\u7684\u5185\u5bb9"));
        this.parameters().add(new Parameter("calibreCode", 6, "\u53e3\u5f84\u4ee3\u7801"));
        this.parameters().add(new Parameter("calibreDataCode", 6, "\u53e3\u5f84\u9879\u4ee3\u7801"));
    }

    public String name() {
        return "InCalibre";
    }

    public String title() {
        return "\u5224\u65ad\u5b57\u7b26\u4e32\u662f\u5426\u5728\u6307\u5b9a\u53d6\u503c\u7c7b\u578b\u7684\u53e3\u5f84\u9879\u4e2d";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 1;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        SumContext context = (SumContext)paramIContext;
        String text = (String)paramList.get(0).evaluate(paramIContext);
        if (text == null) {
            return false;
        }
        String calibreCode = (String)paramList.get(1).evaluate(paramIContext);
        if (calibreCode == null) {
            return false;
        }
        String calibreDataCode = (String)paramList.get(2).evaluate(paramIContext);
        if (calibreDataCode == null) {
            return false;
        }
        String cacheKey = this.name() + "_" + calibreCode + "." + calibreDataCode;
        HashSet set = (HashSet)context.getCache().get(cacheKey);
        if (set == null) {
            try {
                CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
                calibreDataDTO.setCalibreCode(calibreCode);
                calibreDataDTO.setCode(calibreDataCode);
                Result result = context.getBeanSet().calibreDataService.get(calibreDataDTO);
                CalibreDataDTO calibreData = (CalibreDataDTO)result.getData();
                Object expression = calibreData.getValue().getExpression();
                if (expression instanceof List) {
                    List values = (List)expression;
                    set = new HashSet(values);
                    context.getCache().put(cacheKey, set);
                }
            }
            catch (Exception e) {
                context.getMonitor().exception(e);
            }
        }
        return set != null && set.contains(text);
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        try {
            IASTNode field = paramList.get(0);
            paramStringBuilder.append(" exists (select 1 from ");
            paramStringBuilder.append("NR_CALIBRE_SUBLIST").append(" ct ");
            paramStringBuilder.append(" where ct.").append("CS_VALUE").append("=").append(field.interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            paramStringBuilder.append(" and ct.").append("CS_CALIBRE_CODE").append("=").append(paramList.get(1).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            paramStringBuilder.append(" and ct.").append("CS_CODE").append("=").append(paramList.get(2).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            paramStringBuilder.append(")");
        }
        catch (SyntaxException e) {
            throw new InterpretException(e.getMessage(), (Throwable)e);
        }
    }
}


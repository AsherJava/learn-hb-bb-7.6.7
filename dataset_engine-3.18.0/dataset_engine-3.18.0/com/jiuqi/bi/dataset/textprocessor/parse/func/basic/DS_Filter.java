/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.function.Filter;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DS_Filter
extends BasicDSFunction {
    private static final long serialVersionUID = -5453060976162211900L;

    public DS_Filter() {
        super(new Filter());
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        IASTNode p0 = parameters.get(0);
        List<IASTNode> filters = this.getFilterParamList(context, parameters);
        Locale locale = StringUtils.isEmpty((String)tfc.getI18nLang()) ? Locale.getDefault() : Locale.forLanguageTag(tfc.getI18nLang());
        return helper.evaluate(p0, filters, locale);
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(1, parameters.size()));
    }

    @Override
    protected boolean isAutoAggr() {
        return false;
    }
}


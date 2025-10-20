/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class showDiffDateFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    private static final String PREFIXKEY = "X--";

    public String addDescribe() {
        return "\u5c55\u793a\u4e0d\u540c\u7684\u65f6\u671f\u8303\u56f4";
    }

    public String name() {
        return "showDiffDate";
    }

    public String title() {
        return "\u5c55\u793a\u4e0d\u540c\u7684\u65f6\u671f\u8303\u56f4";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        BillModelImpl model = (BillModelImpl)((ModelDataContext)context).model;
        int acctYear = ConverterUtils.getAsIntValue((Object)model.getContext().getContextValue("X--acctYear"));
        int acctPeriod = ConverterUtils.getAsIntValue((Object)model.getContext().getContextValue("X--acctPeriod"));
        Date date = DateUtils.lastDateOf((int)acctYear, (int)acctPeriod);
        return date;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return super.validate(context, parameters);
    }
}


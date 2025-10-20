/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.invest.investbill.dao.impl.InvestBillDaoImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SumInvestNumberFunction
extends Function
implements INrFunction {
    private static final Logger logger = LoggerFactory.getLogger(SumInvestNumberFunction.class);
    private static final long serialVersionUID = 1L;
    static final String FUNCTION_NAME = "SumInvestNumber";

    SumInvestNumberFunction() {
        this.parameters().add(new Parameter("fieldCode", 6, "\u6295\u8d44\u4e3b\u8868\u5b57\u6bb5"));
        this.parameters().add(new Parameter("fieldValueFilter", 6, "\u6295\u8d44\u591a\u4e2a\u5b57\u6bb5\u6761\u4ef6\u8fc7\u6ee4"));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u5355\u4f4d\u6307\u5b9a\u5b57\u6bb5\u7684\u6295\u8d44\u4e3b\u8868\u91d1\u989d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        try {
            String fieldCode = (String)parameters.get(0).evaluate(context);
            fieldCode = StringUtils.isEmpty((String)fieldCode) ? fieldCode : fieldCode.replaceAll("[\\[\\]]", "");
            String filterCondition = (String)parameters.get(1).evaluate(context);
            String unitCode = (String)((QueryContext)context).getMasterKeys().getValue("MD_ORG");
            String periodString = (String)((QueryContext)context).getMasterKeys().getValue("DATATIME");
            if (!StringUtils.isEmpty((String)filterCondition)) {
                filterCondition = filterCondition.replaceAll("(left\\()(\\[[A-Za-z0-9]*\\])(,\\d\\)( ?= ')([A-Za-z0-9]*)('))", "$2 like '$5%'").replaceAll("[\\[]", "t.").replaceAll("[\\]]", "");
            }
            Integer acctYear = null;
            Date period = null;
            if (!StringUtils.isEmpty((String)periodString)) {
                acctYear = Integer.valueOf(periodString.substring(0, 4));
                period = DateUtils.parse((String)(periodString.substring(0, 4) + "-" + periodString.substring(periodString.length() - 2) + "-01"), (String)"yyyy-MM-dd");
            }
            InvestBillDaoImpl investBillDao = (InvestBillDaoImpl)SpringContextUtils.getBean(InvestBillDaoImpl.class);
            return investBillDao.sumInvestNumber(unitCode, acctYear, fieldCode, filterCondition, period);
        }
        catch (Exception e) {
            logger.error("\u51fd\u6570\u83b7\u53d6\u6295\u8d44\u4e3b\u8868\u5b57\u6bb5\u91d1\u989d\u51fa\u73b0\u9519\u8bef\uff1a" + e);
            return null;
        }
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }
}


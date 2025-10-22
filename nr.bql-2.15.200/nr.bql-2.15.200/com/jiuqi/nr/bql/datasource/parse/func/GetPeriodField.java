/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.bql.datasource.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.HashMap;
import java.util.List;

public class GetPeriodField
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 4534004763282389355L;
    private static final String CACHE_KEY = "bqlPeriodField";

    public GetPeriodField() {
        this.parameters().add(new Parameter("peirodCode", 6, "\u65f6\u671f\u4e3b\u4f53"));
        this.parameters().add(new Parameter("fieldName", 6, "\u5b57\u6bb5\u540d"));
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String peirodCode = (String)parameters.get(0).evaluate(context);
        String fieldName = (String)parameters.get(1).evaluate(context);
        QueryContext qContext = (QueryContext)context;
        String periodEntityId = qContext.getExeContext().getPeriodView();
        HashMap<String, IPeriodRow> periodCache = (HashMap<String, IPeriodRow>)qContext.getCache().get(CACHE_KEY);
        if (periodCache == null) {
            PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
            IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
            List periodRows = periodProvider.getPeriodItems();
            periodCache = new HashMap<String, IPeriodRow>();
            for (IPeriodRow p : periodRows) {
                periodCache.put(p.getCode(), p);
            }
            qContext.getCache().put(CACHE_KEY, periodCache);
        }
        IPeriodRow row = (IPeriodRow)periodCache.get(peirodCode);
        return this.getFieldValue(row, fieldName);
    }

    private Object getFieldValue(IPeriodRow row, String fieldName) {
        if (fieldName.equals(PeriodTableColumn.TIMEKEY.getCode())) {
            return row.getTimeKey();
        }
        if (fieldName.equals(PeriodTableColumn.YEAR.getCode())) {
            return row.getYear();
        }
        if (fieldName.equals(PeriodTableColumn.QUARTER.getCode())) {
            return row.getQuarter();
        }
        if (fieldName.equals(PeriodTableColumn.MONTH.getCode())) {
            return row.getMonth();
        }
        if (fieldName.equals(PeriodTableColumn.DAY.getCode())) {
            return row.getDay();
        }
        if (fieldName.equals(PeriodTableColumn.CODE.getCode())) {
            return row.getCode();
        }
        if (fieldName.equals(PeriodTableColumn.TITLE.getCode())) {
            return row.getTitle();
        }
        return null;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String name() {
        return "GetPeriodField";
    }

    public String title() {
        return "\u83b7\u53d6\u65f6\u671f\u5b57\u6bb5\u7684\u503c";
    }
}


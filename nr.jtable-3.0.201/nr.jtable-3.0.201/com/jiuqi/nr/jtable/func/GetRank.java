/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.jtable.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.jtable.util.EntityUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetRank
extends Function
implements IReportFunction {
    private static final Logger logger = LoggerFactory.getLogger(GetRank.class);
    private static final long serialVersionUID = 8715778103998923629L;

    public GetRank() {
        this.parameters().add(new Parameter("UnitCodes", 6, "\u5355\u4f4d\u5217\u8868"));
        this.parameters().add(new Parameter("Code", 6, "\u6307\u5b9a\u5355\u4f4d"));
        this.parameters().add(new Parameter("Period", 6, "\u65f6\u671f"));
        this.parameters().add(new Parameter("ZbExp", 6, "\u6307\u6807\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("IsDsc", 1, "\u662f\u5426\u964d\u5e8f"));
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }

    public String name() {
        return "GetRank";
    }

    public String title() {
        return "\u83b7\u53d6\u6392\u5e8f\u540e\u5f53\u524d\u5355\u4f4d\u7684\u6392\u540d\u3002";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 4;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            String unitCodes = (String)parameters.get(0).evaluate(context);
            String code = (String)parameters.get(1).evaluate(context);
            String periodCode = (String)parameters.get(2).evaluate(context);
            String zbExp = (String)parameters.get(3).evaluate(context);
            final boolean isDesc = (Boolean)parameters.get(4).evaluate(context);
            try {
                Map<String, Object> numbers;
                if (!Arrays.asList(unitCodes.split(",")).contains(code)) {
                    unitCodes = unitCodes + "," + code;
                }
                if ((numbers = EntityUtils.queryDataByFormula(qContext, zbExp, Arrays.asList(unitCodes.split(",")), periodCode)).size() <= 0) {
                    return -1;
                }
                ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(numbers.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Object>>(){

                    @Override
                    public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                        Object obj1 = o1.getValue();
                        Object obj2 = o2.getValue();
                        return isDesc ? new BigDecimal(obj2.toString()).compareTo(new BigDecimal(obj1.toString())) : new BigDecimal(obj1.toString()).compareTo(new BigDecimal(obj2.toString()));
                    }
                });
                int index = 0;
                for (Map.Entry entry : list) {
                    if (code.equals(entry.getKey())) {
                        return ++index;
                    }
                    ++index;
                }
                return -1;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return -1;
    }

    public boolean isDeprecated() {
        return true;
    }
}


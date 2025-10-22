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

public class GetSortedName
extends Function
implements IReportFunction {
    private static final Logger logger = LoggerFactory.getLogger(GetSortedName.class);
    private static final long serialVersionUID = 8779975667172731977L;

    public GetSortedName() {
        this.parameters().add(new Parameter("UnitCodes", 6, "\u5355\u4f4d\u5217\u8868"));
        this.parameters().add(new Parameter("DataYear", 3, "\u5e74"));
        this.parameters().add(new Parameter("DataType", 6, "\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("DataTime", 3, "\u65f6\u671f\u503c"));
        this.parameters().add(new Parameter("ZbExp", 6, "\u6307\u6807\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("IsDesc", 1, "\u662f\u5426\u964d\u5e8f"));
        this.parameters().add(new Parameter("Order", 3, "\u987a\u5e8f\u53f7"));
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }

    public String name() {
        return "GetSortedName";
    }

    public String title() {
        return "\u6307\u5b9a\u65f6\u671f\u4e0b\u5355\u4f4d\u5217\u8868\u4e2d\u6307\u6807\u6392\u5e8f\u540e\u67d0\u4e2a\u4f4d\u7f6e\u7684\u5355\u4f4d\u540d\u79f0\u3002";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            String unitCodes = (String)parameters.get(0).evaluate(context);
            int dataYear = ((Number)parameters.get(1).evaluate(context)).intValue();
            String dataType = (String)parameters.get(2).evaluate(context);
            int dataTime = ((Number)parameters.get(3).evaluate(context)).intValue();
            String zbExp = (String)parameters.get(4).evaluate(context);
            final boolean isDesc = (Boolean)parameters.get(5).evaluate(context);
            int order = ((Number)parameters.get(6).evaluate(context)).intValue();
            order = order > 0 ? order - 1 : 0;
            try {
                String periodCode = String.valueOf(dataYear) + dataType + String.format("%4d", dataTime).replace(" ", "0");
                Map<String, Object> numbers = EntityUtils.queryDataByFormula(qContext, zbExp, Arrays.asList(unitCodes.split(",")), periodCode);
                if (numbers.size() <= 0) {
                    return "";
                }
                ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(numbers.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Object>>(){

                    @Override
                    public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                        Object obj1 = o1.getValue();
                        Object obj2 = o2.getValue();
                        if (obj1 == null && obj2 == null) {
                            return 0;
                        }
                        if (obj1 == null) {
                            return 1;
                        }
                        if (obj2 == null) {
                            return -1;
                        }
                        return isDesc ? new BigDecimal(obj2.toString()).compareTo(new BigDecimal(obj1.toString())) : new BigDecimal(obj1.toString()).compareTo(new BigDecimal(obj2.toString()));
                    }
                });
                String resCode = (String)((Map.Entry)list.get(order)).getKey();
                return EntityUtils.queryIEntityTableNameByCode(zbExp, resCode);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "";
    }

    public boolean isDeprecated() {
        return true;
    }
}


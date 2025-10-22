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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetIncSortedName
extends Function
implements IReportFunction {
    private static final Logger logger = LoggerFactory.getLogger(GetIncSortedName.class);
    private static final long serialVersionUID = -5992108595558613238L;

    public GetIncSortedName() {
        this.parameters().add(new Parameter("UnitCodes", 6, "\u5355\u4f4d\u5217\u8868"));
        this.parameters().add(new Parameter("RefDataYear", 3, "\u53c2\u8003\u5e74"));
        this.parameters().add(new Parameter("RefDataType", 6, "\u53c2\u8003\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("RefDataTime", 3, "\u53c2\u8003\u65f6\u671f\u503c"));
        this.parameters().add(new Parameter("CmpDataYear", 3, "\u6bd4\u8f83\u5e74"));
        this.parameters().add(new Parameter("CmpDataType", 6, "\u6bd4\u8f83\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("CmpDataTime", 3, "\u6bd4\u8f83\u65f6\u671f\u503c"));
        this.parameters().add(new Parameter("ZbName", 6, "\u6307\u6807\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("IsDesc", 1, "\u662f\u5426\u964d\u5e8f"));
        this.parameters().add(new Parameter("Order", 3, "\u987a\u5e8f\u53f7"));
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }

    public String name() {
        return "GetIncSortedName";
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
            int refDataYear = ((Number)parameters.get(1).evaluate(context)).intValue();
            String refDataType = (String)parameters.get(2).evaluate(context);
            int refDataTime = ((Number)parameters.get(3).evaluate(context)).intValue();
            int cmpDataYear = ((Number)parameters.get(4).evaluate(context)).intValue();
            String cmpDataType = (String)parameters.get(5).evaluate(context);
            int cmpDataTime = ((Number)parameters.get(6).evaluate(context)).intValue();
            String zbExp = (String)parameters.get(7).evaluate(context);
            final boolean isDesc = (Boolean)parameters.get(8).evaluate(context);
            int order = ((Number)parameters.get(9).evaluate(context)).intValue();
            order = order > 0 ? order - 1 : 0;
            try {
                String refPeriodCode = String.valueOf(refDataYear) + refDataType + String.format("%4d", refDataTime).replace(" ", "0");
                Map<String, Object> refNumbers = EntityUtils.queryDataByFormula(qContext, zbExp, Arrays.asList(unitCodes.split(",")), refPeriodCode);
                if (refNumbers.size() <= 0) {
                    return "";
                }
                String cmpPeriodCode = String.valueOf(cmpDataYear) + cmpDataType + String.format("%4d", cmpDataTime).replace(" ", "0");
                Map<String, Object> cmpNumbers = EntityUtils.queryDataByFormula(qContext, zbExp, Arrays.asList(unitCodes.split(",")), cmpPeriodCode);
                if (cmpNumbers.size() <= 0) {
                    return "";
                }
                LinkedHashMap<String, Object> numbers = new LinkedHashMap<String, Object>();
                for (String key : refNumbers.keySet()) {
                    Object refObj = refNumbers.get(key);
                    Object cmpObj = cmpNumbers.get(key);
                    if ((refObj == null || "".equals(refObj)) && (cmpObj == null || "".equals(cmpObj))) {
                        numbers.put(key, "");
                        continue;
                    }
                    if (refObj == null || "".equals(refObj)) {
                        numbers.put(key, this.NumberSub(0, cmpObj));
                        continue;
                    }
                    if (cmpObj == null || "".equals(cmpObj)) {
                        numbers.put(key, refObj);
                        continue;
                    }
                    numbers.put(key, this.NumberSub(refObj, cmpObj));
                }
                ArrayList list = new ArrayList(numbers.entrySet());
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

    private Object NumberSub(Object obj1, Object obj2) {
        if (obj2 instanceof Integer) {
            return ((Number)obj1).intValue() - ((Number)obj2).intValue();
        }
        if (obj2 instanceof Double) {
            return ((Number)obj1).doubleValue() - ((Number)obj2).doubleValue();
        }
        if (obj2 instanceof Float) {
            return Float.valueOf(((Number)obj1).floatValue() - ((Number)obj2).floatValue());
        }
        if (obj2 instanceof Long) {
            return ((Number)obj1).longValue() - ((Number)obj2).longValue();
        }
        if (obj2 instanceof Short) {
            return ((Number)obj1).shortValue() - ((Number)obj2).shortValue();
        }
        if (obj2 instanceof String) {
            return String.valueOf(obj1).compareTo(String.valueOf(obj2));
        }
        if (obj2 instanceof BigDecimal) {
            return new BigDecimal(((Number)obj1).doubleValue() - ((Number)obj2).doubleValue());
        }
        return 0;
    }

    public boolean isDeprecated() {
        return true;
    }
}


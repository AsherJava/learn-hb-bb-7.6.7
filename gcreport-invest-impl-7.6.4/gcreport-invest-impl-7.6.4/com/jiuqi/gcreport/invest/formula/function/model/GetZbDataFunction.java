/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetZbDataFunction
extends ModelFunction
implements INrFunction,
IGcFunction {
    private static final long serialVersionUID = 1L;

    public GetZbDataFunction() {
        this.parameters().add(new Parameter("unitZbCode", 6, "\u5355\u4f4d\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("amtZbCode", 6, "\u91d1\u989d\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("time", 2, "\u65e5\u671f\u578b\u5b57\u6bb5"));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u5e74\u6708\u7684\u6307\u6807\u6570\u636e";
    }

    public String name() {
        return "GetZbData";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u5e74\u6708\u7684\u6307\u6807\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("unitZbCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5355\u4f4d\u6307\u6807\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("amtZbCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u91d1\u989d\u6307\u6807\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("time").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u65e5\u671f\u578b\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u8fd4\u56de\u6307\u5b9a\u5e74\u6708\u7684\u6307\u6807\u6570\u636e").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u9500\u552e\u5355\u4f4d\u6307\u5b9a\u5e74\u6708\u7684\u6307\u6807\u6570\u636e ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetZbData(GC_COMMONASSETBILL[OPPUNITCODE],'ZCOX_YB01[E2]',GC_COMMONASSETBILL[PURCHASEDATE])").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        try {
            IASTNode amtZbNode = parameters.get(1);
            Object amtZbNodeData = amtZbNode.evaluate(context);
            if (null == amtZbNodeData) {
                return null;
            }
            DimensionValueSet ds = this.getDimensionValueSet(context, parameters);
            if (null == ds) {
                return null;
            }
            AbstractData abstractData = NrTool.getZbValue((DimensionValueSet)ds, (String)((String)amtZbNodeData));
            return null == abstractData ? 0.0 : abstractData.getAsFloat();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DimensionValueSet getDimensionValueSet(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String unitCode = (String)parameters.get(0).evaluate(context);
        Calendar timeZbNodeData = (Calendar)parameters.get(2).evaluate(context);
        if (null == timeZbNodeData || null == unitCode) {
            return null;
        }
        YearPeriodDO yp = new YearPeriodObject(timeZbNodeData).formatYP();
        DimensionValueSet ds = new DimensionValueSet();
        ds.setValue("MD_ORG", (Object)unitCode);
        ds.setValue("DATATIME", (Object)yp.toString());
        return ds;
    }
}


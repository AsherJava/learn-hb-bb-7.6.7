/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SameMergeLevelFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public SameMergeLevelFunction() {
        this.parameters().add(new Parameter("UnitCode", 6, "\u7ec4\u7ec7\u673a\u6784\u6807\u8bc6", false));
        this.parameters().add(new Parameter("OppUnitCode", 6, "\u7ec4\u7ec7\u673a\u6784\u6807\u8bc6", false));
    }

    public String addDescribe() {
        return "\u5224\u65ad\u6307\u5b9a\u5355\u4f4d\u662f\u5426\u5c5e\u4e8e\u5f53\u524d\u5408\u5e76\u5c42\u7ea7";
    }

    public String name() {
        return "isSameMergeLevel";
    }

    public String title() {
        return "\u5224\u65ad\u6307\u5b9a\u5355\u4f4d\u662f\u5426\u5c5e\u4e8e\u5f53\u524d\u5408\u5e76\u5c42\u7ea7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
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
        buffer.append("    ").append("UnitCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("OppUnitCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u8fd4\u56de\u662f\u5426\u5c5e\u4e8e\u5f53\u524d\u5408\u5e76\u5c42\u7ea7").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5224\u65ad\u6307\u5b9a\u5355\u4f4d\u662f\u5426\u5c5e\u4e8e\u5f53\u524d\u5408\u5e76\u5c42\u7ea7 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("isSameMergeLevel(GC_COMMONASSETBILL[UNITCODE],GC_COMMONASSETBILL[OPPUNITCODE])").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() != 2) {
            return false;
        }
        try {
            String unitCode = (String)parameters.get(0).evaluate(context);
            String oppUnitCode = (String)parameters.get(1).evaluate(context);
            String mergeUnitCode = (String)((BillModelImpl)((ModelDataContext)context).model).getContext().getContextValue("X--mergeUnitCode");
            String periodStr = Calendar.getInstance().get(1) + "Y0012";
            YearPeriodObject yp = new YearPeriodObject(null, periodStr);
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            if (StringUtils.isEmpty((String)mergeUnitCode)) {
                return true;
            }
            return orgTool.checkCommonUnit(mergeUnitCode, unitCode, oppUnitCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


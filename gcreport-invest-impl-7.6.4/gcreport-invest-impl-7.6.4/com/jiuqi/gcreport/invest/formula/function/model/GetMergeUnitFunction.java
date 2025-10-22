/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 */
package com.jiuqi.gcreport.invest.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetMergeUnitFunction
extends Function
implements INrFunction {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    private static final long serialVersionUID = 1L;

    public GetMergeUnitFunction() {
        this.parameters().add(new Parameter("UnitCode", 6, "\u672c\u65b9\u5355\u4f4d\u5b57\u6bb5"));
        this.parameters().add(new Parameter("OppUnitCode", 6, "\u5bf9\u65b9\u5355\u4f4d\u5b57\u6bb5"));
    }

    public String addDescribe() {
        return "\u81ea\u52a8\u83b7\u53d6\u5171\u540c\u4e0a\u7ea7\u51fd\u6570";
    }

    public String name() {
        return "GetMergeUnit";
    }

    public String title() {
        return "\u81ea\u52a8\u83b7\u53d6\u5171\u540c\u4e0a\u7ea7\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("UnitCode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u672c\u65b9\u5355\u4f4d\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("OppUnitCode").append("\uff1a").append(DataType.toString((int)6)).append("\u5bf9\u65b9\u5355\u4f4d\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u5408\u5e76\u5355\u4f4d\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u5f97\u672c\u65b9\u5355\u4f4d\u548c\u5bf9\u65b9\u5355\u4f4d\u7684\u5408\u5e76\u5355\u4f4d ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETMERGEUNIT(GC_INPUTDATA[ORGCODE],GC_INPUTDATA[OPPUNITID])").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() != 2) {
            return false;
        }
        QueryContext queryContext = (QueryContext)iContext;
        try {
            String unitCode = (String)parameters.get(0).evaluate(iContext);
            String oppUnitCode = (String)parameters.get(1).evaluate(iContext);
            String periodStr = (String)queryContext.getMasterKeys().getValue("DATATIME");
            YearPeriodObject yp = new YearPeriodObject(null, periodStr);
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)((String)((QueryContext)iContext).getMasterKeys().getValue("MD_GCORGTYPE")), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            return orgTool.getCommonUnit(orgTool.getOrgByCode(unitCode), orgTool.getOrgByCode(oppUnitCode)).getTitle();
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5171\u540c\u51fd\u6570\u51fa\u9519", e);
            return null;
        }
    }
}


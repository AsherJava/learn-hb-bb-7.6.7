/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlChgOrgFunction
extends Function
implements INrFunction {
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;

    public String name() {
        return "ISTKBDDW";
    }

    public String title() {
        return "\u662f\u5426\u662f\u540c\u63a7\u53d8\u52a8\u5355\u4f4d";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 1;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return this.isSameCtrlChgOrg((QueryContext)iContext);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder stringBuilder = new StringBuilder(supperDescription);
        stringBuilder.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u662f\u5426\u662f\u540c\u63a7\u53d8\u52a8\u5355\u4f4d").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("\u5224\u65ad\u662f\u5426\u662f\u540c\u63a7\u53d8\u52a8\u5355\u4f4d").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("ISTKBDDW()").append(StringUtils.LINE_SEPARATOR);
        return stringBuilder.toString();
    }

    private boolean isSameCtrlChgOrg(QueryContext queryContext) {
        DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
        String dataTime = (String)dimensionValueSet.getValue("DATATIME");
        String orgCode = (String)dimensionValueSet.getValue("MD_ORG");
        return this.sameCtrlChgOrgService.contasinsChangeOrg(orgCode, dataTime);
    }
}


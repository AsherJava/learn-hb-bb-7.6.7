/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.nr.impl.function.GcBaseExecutorContext
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.nr.impl.function.GcBaseExecutorContext;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ApplyToUnitFunction
extends Function
implements IGcFunction {
    private final String FUNCTION_NAME = "APPLYTOUNIT";

    public ApplyToUnitFunction() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4ee3\u7801"));
    }

    public String name() {
        return "APPLYTOUNIT";
    }

    public String title() {
        return "\u9002\u7528\u4e8e\u6b64\u5355\u4f4d";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String unitCode = (String)parameters.get(0).evaluate(context);
        OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode(unitCode);
        if (Objects.isNull(orgByCode)) {
            throw new BusinessRuntimeException(unitCode + "\u5728\u57fa\u7840\u7ec4\u7ec7\u4e2d\u672a\u67e5\u8be2\u5230");
        }
        return super.validate(context, parameters);
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        GcBaseExecutorContext context;
        String mdOrg;
        String unitCode = (String)parameters.get(0).evaluate(iContext);
        if (unitCode.equals(mdOrg = (context = (GcBaseExecutorContext)((QueryContext)iContext).getExeContext()).getOrgId())) {
            return true;
        }
        return false;
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuilder buffer = new StringBuilder(description);
        buffer.append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("APPLYTOUNIT(\"123456\")").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\u542b\u4e49\u662f\u89c4\u5219\u9002\u7528\u5f53\u524d\u5408\u5e76\u5355\u4f4d");
        return buffer.toString();
    }
}


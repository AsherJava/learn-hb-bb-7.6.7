/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Rpt_title
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(Rpt_title.class);
    private static final long serialVersionUID = 1L;

    public String category() {
        return "\u5176\u4ed6";
    }

    public Object evalute(IContext context, List<IASTNode> arg1) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        String rpt_code = qContext.getDefaultGroupName();
        if (StringUtils.isNotEmpty((CharSequence)rpt_code)) {
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            ReportFmlExecEnvironment e = (ReportFmlExecEnvironment)env;
            try {
                FormDefine formDefine = e.getController().queryFormByCodeInScheme(e.getFormSchemeKey(), rpt_code);
                return formDefine == null ? "" : formDefine.getTitle();
            }
            catch (Exception e1) {
                logger.error(e1.getMessage(), (Throwable)e1);
            }
        }
        return "";
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 6;
    }

    public String name() {
        return "rpt_title";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u6807\u9898";
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.nr.impl.function.NrFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportSystemFunction
extends NrFunction {
    public String name() {
        return "GetGcSystemId";
    }

    public String title() {
        return "\u83b7\u53d6\u5408\u5e76\u4f53\u7cfb\uff08\u4f9d\u636e\u5f53\u524d\u4efb\u52a1\u4e0e\u65f6\u671f\uff09";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        String schemeId = env.getFormSchemeKey();
        String dataTime = String.valueOf(queryContext.getCurrentMasterKey().getValue("DATATIME"));
        ConsolidatedTaskService systemService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        return systemService.getSystemIdBySchemeId(schemeId, dataTime);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.bpm.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;

public class GetUploadState
extends Function {
    public GetUploadState() {
        this.parameters().add(new Parameter("formSchemeCode", 6, "\u62a5\u8868\u65b9\u6848code"));
        this.parameters().add(new Parameter("entityKey", 0, "\u4e3b\u4f53\u6570\u636e\u9879\u6807\u8bc6\uff0c\u53ef\u4ee5\u662fcode\u6216\u8005id"));
        this.parameters().add(new Parameter("datetime", 6, "\u65f6\u671f"));
    }

    public String name() {
        return "GetUploadState";
    }

    public String title() {
        return "\u83b7\u53d6\u5355\u4f4d\u4e0a\u62a5\u72b6\u6001";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        if (iContext instanceof QueryContext) {
            QueryContext context = (QueryContext)iContext;
            try {
                ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)context.getExeContext().getEnv();
                IRunTimeViewController viewController = env.getController();
                String formSchemeCode = (String)parameters.get(0).evaluate((IContext)context);
                FormSchemeDefine formSchemeDefine = viewController.getFormschemeByCode(formSchemeCode);
                String entityKey = (String)parameters.get(1).evaluate((IContext)context);
                String dataTime = (String)parameters.get(2).evaluate((IContext)context);
                String unitKey = entityKey;
                String unitDimesion = env.getUnitDimesion(context.getExeContext());
                if (entityKey != null) {
                    DimensionTable entityTable;
                    DimensionRow row;
                    if (entityKey.length() != 32 && entityKey.length() != 36 && (row = (entityTable = context.getDimTable(unitDimesion)).findRowByCode(entityKey)) != null) {
                        unitKey = row.getKey();
                    }
                    return NrParameterUtils.getStateByAction(unitDimesion, unitKey, dataTime, formSchemeDefine);
                }
            }
            catch (Exception e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.quickreport.engine.context.IEvalTracer;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.function.GrowthRateFunction;
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionDescriptor;
import com.jiuqi.bi.quickreport.engine.result.TraceInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import java.util.Collection;

public class YOY
extends GrowthRateFunction {
    private static final long serialVersionUID = 3596476591014221665L;

    public String name() {
        return "YOY";
    }

    public String title() {
        return "\u8ba1\u7b97\u540c\u6bd4\u589e\u957f\u7387";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Number evalPrev(ReportContext context, IASTNode measure) throws SyntaxException {
        if (context.getCurrentCell() == null || context.getCurrentCell().getTracer() == null) {
            return super.evalPrev(context, measure);
        }
        IEvalTracer tracer = context.getCurrentCell().getTracer();
        try {
            context.getCurrentCell().setTracer((node, filters, result) -> {
                TraceInfo traceInfo = tracer.evalNode(node, filters, result);
                if (traceInfo != null) {
                    traceInfo.setTraceType(2);
                }
                return traceInfo;
            });
            Number number = super.evalPrev(context, measure);
            return number;
        }
        finally {
            context.getCurrentCell().setTracer(tracer);
        }
    }

    @Override
    protected RestrictionDescriptor createTimeOffsetter(Collection<DSFieldInfo> timeFields) throws SyntaxException {
        DSFieldInfo yearField = this.findYearField(timeFields);
        if (yearField != null) {
            return new RestrictionDescriptor(2, yearField, -1);
        }
        DSFieldInfo detailField = this.findDetailField(timeFields);
        if (detailField != null) {
            int offset = -TimeGranularity.YEAR.days() / detailField.field.getTimegranularity().days();
            return new RestrictionDescriptor(2, detailField, offset);
        }
        throw new SyntaxException("\u65e0\u6cd5\u57fa\u4e8e\u5f53\u524d\u9650\u5b9a\u6761\u4ef6\u8ba1\u7b97\u540c\u6bd4\u589e\u957f\u7387");
    }
}


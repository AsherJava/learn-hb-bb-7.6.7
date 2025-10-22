/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisConditionJudger;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedFloatRegionConfig;
import java.util.ArrayList;
import java.util.List;

public class AnalysisRegion {
    private List<IExpression> expressions = new ArrayList<IExpression>();
    private ParsedFloatRegionConfig config;
    private AnalysisConditionJudger conditionJuder;

    public List<IExpression> getExpressions() {
        return this.expressions;
    }

    public ParsedFloatRegionConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParsedFloatRegionConfig config) {
        this.config = config;
    }

    public AnalysisConditionJudger getConditionJuder() {
        if (this.conditionJuder == null) {
            this.conditionJuder = new AnalysisConditionJudger();
        }
        return this.conditionJuder;
    }
}


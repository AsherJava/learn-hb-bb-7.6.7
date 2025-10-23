/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 */
package com.jiuqi.nr.task.form.formio.formula;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportContext
implements IContext {
    private String sheet;
    private String formulaKey;
    private List<DesignFormulaDefine> formulaPath;
    private final ReportFormulaParser parser;
    private final Map<String, IExpression> expressionMap;

    public ReportContext(ReportFormulaParser parser) {
        this.parser = parser;
        this.expressionMap = new HashMap<String, IExpression>();
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getSheet() {
        return this.sheet;
    }

    public ReportFormulaParser getParser() {
        return this.parser;
    }

    public Map<String, IExpression> getExpressionMap() {
        return this.expressionMap;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
        this.formulaPath = new ArrayList<DesignFormulaDefine>();
    }

    public List<DesignFormulaDefine> getFormulaPath() {
        if (this.getFormulaKey() == null) {
            throw new RuntimeException("formulaKey is null");
        }
        return this.formulaPath;
    }
}


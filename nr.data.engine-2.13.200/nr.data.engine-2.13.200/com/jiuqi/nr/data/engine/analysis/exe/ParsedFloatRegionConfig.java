/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.nr.data.engine.analysis.define.FloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.define.GroupingConfig;
import com.jiuqi.nr.data.engine.analysis.define.OrderField;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedGroupingConfig;
import java.util.List;

public class ParsedFloatRegionConfig
extends FloatRegionConfig {
    private final FloatRegionConfig define;
    private CheckExpression srcMainDimCondition;
    private IExpression rowCondition;
    private ParsedGroupingConfig parsedGroupingConfig;

    public ParsedFloatRegionConfig(FloatRegionConfig define) {
        super(define.getRegionCode());
        this.define = define;
    }

    @Override
    public String getRegionCode() {
        return this.define.getRegionCode();
    }

    @Override
    public String getSrcMainDimFilter() {
        return this.define.getSrcMainDimFilter();
    }

    @Override
    public String getRowFilter() {
        return this.define.getRowFilter();
    }

    @Override
    public List<OrderField> getOrderFields() {
        return this.define.getOrderFields();
    }

    @Override
    public int getTopN() {
        return this.define.getTopN();
    }

    @Override
    public GroupingConfig getGroupingConfig() {
        return this.define.getGroupingConfig();
    }

    public CheckExpression getSrcMainDimCondition() {
        return this.srcMainDimCondition;
    }

    public IExpression getRowCondition() {
        return this.rowCondition;
    }

    public void setSrcMainDimCondition(CheckExpression srcMainDimCondition) {
        this.srcMainDimCondition = srcMainDimCondition;
    }

    public void setRowCondition(IExpression rowCondition) {
        this.rowCondition = rowCondition;
    }

    public ParsedGroupingConfig getParsedGroupingConfig() {
        return this.parsedGroupingConfig;
    }

    public void setParsedGroupingConfig(ParsedGroupingConfig parsedGroupingConfig) {
        this.parsedGroupingConfig = parsedGroupingConfig;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import java.util.List;

public class PreInterpretFml
extends Fml {
    private final Fml fml;
    private final IParsedExpression expression;

    public PreInterpretFml(Fml fml, IParsedExpression expression) {
        this.fml = fml;
        this.expression = expression;
    }

    public Fml getFml() {
        return this.fml;
    }

    public IParsedExpression getExpression() {
        return this.expression;
    }

    @Override
    public FmlNode getFmlNodeByDataLink(String dataLinkKey) {
        return this.fml.getFmlNodeByDataLink(dataLinkKey);
    }

    @Override
    public FmlNode getAssignNode() {
        return this.fml.getAssignNode();
    }

    @Override
    public List<FmlNode> getNodes() {
        return this.fml.getNodes();
    }

    @Override
    public String getExpressionKey() {
        return this.fml.getExpressionKey();
    }
}


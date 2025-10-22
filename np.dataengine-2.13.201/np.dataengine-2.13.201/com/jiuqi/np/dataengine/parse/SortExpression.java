/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class SortExpression {
    private IParsedExpression parsedExpression;
    private List<DynamicDataNode> relyNodes;
    public int tag;
    private List<SortExpression> nextExps;

    public SortExpression(IParsedExpression parsedExpression) {
        this.parsedExpression = parsedExpression;
    }

    public IParsedExpression getParsedExpression() {
        return this.parsedExpression;
    }

    public void setParsedExpression(IParsedExpression parsedExpression) {
        this.parsedExpression = parsedExpression;
    }

    public List<SortExpression> getNextExps() {
        if (this.nextExps == null) {
            this.nextExps = new ArrayList<SortExpression>();
        }
        return this.nextExps;
    }

    public List<DynamicDataNode> getRelyNodes() {
        if (this.relyNodes == null) {
            this.relyNodes = new ArrayList<DynamicDataNode>();
        }
        return this.relyNodes;
    }

    public String getSourceFomrulaKey() {
        return this.parsedExpression.getSource().getId();
    }

    public DynamicDataNode getAssignNode() {
        return this.parsedExpression.getAssignNode();
    }
}


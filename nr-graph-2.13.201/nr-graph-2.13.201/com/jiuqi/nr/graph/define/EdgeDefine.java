/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.define;

import com.jiuqi.nr.graph.function.EdgePredicate;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.NodeLabel;

public class EdgeDefine
implements ILabelabled<EdgeLabel> {
    private EdgeLabel label;
    private NodeLabel outLabel;
    private NodeLabel inLabel;
    private EdgePredicate predicate;

    protected EdgeDefine(EdgeLabel label, NodeLabel outLabel, NodeLabel inLabel) {
        this(label, outLabel, inLabel, null);
    }

    protected EdgeDefine(EdgeLabel label, NodeLabel outLabel, NodeLabel inLabel, EdgePredicate predicate) {
        this.label = label;
        this.outLabel = outLabel;
        this.inLabel = inLabel;
        this.predicate = predicate;
    }

    @Override
    public EdgeLabel getLabel() {
        return this.label;
    }

    public NodeLabel getOutLabel() {
        return this.outLabel;
    }

    public NodeLabel getInLabel() {
        return this.inLabel;
    }

    public EdgePredicate getPredicate() {
        return this.predicate;
    }
}


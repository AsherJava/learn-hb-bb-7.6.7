/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.define;

import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;

public class IndexDefine
implements ILabelabled<IndexLabel> {
    private IndexLabel label;
    private NodeLabel nodeLabel;
    private AttrValueGetter<INode, String> keyGetter;

    protected IndexDefine(IndexLabel label, NodeLabel nodeLabel, AttrValueGetter<INode, String> keyGetter) {
        this.label = label;
        this.nodeLabel = nodeLabel;
        this.keyGetter = keyGetter;
    }

    @Override
    public IndexLabel getLabel() {
        return this.label;
    }

    public NodeLabel getNodeLabel() {
        return this.nodeLabel;
    }

    public AttrValueGetter<INode, String> getKeyGetter() {
        return this.keyGetter;
    }
}


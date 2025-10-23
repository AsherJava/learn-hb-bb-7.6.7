/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.define;

import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.exception.GraphDefineException;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.IteratorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeDefine
implements ILabelabled<NodeLabel> {
    private NodeLabel label;
    private AttrValueGetter<Object, String> keyGetter;
    private List<EdgeDefine> outEdgeDefines;
    private List<EdgeDefine> inEdgeDefines;

    protected NodeDefine(NodeLabel label, AttrValueGetter<Object, String> keyGetter) {
        this.label = label;
        this.keyGetter = keyGetter;
        this.outEdgeDefines = new ArrayList<EdgeDefine>();
        this.inEdgeDefines = new ArrayList<EdgeDefine>();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void addEdgeDefine(EdgeDefine edgeDefine) {
        if (edgeDefine.getOutLabel().equals(edgeDefine.getInLabel())) {
            if (!this.getLabel().equals(edgeDefine.getOutLabel())) {
                throw new GraphDefineException("\u8282\u70b9\u5b9a\u4e49\u4e0e\u53d8\u5b9a\u4e49\u4e0d\u5339\u914d");
            }
            if (!this.outEdgeDefines.contains(edgeDefine)) {
                this.outEdgeDefines.add(edgeDefine);
                return;
            } else {
                if (this.inEdgeDefines.contains(edgeDefine)) throw new GraphDefineException("\u91cd\u590d\u6dfb\u52a0\u8fb9\u5b9a\u4e49");
                this.inEdgeDefines.add(edgeDefine);
            }
            return;
        } else if (this.getLabel().equals(edgeDefine.getOutLabel())) {
            this.outEdgeDefines.add(edgeDefine);
            return;
        } else {
            if (!this.getLabel().equals(edgeDefine.getInLabel())) throw new GraphDefineException("\u8282\u70b9\u5b9a\u4e49\u4e0e\u53d8\u5b9a\u4e49\u4e0d\u5339\u914d");
            this.inEdgeDefines.add(edgeDefine);
        }
    }

    @Override
    public NodeLabel getLabel() {
        return this.label;
    }

    public AttrValueGetter<Object, String> getKeyGetter() {
        return this.keyGetter;
    }

    public int getOutEdgeDefinesSize() {
        return this.outEdgeDefines.size();
    }

    public EdgeDefine getOutEdgeDefine(EdgeLabel edgeLabel) {
        return this.outEdgeDefines.get(edgeLabel.getOutIndex());
    }

    public Iterator<EdgeDefine> outEdgeDefineIterator() {
        return IteratorUtils.toIterator(this.outEdgeDefines);
    }

    public int getInEdgeDefinesSize() {
        return this.inEdgeDefines.size();
    }

    public EdgeDefine getInEdgeDefine(EdgeLabel edgeLabel) {
        return this.inEdgeDefines.get(edgeLabel.getInIndex());
    }

    public Iterator<EdgeDefine> inEdgeDefineIterator() {
        return IteratorUtils.toIterator(this.inEdgeDefines);
    }
}


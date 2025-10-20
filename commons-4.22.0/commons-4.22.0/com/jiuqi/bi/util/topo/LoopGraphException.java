/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.topo;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.topo.Node;
import java.util.List;

public class LoopGraphException
extends Exception {
    private static final long serialVersionUID = -3110449660068750038L;
    private Node[] loopNodes;
    private List<List<Node>> cycles;

    public LoopGraphException(Node[] loopNodes) {
        super("\u5b58\u5728\u73af\u8def\uff0c\u65e0\u6cd5\u6784\u5efatopo\u6392\u5e8f\u3002");
        this.loopNodes = loopNodes;
    }

    public LoopGraphException(List<List<Node>> cycles) {
        super("\u5b58\u5728\u73af\u8def\uff0c\u65e0\u6cd5\u6784\u5efatopo\u6392\u5e8f\u3002");
        this.cycles = cycles;
    }

    public Node[] getLoopNodes() {
        return this.loopNodes;
    }

    public List<List<Node>> getAllCycles() {
        return this.cycles;
    }

    @Override
    public String getMessage() {
        StringBuffer sb = new StringBuffer(super.getMessage());
        if (this.loopNodes == null) {
            sb.append("\u5171\u6709").append(this.cycles.size()).append("\u4e2a\u73af\u8def\u3002").append("\n");
            for (List<Node> cycle : this.cycles) {
                sb.append(StringUtils.join(cycle.iterator(), (String)" -> ")).append("\n");
            }
        } else {
            sb.append("\u5faa\u73af\u8282\u70b9\u5305\u62ec\uff1a");
            sb.append(StringUtils.join((Object[])this.loopNodes, (String)" -> "));
        }
        return sb.toString();
    }
}


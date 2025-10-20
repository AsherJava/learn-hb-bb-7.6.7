/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Path;

public class VisitLoopException
extends GraphException {
    private static final long serialVersionUID = 1L;

    public VisitLoopException(String msg) {
        super(msg);
    }

    public VisitLoopException(Path path) {
        super("\u7f51\u7edc\u56fe\u4e2d\u51fa\u73b0\u5faa\u73af\u4f9d\u8d56\uff1a" + path.toString());
    }
}


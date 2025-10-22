/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.nr.data.excel.export.PathTreeNode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class PathTreeBuilder {
    private final PathTreeNode tree = new PathTreeNode("");

    public PathTreeBuilder addPath(String path, String separator) {
        List pathNodes = Arrays.stream(path.split("/|\\\\")).filter(StringUtils::hasText).collect(Collectors.toList());
        PathTreeNode curNode = this.tree;
        for (String pathNode : pathNodes) {
            curNode = curNode.appendOrGetNode(pathNode);
        }
        return this;
    }

    public PathTreeNode getTree() {
        return this.tree;
    }
}


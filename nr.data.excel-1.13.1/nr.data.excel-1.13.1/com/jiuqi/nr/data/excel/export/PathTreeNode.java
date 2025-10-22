/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class PathTreeNode {
    private final String pathNode;
    private final List<PathTreeNode> children;

    public PathTreeNode(String pathNode) {
        this.pathNode = pathNode;
        this.children = new ArrayList<PathTreeNode>();
    }

    public String getPathNode() {
        return this.pathNode;
    }

    public List<PathTreeNode> getChildren() {
        return this.children;
    }

    public PathTreeNode appendOrGetNode(String pathNode) {
        boolean exist = false;
        PathTreeNode curNode = null;
        for (PathTreeNode child : this.children) {
            if (!pathNode.equals(child.getPathNode())) continue;
            curNode = child;
            exist = true;
            break;
        }
        if (!exist) {
            curNode = new PathTreeNode(pathNode);
            this.children.add(curNode);
        }
        return curNode;
    }

    public Map<String, String> getSimplePathMap(String pathSeparator, String simpleSeparator) {
        ArrayList<String> paths = new ArrayList<String>();
        PathTreeNode.appendPath(paths, this, new StringBuilder(this.pathNode), pathSeparator, simpleSeparator, false);
        ArrayList<String> simplePaths = new ArrayList<String>();
        PathTreeNode.appendPath(simplePaths, this, new StringBuilder(this.pathNode), pathSeparator, simpleSeparator, true);
        HashMap<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < paths.size(); ++i) {
            result.put((String)paths.get(i), (String)simplePaths.get(i));
        }
        return result;
    }

    private static void appendPath(List<String> pathCol, PathTreeNode node, StringBuilder curPath, String pathSeparator, String simplifySeparator, boolean simplify) {
        if (node == null) {
            return;
        }
        if (CollectionUtils.isEmpty(node.getChildren())) {
            pathCol.add(curPath.append(pathSeparator).toString());
        } else {
            String separator = simplify && node.getChildren().size() == 1 ? simplifySeparator : pathSeparator;
            for (PathTreeNode child : node.getChildren()) {
                StringBuilder newCurPath = new StringBuilder();
                if (StringUtils.hasText(curPath)) {
                    newCurPath.append((CharSequence)curPath).append(separator);
                }
                if (StringUtils.hasText(child.getPathNode())) {
                    newCurPath.append(child.getPathNode());
                }
                PathTreeNode.appendPath(pathCol, child, newCurPath, pathSeparator, simplifySeparator, simplify);
            }
        }
    }
}


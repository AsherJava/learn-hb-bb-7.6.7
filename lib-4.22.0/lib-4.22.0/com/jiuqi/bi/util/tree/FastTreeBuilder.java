/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.ErrorHandler;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class FastTreeBuilder
implements TreeBuilder {
    private ErrorHandler errorHandler;
    private int sortMode = 0;
    protected ObjectVistor visitor;

    public FastTreeBuilder(ObjectVistor visitor) {
        this.visitor = visitor;
    }

    @Override
    public void setErrorHandler(ErrorHandler handler) {
        this.errorHandler = handler;
    }

    @Override
    public TreeNode build(Iterator iterator) throws TreeException {
        TreeNode node;
        int i;
        ArrayList<TreeNode> allNodes = new ArrayList<TreeNode>();
        HashMap<String, TreeNode> nodeFinder = new HashMap<String, TreeNode>();
        int index = 0;
        while (iterator.hasNext()) {
            Object item = iterator.next();
            TreeNode node2 = new TreeNode(item);
            node2.setTag(new BuildInfo(index++));
            allNodes.add(node2);
            nodeFinder.put(this.getObjectCode(item), node2);
        }
        TreeNode root = new TreeNode();
        TreeSet<Object> paths = new TreeSet<Object>(new Comparator(){

            public int compare(Object o1, Object o2) {
                String code2;
                String code1 = FastTreeBuilder.this.getObjectCode(o1);
                if (code1 == (code2 = FastTreeBuilder.this.getObjectCode(o2))) {
                    return 0;
                }
                if (code1 == null) {
                    return -1;
                }
                return code1.compareTo(code2);
            }
        });
        for (i = 0; i < allNodes.size(); ++i) {
            node = (TreeNode)allNodes.get(i);
            if (node.getParent() == null && !((BuildInfo)node.getTag()).processed) {
                paths.clear();
                while (true) {
                    ((BuildInfo)node.getTag()).processed = true;
                    paths.add(node.getItem());
                    String parentCode = this.getObjectParent(node.getItem());
                    if (parentCode == null || parentCode.length() == 0) break;
                    TreeNode parent = (TreeNode)nodeFinder.get(parentCode);
                    if (parent == null) {
                        if (this.errorHandler == null) break;
                        this.errorHandler.onParentNotFound(node.getItem());
                        break;
                    }
                    if (paths.contains(parent.getItem())) {
                        if (this.errorHandler == null) break;
                        this.errorHandler.onPathLooped(paths.iterator());
                        break;
                    }
                    node.setParent(parent);
                    node = parent;
                }
            }
            if ((node = (TreeNode)allNodes.get(i)).getParent() != null) continue;
            node.setParent(root);
        }
        switch (this.sortMode) {
            case 1: {
                root.sort(new Comparator(){

                    public int compare(Object o1, Object o2) {
                        BuildInfo info1 = (BuildInfo)((TreeNode)o1).getTag();
                        BuildInfo info2 = (BuildInfo)((TreeNode)o2).getTag();
                        return info1.index - info2.index;
                    }
                });
                break;
            }
            case 2: {
                root.sort(new Comparator(){

                    public int compare(Object o1, Object o2) {
                        String code2;
                        String code1 = FastTreeBuilder.this.visitor.getCode(o1);
                        if (code1 == (code2 = FastTreeBuilder.this.visitor.getCode(o2))) {
                            return 0;
                        }
                        if (code1 == null) {
                            return -1;
                        }
                        return code1.compareTo(code2);
                    }
                });
            }
        }
        for (i = 0; i < allNodes.size(); ++i) {
            node = (TreeNode)allNodes.get(i);
            node.setTag(null);
        }
        return root;
    }

    @Override
    public int getSortMode() {
        return this.sortMode;
    }

    @Override
    public void setSortMode(int sortMode) {
        this.sortMode = sortMode;
    }

    protected abstract String getObjectCode(Object var1);

    protected abstract String getObjectParent(Object var1);

    private static final class BuildInfo {
        public final int index;
        public boolean processed = false;

        public BuildInfo(int index) {
            this.index = index;
        }
    }
}


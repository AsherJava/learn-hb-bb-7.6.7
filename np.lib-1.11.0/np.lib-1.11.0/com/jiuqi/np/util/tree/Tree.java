/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.np.util.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.util.Escaper;
import com.jiuqi.np.util.IObjectAdapter;
import com.jiuqi.np.util.tree.BreadthFirstIterator;
import com.jiuqi.np.util.tree.DepthFirstIterator;
import com.jiuqi.np.util.tree.ReverseBreadthFirstIterator;
import com.jiuqi.np.util.tree.ReverseDepthFirstIterator;
import com.jiuqi.np.util.tree.TraversalIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Tree<E>
implements Iterable<Tree<E>>,
Cloneable,
Serializable {
    private static final long serialVersionUID = -360536055185124669L;
    private E data;
    private List<Tree<E>> children;
    @JsonIgnore
    private Tree<E> parent;
    @JsonIgnore
    private LinkedList<Tree<E>> _path;
    public static final Escaper ESCAPER = new Escaper(new String[][]{{"&", "&amp;"}, {";", "&sc;"}, {" ", "&space;"}, {"\"", "&dq;"}, {"(", "&lp;"}, {")", "&rp;"}, {"[", "&lsb;"}, {"]", "&rsb;"}}, 2);

    public Tree() {
        this(null);
    }

    public Tree(E data) {
        this.data = data;
        this.children = null;
        this.parent = null;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<Tree<E>> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return this.children != null && this.children.size() > 0;
    }

    public int numChildren() {
        return this.children != null ? this.children.size() : 0;
    }

    @JsonIgnore
    public Tree<E> getParent() {
        return this.parent;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return !this.hasChildren();
    }

    @JsonIgnore
    public boolean isRoot() {
        return null == this.parent;
    }

    public int depth() {
        int result = 0;
        Tree<E> p = this.parent;
        while (p != null) {
            p = p.parent;
            ++result;
        }
        return result;
    }

    public int depth(Tree<E> ancestor) {
        int result = 0;
        Tree<E> p = this.parent;
        while (p != ancestor && p != null) {
            p = p.parent;
            ++result;
        }
        return result;
    }

    @JsonIgnore
    public int getNumSiblings() {
        int result = 0;
        if (this.parent != null && this.parent.children != null) {
            result = this.parent.children.size();
        }
        return result;
    }

    @JsonIgnore
    public List<Tree<E>> getLocalSiblings() {
        ArrayList<Tree<Tree<E>>> result = new ArrayList<Tree<Tree<E>>>();
        if (this.parent == null) {
            result.add(this);
        } else {
            result.addAll(this.parent.children);
        }
        return result;
    }

    @JsonIgnore
    public int getLocalSiblingPosition() {
        int result = 0;
        if (this.parent != null) {
            result = this.getPosition(this, this.parent.children);
        }
        return result;
    }

    @JsonIgnore
    public Tree<E> getNextSibling() {
        List<Tree<E>> siblings;
        int sibPos;
        Tree<E> result = null;
        if (this.parent != null && (sibPos = this.getPosition(this, siblings = this.parent.children) + 1) < siblings.size()) {
            result = siblings.get(sibPos);
        }
        return result;
    }

    @JsonIgnore
    public Tree<E> getPrevSibling() {
        List<Tree<E>> siblings;
        int sibPos;
        Tree<E> result = null;
        if (this.parent != null && (sibPos = this.getPosition(this, siblings = this.parent.children) - 1) >= 0) {
            result = siblings.get(sibPos);
        }
        return result;
    }

    @JsonIgnore
    private final int getPosition(Tree<E> node, List<Tree<E>> siblings) {
        int result = 0;
        for (Tree<E> sibling : siblings) {
            if (node == sibling) {
                return result;
            }
            ++result;
        }
        return result;
    }

    @JsonIgnore
    public List<Tree<E>> getGlobalSiblings() {
        return this.getRoot().treesAtDepth(this.depth());
    }

    @JsonIgnore
    public int getGlobalSiblingPosition() {
        return this.getPosition(this, this.getGlobalSiblings());
    }

    @JsonIgnore
    public Tree<E> getRoot() {
        Tree<E> root = this;
        while (root.parent != null) {
            root = root.parent;
        }
        return root;
    }

    @JsonIgnore
    public boolean isAncestor(Tree<E> other) {
        Tree<E> parent = other.parent;
        while (parent != null) {
            if (parent == this) {
                return true;
            }
            parent = parent.parent;
        }
        return false;
    }

    public boolean isDescendant(Tree<E> other) {
        return other.isAncestor(this);
    }

    public List<Tree<E>> treesAtDepth(int depth) {
        ArrayList<Tree<Tree<E>>> result = new ArrayList<Tree<Tree<E>>>();
        if (depth == 0) {
            result.add(this);
        } else if (depth > 0 && this.children != null) {
            for (Tree<E> child : this.children) {
                result.addAll(child.treesAtDepth(depth - 1));
            }
        }
        return result;
    }

    public int maxDepth() {
        int depth = 1;
        if (this.children != null) {
            int maxChildDepth = 0;
            for (Tree<E> child : this.children) {
                int childDepth = child.maxDepth();
                if (childDepth <= maxChildDepth) continue;
                maxChildDepth = childDepth;
            }
            depth += maxChildDepth;
        }
        return depth;
    }

    public int numLeaves() {
        int leaves = 1;
        if (null == this.children) {
            int childLeafCount = 0;
            for (Tree<E> child : this.children) {
                childLeafCount += child.numLeaves();
            }
            return childLeafCount;
        }
        return leaves;
    }

    public Tree<E> addChild(E childData) {
        Tree<E> result = new Tree<E>(childData);
        this.addChild(result);
        return result;
    }

    public void addChild(Tree<E> child) {
        if (null == this.children) {
            this.children = new ArrayList<Tree<E>>();
        }
        this.children.add(child);
        child.parent = this;
    }

    public Tree<E> addChild(int index, E childData) {
        Tree<E> result = new Tree<E>(childData);
        this.addChild(index, result);
        return result;
    }

    public void addChild(int index, Tree<E> child) {
        if (null == this.children) {
            this.children = new ArrayList<Tree<E>>();
        }
        this.children.add(index, child);
        child.parent = this;
    }

    @JsonIgnore
    public List<Tree<E>> getPath() {
        if (null == this._path) {
            this._path = new LinkedList();
            for (Tree<E> curNode = this; null != curNode; curNode = curNode.getParent()) {
                this._path.addFirst(curNode);
            }
        }
        return this._path;
    }

    @JsonIgnore
    public List<List<E>> getPaths() {
        return this.getPathsAux(new ArrayList());
    }

    @JsonIgnore
    public String getPathString() {
        StringBuilder result = new StringBuilder();
        for (Tree<E> node = this; null != node; node = node.getParent()) {
            if (result.length() > 0) {
                result.insert(0, '.');
            }
            result.insert(0, node.getData().toString());
        }
        return result.toString();
    }

    @Override
    public Iterator<Tree<E>> iterator() {
        return this.breadthFirstIterator();
    }

    public TraversalIterator<E> breadthFirstIterator() {
        return new BreadthFirstIterator(this);
    }

    public TraversalIterator<E> depthFirstIterator() {
        return new DepthFirstIterator(this);
    }

    public TraversalIterator<E> iterator(Traversal traversal) {
        TraversalIterator<E> result = null;
        switch (traversal) {
            case REVERSE_DEPTH_FIRST: {
                result = new ReverseDepthFirstIterator<E>(this);
                break;
            }
            case DEPTH_FIRST: {
                result = this.depthFirstIterator();
                break;
            }
            case REVERSE_BREADTH_FIRST: {
                result = new ReverseBreadthFirstIterator<E>(this);
                break;
            }
            default: {
                result = this.breadthFirstIterator();
            }
        }
        return result;
    }

    public List<Tree<E>> findNodes(E data, Traversal traversal) {
        ArrayList<Tree> result = null;
        TraversalIterator<E> it = this.iterator(traversal);
        while (it.hasNext()) {
            Tree node = (Tree)it.next();
            E nodeData = node.getData();
            if (nodeData != data && (nodeData == null || !nodeData.equals(data))) continue;
            if (result == null) {
                result = new ArrayList<Tree>();
            }
            result.add(node);
        }
        return result;
    }

    public Tree<E> findFirst(E data) {
        return this.findFirst(data, Traversal.BREADTH_FIRST);
    }

    public Tree<E> findFirst(E data, Traversal traversal) {
        Tree result = null;
        TraversalIterator<E> it = this.iterator(traversal);
        while (it.hasNext()) {
            Tree node = (Tree)it.next();
            E nodeData = node.getData();
            if (nodeData != data && (nodeData == null || !nodeData.equals(data))) continue;
            result = node;
            break;
        }
        return result;
    }

    public void prune() {
        this.parent = null;
    }

    public boolean prune(boolean disconnectParent, boolean disconnectAsChild) {
        boolean removed = false;
        if (disconnectAsChild && this.parent != null && this.parent.children != null) {
            removed = this.parent.children.remove(this);
            if (this.parent.children.size() == 0) {
                this.parent.children = null;
            }
        }
        if (disconnectParent) {
            this.parent = null;
        }
        return removed;
    }

    public void moveChildrenTo(Tree<E> newParent) {
        if (this.children != null && this.children.size() > 0) {
            ArrayList<Tree<E>> children = new ArrayList<Tree<E>>(this.children);
            for (Tree tree : children) {
                tree.prune(true, true);
                newParent.addChild(tree);
            }
        }
    }

    public List<Tree<E>> pruneChildren() {
        List<Tree<E>> result = this.children;
        this.children = null;
        return result;
    }

    public Tree<E> pruneChild(int index) {
        Tree<E> oldValue = this.children.remove(index);
        if (this.parent.children.size() == 0) {
            this.parent.children = null;
        }
        oldValue.parent = null;
        return oldValue;
    }

    public boolean pruneChild(E childData) {
        for (Tree<E> child : this.children) {
            if ((null != childData || null != child.getData()) && (null == childData || !childData.equals(child.getData()))) continue;
            return this.pruneChild(child);
        }
        return false;
    }

    public boolean pruneChild(Tree<E> child) {
        return child.prune(true, true);
    }

    public void clear() {
        if (null != this.children) {
            this.children.clear();
            this.children = null;
        }
    }

    public void upgrade() {
    }

    public void downgrade() {
    }

    public List<Tree<E>> gatherLeaves() {
        ArrayList<Tree<E>> result = new ArrayList<Tree<E>>();
        this._gatherLeaves(result);
        return result;
    }

    private final void _gatherLeaves(List<Tree<E>> result) {
        if (this.children == null) {
            result.add(this);
        } else {
            for (Tree<E> child : this.children) {
                super._gatherLeaves(result);
            }
        }
    }

    @JsonIgnore
    public Tree<E> getDeepestCommonAncestor(Tree<E> other) {
        Tree<E> otherNode;
        Tree<E> myNode;
        if (other == null) {
            return null;
        }
        if (this == other) {
            return this;
        }
        Tree<E> result = null;
        Iterator<Tree<E>> myPathIter = this.getPath().iterator();
        Iterator<Tree<E>> otherPathIter = other.getPath().iterator();
        while (myPathIter.hasNext() && otherPathIter.hasNext() && (myNode = myPathIter.next()) == (otherNode = otherPathIter.next())) {
            result = myNode;
        }
        return result;
    }

    @JsonIgnore
    private List<List<E>> getPathsAux(List<E> basePath) {
        ArrayList<List<List<E>>> result = new ArrayList<List<List<E>>>();
        ArrayList<E> path = new ArrayList<E>(basePath);
        path.add(this.data);
        if (this.children != null) {
            for (Tree<E> child : this.children) {
                result.addAll(super.getPathsAux(path));
            }
        } else {
            result.add(path);
        }
        return result;
    }

    public String toString() {
        return this.toString(ESCAPER);
    }

    public String toString(Escaper escaper) {
        String result = null;
        String data = this.getData() == null ? "<null>" : this.getData().toString();
        List<Tree<E>> children = this.getChildren();
        if (children == null || children.size() == 0) {
            result = escaper.escape(data);
        } else {
            StringBuilder buffer = new StringBuilder();
            buffer.append('(');
            buffer.append(escaper.escape(data));
            for (Tree<E> child : children) {
                buffer.append(' ');
                buffer.append(child.toString(escaper));
            }
            buffer.append(')');
            result = buffer.toString();
        }
        return result;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        boolean result = false;
        Tree otherTree = null;
        if (other instanceof Tree) {
            otherTree = (Tree)other;
        }
        if (otherTree != null) {
            if (this.data == null && otherTree.data != null) {
                return false;
            }
            if (this.data == null && otherTree.data == null || this.data.equals(otherTree.data)) {
                if (this.children == null) {
                    result = otherTree.children == null;
                } else if (otherTree.children != null) {
                    result = this.children.equals(otherTree.children);
                }
            }
        }
        return result;
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.data == null ? 0 : this.data.hashCode());
        if (this.children != null) {
            result = result * 31 + this.children.hashCode();
        }
        return result;
    }

    public Object clone() {
        Tree t = null;
        try {
            t = (Tree)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        t.parent = null;
        t._path = null;
        if (null != this.children) {
            t.children = null;
            for (Tree<E> tree : this.children) {
                Tree c = (Tree)tree.clone();
                t.addChild(c);
            }
        }
        return t;
    }

    public <F> Tree<F> adapter(IObjectAdapter<E, F> objectAdapter) {
        Tree<F> t = new Tree<F>(objectAdapter.adapter(this.data));
        if (null != this.children) {
            for (Tree<E> tree : this.children) {
                Tree<F> conver = tree.adapter(objectAdapter);
                t.addChild(conver);
            }
        }
        return t;
    }

    public static enum Traversal {
        BREADTH_FIRST,
        DEPTH_FIRST,
        REVERSE_BREADTH_FIRST,
        REVERSE_DEPTH_FIRST;

    }
}


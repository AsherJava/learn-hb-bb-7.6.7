/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.itree;

import java.util.ArrayList;
import java.util.List;

public class INodeMap<E> {
    private String key;
    private E data;
    private INodeMap<E> parent;
    private List<INodeMap<E>> children = new ArrayList<INodeMap<E>>(0);

    public INodeMap(String key, E data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public INodeMap<E> getParent() {
        return this.parent;
    }

    public void setParent(INodeMap<E> parent) {
        this.parent = parent;
    }

    public List<INodeMap<E>> getChildren() {
        return this.children;
    }

    public void appendChild(INodeMap<E> child) {
        if (child != null) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    public void appendChild(String key, E data) {
        INodeMap<E> child = new INodeMap<E>(key, data);
        child.setParent(this);
        this.children.add(child);
    }

    public void appendChild(String key, int index, E data) {
        INodeMap<E> child = new INodeMap<E>(key, data);
        child.setParent(this);
        this.children.add(index, child);
    }

    public List<E> getChildDataList() {
        ArrayList<E> datalist = new ArrayList<E>(0);
        for (INodeMap<E> node : this.children) {
            datalist.add(node.getData());
        }
        return datalist;
    }
}


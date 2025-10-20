/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.tree;

public class RBTreeNode<E> {
    private RBTreeNode<E> leftChild = null;
    private RBTreeNode<E> rightChild = null;
    private RBTreeNode<E> partentNode = null;
    private boolean colorSymbol = false;
    private E data;

    public RBTreeNode() {
    }

    public RBTreeNode(E object) {
        RBTreeNode<E> node = new RBTreeNode<E>();
        node.setData(object);
    }

    public RBTreeNode(boolean colorSymbol) {
        RBTreeNode<E> node = new RBTreeNode<E>();
        node.setColorSymbol(colorSymbol);
    }

    public RBTreeNode<E> getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(RBTreeNode<E> leftChild) {
        this.leftChild = leftChild;
    }

    public RBTreeNode<E> getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(RBTreeNode<E> rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isColorSymbol() {
        return this.colorSymbol;
    }

    public void setColorSymbol(boolean colorSymbol) {
        this.colorSymbol = colorSymbol;
    }

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return this.data;
    }

    public void setPartentNode(RBTreeNode<E> partentNode) {
        this.partentNode = partentNode;
    }

    public RBTreeNode<E> getPartentNode() {
        return this.partentNode;
    }

    public RBTreeNode<E> getPre() {
        RBTreeNode<E> node = this;
        if (node.getLeftChild() != null && node.getLeftChild().getLeftChild() != null) {
            node = node.getLeftChild();
            while (node.getRightChild() != null && node.getRightChild().getRightChild() != null) {
                node = node.getRightChild();
            }
            return node;
        }
        RBTreeNode<E> nodeParent = node.getPartentNode();
        while (nodeParent != null && nodeParent.getLeftChild() == node) {
            node = nodeParent;
            if ((nodeParent = nodeParent.getPartentNode()) != null) continue;
            return null;
        }
        return nodeParent;
    }

    public RBTreeNode<E> getNext() {
        RBTreeNode<E> node = this;
        if (node.getRightChild() != null && node.getRightChild().getRightChild() != null) {
            node = node.getRightChild();
            while (node.getRightChild() != null && node.getLeftChild().getLeftChild() != null) {
                node = node.getLeftChild();
            }
            return node;
        }
        RBTreeNode<E> nodeParent = node.getPartentNode();
        while (nodeParent != null && nodeParent.getRightChild() == node) {
            node = nodeParent;
            nodeParent = node.getPartentNode();
        }
        if (nodeParent == null || nodeParent.getPartentNode() == null) {
            return null;
        }
        return nodeParent;
    }

    public String toString() {
        if (this.data == null) {
            return "null";
        }
        return this.data.toString();
    }
}


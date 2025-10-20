/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.tree;

import com.jiuqi.bi.tree.RBTreeIterator;
import com.jiuqi.bi.tree.RBTreeNode;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public final class RedBlackTree<E> {
    private RBTreeNode<E> root;
    private Comparator<? super E> comparator;
    private RBTreeNode<E> nil;
    private int count;
    private Iterator<E> qIterator = null;

    public RedBlackTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.nil = new RBTreeNode();
        this.nil.setColorSymbol(true);
        this.root = this.nil;
        this.count = 0;
        this.qIterator = new RBTreeIterator(this);
    }

    public RedBlackTree(Comparator<? super E> comparator, E[] datas) {
        this.comparator = comparator;
        this.nil = new RBTreeNode();
        this.nil.setColorSymbol(true);
        this.root = this.nil;
        this.count = 0;
        if (datas == null) {
            throw new IllegalArgumentException("\u6570\u7ec4\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        for (int i = 0; i < datas.length; ++i) {
            this.add(datas[i]);
        }
        this.qIterator = new RBTreeIterator(this);
    }

    public int size() {
        return this.count;
    }

    public RBTreeNode<E> getNil() {
        return this.nil;
    }

    public RedBlackTree(Comparator<? super E> comparator, Collection<? extends E> c) {
        this.comparator = comparator;
        this.nil = new RBTreeNode();
        this.nil.setColorSymbol(true);
        this.root = this.nil;
        if (c == null) {
            throw new IllegalArgumentException("\u96c6\u5408\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }

    private void leftRotate(RedBlackTree<E> tree, RBTreeNode<E> node) {
        RBTreeNode<E> y = node.getRightChild();
        node.setRightChild(y.getLeftChild());
        y.getLeftChild().setPartentNode(node);
        y.setPartentNode(node.getPartentNode());
        if (node.getPartentNode() == this.nil) {
            this.root = y;
        } else if (node.getPartentNode().getLeftChild() == node) {
            node.getPartentNode().setLeftChild(y);
        } else {
            node.getPartentNode().setRightChild(y);
        }
        y.setLeftChild(node);
        node.setPartentNode(y);
    }

    private void leftRotate(RBTreeNode<E> node) {
        if (node != null) {
            this.leftRotate(this, node);
        }
    }

    private void rightRotate(RedBlackTree<E> tree, RBTreeNode<E> node) {
        RBTreeNode<E> y = node.getLeftChild();
        node.setLeftChild(y.getRightChild());
        y.getRightChild().setPartentNode(node);
        y.setPartentNode(node.getPartentNode());
        if (node.getPartentNode() == this.nil) {
            this.root = y;
        } else if (node.getPartentNode().getLeftChild() == node) {
            node.getPartentNode().setLeftChild(y);
        } else {
            node.getPartentNode().setRightChild(y);
        }
        y.setRightChild(node);
        node.setPartentNode(y);
    }

    private void rightRotate(RBTreeNode<E> node) {
        if (node != null) {
            this.rightRotate(this, node);
        }
    }

    public boolean add(E[] datas) {
        if (datas == null) {
            throw new IllegalArgumentException("\u6570\u7ec4\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        for (int i = 0; i < datas.length; ++i) {
            this.add(datas[i]);
        }
        return true;
    }

    public boolean add(Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException("\u96c6\u5408\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
        return true;
    }

    public boolean add(E object) {
        RBTreeNode<E> z = this.insert(object);
        while (z.getPartentNode() != this.nil && !z.getPartentNode().isColorSymbol()) {
            RBTreeNode<E> y;
            if (z.getPartentNode() == z.getPartentNode().getPartentNode().getLeftChild()) {
                y = z.getPartentNode().getPartentNode().getRightChild();
                if (!y.isColorSymbol()) {
                    z.getPartentNode().setColorSymbol(true);
                    y.setColorSymbol(true);
                    z.getPartentNode().getPartentNode().setColorSymbol(false);
                    z = z.getPartentNode().getPartentNode();
                    continue;
                }
                if (z == z.getPartentNode().getRightChild()) {
                    z = z.getPartentNode();
                    this.leftRotate(z);
                }
                z.getPartentNode().setColorSymbol(true);
                z.getPartentNode().getPartentNode().setColorSymbol(false);
                this.rightRotate(z.getPartentNode().getPartentNode());
                continue;
            }
            y = z.getPartentNode().getPartentNode().getLeftChild();
            if (!y.isColorSymbol()) {
                z.getPartentNode().setColorSymbol(true);
                y.setColorSymbol(true);
                z.getPartentNode().getPartentNode().setColorSymbol(false);
                z = z.getPartentNode().getPartentNode();
                continue;
            }
            if (z == z.getPartentNode().getLeftChild()) {
                z = z.getPartentNode();
                this.rightRotate(z);
            }
            z.getPartentNode().setColorSymbol(true);
            z.getPartentNode().getPartentNode().setColorSymbol(false);
            this.leftRotate(z.getPartentNode().getPartentNode());
        }
        this.root.setColorSymbol(true);
        ++this.count;
        return true;
    }

    private RBTreeNode<E> insert(E object) {
        RBTreeNode y = this.nil;
        RBTreeNode<E> x = this.root;
        while (x != this.nil) {
            y = x;
            if (this.comparator.compare(object, x.getData()) < 0) {
                x = x.getLeftChild();
                continue;
            }
            x = x.getRightChild();
        }
        RBTreeNode<E> z = new RBTreeNode<E>();
        z.setData(object);
        z.setPartentNode(y);
        if (y == this.nil) {
            this.root = z;
        } else if (this.comparator.compare(z.getData(), y.getData()) < 0) {
            y.setLeftChild(z);
        } else {
            y.setRightChild(z);
        }
        z.setLeftChild(this.nil);
        z.setRightChild(this.nil);
        return z;
    }

    public RBTreeNode<E> search(RBTreeNode<E> x, E object) {
        while (x != this.nil && this.comparator.compare(x.getData(), object) != 0) {
            if (this.comparator.compare(object, x.getData()) < 0) {
                x = x.getLeftChild();
                continue;
            }
            x = x.getRightChild();
        }
        return x;
    }

    public Iterator<E> search(E o1, E o2) {
        if (this.comparator.compare(o1, o2) > 0) {
            return null;
        }
        if (o1 == null && o2 == null) {
            return null;
        }
        return this.iterator(this.searchFirst(o1), o2);
    }

    public Iterator<E> qSearch(E o1, E o2) {
        if (this.comparator.compare(o1, o2) > 0) {
            return null;
        }
        if (o1 == null && o2 == null) {
            return null;
        }
        ((RBTreeIterator)this.qIterator).init(this.searchFirst(o1), o2);
        return this.qIterator;
    }

    public boolean contains(E object) {
        RBTreeNode<E> x = this.root;
        while (x != this.nil && this.comparator.compare(x.getData(), object) != 0) {
            if (this.comparator.compare(object, x.getData()) < 0) {
                x = x.getLeftChild();
                continue;
            }
            x = x.getRightChild();
        }
        return x != this.nil;
    }

    public RBTreeNode<E> search(E object) {
        RBTreeNode<E> x = this.root;
        while (x != this.nil && this.comparator.compare(x.getData(), object) != 0) {
            if (this.comparator.compare(object, x.getData()) < 0) {
                x = x.getLeftChild();
                continue;
            }
            x = x.getRightChild();
        }
        return x;
    }

    public boolean addCoverageFirst(E object) {
        RBTreeNode<E> node = this.searchFirst(object);
        if (node == this.nil) {
            return this.add(object);
        }
        node.setData(object);
        return true;
    }

    public boolean addCoverageLast(E object) {
        RBTreeNode<E> node = this.searchLast(object);
        if (node == this.nil) {
            return this.add(object);
        }
        node.setData(object);
        return true;
    }

    public boolean addUnique(E o) {
        if (this.root == this.nil) {
            return this.add(o);
        }
        RBTreeNode<E> node = this.root;
        if (this.comparator.compare(node.getData(), o) > 0) {
            while (node != this.nil) {
                if ((node = node.getPre()) != this.nil && this.comparator.compare(node.getData(), o) == 0) {
                    return false;
                }
                if (node == this.nil || this.comparator.compare(node.getData(), o) >= 0) continue;
                return this.add(o);
            }
            return false;
        }
        if (this.comparator.compare(node.getData(), o) < 0) {
            while (node != this.nil) {
                if ((node = node.getNext()) != this.nil && this.comparator.compare(node.getData(), o) == 0) {
                    return false;
                }
                if (node == this.nil || this.comparator.compare(node.getData(), o) <= 0) continue;
                return this.add(o);
            }
            return false;
        }
        return false;
    }

    public RBTreeNode<E> searchFirst(E object) {
        RBTreeNode<E> node = this.search(object);
        if (node == this.nil) {
            return node;
        }
        while (node.getLeftChild() != this.nil && this.comparator.compare(node.getData(), node.getLeftChild().getData()) == 0) {
            node = node.getLeftChild();
        }
        RBTreeNode<E> a = node;
        while (a != this.nil && this.comparator.compare(a.getData(), object) == 0) {
            node = a;
            a = node.getPre();
        }
        return node;
    }

    public RBTreeNode<E> searchLast(E object) {
        RBTreeNode<E> node = this.search(object);
        if (node == this.nil) {
            return node;
        }
        while (node.getRightChild() != this.nil && this.comparator.compare(node.getData(), node.getRightChild().getData()) == 0) {
            node = node.getRightChild();
        }
        RBTreeNode<E> a = node.getPartentNode();
        while (a != this.nil && this.comparator.compare(a.getData(), object) == 0) {
            node = a;
            a = node.getNext();
        }
        return node;
    }

    public Iterator<E> search(Comparator<E> sComparator, E object) {
        RBTreeNode<E> node;
        RBTreeNode<E> fNode = this.nil;
        RBTreeNode<E> lNode = this.nil;
        for (node = this.getMinumNode(); node != this.nil && sComparator.compare(node.getData(), object) == 0; node = node.getNext()) {
            if (fNode != null) continue;
            fNode = node;
        }
        if (node != this.nil) {
            lNode = node.getPre();
        }
        return this.iterator(fNode, lNode.getData());
    }

    public boolean deleteFirst(E object) {
        RBTreeNode<E> node = this.searchFirst(object);
        if (node == this.nil) {
            return false;
        }
        return this.delete(node);
    }

    public boolean delete(E object) {
        RBTreeNode<E> node = this.searchFirst(object);
        if (node == this.nil) {
            return false;
        }
        while (node != this.nil && this.comparator.compare(node.getData(), object) == 0) {
            if (!this.delete(node)) {
                return false;
            }
            node = this.searchFirst(object);
        }
        return true;
    }

    public boolean delete(Collection<? extends E> c) {
        if (c == null) {
            throw new IllegalArgumentException("\u53c2\u6570\u96c6\u5408\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            if (this.delete(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public boolean delete(E[] datas) {
        if (datas == null) {
            throw new IllegalArgumentException("\u53c2\u6570\u6570\u7ec4\u4e0d\u80fd\u4e3a\u7a7a\u6307\u9488\uff01");
        }
        for (int i = 0; i < datas.length; ++i) {
            if (this.delete(datas[i])) continue;
            return false;
        }
        return true;
    }

    public boolean delete(RBTreeNode<E> z) {
        RBTreeNode<E> y = z.getLeftChild() == this.nil || z.getRightChild() == this.nil ? z : z.getNext();
        RBTreeNode<E> x = y.getLeftChild() != this.nil ? y.getLeftChild() : y.getRightChild();
        x.setPartentNode(y.getPartentNode());
        if (y.getPartentNode() == this.nil) {
            this.root = x;
        } else if (y == y.getPartentNode().getLeftChild()) {
            y.getPartentNode().setLeftChild(x);
        } else {
            y.getPartentNode().setRightChild(x);
        }
        if (y != z) {
            z.setData(y.getData());
        }
        if (y.isColorSymbol() && x != this.nil) {
            this.RBDeleteFixup(x);
        }
        --this.count;
        return true;
    }

    public Comparator<? super E> getComparator() {
        return this.comparator;
    }

    private void RBDeleteFixup(RBTreeNode<E> x) {
        while (x != this.root && x.isColorSymbol()) {
            RBTreeNode<E> w;
            if (x == x.getPartentNode().getLeftChild()) {
                w = x.getPartentNode().getRightChild();
                if (!w.isColorSymbol()) {
                    w.setColorSymbol(true);
                    x.getPartentNode().setColorSymbol(false);
                    this.leftRotate(x.getPartentNode());
                    w = x.getPartentNode().getRightChild();
                }
                if (w.getLeftChild().isColorSymbol() && w.getRightChild().isColorSymbol()) {
                    w.setColorSymbol(false);
                    x = x.getPartentNode();
                    continue;
                }
                if (w.getRightChild().isColorSymbol()) {
                    w.getLeftChild().setColorSymbol(true);
                    w.setColorSymbol(false);
                    this.rightRotate(w);
                    w = x.getPartentNode().getRightChild();
                }
                w.setColorSymbol(x.getPartentNode().isColorSymbol());
                x.getPartentNode().setColorSymbol(true);
                w.getRightChild().setColorSymbol(true);
                this.leftRotate(x.getPartentNode());
                x = this.root;
                continue;
            }
            w = x.getPartentNode().getLeftChild();
            if (!w.isColorSymbol()) {
                w.setColorSymbol(true);
                x.getPartentNode().setColorSymbol(false);
                this.rightRotate(x.getPartentNode());
                w = x.getPartentNode().getLeftChild();
            }
            if (w.getRightChild().isColorSymbol() && w.getLeftChild().isColorSymbol()) {
                w.setColorSymbol(false);
                x = x.getPartentNode();
                continue;
            }
            if (w.getLeftChild().isColorSymbol()) {
                w.getRightChild().setColorSymbol(true);
                w.setColorSymbol(false);
                this.leftRotate(this, w);
                w = x.getPartentNode().getLeftChild();
            }
            w.setColorSymbol(x.getPartentNode().isColorSymbol());
            x.getPartentNode().setColorSymbol(true);
            w.getLeftChild().setColorSymbol(true);
            this.rightRotate(x.getPartentNode());
            x = this.root;
        }
        this.root.setColorSymbol(true);
    }

    public RBTreeNode<E> getRoot() {
        return this.root;
    }

    public RBTreeNode<E> getNext(RBTreeNode<E> node) {
        RBTreeNode<E> y;
        if (node.getRightChild() != this.nil) {
            return this.getMinumNode(node.getRightChild());
        }
        for (y = node.getPartentNode(); y != this.nil && node == y.getRightChild(); y = y.getPartentNode()) {
            node = y;
        }
        return y;
    }

    public RBTreeNode<E> getPre(RBTreeNode<E> node) {
        RBTreeNode<E> y;
        if (node.getLeftChild() != this.nil) {
            return this.getMaxmumNode(node.getLeftChild());
        }
        for (y = node.getPartentNode(); y != this.nil && node == y.getLeftChild(); y = y.getPartentNode()) {
            node = y;
        }
        return y;
    }

    public RBTreeNode<E> getMinumNode(RBTreeNode<E> node) {
        while (node != null && node != this.nil && node.getLeftChild() != this.nil) {
            node = node.getLeftChild();
        }
        return node;
    }

    public RBTreeNode<E> getMinumNode() {
        return this.getMinumNode(this.root);
    }

    public RBTreeNode<E> getMaxmumNode(RBTreeNode<E> node) {
        while (node != null && node.getRightChild() != this.nil) {
            node = node.getRightChild();
        }
        return node;
    }

    public RBTreeNode<E> getMaxmumNode() {
        return this.getMaxmumNode(this.root);
    }

    public Iterator<E> iterator() {
        RBTreeNode<E> node = this.getMaxmumNode();
        if (node != null) {
            return new RBTreeIterator<E>(this, this.getMinumNode(), this.getMaxmumNode().getData());
        }
        return new RBTreeIterator<Object>(this, null, null);
    }

    public Iterator<E> iterator(RBTreeNode<E> minNode, E maxObj) {
        return new RBTreeIterator<E>(this, minNode, maxObj);
    }
}


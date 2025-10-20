/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListMatch<T> {
    private List<T> list1;
    private List<T> list2;
    private CallBack<T> callback;
    private static final int MAXMATCHNUM = 100000;

    public ListMatch(List<T> list1, List<T> list2, CallBack<T> callback) {
        this.list1 = list1;
        this.list2 = list2;
        this.callback = callback;
    }

    public int match() {
        int maxCount = this.callback.getMaxCount();
        Comparator<Node> comparator = Comparator.comparingDouble(o -> ((Node)o).value);
        List<Node<T>> nodeList1 = this.getNodeList(this.list1, this.callback, comparator);
        List<Node<T>> nodeList2 = this.getNodeList(this.list2, this.callback, comparator);
        int[] count = new int[]{this.match1(nodeList1, nodeList2, this.callback)};
        nodeList1 = this.filter(nodeList1);
        nodeList2 = this.filter(nodeList2);
        List<Node<T>> orgList1 = new ArrayList<Node<T>>(nodeList1);
        List<Node<T>> orgList2 = new ArrayList<Node<T>>(nodeList2);
        for (int n = 2; n <= maxCount && orgList1.size() > 0 && orgList2.size() > 0 && (orgList1.size() >= n || orgList2.size() >= n) && count[0] <= 100000; ++n) {
            List<List<Node<T>>> result;
            if (orgList1.size() >= n && orgList2.size() > 0) {
                result = this.matchN(n, orgList1, nodeList1, orgList2, nodeList2, comparator, this.callback, count);
                orgList1 = result.get(0);
                nodeList1 = result.get(1);
                orgList2 = result.get(2);
                nodeList2 = result.get(3);
            }
            if (orgList2.size() < n || orgList1.size() <= 0 || count[0] > 100000) continue;
            result = this.matchN(n, orgList2, nodeList2, orgList1, nodeList1, comparator, this.callback, count);
            orgList2 = result.get(0);
            nodeList2 = result.get(1);
            orgList1 = result.get(2);
            nodeList1 = result.get(3);
        }
        return count[0];
    }

    private List<List<Node<T>>> matchN(int n, List<Node<T>> orgList1, List<Node<T>> nodeList1, List<Node<T>> orgList2, List<Node<T>> nodeList2, Comparator<? super Node<T>> comparator, CallBack<T> callback, int[] matchedNum) {
        int[] indexes = new int[n];
        for (int k = 0; k < n; ++k) {
            indexes[k] = k;
        }
        ArrayList<Node<T>> nodeList1New = new ArrayList<Node<T>>();
        ArrayList nodeList = new ArrayList();
        block1: while (true) {
            nodeList.clear();
            for (int k = 0; k < n; ++k) {
                nodeList.add(orgList1.get(indexes[k]));
            }
            Node node1 = new Node(nodeList);
            int index = Collections.binarySearch(nodeList2, node1, comparator);
            boolean accept = true;
            if (index < 0) {
                double v1;
                double v0;
                if ((index = -index - 1) == nodeList2.size()) {
                    --index;
                } else if (index > 0 && (v0 = node1.value - ((Node)nodeList2.get(index - 1)).value) < (v1 = ((Node)nodeList2.get(index)).value - node1.value)) {
                    --index;
                }
                accept = callback.equals(node1.value, ((Node)nodeList2.get(index)).value, node1.getExistsData(), nodeList2.get(index).getExistsData());
            } else {
                accept = callback.equals(node1.value, ((Node)nodeList2.get(index)).value, node1.getExistsData(), nodeList2.get(index).getExistsData());
            }
            matchedNum[0] = matchedNum[0] + 1;
            if (accept) {
                Node<T> node2 = nodeList2.get(index);
                callback.accept(node1.getExistsData(), node2.getExistsData());
                this.accept(nodeList1, node1);
                this.accept(nodeList1New, node1);
                orgList1 = this.filter(this.accept(orgList1, node1));
                orgList2 = this.filter(this.accept(orgList2, node2));
                nodeList2 = this.filter(this.accept(nodeList2, node2));
                if (nodeList2.size() != 0 && indexes[0] + n - 1 < orgList1.size()) {
                    int s = 1;
                    while (true) {
                        if (s >= n) continue block1;
                        indexes[s] = indexes[0] + s;
                        ++s;
                    }
                }
                break;
            }
            if (matchedNum[0] > 100000) break;
            nodeList1New.add(node1);
            boolean end = false;
            for (int k = n - 1; k >= 0; --k) {
                if (indexes[k] + n - k - 1 + 1 < orgList1.size()) {
                    int n2 = k;
                    indexes[n2] = indexes[n2] + 1;
                    for (int s = k + 1; s < n; ++s) {
                        indexes[s] = indexes[k] + s - k;
                    }
                    break;
                }
                if (k != 0) continue;
                end = true;
            }
            if (end) break;
        }
        nodeList1 = Stream.concat(nodeList1.stream(), nodeList1New.stream()).filter((? super T o) -> !((Node)o).accept).sorted(comparator).collect(Collectors.toList());
        return Arrays.asList(orgList1, nodeList1, orgList2, nodeList2);
    }

    private List<Node<T>> accept(List<Node<T>> nodeList1, Node<T> node1) {
        nodeList1.forEach(o -> {
            if (((Node)o).accept) {
                return;
            }
            for (int index = 0; index < ((Node)o).data.size(); ++index) {
                if (!node1.bitmap[index] || !((Node)o).bitmap[index]) continue;
                ((Node)o).accept = true;
                return;
            }
        });
        return nodeList1;
    }

    private List<Node<T>> filter(List<Node<T>> nodeList1) {
        return nodeList1.stream().filter((? super T o) -> !((Node)o).accept).collect(Collectors.toList());
    }

    private int match1(List<Node<T>> nodeList1, List<Node<T>> nodeList2, CallBack<T> callback) {
        int p = 0;
        int q = 0;
        int matchedNum = 0;
        while (p < nodeList1.size() && q < nodeList2.size()) {
            Node<T> node1 = nodeList1.get(p);
            Node<T> node2 = nodeList2.get(q);
            boolean accept = callback.equals(((Node)node1).value, ((Node)node2).value, node1.getExistsData(), node2.getExistsData());
            ++matchedNum;
            if (accept) {
                callback.accept(node1.getExistsData(), node2.getExistsData());
                ((Node)node1).accept = true;
                ((Node)node2).accept = true;
                ++p;
                ++q;
                continue;
            }
            if (Double.compare(((Node)node1).value, ((Node)node2).value) < 0) {
                ++p;
                continue;
            }
            ++q;
        }
        return matchedNum;
    }

    private List<Node<T>> getNodeList(List<T> list1, CallBack<T> callback, Comparator<? super Node<T>> comparator) {
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
        for (int index = 0; index < list1.size(); ++index) {
            double value = callback.getValue(list1.get(index));
            boolean[] bitmap = new boolean[list1.size()];
            bitmap[index] = true;
            nodes.add(new Node<T>(value, bitmap, list1));
        }
        nodes.sort(comparator);
        return nodes;
    }

    private static class Node<T> {
        private double value;
        private boolean[] bitmap;
        private List<T> data;
        private boolean accept;

        Node(double value, boolean[] bitmap, List<T> data) {
            this.value = value;
            this.bitmap = bitmap;
            this.data = data;
        }

        Node(List<Node<T>> nodeList) {
            this.value = nodeList.stream().map(o -> o.value).reduce(0.0, Double::sum);
            this.data = nodeList.get((int)0).data;
            this.bitmap = new boolean[this.data.size()];
            nodeList.forEach(node -> {
                for (int index = 0; index < this.data.size(); ++index) {
                    int n = index;
                    this.bitmap[n] = this.bitmap[n] | node.bitmap[index];
                }
            });
        }

        List<T> getExistsData() {
            ArrayList<T> list = new ArrayList<T>();
            for (int index = 0; index < this.data.size(); ++index) {
                if (!this.bitmap[index]) continue;
                list.add(this.data.get(index));
            }
            return list;
        }
    }

    public static interface CallBack<T> {
        public int getMaxCount();

        public double getValue(T var1);

        public boolean equals(double var1, double var3, Collection<T> var5, Collection<T> var6);

        public void accept(Collection<T> var1, Collection<T> var2);
    }
}


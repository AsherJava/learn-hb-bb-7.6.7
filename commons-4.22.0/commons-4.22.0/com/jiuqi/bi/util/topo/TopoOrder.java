/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.topo;

import com.jiuqi.bi.util.topo.LoopGraphException;
import com.jiuqi.bi.util.topo.Node;
import com.jiuqi.bi.util.topo.NodeProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TopoOrder {
    public List<Node> topoOrder(NodeProvider provider) throws LoopGraphException {
        return this.topoOrder(provider, false);
    }

    public List<Node> topoOrder(NodeProvider provider, boolean checkLoop) throws LoopGraphException {
        boolean hasHeadNode;
        ArrayList<Node> ordered = new ArrayList<Node>();
        List<Node> unordered = provider.getNodes();
        do {
            hasHeadNode = false;
            ArrayList<Node> temp = new ArrayList<Node>();
            for (Node node : unordered) {
                if (node.getPrevNodes().size() != 0) continue;
                temp.add(node);
                hasHeadNode = true;
            }
            for (Node node : temp) {
                for (Node next : node.getNextNodes()) {
                    next.getPrevNodes().remove(node);
                }
                node.getNextNodes().clear();
            }
            unordered.removeAll(temp);
            ordered.addAll(temp);
        } while (hasHeadNode);
        if (!unordered.isEmpty()) {
            if (checkLoop) {
                ArrayList<List<Node>> cycles = new ArrayList<List<Node>>();
                try {
                    this.checkingLoop(unordered, cycles);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                throw new LoopGraphException(cycles);
            }
            throw new LoopGraphException(unordered.toArray(new Node[0]));
        }
        return ordered;
    }

    private void checkingLoop(List<Node> list, List<List<Node>> cycles) {
        int count = 0;
        Stack<Node> stack = new Stack<Node>();
        block0: while (!list.isEmpty()) {
            Node start = list.get(0);
            stack.push(start);
            while (true) {
                if (++count > 100000) {
                    throw new RuntimeException("\u65e0\u6cd5\u89e3\u6790\u8fc7\u4e8e\u5e9e\u5927\u7684\u56fe");
                }
                Node current = (Node)stack.peek();
                if (current.getNextNodes().size() != 0) {
                    Node next = current.getNextNodes().get(0);
                    int index = stack.indexOf(next);
                    if (index != -1) {
                        List cycle = stack.subList(index, stack.size());
                        cycles.add(new ArrayList(cycle));
                        current.getNextNodes().remove(next);
                        list.remove(next);
                        continue;
                    }
                    stack.push(next);
                    continue;
                }
                current = (Node)stack.pop();
                if (stack.isEmpty()) {
                    List<Node> prevNodes = current.getPrevNodes();
                    for (Node prev : prevNodes) {
                        prev.getNextNodes().remove(current);
                    }
                    list.remove(current);
                    continue block0;
                }
                Node last = (Node)stack.peek();
                last.getNextNodes().remove(current);
                list.remove(current);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TopoOrder.test1();
        TopoOrder.loopTest();
        TopoOrder.loopTest1();
        TopoOrder.loopTest2();
        TopoOrder.loopTest3();
    }

    private static void test1() {
        NodeProvider provider = new NodeProvider(){

            @Override
            public List<Node> getNodes() {
                Node a = new Node("a");
                Node b = new Node("b");
                Node c = new Node("c");
                Node d = new Node("d");
                Node e = new Node("e");
                TopoOrder.buildRelation(a, b);
                TopoOrder.buildRelation(a, c);
                TopoOrder.buildRelation(b, c);
                TopoOrder.buildRelation(b, d);
                TopoOrder.buildRelation(c, d);
                TopoOrder.buildRelation(c, e);
                TopoOrder.buildRelation(d, e);
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                return list;
            }
        };
        TopoOrder topo = new TopoOrder();
        try {
            List<Node> nodes = topo.topoOrder(provider, true);
            TopoOrder.printNodes(nodes);
        }
        catch (LoopGraphException e) {
            e.printStackTrace();
        }
    }

    private static void loopTest() {
        NodeProvider provider = new NodeProvider(){

            @Override
            public List<Node> getNodes() {
                Node a = new Node("a");
                Node b = new Node("b");
                Node c = new Node("c");
                Node d = new Node("d");
                Node e = new Node("e");
                TopoOrder.buildRelation(a, b);
                TopoOrder.buildRelation(a, c);
                TopoOrder.buildRelation(b, c);
                TopoOrder.buildRelation(c, b);
                TopoOrder.buildRelation(b, d);
                TopoOrder.buildRelation(c, d);
                TopoOrder.buildRelation(c, e);
                TopoOrder.buildRelation(d, e);
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                return list;
            }
        };
        TopoOrder topo = new TopoOrder();
        try {
            List<Node> nodes = topo.topoOrder(provider, true);
            TopoOrder.printNodes(nodes);
        }
        catch (LoopGraphException e) {
            e.printStackTrace();
        }
    }

    private static void loopTest1() {
        NodeProvider provider = new NodeProvider(){

            @Override
            public List<Node> getNodes() {
                Node a = new Node("a");
                Node b = new Node("b");
                Node c = new Node("c");
                Node d = new Node("d");
                Node e = new Node("e");
                TopoOrder.buildRelation(a, b);
                TopoOrder.buildRelation(a, c);
                TopoOrder.buildRelation(b, c);
                TopoOrder.buildRelation(c, c);
                TopoOrder.buildRelation(b, d);
                TopoOrder.buildRelation(c, d);
                TopoOrder.buildRelation(c, e);
                TopoOrder.buildRelation(d, e);
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                return list;
            }
        };
        TopoOrder topo = new TopoOrder();
        try {
            List<Node> nodes = topo.topoOrder(provider, true);
            TopoOrder.printNodes(nodes);
        }
        catch (LoopGraphException e) {
            e.printStackTrace();
        }
    }

    private static void loopTest2() {
        NodeProvider provider = new NodeProvider(){

            @Override
            public List<Node> getNodes() {
                Node a = new Node("a");
                Node b = new Node("b");
                Node c = new Node("c");
                Node d = new Node("d");
                Node e = new Node("e");
                Node f = new Node("f");
                Node g = new Node("g");
                Node h = new Node("h");
                Node i = new Node("i");
                Node j = new Node("j");
                Node k = new Node("k");
                TopoOrder.buildRelation(a, b);
                TopoOrder.buildRelation(b, d);
                TopoOrder.buildRelation(c, a);
                TopoOrder.buildRelation(d, c);
                TopoOrder.buildRelation(a, j);
                TopoOrder.buildRelation(j, i);
                TopoOrder.buildRelation(i, g);
                TopoOrder.buildRelation(g, e);
                TopoOrder.buildRelation(h, i);
                TopoOrder.buildRelation(f, h);
                TopoOrder.buildRelation(d, e);
                TopoOrder.buildRelation(e, f);
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                list.add(f);
                list.add(g);
                list.add(h);
                list.add(i);
                list.add(j);
                list.add(k);
                return list;
            }
        };
        TopoOrder topo = new TopoOrder();
        try {
            List<Node> nodes = topo.topoOrder(provider, true);
            TopoOrder.printNodes(nodes);
        }
        catch (LoopGraphException e) {
            e.printStackTrace();
        }
    }

    private static void loopTest3() {
        NodeProvider provider = new NodeProvider(){

            @Override
            public List<Node> getNodes() {
                Node a = new Node("a");
                Node b = new Node("b");
                Node c = new Node("c");
                Node d = new Node("d");
                Node e = new Node("e");
                Node f = new Node("f");
                Node g = new Node("g");
                TopoOrder.buildRelation(a, b);
                TopoOrder.buildRelation(c, b);
                TopoOrder.buildRelation(b, d);
                TopoOrder.buildRelation(d, e);
                TopoOrder.buildRelation(e, c);
                TopoOrder.buildRelation(d, f);
                TopoOrder.buildRelation(f, g);
                TopoOrder.buildRelation(g, a);
                ArrayList<Node> list = new ArrayList<Node>();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                list.add(f);
                list.add(g);
                return list;
            }
        };
        TopoOrder topo = new TopoOrder();
        try {
            List<Node> nodes = topo.topoOrder(provider, true);
            TopoOrder.printNodes(nodes);
        }
        catch (LoopGraphException e) {
            e.printStackTrace();
        }
    }

    private static void buildRelation(Node from, Node to) {
        from.getNextNodes().add(to);
        to.getPrevNodes().add(from);
    }

    private static void printNodes(List<Node> nodes) {
        for (Node node : nodes) {
            System.out.print(node.getBindingData() + " -> ");
        }
        System.out.println();
    }
}


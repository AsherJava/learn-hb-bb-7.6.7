/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.vo.Column
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.sql.vo.Column;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DCQueryTreeTool {
    private DCQueryTreeTool() {
    }

    public static int getTreeStep(List<Column> list, String id, int step) {
        if ("".equals(id) || null == id) {
            return step;
        }
        for (Column cc : list) {
            if (!id.equals(cc.getId())) continue;
            int temp = step + 1;
            return DCQueryTreeTool.getTreeStep(list, cc.getPid(), temp);
        }
        return step;
    }

    public static int getMaxStep(List<Column> list) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (Column cc : list) {
            nums.add(DCQueryTreeTool.getTreeStep(list, cc.getId(), 0));
        }
        return (Integer)Collections.max(nums);
    }

    public static int getDownChilren(List<Column> list, String did) {
        int sum = 0;
        for (Column cc : list) {
            if (!did.equals(cc.getPid())) continue;
            ++sum;
            if (!DCQueryTreeTool.hasChild(list, cc)) continue;
            sum += DCQueryTreeTool.getDownChilren(list, cc.getId()) - 1;
        }
        return sum;
    }

    public static Column getParentCol(List<Column> list, String did) {
        for (Column cc : list) {
            if (did != null && did.equals(cc.getId())) {
                return cc;
            }
            if (did != null || did != cc.getId()) continue;
            return cc;
        }
        return new Column(){
            {
                this.setCol(0);
                this.setRow(0);
            }
        };
    }

    public static int getBrotherChilNum(List<Column> list, Column column) {
        int sum = 0;
        for (Column cc : list) {
            if (column.getId().equals(cc.getId())) break;
            if (!column.getPid().equals(cc.getPid())) continue;
            int temp = DCQueryTreeTool.getDownChilren(list, cc.getId());
            if (temp == 0 || temp == 1) {
                ++sum;
                continue;
            }
            sum += temp;
        }
        return sum;
    }

    public static boolean hasChild(List<Column> list, Column node) {
        return !DCQueryTreeTool.getChildList(list, node).isEmpty();
    }

    public static List<Column> getChildList(List<Column> list, Column node) {
        ArrayList<Column> nodeList = new ArrayList<Column>();
        for (Column n : list) {
            if (n.getPid() == null || !n.getPid().equals(node.getId())) continue;
            nodeList.add(n);
        }
        return nodeList;
    }

    public static List<Column> buildByRecursive(List<Column> treeNodes, String rootId) {
        ArrayList<Column> trees = new ArrayList<Column>();
        boolean flag = false;
        boolean sflag = false;
        for (Column treeNode : treeNodes) {
            if (rootId == null && rootId == treeNode.getId()) {
                flag = true;
            }
            if (rootId != null && rootId.equals(treeNode.getId())) {
                flag = true;
            }
            if (!flag) continue;
            trees.add(DCQueryTreeTool.findChildren(treeNode, treeNodes));
            flag = false;
        }
        if (trees.isEmpty()) {
            for (Column treeNode : treeNodes) {
                if (rootId == null && rootId == treeNode.getPid()) {
                    sflag = true;
                }
                if (rootId != null && rootId.equals(treeNode.getPid())) {
                    sflag = true;
                }
                if (!sflag) continue;
                trees.add(DCQueryTreeTool.findChildren(treeNode, treeNodes));
                sflag = false;
            }
        }
        return trees;
    }

    public static Column findChildren(Column treeNode, List<Column> treeNodes) {
        for (Column it : treeNodes) {
            if (!treeNode.getId().equals(it.getPid())) continue;
            if (treeNode.getListTpamscolumn() == null) {
                treeNode.setListTpamscolumn(new ArrayList());
            }
            treeNode.getListTpamscolumn().add(DCQueryTreeTool.findChildren(it, treeNodes));
        }
        return treeNode;
    }
}


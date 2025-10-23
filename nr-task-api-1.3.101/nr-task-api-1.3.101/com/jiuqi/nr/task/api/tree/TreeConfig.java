/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.tree;

import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TreeConfig
implements Cloneable {
    public static final TreeConfig DEFAULT_CONFIG = new TreeConfig();
    private boolean desc;
    private String nodeIcon;
    private String groupIcon;
    private Comparator<UITreeNode<? extends TreeData>> comparator = this.getDefaultComparator();
    private final Set<String> selected = new HashSet<String>();

    public Comparator<UITreeNode<? extends TreeData>> getComparator() {
        if (this.isDesc()) {
            return this.comparator.reversed();
        }
        return this.comparator;
    }

    public TreeConfig setComparator(Comparator<UITreeNode<? extends TreeData>> comparator) {
        this.comparator = comparator;
        return this;
    }

    private Comparator<UITreeNode<? extends TreeData>> getDefaultComparator() {
        return (o1, o2) -> {
            String s2;
            String s1 = null == o1 ? null : o1.getOrder();
            String string = s2 = null == o2 ? null : o2.getOrder();
            if (null == s1 && null == s2) {
                return 1;
            }
            if (s1 == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            return s1.compareTo(s2);
        };
    }

    public boolean isDesc() {
        return this.desc;
    }

    public TreeConfig setDesc(boolean desc) {
        this.desc = desc;
        return this;
    }

    public String getNodeIcon() {
        return this.nodeIcon;
    }

    public TreeConfig setNodeIcon(String nodeIcon) {
        this.nodeIcon = nodeIcon;
        return this;
    }

    public String getGroupIcon() {
        return this.groupIcon;
    }

    public TreeConfig setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public boolean isSelected(String key) {
        return this.selected.contains(key);
    }

    private Set<String> getSelected() {
        return this.selected;
    }

    public TreeConfig selected(String ... keys) {
        if (null != keys) {
            Collections.addAll(this.selected, keys);
        }
        return this;
    }

    public TreeConfig removeSelected(String ... keys) {
        if (null != keys) {
            Arrays.asList(keys).forEach(this.selected::remove);
        }
        return this;
    }

    public TreeConfig clone() throws CloneNotSupportedException {
        TreeConfig clone = (TreeConfig)super.clone();
        return clone.setNodeIcon(this.getNodeIcon()).setGroupIcon(this.getGroupIcon()).setDesc(this.isDesc()).setComparator(this.getComparator()).selected(this.getSelected().toArray(new String[0]));
    }
}


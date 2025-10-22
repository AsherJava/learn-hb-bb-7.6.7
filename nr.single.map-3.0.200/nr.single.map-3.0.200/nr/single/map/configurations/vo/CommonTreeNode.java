/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.vo;

import java.util.ArrayList;
import java.util.List;

public class CommonTreeNode {
    private String code;
    private String title;
    private boolean expand;
    private boolean selected;
    private List<CommonTreeNode> children;

    public CommonTreeNode() {
    }

    public CommonTreeNode(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<CommonTreeNode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<CommonTreeNode>();
        }
        return this.children;
    }

    public void setChildren(List<CommonTreeNode> children) {
        this.children = children;
    }

    public void addChildren(CommonTreeNode node) {
        this.getChildren().add(node);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}


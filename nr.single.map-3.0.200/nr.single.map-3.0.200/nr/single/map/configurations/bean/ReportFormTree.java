/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.util.ArrayList;
import java.util.List;

public class ReportFormTree {
    public static final String NODE_TYPE_GROUP = "NODE_TYPE_GROUP";
    public static final String NODE_TYPE_FORM = "NODE_TYPE_FORM";
    private String id;
    private String title;
    private String code;
    private String type;
    private int formType;
    private boolean expand;
    private boolean selected;
    private List<ReportFormTree> children;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public List<ReportFormTree> getChildren() {
        return this.children;
    }

    public void addChildren(ReportFormTree form) {
        if (this.children == null) {
            this.children = new ArrayList<ReportFormTree>();
        }
        this.children.add(form);
    }

    public void setChildren(List<ReportFormTree> children) {
        this.children = children;
    }
}


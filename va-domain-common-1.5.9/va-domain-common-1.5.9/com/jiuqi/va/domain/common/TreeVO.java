/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.JSONUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeVO<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String text;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<TreeVO<T>> children = null;
    private String parentid;
    private boolean hasParent = false;
    private boolean hasChildren = false;
    private String icon;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getState() {
        return this.state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeVO<T>> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeVO<T>> children) {
        this.children = children;
    }

    public void addChild(TreeVO<T> child) {
        if (this.children == null) {
            this.children = new ArrayList<TreeVO<T>>();
        }
        this.children.add(child);
    }

    public boolean isHasParent() {
        return this.hasParent;
    }

    public void setHasParent(boolean isParent) {
        this.hasParent = isParent;
    }

    public boolean isHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String toString() {
        return JSONUtil.toJSONString(this);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.internal.tree.FormGroupNode;
import com.jiuqi.nr.zb.scheme.internal.tree.FormNode;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeNodeVO;
import java.util.ArrayList;
import java.util.List;

public class FormSchemeTreeSearchItem {
    private FormSchemeTreeNodeVO node;
    private List<String> path = new ArrayList<String>();

    public static FormSchemeTreeSearchItem buildFromFormGroup(FormGroupNode node) {
        FormSchemeTreeSearchItem item = new FormSchemeTreeSearchItem();
        item.setNode(FormSchemeTreeNodeVO.valueOf(node));
        item.addPath(node.getKey());
        return item;
    }

    public static FormSchemeTreeSearchItem buildFromForm(FormNode node) {
        FormSchemeTreeSearchItem item = new FormSchemeTreeSearchItem();
        item.setNode(FormSchemeTreeNodeVO.valueOf(node));
        return item;
    }

    public void addPath(String onePath) {
        this.getPath().add(onePath);
    }

    public FormSchemeTreeNodeVO getNode() {
        return this.node;
    }

    public void setNode(FormSchemeTreeNodeVO node) {
        this.node = node;
    }

    public List<String> getPath() {
        return this.path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String toString() {
        return "FormSchemeTreeSearchItem{node=" + this.node.toString() + ", path=" + this.path + '}';
    }
}


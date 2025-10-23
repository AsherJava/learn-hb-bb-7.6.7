/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.zb.scheme.internal.tree.FormSchemeTreeNode;

public class FormNode
extends FormSchemeTreeNode {
    public static FormNode valueOf(DesignFormDefine designFormDefine) {
        FormNode formNode = new FormNode();
        formNode.setKey(designFormDefine.getKey());
        formNode.setCode(designFormDefine.getFormCode());
        formNode.setTitle(designFormDefine.getTitle());
        formNode.setType(2);
        return formNode;
    }

    public String toString() {
        return "FormNode{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + '}';
    }
}


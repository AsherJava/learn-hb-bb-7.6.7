/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.zb.scheme.internal.tree.FormSchemeTreeNode;

public class FormGroupNode
extends FormSchemeTreeNode {
    public static FormGroupNode valueOf(DesignFormGroupDefine designFormGroupDefine) {
        FormGroupNode formGroupNode = new FormGroupNode();
        formGroupNode.setKey(designFormGroupDefine.getKey());
        formGroupNode.setCode(designFormGroupDefine.getCode());
        formGroupNode.setTitle(designFormGroupDefine.getTitle());
        formGroupNode.setType(1);
        return formGroupNode;
    }

    public String toString() {
        return "FormGroupNode{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + '}';
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.intf;

import com.jiuqi.va.biz.view.intf.ControlDeclare;
import java.util.ArrayList;
import java.util.List;

public class CompositeDeclare
extends ControlDeclare {
    public CompositeDeclare() {
        this.props.put("children", new ArrayList());
    }

    private void addChild(ControlDeclare controlDeclare) {
        List children = (List)this.props.get("children");
        children.add(controlDeclare.props);
    }

    public ControlDeclare declareControl() {
        ControlDeclare controlDeclare = new ControlDeclare();
        this.addChild(controlDeclare);
        return controlDeclare;
    }

    public CompositeDeclare declareComposite() {
        CompositeDeclare compositeDeclare = new CompositeDeclare();
        this.addChild(compositeDeclare);
        return compositeDeclare;
    }
}


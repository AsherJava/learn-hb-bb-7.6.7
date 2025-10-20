/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.impl;

import com.jiuqi.va.biz.view.impl.ControlImpl;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.Control;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompositeImpl
extends ControlImpl
implements Composite {
    private volatile List<Control> children;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Control> getChildren() {
        if (this.children == null) {
            CompositeImpl compositeImpl = this;
            synchronized (compositeImpl) {
                if (this.children == null) {
                    ArrayList<Control> children = new ArrayList<Control>();
                    List prop_children = (List)this.getProps().get("children");
                    if (prop_children != null) {
                        prop_children.forEach(o -> {
                            Control child = ControlManagerImpl.createControl(o);
                            children.add(child);
                        });
                    }
                    this.children = children;
                }
            }
        }
        return this.children;
    }

    public List<Map<String, Object>> getWizardInfo() {
        return (List)this.getProps().get("wizardInfo");
    }
}


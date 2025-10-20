/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.impl;

import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.view.impl.CompositeImpl;
import com.jiuqi.va.biz.view.impl.ControlImpl;
import com.jiuqi.va.biz.view.intf.Control;
import java.util.HashMap;
import java.util.Map;

public class ControlManagerImpl {
    private static Map<String, Class<? extends ControlImpl>> controlMap = new HashMap<String, Class<? extends ControlImpl>>();

    public static Control createControl(Map<String, Object> props) {
        ControlImpl control;
        Class controlType = controlMap.get(props.get("type"));
        if (controlType == null) {
            controlType = props.containsKey("children") || "v-wizard".equals(props.get("type")) ? CompositeImpl.class : ControlImpl.class;
        }
        try {
            control = controlType.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new ModelException(e);
        }
        control.setProps(props);
        return control;
    }

    static {
        controlMap.put("control", ControlImpl.class);
        controlMap.put("composite", CompositeImpl.class);
    }
}


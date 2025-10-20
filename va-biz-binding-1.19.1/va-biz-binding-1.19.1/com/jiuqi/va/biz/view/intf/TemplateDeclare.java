/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.biz.view.intf;

import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.view.intf.CompositeDeclare;
import com.jiuqi.va.biz.view.intf.ControlDeclare;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.Map;

public class TemplateDeclare {
    private Object template;

    public Object getTemplate() {
        return this.template;
    }

    private void setTemplate(Object template) {
        if (this.template != null) {
            throw new ModelException("\u91cd\u590d\u58f0\u660e\u754c\u9762\u6839\u5143\u7d20");
        }
        this.template = template;
    }

    public ControlDeclare declareControl() {
        ControlDeclare controlDeclare = new ControlDeclare();
        this.setTemplate(controlDeclare.props);
        return controlDeclare;
    }

    public CompositeDeclare declareComposite() {
        CompositeDeclare compositeDeclare = new CompositeDeclare();
        this.setTemplate(compositeDeclare.props);
        return compositeDeclare;
    }

    public void declareFromJSON(String template) {
        this.setTemplate(JSONUtil.parseMap((String)template));
    }

    public CompositeDeclare getRootComposite() {
        Map props = (Map)this.template;
        if (!props.containsKey("children")) {
            throw new ModelException("\u6839\u5143\u7d20\u4e0d\u662f\u5bb9\u5668");
        }
        CompositeDeclare compositeDeclare = new CompositeDeclare();
        compositeDeclare.props = props;
        return compositeDeclare;
    }

    public ControlDeclare getRootControl() {
        Map props = (Map)this.template;
        if (props.containsKey("children")) {
            throw new ModelException("\u6839\u5143\u7d20\u4e0d\u662f\u63a7\u4ef6");
        }
        ControlDeclare controlDeclare = new ControlDeclare();
        controlDeclare.props = props;
        return controlDeclare;
    }
}


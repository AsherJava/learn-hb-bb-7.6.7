/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import org.springframework.util.Assert;

public class Action
extends ActivitiExtension {
    private final String code;

    public Action(String code) {
        super("action");
        Assert.notNull((Object)code, "'code' must not be null.");
        super.setProperty("code", code);
        this.code = code;
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }

    public void setNeedComment(Boolean needComment) {
        super.setProperty("needComment", needComment.toString());
    }

    public String getCode() {
        return this.code;
    }
}


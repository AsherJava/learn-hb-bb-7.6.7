/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.resource.state.ResourceState
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.dto.VariableDTO;
import com.jiuqi.nr.task.api.resource.state.ResourceState;

public class VariableVO
extends VariableDTO {
    private ResourceState state = ResourceState.DEFAULT;

    public ResourceState getState() {
        return this.state;
    }

    public void setState(ResourceState state) {
        this.state = state;
    }
}


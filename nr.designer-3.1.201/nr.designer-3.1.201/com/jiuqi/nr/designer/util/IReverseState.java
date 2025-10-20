/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.util.ReverseItemStateDeserializer;

public interface IReverseState {
    public ReverseItemState getState();

    @JsonDeserialize(using=ReverseItemStateDeserializer.class)
    public void setState(ReverseItemState var1);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service.dto;

import java.io.Serializable;
import java.util.List;

public interface CompletionDimFinder
extends Serializable {
    public List<String> findByDw(String var1);

    public String findByDw(String var1, String var2);
}


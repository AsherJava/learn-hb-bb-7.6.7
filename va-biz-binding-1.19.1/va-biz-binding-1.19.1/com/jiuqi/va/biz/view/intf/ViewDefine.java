/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.intf;

import com.jiuqi.va.biz.view.intf.Composite;
import java.util.List;
import java.util.Map;

public interface ViewDefine {
    public Composite getTemplate();

    public List<Map<String, Object>> getSchemes();
}


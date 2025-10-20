/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.Trigger;
import java.util.Map;

public interface RulerDefine {
    public ListContainer<? extends Formula> getFormulas();

    public ListContainer<? extends Trigger> getTriggers();

    public ListContainer<Map<String, Object>> getSortInfo();

    public ListContainer<RulerItem> getItems();
}


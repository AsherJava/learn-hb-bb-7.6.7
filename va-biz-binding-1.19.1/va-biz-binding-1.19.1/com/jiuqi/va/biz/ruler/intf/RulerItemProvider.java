/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.ruler.intf.RulerItem;
import java.util.stream.Stream;

public interface RulerItemProvider {
    public Stream<RulerItem> getRulerList();
}


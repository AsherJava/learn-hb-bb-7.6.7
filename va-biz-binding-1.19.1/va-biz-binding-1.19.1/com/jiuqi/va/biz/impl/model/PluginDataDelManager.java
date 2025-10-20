/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.model.PluginDataDel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PluginDataDelManager
extends NamedManagerImpl<PluginDataDel> {
    public List<PluginDataDel> getPluginCheckList() {
        return this.stream().collect(Collectors.toList());
    }
}


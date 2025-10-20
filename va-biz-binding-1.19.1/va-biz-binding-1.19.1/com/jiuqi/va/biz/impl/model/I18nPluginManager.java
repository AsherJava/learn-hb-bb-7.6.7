/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class I18nPluginManager
extends NamedManagerImpl<I18nPlugin> {
    public List<I18nPlugin> getI18nPluginList() {
        return this.stream().collect(Collectors.toList());
    }
}


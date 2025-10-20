/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.module.impl.AbstractModule
 */
package com.jiuqi.dc.base.impl.module;

import com.jiuqi.dc.base.common.module.impl.AbstractModule;
import org.springframework.stereotype.Component;

@Component
public class DCModule
extends AbstractModule {
    public String getCode() {
        return "DC";
    }

    public String getName() {
        return "\u4e00\u672c\u8d26";
    }

    public int getOrdinal() {
        return 1;
    }
}


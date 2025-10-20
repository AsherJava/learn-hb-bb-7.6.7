/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.meta.MetaType
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.biz.intf.meta.MetaType;
import org.springframework.stereotype.Component;

@Component
public class BillMetaType
implements MetaType {
    public static final String NAME = "bill";
    public static final String TITLE = "\u5355\u636e";

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }
}


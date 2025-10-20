/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 */
package com.jiuqi.gcreport.nr.impl.action;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import org.springframework.stereotype.Component;

@Component
public class GcOpenBillListAction
extends AbstractGcActionItem {
    protected GcOpenBillListAction() {
        super("gcOpenBillListAction", "\u6253\u5f00\u5355\u636e\u5217\u8868", "\u6253\u5f00\u5355\u636e\u5217\u8868\uff0c\u53ef\u6839\u636e\u5355\u636e\u5217\u8868\u6807\u8bc6\u5230meta_info\u8868\u4e2d\u67e5\u8be2UNIQUECODE\u5b57\u6bb5\u4f5c\u4e3a\u53c2\u6570", "#icon-16_GJ_A_GC_zidongduizhang");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        return null;
    }
}


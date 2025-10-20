/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.IMoudle
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.dc.base.common.intf.IMoudle;

public class BdeModule
implements IMoudle {
    public static final String CODE = "BDE";
    public static final String NAME = "";
    public static final int ORDER = 1;

    private BdeModule() {
    }

    public static IMoudle getInstance() {
        return InnerBdeModule.INSTANCE;
    }

    public String getCode() {
        return CODE;
    }

    public String getName() {
        return NAME;
    }

    public int getOrder() {
        return 1;
    }

    public String getDesc() {
        return null;
    }

    private static class InnerBdeModule {
        private static final BdeModule INSTANCE = new BdeModule();

        private InnerBdeModule() {
        }
    }
}


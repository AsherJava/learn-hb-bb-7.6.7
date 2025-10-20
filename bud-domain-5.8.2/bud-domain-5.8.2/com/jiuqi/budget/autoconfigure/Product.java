/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.autoconfigure;

public enum Product {
    BUDGET{

        @Override
        public String getTitle() {
            return "\u9884\u7b97";
        }
    }
    ,
    STATISTICS{

        @Override
        public String getTitle() {
            return "\u7edf\u8ba1";
        }
    };


    public abstract String getTitle();
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

public interface ILoadListener {
    public static final ILoadListener EMPTY_LISTENER = new ILoadListener(){

        @Override
        public void createProcedure(String procedure) {
        }

        @Override
        public void executeProcedure(String sql) {
        }

        @Override
        public void executeSQL(String sql) {
        }
    };
    public static final ILoadListener CONSOLE_LISTENER = new ILoadListener(){

        @Override
        public void executeSQL(String sql) {
            System.out.println("\u6267\u884cSQL\uff1a" + sql);
        }

        @Override
        public void executeProcedure(String sql) {
            System.out.println("\u8c03\u7528\u5b58\u50a8\u8fc7\u7a0b\uff1a" + sql);
        }

        @Override
        public void createProcedure(String procedure) {
            System.out.println("\u521b\u5efa\u5b58\u50a8\u8fc7\u7a0b\uff1a" + procedure);
        }
    };

    public void createProcedure(String var1);

    public void executeProcedure(String var1);

    public void executeSQL(String var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.event;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.context.ApplicationEvent;

public class EntTableDefineInitAfterEvent
extends ApplicationEvent {
    public EntTableDefineInitAfterEvent(EntTableDefineInitAfterInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public EntTableDefineInitAfterInfo getEntTableDefineInitAfterInfo() {
        if (this.getSource() != null) {
            return (EntTableDefineInitAfterInfo)this.getSource();
        }
        return null;
    }

    public static class EntTableDefineInitAfterInfo {
        private String tableName;

        public EntTableDefineInitAfterInfo(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return this.tableName;
        }
    }
}


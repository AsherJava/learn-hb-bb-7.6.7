/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.db.DbSqlSessionFactory
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.interceptor.Session
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.impl.activiti6.NrDbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;

public class NrDbSqlSessionFactory
extends DbSqlSessionFactory {
    public Session openSession(CommandContext commandContext) {
        return new NrDbSqlSession(this, commandContext.getEntityCache());
    }
}


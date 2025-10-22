/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.db.DbSqlSession
 *  org.activiti.engine.impl.db.DbSqlSessionFactory
 *  org.activiti.engine.impl.persistence.cache.EntityCache
 *  org.activiti.engine.impl.persistence.entity.Entity
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import java.util.Collection;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NrDbSqlSession
extends DbSqlSession {
    private static final Logger log = LoggerFactory.getLogger(NrDbSqlSession.class);

    public NrDbSqlSession(DbSqlSessionFactory dbSqlSessionFactory, EntityCache entityCache) {
        super(dbSqlSessionFactory, entityCache);
    }

    protected void flushInsertEntities(Class<? extends Entity> entityClass, Collection<Entity> entitiesToInsert) {
        if (entitiesToInsert.size() == 1) {
            this.flushRegularInsert(entitiesToInsert.iterator().next(), entityClass);
        } else {
            for (Entity entity : entitiesToInsert) {
                this.flushRegularInsert(entity, entityClass);
            }
        }
    }
}


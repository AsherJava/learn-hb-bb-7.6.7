/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.common.basesql.base;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.io.Serializable;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDbSqlGenericDAO<Entity extends DefaultTableEntity, ID extends Serializable>
extends IBaseSqlGenericDAO<Entity> {
    public Serializable save(Entity var1) throws DataAccessException;

    @Deprecated
    public List<Entity> saveAll(List<Entity> var1) throws DataAccessException;

    @Deprecated
    public void updateAll(List<Entity> var1) throws DataAccessException;

    public Entity get(ID var1) throws DataAccessException;

    public List<Entity> loadAll() throws DataAccessException;
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.definition.impl.basic.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntSqlDao;
import com.jiuqi.gcreport.definition.impl.basic.dao.IEntBaseOrmSqlFactory;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface IBaseSqlGenericDAO<Entity extends BaseEntity>
extends EntSqlDao,
IEntBaseOrmSqlFactory<Entity> {
    public int execute(String var1) throws DataAccessException;

    public int execute(String var1, List<Object> var2);

    public int execute(String var1, Object ... var2);

    public int[] executeBatch(String var1, List<List<Object>> var2);

    public int add(Entity var1) throws DataAccessException;

    public int update(Entity var1) throws DataAccessException;

    public int delete(Entity var1) throws DataAccessException;

    public int[] addBatch(List<Entity> var1) throws DataAccessException;

    public int[] updateBatch(List<Entity> var1) throws DataAccessException;

    public int[] deleteBatch(List<Entity> var1) throws DataAccessException;

    public List<Map<String, Object>> selectMap(String var1, List<Object> var2);

    public List<Map<String, Object>> selectMap(String var1, Object ... var2);

    public List<Entity> selectEntity(String var1, List<Object> var2);

    public List<Entity> selectEntity(String var1, Object ... var2);

    public List<Map<String, Object>> selectMapByPaging(String var1, int var2, int var3, List<Object> var4);

    public List<Map<String, Object>> selectMapByPaging(String var1, int var2, int var3, Object ... var4);

    public List<Entity> selectEntityByPaging(String var1, int var2, int var3, List<Object> var4);

    public List<Entity> selectEntityByPaging(String var1, int var2, int var3, Object ... var4);

    public int count(String var1, List<Object> var2);

    public int count(String var1, Object ... var2);

    public <T> T selectFirst(Class<T> var1, String var2, Object ... var3);

    public <T> List<T> selectFirstList(Class<T> var1, String var2, Object ... var3);

    public <T> List<T> selectFirstListByPaging(Class<T> var1, String var2, int var3, int var4, Object ... var5);

    public Entity selectByEntity(Entity var1) throws DataAccessException;

    public List<Entity> selectList(Entity var1) throws DataAccessException;

    public List<Entity> selectListByPaging(Entity var1, int var2, int var3) throws DataAccessException;

    public int addSelective(Entity var1);

    public int updateSelective(Entity var1);

    public int countByEntity(Entity var1);
}


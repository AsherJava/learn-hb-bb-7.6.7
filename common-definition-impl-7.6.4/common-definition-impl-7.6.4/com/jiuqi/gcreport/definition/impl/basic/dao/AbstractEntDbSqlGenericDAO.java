/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.definition.impl.basic.dao;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.TableSqlDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.DeleteSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.InsertSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.UpdateSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.EntDqlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.SelectSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public abstract class AbstractEntDbSqlGenericDAO<Entity extends BaseEntity>
implements IBaseSqlGenericDAO<Entity> {
    private FEntSqlTemplate entSqlTemplate;
    private TableSqlDeclarator<Entity> sqlDeclarator;
    private EntityTableDeclarator<Entity> tableDeclarator;
    private final Class<Entity> entity;
    private String tableName;

    public AbstractEntDbSqlGenericDAO(Class<Entity> entity) {
        this.entSqlTemplate = (FEntSqlTemplate)SpringContextUtils.getBean(EntNativeSqlTemplate.class);
        this.entity = entity;
    }

    public AbstractEntDbSqlGenericDAO(Class<Entity> entity, FEntSqlTemplate entSqlTemplate) {
        this.entity = entity;
        this.entSqlTemplate = entSqlTemplate;
    }

    protected FEntSqlTemplate getEntSqlTemplate() {
        return this.entSqlTemplate;
    }

    @Override
    public EntityTableDeclarator<Entity> getTableDeclarator() {
        return this.tableDeclarator;
    }

    @Override
    public void setTableDeclarator(EntityTableDeclarator<Entity> tableDeclarator) {
        this.tableDeclarator = tableDeclarator;
    }

    @Override
    public TableSqlDeclarator<Entity> getSqlDeclarator() {
        return this.sqlDeclarator;
    }

    @Override
    public Class<Entity> getEntityType() {
        return this.entity;
    }

    @Override
    public void setSqlDeclarator(TableSqlDeclarator<Entity> sqlDeclarator) {
        this.sqlDeclarator = sqlDeclarator;
        this.tableName = sqlDeclarator.getTableName();
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public int execute(String sql) throws DataAccessException {
        return this.entSqlTemplate.execute(EntSqlTool.newDmlInstance(sql, Collections.emptyList()));
    }

    @Override
    public int execute(String sql, List<Object> params) {
        return this.entSqlTemplate.execute(EntSqlTool.newDmlInstance(sql, params));
    }

    @Override
    public int execute(String sql, Object ... params) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (params != null) {
            Collections.addAll(paramList, params);
        }
        return this.entSqlTemplate.execute(EntSqlTool.newDmlInstance(sql, paramList));
    }

    @Override
    public int[] executeBatch(String sql, List<List<Object>> param) {
        return this.executeBatch(EntSqlTool.newDmlBatchInstance(sql, param));
    }

    @Override
    public int add(Entity object) {
        InsertSql sql = this.getSqlDeclarator().getSqlInsert(object, false);
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int update(Entity object) {
        UpdateSql sql = this.getSqlDeclarator().getSqlUpdate(object, false);
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int delete(Entity object) {
        DeleteSql sql = this.getSqlDeclarator().getSqlDelete(object);
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int[] addBatch(List<Entity> entities) throws DataAccessException {
        return this.entSqlTemplate.executeBatch(this.getSqlDeclarator().getSqlBatchInsert(entities));
    }

    @Override
    public int[] updateBatch(List<Entity> entities) throws DataAccessException {
        return this.entSqlTemplate.executeBatch(this.getSqlDeclarator().getSqlBatchUpdate(entities));
    }

    @Override
    public int[] deleteBatch(List<Entity> entities) throws DataAccessException {
        return this.entSqlTemplate.executeBatch(this.getSqlDeclarator().getSqlBatchDelete(entities));
    }

    @Override
    public List<Map<String, Object>> selectMap(String sql, List<Object> param) {
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, param, rs -> rs.getRowData(this.getTableDeclarator())), -1, -1);
    }

    @Override
    public List<Map<String, Object>> selectMap(String sql, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            paramList.addAll(Arrays.asList(param));
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, rs -> rs.getRowData(this.getTableDeclarator())), -1, -1);
    }

    @Override
    public List<Entity> selectEntity(String sql, List<Object> param) {
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, param, this.initRsExtractor()), -1, -1);
    }

    @Override
    public List<Entity> selectEntity(String sql, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            Collections.addAll(paramList, param);
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, this.initRsExtractor()), -1, -1);
    }

    @Override
    public List<Map<String, Object>> selectMapByPaging(String sql, int begin, int end, List<Object> param) {
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, param, rs -> rs.getRowData(this.getTableDeclarator())), begin, end);
    }

    @Override
    public List<Map<String, Object>> selectMapByPaging(String sql, int begin, int end, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            paramList.addAll(Arrays.asList(param));
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, rs -> rs.getRowData(this.getTableDeclarator())), begin, end);
    }

    @Override
    public List<Entity> selectEntityByPaging(String sql, int begin, int end, List<Object> param) {
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, param, this.initRsExtractor()), begin, end);
    }

    @Override
    public List<Entity> selectEntityByPaging(String sql, int begin, int end, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            paramList.addAll(Arrays.asList(param));
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, this.initRsExtractor()), begin, end);
    }

    @Override
    public int count(String sql, List<Object> param) {
        String countFunc = "COUNT(";
        String sumFunc = "SUM(";
        if (sql.toUpperCase().indexOf(countFunc) <= 0 && sql.toUpperCase().indexOf(sumFunc) <= 0) {
            sql = String.format("SELECT COUNT(*) FROM (%1$s) T", sql);
        }
        return this.entSqlTemplate.query(EntSqlTool.newDqlCountInstance(sql, param));
    }

    @Override
    public int count(String sql, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            paramList.addAll(Arrays.asList(param));
        }
        return this.count(sql, paramList);
    }

    @Override
    public <T> T selectFirst(Class<T> returnType, String sql, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            Collections.addAll(paramList, param);
        }
        EntDqlSql<T> nsql = EntSqlTool.newDqlInstance(sql, paramList, EntSqlTool.newSingleFieldSetter(returnType));
        return this.entSqlTemplate.query(nsql);
    }

    @Override
    public <T> List<T> selectFirstList(Class<T> returnType, String sql, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            Collections.addAll(paramList, param);
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, EntSqlTool.newSingleFieldSetter(returnType)), -1, -1);
    }

    @Override
    public <T> List<T> selectFirstListByPaging(Class<T> returnType, String sql, int begin, int end, Object ... param) {
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (param != null) {
            Collections.addAll(paramList, param);
        }
        return this.entSqlTemplate.queryByPaging(EntSqlTool.newDqlInstance(sql, paramList, EntSqlTool.newSingleFieldSetter(returnType)), begin, end);
    }

    @Override
    public Entity selectByEntity(Entity object) {
        SelectSql<Entity> sql = this.getSqlDeclarator().getSqlSelectWithKey(object, this.initRsExtractor());
        return (Entity)((BaseEntity)this.entSqlTemplate.query(sql));
    }

    @Override
    public List<Entity> selectList(Entity object) {
        return this.selectListByPaging(object, -1, -1);
    }

    @Override
    public List<Entity> selectListByPaging(Entity object, int begin, int end) {
        SelectSql<Entity> sql = this.getSqlDeclarator().getSqlSelect(object, this.initRsExtractor());
        return this.entSqlTemplate.queryByPaging(sql, begin, end);
    }

    @Override
    public int addSelective(Entity object) {
        InsertSql sql = this.getSqlDeclarator().getSqlInsert(object, true);
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int updateSelective(Entity object) {
        UpdateSql sql = this.getSqlDeclarator().getSqlUpdate(object, true);
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int countByEntity(Entity object) {
        return this.entSqlTemplate.query(this.getSqlDeclarator().getSqlSelectCount(object));
    }

    private EntResultSetExtractor<Entity> initRsExtractor() {
        return rs -> {
            BaseEntity obj;
            try {
                obj = (BaseEntity)this.getEntityType().newInstance();
                Map<String, Object> rowData = rs.getRowData(this.getTableDeclarator());
                for (EntFieldDefine c : this.getSqlDeclarator().getEntTableDefine().getAllFields()) {
                    Object value = rowData.get(c.getName());
                    this.getTableDeclarator().setValue(obj, c.getCode(), value);
                }
            }
            catch (IllegalAccessException | InstantiationException e1) {
                Object[] i18Args = new String[]{this.getEntityType().getName()};
                throw new RuntimeException(GcI18nUtil.getMessage((String)"ent.definetion.dao.orm.read.error", (Object[])i18Args), e1);
            }
            return obj;
        };
    }

    @Override
    public int execute(EntDmlSql sql) {
        return this.entSqlTemplate.execute(sql);
    }

    @Override
    public int[] executeBatch(EntDmlBatchSql sql) {
        return this.entSqlTemplate.executeBatch(sql);
    }
}


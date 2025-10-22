/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.Session
 *  com.jiuqi.np.sql.SessionFactoryBuilder
 *  com.jiuqi.np.sql.TableDefineProvider
 *  com.jiuqi.np.sql.da.RecordSet
 *  com.jiuqi.np.sql.da.RecordSetField
 *  com.jiuqi.np.sql.da.RecordSetFieldContainer
 *  com.jiuqi.np.sql.def.exp.ConditionalExpression
 *  com.jiuqi.np.sql.def.query.QueryStatementDefine
 *  com.jiuqi.np.sql.def.table.TableFieldDefine
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.sql.Session;
import com.jiuqi.np.sql.SessionFactoryBuilder;
import com.jiuqi.np.sql.TableDefineProvider;
import com.jiuqi.np.sql.da.RecordSet;
import com.jiuqi.np.sql.da.RecordSetField;
import com.jiuqi.np.sql.da.RecordSetFieldContainer;
import com.jiuqi.np.sql.def.exp.ConditionalExpression;
import com.jiuqi.np.sql.def.query.QueryStatementDefine;
import com.jiuqi.np.sql.def.table.TableFieldDefine;
import com.jiuqi.nr.bpm.common.Query;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.QueryStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.sql.DataSource;

public class Querier<T>
implements Query<T> {
    public static final int MINPAGEINDEX = 1;
    public static final int MAXPAGESIZE = 500;
    protected DataSource dataSource;
    protected TableDefineProvider tableDefineProvider;
    protected Function<RecordSetFieldContainer<? extends RecordSetField>, T> objMapper;
    public QueryStatement queryStatement;

    public Querier(DataSource dataSource, TableDefineProvider tableDefineProvider, Function<RecordSetFieldContainer<? extends RecordSetField>, T> objMapper) {
        this.dataSource = dataSource;
        this.tableDefineProvider = tableDefineProvider;
        this.objMapper = objMapper;
        this.queryStatement = new QueryStatement("bpm");
    }

    public Session getSession() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        builder.addTableDefineProvider(this.tableDefineProvider);
        try {
            return builder.build().openSession(this.dataSource.getConnection());
        }
        catch (SQLException e) {
            throw new BpmException("can not get database connection.", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long count() {
        try (Session session = this.getSession();){
            long l = session.rowCountOfL((QueryStatementDefine)session.newQueryStatement((QueryStatementDefine)this.queryStatement.getStatement()), new Object[0]);
            return l;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T first() {
        try (Session session = this.getSession();){
            RecordSet rs = session.openQueryLimit((QueryStatementDefine)session.newQueryStatement((QueryStatementDefine)this.queryStatement.getStatement()), 0L, 1L, new Object[0]);
            T t = rs.first() ? (T)this.objMapper.apply((RecordSetFieldContainer<? extends RecordSetField>)rs.getFields()) : null;
            return t;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<T> list() {
        try (Session session = this.getSession();){
            RecordSet rs = session.openQuery((QueryStatementDefine)session.newQueryStatement((QueryStatementDefine)this.queryStatement.getStatement()), new Object[0]);
            ArrayList<T> objs = new ArrayList<T>();
            RecordSetFieldContainer rsf = rs.getFields();
            while (rs.next()) {
                objs.add(this.objMapper.apply((RecordSetFieldContainer<? extends RecordSetField>)rsf));
            }
            ArrayList<T> arrayList = objs;
            return arrayList;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<T> listPage(int pageIndex, int pageSize) throws IllegalArgumentException {
        pageIndex = Math.max(1, pageIndex);
        pageSize = Math.min(500, pageSize);
        try (Session session = this.getSession();){
            RecordSet rs = session.openQueryLimit((QueryStatementDefine)session.newQueryStatement((QueryStatementDefine)this.queryStatement.getStatement()), (long)(pageSize * (pageIndex - 1)), (long)pageSize, new Object[0]);
            ArrayList<T> objs = new ArrayList<T>();
            RecordSetFieldContainer rsf = rs.getFields();
            while (rs.next()) {
                objs.add(this.objMapper.apply((RecordSetFieldContainer<? extends RecordSetField>)rsf));
            }
            ArrayList<T> arrayList = objs;
            return arrayList;
        }
    }

    public void addCondition(ConditionalExpression condition, boolean isAnd) {
        this.queryStatement.addCondition(condition, isAnd);
    }

    public void addOrderBy(TableFieldDefine field, boolean isDesc) {
        this.queryStatement.addOrderBy(field, isDesc);
    }
}


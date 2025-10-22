/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.efdc.dao.impl;

import com.jiuqi.nr.efdc.bean.TransferEntityBean;
import com.jiuqi.nr.efdc.dao.SoluctionsDao;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class SoluctionsDaoImpl
implements SoluctionsDao {
    @Autowired
    protected NamedParameterJdbcTemplate template;
    public static final String QUERY_TEMPLATE = "select DC_KEY, DC_ASSIST_DIM, DC_FORMSCHEME_KEY, DC_MAIN_DIM, DC_RPT_SCHEME, DC_SOLUTION_KEY, DC_TASK_KEY, DC_UPDATETIME from EFDC_REPOSITORY ";

    private QueryObjectImpl create(ResultSet rs) throws SQLException {
        QueryObjectImpl queryObject = new QueryObjectImpl();
        queryObject.setId(rs.getString("dc_key"));
        queryObject.setAssistDim(rs.getString("dc_assist_dim"));
        queryObject.setUpdatetime(rs.getTimestamp("dc_updatetime"));
        queryObject.setMainDim(rs.getString("dc_main_dim"));
        queryObject.setRptScheme(rs.getString("dc_rpt_scheme"));
        queryObject.setFormSchemeKey(rs.getString("dc_formscheme_key"));
        queryObject.setSolutionKey(rs.getString("dc_solution_key"));
        queryObject.setTaskKey(rs.getString("dc_task_key"));
        return queryObject;
    }

    @Override
    public QueryObjectImpl find(UUID id) {
        return (QueryObjectImpl)this.template.getJdbcOperations().query("select DC_KEY, DC_ASSIST_DIM, DC_FORMSCHEME_KEY, DC_MAIN_DIM, DC_RPT_SCHEME, DC_SOLUTION_KEY, DC_TASK_KEY, DC_UPDATETIME from EFDC_REPOSITORY where dc_key = ?", this::create, new Object[]{id});
    }

    @Override
    public QueryObjectImpl find(QueryObjectImpl impl) {
        return (QueryObjectImpl)this.template.getJdbcOperations().query("select DC_KEY, DC_ASSIST_DIM, DC_FORMSCHEME_KEY, DC_MAIN_DIM, DC_RPT_SCHEME, DC_SOLUTION_KEY, DC_TASK_KEY, DC_UPDATETIME from EFDC_REPOSITORY where dc_key = ?", this::create, new Object[]{impl.getId()});
    }

    @Override
    public List<QueryObjectImpl> findAll() {
        return this.template.getJdbcOperations().query(QUERY_TEMPLATE, (rs, row) -> this.create(rs));
    }

    @Override
    public List<QueryObjectImpl> getSolutions(QueryObjectImpl queryObject) {
        StringBuilder stringBuilder = new StringBuilder(QUERY_TEMPLATE);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        stringBuilder.append("where 1 =1 ");
        if (queryObject.getTaskKey() != null) {
            stringBuilder.append("and dc_task_key =  :taskKey ");
            parameterSource.addValue("taskKey", (Object)queryObject.getTaskKey());
        }
        if (queryObject.getFormSchemeKey() != null) {
            stringBuilder.append("and dc_formScheme_key = :formSchemeKey ");
            parameterSource.addValue("formSchemeKey", (Object)queryObject.getFormSchemeKey());
        }
        if (queryObject.getMainDim() != null) {
            stringBuilder.append("and dc_main_dim = :mainDim ");
            parameterSource.addValue("mainDim", (Object)queryObject.getMainDim());
        } else if (!CollectionUtils.isEmpty(queryObject.getMainDims())) {
            stringBuilder.append("and dc_main_dim in (:mainDim) ");
            parameterSource.addValue("mainDim", queryObject.getMainDims());
        }
        if (queryObject.getAssistDim() != null && !"".equals(queryObject.getAssistDim())) {
            stringBuilder.append("and dc_assist_dim = :assistDim ");
            parameterSource.addValue("assistDim", (Object)queryObject.getAssistDim());
        }
        return this.template.query(stringBuilder.toString(), (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
    }

    @Override
    public List<QueryObjectImpl> getSolutions2(QueryObjectImpl queryObject) {
        StringBuilder stringBuilder = new StringBuilder(QUERY_TEMPLATE);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        stringBuilder.append("where 1 =1 ");
        if (queryObject.getTaskKey() != null) {
            stringBuilder.append("and dc_task_key =  :taskKey ");
            parameterSource.addValue("taskKey", (Object)queryObject.getTaskKey());
        }
        if (queryObject.getFormSchemeKey() != null) {
            stringBuilder.append("and dc_formScheme_key = :formSchemeKey ");
            parameterSource.addValue("formSchemeKey", (Object)queryObject.getFormSchemeKey());
        }
        if (queryObject.getMainDim() != null) {
            stringBuilder.append("and dc_main_dim = :mainDim ");
            parameterSource.addValue("mainDim", (Object)queryObject.getMainDim());
        }
        if (queryObject.getAssistDim() != null && !"".equals(queryObject.getAssistDim())) {
            stringBuilder.append("and dc_assist_dim = :assistDim ");
            parameterSource.addValue("assistDim", (Object)queryObject.getAssistDim());
        }
        return this.template.query(stringBuilder.toString(), (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
    }

    @Override
    public List<QueryObjectImpl> getSolutions(QueryObjectImpl queryObject, Boolean getScheme) {
        StringBuilder stringBuilder = new StringBuilder(QUERY_TEMPLATE);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        stringBuilder.append("where 1 =1 ");
        if (queryObject.getTaskKey() != null) {
            stringBuilder.append("and dc_task_key =  :taskKey ");
            parameterSource.addValue("taskKey", (Object)queryObject.getTaskKey());
        }
        if (queryObject.getFormSchemeKey() != null) {
            stringBuilder.append("and dc_formScheme_key = :formSchemeKey ");
            parameterSource.addValue("formSchemeKey", (Object)queryObject.getFormSchemeKey());
        }
        if (queryObject.getMainDim() != null) {
            stringBuilder.append("and dc_main_dim = :mainDim ");
            parameterSource.addValue("mainDim", (Object)queryObject.getMainDim());
        }
        if (queryObject.getAssistDim() != null && !"".equals(queryObject.getAssistDim())) {
            stringBuilder.append("and dc_assist_dim = :assistDim ");
            parameterSource.addValue("assistDim", (Object)queryObject.getAssistDim());
        } else {
            stringBuilder.append("and dc_assist_dim is null ");
        }
        if (getScheme.booleanValue()) {
            stringBuilder.append("and dc_solution_key is not null ");
        } else {
            stringBuilder.append("and dc_rpt_scheme is not null ");
        }
        return this.template.query(stringBuilder.toString(), (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
    }

    @Override
    public List<TransferEntityBean> findAllData() {
        String sql = "SELECT DC_KEY, DC_ASSIST_DIM, DC_FORMSCHEME_KEY, DC_MAIN_DIM FROM EFDC_REPOSITORY";
        return (List)this.template.query(sql, rs -> {
            ArrayList<TransferEntityBean> result = new ArrayList<TransferEntityBean>();
            while (rs.next()) {
                TransferEntityBean bean = new TransferEntityBean();
                bean.setId(rs.getString("DC_KEY"));
                bean.setMainDim(rs.getString("DC_MAIN_DIM"));
                bean.setAssistDim(rs.getString("DC_ASSIST_DIM"));
                bean.setFormScheme(rs.getString("DC_FORMSCHEME_KEY"));
                result.add(bean);
            }
            return result;
        });
    }

    @Override
    public void batchUpdate(List<TransferEntityBean> entities) {
        String sql = "UPDATE EFDC_REPOSITORY SET DC_MAIN_DIM = ?, DC_ASSIST_DIM = ? WHERE DC_KEY = ?";
        ArrayList<Object[]> args = new ArrayList<Object[]>(entities.size());
        for (TransferEntityBean entity : entities) {
            Object[] arg = new Object[]{entity.getMainDim(), entity.getAssistDim(), entity.getId()};
            args.add(arg);
        }
        this.template.getJdbcTemplate().batchUpdate(sql, args);
    }

    @Override
    public int countSolutionByTask(String taskKey) {
        String sql = "SELECT COUNT(1) as solution FROM EFDC_REPOSITORY WHERE DC_SOLUTION_KEY != '00000000-0000-0000-0000-000000000000' AND DC_TASK_KEY = ?";
        Object[] args = new Object[]{taskKey};
        return (Integer)this.template.getJdbcTemplate().query(sql, args, rs -> {
            int solution = 0;
            while (rs.next()) {
                solution = rs.getInt("solution");
            }
            return solution;
        });
    }

    @Override
    public String insert(QueryObjectImpl impl) {
        if (impl.getId() == null) {
            impl.setId(UUID.randomUUID().toString());
        }
        this.template.getJdbcOperations().update("insert into EFDC_REPOSITORY (DC_KEY, DC_ASSIST_DIM, DC_FORMSCHEME_KEY, DC_MAIN_DIM, DC_RPT_SCHEME, DC_SOLUTION_KEY, DC_TASK_KEY, DC_UPDATETIME) values (?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{impl.getId(), impl.getAssistDim(), impl.getFormSchemeKey(), impl.getMainDim(), impl.getRptScheme(), impl.getSolutionKey(), impl.getTaskKey(), impl.getUpdatetime()});
        return impl.getId();
    }

    @Override
    public void update(QueryObjectImpl impl) {
        this.template.getJdbcOperations().update("update EFDC_REPOSITORY SET DC_ASSIST_DIM = ?, DC_FORMSCHEME_KEY = ?, DC_MAIN_DIM = ?, DC_RPT_SCHEME = ?, DC_SOLUTION_KEY = ?, DC_TASK_KEY = ?, DC_UPDATETIME = ? where  DC_KEY = ?", new Object[]{impl.getAssistDim(), impl.getFormSchemeKey(), impl.getMainDim(), impl.getRptScheme(), impl.getSolutionKey(), impl.getTaskKey(), impl.getUpdatetime(), impl.getId()});
    }

    @Override
    public void delete(QueryObjectImpl obj) {
        this.template.getJdbcOperations().update("delete from EFDC_REPOSITORY where DC_KEY = ?", new Object[]{obj.getId()});
    }

    @Override
    public void deleteAll(List<QueryObjectImpl> objs) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ids", objs.stream().map(QueryObjectImpl::getId).collect(Collectors.toList()));
        this.template.update("delete from EFDC_REPOSITORY where DC_KEY in (:ids)", (SqlParameterSource)parameterSource);
    }
}


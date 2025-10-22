/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataRegionDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeDataRegionDefineDao
extends BaseDao {
    private static final String ATTR_FORMKEY = "formKey";
    private Class<RunTimeDataRegionDefineImpl> implClass = RunTimeDataRegionDefineImpl.class;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DataRegionDefine> list() {
        return this.list(this.implClass);
    }

    public DataRegionDefine getDefineByKey(String id) {
        return (DataRegionDefine)this.getByKey(id, this.implClass);
    }

    public List<DataRegionDefine> getDefinesByKey(String[] ids) {
        ArrayList<DataRegionDefine> regions = new ArrayList<DataRegionDefine>();
        for (String id : ids) {
            regions.add(this.getDefineByKey(id));
        }
        return regions;
    }

    public List<DataRegionDefine> getAllRegionsInFormScheme(String formSchemeKey) {
        String sql = "SELECT R.* FROM NR_PARAM_DATAREGION R LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY WHERE F.FM_FORMSCHEME = ?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataRegionDefineImpl dataRegionDefine = new RunTimeDataRegionDefineImpl();
            this.readRecord(rs, dataRegionDefine);
            return dataRegionDefine;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formSchemeKey});
    }

    public List<DataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.list(new String[]{ATTR_FORMKEY}, new Object[]{formKey}, this.implClass);
    }

    public List<DataRegionDefine> getAllRegionsFromField(String fieldKey) {
        RunTimeDataLinkDefineImpl link = new RunTimeDataLinkDefineImpl();
        link.setLinkExpression(fieldKey);
        return this.listByForeign(link, new String[]{"fieldKey"}, this.implClass);
    }

    public List<DataRegionDefine> listGhostRegion() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORM fm where DR_FORM_KEY = fm.FM_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }

    public List<DataRegionDefine> getAllRegionsInForm(List<String> formKeys) {
        return DefinitionUtils.limitExe(formKeys, this::listByFormKeys);
    }

    public DataRegionDefine getDataRegion(String formKey, String regionCode) {
        String sqlWhere = " DR_FORM_KEY=? AND DR_CODE=? ";
        return (DataRegionDefine)this.getBy(sqlWhere, new Object[]{formKey, regionCode}, this.implClass);
    }

    private List<DataRegionDefine> listByFormKeys(List<String> formKeys) {
        String sql = "SELECT R.* FROM NR_PARAM_DATAREGION R WHERE R.DR_FORM_KEY IN (:formKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("formKeys", formKeys);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataRegionDefineImpl dataRegionDefine = new RunTimeDataRegionDefineImpl();
            this.readRecord(rs, dataRegionDefine);
            return dataRegionDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }
}


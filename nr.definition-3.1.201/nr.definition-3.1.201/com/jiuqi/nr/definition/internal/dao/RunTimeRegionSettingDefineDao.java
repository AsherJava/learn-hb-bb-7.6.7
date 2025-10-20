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
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeRegionSettingDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeRegionSettingDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private Class<RunTimeRegionSettingDefineImpl> implClass = RunTimeRegionSettingDefineImpl.class;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<RegionSettingDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<RegionSettingDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public RegionSettingDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (RegionSettingDefine)defines.get(0);
        }
        return null;
    }

    public RegionSettingDefine getDefineByKey(String id) {
        return (RegionSettingDefine)this.getByKey(id, this.implClass);
    }

    public List<RegionSettingDefine> getDefinesByForm(String formKey) {
        String sql = "SELECT T_SETTING.* FROM NR_PARAM_REGIONSETTING T_SETTING INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_SETTING.RS_KEY=T_REGION.DR_REGION_SETTING WHERE T_REGION.DR_FORM_KEY=?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeRegionSettingDefineImpl regionSetting = new RunTimeRegionSettingDefineImpl();
            this.readRecord(rs, regionSetting);
            return regionSetting;
        };
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formKey});
    }

    public List<RegionSettingDefine> getDefinesByForm(List<String> formKeys) {
        return DefinitionUtils.limitExe(formKeys, this::listByFormKeys);
    }

    private List<RegionSettingDefine> listByFormKeys(List<String> formKeys) {
        String sql = "SELECT T_SETTING.* FROM NR_PARAM_REGIONSETTING T_SETTING INNER JOIN NR_PARAM_DATAREGION T_REGION ON T_SETTING.RS_KEY=T_REGION.DR_REGION_SETTING WHERE T_REGION.DR_FORM_KEY IN (:formKeys)";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("formKeys", formKeys);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeRegionSettingDefineImpl regionSettingDefine = new RunTimeRegionSettingDefineImpl();
            this.readRecord(rs, regionSettingDefine);
            return regionSettingDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<RegionSettingDefine> getDefinesByFormScheme(String formSchemeKey) {
        String sql = "SELECT S.* FROM NR_PARAM_REGIONSETTING S LEFT JOIN NR_PARAM_DATAREGION R ON S.RS_KEY = R.DR_REGION_SETTING LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY WHERE F.FM_FORMSCHEME = ?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeRegionSettingDefineImpl regionSettingDefine = new RunTimeRegionSettingDefineImpl();
            this.readRecord(rs, regionSettingDefine);
            return regionSettingDefine;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formSchemeKey});
    }
}


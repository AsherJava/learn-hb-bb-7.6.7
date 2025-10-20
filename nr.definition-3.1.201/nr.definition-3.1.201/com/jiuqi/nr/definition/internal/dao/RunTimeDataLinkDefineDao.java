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
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeDataLinkDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_REGIONKEY = "regionKey";
    private static String ATTR_EXPRESSION = "linkFieldKey";
    private static String ATTR_POSX = "posX";
    private static String ATTR_POSY = "posY";
    private static String ATTR_EDITMODE = "editMode";
    private static String ATTR_FILTER_TEMPLATE = "filterTemplate";
    private Class<RunTimeDataLinkDefineImpl> implClass = RunTimeDataLinkDefineImpl.class;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    @Deprecated
    public List<DataLinkDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<DataLinkDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByRegion(String dataRegionkey) throws Exception {
        this.deleteBy(new String[]{ATTR_REGIONKEY}, new Object[]{dataRegionkey});
    }

    public void deleteByField(String fieldkey) throws Exception {
        this.deleteBy(new String[]{ATTR_EXPRESSION}, new Object[]{fieldkey});
    }

    @Deprecated
    public DataLinkDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DataLinkDefine)defines.get(0);
        }
        return null;
    }

    public DataLinkDefine getDefineByKey(String id) {
        return (DataLinkDefine)this.getByKey(id, this.implClass);
    }

    public List<DataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        return this.list(new String[]{ATTR_REGIONKEY}, new Object[]{dataRegionKey}, this.implClass);
    }

    public List<DataLinkDefine> getAllLinksInRegionAndPosXY(String dataRegionKey, int posX, int posY) throws Exception {
        return this.list(new String[]{ATTR_REGIONKEY, ATTR_POSX, ATTR_POSY}, new Object[]{dataRegionKey, posX, posY}, this.implClass);
    }

    public List<DataLinkDefine> getAllLinksInRegionAndEditMode(String dataRegionKey, DataLinkEditMode dataLinkEditMode) throws Exception {
        return this.list(new String[]{ATTR_REGIONKEY, ATTR_EDITMODE}, new Object[]{dataRegionKey, dataLinkEditMode}, this.implClass);
    }

    public DataLinkDefine getDefineByFieldKey(String fieldKey) throws Exception {
        List defines = this.list(new String[]{ATTR_EXPRESSION}, new Object[]{fieldKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DataLinkDefine)defines.get(0);
        }
        return null;
    }

    public List<DataLinkDefine> getDefinesByFieldKey(String fieldKey) throws Exception {
        List defines = this.list(new String[]{ATTR_EXPRESSION}, new Object[]{fieldKey}, this.implClass);
        return defines;
    }

    public List<DataLinkDefine> getDefinesByFieldAndRegionKey(String fieldKey, String regionKey) throws Exception {
        List defines = this.list(new String[]{ATTR_EXPRESSION, ATTR_REGIONKEY}, new Object[]{fieldKey, regionKey}, this.implClass);
        return defines;
    }

    public List<DataLinkDefine> getDefinesByForm(String formKey) {
        if (formKey == null) {
            throw new IllegalArgumentException("'formKey' must not be null.");
        }
        String sql = "select t_datalink.* from NR_PARAM_DATALINK t_datalink inner join NR_PARAM_DATAREGION t_dataregion on t_datalink.DL_REGION_KEY=t_dataregion.DR_KEY where t_dataregion.DR_FORM_KEY=?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataLinkDefineImpl dataLinkDefine = new RunTimeDataLinkDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formKey.toString()});
    }

    public List<DataLinkDefine> listGhostLink() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_DATAREGION dr where DL_REGION_KEY = dr.DR_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }

    public List<DataLinkDefine> getDefinesByFormScheme(String formScheme) {
        String sql = "SELECT L.* FROM NR_PARAM_DATALINK L LEFT JOIN NR_PARAM_DATAREGION R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY WHERE F.FM_FORMSCHEME = ?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataLinkDefineImpl dataLinkDefine = new RunTimeDataLinkDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formScheme});
    }

    public List<DataLinkDefine> getDefinesByForm(List<String> formKeys) {
        return DefinitionUtils.limitExe(formKeys, this::listByFormKeys);
    }

    private List<DataLinkDefine> listByFormKeys(List<String> formKeys) {
        String sql = "SELECT T_DATALINK.* FROM NR_PARAM_DATALINK T_DATALINK INNER JOIN NR_PARAM_DATAREGION T_DATAREGION ON T_DATALINK.DL_REGION_KEY=T_DATAREGION.DR_KEY WHERE T_DATAREGION.DR_FORM_KEY IN (:formKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("formKeys", formKeys);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataLinkDefineImpl dataLinkDefine = new RunTimeDataLinkDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<DataLinkDefine> queryByUniqueCodes(List<String> uniqueCodes) {
        return DefinitionUtils.limitExe(uniqueCodes, subUniqueCodes -> this.listByUniqueCodes((List<String>)subUniqueCodes));
    }

    private List<DataLinkDefine> listByUniqueCodes(List<String> uniqueCodes) {
        String sql = "SELECT T_DATALINK.* FROM NR_PARAM_DATALINK T_DATALINK WHERE T_DATALINK.DL_UNIQUE_CODE IN (:uniqueCodes) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("uniqueCodes", uniqueCodes);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeDataLinkDefineImpl dataLinkDefine = new RunTimeDataLinkDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<RunTimeDataLinkDefineImpl> listHasFilterExpressionLinks() {
        return this.list("DL_FILTER_EXPRESSION IS NOT NULL AND DL_FILTER_EXPRESSION != '' ", new Object[0], this.implClass);
    }
}


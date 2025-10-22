/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.util.StringUtils
 *  org.apache.shiro.util.Assert
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class DesignDataLinkDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_REGIONKEY = "regionKey";
    private static String ATTR_EXPRESSION = "linkFieldKey";
    private static String ATTR_POSX = "posX";
    private static String ATTR_POSY = "posY";
    private static String ATTR_COLNUM = "colNum";
    private static String ATTR_ROWNUM = "rowNum";
    private static String ATTR_UNIQUECODE = "uniqueCode";
    private static String ATTR_FILTER_TEMPLATE = "filterTemplate";
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private Class<DesignDataLinkDefineImpl> implClass = DesignDataLinkDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignDataLinkDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByRegion(String dataRegionkey) throws Exception {
        this.deleteBy(new String[]{ATTR_REGIONKEY}, new Object[]{dataRegionkey});
    }

    public void deleteByField(String fieldkey) throws Exception {
        this.deleteBy(new String[]{ATTR_EXPRESSION}, new Object[]{fieldkey});
    }

    public DesignDataLinkDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignDataLinkDefine)defines.get(0);
        }
        return null;
    }

    public DesignDataLinkDefine getDefineByKey(String id) throws Exception {
        return (DesignDataLinkDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignDataLinkDefine> getAllLinksInRegion(String dataRegionKey) throws Exception {
        return this.list(new String[]{ATTR_REGIONKEY}, new Object[]{dataRegionKey}, this.implClass);
    }

    public List<DesignDataLinkDefine> getLinksInRegionAndPos(String dataRegionKey, int posX, int posY) throws Exception {
        return this.list(new String[]{ATTR_REGIONKEY, ATTR_POSX, ATTR_POSY}, new Object[]{dataRegionKey, posX, posY}, this.implClass);
    }

    public List<DesignDataLinkDefine> ListRelDataLinks(String filterTemplateID) {
        Assert.notNull((Object)filterTemplateID, (String)"filterTemplateID must not be null");
        return this.list(new String[]{ATTR_FILTER_TEMPLATE}, new Object[]{filterTemplateID}, this.implClass);
    }

    public DesignDataLinkDefine getDefineByFieldKey(String fieldKey) throws Exception {
        List defines = this.list(new String[]{ATTR_EXPRESSION}, new Object[]{fieldKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignDataLinkDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignDataLinkDefine> getDefinesByFieldKey(String fieldKey) throws Exception {
        return this.list(new String[]{ATTR_EXPRESSION}, new Object[]{fieldKey.toString()}, this.implClass);
    }

    public List<DesignDataLinkDefine> getDefinesByFieldKeys(List<String> fieldKeys) {
        return DefinitionUtils.limitExe(fieldKeys, this::listDefinesByFieldKeys);
    }

    private List<DesignDataLinkDefine> listDefinesByFieldKeys(List<String> fieldKeys) {
        String sql = "SELECT T_DATALINK.* FROM NR_PARAM_DATALINK_DES T_DATALINK WHERE T_DATALINK.DL_FIELD_KEY IN (:fieldKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("fieldKeys", fieldKeys);
        RowMapper rowMapper = (rs, rowNum) -> {
            DesignDataLinkDefineImpl dataLinkDefine = new DesignDataLinkDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<DesignDataLinkDefine> getDefinesByFieldAndRegionKey(String fieldKey, String regionKey) throws Exception {
        return this.list(new String[]{ATTR_EXPRESSION, ATTR_REGIONKEY}, new Object[]{fieldKey, regionKey}, this.implClass);
    }

    public DesignDataLinkDefine queryDataLinkDefineByColRow(String reportKey, int colIndex, int rowIndex) {
        DesignDataRegionDefineImpl designDataRegionDefineImpl = new DesignDataRegionDefineImpl();
        designDataRegionDefineImpl.setFormKey(reportKey);
        List list = this.listByForeign(designDataRegionDefineImpl, new String[]{ATTR_COLNUM, ATTR_ROWNUM}, new String[]{"formKey"}, new Object[]{colIndex, rowIndex}, this.implClass);
        if (list.size() == 0) {
            return null;
        }
        return (DesignDataLinkDefine)list.get(0);
    }

    public DesignDataLinkDefine queryDataLinkDefineByUniquecode(String reportKey, String dataLinkCode) {
        DesignDataRegionDefineImpl designDataRegionDefineImpl = new DesignDataRegionDefineImpl();
        designDataRegionDefineImpl.setFormKey(reportKey);
        List list = this.listByForeign(designDataRegionDefineImpl, new String[]{ATTR_UNIQUECODE}, new String[]{"formKey"}, new Object[]{dataLinkCode, reportKey}, this.implClass);
        if (list.size() == 0) {
            return null;
        }
        return (DesignDataLinkDefine)list.get(0);
    }

    public List<DesignDataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        DesignDataRegionDefineImpl designDataRegionDefineImpl = new DesignDataRegionDefineImpl();
        designDataRegionDefineImpl.setFormKey(formKey);
        return this.listByForeign(designDataRegionDefineImpl, new String[]{ATTR_EXPRESSION}, new String[]{"formKey"}, new Object[]{fieldKey.toString(), formKey}, this.implClass);
    }

    public List<DesignDataLinkDefine> listGhostLink() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_DATAREGION_DES dr where DL_REGION_KEY = dr.DR_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }

    public List<DesignDataLinkDefine> listLinkNotHaveField(String regionKey) {
        String sqlWhere = " DL_TYPE = 1 and  not exists (select 1 from DES_SYS_FIELDDEFINE FD where dl_field_key = FD.FD_KEY)";
        if (StringUtils.isNotEmpty((String)regionKey)) {
            sqlWhere = sqlWhere + " and DL_REGION_KEY = '" + regionKey + "'";
        }
        return this.list(sqlWhere, null, this.implClass);
    }

    public List<String> listLinkKeyPhysicalCoordinatesDuplicate(String regionKey) {
        String sql = "SELECT MAX(DL_KEY) FROM NR_PARAM_DATALINK_DES DL WHERE DL_POSX<>0 AND DL_POSY<>0" + (StringUtils.isEmpty((String)regionKey) ? "" : " and DL_REGION_KEY = '" + regionKey + "'") + " GROUP BY DL_POSX,DL_POSY,DL_REGION_KEY HAVING COUNT(1)>1";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> listLinkKeyDataCoordinatesDuplicate(String regionKey) {
        String sql = "SELECT MAX(DL_KEY) FROM NR_PARAM_DATALINK_DES DL " + (StringUtils.isEmpty((String)regionKey) ? "" : " WHERE DL_REGION_KEY = '" + regionKey + "'") + "GROUP BY DL_COL_NUM,DL_ROW_NUM,DL_REGION_KEY HAVING COUNT(1)>1";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> listLinkKeyRefuseView(String regionKey) {
        String sql = "SELECT DL.DL_KEY FROM NR_PARAM_DATALINK_DES DL WHERE " + (StringUtils.isNotEmpty((String)regionKey) ? "DL.DL_REGION_KEY = '" + regionKey + "' AND" : "") + " DL.DL_VIEW_KEY IS NOT NULL AND NOT EXISTS (SELECT 1 FROM DES_SYS_ENTITYVIEWDEFINE EV WHERE EV.EV_KEY = DL.DL_VIEW_KEY)";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> listLinkKeyViewQuoteError(String regionKey) {
        String sql = "SELECT DL.DL_KEY FROM NR_PARAM_DATALINK_DES DL ,DES_SYS_FIELDDEFINE FD1 ,DES_SYS_FIELDDEFINE FD2 WHERE " + (StringUtils.isNotEmpty((String)regionKey) ? "DL.DL_REGION_KEY = '" + regionKey + "' AND" : "") + " DL.DL_VIEW_KEY IS NOT NULL AND DL.dl_field_key = FD1.FD_KEY AND FD1.FD_REF_FIELD = FD2.FD_KEY  AND NOT EXISTS (SELECT * FROM DES_SYS_ENTITYVIEWDEFINE EV WHERE FD2.FD_OWN_TABLE = EV.EV_TABLE_KEY AND EV.EV_KEY = DL.DL_VIEW_KEY)";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> listLinkKeyNoneView(String regionKey) {
        String sql = "SELECT DL.DL_KEY FROM NR_PARAM_DATALINK_DES DL ,DES_SYS_FIELDDEFINE FD WHERE " + (StringUtils.isNotEmpty((String)regionKey) ? "DL.DL_REGION_KEY = '" + regionKey + "' AND" : "") + " DL.DL_TYPE = 1 AND DL.DL_VIEW_KEY IS NULL AND DL.dl_field_key = FD.FD_KEY AND FD.FD_REF_FIELD IS NOT NULL; ";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> getAllTableKeysInRegion(String dataRegionKey) {
        String sql = "SELECT DISTINCT(DF_DT_KEY) FROM NR_PARAM_DATALINK_DES INNER JOIN NR_DATASCHEME_FIELD_DES ON dl_field_key = DF_KEY WHERE DL_REGION_KEY =? AND DL_TYPE = 1 ";
        return this.queryForList(sql, new String[]{dataRegionKey}, String.class);
    }

    public List<DesignDataLinkDefine> listHasFilterExpressionLinks() {
        return this.list("DL_FILTER_EXPRESSION IS NOT NULL AND DL_FILTER_EXPRESSION != '' ", new Object[0], this.implClass);
    }

    public List<String> listAllRegionRelFilterTemplate(String filterTemplateID) {
        String sql = "SELECT DISTINCT(DL_REGION_KEY) FROM NR_PARAM_DATALINK_DES WHERE DL_FILTER_TEMPLATE = '" + filterTemplateID + "' ";
        return this.queryForList(sql, null, String.class);
    }

    public List<String> listDataTableByForm(Set<String> forms) {
        String sql = "SELECT F.DF_DT_KEY FROM NR_PARAM_DATALINK_DES L LEFT JOIN NR_PARAM_DATAREGION_DES R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_DATASCHEME_FIELD_DES F ON L.DL_FIELD_KEY = F.DF_KEY WHERE L.DL_TYPE IN (1, 4) AND R.DR_FORM_KEY IN (:forms) GROUP BY F.DF_DT_KEY";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("forms", forms);
        return (List)this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rs -> {
            ArrayList<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        });
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.util.FieldSqlConditionUtil
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DesignFormDefineDao
extends BaseDao {
    private static String ATTR_CODE = "formCode";
    private static String ATTR_TITLE = "title";
    private static String ATTR_TYPE = "formType";
    private static String ATTR_SCHEME = "formScheme";
    private static String FIELD_KEY = "fm_key";
    private static String FIELD_CODE = "fm_code";
    private static String FIELD_TITLE = "fm_title";
    private static String FIELD_TYPE = "fm_type";
    private static String FIELD_ORDER = "fm_order";
    private static String FIELD_UPDATE_TIME = "fm_updatetime";
    private static String FIELD_UPDATE_USER = "FM_UPDATE_USER";
    private static String FIELD_SERIAL = "fm_serial_Number";
    private static String TABLE_NAME = "nr_param_form_des";
    private Class<DesignFormDefineImpl> implClass = DesignFormDefineImpl.class;
    @Autowired
    private DesignFormGroupLinkDao groupLinkDao;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(DesignFormDefineDao.class);

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignFormDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignFormDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignFormDefine> queryDefinesListByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines) {
            return defines;
        }
        return null;
    }

    public DesignFormDefine getDefineByKey(String id) {
        return (DesignFormDefine)this.getByKey(id, this.implClass);
    }

    public void deleteAllFromDefines() throws Exception {
        this.deleteBy(null, new Object[0]);
    }

    public List<DesignFormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setGroupKey(formGroupKey);
        List formDefinesList = this.listByForeign(link, new String[]{"groupKey"}, DesignFormDefineImpl.class);
        ArrayList<DesignFormDefine> newFormDefinesList = new ArrayList<DesignFormDefine>();
        if (formDefinesList != null && formDefinesList.size() > 0) {
            for (DesignFormDefine DesignFormDefine2 : formDefinesList) {
                newFormDefinesList.add(this.buildNewFormDefine(formGroupKey, DesignFormDefine2));
            }
        }
        return newFormDefinesList;
    }

    public List<DesignFormDefine> getFormsInGroupByCode(String formGroupKey, String formDefineCode) throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setGroupKey(formGroupKey);
        List formDefinesList = this.listByForeign(link, new String[]{ATTR_CODE}, new String[]{"groupKey"}, new Object[]{formDefineCode, formGroupKey}, DesignFormDefineImpl.class);
        ArrayList<DesignFormDefine> newFormDefinesList = new ArrayList<DesignFormDefine>();
        if (formDefinesList != null && formDefinesList.size() > 0) {
            for (DesignFormDefine DesignFormDefine2 : formDefinesList) {
                newFormDefinesList.add(this.buildNewFormDefine(formGroupKey, DesignFormDefine2));
            }
        }
        return newFormDefinesList;
    }

    public List<DesignFormDefine> getFormsInGroupByTitle(String formGroupKey, String formDefineTitle) throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setGroupKey(formGroupKey);
        List formDefinesList = this.listByForeign(link, new String[]{ATTR_TITLE}, new String[]{"groupKey"}, new Object[]{formDefineTitle, formGroupKey}, DesignFormDefineImpl.class);
        ArrayList<DesignFormDefine> newFormDefinesList = new ArrayList<DesignFormDefine>();
        if (formDefinesList != null && formDefinesList.size() > 0) {
            for (DesignFormDefine DesignFormDefine2 : formDefinesList) {
                newFormDefinesList.add(this.buildNewFormDefine(formGroupKey, DesignFormDefine2));
            }
        }
        return newFormDefinesList;
    }

    public List<DesignFormDefine> getFormsInGroupByType(String formGroupKey, FormType type) throws JQException {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setGroupKey(formGroupKey);
        List formDefinesList = this.listByForeign(link, new String[]{ATTR_TYPE}, new String[]{"groupKey"}, new Object[]{type.getValue(), formGroupKey}, DesignFormDefineImpl.class);
        ArrayList<DesignFormDefine> newFormDefinesList = new ArrayList<DesignFormDefine>();
        if (formDefinesList != null && formDefinesList.size() > 0) {
            for (DesignFormDefine DesignFormDefine2 : formDefinesList) {
                newFormDefinesList.add(this.buildNewFormDefine(formGroupKey, DesignFormDefine2));
            }
        }
        return newFormDefinesList;
    }

    public List<DesignFormDefine> getFormsInSchemeByCode(String scheme, String formDefineCode) {
        List list = this.list(new String[]{ATTR_CODE, ATTR_SCHEME}, new String[]{formDefineCode, scheme}, this.implClass);
        return list;
    }

    public List<DesignFormDefine> getFormsInSchemeByTitle(String scheme, String formDefineTitle) throws Exception {
        List list = this.list(new String[]{ATTR_TITLE, ATTR_SCHEME}, new String[]{formDefineTitle, scheme}, this.implClass);
        return list;
    }

    public List<DesignFormDefine> getFormsInSchemeByType(String scheme, FormType type) throws JQException {
        List list = this.list(new String[]{ATTR_TYPE, ATTR_SCHEME}, new Object[]{type.getValue(), scheme}, this.implClass);
        return list;
    }

    public List<DesignFormDefine> listGhostForm() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMGROUPLINK_DES fgl where FM_KEY = fgl.FGL_FORM_KEY) and (FM_TYPE <> 4 or FM_TYPE <> 7)";
        return this.list(sqlWhere, null, this.implClass);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DesignFormDefine> querySimpleFormDefinesByFormKeys(List<String> args) {
        if (CollectionUtils.isEmpty(args)) {
            return Collections.emptyList();
        }
        StringBuilder sb = new StringBuilder(String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s WHERE ", FIELD_KEY, FIELD_CODE, FIELD_TITLE, FIELD_TYPE, FIELD_SERIAL, FIELD_ORDER, FIELD_UPDATE_TIME, TABLE_NAME));
        Connection connection = null;
        try {
            connection = this.jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            FieldSqlConditionUtil.printSplitedInSQL((IDatabase)database, (Connection)connection, (StringBuilder)sb, (String)FIELD_KEY, (int)6, args, null, null, (boolean)false);
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("ids", args);
            List<DesignFormDefine> list = this.executeInSql(sb.toString(), parameterSource);
            return list;
        }
        catch (SQLException e) {
            this.logger.error("\u67e5\u8be2\u62a5\u8868\u5217\u8868\u5931\u8d25\uff01", e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                this.logger.error("\u67e5\u8be2\u62a5\u8868\u5217\u8868\u5931\u8d25\uff01", e);
            }
        }
        return Collections.emptyList();
    }

    private List<DesignFormDefine> executeInSql(String sql, MapSqlParameterSource parameterSource) {
        return this.jdbcTemplate.query(sql, (SqlParameterSource)parameterSource, (rs, num) -> {
            DesignFormDefineImpl define = new DesignFormDefineImpl();
            define.setKey(rs.getString(FIELD_KEY));
            define.setTitle(rs.getString(FIELD_TITLE));
            define.setFormCode(rs.getString(FIELD_CODE));
            define.setFormCode(rs.getString(FIELD_CODE));
            define.setSerialNumber(rs.getString(FIELD_SERIAL));
            define.setFormType(FormType.forValue(rs.getInt(FIELD_TYPE)));
            define.setOrder(rs.getString(FIELD_ORDER));
            define.setUpdateTime(new Date(rs.getTimestamp(FIELD_UPDATE_TIME).getTime()));
            return define;
        });
    }

    private DesignFormDefine buildNewFormDefine(String formGroupKey, DesignFormDefine designFormDefine) throws JQException {
        DesignFormGroupLink designFormGroupLink = null;
        try {
            designFormGroupLink = this.groupLinkDao.queryDesignFormGroupLink(designFormDefine.getKey(), formGroupKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_052, (Throwable)e);
        }
        String formOrder = designFormGroupLink.getFormOrder();
        if (!StringUtils.isEmpty((String)formOrder)) {
            designFormDefine.setOrder(formOrder);
        }
        return designFormDefine;
    }

    public List<DesignFormDefine> queryByFormScheme(String scheme) {
        return this.list(new String[]{ATTR_SCHEME}, new Object[]{scheme}, this.implClass);
    }

    public List<DesignFormDefine> queryByFormSchemeAndType(String scheme, FormType formType) {
        return this.list(new String[]{ATTR_SCHEME, ATTR_TYPE}, new Object[]{scheme, formType.getValue()}, this.implClass);
    }

    public List<DesignFormDefine> getDefineByKeys(List<String> keys) {
        if (null == keys || keys.isEmpty()) {
            return Collections.emptyList();
        }
        return DefinitionUtils.limitExe(keys, this::listDefinesByKeys);
    }

    public List<DesignFormDefine> listDefinesByKeys(List<String> keys) {
        String whereSql = String.format(" FM_KEY IN (%s) ", keys.stream().map(k -> "?").collect(Collectors.joining(",")));
        return super.list(whereSql, keys.toArray(), this.implClass);
    }

    public void updateFormTime(String key) {
        String sql = String.format("UPDATE %s SET %s = :%s, %s = :%s where %s = :%s", TABLE_NAME, FIELD_UPDATE_TIME, FIELD_UPDATE_TIME, FIELD_UPDATE_USER, FIELD_UPDATE_USER, FIELD_KEY, FIELD_KEY);
        HashMap<String, Object> arg = new HashMap<String, Object>(2);
        arg.put(FIELD_KEY, key);
        arg.put(FIELD_UPDATE_USER, NpContextHolder.getContext().getUserName());
        arg.put(FIELD_UPDATE_TIME, new Date());
        this.jdbcTemplate.update(sql, arg);
    }

    public void batchUpdateFormTime(String ... keys) {
        String sql = String.format("UPDATE %s SET %s = :%s, %s = :%s where %s in (:%s)", TABLE_NAME, FIELD_UPDATE_TIME, FIELD_UPDATE_TIME, FIELD_UPDATE_USER, FIELD_UPDATE_USER, FIELD_KEY, FIELD_KEY);
        HashMap<String, Object> arg = new HashMap<String, Object>(2);
        arg.put(FIELD_UPDATE_USER, NpContextHolder.getContext().getUserName());
        arg.put(FIELD_UPDATE_TIME, new Date());
        arg.put(FIELD_KEY, Arrays.asList(keys));
        this.jdbcTemplate.update(sql, arg);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.formulamapping.dao;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.formulamapping.common.MappingKind;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class FormulaMappingDao
extends BaseDao {
    private static final String DB_TABLE_FGL = "nr_param_formgrouplink";
    private static final String DB_FIELD_FGL_GROUPKEY = "fgl_group_key";
    private static final String DB_FIELD_FGL_FORMKEY = "fgl_form_key";
    private final Class<FormulaMappingDefine> implClass = FormulaMappingDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<FormulaMappingDefine> query(String schemeKey) {
        String sql = String.format(" %s=? ", "FM_SCHEME_KEY");
        return super.list(sql, new Object[]{schemeKey}, this.implClass);
    }

    public List<FormulaMappingDefine> queryValid(String schemeKey) {
        String sql = String.format(" %s=? and %s is not null", "FM_SCHEME_KEY", "FM_SOURCE_CODE");
        return super.list(sql, new Object[]{schemeKey}, this.implClass);
    }

    public List<FormulaMappingDefine> query(String schemeKey, String formKey) {
        String sql = String.format(" %s=? and %s=? ", "FM_SCHEME_KEY", "FM_TARGET_FORM_KEY");
        return super.list(sql, new Object[]{schemeKey, formKey}, this.implClass);
    }

    public List<FormulaMappingDefine> query(String schemeKey, String formKey, String targetCode) {
        String sql = String.format(" %s=? and %s=? and %s=?", "FM_SCHEME_KEY", "FM_TARGET_FORM_KEY", "FM_TARGET_CODE");
        return super.list(sql, new Object[]{schemeKey, formKey, targetCode}, this.implClass);
    }

    public List<FormulaMappingDefine> queryAll(String schemeKey, String groupKey, String formKey) {
        super.buildMethod();
        StringBuffer sbr = new StringBuffer("select * from ");
        sbr.append("SYS_FORMULA_MAPPING");
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((CharSequence)schemeKey)) {
            if (!StringUtils.isEmpty((CharSequence)groupKey) && StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(groupKey);
                sbr.append(" inner join ").append(DB_TABLE_FGL).append(" on ").append(DB_FIELD_FGL_FORMKEY).append("=").append("FM_TARGET_FORM_KEY").append(" and ").append(DB_FIELD_FGL_GROUPKEY).append("=? ");
            }
            args.add(schemeKey);
            sbr.append(" where ").append("FM_SCHEME_KEY").append("=? ");
            if (!StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(formKey);
                sbr.append("and ").append("FM_TARGET_FORM_KEY").append("=? ");
            }
        }
        return super.queryList(sbr.toString(), args.toArray(), super.getRowMapper(this.implClass));
    }

    public long queryCount(String schemeKey, String groupKey, String formKey, String keyword) {
        StringBuffer sbr = new StringBuffer("select count(1) count from ");
        sbr.append("SYS_FORMULA_MAPPING");
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((CharSequence)schemeKey)) {
            if (!StringUtils.isEmpty((CharSequence)groupKey) && StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(groupKey);
                sbr.append(" inner join ").append(DB_TABLE_FGL).append(" on ").append(DB_FIELD_FGL_FORMKEY).append("=").append("FM_TARGET_FORM_KEY").append(" and ").append(DB_FIELD_FGL_GROUPKEY).append("=? ");
            }
            args.add("");
            args.add(schemeKey);
            sbr.append(" where ").append("(").append("FM_GROUP").append(" is null or ").append("FM_GROUP").append("=?) ").append("and ").append("FM_SCHEME_KEY").append("=? ");
            if (!StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(formKey);
                sbr.append("and ").append("FM_TARGET_FORM_KEY").append("=? ");
            }
        }
        this.keryword(sbr, args, keyword);
        Long count = (Long)super.queryForObject(sbr.toString(), args.toArray(), Long.class);
        return null == count ? 0L : count;
    }

    public List<FormulaMappingDefine> queryByCondition(String schemeKey, String groupKey, String formKey, String keyword) {
        super.buildMethod();
        String querySQL = this.buildQuerySQL();
        StringBuffer sbr = new StringBuffer(querySQL);
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((CharSequence)schemeKey)) {
            if (!StringUtils.isEmpty((CharSequence)groupKey) && StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(groupKey);
                sbr.append(" inner join ").append(DB_TABLE_FGL).append(" on ").append(DB_FIELD_FGL_FORMKEY).append("=").append("FM_TARGET_FORM_KEY").append(" and ").append(DB_FIELD_FGL_GROUPKEY).append("=? ");
            }
            args.add("");
            args.add(schemeKey);
            sbr.append(" where ").append("(").append("FM_GROUP").append(" is null or ").append("FM_GROUP").append("=?) ").append("and ").append("FM_SCHEME_KEY").append("=? ");
            if (!StringUtils.isEmpty((CharSequence)formKey)) {
                args.add(formKey);
                sbr.append("and ").append("FM_TARGET_FORM_KEY").append("=? ");
            }
        }
        this.keryword(sbr, args, keyword);
        sbr.append("order by ").append("FM_ORDER");
        List formulaMappingDefines = super.queryList(sbr.toString(), args.toArray(), super.getRowMapper(this.implClass));
        return formulaMappingDefines;
    }

    private String buildQuerySQL() {
        Field[] fields;
        StringBuffer querySQL = new StringBuffer("select ");
        String tablename = null;
        if (this.implClass.isAnnotationPresent(DBAnno.DBTable.class)) {
            DBAnno.DBTable dbTable = this.implClass.getAnnotation(DBAnno.DBTable.class);
            tablename = dbTable.dbTable().toUpperCase();
        }
        int i = 0;
        for (Field field : fields = this.implClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                querySQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            querySQL.append(fieldAnno.dbField().toUpperCase());
            ++i;
        }
        querySQL.append(" from ").append(tablename).append(" ");
        return querySQL.toString();
    }

    private String buildSQL(String sql, int startRow, int endRow) throws JQException {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        if (null == dataSource) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_100);
        }
        try (Connection connection = dataSource.getConnection();){
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql);
            sql = sqlBuilder.buildSQL(startRow, endRow);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_100, e.getMessage(), (Throwable)e);
        }
        return sql;
    }

    public List<FormulaMappingDefine> query(String schemeKey, String groupKey, String formKey, String keyword, int startRow, int endRow) throws JQException {
        super.buildMethod();
        String querySQL = this.buildQuerySQL();
        StringBuffer sbr = new StringBuffer(querySQL);
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((CharSequence)groupKey) && StringUtils.isEmpty((CharSequence)formKey)) {
            sbr.append(" inner join ").append(DB_TABLE_FGL).append(" on ").append(DB_FIELD_FGL_FORMKEY).append("=").append("FM_TARGET_FORM_KEY").append(" and ").append(DB_FIELD_FGL_GROUPKEY).append("=? ").append(" where ").append("(").append("FM_GROUP").append(" is null or ").append("FM_GROUP").append("=?) ").append("and ").append("FM_SCHEME_KEY").append("=? ");
            args.add(groupKey);
            args.add("");
            args.add(schemeKey);
        } else {
            sbr.append("where ").append("(").append("FM_GROUP").append(" is null or ").append("FM_GROUP").append("=?) ");
            args.add("");
            if (!StringUtils.isEmpty((CharSequence)schemeKey)) {
                sbr.append("and ").append("FM_SCHEME_KEY").append("=? ");
                args.add(schemeKey);
                if (!StringUtils.isEmpty((CharSequence)formKey)) {
                    sbr.append("and ").append("FM_TARGET_FORM_KEY").append("=? ");
                    args.add(formKey);
                }
            }
        }
        this.keryword(sbr, args, keyword);
        sbr.append("order by ").append("FM_ORDER");
        String sql = this.buildSQL(sbr.toString(), startRow, endRow);
        return super.queryList(sql, args.toArray(), super.getRowMapper(this.implClass));
    }

    private void keryword(StringBuffer sbr, List<String> args, String keyword) {
        if (!StringUtils.isEmpty((CharSequence)keyword)) {
            sbr.append(" and (UPPER(").append("FM_TARGET_CODE").append(") like ? or UPPER(").append("FM_SOURCE_CODE").append(") like ? or UPPER(").append("FM_TARGET_EXPRESSION").append(") like ? or UPPER(").append("FM_SOURCE_EXPRESSION").append(") like ? ").append(")");
            args.add("%" + keyword.toUpperCase() + "%");
            args.add("%" + keyword.toUpperCase() + "%");
            args.add("%" + keyword.toUpperCase() + "%");
            args.add("%" + keyword.toUpperCase() + "%");
        }
    }

    public double queryMaxOrder(String schemeKey, String formKey) throws JQException {
        super.buildMethod();
        String querySQL = this.buildQuerySQL();
        StringBuffer sbr = new StringBuffer(querySQL);
        Object[] args = null;
        if (!StringUtils.isEmpty((CharSequence)schemeKey)) {
            sbr.append(" where ").append("FM_SCHEME_KEY").append("=? ");
            if (!StringUtils.isEmpty((CharSequence)formKey)) {
                sbr.append("and ").append("FM_TARGET_FORM_KEY").append("=? ");
                args = new Object[]{schemeKey, formKey};
            } else {
                args = new Object[]{schemeKey};
            }
        } else {
            args = new Object[]{};
        }
        sbr.append(" order by ").append("FM_ORDER").append(" desc");
        String sql = this.buildSQL(sbr.toString(), 0, 1);
        List queryForList = super.queryList(sql, args, super.getRowMapper(this.implClass));
        if (null != queryForList && !queryForList.isEmpty()) {
            return ((FormulaMappingDefine)queryForList.get(0)).getOrder();
        }
        return 0.0;
    }

    public List<FormulaMappingDefine> queryByGroup(String schemeKey, String formKey, String groupKey) {
        String sql = String.format(" %s =? and %s=? and %s=? and %s=? ", "FM_SCHEME_KEY", "FM_TARGET_FORM_KEY", "FM_GROUP", "FM_KIND");
        return super.list(sql, new Object[]{schemeKey, formKey, groupKey, MappingKind.MAPPING.getValue()}, this.implClass);
    }

    public void deleteBySchemeKey(String schemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"schemeKey"}, new Object[]{schemeKey});
    }

    public void delete(String schemeKey, String formKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"schemeKey", "targetFormKey"}, new Object[]{schemeKey, formKey});
    }

    public void delete(String schemeKey, String[] formKeys) throws BeanParaException, DBParaException {
        if (1 == formKeys.length) {
            this.delete(schemeKey, formKeys[0]);
            return;
        }
        ArrayList<String> args = new ArrayList<String>();
        args.add(schemeKey);
        StringBuffer sbr = new StringBuffer("delete from %s where %s=? and %s in (");
        sbr.append("?");
        args.add(formKeys[0]);
        for (int i = 1; i < formKeys.length; ++i) {
            sbr.append(",?");
            args.add(formKeys[i]);
        }
        sbr.append(")");
        String sql = String.format(sbr.toString(), "SYS_FORMULA_MAPPING", "FM_SCHEME_KEY", "FM_TARGET_FORM_KEY");
        this.jdbcTemplate.update(sql, args.toArray());
    }

    public void deleteByGroup(String schemeKey, String mappingGroupKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"schemeKey", "group"}, new Object[]{schemeKey, mappingGroupKey});
    }

    public FormulaMappingDefine queryGroup(String schemeKey, String formKey, String targetCode) {
        String sql = String.format(" %s=? and %s=? and %s=? and %s=? ", "FM_SCHEME_KEY", "FM_TARGET_FORM_KEY", "FM_TARGET_CODE", "FM_KIND");
        return (FormulaMappingDefine)super.getBy(sql, new Object[]{schemeKey, formKey, targetCode, MappingKind.GROUP.getValue()}, this.implClass);
    }

    public void cleanMapping(String schemeKey, String[] formKeys) {
        ArrayList<Object> args = new ArrayList<Object>();
        String updateSql = String.format("update %s set %s=?, %s=?, %s=?, %s=?, %s=? ", "SYS_FORMULA_MAPPING", "FM_SOURCE_KEY", "FM_SOURCE_CODE", "FM_SOURCE_CHECKTYPE", "FM_SOURCE_EXPRESSION", "FM_MODE");
        StringBuffer sbr = new StringBuffer(updateSql);
        args.add("");
        args.add("");
        args.add(0);
        args.add("");
        args.add(0);
        args.add(schemeKey);
        sbr.append(" where ").append("FM_SCHEME_KEY").append("=?");
        if (null != formKeys && 0 != formKeys.length) {
            if (1 == formKeys.length) {
                args.add(formKeys[0]);
                sbr.append(" and ").append("FM_TARGET_FORM_KEY").append("=? ");
            } else {
                sbr.append(" and ").append("FM_TARGET_FORM_KEY").append(" in ('?',");
                args.add(formKeys[0]);
                for (int i = 1; i < formKeys.length; ++i) {
                    sbr.append(",'?'");
                    args.add(formKeys[i]);
                }
                sbr.append(")");
            }
        }
        this.jdbcTemplate.update(sbr.toString(), args.toArray());
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.task.form.formcopy.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyDataModelInfo;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyDataModelInfoImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class FormCopyDataModelInfoDao
extends BaseDao {
    private static final String SQL_SELECT = String.format("SELECT %s, %s, %s, %s, %s FROM %s ", "CDMI_DF_KEY", "CDMI_DT_KEY", "CDMI_DS_KEY", "CDMI_SRC_DF_KEY", "CDMI_SRC_DT_KEY", "NR_PARAM_COPY_DATAMODEL_INFO");
    private Class<FormCopyDataModelInfoImpl> implClz = FormCopyDataModelInfoImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public IFormCopyDataModelInfo getByFieldKey(String fieldKey) {
        return (IFormCopyDataModelInfo)super.getByKey((Object)fieldKey, this.implClz);
    }

    public List<IFormCopyDataModelInfo> getByFieldKey(List<String> fieldKey) {
        final ArrayList<IFormCopyDataModelInfo> result = new ArrayList<IFormCopyDataModelInfo>();
        String sql = String.format("%s WHERE %s IN (:fieldKey) ", SQL_SELECT, "CFI_FORM_KEY");
        HashMap<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("fieldKey", fieldKey);
        MapSqlParameterSource ps = new MapSqlParameterSource(params);
        new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate).query(sql, (SqlParameterSource)ps, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                FormCopyDataModelInfoImpl info = new FormCopyDataModelInfoImpl();
                info.setDataFieldKey(rs.getString(1));
                info.setDataTableKey(rs.getString(2));
                info.setDataSchemeKey(rs.getString(3));
                info.setSrcDataFieldKey(rs.getString(4));
                info.setSrcDataTableKey(rs.getString(5));
                result.add(info);
            }
        });
        return result;
    }

    public List<IFormCopyDataModelInfo> getBySchemeKey(String dataSchemeKey) {
        return super.list(new String[]{"dataSchemeKey"}, new Object[]{dataSchemeKey}, this.implClz);
    }

    public List<IFormCopyDataModelInfo> getByTableKey(String tableKey) {
        return super.list(new String[]{"dataTableKey"}, new Object[]{tableKey}, this.implClz);
    }

    public void deleteByFieldKey(List<String> fieldKeys) throws BeanParaException, DBParaException {
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return;
        }
        super.delete(fieldKeys.toArray());
    }

    public void deleteByTableKey(String tableKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"dataTableKey"}, new Object[]{tableKey});
        super.deleteBy(new String[]{"srcDataTableKey"}, new Object[]{tableKey});
    }
}


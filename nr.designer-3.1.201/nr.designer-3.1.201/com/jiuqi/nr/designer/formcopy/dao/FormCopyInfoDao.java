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
package com.jiuqi.nr.designer.formcopy.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.designer.formcopy.bean.impl.FormCopyInfoImpl;
import com.jiuqi.nr.designer.formcopy.common.FormCopyTransUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class FormCopyInfoDao
extends BaseDao {
    public static final String SQL_SELECT = String.format("SELECT %s, %s, %s, %s, %s FROM %s ", "CFI_FORM_KEY", "CFI_FS_KEY", "CFI_SRC_FORM_KEY", "CFI_SRC_FS_KEY", "CFI_UPDATETIME", "NR_PARAM_COPY_FORM_INFO");
    private Class<FormCopyInfoImpl> implClz = FormCopyInfoImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public Class<?> getExternalTransCls() {
        return FormCopyTransUtils.class;
    }

    public List<IFormCopyInfo> getByFormKeys(List<String> formKeys) {
        final ArrayList<IFormCopyInfo> result = new ArrayList<IFormCopyInfo>();
        String sql = String.format("%s WHERE %s IN (:formKeys) ", SQL_SELECT, "CFI_FORM_KEY");
        HashMap<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("formKeys", formKeys);
        MapSqlParameterSource ps = new MapSqlParameterSource(params);
        new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate).query(sql, (SqlParameterSource)ps, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                FormCopyInfoImpl info = new FormCopyInfoImpl();
                info.setFormKey(rs.getString("CFI_FORM_KEY"));
                info.setFormSchemeKey(rs.getString("CFI_FS_KEY"));
                info.setSrcFormKey(rs.getString("CFI_SRC_FORM_KEY"));
                info.setSrcFormSchemeKey(rs.getString("CFI_SRC_FS_KEY"));
                Timestamp timestamp = rs.getTimestamp("CFI_UPDATETIME");
                if (null != timestamp) {
                    info.setUpdateTime(new Date(timestamp.getTime()));
                }
                result.add(info);
            }
        });
        return result;
    }

    public IFormCopyInfo getByFormKey(String formKey) {
        return (IFormCopyInfo)super.getByKey((Object)formKey, this.implClz);
    }

    public List<IFormCopyInfo> getByFormSchemeKey(String formSchemeKey) {
        return super.list(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey}, this.implClz);
    }

    public List<IFormCopyInfo> getBySrcFormSchemeKey(String srcFormSchemeKey) {
        return super.list(new String[]{"srcFormSchemeKey"}, new Object[]{srcFormSchemeKey}, this.implClz);
    }

    public List<IFormCopyInfo> getByFormSchemeKey(String formSchemeKey, String srcFormSchemeKey) {
        return super.list(new String[]{"formSchemeKey", "srcFormSchemeKey"}, new Object[]{formSchemeKey, srcFormSchemeKey}, this.implClz);
    }

    public void deleteByFormKey(String formKey) throws BeanParaException, DBParaException {
        super.delete((Object)formKey);
        super.deleteBy(new String[]{"srcFormKey"}, new Object[]{formKey});
    }

    public void deleteByFormSchemeKey(String formSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey});
        super.deleteBy(new String[]{"srcFormSchemeKey"}, new Object[]{formSchemeKey});
    }

    public void saveFormCopyInfos(List<IFormCopyInfo> infos) throws BeanParaException, DBParaException {
        if (null == infos || infos.isEmpty()) {
            return;
        }
        for (int i = 0; i < infos.size(); ++i) {
            super.delete((Object)infos.get(i).getFormKey());
            super.deleteBy(new String[]{"formSchemeKey", "srcFormKey"}, (Object[])new String[]{infos.get(i).getFormSchemeKey(), infos.get(i).getSrcFormKey()});
        }
        super.insert(infos.toArray());
    }
}


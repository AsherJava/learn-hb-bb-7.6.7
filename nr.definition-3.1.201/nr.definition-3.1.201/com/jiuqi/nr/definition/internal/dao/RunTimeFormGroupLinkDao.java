/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormGroupLinkDao
extends BaseDao {
    private static String ATTR_GROUP = "groupKey";
    private static String ATTR_FORMKEY = "formKey";
    private Class<RunTimeFormGroupLink> implClass = RunTimeFormGroupLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void deleteLink(RunTimeFormGroupLink define) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP, ATTR_FORMKEY}, new Object[]{define.getGroupKey(), define.getFormKey()});
    }

    public List<RunTimeFormGroupLink> getFormGroupLinksByFormId(String formKey) {
        return this.list(new String[]{ATTR_FORMKEY}, new Object[]{formKey}, this.implClass);
    }

    public List<RunTimeFormGroupLink> getFormGroupLinksByFormGroupId(String formGroupKey) {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{formGroupKey}, this.implClass);
    }

    public List<RunTimeFormGroupLink> getFormGroupLinksByFormScheme(String formSchemeKey) {
        if (formSchemeKey == null) {
            throw new IllegalArgumentException("'formSchemeKey' must not be null.");
        }
        String sql = "select t_grouplink.* from NR_PARAM_FORMGROUPLINK t_grouplink inner join NR_PARAM_FORMGROUP t_group on t_grouplink.FGL_GROUP_KEY=t_group.FG_KEY where t_group.FG_FORM_SCHEME_KEY=?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeFormGroupLink formGroupLink = new RunTimeFormGroupLink();
            this.readRecord(rs, formGroupLink);
            return formGroupLink;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formSchemeKey.toString()});
    }

    public RunTimeFormGroupLink queryFormGroupLink(String formKey, String formGroupKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMKEY, ATTR_GROUP}, new Object[]{formKey, formGroupKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (RunTimeFormGroupLink)defines.get(0);
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DesignFormGroupLinkDao
extends BaseDao {
    private static String ATTR_GROUP = "groupKey";
    private static String ATTR_FORMKEY = "formKey";
    private static String FIELD_GROUP = "fgl_group_key";
    private static String FIELD_FORM = "fgl_form_key";
    private static String FIELD_ORDER = "fgl_form_order";
    private static String TABLE_NAME = "nr_param_formgrouplink_des";
    private Class<DesignFormGroupLink> implClass = DesignFormGroupLink.class;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void deleteLink(DesignFormGroupLink define) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP, ATTR_FORMKEY}, new Object[]{define.getGroupKey(), define.getFormKey()});
    }

    public int update(Object obj, String[] fieldNames, Object[] args) throws BeanParaException, DBParaException {
        return super.update(obj, fieldNames, args);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String formKey) throws Exception {
        return this.list(new String[]{ATTR_FORMKEY}, new Object[]{formKey}, this.implClass);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String formGroupKey) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{formGroupKey}, this.implClass);
    }

    public DesignFormGroupLink queryDesignFormGroupLink(String formKey, String formGroupKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMKEY, ATTR_GROUP}, new Object[]{formKey, formGroupKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormGroupLink)defines.get(0);
        }
        return null;
    }

    public void updateLink(DesignFormGroupLink define) throws BeanParaException, DBParaException {
        this.update(define, new String[]{ATTR_GROUP, ATTR_FORMKEY}, new Object[]{define.getGroupKey(), define.getFormKey()});
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroups(List<String> args) {
        if (CollectionUtils.isEmpty(args)) {
            return Collections.emptyList();
        }
        String sql = String.format("SELECT %s, %s, %s from %s where %s in (:ids)", FIELD_GROUP, FIELD_FORM, FIELD_ORDER, TABLE_NAME, FIELD_GROUP);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        ArrayList<DesignFormGroupLink> groupLinks = new ArrayList<DesignFormGroupLink>();
        int number = 1000;
        int limit = (args.size() + number - 1) / number;
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List subIds = args.stream().skip(i * number).limit(number).collect(Collectors.toList());
            parameterSource.addValue("ids", subIds);
            groupLinks.addAll(this.executeInSql(sql, parameterSource));
        });
        return groupLinks;
    }

    private List<DesignFormGroupLink> executeInSql(String sql, MapSqlParameterSource parameterSource) {
        return this.jdbcTemplate.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> {
            DesignFormGroupLink groupLink = new DesignFormGroupLink();
            groupLink.setGroupKey(rs.getString(FIELD_GROUP));
            groupLink.setFormKey(rs.getString(FIELD_FORM));
            groupLink.setFormOrder(rs.getString(FIELD_ORDER));
            return groupLink;
        });
    }

    public void deleteLinkByGroup(String formgroupKey) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP}, new Object[]{formgroupKey});
    }

    public void deleteLinkByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY}, new Object[]{formKey});
    }
}


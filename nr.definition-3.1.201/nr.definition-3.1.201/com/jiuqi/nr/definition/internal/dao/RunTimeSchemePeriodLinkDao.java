/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeSchemePeriodLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeSchemePeriodLinkDao
extends BaseDao {
    private String ATTR_SCHEME = "schemeKey";
    private String ATTR_PERIOD = "periodKey";
    private Class<RunTimeSchemePeriodLink> implClass = RunTimeSchemePeriodLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<SchemePeriodLinkDefine> list() {
        return super.list(this.implClass);
    }

    public List<SchemePeriodLinkDefine> queryByScheme(String scheme) {
        return this.list(new String[]{this.ATTR_SCHEME}, new Object[]{scheme}, this.implClass);
    }

    public List<SchemePeriodLinkDefine> queryByPeriod(String period) {
        return this.list(new String[]{this.ATTR_PERIOD}, new Object[]{period}, this.implClass);
    }

    public SchemePeriodLinkDefine queryLinkByPeriodAndScheme(String period, String scheme) {
        List list = this.list(new String[]{this.ATTR_PERIOD}, new Object[]{period}, this.implClass);
        if (null != list && list.size() == 1) {
            return (SchemePeriodLinkDefine)list.get(0);
        }
        return null;
    }

    public SchemePeriodLinkDefine queryLinkByPeriodAndTask(String period, String task) throws Exception {
        String sqlWhere = " sp_scheme_key in (select fc_key from nr_param_formscheme pfs where pfs.fc_task_key=?) and sp_period_key=?";
        List list = this.list(sqlWhere, new String[]{task, period}, this.implClass);
        if (null != list && list.size() == 1) {
            return (SchemePeriodLinkDefine)list.get(0);
        }
        return null;
    }

    private SchemePeriodLinkDefine queryLinkByIsDefault(String task) throws Exception {
        String sqlWhere = " sp_scheme_key in (select fc_key from nr_param_formscheme pfs where pfs.fc_task_key=?)";
        List list = this.list(sqlWhere, new String[]{task}, this.implClass);
        if (null != list && list.size() == 1) {
            return (SchemePeriodLinkDefine)list.get(0);
        }
        return null;
    }

    public List<SchemePeriodLinkDefine> queryByTask(String task) {
        String sqlWhere = " sp_scheme_key in (select fc_key from nr_param_formscheme pfs where pfs.fc_task_key=?)";
        List list = this.list(sqlWhere, new String[]{task}, this.implClass);
        return list;
    }
}


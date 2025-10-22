/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignSchemePeriodLinkDao
extends BaseDao {
    private String ATTR_SCHEME = "schemeKey";
    private String ATTR_PERIOD = "periodKey";
    private Class<DesignSchemePeriodLink> implClass = DesignSchemePeriodLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignSchemePeriodLinkDefine> queryByScheme(String scheme) {
        return this.list(new String[]{this.ATTR_SCHEME}, new Object[]{scheme}, this.implClass);
    }

    public List<DesignSchemePeriodLinkDefine> queryByPeriod(String period) {
        return this.list(new String[]{this.ATTR_PERIOD}, new Object[]{period}, this.implClass);
    }

    public DesignSchemePeriodLinkDefine queryLinkByPeriodAndScheme(String period, String scheme) {
        List list = this.list(new String[]{this.ATTR_PERIOD}, new Object[]{period}, this.implClass);
        if (null != list && list.size() == 1) {
            return (DesignSchemePeriodLinkDefine)list.get(0);
        }
        return null;
    }

    public DesignSchemePeriodLinkDefine queryLinkByPeriodAndTask(String period, String task) throws Exception {
        String sqlWhere = " sp_scheme_key in (select fc_key from nr_param_formscheme_des pfs where pfs.fc_task_key=?) and sp_period_key=?";
        List list = this.list(sqlWhere, new String[]{task, period}, this.implClass);
        if (null != list && list.size() == 1) {
            return (DesignSchemePeriodLinkDefine)list.get(0);
        }
        return null;
    }

    private DesignSchemePeriodLinkDefine queryLinkByIsDefault(String task) throws Exception {
        return null;
    }

    public void deleteData(DesignSchemePeriodLinkDefine link) throws Exception {
        this.deleteBy(new String[]{this.ATTR_SCHEME, this.ATTR_PERIOD}, new String[]{link.getSchemeKey(), link.getPeriodKey()});
    }

    public void deleteDataByScheme(String scheme) throws Exception {
        this.deleteBy(new String[]{this.ATTR_SCHEME}, new String[]{scheme});
    }

    public void updateData(DesignSchemePeriodLinkDefine link) throws Exception {
        this.update(link, new String[]{this.ATTR_PERIOD}, new String[]{link.getPeriodKey()});
    }

    public void insertData(DesignSchemePeriodLinkDefine link) throws Exception {
        this.insert(link);
    }

    public void insertData(DesignSchemePeriodLinkDefine[] link) throws Exception {
        this.insert(link);
    }

    public List<DesignSchemePeriodLinkDefine> queryByTask(String task) {
        String sqlWhere = " sp_scheme_key in (select fc_key from nr_param_formscheme_des pfs where pfs.fc_task_key='" + task + "')";
        List list = this.list(sqlWhere, new String[0], this.implClass);
        return list;
    }
}


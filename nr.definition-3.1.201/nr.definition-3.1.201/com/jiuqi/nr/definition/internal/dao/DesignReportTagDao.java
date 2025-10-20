/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class DesignReportTagDao
extends BaseDao {
    private Class<DesignReportTagDefineImpl> implClz = DesignReportTagDefineImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public void insertTags(List<DesignReportTagDefine> reportTagDefines) throws BeanParaException, DBParaException {
        super.insert(reportTagDefines.toArray());
    }

    public int[] deleteByKeys(String[] keys) throws BeanParaException, DBParaException {
        return super.delete((Object[])keys);
    }

    public List<DesignReportTagDefine> queryByRptKey(String rptKey) {
        return super.list(new String[]{"rptKey"}, new Object[]{rptKey}, this.implClz);
    }

    public List<DesignReportTagDefine> queryByRptKeys(Set<String> rptKeys) {
        ArrayList<DesignReportTagDefine> result = new ArrayList<DesignReportTagDefine>();
        for (String rptKey : rptKeys) {
            result.addAll(super.list(new String[]{"rptKey"}, new Object[]{rptKey}, this.implClz));
        }
        return result;
    }

    public DesignReportTagDefine getByKey(String key) {
        return (DesignReportTagDefine)super.getByKey((Object)key, this.implClz);
    }

    public void deleteByRptKey(String rptKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"rptKey"}, new Object[]{rptKey});
    }

    public void deleteByRptKeys(Set<String> rptKeys) throws DBParaException {
        for (String rptKey : rptKeys) {
            super.deleteBy(new String[]{"rptKey"}, new Object[]{rptKey});
        }
    }

    public int saveTag(DesignReportTagDefine reportTagDefine) throws BeanParaException, DBParaException {
        return super.update((Object)reportTagDefine);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.interval.dao.BaseDao
 */
package com.jiuqi.nr.period.dao;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.EntityInfo;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nvwa.definition.interval.dao.BaseDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodDao
extends BaseDao {
    private Class<PeriodDefineImpl> implClass = PeriodDefineImpl.class;
    private static String CODE = "code";
    private static String KEY = "key";
    private static String TITLE = "title";
    private static String TYPE = "type";

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<PeriodDefineImpl> getPeriodList() throws JQException {
        return this.list(this.implClass);
    }

    public List<IPeriodEntity> getDefaultPeriodList() throws JQException {
        ArrayList<IPeriodEntity> list = new ArrayList<IPeriodEntity>();
        for (PeriodDefineImpl periodDefine : this.getPeriodList()) {
            if (PeriodType.CUSTOM.equals((Object)periodDefine.getType())) continue;
            list.add(periodDefine);
        }
        return list;
    }

    public List<IPeriodEntity> getCustomPeriodList() throws JQException {
        ArrayList<IPeriodEntity> list = new ArrayList<IPeriodEntity>();
        for (PeriodDefineImpl periodDefine : this.getPeriodList()) {
            if (!PeriodType.CUSTOM.equals((Object)periodDefine.getType())) continue;
            list.add(periodDefine);
        }
        return list;
    }

    public IPeriodEntity queryPeriodByKey(String key) {
        List defines = this.list(new String[]{KEY}, new Object[]{key}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (IPeriodEntity)defines.get(0);
        }
        return null;
    }

    public IPeriodEntity queryPeriodByCode(String code) {
        List defines = this.list(new String[]{CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (IPeriodEntity)defines.get(0);
        }
        return null;
    }

    public void insertDate(PeriodDefineImpl periodDefine) throws Exception {
        this.insert(periodDefine);
    }

    public void updateDate(PeriodDefineImpl periodDefine) throws Exception {
        List defines = this.list(new String[]{KEY}, new Object[]{periodDefine.getKey()}, this.implClass);
        PeriodDefineImpl pd = (PeriodDefineImpl)defines.get(0);
        pd.setTitle(periodDefine.getTitle());
        pd.setUpdateUser(periodDefine.getUpdateUser());
        pd.setUpdateTime(new Date());
        pd.setPeriodPropertyGroup(periodDefine.getPeriodPropertyGroup());
        this.update(pd, new String[]{KEY}, new Object[]{periodDefine.getKey()});
    }

    public void updateEntityInfo(PeriodDefineImpl periodDefine, EntityInfo entityInfo) throws Exception {
        List defines = this.list(new String[]{KEY}, new Object[]{periodDefine.getKey()}, this.implClass);
        PeriodDefineImpl pd = (PeriodDefineImpl)defines.get(0);
        pd.setMaxFiscalMonth(entityInfo.getMaxFiscalMonth());
        pd.setMinFiscalMonth(entityInfo.getMinFiscalMonth());
        pd.setMaxYear(entityInfo.getMaxYear());
        pd.setMinYear(entityInfo.getMinYear());
        this.update(pd, new String[]{KEY}, new Object[]{periodDefine.getKey()});
    }

    public void updateDataType(IPeriodEntity periodEntity, int dataType) throws Exception {
        List defines = this.list(new String[]{KEY}, new Object[]{periodEntity.getKey()}, this.implClass);
        PeriodDefineImpl pd = (PeriodDefineImpl)defines.get(0);
        pd.setDataType(dataType);
        this.update(pd, new String[]{KEY}, new Object[]{periodEntity.getKey()});
    }

    public void deleteData(String key) throws Exception {
        this.delete(key);
    }

    public List<IPeriodEntity> queryPeriodByPeriodType(int type) {
        List defines = this.list(new String[]{TYPE}, new Object[]{type}, this.implClass);
        if (null != defines && defines.size() > 0) {
            ArrayList<IPeriodEntity> rs = new ArrayList<IPeriodEntity>();
            defines.forEach(t -> rs.add((IPeriodEntity)t));
            return rs;
        }
        return null;
    }
}


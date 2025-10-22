/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.interval.dao.BaseDao
 */
package com.jiuqi.nr.period.dao;

import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nr.period.modal.impl.PeriodLanguageImpl;
import com.jiuqi.nvwa.definition.interval.dao.BaseDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodLanguageDao
extends BaseDao {
    private Class<PeriodLanguageImpl> implClass = PeriodLanguageImpl.class;
    private static String CODE = "code";
    private static String TITLE = "title";
    private static String ENTITY = "entity";
    private static String TYPE = "type";

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<IPeriodLanguage> queryPeriodLanguage() {
        return this.list(this.implClass);
    }

    public IPeriodLanguage queryLanguageByEntityAndCode(String entity, String code, String type) throws Exception {
        List defines = this.list(new String[]{CODE, ENTITY, TYPE}, new Object[]{code, entity, type}, this.implClass);
        if (null != defines && !defines.isEmpty()) {
            return (IPeriodLanguage)defines.get(0);
        }
        return null;
    }

    public List<IPeriodLanguage> queryLanguageByEntityAndType(String entity, String type) throws Exception {
        return this.list(new String[]{ENTITY, TYPE}, new Object[]{entity, type}, this.implClass);
    }

    public List<IPeriodLanguage> queryLanguageByEntity(String entity) throws Exception {
        return this.list(new String[]{ENTITY}, new Object[]{entity}, this.implClass);
    }

    public void insertDate(IPeriodLanguage periodLanguage) throws Exception {
        this.insert(periodLanguage);
    }

    public void insertDates(IPeriodLanguage[] periodLanguages) throws Exception {
        this.insert(periodLanguages);
    }

    public void insertOrUpdate(IPeriodLanguage periodLanguage) throws Exception {
        IPeriodLanguage iPeriodLanguage = this.queryLanguageByEntityAndCode(periodLanguage.getEntity(), periodLanguage.getCode(), periodLanguage.getType());
        if (null == iPeriodLanguage) {
            this.insert(periodLanguage);
        } else {
            this.update(periodLanguage, new String[]{ENTITY, CODE, TYPE}, new Object[]{periodLanguage.getEntity(), periodLanguage.getCode(), periodLanguage.getType()});
        }
    }

    public void deleteByEntity(String entity) throws Exception {
        this.deleteBy(new String[]{ENTITY}, new Object[]{entity});
    }

    public void deleteByEntityAndCode(String entity, String code) throws Exception {
        this.deleteBy(new String[]{ENTITY, CODE}, new Object[]{entity, code});
    }
}


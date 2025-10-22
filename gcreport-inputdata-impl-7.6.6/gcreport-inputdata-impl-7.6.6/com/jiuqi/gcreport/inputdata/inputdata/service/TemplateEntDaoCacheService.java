/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 */
package com.jiuqi.gcreport.inputdata.inputdata.service;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;

public interface TemplateEntDaoCacheService {
    public <T extends BaseEntity> EntNativeSqlDefaultDao<T> getTemplateEntDao(String var1, Class<T> var2);
}


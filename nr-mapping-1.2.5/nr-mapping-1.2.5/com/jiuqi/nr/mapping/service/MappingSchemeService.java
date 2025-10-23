/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import java.util.List;

public interface MappingSchemeService {
    public String add(MappingScheme var1) throws JQException;

    public MappingScheme getByKey(String var1);

    public MappingScheme getByCode(String var1);

    public List<MappingScheme> getMappingByTask(String var1);

    public List<MappingScheme> getMappingByTaskForm(String var1, String var2);

    public List<MappingScheme> getJIOMappingByTaskForm(String var1, String var2);

    public List<MappingScheme> getAllScheme();

    public void rename(MappingScheme var1) throws Exception;

    public void updateTime(String var1);

    public void deleteByKey(String var1);

    public void deleteMappingsByKey(String var1);

    public void batchDelete(List<String> var1);

    public void batchDeleteMappingsByKey(List<String> var1);

    public List<MappingScheme> fuzzySearch(String var1);

    public void copyMapping(String var1, String var2);
}


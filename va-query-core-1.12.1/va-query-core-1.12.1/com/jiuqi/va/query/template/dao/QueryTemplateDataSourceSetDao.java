/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;

public interface QueryTemplateDataSourceSetDao {
    public void deleteByTemplateId(String var1);

    public void save(TemplateDataSourceSetVO var1);

    public void update(TemplateDataSourceSetVO var1);

    public TemplateDataSourceSetVO getByTemplateId(String var1);
}


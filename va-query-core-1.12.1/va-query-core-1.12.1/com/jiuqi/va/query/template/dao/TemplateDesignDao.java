/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.QueryTemplateDesignVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.QueryTemplateDesignVO;

public interface TemplateDesignDao {
    public QueryTemplateDesignVO getTemplateDesignByTemplateId(String var1);

    public int updateTemplateDesign(QueryTemplate var1);

    public boolean existByID(String var1);

    public int saveTemplateDesign(QueryTemplate var1);

    public int deleteById(String var1);

    public int saveTemplateDesignData(QueryTemplateDesignVO var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.TemplateInfoVO;

public interface TemplateInfoDao {
    public TemplateInfoVO getTemplateInfo(String var1);

    public TemplateInfoVO getTemplateInfoByCode(String var1);

    public int updateTemplateInfo(TemplateInfoVO var1);

    public int saveTemplateInfo(TemplateInfoVO var1);

    public int deleteById(String var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import java.util.List;

public interface QueryTemplateInfoDao {
    public List<TemplateInfoVO> getTemplatesByGroupId(String var1);

    public Boolean hasTemplatesByGroupId(String var1);

    public void save(TemplateInfoVO var1);

    public void deleteById(String var1);

    public void update(TemplateInfoVO var1);

    public void updateByCode(TemplateInfoVO var1);

    public TemplateInfoVO getTemplatesById(String var1);

    public Boolean hasTemplatesById(String var1);

    public List<TemplateInfoVO> getAllTemplates();

    public TemplateInfoVO getTemplatesByCode(String var1);

    public List<TemplateInfoVO> getTemplatesByDataSourceCode(List<String> var1);

    public void updateGroupIdAndSortOrderByTemplateId(String var1, String var2, int var3);

    public int getMaxSortOrderByGroupId(String var1);
}


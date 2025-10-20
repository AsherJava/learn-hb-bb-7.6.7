/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import java.util.List;

@Deprecated
public interface QueryTemplateToolbarInfoDao {
    public void save(TemplateToolbarInfoVO var1);

    public void deleteByTemplateId(String var1);

    public List<TemplateToolbarInfoVO> getByTemplateId(String var1);

    public void batchSave(List<TemplateToolbarInfoVO> var1);
}


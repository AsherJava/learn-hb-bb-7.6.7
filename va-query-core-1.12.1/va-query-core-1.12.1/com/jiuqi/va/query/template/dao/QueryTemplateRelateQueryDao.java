/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.List;

@Deprecated
public interface QueryTemplateRelateQueryDao {
    public List<TemplateRelateQueryVO> getRelateQueryByTemplateId(String var1);

    public void deleteByTemplateId(String var1);

    public void batchSave(List<TemplateRelateQueryVO> var1);
}


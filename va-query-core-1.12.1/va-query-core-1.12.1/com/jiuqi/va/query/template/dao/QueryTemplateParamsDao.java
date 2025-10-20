/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.vo.TemplateParamsVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import java.util.List;

@Deprecated
public interface QueryTemplateParamsDao {
    public void deleteByTemplateId(String var1);

    public void batchSave(List<TemplateParamsVO> var1);

    public void batchUpdate(List<TemplateParamsVO> var1);

    public void save(TemplateParamsVO var1);

    public List<TemplateParamsVO> getByTemplateId(String var1);

    public List<FetchQueryFiledVO> getSimpleTemplateParamsByTemplateCode(String var1);
}


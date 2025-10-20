/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.template.dao;

import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;

@Deprecated
public interface QueryTemplateFieldSettingDao {
    public void deleteByTemplateId(String var1);

    public void batchSave(List<TemplateFieldSettingVO> var1);

    public void batchUpdate(List<TemplateFieldSettingVO> var1);

    public List<TemplateFieldSettingVO> getByTemplateId(String var1);

    public List<FetchQueryFiledVO> getSimpleTemplateFieldsByTemplateCode(String var1);
}


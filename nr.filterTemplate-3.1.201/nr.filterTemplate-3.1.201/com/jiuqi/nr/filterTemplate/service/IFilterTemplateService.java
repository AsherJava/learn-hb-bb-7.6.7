/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.service;

import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateSearchVO;
import com.jiuqi.nr.filterTemplate.web.vo.SaveTipsVO;
import java.util.List;

public interface IFilterTemplateService {
    public FilterTemplateDTO init();

    public String insert(FilterTemplateDTO var1);

    public void batchInsert(List<FilterTemplateDTO> var1);

    public void delete(String var1);

    public void batchDelete(List<String> var1);

    public FilterTemplateDTO getFilterTemplate(String var1);

    public void update(FilterTemplateDTO var1);

    public void batchUpdate(List<FilterTemplateDTO> var1);

    public List<FilterTemplateDTO> listAll();

    public FilterTemplateDTO copy(String var1);

    public SaveTipsVO saveTips(String var1);

    public List<FilterTemplateDTO> getByEntity(String var1);

    public List<FilterTemplateDTO> getByTaskRefEntity(String var1);

    public List<FilterTemplateDTO> getByFormSchemeRefEntity(String var1);

    public List<FilterTemplateDTO> getByDataLinkRefEntity(String var1);

    public List<FilterTemplateSearchVO> search(FilterTemplateSearchVO var1);
}


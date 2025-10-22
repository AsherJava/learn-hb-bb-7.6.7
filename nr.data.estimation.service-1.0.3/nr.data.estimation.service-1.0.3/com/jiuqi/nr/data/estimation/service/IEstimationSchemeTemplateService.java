/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 */
package com.jiuqi.nr.data.estimation.service;

import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import java.util.List;

public interface IEstimationSchemeTemplateService {
    public IEstimationSchemeTemplate findSchemeTemplateByKey(String var1);

    public IEstimationSchemeTemplate findSchemeTemplateByFormScheme(String var1);

    public List<IEstimationSchemeTemplate> findAllSchemeTemplate();

    public String saveEstimationSchemeTemplate(IEstimationSchemeTemplate var1);

    public String updateEstimationSchemeForms(String var1, List<String> var2);

    public boolean hasSchemeCode(String var1, String var2);
}


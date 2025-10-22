/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.dao;

import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import java.util.List;

public interface TemplateConfigDao {
    public boolean updateTemplateConfig(TemplateConfigImpl var1);

    public FTemplateConfig getTemplateConfigById(String var1);

    public FTemplateConfig getTemplateConfigByCode(String var1);

    public boolean addTemplate_old(TemplateConfigImpl var1);

    public boolean addTemplate(TemplateConfigImpl var1);

    public boolean verifyCode(TemplateConfigImpl var1);

    public boolean updateCode(TemplateConfigImpl var1);

    public List<TemplateConfigImpl> getAllTemplateConfig();

    public void deleteTemplateConfig(String var1);

    public List<TemplateConfigImpl> getMiniTemplateConfig();

    public void deleteTemplateByKind(String var1);

    public List<ExtendTemplateImpl> getExtendTemplateImpls();

    public ExtendTemplateImpl getExtendTemplateImpl(String var1);

    public boolean updateExtendTemplate(ExtendTemplateImpl var1);

    public boolean addExtendTemplate(ExtendTemplateImpl var1);

    public boolean deleteExtendTemplate(String var1);

    public boolean updateTemplateConfigByCode(TemplateConfigImpl var1);
}


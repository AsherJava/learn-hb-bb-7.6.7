/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.util.List;

public interface ITemplateConfigService {
    public boolean updateTemplateConfig(TemplateConfigImpl var1);

    public boolean updateTemplateConfigByCode(TemplateConfigImpl var1);

    public FTemplateConfig getTemplateConfigById(String var1);

    public FTemplateConfig getTemplateConfigByCode(String var1);

    public boolean addTemplate_old(TemplateConfigImpl var1);

    public boolean addTemplate(TemplateConfigImpl var1);

    public boolean verifyCode(TemplateConfigImpl var1);

    public boolean updateTemplateCode(TemplateConfigImpl var1);

    public List<TemplateConfigImpl> getAllTemplateConfig();

    public FuncExecuteConfig getTemplateConfig(FTemplateConfig var1);

    public FuncExecuteConfig getFuncExecuteConfigByCode(String var1);

    public void deleteTemplateConfig(String var1);

    public List<TemplateConfigImpl> getMiniTemplateConfig();

    public List<ExtendTemplateImpl> getExtendTemplateImpls();

    public ExtendTemplateImpl getExtendTemplateImpl(String var1);

    public boolean modifyExtendTemplate(ExtendTemplateImpl var1);

    public ReturnInfo uploadExtendTemplate(ExtendTemplateImpl var1);

    public boolean deleteExtendTemplate(String var1);
}


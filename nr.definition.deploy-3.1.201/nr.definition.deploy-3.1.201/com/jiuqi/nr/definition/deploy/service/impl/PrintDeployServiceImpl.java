/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignPrintTemplateSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateSchemeDefineDao
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.deploy.service.impl.AbstractResourceDeployService;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignPrintTemplateSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateSchemeDefineDao;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintDeployServiceImpl
extends AbstractResourceDeployService {
    @Autowired
    private DesignPrintTemplateSchemeDefineDao designPrintSchemeDao;
    @Autowired
    private RunTimePrintTemplateSchemeDefineDao runTimePrintSchemeDao;

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.PRINT_TEMPLATE;
    }

    private List<PrintTemplateSchemeDefine> getSchemes(String formSchemeKey) {
        HashMap<String, PrintTemplateSchemeDefine> defines = new HashMap<String, PrintTemplateSchemeDefine>();
        try {
            List schemeDefines = this.runTimePrintSchemeDao.queryPrintSchemeDefineByScheme(formSchemeKey);
            for (PrintTemplateSchemeDefine define : schemeDefines) {
                defines.put(define.getKey(), define);
            }
            List designSchemeDefines = this.designPrintSchemeDao.queryPrintSchemeDefineByScheme(formSchemeKey);
            for (PrintTemplateSchemeDefine define : designSchemeDefines) {
                defines.put(define.getKey(), define);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u67e5\u8be2\u6253\u5370\u65b9\u6848\u5f02\u5e38", e);
            throw new ParamDeployException("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u67e5\u8be2\u6253\u5370\u65b9\u6848\u5f02\u5e38");
        }
        return defines.values().stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
    }

    @Override
    public void deploy(ParamDeployContext context, String formSchemeKey) {
        List<PrintTemplateSchemeDefine> schemes = this.getSchemes(formSchemeKey);
        for (PrintTemplateSchemeDefine scheme : schemes) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u6253\u5370\u65b9\u6848[{}]", (Object)scheme.getTitle());
            context.getProgressConsumer().accept(this.getType(), "\u53d1\u5e03\u6253\u5370\u65b9\u6848[" + scheme.getTitle() + "]");
            super.deploy(scheme.getKey());
            context.getDeployItems().add(new ParamDeployItem(this.getType(), scheme.getKey()));
        }
    }

    @Override
    public void rollback(ParamDeployContext context, String formSchemeKey) {
        List<PrintTemplateSchemeDefine> schemes = this.getSchemes(formSchemeKey);
        for (PrintTemplateSchemeDefine scheme : schemes) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u6253\u5370\u65b9\u6848[{}]", (Object)scheme.getTitle());
            context.getProgressConsumer().accept(this.getType(), "\u56de\u6eda\u6253\u5370\u65b9\u6848[" + scheme.getTitle() + "]");
            super.rollback(scheme.getKey());
            context.getDeployItems().add(new ParamDeployItem(this.getType(), scheme.getKey()));
        }
    }
}


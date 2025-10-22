/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.deploy.service.impl.AbstractResourceDeployService;
import org.springframework.stereotype.Service;

@Service
public class ReportDeployServiceImpl
extends AbstractResourceDeployService {
    @Override
    public ParamResourceType getType() {
        return ParamResourceType.REPORT_TEMPLATE;
    }

    @Override
    public void deploy(ParamDeployContext context, String formSchemeKey) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u544a\u6a21\u677f");
        context.getProgressConsumer().accept(this.getType(), "\u53d1\u5e03\u62a5\u544a\u6a21\u677f");
        super.deploy(formSchemeKey);
        context.getDeployItems().add(new ParamDeployItem(this.getType(), formSchemeKey));
    }

    @Override
    public void rollback(ParamDeployContext context, String formSchemeKey) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u62a5\u544a\u6a21\u677f");
        context.getProgressConsumer().accept(this.getType(), "\u56de\u6eda\u62a5\u544a\u6a21\u677f");
        super.rollback(formSchemeKey);
        context.getDeployItems().add(new ParamDeployItem(this.getType(), formSchemeKey));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.paramlanguage.service.I18nDeployService
 */
package com.jiuqi.nr.definition.deploy.extend.nr;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.IPartialDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.nr.FormulaVariableDeployService;
import com.jiuqi.nr.definition.paramlanguage.service.I18nDeployService;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OtherParamDeployService
implements IParamDeployExtendService,
IPartialDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaVariableDeployService.class);
    @Autowired
    private I18nDeployService i18nDeployService;

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORM;
    }

    @Override
    public void afterDeploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.i18nDeployService.I18nDeployByFormScheme(schemeKey, false);
        } else {
            for (String sourceKey : sourceKeys) {
                this.i18nDeployService.I18nDeployByForm(sourceKey, false);
            }
        }
    }

    @Override
    public void afterDeploy(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u53d1\u5e03\u591a\u8bed\u8a00\u53c2\u6570");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u591a\u8bed\u8a00\u53c2\u6570");
        this.i18nDeployService.I18nDeployByFormScheme(formSchemeKey, false);
    }
}


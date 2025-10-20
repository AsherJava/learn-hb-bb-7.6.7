/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.DataModelTemplateDTO
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  com.jiuqi.va.feign.client.DataModelTemplateClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.datamodel.service.join;

import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.DataModelTemplateClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class VaDataModelTemplateClientImpl
implements DataModelTemplateClient {
    private VaDataModelTemplateService vaDataModelTemplateService;

    public DataModelTemplateEntity getDataModelTemplate(DataModelTemplateDTO param) {
        if (this.vaDataModelTemplateService == null) {
            this.vaDataModelTemplateService = (VaDataModelTemplateService)ApplicationContextRegister.getBean(VaDataModelTemplateService.class);
        }
        return this.vaDataModelTemplateService.getTemplateEntity(param);
    }
}


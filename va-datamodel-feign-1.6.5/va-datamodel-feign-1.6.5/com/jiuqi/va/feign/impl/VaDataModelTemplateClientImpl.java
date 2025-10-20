/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.http.ResponseEntity
 */
package com.jiuqi.va.feign.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.DataModelTemplateClient;
import com.jiuqi.va.feign.remote.DataModelTemplateFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelTemplateClientImpl
implements DataModelTemplateClient {
    @Autowired
    private DataModelTemplateFeign dataModelTemplateFeign;

    @Override
    public DataModelTemplateEntity getDataModelTemplate(DataModelTemplateDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelTemplateFeign.getDataModelTemplate(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (DataModelTemplateEntity)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), DataModelTemplateEntity.class);
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.annotation.EncryptResponse
 *  com.jiuqi.dc.mappingscheme.client.DataSchemeInitClient
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO
 *  com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.mappingscheme.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.annotation.EncryptResponse;
import com.jiuqi.dc.mappingscheme.client.DataSchemeInitClient;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo;
import com.jiuqi.dc.mappingscheme.impl.annotation.DataSchemeCheck;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeInitService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSchemeInitController
implements DataSchemeInitClient {
    @Autowired
    private DataSchemeInitService dataSchemeInitService;

    @DataSchemeCheck
    public BusinessResponseEntity<SchemeDefaultDataVO> getDefaultSchemeData(@RequestBody DataSchemeDTO dataScheme) {
        return BusinessResponseEntity.ok((Object)this.dataSchemeInitService.getDefaultSchemeData(dataScheme));
    }

    @DataSchemeCheck
    @EncryptResponse
    public BusinessResponseEntity<SchemeDefaultVO> getDefaultData(@RequestBody DataSchemeDTO dataScheme) {
        return BusinessResponseEntity.ok((Object)this.dataSchemeInitService.getDefaultData(dataScheme));
    }

    public BusinessResponseEntity<Set<SchemeDimVo>> getDimList(String pluginType) {
        return BusinessResponseEntity.ok(this.dataSchemeInitService.getDimList(pluginType));
    }
}


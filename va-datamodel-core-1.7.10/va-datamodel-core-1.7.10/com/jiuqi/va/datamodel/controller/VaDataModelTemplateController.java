/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.extend.DataModelTemplateDTO
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.datamodel.controller;

import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.extend.DataModelTemplateDTO;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaDataModelTemplateController")
@RequestMapping(value={"/dataModel/template/binary"})
public class VaDataModelTemplateController {
    @Autowired
    private VaDataModelTemplateService vaDataModelTemplateService;

    @PostMapping(value={"/get"})
    Object getDataModelTemplate(@RequestBody byte[] binaryData) {
        DataModelTemplateDTO param = (DataModelTemplateDTO)JSONUtil.parseObject((byte[])binaryData, DataModelTemplateDTO.class);
        DataModelTemplateEntity rs = this.vaDataModelTemplateService.getTemplateEntity(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}


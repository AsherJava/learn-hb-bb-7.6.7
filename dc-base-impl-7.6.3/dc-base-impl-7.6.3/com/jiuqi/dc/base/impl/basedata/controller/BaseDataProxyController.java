/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.basedata.BaseDataProxyClient
 *  com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO
 *  com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.basedata.controller;

import com.jiuqi.dc.base.client.basedata.BaseDataProxyClient;
import com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO;
import com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO;
import com.jiuqi.dc.base.impl.basedata.service.BaseDataProxyService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseDataProxyController
implements BaseDataProxyClient {
    @Autowired
    private BaseDataProxyService service;

    public BaseDataDefineProxyVO getDefine(@RequestBody BaseDataDefineDTO defineDto) {
        return this.service.getDefine(defineDto);
    }

    public PageVO<BaseDataDO> list(@RequestBody BaseDataProxyDTO dto) {
        return this.service.list(dto);
    }
}


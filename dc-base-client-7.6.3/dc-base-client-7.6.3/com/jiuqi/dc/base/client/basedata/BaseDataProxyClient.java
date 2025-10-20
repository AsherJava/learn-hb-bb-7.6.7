/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.base.client.basedata;

import com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO;
import com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseDataProxyClient {
    public static final String BASEDATA_DEFINE_API_BASE_PATH = "/api/datacenter/v1/base/basedata/define";
    public static final String BASEDATA_API_BASE_PATH = "/api/datacenter/v1/base/basedata/data";

    @PostMapping(value={"/api/datacenter/v1/base/basedata/define/get"})
    public BaseDataDefineProxyVO getDefine(@RequestBody BaseDataDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/basedata/data/list"})
    public PageVO<BaseDataDO> list(@RequestBody BaseDataProxyDTO var1);
}


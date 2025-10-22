/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.client;

import com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.nr.entity.adapter.impl.basedata.client.BaseDataAdapterClient", name="NRBASEDATAADAPTER", url="${feignClient.basedataMgr.url}")
@RefreshScope
public interface BaseDataAdapterClient {
    @PostMapping(value={"/baseData/version/list"})
    public PageVO<BaseDataVersionDO> list(@RequestBody BaseDataVersionDTO var1);

    @PostMapping(value={"/baseData/data/recover"})
    public R recover(@RequestBody BaseDataDTO var1);

    @PostMapping(value={"/baseData/auth/mgr/detail/update"})
    public R updateDetail(@RequestBody List<BaseDataAuthDO> var1);
}


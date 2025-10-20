/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrReceiveSettingClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrReceiveSettingClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/clbr/todosetting/";

    @PostMapping(value={"/api/gcreport/v1/clbr/todosetting/add"})
    public BusinessResponseEntity<Boolean> save(@RequestBody ClbrReceiveSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/todosetting/query"})
    public BusinessResponseEntity<PageInfo<ClbrReceiveSettingVO>> query(@RequestBody ClbrReceiveSettingCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/todosetting/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/todosetting/edit"})
    public BusinessResponseEntity<Boolean> edit(@RequestBody ClbrReceiveSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/todosetting/queryForUser"})
    public BusinessResponseEntity<List<ClbrReceiveSettingVO>> queryForUser(@RequestBody ClbrReceiveSettingCondition var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.bde.bizmodel.client;

import com.jiuqi.bde.bizmodel.client.dto.BizModelCategoryDTO;
import com.jiuqi.bde.bizmodel.client.util.BizModelTreeNode;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.bde.fetch.client.BizModelManageClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface BizModelManageClient {
    public static final String FETCH_API_PREFIX = "/api/bde/v1/bizModelManage";

    @GetMapping(value={"/api/bde/v1/bizModelManage/listCategory"})
    public BusinessResponseEntity<List<BizModelCategoryDTO>> listCategory();

    @GetMapping(value={"/api/bde/v1/bizModelManage/listAllCategoryAppInfo"})
    public BusinessResponseEntity<List<BizModelCategoryDTO>> listAllCategoryAppInfo();

    @GetMapping(value={"/api/bde/v1/bizModelManage/getBizModelTree"})
    public BusinessResponseEntity<List<BizModelTreeNode>> getBizModelTree(@RequestParam(value="category") String var1);

    @GetMapping(value={"/api/bde/v1/bizModelManage/list"})
    public BusinessResponseEntity<String> list(@RequestParam(value="category") String var1);

    @PostMapping(value={"/api/bde/v1/bizModelManage/save/{type}"})
    public BusinessResponseEntity<Object> save(@PathVariable(value="type") String var1, @RequestParam(value="category") String var2, @RequestBody String var3);

    @PostMapping(value={"/api/bde/v1/bizModelManage/start/{id}"})
    public BusinessResponseEntity<Object> start(@PathVariable(value="id") String var1, @RequestParam(value="category") String var2);

    @PostMapping(value={"/api/bde/v1/bizModelManage/stop/{id}"})
    public BusinessResponseEntity<Object> stop(@PathVariable(value="id") String var1, @RequestParam(value="category") String var2);

    @GetMapping(value={"/api/bde/v1/bizModelManage/exchangeOrdinal"})
    public BusinessResponseEntity<Object> exchangeOrdinal(@RequestParam(value="category") String var1, @RequestParam(value="srcId") String var2, @RequestParam(value="targetId") String var3);

    @GetMapping(value={"/api/bde/v1/bizModelManage/getFetchTypeByBizModelCode/{bizModelCode}"})
    public BusinessResponseEntity<String> getFetchTypeByBizModelCode(@PathVariable(value="bizModelCode") String var1, @RequestParam(value="category") String var2);

    @GetMapping(value={"/api/bde/v1/bizModelManage/getDimensionByBizModelCode/{bizModelCode}"})
    public BusinessResponseEntity<String> getDimensionByBizModelCode(@PathVariable(value="bizModelCode") String var1, @RequestParam(value="category") String var2);
}


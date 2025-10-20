/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.va.query.tree.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(primary=false, contextId="queryTreeClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface QueryTreeClient {
    public static final String QUERY_TREE_BASE_API = "/api/datacenter/v1/userDefined/tree";

    @GetMapping(value={"/api/datacenter/v1/userDefined/tree/init"})
    public BusinessResponseEntity<List<MenuTreeVO>> treeInit();

    @GetMapping(value={"/api/datacenter/v1/userDefined/tree/list/init"})
    public List<TemplateInfoVO> listInit();
}


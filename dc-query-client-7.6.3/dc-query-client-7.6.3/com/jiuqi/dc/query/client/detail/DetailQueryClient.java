/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.query.client.detail;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.query.client.balance.vo.QueryDimVO;
import com.jiuqi.dc.query.client.detail.vo.DetailQueryVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DetailQueryClient {
    public static final String BALANCE_QUERY_PATH = "/api/datacenter/v1/query/detail";

    @PostMapping(value={"/api/datacenter/v1/query/detail/list"})
    public BusinessResponseEntity<PageVO<LinkedHashMap<String, Object>>> getDetailQueryList(@RequestBody DetailQueryVO var1);

    @GetMapping(value={"/api/datacenter/v1/query/detail/voucher/dim/list"})
    public BusinessResponseEntity<List<QueryDimVO>> getVoucherDimList();

    @GetMapping(value={"/api/datacenter/v1/query/detail/cf/dim/list"})
    public BusinessResponseEntity<List<QueryDimVO>> getCfDimList();

    @PostMapping(value={"/api/datacenter/v1/query/detail/cf/list"})
    public BusinessResponseEntity<PageVO<LinkedHashMap<String, Object>>> getCfDetailQueryList(@RequestBody DetailQueryVO var1);
}


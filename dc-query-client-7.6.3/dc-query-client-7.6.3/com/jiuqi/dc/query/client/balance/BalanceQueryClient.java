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
package com.jiuqi.dc.query.client.balance;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.query.client.balance.vo.BalanceQueryVO;
import com.jiuqi.dc.query.client.balance.vo.QueryDimVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BalanceQueryClient {
    public static final String BALANCE_QUERY_PATH = "/api/datacenter/v1/query/balance";

    @GetMapping(value={"/api/datacenter/v1/query/balance/minYear"})
    public BusinessResponseEntity<Integer> getMinYear();

    @GetMapping(value={"/api/datacenter/v1/query/balance/dim/list"})
    public BusinessResponseEntity<List<QueryDimVO>> getDimList();

    @PostMapping(value={"/api/datacenter/v1/query/balance/list"})
    public BusinessResponseEntity<PageVO<Map<Object, Object>>> getBalanceQueryList(@RequestBody BalanceQueryVO var1);

    @PostMapping(value={"/api/datacenter/v1/query/balance/subj/dim/list"})
    public BusinessResponseEntity<List<String>> getSubjDimList(@RequestBody BalanceQueryVO var1);
}


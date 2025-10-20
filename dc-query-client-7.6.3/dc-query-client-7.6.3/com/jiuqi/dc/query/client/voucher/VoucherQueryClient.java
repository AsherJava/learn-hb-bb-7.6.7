/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.query.client.voucher;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.query.client.voucher.vo.VoucherQueryVO;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoucherQueryClient {
    public static final String VOUCHER_QUERY_PATH = "/api/datacenter/v1/query/voucher";

    @PostMapping(value={"/api/datacenter/v1/query/voucher/list"})
    public BusinessResponseEntity<Map<String, Object>> getVoucherQueryList(@RequestBody VoucherQueryVO var1);

    @PostMapping(value={"/api/datacenter/v1/query/voucher/cf/list"})
    public BusinessResponseEntity<Map<String, Object>> getCfVoucherQueryList(@RequestBody VoucherQueryVO var1);
}


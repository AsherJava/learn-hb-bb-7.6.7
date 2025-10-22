/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.bill.extract.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.extract.client.dto.BillExtractDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractLogVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BillExtractClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/extract"})
    public BusinessResponseEntity<BillExtractLogVO> doExtract(@RequestBody BillExtractDTO var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.extract.client.BillExtractClient
 *  com.jiuqi.gcreport.bde.bill.extract.client.dto.BillExtractDTO
 *  com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractLogVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.extract.client.BillExtractClient;
import com.jiuqi.gcreport.bde.bill.extract.client.dto.BillExtractDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractLogVO;
import com.jiuqi.gcreport.bde.bill.extract.impl.service.BillExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillExtractController
implements BillExtractClient {
    @Autowired
    private BillExtractService extractService;

    public BusinessResponseEntity<BillExtractLogVO> doExtract(@RequestBody BillExtractDTO dto) {
        return BusinessResponseEntity.ok((Object)this.extractService.doExtract(dto));
    }
}


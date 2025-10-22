/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.clbr.api.ClbrBillExtendClient
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.clbr.api.ClbrBillExtendClient;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.gcreport.clbr.service.ClbrBillExtendService;
import com.jiuqi.va.domain.common.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClbrBillExtendController
implements ClbrBillExtendClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClbrBillExtendController.class);
    final ClbrBillExtendService clbrBillExtendService;

    public ClbrBillExtendController(ClbrBillExtendService clbrBillExtendService) {
        this.clbrBillExtendService = clbrBillExtendService;
    }

    public BusinessResponseEntity<ClbrBillPushResultDTO> partCancelClbrBill(ClbrBillCancelMsgDTO billCancelMsgDTO) {
        LOGGER.info("\u90e8\u5206\u53d6\u6d88\u534f\u540c\u63a5\u53e3\u83b7\u53d6\u6570\u636e\uff1a{}", (Object)JSONUtil.toJSONString((Object)billCancelMsgDTO));
        try {
            this.clbrBillExtendService.partCancelClbrBills(billCancelMsgDTO);
            return BusinessResponseEntity.ok();
        }
        catch (Exception e) {
            LOGGER.error("\u90e8\u5206\u53d6\u6d88\u534f\u540c\u5931\u8d25", e);
            return BusinessResponseEntity.error((String)"500", (String)("\u90e8\u5206\u53d6\u6d88\u534f\u540c\u5931\u8d25\uff1a" + e.getMessage()));
        }
    }
}


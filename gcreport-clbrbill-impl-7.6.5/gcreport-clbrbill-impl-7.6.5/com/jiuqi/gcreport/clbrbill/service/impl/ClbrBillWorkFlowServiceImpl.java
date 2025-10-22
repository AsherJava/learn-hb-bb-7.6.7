/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 */
package com.jiuqi.gcreport.clbrbill.service.impl;

import com.jiuqi.gcreport.clbrbill.service.ClbrBillWorkFlowService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClbrBillWorkFlowServiceImpl
implements ClbrBillWorkFlowService {
    private static final Logger logger = LoggerFactory.getLogger(ClbrBillWorkFlowServiceImpl.class);
    @Autowired
    ActionManager actionManager;

    @Override
    public void commitBill(BillModelImpl billModel) {
        Action commitAction = (Action)this.actionManager.get("bill-commit");
        ActionRequest request = new ActionRequest();
        HashMap modelParams = new HashMap();
        request.setParams(modelParams);
        ActionResponse response = new ActionResponse();
        billModel.executeAction(commitAction, request, response);
        if (!response.isSuccess()) {
            logger.info("\u63d0\u4ea4\u5de5\u4f5c\u6d41\u5931\u8d25\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)response));
        }
    }
}


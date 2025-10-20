/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller.auto.task;

import com.jiuqi.va.bill.service.auto.task.BillAutoTaskService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillAutoTaskController {
    private static final Logger log = LoggerFactory.getLogger(BillAutoTaskController.class);
    @Autowired
    private BillAutoTaskService billAutoTaskService;

    @PostMapping(value={"/bill/business/message/notice/param/handle"})
    public R handleMessageNoticeParam(@RequestBody MessageNoticeParam messageNoticeParam) {
        R r = R.ok();
        try {
            r.put("data", (Object)this.billAutoTaskService.handleMessageNoticeParam(messageNoticeParam));
        }
        catch (Exception e) {
            log.error("\u5904\u7406\u4e1a\u52a1\u6d88\u606f\u901a\u77e5\u53c2\u6570\u5931\u8d25\uff0c", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.batchreject.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.batchupload.bean.TaskDefineData;
import com.jiuqi.nr.batchupload.exception.UploadException;
import com.jiuqi.nr.batchupload.service.IBatchUploadService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/BatchReject/"})
@Api(tags={"\u6279\u91cf\u9000\u56de\u4fe1\u606f"})
public class BatchRejectController {
    @Autowired
    public IBatchUploadService batchUploadService;

    @GetMapping(value={"getApprovalTask"})
    public List<TaskDefineData> getUploadTask() throws JQException {
        try {
            return this.batchUploadService.getApprovalTask();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)UploadException.ERROR, "\u672a\u67e5\u5230\u6570\u636e");
        }
    }
}


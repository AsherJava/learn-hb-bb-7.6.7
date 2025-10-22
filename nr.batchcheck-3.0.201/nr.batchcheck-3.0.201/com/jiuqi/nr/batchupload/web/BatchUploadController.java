/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.batchupload.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.batchupload.bean.TaskDefineData;
import com.jiuqi.nr.batchupload.bean.UploadResult;
import com.jiuqi.nr.batchupload.bean.UploadloadParam;
import com.jiuqi.nr.batchupload.exception.UploadException;
import com.jiuqi.nr.batchupload.service.IBatchUploadService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@JQRestController
@RequestMapping(value={"/api/v1/BatchUpload/"})
@Api(tags={"\u6279\u91cf\u4e0a\u62a5\u4fe1\u606f"})
public class BatchUploadController {
    @Autowired
    public IBatchUploadService batchUploadService;

    @GetMapping(value={"getUploadTask"})
    public List<TaskDefineData> getUploadTask() throws JQException {
        try {
            return this.batchUploadService.getUploadTask();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)UploadException.ERROR, "\u672a\u67e5\u5230\u6570\u636e");
        }
    }

    @PostMapping(value={"getUploadEntities"})
    @ResponseBody
    public List<UploadResult> getUploadEntities(@RequestBody UploadloadParam uploadloadParam) throws JQException {
        try {
            return this.batchUploadService.getUploadEntities(uploadloadParam);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)UploadException.ERROR, "\u672a\u67e5\u5230\u6570\u636e");
        }
    }
}


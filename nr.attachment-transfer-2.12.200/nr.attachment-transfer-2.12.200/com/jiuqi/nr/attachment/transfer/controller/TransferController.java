/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.attachment.transfer.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.attachment.transfer.service.IFileDownLoadService;
import com.jiuqi.nr.attachment.transfer.vo.DownLoadInfo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/attachment-transfer/download"})
public class TransferController {
    @Autowired
    private IFileDownLoadService fileDownLoadService;

    @GetMapping(value={"/one/{key}"})
    public void downloadOne(@PathVariable String key, @RequestHeader(required=false, value="Range") String range, HttpServletResponse res) {
        this.fileDownLoadService.downLoadOne(key, range, res);
    }

    @GetMapping(value={"/batch/{number}"})
    public List<DownLoadInfo> batchDownload(@PathVariable int number) {
        return this.fileDownLoadService.batchDownLoad(number);
    }
}


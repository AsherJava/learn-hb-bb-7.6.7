/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  io.swagger.annotations.Api
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.param.task.controller;

import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.task.service.IParamIOExecuteService;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/nrdx/paramio/"})
@Api(tags={"NRDX\u53c2\u6570\u5bfc\u5165\u5bfc\u51faAPI"})
public class ParamIOController {
    private static final Logger log = LoggerFactory.getLogger(ParamIOController.class);
    @Autowired
    IParamIOExecuteService paramIOExecuteService;

    @PostMapping(value={"export"})
    public void exportData(@RequestBody NrdxTransferContext transferContext, HttpServletResponse response) throws Exception {
        this.paramIOExecuteService.exportNRDXParam(transferContext, response);
    }

    @PostMapping(value={"upload"})
    String upload(@RequestBody MultipartFile file) throws Exception {
        return this.paramIOExecuteService.uploadNRDXParam(file);
    }

    @PostMapping(value={"import"})
    public void exportData(@RequestBody IParamVO paramVO) throws Exception {
        this.paramIOExecuteService.importNRDXParam(paramVO);
    }
}


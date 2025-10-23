/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.transmission.data.exception.SchemeExportException;
import com.jiuqi.nr.transmission.data.exception.SchemeImportException;
import com.jiuqi.nr.transmission.data.intf.InstanceResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;
import com.jiuqi.nr.transmission.data.service.IProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/sync/scheme/sync/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u540c\u6b65\u670d\u52a1"})
public class ProcessController {
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
    @Autowired
    private IProcessService processService;

    @ApiOperation(value="\u67e5\u8be2\u6267\u884c\u8fdb\u5ea6")
    @GetMapping(value={"process/{executeKey}/{type}"})
    public TransmissionMonitorInfo loadProcess(@PathVariable String executeKey, @PathVariable int type) throws JQException {
        TransmissionMonitorInfo transmissionMonitorInfo = new TransmissionMonitorInfo();
        try {
            transmissionMonitorInfo = this.processService.queryProcess(executeKey, type);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new JQException((ErrorEnum)SchemeImportException.IMPORT_PROCESS_ERROR, e.getMessage());
        }
        return transmissionMonitorInfo;
    }

    @ApiOperation(value="\u67e5\u8be2\u8fdc\u7aef\u5373\u65f6\u4efb\u52a1\u88c5\u5165\u5b9e\u4f8b")
    @PostMapping(value={"get_instance"})
    public InstanceResult getInstance(@RequestParam(value="instanceId", required=false) String instanceId) throws JQException {
        try {
            InstanceResult instanceResult = new InstanceResult();
            String intsance = this.processService.getInstance(instanceId);
            instanceResult.setIntsance(intsance);
            return instanceResult;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeExportException.EXPORT_NULL_ERROR, e.getMessage());
        }
    }
}


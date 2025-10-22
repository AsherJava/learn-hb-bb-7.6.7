/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.BatchSecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.params.output.SecretUploadParam
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.BatchSecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.params.output.SecretUploadParam;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/secretLevel"})
@Api(tags={"\u5bc6\u7ea7rest\u63a5\u53e3"})
public class SecretLevelController {
    private static final Logger logger = LoggerFactory.getLogger(SecretLevelController.class);
    @Autowired
    private ISecretLevelService secretLevelService;

    @PostMapping(value={"/items"})
    @NRContextBuild
    @ApiOperation(value="\u83b7\u53d6\u5bc6\u7ea7\u5217\u8868")
    @ResponseBody
    public List<SecretLevelItem> getSecretLevelItems() {
        return this.secretLevelService.getSecretLevelItems();
    }

    @PostMapping(value={"/query"})
    @NRContextBuild
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u73af\u5883\u5bc6\u7ea7")
    @ResponseBody
    public SecretLevelInfo querySecretLevel(@Valid @RequestBody JtableContext jtableContext) {
        SecretLevelInfo secretLevel = this.secretLevelService.getSecretLevel(jtableContext);
        return secretLevel;
    }

    @PostMapping(value={"/save"})
    @NRContextBuild
    @ApiOperation(value="\u4fdd\u5b58\u5f53\u524d\u73af\u5883\u5bc6\u7ea7")
    @ResponseBody
    public ReturnInfo saveSecretLevel(@Valid @RequestBody SecretLevelInfo sercetLevelInfo) {
        this.secretLevelService.setSecretLevel(sercetLevelInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @PostMapping(value={"/batchquery"})
    @NRContextBuild
    @ApiOperation(value="\u6279\u91cf\u83b7\u53d6\u5bc6\u7ea7")
    @ResponseBody
    public List<SecretLevelInfo> querySecretLevels(@Valid @RequestBody JtableContext jtableContext) {
        List secretLevels = this.secretLevelService.querySecretLevels(jtableContext);
        return secretLevels;
    }

    @PostMapping(value={"/batchsave"})
    @NRContextBuild
    @ApiOperation(value="\u6279\u91cf\u4fdd\u5b58\u5bc6\u7ea7")
    @ResponseBody
    public ReturnInfo batchSaveBatchSecretLevel(@Valid @RequestBody BatchSecretLevelInfo batchSecretLevelInfo) {
        this.secretLevelService.batchSaveBatchSecretLevel(batchSecretLevelInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @PostMapping(value={"/extractpre"})
    @NRContextBuild
    @ApiOperation(value="\u63d0\u53d6\u4e0a\u671f\u5bc6\u7ea7")
    @ResponseBody
    public ReturnInfo extractPrePeriodSecretLevel(@Valid @RequestBody JtableContext jtableContext) {
        this.secretLevelService.extractPrePeriodSecretLevel(jtableContext);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @PostMapping(value={"/export"})
    @NRContextBuild
    @ApiOperation(value="\u5bfc\u51fa\u5bc6\u7ea7")
    @ResponseBody
    public void exportSecretLevel(@Valid @RequestBody JtableContext jtableContext, HttpServletResponse response) {
        this.secretLevelService.exportSecretLevel(jtableContext, response);
    }

    @PostMapping(value={"/import"})
    @NRContextBuild
    @ApiOperation(value="\u5bfc\u5165\u5bc6\u7ea7")
    @ResponseBody
    public ReturnInfo importSecretLevel(@Valid @RequestBody SecretUploadParam secretUploadParam) {
        try {
            ReturnInfo returnInfo = this.secretLevelService.importSecretLevel(secretUploadParam.getFileKey(), secretUploadParam.getJtableContext());
            return returnInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setMessage("error");
            return returnInfo;
        }
    }

    @PostMapping(value={"/secretLevelEnable"})
    @ApiOperation(value="\u5224\u65ad\u4efb\u52a1\u662f\u5426\u542f\u7528\u5bc6\u7ea7")
    @NRContextBuild
    @ResponseBody
    public ReturnInfo secretLevelEnable(@RequestBody List<String> taskKeys) {
        ArrayList<String> secretTaskList = new ArrayList<String>();
        ReturnInfo returnInfo = new ReturnInfo();
        for (String taskKey : taskKeys) {
            boolean securityLevelEnabled = this.secretLevelService.secretLevelEnable(taskKey);
            if (!securityLevelEnabled) continue;
            secretTaskList.add(taskKey);
        }
        returnInfo.setTaskKeyList(secretTaskList);
        returnInfo.setMessage("success");
        return returnInfo;
    }
}


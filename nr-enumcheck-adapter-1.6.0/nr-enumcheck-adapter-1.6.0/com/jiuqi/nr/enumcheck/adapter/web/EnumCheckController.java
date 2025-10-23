/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo
 *  com.jiuqi.nr.datacheckcommon.param.QueryDimParam
 *  com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.integritycheck.common.UUIDMerger
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.enumcheck.adapter.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.param.QueryDimParam;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import com.jiuqi.nr.enumcheck.adapter.message.ResultInfo;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.integritycheck.common.UUIDMerger;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/enumcheck"})
public class EnumCheckController {
    private static final Logger logger = LoggerFactory.getLogger(EnumCheckController.class);
    @Autowired
    private IDataCheckCommonService dataCheckCommonService;
    @Autowired
    private MultCheckResDao multCheckResDao;

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u7ed3\u679c\u7ef4\u5ea6")
    @PostMapping(value={"/get-enumcheck-dims"})
    @NRContextBuild
    public DataCheckDimInfo queryResult(@Valid @RequestBody QueryDimParam queryDimParam) {
        try {
            return this.dataCheckCommonService.queryDims(queryDimParam);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u60c5\u666f\u5931\u8d25", e);
            return null;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c")
    @GetMapping(value={"/get-enumcheck-result/{runId}/{checkId}"})
    public ResultInfo getEnumCheckResult(@PathVariable(value="runId") String runId, @PathVariable(value="checkId") String checkId) {
        try {
            String asyncTaskId = UUIDMerger.merge((String)runId, (String)checkId);
            MultCheckRes result = this.multCheckResDao.findById(asyncTaskId);
            ObjectMapper objectMapper = new ObjectMapper();
            return (ResultInfo)objectMapper.readValue(result.getData(), ResultInfo.class);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u5931\u8d25", e);
            return null;
        }
    }
}


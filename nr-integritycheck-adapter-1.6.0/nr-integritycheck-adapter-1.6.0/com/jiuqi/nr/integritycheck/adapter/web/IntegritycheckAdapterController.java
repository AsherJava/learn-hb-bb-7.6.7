/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo
 *  com.jiuqi.nr.datacheckcommon.param.QueryDimParam
 *  com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo
 *  com.jiuqi.nr.integritycheck.common.UUIDMerger
 *  com.jiuqi.nr.integritycheck.dao.IntegrityCheckRes
 *  com.jiuqi.nr.integritycheck.dao.IntegrityCheckResDao
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.integritycheck.adapter.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.param.QueryDimParam;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.adapter.common.CheckResultInfo;
import com.jiuqi.nr.integritycheck.adapter.intf.QueryICRContext;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import com.jiuqi.nr.integritycheck.common.UUIDMerger;
import com.jiuqi.nr.integritycheck.dao.IntegrityCheckRes;
import com.jiuqi.nr.integritycheck.dao.IntegrityCheckResDao;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
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
@RequestMapping(value={"/api/integritycheck"})
public class IntegritycheckAdapterController {
    private static final Logger logger = LoggerFactory.getLogger(IntegritycheckAdapterController.class);
    @Autowired
    private IDataCheckCommonService dataCheckCommonService;
    @Autowired
    private IntegrityCheckResDao integrityCheckResDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u7ed3\u679c\u7ef4\u5ea6")
    @PostMapping(value={"/get-integritycheck-dims"})
    @NRContextBuild
    public DataCheckDimInfo queryResult(@Valid @RequestBody QueryDimParam queryDimParam) {
        try {
            return this.dataCheckCommonService.queryDims(queryDimParam);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u60c5\u666f\u5931\u8d25", e);
            return null;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"/get-integritycheck-result"})
    public CheckResultInfo getIntegritycheckResult(@Valid @RequestBody QueryICRContext queryICRContext) {
        try {
            CheckResultInfo resultInfo = new CheckResultInfo();
            String batchId = UUIDMerger.merge((String)queryICRContext.getRunId(), (String)queryICRContext.getCheckId());
            resultInfo.setBatchId(batchId);
            IntegrityCheckRes result = this.integrityCheckResDao.findById(batchId);
            ObjectMapper objectMapper = new ObjectMapper();
            IntegrityCheckResInfo integrityCheckResInfo = (IntegrityCheckResInfo)objectMapper.readValue(result.getData(), IntegrityCheckResInfo.class);
            List<String> orgKeys = queryICRContext.getOrgKeys();
            if (null != orgKeys && !orgKeys.isEmpty()) {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(queryICRContext.getTaskKey());
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
                String dwDimName = iEntityDefine.getDimensionName();
                Map dimensionSet = integrityCheckResInfo.getDimensionSet();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dwDimName);
                dimensionValue.setValue(String.join((CharSequence)";", orgKeys));
                dimensionSet.put(dwDimName, dimensionValue);
                integrityCheckResInfo.setDimensionSet(dimensionSet);
            }
            resultInfo.setIntegrityCheckResInfo(integrityCheckResInfo);
            return resultInfo;
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u5931\u8d25", e);
            return null;
        }
    }
}


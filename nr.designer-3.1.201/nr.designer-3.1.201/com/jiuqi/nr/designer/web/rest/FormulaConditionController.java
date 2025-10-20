/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IFormulaConditionService;
import com.jiuqi.nr.designer.web.facade.ConditionImportResult;
import com.jiuqi.nr.designer.web.facade.FormulaConditionObj;
import com.jiuqi.nr.designer.web.facade.FormulaConditionPageObj;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u516c\u5f0f\u9002\u7528\u6761\u4ef6"})
public class FormulaConditionController {
    @Autowired
    private IFormulaConditionService formulaConditionService;
    private static final Logger logger = LoggerFactory.getLogger(FormulaConditionController.class);

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @GetMapping(value={"/formula/condition/query"})
    public FormulaConditionPageObj queryConditionsByTask(@RequestParam String task, @RequestParam(required=false) Long start, @RequestParam(required=false) Long num) throws JQException {
        try {
            return this.formulaConditionService.queryConditionsByTask(task, start, num);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_225, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/update"})
    public void updateCondition(@RequestBody FormulaConditionObj obj) throws JQException {
        try {
            this.formulaConditionService.updateFormulaCondition(obj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_220, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/update_rsa"})
    public void updateConditionRsa(@RequestBody @SFDecrypt FormulaConditionObj obj) throws JQException {
        this.updateCondition(obj);
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/batch-update"})
    public void updateConditions(@RequestBody List<FormulaConditionObj> objs) throws JQException {
        try {
            if (CollectionUtils.isEmpty(objs)) {
                return;
            }
            this.formulaConditionService.updateFormulaConditions(objs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_220, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @GetMapping(value={"/formula/condition/delete"})
    public void deleteCondition(@RequestParam String key) throws JQException {
        try {
            this.formulaConditionService.deleteFormulaCondition(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_221, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/delete"})
    public void deleteCondition(@RequestBody List<String> keys) throws JQException {
        try {
            this.formulaConditionService.deleteFormulaConditions(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_221, e.getMessage());
        }
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/insert"})
    public void insertCondition(@RequestBody FormulaConditionObj obj) throws JQException {
        try {
            this.formulaConditionService.insertFormulaCondition(obj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_222, e.getMessage());
        }
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/insert_rsa"})
    public void insertConditionRsa(@RequestBody @SFDecrypt FormulaConditionObj obj) throws JQException {
        this.insertCondition(obj);
    }

    @ApiOperation(value="\u5bfc\u5165\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/export"})
    public void exportConditions(HttpServletResponse response, @RequestParam String task) throws JQException {
        try (ServletOutputStream outputStream = response.getOutputStream();){
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
            this.formulaConditionService.exportConditions((OutputStream)outputStream, task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_226, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u5165\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/import-add"})
    public void importAddConditions(@RequestBody MultipartFile file, @RequestParam String task) throws JQException {
        ConditionImportResult result;
        try (InputStream inputStream = file.getInputStream();){
            result = this.formulaConditionService.importAddConditions(inputStream, task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_226, e.getMessage());
        }
        if (Boolean.FALSE.equals(result.getSuccess())) {
            logger.debug("\n\t\u5bfc\u5165\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25: {}", (Object)result);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_226, (Object)result);
        }
    }

    @ApiOperation(value="\u53d1\u5e03\u9002\u7528\u6761\u4ef6")
    @PostMapping(value={"/formula/condition/publish"})
    public void publishConditions(@RequestParam String task, @RequestBody(required=false) String[] conditionKeys) throws JQException {
        try {
            this.formulaConditionService.deployFormulaConditions(task, conditionKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_227, e.getMessage());
        }
    }

    @ApiOperation(value="\u662f\u5426\u5b58\u5728\u9002\u7528\u6761\u4ef6")
    @GetMapping(value={"/formula/condition/exist"})
    public Boolean isConditionExist(@RequestParam String task) throws JQException {
        try {
            return this.formulaConditionService.isConditionExist(task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_225, e.getMessage());
        }
    }
}


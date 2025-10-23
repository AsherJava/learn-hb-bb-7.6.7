/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.dto.IPageDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.exception.FormulaConditionErrorEnum;
import com.jiuqi.nr.formula.service.IFormulaConditionService;
import com.jiuqi.nr.formula.web.vo.ConditionImportResult;
import com.jiuqi.nr.formula.web.vo.UpdateResult;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.dto.IPageDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/condition"})
@Api(value="\u516c\u5f0f\u9002\u7528\u6761\u4ef6")
public class FormulaConditionController {
    private static final Logger logger = LoggerFactory.getLogger(FormulaConditionController.class);
    @Autowired
    private IFormulaConditionService formulaConditionService;

    @GetMapping(value={"query"})
    public IPageDTO<FormulaConditionDTO> query(@RequestParam String task, @RequestParam(required=false) Long start, @RequestParam(required=false) Long num) throws JQException {
        try {
            return this.formulaConditionService.queryConditionsByTask(task, start, num);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"list"})
    public List<FormulaConditionDTO> list(@RequestBody List<String> keys) throws JQException {
        try {
            return this.formulaConditionService.list(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"update"})
    @TaskLog(operation="\u66f4\u65b0\u9002\u7528\u6761\u4ef6")
    public UpdateResult update(@RequestBody FormulaConditionDTO obj) throws JQException {
        try {
            return this.formulaConditionService.updateFormulaCondition(obj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.UPDATE_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"update-rsa"})
    @TaskLog(operation="\u66f4\u65b0\u9002\u7528\u6761\u4ef6")
    public UpdateResult updateRsa(@RequestBody @SFDecrypt FormulaConditionDTO obj) throws JQException {
        return this.update(obj);
    }

    @PostMapping(value={"update/batch"})
    @TaskLog(operation="\u66f4\u65b0\u9002\u7528\u6761\u4ef6")
    public void batchUpdate(@RequestBody List<FormulaConditionDTO> objs) throws JQException {
        try {
            if (CollectionUtils.isEmpty(objs)) {
                return;
            }
            this.formulaConditionService.updateFormulaConditions(objs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.UPDATE_ERROR, e.getMessage());
        }
    }

    @GetMapping(value={"delete"})
    @TaskLog(operation="\u5220\u9664\u9002\u7528\u6761\u4ef6")
    public void delete(@RequestParam String key) throws JQException {
        try {
            this.formulaConditionService.deleteFormulaCondition(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.DELETE_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"delete/batch"})
    @TaskLog(operation="\u5220\u9664\u9002\u7528\u6761\u4ef6")
    public void batchDelete(@RequestBody List<String> keys) throws JQException {
        try {
            this.formulaConditionService.deleteFormulaConditions(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.DELETE_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"insert"})
    @TaskLog(operation="\u65b0\u5efa\u9002\u7528\u6761\u4ef6")
    public UpdateResult insert(@RequestBody FormulaConditionDTO obj) throws JQException {
        try {
            return this.formulaConditionService.insertFormulaCondition(obj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.INSERT_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"insert-rsa"})
    @TaskLog(operation="\u65b0\u5efa\u9002\u7528\u6761\u4ef6")
    public UpdateResult insertRsa(@RequestBody @SFDecrypt FormulaConditionDTO obj) throws JQException {
        return this.insert(obj);
    }

    @PostMapping(value={"export"})
    public void exportConditions(HttpServletResponse response, @RequestParam String task) throws JQException {
        try (ServletOutputStream outputStream = response.getOutputStream();){
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
            this.formulaConditionService.exportConditions((OutputStream)outputStream, task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.EXPORT_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"upload"})
    public FileInfoDTO uploadFile(@RequestBody MultipartFile file) throws JQException {
        try {
            return this.formulaConditionService.uploadFile(file);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.IMPORT_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"import"})
    @TaskLog(operation="\u5bfc\u5165\u9002\u7528\u6761\u4ef6")
    public void importConditions(@RequestParam String fileKey, @RequestParam String taskKey) throws JQException {
        ConditionImportResult result = null;
        try {
            result = this.formulaConditionService.importAddConditions(fileKey, taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.IMPORT_ERROR, e.getMessage());
        }
        if (result != null && Boolean.FALSE.equals(result.getSuccess())) {
            logger.debug("\n\t\u5bfc\u5165\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25: {}", (Object)result);
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.IMPORT_ERROR, (Object)result);
        }
    }

    @PostMapping(value={"publish"})
    @TaskLog(operation="\u53d1\u5e03\u9002\u7528\u6761\u4ef6")
    public void publish(@RequestParam String task, @RequestBody(required=false) String[] conditionKeys) throws JQException {
        try {
            this.formulaConditionService.deployFormulaConditions(task, conditionKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.PUBLISH_ERROR, e.getMessage());
        }
    }

    @GetMapping(value={"exist"})
    public Boolean isConditionExist(@RequestParam String task) throws JQException {
        try {
            return this.formulaConditionService.isConditionExist(task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }

    @GetMapping(value={"getCode/{taskKey}"})
    public String generatorCode(@PathVariable String taskKey) throws JQException {
        try {
            return this.formulaConditionService.generatorCode(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }

    @GetMapping(value={"getCondition/{taskKey}/{code}"})
    public FormulaConditionDTO getCondition(@PathVariable String taskKey, @PathVariable String code) throws JQException {
        try {
            return this.formulaConditionService.getCondition(taskKey, code);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }

    @PostMapping(value={"listConditions/{taskKey}"})
    public List<FormulaConditionDTO> listConditions(@PathVariable String taskKey, @RequestBody List<String> codes) throws JQException {
        try {
            return this.formulaConditionService.listConditionsByCode(taskKey, codes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaConditionErrorEnum.QUERY_ERROR, e.getMessage());
        }
    }
}


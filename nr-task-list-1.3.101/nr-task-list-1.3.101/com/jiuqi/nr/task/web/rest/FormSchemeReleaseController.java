/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResult$Result
 *  com.jiuqi.nr.datascheme.api.core.DeployResultDetail
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.graph.exception.RWLockExecuterException
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployResultDetail;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.dto.FormPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishResultDTO;
import com.jiuqi.nr.task.dto.FormSchemePublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.exception.FormSchemeException;
import com.jiuqi.nr.task.service.IFormSchemeReleaseService;
import com.jiuqi.nr.task.web.param.BatchReleaseResultParam;
import com.jiuqi.nr.task.web.param.ReleaseFormParam;
import com.jiuqi.nr.task.web.param.ReleaseParam;
import com.jiuqi.nr.task.web.vo.FormSchemeStatusVO;
import com.jiuqi.nr.task.web.vo.ReleaseErrorVO;
import com.jiuqi.nr.task.web.vo.ReleaseFormVO;
import com.jiuqi.nr.task.web.vo.ReleaseResultVO;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u62a5\u8868\u65b9\u6848\u7ef4\u62a4")
@RequestMapping(value={"api/v1/formScheme/release"})
public class FormSchemeReleaseController {
    @Autowired
    private IFormSchemeReleaseService formSchemeReleaseService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    private static final Logger LOGGER = LoggerFactory.getLogger(FormSchemeReleaseController.class);

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u53d1\u5e03")
    @PostMapping(value={"/publish"})
    @TaskLog(operation="\u53d1\u5e03\u62a5\u8868\u65b9\u6848")
    public void publish(@RequestBody ReleaseParam param) throws JQException {
        try {
            boolean syncPublish = param.isSyncPublish();
            if (syncPublish) {
                this.formSchemeReleaseService.asyncPublish(new FormSchemePublishDTO(param.getFormSchemeKey(), param.isPublishDataScheme()));
            } else {
                this.formSchemeReleaseService.publish(new FormSchemePublishDTO(param.getFormSchemeKey(), param.isPublishDataScheme()));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_RELEASE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u53d1\u5e03")
    @PostMapping(value={"/publish/form"})
    @TaskLog(operation="\u53d1\u5e03\u62a5\u8868")
    public ReleaseResultVO publishForm(@RequestBody ReleaseFormParam param) throws JQException {
        try {
            FormPublishDTO obj = new FormPublishDTO(param.getFormSchemeKey(), param.getFormKeys());
            obj.setDeployDataTable(param.isPublishDataTable());
            this.formSchemeReleaseService.publish(obj);
        }
        catch (RWLockExecuterException e) {
            LOGGER.error(e.getMessage(), e);
            return new ReleaseResultVO(FormSchemeException.FORM_SCHEME_RELEASE_FORM_FAILED.getMessage(), FormSchemeException.FORM_SCHEME_RELEASE_TIMEOUT.getMessage());
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(param.getFormSchemeKey());
            DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
            ReleaseResultVO result = this.getDataSchemeReleaseResult(task.getDataScheme());
            if (null != result) {
                return result;
            }
            return new ReleaseResultVO(FormSchemeException.FORM_SCHEME_RELEASE_FORM_FAILED.getMessage(), e.getMessage());
        }
        return new ReleaseResultVO();
    }

    private ReleaseResultVO getDataSchemeReleaseResult(String dataSchemeKey) {
        DeployResult deployResult;
        DataSchemeDeployStatus deployStatus = this.runtimeDataSchemeService.getDeployStatus(dataSchemeKey);
        if (null != deployStatus && null != (deployResult = deployStatus.getDeployResult()) && !deployResult.isSuccess()) {
            ArrayList<ReleaseErrorVO> errors = new ArrayList<ReleaseErrorVO>();
            List details = deployResult.getDetails();
            if (!CollectionUtils.isEmpty(details)) {
                for (DeployResultDetail detail : details) {
                    if (detail.isSuccess()) continue;
                    String desc = null;
                    desc = !CollectionUtils.isEmpty(detail.getErrorMsg()) ? StringUtils.collectionToCommaDelimitedString(detail.getErrorMsg()) : (!CollectionUtils.isEmpty(detail.getWarnMsg()) ? StringUtils.collectionToCommaDelimitedString(detail.getWarnMsg()) : "\u53d1\u5e03\u5931\u8d25");
                    errors.add(new ReleaseErrorVO(detail.getTitle() + "[" + detail.getCode() + "]", desc));
                }
            } else if (DeployResult.Result.DEPLOY_WARN == deployResult.getResult()) {
                errors.add(new ReleaseErrorVO("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u8b66\u544a", deployResult.getMessage()));
            } else {
                errors.add(new ReleaseErrorVO("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38", deployResult.getMessage()));
            }
            return new ReleaseResultVO(errors);
        }
        return null;
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u6279\u91cf\u53d1\u5e03")
    @PostMapping(value={"/batch/publish"})
    @TaskLog(operation="\u6279\u91cf\u53d1\u5e03")
    public List<BatchReleaseResultParam> batchPublish(@RequestBody List<ReleaseParam> params) throws JQException {
        if (CollectionUtils.isEmpty(params)) {
            return Collections.emptyList();
        }
        boolean deployDataScheme = false;
        HashSet<String> formSchemeKeys = new HashSet<String>();
        for (ReleaseParam param : params) {
            deployDataScheme = param.isPublishDataScheme();
            formSchemeKeys.add(param.getFormSchemeKey());
        }
        FormSchemeBatchPublishDTO batchDeployDTO = new FormSchemeBatchPublishDTO(formSchemeKeys, deployDataScheme);
        try {
            List<FormSchemeBatchPublishResultDTO> result = this.formSchemeReleaseService.batchPublish(batchDeployDTO);
            return result.stream().map(r -> new BatchReleaseResultParam(r.isSuccess(), r.getFormSchemeDefine().getTaskKey(), r.getFormSchemeDefine().getKey(), r.isSuccess() ? "\u53d1\u5e03\u6210\u529f" : "\u53d1\u5e03\u5931\u8d25")).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.FORM_SCHEME_RELEASE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u53d1\u5e03")
    @GetMapping(value={"/status/{formSchemeKey}"})
    public FormSchemeStatusVO getStatus(@PathVariable String formSchemeKey) {
        FormSchemeStatusDTO status = this.formSchemeReleaseService.getStatus(formSchemeKey);
        return FormSchemeStatusVO.getInstance(status);
    }

    @ApiOperation(value="\u67e5\u8be2\u53d1\u5e03\u8fdb\u5ea6")
    @GetMapping(value={"/query/progress/{formSchemeKey}"})
    public ProgressItem getProgress(@PathVariable String formSchemeKey) {
        return this.formSchemeReleaseService.getProgress(formSchemeKey);
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u7ef4\u62a4")
    @GetMapping(value={"/maintenance/{formSchemeKey}"})
    public void maintainScheme(@PathVariable String formSchemeKey) throws JQException {
        try {
            this.formSchemeReleaseService.maintain(formSchemeKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848\u7ef4\u62a4")
    @GetMapping(value={"/maintenance/cancel/{formSchemeKey}"})
    public void cancelMaintain(@PathVariable String formSchemeKey) throws JQException {
        try {
            this.formSchemeReleaseService.cancelMaintain(formSchemeKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u4efb\u52a1\u53d1\u5e03\u5931\u8d25\u72b6\u6001")
    @PostMapping(value={"/publish/error/{formSchemeKey}"})
    public void downloadError(@PathVariable String formSchemeKey, HttpServletResponse response) throws JQException {
        this.formSchemeReleaseService.exportPublishError(formSchemeKey, response);
    }

    @ApiOperation(value="\u662f\u5426\u53ef\u4ee5\u9009\u8868\u53d1\u5e03")
    @GetMapping(value={"can/release/form/{formSchemeKey}"})
    public ReleaseFormVO getReleaseForm(@PathVariable String formSchemeKey) {
        ReleaseFormVO vo = new ReleaseFormVO();
        vo.setFormSchemeKey(formSchemeKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (null == formScheme) {
            vo.setFormSchemeNotPublished(true);
            formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        vo.setDataSchemeKey(task.getDataScheme());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(task.getDataScheme());
        if (null == dataScheme) {
            vo.setDataSchemeNotPublished(true);
            return vo;
        }
        DesignDataScheme desDataScheme = this.designDataSchemeService.getDataScheme(task.getDataScheme());
        if (!Objects.equals(dataScheme.getPrefix(), desDataScheme.getPrefix())) {
            vo.setDataPrefixChanged(true);
            return vo;
        }
        List des = this.designDataSchemeService.getDataSchemeDimension(task.getDataScheme()).stream().filter(d -> DimensionType.UNIT_SCOPE != d.getDimensionType()).map(DataDimension::getDimKey).collect(Collectors.toList());
        List run = this.runtimeDataSchemeService.getDataSchemeDimension(task.getDataScheme()).stream().filter(d -> DimensionType.UNIT_SCOPE != d.getDimensionType()).map(DataDimension::getDimKey).collect(Collectors.toList());
        if (des.size() != run.size()) {
            vo.setDataDimChanged(true);
            return vo;
        }
        des.removeAll(run);
        if (!des.isEmpty()) {
            vo.setDataDimChanged(true);
            return vo;
        }
        return vo;
    }
}


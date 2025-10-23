/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.common.ProgressConsumer$Progress
 *  com.jiuqi.nr.definition.common.ProgressConsumer$Step
 *  com.jiuqi.nr.definition.deploy.common.ParamDeployException
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 *  com.jiuqi.nr.graph.exception.RWLockExecuterException
 *  com.jiuqi.nr.task.api.common.FileDownload
 *  com.jiuqi.nr.task.api.status.ReleaseStatus
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.common.ProgressConsumer;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import com.jiuqi.nr.task.api.common.FileDownload;
import com.jiuqi.nr.task.api.status.ReleaseStatus;
import com.jiuqi.nr.task.dto.FormPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishResultDTO;
import com.jiuqi.nr.task.dto.FormSchemePublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.exception.PublishException;
import com.jiuqi.nr.task.service.IFormSchemeReleaseService;
import com.jiuqi.nr.task.service.impl.FormSchemeAsyncDeployExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FormSchemeReleaseServiceImpl
implements IFormSchemeReleaseService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IParamDeployController paramDeployController;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;

    private static ProgressItem toProgressItem(String progressId, ProgressConsumer.Progress progress) {
        ProgressItem item = new ProgressItem();
        item.setProgressId(progressId);
        item.setCurrentProgess(progress.getProgress());
        item.setCurrentStep(progress.getCurrentStep());
        item.setMessage(progress.getMessage());
        item.setFinished(progress.isFinished());
        item.setFailed(progress.isFailed());
        item.setStepTitles(progress.getSteps().stream().map(ProgressConsumer.Step::getName).collect(Collectors.toList()));
        return item;
    }

    @Override
    public void publish(FormSchemePublishDTO publishDTO) throws PublishException {
        String formSchemeKey = publishDTO.getFormSchemeKey();
        ProgressItem progressItem = this.progressCacheService.getProgress(formSchemeKey);
        if (null != progressItem && (progressItem.isFinished() || progressItem.isFailed())) {
            this.progressCacheService.removeProgress(formSchemeKey);
        }
        try {
            this.paramDeployController.deploy(formSchemeKey, publishDTO.isDeployDataScheme(), progress -> this.progressCacheService.setProgress(formSchemeKey, FormSchemeReleaseServiceImpl.toProgressItem(formSchemeKey, progress)));
        }
        catch (Exception e) {
            progressItem = this.progressCacheService.getProgress(formSchemeKey);
            progressItem.setFailed(true);
            progressItem.setMessage(e.getMessage());
            this.progressCacheService.setProgress(formSchemeKey, progressItem);
            throw new PublishException("\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u5931\u8d25", e);
        }
    }

    @Override
    public void publish(FormPublishDTO formPublishDTO) throws PublishException {
        try {
            this.paramDeployController.deploy(formPublishDTO.getFormSchemeKey(), formPublishDTO.getFormKeys(), formPublishDTO.isDeployDataTable(), (type, item) -> true);
        }
        catch (ParamDeployException | RWLockExecuterException e) {
            throw e;
        }
        catch (Exception e) {
            throw new PublishException("\u62a5\u8868\u53d1\u5e03\u5931\u8d25", e);
        }
    }

    @Override
    public String asyncPublish(FormSchemePublishDTO formSchemePublishDTO) {
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)formSchemePublishDTO));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new FormSchemeAsyncDeployExecutor());
        return this.asyncTaskManager.publishTask(info);
    }

    @Override
    public List<FormSchemeBatchPublishResultDTO> batchPublish(FormSchemeBatchPublishDTO batchDeployDTO) throws PublishException {
        if (CollectionUtils.isEmpty(batchDeployDTO.getFormSchemeKeys())) {
            return Collections.emptyList();
        }
        List formSchemes = this.designTimeViewController.listFormScheme(batchDeployDTO.getFormSchemeKeys());
        if (batchDeployDTO.isDeployDataScheme()) {
            HashSet<String> taskKeys = new HashSet<String>();
            for (Object formScheme : formSchemes) {
                taskKeys.add(formScheme.getTaskKey());
            }
            List dataSchemeKeys = this.designTimeViewController.listAllTask().stream().filter(t -> taskKeys.contains(t.getKey())).map(TaskDefine::getDataScheme).distinct().collect(Collectors.toList());
            for (String dataSchemeKey : dataSchemeKeys) {
                DeployResult deployResult = this.dataSchemeDeployService.deployDataScheme(dataSchemeKey, null, null);
                if (deployResult.isSuccess()) continue;
                throw new PublishException(deployResult.getDeployErrorMessage());
            }
        }
        ArrayList<FormSchemeBatchPublishResultDTO> result = new ArrayList<FormSchemeBatchPublishResultDTO>();
        for (Object formScheme : formSchemes) {
            try {
                FormSchemePublishDTO formSchemeDeployDTO = new FormSchemePublishDTO(formScheme.getKey(), false);
                this.publish(formSchemeDeployDTO);
                result.add(new FormSchemeBatchPublishResultDTO((FormSchemeDefine)formScheme, true));
            }
            catch (PublishException e) {
                result.add(new FormSchemeBatchPublishResultDTO((FormSchemeDefine)formScheme, false));
            }
        }
        return result;
    }

    @Override
    public FormSchemeStatusDTO getStatus(String formSchemeKey) {
        ParamDeployStatus status = this.paramDeployController.getDeployStatus(formSchemeKey);
        FormSchemeStatusDTO statusDTO = new FormSchemeStatusDTO();
        if (null != status) {
            statusDTO.setStatus(FormSchemeReleaseServiceImpl.toReleaseStatus(status.getDeployStatus(), status.getParamStatus()));
            statusDTO.setPublishTime(status.getDeployTime());
            statusDTO.setMessage(status.getDeployDetail());
        } else {
            statusDTO.setStatus(ReleaseStatus.NEVER_PUBLISH);
        }
        return statusDTO;
    }

    private static ReleaseStatus toReleaseStatus(ParamDeployEnum.DeployStatus deployStatus, ParamDeployEnum.ParamStatus paramStatus) {
        ReleaseStatus releaseStatus;
        switch (deployStatus) {
            case DEPLOY: {
                releaseStatus = ReleaseStatus.PUBLISHING;
                break;
            }
            case WARNING: {
                releaseStatus = ReleaseStatus.PUBLISH_WARRING;
                break;
            }
            case SUCCESS: {
                releaseStatus = ReleaseStatus.PUBLISH_SUCCESS;
                break;
            }
            case FAIL: {
                releaseStatus = ReleaseStatus.PUBLISH_FAIL;
                break;
            }
            default: {
                releaseStatus = ReleaseStatus.NEVER_PUBLISH;
            }
        }
        if (releaseStatus == ReleaseStatus.PUBLISHING) {
            return releaseStatus;
        }
        switch (paramStatus) {
            case MAINTENANCE: {
                releaseStatus = ReleaseStatus.PROTECTING;
                break;
            }
            case READONLY: 
            case LOCKED: {
                releaseStatus = ReleaseStatus.PARAM_LOCKING;
                break;
            }
        }
        return releaseStatus;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void maintain(String formSchemeKey) {
        this.paramDeployController.updateParamStatus(formSchemeKey, ParamDeployEnum.ParamStatus.MAINTENANCE);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelMaintain(String formSchemeKey) {
        this.paramDeployController.updateParamStatus(formSchemeKey, ParamDeployEnum.ParamStatus.DEFAULT);
    }

    @Deprecated
    private void checkAccountForm(String taskId) throws Exception {
        List designFormSchemeDefines = this.designTimeViewController.listFormSchemeByTask(taskId);
        for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
            List forms = this.designTimeViewController.listFormByFormScheme(designFormSchemeDefine.getKey());
            for (DesignFormDefine form : forms) {
                if (!FormType.FORM_TYPE_ACCOUNT.equals((Object)form.getFormType())) continue;
                List regions = this.designTimeViewController.listDataRegionByForm(form.getKey());
                int floatRegionNum = 0;
                HashSet<String> tableNum = new HashSet<String>();
                boolean hasAllDim = true;
                block2: for (DesignDataRegionDefine region : regions) {
                    if (!DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)region.getRegionKind()) && !DataRegionKind.DATA_REGION_COLUMN_LIST.equals((Object)region.getRegionKind())) continue;
                    ArrayList<String> fieldKeys = new ArrayList<String>();
                    String[] bizKeys = new String[]{};
                    ++floatRegionNum;
                    List links = this.designTimeViewController.listDataLinkByDataRegion(region.getKey());
                    for (DesignDataLinkDefine link : links) {
                        DesignFieldDefine designFieldDefine;
                        if (!DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)link.getType()) || null == (designFieldDefine = this.dataDefinitionDesignTimeController.queryFieldDefine(link.getLinkExpression()))) continue;
                        tableNum.add(designFieldDefine.getOwnerTableKey());
                        fieldKeys.add(designFieldDefine.getKey());
                        DesignTableDefine tabledefine = this.dataDefinitionDesignTimeController.queryTableDefine(designFieldDefine.getOwnerTableKey());
                        bizKeys = tabledefine.getBizKeyFieldsID();
                    }
                    List bizFields = this.designDataSchemeService.getDataFields(Arrays.asList(bizKeys)).stream().filter(f -> DataFieldKind.PUBLIC_FIELD_DIM != f.getDataFieldKind() && DataFieldKind.BUILT_IN_FIELD != f.getDataFieldKind()).collect(Collectors.toList());
                    for (DesignDataField field : bizFields) {
                        if (fieldKeys.contains(field.getKey())) continue;
                        hasAllDim = false;
                        continue block2;
                    }
                }
                if (floatRegionNum != 1) {
                    throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u53ea\u80fd\u5b58\u5728\u4e00\u4e2a\u6d6e\u52a8\u533a\u57df");
                }
                if (tableNum.size() != 1) {
                    throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u6d6e\u52a8\u533a\u57df\u4e2d\u5fc5\u987b\u8bbe\u7f6e\u6307\u6807\u4e14\u53ea\u80fd\u6765\u81ea\u540c\u4e00\u4e2a\u53f0\u8d26\u8868");
                }
                if (hasAllDim) continue;
                throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u6d6e\u52a8\u533a\u57df\u4e2d\u5fc5\u987b\u9009\u62e9\u7ef4\u5ea6");
            }
        }
    }

    @Override
    public ProgressItem getProgress(String formSchemeKey) {
        ProgressItem progress = this.progressCacheService.getProgress(formSchemeKey);
        if (null != progress && (progress.isFinished() || progress.isFailed())) {
            this.progressCacheService.removeProgress(formSchemeKey);
        }
        return progress;
    }

    @Override
    public void exportPublishError(String formSchemeKey, HttpServletResponse response) {
        FormSchemeStatusDTO status = this.getStatus(formSchemeKey);
        String errorType = status.getStatus().equals((Object)ReleaseStatus.PUBLISH_FAIL) ? "\u5931\u8d25" : "\u8b66\u544a";
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        FileDownload.exportTxtFile((HttpServletResponse)response, (String)status.getMessage(), (String)String.format("\u53d1\u5e03%s\u8be6\u60c5-%s.txt", errorType, formScheme.getTitle()));
    }
}


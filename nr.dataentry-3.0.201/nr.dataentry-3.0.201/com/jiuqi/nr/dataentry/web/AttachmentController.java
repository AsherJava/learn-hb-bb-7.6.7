/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FilePoolFiles
 *  com.jiuqi.nr.attachment.output.ReferencesInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.asynctask.BatchDownloadAttachmentExecutor;
import com.jiuqi.nr.dataentry.attachment.intf.AttachmentDownloadContext;
import com.jiuqi.nr.dataentry.attachment.intf.AttachmentReferencesContext;
import com.jiuqi.nr.dataentry.attachment.intf.DelAllNotReferAttachContext;
import com.jiuqi.nr.dataentry.attachment.intf.FileDownloadContext;
import com.jiuqi.nr.dataentry.attachment.intf.FileGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.FilePoolAtachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.ForceDeleteAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.HistoricalAttachmentClearingContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileCategoryContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentChangeFileSecretContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataPageContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentSaveFilesContext;
import com.jiuqi.nr.dataentry.attachment.intf.ReFileNameContext;
import com.jiuqi.nr.dataentry.attachment.intf.SubordinateDWContext;
import com.jiuqi.nr.dataentry.attachment.message.AttachmentDetails;
import com.jiuqi.nr.dataentry.attachment.message.FileDetails;
import com.jiuqi.nr.dataentry.attachment.message.FileNode;
import com.jiuqi.nr.dataentry.attachment.message.FormDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.GridDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.IReturnObjcet;
import com.jiuqi.nr.dataentry.attachment.message.SaveFilesResult;
import com.jiuqi.nr.dataentry.attachment.message.TaskObj;
import com.jiuqi.nr.dataentry.attachment.service.AttachmentOperationService;
import com.jiuqi.nr.dataentry.attachment.service.IAttachmentService;
import com.jiuqi.nr.dataentry.attachment.service.TaskOperationService;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/attachment"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u9644\u4ef6\u7ba1\u7406\u529f\u80fd\u5165\u53e3"})
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);
    @Autowired
    private IAttachmentService service;
    @Autowired
    private AttachmentOperationService attachmentOperationService;
    @Autowired
    private TaskOperationService taskOperationService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private FileService fileService;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;

    @ResponseBody
    @ApiOperation(value="\u5de6\u4fa7\u6811-\u52a0\u8f7d\u9644\u4ef6\u6307\u6807\u5206\u7ec4")
    @PostMapping(value={"/load-groups"})
    @NRContextBuild
    public IReturnObjcet<List<ITree<FileNode>>> loadFieldGroups(@Valid @RequestBody IAttachmentContext context) {
        IReturnObjcet<List<ITree<FileNode>>> instance = null;
        List<ITree<FileNode>> groups = null;
        try {
            groups = this.service.loadFieldGroups(context);
            instance = IReturnObjcet.getSuccessInstance(groups);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u52a0\u8f7d\u9644\u4ef6\u6307\u6807\u5206\u7ec4\u5f02\u5e38\uff1a" + e.getMessage(), groups);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u53f3\u4fa7\u9644\u4ef6\u4fe1\u606f")
    @PostMapping(value={"/load-details"})
    @NRContextBuild
    public IReturnObjcet<AttachmentDetails> loadAttachmentDetails(@Valid @RequestBody IAttachmentGridDataContext context) {
        IReturnObjcet<AttachmentDetails> instance = null;
        AttachmentDetails details = null;
        try {
            details = this.service.loadDetails(context);
            instance = IReturnObjcet.getSuccessInstance(details);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u52a0\u8f7d\u9644\u4ef6\u8be6\u7ec6\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), details);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u5206\u9875\u52a0\u8f7d\u8868\u683c\u6570\u636e")
    @PostMapping(value={"/load-page-data"})
    @NRContextBuild
    public IReturnObjcet<GridDataInfo> loadAttachmentGridPageData(@Valid @RequestBody IAttachmentGridDataPageContext context) {
        IReturnObjcet<GridDataInfo> instance = null;
        GridDataInfo gridData = null;
        try {
            gridData = this.service.loadGridPageData(context);
            instance = IReturnObjcet.getSuccessInstance(gridData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u5206\u9875\u52a0\u8f7d\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), gridData);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u9644\u4ef6\u7ba1\u7406\u4fee\u6539\u5bc6\u7ea7")
    @PostMapping(value={"/updatefilessecret"})
    public ReturnInfo updateJtableFilesSecret(@Valid @RequestBody IAttachmentChangeFileSecretContext context) {
        return this.attachmentOperationService.updateJtableFilesSecret(context);
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u9644\u4ef6\u7ba1\u7406\u4fee\u6539\u7c7b\u522b")
    @PostMapping(value={"/updatefilecategory"})
    public ReturnInfo updateFilesCategory(@RequestBody IAttachmentChangeFileCategoryContext context) {
        return this.attachmentOperationService.updateFilesCategory(context);
    }

    @ResponseBody
    @ApiOperation(value="\u9644\u4ef6\u7ba1\u7406\u4e0b\u8f7d\u9644\u4ef6")
    @GetMapping(value={"/downloadfiles"})
    public void downloadAttachmentFiles(String param, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AttachmentDownloadContext context = (AttachmentDownloadContext)((Object)objectMapper.readValue(param, AttachmentDownloadContext.class));
            DsContextImpl dsContext = new DsContextImpl();
            dsContext.setEntityId(context.getContextEntityId());
            dsContext.setFilterExpression(context.getContextFilterExpression());
            DsContextHolder.setDsContext((DsContext)dsContext);
            this.attachmentOperationService.downloadAttachmentFiles(context, response);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @ResponseBody
    @ApiOperation(value="\u9644\u4ef6\u7ba1\u7406\u5220\u9664\u9644\u4ef6")
    @PostMapping(value={"/removefiles"})
    @NRContextBuild
    public ReturnInfo removeAttachmentFiles(@Valid @RequestBody IAttachmentChangeFileSecretContext context) {
        return this.attachmentOperationService.removeAttachmentFiles(context);
    }

    @ResponseBody
    @ApiOperation(value="\u6309\u6587\u4ef6\u540d\u79f0\u641c\u7d22")
    @PostMapping(value={"/search-by-filename"})
    @NRContextBuild
    public IReturnObjcet<GridDataInfo> searchByFilename(@Valid @RequestBody IAttachmentGridDataPageContext context) {
        IReturnObjcet<GridDataInfo> instance = null;
        GridDataInfo gridData = null;
        try {
            gridData = this.service.searchByFilename(context);
            instance = IReturnObjcet.getSuccessInstance(gridData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u641c\u7d22\u5f02\u5e38\uff1a" + e.getMessage(), gridData);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u4e0b\u6240\u6709\u4fe1\u606f")
    @PostMapping(value={"/get-currenttask-info"})
    @NRContextBuild
    public IReturnObjcet<TaskObj> getCurrentTaskInfo(@Valid @RequestBody IAttachmentContext context) {
        IReturnObjcet<TaskObj> instance = null;
        TaskObj taskObj = null;
        try {
            taskObj = this.taskOperationService.getCurrentTaskInfo(context);
            instance = IReturnObjcet.getSuccessInstance(taskObj);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u83b7\u53d6\u4efb\u52a1\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), taskObj);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u5f53\u524d\u7ef4\u5ea6\u4e0b\u6240\u6709\u8868\u5355\u4fe1\u606f")
    @PostMapping(value={"/get-form-data"})
    @NRContextBuild
    public IReturnObjcet<List<FormDataInfo>> getFormData(@Valid @RequestBody IAttachmentContext context) {
        IReturnObjcet<List<FormDataInfo>> instance = null;
        List<FormDataInfo> formDataInfos = null;
        try {
            formDataInfos = this.service.getFormData(context);
            instance = IReturnObjcet.getSuccessInstance(formDataInfos);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u83b7\u53d6\u8868\u5355\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), formDataInfos);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u5f00\u542f\u9644\u4ef6\u6c60")
    @PostMapping(value={"/isOpenFilepool"})
    public boolean isOpenFilepool() {
        return this.taskOperationService.isOpenFilepool();
    }

    @ResponseBody
    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u5f00\u542f\u9644\u4ef6\u9884\u89c8")
    @PostMapping(value={"/isPreview"})
    public boolean isPreview() {
        return this.taskOperationService.isPreview();
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u4e0b\u7ea7")
    @PostMapping(value={"/subordinateDW"})
    @NRContextBuild
    public List<String> getSubordinateDW(@Valid @RequestBody SubordinateDWContext context) {
        return this.taskOperationService.getSubordinateDW(context);
    }

    @ResponseBody
    @ApiOperation(value="\u4e0a\u4f20\u6821\u9a8c")
    @PostMapping(value={"/upload-verification"})
    @NRContextBuild
    public FilesUploadInfo uploadVerification(@Valid @RequestBody FilesUploadInfo filesUploadInfo) {
        return this.service.uploadVerification(filesUploadInfo);
    }

    @ResponseBody
    @ApiOperation(value="\u4e0a\u4f20\u9644\u4ef6")
    @PostMapping(value={"/upload-files"})
    @NRContextBuild
    public ReturnInfo uploadFilesKeys(@Valid @RequestBody FilesUploadInfo filesUploadInfo, HttpServletResponse response) {
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'");
        return this.attachmentOperationService.uploadFiles(filesUploadInfo);
    }

    @JLoggable(value="\u6279\u91cf\u9644\u4ef6\u7ba1\u7406\u4fdd\u5b58\u9644\u4ef6")
    @ApiOperation(value="\u9644\u4ef6\u7ba1\u7406\u7ec4\u4ef6\uff1a\u4fdd\u5b58\u9644\u4ef6")
    @RequestMapping(value={"/save-files"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public SaveFilesResult saveFiles(@Valid @RequestBody IAttachmentSaveFilesContext attachmentSaveFilesContext) {
        return this.attachmentOperationService.saveFiles(attachmentSaveFilesContext);
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @GetMapping(value={"/get-all-task"})
    public IReturnObjcet<List<TaskObj>> getAllRunTimeTasks() throws JQException {
        IReturnObjcet<List<TaskObj>> instance = null;
        List<TaskObj> taskObjList = null;
        try {
            taskObjList = this.taskOperationService.getAllRunTimeTasks();
            instance = IReturnObjcet.getSuccessInstance(taskObjList);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u83b7\u53d6\u4efb\u52a1\u5f02\u5e38\uff1a" + e.getMessage(), taskObjList);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u65f6\u671f\u83b7\u53d6\u62a5\u8868\u65b9\u6848key")
    @PostMapping(value={"/get-formScheme-key"})
    public String getFormSchemeKey(@Valid @RequestBody IAttachmentContext context) {
        return this.taskOperationService.getFormSchemeKey(context);
    }

    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u9644\u4ef6")
    @PostMapping(value={"/batch-delete-files"})
    @NRContextBuild
    public ReturnInfo batchDeleteFiles(@Valid @RequestBody BatchDownLoadEnclosureInfo context) {
        return this.attachmentOperationService.batchDeleteFiles(context);
    }

    @ResponseBody
    @ApiOperation(value="pdf\u9884\u89c8")
    @GetMapping(value={"/pdf-preview/{fileKey}"})
    public void pdfPreview(@PathVariable String fileKey) {
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u67e5\u8be2\u9644\u4ef6\u5f15\u7528\u60c5\u51b5")
    @PostMapping(value={"/get-attachment-references"})
    @NRContextBuild
    public IReturnObjcet<List<ReferencesInfo>> getAttachmentReferences(@Valid @RequestBody AttachmentReferencesContext context) {
        IReturnObjcet<List<ReferencesInfo>> instance = null;
        List<ReferencesInfo> referencesInfos = null;
        try {
            referencesInfos = this.service.getAttachmentReferences(context);
            instance = IReturnObjcet.getSuccessInstance(referencesInfos);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), referencesInfos);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u67e5\u8be2\u9644\u4ef6\u6c60\u9644\u4ef6")
    @PostMapping(value={"/get-filepool-attachment"})
    @NRContextBuild
    public IReturnObjcet<GridDataInfo> getFilePoolAttachment(@Valid @RequestBody FilePoolAtachmentContext context) {
        IReturnObjcet<GridDataInfo> instance = null;
        GridDataInfo gridDataInfo = null;
        try {
            gridDataInfo = this.service.getFilePoolAttachment(context);
            instance = IReturnObjcet.getSuccessInstance(gridDataInfo);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), gridDataInfo);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="(\u9644\u4ef6\u6c60)\u6309\u5f53\u524d\u7ef4\u5ea6\u67e5\u8be2\u9644\u4ef6\u6c60\u6240\u6709\u9644\u4ef6")
    @PostMapping(value={"/get-allFilesPool-files"})
    @NRContextBuild
    public IReturnObjcet<List<FilePoolFiles>> getAllFilePoolFiles(@Valid @RequestBody IAttachmentGridDataPageContext context) {
        IReturnObjcet<List<FilePoolFiles>> instance = null;
        List<FilePoolFiles> filePoolFiles = null;
        try {
            filePoolFiles = this.service.getAllFilePoolFiles(context);
            instance = IReturnObjcet.getSuccessInstance(filePoolFiles);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), filePoolFiles);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u91cd\u547d\u540d\u9644\u4ef6")
    @PostMapping(value={"/rename-attachment"})
    @NRContextBuild
    public ReturnInfo rename(@Valid @RequestBody ReFileNameContext reFileNameContext) {
        return this.attachmentOperationService.rename(reFileNameContext);
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u5386\u53f2\u9644\u4ef6\u6e05\u9664")
    @PostMapping(value={"/historical-attachment-clearing"})
    @NRContextBuild
    public ReturnInfo historicalAttachmentClearing(@Valid @RequestBody HistoricalAttachmentClearingContext context) {
        return this.attachmentOperationService.historicalAttachmentClearing(context);
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u5f3a\u5236\u5220\u9664\u9644\u4ef6")
    @PostMapping(value={"/forceDelete-attachment"})
    @NRContextBuild
    public ReturnInfo forceDeleteAttachment(@Valid @RequestBody ForceDeleteAttachmentContext context) {
        return this.attachmentOperationService.forceDeleteAttachment(context);
    }

    @ResponseBody
    @ApiOperation(value="\uff08\u9644\u4ef6\u6c60\uff09\u6e05\u9664\u6240\u6709\u672a\u88ab\u5f15\u7528\u9644\u4ef6")
    @PostMapping(value={"/delAllNotRefer-attachment"})
    @NRContextBuild
    public ReturnInfo deleteAllNotReferencesAttachment(@Valid @RequestBody DelAllNotReferAttachContext context) {
        return this.attachmentOperationService.deleteAllNotReferencesAttachment(context);
    }

    @ResponseBody
    @ApiOperation(value="\u901a\u8fc7groupKey\u83b7\u53d6\u6587\u4ef6\u4fe1\u606f\uff08\u83b7\u53d6\u56fe\u7247\u6307\u6807\u7684\u56fe\u7247\u4fe1\u606f\u3010\u4e34\u65f6\u3011\uff09")
    @GetMapping(value={"/getFileInfo/{taskKey}/{groupKey}"})
    public List<FileInfo> getFileInfoByGroupKey(@PathVariable String taskKey, @PathVariable String groupKey) {
        return this.service.getFileInfoByGroupKey(taskKey, groupKey);
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u53f3\u4fa7\u9644\u4ef6\u4fe1\u606f(\u4e13\u4e3a\u9644\u4ef6\u67e5\u770b\u7ec4\u4ef6\u63d0\u4f9b\u7684\u67e5\u8be2\u63a5\u53e3)")
    @PostMapping(value={"/load-file-details"})
    @NRContextBuild
    public IReturnObjcet<FileDetails> loadFileDetails(@Valid @RequestBody FileGridDataContext context) {
        IReturnObjcet<FileDetails> instance = null;
        FileDetails details = null;
        try {
            details = this.service.loadDetails(context);
            instance = IReturnObjcet.getSuccessInstance(details);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            instance = IReturnObjcet.getErrorInstance("\u52a0\u8f7d\u9644\u4ef6\u8be6\u7ec6\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage(), details);
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u9644\u4ef6\u7ba1\u7406\u4e0b\u8f7d\u9644\u4ef6(\u4e13\u4e3a\u9644\u4ef6\u67e5\u770b\u7ec4\u4ef6\u63d0\u4f9b\u7684\u4e0b\u8f7d\u63a5\u53e3)")
    @GetMapping(value={"/download-file"})
    public void downloadFile(String param, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileDownloadContext context = (FileDownloadContext)objectMapper.readValue(param, FileDownloadContext.class);
            this.attachmentOperationService.downloadFiles(context.getDataSchemeCode(), context.getFileKeys(), response);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @JLoggable(value="\u5bfc\u51fa\u88ab\u6279\u91cf\u4e0b\u8f7d\u7684\u9644\u4ef6", ignoreArgs=false)
    @CrossOrigin(value={"*"})
    @RequestMapping(value={"/batch-download-attachment"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5bfc\u51fa\u88ab\u6279\u91cf\u4e0b\u8f7d\u7684\u9644\u4ef6")
    @NRContextBuild
    public AsyncTaskInfo batchDownLoadAttachment(@Valid @RequestBody BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchDownLoadEnclosureInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDownloadAttachmentExecutor());
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setState(TaskState.PROCESSING);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @CrossOrigin(value={"*"})
    @GetMapping(value={"/download-exportAttachment"})
    @ApiOperation(value="\u4e0b\u8f7d\u88ab\u6279\u91cf\u4e0b\u8f7d\u5bfc\u51fa\u7684\u9644\u4ef6")
    public void downloadExportAttachment(String downLoadKey, HttpServletResponse response) {
        Object downLoadFileKey = this.cacheObjectResourceRemote.find((Object)downLoadKey);
        FileAreaService fileAreaService = this.fileService.tempArea();
        com.jiuqi.nr.file.FileInfo fileInfo = fileAreaService.getInfo((String)downLoadFileKey);
        if (null != fileInfo) {
            byte[] bytes = fileAreaService.download((String)downLoadFileKey);
            try (ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileInfo.getName(), "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", "" + fileInfo.getSize());
                IOUtils.copyLarge(ins, ous);
                ((OutputStream)ous).flush();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.PageInfo
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.io.api.ITaskDataService
 *  com.jiuqi.nr.io.api.TaskServiceFactory
 *  com.jiuqi.nr.io.params.input.UnitCountQueryParam
 *  com.jiuqi.nr.io.params.input.UnitQueryParam
 *  com.jiuqi.nr.io.record.bean.ImportHistory
 *  com.jiuqi.nr.io.record.bean.ImportState
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 *  com.jiuqi.nr.io.tsd.dto.AnalysisRes
 *  com.jiuqi.nr.io.tsd.dto.ExportType
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nr.nrdx.adapter.dto.AnalysisVO
 *  com.jiuqi.nr.nrdx.adapter.dto.DecryptionVO
 *  com.jiuqi.nr.nrdx.adapter.dto.EParamVO
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.transmission.data.intf.AnalysisVO
 *  com.jiuqi.nr.transmission.data.service.IFileAnalysisService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.AuthorizationException
 *  org.json.JSONArray
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.data.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.io.api.ITaskDataService;
import com.jiuqi.nr.io.api.TaskServiceFactory;
import com.jiuqi.nr.io.params.input.UnitCountQueryParam;
import com.jiuqi.nr.io.params.input.UnitQueryParam;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportState;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.io.tsd.dto.AnalysisRes;
import com.jiuqi.nr.io.tsd.dto.ExportType;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.nrdx.adapter.dto.DecryptionVO;
import com.jiuqi.nr.nrdx.adapter.dto.EParamVO;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.data.NRDXExportJob;
import com.jiuqi.nr.nrdx.data.NRDXImportJob;
import com.jiuqi.nr.nrdx.data.NRDXLogExportJob;
import com.jiuqi.nr.nrdx.data.dto.ConfirmRes;
import com.jiuqi.nr.nrdx.data.dto.FileDTO;
import com.jiuqi.nr.nrdx.data.dto.FileType;
import com.jiuqi.nr.nrdx.data.dto.IConfirmParamVO;
import com.jiuqi.nr.nrdx.data.dto.UnitParamVO;
import com.jiuqi.nr.nrdx.data.nrd.INRDHelper;
import com.jiuqi.nr.nrdx.data.nrd.ImpParVO;
import com.jiuqi.nr.nrdx.data.nrd.NRDImpJob;
import com.jiuqi.nr.transmission.data.intf.AnalysisVO;
import com.jiuqi.nr.transmission.data.service.IFileAnalysisService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/tsd"})
@Api(tags={"NRDX\u6570\u636e\u5bfc\u5165\u5bfc\u51faAPI"})
public class TsdController {
    private static final Logger log = LoggerFactory.getLogger(TsdController.class);
    @Autowired
    private TaskDataFactoryManager taskDataFactoryManager;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private TaskServiceFactory taskServiceFactory;
    @Autowired
    private ImportHistoryService importHistoryService;
    @Autowired
    private INRDHelper nrdHelper;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IFileAnalysisService fileAnalysisService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewApi;
    @Autowired
    private MappingTransferService mappingTransferService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value={"/export"})
    @NRContextBuild
    public AsyncTaskInfo createExportAsyncTask(@RequestBody EParamVO eParamVO) {
        String args;
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(eParamVO.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(eParamVO.getFormSchemeKey());
        try {
            args = this.objectMapper.writeValueAsString((Object)eParamVO);
        }
        catch (Exception e) {
            log.error("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u51fa\u5931\u8d25", e);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u51fa\u5931\u8d25");
            asyncTaskInfo.setState(TaskState.ERROR);
            return asyncTaskInfo;
        }
        npRealTimeTaskInfo.setArgs(args);
        NRDXExportJob nrdxRealTimeJob = new NRDXExportJob();
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)nrdxRealTimeJob);
        String taskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setState(TaskState.WAITING);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/download"})
    public void download(@RequestParam(value="key") String key, HttpServletResponse response) throws Exception {
        block29: {
            try (ObjectStorageService service = ObjectStorageManager.getInstance().createObjectService("TEMP");){
                ObjectInfo objectInfo = service.getObjectInfo(key);
                if (objectInfo == null) {
                    response.sendError(404, "\u6587\u4ef6\u4e0d\u5b58\u5728");
                    return;
                }
                String owner = objectInfo.getOwner();
                if (Objects.equals(owner, NpContextHolder.getContext().getUserId())) {
                    String name = objectInfo.getName();
                    TsdController.respHeader(response, name, objectInfo.getSize());
                    try (ServletOutputStream out = response.getOutputStream();){
                        service.download(key, (OutputStream)out);
                        break block29;
                    }
                }
                response.sendError(403, "\u65e0\u6743\u9650\u8bbf\u95ee");
            }
        }
    }

    @PostMapping(value={"/export/log/{recKey}"})
    public AsyncTaskInfo createLogExport(@PathVariable(value="recKey") String recKey) {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(recKey);
        NRDXLogExportJob logExportJob = new NRDXLogExportJob();
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)logExportJob);
        String taskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setState(TaskState.WAITING);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/exportTypes"})
    public List<ExportType> getExportTypes() {
        ArrayList<ExportType> list = new ArrayList<ExportType>();
        List allFactory = this.taskDataFactoryManager.getAllFactory();
        for (TaskDataFactory taskDataFactory : allFactory) {
            if ("FMDM_DATA".equals(taskDataFactory.getCode())) continue;
            ExportType exportType = new ExportType();
            exportType.setName(taskDataFactory.getName());
            exportType.setDescription(taskDataFactory.getDescription());
            exportType.setCode(taskDataFactory.getCode());
            list.add(exportType);
        }
        return list;
    }

    /*
     * Exception decompiling
     */
    @PostMapping(value={"/upload"})
    public FileDTO uploadFile(@RequestParam(value="file") MultipartFile file) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    @PostMapping(value={"/decryption"})
    public Result<Void> executeDecryption(@RequestBody DecryptionVO decryptionVO) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/analysis"})
    @NRContextBuild
    public Result<AnalysisRes> analysis(@RequestBody com.jiuqi.nr.nrdx.adapter.dto.AnalysisVO analysisVO) {
        String taskKey = analysisVO.getTaskKey();
        if (!org.springframework.util.StringUtils.hasLength(taskKey)) {
            analysisVO.setTaskKey(null);
        }
        if (!org.springframework.util.StringUtils.hasLength(analysisVO.getFormSchemeKey())) {
            analysisVO.setFormSchemeKey(null);
        }
        try {
            this.fileAuth(analysisVO.getKey());
        }
        catch (FileNotFoundException e) {
            return Result.failed((String)"\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        catch (AuthorizationException e) {
            return Result.failed((String)"\u65e0\u6743\u8bbf\u95ee");
        }
        catch (Exception e) {
            log.error("\u6587\u4ef6\u5206\u6790\u5931\u8d25", e);
            return Result.failed((String)"\u6587\u4ef6\u5206\u6790\u5931\u8d25");
        }
        try {
            TransferContext context = new TransferContext(NpContextHolder.getContext().getUserId());
            context.setSkipImportModel(true);
            context.setSource("NR");
            context.setEnableFactoryPreParseImport(true);
            context.getExtInfo().put("a_args", this.objectMapper.writeValueAsString((Object)analysisVO));
            TransferFileRecorder recorder = new TransferFileRecorder(analysisVO.getKey(), (ITransferContext)context);
            try {
                TransferEngine engine = new TransferEngine();
                Desc desc = engine.getDescInfo((IFileRecorder)recorder);
                JSONArray dataArray = TransferUtils.buildTreeTableData((ITransferContext)context, (Desc)desc, (IFileRecorder)recorder);
                Object resultObj = dataArray.getJSONObject(0).getJSONArray("children").getJSONObject(0).get("a_args");
                if (!(resultObj instanceof Result)) return Result.failed((Object)new AnalysisRes(), (String)"\u9884\u89e3\u6790\u5931\u8d25");
                Result result = (Result)resultObj;
                return result;
            }
            finally {
                recorder.destroy();
            }
        }
        catch (Exception e) {
            log.error("\u9884\u89e3\u6790\u5931\u8d25", e);
            return Result.failed((String)"\u9884\u89e3\u6790\u5931\u8d25");
        }
    }

    @PostMapping(value={"/analysis/nrd"})
    public Result<AnalysisVO> analysisNrd(@RequestBody com.jiuqi.nr.nrdx.adapter.dto.AnalysisVO analysisVO) {
        try {
            this.fileAuth(analysisVO.getKey());
        }
        catch (FileNotFoundException e) {
            return Result.failed((String)"\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        catch (AuthorizationException e) {
            return Result.failed((String)"\u65e0\u6743\u8bbf\u95ee");
        }
        catch (Exception e) {
            log.error("\u6587\u4ef6\u5206\u6790\u5931\u8d25", e);
            return Result.failed((String)"\u6587\u4ef6\u5206\u6790\u5931\u8d25");
        }
        AnalysisVO vo = new AnalysisVO();
        vo.setFileKey(analysisVO.getKey());
        try {
            vo = this.fileAnalysisService.analysisParam(vo);
            Result result = new Result();
            result.setDatas((Object)vo);
            result.setCode("0");
            result.setMessage("\u5206\u6790\u6210\u529f");
            return result;
        }
        catch (Exception e) {
            log.error("\u6587\u4ef6\u5206\u6790\u5931\u8d25", e);
            return Result.failed((String)e.getMessage());
        }
    }

    /*
     * Exception decompiling
     */
    public Result<AnalysisRes> analysisNR(@RequestBody com.jiuqi.nr.nrdx.adapter.dto.AnalysisVO analysisVO) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @PostMapping(value={"/confirm"})
    public ConfirmRes confirmImportTaskData(@RequestBody IConfirmParamVO paramVO) {
        ITaskDataService taskDataService = this.taskServiceFactory.getTaskDataService();
        UnitCountQueryParam countQueryParam = new UnitCountQueryParam();
        countQueryParam.setDwSchemeKey(paramVO.getDwSchemeKey());
        countQueryParam.setEntityIds(paramVO.getUnitKeys());
        Map unitEntityDataRowTypeEnumIntegerMap = taskDataService.countUnitByParam(countQueryParam);
        ConfirmRes confirmRes = new ConfirmRes();
        confirmRes.setUpdateUnitCount(unitEntityDataRowTypeEnumIntegerMap.getOrDefault(EntityDataType.EXIST, 0));
        confirmRes.setNoneExistUnitCount(unitEntityDataRowTypeEnumIntegerMap.getOrDefault(EntityDataType.NOT_EXIST, 0));
        return confirmRes;
    }

    @PostMapping(value={"/import"})
    @NRContextBuild
    public AsyncTaskInfo importTaskData(@RequestBody IParamVO paramVO) {
        String args;
        try {
            this.fileAuth(paramVO.getKey());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(paramVO.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(paramVO.getFormSchemeKey());
        try {
            args = this.objectMapper.writeValueAsString((Object)paramVO);
        }
        catch (Exception e) {
            log.error("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25", e);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25");
            asyncTaskInfo.setState(TaskState.ERROR);
            return asyncTaskInfo;
        }
        npRealTimeTaskInfo.setArgs(args);
        NRDXImportJob nrdxImportJob = new NRDXImportJob();
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)nrdxImportJob);
        String taskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setState(TaskState.WAITING);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        ImportHistory importHistory = new ImportHistory();
        importHistory.setRecKey(taskId);
        importHistory.setParamType("NRDX");
        importHistory.setTaskKey(paramVO.getTaskKey());
        List taskOrgLinkDefines = this.runTimeViewApi.listTaskOrgLinkByTask(paramVO.getTaskKey());
        if (taskOrgLinkDefines != null && taskOrgLinkDefines.size() > 1) {
            importHistory.setCaliberEntity(paramVO.getContextEntityId());
        }
        importHistory.setFormSchemeKey(paramVO.getFormSchemeKey());
        importHistory.setDataTime(paramVO.getPeriodValue());
        importHistory.setMappingKey(paramVO.getMappingKey());
        importHistory.setState(Integer.valueOf(ImportState.WAITING.getCode()));
        importHistory.setCreateTime(new Date(System.currentTimeMillis()));
        importHistory.setCreateUser(NpContextHolder.getContext().getUserId());
        this.importHistoryService.createImportHistory(importHistory);
        return asyncTaskInfo;
    }

    @PostMapping(value={"/import-nrd"})
    public AsyncTaskInfo importNrdTaskData(@RequestBody ImpParVO paramVO) {
        String args;
        try {
            this.fileAuth(paramVO.getFileKey());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(paramVO.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(paramVO.getFormSchemeKey());
        try {
            args = this.objectMapper.writeValueAsString((Object)paramVO);
        }
        catch (Exception e) {
            log.error("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25", e);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25");
            asyncTaskInfo.setState(TaskState.ERROR);
            return asyncTaskInfo;
        }
        npRealTimeTaskInfo.setArgs(args);
        NRDImpJob job = new NRDImpJob();
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)job);
        String taskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setState(TaskState.WAITING);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        ImportHistory importHistory = new ImportHistory();
        importHistory.setRecKey(taskId);
        importHistory.setParamType(FileType.NRD.getValue());
        importHistory.setTaskKey(paramVO.getTaskKey());
        importHistory.setFormSchemeKey(paramVO.getFormSchemeKey());
        importHistory.setDataTime(paramVO.getPeriodValue());
        importHistory.setState(Integer.valueOf(ImportState.WAITING.getCode()));
        importHistory.setCreateTime(new Date(System.currentTimeMillis()));
        importHistory.setCreateUser(NpContextHolder.getContext().getUserId());
        this.importHistoryService.createImportHistory(importHistory);
        return asyncTaskInfo;
    }

    @GetMapping(value={"/list"})
    public List<IEntityDataDTO> getUnitByParam(@ModelAttribute UnitParamVO paramVO) {
        ITaskDataService taskDataService = this.taskServiceFactory.getTaskDataService();
        UnitQueryParam unitQueryParam = new UnitQueryParam();
        unitQueryParam.setDwSchemeKey(paramVO.getDwSchemeKey());
        unitQueryParam.setEntityIds(paramVO.getEntityIds());
        if (paramVO.getType() == EntityDataType.EXIST.getCode()) {
            unitQueryParam.setTypes(new EntityDataType[]{EntityDataType.EXIST});
        } else if (paramVO.getType() == EntityDataType.NOT_EXIST.getCode()) {
            unitQueryParam.setTypes(new EntityDataType[]{EntityDataType.NOT_EXIST});
        } else {
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b" + paramVO.getType());
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(paramVO.getPageNum() - 1);
        pageInfo.setRowsPerPage(paramVO.getPageSize());
        unitQueryParam.setPageInfo(pageInfo);
        return taskDataService.getUnitByParam(unitQueryParam);
    }

    @GetMapping(value={"/cryptoSwitch"})
    public boolean getTaskCryptoSwitch(@RequestParam(name="taskKey") String taskKey) throws IOException {
        if (!org.springframework.util.StringUtils.hasText(taskKey)) {
            return false;
        }
        String value = this.taskOptionController.getValue(taskKey, "IO_CRYPTO_SWITCH");
        return "1".equals(value);
    }

    @GetMapping(value={"/flowsTypeSwitch"})
    public boolean getTaskFlowsTypeSwitch(@RequestParam(name="taskKey") String taskKey) throws IOException {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return false;
        }
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if (flowsSetting == null) {
            return false;
        }
        return flowsSetting.getFlowsType() != null && flowsSetting.getFlowsType() == FlowsType.DEFAULT;
    }

    public void fileAuth(String fileKey) throws Exception {
        try (ObjectStorageService service = ObjectStorageManager.getInstance().createObjectService("TEMP");){
            ObjectInfo objectInfo = service.getObjectInfo(fileKey);
            if (objectInfo == null) {
                throw new FileNotFoundException(fileKey);
            }
            if (!Objects.equals(objectInfo.getOwner(), NpContextHolder.getContext().getUserId())) {
                throw new AuthorizationException("\u65e0\u6743\u8bbf\u95ee");
            }
        }
    }

    protected static void respHeader(HttpServletResponse response, String fileName, long size) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + fileName.replaceAll("\\+", "%20"));
            response.setContentLengthLong(size);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }

    @GetMapping(value={"/get-mapping-scheme/{formSchemeKey}"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6NRDX\u6620\u5c04\u65b9\u6848")
    public List<MappingScheme> getMappingSchemeBy(@PathVariable String formSchemeKey) {
        List<Object> result = new ArrayList<MappingScheme>();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            result = this.mappingTransferService.getMappingSchemeByFormScheme(formSchemeKey, "NRDX");
        }
        return result;
    }
}


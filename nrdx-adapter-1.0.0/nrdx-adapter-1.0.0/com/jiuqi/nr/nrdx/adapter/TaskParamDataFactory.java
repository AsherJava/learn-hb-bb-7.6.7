/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferImportResult
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.helper.ZipInputStreamHelper
 *  com.jiuqi.bi.transfer.engine.helper.ZipOutputStreamHelper
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.io.api.ITaskDataService
 *  com.jiuqi.nr.io.api.TaskServiceFactory
 *  com.jiuqi.nr.io.tsd.dto.AnalysisParam
 *  com.jiuqi.nr.io.tsd.dto.EParam
 *  com.jiuqi.nr.io.tsd.dto.IParam
 *  com.jiuqi.nr.io.tsd.dto.IResult
 *  org.json.JSONObject
 */
package com.jiuqi.nr.nrdx.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferImportResult;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.helper.ZipInputStreamHelper;
import com.jiuqi.bi.transfer.engine.helper.ZipOutputStreamHelper;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.io.api.ITaskDataService;
import com.jiuqi.nr.io.api.TaskServiceFactory;
import com.jiuqi.nr.io.tsd.dto.AnalysisParam;
import com.jiuqi.nr.io.tsd.dto.EParam;
import com.jiuqi.nr.io.tsd.dto.IParam;
import com.jiuqi.nr.io.tsd.dto.IResult;
import com.jiuqi.nr.nrdx.adapter.ZipInFileReader;
import com.jiuqi.nr.nrdx.adapter.ZipOutFileWriter;
import com.jiuqi.nr.nrdx.adapter.dto.AnalysisVO;
import com.jiuqi.nr.nrdx.adapter.dto.EParamVO;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.dto.IResultVO;
import com.jiuqi.nr.nrdx.adapter.impl.FileRecorder2Finder;
import com.jiuqi.nr.nrdx.adapter.param.common.ExportType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid;
import com.jiuqi.nr.nrdx.adapter.param.service.IParamIOService;
import com.jiuqi.nr.nrdx.adapter.param.utils.ParamNodeUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskParamDataFactory
extends TransferFactory {
    private static final Logger log = LoggerFactory.getLogger(TaskParamDataFactory.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private TaskServiceFactory taskServiceFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IParamIOService paramIOService;

    public String getId() {
        return "TASK_DATA";
    }

    public String getTitle() {
        return "\u62a5\u8868\u4efb\u52a1\u53c2\u6570\u548c\u6570\u636e";
    }

    public String getModuleId() {
        return "NR";
    }

    public String getModuleTitle() {
        return "\u62a5\u8868";
    }

    public int getOrder() {
        return 100;
    }

    public List<String> getDependenceFactoryIds() {
        return Collections.emptyList();
    }

    public boolean supportExport(String guid) {
        return true;
    }

    public boolean supportExportData(String guid) {
        return true;
    }

    public boolean hasPublisher() {
        return false;
    }

    public List<NameMapperBean> handleMapper() throws TransferException {
        return Collections.emptyList();
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> nameMapperBeans) throws TransferException {
        return Collections.emptyList();
    }

    public String getModifiedTime(ITransferContext context, String guid) throws TransferException {
        return null;
    }

    public String getRootParentId(ITransferContext context) {
        return null;
    }

    public boolean supportHandleFile() {
        return true;
    }

    public List<FolderNode> getFolderNodes(ITransferContext context, String parentGuid) throws TransferException {
        try {
            if (parentGuid == null) {
                List rootTasks = this.runTimeViewController.getAllReportTaskDefines();
                return ParamNodeUtil.taskToFolderNodes(rootTasks, null);
            }
            return Collections.emptyList();
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public List<BusinessNode> getBusinessNodes(ITransferContext context, String parentGuid) throws TransferException {
        if (parentGuid == null) {
            return Collections.emptyList();
        }
        ParamGuid parse = NrdxGuidParse.parseId(parentGuid);
        String key = parse.getKey();
        if (parse.isBusiness()) {
            return Collections.emptyList();
        }
        NrdxParamNodeType nrdxParamNodeType = parse.getNrdxParamNodeType();
        try {
            TaskDefine taskDefine;
            if (nrdxParamNodeType == NrdxParamNodeType.TASK && (taskDefine = this.runTimeViewController.queryTaskDefine(key)) != null) {
                List designFormSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(key);
                return ParamNodeUtil.metaToBusinessNodes(designFormSchemeDefines, parentGuid, null, NrdxParamNodeType.FORMSCHEME);
            }
            return Collections.emptyList();
        }
        catch (Exception e) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25", (Throwable)e);
        }
    }

    public BusinessNode getBusinessByTitleAndNameAndType(ITransferContext context, String title, String name, String type, String subType) throws TransferException {
        return null;
    }

    public BusinessNode getBusinessNode(ITransferContext context, String nodeId) throws TransferException {
        if (nodeId == null) {
            return null;
        }
        ParamGuid parse = NrdxGuidParse.parseId(nodeId);
        String formSchemeKey = parse.getKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            return ParamNodeUtil.metaToBusinessNode(formScheme, NrdxGuidParse.toBusinessId(NrdxParamNodeType.TASK, formScheme.getTaskKey()), null, NrdxParamNodeType.FORMSCHEME);
        }
        BusinessNode businessNode = new BusinessNode();
        businessNode.setTitle("");
        businessNode.setName("");
        businessNode.setType("");
        businessNode.setSubType("");
        businessNode.setGuid(nodeId);
        return businessNode;
    }

    public String getModifiedTime(String guid) {
        return "";
    }

    public List<ResItem> getRelatedBusiness(String guid) throws TransferException {
        return null;
    }

    public IMetaFinder createMetaFinder(String mapperType) {
        return null;
    }

    public IPublisher createPublisher() throws TransferException {
        return null;
    }

    public IConfigTransfer createConfigTransfer() throws TransferException {
        return null;
    }

    public IDataTransfer createDataTransfer(String type) throws TransferException {
        return null;
    }

    public IModelTransfer createModelTransfer(String type) throws TransferException {
        return null;
    }

    public String getIcon() throws TransferException {
        return "";
    }

    public AbstractFolderManager getFolderManager() {
        return null;
    }

    public AbstractBusinessManager getBusinessManager() {
        return null;
    }

    public boolean isDisplay() {
        return false;
    }

    public boolean supportPreParseImport() {
        return true;
    }

    public void preParseImportModel(ITransferContext context, Desc desc, IFileRecorder recorder, JSONObject resJSON) throws TransferException {
        InputStream defaultFile = recorder.getDataStream("/1/1.txt");
    }

    public void handleExportModel(ITransferContext context, ZipOutputStreamHelper outputHelper, List<FolderNode> rootFolderNodeList, List<BusinessNode> rootBusinessNodeList) throws IOException {
        if (!this.doParamCheck(context)) {
            return;
        }
        Object o = context.getExtInfo().get("e_args");
        if (o == null) {
            throw new IllegalStateException("\u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570e_args");
        }
        EParamVO eParamVO = (EParamVO)o;
        if (!eParamVO.isExportParam()) {
            return;
        }
        NrdxTransferContext transferContext = new NrdxTransferContext();
        String rootFilePath = eParamVO.getRootFilePath();
        transferContext.setPath(rootFilePath);
        transferContext.setResItems(eParamVO.getResItems());
        outputHelper.addFile("/1/", "1.txt", "1".getBytes(StandardCharsets.UTF_8));
    }

    public void handleImportModel(ZipInputStreamHelper inputHelper, IImportContext importContext, BusinessNode businessNode) throws Exception {
        if (!this.doParamCheck((ITransferContext)importContext)) {
            return;
        }
        byte[] bytes = inputHelper.readFile("/1/", "1.txt");
        String folderGuid = importContext.getFolderGuid();
    }

    private boolean doParamCheck(ITransferContext context) {
        Object exportType = context.getExtInfo().get("handle_type");
        if (exportType == null) {
            return false;
        }
        String exportTypeString = (String)exportType;
        if (!StringUtils.isEmpty((String)exportTypeString)) {
            ExportType exportTypeEnum = ExportType.valueOf(exportTypeString);
            return ExportType.ONLY_DATA != exportTypeEnum;
        }
        return true;
    }

    public void preParseImportData(ITransferContext context, Desc desc, IFileRecorder recorder, JSONObject resJSON) {
        AnalysisVO analysisVO;
        Object aeqArgs = context.getExtInfo().get("a_args");
        if (aeqArgs == null) {
            throw new IllegalStateException("\u9884\u89e3\u6790\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570a_args");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            analysisVO = (AnalysisVO)objectMapper.readValue(aeqArgs.toString(), AnalysisVO.class);
        }
        catch (IOException e) {
            throw new IllegalStateException("\u9884\u89e3\u6790\u53c2\u6570\u89e3\u6790\u5931\u8d25,\u68c0\u67e5\u53c2\u6570" + aeqArgs);
        }
        ITaskDataService taskDataService = this.taskServiceFactory.getTaskDataService();
        FileRecorder2Finder finder = new FileRecorder2Finder(recorder);
        AnalysisParam analysisParam = new AnalysisParam();
        analysisParam.setTaskKey(analysisVO.getTaskKey());
        analysisParam.setFormSchemeKey(analysisVO.getFormSchemeKey());
        analysisParam.setMappingKey(analysisVO.getMappingKey());
        analysisParam.setAnalysisDw(!StringUtils.isEmpty((String)analysisVO.getTaskKey()));
        analysisParam.setAnalysisDim(!StringUtils.isEmpty((String)analysisVO.getTaskKey()));
        Result analysisResResult = taskDataService.preAnalysis(analysisParam, (FileFinder)finder);
        resJSON.put("a_args", (Object)analysisResResult);
    }

    public void handleExportData(ITransferContext context, ZipOutputStreamHelper outputHelper, List<BusinessNode> businessNodeList) {
        EParamVO eParamVO;
        Object o = context.getExtInfo().get("e_args");
        if (o == null) {
            log.info("handleExportData \u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a");
            throw new IllegalStateException("\u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570e_args");
        }
        if (log.isDebugEnabled()) {
            log.debug("handleExportData \u53c2\u6570: {}", o);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String args = o.toString();
        try {
            eParamVO = (EParamVO)objectMapper.readValue(args, EParamVO.class);
        }
        catch (IOException e) {
            throw new IllegalStateException("\u5bfc\u51fa\u53c2\u89e3\u6790\u5931\u8d25,\u68c0\u67e5\u53c2\u6570" + args);
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(eParamVO.getTaskKey());
        log.info("\u5f00\u59cb\u6267\u884c\u62a5\u8868\u76f8\u5173\u6570\u636e\u5bfc\u51fa,\u4efb\u52a1:{}", (Object)taskDefine.getTitle());
        EParam eParam = new EParam();
        eParam.setFormSchemeKey(eParamVO.getFormSchemeKey());
        if (!StringUtils.isEmpty((String)eParamVO.getMappingKey())) {
            eParam.setMappingSchemeKey(eParamVO.getMappingKey());
        }
        eParam.setTaskKey(eParamVO.getTaskKey());
        if (StringUtils.isEmpty((String)eParamVO.getFormKeys())) {
            if (log.isDebugEnabled()) {
                log.debug("\u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570 \u53c2\u6570: {}", (Object)args);
            }
            throw new IllegalStateException("\u5bfc\u51fa\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570e_args");
        }
        Map<String, DimensionValue> masterKeys = eParamVO.getMasterKeys();
        DimensionValue dimensionValue = masterKeys.get("DATATIME");
        String periodValue = dimensionValue.getValue();
        if (StringUtils.isEmpty((String)periodValue)) {
            log.info("\u5bfc\u51fa\u65f6\u671f\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570 \u53c2\u6570: {}", (Object)args);
            throw new IllegalStateException("\u5bfc\u51fa\u65f6\u671f\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570");
        }
        if (periodValue.split(";").length > 1) {
            log.info("\u5bfc\u51fa\u65f6\u671f\u4e0d\u652f\u6301\u591a\u4e2a\u503c,\u68c0\u67e5\u53c2\u6570 \u53c2\u6570: {}", (Object)args);
            throw new IllegalStateException("\u5bfc\u51fa\u65f6\u671f\u4e0d\u652f\u6301\u591a\u4e2a\u503c,\u68c0\u67e5\u53c2\u6570");
        }
        eParam.setPeriodValue(periodValue);
        ArrayList<String> formKeys = new ArrayList<String>();
        List formDefines = eParamVO.getFormKeys().equals("*") ? this.runTimeViewController.queryAllFormDefinesByFormScheme(eParam.getFormSchemeKey()) : this.runTimeViewController.queryFormsById(Arrays.asList(eParamVO.getFormKeys().split(";")));
        log.info("\u5bfc\u51fa\u62a5\u8868,\u62a5\u8868\u6570\u91cf:{}", (Object)formDefines.size());
        for (FormDefine formDefine : formDefines) {
            List<String> exportTypes;
            formKeys.add(formDefine.getKey());
            if (formDefine.getFormType() != FormType.FORM_TYPE_FMDM && formDefine.getFormType() != FormType.FORM_TYPE_NEWFMDM || !(exportTypes = eParamVO.getExportTypes()).contains("FORMDATA")) continue;
            exportTypes.add("FMDM_DATA");
        }
        log.info("\u5bfc\u51fa\u7c7b\u578b :{}", (Object)String.join((CharSequence)",", eParamVO.getExportTypes()));
        eParam.setFormKeys(formKeys);
        eParam.setMasterKeys(masterKeys);
        eParam.setExportTypes(eParamVO.getExportTypes());
        eParam.setExportAttachments(eParamVO.isExpAttachment());
        ITaskDataService taskDataService = this.taskServiceFactory.getTaskDataService();
        taskDataService.exportTaskData(eParam, (FileWriter)new ZipOutFileWriter(outputHelper), context.getProgressMonitor());
        log.info("\u62a5\u8868\u6570\u636e\u8d44\u6e90\u5bfc\u51fa\u6267\u884c\u7ed3\u675f");
    }

    public void handleImportData(ZipInputStreamHelper inputHelper, IImportContext context, BusinessNode srcBusinessNode, BusinessNode targetBusinessNode) throws Exception {
        String resultString;
        String args;
        ObjectMapper objectMapper;
        IParamVO iParamVO;
        Object o = context.getExtInfo().get("i_args");
        if (o == null) {
            throw new IllegalStateException("\u5bfc\u5165\u53c2\u6570\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570i_args");
        }
        if (log.isDebugEnabled()) {
            log.debug("handleImportData \u53c2\u6570: {}", o);
        }
        if ((iParamVO = (IParamVO)(objectMapper = new ObjectMapper()).readValue(args = o.toString(), IParamVO.class)).getRecKey() == null) {
            throw new IllegalStateException("\u5bfc\u5165\u53c2\u6570\u8bb0\u5f55key\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570: " + args);
        }
        log.info("\u5f00\u59cb\u6267\u884c\u62a5\u8868\u76f8\u5173\u6570\u636e\u5bfc\u5165");
        IParam iParam = iParamVO.toIParam();
        ITaskDataService taskDataService = this.taskServiceFactory.getTaskDataService();
        IResult importResult = taskDataService.importTaskData(iParam, (FileFinder)new ZipInFileReader(inputHelper), context.getProgressMonitor());
        try {
            IResultVO resultVO = new IResultVO(importResult);
            resultString = objectMapper.writeValueAsString((Object)resultVO);
        }
        catch (JsonProcessingException e) {
            throw new IllegalStateException("\u5bfc\u5165\u7ed3\u679c\u53c2\u6570\u4e0d\u652f\u6301\u5e8f\u5217\u5316\uff0c\u8bf7\u68c0\u67e5\uff01", e);
        }
        Map facoryMap = context.getImportResult().getOrDefault("TASK_DATA", new HashMap());
        facoryMap.put("i_detail", TransferImportResult.SUCCESS((String)resultString));
        context.getImportResult().putIfAbsent("TASK_DATA", facoryMap);
        log.info("\u6267\u884c\u62a5\u8868\u76f8\u5173\u6570\u636e\u5bfc\u5165\u7ed3\u675f");
    }
}


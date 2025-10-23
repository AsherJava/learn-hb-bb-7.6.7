/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.nrdx.adapter.dto.EParamVO
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.nrdx.adapter.exception.NrdxTransferException
 *  com.jiuqi.nr.nrdx.adapter.param.common.ExportType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxFormulaAndPrintGuidKeyParse
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid
 *  com.jiuqi.nr.nrdx.adapter.param.service.IParamIOService
 *  com.jiuqi.nr.tds.Costs
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.nrdx.adapter.dto.EParamVO;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.exception.NrdxTransferException;
import com.jiuqi.nr.nrdx.adapter.param.common.ExportType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxFormulaAndPrintGuidKeyParse;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid;
import com.jiuqi.nr.nrdx.adapter.param.service.IParamIOService;
import com.jiuqi.nr.nrdx.param.dto.FormulaParamDTO;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.dto.PrintParamDTO;
import com.jiuqi.nr.nrdx.param.dto.TaskParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import com.jiuqi.xlib.utils.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ParamIOServiceImpl
implements IParamIOService {
    private static Logger logger = LoggerFactory.getLogger(ParamIOServiceImpl.class);
    public static final String TEMP_DIR = Costs.ROOTPATH + Costs.FILE_SEPARATOR + "nrdx" + Costs.FILE_SEPARATOR + "param" + Costs.FILE_SEPARATOR;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    private List<AbstractParamTransfer> abstractParamTransfers;
    private Map<String, AbstractParamTransfer> abstractParamTransferMap;

    @Autowired
    private void setGatherList(List<AbstractParamTransfer> abstractParamTransferList) {
        this.abstractParamTransfers = abstractParamTransferList;
        this.abstractParamTransferMap = abstractParamTransferList.stream().collect(Collectors.toMap(ITransferModel::code, a -> a, (k1, k2) -> k1));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void importFile(IParamVO paramVO, AsyncTaskMonitor monitor) throws TransferException {
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        context.getExtInfo().put("handle_type", ExportType.ONLY_PARAM.name());
        context.setSkipImportModel(true);
        context.setEnableFactoryHandleFile(true);
        context.setSource("NR");
        context.setProgressMonitor(null);
        TransferEngine engine = new TransferEngine();
        TransferFileRecorder recorder = new TransferFileRecorder(paramVO.getKey(), (ITransferContext)context);
        Desc desc = engine.getDescInfo((IFileRecorder)recorder);
        try {
            engine.importProcess((ITransferContext)context, (IFileRecorder)recorder, desc, null);
        }
        finally {
            recorder.destroy();
        }
    }

    public void importTaskParam(NrdxTransferContext transferContext, AsyncTaskMonitor monitor) throws NrdxTransferException {
        List resItems = transferContext.getResItems();
        ParamGuid parse = NrdxGuidParse.parse((String)((ResItem)resItems.get(0)).getGuid());
        HashMap result = new HashMap();
        NrdxTransferContext context = new NrdxTransferContext(transferContext.getPath(), transferContext.getMode(), transferContext.getLog());
        AbstractParamTransfer abstractParamTransfer = this.abstractParamTransferMap.get(parse.getNrdxParamNodeType().name());
        ParamDTO param = new ParamDTO(parse.getKey(), parse.getNrdxParamNodeType());
        context.setPath(transferContext.getPath() + parse.getKey());
        abstractParamTransfer.importModel(param, context, monitor);
    }

    public File exportFile(NrdxTransferContext transferContext, AsyncTaskMonitor monitor) throws NrdxTransferException {
        if (CollectionUtils.isEmpty(transferContext.getResItems())) {
            throw new NrdxTransferException("\u5bfc\u51fa\u6570\u636e\u5165\u53c2\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u68c0\u67e5\uff01");
        }
        List formSchemeResItem = transferContext.getResItems().stream().filter(a -> "TASK_DATA".equals(a.getFactoryId())).collect(Collectors.toList());
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        EParamVO eParam = new EParamVO();
        eParam.setResItems(formSchemeResItem);
        eParam.setMode(transferContext.getMode());
        eParam.setExportParam(true);
        context.setEnableFactoryHandleFile(true);
        context.getExtInfo().put("e_args", eParam);
        context.getExtInfo().put("handle_type", ExportType.ONLY_PARAM.name());
        LocalTime hour = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
        String hourStr = hour.format(Costs.FORMATTER);
        if (!StringUtils.hasText((String)transferContext.getPath())) {
            transferContext.setPath(TEMP_DIR + LocalDate.now() + Costs.FILE_SEPARATOR + hourStr + Costs.FILE_SEPARATOR + OrderGenerator.newOrder() + Costs.FILE_SEPARATOR);
        }
        eParam.setRootFilePath(transferContext.getPath());
        String fileName = "\u4efb\u52a1\u6570\u636e" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".NRDX";
        File file = new File(transferContext.getPath() + fileName);
        Costs.createPathIfNotExists((Path)new File(transferContext.getPath()).toPath());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            TransferEngine engine = new TransferEngine();
            engine.exportProcess((ITransferContext)context, transferContext.getResItems(), (OutputStream)fileOutputStream);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NrdxTransferException(e.getMessage());
        }
        return file;
    }

    public void exportTaskParam(NrdxTransferContext transferContext, AsyncTaskMonitor monitor) throws Exception {
        List resItems = transferContext.getResItems();
        if (CollectionUtils.isEmpty(resItems)) {
            return;
        }
        HashMap<String, Map> typeToParams = new HashMap<String, Map>();
        for (ResItem resItem : resItems) {
            ParamGuid parse = NrdxGuidParse.parse((String)resItem.getGuid());
            if (parse == null) continue;
            typeToParams.computeIfAbsent(parse.getNrdxParamNodeType().name(), key -> new HashMap()).put(parse.getKey(), parse);
        }
        String rootPath = transferContext.getPath();
        NrdxTransferContext context = new NrdxTransferContext(rootPath, transferContext.getMode(), transferContext.getLog());
        Map taskParamGuidsMap = (Map)typeToParams.get(NrdxParamNodeType.TASK);
        if (!CollectionUtils.isEmpty(taskParamGuidsMap)) {
            logger.info("\u5f00\u59cb\u5bfc\u51fa\u4efb\u52a1\u53c2\u6570");
            AbstractParamTransfer taskAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.TASK.getCode());
            List<String> exportTaskKey = taskParamGuidsMap.values().stream().map(ParamGuid::getKey).collect(Collectors.toList());
            Map taskGroupParamGuidsMap = (Map)typeToParams.get(NrdxParamNodeType.TASKGROUP);
            List<String> exportTaskGroupKey = taskGroupParamGuidsMap.values().stream().map(ParamGuid::getKey).collect(Collectors.toList());
            TaskParamDTO taskParam = new TaskParamDTO(exportTaskGroupKey, exportTaskKey, NrdxParamNodeType.TASK);
            context.setPath(rootPath);
            taskAbstractParamTransfer.exportModel(taskParam, context, monitor);
            for (Map.Entry taskParamGuidEntry : taskParamGuidsMap.entrySet()) {
                ParamGuid taskParamGuid = (ParamGuid)taskParamGuidEntry.getValue();
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskParamGuid.getKey());
                Map formSchemeParamGuidMap = (Map)typeToParams.get(NrdxParamNodeType.FORMSCHEME);
                if (!CollectionUtils.isEmpty(formSchemeParamGuidMap)) {
                    List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskParamGuid.getKey());
                    List formSchemeDefinesForTask = formSchemeDefines.stream().filter(a -> formSchemeParamGuidMap.get(a.getKey()) != null).collect(Collectors.toList());
                    logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u65b9\u6848\u53c2\u6570");
                    if (CollectionUtils.isEmpty(formSchemeDefinesForTask)) continue;
                    AbstractParamTransfer formSchemeAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.TASK.getCode());
                    List<String> formSchemeKeyForTask = formSchemeDefinesForTask.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    ParamDTO formSchemeParam = new ParamDTO(formSchemeKeyForTask, NrdxParamNodeType.FORMSCHEME);
                    String taskPath = rootPath + Costs.FILE_SEPARATOR + taskDefine.getTitle();
                    context.setPath(taskPath);
                    formSchemeAbstractParamTransfer.exportModel(formSchemeParam, context, monitor);
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefinesForTask) {
                        String formSchemePath = taskPath + Costs.FILE_SEPARATOR + formSchemeDefine.getTitle();
                        Map formGroupTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.FORMGROUP);
                        if (!CollectionUtils.isEmpty(formGroupTransferGuids)) {
                            logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u5206\u7ec4\u53c2\u6570");
                            List allFormGroupsInFormScheme = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeDefine.getKey());
                            List formGroupsDefinesForFormScheme = allFormGroupsInFormScheme.stream().filter(a -> formGroupTransferGuids.get(a.getKey()) != null).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(formGroupsDefinesForFormScheme)) {
                                String formFolderPath = formSchemePath + Costs.FILE_SEPARATOR + "\u8868\u5355";
                                AbstractParamTransfer formGroupAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.FORMGROUP.getCode());
                                List<String> formGroupKeyForFormScheme = formGroupsDefinesForFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                                ParamDTO formGroupParam = new ParamDTO(formGroupKeyForFormScheme, NrdxParamNodeType.FORMGROUP);
                                context.setPath(formFolderPath);
                                formGroupAbstractParamTransfer.exportModel(formGroupParam, context, monitor);
                                for (FormGroupDefine formGroupDefine : formGroupsDefinesForFormScheme) {
                                    Map formTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.FORM);
                                    if (CollectionUtils.isEmpty(formGroupTransferGuids)) continue;
                                    logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u53c2\u6570");
                                    List allFormsInFormGroup = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                                    List formDefinesForFormScheme = allFormsInFormGroup.stream().filter(a -> formTransferGuids.get(a.getKey()) != null).collect(Collectors.toList());
                                    if (CollectionUtils.isEmpty(formDefinesForFormScheme)) continue;
                                    String formGroupPath = formFolderPath + Costs.FILE_SEPARATOR + formGroupDefine.getTitle();
                                    AbstractParamTransfer formAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.FORM.getCode());
                                    List<String> formKeyForFormGroup = formDefinesForFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                                    ParamDTO formParam = new ParamDTO(formKeyForFormGroup, NrdxParamNodeType.FORMGROUP);
                                    context.setPath(formFolderPath);
                                    formAbstractParamTransfer.exportModel(formParam, context, monitor);
                                }
                            }
                        } else {
                            logger.info("\u6ca1\u6709\u62a5\u8868\u5206\u7ec4\u53c2\u6570\u8981\u5bfc\u51fa");
                        }
                        Map formulaSchemeTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.FORMULASCHEME);
                        if (!CollectionUtils.isEmpty(formulaSchemeTransferGuids)) {
                            logger.info("\u5f00\u59cb\u5bfc\u51fa\u516c\u5f0f\u65b9\u6848\u53c2\u6570");
                            List allFormulasSchemesInFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
                            List formulaSchemeDefinesForFormScheme = allFormulasSchemesInFormScheme.stream().filter(a -> formulaSchemeTransferGuids.get(a.getKey()) != null).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(formulaSchemeDefinesForFormScheme)) {
                                String formulaFolderPath = formSchemePath + Costs.FILE_SEPARATOR + "\u516c\u5f0f";
                                AbstractParamTransfer formulaSchemeAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.FORMULASCHEME.getCode());
                                List<String> formulaSchemeForFormScheme = formulaSchemeDefinesForFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                                ParamDTO formulaSchemeParam = new ParamDTO(formulaSchemeForFormScheme, NrdxParamNodeType.FORMULASCHEME);
                                context.setPath(formulaFolderPath);
                                formulaSchemeAbstractParamTransfer.exportModel(formulaSchemeParam, context, monitor);
                                List allFormsInFormScheme = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
                                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefinesForFormScheme) {
                                    Map formulaFormTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.FORMULAFORM);
                                    if (CollectionUtils.isEmpty(formulaFormTransferGuids)) continue;
                                    logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u516c\u5f0f\u53c2\u6570");
                                    List formDefinesForFormScheme = allFormsInFormScheme.stream().filter(a -> formulaFormTransferGuids.get(NrdxFormulaAndPrintGuidKeyParse.toFormulaId((String)formulaSchemeDefine.getFormSchemeKey(), (String)a.getKey())) != null).collect(Collectors.toList());
                                    if (CollectionUtils.isEmpty(formDefinesForFormScheme)) continue;
                                    String formulaSchemePath = formulaFolderPath + Costs.FILE_SEPARATOR + formulaSchemeDefine.getTitle();
                                    AbstractParamTransfer formulaFormAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.FORMULAFORM.getCode());
                                    List<String> formKeyForFormGroup = formDefinesForFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                                    FormulaParamDTO formParam = new FormulaParamDTO(formulaSchemeDefine.getKey(), formKeyForFormGroup, NrdxParamNodeType.FORMULAFORM);
                                    context.setPath(formulaSchemePath);
                                    formulaFormAbstractParamTransfer.exportModel(formParam, context, monitor);
                                }
                            }
                        } else {
                            logger.info("\u6ca1\u6709\u62a5\u8868\u5206\u7ec4\u53c2\u6570\u8981\u5bfc\u51fa");
                        }
                        Map printSchemeTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.PRINTSCHEME);
                        if (!CollectionUtils.isEmpty(printSchemeTransferGuids)) {
                            logger.info("\u5f00\u59cb\u5bfc\u51fa\u6253\u5370\u65b9\u6848\u53c2\u6570");
                            List allPrintSchemesInFormScheme = this.printRunTimeController.getAllPrintSchemeByFormScheme(formSchemeDefine.getKey());
                            List printSchemeDefinesForFormScheme = allPrintSchemesInFormScheme.stream().filter(a -> printSchemeTransferGuids.get(a.getKey()) != null).collect(Collectors.toList());
                            if (CollectionUtils.isEmpty(printSchemeDefinesForFormScheme)) continue;
                            String printFolderPath = formSchemePath + Costs.FILE_SEPARATOR + "\u6253\u5370";
                            AbstractParamTransfer printSchemeAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.PRINTSCHEME.getCode());
                            List<String> printSchemeForFormScheme = printSchemeDefinesForFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                            ParamDTO formulaSchemeParam = new ParamDTO(printSchemeForFormScheme, NrdxParamNodeType.PRINTSCHEME);
                            context.setPath(printFolderPath);
                            printSchemeAbstractParamTransfer.exportModel(formulaSchemeParam, context, monitor);
                            List allFormsInFormScheme = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
                            for (PrintTemplateSchemeDefine printTemplateSchemeDefine : printSchemeDefinesForFormScheme) {
                                Map printFormTransferGuids = (Map)typeToParams.get(NrdxParamNodeType.PRINTTEMPLATE);
                                if (CollectionUtils.isEmpty(printFormTransferGuids)) continue;
                                logger.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\u516c\u5f0f\u53c2\u6570");
                                List formDefinesForPrintScheme = allFormsInFormScheme.stream().filter(a -> printFormTransferGuids.get(NrdxFormulaAndPrintGuidKeyParse.toFormulaId((String)printTemplateSchemeDefine.getFormSchemeKey(), (String)a.getKey())) != null).collect(Collectors.toList());
                                if (CollectionUtils.isEmpty(formDefinesForPrintScheme)) continue;
                                String formulaSchemePath = printFolderPath + Costs.FILE_SEPARATOR + printTemplateSchemeDefine.getTitle();
                                AbstractParamTransfer formulaFormAbstractParamTransfer = this.abstractParamTransferMap.get(NrdxParamNodeType.FORMULAFORM.getCode());
                                List<String> formKeyForFormGroup = formDefinesForPrintScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                                PrintParamDTO formParam = new PrintParamDTO(printTemplateSchemeDefine.getKey(), formKeyForFormGroup, NrdxParamNodeType.FORMULAFORM);
                                context.setPath(formulaSchemePath);
                                formulaFormAbstractParamTransfer.exportModel(formParam, context, monitor);
                            }
                            continue;
                        }
                        logger.info("\u6ca1\u6709\u62a5\u8868\u5206\u7ec4\u53c2\u6570\u8981\u5bfc\u51fa");
                    }
                    continue;
                }
                logger.info("\u6ca1\u6709\u62a5\u8868\u65b9\u6848\u53c2\u6570\u8981\u5bfc\u51fa");
            }
        } else {
            logger.info("\u6ca1\u6709\u4efb\u52a1\u53c2\u6570\u8981\u5bfc\u51fa");
        }
    }
}


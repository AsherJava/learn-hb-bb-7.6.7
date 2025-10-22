/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.configuration.common.ConfigContentType
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.configuration.controller.IBusinessConfigurationController
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.xlib.utils.StringUtil
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.facade.ISingleMapNrController
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 */
package nr.single.para.parain.internal.service3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.configuration.controller.IBusinessConfigurationController;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParaInfo;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.xlib.utils.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nr.single.map.data.PathUtil;
import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareStatusType;
import nr.single.para.compare.internal.util.CompareUtil;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping2.ITaskFileMapingTansService;
import nr.single.para.parain.service.IAnalInnerImportService;
import nr.single.para.parain.service.IDataSchemeImportService;
import nr.single.para.parain.service.IEnumTableDefineImportService;
import nr.single.para.parain.service.IFormDefineImportService;
import nr.single.para.parain.service.IFormulaDefineImportService;
import nr.single.para.parain.service.IPrintDefineImportService;
import nr.single.para.parain.service.IQueryModalDefineImportService;
import nr.single.para.parain.service.ITaskDefineImportService;
import nr.single.para.parain.service.ITaskFileImportService;
import nr.single.para.parain.util.IParaImportFileServcie;
import nr.single.para.parain.util.IParaServeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileImportServiceImpl
implements ITaskFileImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskFileImportServiceImpl.class);
    @Autowired
    private ITaskDefineImportService taskImportService;
    @Autowired
    private IFormDefineImportService formImportService;
    @Autowired
    private IEnumTableDefineImportService enumImportService;
    @Autowired
    private IFormulaDefineImportService formulaService;
    @Autowired
    private IPrintDefineImportService printService;
    @Autowired
    private ConfigurationConfig configService;
    @Autowired
    private ISingleMapNrController mapController;
    @Autowired
    private IQueryModalDefineImportService queryService;
    @Autowired
    private IViewDeployController deployController;
    @Autowired
    private IParaServeCodeService serverCodeService;
    @Autowired
    private IAnalInnerImportService analInerService;
    @Autowired
    private IDataSchemeImportService dataSchemeImportServce;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private SingleFileParserService singleParserService;
    @Autowired
    private ISingleCompareInfoService infoService;
    @Autowired
    private IParaImportFileServcie fileService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private ITaskFileMapingTansService mappingTransService;

    @Override
    public String ImportSingleToTask(String taskKey, String file) throws Exception {
        String taskId = taskKey;
        SingleParaImportOption option = new SingleParaImportOption();
        option.SelectAll();
        this.ImportSingleToFormScheme(taskKey, null, file, option);
        return taskId;
    }

    @Override
    public String ImportSingleToFormScheme(String taskKey, String schemeKey, String file, SingleParaImportOption option) throws Exception {
        return this.ImportSingleToFormScheme(taskKey, schemeKey, file, option, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String ImportSingleToFormScheme(String taskKey, String schemeKey, String file, SingleParaImportOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        String schemeId = schemeKey;
        TaskImportContext importContext = new TaskImportContext();
        try {
            importContext.setAsyncMonitor(asyncMonitor);
            importContext.setImportOption(option);
            importContext.onProgress(0.01, "\u5f00\u59cb\u5bfc\u5165\u6587\u4ef6");
            importContext.info(log, "\u5f00\u59cb\u5bfc\u5165\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            long currentTimeMillis = System.currentTimeMillis();
            JIOParamParser jioParaser = this.singleParserService.getParaParaser(file);
            try {
                if (jioParaser.getInOutData().indexOf(InOutDataType.BBCS) < 0 && jioParaser.getInOutData().indexOf(InOutDataType.CSCS) < 0) {
                    throw new Exception("\u5bfc\u5165\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                }
                importContext.setJioParser(jioParaser);
                importContext.info(log, "\u89e3\u6790JIO\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                currentTimeMillis = System.currentTimeMillis();
                importContext.onProgress(0.05, "\u89e3\u6790JIO\u5b8c\u6210");
                importContext.setCurServerCode(this.serverCodeService.getCurServeCode());
                this.importTaskInfo(importContext, taskKey, schemeKey, file, asyncMonitor);
            }
            finally {
                PathUtil.deleteDir((String)jioParaser.getFilePath());
            }
        }
        finally {
            importContext.onProgress(1.0, "JIO\u5bfc\u5165\u5b8c\u6210");
            if (asyncMonitor != null) {
                PathUtil.deleteFile((String)file);
            }
        }
        return schemeId;
    }

    @Override
    public String ImportSingleToFormScheme(String taskKey, String schemeKey, String dataSchemeKey, String file, SingleParaImportOption option, AsyncTaskMonitor asyncMonitor) throws Exception {
        ParaImportResult importResult = new ParaImportResult();
        return this.ImportSingleToFormScheme(null, taskKey, schemeKey, dataSchemeKey, file, option, asyncMonitor, importResult);
    }

    @Override
    public String ImportSingleToFormScheme(String compareKey, SingleParaImportOption option, AsyncTaskMonitor asyncMonitor, ParaImportResult importResult) throws Exception {
        String schemeId = "";
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            if (option == null) {
                ParaCompareOption compareOption = this.getOptonFromInfo(null, info);
                option = new SingleParaImportOption();
                if (compareOption != null) {
                    option.setUploadTask(compareOption.isUploadBaseParam());
                    option.setUploadFormScheme(compareOption.isUploadBaseParam());
                    option.setUploadEnum(compareOption.isUploadBaseParam());
                    option.setUploadForm(compareOption.isUploadBaseParam());
                    option.setUploadTaskLink(compareOption.isUploadBaseParam());
                    option.setUploadFormula(compareOption.isUploadFormula());
                    option.setUploadPrint(compareOption.isUploadPrint());
                    option.setUploadQuery(compareOption.isUploadQuery());
                    option.setCorpEntityId(compareOption.getCorpEntityId());
                    option.setDateEntityId(compareOption.getDateEntityId());
                    option.setDimEntityIds(compareOption.getDimEntityIds());
                    option.setFilePrefix(compareOption.getDataPrefix());
                    option.setEnumPrefix(compareOption.getEnumPrefix());
                    option.setDataSchemeCode(compareOption.getDataSchemeCode());
                    option.setDataSchemeTitle(compareOption.getDataSchemeTitle());
                    option.setTaskCode(compareOption.getTaskCode());
                    option.setTaskTitle(compareOption.getTaskTitle());
                    option.setFromPeriod(compareOption.getFromPeriod());
                    option.setToPeriod(compareOption.getToPeriod());
                    option.setFormSchemeTitle(compareOption.getFormSchemeTitle());
                    option.setOverWriteAll(compareOption.isOverWriteAll());
                }
            }
            info.setStatus(CompareStatusType.SCHEME_IMPORTING);
            this.infoService.update(info);
            try {
                schemeId = this.ImportSingleToFormSchemeByInfo(info, option, asyncMonitor, importResult);
                info.setStatus(CompareStatusType.SCHEME_IMPORTED);
                if (StringUtil.isEmpty((String)info.getFormSchemeKey())) {
                    info.setFormSchemeKey(schemeId);
                }
                this.infoService.update(info);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                info.setStatus(CompareStatusType.SCHEME_IMPORTFAIL);
                info.setMessage("\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
                importResult.setSuccess(false);
                importResult.setMessage("\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
                this.infoService.update(info);
                throw e;
            }
        }
        return schemeId;
    }

    @Override
    public String ImportSingleToFormScheme(String compareKey, AsyncTaskMonitor asyncMonitor) throws Exception {
        String schemeId = "";
        CompareInfoDTO compareInfoDTO = new CompareInfoDTO();
        compareInfoDTO.setKey(compareKey);
        List<CompareInfoDTO> list = this.infoService.list(compareInfoDTO);
        if (list != null && list.size() > 0) {
            CompareInfoDTO info = list.get(0);
            ParaCompareOption compareOption = this.getOptonFromInfo(null, info);
            SingleParaImportOption option = new SingleParaImportOption();
            if (compareOption != null) {
                option.setUploadTask(compareOption.isUploadBaseParam());
                option.setUploadFormScheme(compareOption.isUploadBaseParam());
                option.setUploadEnum(compareOption.isUploadBaseParam());
                option.setUploadForm(compareOption.isUploadBaseParam());
                option.setUploadTaskLink(compareOption.isUploadBaseParam());
                option.setUploadFormula(compareOption.isUploadFormula());
                option.setUploadPrint(compareOption.isUploadPrint());
                option.setUploadQuery(compareOption.isUploadQuery());
                option.setCorpEntityId(compareOption.getCorpEntityId());
                option.setDateEntityId(compareOption.getDateEntityId());
                option.setDimEntityIds(compareOption.getDimEntityIds());
                option.setFilePrefix(compareOption.getDataPrefix());
                option.setEnumPrefix(compareOption.getEnumPrefix());
                option.setOverWriteAll(compareOption.isOverWriteAll());
            }
            ParaImportResult importResult = new ParaImportResult();
            return this.ImportSingleToFormSchemeByInfo(info, option, asyncMonitor, importResult);
        }
        return schemeId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String ImportSingleToFormSchemeByInfo(CompareInfoDTO info, SingleParaImportOption option, AsyncTaskMonitor asyncMonitor, ParaImportResult importResult) throws Exception {
        String string;
        DesignDataScheme dataScheme;
        ParaImportInfoResult exceptonsLog = null;
        if (info != null && importResult != null) {
            exceptonsLog = importResult.getLogInfo(CompareDataType.DATA_EXCEPTION, "exceptions", "\u5f02\u5e38\u4fe1\u606f");
        }
        if (StringUtils.isNotEmpty((String)info.getDataSchemeKey()) && (dataScheme = this.dataSchemeSevice.getDataScheme(info.getDataSchemeKey())) != null && !this.dataSchemeAuthService.canWriteScheme(info.getDataSchemeKey())) {
            throw new Exception("\u5bfc\u5165\u5931\u8d25\uff0c\u65e0\u6570\u636e\u65b9\u6848" + dataScheme.getCode() + "\u5199\u6743\u9650\uff01");
        }
        String filePath = CompareUtil.getCompareFilePath();
        byte[] oldFileData = null;
        if (StringUtils.isNotEmpty((String)info.getJioData())) {
            oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
        }
        String file = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
        try {
            string = this.ImportSingleToFormScheme(info, info.getTaskKey(), info.getFormSchemeKey(), info.getDataSchemeKey(), file, option, asyncMonitor, importResult);
        }
        catch (Throwable throwable) {
            try {
                PathUtil.deleteFile((String)file);
                PathUtil.deleteDir((String)filePath);
                throw throwable;
            }
            catch (Exception e1) {
                if (exceptonsLog != null) {
                    ParaImportInfoResult exceptonLog = new ParaImportInfoResult();
                    exceptonLog.setCode("exception_paraImport");
                    exceptonLog.setSuccess(false);
                    exceptonLog.setSingleCode("");
                    exceptonLog.setSingleTitle("\u5bfc\u5165\u53c2\u6570");
                    exceptonLog.setNetTitle(e1.getMessage());
                    exceptonLog.setMessage(e1.getStackTrace().toString());
                    exceptonsLog.addItem(exceptonLog);
                }
                throw e1;
            }
        }
        PathUtil.deleteFile((String)file);
        PathUtil.deleteDir((String)filePath);
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String ImportSingleToFormScheme(CompareInfoDTO info, String taskKey, String schemeKey, String dataSchemeKey, String file, SingleParaImportOption option, AsyncTaskMonitor asyncMonitor, ParaImportResult importResult) throws Exception {
        String schemeId;
        block18: {
            schemeId = "";
            TaskImportContext importContext = new TaskImportContext();
            try {
                importContext.setAsyncMonitor(asyncMonitor);
                importContext.setImportOption(option);
                importContext.setCompareInfo(info);
                importContext.setImportResult(importResult);
                importContext.onProgress(0.01, "\u5f00\u59cb\u5bfc\u5165");
                importContext.info(log, "\u5f00\u59cb\u5bfc\u5165\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
                long currentTimeMillis = System.currentTimeMillis();
                JIOParamParser jioParaser = this.singleParserService.getParaParaser(file);
                try {
                    String identityId;
                    if (jioParaser.getInOutData().indexOf(InOutDataType.BBCS) < 0 && jioParaser.getInOutData().indexOf(InOutDataType.CSCS) < 0) {
                        throw new Exception("\u5bfc\u5165\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                    }
                    importContext.setJioParser(jioParaser);
                    importContext.setCurServerCode(this.serverCodeService.getCurServeCode());
                    importContext.info(log, "\u89e3\u6790JIO\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                    if (info == null) {
                        importContext.onProgress(0.05, "\u89e3\u6790JIO\u5b8c\u6210");
                    }
                    importContext.setDataSchemeKey(dataSchemeKey);
                    importContext.setTaskKey(taskKey);
                    importContext.setFormSchemeKey(schemeKey);
                    this.dataSchemeImportServce.importDataSchemeDefine(importContext);
                    importContext.info(log, "\u5bfc\u5165\u6570\u636e\u65b9\u6848\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                    importContext.onProgress(0.06, "\u5bfc\u5165\u6570\u636e\u65b9\u6848\u5b8c\u6210");
                    String taskId = importContext.getTaskKey();
                    schemeId = importContext.getFormSchemeKey();
                    this.importTaskInfo(importContext, taskId, schemeId, file, asyncMonitor);
                    if (info != null) {
                        if (StringUtils.isEmpty((String)info.getTaskKey())) {
                            info.setTaskKey(importContext.getTaskKey());
                        }
                        if (StringUtils.isEmpty((String)info.getFormSchemeKey())) {
                            info.setFormSchemeKey(importContext.getFormSchemeKey());
                        }
                        if (StringUtils.isEmpty((String)info.getDataSchemeKey())) {
                            info.setDataSchemeKey(importContext.getDataSchemeKey());
                        }
                    }
                    if ((identityId = NpContextHolder.getContext().getIdentityId()) != null) {
                        if (StringUtils.isEmpty((String)taskId)) {
                            taskId = importContext.getTaskKey();
                        }
                        importContext.info(log, "\u7ed9\u7528\u6237\u6388\u6743\u5f53\u524d\u4efb\u52a1" + taskId);
                        this.definitionAuthority.grantAllPrivileges(taskId);
                        break block18;
                    }
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_007);
                }
                catch (Exception e) {
                    importContext.error(log, e.getMessage(), e);
                    throw e;
                }
                finally {
                    PathUtil.deleteDir((String)jioParaser.getFilePath());
                }
            }
            finally {
                importContext.onProgress(0.91, "JIO\u5bfc\u5165\u5b8c\u6210");
                if (asyncMonitor != null) {
                    PathUtil.deleteFile((String)file);
                }
            }
        }
        if (importResult != null) {
            importResult.setFormSchemeKey(schemeId);
        }
        return schemeId;
    }

    private String importTaskInfo(TaskImportContext importContext, String taskKey, String schemeKey, String file, AsyncTaskMonitor asyncMonitor) throws Exception {
        long currentTimeMillis;
        String schemeId = schemeKey;
        SingleParaImportOption option = importContext.getImportOption();
        JIOParamParser jioParaser = importContext.getJioParser();
        long startCurrentTimeMillis = currentTimeMillis = System.currentTimeMillis();
        importContext.onProgress(0.05, "\u89e3\u6790JIO\u5b8c\u6210");
        importContext.setCurServerCode(this.serverCodeService.getCurServeCode());
        this.importBaseTaskInfo(importContext, jioParaser.getParaInfo(), taskKey, schemeKey, option, file);
        currentTimeMillis = System.currentTimeMillis();
        this.importAnalInnerInfo(importContext);
        importContext.info(log, "\u57fa\u7840\u4efb\u52a1\u5bfc\u5165\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString() + ",\u8017\u65f6\uff1a" + (System.currentTimeMillis() - startCurrentTimeMillis));
        if (option.isAnalPara() && jioParaser.getParaInfo().getAnalInfo().getRootTableSets().size() > 0) {
            long startCurrentTimeMillisAnal = System.currentTimeMillis();
            importContext.info(log, "\u5f00\u59cb\u5bfc\u5165\u5206\u6790\u4efb\u52a1:" + file + ",\u65f6\u95f4:" + new Date().toString());
            for (AnalParaInfo subAnalInfo : jioParaser.getParaInfo().getAnalInfo().getSubAnalInfos()) {
                importContext.info(log, "\u5f00\u59cb\u5bfc\u5165\u5206\u6790\u5b50\u4efb\u52a1:" + subAnalInfo.getAnalParaInfo().getTaskName() + ",\u65f6\u95f4:" + new Date().toString());
                SingleParaImportOption analOption = new SingleParaImportOption();
                analOption.NotSelectAll();
                analOption.setUploadTask(true);
                analOption.setUploadFormScheme(true);
                analOption.setUploadForm(true);
                analOption.setUploadEnum(true);
                analOption.setUploadFormula(true);
                analOption.setAnalPara(true);
                TaskImportContext importAnalContext = new TaskImportContext();
                importAnalContext.setImportOption(option);
                importAnalContext.setBaseTaskKey(importContext.getTaskKey());
                importAnalContext.setBaseformSchemeKeyKey(importContext.getFormSchemeKey());
                importAnalContext.setBaseImportContext(importContext);
                importAnalContext.setAnalParaInfo(subAnalInfo);
                importAnalContext.setCurServerCode(this.serverCodeService.getCurServeCode());
                importAnalContext.onProgress(0.01);
                importAnalContext.setDataSchemeKey(importContext.getDataSchemeKey());
                importAnalContext.setDataScheme(importContext.getDataScheme());
                currentTimeMillis = System.currentTimeMillis();
                this.importAnalTaskInfo(importAnalContext, jioParaser.getParaInfo(), subAnalInfo, taskKey, schemeKey, analOption, file);
                importContext.onProgress(0.87, "\u5bfc\u5165\u5206\u6790\u5b50\u4efb\u52a1" + subAnalInfo.getAnalParaInfo().getTaskName());
                importAnalContext.onProgress(1.0);
                if (StringUtils.isNotEmpty((String)importAnalContext.getTaskKey())) {
                    this.deployController.deployTask(importAnalContext.getTaskKey());
                }
                importContext.info(log, "\u5b8c\u6210\u5bfc\u5165\u5206\u6790\u5b50\u4efb\u52a1:" + subAnalInfo.getAnalParaInfo().getTaskName() + ",\u65f6\u95f4:" + new Date().toString());
            }
            importContext.info(log, "\u5206\u6790\u4efb\u52a1\u5bfc\u5165\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString() + ",\u8017\u65f6\uff1a" + (System.currentTimeMillis() - startCurrentTimeMillisAnal));
        }
        importContext.info(log, "\u5bfc\u5165\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString() + ",\u603b\u8017\u65f6\uff1a" + (System.currentTimeMillis() - startCurrentTimeMillis));
        importContext.onProgress(0.9);
        return schemeId;
    }

    private void importAnalInnerInfo(TaskImportContext importAnalContext) throws Exception {
        AnalParaInfo innerPara = importAnalContext.getParaInfo().getInnerAnalInfo();
        if (innerPara != null && innerPara.getAnalTableList().size() > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            this.analInerService.importAnalInner(importAnalContext);
            importAnalContext.info(log, "\u5bfc\u5165\u5d4c\u5165\u5206\u6790\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
        }
    }

    private void importAnalTaskInfo(TaskImportContext importAnalContext, ParaInfo baseParaInfo, AnalParaInfo analInfo, String taskKey, String schemeKey, SingleParaImportOption option, String file) throws Exception {
        if (analInfo.getAnalTableList().size() == 0) {
            return;
        }
    }

    private void importBaseTaskInfo(TaskImportContext importContext, ParaInfo paraInfo, String taskKey, String schemeKey, SingleParaImportOption option, String file) throws Exception {
        ParaImportInfoResult exceptonLog;
        MappingScheme oldScheme;
        if (option != null && option.isOverWriteAll()) {
            importContext.info("\u5bfc\u5165\u65b9\u5f0f\uff1a\u662f\u5b8c\u5168\u8986\u76d6\u5bfc\u5165\uff08\u5220\u9664\u5f53\u524dJIO\u53c2\u6570\u4e2d\u6ca1\u6709\u7684\u8868\u5355\u3001\u516c\u5f0f\u3001\u6253\u5370\u6a21\u677f\uff09");
        }
        long currentTimeMillis = System.currentTimeMillis();
        importContext.setParaInfo(paraInfo);
        this.taskImportService.UpdateContextCache(importContext);
        importContext.onProgress(0.1, "\u5bfc\u5165\u57fa\u7840\u8868");
        ParaImportInfoResult exceptonsLog = null;
        if (importContext.getCompareInfo() != null && importContext.getImportResult() != null) {
            exceptonsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_EXCEPTION, "exceptions", "\u5f02\u5e38\u4fe1\u606f");
        }
        String schemeId = schemeKey;
        String taskId = taskKey;
        importContext.setTaskKey(taskKey);
        importContext.setFormSchemeKey(schemeKey);
        if (StringUtils.isEmpty((String)importContext.getDataSchemeKey()) || importContext.getDataScheme() == null) {
            this.taskImportService.getDataSchemKeyByTask(importContext, taskKey);
        }
        if (option.isUploadTask()) {
            taskId = this.taskImportService.importTaskDefine(importContext);
            this.taskImportService.importTaskGroupDefines(importContext);
            importContext.info(log, "\u5bfc\u5165\u4efb\u52a1\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.1, "\u5bfc\u5165\u4efb\u52a1\u5b8c\u6210");
            if (StringUtils.isEmpty((String)importContext.getTaskKey())) {
                importContext.setFormSchemeKey(taskId);
            }
        }
        schemeId = importContext.getFormSchemeKey();
        taskId = importContext.getTaskKey();
        if (option.isUploadFormScheme()) {
            schemeId = this.taskImportService.importFormSchemeDefine(importContext);
            this.taskImportService.UpdateTask(importContext);
            importContext.info(log, "\u5bfc\u5165\u62a5\u8868\u65b9\u6848\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.2, "\u5bfc\u5165\u62a5\u8868\u65b9\u6848\u5b8c\u6210");
            if (StringUtils.isEmpty((String)importContext.getFormSchemeKey())) {
                importContext.setFormSchemeKey(schemeId);
            }
        }
        if (null == importContext.getMapScheme()) {
            SingleMapFormSchemeDefine mapScheme = this.mapController.QueryAndCreateSingleMapDefine(taskKey, schemeId);
            if (mapScheme != null) {
                mapScheme.setMapSchemeTitle("\u9ed8\u8ba4\u6620\u5c04\u65b9\u6848");
            }
            importContext.setMapScheme(mapScheme);
        }
        if ((oldScheme = this.mappingTransService.findMappingScheme(importContext)) != null) {
            importContext.setMapSchemeKey(oldScheme.getKey());
            if (importContext.getImportResult() != null) {
                importContext.getImportResult().setMapSchemeKey(oldScheme.getKey());
            }
        }
        this.taskImportService.UpdateMapSchemeDefineByTask(importContext);
        String fileName = SinglePathUtil.normalize((String)file);
        File aFile = new File(fileName);
        importContext.getMapScheme().getTaskInfo().setUploadFileName(aFile.getName());
        if (option.isUploadTask()) {
            this.dataSchemeImportServce.updateDataSchemeDefine(importContext);
        }
        if (option.isUploadEnum()) {
            this.enumImportService.importEnumTableDefines(importContext);
            importContext.info(log, "\u5bfc\u5165\u679a\u4e3e\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.3, "\u5bfc\u5165\u679a\u4e3e\u5b8c\u6210");
        }
        this.taskImportService.updateTaskEntitys(importContext);
        if (option.isUploadForm()) {
            this.formImportService.importFormGroupDefines(importContext, schemeId);
            this.formImportService.importFormDefines(importContext, schemeId);
            this.formImportService.deleteOtherFormGroups(importContext, schemeId);
            importContext.info(log, "\u5bfc\u5165\u8868\u5355\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.5, "\u5bfc\u5165\u8868\u5355\u3001\u5206\u7ec4\u5b8c\u6210");
        }
        if (option.isUploadTaskLink()) {
            try {
                this.taskImportService.importTaskLinkDefines(importContext);
            }
            catch (Exception e1) {
                importContext.error(log, "\u5bfc\u5165\u5173\u8054\u4efb\u52a1\u5f02\u5e38\uff1a" + e1.getMessage(), e1);
                exceptonLog = new ParaImportInfoResult();
                exceptonLog.setCode("exception_taskLink");
                exceptonLog.setSuccess(false);
                exceptonLog.setSingleCode("");
                exceptonLog.setSingleTitle("\u5173\u8054\u4efb\u52a1");
                exceptonLog.setNetTitle(e1.getMessage());
                exceptonLog.setMessage(e1.getStackTrace().toString());
                exceptonsLog.addItem(exceptonLog);
            }
            importContext.info(log, "\u5bfc\u5165\u5173\u8054\u4efb\u52a1\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.6, "\u5bfc\u5165\u5173\u8054\u4efb\u52a1\u5b8c\u6210");
        }
        if (option.isUploadFormula()) {
            try {
                this.formulaService.importFormulaDefines(importContext, schemeId);
            }
            catch (Exception e1) {
                importContext.error(log, "\u5bfc\u5165\u516c\u5f0f\u5f02\u5e38\uff1a" + e1.getMessage(), e1);
                exceptonLog = new ParaImportInfoResult();
                exceptonLog.setCode("exception_formulas");
                exceptonLog.setSuccess(false);
                exceptonLog.setSingleCode("");
                exceptonLog.setSingleTitle("\u516c\u5f0f");
                exceptonLog.setNetTitle(e1.getMessage());
                exceptonLog.setMessage(e1.getStackTrace().toString());
                exceptonsLog.addItem(exceptonLog);
            }
            importContext.info(log, "\u5bfc\u5165\u516c\u5f0f\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.7, "\u5bfc\u5165\u516c\u5f0f\u5b8c\u6210");
        }
        if (option.isUploadPrint()) {
            try {
                this.printService.importPrintDefines(importContext, schemeId);
            }
            catch (Exception e1) {
                importContext.error(log, "\u5bfc\u5165\u6253\u5370\u6a21\u677f\u5f02\u5e38\uff1a" + e1.getMessage(), e1);
                exceptonLog = new ParaImportInfoResult();
                exceptonLog.setCode("exception_printItem");
                exceptonLog.setSuccess(false);
                exceptonLog.setSingleCode("");
                exceptonLog.setSingleTitle("\u6253\u5370\u6a21\u677f");
                exceptonLog.setNetTitle(e1.getMessage());
                exceptonLog.setMessage(e1.getStackTrace().toString());
                exceptonsLog.addItem(exceptonLog);
            }
            importContext.info(log, "\u5bfc\u5165\u6253\u5370\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.8, "\u5bfc\u5165\u6253\u5370");
        }
        if (option.isUploadQuery() && this.queryService.isNeedImportQuery()) {
            this.queryService.importQueryModalDefines(importContext);
            importContext.info(log, "\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
            currentTimeMillis = System.currentTimeMillis();
            importContext.onProgress(0.82, "\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\u5b8c\u6210");
        }
        try {
            this.mapController.UpdateSingleMapDefine(taskKey, schemeId, importContext.getMapScheme());
            importContext.onProgress(0.83, "\u66f4\u65b0\u6620\u5c04\u5b8c\u6210");
            if (option.isUploadForm() && option.isUploadEnum() && option.isUploadFormula()) {
                this.saveJioToConfig(importContext, importContext.getTaskKey(), importContext.getFormSchemeKey(), file);
                this.mappingTransService.splictJioConifg(importContext, file);
                importContext.onProgress(0.84, "\u4fdd\u5b58JIO\u5230\u914d\u7f6e\u5b8c\u6210");
            }
        }
        catch (Exception e1) {
            importContext.error(log, "\u4fdd\u5b58JIO\u5230\u914d\u7f6e\u51fa\u932f:" + e1.getMessage(), e1);
            exceptonLog = new ParaImportInfoResult();
            exceptonLog.setCode("exception_printItem");
            exceptonLog.setSuccess(false);
            exceptonLog.setSingleCode("");
            exceptonLog.setSingleTitle("JIO\u6620\u5c04\u914d\u7f6e");
            exceptonLog.setNetTitle(e1.getMessage());
            exceptonLog.setMessage(e1.getStackTrace().toString());
            exceptonsLog.addItem(exceptonLog);
        }
        try {
            this.saveJioFilesToConfigs(importContext, importContext.getTaskKey(), importContext.getFormSchemeKey());
        }
        catch (Exception ex) {
            importContext.error(log, "\u4fdd\u5b58\u53c2\u6570\u6587\u4ef6\u51fa\u932f:" + ex.getMessage(), ex);
        }
    }

    private void saveJioToConfig(TaskImportContext importContext, String taskKey, String formSchemeKey, String file) throws Exception {
        boolean isNew;
        IBusinessConfigurationController controller = this.configService.getBusinessConfigurationController();
        BusinessConfigurationDefine define = controller.getConfiguration(taskKey, formSchemeKey, "FormSchemeJIOFile");
        boolean bl = isNew = null == define;
        if (isNew) {
            define = controller.createConfiguration();
            define.setTaskKey(taskKey);
            define.setFormSchemeKey(formSchemeKey);
            define.setCode("FormSchemeJIOFile");
        }
        String fileName = SinglePathUtil.normalize((String)file);
        File tempFile = new File(fileName);
        define.setFileName(tempFile.getName());
        if (null != importContext.getSchemeInfoCache() && null != importContext.getSchemeInfoCache().getFormScheme()) {
            define.setTitle(importContext.getSchemeInfoCache().getFormScheme().getTitle() + "");
            define.setDescription(importContext.getSchemeInfoCache().getFormScheme().getTitle() + "-\u5bf9\u5e94\u7684JIO\u6587\u4ef6");
        } else {
            define.setTitle(formSchemeKey.toString());
        }
        define.setCategory("JIOFile");
        define.setContentType(ConfigContentType.CCT_BINARY);
        try (FileInputStream inStream = new FileInputStream(tempFile);){
            byte[] data = new byte[inStream.available()];
            inStream.read(data, 0, inStream.available());
            String value = "";
            if (data.length > 0) {
                value = Convert.bytesToBase64((byte[])data);
            }
            define.setConfigurationContent(value);
        }
        if (isNew) {
            controller.addConfiguration(define);
        } else {
            controller.updateConfiguration(define);
        }
    }

    private void saveJioFilesToConfigs(TaskImportContext importContext, String taskKey, String formSchemeKey) throws Exception {
        this.saveJioFilesToConfigsByDir(importContext, taskKey, formSchemeKey, "", "JIOTASK");
        this.saveJioFilesToConfigsByDir(importContext, taskKey, formSchemeKey, "PARA", "JIOPARA");
        this.saveJioFilesToConfigsByDir(importContext, taskKey, formSchemeKey, "QUERY", "JIOQUERY");
        this.saveJioFilesToConfigsByDir(importContext, taskKey, formSchemeKey, "ANALPARA", "JIOANALPARA");
    }

    private void saveJioFilesToConfigsByDir(TaskImportContext importContext, String taskKey, String formSchemeKey, String SubDir, String category) throws Exception {
        File file;
        File[] tempList;
        JIOParamParser jioParaser = importContext.getJioParser();
        if (jioParaser == null) {
            return;
        }
        String dirName = jioParaser.getFilePath();
        if (StringUtils.isNotEmpty((String)SubDir)) {
            dirName = dirName + File.separator + SubDir;
        }
        if ((tempList = (file = new File(dirName = SinglePathUtil.normalize((String)dirName))).listFiles()) == null || tempList.length <= 0) {
            return;
        }
        ArrayList<String> files = new ArrayList<String>();
        for (int i = 0; i < tempList.length; ++i) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                try {
                    this.saveJioFileToConfig(taskKey, formSchemeKey, tempList[i], category);
                }
                catch (Exception ex) {
                    importContext.error(log, "JIO\u53c3\u6578\u6587\u4ef6\u51fa\u9519:" + tempList[i] + "," + ex.getMessage(), ex);
                }
            }
            if (!tempList[i].isDirectory()) continue;
        }
    }

    private void saveJioFileToConfig(String taskKey, String formSchemeKey, File file, String category) throws Exception {
        IBusinessConfigurationController controller;
        BusinessConfigurationDefine define;
        boolean isNew;
        String fileName = file.getName();
        String code = fileName;
        if (StringUtils.isEmpty((String)code)) {
            return;
        }
        if ((code = code.toUpperCase()).length() > 50) {
            code = code.substring(0, 50);
        }
        boolean bl = isNew = null == (define = (controller = this.configService.getBusinessConfigurationController()).getConfiguration(taskKey, formSchemeKey, code));
        if (isNew) {
            define = controller.createConfiguration();
            define.setTaskKey(taskKey);
            define.setFormSchemeKey(formSchemeKey);
            define.setCode(code);
        }
        File tempFile = file;
        define.setFileName(fileName);
        define.setTitle(code);
        define.setDescription(code + "-JIO\u6587\u4ef6");
        define.setTitle(code);
        define.setCategory(category);
        define.setContentType(ConfigContentType.CCT_BINARY);
        try (FileInputStream inStream = new FileInputStream(tempFile);){
            String value = "";
            if (inStream.available() <= 0x300000) {
                byte[] data = new byte[inStream.available()];
                inStream.read(data, 0, inStream.available());
                if (data.length > 0) {
                    value = Convert.bytesToBase64((byte[])data);
                }
            } else {
                define.setDescription(define.getDescription() + "\u8d85\u5927\u672a\u5bfc\u5165");
            }
            define.setConfigurationContent(value);
        }
        if (isNew) {
            controller.addConfiguration(define);
        } else {
            controller.updateConfiguration(define);
        }
    }

    private ParaCompareOption getOptonFromInfo(ParaCompareOption option, CompareInfoDTO info) {
        if (option == null) {
            try {
                if (StringUtils.isNotEmpty((String)info.getOptionData())) {
                    ObjectMapper mapper = new ObjectMapper();
                    option = (ParaCompareOption)mapper.readValue(info.getOptionData(), ParaCompareOption.class);
                }
                if (option == null) {
                    option = new ParaCompareOption();
                    option.setCorpEntityId("");
                    option.setDateEntityId("");
                    option.setDimEntityIds("");
                    option.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
                    option.setFieldContainForm(true);
                    option.setOverWriteAll(false);
                    option.setUploadBaseParam(true);
                    option.setUploadFormula(true);
                    option.setUploadPrint(true);
                    option.setUploadQuery(true);
                    option.setUseFormLevel(true);
                }
            }
            catch (Exception e) {
                log.info(e.getMessage());
                option = new ParaCompareOption();
                option.setCorpEntityId("");
                option.setDateEntityId("");
                option.setDimEntityIds("");
                option.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
                option.setFieldContainForm(true);
                option.setOverWriteAll(false);
                option.setUploadBaseParam(true);
                option.setUploadFormula(true);
                option.setUploadPrint(true);
                option.setUploadQuery(true);
                option.setUseFormLevel(true);
            }
        }
        return option;
    }
}


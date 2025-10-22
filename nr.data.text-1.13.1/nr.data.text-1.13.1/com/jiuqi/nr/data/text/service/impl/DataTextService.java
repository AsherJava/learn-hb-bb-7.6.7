/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.exception.DataCommonException
 *  com.jiuqi.nr.data.common.exception.ErrorCode
 *  com.jiuqi.nr.data.common.exception.ErrorCodeEnum
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.logger.DataIoLoggerFactory
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.param.ImportFileDataRange
 *  com.jiuqi.nr.data.common.service.QueryMappings
 *  com.jiuqi.nr.data.common.service.dto.ImportCancledResult
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.params.output.ImportInformations
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.service.impl.CsvExportServiceImpl
 *  com.jiuqi.nr.io.service.impl.TxtExportServiceImpl
 *  com.jiuqi.nr.io.util.FileUtil
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.exception.DataCommonException;
import com.jiuqi.nr.data.common.exception.ErrorCode;
import com.jiuqi.nr.data.common.exception.ErrorCodeEnum;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.logger.DataIoLoggerFactory;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import com.jiuqi.nr.data.common.service.QueryMappings;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.data.text.param.TextParams;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.data.text.service.ExpTextService;
import com.jiuqi.nr.data.text.service.ImpTextService;
import com.jiuqi.nr.data.text.service.impl.TextFileImportService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.impl.CsvExportServiceImpl;
import com.jiuqi.nr.io.service.impl.TxtExportServiceImpl;
import com.jiuqi.nr.io.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataTextService
implements ExpTextService,
ImpTextService {
    private static final Logger log = LoggerFactory.getLogger(DataTextService.class);
    private static final String SEPARATOR = "/";
    public static final String MODULEFILEUPLOAD = "\u6570\u636e\u670d\u52a1-\u6587\u672c\u5bfc\u5165";
    public static final String MODULEFILEDOWNLOAD = "\u6570\u636e\u670d\u52a1-\u6587\u672c\u5bfc\u51fa";
    @Autowired
    private CsvExportServiceImpl csvExportService;
    @Autowired
    private TxtExportServiceImpl txtExportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TextFileImportService textFileImportService;
    @Autowired(required=false)
    private QueryMappings queryMappings;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired(required=false)
    private IoQualifier ioQualifier;
    @Autowired
    private DataIoLoggerFactory dataIoLoggerFactory;

    @Override
    public String downloadTextData(TextParams params) throws IOException {
        return this.downloadTextData(params, null);
    }

    @Override
    public String downloadTextData(TextParams params, String filePath) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(MODULEFILEDOWNLOAD, OperLevel.USER_OPER);
        params.check();
        filePath = filePath == null ? System.getProperty("java.io.tmpdir") + SEPARATOR + UUID.randomUUID().toString() + SEPARATOR : ((filePath = filePath.replace("\\\\", "\\/")).endsWith(SEPARATOR) ? filePath + System.currentTimeMillis() + SEPARATOR : filePath + SEPARATOR + UUID.randomUUID().toString() + SEPARATOR);
        StringBuilder formKey = new StringBuilder();
        if (params.getFormKeys() != null) {
            for (String form : params.getFormKeys()) {
                formKey.append(form).append(";");
            }
        }
        DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)params.getDimensionSet());
        TableContext tbContext = new TableContext(null, params.getFormSchemeKey(), formKey.toString(), mergeDimensionValueSet, OptTypes.FORM, params.getTextType().equals((Object)TextType.TEXTTYPE_CSV) ? ".csv" : ".txt", params.getSplit(), null);
        tbContext.setExportBizkeyorder(true);
        tbContext.setSecretLevelTitle(params.getSecretLevelTitle());
        tbContext.setSecretLevelTitleHigher(params.getSecretLevelTitleHigher());
        tbContext.setDataMasking(params.isDataMasking());
        String extZipFile = null;
        try {
            extZipFile = params.getTextType().equals((Object)TextType.TEXTTYPE_CSV) ? this.csvExportService.getExtZipFile(tbContext, params.getMonitor()) : this.txtExportService.getExtZipFile(tbContext, params.getMonitor());
        }
        catch (Exception e) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(params.getFormSchemeKey());
            logHelper.error(formScheme != null ? formScheme.getTaskKey() : null, null, MODULEFILEDOWNLOAD, e.getMessage());
            throw e;
        }
        log.info("\u51c6\u5907\u5f00\u59cb\u89e3\u538b\uff0c\u89e3\u538b\u76ee\u6807\u8def\u5f84\uff1a" + filePath);
        FileUtil.unZip((File)new File(FilenameUtils.normalize(extZipFile)), (String)FilenameUtils.normalize(filePath));
        ArrayList<String> formCodes = new ArrayList<String>();
        for (String string : params.getFormKeys()) {
            FormDefine queryFormById = this.runTimeViewController.queryFormById(string);
            formCodes.add(queryFormById.getFormCode());
        }
        HashMap<String, String> extraAttributes = new HashMap<String, String>();
        extraAttributes.put("split", params.getSplit());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(params.getFormSchemeKey());
        extraAttributes.put("fromSchemeCode", formScheme.getFormSchemeCode());
        DataCommonUtils.writeVersionInfoExtra((DimensionValueSet)mergeDimensionValueSet, formCodes, (String)filePath, extraAttributes);
        if (this.queryMappings != null) {
            DataCommonUtils.buildMappings((String)params.getFormSchemeKey(), (String)filePath, (QueryMappings)this.queryMappings);
        }
        return filePath;
    }

    @Override
    public Message<CommonMessage> uploadTextData(TextParams params, CommonParams commonParams) throws Exception {
        AsyncTaskMonitor monitor = null;
        if (commonParams != null && commonParams.getMonitor() != null) {
            monitor = commonParams.getMonitor();
            this.process(monitor, 0.05);
        }
        DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)params.getDimensionSet());
        TableContext tbContext = new TableContext(null, params.getFormSchemeKey(), null, mergeDimensionValueSet, OptTypes.FORMSCHEME, params.getTextType().equals((Object)TextType.TEXTTYPE_CSV) ? ".csv" : ".txt", params.getSplit(), null);
        tbContext.setExportBizkeyorder(true);
        tbContext.setNewFileGroup(true);
        tbContext.setSplitGather(params.getSplitGather());
        tbContext.setFloatImpOpt(params.getFloatImpOpt());
        tbContext.setIoQualifier(this.ioQualifier);
        tbContext.setValidEntityExist(true);
        CommonMessage message = new CommonMessage();
        ImportCancledResult importCancledResult = new ImportCancledResult();
        importCancledResult.setImportFileDataRange(ImportFileDataRange.REGION);
        message.setImportCancledResult(importCancledResult);
        HashMap detail = new HashMap();
        message.setDetail(detail);
        String filePath = params.getFilePath();
        if (filePath == null || filePath.equals("")) {
            throw new IllegalArgumentException("filePath\u4e3a\u7a7a,\u68c0\u67e5\u53c2\u6570.");
        }
        DimensionCombination dimensionCombination = new DimensionCombinationBuilder(tbContext.getDimensionSet()).getCombination();
        DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger(MODULEFILEUPLOAD, tbContext.getFormSchemeKey(), dimensionCombination);
        dataImportLogger.startImport();
        if (this.checkAsyncTaskCancling(monitor, importCancledResult)) {
            dataImportLogger.cancelImport();
            return message;
        }
        File dir = new File(FilenameUtils.normalize(filePath));
        if (dir.isFile()) {
            this.textFileImportService.textFileData(params.getTextType(), dir, tbContext, commonParams, message, dataImportLogger);
            this.process(monitor, 1.0);
            if (detail.get("error_data") != null || ((List)detail.get("error_data")).isEmpty()) {
                importCancledResult.getSuccessFiles().add(dir.getName());
            }
        } else {
            File[] listFiles = dir.listFiles();
            int lastErrorDataCount = 0;
            for (File file : listFiles) {
                if (this.checkAsyncTaskCancling(monitor, importCancledResult)) {
                    dataImportLogger.cancelImport();
                    return message;
                }
                this.process(monitor, 0.05 + 0.7 / ((double)listFiles.length * 1.0));
                if (file.getName().equals("VERSION")) continue;
                this.textFileImportService.textFileData(params.getTextType(), file, tbContext, commonParams, message, dataImportLogger);
                if (detail.get("error_data") == null || ((List)detail.get("error_data")).size() != lastErrorDataCount) continue;
                lastErrorDataCount = ((List)detail.get("error_data")).size();
                importCancledResult.getSuccessFiles().add(file.getName());
            }
        }
        if (this.checkAsyncTaskCancling(monitor, importCancledResult)) {
            dataImportLogger.cancelImport();
            return message;
        }
        this.process(monitor, 1.0);
        dataImportLogger.finishImport();
        return message;
    }

    private void mergeDataMessage(List<Map<String, Object>> dealFileDataMessage, Set<String> noAccessDWs) {
        Iterator<Map<String, Object>> iterator = dealFileDataMessage.iterator();
        block0: while (iterator.hasNext()) {
            Map<String, Object> dataMessage = iterator.next();
            if (dataMessage.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (!Objects.nonNull(dataMessage.get("successInfo"))) continue;
            List list = (List)dataMessage.get("successInfo");
            for (ImportInformations importInformations : list) {
                if (!noAccessDWs.contains(importInformations.getUnitCode())) continue;
                dataMessage.remove("successInfo");
                continue block0;
            }
        }
    }

    private void process(AsyncTaskMonitor monitor, double progress) {
        if (monitor != null) {
            monitor.progressAndMessage(progress, "execute");
            if (progress == 1.0) {
                monitor.progressAndMessage(progress, "\u4efb\u52a1\u5b8c\u6210");
            }
        }
    }

    @Override
    public String uploadTextDataAsync(TextParams params, CommonParams commonParams) throws Exception {
        throw new DataCommonException((ErrorCode)ErrorCodeEnum.METHOD_IS_DEPRECATED);
    }

    private boolean checkAsyncTaskCancling(AsyncTaskMonitor asyncTaskMonitor, ImportCancledResult importCancledResult) {
        if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
            return true;
        }
        return false;
    }
}


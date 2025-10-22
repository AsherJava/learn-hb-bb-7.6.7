/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.exception.DataCommonException
 *  com.jiuqi.nr.data.common.exception.ErrorCode
 *  com.jiuqi.nr.data.common.exception.ErrorCodeEnum
 *  com.jiuqi.nr.data.common.param.CommonImportDetails
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.service.QueryMappings
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.attachment.service.impl;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.data.attachment.param.ExpParams;
import com.jiuqi.nr.data.attachment.param.FileConsts;
import com.jiuqi.nr.data.attachment.param.FileDataRow;
import com.jiuqi.nr.data.attachment.param.FileFilter;
import com.jiuqi.nr.data.attachment.param.ImpParams;
import com.jiuqi.nr.data.attachment.service.ExpFileService;
import com.jiuqi.nr.data.attachment.service.ImpFileService;
import com.jiuqi.nr.data.attachment.service.impl.FileDataQueryService;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.exception.DataCommonException;
import com.jiuqi.nr.data.common.exception.ErrorCode;
import com.jiuqi.nr.data.common.exception.ErrorCodeEnum;
import com.jiuqi.nr.data.common.param.CommonImportDetails;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.service.QueryMappings;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFileService
implements ExpFileService,
ImpFileService {
    private static final String SEPARATOR = "/";
    private static final Logger log = LoggerFactory.getLogger(DataFileService.class);
    public static final String MODULEFILEDOWNLOAD = "\u6570\u636e\u670d\u52a1-\u9644\u4ef6\u5bfc\u51fa";
    public static final String MODULEFILEUPLOAD = "\u6570\u636e\u670d\u52a1-\u9644\u4ef6\u5bfc\u5165";
    private static final String SKIP_TABLE_IMPORT = ",\u8df3\u8fc7\u8be5\u8868\u7684\u5bfc\u5165";
    private static final String NOT_FIND_REPORT = "\u672a\u627e\u5230\u6620\u5c04\u91cc\u7684\u62a5\u8868";
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private FileDataQueryService fileDataQueryService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired(required=false)
    private QueryMappings queryMappings;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;

    @Override
    public String downloadFiles(ExpParams params) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(MODULEFILEDOWNLOAD, OperLevel.USER_OPER);
        DimensionCollection dimensions = params.getDimensions();
        String filePath = params.getFilePath();
        List<String> formKeys = params.getFormKeys();
        String formSchemeKey = params.getFormSchemeKey();
        FileFilter filter = params.getFilter();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (dimensions == null || dimensions.getDimensionCombinations().isEmpty()) {
            logHelper.info(formScheme != null ? formScheme.getTaskKey() : null, null, MODULEFILEDOWNLOAD, "dimensions \u7ef4\u5ea6\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a.");
            throw new IllegalArgumentException("dimensions \u7ef4\u5ea6\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a.");
        }
        filePath = filePath == null ? System.getProperty("java.io.tmpdir") + SEPARATOR + UUID.randomUUID().toString() + SEPARATOR : ((filePath = filePath.replace("\\\\", "\\/")).endsWith(SEPARATOR) ? filePath + System.currentTimeMillis() + SEPARATOR : filePath + SEPARATOR + System.currentTimeMillis() + SEPARATOR);
        filePath = FilenameUtils.normalize(filePath);
        String dataSchemeKey = this.getDataSchemeKey(formKeys.get(0));
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(dataSchemeKey);
        commonParamsDTO.setTaskKey(formScheme.getTaskKey());
        File dir = new File(filePath);
        File f = new File(filePath + "ATTACHMENT_RELATION_INFO.CSV");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!f.exists()) {
            f.createNewFile();
        }
        try (FileOutputStream fos = new FileOutputStream(f);){
            CsvWriter cwriter = new CsvWriter((OutputStream)fos, ',', StandardCharsets.UTF_8);
            cwriter.writeRecord(FileConsts.ATTACHMENT_RELATION_HEADER, false);
            for (String formKey : formKeys) {
                List<DataRegionDefine> regions = this.getRegions(formKey);
                List<FileDataRow> fileDataRows = this.fileDataQueryService.queryFileData(regions, dimensions, formScheme, filter, logHelper);
                for (FileDataRow fileDataRow : fileDataRows) {
                    String fileGroup = fileDataRow.getFileGroup();
                    if (fileGroup != null && !fileGroup.equals("")) {
                        List fileKeys = this.attachmentIOService.getFileByGroup(fileGroup, commonParamsDTO);
                        if (fileKeys == null || fileKeys.isEmpty()) {
                            this.writeLine(cwriter, fileDataRow);
                            continue;
                        }
                        for (FileInfo fileInfo : fileKeys) {
                            this.writeLine(cwriter, fileDataRow, fileInfo);
                            byte[] download = this.attachmentIOService.download(fileInfo.getKey(), commonParamsDTO);
                            if (download == null) continue;
                            this.writeFiles(filePath, fileInfo, download);
                        }
                    } else {
                        this.writeLine(cwriter, fileDataRow);
                    }
                    if (fileDataRow.getFileKey() == null) continue;
                    this.attachmentIOService.download(fileDataRow.getFileKey(), commonParamsDTO);
                }
            }
            cwriter.close();
        }
        catch (IOException e) {
            log.error(e.getMessage());
            logHelper.error(formScheme != null ? formScheme.getTaskKey() : null, null, MODULEFILEDOWNLOAD, e.getMessage());
            throw new IOException("\u5bfc\u51fa\u6307\u6807\u9644\u4ef6\u51fa\u9519", e);
        }
        this.writeVersionInfo(dimensions.combineWithoutVarDim(), formKeys, formSchemeKey, filePath);
        if (this.queryMappings != null) {
            DataCommonUtils.buildMappings((String)params.getFormSchemeKey(), (String)filePath, (QueryMappings)this.queryMappings);
        }
        return filePath;
    }

    private void writeVersionInfo(DimensionValueSet dimensionValueSet, List<String> formKeys, String formSchemeKey, String filePath) throws IOException {
        HashMap<String, Object> versionDim = new HashMap<String, Object>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            versionDim.put(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
        }
        ArrayList<String> formCodes = new ArrayList<String>();
        for (String form : formKeys) {
            FormDefine queryFormById = this.runTimeViewController.queryFormById(form);
            if (queryFormById == null) continue;
            formCodes.add(queryFormById.getFormCode());
        }
        DataCommonUtils.writeVersionInfo((DimensionValueSet)dimensionValueSet, formCodes, (String)filePath);
    }

    private void writeLine(CsvWriter cwriter, FileDataRow fileDataRow, FileInfo fileInfo) throws IOException {
        String[] arg0 = new String[]{fileDataRow.getDimNameList(), fileDataRow.getDimValueList(), fileDataRow.getFormKey(), fileDataRow.getFormCode(), fileDataRow.getFieldKey(), fileDataRow.getFieldCode(), fileDataRow.getFileGroup(), fileInfo.getKey()};
        cwriter.writeRecord(arg0);
    }

    private void writeLine(CsvWriter cwriter, FileDataRow fileDataRow) throws IOException {
        String[] arg0 = new String[]{fileDataRow.getDimNameList(), fileDataRow.getDimValueList(), fileDataRow.getFormKey(), fileDataRow.getFormCode(), fileDataRow.getFieldKey(), fileDataRow.getFieldCode(), fileDataRow.getFileGroup(), fileDataRow.getFileKey()};
        cwriter.writeRecord(arg0);
    }

    @Override
    public Message<CommonMessage> uploadFileds(ImpParams prams, CommonParams commonParams) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(MODULEFILEUPLOAD, OperLevel.USER_OPER);
        String filePath = prams.getFilePath();
        String formSchemeKey = prams.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        AsyncTaskMonitor monitor = null;
        if (commonParams != null && commonParams.getMonitor() != null) {
            monitor = commonParams.getMonitor();
            this.progress(monitor, 0.05);
        }
        CommonMessage message = new CommonMessage();
        ArrayList<CommonImportDetails> details = new ArrayList<CommonImportDetails>();
        message.setDetail(details);
        File csvFile = new File(FilenameUtils.normalize(filePath.endsWith(SEPARATOR) || filePath.endsWith("\\") ? filePath + "ATTACHMENT_RELATION_INFO.CSV" : filePath + SEPARATOR + "ATTACHMENT_RELATION_INFO.CSV"));
        if (!csvFile.exists()) {
            logHelper.error(formScheme.getTaskKey(), null, MODULEFILEUPLOAD, "filePath \u4e0b\u6ca1\u6709\u9644\u4ef6\u5173\u8054\u8868:ATTACHMENT_RELATION_INFO.CSV\u6587\u4ef6.");
            throw new IllegalArgumentException("filePath \u4e0b\u6ca1\u6709\u9644\u4ef6\u5173\u8054\u8868:ATTACHMENT_RELATION_INFO.CSV\u6587\u4ef6.");
        }
        try (FileInputStream fos = new FileInputStream(csvFile);){
            int i;
            CsvReader reader = new CsvReader((InputStream)fos, StandardCharsets.UTF_8);
            reader.readHeaders();
            String[] headers = reader.getHeaders();
            HashMap<String, Integer> headerMap = new HashMap<String, Integer>();
            for (i = 0; i < headers.length; ++i) {
                headerMap.put(headers[i], i);
            }
            for (i = 0; i < FileConsts.ATTACHMENT_RELATION_HEADER.length; ++i) {
                if (headerMap.containsKey(FileConsts.ATTACHMENT_RELATION_HEADER[i])) continue;
                logHelper.error(formScheme.getTaskKey(), null, MODULEFILEUPLOAD, "CSV\u6587\u4ef6\u5185\u5bb9\u8868\u5934\u4e0d\u7b26\u5408\u89c4\u8303.");
                throw new IllegalArgumentException("CSV\u6587\u4ef6\u5185\u5bb9\u8868\u5934\u4e0d\u7b26\u5408\u89c4\u8303.");
            }
            HashMap formDataMap = new HashMap();
            ArrayList<String> formMapping = new ArrayList<String>();
            this.progress(monitor, 0.1);
            while (reader.readRecord()) {
                FileDataRow row = new FileDataRow(reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[0]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[1]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[2]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[3]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[4]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[5]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[6]), reader.get(FileConsts.ATTACHMENT_RELATION_HEADER[7]));
                if (formDataMap.containsKey(row.getFormCode())) {
                    ((List)formDataMap.get(row.getFormCode())).add(row);
                    continue;
                }
                ArrayList<FileDataRow> rowList = new ArrayList<FileDataRow>();
                rowList.add(row);
                formDataMap.put(row.getFormCode(), rowList);
                formMapping.add(row.getFormCode());
            }
            this.progress(monitor, 0.2);
            Map originFormCode = null;
            if (commonParams != null && commonParams.getMapping() != null) {
                originFormCode = commonParams.getMapping().getOriginFormCode(formMapping);
            }
            for (String form : formDataMap.keySet()) {
                CommonImportDetails cid;
                FormDefine formDefine;
                List list;
                block25: {
                    this.progress(monitor, 0.2 + 0.7 / (1.0 * (double)formDataMap.size()));
                    list = (List)formDataMap.get(form);
                    if (list == null || list.isEmpty()) continue;
                    formDefine = null;
                    if (originFormCode != null && originFormCode.get(form) != null) {
                        try {
                            formDefine = this.runTimeViewController.queryFormByCodeInScheme(formSchemeKey, (String)originFormCode.get(form));
                            break block25;
                        }
                        catch (Exception e) {
                            log.error(e.getMessage());
                            cid = new CommonImportDetails(null, (String)originFormCode.get(form), (String)originFormCode.get(form), null, NOT_FIND_REPORT + (String)originFormCode.get(form) + SKIP_TABLE_IMPORT);
                            details.add(cid);
                            logHelper.error(formScheme.getTaskKey(), null, MODULEFILEUPLOAD, e.getMessage());
                            continue;
                        }
                    }
                    formDefine = this.runTimeViewController.queryFormById(((FileDataRow)list.get(0)).getFormKey());
                    if (formDefine == null) {
                        CommonImportDetails cid2 = new CommonImportDetails(null, form, form, null, NOT_FIND_REPORT + form + SKIP_TABLE_IMPORT);
                        details.add(cid2);
                        logHelper.error(formScheme.getTaskKey(), null, MODULEFILEUPLOAD, NOT_FIND_REPORT + form + SKIP_TABLE_IMPORT);
                        continue;
                    }
                }
                List<DataRegionDefine> regions = this.getRegions(formDefine.getKey());
                if (regions == null || regions.isEmpty()) {
                    cid = new CommonImportDetails(null, form, form, null, "\u672a\u627e\u5230\u62a5\u8868" + form + "\u76f8\u5173\u7684\u533a\u57df\u5c5e\u6027,\u8df3\u8fc7\u8be5\u8868\u7684\u5bfc\u5165");
                    details.add(cid);
                    logHelper.error(formScheme.getTaskKey(), null, MODULEFILEUPLOAD, "\u672a\u627e\u5230\u62a5\u8868" + form + "\u76f8\u5173\u7684\u533a\u57df\u5c5e\u6027,\u8df3\u8fc7\u8be5\u8868\u7684\u5bfc\u5165");
                    continue;
                }
                this.fileDataQueryService.updateGroupKeys(list, formDefine, formSchemeKey, regions, filePath, commonParams, message);
            }
            this.process(monitor, message);
        }
        return message;
    }

    private void process(AsyncTaskMonitor monitor, Object message) {
        if (monitor != null) {
            monitor.progressAndMessage(1.0, "finish");
            monitor.finish("\u5bfc\u5165\u5b8c\u6210", message);
        }
    }

    private void progress(AsyncTaskMonitor monitor, double process) {
        if (monitor != null) {
            monitor.progressAndMessage(process, "execute");
            if (process == 1.0) {
                monitor.finish("\u5bfc\u5165\u5b8c\u6210", (Object)"\u5bfc\u5165\u5b8c\u6210");
            }
        }
    }

    private String getDataSchemeKey(String formKey) {
        List fieldDefinesInRange = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInForm(formKey));
        try {
            List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(((FieldDefine)fieldDefinesInRange.get(0)).getOwnerTableKey());
            if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty() && deployInfoByDataTableKey.get(0) != null) {
                return ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getDataSchemeKey();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private List<DataRegionDefine> getRegions(String formKey) {
        return this.runTimeViewController.getAllRegionsInForm(formKey);
    }

    private void writeFiles(String filePath, FileInfo fileInfo, byte[] download) throws IOException {
        String newFileDir = filePath + DigestUtils.md5Hex(fileInfo.getKey());
        File newDir = new File(FilenameUtils.normalize(newFileDir));
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(newFileDir + SEPARATOR + fileInfo.getName());
             ByteArrayInputStream is = new ByteArrayInputStream(download);){
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                ((OutputStream)out).write(buff, 0, len);
            }
        }
        catch (Exception e) {
            log.error("\u5199\u6587\u4ef6\u51fa\u9519{}", (Object)e.getMessage());
        }
    }

    @Override
    public String uploadFiledsAsync(ImpParams params, CommonParams commonParams) throws IOException {
        throw new DataCommonException((ErrorCode)ErrorCodeEnum.METHOD_IS_DEPRECATED);
    }
}


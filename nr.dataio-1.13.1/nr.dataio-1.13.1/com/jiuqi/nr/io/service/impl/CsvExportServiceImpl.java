/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.io.service.impl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.service.ExportService;
import com.jiuqi.nr.io.service.FileExportService;
import com.jiuqi.nr.io.service.impl.CSVRegionDataReader;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="CsvExportServiceImpl")
public class CsvExportServiceImpl
implements FileExportService {
    private static final Logger log = LoggerFactory.getLogger(CsvExportServiceImpl.class);
    private static final String MODULCSV = "CSV\u5bfc\u51fa";
    @Autowired
    private ExportService exportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileAreaService fileAreaService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private NrIoProperties nrIoProperties;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private boolean exportByReader = true;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @Override
    public void getExtZipOutputStream(TableContext tbContext, ZipOutputStream zipos, AsyncTaskMonitor monitor) {
        List forms;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("CSV\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(tbContext.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        try {
            logDimension = new LogDimensionCollection();
            Object value = tbContext.getDimensionSet().getValue(queryEntity.getDimensionName());
            String[] dims = null;
            if (value instanceof List) {
                dims = new String[((List)value).size()];
                ((List)value).toArray(dims);
                logDimension.setDw(formScheme.getDw(), dims);
            } else {
                logDimension.setDw(formScheme.getDw(), new String[]{value.toString()});
            }
            logDimension.setPeriod(formScheme.getDateTime(), tbContext.getDimensionSet().getValue("DATATIME").toString());
        }
        catch (Exception e) {
            log.error("\u6784\u9020\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5f00\u59cb", MODULCSV);
        if (tbContext.getTaskKey() == null) {
            tbContext.setTaskKey(formScheme.getTaskKey());
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "");
        }
        zipos.setMethod(8);
        CsvWriter csvWriter = new CsvWriter((OutputStream)zipos, new Character(tbContext.getSplit().toCharArray()[0]).charValue(), Charset.forName("UTF-8"));
        if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
            forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
            tbContext.setOptType(OptTypes.FORM);
            for (FormDefine item : forms) {
                tbContext.setFormKey(item.getKey());
                List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                this.initZipFileStream(tbContext, zipos, monitor, result, forms.size(), logHelper, logDimension);
            }
        } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
            forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
            tbContext.setOptType(OptTypes.FORM);
            for (FormDefine item : forms) {
                tbContext.setFormKey(item.getKey());
                List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                this.initZipFileStream(tbContext, zipos, monitor, result, forms.size(), logHelper, logDimension);
            }
        } else {
            List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
            this.initZipFileStream(tbContext, zipos, monitor, result, 1, logHelper, logDimension);
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.9, "");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5b8c\u6210", "CSV\u5bfc\u51fa\u5b8c\u6210");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initZipFileStream(TableContext tbContext, ZipOutputStream zipos, AsyncTaskMonitor monitor, List<IRegionDataSet> result, int formSize, DataServiceLogHelper logHelper, LogDimensionCollection logDimension) {
        CsvWriter csvWriter = new CsvWriter((OutputStream)zipos, new Character(tbContext.getSplit().toCharArray()[0]).charValue(), Charset.forName("UTF-8"));
        for (IRegionDataSet item : result) {
            String fileName = "";
            try {
                block23: {
                    List<ExportFieldDefine> def = item.getFieldDataList();
                    if (def.size() == 0) continue;
                    RegionData region = item.getRegionData();
                    fileName = region.getFormCode();
                    if (region.getType() == 3) {
                        fileName = fileName + "_F" + region.getRegionTop();
                    }
                    fileName = fileName + ".csv";
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipos.putNextEntry(zipEntry);
                    StringBuffer fieldDefineHead = new StringBuffer();
                    for (ExportFieldDefine fieldDefine : def) {
                        fieldDefineHead.append(fieldDefine.getCode()).append(tbContext.getSplit());
                    }
                    csvWriter.write(fieldDefineHead.deleteCharAt(fieldDefineHead.length() - 1).append("\r\n").toString());
                    while (item.hasNext()) {
                        ArrayList dataRows = (ArrayList)item.next();
                        if (dataRows.isEmpty()) continue;
                        StringBuffer formatResul = new StringBuffer();
                        Arrays.asList(dataRows.toArray()).stream().forEach(x -> formatResul.append(x).append(tbContext.getSplit()));
                        csvWriter.write(formatResul.deleteCharAt(formatResul.length() - 1).append("\r\n").toString());
                    }
                    zipos.closeEntry();
                    try {
                        if (!tbContext.isAttachment()) break block23;
                        this.fileAreaService = this.fileService.area(tbContext.getAttachmentArea());
                        List<FileInfo> attamentFiles = item.getAttamentFiles();
                        for (FileInfo fileInfo : attamentFiles) {
                            byte[] download = this.fileAreaService.download(fileInfo.getKey());
                            if (null == download) continue;
                            ZipEntry fileZipEntry = new ZipEntry("Attament/" + fileInfo.getKey() + "/" + fileInfo.getName() + fileInfo.getName() != null && fileInfo.getName().contains(".") ? fileInfo.getName() : fileInfo.getName() + fileInfo.getExtension());
                            zipos.putNextEntry(fileZipEntry);
                            DataOutputStream os = new DataOutputStream(zipos);
                            Throwable throwable = null;
                            try {
                                os.write(download);
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                            finally {
                                if (os == null) continue;
                                if (throwable != null) {
                                    try {
                                        os.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    continue;
                                }
                                os.close();
                            }
                        }
                    }
                    catch (Exception e) {
                        logHelper.error(tbContext.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5f02\u5e38", "\u62a5\u8868\uff1a" + region.getFormCode() + ",\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519:" + e.getMessage());
                        log.info("\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519{}", e);
                    }
                }
                if (null == monitor) continue;
                monitor.progressAndMessage(0.8 / (double)result.size() / (double)formSize, "");
            }
            catch (IOException e) {
                logHelper.error(tbContext.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5f02\u5e38", "\u62a5\u8868\uff1a" + item.getRegionData().getFormCode() + ",\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519:" + e.getMessage());
                log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
            }
            finally {
                if (csvWriter == null) continue;
                csvWriter.close();
            }
        }
    }

    @Override
    public String getExtZipFile(TableContext tbContext, AsyncTaskMonitor monitor) {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("CSV\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(tbContext.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        this.exportByReader = this.nrIoProperties.isExportByReader();
        try {
            logDimension = new LogDimensionCollection();
            Object value = tbContext.getDimensionSet().getValue(queryEntity.getDimensionName());
            String[] dims = null;
            if (value instanceof List) {
                dims = new String[((List)value).size()];
                ((List)value).toArray(dims);
                logDimension.setDw(formScheme.getDw(), dims);
            } else {
                logDimension.setDw(formScheme.getDw(), new String[]{value.toString()});
            }
            logDimension.setPeriod(formScheme.getDateTime(), tbContext.getDimensionSet().getValue("DATATIME").toString());
        }
        catch (Exception e1) {
            log.error("\u6784\u9020\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5f00\u59cb", MODULCSV);
        if (tbContext.getTaskKey() == null) {
            tbContext.setTaskKey(formScheme.getTaskKey());
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "");
        }
        String path = ExtConstants.ROOTPATH + "/" + UUID.randomUUID().toString() + File.separator + "ExportTxtDatas.zip";
        File file = null;
        try {
            file = FileUtil.createIfNotExists(path);
        }
        catch (IOException e) {
            log.info("\u6587\u4ef6\u521b\u5efa\u51fa\u9519{}".concat(e.getMessage()));
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             ZipOutputStream zipos = new ZipOutputStream(fos);){
            zipos.setMethod(8);
            CsvWriter csvWriter = new CsvWriter((OutputStream)zipos, new Character(tbContext.getSplit().toCharArray()[0]).charValue(), Charset.forName("UTF-8"));
            if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
                List forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.initZipFile(tbContext, monitor, result, zipos, forms.size(), csvWriter, logHelper, logDimension);
                }
            } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
                List forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.initZipFile(tbContext, monitor, result, zipos, forms.size(), csvWriter, logHelper, logDimension);
                }
            } else {
                List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                this.initZipFile(tbContext, monitor, result, zipos, 1, csvWriter, logHelper, logDimension);
            }
            csvWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.9, "");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "CSV\u5bfc\u51fa\u5b8c\u6210", "CSV\u5bfc\u51fa\u5b8c\u6210");
        return path;
    }

    private void initZipFile(TableContext tbContext, AsyncTaskMonitor monitor, List<IRegionDataSet> result, ZipOutputStream zipos, int formSize, CsvWriter csvWriter, DataServiceLogHelper logHelper, LogDimensionCollection logDimension) {
        Object versionDv;
        FilterOutputStream os = null;
        DimensionValueSet dimensionSet = tbContext.getDimensionSet();
        if (null != dimensionSet.getValue("VERSIONID") && !((String)(versionDv = dimensionSet.getValue("VERSIONID").toString())).equals("00000000-0000-0000-0000-000000000000")) {
            os = this.getVersionData(tbContext, result, zipos, (DataOutputStream)os, (String)versionDv, csvWriter);
            return;
        }
        for (IRegionDataSet item : result) {
            String fileName = "";
            try {
                List<ExportFieldDefine> def = item.getFieldDataList();
                if (def.size() == 0) continue;
                RegionData region = item.getRegionData();
                FormDefine formDefine = this.runTimeViewController.queryFormById(region.getFormKey());
                String formName = formDefine.getFormCode() + "\u3010" + formDefine.getTitle() + "\u3011";
                String regionName = region.getKey() + "\u3010" + region.getTitle() + "\u3011";
                log.info("\u5f00\u59cb\u5bfc\u51fa\u62a5\u8868\uff1a{}\uff1b\u533a\u57df\uff1a{}", (Object)formName, (Object)regionName);
                fileName = region.getFormCode();
                if (region.getType() == 3) {
                    fileName = fileName + "_F" + region.getRegionTop();
                }
                String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
                String separator = " ";
                if (separatorMessage.equals("1")) {
                    separator = "_";
                } else if (separatorMessage.equals("2")) {
                    separator = "&";
                }
                if (StringUtils.isNotEmpty((String)tbContext.getSecretLevelTitle())) {
                    fileName = fileName + separator + tbContext.getSecretLevelTitle();
                }
                fileName = fileName + ".csv";
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipos.putNextEntry(zipEntry);
                String[] fieldDefineArray = new String[def.size()];
                for (int i = 0; i < def.size(); ++i) {
                    fieldDefineArray[i] = def.get(i).getCode();
                }
                csvWriter.writeRecord(fieldDefineArray);
                CSVRegionDataReader regionDataReader = new CSVRegionDataReader(true, csvWriter);
                if (this.exportByReader) {
                    item.queryToDataRowReader(regionDataReader);
                } else {
                    while (item.hasNext()) {
                        List dataRow = (List)item.next();
                        regionDataReader.readRowData(dataRow);
                    }
                }
                csvWriter.flush();
                zipos.closeEntry();
                try {
                    if (tbContext.isAttachment()) {
                        this.fileAreaService = this.fileService.area(tbContext.getAttachmentArea());
                        List<FileInfo> attamentFiles = item.getAttamentFiles();
                        for (FileInfo fileInfo : attamentFiles) {
                            byte[] download = this.fileAreaService.download(fileInfo.getKey());
                            if (null == download) continue;
                            ZipEntry fileZipEntry = new ZipEntry("Attament/" + fileInfo.getKey() + "/" + (fileInfo.getName() != null && fileInfo.getName().contains(".") ? fileInfo.getName() : fileInfo.getName() + fileInfo.getExtension()));
                            zipos.putNextEntry(fileZipEntry);
                            os = new DataOutputStream(zipos);
                            os.write(download);
                        }
                    }
                }
                catch (Exception e) {
                    logHelper.error(tbContext.getTaskKey(), logDimension, MODULCSV, "\u62a5\u8868\uff1a" + item.getRegionData().getFormCode() + ",\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519:" + e.getMessage());
                    log.info("\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519{}", e);
                }
                if (null != monitor) {
                    monitor.progressAndMessage(0.8 / (double)result.size() / (double)formSize, "");
                }
                log.info("\u62a5\u8868\uff1a{}\uff1b\u533a\u57df\uff1a{} \u5bfc\u51fa\u5b8c\u6210", (Object)formName, (Object)regionName);
            }
            catch (IOException e) {
                logHelper.error(tbContext.getTaskKey(), logDimension, MODULCSV, "\u62a5\u8868\uff1a" + item.getRegionData().getFormCode() + ",\u5199\u9644\u4ef6\u6570\u636e\u51fa\u9519:" + e.getMessage());
                log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
            }
        }
        try {
            if (os != null) {
                os.close();
            }
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataOutputStream getVersionData(TableContext tbContext, List<IRegionDataSet> result, ZipOutputStream zipos, DataOutputStream os, String versionDv, CsvWriter csvWriter) {
        List fileList = this.fileInfoService.getFileInfoByGroup(tbContext.getFormKey(), "DataVer", FileStatus.AVAILABLE);
        ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
        for (FileInfo item : fileList) {
            if (!item.getName().equals(versionDv)) continue;
            tableFile.add(item);
        }
        List fieldKeys = this.runTimeViewController.getFieldKeysInForm(tbContext.getFormKey());
        HashSet fieldSet = new HashSet();
        fieldSet.addAll(fieldKeys);
        for (IRegionDataSet item : result) {
            String fileName = "";
            try {
                List<ExportFieldDefine> def = item.getFieldDataList();
                if (def.size() == 0) continue;
                RegionData region = item.getRegionData();
                fileName = region.getFormCode();
                if (region.getType() == 3) {
                    fileName = fileName + "_F" + region.getRegionTop();
                }
                fileName = fileName + ".csv";
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipos.putNextEntry(zipEntry);
                StringBuffer fieldDefineHead = new StringBuffer();
                String[] fieldDefineArray = new String[def.size()];
                for (int i = 0; i < def.size(); ++i) {
                    fieldDefineArray[i] = def.get(i).getCode();
                }
                csvWriter.writeRecord(fieldDefineArray);
                List listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.runTimeViewController.getFieldKeysInRegion(region.getKey()));
                for (FileInfo fileInfo : tableFile) {
                    byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
                    String resultsss = null;
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
                        out.write(bs);
                        resultsss = new String(out.toByteArray());
                    }
                    catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                    List formList = (List)this.objectMapper.readValue(resultsss, Object.class);
                    HashSet<String> tableKeys = new HashSet<String>();
                    for (FieldDefine fieldDefine : listFieldDefine) {
                        tableKeys.add(fieldDefine.getOwnerTableKey());
                    }
                    ArrayList<Object[]> lists = new ArrayList<Object[]>();
                    String[] heads = fieldDefineHead.toString().replace("\r\n", "").split(tbContext.getSplit());
                    for (Object object : formList) {
                        Map o = (Map)object;
                        for (Map.Entry table : o.entrySet()) {
                            int i = 0;
                            TableDefine tableDef = this.dataDefinitionRuntimeController.queryTableDefine((String)table.getKey());
                            for (Map it : (List)table.getValue()) {
                                for (int j = 0; j < heads.length; ++j) {
                                    try {
                                        Object[] row;
                                        List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableDef.getKey());
                                        if (deployInfoByDataTableKey == null || deployInfoByDataTableKey.isEmpty() || !((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName().equals(heads[j].split("\\.")[0])) continue;
                                        if (lists.isEmpty() || lists.size() <= i) {
                                            row = new Object[heads.length];
                                            lists.add(row);
                                        }
                                        row = (Object[])lists.get(i);
                                        row[j] = String.valueOf(it.get(heads[j].split("\\.")[1]));
                                        continue;
                                    }
                                    catch (Exception e) {
                                        log.error(e.getMessage(), e);
                                    }
                                }
                                ++i;
                            }
                        }
                    }
                    String[] rowArray = new String[lists.size()];
                    for (int i = 0; i < lists.size(); ++i) {
                        String strRow;
                        rowArray[i] = strRow = Arrays.toString((Object[])lists.get(i)).replace("[", "").replace("]", "").replace(" ", "");
                    }
                    csvWriter.writeRecord(rowArray);
                    csvWriter.flush();
                    zipos.closeEntry();
                }
            }
            catch (Exception e) {
                log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
            }
            finally {
                csvWriter.close();
            }
        }
        return os;
    }
}


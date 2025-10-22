/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 */
package com.jiuqi.nr.io.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.output.Datablocks;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.params.output.ExportJsonData;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.service.ExportService;
import com.jiuqi.nr.io.service.FileExportService;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.util.FileUtil;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="JsonExportServiceImpl")
public class JsonExportServiceImpl
implements FileExportService {
    private static final Logger log = LoggerFactory.getLogger(JsonExportServiceImpl.class);
    @Autowired
    private ExportService exportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileAreaService fileAreaService;
    @Autowired(required=false)
    private MultistageUnitReplace multistageUnitReplace;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getExtZipOutputStream(TableContext tbContext, ZipOutputStream zipos, AsyncTaskMonitor monitor) {
        List<IRegionDataSet> result;
        List forms;
        zipos.setMethod(8);
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "");
        }
        ArrayList<ImportInformations> successInfo = new ArrayList<ImportInformations>();
        ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
        boolean error = false;
        if (this.multistageUnitReplace != null) {
            tbContext.setMultistageUnitReplace(this.multistageUnitReplace);
        }
        if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
            forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
            tbContext.setOptType(OptTypes.FORM);
            for (FormDefine item : forms) {
                tbContext.setFormKey(item.getKey());
                result = this.exportService.getRegionsDatas(tbContext);
                try {
                    this.generalZip(result, zipos, tbContext);
                }
                catch (IOException e) {
                    log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                    error = true;
                    ImportInformations err = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                    errorInfo.add(err);
                }
                finally {
                    if (!error) {
                        ImportInformations e = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), null, "");
                        successInfo.add(e);
                    }
                    error = false;
                }
                if (null == monitor) continue;
                monitor.progressAndMessage(0.1 + (double)(0.8 / (double)forms.size() / (double)result.size() == 0.0 ? 1 : result.size()), "");
            }
        } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
            forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
            tbContext.setOptType(OptTypes.FORM);
            for (FormDefine item : forms) {
                tbContext.setFormKey(item.getKey());
                result = this.exportService.getRegionsDatas(tbContext);
                try {
                    this.generalZip(result, zipos, tbContext);
                }
                catch (IOException e) {
                    error = true;
                    ImportInformations err = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                    errorInfo.add(err);
                    log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                }
                finally {
                    if (!error) {
                        ImportInformations e = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), null, "");
                        successInfo.add(e);
                    }
                    error = false;
                }
                if (null == monitor) continue;
                monitor.progressAndMessage(0.1 + (double)(0.8 / (double)forms.size() / (double)result.size() == 0.0 ? 1 : result.size()), "");
            }
        } else {
            List<IRegionDataSet> result2 = this.exportService.getRegionsDatas(tbContext);
            try {
                this.generalZip(result2, zipos, tbContext);
            }
            catch (IOException e) {
                error = true;
                log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                FormDefine formDefine = this.runTimeViewController.queryFormById(tbContext.getFormKey());
                if (null != formDefine) {
                    ImportInformations err = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                    errorInfo.add(err);
                }
            }
            finally {
                FormDefine formDefine;
                if (!error && null != (formDefine = this.runTimeViewController.queryFormById(tbContext.getFormKey()))) {
                    ImportInformations e = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), null, "");
                    successInfo.add(e);
                }
                error = false;
            }
            if (null != monitor) {
                monitor.progressAndMessage((double)(0.8 / (double)result2.size() == 0.0 ? 1 : result2.size()), "");
            }
        }
        try {
            ZipEntry zipEntry = new ZipEntry("successInfo.json");
            zipos.putNextEntry(zipEntry);
            DataOutputStream os = new DataOutputStream(zipos);
            os.writeBytes(objectMapper.writeValueAsString(successInfo));
            ZipEntry errzipEntry = new ZipEntry("errorInfo.json");
            zipos.putNextEntry(errzipEntry);
            DataOutputStream erros = new DataOutputStream(zipos);
            erros.writeBytes(objectMapper.writeValueAsString(errorInfo));
        }
        catch (IOException e1) {
            log.info("\u5199\u5bfc\u51fa\u65e5\u5fd7\u51fa\u9519{}", (Object)e1.getMessage(), (Object)e1);
        }
        try {
            zipos.finish();
            zipos.close();
        }
        catch (IOException e) {
            log.info("\u5173\u95ed\u6587\u4ef6\u6d41\u51fa\u9519{}", e);
        }
    }

    private void generalZip(List<IRegionDataSet> result, ZipOutputStream zipos, TableContext tbContext) throws IOException {
        if (null == result || result.size() == 0) {
            return;
        }
        DataOutputStream os = null;
        String suffix = ".json";
        ExportJsonData ejd = new ExportJsonData();
        String fileName = "";
        ArrayList<Datablocks> datablocks = new ArrayList<Datablocks>();
        List<ExportEntity> entitys = null;
        if (tbContext.isExportEntitys()) {
            entitys = this.exportService.getEntitys(tbContext);
        }
        for (IRegionDataSet item : result) {
            List<ExportFieldDefine> def;
            Datablocks datablock = new Datablocks();
            RegionData region = item.getRegionData();
            if (null == ejd.getFormCode() || fileName.equals("")) {
                fileName = region.getFormCode();
                ejd.setFormCode(fileName);
                fileName = fileName + suffix;
            }
            if ((def = item.getFieldDataList()).size() == 0) continue;
            datablock.setFields(def);
            datablock.setIsFloat(item.isFloatRegion());
            datablock.setRegionTop(region.getRegionTop());
            ArrayList<Object> datas = new ArrayList<Object>();
            boolean zipEntryFlag = false;
            ArrayList firstRow = null;
            if (item.hasNext()) {
                firstRow = (ArrayList)item.next();
                datablock.setTotalCount(item.getTotalCount());
            }
            ArrayList<ZipEntry> zipEntrys = new ArrayList<ZipEntry>();
            int zipEntryRowsCount = 0;
            if (null != firstRow && firstRow.size() > 0 && item.getTotalCount() > 20000) {
                ArrayList<String> dataFilesName = new ArrayList<String>();
                for (int i = 0; i < (int)Math.ceil((double)item.getTotalCount() * 1.0 / 10000.0); ++i) {
                    String dataFileName = region.getFormCode() + "_ROWDATAS" + (region.getType() == 3 ? "_F" : "") + region.getRegionTop() + "_" + i + suffix;
                    ZipEntry zipEntry = new ZipEntry(dataFileName);
                    dataFilesName.add(dataFileName);
                    zipEntrys.add(zipEntry);
                }
                zipos.putNextEntry((ZipEntry)zipEntrys.get(0));
                zipEntryFlag = true;
                datablock.setLinkDatasFilesName(dataFilesName);
                os = new DataOutputStream(zipos);
                os.writeBytes("[" + objectMapper.writeValueAsString((Object)firstRow) + "\r\n");
                ++zipEntryRowsCount;
            } else if (firstRow != null && !firstRow.isEmpty()) {
                datas.add(firstRow);
            }
            while (item.hasNext()) {
                ArrayList dataRow = (ArrayList)item.next();
                if (zipEntryFlag) {
                    if (zipEntryRowsCount % 10000 == 0) {
                        os.writeBytes("]");
                        zipos.putNextEntry((ZipEntry)zipEntrys.get(zipEntryRowsCount / 10000));
                        os = new DataOutputStream(zipos);
                        os.writeBytes("[".concat(objectMapper.writeValueAsString((Object)dataRow)));
                        ++zipEntryRowsCount;
                        continue;
                    }
                    os.writeBytes(",".concat(objectMapper.writeValueAsString((Object)dataRow)));
                    if (++zipEntryRowsCount != item.getTotalCount()) continue;
                    os.writeBytes("]");
                    continue;
                }
                if (dataRow == null || dataRow.isEmpty()) continue;
                datas.add(dataRow);
            }
            try {
                if (tbContext.isAttachment()) {
                    this.fileAreaService = this.fileService.area(tbContext.getAttachmentArea());
                    List<FileInfo> attamentFiles = item.getAttamentFiles();
                    for (FileInfo fileInfo : attamentFiles) {
                        byte[] download = this.fileAreaService.download(fileInfo.getKey());
                        if (null == download) continue;
                        ZipEntry zipEntry = new ZipEntry("Attament/" + fileInfo.getKey() + "/" + fileInfo.getName());
                        zipos.putNextEntry(zipEntry);
                        os = new DataOutputStream(zipos);
                        os.write(download);
                    }
                }
            }
            catch (Exception e) {
                log.info("\u5199\u9644\u4ef6\u6587\u4ef6\u51fa\u9519{}", e);
            }
            if (item.getTotalCount() > 10000) {
                zipos.closeEntry();
            }
            datablock.setDatas(datas);
            datablocks.add(datablock);
        }
        ejd.setDatablocks(datablocks);
        if (null != entitys) {
            ejd.setEntitys(entitys);
        }
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipos.putNextEntry(zipEntry);
        os = new DataOutputStream(zipos);
        os.write(objectMapper.writeValueAsBytes((Object)ejd));
        zipos.closeEntry();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getExtZipFile(TableContext tbContext, AsyncTaskMonitor monitor) {
        String path;
        block88: {
            if (null != monitor) {
                monitor.progressAndMessage(0.1, "");
            }
            path = ExtConstants.ROOTPATH + "/" + UUID.randomUUID().toString() + "/" + "ExportJsonDatas.zip";
            File file = null;
            try {
                file = FileUtil.createIfNotExists(path);
            }
            catch (IOException e) {
                log.info("\u521b\u5efa\u6587\u4ef6\u51fa\u9519{}", e);
            }
            try (FileOutputStream fos = new FileOutputStream(file);
                 ZipOutputStream zipos = new ZipOutputStream(fos);){
                List<IRegionDataSet> result;
                List forms;
                zipos.setMethod(8);
                if (null != monitor) {
                    monitor.progressAndMessage(0.1, "");
                }
                ArrayList<ImportInformations> successInfo = new ArrayList<ImportInformations>();
                ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
                boolean error = false;
                if (this.multistageUnitReplace != null) {
                    tbContext.setMultistageUnitReplace(this.multistageUnitReplace);
                }
                if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
                    forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
                    tbContext.setOptType(OptTypes.FORM);
                    for (FormDefine item : forms) {
                        tbContext.setFormKey(item.getKey());
                        result = this.exportService.getRegionsDatas(tbContext);
                        try {
                            this.generalZip(result, zipos, tbContext);
                        }
                        catch (IOException e) {
                            error = true;
                            log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                            ImportInformations err = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                            errorInfo.add(err);
                        }
                        finally {
                            if (!error) {
                                ImportInformations e = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), null, "");
                                successInfo.add(e);
                            }
                            error = false;
                        }
                        if (null == monitor) continue;
                        monitor.progressAndMessage(0.8 / (double)forms.size() / (double)(result.size() == 0 ? 1 : result.size()), "");
                    }
                } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
                    forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
                    tbContext.setOptType(OptTypes.FORM);
                    for (FormDefine item : forms) {
                        tbContext.setFormKey(item.getKey());
                        result = this.exportService.getRegionsDatas(tbContext);
                        try {
                            this.generalZip(result, zipos, tbContext);
                        }
                        catch (IOException e) {
                            error = true;
                            log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                            ImportInformations err = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                            errorInfo.add(err);
                        }
                        finally {
                            if (!error) {
                                ImportInformations e = new ImportInformations(item.getKey(), item.getFormCode(), item.getTitle(), null, "");
                                successInfo.add(e);
                            }
                            error = false;
                        }
                        if (null == monitor) continue;
                        monitor.progressAndMessage(0.1 + (double)(0.8 / (double)forms.size() / (double)result.size() == 0.0 ? 1 : result.size()), "");
                    }
                } else {
                    List<IRegionDataSet> result2 = this.exportService.getRegionsDatas(tbContext);
                    try {
                        this.generalZip(result2, zipos, tbContext);
                    }
                    catch (IOException e) {
                        error = true;
                        log.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
                        FormDefine formDefine = this.runTimeViewController.queryFormById(tbContext.getFormKey());
                        if (null != formDefine) {
                            ImportInformations err = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u5199\u6570\u636e\u51fa\u9519!", "");
                            errorInfo.add(err);
                        }
                    }
                    finally {
                        FormDefine formDefine;
                        if (!error && null != (formDefine = this.runTimeViewController.queryFormById(tbContext.getFormKey()))) {
                            ImportInformations e = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), null, "");
                            successInfo.add(e);
                        }
                        error = false;
                    }
                    if (null != monitor) {
                        monitor.progressAndMessage((double)(0.8 / (double)result2.size() == 0.0 ? 1 : result2.size()), "");
                    }
                }
                try {
                    Throwable throwable;
                    if (!successInfo.isEmpty()) {
                        ZipEntry zipEntry = new ZipEntry("successInfo.json");
                        zipos.putNextEntry(zipEntry);
                        throwable = null;
                        try (DataOutputStream os = new DataOutputStream(zipos);){
                            os.write(objectMapper.writeValueAsBytes(successInfo));
                        }
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                    }
                    if (errorInfo.isEmpty()) break block88;
                    ZipEntry errzipEntry = new ZipEntry("errorInfo.json");
                    zipos.putNextEntry(errzipEntry);
                    throwable = null;
                    try (DataOutputStream erros = new DataOutputStream(zipos);){
                        erros.write(objectMapper.writeValueAsBytes(errorInfo));
                    }
                    catch (Throwable throwable3) {
                        throwable = throwable3;
                        throw throwable3;
                    }
                }
                catch (IOException e1) {
                    log.info("\u5199\u5bfc\u51fa\u65e5\u5fd7\u51fa\u9519{}", (Object)e1.getMessage(), (Object)e1);
                }
            }
            catch (IOException e) {
                log.info("\u6587\u4ef6\u6d41\u5173\u95ed\u51fa\u9519{}", e);
            }
        }
        return path;
    }
}


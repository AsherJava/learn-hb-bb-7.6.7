/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.data.common.service.dto.FileEntry
 *  com.jiuqi.nr.data.common.service.dto.ResultDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.checkdes.nrdx;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ExpContext;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.impl.CsvFileDataReader;
import com.jiuqi.nr.data.checkdes.internal.impl.CsvFileDataWriter;
import com.jiuqi.nr.data.checkdes.internal.util.CKDIODataHandler;
import com.jiuqi.nr.data.checkdes.nrdx.CKDParamMappingTransfer;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.data.common.service.dto.FileEntry;
import com.jiuqi.nr.data.common.service.dto.ResultDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Order(value=30)
public class CKDTaskDataFactory
implements TaskDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(CKDTaskDataFactory.class);
    @Autowired
    private CommonUtil commonUtil;

    public String getCode() {
        return "FORMULA_CHECK";
    }

    public String getName() {
        return "\u5ba1\u6838\u51fa\u9519\u8bf4\u660e";
    }

    public String getDescription() {
        return "";
    }

    public Version getVersion() {
        return new Version("1.0.0");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportTaskData(TransferContext context, FileWriter writer) {
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        String taskName = "FORMULA_CHECK-EXPORT";
        CsvWriter csvWriter = null;
        try {
            progressMonitor.startTask(taskName, 3);
            ExpContext expContext = this.getExpContext(context);
            try {
                OutputStream outputStream = writer.createFile("DATA.csv");
                csvWriter = new CsvWriter(outputStream, '\t', StandardCharsets.UTF_8);
                progressMonitor.stepIn();
                CsvFileDataWriter csvFileDataWriter = new CsvFileDataWriter(csvWriter, expContext);
                CKDIODataHandler.exportData(expContext, csvFileDataWriter, progressMonitor);
                progressMonitor.stepIn();
            }
            finally {
                if (csvWriter != null) {
                    csvWriter.flush();
                    csvWriter.close();
                }
            }
            this.writeInfo(writer);
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setMessage("\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u5b8c\u6210");
            context.setResult(this.getCode(), (Result)resultDTO);
            progressMonitor.stepIn();
        }
        catch (Exception e) {
            logger.error("failed to export CKD data:{}", (Object)e.getMessage(), (Object)e);
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setMessage("\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u5f02\u5e38\uff1a" + e.getMessage());
            context.setResult(this.getCode(), (Result)resultDTO);
            throw new CKDIOException(resultDTO.getMessage());
        }
        finally {
            progressMonitor.finishTask(taskName);
        }
    }

    public void importTaskData(TransferContext context, FileFinder finder) {
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        String taskName = "FORMULA_CHECK-IMPORT";
        CsvReader csvReader = null;
        try {
            progressMonitor.startTask(taskName, 2);
            FileEntry impDataFile = this.getImpDataFile(finder);
            InputStream inputStream = finder.getFileInputStream(impDataFile.getFileName());
            csvReader = new CsvReader(inputStream, '\t', StandardCharsets.UTF_8);
            progressMonitor.stepIn();
            ImpContext impContext = this.getImpContext(context, impDataFile);
            CsvFileDataReader csvFileDataReader = new CsvFileDataReader(csvReader, impContext);
            Message<CKDImpMes> ckdImpMesMessage = CKDIODataHandler.importData(impContext, csvFileDataReader, progressMonitor);
            Result result = CKDTaskDataFactory.getImpResult(impContext, ckdImpMesMessage);
            context.setResult(this.getCode(), result);
            progressMonitor.stepIn();
        }
        catch (Exception e) {
            logger.error("failed to import CKD data:{}", (Object)e.getMessage(), (Object)e);
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setMessage("\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage());
            context.setResult(this.getCode(), (Result)resultDTO);
            throw new CKDIOException(resultDTO.getMessage(), e);
        }
        finally {
            if (csvReader != null) {
                csvReader.close();
            }
            progressMonitor.finishTask(taskName);
        }
    }

    @NonNull
    private static Result getImpResult(ImpContext impContext, Message<CKDImpMes> ckdImpMesMessage) {
        ResultDTO resultDTO = new ResultDTO();
        String dwDimName = impContext.getDimName(impContext.getFormSchemeDefine().getDw());
        ArrayList<String> failUnits = new ArrayList<String>();
        List collect = ((CKDImpMes)ckdImpMesMessage.getMessage()).getFailedInfos().stream().filter(o -> o.getImpFailType() == ImpFailType.CHECK_FAIL || o.getImpFailType() == ImpFailType.NO_ACCESS).collect(Collectors.toList());
        for (ImpFailedInfo impFailedInfo : collect) {
            String dw = String.valueOf(impFailedInfo.getCombination().getValue(dwDimName));
            if (failUnits.contains(dw)) continue;
            failUnits.add(dw);
        }
        resultDTO.setFailUnits(failUnits);
        String resultMes = "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u5b8c\u6210";
        if (!CollectionUtils.isEmpty(failUnits)) {
            resultMes = resultMes + "\uff0c\u5b58\u5728\u6570\u636e\u5bfc\u5165\u5931\u8d25\u5355\u4f4d" + failUnits.size() + "\u4e2a";
        }
        resultDTO.setMessage(resultMes);
        return resultDTO;
    }

    @NonNull
    private ImpContext getImpContext(TransferContext context, FileEntry impDataFile) {
        String formSchemeKey = context.getFormSchemeKey();
        DimensionCollection masterKeys = context.getMasterKeys();
        DimensionCollection impSettingMasterKeys = context.getImportSettings().getMasterKeys();
        if (impSettingMasterKeys != null) {
            masterKeys = impSettingMasterKeys;
        }
        ArrayList<String> formKeys = context.getFormKeys();
        CKDImpPar ckdImpPar = new CKDImpPar();
        ckdImpPar.setFilePath(impDataFile.getFilePath());
        ckdImpPar.setDimensionCollection(masterKeys);
        ckdImpPar.setFormSchemeKey(formSchemeKey);
        if (!CollectionUtils.isEmpty(formKeys)) {
            formKeys = new ArrayList<String>(formKeys);
            formKeys.add("00000000-0000-0000-0000-000000000000");
        }
        ckdImpPar.setFormKeys((List<String>)formKeys);
        if (context.getParamsMapping() != null) {
            CKDParamMappingTransfer ckdParamMappingTransfer = new CKDParamMappingTransfer(context.getParamsMapping());
            ckdImpPar.setCkdParamMapping(ckdParamMappingTransfer);
        }
        return new ImpContext(this.commonUtil, ckdImpPar, context.getImportSettings(), context.getProviderStore());
    }

    @NonNull
    private ExpContext getExpContext(TransferContext context) {
        String formSchemeKey = context.getFormSchemeKey();
        DimensionCollection masterKeys = context.getMasterKeys();
        ArrayList<String> formKeys = context.getFormKeys();
        CKDExpPar ckdExpPar = new CKDExpPar();
        ckdExpPar.setDimensionCollection(masterKeys);
        ckdExpPar.setFormSchemeKey(formSchemeKey);
        if (!CollectionUtils.isEmpty(formKeys)) {
            formKeys = new ArrayList<String>(formKeys);
            formKeys.add("00000000-0000-0000-0000-000000000000");
        }
        ckdExpPar.setFormKeys((List<String>)formKeys);
        if (context.getParamsMapping() != null) {
            CKDParamMappingTransfer ckdParamMappingTransfer = new CKDParamMappingTransfer(context.getParamsMapping());
            ckdExpPar.setCkdParamMapping(ckdParamMappingTransfer);
        }
        return new ExpContext(this.commonUtil, ckdExpPar, context.getProviderStore());
    }

    private void writeInfo(FileWriter fileWriter) throws IOException {
        LinkedHashMap<String, String> paramInfo = new LinkedHashMap<String, String>();
        paramInfo.put("type", "NRDX");
        paramInfo.put("version", (String)this.getVersion());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        byte[] bytes = mapper.writeValueAsBytes(paramInfo);
        fileWriter.addBytes("PARAM_INFO.json", bytes);
    }

    private FileEntry getImpDataFile(FileFinder finder) throws IOException {
        List fileEntries = finder.listFiles("/");
        if (CollectionUtils.isEmpty(fileEntries)) {
            throw new CKDIOException("\u6570\u636e\u5305\u4e2d\u65e0\u51fa\u9519\u8bf4\u660e\u6570\u636e\u6587\u4ef6");
        }
        Optional<FileEntry> any = fileEntries.stream().filter(o -> "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u6587\u4ef6.csv".equals(o.getFileName()) || "DATA.csv".equals(o.getFileName())).findAny();
        if (!any.isPresent()) {
            throw new CKDIOException("\u6570\u636e\u5305\u4e2d\u672a\u627e\u5230\u51fa\u9519\u8bf4\u660e\u6570\u636e\u6587\u4ef6");
        }
        return any.get();
    }
}


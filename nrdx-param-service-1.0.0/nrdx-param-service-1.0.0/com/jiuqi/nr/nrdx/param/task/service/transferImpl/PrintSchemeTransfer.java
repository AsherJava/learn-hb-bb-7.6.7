/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.formScheme.NrdxFormSchemeDTO;
import com.jiuqi.nr.nrdx.param.task.dto.print.NrdxPrintTemplateSchemeDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.service.transferImpl.FormulaSchemeTransfer;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintSchemeTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormulaSchemeTransfer.class);
    public static final List<String> PRINT_SCHEME_CSV_HEADER = Arrays.asList("key", "title", "formSchemeKey", "taskKey", "order", "version", "ownerLevelAndId", "updateTime", "description", "commonAttribute", "gatherCoverData", "desParamLanguageDTO");
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    UtilsService utilsService;

    public PrintSchemeTransfer() {
        logger.info("PrintSchemeTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        List<String> printSchemeKeys = param.getParamKeys();
        NrdxFormSchemeDTO nrdxFormSchemeDTO = new NrdxFormSchemeDTO();
        this.exportPrintSchemeSchemeCSV(printSchemeKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportPrintSchemeSchemeCSV(List<String> printSchemeKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "PRINT_SCHEME.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[PRINT_SCHEME_CSV_HEADER.size()];
            for (int i = 0; i < PRINT_SCHEME_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = PRINT_SCHEME_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String printSchemeKey : printSchemeKeys) {
                PrintTemplateSchemeDefine printTemplateSchemeDefine = this.printRunTimeController.queryPrintTemplateSchemeDefine(printSchemeKey);
                if (printTemplateSchemeDefine == null) continue;
                NrdxPrintTemplateSchemeDTO nrdxPrintTemplateSchemeDTO = NrdxPrintTemplateSchemeDTO.valueOf(printTemplateSchemeDefine);
                DesParamLanguageDTO desParamLanguage = this.utilsService.getDesParamLanguage(printSchemeKey, LanguageResourceType.PRINTSCHEMETITLE, "2");
                if (desParamLanguage != null) {
                    nrdxPrintTemplateSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                this.exportPrintSchemeDTO(csvWriter, nrdxPrintTemplateSchemeDTO);
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }

    private void exportPrintSchemeDTO(CsvWriter csvWriter, NrdxPrintTemplateSchemeDTO nrdxPrintTemplateSchemeDTO) throws Exception {
        String[] dataArray = new String[PRINT_SCHEME_CSV_HEADER.size()];
        block28: for (int i = 0; i < PRINT_SCHEME_CSV_HEADER.size(); ++i) {
            switch (PRINT_SCHEME_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getKey();
                    continue block28;
                }
                case "title": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getTitle();
                    continue block28;
                }
                case "formSchemeKey": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getFormSchemeKey();
                    continue block28;
                }
                case "taskKey": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getTaskKey();
                    continue block28;
                }
                case "order": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getOrder();
                    continue block28;
                }
                case "version": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getVersion();
                    continue block28;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getOwnerLevelAndId();
                    continue block28;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxPrintTemplateSchemeDTO.getUpdateTime());
                    continue block28;
                }
                case "description": {
                    dataArray[i] = nrdxPrintTemplateSchemeDTO.getDescription();
                    continue block28;
                }
                case "commonAttribute": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateSchemeDTO.getCommonAttribute());
                    continue block28;
                }
                case "gatherCoverData": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateSchemeDTO.getGatherCoverData());
                    continue block28;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateSchemeDTO.getDesParamLanguageDTO());
                    continue block28;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    public void importModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public void preAnalysis(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
    }

    public DepResource depResource(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        return null;
    }

    @Override
    public List<ITransferModel> depModel(String s) {
        return null;
    }

    @Override
    public String code() {
        return NrdxParamNodeType.PRINTSCHEME.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}


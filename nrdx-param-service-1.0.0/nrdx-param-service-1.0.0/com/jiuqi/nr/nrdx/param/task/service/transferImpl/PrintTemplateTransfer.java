/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IRunTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimePrintComTemDefineService
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.internal.service.RunTimePrintComTemDefineService;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.dto.PrintParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.print.NrdxPrintComTemDTO;
import com.jiuqi.nr.nrdx.param.task.dto.print.NrdxPrintTemplateDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.print.PrintSettingDTO;
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
public class PrintTemplateTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(PrintTemplateTransfer.class);
    public static final List<String> PRINT_TEMPLATE_CSV_HEADER = Arrays.asList("key", "title", "printSchemeKey", "formKey", "formUpdateTime", "order", "version", "ownerLevelAndId", "updateTime", "description", "templateData", "labelData", "comTemCode", "printSettingDTO");
    public static final List<String> PRINT_COMTEM_CSV_HEADER = Arrays.asList("key", "code", "title", "order", "printSchemeKey", "description", "updateTime", "version", "", "ownerLevelAndId", "templateData");
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IRunTimePrintController runTimePrintController;
    @Autowired
    private RunTimePrintComTemDefineService printComTemDefineService;
    @Autowired
    UtilsService utilsService;
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;

    public PrintTemplateTransfer() {
        logger.info("FormulaTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        PrintParamDTO printParamDTO = (PrintParamDTO)param;
        String printScheme = printParamDTO.getPrintScheme();
        List<String> formKeys = printParamDTO.getParamKeys();
        this.exportPrintTemplateCSV(printScheme, formKeys, path);
        this.exportPrintComTemCSV(printScheme, formKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportPrintTemplateCSV(String printScheme, List<String> formKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "PRINT_TEMPLATE.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[PRINT_TEMPLATE_CSV_HEADER.size()];
            for (int i = 0; i < PRINT_TEMPLATE_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = PRINT_TEMPLATE_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formKey : formKeys) {
                PrintTemplateDefine templateDefine = this.runTimePrintController.getPrintTemplateBySchemeAndForm(printScheme, formKey);
                NrdxPrintTemplateDTO nrdxPrintTemplateDTO = NrdxPrintTemplateDTO.valueOf(templateDefine);
                PrintSettingDefine setting = this.runTimePrintController.getPrintSettingDefine(printScheme, formKey);
                nrdxPrintTemplateDTO.setPrintSettingDTO(PrintSettingDTO.valueOf((PrintSettingDefine)setting));
                this.exportPrintTemplateDTO(csvWriter, nrdxPrintTemplateDTO);
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

    private void exportPrintTemplateDTO(CsvWriter csvWriter, NrdxPrintTemplateDTO nrdxPrintTemplateDTO) throws Exception {
        String[] dataArray = new String[PRINT_TEMPLATE_CSV_HEADER.size()];
        block32: for (int i = 0; i < PRINT_TEMPLATE_CSV_HEADER.size(); ++i) {
            switch (PRINT_TEMPLATE_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxPrintTemplateDTO.getKey();
                    continue block32;
                }
                case "title": {
                    dataArray[i] = nrdxPrintTemplateDTO.getTitle();
                    continue block32;
                }
                case "printSchemeKey": {
                    dataArray[i] = nrdxPrintTemplateDTO.getPrintSchemeKey();
                    continue block32;
                }
                case "formKey": {
                    dataArray[i] = nrdxPrintTemplateDTO.getFormKey();
                    continue block32;
                }
                case "formUpdateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxPrintTemplateDTO.getFormUpdateTime());
                    continue block32;
                }
                case "order": {
                    dataArray[i] = nrdxPrintTemplateDTO.getOrder();
                    continue block32;
                }
                case "version": {
                    dataArray[i] = nrdxPrintTemplateDTO.getVersion();
                    continue block32;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxPrintTemplateDTO.getOwnerLevelAndId();
                    continue block32;
                }
                case "updateTime": {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf1.format(nrdxPrintTemplateDTO.getUpdateTime());
                    continue block32;
                }
                case "description": {
                    dataArray[i] = nrdxPrintTemplateDTO.getDescription();
                    continue block32;
                }
                case "templateData": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateDTO.getTemplateData());
                    continue block32;
                }
                case "labelData": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateDTO.getLabelData());
                    continue block32;
                }
                case "comTemCode": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateDTO.getComTemCode());
                    continue block32;
                }
                case "printSettingDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintTemplateDTO.getPrintSettingDTO());
                    continue block32;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportPrintComTemCSV(String printScheme, List<String> formKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "PRINT_COMTEM.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[PRINT_COMTEM_CSV_HEADER.size()];
            for (int i = 0; i < PRINT_COMTEM_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = PRINT_COMTEM_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            List printComTemDefines = this.printComTemDefineService.listByScheme(printScheme);
            for (PrintComTemDefine printComTemDefine : printComTemDefines) {
                NrdxPrintComTemDTO nrdxPrintComTemDTO = NrdxPrintComTemDTO.valueOf(printComTemDefine);
                this.exportPrintComTemDTO(csvWriter, nrdxPrintComTemDTO);
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

    private void exportPrintComTemDTO(CsvWriter csvWriter, NrdxPrintComTemDTO nrdxPrintComTemDTO) throws Exception {
        String[] dataArray = new String[PRINT_COMTEM_CSV_HEADER.size()];
        block24: for (int i = 0; i < PRINT_COMTEM_CSV_HEADER.size(); ++i) {
            switch (PRINT_COMTEM_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxPrintComTemDTO.getKey();
                    continue block24;
                }
                case "code": {
                    dataArray[i] = nrdxPrintComTemDTO.getCode();
                    continue block24;
                }
                case "title": {
                    dataArray[i] = nrdxPrintComTemDTO.getTitle();
                    continue block24;
                }
                case "order": {
                    dataArray[i] = nrdxPrintComTemDTO.getOrder();
                    continue block24;
                }
                case "printSchemeKey": {
                    dataArray[i] = nrdxPrintComTemDTO.getPrintSchemeKey();
                    continue block24;
                }
                case "description": {
                    dataArray[i] = nrdxPrintComTemDTO.getDescription();
                    continue block24;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxPrintComTemDTO.getUpdateTime());
                    continue block24;
                }
                case "version": {
                    dataArray[i] = nrdxPrintComTemDTO.getVersion();
                    continue block24;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxPrintComTemDTO.getOwnerLevelAndId();
                    continue block24;
                }
                case "templateData": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxPrintComTemDTO.getTemplateData());
                    continue block24;
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
        return NrdxParamNodeType.PRINTTEMPLATE.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.formula.NrdxFormulaSchemeDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormulaSchemeTransfer.class);
    public static final List<String> FORMULA_SCHEME_CSV_HEADER = Arrays.asList("key", "title", "formSchemeKey", "description", "displayMode", "order", "version", "ownerLevelAndId", "updateTime", "formulaSchemeType", "defaultScheme", "showScheme", "formulaConditions", "desParamLanguageDTO");
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    UtilsService utilsService;

    public FormulaSchemeTransfer() {
        logger.info("FormulaSchemeTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        List<String> formulaSchemeKeys = param.getParamKeys();
        this.exportFormulaSchemeSchemeCSV(formulaSchemeKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormulaSchemeSchemeCSV(List<String> formulaSchemeKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + "FORMULA_SCHEME.CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORMULA_SCHEME_CSV_HEADER.size()];
            for (int i = 0; i < FORMULA_SCHEME_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORMULA_SCHEME_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formulaSchemeKey : formulaSchemeKeys) {
                FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
                if (formulaSchemeDefine == null) continue;
                NrdxFormulaSchemeDTO nrdxFormulaSchemeDTO = NrdxFormulaSchemeDTO.valueOf(formulaSchemeDefine);
                List formulaConditions = this.formulaDesignTimeController.listFormulaConditionByScheme(formulaSchemeKey);
                nrdxFormulaSchemeDTO.setFormulaConditions(formulaConditions.stream().map(FormulaConditionDTO::toDto).collect(Collectors.toList()));
                DesParamLanguageDTO desParamLanguage = this.utilsService.getDesParamLanguage(formulaSchemeKey, LanguageResourceType.FORMULASCHEMETITLE, "2");
                if (desParamLanguage != null) {
                    nrdxFormulaSchemeDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                this.exportFormulaSchemeDTO(csvWriter, nrdxFormulaSchemeDTO);
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

    private void exportFormulaSchemeDTO(CsvWriter csvWriter, NrdxFormulaSchemeDTO nrdxFormulaSchemeDTO) throws Exception {
        String[] dataArray = new String[FORMULA_SCHEME_CSV_HEADER.size()];
        block32: for (int i = 0; i < FORMULA_SCHEME_CSV_HEADER.size(); ++i) {
            switch (FORMULA_SCHEME_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getKey();
                    continue block32;
                }
                case "title": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getTitle();
                    continue block32;
                }
                case "formSchemeKey": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getFormSchemeKey();
                    continue block32;
                }
                case "description": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getDescription();
                    continue block32;
                }
                case "displayMode": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getDisplayMode().name();
                    continue block32;
                }
                case "order": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getOrder();
                    continue block32;
                }
                case "version": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getVersion();
                    continue block32;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getOwnerLevelAndId();
                    continue block32;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormulaSchemeDTO.getUpdateTime());
                    continue block32;
                }
                case "formulaSchemeType": {
                    dataArray[i] = nrdxFormulaSchemeDTO.getFormulaSchemeType().name();
                    continue block32;
                }
                case "defaultScheme": {
                    dataArray[i] = String.valueOf(nrdxFormulaSchemeDTO.isDefaultScheme());
                    continue block32;
                }
                case "showScheme": {
                    dataArray[i] = String.valueOf(nrdxFormulaSchemeDTO.isShowScheme());
                    continue block32;
                }
                case "formulaConditions": {
                    dataArray[i] = this.objectMapper.writeValueAsString(nrdxFormulaSchemeDTO.getFormulaConditions());
                    continue block32;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormulaSchemeDTO.getDesParamLanguageDTO());
                    continue block32;
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
        return NrdxParamNodeType.FORMULASCHEME.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}


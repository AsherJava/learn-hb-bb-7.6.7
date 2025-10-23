/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.FormulaParamDTO;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.formScheme.NrdxFormSchemeDTO;
import com.jiuqi.nr.nrdx.param.task.dto.formula.NrdxFormulaFormDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.service.transferImpl.FormulaSchemeTransfer;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormulaTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormulaSchemeTransfer.class);
    public static final List<String> FORMULA_FORM_CSV_HEADER = Arrays.asList("key", "code", "title", "formulaSchemeKey", "formKey", "expression", "dataItems", "adjustItems", "description", "autoExecute", "useCalculate", "useCheck", "useBalance", "checkType", "order", "version", "ownerLevelAndId", "updateTime", "balanceZBExp", "languageInfo", "formulaConditionLinkDTO");
    private final ObjectMapper objectMapper;
    private static final String PARAM_LANGUAGE_ENGLISH = "2";
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
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;

    public FormulaTransfer() {
        logger.info("FormulaTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String formSchemePath = context.getPath();
        Costs.createPathIfNotExists((Path)new File(formSchemePath).toPath());
        FormulaParamDTO formulaParamDTO = (FormulaParamDTO)param;
        String formulaScheme = formulaParamDTO.getFormulaScheme();
        List<String> formKeys = formulaParamDTO.getParamKeys();
        NrdxFormSchemeDTO nrdxFormSchemeDTO = new NrdxFormSchemeDTO();
        List formulaConditionLinks = this.formulaRunTimeController.getFormulaConditionLinks(formulaScheme);
        Map<String, FormulaConditionLink> formulaConditionLinkMap = formulaConditionLinks.stream().collect(Collectors.toMap(FormulaConditionLink::getFormulaKey, a -> a, (k1, k2) -> k1));
        for (String formKey : formKeys) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (formDefine == null) continue;
            String formulaSchemePath = formSchemePath + Costs.FILE_SEPARATOR + formDefine.getFormCode();
            this.exportFormulaFormCSV(formulaScheme, formDefine, formulaSchemePath, formulaConditionLinkMap);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormulaFormCSV(String formulaScheme, FormDefine formDefine, String path, Map<String, FormulaConditionLink> formulaConditionLinkMap) {
        List allFormulasInForm = this.formulaRunTimeController.getAllFormulasInForm(formulaScheme, formDefine.getKey());
        if (CollectionUtils.isEmpty(allFormulasInForm)) {
            return;
        }
        CsvWriter csvWriter = null;
        File file = new File(path + formDefine.getFormCode() + ".CSV");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORMULA_FORM_CSV_HEADER.size()];
            for (int i = 0; i < FORMULA_FORM_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORMULA_FORM_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            List formulaKeys = allFormulasInForm.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            List desParamLanguages = this.desParamLanguageDao.queryLanguageByResKeys(formulaKeys);
            Map<String, DesParamLanguage> formulaLanguageMaps = desParamLanguages.stream().filter(a -> PARAM_LANGUAGE_ENGLISH.equals(a.getLanguageType())).collect(Collectors.toMap(DesParamLanguage::getResourceKey, a -> a, (k1, k2) -> k1));
            for (FormulaDefine formula : allFormulasInForm) {
                FormulaConditionLink formulaConditionLink;
                NrdxFormulaFormDTO nrdxFormulaFormDTO = NrdxFormulaFormDTO.valueOf(formula);
                DesParamLanguage desParamLanguage = formulaLanguageMaps.get(formula.getKey());
                if (desParamLanguage != null) {
                    nrdxFormulaFormDTO.setLanguageInfo(desParamLanguage.getLanguageInfo());
                }
                if ((formulaConditionLink = formulaConditionLinkMap.get(formula.getKey())) != null) {
                    FormulaConditionLinkDTO formulaConditionLinkDTO = new FormulaConditionLinkDTO();
                    formulaConditionLinkDTO.setConditionKey(formulaConditionLink.getConditionKey());
                    formulaConditionLinkDTO.setFormulaKey(formulaConditionLink.getFormulaKey());
                    formulaConditionLinkDTO.setFormulaSchemeKey(formulaConditionLink.getFormulaSchemeKey());
                    nrdxFormulaFormDTO.setFormulaConditionLinkDTO(formulaConditionLinkDTO);
                }
                this.exportFormulaDTO(csvWriter, nrdxFormulaFormDTO);
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

    private void exportFormulaDTO(CsvWriter csvWriter, NrdxFormulaFormDTO nrdxFormulaFormDTO) throws Exception {
        String[] dataArray = new String[FORMULA_FORM_CSV_HEADER.size()];
        block46: for (int i = 0; i < FORMULA_FORM_CSV_HEADER.size(); ++i) {
            switch (FORMULA_FORM_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormulaFormDTO.getKey();
                    continue block46;
                }
                case "code": {
                    dataArray[i] = nrdxFormulaFormDTO.getCode();
                    continue block46;
                }
                case "title": {
                    dataArray[i] = nrdxFormulaFormDTO.getTitle();
                    continue block46;
                }
                case "formulaSchemeKey": {
                    dataArray[i] = nrdxFormulaFormDTO.getFormulaSchemeKey();
                    continue block46;
                }
                case "formKey": {
                    dataArray[i] = nrdxFormulaFormDTO.getFormKey();
                    continue block46;
                }
                case "expression": {
                    dataArray[i] = nrdxFormulaFormDTO.getExpression();
                    continue block46;
                }
                case "dataItems": {
                    dataArray[i] = nrdxFormulaFormDTO.getDataItems();
                    continue block46;
                }
                case "adjustItems": {
                    dataArray[i] = nrdxFormulaFormDTO.getAdjustItems();
                    continue block46;
                }
                case "description": {
                    dataArray[i] = nrdxFormulaFormDTO.getDescription();
                    continue block46;
                }
                case "autoExecute": {
                    dataArray[i] = String.valueOf(nrdxFormulaFormDTO.isAutoExecute());
                    continue block46;
                }
                case "useCalculate": {
                    dataArray[i] = String.valueOf(nrdxFormulaFormDTO.isUseCalculate());
                    continue block46;
                }
                case "useCheck": {
                    dataArray[i] = String.valueOf(nrdxFormulaFormDTO.isUseCheck());
                    continue block46;
                }
                case "useBalance": {
                    dataArray[i] = String.valueOf(nrdxFormulaFormDTO.isUseBalance());
                    continue block46;
                }
                case "checkType": {
                    dataArray[i] = String.valueOf(nrdxFormulaFormDTO.getCheckType());
                    continue block46;
                }
                case "order": {
                    dataArray[i] = nrdxFormulaFormDTO.getOrder();
                    continue block46;
                }
                case "version": {
                    dataArray[i] = nrdxFormulaFormDTO.getVersion();
                    continue block46;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormulaFormDTO.getOwnerLevelAndId();
                    continue block46;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormulaFormDTO.getUpdateTime());
                    continue block46;
                }
                case "balanceZBExp": {
                    dataArray[i] = nrdxFormulaFormDTO.getBalanceZBExp();
                    continue block46;
                }
                case "languageInfo": {
                    dataArray[i] = nrdxFormulaFormDTO.getLanguageInfo();
                    continue block46;
                }
                case "formulaConditionLinkDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormulaFormDTO.getFormulaConditionLinkDTO());
                    continue block46;
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
        return NrdxParamNodeType.FORMULAFORM.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}


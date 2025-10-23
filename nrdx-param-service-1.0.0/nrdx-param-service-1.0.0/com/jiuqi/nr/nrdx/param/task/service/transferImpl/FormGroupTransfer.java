/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.tds.Costs
 *  com.jiuqi.xlib.utils.io.FilenameUtils
 */
package com.jiuqi.nr.nrdx.param.task.service.transferImpl;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.dto.form.NrdxFormGroupDTO;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import com.jiuqi.nr.nrdx.param.task.utils.UtilsService;
import com.jiuqi.nr.param.transfer.definition.DefinitionModelTransfer;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.xlib.utils.io.FilenameUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGroupTransfer
extends AbstractParamTransfer {
    private static final Logger logger = LoggerFactory.getLogger(FormGroupTransfer.class);
    public static final List<String> FORM_GROUP_CSV_HEADER = Arrays.asList("key", "title", "formSchemeKey", "parentKey", "order", "version", "ownerLevelAndId", "updateTime", "code", "condition", "measureUnit", "desParamLanguageDTO");
    public static final String FORM_GROUP_CSV = "FORM_GROUP.CSV";
    private final ObjectMapper objectMapper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    UtilsService utilsService;

    public FormGroupTransfer() {
        logger.info("FormGroupTransfer \u521d\u59cb\u5316objectMapper");
        this.objectMapper = new ObjectMapper();
        DefinitionModelTransfer.moduleRegister((ObjectMapper)this.objectMapper);
    }

    public void exportModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        String path = context.getPath();
        Costs.createPathIfNotExists((Path)new File(path).toPath());
        List<String> formGroupKeys = param.getParamKeys();
        this.exportFormGroupCSV(formGroupKeys, path);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exportFormGroupCSV(List<String> formGroupKeys, String path) {
        CsvWriter csvWriter = null;
        File file = new File(path + Costs.FILE_SEPARATOR + FORM_GROUP_CSV);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            csvWriter = new CsvWriter((OutputStream)fileOutputStream, '\t', StandardCharsets.UTF_8);
            String[] fieldDefineArray = new String[FORM_GROUP_CSV_HEADER.size()];
            for (int i = 0; i < FORM_GROUP_CSV_HEADER.size(); ++i) {
                fieldDefineArray[i] = FORM_GROUP_CSV_HEADER.get(i);
            }
            csvWriter.writeRecord(fieldDefineArray);
            for (String formGroupKey : formGroupKeys) {
                FormGroupDefine formGroupDefine = this.runTimeViewController.queryFormGroup(formGroupKey);
                if (formGroupDefine == null) continue;
                NrdxFormGroupDTO nrdxFormGroupDTO = NrdxFormGroupDTO.valueOf(formGroupDefine);
                DesParamLanguageDTO desParamLanguage = this.utilsService.getDesParamLanguage(formGroupKey, LanguageResourceType.FORMGROUPTITLE, "2");
                if (desParamLanguage != null) {
                    nrdxFormGroupDTO.setDesParamLanguageDTO(desParamLanguage);
                }
                this.exportFormGroupDTO(csvWriter, nrdxFormGroupDTO);
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

    private void exportFormGroupDTO(CsvWriter csvWriter, NrdxFormGroupDTO nrdxFormGroupDTO) throws Exception {
        String[] dataArray = new String[FORM_GROUP_CSV_HEADER.size()];
        block28: for (int i = 0; i < FORM_GROUP_CSV_HEADER.size(); ++i) {
            switch (FORM_GROUP_CSV_HEADER.get(i)) {
                case "key": {
                    dataArray[i] = nrdxFormGroupDTO.getKey();
                    continue block28;
                }
                case "title": {
                    dataArray[i] = nrdxFormGroupDTO.getTitle();
                    continue block28;
                }
                case "formSchemeKey": {
                    dataArray[i] = nrdxFormGroupDTO.getFormSchemeKey();
                    continue block28;
                }
                case "parentKey": {
                    dataArray[i] = nrdxFormGroupDTO.getParentKey();
                    continue block28;
                }
                case "order": {
                    dataArray[i] = nrdxFormGroupDTO.getOrder();
                    continue block28;
                }
                case "version": {
                    dataArray[i] = nrdxFormGroupDTO.getVersion();
                    continue block28;
                }
                case "ownerLevelAndId": {
                    dataArray[i] = nrdxFormGroupDTO.getOwnerLevelAndId();
                    continue block28;
                }
                case "updateTime": {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                    dataArray[i] = sdf.format(nrdxFormGroupDTO.getUpdateTime());
                    continue block28;
                }
                case "code": {
                    dataArray[i] = nrdxFormGroupDTO.getCode();
                    continue block28;
                }
                case "condition": {
                    dataArray[i] = nrdxFormGroupDTO.getCondition();
                    continue block28;
                }
                case "measureUnit": {
                    dataArray[i] = nrdxFormGroupDTO.getMeasureUnit();
                    continue block28;
                }
                case "desParamLanguageDTO": {
                    dataArray[i] = this.objectMapper.writeValueAsString((Object)nrdxFormGroupDTO.getDesParamLanguageDTO());
                    continue block28;
                }
            }
        }
        csvWriter.writeRecord(dataArray);
    }

    public void importModel(ParamDTO param, NrdxTransferContext context, AsyncTaskMonitor monitor) {
        List<String> formGroupKeys = param.getParamKeys();
        String path = context.getPath();
        this.importFormGroupCSV(formGroupKeys, path);
    }

    private void importFormGroupCSV(List<String> formGroupKeys, String path) {
        String normalizedFilePath = FilenameUtils.normalize((String)(path + Costs.FILE_SEPARATOR + FORM_GROUP_CSV));
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        ArrayList<NrdxFormGroupDTO> nrdxFormGroupDTOs = new ArrayList<NrdxFormGroupDTO>();
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                NrdxFormGroupDTO nrdxFormGroupDTO = new NrdxFormGroupDTO();
                for (String header : headers) {
                    String colValue = "";
                    try {
                        colValue = csvReader.get(header);
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                    switch (header) {
                        case "key": {
                            nrdxFormGroupDTO.setKey(colValue);
                            break;
                        }
                        case "title": {
                            nrdxFormGroupDTO.setTitle(colValue);
                            break;
                        }
                        case "formSchemeKey": {
                            nrdxFormGroupDTO.setFormSchemeKey(colValue);
                            break;
                        }
                        case "parentKey": {
                            nrdxFormGroupDTO.setParentKey(colValue);
                            break;
                        }
                        case "order": {
                            nrdxFormGroupDTO.setOrder(colValue);
                            break;
                        }
                        case "version": {
                            nrdxFormGroupDTO.setVersion(colValue);
                            break;
                        }
                        case "ownerLevelAndId": {
                            nrdxFormGroupDTO.setOwnerLevelAndId(colValue);
                            break;
                        }
                        case "updateTime": {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss zzz");
                            nrdxFormGroupDTO.setUpdateTime(sdf.parse(colValue));
                            break;
                        }
                        case "code": {
                            nrdxFormGroupDTO.setCode(colValue);
                            break;
                        }
                        case "condition": {
                            nrdxFormGroupDTO.setCondition(colValue);
                            break;
                        }
                        case "measureUnit": {
                            nrdxFormGroupDTO.setMeasureUnit(colValue);
                            break;
                        }
                        case "desParamLanguageDTO": {
                            nrdxFormGroupDTO.setDesParamLanguageDTO((DesParamLanguageDTO)this.objectMapper.readValue(colValue, DesParamLanguageDTO.class));
                            break;
                        }
                    }
                    if (!formGroupKeys.contains(nrdxFormGroupDTO.getKey())) break;
                }
                nrdxFormGroupDTOs.add(nrdxFormGroupDTO);
            }
        }
        this.importFormGroupDTO(nrdxFormGroupDTOs);
    }

    private void importFormGroupDTO(List<NrdxFormGroupDTO> nrdxFormSchemeDTOs) {
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
        return NrdxParamNodeType.FORMGROUP.getCode();
    }

    @Override
    public String version() {
        return null;
    }
}


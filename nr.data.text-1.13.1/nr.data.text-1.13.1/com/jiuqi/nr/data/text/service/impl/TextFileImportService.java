/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.bi.util.StringTokenEnumeration
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.exception.DataTransferException
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.param.CommonImportDetails
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.CSVRange
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ImportErrorData
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.io.params.output.ImportInformations
 *  com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet
 *  com.jiuqi.nr.io.service.IoQualifier
 *  com.jiuqi.nr.io.util.ExtUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.text.service.impl;

import com.csvreader.CsvReader;
import com.jiuqi.bi.util.StringTokenEnumeration;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.exception.DataTransferException;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.param.CommonImportDetails;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.CSVRange;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ImportErrorData;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.util.ExtUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextFileImportService {
    private static final Logger log = LoggerFactory.getLogger(TextFileImportService.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired(required=false)
    private IoQualifier ioQualifier;

    public void textFileData(TextType textType, File file, TableContext tableContext, CommonParams commonParams, CommonMessage message, DataImportLogger dataImportLogger) throws Exception {
        tableContext.setIoQualifier(this.ioQualifier);
        if (textType.equals((Object)TextType.TEXTTYPE_CSV)) {
            this.csvFileData(file, tableContext, commonParams, message, dataImportLogger);
        } else {
            this.txtFileData(file, tableContext, commonParams, message, dataImportLogger);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void csvFileData(File file, TableContext tableContext, CommonParams commonParams, CommonMessage message, DataImportLogger dataImportLogger) throws Exception {
        List<RegionData> formRegions;
        int regionTop;
        Map results = (Map)message.getDetail();
        results.computeIfAbsent("error_data", k -> new ArrayList());
        results.computeIfAbsent("skip_data", k -> new ArrayList());
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            ImportInformations importInformations = new ImportInformations(null, null, null, String.format("%s \u4e0d\u662fCSV\u7c7b\u578b\u6587\u4ef6\uff0c\u4e0d\u53ef\u5bfc\u5165\uff01", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        String[] tokens = ExtUtil.trimEnd((String)file.getName(), (String)".csv").split("_F");
        String formCode = null;
        if (tokens.length == 2) {
            formCode = tokens[0];
            regionTop = Integer.parseInt(tokens[1]);
        } else {
            formCode = ExtUtil.trimEnd((String)file.getName(), (String)".csv");
            regionTop = 1;
        }
        tableContext.setPwd(file.getAbsolutePath().replace(file.getName(), ""));
        ParamsMapping mapping = null;
        formCode = this.mapping(commonParams, formCode, mapping);
        FormDefine formDefine = this.getFormDefine(tableContext, formCode);
        if (null == formDefine) {
            ImportInformations importInformations = new ImportInformations(null, formCode, null, String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        CSVRange csvRange = tableContext.getCsvRange();
        if (csvRange != null && !csvRange.contains(formDefine.getFormCode()) && !csvRange.contains(formDefine.getKey())) {
            ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), String.format("\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u4e0d\u53ef\u5bfc\u5165\u3002", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
        }
        if (null == (formRegions = this.getFormRegions(tableContext, formCode))) {
            ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
            return;
        }
        RegionData region = null;
        for (RegionData regionData : formRegions) {
            if (regionData.getRegionTop() != regionTop) continue;
            region = regionData;
            break;
        }
        if (null == region) {
            ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u672a\u5339\u914d\u8be5\u533a\u57df\u6570\u636e\uff0c\u8bb0\u5f55\u9519\u8bef\u4fe1\u606f", "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        ArrayList<ExportFieldDefine> fields1 = new ArrayList<ExportFieldDefine>();
        CsvReader reader = null;
        int lineNum = 0;
        String unitCode = "";
        if (message.getSuccessDW() == null) {
            message.setSuccessDW(new ArrayList());
        }
        int adjustIndex = -1;
        RegionDataSet unitCodeUtil = null;
        HashSet<String> unitCodes = new HashSet<String>();
        HashSet<String> tableCodes = new HashSet<String>();
        try {
            reader = new CsvReader(file.getAbsolutePath(), tableContext.getSplit().toCharArray()[0], StandardCharsets.UTF_8);
            if (reader.readHeaders()) {
                for (int i = 0; i < reader.getHeaderCount(); ++i) {
                    ExportFieldDefine e2 = new ExportFieldDefine();
                    e2.setCode(reader.getHeader(i));
                    ExportFieldDefine e1 = new ExportFieldDefine();
                    e1.setCode(reader.getHeader(i));
                    fields.add(e2);
                    if (e2.getCode().contains("ADJUST")) {
                        adjustIndex = i;
                    }
                    fields1.add(e1);
                }
            }
            boolean noPage = false;
            RegionDataSet regionDataSet = null;
            if (formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && region.getRegionTop() > 1) {
                regionDataSet = new SBRegionDataSet(tableContext, region, fields1);
                noPage = true;
            } else {
                regionDataSet = new RegionDataSet(tableContext, region, fields1);
            }
            unitCodeUtil = regionDataSet;
            List importFields = regionDataSet.getFieldDataList();
            importFields.forEach(e -> tableCodes.add(e.getTableCode()));
            try {
                while (reader.readRecord()) {
                    DimensionValueSet dimensionValueSet;
                    List dws;
                    if (tableContext.getDataLineIndex() != null && reader.getCurrentRecord() + 2L < (long)tableContext.getDataLineIndex().intValue()) continue;
                    ArrayList<Object> listData = new ArrayList<Object>();
                    for (ExportFieldDefine field : fields) {
                        if (mapping != null && field.getCode().contains("MDCODE")) {
                            ArrayList<String> dwCodes = new ArrayList<String>();
                            dwCodes.add(reader.get(field.getCode()));
                            Map originOrgCode = mapping.getOriginOrgCode(dwCodes);
                            listData.add(originOrgCode.get(reader.get(field.getCode())));
                            continue;
                        }
                        listData.add(reader.get(field.getCode()));
                    }
                    if (tableContext.isReturnBizKeyValue()) {
                        unitCode = listData.get(0).toString();
                        if (csvRange != null && (adjustIndex == -1 ? !csvRange.searchDwInForm(formDefine.getFormCode(), formDefine.getKey()).contains(unitCode) : (dws = csvRange.searchAdjustInForm(formDefine.getFormCode(), formDefine.getKey(), listData.get(adjustIndex).toString())) != null && !dws.isEmpty() && !dws.contains(unitCode))) continue;
                        dimensionValueSet = regionDataSet.importDatas(listData);
                        if (dimensionValueSet != null) {
                            unitCodes.add(unitCode);
                        }
                    } else {
                        unitCode = listData.get(0).toString();
                        if (csvRange != null && (adjustIndex == -1 ? !csvRange.searchDwInForm(formDefine.getFormCode(), formDefine.getKey()).contains(unitCode) : (dws = csvRange.searchAdjustInForm(formDefine.getFormCode(), formDefine.getKey(), listData.get(adjustIndex).toString())) != null && !dws.isEmpty() && !dws.contains(unitCode))) continue;
                        dimensionValueSet = regionDataSet.importDatas(listData);
                        if (dimensionValueSet != null) {
                            unitCodes.add(unitCode);
                        }
                    }
                    if (!message.getSuccessDW().contains(unitCode)) {
                        message.getSuccessDW().add(unitCode);
                    }
                    if (noPage || ++lineNum % 20000 != 0) continue;
                    regionDataSet.commit();
                }
                regionDataSet.commit();
            }
            catch (Exception e3) {
                if (!(e3 instanceof DataTransferException)) {
                    ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), e3.getMessage(), unitCode);
                    ((List)results.get("error_data")).add(importInformations);
                }
                throw e3;
            }
        }
        catch (Exception e4) {
            String msg = "\u8bfbcsv\u6587\u4ef6\u51fa\u9519\uff1a" + e4.getMessage();
            dataImportLogger.importError(msg);
            log.info(msg, e4);
        }
        finally {
            this.addTableToUnit(unitCodes, tableCodes, dataImportLogger);
            if (reader != null) {
                reader.close();
            }
        }
        if (null != unitCodeUtil) {
            List amendInfos;
            List errorInfos = unitCodeUtil.getImportErrorInfos();
            if (!errorInfos.isEmpty()) {
                for (ImportErrorData errorInfo : errorInfos) {
                    String orgCode;
                    String title;
                    ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), errorInfo.getErrorMessage(), errorInfo.getOrgCode());
                    Point dataSetPoint = errorInfo.getPoint();
                    if (Objects.nonNull(dataSetPoint)) {
                        String yString = dataSetPoint.y == 0 ? "" : "[" + dataSetPoint.y + "]\u5217";
                        importInformations.setMessage(errorInfo.getErrorMessage() + "\uff08\u7b2c[" + (dataSetPoint.x + 1) + "]\u884c" + yString + "\uff09");
                    }
                    orgCode = (title = unitCodeUtil.getCodeTitle(orgCode = errorInfo.getOrgCode())) == null ? orgCode : title;
                    importInformations.setUnitCode(orgCode);
                    ((List)results.get("error_data")).add(importInformations);
                }
            }
            if (!(amendInfos = unitCodeUtil.getImportAmendInfos()).isEmpty()) {
                for (ImportErrorData amendInfo : amendInfos) {
                    String orgCode;
                    String title;
                    ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), amendInfo.getErrorMessage(), amendInfo.getOrgCode());
                    Point dataSetPoint = amendInfo.getPoint();
                    if (Objects.nonNull(dataSetPoint)) {
                        String yString = dataSetPoint.y == 0 ? "" : "[" + dataSetPoint.y + "]\u5217";
                        importInformations.setMessage(amendInfo.getErrorMessage() + "\uff08\u7b2c[" + (dataSetPoint.x + 1) + "]\u884c" + yString + "\uff09");
                    }
                    orgCode = (title = unitCodeUtil.getCodeTitle(orgCode = amendInfo.getOrgCode())) == null ? orgCode : title;
                    importInformations.setUnitCode(orgCode);
                    ((List)results.get("skip_data")).add(importInformations);
                }
            }
        }
    }

    private void buildImpInfos(CommonMessage message, FormDefine formDefine, String dw, List<CommonImportDetails> detail, IRegionDataSet unitCodeUtil) throws Exception {
        if (unitCodeUtil != null) {
            CommonImportDetails commonImportDetails;
            DimensionCombinationBuilder builder;
            Map unImport = unitCodeUtil.getUnImport();
            Set inexistence = (Set)unImport.get("unit_inexistence");
            Set noaccess = (Set)unImport.get("unit_noaccess");
            Set success = (Set)unImport.get("unit_success");
            Set regionNoAccess = (Set)unImport.get("region_nosuccess");
            if (inexistence != null && !inexistence.isEmpty()) {
                for (String item : inexistence) {
                    builder = new DimensionCombinationBuilder();
                    builder.setValue(dw, (Object)item);
                    commonImportDetails = new CommonImportDetails(builder.getCombination(), formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u5355\u4f4d\u4e0d\u5b58\u5728\uff1a" + item);
                    detail.add(commonImportDetails);
                }
            }
            if (noaccess != null && !noaccess.isEmpty()) {
                for (String item : noaccess) {
                    builder = new DimensionCombinationBuilder();
                    builder.setValue(dw, (Object)item);
                    commonImportDetails = new CommonImportDetails(builder.getCombination(), formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u5355\u4f4d\u6ca1\u6743\u9650\uff1a" + item);
                    detail.add(commonImportDetails);
                }
            }
            if (regionNoAccess != null && !regionNoAccess.isEmpty()) {
                for (String item : regionNoAccess) {
                    builder = new DimensionCombinationBuilder();
                    builder.setValue(dw, (Object)item);
                    commonImportDetails = new CommonImportDetails(builder.getCombination(), formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u533a\u57df\u65e0\u6743\u9650\u5bfc\u5165");
                    detail.add(commonImportDetails);
                }
            }
            if (success != null && !success.isEmpty()) {
                for (String item : success) {
                    builder = new DimensionCombinationBuilder();
                    builder.setValue(dw, (Object)item);
                    if (message.getDimensions() != null) {
                        message.getDimensions().getDimensionCombinations().add(builder.getCombination());
                        continue;
                    }
                    DimensionCollectionBuilder build = new DimensionCollectionBuilder();
                    build.setEntityValue(dw, null, new Object[]{item});
                    message.setDimensions(build.getCollection());
                }
            }
        }
    }

    private void buildErrInfoNormal(Map<String, Object> results, RegionData region, Map<String, Set<String>> unImport) {
        Set<String> set;
        Set<String> noAccess;
        Set<String> inexistence = unImport.get("unit_inexistence");
        if (inexistence != null && !inexistence.isEmpty()) {
            for (String string : inexistence) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u6ca1\u6709\u8be5\u5355\u4f4dcode\u5bf9\u5e94\u7684\u4e3b\u4f53\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", string);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((noAccess = unImport.get("unit_noaccess")) != null && !noAccess.isEmpty()) {
            for (String item : noAccess) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u6ca1\u6709\u5bfc\u5165\u6743\u9650\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
        if ((set = unImport.get("region_nosuccess")) != null && !set.isEmpty()) {
            for (String item : set) {
                ImportInformations errorss = new ImportInformations(region.getFormKey(), region.getFormCode(), region.getTitle(), "\u8be5\u5355\u4f4d\u5b58\u5728\u4e0e\u9ed8\u8ba4\u7ef4\u5ea6\u503c\u4e0d\u5339\u914d\u7684\u6570\u636e\uff0c\u4e0d\u6267\u884c\u5bfc\u5165\u64cd\u4f5c!", item);
                List errorInfo = null;
                errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorss");
                errorInfo.add(errorss);
                results.put("errorInfo", errorInfo);
                results.put("msg", "error");
            }
        }
    }

    private void buildErrInfo(Map<String, Object> results, RegionData regionData, Exception e) {
        int dbDataCount = Integer.parseInt(results.get("dbDataCount").toString());
        int lineNum = Integer.parseInt(results.get("lineNum").toString());
        String errMessage = e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : "");
        ImportInformations error = new ImportInformations(regionData.getFormKey(), regionData.getFormCode(), regionData.getTitle(), "\u7b2c" + (lineNum - 1000 > 0 ? lineNum - 1000 : 0) + "\u5230" + lineNum + "\u884c," + errMessage + "(\u5efa\u8bae\u5b9a\u4f4d\uff1a" + dbDataCount + "\u884c)", (String)results.get("unitCode"));
        List errorInfo = null;
        errorInfo = results.get("errorInfo") == null ? new ArrayList() : (List)results.get("errorInfo");
        errorInfo.add(error);
        results.put("errorInfo", errorInfo);
        results.put("msg", "error");
    }

    private String mapping(CommonParams commonParams, String formCode, ParamsMapping mapping) {
        if (commonParams != null && commonParams.getMapping() != null) {
            ParamsMapping paramsMapping = commonParams.getMapping();
            ArrayList<String> formCodes = new ArrayList<String>();
            formCodes.add(formCode);
            Map originFormCode = paramsMapping.getOriginFormCode(formCodes);
            formCode = (String)originFormCode.get(formCode);
        }
        return formCode;
    }

    public void txtFileData(File file, TableContext tableContext, CommonParams commonParams, CommonMessage message, DataImportLogger dataImportLogger) throws Exception {
        int regionTop;
        Map results = (Map)message.getDetail();
        results.computeIfAbsent("error_data", k -> new ArrayList());
        results.computeIfAbsent("skip_data", k -> new ArrayList());
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            ImportInformations importInformations = new ImportInformations(null, null, null, String.format("%s \u4e0d\u662fTXT\u7c7b\u578b\u6587\u4ef6\uff0c\u4e0d\u53ef\u5bfc\u5165\uff01", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        String[] tokens = ExtUtil.trimEnd((String)file.getName(), (String)".txt").split("_F");
        String formCode = null;
        if (tokens.length == 2) {
            formCode = tokens[0];
            regionTop = Integer.parseInt(tokens[1]);
        } else {
            formCode = ExtUtil.trimEnd((String)file.getName(), (String)".txt");
            regionTop = 1;
        }
        String dw = null;
        TaskDefine queryTaskDefine = null;
        if (tableContext.getTaskKey() != null) {
            queryTaskDefine = this.viewController.queryTaskDefine(tableContext.getTaskKey());
        }
        if (queryTaskDefine == null) {
            FormSchemeDefine formScheme = this.viewController.getFormScheme(tableContext.getFormSchemeKey());
            dw = formScheme.getDw();
        } else {
            dw = queryTaskDefine.getDw();
        }
        ParamsMapping mapping = null;
        formCode = this.mapping(commonParams, formCode, mapping);
        FormDefine formDefine = this.getFormDefine(tableContext, formCode);
        if (null == formDefine) {
            ImportInformations importInformations = new ImportInformations(null, formCode, null, String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        tableContext.setPwd(file.getAbsolutePath().replace(file.getName(), ""));
        List<RegionData> formRegions = this.getFormRegions(tableContext, formCode);
        if (null == formRegions) {
            ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), String.format("\u672a\u627e\u5230\u5bf9\u5e94\u6587\u4ef6\uff1a%s \u7684\u62a5\u8868\u3002", file.getName()), "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        if (formDefine != null && formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
            return;
        }
        RegionData region = null;
        for (RegionData regionData : formRegions) {
            if (regionData.getRegionTop() != regionTop) continue;
            region = regionData;
            break;
        }
        if (null == region) {
            ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), "\u672a\u5339\u914d\u8be5\u533a\u57df\u6570\u636e\uff0c\u8bb0\u5f55\u9519\u8bef\u4fe1\u606f", "");
            ((List)results.get("error_data")).add(importInformations);
            return;
        }
        ArrayList<ExportFieldDefine> fields = new ArrayList<ExportFieldDefine>();
        int lineNum = 0;
        String unitCode = "";
        if (message.getSuccessDW() == null) {
            message.setSuccessDW(new ArrayList());
        }
        RegionDataSet unitCodeUtil = null;
        int unitIndex = -1;
        HashSet<String> unitCodes = new HashSet<String>();
        HashSet<String> tableCodes = new HashSet<String>();
        try (FileReader fr = new FileReader(file.getAbsolutePath());
             BufferedReader bf = new BufferedReader(fr);){
            RegionDataSet regionDataSet;
            String str = bf.readLine();
            if (str != null) {
                for (String s : tableContext.getSplitGather()) {
                    str = str.replace(s, tableContext.getSplit());
                }
                StringTokenizer tokenF = new StringTokenizer(str, tableContext.getSplit());
                int index = 0;
                while (tokenF.hasMoreElements()) {
                    ExportFieldDefine efd = new ExportFieldDefine();
                    efd.setCode(tokenF.nextToken());
                    fields.add(efd);
                    if (efd.getCode().contains("MDCODE")) {
                        unitIndex = index;
                    }
                    ++index;
                }
            }
            boolean noPage = false;
            if (formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && region.getRegionTop() > 1) {
                regionDataSet = new SBRegionDataSet(tableContext, region, fields);
                noPage = true;
            } else {
                regionDataSet = new RegionDataSet(tableContext, region, fields);
            }
            unitCodeUtil = regionDataSet;
            List importFields = regionDataSet.getFieldDataList();
            importFields.forEach(e -> tableCodes.add(e.getTableCode()));
            while ((str = bf.readLine()) != null) {
                for (String s : tableContext.getSplitGather()) {
                    str = str.replace(s, tableContext.getSplit());
                }
                StringTokenEnumeration tokenEnm = new StringTokenEnumeration(str, tableContext.getSplit());
                ArrayList<Object> listData = new ArrayList<Object>();
                while (tokenEnm.hasMoreElements()) {
                    listData.add(tokenEnm.nextElement());
                }
                unitCode = listData.get(0).toString();
                if (!message.getSuccessDW().contains(unitCode)) {
                    message.getSuccessDW().add(unitCode);
                }
                if (mapping != null && unitIndex != -1) {
                    ArrayList<String> dwCodes = new ArrayList<String>();
                    dwCodes.add(listData.get(unitIndex).toString());
                    Map originOrgCode = mapping.getOriginOrgCode(dwCodes);
                    listData.add(unitIndex, originOrgCode.get(dwCodes.get(0)));
                }
                regionDataSet.importDatas(listData);
                unitCodes.add(unitCode);
                if (noPage || ++lineNum % 20000 != 0) continue;
                regionDataSet.commit();
            }
            regionDataSet.commit();
        }
        catch (Exception e2) {
            if (!(e2 instanceof DataTransferException)) {
                ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), e2.getMessage(), unitCode);
                ((List)results.get("error_data")).add(importInformations);
            }
            String msg = "\u8bfbtxt\u6587\u4ef6\u51fa\u9519\uff1a" + e2.getMessage();
            dataImportLogger.importError(msg);
            log.info(msg, e2);
        }
        this.addTableToUnit(unitCodes, tableCodes, dataImportLogger);
        if (null != unitCodeUtil) {
            List amendInfos;
            List errorInfos = unitCodeUtil.getImportErrorInfos();
            if (!errorInfos.isEmpty()) {
                for (ImportErrorData errorInfo : errorInfos) {
                    String orgCode;
                    String title;
                    ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), errorInfo.getErrorMessage(), errorInfo.getOrgCode());
                    Point dataSetPoint = errorInfo.getPoint();
                    if (Objects.nonNull(dataSetPoint)) {
                        String yString = dataSetPoint.y == 0 ? "" : "[" + dataSetPoint.y + "]\u5217";
                        importInformations.setMessage(errorInfo.getErrorMessage() + " \uff08\u9519\u8bef\u4f4d\u7f6e\uff1a\u7b2c[" + (dataSetPoint.x + 1) + "]\u884c" + yString + "\uff09");
                    }
                    orgCode = (title = unitCodeUtil.getCodeTitle(orgCode = errorInfo.getOrgCode())) == null ? orgCode : title;
                    importInformations.setUnitCode(orgCode);
                    ((List)results.get("error_data")).add(importInformations);
                }
            }
            if (!(amendInfos = unitCodeUtil.getImportAmendInfos()).isEmpty()) {
                for (ImportErrorData amendInfo : amendInfos) {
                    String orgCode;
                    String title;
                    ImportInformations importInformations = new ImportInformations(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), amendInfo.getErrorMessage(), amendInfo.getOrgCode());
                    Point dataSetPoint = amendInfo.getPoint();
                    if (Objects.nonNull(dataSetPoint)) {
                        String yString = dataSetPoint.y == 0 ? "" : "[" + dataSetPoint.y + "]\u5217";
                        importInformations.setMessage(amendInfo.getErrorMessage() + "\uff08\u7b2c[" + (dataSetPoint.x + 1) + "]\u884c" + yString + "\uff09");
                    }
                    orgCode = (title = unitCodeUtil.getCodeTitle(orgCode = amendInfo.getOrgCode())) == null ? orgCode : title;
                    importInformations.setUnitCode(orgCode);
                    ((List)results.get("skip_data")).add(importInformations);
                }
            }
        }
    }

    private Map<String, Set<String>> getImportInfo(IRegionDataSet regionDataSet, Map<String, Set<String>> unImport) throws Exception {
        if (unImport != null && regionDataSet != null) {
            Set<String> inexistence = unImport.get("unit_inexistence");
            Set<String> noaccess = unImport.get("unit_noaccess");
            Set<String> success = unImport.get("unit_success");
            Set<String> regionNoAccess = unImport.get("region_nosuccess");
            Map unImport2 = regionDataSet.getUnImport();
            Set<String> inexistence2 = (Set<String>)unImport2.get("unit_inexistence");
            Set<String> noaccess2 = (Set<String>)unImport2.get("unit_noaccess");
            Set<String> success2 = (Set<String>)unImport2.get("unit_success");
            Set<String> regionNoAccess2 = unImport.get("region_nosuccess");
            if (inexistence != null && inexistence2 != null) {
                inexistence2.addAll(inexistence);
            } else if (inexistence2 == null) {
                inexistence2 = inexistence;
            }
            if (noaccess != null && noaccess2 != null) {
                noaccess2.addAll(noaccess);
            } else if (noaccess2 == null) {
                noaccess2 = noaccess;
            }
            if (success2 != null && success != null) {
                success2.addAll(success);
            } else if (success2 == null) {
                success2 = success;
            }
            if (regionNoAccess2 != null && regionNoAccess != null) {
                regionNoAccess2.addAll(regionNoAccess);
            } else if (regionNoAccess2 == null) {
                regionNoAccess2 = regionNoAccess;
            }
            return unImport2;
        }
        if (regionDataSet != null) {
            return regionDataSet.getUnImport();
        }
        return new HashMap<String, Set<String>>();
    }

    private List<RegionData> getFormRegions(TableContext tableContext, String formCode) {
        FormDefine formDefine;
        block18: {
            formDefine = null;
            String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
            String separator = " ";
            if (separatorMessage.equals("1")) {
                separator = "_";
            } else if (separatorMessage.equals("2")) {
                separator = "&";
            }
            String[] formCodeArr = null;
            if (formCode.contains(separator)) {
                formCodeArr = formCode.split(separator);
            }
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    Iterator iterator = schemeByTask.iterator();
                    while (iterator.hasNext()) {
                        FormSchemeDefine fsd = (FormSchemeDefine)iterator.next();
                        if (formCodeArr != null && formCodeArr.length > 1) {
                            for (String formCodeInfo : formCodeArr) {
                                formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCodeInfo);
                                if (formDefine == null) {
                                    continue;
                                }
                                break;
                            }
                        } else {
                            formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        }
                        if (null == formDefine) continue;
                        tableContext.setFormSchemeKey(fsd.getKey());
                        break block18;
                    }
                    break block18;
                }
                if (formCodeArr != null && formCodeArr.length > 1) {
                    for (String formCodeInfo : formCodeArr) {
                        formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCodeInfo);
                        if (formDefine == null) {
                            continue;
                        }
                        break block18;
                    }
                    break block18;
                }
                formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        if (null == formDefine) {
            return null;
        }
        List allRegions = this.viewController.getAllRegionsInForm(formDefine.getKey());
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        for (DataRegionDefine dataRegionDefine : allRegions) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private FormDefine getFormDefine(TableContext tableContext, String formCode) {
        block4: {
            FormDefine formDefine = null;
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    for (FormSchemeDefine fsd : schemeByTask) {
                        formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formCode);
                        if (null == formDefine) continue;
                        return formDefine;
                    }
                    break block4;
                }
                return this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formCode);
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formCode, (Object)e);
            }
        }
        return null;
    }

    private void addTableToUnit(Set<String> unitCodes, Set<String> tableCodes, DataImportLogger dataImportLogger) {
        if (unitCodes.isEmpty() || tableCodes.isEmpty()) {
            return;
        }
        try {
            for (String unitCode : unitCodes) {
                for (String tableCode : tableCodes) {
                    dataImportLogger.addTableToUnit(unitCode, tableCode);
                }
            }
        }
        catch (Exception e) {
            log.error("\u7ec4\u88c5\u7cfb\u7edf\u65e5\u5fd7\u53c2\u6570\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef {}", (Object)e.getMessage(), (Object)e);
        }
    }
}


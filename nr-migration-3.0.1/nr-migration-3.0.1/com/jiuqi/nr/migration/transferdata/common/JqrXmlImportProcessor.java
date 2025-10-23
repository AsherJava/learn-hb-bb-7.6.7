/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.sql.misc.SXElement
 *  com.jiuqi.np.sql.misc.SXElementBuilder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.param.output.DesCheckState
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.bean.BaseDataMapping
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  com.jiuqi.nvwa.mapping.service.impl.BaseDataMappingServiceImpl
 *  com.jiuqi.nvwa.mapping.service.impl.OrgMappingServiceImpl
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.sql.misc.SXElement;
import com.jiuqi.np.sql.misc.SXElementBuilder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransFloatRowZbValue;
import com.jiuqi.nr.migration.transferdata.bean.TransImportContext;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import com.jiuqi.nr.migration.transferdata.common.JqrDataPackageUtil;
import com.jiuqi.nr.migration.transferdata.common.MatchFormula;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.dbservice.service.ISaveDataService;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Service;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataMapping;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import com.jiuqi.nvwa.mapping.service.impl.BaseDataMappingServiceImpl;
import com.jiuqi.nvwa.mapping.service.impl.OrgMappingServiceImpl;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

public class JqrXmlImportProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JqrXmlImportProcessor.class);
    private static final String LOGGER_PREFIX_LOAD = "xml\u6570\u636e\u6587\u4ef6\u89e3\u6790\uff1a";
    private static final String Tree_xml = "0_Tree.xml";
    private static final String LOGGER_PREFIX_LOAD_XML = "xml\u6570\u636e\u6587\u4ef6\u89e3\u6790\uff1a{}";
    private static final String UNDERLINE = "_";
    private final JQRResourceMapping2Service jqrCustomMapping2Service;
    private final ISaveDataService saveExecutor;
    private final IFormulaRunTimeController formulaRunTimeController;
    private final FormulaMappingService formulaMappingService;
    private final IRunTimeViewController runTimeViewController;
    private final ICheckErrorDescriptionService iCheckErrorDescriptionService;
    private final EntityUtil entityUtil;
    private final DimensionCollectionUtil dimensionCollectionUtil;
    private Map<String, Map<String, String>> dimValues2DimKVCache;

    public JqrXmlImportProcessor(JQRResourceMapping2Service jqrCustomMapping2Service, ISaveDataService saveExecutor, IFormulaRunTimeController formulaRunTimeController, FormulaMappingService formulaMappingService, IRunTimeViewController runTimeViewController, ICheckErrorDescriptionService iCheckErrorDescriptionService, EntityUtil entityUtil, DimensionCollectionUtil dimensionCollectionUtil) {
        this.jqrCustomMapping2Service = jqrCustomMapping2Service;
        this.saveExecutor = saveExecutor;
        this.formulaRunTimeController = formulaRunTimeController;
        this.formulaMappingService = formulaMappingService;
        this.runTimeViewController = runTimeViewController;
        this.iCheckErrorDescriptionService = iCheckErrorDescriptionService;
        this.entityUtil = entityUtil;
        this.dimensionCollectionUtil = dimensionCollectionUtil;
    }

    private ReturnObject parseAndGetInfo(byte[] byteData, int count) throws ParserConfigurationException, SAXException, IOException {
        SXElement dataSXE = new SXElementBuilder().build(byteData);
        SXElement e_data = this.getChildElement(dataSXE, "data");
        StringBuilder stringBuilder = new StringBuilder();
        if (e_data == null) {
            stringBuilder.append("\u6587\u4ef6\u5185\u5bb9\u9519\u8bef\u3002");
            return ReturnObject.Error(stringBuilder.toString());
        }
        SXElement e_general = this.getChildElement(e_data, "general");
        if (e_general == null) {
            stringBuilder.append("\u6587\u4ef6\u5185\u5bb9\u9519\u8bef\u3002");
            return ReturnObject.Error(stringBuilder.toString());
        }
        SXElement e_solution = this.getChildElement(e_general, "solution");
        if (e_solution == null) {
            stringBuilder.append("\u672a\u627e\u5230\u4e1a\u52a1\u65b9\u6848\u8282\u70b9\u4fe1\u606f\u3002");
            return ReturnObject.Error(stringBuilder.toString());
        }
        String solutionName = e_solution.getAttribute("name");
        if (!StringUtils.hasLength(solutionName)) {
            stringBuilder.append("\u4e1a\u52a1\u65b9\u6848\u4e3a\u7a7a\u3002");
            return ReturnObject.Error(stringBuilder.toString());
        }
        stringBuilder.append("\u4e1a\u52a1\u65b9\u6848\u6807\u8bc6\uff1a").append(solutionName).append("; ");
        List<SXElement> dimElements = this.getChildren(e_general, "dim");
        if (dimElements.size() < 2) {
            stringBuilder.append("\u7ef4\u5ea6\u4fe1\u606f\u5f02\u5e38\u3002");
            return ReturnObject.Error(stringBuilder.toString());
        }
        boolean flag = true;
        for (SXElement dimSxEle : dimElements) {
            String entityName = dimSxEle.getAttribute("name");
            if ("DATATIME".equals(entityName)) {
                String entityValue = dimSxEle.getAttribute("value");
                if (!StringUtils.hasLength(entityValue)) {
                    flag = false;
                    stringBuilder.append("\u65f6\u671f\u4fe1\u606f\u4e3a\u7a7a\u3002");
                    continue;
                }
                stringBuilder.append("\u65f6\u671f\uff1a").append(entityValue).append("; ");
                continue;
            }
            String dw = dimSxEle.getAttribute("tableName");
            if (!StringUtils.hasLength(dw)) {
                flag = false;
                stringBuilder.append("\u4e3b\u7ef4\u5ea6\u4fe1\u606f\u4e3a\u7a7a\u3002");
                continue;
            }
            stringBuilder.append("\u4e3b\u7ef4\u5ea6\uff1a").append(dw).append("; ");
        }
        if (!flag) {
            return ReturnObject.Error(stringBuilder.append("\u5355\u4f4d\u6570\u91cf\uff1a").append(count).append(".").toString());
        }
        return ReturnObject.Success(stringBuilder.append("\u5355\u4f4d\u6570\u91cf\uff1a").append(count).append(".").toString());
    }

    public ReturnObject parseAndGetXmlInfo(String filename, File cacheFile) throws Exception {
        if (filename.endsWith(".xml")) {
            byte[] byteData = JqrDataPackageUtil.getByteData(cacheFile);
            return this.parseAndGetInfo(byteData, 1);
        }
        Map<String, byte[]> filename2Bytes = JqrDataPackageUtil.getFileDataMap(cacheFile);
        if (filename2Bytes.size() == 0) {
            return ReturnObject.Error("\u538b\u7f29\u6587\u4ef6\u4e3a\u7a7a");
        }
        Set<String> entryNameSet = filename2Bytes.keySet();
        List entryNameList = entryNameSet.stream().sorted().collect(Collectors.toList());
        Optional<String> management = entryNameList.stream().filter(n -> n.split(UNDERLINE).length == 2 && !Tree_xml.endsWith((String)n)).findFirst();
        boolean isXM = management.isPresent();
        boolean isCreateOrg = false;
        byte[] oneFileData = null;
        for (String entryName : entryNameList) {
            if (entryName.startsWith("0_")) {
                isCreateOrg = true;
                continue;
            }
            if (isXM && management.get().equals(entryName)) continue;
            oneFileData = filename2Bytes.get(entryName);
            break;
        }
        if (oneFileData != null) {
            int unitCount = isXM && isCreateOrg ? entryNameSet.size() - 2 : (isXM || isCreateOrg ? entryNameSet.size() - 1 : entryNameSet.size());
            return this.parseAndGetInfo(oneFileData, unitCount);
        }
        return ReturnObject.Error("jqr\u538b\u7f29\u5305\u91cc\u65e0\u7b26\u5408\u8981\u6c42\u7684xml\u6570\u636e\u6587\u4ef6\u3002");
    }

    public void decompressJQR(String filename, byte[] byteData, TransImportContext importContext) throws Exception {
        Map<String, byte[]> filename2Bytes = JqrDataPackageUtil.getFileDataMap(filename, byteData);
        if (filename2Bytes.size() == 0) {
            logger.error(LOGGER_PREFIX_LOAD_XML, (Object)"\u538b\u7f29\u6587\u4ef6\u4e3a\u7a7a");
            importContext.getImportLog().setSummaryLog("\u538b\u7f29\u6587\u4ef6\u4e3a\u7a7a");
            return;
        }
        List entryNameList = filename2Bytes.keySet().stream().sorted().collect(Collectors.toList());
        int unitCount = entryNameList.size();
        for (String entryName : entryNameList) {
            if (entryName.startsWith("0_")) {
                --unitCount;
                filename2Bytes.remove(entryName);
                entryNameList.remove(entryName);
                continue;
            }
            if (entryName.split(UNDERLINE).length == 2) {
                --unitCount;
                filename2Bytes.remove(entryName);
                entryNameList.remove(entryName);
                continue;
            }
            importContext.setLogUnitInfo(this.getUnitInfo(entryName));
            byte[] oneFileData = filename2Bytes.get(entryName);
            if (oneFileData != null) {
                logger.info(LOGGER_PREFIX_LOAD_XML, (Object)entryName);
                this.analyseDataXml(oneFileData, importContext);
                continue;
            }
            importContext.addErrorInfoToLog("\u6587\u4ef6\u6d41\u4e3a\u7a7a\u3002");
        }
        importContext.getImportLog().setUnitCount(String.valueOf(unitCount));
    }

    public String getUnitInfo(String xmlFileName) {
        String[] unitInfo = xmlFileName.split("\\.")[0].split(UNDERLINE);
        if (unitInfo.length > 2) {
            return "\u5355\u4f4d\uff1a" + unitInfo[2] + " " + unitInfo[1];
        }
        return xmlFileName;
    }

    public void analyseDataXml(byte[] byteData, TransImportContext importContext) throws Exception {
        SXElement memosSXE;
        SXElement e_dataState;
        List<SXElement> mbList;
        assert (byteData != null);
        SXElement dataSXE = new SXElementBuilder().build(byteData);
        SXElement e_data = this.getChildElement(dataSXE, "data");
        this.checkElementNotNull(e_data, "\u5bfc\u5165\u7684\u6570\u636e\u4e3a\u7a7a", importContext);
        SXElement e_general = this.getChildElement(e_data, "general");
        this.checkElementNotNull(e_general, "general\u4fe1\u606f\u4e3a\u7a7a", importContext);
        SXElement e_solution = this.getChildElement(e_general, "solution");
        this.checkSolution(e_solution, importContext);
        importContext.getImportLog().setSolutionInfo("\u4efb\u52a1\uff1a" + importContext.getTaskDefine().getTitle() + " " + importContext.getTaskDefine().getTaskCode());
        List<SXElement> dimSxEleList = this.getChildren(e_general, "dim");
        this.checkAndParseDimInfos(dimSxEleList, importContext);
        CKDParam ckdParam = this.getCKDParam(e_data, importContext);
        if (ckdParam != null) {
            this.dimValues2DimKVCache = new HashMap<String, Map<String, String>>();
        }
        if ((mbList = this.getChildren(e_data, "mb")).size() > 0) {
            importContext.addDetailInfoToLog("\u5f00\u59cbparseAndStorageData\u3002\u3002\u3002");
            this.parseAndStorageData(mbList, importContext);
            importContext.addDetailInfoToLog("parseAndStorageData\u7ed3\u675f\u3002");
        }
        if ((e_dataState = this.getChildElement(e_data, "datastate")) != null) {
            this.parseAndStorageDataState(e_dataState, importContext);
            TaskFlowsDefine flowsSetting = importContext.getTaskDefine().getFlowsSetting();
            this.logSyncStatus(flowsSetting, importContext);
        }
        if (ckdParam != null) {
            this.processCheckError(ckdParam, importContext);
        }
        if ((memosSXE = this.getChildElement(e_data, "memos")) != null) {
            this.parseAndStorageHiDesc(memosSXE, importContext);
        }
    }

    private void checkSolution(SXElement e_solution, TransImportContext importContext) {
        if (e_solution == null || !StringUtils.hasLength(e_solution.getAttribute("name"))) {
            this.logErrorAndThrow("\u4e1a\u52a1\u65b9\u6848\u4e3a\u7a7a", importContext);
        }
        String solutionName = e_solution.getAttribute("name");
        String mappingSchemeKey = importContext.getMappingSchemeKey();
        boolean flag = false;
        if (StringUtils.hasLength(mappingSchemeKey)) {
            String solutionNameMapping = this.getSolutionNameMapping(importContext);
            if (!solutionNameMapping.equals(solutionName)) {
                flag = true;
                logger.error("{}\u4e1a\u52a1\u65b9\u6848\u4e0e\u4efb\u52a1\u4e0d\u5339\u914d", (Object)LOGGER_PREFIX_LOAD);
            }
        } else if (!solutionName.equals(importContext.getTaskDefine().getTaskCode())) {
            flag = true;
            logger.error("{}\u4e1a\u52a1\u65b9\u6848\u4e0e\u4efb\u52a1\u4e0d\u5339\u914d", (Object)LOGGER_PREFIX_LOAD);
        }
        if (flag) {
            importContext.addErrorInfoToLog("\u4e1a\u52a1\u65b9\u6848\u4e0e\u4efb\u52a1\u4e0d\u5339\u914d\uff0c\u8bf7\u68c0\u67e5\u6620\u5c04\u65b9\u6848\u914d\u7f6e\u3002");
            throw new RuntimeException();
        }
    }

    private void processCheckError(CKDParam CKDParam2, TransImportContext importContext) {
        Map<String, String> formCode2Key = CKDParam2.formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, IBaseMetaItem::getKey));
        HashMap<String, Map<String, List<IParsedExpression>>> formKey2Fmcode2ExpCache = new HashMap<String, Map<String, List<IParsedExpression>>>();
        List<SXElement> dimElements = this.getChildren(CKDParam2.e_checkError, "checkDimension");
        ArrayList<CheckDesObj> checkDesObjs = new ArrayList<CheckDesObj>();
        Map<String, DimInfo> allCKDDimRange = importContext.getDimInfos().stream().collect(Collectors.toMap(e -> e.getName().startsWith("MD_ORG") ? "MD_ORG" : e.getName(), e -> e));
        for (SXElement dimElement : dimElements) {
            List<SXElement> floatAreaChildrens = this.getChildren(dimElement, "checkFloatElement");
            List<SXElement> fixAreaChildrens = this.getChildren(dimElement, "checkErroElement");
            ArrayList<SXElement> ckdElement = new ArrayList<SXElement>();
            ckdElement.addAll(floatAreaChildrens);
            ckdElement.addAll(fixAreaChildrens);
            for (SXElement element : ckdElement) {
                CheckDesObj checkDesObj = this.parseElementToCheckDesObj(CKDParam2, formCode2Key, formKey2Fmcode2ExpCache, allCKDDimRange, element, importContext);
                if (checkDesObj == null) continue;
                checkDesObjs.add(checkDesObj);
            }
        }
        if (checkDesObjs.size() == 0) {
            logger.info(LOGGER_PREFIX_LOAD_XML, (Object)"\u65e0\u516c\u5f0f\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u3002");
            return;
        }
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        Map<String, DimensionValue> curCKDDimMap = this.dimInfoToDimValueMap(new ArrayList<DimInfo>(allCKDDimRange.values()));
        FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(importContext.getTaskDefine().getDw()), curCKDDimMap);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(curCKDDimMap, importContext.getFormSchemeKey(), (SpecificDimBuilder)fixedDimBuilder);
        checkDesBatchSaveObj.setCheckDesQueryParam(this.buildCheckDesQueryParam(dimensionCollection, importContext.getFormSchemeKey(), checkDesObjs));
        checkDesBatchSaveObj.setCheckDesObjs(checkDesObjs);
        logger.info("\u516c\u5f0f\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u5bfc\u5165\u5f00\u59cb\uff0c\u5171\u6709 " + checkDesObjs.size() + "\u6761\u9519\u8bef\u8bf4\u660e---");
        try {
            this.iCheckErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
            logger.info("\u516c\u5f0f\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u5bfc\u5165\u7ed3\u675f---");
        }
        catch (Exception e2) {
            logger.error("\u6279\u91cf\u6dfb\u52a0\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u5931\u8d25");
        }
    }

    private CheckDesQueryParam buildCheckDesQueryParam(DimensionCollection dimensionCollection, String formSchemeKey, List<CheckDesObj> checkDesObjs) {
        CheckDesQueryParam queryParam = new CheckDesQueryParam();
        ArrayList<String> formulaSchemeKey = new ArrayList<String>();
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<String> formulaKey = new ArrayList<String>();
        for (CheckDesObj checkDesObj : checkDesObjs) {
            if (!formKeys.contains(checkDesObj.getFormKey())) {
                formKeys.add(checkDesObj.getFormKey());
            }
            if (!formulaKey.contains(checkDesObj.getFormulaExpressionKey())) {
                formulaKey.add(checkDesObj.getFormulaExpressionKey());
            }
            if (formulaSchemeKey.contains(checkDesObj.getFormulaSchemeKey())) continue;
            formulaSchemeKey.add(checkDesObj.getFormulaSchemeKey());
        }
        queryParam.setFormulaKey(formulaKey);
        queryParam.setFormKey(formKeys);
        queryParam.setFormulaSchemeKey(formulaSchemeKey);
        queryParam.setDimensionCollection(dimensionCollection);
        queryParam.setFormSchemeKey(formSchemeKey);
        return queryParam;
    }

    private CheckDesObj parseElementToCheckDesObj(CKDParam CKDParam2, Map<String, String> formCode2Key, Map<String, Map<String, List<IParsedExpression>>> formKey2Fmcode2ExpCache, Map<String, DimInfo> allCKDDimRange, SXElement element, TransImportContext importContext) {
        Map<String, List<IParsedExpression>> fmcode2Exp;
        String jqrFormulaCode = element.getString("formulaCode");
        if (!StringUtils.hasLength(jqrFormulaCode)) {
            return null;
        }
        Optional<FormulaMapping> first = CKDParam2.allFormulaMappings.stream().filter(formulaMapping -> jqrFormulaCode.equals(formulaMapping.getmFormulaCode())).findFirst();
        if (!first.isPresent()) {
            importContext.getImportLog().addNoMappingFormulaCode(jqrFormulaCode);
            logger.error("\u672a\u627e\u5230\u516c\u5f0f" + jqrFormulaCode + "\u5bf9\u5e94\u7684\u516c\u5f0f\u6620\u5c04\uff0c\u8be5\u6761\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\u5bfc\u5165\u7ec8\u6b62\u3002");
            return null;
        }
        FormulaMapping formulaMapping2 = first.get();
        String formulaSchemeKey = formulaMapping2.getFormulaScheme();
        String nrFormulaCode = formulaMapping2.getFormulaCode();
        String formKey = formCode2Key.get(formulaMapping2.getFormCode());
        if (!formKey2Fmcode2ExpCache.containsKey(formKey)) {
            formKey2Fmcode2ExpCache.put(formKey, this.getFormulaCode2IParsedExps(formulaSchemeKey, formKey));
        }
        if ((fmcode2Exp = formKey2Fmcode2ExpCache.get(formKey)).size() == 0 || !fmcode2Exp.containsKey(nrFormulaCode)) {
            return null;
        }
        List<IParsedExpression> iParsedExpressions = fmcode2Exp.get(nrFormulaCode);
        if (iParsedExpressions.size() == 0) {
            return null;
        }
        String floatCode = element.getString("floatCode");
        ArrayList<DimInfo> commonCKDDim = new ArrayList<DimInfo>();
        commonCKDDim.add(allCKDDimRange.get("DATATIME"));
        commonCKDDim.add(allCKDDimRange.get("MD_ORG"));
        String jqrCompiledFormulaExp = element.getString("formulaExp");
        String description = element.getString("description");
        String userId = element.getString("userId");
        String recid = element.getString("recid");
        for (IParsedExpression iParsedExpression : iParsedExpressions) {
            ArrayList<DimInfo> curCKDDim = new ArrayList<DimInfo>(commonCKDDim);
            if (floatCode != null) {
                this.addInnerDimToDimInfos(allCKDDimRange, curCKDDim, floatCode, iParsedExpression);
            }
            Map<String, DimensionValue> curCKDDimMap = this.dimInfoToDimValueMap(curCKDDim);
            String nrFormulaExp = iParsedExpression.getSource().getFormula();
            String parsedExpressionKey = iParsedExpression.getKey();
            if (!nrFormulaExp.contains("*")) {
                return this.getCheckDesObj(recid, description, userId, formKey, nrFormulaCode, formulaSchemeKey, parsedExpressionKey, -1, -1, curCKDDimMap);
            }
            int wildcardRow = ((CheckExpression)iParsedExpression).getWildcardRow();
            int wildcardCol = ((CheckExpression)iParsedExpression).getWildcardCol();
            if (wildcardRow == -1 && wildcardCol == -1) {
                return null;
            }
            int[] ints = new MatchFormula().praseWildcard(nrFormulaExp, jqrCompiledFormulaExp);
            if (ints[0] != wildcardRow || ints[1] != wildcardCol) continue;
            return this.getCheckDesObj(recid, description, userId, formKey, nrFormulaCode, formulaSchemeKey, parsedExpressionKey, wildcardRow, wildcardCol, curCKDDimMap);
        }
        return null;
    }

    private void addInnerDimToDimInfos(Map<String, DimInfo> allCKDDimRange, List<DimInfo> curCKDDim, String floatCode, IParsedExpression iParsedExpression) {
        if (this.dimValues2DimKVCache.containsKey(floatCode)) {
            Map<String, String> innerDVs = this.dimValues2DimKVCache.get(floatCode);
            for (String dimName : innerDVs.keySet()) {
                if (allCKDDimRange.containsKey(dimName)) {
                    allCKDDimRange.get(dimName).addValue(innerDVs.get(dimName));
                } else {
                    DimInfo dimInfo = new DimInfo();
                    dimInfo.setName(dimName);
                    dimInfo.addValue(innerDVs.get(dimName));
                    allCKDDimRange.put(dimName, dimInfo);
                }
                curCKDDim.add(new DimInfo(dimName, innerDVs.get(dimName)));
            }
        }
    }

    private static void permuteInnerDimValues(String[] array, int start, List<String> result) {
        if (start == array.length - 1) {
            result.add(String.join((CharSequence)"", array));
            return;
        }
        for (int i = start; i < array.length; ++i) {
            JqrXmlImportProcessor.swapInnerDimValue(array, start, i);
            JqrXmlImportProcessor.permuteInnerDimValues(array, start + 1, result);
            JqrXmlImportProcessor.swapInnerDimValue(array, start, i);
        }
    }

    private static void swapInnerDimValue(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Map<String, List<IParsedExpression>> getFormulaCode2IParsedExps(String formulaSchemeKey, String formKey) {
        HashMap<String, List<IParsedExpression>> result = new HashMap<String, List<IParsedExpression>>();
        List checkParsedExps = this.formulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CHECK);
        for (IParsedExpression iParsedExpression : checkParsedExps) {
            String code = iParsedExpression.getSource().getCode();
            if (!result.containsKey(code)) {
                result.put(code, new ArrayList());
            }
            ((List)result.get(code)).add(iParsedExpression);
        }
        return result;
    }

    @Nullable
    private CKDParam getCKDParam(SXElement eData, TransImportContext importContext) {
        SXElement e_checkError = this.getChildElement(eData, "checkerroDesc");
        if (e_checkError == null) {
            logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)"\u4e0d\u540c\u6b65\u5ba1\u6838\u9519\u8bef\u8bf4\u660e");
            importContext.addDetailInfoToLog("\u4e0d\u540c\u6b65\u5ba1\u6838\u9519\u8bef\u8bf4\u660e");
            return null;
        }
        boolean synCheckErro = e_checkError.getBoolean("checkerroDesc");
        if (!synCheckErro) {
            logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)"\u4e0d\u540c\u6b65\u5ba1\u6838\u9519\u8bef\u8bf4\u660e");
            importContext.addDetailInfoToLog("\u4e0d\u540c\u6b65\u5ba1\u6838\u9519\u8bef\u8bf4\u660e");
            return null;
        }
        String mappingSchemeKey = importContext.getMappingSchemeKey();
        if (!StringUtils.hasText(mappingSchemeKey)) {
            logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)"\u6620\u5c04\u65b9\u6848\u4e3a\u7a7a\uff0c\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u540c\u6b65\u505c\u6b62\u3002");
            importContext.addDetailInfoToLog("\u6620\u5c04\u65b9\u6848\u4e3a\u7a7a\uff0c\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u540c\u6b65\u505c\u6b62\u3002");
            return null;
        }
        List allFormulaMappings = this.formulaMappingService.findByMS(mappingSchemeKey);
        if (allFormulaMappings == null || allFormulaMappings.size() == 0) {
            logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)"\u6620\u5c04\u65b9\u6848\u672a\u914d\u7f6e\u516c\u5f0f\u6620\u5c04\uff0c\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53ef\u80fd\u5bfc\u5165\u5931\u8d25\u3002");
            importContext.addDetailInfoToLog("\u6620\u5c04\u65b9\u6848\u672a\u914d\u7f6e\u516c\u5f0f\u6620\u5c04\uff0c\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53ef\u80fd\u5bfc\u5165\u5931\u8d25\u3002");
            return null;
        }
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(importContext.getFormSchemeKey());
        if (formDefines.size() == 0) {
            logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)"\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u540c\u6b65\u51fa\u73b0\u5f02\u5e38\uff0c\u62a5\u8868\u65b9\u6848\u4e0b\u65e0\u62a5\u8868");
            importContext.addDetailInfoToLog("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u540c\u6b65\u51fa\u73b0\u5f02\u5e38\uff0c\u62a5\u8868\u65b9\u6848\u4e0b\u65e0\u62a5\u8868");
            return null;
        }
        return new CKDParam(e_checkError, mappingSchemeKey, allFormulaMappings, formDefines);
    }

    private Map<String, DimensionValue> dimInfoToDimValueMap(List<DimInfo> dimInfos) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (DimInfo dimInfo : dimInfos) {
            DimensionValue dimensionValue = new DimensionValue();
            if ("DATATIME".equals(dimInfo.getName())) {
                dimensionValue.setName(dimInfo.getName());
                dimensionValue.setValue(dimInfo.getValue());
                dimensionSet.put("DATATIME", dimensionValue);
                continue;
            }
            if (dimInfo.getName().startsWith("MD_ORG")) {
                dimensionValue.setName("MD_ORG");
                dimensionValue.setValue(dimInfo.getValue());
                dimensionSet.put("MD_ORG", dimensionValue);
                continue;
            }
            if (!dimInfo.getName().startsWith("MD_")) continue;
            dimensionValue.setName(dimInfo.getName());
            if (dimInfo.getValues().size() == 1) {
                dimensionValue.setValue(dimInfo.getValues().get(0));
            } else if (dimInfo.getValues().size() > 1) {
                dimensionValue.setValue(String.join((CharSequence)";", dimInfo.getValues()));
            } else {
                dimensionValue.setValue(dimInfo.getValue());
            }
            dimensionSet.put(dimInfo.getName(), dimensionValue);
        }
        return dimensionSet;
    }

    private CheckDesObj getCheckDesObj(String recid, String description, String userId, String formKey, String nrFormulaCode, String formulaSchemeKey, String expressionKey, int globRow, int globCol, Map<String, DimensionValue> dimensionSet) {
        CheckDesObj checkDesObj = new CheckDesObj();
        checkDesObj.setRecordId(recid);
        checkDesObj.setFormKey(formKey);
        checkDesObj.setFormulaSchemeKey(formulaSchemeKey);
        checkDesObj.setFormulaCode(nrFormulaCode);
        checkDesObj.setFormulaExpressionKey(expressionKey);
        checkDesObj.setGlobRow(globRow);
        checkDesObj.setGlobCol(globCol);
        CheckDescription checkDescription = new CheckDescription();
        checkDescription.setDescription(description);
        checkDescription.setState(DesCheckState.DEFAULT);
        checkDescription.setUserId(userId);
        checkDesObj.setCheckDescription(checkDescription);
        checkDesObj.setDimensionSet(dimensionSet);
        return checkDesObj;
    }

    private void checkElementNotNull(SXElement element, String errorMessage, TransImportContext transImport) {
        if (element == null) {
            this.logErrorAndThrow(errorMessage, transImport);
        }
    }

    private void logSyncStatus(TaskFlowsDefine flowsSetting, TransImportContext importContext) {
        if (flowsSetting.getFlowsType() == FlowsType.NOSTARTUP) {
            importContext.addDetailInfoToLog("\u6d41\u7a0b\u72b6\u6001\u540c\u6b65\u7ed3\u675f\uff0c\u4f46\u4efb\u52a1\u672a\u542f\u7528\u6d41\u7a0b\u3002");
        } else {
            importContext.addDetailInfoToLog("\u6d41\u7a0b\u72b6\u6001\u540c\u6b65\u7ed3\u675f\u3002");
        }
    }

    private void parseAndStorageHiDesc(SXElement memosSXE, TransImportContext importContext) {
        List<SXElement> memoSXEList = this.getChildren(memosSXE, "memo");
        ArrayList<TransMemo> memos = new ArrayList<TransMemo>();
        for (SXElement memoSXE : memoSXEList) {
            memos.add(this.parseMemo(memoSXE));
        }
        if (memos.size() == 0) {
            return;
        }
        this.saveExecutor.storageWorkFlowHi(importContext.getFormSchemeKey(), TransferUtils.getDimensionValueSet(importContext.getDimInfos()), importContext.getDimInfos(), memos);
    }

    private void parseAndStorageDataState(SXElement e_dataState, TransImportContext importContext) {
        int targetStates = e_dataState.getInt("state");
        this.saveExecutor.storageDataState(importContext.getFormSchemeKey(), TransferUtils.getDimensionValueSet(importContext.getDimInfos()), targetStates, false);
    }

    private TransMemo parseMemo(SXElement memosSXE) {
        TransMemo transMemo = new TransMemo();
        transMemo.setTime(memosSXE.getAttribute("time"));
        transMemo.setUser(memosSXE.getAttribute("user"));
        transMemo.setOperateCode(memosSXE.getAttribute("operatecode"));
        transMemo.setState(memosSXE.getAttribute("state"));
        transMemo.setStatus(memosSXE.getAttribute("status"));
        transMemo.setContent(memosSXE.getAttribute("content"));
        transMemo.setRpCode(memosSXE.getAttribute("rp"));
        return transMemo;
    }

    private void parseAndStorageData(List<SXElement> mbList, TransImportContext transImport) {
        List<DimInfo> dims = transImport.getDimInfos();
        FieldRelationFactory fieldRelationFactory = (FieldRelationFactory)SpringBeanUtils.getBean(FieldRelationFactory.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        Map<String, String> zbMappingMap = this.getZbMappingMap(transImport);
        for (SXElement mbSXElement : mbList) {
            boolean isFloat = mbSXElement.getBoolean("isfloat");
            if (!isFloat) {
                HashMap<String, Object> zb2ValueInFixRegion = new HashMap<String, Object>();
                this.parseFixRegionMainBody(mbSXElement, zb2ValueInFixRegion, zbMappingMap);
                if (zb2ValueInFixRegion.size() <= 0) continue;
                this.fixRegionStorageData(dims, zb2ValueInFixRegion, transImport, fieldRelationFactory, runtimeDataSchemeService);
                continue;
            }
            ArrayList<TransFloatRowZbValue> zbDataInFloatRegion = new ArrayList<TransFloatRowZbValue>();
            this.parseFloatRegionMainBody(mbSXElement, zbDataInFloatRegion, zbMappingMap);
            if (zbDataInFloatRegion.size() <= 0) continue;
            this.floatRegionStorageData(dims, zbDataInFloatRegion, transImport, fieldRelationFactory, runtimeDataSchemeService);
        }
    }

    private String getEntityValue(SXElement dimSxEle, String entityName, TransImportContext transImport) {
        String entityValue = dimSxEle.getAttribute("value");
        if (!StringUtils.hasLength(entityValue)) {
            String errorMessage = "DATATIME".equals(entityName) ? "\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a" : "\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a";
            this.logErrorAndThrow(errorMessage, transImport);
        }
        return entityValue;
    }

    private void logErrorAndThrow(String errorMessage, TransImportContext transImport) {
        logger.error("{} {}", (Object)LOGGER_PREFIX_LOAD, (Object)errorMessage);
        transImport.addErrorInfoToLog(errorMessage);
        throw new RuntimeException();
    }

    private void processDateTimeDimension(String entityValue, List<DimInfo> dims, TransImportContext transImport) {
        Map<String, String> periodMapping = this.getPeriodMapping(transImport);
        String nrPeriod = periodMapping.get(entityValue);
        if (StringUtils.hasLength(nrPeriod)) {
            entityValue = nrPeriod;
        }
        transImport.getImportLog().setPeriodStr("\u65f6\u671f\uff1a" + entityValue);
        dims.add(new DimInfo("DATATIME", transImport.getTaskDefine().getDateTime(), entityValue));
    }

    private void checkAndParseDimInfos(List<SXElement> dimElements, TransImportContext transImport) {
        if (dimElements.size() < 2) {
            this.logErrorAndThrow("dim\u4fe1\u606f\u9519\u8bef", transImport);
        }
        ArrayList<DimInfo> dims = new ArrayList<DimInfo>(2);
        for (SXElement dimSxEle : dimElements) {
            String entityName = dimSxEle.getAttribute("name");
            String entityValue = this.getEntityValue(dimSxEle, entityName, transImport);
            if ("DATATIME".equals(entityName)) {
                this.processDateTimeDimension(entityValue, dims, transImport);
                continue;
            }
            this.processDWDimension(entityValue, dims, transImport, dimSxEle);
        }
        transImport.setDimInfos(dims);
    }

    private void doStartFlow() {
    }

    private void processDWDimension(String entityValue, List<DimInfo> dims, TransImportContext transImport, SXElement dimSxEle) {
        boolean isBase;
        String dw = transImport.getTaskDefine().getDw();
        transImport.getImportLog().setTableName("\u4e3b\u7ef4\u5ea6\uff1a " + dw.split("@")[0]);
        transImport.addOrgData(new TransOrgInfo(entityValue, dimSxEle.getAttribute("title")));
        String tableNameMapping = this.getTableNameMapping(transImport);
        String tableName = dimSxEle.getAttribute("tableName");
        boolean aBoolean = dimSxEle.getBoolean("startflow");
        if (aBoolean) {
            this.doStartFlow();
        }
        if (!tableNameMapping.equals(tableName)) {
            // empty if block
        }
        Map<String, String> unitMapping = (isBase = dw.endsWith("@BASE")) ? this.getBaseDataMapping(transImport) : this.getOrgMapping(transImport);
        entityValue = StringUtils.hasLength(unitMapping.get(entityValue.toUpperCase())) ? unitMapping.get(entityValue.toUpperCase()) : entityValue;
        dims.add(new DimInfo(dw.split("@")[0], dw, entityValue));
        logger.info("xml\u6570\u636e\u6587\u4ef6\u89e3\u6790\uff1atableName: " + tableName + ", dw: " + dw);
    }

    private List<List<Object>> getFloatValues(List<IMetaData> metaDataList, List<TransFloatRowZbValue> rowDatas, List<DimInfo> dims, boolean isCacheInnerDimValue) {
        Map<String, String> code2Idx = rowDatas.get(0).getCode2Idx();
        ArrayList<List<Object>> floatValues = new ArrayList<List<Object>>();
        block0: for (TransFloatRowZbValue data : rowDatas) {
            ArrayList<String> innerDimValues = new ArrayList<String>();
            HashMap<String, String> innerDimKVs = new HashMap<String, String>();
            ArrayList<Object> values = new ArrayList<Object>();
            double floatOrder = data.getFloatOrder();
            Map<String, TransZbValue> idx2valueInfo = data.getIdx2valueInfo();
            for (int fieldIndex = 0; fieldIndex < metaDataList.size(); ++fieldIndex) {
                IMetaData iMetaData = metaDataList.get(fieldIndex);
                String code = iMetaData.getCode();
                iMetaData.setIndex(fieldIndex);
                if (code2Idx.containsKey(code)) {
                    TransZbValue transZbValue;
                    String idx = code2Idx.get(code);
                    if (!idx2valueInfo.containsKey(idx) || (transZbValue = idx2valueInfo.get(idx)) == null) continue;
                    if (iMetaData.getDataField().getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                        String innerDimValue = String.valueOf(transZbValue.getValue());
                        if (!StringUtils.hasLength(innerDimValue)) continue block0;
                        if (isCacheInnerDimValue) {
                            innerDimValues.add(innerDimValue);
                            innerDimKVs.put(iMetaData.getDataField().getRefDataEntityKey().split("@")[0], innerDimValue);
                        }
                        values.add(transZbValue.getValue());
                        continue;
                    }
                    values.add(transZbValue.getValue());
                    continue;
                }
                if ("DATATIME".equals(code)) {
                    iMetaData.setIndex(fieldIndex);
                    values.add(dims.get(0).getValue());
                    continue;
                }
                if ("MDCODE".equals(code)) {
                    iMetaData.setIndex(fieldIndex);
                    values.add(dims.get(1).getValue());
                    continue;
                }
                if ("FLOATORDER".equals(code)) {
                    iMetaData.setIndex(fieldIndex);
                    BigDecimal bigDecimal = new BigDecimal(floatOrder);
                    values.add(bigDecimal.setScale(4, RoundingMode.HALF_UP));
                    continue;
                }
                if (!"BIZKEYORDER".equals(code)) continue;
                iMetaData.setIndex(fieldIndex);
                values.add(UUIDUtils.getKey());
            }
            floatValues.add(values);
            if (this.dimValues2DimKVCache == null || innerDimValues.contains("")) continue;
            ArrayList<String> permuteValRes = new ArrayList<String>();
            JqrXmlImportProcessor.permuteInnerDimValues(innerDimValues.toArray(new String[0]), 0, permuteValRes);
            for (String innerVal : permuteValRes) {
                if (this.dimValues2DimKVCache.containsKey(innerVal)) continue;
                this.dimValues2DimKVCache.put(innerVal, innerDimKVs);
            }
        }
        return floatValues;
    }

    private void floatRegionStorageData(List<DimInfo> dims, List<TransFloatRowZbValue> rowDatas, TransImportContext importContext, FieldRelationFactory fieldRelationFactory, IRuntimeDataSchemeService runtimeDataSchemeService) {
        if (rowDatas == null || rowDatas.size() == 0) {
            return;
        }
        Map<String, String> code2Idx = rowDatas.get(0).getCode2Idx();
        String dataScheme = importContext.getTaskDefine().getDataScheme();
        List floatTables = runtimeDataSchemeService.getAllDataTable(dataScheme).stream().filter(t -> DataTableType.DETAIL.getValue() == t.getDataTableType().getValue()).collect(Collectors.toList());
        for (DataTable dataTable : floatTables) {
            FieldRelation fieldRelation;
            List metaDataList;
            List dataFields = runtimeDataSchemeService.getDataFieldByTableCode(dataTable.getCode());
            HashMap fieldCode2Key = new HashMap();
            dataFields.forEach(df -> fieldCode2Key.put(df.getCode(), df.getKey()));
            String floatOrderKey = (String)fieldCode2Key.get("FLOATORDER");
            ArrayList<Object> zbKeys = new ArrayList<Object>();
            for (String zbCode : code2Idx.keySet()) {
                if (!fieldCode2Key.containsKey(zbCode)) continue;
                zbKeys.add(fieldCode2Key.get(zbCode));
            }
            if (zbKeys.size() == 0) continue;
            for (String bizKey : dataTable.getBizKeys()) {
                if (zbKeys.contains(bizKey)) continue;
                zbKeys.add(bizKey);
            }
            if (!zbKeys.contains(floatOrderKey) && fieldCode2Key.containsKey("FLOATORDER")) {
                zbKeys.add(fieldCode2Key.get("FLOATORDER"));
            }
            if ((metaDataList = (fieldRelation = fieldRelationFactory.getFieldRelation(zbKeys)).getMetaData()) == null || metaDataList.size() == 0) {
                return;
            }
            try {
                String loggerStr;
                importContext.addDetailInfoToLog("\u4e3b\u4f53\uff1a\u3010" + dataTable.getTitle() + "<" + dataTable.getCode() + ">\u3011");
                List<List<Object>> floatValues = this.getFloatValues(metaDataList, rowDatas, dims, dataTable.getBizKeys().length > 2);
                int size = floatValues.size();
                importContext.addDetailInfoToLog("[\u63d0\u793a]:\u6b63\u5728\u5bfc\u5165" + zbKeys.size() + "\u4e2a\u6307\u6807\uff0c" + size + "\u884c\u6570\u636e.");
                int successRows = this.saveExecutor.storageFloatDatas(dims, importContext.getTaskDefine().getKey(), importContext.getFormSchemeKey(), dataTable.getCode(), metaDataList, floatValues);
                if (successRows == size) {
                    loggerStr = "[\u63d0\u793a]:" + size + "\u884c\u6570\u636e\u5168\u90e8\u5bfc\u5165\u6210\u529f.";
                    logger.info("[\u63d0\u793a]:" + size + "\u884c\u6570\u636e\u5168\u90e8\u5bfc\u5165\u6210\u529f.");
                } else {
                    loggerStr = "[\u63d0\u793a]:\u6210\u529f\u5bfc\u5165" + successRows + "\u884c.\u5931\u8d25\u5bfc\u5165" + (size - successRows) + "\u884c.";
                    logger.info("[\u63d0\u793a]:\u6210\u529f\u5bfc\u5165" + successRows + "\u884c.\u5931\u8d25\u5bfc\u5165" + (size - successRows) + "\u884c.");
                }
                importContext.addDetailInfoToLog(loggerStr);
            }
            catch (Exception e) {
                this.errorLogInStorage(e, dataTable, dims, importContext);
            }
        }
    }

    private static Map<String, List<DataField>> getDataTable2Fields(Set<String> zbCodes, String dataScheme, IRuntimeDataSchemeService runtimeDataSchemeService, TransImportContext transImport) {
        HashMap<String, List<DataField>> dataTable2zbKeys = new HashMap<String, List<DataField>>();
        for (String zbCode : zbCodes) {
            DataField zbField = runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, zbCode);
            if (zbField != null) {
                String dataTableKey = zbField.getDataTableKey();
                if (!dataTable2zbKeys.containsKey(dataTableKey)) {
                    dataTable2zbKeys.put(dataTableKey, new ArrayList());
                    DataTable dataTable = runtimeDataSchemeService.getDataTable(dataTableKey);
                    List dimFields = runtimeDataSchemeService.getDataFields(Arrays.stream(dataTable.getBizKeys()).collect(Collectors.toList()));
                    for (DataField dimField : dimFields) {
                        ((List)dataTable2zbKeys.get(dataTableKey)).add(dimField);
                    }
                }
                ((List)dataTable2zbKeys.get(dataTableKey)).add(zbField);
                continue;
            }
            logger.error("zbCode:{}\u5728\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e2d\u4e0d\u5b58\u5728.", (Object)zbCode);
            transImport.addErrorInfoToLog("zbCode: " + zbCode + "\u5728\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e2d\u4e0d\u5b58\u5728.");
        }
        return dataTable2zbKeys;
    }

    private void fixRegionStorageData(List<DimInfo> dims, Map<String, Object> zbCode2Values, TransImportContext transImport, FieldRelationFactory fieldRelationFactory, IRuntimeDataSchemeService runtimeDataSchemeService) {
        String dataScheme = transImport.getTaskDefine().getDataScheme();
        Map<String, List<DataField>> dataTable2Fields = JqrXmlImportProcessor.getDataTable2Fields(zbCode2Values.keySet(), dataScheme, runtimeDataSchemeService, transImport);
        transImport.addDetailInfoToLog("\u5171\u5bfc\u5165" + dataTable2Fields.size() + "\u5f20\u4e3b\u4f53\u8868.");
        int dataTableIndex = 0;
        for (String dataTableKey : dataTable2Fields.keySet()) {
            ++dataTableIndex;
            DataTable dataTable = runtimeDataSchemeService.getDataTable(dataTableKey);
            transImport.addDetailInfoToLog("\u4e3b\u4f53\uff1a\u3010" + dataTable.getTitle() + "<" + dataTable.getCode() + ">\u3011");
            List<DataField> dataFields = dataTable2Fields.get(dataTableKey);
            transImport.addDetailInfoToLog("[\u63d0\u793a]:\u6b63\u5728\u5bfc\u5165" + dataFields.size() + "\u4e2a\u6307\u6807\uff0c\u5bfc\u51651\u884c\u6570\u636e.");
            List zbKeys = dataFields.stream().map(Basic::getKey).collect(Collectors.toList());
            FieldRelation fieldRelation = fieldRelationFactory.getFieldRelation(zbKeys);
            List metaDataList = fieldRelation.getMetaData();
            if (zbKeys.size() == metaDataList.size()) {
                ArrayList<Object> values = new ArrayList<Object>();
                for (IMetaData iMetaData : metaDataList) {
                    if (!zbCode2Values.containsKey(iMetaData.getCode())) continue;
                    Object value = zbCode2Values.get(iMetaData.getCode());
                    if ("".equals(value)) {
                        value = null;
                    }
                    values.add(value);
                }
                if (dataTable.getBizKeys().length == 2) {
                    values.add(0, dims.get(0).getValue());
                    values.add(0, dims.get(1).getValue());
                }
                if (values.size() != metaDataList.size()) {
                    logger.error("xml\u6570\u636e\u6587\u4ef6\u89e3\u6790\uff1ametaDataList\u4e0evalues\u957f\u5ea6\u4e0d\u4e00\u81f4\uff0cbizKeys\u7684\u503c\u7ec4\u7ec7\u6709\u95ee\u9898");
                    transImport.addErrorInfoToLog("\u6570\u636e\u5165\u5e93\u65f6\uff0c\u6307\u6807\u53ca\u503c\u7684\u957f\u5ea6\u4e0d\u5339\u914d\u3002");
                    throw new RuntimeException();
                }
                try {
                    this.saveExecutor.storageOneData(dims, transImport.getTaskDefine().getKey(), transImport.getFormSchemeKey(), dataTable.getCode(), metaDataList, values);
                    transImport.addDetailInfoToLog("[\u63d0\u793a]:1\u884c\u6570\u636e\u5bfc\u5165\u6210\u529f.");
                }
                catch (Exception e) {
                    this.errorLogInStorage(e, dataTable, dims, transImport);
                }
                continue;
            }
            String zbCodeLoggerInfo = dataFields.stream().map(Basic::getCode).collect(Collectors.joining(","));
            transImport.addErrorInfoToLog(dataTableKey + " \u5b58\u5728\u6ca1\u6709\u67e5\u5230IMetaData\u7684\u5f02\u5e38\u6307\u6807" + zbCodeLoggerInfo);
            logger.error("\u8868{}\u5b58\u5728\u6ca1\u6709\u67e5\u5230IMetaData\u7684\u5f02\u5e38\u6307\u6807{}", (Object)dataTableKey, (Object)zbCodeLoggerInfo);
        }
    }

    private void errorLogInStorage(Exception e, DataTable dataTable, List<DimInfo> dims, TransImportContext importContext) {
        String errorInfo = e.getMessage();
        if (e instanceof CrudException) {
            errorInfo = DataTransUtil.getCrudExceptionMsg(((CrudException)e).getCode(), e.getMessage());
        }
        String msg = String.format("\u7269\u7406\u8868\uff1a%s-%s\uff0c\u5355\u4f4d\uff1a%s\uff0c\u65f6\u671f\uff1a%s, \u9519\u8bef\u4fe1\u606f\uff1a%s", dataTable.getCode(), dataTable.getTitle(), dims.get(1).getValue(), dims.get(0).getValue(), errorInfo);
        importContext.addErrorInfoToLog(msg);
        importContext.addDetailInfoToLog(msg);
    }

    private Map<String, String> getIdx2Code(List<SXElement> zbDefines, Map<String, String> zbMappingKV) {
        HashMap<String, String> idx2Code = new HashMap<String, String>();
        HashSet<String> codeSet = new HashSet<String>();
        for (SXElement zbDefine : zbDefines) {
            String zbCode = zbDefine.getAttribute("name");
            if (codeSet.contains(zbCode = zbCode.toUpperCase())) continue;
            codeSet.add(zbCode);
            int idx = zbDefine.getInt("idx");
            idx2Code.put("idx" + idx, StringUtils.hasLength(zbMappingKV.get(zbCode)) ? zbMappingKV.get(zbCode) : zbCode);
        }
        return idx2Code;
    }

    private Map<String, String> getCode2Idx(Map<String, String> idx2Code) {
        HashMap<String, String> code2Idx = new HashMap<String, String>();
        for (String idx : idx2Code.keySet()) {
            code2Idx.put(idx2Code.get(idx), idx);
        }
        return code2Idx;
    }

    private void parseFixRegionMainBody(SXElement mbElement, Map<String, Object> zb2Value, Map<String, String> zbMappingMap) {
        SXElement zbSetSxelement = this.getChildElement(mbElement, "zbset");
        List<SXElement> zbsets = this.getChildren(zbSetSxelement, "zb");
        Map<String, String> idx2Code = this.getIdx2Code(zbsets, zbMappingMap);
        SXElement rowsElement = this.getChildElement(mbElement, "rows");
        if (rowsElement == null) {
            return;
        }
        SXElement fixRow = this.getChildElement(rowsElement, "row");
        if (fixRow == null) {
            return;
        }
        zb2Value.putAll(this.getFixRowZbCode2Value(idx2Code, fixRow));
    }

    private void parseFloatRegionMainBody(SXElement sxElement, List<TransFloatRowZbValue> zbFloatValues, Map<String, String> zbMappingMap) {
        SXElement zbSetSxelement = this.getChildElement(sxElement, "zbset");
        List<SXElement> zbDefines = this.getChildren(zbSetSxelement, "zb");
        Map<String, String> idx2Code = this.getIdx2Code(zbDefines, zbMappingMap);
        Map<String, String> code2Idx = this.getCode2Idx(idx2Code);
        SXElement rowsElement = this.getChildElement(sxElement, "rows");
        if (rowsElement != null) {
            List<SXElement> floatRows = this.getChildren(rowsElement, "row");
            for (SXElement floatRow : floatRows) {
                zbFloatValues.add(this.getFloatRowZbInfos(idx2Code, code2Idx, floatRow));
            }
        }
    }

    private TransFloatRowZbValue getFloatRowZbInfos(Map<String, String> idx2code, Map<String, String> code2Idx, SXElement rowElement) {
        TransFloatRowZbValue rowZbValue = new TransFloatRowZbValue();
        HashMap<String, TransZbValue> idx2valueInfo = new HashMap<String, TransZbValue>();
        List<SXElement> eleValues = this.getChildren(rowElement, "zbvalue");
        for (SXElement sxElement : eleValues) {
            String zbValue = sxElement.getAttribute("value");
            int zbType = sxElement.getInt("type");
            int idx = sxElement.getInt("idx");
            if (!idx2code.containsKey("idx" + idx)) continue;
            String zbCode = idx2code.get("idx" + idx);
            idx2valueInfo.put("idx" + idx, new TransZbValue(zbCode.toUpperCase(), idx, zbType, zbValue));
        }
        rowZbValue.setCode2Idx(code2Idx);
        rowZbValue.setFloatOrder(rowElement.getFloat("order"));
        rowZbValue.setIdx2valueInfo(idx2valueInfo);
        return rowZbValue;
    }

    private Map<String, Object> getFixRowZbCode2Value(Map<String, String> idx2ZbCode, SXElement rowElement) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        List<SXElement> zbFixValues = this.getChildren(rowElement, "zbvalue");
        HashMap<String, String> idx2ZbValue = new HashMap<String, String>();
        for (SXElement zbValueEle : zbFixValues) {
            String zbValue = zbValueEle.getAttribute("value");
            int idx = zbValueEle.getInt("idx");
            if (idx < 0) continue;
            idx2ZbValue.put("idx" + idx, zbValue);
        }
        for (String idx : idx2ZbCode.keySet()) {
            String zbCode = idx2ZbCode.get(idx);
            if (!idx2ZbValue.containsKey(idx)) continue;
            result.put(zbCode.toUpperCase(), idx2ZbValue.get(idx));
        }
        return result;
    }

    private Map<String, String> getOrgMapping(TransImportContext context) {
        Map<String, String> mappingMap = context.getUnitMappingMap();
        if (mappingMap == null) {
            mappingMap = new HashMap<String, String>();
            context.setUnitMappingMap(mappingMap);
            String mappingSchemeKey = context.getMappingSchemeKey();
            if (StringUtils.hasLength(mappingSchemeKey)) {
                IOrgMappingService orgMappingService = (IOrgMappingService)SpringBeanUtils.getBean(OrgMappingServiceImpl.class);
                List orgMappingByMS = orgMappingService.getOrgMappingByMS(mappingSchemeKey);
                for (OrgMapping pm : orgMappingByMS) {
                    if (!StringUtils.hasLength(pm.getMapping())) continue;
                    mappingMap.put(pm.getMapping(), pm.getCode());
                }
            }
        }
        return mappingMap;
    }

    private Map<String, String> getBaseDataMapping(TransImportContext context) {
        Map<String, Map<String, String>> baseDataMappingMap = context.getBaseDataMappingMap();
        String dw = context.getTaskDefine().getDw();
        if (baseDataMappingMap == null) {
            String mappingSchemeKey = context.getMappingSchemeKey();
            baseDataMappingMap = new HashMap<String, Map<String, String>>();
            context.setBaseDataMappingMap(baseDataMappingMap);
            if (!StringUtils.hasLength(mappingSchemeKey)) {
                return new HashMap<String, String>();
            }
            IBaseDataMappingService baseDataMappingService = (IBaseDataMappingService)SpringBeanUtils.getBean(BaseDataMappingServiceImpl.class);
            List baseDataMapping = baseDataMappingService.getBaseDataMapping(mappingSchemeKey);
            for (BaseDataMapping bs : baseDataMapping) {
                String baseDataCode;
                if (bs == null || !StringUtils.hasLength(baseDataCode = bs.getBaseDataCode()) || !baseDataCode.equals(dw.split("@BASE")[0])) continue;
                HashMap<String, String> bdItemMapping = new HashMap<String, String>();
                List baseDataItemMapping = baseDataMappingService.getBaseDataItem(mappingSchemeKey, baseDataCode);
                for (BaseDataItemMapping bdim : baseDataItemMapping) {
                    bdItemMapping.put(bdim.getMappingCode(), bdim.getBaseDataItemCode());
                }
                baseDataMappingMap.put(dw, bdItemMapping);
                return bdItemMapping;
            }
        } else {
            boolean has = baseDataMappingMap.containsKey(dw);
            if (has) {
                return baseDataMappingMap.get(dw);
            }
        }
        return new HashMap<String, String>();
    }

    private Map<String, String> getPeriodMapping(TransImportContext context) {
        Map<String, String> periodMappingMap = context.getPeriodMappingMap();
        if (periodMappingMap == null) {
            periodMappingMap = new HashMap<String, String>();
            context.setPeriodMappingMap(periodMappingMap);
            if (StringUtils.hasLength(context.getMappingSchemeKey())) {
                PeriodMappingService periodMapping2Service = (PeriodMappingService)SpringBeanUtils.getBean(PeriodMappingService.class);
                List byMS = periodMapping2Service.findByMS(context.getMappingSchemeKey());
                for (PeriodMapping pm : byMS) {
                    if (!StringUtils.hasLength(pm.getMapping())) continue;
                    periodMappingMap.put(pm.getMapping(), pm.getPeriod());
                }
            }
        }
        return periodMappingMap;
    }

    private String getTableNameMapping(TransImportContext context) {
        String dw = context.getTaskDefine().getDw();
        String tableNameMapping = context.getTableNameMapping();
        if (!StringUtils.hasLength(tableNameMapping)) {
            if (StringUtils.hasLength(context.getMappingSchemeKey())) {
                tableNameMapping = this.jqrCustomMapping2Service.getTableNameMapping(context.getMappingSchemeKey());
            }
            if (!StringUtils.hasLength(tableNameMapping)) {
                tableNameMapping = dw.startsWith("MD_") && dw.endsWith("@BASE") ? dw.split("@")[0].substring(3) : dw.split("@")[0];
            }
            context.setTableNameMapping(tableNameMapping);
        }
        return tableNameMapping;
    }

    private String getSolutionNameMapping(TransImportContext context) {
        String solutionNameMapping = context.getSolutionNameMapping();
        if (!StringUtils.hasLength(solutionNameMapping)) {
            solutionNameMapping = this.jqrCustomMapping2Service.getSolutionNameMapping(context.getMappingSchemeKey());
            if (StringUtils.hasLength(solutionNameMapping)) {
                context.setSolutionNameMapping(solutionNameMapping);
            } else {
                solutionNameMapping = context.getTaskDefine().getTaskCode();
                context.setSolutionNameMapping(context.getTaskDefine().getTaskCode());
            }
        }
        return solutionNameMapping;
    }

    private Map<String, String> getZbMappingMap(TransImportContext context) {
        Map<String, String> zbMappingMap = context.getZbMappingMap();
        if (zbMappingMap == null) {
            zbMappingMap = new HashMap<String, String>();
            context.setZbMappingMap(zbMappingMap);
            ZBMappingService zbMappingService = (ZBMappingService)SpringBeanUtils.getBean(ZBMappingService.class);
            List byMS = zbMappingService.findByMS(context.getMappingSchemeKey());
            if (byMS == null || byMS.size() == 0) {
                return zbMappingMap;
            }
            for (ZBMapping zbMapping : byMS) {
                String mapping = zbMapping.getMapping();
                if (!StringUtils.hasLength(mapping) || !StringUtils.hasLength(zbMapping.getZbCode())) continue;
                zbMappingMap.put(mapping, zbMapping.getZbCode());
            }
        }
        return zbMappingMap;
    }

    private SXElement getChildElement(SXElement parent, String childName) {
        Iterable children = parent.getChildren(childName);
        if (children == null) {
            return null;
        }
        Iterator iterator = children.iterator();
        if (iterator.hasNext()) {
            return (SXElement)iterator.next();
        }
        return null;
    }

    private List<SXElement> getChildren(SXElement parent, String childName) {
        ArrayList<SXElement> children = new ArrayList<SXElement>();
        if (parent != null) {
            Iterable ita = parent.getChildren(childName);
            for (SXElement sxElement : ita) {
                children.add(sxElement);
            }
        }
        return children;
    }

    private static class CKDParam {
        public final SXElement e_checkError;
        public final String mappingSchemeKey;
        public final List<FormulaMapping> allFormulaMappings;
        public final List<FormDefine> formDefines;

        public CKDParam(SXElement e_checkError, String mappingSchemeKey, List<FormulaMapping> allFormulaMappings, List<FormDefine> formDefines) {
            this.e_checkError = e_checkError;
            this.mappingSchemeKey = mappingSchemeKey;
            this.allFormulaMappings = allFormulaMappings;
            this.formDefines = formDefines;
        }
    }
}


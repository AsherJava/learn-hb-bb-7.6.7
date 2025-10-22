/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.csvreader.CsvReader;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.checkdes.common.Utils;
import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDImpFilter;
import com.jiuqi.nr.data.checkdes.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.FilterType;
import com.jiuqi.nr.data.checkdes.obj.ImpAsyncPar;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.checkdes.obj.MapHandlePar;
import com.jiuqi.nr.data.checkdes.service.IImportCKDService;
import com.jiuqi.nr.data.checkdes.service.impl.ImpCKDAsyncTaskExecutor;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ImportCKDServiceImpl
implements IImportCKDService {
    private static final Logger logger = LoggerFactory.getLogger(ImportCKDServiceImpl.class);
    @Autowired
    private ICheckErrorDescriptionService ckdService;
    @Autowired
    private CommonUtil util;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;

    @Override
    public String importAsync(CKDImpPar param, CommonParams context) {
        this.util.checkPar(param);
        FormSchemeDefine formScheme = this.util.getFormSchemeDefine(param.getFormSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException(String.format("incorrect formSchemeKey %s!", param.getFormSchemeKey()));
        }
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(formScheme.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(formScheme.getKey());
        ImpAsyncPar impAsyncPar = new ImpAsyncPar();
        impAsyncPar.setCkdImpPar(param);
        impAsyncPar.setParamsMapping(context.getMapping());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)impAsyncPar));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ImpCKDAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_IMP_CKD");
    }

    @Override
    public Message<CKDImpMes> importSync(CKDImpPar param, CommonParams context) {
        CKDImpMes ckdImpMes;
        block4: {
            this.util.checkPar(param);
            this.progress(context.getMonitor(), 0.05, null);
            ckdImpMes = new CKDImpMes();
            ArrayList<CKDImpDetails> ckdImpDetails = new ArrayList<CKDImpDetails>();
            ckdImpMes.setDetail(ckdImpDetails);
            try {
                List<CKDTransObj> impData = this.getCKDTransObjs(param, context.getMapping(), ckdImpDetails);
                this.dataFilter(impData, context.getFilter());
                List<CKDImpFilter> filters = this.getFilters(param);
                this.progress(context.getMonitor(), 0.3, null);
                DimensionAccessFormInfo dimensionAccessFormInfo = this.util.getDimensionAccessFormInfo(param, AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
                Map<String, Set<DimensionValueSet>> accessFormDim = this.util.mapAccessFormDim(dimensionAccessFormInfo);
                this.progress(context.getMonitor(), 0.5, null);
                ArrayList<CheckDesObj> effectiveData = new ArrayList<CheckDesObj>();
                InfoCollection infoCollection = new InfoCollection();
                for (CKDTransObj impDatum : impData) {
                    this.progress(context.getMonitor(), 0.5 + 0.3 / (double)impData.size(), null);
                    String formKey = impDatum.getFormKey();
                    DimensionValueSet dimensionValueSet = impDatum.getDimensionValueSet();
                    if (formKey == null || "00000000-0000-0000-0000-000000000000".equals(formKey) || accessFormDim.containsKey(formKey) && accessFormDim.get(formKey).contains(dimensionValueSet)) {
                        CKDTransObj filteredData = this.filter(filters, impDatum);
                        CKDTransObj validatedData = param.getValidator().validate(filteredData, ckdImpDetails);
                        if (validatedData == null) continue;
                        infoCollection.getDwSet().add(validatedData.getDimMap().get("MDCODE"));
                        infoCollection.getDimensionValueSets().add(validatedData.getDimensionValueSet());
                        infoCollection.getForms().add(validatedData.getFormKey());
                        infoCollection.getFormulaSchemes().add(validatedData.getFormulaSchemeKey());
                        effectiveData.add(this.util.generateCheckDesObj(validatedData));
                        continue;
                    }
                    CKDImpDetails impDetail = this.util.getImpDetail(impDatum, String.format("\u8be5\u5355\u4f4d%s\u5bf9\u62a5\u8868%s\u65e0\u6570\u636e\u5199\u5165\u6743\u9650", dimensionValueSet, formKey));
                    ckdImpDetails.add(impDetail);
                }
                this.fillImpMes(ckdImpMes, infoCollection, param.getFormSchemeKey());
                CheckDesBatchSaveObj saveObj = this.getCheckDesBatchSaveObj(param, effectiveData);
                this.ckdService.batchSaveFormulaCheckDes(saveObj);
                this.progress(context.getMonitor(), 1.0, ckdImpMes);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                if (context.getMonitor() == null) break block4;
                context.getMonitor().error("\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u5f02\u5e38", (Throwable)e);
            }
        }
        return ckdImpMes;
    }

    private void dataFilter(List<CKDTransObj> impData, Map<String, DimensionCollection> filter) {
        if (!CollectionUtils.isEmpty(filter)) {
            HashMap f = new HashMap();
            for (Map.Entry<String, DimensionCollection> entry : filter.entrySet()) {
                String formKey = entry.getKey();
                DimensionCollection value = entry.getValue();
                if (value == null || value.getDimensionCombinations() == null) continue;
                Set dims = value.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toSet());
                f.put(formKey, dims);
            }
            impData.removeIf(o -> !f.containsKey(o.getFormKey()) || !((Set)f.get(o.getFormKey())).contains(o.getDimensionValueSet()));
        }
    }

    private void fillImpMes(CKDImpMes ckdImpMes, InfoCollection infoCollection, String formSchemeKey) {
        DimensionValueSet dimensionValueSet = this.util.mergeDimensionValueSet(infoCollection.getDimensionValueSets());
        ckdImpMes.setDimensions(this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formSchemeKey));
        ckdImpMes.setSuccessDW(new ArrayList<String>(infoCollection.getDwSet()));
        ckdImpMes.setFormulaSchemeKeys(new ArrayList<String>(infoCollection.getFormulaSchemes()));
        ckdImpMes.setForms(new ArrayList<String>(infoCollection.getForms()));
    }

    private List<CKDImpFilter> getFilters(CKDImpPar param) {
        List<String> formulaKeys;
        List<String> formKeys;
        ArrayList<CKDImpFilter> filters = new ArrayList<CKDImpFilter>();
        DimensionCollection dimensionCollection = param.getDimensionCollection();
        Set dimensionValueSets = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toSet());
        filters.add(new CKDImpFilter(Collections.singleton(dimensionValueSets), FilterType.DIMENSION));
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        if (!CollectionUtils.isEmpty(formulaSchemeKeys)) {
            HashSet<String> strings = new HashSet<String>(formulaSchemeKeys);
            filters.add(new CKDImpFilter(Collections.singleton(strings), FilterType.FORMULA_SCHEME));
        }
        if (!CollectionUtils.isEmpty(formKeys = param.getFormKeys())) {
            HashSet<String> strings = new HashSet<String>(formKeys);
            filters.add(new CKDImpFilter(Collections.singleton(strings), FilterType.FORM));
        }
        if (!CollectionUtils.isEmpty(formulaKeys = param.getFormulaKeys())) {
            HashSet<String> strings = new HashSet<String>(formulaKeys);
            filters.add(new CKDImpFilter(Collections.singleton(strings), FilterType.FORMULA));
        }
        return filters;
    }

    private CKDTransObj filter(List<CKDImpFilter> filters, CKDTransObj ckdDetailObj) {
        if (!CollectionUtils.isEmpty(filters)) {
            for (CKDImpFilter filter : filters) {
                boolean r = filter.filter(ckdDetailObj);
                if (r) continue;
                return null;
            }
        }
        return ckdDetailObj;
    }

    private void progress(AsyncTaskMonitor monitor, double progress, Object result) {
        if (monitor != null) {
            monitor.progressAndMessage(progress, "\u6b63\u5728\u6267\u884c\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165");
            if (progress >= 1.0) {
                monitor.finish("\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u5b8c\u6210", result);
            }
        }
    }

    private CheckDesBatchSaveObj getCheckDesBatchSaveObj(CKDImpPar param, List<CheckDesObj> data) {
        CheckDesBatchSaveObj saveObj = new CheckDesBatchSaveObj();
        saveObj.setCheckDesObjs(data);
        CheckDesQueryParam queryParam = new CheckDesQueryParam();
        queryParam.setDimensionCollection(param.getDimensionCollection());
        queryParam.setFormulaSchemeKey(param.getFormulaSchemeKeys());
        saveObj.setCheckDesQueryParam(queryParam);
        return saveObj;
    }

    private List<CKDTransObj> getCKDTransObjs(CKDImpPar param, ParamsMapping paramsMapping, List<CKDImpDetails> ckdImpDetails) {
        ArrayList<CKDTransObj> impData = new ArrayList<CKDTransObj>();
        MapHandlePar mapHandlePar = null;
        if (paramsMapping != null) {
            FormSchemeDefine formSchemeDefine = this.util.getFormSchemeDefine(param.getFormSchemeKey());
            mapHandlePar = new MapHandlePar(paramsMapping, formSchemeDefine, ckdImpDetails);
        }
        String normalizedFilePath = FilenameUtils.normalize(Utils.getFilePathWithName(param.getFilePath()));
        Path csvPath = Paths.get(normalizedFilePath, new String[0]);
        try (CsvReader csvReader = null;){
            csvReader = new CsvReader(Files.newInputStream(csvPath, new OpenOption[0]), StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                CKDTransObj ckdTransObj = this.getCKDTransObj(headers, csvReader);
                if (mapHandlePar != null && (ckdTransObj = this.util.handleMapping(mapHandlePar, ckdTransObj)) == null) continue;
                impData.add(ckdTransObj);
            }
        }
        return impData;
    }

    private CKDTransObj getCKDTransObj(String[] headers, CsvReader csvReader) {
        CKDTransObj desEntity = new CKDTransObj();
        block34: for (String header : headers) {
            String colValue = "";
            try {
                colValue = csvReader.get(header);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            switch (header) {
                case "FMLSCHEMETITLE": {
                    desEntity.setFormulaSchemeTitle(colValue);
                    continue block34;
                }
                case "FMLSCHEMEKEY": {
                    desEntity.setFormulaSchemeKey(colValue);
                    continue block34;
                }
                case "FORMCODE": {
                    desEntity.setFormCode(colValue);
                    continue block34;
                }
                case "FORMKEY": {
                    desEntity.setFormKey(colValue);
                    continue block34;
                }
                case "FORMULACODE": {
                    desEntity.setFormulaCode(colValue);
                    continue block34;
                }
                case "FMLEXPKEY": {
                    desEntity.setFormulaExpressionKey(colValue);
                    continue block34;
                }
                case "GLOBROW": {
                    desEntity.setGlobRow(colValue);
                    continue block34;
                }
                case "GLOBCOL": {
                    desEntity.setGlobCol(colValue);
                    continue block34;
                }
                case "DIMSTR": {
                    desEntity.setDimStr(colValue);
                    continue block34;
                }
                case "USERID": {
                    desEntity.setUserId(colValue);
                    continue block34;
                }
                case "USERNICKNAME": {
                    desEntity.setUserNickName(colValue);
                    continue block34;
                }
                case "UPDATETIME": {
                    try {
                        desEntity.setUpdateTime(Long.parseLong(colValue));
                    }
                    catch (NumberFormatException e) {
                        logger.error(e.getMessage(), e);
                    }
                    continue block34;
                }
                case "CONTENT": {
                    desEntity.setDescription(colValue);
                    continue block34;
                }
                default: {
                    desEntity.getDimMap().put(header, colValue);
                }
            }
        }
        return desEntity;
    }
}


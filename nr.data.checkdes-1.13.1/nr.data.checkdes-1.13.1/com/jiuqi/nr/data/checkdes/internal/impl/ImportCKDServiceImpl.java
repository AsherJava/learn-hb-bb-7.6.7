/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.data.checkdes.internal.impl;

import com.csvreader.CsvReader;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.checkdes.api.ICKDImpService;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.api.IMonitor;
import com.jiuqi.nr.data.checkdes.common.Utils;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailType;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpSuccessInfo;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.helper.Helper;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.io.CsvHeaders;
import com.jiuqi.nr.data.checkdes.internal.util.IOUtils;
import com.jiuqi.nr.data.checkdes.internal.util.MonitorUtils;
import com.jiuqi.nr.data.checkdes.obj.CKDImpFilter;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.FilterType;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
implements ICKDImpService {
    private static final Logger logger = LoggerFactory.getLogger(ImportCKDServiceImpl.class);
    @Autowired
    private ICheckErrorDescriptionService ckdService;
    @Autowired
    private CommonUtil util;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private Helper helper;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IProviderStore providerStore;

    @Override
    public Message<CKDImpMes> importSync(CKDImpPar param) throws CKDIOException {
        IMonitor monitor = param.getMonitor();
        MonitorUtils.process(monitor, 0.01, "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u5f00\u59cb\u6267\u884c");
        this.util.checkPar(param);
        ImpContext impContext = new ImpContext(this.util, param, this.providerStore);
        try {
            MonitorUtils.process(monitor, 0.1, "\u6b63\u5728\u8bfb\u53d6\u6587\u4ef6\u6570\u636e\u5e76\u5904\u7406\u53c2\u6570\u6620\u5c04");
            List<CKDExpEntity> impData = this.getMappedData(impContext);
            MonitorUtils.process(monitor, 0.4, "\u6587\u4ef6\u6570\u636e\u8bfb\u53d6\u5b8c\u6210\uff0c\u6b63\u5728\u8fdb\u884c\u6570\u636e\u4fdd\u5b58\u524d\u5904\u7406");
            if (CollectionUtils.isEmpty(impData) || !this.helper.validDimsSame(impContext, impData)) {
                MonitorUtils.process(monitor, 1.0, "\u6587\u4ef6\u6570\u636e\u4e3a\u7a7a\u6216\u53c2\u6570\u4e0d\u5339\u914d\uff0c\u672a\u5bfc\u5165\u6570\u636e");
                return impContext.getCkdImpMes();
            }
            List<CKDImpFilter> filters = this.getFilters(param);
            IDataAccessService dataAccessService = this.helper.getDataAccessService(impContext);
            ArrayList<CheckDesObj> effectiveData = new ArrayList<CheckDesObj>();
            InfoCollection infoCollection = new InfoCollection();
            String dwDimName = impContext.getDimName(impContext.getFormSchemeDefine().getDw());
            for (CKDExpEntity impDatum : impData) {
                CKDTransObj filteredData;
                CKDTransObj ckdTransObj = this.helper.getValidCKDTransObj(impContext, impDatum);
                if (ckdTransObj == null || (filteredData = this.filter(filters, ckdTransObj, impContext)) == null) continue;
                String formKey = ckdTransObj.getFormKey();
                DimensionValueSet dimensionValueSet = ckdTransObj.getDimensionValueSet();
                DimensionCombination combination = new DimensionCombinationBuilder(dimensionValueSet).getCombination();
                if (this.formAccess(impContext, dataAccessService, combination, formKey)) {
                    CKDTransObj validatedData = param.getCkdImpValidator().validate(filteredData, impContext.getCkdImpMes());
                    if (validatedData == null) continue;
                    infoCollection.getDwSet().add(validatedData.getDimMap().get(dwDimName));
                    infoCollection.getDimensionValueSets().add(validatedData.getDimensionValueSet());
                    infoCollection.getForms().add(validatedData.getFormKey());
                    infoCollection.getFormulaSchemes().add(validatedData.getFormulaSchemeKey());
                    effectiveData.add(this.util.generateCheckDesObj(validatedData));
                    continue;
                }
                ImpFailedInfo failedInfo = this.helper.getImpFailedInfo(filteredData, String.format("\u8be5\u5355\u4f4d%s\u5bf9\u62a5\u8868%s\u65e0\u6570\u636e\u5199\u5165\u6743\u9650", dimensionValueSet, formKey), ImpFailType.NO_ACCESS);
                impContext.getCkdImpMes().getFailedInfos().add(failedInfo);
            }
            MonitorUtils.process(monitor, 0.7, "\u6570\u636e\u5904\u7406\u5b8c\u6210\uff0c\u6b63\u5728\u6267\u884c\u6570\u636e\u4fdd\u5b58");
            CheckDesBatchSaveObj saveObj = this.getCheckDesBatchSaveObj(param, effectiveData);
            this.ckdService.batchSaveFormulaCheckDes(saveObj);
            MonitorUtils.process(monitor, 0.95, "\u6570\u636e\u4fdd\u5b58\u5b8c\u6210\uff0c\u6b63\u5728\u751f\u6210\u5bfc\u5165\u4fe1\u606f");
            impContext.getCkdImpMes().setSuccessInfos(saveObj.getCheckDesObjs().stream().map(o -> {
                ImpSuccessInfo impSuccessInfo = new ImpSuccessInfo();
                impSuccessInfo.setRecordId(o.getRecordId());
                impSuccessInfo.setFormulaSchemeKey(o.getFormulaSchemeKey());
                return impSuccessInfo;
            }).collect(Collectors.toList()));
            this.fillImpMes(impContext, infoCollection);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CKDIOException("\u5bfc\u5165\u51fa\u9519\u8bf4\u660e\u5f02\u5e38:" + e.getMessage(), e);
        }
        MonitorUtils.process(monitor, 1.0, "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u5165\u6267\u884c\u5b8c\u6210");
        return impContext.getCkdImpMes();
    }

    private boolean formAccess(ImpContext impContext, IDataAccessService dataAccessService, DimensionCombination combination, String formKey) throws Exception {
        if (formKey == null || "00000000-0000-0000-0000-000000000000".equals(formKey)) {
            for (String fsAllForm : impContext.getFsAllForms()) {
                IAccessResult accessResult = dataAccessService.sysWriteable(combination, fsAllForm);
                if (accessResult == null || !accessResult.haveAccess()) continue;
                return true;
            }
        } else {
            IAccessResult accessResult = dataAccessService.sysWriteable(combination, formKey);
            return accessResult != null && accessResult.haveAccess();
        }
        return false;
    }

    private void fillImpMes(ImpContext context, InfoCollection infoCollection) {
        CKDImpMes ckdImpMes = context.getCkdImpMes();
        if (!CollectionUtils.isEmpty(infoCollection.getDimensionValueSets())) {
            DimensionValueSet dimensionValueSet = this.util.mergeDimensionValueSet(infoCollection.getDimensionValueSets());
            DimensionCollection dimensionCollection = null;
            try {
                FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(context.getFormSchemeDefine().getDw()), dimensionValueSet);
                dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, context.getFormSchemeDefine().getKey(), (SpecificDimBuilder)fixedDimBuilder);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            ckdImpMes.setDimensions(dimensionCollection);
        }
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
        filters.add(new CKDImpFilter(dimensionValueSets, FilterType.DIMENSION));
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        if (!CollectionUtils.isEmpty(formulaSchemeKeys)) {
            HashSet<String> strings = new HashSet<String>(formulaSchemeKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORMULA_SCHEME));
        }
        if (!CollectionUtils.isEmpty(formKeys = param.getFormKeys())) {
            HashSet<String> strings = new HashSet<String>(formKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORM));
        }
        if (!CollectionUtils.isEmpty(formulaKeys = param.getFormulaKeys())) {
            HashSet<String> strings = new HashSet<String>(formulaKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORMULA));
        }
        return filters;
    }

    private CKDTransObj filter(List<CKDImpFilter> filters, CKDTransObj ckdDetailObj, ImpContext impContext) {
        if (!CollectionUtils.isEmpty(filters)) {
            for (CKDImpFilter filter : filters) {
                boolean r = filter.filter(ckdDetailObj);
                if (r) continue;
                List<ImpFailedInfo> failedInfos = impContext.getCkdImpMes().getFailedInfos();
                ImpFailedInfo impFailedInfo = this.helper.getImpFailedInfo(ckdDetailObj, "\u4e0d\u6ee1\u8db3\u8fc7\u6ee4\u6761\u4ef6\uff1a" + filter.getFilterType().getDesc(), ImpFailType.OUT_OF_RANGE);
                failedInfos.add(impFailedInfo);
                return null;
            }
        }
        return ckdDetailObj;
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

    private List<CKDExpEntity> getMappedData(ImpContext context) {
        ArrayList<CKDExpEntity> impData = new ArrayList<CKDExpEntity>();
        CKDImpPar param = context.getImpPar();
        ICKDParamMapping paramMapping = param.getCkdParamMapping();
        CsvReader csvReader = null;
        InputStream inputStream = null;
        try {
            String normalizedFilePath = FilenameUtils.normalize(Utils.getFilePathWithName(param.getFilePath()));
            Path csvPath = Paths.get(normalizedFilePath, new String[0]);
            inputStream = Files.newInputStream(csvPath, new OpenOption[0]);
            csvReader = new CsvReader(inputStream, '\t', StandardCharsets.UTF_8);
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                CKDExpEntity ckdExpEntity = this.read(headers, csvReader, context);
                if (paramMapping != null && (ckdExpEntity = IOUtils.handleMapping(paramMapping, ckdExpEntity)) == null) continue;
                impData.add(ckdExpEntity);
            }
        }
        catch (IOException e) {
            throw new CKDIOException("failed to import CKD data:" + e.getMessage(), e);
        }
        finally {
            if (csvReader != null) {
                csvReader.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return impData;
    }

    private CKDExpEntity read(String[] headers, CsvReader csvReader, ImpContext context) {
        CKDExpEntity ckdExpEntity = new CKDExpEntity();
        ckdExpEntity.setUserId(context.getUserId());
        ckdExpEntity.setUserNickName(context.getUserNickName());
        ckdExpEntity.setUpdateTime(context.getImpDate().getTime());
        for (String header : headers) {
            String colValue;
            try {
                colValue = csvReader.get(header);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            if (CsvHeaders.MD_CODE.getValue().equals(header)) {
                ckdExpEntity.setMdCode(colValue);
                continue;
            }
            if (CsvHeaders.PERIOD.getValue().equals(header)) {
                ckdExpEntity.setPeriod(colValue);
                continue;
            }
            if (CsvHeaders.FORMULA_SCHEME_TITLE.getValue().equals(header)) {
                ckdExpEntity.setFormulaSchemeTitle(colValue);
                continue;
            }
            if (CsvHeaders.FORM_CODE.getValue().equals(header)) {
                ckdExpEntity.setFormCode(colValue);
                continue;
            }
            if (CsvHeaders.FORMULA_CODE.getValue().equals(header)) {
                ckdExpEntity.setFormulaCode(colValue);
                continue;
            }
            if (CsvHeaders.GLOB_ROW.getValue().equals(header)) {
                ckdExpEntity.setGlobRow(Integer.parseInt(colValue));
                continue;
            }
            if (CsvHeaders.GLOB_COL.getValue().equals(header)) {
                ckdExpEntity.setGlobCol(Integer.parseInt(colValue));
                continue;
            }
            if (CsvHeaders.DIM_STR.getValue().equals(header)) {
                ckdExpEntity.setDimStr(colValue);
                continue;
            }
            if (CsvHeaders.CONTENT.getValue().equals(header)) {
                ckdExpEntity.setDescription(colValue);
                continue;
            }
            if (CsvHeaders.USER_ID.getValue().equals(header)) {
                ckdExpEntity.setUserId(colValue);
                continue;
            }
            if (CsvHeaders.USER_NICK_NAME.getValue().equals(header)) {
                ckdExpEntity.setUserNickName(colValue);
                continue;
            }
            if (CsvHeaders.UPDATE_TIME.getValue().equals(header)) {
                try {
                    ckdExpEntity.setUpdateTime(Long.parseLong(colValue));
                }
                catch (NumberFormatException e) {
                    logger.error(e.getMessage(), e);
                }
                continue;
            }
            ckdExpEntity.addDim(CsvHeaders.getEntityIdByHeader(header), colValue);
        }
        return ckdExpEntity;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.data.checkdes.internal.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.checkdes.api.ICKDExportService;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.api.IMonitor;
import com.jiuqi.nr.data.checkdes.common.Constants;
import com.jiuqi.nr.data.checkdes.common.Utils;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.ExpInfo;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ExpContext;
import com.jiuqi.nr.data.checkdes.internal.helper.Helper;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.io.CsvHeaders;
import com.jiuqi.nr.data.checkdes.internal.util.IOUtils;
import com.jiuqi.nr.data.checkdes.internal.util.MonitorUtils;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ExportCKDServiceImpl
implements ICKDExportService {
    @Autowired
    private ICheckErrorDescriptionService ckdService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CommonUtil util;
    @Autowired
    private Helper helper;
    @Autowired
    private IProviderStore providerStore;
    private static final Logger logger = LoggerFactory.getLogger(ExportCKDServiceImpl.class);

    @Override
    public ExpInfo export(CKDExpPar param) throws CKDIOException {
        return this.export(param, Utils.getDefExpFilePath());
    }

    @Override
    public ExpInfo export(CKDExpPar param, String filePath) throws CKDIOException {
        IMonitor monitor = param.getMonitor();
        MonitorUtils.process(monitor, 0.01, "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u5f00\u59cb\u6267\u884c");
        filePath = FilenameUtils.normalize(filePath);
        this.util.checkPar(param);
        MonitorUtils.process(monitor, 0.1, "\u6b63\u5728\u6267\u884c\u6743\u9650\u5224\u65ad");
        DimensionAccessFormInfo dimensionAccessFormInfo = this.util.getDimensionAccessFormInfo(param, AccessLevel.FormAccessLevel.FORM_READ);
        MonitorUtils.process(monitor, 0.4, "\u6743\u9650\u5224\u65ad\u5b8c\u6210\uff0c\u6b63\u5728\u67e5\u8be2\u51fa\u9519\u8bf4\u660e\u6570\u636e");
        List<CheckDesObj> exportData = this.getExportData(param, dimensionAccessFormInfo);
        MonitorUtils.process(monitor, 0.7, "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u67e5\u8be2\u5b8c\u6210\uff0c\u6b63\u5728\u521b\u5efa\u6587\u4ef6");
        String filePathWithName = Utils.getFilePathWithName(filePath);
        Path csvPath = Paths.get(filePathWithName, new String[0]);
        try {
            Files.createDirectories(csvPath.getParent(), new FileAttribute[0]);
        }
        catch (IOException e) {
            throw new CKDIOException("failed to create file directories before exporting CKD data:" + filePathWithName + "\u2014\u2014" + e.getMessage(), e);
        }
        MonitorUtils.process(monitor, 0.72, "\u6587\u4ef6\u521b\u5efa\u5b8c\u6210\uff0c\u6b63\u5728\u8fdb\u884c\u6570\u636e\u5199\u5165\u524d\u51c6\u5907\u5de5\u4f5c");
        CsvWriter csvWriter = null;
        OutputStream outputStream = null;
        ExpContext expContext = new ExpContext(this.util, param, this.providerStore);
        try {
            outputStream = Files.newOutputStream(csvPath, new OpenOption[0]);
            csvWriter = new CsvWriter(outputStream, '\t', StandardCharsets.UTF_8);
            MonitorUtils.process(monitor, 0.74, "\u6570\u636e\u5199\u5165\u524d\u51c6\u5907\u5de5\u4f5c\u5b8c\u6210\uff0c\u6b63\u5728\u5199\u5165\u6570\u636e");
            InfoCollection infoCollection = this.writeData(csvWriter, exportData, expContext);
            MonitorUtils.process(monitor, 0.99, "\u6570\u636e\u5199\u5165\u6587\u4ef6\u5b8c\u6210");
            this.writeDes(infoCollection, filePath + Constants.FILE_PATH_SEP);
        }
        catch (IOException e) {
            throw new CKDIOException("failed to export CKD data:" + e.getMessage(), e);
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        MonitorUtils.process(monitor, 1.0, "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u6267\u884c\u5b8c\u6210");
        return new ExpInfo(filePath);
    }

    private List<CheckDesObj> getExportData(CKDExpPar param, DimensionAccessFormInfo dimensionAccessFormInfo) {
        ArrayList<CheckDesObj> exportData = new ArrayList<CheckDesObj>();
        List<String> formulaKeys = param.getFormulaKeys();
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        List<IParsedExpression> parsedExpression = this.helper.getParsedExpression(param.getFormSchemeKey(), formulaSchemeKeys, formulaKeys);
        boolean containsBJ = param.getFormKeys() == null || param.getFormKeys().contains("00000000-0000-0000-0000-000000000000");
        for (DimensionAccessFormInfo.AccessFormInfo accessForm : dimensionAccessFormInfo.getAccessForms()) {
            Map dimensions = accessForm.getDimensions();
            ArrayList<String> formKeys = accessForm.getFormKeys();
            CheckDesQueryParam queryParam = new CheckDesQueryParam();
            queryParam.setDimensionCollection(this.dimensionCollectionUtil.getDimensionCollection(dimensions, param.getFormSchemeKey()));
            queryParam.setFormSchemeKey(param.getFormSchemeKey());
            queryParam.setFormulaSchemeKey(formulaSchemeKeys);
            if (containsBJ) {
                formKeys = new ArrayList<String>(formKeys);
                formKeys.add("00000000-0000-0000-0000-000000000000");
            }
            queryParam.setFormKey((List)formKeys);
            queryParam.setFormulaKey(parsedExpression.stream().map(IParsedExpression::getKey).collect(Collectors.toList()));
            List checkDesObjs = this.ckdService.queryFormulaCheckDes(queryParam);
            if (CollectionUtils.isEmpty(checkDesObjs)) continue;
            exportData.addAll(checkDesObjs);
        }
        return exportData;
    }

    private InfoCollection writeData(CsvWriter csvWriter, List<CheckDesObj> exportData, ExpContext expContext) throws IOException {
        InfoCollection infoCollection = new InfoCollection();
        CKDExpPar ckdExpPar = expContext.getCkdExpPar();
        List<String> allDimEntityIds = this.helper.getSchemeDimEntityIds(expContext.getFormSchemeDefine());
        boolean expUserTime = ckdExpPar.isExpUserTime();
        this.writeHeaders(csvWriter, allDimEntityIds, expUserTime);
        IMonitor monitor = ckdExpPar.getMonitor();
        MonitorUtils.process(monitor, 0.75, "\u6587\u4ef6\u5934\u5199\u5165\u5b8c\u6210\uff0c\u6b63\u5728\u5199\u5165\u6570\u636e\u884c");
        ICKDParamMapping paramMapping = ckdExpPar.getCkdParamMapping();
        for (CheckDesObj exportDatum : exportData) {
            CKDExpEntity ckdExpEntity = this.helper.transCKDExpEntity(expContext, exportDatum);
            if (ckdExpEntity == null) continue;
            this.fillInfoCollection(ckdExpEntity, exportDatum, infoCollection);
            if (paramMapping != null) {
                ckdExpEntity = IOUtils.handleMapping(paramMapping, ckdExpEntity);
            }
            ArrayList<String> rowData = new ArrayList<String>();
            rowData.add(ckdExpEntity.getMdCode());
            rowData.add(ckdExpEntity.getPeriod());
            rowData.add(ckdExpEntity.getFormulaSchemeTitle());
            rowData.add(ckdExpEntity.getFormCode());
            rowData.add(ckdExpEntity.getFormulaCode());
            rowData.add(String.valueOf(ckdExpEntity.getGlobRow()));
            rowData.add(String.valueOf(ckdExpEntity.getGlobCol()));
            rowData.add(ckdExpEntity.getDimStr());
            rowData.add(ckdExpEntity.getDescription());
            if (expUserTime) {
                rowData.add(ckdExpEntity.getUserId());
                rowData.add(ckdExpEntity.getUserNickName());
                rowData.add(String.valueOf(ckdExpEntity.getUpdateTime()));
            }
            for (String entityId : allDimEntityIds) {
                String dimValue = ckdExpEntity.getDims().get(entityId);
                rowData.add(dimValue);
            }
            csvWriter.writeRecord(rowData.toArray(new String[0]));
        }
        return infoCollection;
    }

    private void fillInfoCollection(CKDExpEntity ckdExpEntity, CheckDesObj checkDesObj, InfoCollection infoCollection) {
        infoCollection.getFormulaSchemes().add(ckdExpEntity.getFormulaSchemeTitle());
        infoCollection.getForms().add(ckdExpEntity.getFormCode());
        infoCollection.getDimensionValueSets().add(checkDesObj.getDimensionValueSet());
    }

    private void writeHeaders(CsvWriter csvWriter, List<String> dimEntityIds, boolean expUserTime) throws IOException {
        ArrayList<String> headers = new ArrayList<String>();
        headers.add(CsvHeaders.MD_CODE.getValue());
        headers.add(CsvHeaders.PERIOD.getValue());
        headers.add(CsvHeaders.FORMULA_SCHEME_TITLE.getValue());
        headers.add(CsvHeaders.FORM_CODE.getValue());
        headers.add(CsvHeaders.FORMULA_CODE.getValue());
        headers.add(CsvHeaders.GLOB_ROW.getValue());
        headers.add(CsvHeaders.GLOB_COL.getValue());
        headers.add(CsvHeaders.DIM_STR.getValue());
        headers.add(CsvHeaders.CONTENT.getValue());
        if (expUserTime) {
            headers.add(CsvHeaders.USER_ID.getValue());
            headers.add(CsvHeaders.USER_NICK_NAME.getValue());
            headers.add(CsvHeaders.UPDATE_TIME.getValue());
        }
        if (!CollectionUtils.isEmpty(dimEntityIds)) {
            dimEntityIds.forEach(o -> headers.add(CsvHeaders.getEntityHeader(o)));
        }
        csvWriter.writeRecord(headers.toArray(new String[0]), false);
    }

    private void writeDes(InfoCollection infoCollection, String filePath) throws IOException {
        DimensionValueSet dimensionValueSet = this.util.mergeDimensionValueSet(infoCollection.getDimensionValueSets());
        HashMap<String, String> extraAttributes = new HashMap<String, String>();
        extraAttributes.put("formulaScheme", infoCollection.getFormulaSchemes().toString());
        DataCommonUtils.writeVersionInfoExtra((DimensionValueSet)dimensionValueSet, new ArrayList<String>(infoCollection.getForms()), (String)filePath, extraAttributes);
    }
}


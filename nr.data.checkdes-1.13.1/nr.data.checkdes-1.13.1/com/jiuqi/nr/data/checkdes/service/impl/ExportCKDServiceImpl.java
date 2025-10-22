/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.common.service.QueryMappings
 *  com.jiuqi.nr.data.common.utils.DataCommonUtils
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 */
package com.jiuqi.nr.data.checkdes.service.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.checkdes.common.Constants;
import com.jiuqi.nr.data.checkdes.common.Utils;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.checkdes.service.IExportCKDService;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.service.QueryMappings;
import com.jiuqi.nr.data.common.utils.DataCommonUtils;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import java.io.IOException;
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
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ExportCKDServiceImpl
implements IExportCKDService {
    @Autowired
    private ICheckErrorDescriptionService ckdService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CommonUtil util;
    @Autowired(required=false)
    private QueryMappings queryMappings;

    @Override
    public String export(CKDExpPar param) {
        return this.export(param, Utils.getDefExpFilePath());
    }

    @Override
    public String export(CKDExpPar param, String filePath) {
        filePath = FilenameUtils.normalize(filePath);
        this.util.checkPar(param);
        DimensionAccessFormInfo dimensionAccessFormInfo = this.util.getDimensionAccessFormInfo(param, AccessLevel.FormAccessLevel.FORM_READ);
        List<CheckDesObj> exportData = this.getExportData(param, dimensionAccessFormInfo);
        Path csvPath = Paths.get(Utils.getFilePathWithName(filePath), new String[0]);
        try {
            Files.createDirectories(csvPath.getParent(), new FileAttribute[0]);
        }
        catch (IOException e) {
            throw new RuntimeException("failed to create file directories before exporting CKD data:" + e.getMessage(), e);
        }
        CsvWriter csvWriter = null;
        try {
            csvWriter = new CsvWriter(Files.newOutputStream(csvPath, new OpenOption[0]), '\t', StandardCharsets.UTF_8);
            InfoCollection infoCollection = this.writeData(csvWriter, exportData, param);
            this.writeDes(infoCollection, filePath + Constants.FILE_PATH_SEP);
            DataCommonUtils.buildMappings((String)param.getFormSchemeKey(), (String)filePath, (QueryMappings)this.queryMappings);
        }
        catch (IOException e) {
            throw new RuntimeException("failed to export CKD data:" + e.getMessage(), e);
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
        return filePath;
    }

    private List<CheckDesObj> getExportData(CKDExpPar param, DimensionAccessFormInfo dimensionAccessFormInfo) {
        ArrayList<CheckDesObj> exportData = new ArrayList<CheckDesObj>();
        for (DimensionAccessFormInfo.AccessFormInfo accessForm : dimensionAccessFormInfo.getAccessForms()) {
            Map dimensions = accessForm.getDimensions();
            List formKeys = accessForm.getFormKeys();
            CheckDesQueryParam queryParam = new CheckDesQueryParam();
            queryParam.setDimensionCollection(this.dimensionCollectionUtil.getDimensionCollection(dimensions, param.getFormSchemeKey()));
            queryParam.setFormulaSchemeKey(param.getFormulaSchemeKeys());
            queryParam.setFormKey(formKeys);
            queryParam.setFormulaKey(param.getFormulaKeys());
            List checkDesObjs = this.ckdService.queryFormulaCheckDes(queryParam);
            if (CollectionUtils.isEmpty(checkDesObjs)) continue;
            exportData.addAll(checkDesObjs);
        }
        return exportData;
    }

    private InfoCollection writeData(CsvWriter csvWriter, List<CheckDesObj> exportData, CKDExpPar param) throws IOException {
        InfoCollection infoCollection = new InfoCollection();
        List<String> allDimNames = this.util.getFormSchemeEntityNames(param.getFormSchemeKey());
        this.writeHeaders(csvWriter, allDimNames);
        for (CheckDesObj exportDatum : exportData) {
            CKDTransObj ckdTransObj = this.util.getCKDTransObj(exportDatum);
            this.fillInfoCollection(ckdTransObj, infoCollection);
            ArrayList<String> rowData = new ArrayList<String>();
            for (String dimEntityName : allDimNames) {
                rowData.add(exportDatum.getDimensionValueSet().getValue(dimEntityName).toString());
            }
            rowData.add(ckdTransObj.getFormulaSchemeTitle());
            rowData.add(ckdTransObj.getFormulaSchemeKey());
            rowData.add(ckdTransObj.getFormCode());
            rowData.add(ckdTransObj.getFormKey());
            rowData.add(ckdTransObj.getFormulaCode());
            rowData.add(ckdTransObj.getFormulaExpressionKey());
            rowData.add(ckdTransObj.getGlobRow());
            rowData.add(ckdTransObj.getGlobCol());
            rowData.add(ckdTransObj.getDimStr());
            rowData.add(ckdTransObj.getUserId());
            rowData.add(ckdTransObj.getUserNickName());
            rowData.add(String.valueOf(ckdTransObj.getUpdateTime()));
            rowData.add(ckdTransObj.getDescription());
            csvWriter.writeRecord(rowData.toArray(new String[0]));
        }
        return infoCollection;
    }

    private void fillInfoCollection(CKDTransObj ckdTransObj, InfoCollection infoCollection) {
        infoCollection.getFormulaSchemes().add(ckdTransObj.getFormulaSchemeTitle());
        infoCollection.getForms().add(ckdTransObj.getFormCode());
        infoCollection.getDimensionValueSets().add(ckdTransObj.getDimensionValueSet());
    }

    private void writeHeaders(CsvWriter csvWriter, List<String> dimEntityNames) throws IOException {
        ArrayList<String> headers = new ArrayList<String>(dimEntityNames);
        headers.add("FMLSCHEMETITLE");
        headers.add("FMLSCHEMEKEY");
        headers.add("FORMCODE");
        headers.add("FORMKEY");
        headers.add("FORMULACODE");
        headers.add("FMLEXPKEY");
        headers.add("GLOBROW");
        headers.add("GLOBCOL");
        headers.add("DIMSTR");
        headers.add("USERID");
        headers.add("USERNICKNAME");
        headers.add("UPDATETIME");
        headers.add("CONTENT");
        csvWriter.writeRecord(headers.toArray(new String[0]), false);
    }

    private void writeDes(InfoCollection infoCollection, String filePath) throws IOException {
        DimensionValueSet dimensionValueSet = this.util.mergeDimensionValueSet(infoCollection.getDimensionValueSets());
        HashMap<String, String> extraAttributes = new HashMap<String, String>();
        extraAttributes.put("formulaScheme", infoCollection.getFormulaSchemes().toString());
        DataCommonUtils.writeVersionInfoExtra((DimensionValueSet)dimensionValueSet, new ArrayList<String>(infoCollection.getForms()), (String)filePath, extraAttributes);
    }
}


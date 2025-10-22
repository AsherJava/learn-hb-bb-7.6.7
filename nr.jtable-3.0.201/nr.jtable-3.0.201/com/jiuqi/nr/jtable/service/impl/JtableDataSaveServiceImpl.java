/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.datacrud.ClearInfoBuilder
 *  com.jiuqi.nr.datacrud.IClearInfo
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveData
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveReturnRes
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.datacrud.ClearInfoBuilder;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.FloatOrderStructure;
import com.jiuqi.nr.jtable.params.input.FloatRegionSaveStructure;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.output.FMDMCheckFailNodeInfoExtend;
import com.jiuqi.nr.jtable.params.output.FormAccessResult;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.service.IAfterSaveAction;
import com.jiuqi.nr.jtable.service.IFormAuthorityServive;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableDataQueryService;
import com.jiuqi.nr.jtable.service.IJtableDataSaveService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DateUtils;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FloatOrderCalc;
import com.jiuqi.nr.jtable.util.JLoggerUtils;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JtableDataSaveServiceImpl
implements IJtableDataSaveService {
    private static final Logger logger = LoggerFactory.getLogger(JtableDataSaveServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IDataService dataService;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;
    @Autowired
    private IJtableDataQueryService jtableDataQueryService;
    @Autowired(required=false)
    private IAfterSaveAction afterSaveAction;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private List<IFormAuthorityServive> iFormAuthorityServives;
    @Autowired
    private JLoggerUtils jLoggerUtils;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public SaveResult saveReportFormDatas(ReportDataCommitSet reportDataCommitSet) {
        FormAccessResult canWrite;
        SaveResult result = new SaveResult();
        JtableContext jtableContext = reportDataCommitSet.getContext();
        IFormAuthorityServive formAuthorityServive = this.getFormAuthorityServive(jtableContext.getAccessCode());
        if (formAuthorityServive != null && !(canWrite = formAuthorityServive.canWrite(jtableContext)).isHaveAccess()) {
            SaveErrorDataInfo error = this.collectSystemError(canWrite.getMessage());
            result.getErrors().add(error);
            result.setMessage("failed");
            return result;
        }
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        for (RegionData region : regions) {
            if (!commitData.containsKey(region.getKey())) continue;
            SaveResult regionResult = new SaveResult();
            RegionDataCommitSet regionDataCommitSet = commitData.get(region.getKey());
            regionDataCommitSet.setContext(jtableContext);
            regionDataCommitSet.setRegionKey(region.getKey());
            regionResult = region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() ? this.saveFloatRegionDatas(regionDataCommitSet, region) : this.saveFixRegionDatas(regionDataCommitSet, region);
            if (regionResult == null) continue;
            result.getErrors().addAll(regionResult.getErrors());
            result.getRowKeyMap().putAll(regionResult.getRowKeyMap());
            result.getFloatOrderMap().putAll(regionResult.getFloatOrderMap());
            if (regionResult.getMessage() == null) continue;
            if (regionResult.getMessage().equals("NO_DATA") && result.getMessage() != null && result.getMessage().equals("NO_DATA")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (regionResult.getMessage().equals("HAVE_DATA") && result.getMessage() != null && !result.getMessage().equals("failed")) {
                result.setMessage(regionResult.getMessage());
                continue;
            }
            if (result.getMessage() != null) continue;
            result.setMessage(regionResult.getMessage());
        }
        if (result.getMessage() != null && result.getMessage().equals("NO_DATA")) {
            result.setMessage("noData");
        } else if (result.getErrors().isEmpty()) {
            result.setMessage("success");
            this.jLoggerUtils.logDeleteFloatRowInfo(jtableContext, commitData);
            if (this.afterSaveAction != null) {
                this.afterSaveAction.afterDeleteSurvey(jtableContext, commitData);
            }
        } else {
            result.setMessage("failed");
            throw new SaveDataException(result);
        }
        return result;
    }

    @Override
    public SaveResult saveFMDMDatas(ReportDataCommitSet reportDataCommitSet) {
        boolean systemError;
        FormAccessResult canWrite;
        SaveResult result = new SaveResult();
        JtableContext jtableContext = reportDataCommitSet.getContext();
        IFormAuthorityServive formAuthorityServive = this.getFormAuthorityServive(jtableContext.getAccessCode());
        if (formAuthorityServive != null && !reportDataCommitSet.isUnitAdd() && !(canWrite = formAuthorityServive.canWrite(jtableContext)).isHaveAccess()) {
            SaveErrorDataInfo error = this.collectSystemError(canWrite.getMessage());
            result.getErrors().add(error);
            result.setMessage("failed");
            return result;
        }
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        result = this.saveFMDMFormDatas(reportDataCommitSet, regions.get(0));
        boolean fmdmError = result.getfMDMCheckFailNodeInfoExtend() != null && result.getfMDMCheckFailNodeInfoExtend().size() > 0;
        boolean bl = systemError = result.getErrors() != null && result.getErrors().size() > 0;
        if (fmdmError || systemError) {
            result.setMessage("failed");
        } else if (StringUtils.isNotEmpty((String)result.getMessage()) && !result.getMessage().equals("NO_DATA")) {
            result.setMessage("HAVE_DATA");
        }
        if (result.getMessage() != null && result.getMessage().equals("NO_DATA")) {
            result.setMessage("noData");
        } else {
            if (StringUtils.isNotEmpty((String)result.getMessage()) && result.getMessage().equals("failed")) {
                throw new SaveDataException(result);
            }
            result.setMessage("success");
        }
        return result;
    }

    public SaveResult saveFMDMFormDatas(ReportDataCommitSet reportDataCommitSet, RegionData region) {
        SaveErrorDataInfo saveErrorDataInfo;
        ReturnRes addRowReturnRes;
        SaveResult result = new SaveResult();
        JtableContext context = reportDataCommitSet.getContext();
        String regionKey = region.getKey();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            return null;
        }
        Map<String, RegionDataCommitSet> commitData = reportDataCommitSet.getCommitData();
        RegionDataCommitSet regionDataCommitSet = commitData.get(regionKey);
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(regionKey);
        if (linkKeys == null || linkKeys.isEmpty()) {
            result.setMessage("NO_DATA");
            return result;
        }
        List<List<Object>> datas = regionDataCommitSet.getData().get(0);
        if (regionDataCommitSet.getData() == null || regionDataCommitSet.getData().size() == 0) {
            result.setMessage("NO_DATA");
            return result;
        }
        regionDataCommitSet.setContext(context);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        int fmdmState = 0;
        if (reportDataCommitSet.isUnitAdd()) {
            fmdmState = 1;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dwDimensionName = dwEntity.getDimensionName();
            dimensionValueSet.clearValue(dwDimensionName);
            dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        } else {
            dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        }
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        result.setMessage("HAVE_DATA");
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            String linkKey = linkKeys.get(i);
            int index = saveDataBuilder.addLink(linkKey);
            linkIndexs.add(index);
        }
        Map<String, Object> fmdmAttributes = reportDataCommitSet.getFmdmAttributes();
        HashMap<Integer, Object> fmdmValueIndexs = new HashMap<Integer, Object>();
        if (fmdmAttributes != null && fmdmAttributes.size() > 0) {
            for (String code : fmdmAttributes.keySet()) {
                Object fmdmValue = fmdmAttributes.get(code);
                int codeIndex = saveDataBuilder.addLink(code);
                fmdmValueIndexs.put(codeIndex, fmdmValue);
            }
        }
        if ((addRowReturnRes = saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), fmdmState)).getCode() != 0 && (saveErrorDataInfo = this.collectSystemError(addRowReturnRes.getMessage())) != null) {
            result.getErrors().add(saveErrorDataInfo);
            return result;
        }
        ArrayList<FMDMCheckFailNodeInfoExtend> fmdmFailNodeInfos = new ArrayList<FMDMCheckFailNodeInfoExtend>();
        for (int i = 0; i < datas.size(); ++i) {
            ReturnRes setReturnRes;
            Object data = datas.get(i).get(1);
            if (data != null && data.toString().equals("")) {
                data = null;
            }
            if ((setReturnRes = saveDataBuilder.setData(((Integer)linkIndexs.get(i)).intValue(), data)).getCode() == 0) continue;
            FMDMCheckFailNodeInfoExtend fmdmFailNodeInfo = this.collectFmdmCellError(linkKeys.get(i), setReturnRes);
            fmdmFailNodeInfos.add(fmdmFailNodeInfo);
        }
        if (fmdmValueIndexs != null && fmdmValueIndexs.size() > 0) {
            for (Integer codeIndex : fmdmValueIndexs.keySet()) {
                Object fmdmValue = fmdmValueIndexs.get(codeIndex);
                if (fmdmValue != null && fmdmValue.toString().equals("")) {
                    fmdmValue = null;
                }
                saveDataBuilder.setData(codeIndex.intValue(), fmdmValue);
            }
        }
        if (fmdmFailNodeInfos != null && fmdmFailNodeInfos.size() > 0) {
            result.setfMDMCheckFailNodeInfoExtend(fmdmFailNodeInfos);
            return result;
        }
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            SaveReturnRes saveReturnRes = this.dataService.saveRegionData(saveInfo);
            int code = saveReturnRes.getCode();
            if (code != 0) {
                List saveErrors = saveReturnRes.getSaveResItems();
                List<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = this.collectFmdmCellErrors(saveErrors, regionKey);
                result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfoExtends);
            }
            result.setUnitCode(saveReturnRes.getData());
        }
        catch (CrudSaveException e) {
            List items = e.getItems();
            List<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = this.collectFmdmCellErrors(items, regionKey);
            result.setfMDMCheckFailNodeInfoExtend(fMDMCheckFailNodeInfoExtends);
        }
        catch (CrudOperateException e) {
            SaveErrorDataInfo saveError = this.collectSystemError(e.getMessage());
            result.getErrors().add(saveError);
        }
        return result;
    }

    private SaveResult saveFixRegionDatas(RegionDataCommitSet regionDataCommitSet, RegionData region) {
        int i;
        SaveResult result = new SaveResult();
        String regionKey = region.getKey();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            return result;
        }
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(regionKey);
        if (linkKeys == null || linkKeys.isEmpty()) {
            result.setMessage("NO_DATA");
            return result;
        }
        List<List<Object>> datas = regionDataCommitSet.getData().get(0);
        if (regionDataCommitSet.getData() == null || regionDataCommitSet.getData().size() == 0) {
            result.setMessage("NO_DATA");
            return result;
        }
        result.setMessage("HAVE_DATA");
        JtableContext context = regionDataCommitSet.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createCheckSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (i = 0; i < linkKeys.size(); ++i) {
            String linkKey = linkKeys.get(i);
            int index = saveDataBuilder.addLink(linkKey);
            linkIndexs.add(index);
        }
        saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 0);
        for (i = 0; i < datas.size(); ++i) {
            Object data = datas.get(i).get(1);
            if (data != null && data.toString().equals("")) {
                data = null;
            }
            saveDataBuilder.setData(((Integer)linkIndexs.get(i)).intValue(), data);
        }
        SaveReturnRes checkData = saveDataBuilder.checkData();
        List<SaveErrorDataInfo> saveErrorDataInfos = this.collectFixCellErrors(checkData.getSaveResItems());
        if (saveErrorDataInfos != null && saveErrorDataInfos.size() > 0) {
            result.setErrors(saveErrorDataInfos);
            return result;
        }
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            SaveReturnRes saveReturnRes = this.dataService.saveRegionData(saveInfo);
            if (saveReturnRes.getCode() != 0) {
                if (saveReturnRes.getSaveResItems() != null && saveReturnRes.getSaveResItems().size() > 0) {
                    List<SaveErrorDataInfo> saveErrorList = this.collectCommonCellErrors(saveReturnRes.getSaveResItems(), null, null);
                    result.getErrors().addAll(saveErrorList);
                } else {
                    SaveErrorDataInfo saveError = this.collectSystemError(saveReturnRes.getMessage());
                    result.getErrors().add(saveError);
                }
            }
        }
        catch (CrudSaveException e) {
            List items = e.getItems();
            List<SaveErrorDataInfo> saveErrorList = this.collectCommonCellErrors(items, null, null);
            result.getErrors().addAll(saveErrorList);
        }
        catch (CrudOperateException e) {
            SaveErrorDataInfo saveError = this.collectSystemError(e.getMessage());
            result.getErrors().add(saveError);
        }
        return result;
    }

    private SaveResult saveFloatRegionDatas(RegionDataCommitSet regionDataCommitSet, RegionData region) {
        SaveResult result = new SaveResult();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(region.getKey());
        List regionMetaDatas = regionRelation.getMetaData(null);
        if (regionMetaDatas == null || regionMetaDatas.size() == 0) {
            return result;
        }
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        if (linkKeys == null || linkKeys.isEmpty()) {
            result.setMessage("NO_DATA");
            return result;
        }
        List<String> deletedata = regionDataCommitSet.getDeletedata();
        List<List<List<Object>>> newdata = regionDataCommitSet.getNewdata();
        List<List<List<Object>>> modifydata = regionDataCommitSet.getModifydata();
        if (!(deletedata != null && deletedata.size() != 0 || newdata != null && newdata.size() != 0 || modifydata != null && modifydata.size() != 0)) {
            result.setMessage("NO_DATA");
            return result;
        }
        result.setMessage("HAVE_DATA");
        JtableContext context = regionDataCommitSet.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(context);
        RegionSettingUtil.rebuildMasterKeyByRegion(region, context, dimensionValueSet, regionRelation);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createCheckSaveDataBuilder(region.getKey(), dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        HashMap<String, String> bizKeyToRowIdMap = new HashMap<String, String>();
        ArrayList<FieldData> bizKeyOrderFields = new ArrayList();
        List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(region.getKey(), context);
        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
            bizKeyOrderFields = bizKeyOrderFieldList.get(0);
        }
        Integer rowIndex = -1;
        LinkedHashMap<Integer, String> rowIndexToRowIdMap = new LinkedHashMap<Integer, String>();
        FloatRegionSaveStructure floatRegionSaveParam = new FloatRegionSaveStructure();
        floatRegionSaveParam.setRegion(region);
        floatRegionSaveParam.setBizKeyOrderFields(bizKeyOrderFields);
        floatRegionSaveParam.setBizKeyToRowIdMap(bizKeyToRowIdMap);
        floatRegionSaveParam.setDimensionValueSet(dimensionValueSet);
        floatRegionSaveParam.setRegionMetaDatas(regionMetaDatas);
        floatRegionSaveParam.setSaveDataBuilder(saveDataBuilder);
        floatRegionSaveParam.setRegionRelation(regionRelation);
        floatRegionSaveParam.setRowIndex(rowIndex);
        floatRegionSaveParam.setRowIndexToRowIdMap(rowIndexToRowIdMap);
        FloatOrderCalc floatOrderCalc = new FloatOrderCalc(context, region.getKey(), regionRelation);
        floatRegionSaveParam.setFloatOrderCalc(floatOrderCalc);
        this.modifyFloatRowData(result, regionDataCommitSet, floatRegionSaveParam);
        this.deleteFloatRowData(result, regionDataCommitSet, floatRegionSaveParam);
        this.inserFloatRowData(result, regionDataCommitSet, floatRegionSaveParam);
        SaveReturnRes checkData = saveDataBuilder.checkData();
        List<SaveErrorDataInfo> saveErrorDataInfos = this.collectFloatCellErrors(checkData.getSaveResItems(), rowIndexToRowIdMap, bizKeyOrderFields);
        if (saveErrorDataInfos != null && saveErrorDataInfos.size() > 0) {
            result.setErrors(saveErrorDataInfos);
            return result;
        }
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            SaveReturnRes saveReturnRes;
            SaveData saveData = saveInfo.getSaveData();
            if (saveData.getRowCount() > 0 && (saveReturnRes = this.dataService.saveRegionData(saveInfo)).getCode() != 0) {
                if (saveReturnRes.getSaveResItems() != null && saveReturnRes.getSaveResItems().size() > 0) {
                    List<SaveErrorDataInfo> saveErrorList = this.collectCommonCellErrors(saveReturnRes.getSaveResItems(), bizKeyToRowIdMap, bizKeyOrderFields);
                    result.getErrors().addAll(saveErrorList);
                } else {
                    SaveErrorDataInfo saveError = this.collectSystemError(saveReturnRes.getMessage());
                    result.getErrors().add(saveError);
                }
            }
        }
        catch (CrudSaveException e) {
            List items = e.getItems();
            List<SaveErrorDataInfo> saveErrorList = this.collectCommonCellErrors(items, bizKeyToRowIdMap, bizKeyOrderFields);
            result.getErrors().addAll(saveErrorList);
        }
        catch (CrudOperateException e) {
            SaveErrorDataInfo saveError = this.collectSystemError(e.getMessage());
            result.getErrors().add(saveError);
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    private void inserFloatRowData(SaveResult result, RegionDataCommitSet regionDataCommitSet, FloatRegionSaveStructure floatRegionSaveParam) {
        List<List<List<Object>>> newdatas = regionDataCommitSet.getNewdata();
        if (newdatas == null || newdatas.size() == 0) {
            return;
        }
        Integer rowIndex = floatRegionSaveParam.getRowIndex();
        Map<Integer, String> rowIndexToRowIdMap = floatRegionSaveParam.getRowIndexToRowIdMap();
        RegionData region = floatRegionSaveParam.getRegion();
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        this.setNewDataFloatOrder(region, regionDataCommitSet);
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        int rowIDIndex = linkKeys.indexOf("ID");
        int floatOrderIndex = linkKeys.indexOf("FLOATORDER");
        int sumIndex = linkKeys.indexOf("SUM");
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            if (i == rowIDIndex || sumIndex != -1 && i == sumIndex) continue;
            int index = saveDataBuilder.addLink(linkKeys.get(i));
            linkIndexs.add(index);
        }
        List<MetaData> regionMetaDatas = floatRegionSaveParam.getRegionMetaDatas();
        FloatOrderCalc floatOrderCalc = floatRegionSaveParam.getFloatOrderCalc();
        JtableContext context = regionDataCommitSet.getContext();
        List<Integer> validDataRowIndexs = DataCrudUtil.getValidDataRowIndexs(regionMetaDatas, linkKeys, region, context, newdatas);
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        for (int i = 0; i < newdatas.size(); ++i) {
            Object linkKey;
            List<List<Object>> rowDatas = newdatas.get(i);
            if (validDataRowIndexs.contains(i)) continue;
            List<Object> rowIDValueList = rowDatas.get(rowIDIndex);
            String rowID = rowIDValueList.get(1).toString();
            List<Object> floatOrderValueList = rowDatas.get(floatOrderIndex);
            String floatOrder = floatOrderValueList.get(1).toString();
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            StringBuffer floatRowID = new StringBuffer();
            if (region.getAllowDuplicateKey()) {
                Object bizKeyOrder = rowID;
                if (StringUtils.isEmpty((String)rowID) || rowID.contains("FILL_ENTITY_EMPTY")) {
                    bizKeyOrder = UUID.randomUUID().toString();
                }
                dimensionCombinationBuilder.setValue("RECORDKEY", bizKeyOrder);
                if (floatRowID.length() > 0) {
                    floatRowID.append("#^$");
                }
                floatRowID.append((String)bizKeyOrder);
            }
            for (FieldData fieldData : bizKeyOrderFields) {
                linkKey = fieldData.getDataLinkKey();
                if (!StringUtils.isNotEmpty((String)linkKey)) continue;
                int linkKeyIndex = linkKeys.indexOf(linkKey);
                List<Object> linkKeyValueList = rowDatas.get(linkKeyIndex);
                String dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
                if (dimensionValueSet.hasValue(dimensionName)) continue;
                if (fieldData.getFieldType() == FieldType.FIELD_TYPE_DATE.getValue()) {
                    if (StringUtils.isNotEmpty((String)linkKeyValueList.get(1).toString())) {
                        dimensionCombinationBuilder.setValue(dimensionName, (Object)DateUtils.stringToDate(linkKeyValueList.get(1).toString()));
                    } else {
                        dimensionCombinationBuilder.setValue(dimensionName, (Object)"");
                    }
                } else {
                    dimensionCombinationBuilder.setValue(dimensionName, linkKeyValueList.get(1));
                }
                if (floatRowID.length() > 0) {
                    floatRowID.append("#^$");
                }
                floatRowID.append(linkKeyValueList.get(1));
            }
            DimensionCombination rowCombination = dimensionCombinationBuilder.getCombination();
            saveDataBuilder.addRow(rowCombination, 1);
            Integer n = rowIndex;
            rowIndex = rowIndex + 1;
            linkKey = rowIndex;
            rowIndexToRowIdMap.put(rowIndex, rowID);
            boolean bl = false;
            for (int j = 0; j < rowDatas.size(); ++j) {
                void var30_32;
                if (j == rowIDIndex || sumIndex != -1 && j == sumIndex) continue;
                Object data = rowDatas.get(j).get(1);
                if (data == null || StringUtils.isEmpty((String)data.toString())) {
                    data = j == floatOrderIndex ? Double.valueOf(floatOrderCalc.getMaxFloatOrder()) : null;
                }
                saveDataBuilder.setData(((Integer)linkIndexs.get((int)var30_32)).intValue(), data);
                ++var30_32;
            }
            String newRowId = this.buildFloatRowId(bizKeyOrderFields, rowCombination);
            Map<String, String> bizKeyToRowIdMap = floatRegionSaveParam.getBizKeyToRowIdMap();
            bizKeyToRowIdMap.put(rowID, newRowId);
            result.getRowKeyMap().put(rowID, newRowId);
            result.getFloatOrderMap().put(rowID, floatOrder);
        }
        floatRegionSaveParam.setRowIndex(rowIndex);
    }

    private void modifyFloatRowData(SaveResult result, RegionDataCommitSet regionDataCommitSet, FloatRegionSaveStructure floatRegionSaveParam) {
        List<List<List<Object>>> modifydata = regionDataCommitSet.getModifydata();
        if (modifydata == null || modifydata.size() == 0) {
            return;
        }
        Integer rowIndex = floatRegionSaveParam.getRowIndex();
        Map<Integer, String> rowIndexToRowIdMap = floatRegionSaveParam.getRowIndexToRowIdMap();
        RegionData region = floatRegionSaveParam.getRegion();
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        Map<String, List<String>> cells = regionDataCommitSet.getCells();
        List<String> linkKeys = cells.get(region.getKey());
        ArrayList<String> useLinkKeys = new ArrayList<String>();
        int rowIDIndex = linkKeys.indexOf("ID");
        int floatOrderIndex = linkKeys.indexOf("FLOATORDER");
        int sumIndex = linkKeys.indexOf("SUM");
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            if (i == rowIDIndex || sumIndex != -1 && i == sumIndex) continue;
            int index = saveDataBuilder.addLink(linkKeys.get(i));
            linkIndexs.add(index);
            useLinkKeys.add(linkKeys.get(i));
        }
        List<MetaData> regionMetaDatas = floatRegionSaveParam.getRegionMetaDatas();
        JtableContext context = regionDataCommitSet.getContext();
        List<Integer> validDataRowIndexs = DataCrudUtil.getValidDataRowIndexs(regionMetaDatas, linkKeys, region, context, modifydata);
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        Map linkFieldMap = bizKeyOrderFields.stream().collect(Collectors.toMap(FieldData::getDataLinkKey, Function.identity(), (key1, key2) -> key2));
        RegionRelation regionRelation = floatRegionSaveParam.getRegionRelation();
        boolean isDataMask = regionRelation.isContainsDesensitized();
        for (int i = 0; i < modifydata.size(); ++i) {
            List<List<Object>> rowDatas = modifydata.get(i);
            List<Object> rowIDValueList = rowDatas.get(rowIDIndex);
            String rowID = rowIDValueList.get(1).toString();
            if (rowID.contains("FILL_ENTITY_EMPTY")) {
                regionDataCommitSet.getNewdata().add(rowDatas);
                continue;
            }
            if (validDataRowIndexs.contains(i)) {
                regionDataCommitSet.getDeletedata().add(rowID);
                continue;
            }
            List<Object> floatOrderValueList = rowDatas.get(floatOrderIndex);
            String floatOrder = floatOrderValueList.get(1).toString();
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DataCrudUtil.setBizKeyValueForDimension(dimensionCombinationBuilder, dimensionValueSet, rowID, bizKeyOrderFields);
            DimensionCombination rowCombination = dimensionCombinationBuilder.getCombination();
            saveDataBuilder.addRow(rowCombination, isDataMask ? 4 : 2);
            Integer n = rowIndex;
            Integer n2 = rowIndex = Integer.valueOf(rowIndex + 1);
            rowIndexToRowIdMap.put(rowIndex, rowID);
            HashMap<FieldData, Object> rowDimFieldValueMap = new HashMap<FieldData, Object>();
            int index = 0;
            for (int j = 0; j < rowDatas.size(); ++j) {
                String useLinkKey;
                if (j == rowIDIndex || sumIndex != -1 && j == sumIndex) continue;
                if (isDataMask && !DataCrudUtil.cellDataChange(rowDatas.get(j))) {
                    ++index;
                    continue;
                }
                Object data = rowDatas.get(j).get(1);
                if (data == null || StringUtils.isEmpty((String)data.toString())) {
                    data = null;
                }
                if (useLinkKeys.get(index) != null && linkFieldMap.containsKey(useLinkKey = (String)useLinkKeys.get(index))) {
                    rowDimFieldValueMap.put((FieldData)linkFieldMap.get(useLinkKey), data);
                }
                saveDataBuilder.setData(((Integer)linkIndexs.get(index)).intValue(), data);
                ++index;
            }
            String newRowId = this.buildFloatRowId2(bizKeyOrderFields, rowDimFieldValueMap, rowCombination);
            Map<String, String> bizKeyToRowIdMap = floatRegionSaveParam.getBizKeyToRowIdMap();
            bizKeyToRowIdMap.put(rowID, newRowId);
            result.getRowKeyMap().put(rowID, newRowId);
            result.getFloatOrderMap().put(rowID, floatOrder);
        }
        floatRegionSaveParam.setRowIndex(rowIndex);
    }

    private void deleteFloatRowData(SaveResult result, RegionDataCommitSet regionDataCommitSet, FloatRegionSaveStructure floatRegionSaveParam) {
        List<String> rowIDs = regionDataCommitSet.getDeletedata();
        if (rowIDs == null || rowIDs.size() == 0) {
            return;
        }
        Integer rowIndex = floatRegionSaveParam.getRowIndex();
        Map<Integer, String> rowIndexToRowIdMap = floatRegionSaveParam.getRowIndexToRowIdMap();
        SaveDataBuilder saveDataBuilder = floatRegionSaveParam.getSaveDataBuilder();
        DimensionValueSet dimensionValueSet = floatRegionSaveParam.getDimensionValueSet();
        List<FieldData> bizKeyOrderFields = floatRegionSaveParam.getBizKeyOrderFields();
        for (String rowID : rowIDs) {
            if (rowID.contains("FILL_ENTITY_EMPTY")) continue;
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            DataCrudUtil.setBizKeyValueForDimension(dimensionCombinationBuilder, dimensionValueSet, rowID, bizKeyOrderFields);
            saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 3);
            Integer n = rowIndex;
            Integer n2 = rowIndex = Integer.valueOf(rowIndex + 1);
            rowIndexToRowIdMap.put(rowIndex, rowID);
        }
        floatRegionSaveParam.setRowIndex(rowIndex);
    }

    private SaveErrorDataInfo collectSystemError(String message) {
        SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
        ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
        resultErrorInfo.setErrorCode(ErrorCode.SYSTEMERROR);
        resultErrorInfo.setErrorInfo(message);
        saveErrorDataInfo.setDataError(resultErrorInfo);
        return saveErrorDataInfo;
    }

    private List<SaveErrorDataInfo> collectFixCellErrors(List<SaveResItem> saveResItems) {
        ArrayList<SaveErrorDataInfo> saveErrorDataInfos = new ArrayList<SaveErrorDataInfo>();
        for (SaveResItem saveResItem : saveResItems) {
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            String linkKey = saveResItem.getLinkKey();
            LinkData link = this.jtableParamService.getLink(linkKey);
            List messages = saveResItem.getMessages();
            String errorMessage = String.join((CharSequence)"$^#^$", messages);
            if (link != null) {
                ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
                resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                resultErrorInfo.setErrorInfo(errorMessage);
                saveErrorDataInfo.setDataLinkKey(link.getKey());
                saveErrorDataInfo.setFieldCode(link.getZbcode());
                saveErrorDataInfo.setFieldKey(link.getZbid());
                saveErrorDataInfo.setFieldTitle(link.getZbtitle());
                saveErrorDataInfo.setRegionKey(link.getRegionKey());
                saveErrorDataInfo.setDataError(resultErrorInfo);
            } else {
                saveErrorDataInfo = this.collectSystemError((String)messages.get(0));
            }
            saveErrorDataInfos.add(saveErrorDataInfo);
        }
        return saveErrorDataInfos;
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<SaveErrorDataInfo> collectFloatCellErrors(List<SaveResItem> saveResItems, Map<Integer, String> rowIndexToRowIdMap, List<FieldData> bizKeyOrderFields) {
        ArrayList<SaveErrorDataInfo> saveErrorDataInfos = new ArrayList<SaveErrorDataInfo>();
        Iterator<SaveResItem> iterator = saveResItems.iterator();
        while (true) {
            SaveErrorDataInfo saveErrorDataInfo;
            block6: {
                if (!iterator.hasNext()) {
                    return saveErrorDataInfos;
                }
                SaveResItem saveResItem = iterator.next();
                saveErrorDataInfo = new SaveErrorDataInfo();
                String linkKey = saveResItem.getLinkKey();
                List messages = saveResItem.getMessages();
                String errorMessage = String.join((CharSequence)"$^#^$", messages);
                if (StringUtils.isNotEmpty((String)linkKey) && linkKey.equals("FLOATORDER")) {
                    saveErrorDataInfo = this.collectSystemError((String)messages.get(0));
                    saveErrorDataInfos.add(saveErrorDataInfo);
                    continue;
                }
                int rowIndex = saveResItem.getRowIndex();
                LinkData link = this.jtableParamService.getLink(linkKey);
                if (rowIndex != -1 && rowIndexToRowIdMap.containsKey(rowIndex)) {
                    String rowId = rowIndexToRowIdMap.get(rowIndex);
                    saveErrorDataInfo.setRowId(rowId);
                    saveErrorDataInfo.setDataIndex(Integer.valueOf(rowIndex));
                    if (link != null) {
                        ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
                        resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                        resultErrorInfo.setErrorInfo(errorMessage);
                        saveErrorDataInfo.setDataLinkKey(link.getKey());
                        saveErrorDataInfo.setFieldCode(link.getZbcode());
                        saveErrorDataInfo.setFieldKey(link.getZbid());
                        saveErrorDataInfo.setFieldTitle(link.getZbtitle());
                        saveErrorDataInfo.setRegionKey(link.getRegionKey());
                        saveErrorDataInfo.setDataError(resultErrorInfo);
                        break block6;
                    } else {
                        List<SaveErrorDataInfo> rowIdRepeatErrors = this.collectRowIdRepeatErrors(bizKeyOrderFields, errorMessage, rowId, rowIndex);
                        saveErrorDataInfos.addAll(rowIdRepeatErrors);
                        continue;
                    }
                }
                saveErrorDataInfo = this.collectSystemError((String)messages.get(0));
                saveErrorDataInfo.setDataIndex(Integer.valueOf(rowIndex));
            }
            saveErrorDataInfos.add(saveErrorDataInfo);
        }
    }

    private List<SaveErrorDataInfo> collectRowIdRepeatErrors(List<FieldData> bizKeyOrderFields, String errorMessage, String rowId, int rowIndex) {
        ArrayList<SaveErrorDataInfo> saveErrorDataInfos = new ArrayList<SaveErrorDataInfo>();
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            saveErrorDataInfo.setRowId(rowId);
            saveErrorDataInfo.setDataIndex(Integer.valueOf(rowIndex));
            String dataLinkKey = bizKeyOrderField.getDataLinkKey();
            LinkData link = this.jtableParamService.getLink(dataLinkKey);
            if (link == null) continue;
            ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
            resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
            resultErrorInfo.setErrorInfo(errorMessage);
            saveErrorDataInfo.setDataLinkKey(link.getKey());
            saveErrorDataInfo.setFieldCode(link.getZbcode());
            saveErrorDataInfo.setFieldKey(link.getZbid());
            saveErrorDataInfo.setFieldTitle(link.getZbtitle());
            saveErrorDataInfo.setRegionKey(link.getRegionKey());
            saveErrorDataInfo.setDataError(resultErrorInfo);
            saveErrorDataInfos.add(saveErrorDataInfo);
        }
        return saveErrorDataInfos;
    }

    private SaveErrorDataInfo collectCommonCellError(String linkKey, String rowId, ReturnRes linkReturnRes) {
        SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
        LinkData link = this.jtableParamService.getLink(linkKey);
        ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
        String errorMessage = "";
        List messages = linkReturnRes.getMessages();
        errorMessage = messages != null && !messages.isEmpty() ? String.join((CharSequence)"$^#^$", messages) : linkReturnRes.getMessage();
        if (link != null) {
            resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
            resultErrorInfo.setErrorInfo(errorMessage);
            saveErrorDataInfo.setDataLinkKey(link.getKey());
            saveErrorDataInfo.setFieldCode(link.getZbcode());
            saveErrorDataInfo.setFieldKey(link.getZbid());
            saveErrorDataInfo.setFieldTitle(link.getZbtitle());
            saveErrorDataInfo.setRegionKey(link.getRegionKey());
            saveErrorDataInfo.setRowId(rowId);
            saveErrorDataInfo.setDataError(resultErrorInfo);
        } else {
            saveErrorDataInfo = this.collectSystemError(linkReturnRes.getMessage());
        }
        return saveErrorDataInfo;
    }

    private List<SaveErrorDataInfo> collectCommonCellErrors(List<SaveResItem> saveErrors, Map<String, String> bizKeyToRowIdMap, List<FieldData> bizKeyOrderFields) {
        ArrayList<SaveErrorDataInfo> saveErrorDataInfos = new ArrayList<SaveErrorDataInfo>();
        for (SaveResItem saveError : saveErrors) {
            SaveErrorDataInfo saveErrorDataInfo = new SaveErrorDataInfo();
            ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
            if (StringUtils.isNotEmpty((String)saveError.getLinkKey())) {
                String floatRowId;
                DimensionCombination dimension;
                LinkData link = this.jtableParamService.getLink(saveError.getLinkKey());
                if (link != null) {
                    saveErrorDataInfo.setDataLinkKey(link.getKey());
                    saveErrorDataInfo.setFieldCode(link.getZbcode());
                    saveErrorDataInfo.setFieldKey(link.getZbid());
                    saveErrorDataInfo.setFieldTitle(link.getZbtitle());
                    saveErrorDataInfo.setRegionKey(link.getRegionKey());
                }
                if (bizKeyToRowIdMap != null && bizKeyToRowIdMap.size() > 0 && (dimension = saveError.getDimension()) != null && StringUtils.isNotEmpty((String)(floatRowId = this.buildFloatRowId(bizKeyOrderFields, dimension))) && bizKeyToRowIdMap.containsKey(floatRowId)) {
                    String rowID = bizKeyToRowIdMap.get(floatRowId);
                    saveErrorDataInfo.setRowId(rowID);
                }
                resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                resultErrorInfo.setErrorInfo(saveError.getMessage());
                saveErrorDataInfo.setDataError(resultErrorInfo);
                saveErrorDataInfo.setDataIndex(Integer.valueOf(saveError.getRowIndex()));
                saveErrorDataInfos.add(saveErrorDataInfo);
                continue;
            }
            SaveErrorDataInfo collectSystemError = this.collectSystemError(saveError.getMessage());
            saveErrorDataInfos.add(collectSystemError);
        }
        return saveErrorDataInfos;
    }

    private String buildFloatRowId(List<FieldData> bizKeyOrderFields, DimensionCombination rowDimension) {
        StringBuilder floatRowID = new StringBuilder();
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(bizKeyOrderField);
            Object dimensionValue = rowDimension.getValue(dimensionName);
            if (dimensionValue == null || StringUtils.isEmpty((String)dimensionValue.toString())) {
                dimensionValue = "-";
            }
            if (floatRowID.length() > 0) {
                floatRowID.append("#^$");
            }
            floatRowID.append(dimensionValue);
        }
        return floatRowID.toString();
    }

    private String buildFloatRowId2(List<FieldData> bizKeyOrderFields, Map<FieldData, Object> rowDimFieldValueMap, DimensionCombination rowDimension) {
        StringBuilder floatRowID = new StringBuilder();
        for (FieldData bizKeyOrderField : bizKeyOrderFields) {
            String dimensionName = this.jtableDataEngineService.getDimensionName(bizKeyOrderField);
            Object dimensionValue = null;
            dimensionValue = rowDimFieldValueMap.containsKey(bizKeyOrderField) ? rowDimFieldValueMap.get(bizKeyOrderField) : rowDimension.getValue(dimensionName);
            if (dimensionValue == null || StringUtils.isEmpty((String)dimensionValue.toString())) {
                dimensionValue = "-";
            }
            if (floatRowID.length() > 0) {
                floatRowID.append("#^$");
            }
            floatRowID.append(dimensionValue);
        }
        return floatRowID.toString();
    }

    private FMDMCheckFailNodeInfoExtend collectFmdmCellError(String linkKey, ReturnRes linkReturnRes) {
        FMDMCheckFailNodeInfoExtend failNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
        LinkData link = this.jtableParamService.getLink(linkKey);
        ArrayList<CheckNodeInfo> checkNodeInfos = new ArrayList<CheckNodeInfo>();
        ArrayList<String> messages = linkReturnRes.getMessages();
        if (messages == null || messages.isEmpty()) {
            messages = new ArrayList<String>();
            messages.add(linkReturnRes.getMessage());
        }
        for (String message : messages) {
            CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
            checkNodeInfo.setContent(message);
            checkNodeInfo.setType(-1);
            checkNodeInfos.add(checkNodeInfo);
        }
        if (link != null) {
            failNodeInfoExtend.setDataLinkKey(link.getKey());
            failNodeInfoExtend.setFieldCode(link.getZbcode());
            failNodeInfoExtend.setFieldTitle(link.getZbtitle());
            failNodeInfoExtend.setRegionKey(link.getRegionKey());
        }
        failNodeInfoExtend.setNodes(checkNodeInfos);
        return failNodeInfoExtend;
    }

    private List<FMDMCheckFailNodeInfoExtend> collectFmdmCellErrors(List<SaveResItem> saveErrors, String regionKey) {
        ArrayList<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtends = new ArrayList<FMDMCheckFailNodeInfoExtend>();
        if (saveErrors != null && saveErrors.size() > 0) {
            Map<String, List<SaveResItem>> saveErrorMap = saveErrors.stream().collect(Collectors.groupingBy(SaveResItem::getLinkKey));
            for (String linkKey : saveErrorMap.keySet()) {
                FMDMCheckFailNodeInfoExtend fMDMCheckFailNodeInfoExtend = new FMDMCheckFailNodeInfoExtend();
                List<SaveResItem> saveErrorItems = saveErrorMap.get(linkKey);
                ArrayList<CheckNodeInfo> checkNodeInfos = new ArrayList<CheckNodeInfo>();
                for (SaveResItem saveError : saveErrorItems) {
                    CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
                    checkNodeInfo.setContent(saveError.getMessage());
                    checkNodeInfo.setType(saveError.getLevel());
                    checkNodeInfos.add(checkNodeInfo);
                }
                fMDMCheckFailNodeInfoExtend.setNodes(checkNodeInfos);
                fMDMCheckFailNodeInfoExtend.setDataLinkKey(linkKey);
                fMDMCheckFailNodeInfoExtend.setRegionKey(regionKey);
                fMDMCheckFailNodeInfoExtends.add(fMDMCheckFailNodeInfoExtend);
            }
        }
        return fMDMCheckFailNodeInfoExtends;
    }

    private void setNewDataFloatOrder(RegionData region, RegionDataCommitSet regionDataCommitSet) {
        FloatOrderStructure floatOrderStructure;
        List<List<List<Object>>> newdata = regionDataCommitSet.getNewdata();
        if (newdata != null && newdata.size() > 0 && (floatOrderStructure = regionDataCommitSet.getFloatOrderStructure()) != null) {
            List<String> afterInsertRowIds;
            List<String> beforeInsertRowIds = floatOrderStructure.getBeforeInsertRowIds();
            if (beforeInsertRowIds != null && beforeInsertRowIds.size() > 0) {
                String firstFloatOrder = floatOrderStructure.getFirstFloatOrder();
                String firstRowID = floatOrderStructure.getFirstRowID();
                if (StringUtils.isNotEmpty((String)firstFloatOrder) && StringUtils.isNotEmpty((String)firstRowID)) {
                    this.calcFloatOrder(region, regionDataCommitSet, -1);
                }
            }
            if ((afterInsertRowIds = floatOrderStructure.getAfterInsertRowIds()) != null && afterInsertRowIds.size() > 0) {
                String lastFloatOrder = floatOrderStructure.getLastFloatOrder();
                String lastRowID = floatOrderStructure.getLastRowID();
                if (StringUtils.isNotEmpty((String)lastFloatOrder) && StringUtils.isNotEmpty((String)lastRowID)) {
                    this.calcFloatOrder(region, regionDataCommitSet, 1);
                }
            }
        }
    }

    private void calcFloatOrder(RegionData region, RegionDataCommitSet regionDataCommitSet, int offset) {
        String preOrNextFloatOrder;
        List<Object> preOrNextRowData;
        List<List<List<Object>>> newdata = regionDataCommitSet.getNewdata();
        FloatOrderStructure floatOrderStructure = regionDataCommitSet.getFloatOrderStructure();
        List<String> insertRowIds = floatOrderStructure.getAfterInsertRowIds();
        String markFloatOrder = floatOrderStructure.getLastFloatOrder();
        String locateRowID = floatOrderStructure.getLastRowID();
        if (offset == -1) {
            insertRowIds = floatOrderStructure.getBeforeInsertRowIds();
            markFloatOrder = floatOrderStructure.getFirstFloatOrder();
            locateRowID = floatOrderStructure.getFirstRowID();
        }
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        RegionRestructureInfo restructureInfo = new RegionRestructureInfo();
        restructureInfo.setOffset(offset);
        restructureInfo.setDataID(locateRowID);
        regionQueryInfo.setRestructureInfo(restructureInfo);
        regionQueryInfo.setContext(regionDataCommitSet.getContext());
        regionQueryInfo.setRegionKey(region.getKey());
        RegionSingleDataSet regionSingleDataSet = this.jtableDataQueryService.querySingleFloatRowData(regionQueryInfo);
        List<List<Object>> rowData = regionSingleDataSet.getData();
        if (rowData != null && rowData.size() > 0 && (preOrNextRowData = rowData.get(0)) != null && preOrNextRowData.size() > 0 && preOrNextRowData.get(1) != null && StringUtils.isNotEmpty((String)(preOrNextFloatOrder = String.valueOf(preOrNextRowData.get(1))))) {
            String startIndex = markFloatOrder;
            String endIndex = preOrNextFloatOrder;
            DecimalFormat df = new DecimalFormat("#.00000");
            block0: for (String id : insertRowIds) {
                double a = Double.valueOf(startIndex);
                double b = Double.valueOf(endIndex);
                if (offset == -1) {
                    a = Double.valueOf(endIndex);
                    b = Double.valueOf(startIndex);
                }
                double c = (b - a) / (double)(insertRowIds.size() + 1);
                String cFormat = df.format(c);
                double d = a + Double.valueOf(cFormat);
                for (List<List<Object>> row : newdata) {
                    String rowId;
                    List<Object> rowIdList = row.get(0);
                    if (rowIdList == null || rowIdList.size() <= 0 || rowIdList.get(0) == null || !(rowId = String.valueOf(rowIdList.get(0))).equals(id)) continue;
                    List<Object> floatorderList = row.get(1);
                    if (floatorderList == null || floatorderList.size() <= 0) continue block0;
                    floatorderList.set(0, df.format(d));
                    floatorderList.set(1, df.format(d));
                    if (offset == -1) {
                        endIndex = df.format(d);
                        continue block0;
                    }
                    startIndex = df.format(d);
                    continue block0;
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ReturnInfo clearReportFormDatas(ReportDataQueryInfo reportDataQueryInfo) throws CrudOperateException {
        JtableContext jtableContext = reportDataQueryInfo.getContext();
        FormData formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("success");
        if (regions == null || regions.size() == 0) {
            returnInfo.setMessage(formData.getCode() + "\uff1a\u62a5\u8868\u6ca1\u6709\u533a\u57df");
            return returnInfo;
        }
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            returnInfo.setMessage(formData.getCode() + "\uff1a\u5c01\u9762\u4ee3\u7801\u4e0d\u80fd\u6e05\u9664");
            return returnInfo;
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        for (RegionData region : regions) {
            IClearInfo clearInfo = ClearInfoBuilder.create((String)region.getKey(), (DimensionCombination)dimensionCombinationBuilder.getCombination()).build();
            ReturnRes returnRes = this.dataService.clearRegionData(clearInfo);
            if (returnRes.getCode() == 0 || returnRes.getCode() == 1102) continue;
            returnInfo.setMessage(returnRes.getMessage());
            break;
        }
        if (this.dataStatusService != null) {
            ClearStatusPar clearStatusPar = new ClearStatusPar();
            clearStatusPar.setFormSchemeKey(jtableContext.getFormSchemeKey());
            clearStatusPar.setFormKey(jtableContext.getFormKey());
            clearStatusPar.setDimensionCollection(this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, jtableContext.getFormSchemeKey()));
            try {
                this.dataStatusService.clearDataStatusByForm(clearStatusPar);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return returnInfo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public SaveResult saveRegionDatas(RegionDataCommitSet regionDataCommitSet) {
        SaveResult result = new SaveResult();
        RegionData region = this.jtableParamService.getRegion(regionDataCommitSet.getRegionKey());
        result = region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() ? this.saveFloatRegionDatas(regionDataCommitSet, region) : this.saveFixRegionDatas(regionDataCommitSet, region);
        if (!result.getErrors().isEmpty()) {
            result.setMessage("failed");
            throw new SaveDataException(result);
        }
        result.setMessage("success");
        return result;
    }

    @Override
    public SaveResult deleteFmdm(JtableContext jtableContext) {
        SaveResult result = new SaveResult();
        List<RegionData> regions = this.jtableParamService.getRegions(jtableContext.getFormKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        IClearInfo clearInfo = ClearInfoBuilder.create((String)regions.get(0).getKey(), (DimensionCombination)dimensionCombinationBuilder.getCombination()).build();
        ReturnRes returnRes = null;
        try {
            returnRes = this.dataService.clearRegionData(clearInfo);
            if (returnRes.getCode() != 0) {
                result.setMessage("failed");
                SaveErrorDataInfo errorDataInfo = this.collectSystemError(returnRes.getMessage());
                result.getErrors().add(errorDataInfo);
            } else {
                result.setMessage("success");
            }
        }
        catch (CrudException | CrudOperateException e) {
            result.setMessage("failed");
            SaveErrorDataInfo errorDataInfo = this.collectSystemError(e.getMessage());
            result.getErrors().add(errorDataInfo);
        }
        return result;
    }

    private IFormAuthorityServive getFormAuthorityServive(String accessCode) {
        if (StringUtils.isEmpty((String)accessCode)) {
            accessCode = "dataentry";
        }
        for (IFormAuthorityServive iFormAuthorityServive : this.iFormAuthorityServives) {
            String accessCode2 = iFormAuthorityServive.getAccessCode();
            if (!accessCode.equals(accessCode2)) continue;
            return iFormAuthorityServive;
        }
        return null;
    }
}


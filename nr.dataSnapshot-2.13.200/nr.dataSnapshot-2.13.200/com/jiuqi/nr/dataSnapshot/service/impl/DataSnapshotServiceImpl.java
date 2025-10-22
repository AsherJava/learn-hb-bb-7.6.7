/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn
 *  com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.snapshot.consts.RegionType
 *  com.jiuqi.nr.snapshot.message.DataRange
 *  com.jiuqi.nr.snapshot.message.DataRegionRange
 *  com.jiuqi.nr.snapshot.message.DifferenceData
 *  com.jiuqi.nr.snapshot.message.DifferenceDataItem
 *  com.jiuqi.nr.snapshot.message.FixedDataRegionCompareDifference
 *  com.jiuqi.nr.snapshot.message.FloatCompareDifference
 *  com.jiuqi.nr.snapshot.message.FloatDataRegionCompareDifference
 *  com.jiuqi.nr.snapshot.output.ComparisonResult
 *  com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotDifference;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotExportParam;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotFormInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataSnapshot.param.DifferenceResult;
import com.jiuqi.nr.dataSnapshot.param.FieldObject;
import com.jiuqi.nr.dataSnapshot.param.IDataRegionCompareDifference;
import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotExportService;
import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotService;
import com.jiuqi.nr.dataSnapshot.service.impl.AbstractEnhancedDataSnapshotService;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.message.DifferenceData;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import com.jiuqi.nr.snapshot.message.FixedDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.message.FloatCompareDifference;
import com.jiuqi.nr.snapshot.message.FloatDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class DataSnapshotServiceImpl
extends AbstractEnhancedDataSnapshotService
implements IDataSnapshotService {
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataValueFormatterBuilderFactory formatFactory;
    @Autowired
    private IDataSnapshotExportService dataSnapshotExportService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private List<DataSnapshotDifference> curCompareResult;

    @Override
    public AsyncTaskInfo createDataSnapshot(DataSnapshotParam param) throws JTableException {
        return this.getIDataSnapshotService().createDataSnapshot(param);
    }

    @Override
    public ReturnInfo updateDataSnapshot(DataSnapshotParam param) throws JTableException {
        return this.getIDataSnapshotService().updateDataSnapshot(param);
    }

    @Override
    public List<DataSnapshotInfo> queryDataSnapshot(DataSnapshotParam param) {
        return this.getIDataSnapshotService().queryDataSnapshot(param);
    }

    @Override
    public ReturnInfo deleteDataSnapshot(DataSnapshotParam param) {
        return this.getIDataSnapshotService().deleteDataSnapshot(param);
    }

    @Override
    public ReturnInfo restoreDataSnapshot(DataSnapshotParam param) {
        if (param.getDataRange() == null) {
            List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(param.getContext().getFormSchemeKey());
            DataRange dataRange = new DataRange();
            for (String formKey : formKeys) {
                DataRegionRange dataRegionRange = new DataRegionRange();
                dataRegionRange.setFormKey(formKey);
                dataRange.getFormAndFields().add(dataRegionRange);
            }
        }
        return this.getIDataSnapshotService().restoreDataSnapshot(param);
    }

    @Override
    public Map<String, Integer> compareDataSnapshotReturnTotal(DataSnapshotParam param) {
        List<ComparisonResult> fromResult = this.getIDataSnapshotService().compareDataSnapshot(param);
        HashMap<String, Integer> differenceResult = new HashMap<String, Integer>();
        this.curCompareResult = this.convertComparisonResultToDSDiff(fromResult, param);
        if (fromResult != null) {
            for (ComparisonResult comparisonResult : fromResult) {
                int diffCells = 0;
                for (String regionKey : comparisonResult.getDifferenceDatas().keySet()) {
                    diffCells += ((com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference)comparisonResult.getDifferenceDatas().get(regionKey)).getDifferenceCount();
                }
                differenceResult.put(comparisonResult.getFormKey(), diffCells);
            }
        }
        return differenceResult;
    }

    @Override
    public DifferenceResult compareDataSnapshot(DataSnapshotParam param) {
        DifferenceResult differenceResult = new DifferenceResult();
        differenceResult.setDataSnapshotDifference(this.convertComparisonResultToDSDiff(this.getIDataSnapshotService().compareDataSnapshot(param), param));
        if (param.isCompareAllField()) {
            HashMap<String, String> fieldMap = new HashMap<String, String>();
            ArrayList<FieldObject> selectedField = new ArrayList<FieldObject>();
            ArrayList<FieldObject> selectableFixedField = new ArrayList<FieldObject>();
            for (DataSnapshotDifference dataSnapshotDifference : differenceResult.getDataSnapshotDifference()) {
                for (String key : dataSnapshotDifference.getDifferenceDatas().keySet()) {
                    IDataRegionCompareDifference iDataRegionCompareDifference = dataSnapshotDifference.getDifferenceDatas().get(key);
                    List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(iDataRegionCompareDifference.getRegionKey());
                    HashMap<String, String> linksMap = new HashMap<String, String>();
                    for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                        linksMap.put(dataLinkDefine.getLinkExpression(), dataLinkDefine.getKey());
                    }
                    if (iDataRegionCompareDifference.getRegionType().equals((Object)RegionType.FIXED)) {
                        for (DifferenceDataItem differenceDataItem : ((com.jiuqi.nr.dataSnapshot.param.FixedDataRegionCompareDifference)iDataRegionCompareDifference).getDifferenceDataItems()) {
                            fieldMap.put(differenceDataItem.getFieldKey(), (String)linksMap.get(differenceDataItem.getFieldKey()));
                            FieldObject fieldObject = new FieldObject();
                            fieldObject.setFieldCode(differenceDataItem.getFieldKey());
                            try {
                                fieldObject.setFieldType(this.runTimeViewController.queryFieldDefine(differenceDataItem.getFieldKey()).getType().name());
                            }
                            catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            selectedField.add(fieldObject);
                            selectableFixedField.add(fieldObject);
                        }
                        continue;
                    }
                    List<com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference> floatCompareDifferences = ((com.jiuqi.nr.dataSnapshot.param.FloatDataRegionCompareDifference)iDataRegionCompareDifference).getFloatCompareDifferences();
                    if (floatCompareDifferences.size() <= 0) continue;
                    com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference floatCompareDifference = floatCompareDifferences.get(0);
                    for (DifferenceDataItem differenceDataItem : floatCompareDifference.getDifferenceDataItems()) {
                        fieldMap.put(differenceDataItem.getFieldKey(), (String)linksMap.get(differenceDataItem.getFieldKey()));
                        FieldObject fieldObject = new FieldObject();
                        fieldObject.setFieldCode(differenceDataItem.getFieldKey());
                        try {
                            fieldObject.setFieldType(this.runTimeViewController.queryFieldDefine(differenceDataItem.getFieldKey()).getType().name());
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        selectedField.add(fieldObject);
                    }
                }
            }
            differenceResult.setFieldMap(fieldMap);
            differenceResult.setSelectedField(selectedField);
            differenceResult.setSelectableFixedField(selectableFixedField);
        }
        return differenceResult;
    }

    @Override
    public List<DataSnapshotFormInfo> queryFormList(String taskKey) {
        ArrayList<DataSnapshotFormInfo> dataSnapshotFormInfos = new ArrayList<DataSnapshotFormInfo>();
        List formSchemeKeys = new ArrayList();
        try {
            formSchemeKeys = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (FormSchemeDefine formSchemeDefine : formSchemeKeys) {
            DataSnapshotFormInfo dataSnapshotFormInfo = new DataSnapshotFormInfo();
            dataSnapshotFormInfo.setFormSchemeKey(formSchemeDefine.getFormSchemeCode());
            dataSnapshotFormInfo.setFormSchemeTitle(formSchemeDefine.getTitle());
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getFormSchemeCode());
            dataSnapshotFormInfo.setDataSnapshotForms(formDefines, 1);
            dataSnapshotFormInfos.add(dataSnapshotFormInfo);
        }
        return dataSnapshotFormInfos;
    }

    @Override
    public ExportData dataSnapshotCompareResultExport(DataSnapshotExportParam info) {
        return this.dataSnapshotExportService.compareResultExport(info, this.curCompareResult, info.getFormKey());
    }

    @Override
    public List<ExportData> dataSnapshotCompareResultBatchExport(DataSnapshotExportParam info) {
        List<String> param = info.getExportForms();
        if (param == null) {
            return this.dataSnapshotExportService.compareResultBatchExport(info, this.curCompareResult);
        }
        return this.dataSnapshotExportService.compareResultBatchExport(info, this.curCompareResult, param);
    }

    @Override
    public Map<String, Object> querySystemParam(String taskKey) {
        HashMap<String, Object> sysParamMap = new HashMap<String, Object>();
        String SNAPSHOT_SELECT_FIELD = this.iNvwaSystemOptionService.get("snapshotManagement", "SNAPSHOT_SELECT_FIELD");
        sysParamMap.put("SNAPSHOT_SELECT_FIELD", SNAPSHOT_SELECT_FIELD.equals("1"));
        String SNAPSHOT_SET_RATE = this.iNvwaSystemOptionService.get("snapshotManagement", "SNAPSHOT_SET_RATE");
        sysParamMap.put("SNAPSHOT_SET_RATE", SNAPSHOT_SET_RATE.equals("1"));
        return sysParamMap;
    }

    private List<DataSnapshotDifference> convertComparisonResultToDSDiff(List<ComparisonResult> fromResult, DataSnapshotParam param) {
        ArrayList<DataSnapshotDifference> toResult = new ArrayList<DataSnapshotDifference>();
        DataValueFormatterBuilder formatterBuilder = this.formatFactory.createFormatterBuilder();
        DataValueFormatter formatter = formatterBuilder.build();
        if (fromResult != null) {
            for (ComparisonResult curr : fromResult) {
                if (curr.getDifferenceDatas().isEmpty()) continue;
                DataSnapshotDifference toCurr = new DataSnapshotDifference();
                toCurr.setFormKey(curr.getFormKey());
                toCurr.setDataSourceNames(curr.getDataSourceNames());
                HashMap<String, IDataRegionCompareDifference> differenceDatas = new HashMap<String, IDataRegionCompareDifference>();
                for (String keyString : curr.getDifferenceDatas().keySet()) {
                    if (curr.getDifferenceDatas().get(keyString) instanceof FixedDataRegionCompareDifference) {
                        com.jiuqi.nr.dataSnapshot.param.FixedDataRegionCompareDifference fix = new com.jiuqi.nr.dataSnapshot.param.FixedDataRegionCompareDifference();
                        FixedDataRegionCompareDifference curFix = (FixedDataRegionCompareDifference)curr.getDifferenceDatas().get(keyString);
                        fix.setRegionKey(curFix.getRegionKey());
                        fix.setRegionName(curFix.getRegionName());
                        fix.setRegionType(curFix.getRegionType());
                        List differenceDataItems = curFix.getDifferenceDataItems();
                        for (DifferenceDataItem differenceDataItem : differenceDataItems) {
                            if (this.jtableParamService.getField(differenceDataItem.getFieldKey()).getFieldType() != 5) continue;
                            List differenceDataList = differenceDataItem.getDifferenceDatas();
                            for (DifferenceData diffData : differenceDataList) {
                                if (diffData.getInitialValue() != null) {
                                    diffData.setInitialValue(diffData.getInitialValue().split(" ")[0]);
                                }
                                if (diffData.getCompareValue() == null) continue;
                                diffData.setCompareValue(diffData.getCompareValue().split(" ")[0]);
                            }
                        }
                        fix.setDifferenceDataItems(differenceDataItems);
                        differenceDatas.put(keyString, fix);
                    } else {
                        com.jiuqi.nr.dataSnapshot.param.FloatDataRegionCompareDifference flo = new com.jiuqi.nr.dataSnapshot.param.FloatDataRegionCompareDifference();
                        FloatDataRegionCompareDifference curFlo = (FloatDataRegionCompareDifference)curr.getDifferenceDatas().get(keyString);
                        AbstractRegionRelationEvn abstractRegionRelationEvn = this.createRegionRelationEvn(this.jtableParamService.getRegion(curFlo.getRegionKey()), param.getContext());
                        List bizKeyOrderFieldList = abstractRegionRelationEvn.getBizKeyOrderFields();
                        List bizKeyOrderFields = new ArrayList();
                        if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
                            bizKeyOrderFields = (List)bizKeyOrderFieldList.get(0);
                        }
                        flo.setNaturalKeys(curFlo.getNaturalKeys());
                        flo.setRegionKey(curFlo.getRegionKey());
                        flo.setRegionName(curFlo.getRegionName());
                        flo.setRegionType(curFlo.getRegionType());
                        ArrayList<com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference> floComs = new ArrayList<com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference>();
                        int rootTreeDeep = Integer.MIN_VALUE;
                        for (FloatCompareDifference curFloCom : curFlo.getFloatCompareDifferences()) {
                            if (curFloCom.getBizKeyOrder() != null && curFloCom.getBizKeyOrder().getAsString() != null || curFloCom.getGroupTreeDeep() == null || curFloCom.getGroupTreeDeep().getAsString() == null) continue;
                            rootTreeDeep = Math.max(rootTreeDeep, Integer.parseInt(curFloCom.getGroupTreeDeep().getAsString()));
                        }
                        for (FloatCompareDifference curFloCom : curFlo.getFloatCompareDifferences()) {
                            StringBuilder floatRowID;
                            com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference floCom = new com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference();
                            List differenceDataItems = curFloCom.getDifferenceDataItems();
                            for (Object differenceDataItem : differenceDataItems) {
                                if (this.jtableParamService.getField(differenceDataItem.getFieldKey()).getFieldType() != 5) continue;
                                List differenceDataList = differenceDataItem.getDifferenceDatas();
                                for (DifferenceData diffData : differenceDataList) {
                                    if (diffData.getInitialValue() != null) {
                                        diffData.setInitialValue(diffData.getInitialValue().split(" ")[0]);
                                    }
                                    if (diffData.getCompareValue() == null) continue;
                                    diffData.setCompareValue(diffData.getCompareValue().split(" ")[0]);
                                }
                            }
                            floCom.setDifferenceDataItems(differenceDataItems);
                            if (curFloCom.getBizKeyOrder() == null) {
                                floCom.setBizKeyOrder(UUID.randomUUID().toString());
                            } else {
                                floCom.setBizKeyOrder(((StringData)curFloCom.getBizKeyOrder()).getAsString());
                            }
                            ArrayList<String> natu = new ArrayList<String>();
                            if (curFloCom.getNaturalDatas() != null) {
                                Object differenceDataItem;
                                differenceDataItem = curFloCom.getNaturalDatas().iterator();
                                while (differenceDataItem.hasNext()) {
                                    AbstractData curData = (AbstractData)differenceDataItem.next();
                                    natu.add(((StringData)curData).getAsString());
                                }
                            }
                            floCom.setNaturalDatas(natu);
                            if ((curFloCom.getBizKeyOrder() == null || curFloCom.getBizKeyOrder().getAsString() == null) && curFloCom.getGroupTreeDeep() != null && curFloCom.getGroupTreeDeep().getAsString() != null) {
                                floatRowID = new StringBuilder();
                                floatRowID.append(curFloCom.getGroupKey().getAsString());
                                floatRowID.append("#^$").append(curFloCom.getGroupTreeDeep().getAsString());
                                floCom.setBizKeyOrder(floatRowID.toString());
                                if (Integer.parseInt(curFloCom.getGroupTreeDeep().getAsString()) == rootTreeDeep && floCom.getNaturalDatas().get(0).equals("\u2014\u2014")) {
                                    floCom.getNaturalDatas().remove(0);
                                    floCom.getNaturalDatas().add(0, "\u5408\u8ba1\u884c\u7684\u9876\u7ea7root\u8282\u70b9&$");
                                }
                            } else if (flo.getNaturalKeys() != null && flo.getNaturalKeys().size() != 0) {
                                floatRowID = new StringBuilder();
                                for (FieldData bizKeyOrderField : bizKeyOrderFields) {
                                    String dimensionValue = null;
                                    for (int i = 0; i < flo.getNaturalKeys().size(); ++i) {
                                        if (!flo.getNaturalKeys().get(i).split(";")[1].equals(bizKeyOrderField.getFieldTitle())) continue;
                                        dimensionValue = floCom.getNaturalDatas().get(i);
                                        break;
                                    }
                                    if (bizKeyOrderField.getFieldTitle().equals("BIZKEYORDER")) {
                                        dimensionValue = floCom.getBizKeyOrder();
                                    }
                                    if (dimensionValue == null || StringUtils.isEmpty((String)dimensionValue)) {
                                        dimensionValue = "-";
                                    }
                                    if (floatRowID.length() > 0) {
                                        floatRowID.append("#^$");
                                    }
                                    floatRowID.append(dimensionValue);
                                }
                                floCom.setBizKeyOrder(floatRowID.toString());
                            }
                            floComs.add(floCom);
                        }
                        if (curFlo.getNaturalKeys() != null) {
                            for (com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference fd : floComs) {
                                ArrayList<String> formatDatas = new ArrayList<String>();
                                for (int i = 0; i < curFlo.getNaturalKeys().size(); ++i) {
                                    String nTKeys = (String)curFlo.getNaturalKeys().get(i);
                                    List linkDefines = this.runTimeViewController.getLinksInRegionByField(curFlo.getRegionKey(), nTKeys.split(";")[0]);
                                    String linkKey = null;
                                    if (linkDefines.size() > 0) {
                                        linkKey = ((DataLinkDefine)linkDefines.get(0)).getKey();
                                    }
                                    if (fd.getNaturalDatas().get(i).equals("\u5408\u8ba1\u884c\u7684\u9876\u7ea7root\u8282\u70b9&$")) {
                                        formatDatas.add("\u5408\u8ba1");
                                        continue;
                                    }
                                    formatDatas.add(formatter.format(linkKey, (Object)fd.getNaturalDatas().get(i)));
                                }
                                fd.setNaturalDatas(formatDatas);
                            }
                        }
                        flo.setFloatCompareDifferences(floComs);
                        differenceDatas.put(keyString, flo);
                    }
                    toCurr.setDifferenceDatas(differenceDatas);
                }
                toResult.add(toCurr);
            }
        }
        return toResult;
    }

    private AbstractRegionRelationEvn createRegionRelationEvn(RegionData region, JtableContext jtableContext) {
        Object regionRelationEvn = null;
        regionRelationEvn = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new SimpleRegionRelationEvn(region, jtableContext) : new FloatRegionRelationEvn(region, jtableContext);
        return regionRelationEvn;
    }
}


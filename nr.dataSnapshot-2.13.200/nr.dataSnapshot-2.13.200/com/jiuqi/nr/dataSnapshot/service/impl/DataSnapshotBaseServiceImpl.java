/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.snapshot.consts.HistoryPeriodType
 *  com.jiuqi.nr.snapshot.consts.RegionType
 *  com.jiuqi.nr.snapshot.input.ComparisonContext
 *  com.jiuqi.nr.snapshot.input.ReversionByPeriodContext
 *  com.jiuqi.nr.snapshot.input.ReversionBySnapshotContext
 *  com.jiuqi.nr.snapshot.input.UpdateSnapshotInfo
 *  com.jiuqi.nr.snapshot.message.FloatDataRegionCompareDifference
 *  com.jiuqi.nr.snapshot.output.ComparisonResult
 *  com.jiuqi.nr.snapshot.output.SnapshotInfo
 *  com.jiuqi.nr.snapshot.service.DataOperationService
 *  com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataSnapshot.asynctask.DataSnapshotTaskExecutor;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotBaseService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.snapshot.consts.HistoryPeriodType;
import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.input.ComparisonContext;
import com.jiuqi.nr.snapshot.input.ReversionByPeriodContext;
import com.jiuqi.nr.snapshot.input.ReversionBySnapshotContext;
import com.jiuqi.nr.snapshot.input.UpdateSnapshotInfo;
import com.jiuqi.nr.snapshot.message.FloatDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSnapshotBaseServiceImpl
implements IDataSnapshotBaseService {
    private static final Logger logger = LoggerFactory.getLogger(DataSnapshotBaseServiceImpl.class);
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private DataOperationService dataOperationService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;

    @Override
    public AsyncTaskInfo createDataSnapshot(DataSnapshotParam param) throws JTableException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DataSnapshotTaskExecutor());
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @Override
    public ReturnInfo updateDataSnapshot(DataSnapshotParam param) throws JTableException {
        ReturnInfo returnInfo = new ReturnInfo();
        UpdateSnapshotInfo updateSnapshotInfo = new UpdateSnapshotInfo();
        updateSnapshotInfo.setSnapshotId(param.getSnapshotId());
        updateSnapshotInfo.setDescribe(param.getDescribe());
        updateSnapshotInfo.setTitle(param.getTitle());
        updateSnapshotInfo.setTaskKey(param.getContext().getTaskKey());
        Map dimensionSet = param.getContext().getDimensionSet();
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, param.getContext().getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        DimensionCombination dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, param.getContext().getFormSchemeKey());
        updateSnapshotInfo.setDimensionCombination(dimensionCombination);
        try {
            this.snapshotService.updateSnapshot(updateSnapshotInfo);
        }
        catch (Exception e) {
            returnInfo.setMessage(e.getMessage());
            return returnInfo;
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public List<DataSnapshotInfo> queryDataSnapshot(DataSnapshotParam param) {
        Map dimensionSet = param.getContext().getDimensionSet();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (String key : dimensionSet.keySet()) {
            String[] values = ((DimensionValue)dimensionSet.get(key)).getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                builder.setEntityValue(key, null, new Object[]{((DimensionValue)dimensionSet.get(key)).getValue()});
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            builder.setEntityValue(key, null, new Object[]{valueList});
        }
        DimensionCollection dimensionCollection = builder.getCollection();
        List dimensionCombination = dimensionCollection.getDimensionCombinations();
        List snapshotInfoList = this.snapshotService.querySnapshot((DimensionCombination)dimensionCombination.get(0), param.getContext().getTaskKey());
        ArrayList<DataSnapshotInfo> dataSnapshotInfoList = new ArrayList<DataSnapshotInfo>();
        for (SnapshotInfo snapshotInfo : snapshotInfoList) {
            DataSnapshotInfo dataSnapshotInfo = new DataSnapshotInfo();
            dataSnapshotInfo.setAutoCreate(snapshotInfo.getSnapshot().isAutoCreate());
            dataSnapshotInfo.setDimensionSet(dimensionSet);
            dataSnapshotInfo.setSnapshotId(snapshotInfo.getSnapshot().getSnapshotId());
            dataSnapshotInfo.setDescribe(snapshotInfo.getSnapshot().getDescribe());
            dataSnapshotInfo.setCreatTime(snapshotInfo.getSnapshot().getCreatTime());
            dataSnapshotInfo.setTitle(snapshotInfo.getSnapshot().getTitle());
            dataSnapshotInfo.setDescribe(snapshotInfo.getSnapshot().getDescribe());
            dataSnapshotInfo.setCreatUserName(snapshotInfo.getSnapshot().getCreatUserName());
            dataSnapshotInfoList.add(dataSnapshotInfo);
        }
        return dataSnapshotInfoList;
    }

    @Override
    public ReturnInfo deleteDataSnapshot(DataSnapshotParam param) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            this.snapshotService.deleteSnapshot(param.getContext().getTaskKey(), param.getSnapshotId());
        }
        catch (Exception e) {
            returnInfo.setMessage("fail");
            return returnInfo;
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public ReturnInfo restoreDataSnapshot(DataSnapshotParam param) {
        ReturnInfo returnInfo = new ReturnInfo();
        Map dimensionSet = param.getContext().getDimensionSet();
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, param.getContext().getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        DimensionCombination dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, param.getContext().getFormSchemeKey());
        if (param.getPeriodCode().equals("NotPeriod")) {
            ReversionBySnapshotContext revertContext = new ReversionBySnapshotContext();
            revertContext.setCurrentDim(dimensionCombination);
            revertContext.setDataRange(param.getDataRange());
            revertContext.setSnapshotId(param.getSnapshotId());
            revertContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
            try {
                this.dataOperationService.reversionBySnapshot(revertContext);
            }
            catch (Exception e) {
                returnInfo.setMessage("fail");
                return returnInfo;
            }
        }
        ReversionByPeriodContext revertContext = new ReversionByPeriodContext();
        if (param.getPeriodCode().equals("LastYearPeriod")) {
            revertContext.setHistoryPeriodType(HistoryPeriodType.LASTYEAR_SAMEPERIOD);
            revertContext.setHistoryDataName("\u4e0a\u5e74\u540c\u671f");
        } else if (param.getPeriodCode().equals("LastPeriod")) {
            revertContext.setHistoryPeriodType(HistoryPeriodType.LAST_PERIOD);
            revertContext.setHistoryDataName("\u4e0a\u4e00\u671f");
        } else if (param.getPeriodCode().equals("LastReturned")) {
            revertContext.setHistoryPeriodType(HistoryPeriodType.LATEST_ROLLBACK_SNAPSHOT);
            revertContext.setHistoryDataName("\u6700\u65b0\u9000\u56de");
        }
        revertContext.setDataRange(param.getDataRange());
        revertContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
        revertContext.setCurrentDim(dimensionCombination);
        try {
            this.dataOperationService.reversionByPeriod(revertContext);
        }
        catch (Exception e) {
            returnInfo.setMessage("fail");
            return returnInfo;
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public List<ComparisonResult> compareDataSnapshot(DataSnapshotParam param) {
        ComparisonContext comparisonContext = new ComparisonContext();
        Map dimensionSet = param.getContext().getDimensionSet();
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, param.getContext().getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        DimensionCombination dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, param.getContext().getFormSchemeKey());
        comparisonContext.setCurrentDim(dimensionCombination);
        comparisonContext.setDataRange(param.getDataRange());
        comparisonContext.setSnapshotIds(param.getSnapshotIds());
        comparisonContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
        comparisonContext.setCurrentDataName("\u5f53\u524d\u6570\u636e");
        HashMap<String, HistoryPeriodType> historyPeriodMap = new HashMap<String, HistoryPeriodType>();
        if (param.getPeriodCodeList() != null && param.getPeriodCodeList().size() > 0) {
            for (String str : param.getPeriodCodeList()) {
                if (str.equals("LastYearPeriod")) {
                    historyPeriodMap.put("\u4e0a\u5e74\u540c\u671f", HistoryPeriodType.LASTYEAR_SAMEPERIOD);
                }
                if (str.equals("LastPeriod")) {
                    historyPeriodMap.put("\u4e0a\u4e00\u671f", HistoryPeriodType.LAST_PERIOD);
                }
                if (!str.equals("LastReturned")) continue;
                historyPeriodMap.put("\u6700\u65b0\u9000\u56de", HistoryPeriodType.LATEST_ROLLBACK_SNAPSHOT);
            }
        }
        if (historyPeriodMap.isEmpty()) {
            comparisonContext.setHistoryPeriodMap(null);
        } else {
            comparisonContext.setHistoryPeriodMap(historyPeriodMap);
        }
        List results = null;
        try {
            results = this.dataOperationService.comparison(comparisonContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (results != null) {
            for (ComparisonResult comparisonResult : results) {
                Map map = comparisonResult.getDifferenceDatas();
                for (String key : map.keySet()) {
                    List nKeys;
                    IDataRegionCompareDifference dataRegionCompareDifference = (IDataRegionCompareDifference)map.get(key);
                    if (!dataRegionCompareDifference.getRegionType().equals((Object)RegionType.FLOAT) || (nKeys = ((FloatDataRegionCompareDifference)dataRegionCompareDifference).getNaturalKeys()) == null || nKeys.isEmpty()) continue;
                    ArrayList<String> nNames = new ArrayList<String>();
                    for (String nkey : nKeys) {
                        try {
                            FieldDefine kField = this.iRunTimeViewController.queryFieldDefine(nkey);
                            nNames.add(nkey + ";" + kField.getTitle());
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    ((FloatDataRegionCompareDifference)dataRegionCompareDifference).setNaturalKeys(nNames);
                }
            }
        }
        return results;
    }
}


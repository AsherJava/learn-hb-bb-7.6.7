/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.time.setting.de.FillDataType
 *  com.jiuqi.nr.time.setting.de.FillTimeDataSetting
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreParam
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService
 *  com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor
 *  com.jiuqi.nvwa.midstore.work.service.IMidstoreExcuteGetService
 *  com.jiuqi.nvwa.midstore.work.service.IMidstoreExcutePostService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore2.data.service.internal;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.time.setting.de.FillDataType;
import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreParam;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType;
import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService;
import com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor;
import com.jiuqi.nvwa.midstore.work.service.IMidstoreExcuteGetService;
import com.jiuqi.nvwa.midstore.work.service.IMidstoreExcutePostService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.monitor.ReportMidstoreProgressMonitor;
import nr.midstore2.data.service.IReportMidstoreBatchExcuteService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreBatchExcuteServiceImpl
implements IReportMidstoreBatchExcuteService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreBatchExcuteServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService midstoreSchemeInfoSevice;
    @Autowired
    private IMidstoreExcutePostService postService;
    @Autowired
    private IMidstoreExcuteGetService getService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IMidstoreSourceService sourceService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IReportMidstoreDimensionService reportDimSerice;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FillTimeDataSetting fillTimeDataSetting;
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public MidstoreResultObject batchExcuteDataGet(String midstoreSchemeKey, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        return this.batchExcuteDataGet2(midstoreSchemeKey, orgCodes, periods, dimSetMap, isDeleteEmpty, monitor, 0.1, 0.8);
    }

    @Override
    public List<MidstoreResultObject> batchExcuteDataGets(List<String> midstoreSchemeKeys, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        ArrayList<MidstoreResultObject> list = new ArrayList<MidstoreResultObject>();
        double processStart = 0.1;
        double processLen = 0.8;
        double processCur = processStart;
        BigDecimal b = new BigDecimal(processLen / (double)midstoreSchemeKeys.size());
        double processLen2 = b.setScale(2, 4).doubleValue();
        for (String midstoreSchemeKey : midstoreSchemeKeys) {
            MidstoreResultObject result = this.batchExcuteDataGet2(midstoreSchemeKey, orgCodes, periods, dimSetMap, isDeleteEmpty, monitor, processCur, processLen2);
            if (result != null) {
                result.setSchemeKey(midstoreSchemeKey);
                list.add(result);
            }
            processCur += processLen2;
            if (monitor == null || !monitor.isCancel()) continue;
            return list;
        }
        if (monitor != null) {
            monitor.progressAndMessage(0.9, "\u63a5\u6536\u6570\u636e\u5b8c\u6210");
        }
        return list;
    }

    private MidstoreResultObject batchExcuteDataGet2(String midstoreSchemeKey, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, boolean isDeleteEmpty, AsyncTaskMonitor monitor, double processStart, double processLen) throws MidstoreException {
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(midstoreSchemeKey);
        if (scheme == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728", null, midstoreSchemeKey, null, null);
        }
        if (null == scheme.getExchangeMode()) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5b9a\u4e49\u4ea4\u6362\u6a21\u5f0f\uff0c", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
        }
        if (ExchangeModeType.EXCHANGE_GET != scheme.getExchangeMode()) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u662f\u62c9\u53d6\u6570\u636e", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
        }
        if (ExchangeModeType.EXCHANGE_GET == scheme.getExchangeMode()) {
            MidstoreSchemeInfoDTO schemeInfo = this.midstoreSchemeInfoSevice.getBySchemeKey(scheme.getKey());
            MidstoreResultObject checkResult = this.checkMidstoreScheme(scheme, schemeInfo, orgCodes, dimSetMap);
            if (!checkResult.isSuccess()) {
                return checkResult;
            }
            MidstoreSourceDTO oldSource = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field");
            if (oldSource == null) {
                return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5173\u8054\u62a5\u8868\u4efb\u52a1", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            }
            String deDataTime = null;
            ReportDataSourceDTO reportSource = new ReportDataSourceDTO(oldSource);
            String taskKey = reportSource.getTaskKey();
            if (StringUtils.isEmpty((CharSequence)taskKey)) {
                return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5173\u8054\u62a5\u8868\u4efb\u52a1", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            }
            TaskDefine task = this.viewController.queryTaskDefine(taskKey);
            String nrBeginPeriod = null;
            if (StringUtils.isNotEmpty((CharSequence)schemeInfo.getFromPeriod())) {
                nrBeginPeriod = this.reportDimSerice.getNrPeriodFromDe(task.getDateTime(), schemeInfo.getFromPeriod());
            }
            String nrToPeriod = null;
            if (StringUtils.isNotEmpty((CharSequence)schemeInfo.getToPeriod())) {
                nrToPeriod = this.reportDimSerice.getNrPeriodFromDe(task.getDateTime(), schemeInfo.getToPeriod());
            }
            List<String> newPeriods = this.getNewPeriodList(schemeInfo, periods, nrBeginPeriod, nrToPeriod);
            List<String> newOrgList = this.getNewOrgList(schemeInfo, orgCodes);
            MidstoreResultObject result = new MidstoreResultObject(true, "", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            if (!schemeInfo.isAllPeriod()) {
                if (newPeriods.isEmpty()) {
                    for (String dataTime : periods) {
                        MistoreWorkResultObject workResult = new MistoreWorkResultObject(false, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185:" + dataTime);
                    }
                    return result;
                }
                if (newPeriods.size() < periods.size()) {
                    for (String dataTime : periods) {
                        if (newPeriods.contains(dataTime)) continue;
                        this.info(monitor, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185:" + dataTime);
                        MistoreWorkResultObject workResult = new MistoreWorkResultObject(false, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                    }
                }
            }
            HashMap<String, Map> periodUnitTimeOuts = new HashMap<String, Map>();
            ArrayList<String> executePeriods = new ArrayList<String>();
            Calendar instance = Calendar.getInstance();
            FillInAutomaticallyDue fillInAutomaticallyDue = task.getFillInAutomaticallyDue();
            boolean isCheckFillDate = false;
            if (fillInAutomaticallyDue != null) {
                int type = fillInAutomaticallyDue.getType();
                if (FillInAutomaticallyDue.Type.CLOSE.getValue() != type) {
                    isCheckFillDate = true;
                }
            }
            if (isCheckFillDate || !FillDateType.NONE.equals((Object)task.getFillingDateType())) {
                for (String dataTime : newPeriods) {
                    FillDataType fillTimeData = this.fillTimeDataSetting.fillTimeData(task.getKey(), dataTime, instance.getTime());
                    boolean periodIsOutTme = false;
                    if (!FillDataType.SUCCESS.equals((Object)fillTimeData)) {
                        periodIsOutTme = true;
                    }
                    String formSchemeKey = null;
                    try {
                        SchemePeriodLinkDefine periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(dataTime, task.getKey());
                        if (periodLinkDefine != null) {
                            formSchemeKey = periodLinkDefine.getSchemeKey();
                        }
                    }
                    catch (Exception e1) {
                        logger.error(e1.getMessage(), e1);
                        throw new MidstoreException((Throwable)e1);
                    }
                    String entityDimName = this.entityMetaService.getDimensionName(task.getDw());
                    HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
                    DimensionValue unitValue = new DimensionValue();
                    unitValue.setName(entityDimName);
                    unitValue.setValue(StringUtils.join(newOrgList.iterator(), (String)";"));
                    masterDims.put(entityDimName, unitValue);
                    String periodDimName = this.periodAdapter.getPeriodDimensionName();
                    DimensionValue periodValue = new DimensionValue();
                    periodValue.setName(periodDimName);
                    periodValue.setValue(dataTime);
                    masterDims.put(periodDimName, periodValue);
                    DimensionCollection masterKey = DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)formSchemeKey);
                    DimensionValueSet dataSets = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKey);
                    Map stringMsgReturnMap = this.deSetTimeProvide.batchCompareSetTime(formSchemeKey, dataSets);
                    boolean hasWriteUnits = false;
                    for (Map.Entry entry : stringMsgReturnMap.entrySet()) {
                        String unitKey = (String)entry.getKey();
                        MsgReturn msgReturn = (MsgReturn)entry.getValue();
                        if (msgReturn.isDisabled()) continue;
                        hasWriteUnits = true;
                        break;
                    }
                    periodUnitTimeOuts.put(dataTime, stringMsgReturnMap);
                    if (periodIsOutTme && !hasWriteUnits) {
                        MistoreWorkResultObject workResult = new MistoreWorkResultObject(false, fillTimeData.getMessage(), dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u68c0\u67e5\u586b\u62a5\u671f:" + fillTimeData.getMessage() + "," + dataTime);
                        continue;
                    }
                    executePeriods.add(dataTime);
                }
            } else {
                executePeriods.addAll(newPeriods);
            }
            if (monitor != null) {
                monitor.progressAndMessage(processStart, "\u63a5\u6536\u6570\u636e\u5f00\u59cb\uff1a" + scheme.getTitle());
            }
            result.setSchemeKey(midstoreSchemeKey);
            result.setSchemeCode(scheme.getCode());
            result.setSchemeTitle(scheme.getTitle());
            double curProcess = processStart;
            for (String dataTime : executePeriods) {
                MistoreWorkResultObject workResult;
                deDataTime = this.reportDimSerice.getDePeriodFromNr(task.getDateTime(), dataTime);
                ArrayList<String> excuteOrgList = new ArrayList<String>();
                ArrayList<String> excuteOrgList1 = new ArrayList<String>();
                if (newOrgList.isEmpty()) {
                    List<String> authUnitList = this.getUnitCodesByAuth(task, dataTime, MidstoreOperateType.OPERATETYPE_GET);
                    if (authUnitList.isEmpty()) {
                        workResult = new MistoreWorkResultObject(false, "\u6ca1\u6709\u5355\u4f4d\u6743\u9650", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u6ca1\u6709\u5355\u4f4d\u6743\u9650\uff1a" + dataTime + "");
                        continue;
                    }
                    excuteOrgList1.addAll(authUnitList);
                } else {
                    excuteOrgList1.addAll(newOrgList);
                }
                if (!periodUnitTimeOuts.isEmpty()) {
                    Map stringMsgReturnMap = (Map)periodUnitTimeOuts.get(dataTime);
                    for (String unitCode : excuteOrgList1) {
                        MsgReturn msgReturn = (MsgReturn)stringMsgReturnMap.get(unitCode);
                        if (msgReturn != null && msgReturn.isDisabled()) {
                            MistoreWorkResultObject workResult2 = new MistoreWorkResultObject(false, msgReturn.getMsg(), dataTime, null);
                            workResult2.setSchemeKey(scheme.getKey());
                            workResult2.setSchemeCode(scheme.getCode());
                            workResult2.setSchemeTitle(scheme.getTitle());
                            workResult2.setPeriodCode(dataTime);
                            workResult2.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                            result.getWorkResults().add(workResult2);
                            this.info(monitor, "\u68c0\u67e5\u586b\u62a5\u671f:" + dataTime + "," + msgReturn.getMsg());
                            continue;
                        }
                        excuteOrgList.add(unitCode);
                    }
                    if (excuteOrgList.isEmpty()) {
                        this.info(monitor, "\u68c0\u67e5\u586b\u62a5\u671f:\u6ca1\u6709\u6ee1\u8db3\u7684\u5355\u4f4d");
                        continue;
                    }
                } else {
                    excuteOrgList.addAll(excuteOrgList1);
                }
                MidstoreResultObject subResult = this.excuteDataGetByScheme(scheme, deDataTime, excuteOrgList, dimSetMap, isDeleteEmpty, monitor);
                if (subResult.getWorkResults().size() > 0) {
                    MistoreWorkResultObject wrokdResult = (MistoreWorkResultObject)subResult.getWorkResults().get(0);
                    wrokdResult.setPeriodCode(dataTime);
                    wrokdResult.setMidstorePeriodCode(deDataTime);
                    wrokdResult.setSchemeKey(scheme.getKey());
                    wrokdResult.setSchemeCode(scheme.getCode());
                    wrokdResult.setSchemeTitle(scheme.getTitle());
                    wrokdResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                    result.getWorkResults().add(wrokdResult);
                    this.info(monitor, "\u6570\u636e\u63a5\u6536\u5b8c\u6210\uff0c" + dataTime + "," + wrokdResult.getMessage());
                } else if (!subResult.isSuccess()) {
                    workResult = new MistoreWorkResultObject(false, subResult.getMessage(), dataTime, null);
                    workResult.setSchemeKey(scheme.getKey());
                    workResult.setSchemeCode(scheme.getCode());
                    workResult.setSchemeTitle(scheme.getTitle());
                    workResult.setPeriodCode(dataTime);
                    workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                    result.getWorkResults().add(workResult);
                    this.info(monitor, "\u6570\u636e\u63a5\u6536\u5b8c\u6210\u4f46\u6709\u9519\uff0c" + dataTime + "," + subResult.getMessage());
                }
                BigDecimal b = new BigDecimal(processLen / (double)executePeriods.size());
                double f1 = b.setScale(2, 4).doubleValue();
                curProcess += f1;
                if (monitor == null) continue;
                monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a5\u6536\uff0c\u5b8c\u6210\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle() + ",\u65f6\u671f\uff1a" + dataTime);
                if (!monitor.isCancel()) continue;
                return result;
            }
            if (monitor != null) {
                monitor.progressAndMessage(processStart + processLen, "\u63a5\u6536\u6570\u636e\u7ed3\u675f:" + scheme.getTitle());
            }
            return result;
        }
        return null;
    }

    @Override
    public MidstoreResultObject batchExcuteDataPost(String midstoreSchemeKey, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, AsyncTaskMonitor monitor) throws MidstoreException {
        return this.batchExcuteDataPost2(midstoreSchemeKey, orgCodes, periods, dimSetMap, monitor, 0.1, 0.8);
    }

    @Override
    public List<MidstoreResultObject> batchExcuteDataPosts(List<String> midstoreSchemeKeys, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, AsyncTaskMonitor monitor) throws MidstoreException {
        ArrayList<MidstoreResultObject> list = new ArrayList<MidstoreResultObject>();
        double processStart = 0.1;
        double processLen = 0.8;
        double processCur = processStart;
        BigDecimal b = new BigDecimal(processLen / (double)midstoreSchemeKeys.size());
        double processLen2 = b.setScale(2, 4).doubleValue();
        for (String midstoreSchemeKey : midstoreSchemeKeys) {
            MidstoreResultObject result = this.batchExcuteDataPost2(midstoreSchemeKey, orgCodes, periods, dimSetMap, monitor, processCur, processLen2);
            if (result != null) {
                result.setSchemeKey(midstoreSchemeKey);
                list.add(result);
            }
            processCur += processLen2;
            if (monitor == null || !monitor.isCancel()) continue;
            return list;
        }
        if (monitor != null) {
            monitor.progressAndMessage(0.9, "\u63a8\u9001\u6570\u636e\u5b8c\u6210");
        }
        return list;
    }

    private MidstoreResultObject batchExcuteDataPost2(String midstoreSchemeKey, List<String> orgCodes, List<String> periods, Map<String, DimensionValue> dimSetMap, AsyncTaskMonitor monitor, double processStart, double processLen) throws MidstoreException {
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(midstoreSchemeKey);
        if (scheme == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        if (null == scheme.getExchangeMode()) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5b9a\u4e49\u4ea4\u6362\u6a21\u5f0f\uff0c", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
        }
        if (ExchangeModeType.EXCHANGE_POST != scheme.getExchangeMode()) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u662f\u63a8\u9001\u6570\u636e", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
        }
        if (ExchangeModeType.EXCHANGE_POST == scheme.getExchangeMode()) {
            MidstoreSchemeInfoDTO schemeInfo = this.midstoreSchemeInfoSevice.getBySchemeKey(scheme.getKey());
            MidstoreResultObject checkResult = this.checkMidstoreScheme(scheme, schemeInfo, orgCodes, dimSetMap);
            if (!checkResult.isSuccess()) {
                checkResult.setSchemeKey(midstoreSchemeKey);
                checkResult.setSchemeCode(scheme.getCode());
                checkResult.setSchemeTitle(scheme.getTitle());
                return checkResult;
            }
            MidstoreSourceDTO oldSource = this.sourceService.getBySchemeAndSource(scheme.getKey(), "nr_midstore_field");
            if (oldSource == null) {
                return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5173\u8054\u62a5\u8868\u4efb\u52a1", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            }
            String deDataTime = null;
            ReportDataSourceDTO reportSource = new ReportDataSourceDTO(oldSource);
            String taskKey = reportSource.getTaskKey();
            if (StringUtils.isEmpty((CharSequence)taskKey)) {
                return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u5173\u8054\u62a5\u8868\u4efb\u52a1", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            }
            TaskDefine task = this.viewController.queryTaskDefine(taskKey);
            String nrBeginPeriod = null;
            if (StringUtils.isNotEmpty((CharSequence)schemeInfo.getFromPeriod())) {
                nrBeginPeriod = this.reportDimSerice.getNrPeriodFromDe(task.getDateTime(), schemeInfo.getFromPeriod());
            }
            String nrToPeriod = null;
            if (StringUtils.isNotEmpty((CharSequence)schemeInfo.getToPeriod())) {
                nrToPeriod = this.reportDimSerice.getNrPeriodFromDe(task.getDateTime(), schemeInfo.getToPeriod());
            }
            List<String> newPeriods = this.getNewPeriodList(schemeInfo, periods, nrBeginPeriod, nrToPeriod);
            List<String> newOrgList = this.getNewOrgList(schemeInfo, orgCodes);
            MidstoreResultObject result = new MidstoreResultObject(true, "", null, midstoreSchemeKey, scheme.getCode(), scheme.getTitle());
            if (!schemeInfo.isAllPeriod()) {
                if (newPeriods.isEmpty()) {
                    for (String dataTime : periods) {
                        MistoreWorkResultObject workResult = new MistoreWorkResultObject(false, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185:" + dataTime);
                    }
                    return result;
                }
                if (newPeriods.size() < periods.size()) {
                    for (String dataTime : newPeriods) {
                        if (newPeriods.contains(dataTime)) continue;
                        MistoreWorkResultObject workResult = new MistoreWorkResultObject(false, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime));
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u65f6\u671f\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5185:" + dataTime);
                    }
                }
            }
            if (monitor != null) {
                monitor.progressAndMessage(processStart, "\u63a8\u9001\u6570\u636e\u5f00\u59cb" + scheme.getTitle());
            }
            double curProcess = processStart;
            for (String dataTime : newPeriods) {
                MistoreWorkResultObject workResult;
                deDataTime = this.reportDimSerice.getDePeriodFromNr(task.getDateTime(), dataTime);
                String nrPeriodTitle = this.reportDimSerice.getPeriodTitle(task.getDateTime(), dataTime);
                ArrayList<String> excuteOrgList = new ArrayList<String>();
                if (newOrgList.isEmpty()) {
                    List<String> authUnitList = this.getUnitCodesByAuth(task, dataTime, MidstoreOperateType.OPERATETYPE_POST);
                    if (authUnitList.isEmpty()) {
                        workResult = new MistoreWorkResultObject(false, "\u6ca1\u6709\u5355\u4f4d\u6743\u9650", dataTime, null);
                        workResult.setSchemeKey(scheme.getKey());
                        workResult.setSchemeCode(scheme.getCode());
                        workResult.setSchemeTitle(scheme.getTitle());
                        workResult.setPeriodCode(dataTime);
                        workResult.setPeriodTitle(nrPeriodTitle);
                        result.getWorkResults().add(workResult);
                        this.info(monitor, "\u6ca1\u6709\u5355\u4f4d\u6743\u9650:" + dataTime);
                        continue;
                    }
                    excuteOrgList.addAll(authUnitList);
                } else {
                    excuteOrgList.addAll(newOrgList);
                }
                MidstoreResultObject subResult = this.excuteDataPostByScheme(scheme, deDataTime, excuteOrgList, dimSetMap, false, monitor);
                if (subResult.getWorkResults().size() > 0) {
                    MistoreWorkResultObject wrokdResult = (MistoreWorkResultObject)subResult.getWorkResults().get(0);
                    wrokdResult.setPeriodCode(dataTime);
                    wrokdResult.setMidstorePeriodCode(deDataTime);
                    wrokdResult.setSchemeKey(scheme.getKey());
                    wrokdResult.setSchemeCode(scheme.getCode());
                    wrokdResult.setSchemeTitle(scheme.getTitle());
                    wrokdResult.setPeriodTitle(nrPeriodTitle);
                    result.getWorkResults().add(wrokdResult);
                    this.info(monitor, "\u6570\u636e\u63a8\u9001\u5b8c\u6210:" + dataTime);
                } else if (!subResult.isSuccess()) {
                    workResult = new MistoreWorkResultObject(false, subResult.getMessage(), dataTime, null);
                    workResult.setPeriodCode(dataTime);
                    workResult.setMidstorePeriodCode(deDataTime);
                    workResult.setSchemeKey(scheme.getKey());
                    workResult.setSchemeCode(scheme.getCode());
                    workResult.setSchemeTitle(scheme.getTitle());
                    workResult.setPeriodTitle(nrPeriodTitle);
                    result.getWorkResults().add(workResult);
                    this.info(monitor, "\u6570\u636e\u63a8\u9001\u5b8c\u6210\u4f46\u6709\u9519:" + dataTime + "," + subResult.getMessage());
                }
                BigDecimal b = new BigDecimal(processLen / (double)newPeriods.size());
                double f1 = b.setScale(2, 4).doubleValue();
                curProcess += f1;
                if (monitor == null) continue;
                monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a8\u9001\uff0c\u5b8c\u6210\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle() + ",\u65f6\u671f\uff1a" + dataTime);
                if (monitor == null || !monitor.isCancel()) continue;
                return result;
            }
            if (monitor != null) {
                monitor.progressAndMessage(processStart + processLen, "\u63a8\u9001\u6570\u636e\u7ed3\u675f\uff1a" + scheme.getTitle());
            }
            return result;
        }
        return null;
    }

    private MidstoreResultObject excuteDataGetByScheme(MidstoreSchemeDTO scheme, String deDataTime, List<String> orgList, Map<String, DimensionValue> dimSetMap, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        ReportMidstoreProgressMonitor monitor2 = new ReportMidstoreProgressMonitor(monitor);
        MidstoreParam param = new MidstoreParam();
        if (orgList != null && !orgList.isEmpty()) {
            param.getExcuteParams().put("OrgData", String.join((CharSequence)",", orgList));
        }
        if (StringUtils.isNotEmpty((CharSequence)deDataTime)) {
            param.getExcuteParams().put("DataTime", deDataTime);
        }
        if (dimSetMap != null && !dimSetMap.isEmpty()) {
            param.getExcuteParamObjects().put("BATCHDIMSETMAP", dimSetMap);
        }
        if (isDeleteEmpty) {
            param.getExcuteParams().put("EXCUTE_DELETEEMPTY", "TRUE");
        } else {
            param.getExcuteParams().put("EXCUTE_DELETEEMPTY", "FALSE");
        }
        param.getExcuteParams().put("EXCUTE_SOURCE", "EXCUTE_SOURCE_NR_BATCH");
        param.setMidstoreSchemeId(scheme.getKey());
        MidstoreResultObject result = this.getService.excuteGetData(param, (MidstoreProgressMonitor)monitor2);
        return result;
    }

    private MidstoreResultObject excuteDataPostByScheme(MidstoreSchemeDTO scheme, String deDataTime, List<String> orgList, Map<String, DimensionValue> dimSetMap, boolean isDeleteOther, AsyncTaskMonitor monitor) throws MidstoreException {
        ReportMidstoreProgressMonitor monitor2 = new ReportMidstoreProgressMonitor(monitor);
        MidstoreParam param = new MidstoreParam();
        if (orgList != null && !orgList.isEmpty()) {
            param.getExcuteParams().put("OrgData", String.join((CharSequence)",", orgList));
        }
        if (StringUtils.isNotEmpty((CharSequence)deDataTime)) {
            param.getExcuteParams().put("DataTime", deDataTime);
        }
        if (dimSetMap != null && !dimSetMap.isEmpty()) {
            param.getExcuteParamObjects().put("BATCHDIMSETMAP", dimSetMap);
        }
        if (isDeleteOther) {
            param.getExcuteParams().put("DeleteOther", "TRUE");
        } else {
            param.getExcuteParams().put("DeleteOther", "FALSE");
        }
        param.getExcuteParams().put("EXCUTE_SOURCE", "EXCUTE_SOURCE_NR_BATCH");
        param.setMidstoreSchemeId(scheme.getKey());
        MidstoreResultObject result = this.postService.excutePostData(param, (MidstoreProgressMonitor)monitor2);
        return result;
    }

    private MidstoreResultObject checkMidstoreScheme(MidstoreSchemeDTO scheme, MidstoreSchemeInfoDTO schemeInfo, List<String> orgCodes, Map<String, DimensionValue> dimSetMap) {
        List<String> neworgCodes;
        if (schemeInfo == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03", null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
        }
        if (schemeInfo.getPublishState() != PublishStateType.PUBLISHSTATE_SUCCESS) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03", null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
        }
        if (!schemeInfo.isAllOrgData() && orgCodes != null && !orgCodes.isEmpty() && (neworgCodes = this.getNewOrgList(schemeInfo, orgCodes)).isEmpty()) {
            return new MidstoreResultObject(false, "\u7ec4\u7ec7\u673a\u6784\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u8303\u56f4\u5185", null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
        }
        return new MidstoreResultObject(true, "", null, scheme.getKey(), scheme.getCode(), scheme.getTitle());
    }

    private List<String> getNewPeriodList(MidstoreSchemeInfoDTO schemeInfo, List<String> periods, String nrBeginPeriod, String nrToPeriod) {
        ArrayList<String> newPeriods = new ArrayList<String>();
        HashSet<String> repeatChecks = new HashSet<String>();
        if (!schemeInfo.isAllPeriod()) {
            for (String nrPeriod : periods) {
                boolean isOk = true;
                if (StringUtils.isNotEmpty((CharSequence)nrBeginPeriod) && nrBeginPeriod.compareToIgnoreCase(nrPeriod) > 0) {
                    isOk = false;
                }
                if (StringUtils.isNotEmpty((CharSequence)nrToPeriod) && nrToPeriod.compareToIgnoreCase(nrPeriod) < 0) {
                    isOk = false;
                }
                if (!isOk || repeatChecks.contains(nrPeriod)) continue;
                newPeriods.add(nrPeriod);
                repeatChecks.add(nrPeriod);
            }
        } else {
            for (String nrPeriod : periods) {
                if (repeatChecks.contains(nrPeriod)) continue;
                newPeriods.add(nrPeriod);
                repeatChecks.add(nrPeriod);
            }
        }
        return newPeriods;
    }

    private List<String> getNewOrgList(MidstoreSchemeInfoDTO schemeInfo, List<String> orgCodes) {
        ArrayList<String> neworgCodes = new ArrayList<String>();
        if (orgCodes != null && !orgCodes.isEmpty()) {
            if (!schemeInfo.isAllOrgData()) {
                MidstoreOrgDataDTO midstoreDataDTO = new MidstoreOrgDataDTO();
                midstoreDataDTO.setSchemeKey(schemeInfo.getSchemeKey());
                List midOrgDatas = this.orgDataService.list(midstoreDataDTO);
                HashMap<String, MidstoreOrgDataDTO> midOrgDataDic = new HashMap<String, MidstoreOrgDataDTO>();
                for (MidstoreOrgDataDTO orgData : midOrgDatas) {
                    midOrgDataDic.put(orgData.getCode(), orgData);
                }
                for (String orgCode : orgCodes) {
                    if (!midOrgDataDic.containsKey(orgCode)) continue;
                    neworgCodes.add(orgCode);
                }
            } else {
                neworgCodes.addAll(orgCodes);
            }
        }
        return neworgCodes;
    }

    private List<String> getUnitCodesByAuth(TaskDefine task, String nrPeriodCode, MidstoreOperateType operateType) {
        ArrayList<String> unitCodeList = new ArrayList<String>();
        try {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(task.getDw());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setEntityView(entityViewDefine);
            String dateDimName = this.periodAdapter.getPeriodDimensionName();
            DimensionValueSet dimSet = new DimensionValueSet();
            dimSet.setValue(dateDimName, (Object)nrPeriodCode);
            entityQuery.setMasterKeys(dimSet);
            if (MidstoreOperateType.OPERATETYPE_GET == operateType) {
                entityQuery.setAuthorityOperations(AuthorityType.Modify);
            } else {
                entityQuery.setAuthorityOperations(AuthorityType.Read);
            }
            ExecutorContext queryContext = new ExecutorContext(this.dataRuntimeController);
            IEntityTable entityTable = entityQuery.executeReader((IContext)queryContext);
            List rows = entityTable.getAllRows();
            if (rows != null && rows.size() > 0) {
                for (IEntityRow row : rows) {
                    unitCodeList.add(row.getEntityKeyData());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return unitCodeList;
    }

    private void info(AsyncTaskMonitor monitor, String msg) {
        if (monitor != null && monitor.getBILogger() != null) {
            monitor.getBILogger().info(msg);
        }
    }
}


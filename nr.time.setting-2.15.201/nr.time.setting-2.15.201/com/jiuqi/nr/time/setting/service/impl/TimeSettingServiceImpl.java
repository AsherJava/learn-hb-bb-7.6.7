/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentity.entity.DataEntityType
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.time.setting.bean.SelectData;
import com.jiuqi.nr.time.setting.bean.SelectDataResult;
import com.jiuqi.nr.time.setting.bean.TimeSettingDao;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.bean.TimeSettingResult;
import com.jiuqi.nr.time.setting.dao.ITimeSettingDao;
import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nr.time.setting.de.TimeCommon;
import com.jiuqi.nr.time.setting.service.ITimeSettingService;
import com.jiuqi.nr.time.setting.util.MapValueComparator;
import com.jiuqi.nr.time.setting.util.TdEntityHelperOfActive;
import com.jiuqi.nr.time.setting.util.TdUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSettingServiceImpl
implements ITimeSettingService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingServiceImpl.class);
    public static final String SPLIT = ";";
    public static final byte UNIT_ALL_SUBORDINATE = 0;
    public static final byte UNIT_DIRECT_SUBORDINATE = 1;
    public static final String TASK_START_TIME = "starttime";
    public static final String TASK_TO_TIME = "toTime";
    public static final int SELF_TIME = 0;
    public static final int INHERIT_TIME = 1;
    @Autowired
    private TdUtils tdUtil;
    @Autowired
    private TimeCommon timeCommon;
    @Autowired
    private ITimeSettingDao setTimeDao;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataEntityService dataEntityService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private FillTimeDataSetting fillTimeDataSetting;
    private static final String TIME_MAIN_DIM_CACHE = "TIME_MAIN_DIM_CACHE";

    @Override
    public String createDeadlineInfo(TimeSettingInfo setTimeInfo) {
        TdEntityHelperOfActive entityHelper = new TdEntityHelperOfActive();
        ArrayList<String> inhertUnits = new ArrayList<String>();
        List<String> unitIds = setTimeInfo.getUnitIds();
        try {
            if (0 == setTimeInfo.getUnitRange()) {
                String unitId = setTimeInfo.getUnitId();
                unitIds.add(unitId);
            } else if (1 == setTimeInfo.getUnitRange()) {
                unitIds = entityHelper.getDirectSubordinate(setTimeInfo.getFormSchemeKey(), setTimeInfo.getPeriod(), setTimeInfo.getUnitId());
                inhertUnits.addAll(unitIds);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        setTimeInfo.setUnitIds(unitIds);
        String unitStr = "";
        String currUserId = this.getCurrUserId();
        Map<String, Collection<String>> grantedEntityKeys = this.tdUtil.getGrantedEntityKeys(currUserId);
        if (grantedEntityKeys != null) {
            for (Map.Entry<String, Collection<String>> entry : grantedEntityKeys.entrySet()) {
                Collection<String> entityIds = entry.getValue();
                for (String entityKeyData : entityIds) {
                    unitStr = entityKeyData + SPLIT;
                }
            }
        }
        setTimeInfo.setOperator(currUserId);
        setTimeInfo.setOperatorOfUnitId(unitStr);
        List<String> compareTime = this.compareTime(entityHelper, setTimeInfo, unitIds);
        if (compareTime != null && compareTime.size() > 0) {
            return "\u5f53\u524d\u8bbe\u7f6e\u7684\u65f6\u95f4\u5927\u4e8e\u4e0a\u7ea7\u8bbe\u7f6e\u7684\u65f6\u95f4";
        }
        List<String> distinctUnits = unitIds.stream().distinct().collect(Collectors.toList());
        this.saveAndUpdateData(entityHelper, distinctUnits, setTimeInfo);
        return "\u64cd\u4f5c\u6210\u529f";
    }

    @Override
    public List<TimeSettingResult> queryTableData(String formSchemeKey, String period) {
        TdEntityHelperOfActive entityHelper = new TdEntityHelperOfActive();
        ArrayList<TimeSettingResult> resultList = new ArrayList<TimeSettingResult>();
        try {
            List<TimeSettingInfo> queryTableData = this.setTimeDao.queryTableData(formSchemeKey, period);
            ArrayList<String> queryUnits = new ArrayList<String>();
            for (TimeSettingInfo deadTimeResult : queryTableData) {
                queryUnits.add(deadTimeResult.getUnitId());
            }
            if (queryTableData.size() > 0) {
                String entityId = this.tdUtil.getMainDimEntityId(formSchemeKey);
                for (TimeSettingInfo deadTimeResult : queryTableData) {
                    TimeSettingResult deadResult = new TimeSettingResult();
                    String unitId = deadTimeResult.getUnitId();
                    List<String> unitIdList = entityHelper.getAllSubordinate(formSchemeKey, period, unitId);
                    queryUnits.retainAll(unitIdList);
                    if (queryUnits.size() > 0) {
                        deadResult.setChildren(new ArrayList<TimeSettingResult>());
                    }
                    ArrayList<String> unitIds = new ArrayList<String>();
                    unitIds.add(unitId);
                    DimensionValueSet buildDimension = this.tdUtil.buildDimension(formSchemeKey, unitIds, period);
                    IEntityTable entityQuerySet = this.tdUtil.entityQuerySet(entityId, buildDimension, formSchemeKey);
                    List allRows = entityQuerySet.getAllRows();
                    if (allRows.size() > 0) {
                        for (IEntityRow iEntityRow : allRows) {
                            String title = iEntityRow.getTitle();
                            deadResult.setId(unitId);
                            deadResult.setUnitName(title);
                            deadResult.setDeadLineTime(deadTimeResult.getDeadLineTime());
                            deadResult.setSubmitStartTime(deadTimeResult.getSubmitStartTime());
                            deadResult.setRepayDeadline(deadTimeResult.getRepayDeadline());
                        }
                    }
                    resultList.add(deadResult);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<EntityViewDefine> queryAdminEntitieList(String formSchemeKey) {
        ArrayList<EntityViewDefine> entityList = new ArrayList<EntityViewDefine>();
        try {
            String entityId = this.tdUtil.getMainDimEntityId(formSchemeKey);
            EntityViewDefine viewDefine = this.viewAdapter.buildEntityView(entityId);
            entityList.add(viewDefine);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entityList;
    }

    @Override
    public List<TimeSettingResult> queryUnitOfUserAuth(String formSchemeKey, String period, String parentId, String unitName) {
        TdEntityHelperOfActive entityHelper = new TdEntityHelperOfActive();
        ArrayList<TimeSettingResult> resultList = new ArrayList<TimeSettingResult>();
        try {
            String entityId = this.tdUtil.getMainDimEntityId(formSchemeKey);
            List<IEntityRow> rootRows = null;
            if (entityId != null) {
                if (StringUtils.isNotEmpty((String)unitName)) {
                    List<IEntityRow> directChildrenData = entityHelper.getEntityRow(formSchemeKey, period);
                    for (IEntityRow iEntityRow : directChildrenData) {
                        String id = iEntityRow.getEntityKeyData();
                        String title = iEntityRow.getTitle();
                        if (!StringUtils.isNotEmpty((String)unitName) || !title.contains(unitName)) continue;
                        TimeSettingResult timeSettingResult = this.timeSettingResult(entityHelper, id, title, formSchemeKey, period, entityId);
                        resultList.add(timeSettingResult);
                    }
                } else {
                    rootRows = StringUtils.isNotEmpty((String)parentId) ? entityHelper.getDirectChildrenData(formSchemeKey, period, parentId) : entityHelper.getRootData(formSchemeKey, period);
                    if (rootRows.size() > 0) {
                        for (IEntityRow iEntityRow : rootRows) {
                            String id = iEntityRow.getEntityKeyData();
                            String title = iEntityRow.getTitle();
                            TimeSettingResult timeSettingResult = this.timeSettingResult(entityHelper, id, title, formSchemeKey, period, entityId);
                            resultList.add(timeSettingResult);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    private TimeSettingResult timeSettingResult(TdEntityHelperOfActive entityHelper, String id, String title, String formSchemeKey, String period, String entityKey) {
        TimeSettingResult deadTimeResult = new TimeSettingResult();
        try {
            TimeSettingInfo unit;
            deadTimeResult.setId(id);
            deadTimeResult.setUnitName(title);
            List<String> unitIdList = entityHelper.getDirectSubordinate(formSchemeKey, period, id);
            if (unitIdList.size() > 0) {
                deadTimeResult.setLeafNode(false);
                deadTimeResult.setExpanded(true);
            } else {
                deadTimeResult.setLeafNode(true);
                deadTimeResult.setExpanded(false);
            }
            String parent = entityHelper.getParentId(formSchemeKey, period, id);
            if (null != parent) {
                deadTimeResult.setParentId(parent);
            }
            if (null != (unit = this.getDeadlineInfoOfUnit(formSchemeKey, period, id))) {
                deadTimeResult.setDeadLineTime(unit.getDeadLineTime());
                deadTimeResult.setRepayDeadline(unit.getRepayDeadline());
                deadTimeResult.setSubmitStartTime(unit.getSubmitStartTime());
                deadTimeResult.setDefaultColor(unit.isDefaultColor());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return deadTimeResult;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TimeSettingInfo getDeadlineInfoOfUnit(String formSchemeKey, String period, String unitId) {
        TdEntityHelperOfActive entityHelper = new TdEntityHelperOfActive();
        TimeSettingInfo deadTime = new TimeSettingInfo();
        List<DimensionValueSet> setReportDimension = this.setReportDimension(formSchemeKey, unitId, period);
        ArrayList<TimeSettingInfo> queryTableData = new ArrayList<TimeSettingInfo>();
        for (DimensionValueSet dimensionValueSet : setReportDimension) {
            List<TimeSettingInfo> timeDatas = this.setTimeDao.queryTableData(formSchemeKey, dimensionValueSet);
            queryTableData.addAll(timeDatas);
        }
        try {
            Calendar endfillTimeData;
            boolean rootNode = false;
            String parentId = entityHelper.getParentId(formSchemeKey, period, unitId);
            if ("-".equals(parentId)) {
                rootNode = true;
                deadTime.setRootNode(true);
            } else {
                rootNode = false;
                deadTime.setRootNode(false);
            }
            if (queryTableData.size() == 0) {
                queryTableData = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
            }
            if (queryTableData != null && queryTableData.size() > 0) {
                Map<String, String> userMap = this.timeCommon.userMap(queryTableData);
                if (queryTableData.size() == 1) {
                    TimeSettingInfo deadlineInfo = (TimeSettingInfo)queryTableData.get(0);
                    List<TimeSettingInfo> queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
                    if (queryParentTime != null && queryParentTime.size() > 0) {
                        deadlineInfo.setParentTime(queryParentTime.get(0));
                    }
                    deadlineInfo.setDefaultColor(false);
                    deadlineInfo.setRootNode(rootNode);
                    return deadlineInfo;
                }
                Map<Integer, String> unitOfParent = this.timeCommon.getUnitOfParent(formSchemeKey, period, userMap);
                if (unitOfParent.size() > 0) {
                    Object maxValue = MapValueComparator.getMaxValue(unitOfParent);
                    if (maxValue == null) return deadTime;
                    String unit = unitOfParent.get(maxValue);
                    if (null != unit) {
                        TimeSettingInfo deadlineInfo;
                        Iterator iterator = queryTableData.iterator();
                        do {
                            if (!iterator.hasNext()) return deadTime;
                        } while (!(deadlineInfo = (TimeSettingInfo)iterator.next()).getOperatorOfUnitId().contains(unit));
                        deadlineInfo.setDefaultColor(false);
                        List<TimeSettingInfo> queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
                        if (queryParentTime != null && queryParentTime.size() > 0) {
                            deadlineInfo.setParentTime(queryParentTime.get(0));
                        }
                        deadlineInfo.setRootNode(rootNode);
                        return deadlineInfo;
                    }
                    Iterator iterator = queryTableData.iterator();
                    if (!iterator.hasNext()) return deadTime;
                    TimeSettingInfo deadlineInfo = (TimeSettingInfo)iterator.next();
                    deadlineInfo.setDefaultColor(false);
                    List<TimeSettingInfo> queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
                    if (queryParentTime != null && queryParentTime.size() > 0) {
                        deadlineInfo.setParentTime(queryParentTime.get(0));
                    }
                    deadlineInfo.setRootNode(rootNode);
                    return deadlineInfo;
                }
                TimeSettingInfo deadlineInfo = (TimeSettingInfo)queryTableData.get(0);
                deadlineInfo.setDefaultColor(false);
                List<TimeSettingInfo> queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
                if (queryParentTime != null && queryParentTime.size() > 0) {
                    deadlineInfo.setParentTime(queryParentTime.get(0));
                }
                deadlineInfo.setRootNode(rootNode);
                return deadlineInfo;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            FormSchemeDefine formScheme = this.tdUtil.getFormScheme(formSchemeKey);
            Calendar startFillTimeData = this.fillTimeDataSetting.getStartFillTimeData(formScheme.getTaskKey(), period, null);
            if (startFillTimeData != null) {
                Date time = startFillTimeData.getTime();
                deadTime.setSubmitStartTime(dateFormat.format(time) + " 00:00:00");
                deadTime.setDefaultColor(true);
                deadTime.setDefaultTime(true);
            }
            if ((endfillTimeData = this.fillTimeDataSetting.getEndFillTimeData(formScheme.getTaskKey(), period, null)) == null) return deadTime;
            Date time2 = endfillTimeData.getTime();
            deadTime.setDeadLineTime(dateFormat.format(time2) + " 23:59:59");
            deadTime.setDefaultColor(true);
            deadTime.setDefaultTime(true);
            return deadTime;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return deadTime;
    }

    @Override
    public TimeSettingInfo queryParent(String formSchemeKey, String period, String unitId) {
        List<TimeSettingInfo> queryParentTime;
        TdEntityHelperOfActive entityHelper = new TdEntityHelperOfActive();
        TimeSettingInfo deadTime = new TimeSettingInfo();
        List<DimensionValueSet> setReportDimension = this.setReportDimension(formSchemeKey, unitId, period);
        ArrayList<TimeSettingInfo> queryTableData = new ArrayList<TimeSettingInfo>();
        for (DimensionValueSet dimensionValueSet : setReportDimension) {
            queryTableData.addAll(this.setTimeDao.queryTableData(formSchemeKey, dimensionValueSet));
        }
        if (queryTableData != null && queryTableData.size() > 0) {
            deadTime = (TimeSettingInfo)queryTableData.get(0);
            queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
            if (queryParentTime != null && queryParentTime.size() > 0) {
                deadTime.setParentTime(queryParentTime.get(0));
            }
        } else {
            queryParentTime = this.queryParentTime(entityHelper, unitId, formSchemeKey, period, 0);
            if (queryParentTime != null && queryParentTime.size() > 0) {
                TimeSettingInfo timeSettingInfo = queryParentTime.get(0);
                deadTime.setParentTime(timeSettingInfo);
            }
        }
        return deadTime;
    }

    private List<TimeSettingInfo> queryParentTime(TdEntityHelperOfActive entityHelper, String unitId, String formSchemeKey, String period, int num) {
        List<TimeSettingInfo> queryTableData;
        block7: {
            queryTableData = new ArrayList<TimeSettingInfo>();
            try {
                String parentId = entityHelper.getParentId(formSchemeKey, period, unitId);
                if (parentId != null && !unitId.equals(parentId)) {
                    List<DimensionValueSet> reportDimension = this.setReportDimension(formSchemeKey, parentId, period);
                    for (DimensionValueSet dimensionValueSet : reportDimension) {
                        queryTableData.addAll(this.setTimeDao.queryTableData(formSchemeKey, dimensionValueSet));
                    }
                    if (queryTableData.size() == 0) {
                        if ((queryTableData = this.queryParentTime(entityHelper, parentId, formSchemeKey, period, ++num)).size() > 0) {
                            return queryTableData;
                        }
                        if (num > 25) {
                            logger.info("\u3010" + unitId + "\u3011\u81ea\u8eab\u5b58\u5728\u5faa\u73af\u94fe\uff0c\u5efa\u8bae\u68c0\u67e5");
                            return queryTableData;
                        }
                    }
                    break block7;
                }
                return queryTableData;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return queryTableData;
    }

    private List<String> compareTime(TdEntityHelperOfActive entityHelper, TimeSettingInfo setlineInfo, List<String> unitIds) {
        ArrayList<String> ids = new ArrayList<String>();
        String currDeadLineTime = setlineInfo.getDeadLineTime();
        try {
            for (String unit : unitIds) {
                int compareTo;
                String parentId = entityHelper.getParentId(setlineInfo.getFormSchemeKey(), setlineInfo.getPeriod(), unit);
                List<TimeSettingInfo> queryParentTime = this.queryParentTime(entityHelper, parentId, setlineInfo.getFormSchemeKey(), setlineInfo.getPeriod(), 0);
                if (queryParentTime.size() <= 0 || queryParentTime == null || currDeadLineTime == null || queryParentTime.get(0).getDeadLineTime() == null || (compareTo = currDeadLineTime.compareTo(queryParentTime.get(0).getDeadLineTime())) <= 0) continue;
                ids.add(unit);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ids;
    }

    @Override
    public void saveUnitTime(TimeSettingInfo deadlineInfo) {
        ArrayList<TimeSettingDao> timeSettingDaos = new ArrayList<TimeSettingDao>();
        List<DimensionValueSet> dimension = this.setReportDimension(deadlineInfo.getFormSchemeKey(), deadlineInfo.getUnitId(), deadlineInfo.getPeriod());
        for (DimensionValueSet dimensionValueSet : dimension) {
            TimeSettingDao timeSettingDao = new TimeSettingDao();
            timeSettingDao.setFormSchemeKey(deadlineInfo.getFormSchemeKey());
            timeSettingDao.setDimensionValueSet(dimensionValueSet);
            timeSettingDao.setSubmitStartTime(deadlineInfo.getSubmitStartTime());
            timeSettingDao.setDeadLineTime(deadlineInfo.getDeadLineTime());
            timeSettingDao.setRepayDeadline(deadlineInfo.getRepayDeadline());
            timeSettingDao.setOperator(deadlineInfo.getOperator());
            timeSettingDao.setOperatorOfUnitId(deadlineInfo.getOperatorOfUnitId());
            timeSettingDao.setUnitLevel(deadlineInfo.getUnitLevel());
            timeSettingDaos.add(timeSettingDao);
        }
        this.setTimeDao.saveDeadlineInfo(timeSettingDaos);
    }

    private void saveAndUpdateData(TdEntityHelperOfActive entityHelper, List<String> unitIds, TimeSettingInfo setTimeInfo) {
        if (unitIds.size() > 0) {
            if (1 == setTimeInfo.getUnitRange()) {
                this.insertAndUpdate(entityHelper, setTimeInfo, unitIds, 1);
            } else {
                this.insertAndUpdate(entityHelper, setTimeInfo, unitIds, 0);
            }
        }
    }

    private void insertAndUpdate(TdEntityHelperOfActive entityHelper, TimeSettingInfo setTimeInfo, List<String> unitIds, int type) {
        ArrayList<TimeSettingDao> insertData = new ArrayList<TimeSettingDao>();
        ArrayList<TimeSettingDao> updateData = new ArrayList<TimeSettingDao>();
        if (unitIds.size() > 0) {
            for (String unitid : unitIds) {
                List<String> childrenData;
                List<DimensionValueSet> dimensions;
                List<TimeSettingInfo> queryTableData = this.queryTableData(setTimeInfo.getFormSchemeKey(), setTimeInfo.getPeriod(), unitid);
                if (queryTableData != null && queryTableData.size() > 0) {
                    if (0 == queryTableData.get(0).getUnitLevel() && queryTableData.get(0).getSubmitStartTime() != null && queryTableData.get(0).getDeadLineTime() != null && queryTableData.get(0).getSubmitStartTime().equals(setTimeInfo.getSubmitStartTime()) && queryTableData.get(0).getDeadLineTime().equals(setTimeInfo.getDeadLineTime())) continue;
                    dimensions = this.setReportDimension(setTimeInfo.getFormSchemeKey(), unitid, setTimeInfo.getPeriod());
                    for (DimensionValueSet dimension : dimensions) {
                        TimeSettingDao timeSettingDao = new TimeSettingDao();
                        timeSettingDao.setDimensionValueSet(dimension);
                        timeSettingDao.setFormSchemeKey(queryTableData.get(0).getFormSchemeKey());
                        timeSettingDao.setOperator(queryTableData.get(0).getOperator());
                        timeSettingDao.setOperatorOfUnitId(queryTableData.get(0).getOperatorOfUnitId());
                        timeSettingDao.setDeadLineTime(setTimeInfo.getDeadLineTime());
                        timeSettingDao.setSubmitStartTime(setTimeInfo.getSubmitStartTime());
                        timeSettingDao.setRepayDeadline(setTimeInfo.getRepayDeadline());
                        timeSettingDao.setUnitLevel(type);
                        updateData.add(timeSettingDao);
                    }
                } else {
                    dimensions = this.setReportDimension(setTimeInfo.getFormSchemeKey(), unitid, setTimeInfo.getPeriod());
                    for (DimensionValueSet dimensionValueSet : dimensions) {
                        TimeSettingDao timeSetting = new TimeSettingDao();
                        timeSetting.setDimensionValueSet(dimensionValueSet);
                        timeSetting.setFormSchemeKey(setTimeInfo.getFormSchemeKey());
                        timeSetting.setOperator(setTimeInfo.getOperator());
                        timeSetting.setOperatorOfUnitId(setTimeInfo.getOperatorOfUnitId());
                        timeSetting.setSubmitStartTime(setTimeInfo.getSubmitStartTime());
                        timeSetting.setDeadLineTime(setTimeInfo.getDeadLineTime());
                        timeSetting.setRepayDeadline(setTimeInfo.getRepayDeadline());
                        if (1 == setTimeInfo.getUnitRange()) {
                            timeSetting.setUnitLevel(1);
                        } else {
                            timeSetting.setUnitLevel(0);
                        }
                        insertData.add(timeSetting);
                    }
                }
                if (1 == setTimeInfo.getUnitRange() || unitIds.size() != 0 || (childrenData = entityHelper.getDirectSubordinate(setTimeInfo.getFormSchemeKey(), setTimeInfo.getPeriod(), unitid)).size() <= 0) continue;
                this.insertAndUpdate(entityHelper, setTimeInfo, childrenData, 1);
            }
        }
        if (insertData.size() > 0) {
            this.setTimeDao.saveDeadlineInfo(insertData);
        }
        if (updateData.size() > 0) {
            this.setTimeDao.updateDeadlineInfo(updateData);
        }
    }

    public DimensionValueSet getDimensionValueSet(String unitCode, TimeSettingInfo setTimeInfo) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String period = setTimeInfo.getPeriod();
        String dimName = this.tdUtil.getDwMainDimName(setTimeInfo.getFormSchemeKey());
        dimensionValueSet.setValue(dimName, (Object)unitCode);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        return dimensionValueSet;
    }

    @Override
    public boolean canOperatorEntity(String formSchemeKey, String period, String unitId) {
        return this.tdUtil.canOperateEntityData(formSchemeKey, period, unitId);
    }

    private String getCurrUserId() {
        return NpContextHolder.getContext().getIdentityId();
    }

    @Override
    public TimeSettingInfo getSetTimeInfo(String formSchemeKey, String period, String unitId, String operator) {
        Iterator<DimensionValueSet> iterator;
        List<DimensionValueSet> dimensions = this.setReportDimension(formSchemeKey, unitId, period);
        if (dimensions != null && dimensions.size() > 0 && (iterator = dimensions.iterator()).hasNext()) {
            DimensionValueSet dimensionValueSet = iterator.next();
            return this.setTimeDao.queryDeadTime(formSchemeKey, dimensionValueSet, operator);
        }
        return null;
    }

    @Override
    public List<TimeSettingInfo> queryTableData(String formSchemeKey, String period, String unitId) {
        Iterator<DimensionValueSet> iterator;
        List<DimensionValueSet> dimensions = this.setReportDimension(formSchemeKey, unitId, period);
        if (dimensions != null && dimensions.size() > 0 && (iterator = dimensions.iterator()).hasNext()) {
            DimensionValueSet dimensionValueSet = iterator.next();
            return this.setTimeDao.queryTableData(formSchemeKey, dimensionValueSet);
        }
        return null;
    }

    @Override
    public SelectDataResult distinguishData(SelectData selectData) {
        SelectDataResult selectDataResult = new SelectDataResult();
        ArrayList<String> canOperateList = new ArrayList<String>();
        ArrayList<String> noOperateList = new ArrayList<String>();
        try {
            for (String unitId : selectData.getUnitIds()) {
                boolean canOperateEntityData = this.tdUtil.canOperateEntityData(selectData.getFromSchemeKey(), selectData.getPeriod(), unitId);
                if (canOperateEntityData) {
                    canOperateList.add(unitId);
                    continue;
                }
                noOperateList.add(unitId);
            }
            selectDataResult.setCanOperateList(canOperateList);
            selectDataResult.setNoOperateList(noOperateList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return selectDataResult;
    }

    public Date[] parseFromPeriod(String periodString, String formSchemeKey) throws ParseException {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (StringUtils.isEmpty((String)periodString)) {
            return dateRegion;
        }
        String[] periodList = PeriodUtil.getTimesArr((String)periodString);
        if (periodList == null) {
            return dateRegion;
        }
        return dateRegion;
    }

    private List<DimensionValueSet> setReportDimension(String formSchemeKey, String unitId, String period) {
        Map<String, List<String>> reportDimensionValue = this.getReportDimensionValue(formSchemeKey, period);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (reportDimensionValue != null && reportDimensionValue.size() > 0) {
            for (Map.Entry<String, List<String>> value : reportDimensionValue.entrySet()) {
                dimensionValueSet.setValue(value.getKey(), value.getValue());
            }
        }
        String dimName = this.getDwDimension(formSchemeKey);
        dimensionValueSet.setValue(dimName, (Object)unitId);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimensionValueSet);
        return dimensionSetList;
    }

    private String getDwDimension(String formSchemeKey) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getDefaultExtension();
        Object object = extension.get(TIME_MAIN_DIM_CACHE);
        if (object != null && !"".equals(object)) {
            return object.toString();
        }
        String mainDimension = this.tdUtil.getDwMainDimName(formSchemeKey);
        extension.put(TIME_MAIN_DIM_CACHE, (Serializable)((Object)mainDimension));
        return mainDimension;
    }

    private Map<String, List<String>> getReportDimensionValue(String formSchemeKey, String period) {
        HashMap<String, List<String>> valueMap = new HashMap<String, List<String>>();
        List reportDimensionKey = this.formSchemeService.getReportDimensionKey(formSchemeKey);
        for (String dimKey : reportDimensionKey) {
            EntityViewDefine entityView = this.tdUtil.getEntityView(dimKey);
            String dimName = this.tdUtil.getEntityDimName(dimKey);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            try {
                List keys;
                IDataEntity iEntityTable = this.dataEntityService.getIEntityTable(entityView, this.executorContext(formSchemeKey), dimensionValueSet, formSchemeKey);
                DataEntityType type = iEntityTable.type();
                IDataEntityRow allRow = iEntityTable.getAllRow();
                if (allRow == null) continue;
                if (DataEntityType.DataEntity.equals((Object)type)) {
                    List rowList = allRow.getRowList();
                    keys = rowList.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                    valueMap.put(dimName, keys);
                    continue;
                }
                if (!DataEntityType.DataEntityAdjust.equals((Object)type)) continue;
                List adjustPeriods = allRow.getAdjustPeriod();
                keys = adjustPeriods.stream().map(e -> e.getCode()).collect(Collectors.toList());
                valueMap.put(dimName, keys);
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return valueMap;
    }

    private ExecutorContext executorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(new DimensionValueSet());
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.viewAdapter, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    @Override
    public List<TimeSettingInfo> queryTableData(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        return this.setTimeDao.queryTableData(formSchemeKey, dimensionValueSet);
    }
}


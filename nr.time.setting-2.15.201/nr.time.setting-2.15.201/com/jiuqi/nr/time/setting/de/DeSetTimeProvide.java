/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.de.FillDataType;
import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nr.time.setting.de.ITimeSettingServiceExtend;
import com.jiuqi.nr.time.setting.de.TimeCommon;
import com.jiuqi.nr.time.setting.service.ITimeSettingService;
import com.jiuqi.nr.time.setting.util.MapValueComparator;
import com.jiuqi.nr.time.setting.util.TdEntityHelper;
import com.jiuqi.nr.time.setting.util.TdUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeSetTimeProvide {
    private static final Logger logger = LogFactory.getLogger(DeSetTimeProvide.class);
    private static final String READ = "\u53ea\u8bfb";
    private static final String WRITE = "\u53ef\u5199";
    private static final String OVERDUE = "\u5df2\u8fc7\u671f";
    private static final String STARTDES = "\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb";
    private static final String DEADDES = "\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f";
    @Autowired
    private ITimeSettingService timeService;
    @Autowired
    private TdUtils tdEntityUtil;
    @Autowired
    private TdEntityHelper tdEntityHelper;
    @Autowired
    private TimeCommon timeCommon;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FillTimeDataSetting fillTimeDataSetting;
    @Autowired(required=false)
    private ITimeSettingServiceExtend timeSettingServiceExtend;

    public MsgReturn compareSetTime(String formSchemeKey, DimensionValueSet dimension) {
        MsgReturn msgReturn = new MsgReturn();
        boolean disabled = false;
        String msg = "";
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (this.timeSettingServiceExtend != null && this.timeSettingServiceExtend.enable(formScheme.getTaskKey())) {
                return this.timeSettingServiceExtend.compareTime(formSchemeKey, dimension);
            }
            boolean fillInAutoDueOpen = this.fillInAutoDueOpen(formScheme);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            FillDateType fillingDateType = taskDefine.getFillingDateType();
            if (fillInAutoDueOpen || !FillDateType.NONE.equals((Object)fillingDateType)) {
                String period = (String)dimension.getValue("DATATIME");
                String unitId = (String)dimension.getValue(this.tdEntityUtil.getDwMainDimName(formSchemeKey));
                Calendar instance = Calendar.getInstance();
                TimeSettingInfo setTime = this.getSetTime(formSchemeKey, period, unitId);
                if (setTime != null && setTime.getFormSchemeKey() != null) {
                    Calendar submitStartTime = this.convertCalender(setTime.getSubmitStartTime());
                    Calendar deadLineTime = this.convertCalender(setTime.getDeadLineTime());
                    Calendar repayDeadline = this.convertCalender(setTime.getRepayDeadline());
                    long difValue = 0L;
                    if (submitStartTime != null) {
                        difValue = instance.getTimeInMillis() - submitStartTime.getTimeInMillis();
                    }
                    if (repayDeadline != null) {
                        long difTime = repayDeadline.getTimeInMillis() - instance.getTimeInMillis();
                        if (difValue >= 0L && difTime > 0L) {
                            disabled = false;
                            msg = WRITE;
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                        } else {
                            disabled = true;
                            if (difValue < 0L) {
                                msg = STARTDES;
                            } else if (difTime < 0L) {
                                msg = DEADDES;
                            }
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                        }
                    } else if (deadLineTime != null) {
                        long difTime = deadLineTime.getTimeInMillis() - instance.getTimeInMillis();
                        if (difValue >= 0L && difTime > 0L) {
                            disabled = false;
                            msg = WRITE;
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                        } else if (difValue < 0L) {
                            disabled = true;
                            msg = STARTDES;
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                        } else {
                            disabled = true;
                            if (difValue < 0L) {
                                msg = STARTDES;
                            } else if (difTime < 0L) {
                                msg = DEADDES;
                            }
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                        }
                    } else {
                        msgReturn = this.getReturnMsg(formScheme.getTaskKey(), period, instance.getTime());
                    }
                } else {
                    msgReturn = this.getReturnMsg(formScheme.getTaskKey(), period, instance.getTime());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException(e);
        }
        return msgReturn;
    }

    private MsgReturn getReturnMsg(String taskKey, String period, Date data) {
        MsgReturn msgReturn = new MsgReturn();
        FillDataType fillTimeData = this.fillTimeDataSetting.fillTimeData(taskKey, period, data);
        if (FillDataType.SUCCESS.equals((Object)fillTimeData)) {
            msgReturn.setDisabled(false);
            msgReturn.setMsg(WRITE);
        } else {
            msgReturn.setDisabled(true);
            msgReturn.setMsg(fillTimeData.getMessage());
        }
        return msgReturn;
    }

    public Calendar queryAllowDelayDeadlineTime(FormSchemeDefine formScheme, String period) {
        return this.fillTimeDataSetting.getEndFillTimeData(formScheme.getTaskKey(), period, null);
    }

    public TimeSettingInfo getSetTime(String formSchemeKey, String period, String unitId) throws Exception {
        TimeSettingInfo setTimeInfo = this.timeService.getDeadlineInfoOfUnit(formSchemeKey, period, unitId);
        if (setTimeInfo != null && (setTimeInfo.getSubmitStartTime() != null || setTimeInfo.getDeadLineTime() != null || setTimeInfo.getRepayDeadline() != null)) {
            return setTimeInfo;
        }
        ArrayList<TimeSettingInfo> times = new ArrayList<TimeSettingInfo>();
        List<TimeSettingInfo> parentTime = this.getParentTime(formSchemeKey, period, unitId, times);
        if (parentTime != null && parentTime.size() > 0) {
            if (parentTime.size() == 1) {
                return parentTime.get(0);
            }
            Map<String, String> userMap = this.timeCommon.userMap(parentTime);
            Map<Integer, String> unitOfParent = this.timeCommon.getUnitOfParent(formSchemeKey, period, userMap);
            if (unitOfParent.size() > 0) {
                String unit;
                Object minValue = MapValueComparator.getMinValue(unitOfParent);
                if (minValue != null && null != (unit = unitOfParent.get(minValue))) {
                    for (TimeSettingInfo timeInfo : parentTime) {
                        if (!timeInfo.getOperatorOfUnitId().contains(unit)) continue;
                        timeInfo.setDefaultColor(false);
                        return timeInfo;
                    }
                }
            } else {
                TimeSettingInfo timeInfo = parentTime.get(0);
                timeInfo.setDefaultColor(false);
                return timeInfo;
            }
        }
        return setTimeInfo;
    }

    private List<TimeSettingInfo> getParentTime(String formSchemeKey, String period, String unitId, List<TimeSettingInfo> times) throws Exception {
        String parentId = this.tdEntityHelper.getParentId(formSchemeKey, period, unitId);
        if (parentId != null && ((times = this.timeService.queryTableData(formSchemeKey, period, parentId)) == null || times.size() == 0)) {
            times = this.getParentTime(formSchemeKey, period, parentId, times);
        }
        return times;
    }

    public Calendar convertCalender(String period) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (period != null) {
                Date date = sf.parse(period);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return cal;
            }
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    @Deprecated
    public Date queryTime(String formSchemeKey, DimensionValueSet dimension) {
        Date time = null;
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String period = (String)dimension.getValue("DATATIME");
            String unitId = (String)dimension.getValue(this.tdEntityUtil.getDwMainDimName(formSchemeKey));
            TimeSettingInfo setTime = this.getSetTime(formSchemeKey, period, unitId);
            if (setTime != null && setTime.getFormSchemeKey() != null && setTime.getDeadLineTime() != null) {
                Calendar deadLineTime = this.convertCalender(setTime.getDeadLineTime());
                time = deadLineTime.getTime();
            } else {
                Calendar endfillTimeData = this.fillTimeDataSetting.getEndFillTimeData(formScheme.getTaskKey(), period, null);
                time = endfillTimeData.getTime();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return time;
    }

    private boolean fillInAutoDueOpen(FormSchemeDefine formSchemeDefine) {
        boolean open = false;
        FillInAutomaticallyDue fillInAutomaticallyDue = formSchemeDefine.getFillInAutomaticallyDue();
        int type = fillInAutomaticallyDue.getType();
        if (FillInAutomaticallyDue.Type.CLOSE.getValue() != type) {
            open = true;
        }
        return open;
    }

    public Map<String, String> queryUnits(String formSchemeKey, List<DimensionValueSet> dimensionValueSetList) {
        HashMap<String, String> unitKeys = new HashMap<String, String>();
        for (DimensionValueSet dimensionValue : dimensionValueSetList) {
            MsgReturn compareSetTime = this.compareSetTime(formSchemeKey, dimensionValue);
            if (!compareSetTime.isDisabled()) continue;
            String msg = compareSetTime.getMsg();
            String dwMainDimName = this.tdEntityUtil.getDwMainDimName(formSchemeKey);
            String unitKey = dimensionValue.getValue(dwMainDimName).toString();
            unitKeys.put(unitKey, msg);
        }
        return unitKeys;
    }

    public Map<String, MsgReturn> batchCompareSetTime(String formSchemeKey, DimensionValueSet dimension) {
        HashMap<String, MsgReturn> result = new HashMap<String, MsgReturn>();
        boolean disabled = false;
        String msg = "";
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            boolean fillInAutoDueOpen = this.fillInAutoDueOpen(formScheme);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            FillDateType fillingDateType = taskDefine.getFillingDateType();
            if (fillInAutoDueOpen || !FillDateType.NONE.equals((Object)fillingDateType)) {
                String period = (String)dimension.getValue("DATATIME");
                List unitKeys = new ArrayList();
                Object value = dimension.getValue(this.tdEntityUtil.getDwMainDimName(formSchemeKey));
                if (value != null && value instanceof List) {
                    unitKeys = (List)value;
                }
                Calendar instance = Calendar.getInstance();
                Map<String, TimeSettingInfo> unitTimeSettingMap = this.getUnitTimeSettingMap(formSchemeKey, dimension);
                for (Map.Entry<String, TimeSettingInfo> entry : unitTimeSettingMap.entrySet()) {
                    String unitKey = entry.getKey();
                    TimeSettingInfo setTime = entry.getValue();
                    if (setTime != null && setTime.getFormSchemeKey() != null) {
                        MsgReturn msgReturn;
                        Calendar submitStartTime = this.convertCalender(setTime.getSubmitStartTime());
                        Calendar deadLineTime = this.convertCalender(setTime.getDeadLineTime());
                        Calendar repayDeadline = this.convertCalender(setTime.getRepayDeadline());
                        long difValue = 0L;
                        if (submitStartTime != null) {
                            difValue = instance.getTimeInMillis() - submitStartTime.getTimeInMillis();
                        }
                        if (repayDeadline != null) {
                            long difTime = repayDeadline.getTimeInMillis() - instance.getTimeInMillis();
                            if (difValue >= 0L && difTime > 0L) {
                                disabled = false;
                                msg = WRITE;
                                msgReturn = new MsgReturn();
                                msgReturn.setDisabled(disabled);
                                msgReturn.setMsg(msg);
                                result.put(unitKey, msgReturn);
                                continue;
                            }
                            disabled = true;
                            if (difValue < 0L) {
                                msg = STARTDES;
                            } else if (difTime < 0L) {
                                msg = DEADDES;
                            }
                            msgReturn = new MsgReturn();
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                            result.put(unitKey, msgReturn);
                            continue;
                        }
                        if (deadLineTime != null) {
                            long difTime = deadLineTime.getTimeInMillis() - instance.getTimeInMillis();
                            if (difValue >= 0L && difTime > 0L) {
                                disabled = false;
                                msg = WRITE;
                                msgReturn = new MsgReturn();
                                msgReturn.setDisabled(disabled);
                                msgReturn.setMsg(msg);
                                result.put(unitKey, msgReturn);
                                continue;
                            }
                            if (difValue < 0L) {
                                disabled = true;
                                msg = STARTDES;
                                msgReturn = new MsgReturn();
                                msgReturn.setDisabled(disabled);
                                msgReturn.setMsg(msg);
                                result.put(unitKey, msgReturn);
                                continue;
                            }
                            disabled = true;
                            if (difValue < 0L) {
                                msg = STARTDES;
                            } else if (difTime < 0L) {
                                msg = DEADDES;
                            }
                            msgReturn = new MsgReturn();
                            msgReturn.setDisabled(disabled);
                            msgReturn.setMsg(msg);
                            result.put(unitKey, msgReturn);
                            continue;
                        }
                        MsgReturn msgReturn2 = this.getReturnMsg(formScheme.getTaskKey(), period, instance.getTime());
                        result.put(unitKey, msgReturn2);
                        continue;
                    }
                    MsgReturn msgReturn = this.getReturnMsg(formScheme.getTaskKey(), period, instance.getTime());
                    result.put(unitKey, msgReturn);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Map<String, TimeSettingInfo> getUnitTimeSettingMap(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        String dwMainDimName = this.tdEntityUtil.getDwMainDimName(formSchemeKey);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimensionValueSet);
        String entityId = this.tdEntityUtil.getMainDimEntityId(formSchemeKey);
        IEntityTable iEntityTable = this.tdEntityUtil.entityFullQuerySet(entityId, dimensionValueSet, formSchemeKey);
        HashMap<String, Map<Integer, String>> unitKeyToParentKeyMap = new HashMap<String, Map<Integer, String>>();
        List<String> unitKeys = this.parentKeySort(dimensionSetList, iEntityTable, dwMainDimName, unitKeyToParentKeyMap);
        DimensionValueSet dims = new DimensionValueSet();
        dims.setValue("DATATIME", (Object)dimensionValueSet.getValue("DATATIME").toString());
        dims.setValue(dwMainDimName, unitKeys);
        List<TimeSettingInfo> timeSettingInfos = this.timeService.queryTableData(formSchemeKey, dims);
        Map<String, TimeSettingInfo> timeSettingInfoMap = this.caculateUnitTimeSetting(timeSettingInfos, unitKeyToParentKeyMap, dwMainDimName);
        return timeSettingInfoMap;
    }

    private Map<String, TimeSettingInfo> caculateUnitTimeSetting(List<TimeSettingInfo> timeSettingInfos, Map<String, Map<Integer, String>> unitKeyToParentKeyMap, String dwMainDimName) {
        HashMap<String, TimeSettingInfo> timeSettingInfoMap = new HashMap<String, TimeSettingInfo>();
        HashMap<String, TimeSettingInfo> unitToTimeSettingInfoMap = new HashMap<String, TimeSettingInfo>();
        if (timeSettingInfos != null && timeSettingInfos.size() > 0) {
            for (TimeSettingInfo timeSettingInfo : timeSettingInfos) {
                String unitKey = timeSettingInfo.getUnitId();
                unitToTimeSettingInfoMap.put(unitKey, timeSettingInfo);
            }
        }
        for (Map.Entry entry : unitKeyToParentKeyMap.entrySet()) {
            String currentUnitKey = (String)entry.getKey();
            Map parentKeys = (Map)entry.getValue();
            TimeSettingInfo timeSettingInfo = (TimeSettingInfo)unitToTimeSettingInfoMap.get(currentUnitKey);
            if (timeSettingInfo != null && (timeSettingInfo.getSubmitStartTime() != null || timeSettingInfo.getDeadLineTime() != null || timeSettingInfo.getRepayDeadline() != null)) {
                timeSettingInfoMap.put(currentUnitKey, timeSettingInfo);
                continue;
            }
            if (parentKeys != null && parentKeys.size() > 0) {
                Object minValue = MapValueComparator.getMinValue(parentKeys);
                if (minValue != null) {
                    String parentKey = (String)parentKeys.get(minValue);
                    TimeSettingInfo timeSettingInfo1 = (TimeSettingInfo)unitToTimeSettingInfoMap.get(parentKey);
                    TimeSettingInfo parentTimeSetting = this.getParentTimeSetting(parentKeys, unitToTimeSettingInfoMap, Integer.valueOf(minValue.toString()), timeSettingInfo1);
                    if (parentTimeSetting != null) {
                        timeSettingInfoMap.put(currentUnitKey, parentTimeSetting);
                        continue;
                    }
                    timeSettingInfoMap.put(currentUnitKey, null);
                    continue;
                }
                timeSettingInfoMap.put(currentUnitKey, null);
                continue;
            }
            timeSettingInfoMap.put(currentUnitKey, null);
        }
        return timeSettingInfoMap;
    }

    private List<String> parentKeySort(List<DimensionValueSet> dimensionSetList, IEntityTable iEntityTable, String dwMainDimName, Map<String, Map<Integer, String>> unitKeyToParentKeyMap) {
        HashSet<String> unitKeys = new HashSet<String>();
        for (DimensionValueSet dimension : dimensionSetList) {
            String unitKey = dimension.getValue(dwMainDimName).toString();
            IEntityRow byEntityKey = iEntityTable.findByEntityKey(unitKey);
            if (byEntityKey == null) continue;
            String[] parentsEntityKeyDataPath = iEntityTable.findByEntityKey(unitKey).getParentsEntityKeyDataPath();
            HashMap<Integer, String> parentMap = new HashMap<Integer, String>();
            if (parentsEntityKeyDataPath != null) {
                int orderNum = parentsEntityKeyDataPath.length;
                for (String parentKey : parentsEntityKeyDataPath) {
                    unitKeys.add(parentKey);
                    parentMap.put(orderNum, parentKey);
                    --orderNum;
                }
            }
            unitKeyToParentKeyMap.put(unitKey, parentMap);
            unitKeys.add(unitKey);
        }
        List<String> list = new ArrayList<String>();
        if (unitKeys != null && unitKeys.size() > 0) {
            list = unitKeys.stream().collect(Collectors.toList());
        }
        return list;
    }

    private TimeSettingInfo getParentTimeSetting(Map<Integer, String> parentKeys, Map<String, TimeSettingInfo> unitToTimeSettingInfoMap, Integer minValue, TimeSettingInfo timeSettingInfo) {
        if (timeSettingInfo != null && (timeSettingInfo.getSubmitStartTime() != null || timeSettingInfo.getDeadLineTime() != null || timeSettingInfo.getRepayDeadline() != null)) {
            timeSettingInfo.setDefaultColor(false);
            return timeSettingInfo;
        }
        Integer n = minValue;
        Integer n2 = minValue = Integer.valueOf(minValue + 1);
        if (minValue >= parentKeys.size()) {
            return timeSettingInfo;
        }
        this.getParentTimeSetting(parentKeys, unitToTimeSettingInfoMap, minValue, timeSettingInfo);
        return timeSettingInfo;
    }
}


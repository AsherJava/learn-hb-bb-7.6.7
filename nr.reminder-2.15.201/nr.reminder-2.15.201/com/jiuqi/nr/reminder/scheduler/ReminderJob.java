/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  org.quartz.Job
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package com.jiuqi.nr.reminder.scheduler;

import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.reminder.internal.Reminder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class ReminderJob
implements Job {
    private static final Logger log = LoggerFactory.getLogger(ReminderJob.class);
    protected Map<String, Object> dataMap;

    @Nullable
    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    @NonNull
    public abstract String getId();

    @NonNull
    public abstract String getGroupId();

    @Nullable
    public abstract String getCronExpression();

    @Nullable
    public abstract String getStarTime();

    @Nullable
    public abstract String getEndTime();

    @Nullable
    public abstract String getExecuteTime();

    public abstract void sendMsg(List<Reminder> var1, NpContextImpl var2);

    public abstract void updateState(String var1, String var2, String var3);

    protected abstract Reminder find(String var1);

    protected abstract String nextTime(String var1, int var2);

    protected abstract void updateExecuteNums(Integer var1, String var2, String var3);

    protected abstract void updateReguLarAutoReminder(String var1, String var2, String var3, String var4, String var5, int var6, String var7);

    protected abstract void deleteQuick(String var1);

    protected abstract Map<String, Map<String, ArrayList<String>>> filterUserIdByWorkFlowType(List<String> var1, String var2, List<String> var3, String var4, String var5, String var6, List<String> var7, NpContextIdentity var8, ContextUser var9, Map<String, Set<String>> var10) throws Exception;

    protected abstract Map<List<String>, Map<List<String>, String>> msgContent(String var1, String var2, List<String> var3, String var4, Map<String, Map<String, ArrayList<String>>> var5, NpContextIdentity var6, ContextUser var7, List<String> var8, Map<List<String>, Map<List<String>, String>> var9);

    protected abstract List<String> findFormKeys(String var1);

    protected abstract List<String> findUnitIds(String var1);

    protected abstract List<String> filterUnitIds(String var1, List<String> var2, String var3, NpContextIdentity var4, ContextUser var5);

    protected abstract Map<String, Set<String>> getCanFormGroupUploadIdentityMaps(List<String> var1, String var2, String var3, String var4, String var5, List<String> var6, NpContextIdentity var7, ContextUser var8);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = null;
        ArrayList<Reminder> reminderList = new ArrayList<Reminder>();
        Reminder reminder = null;
        if (context != null) {
            this.dataMap = jobDataMap = context.getMergedJobDataMap();
            String id = (String)this.dataMap.get("id");
            Object object = this.dataMap.get("content");
            String formSchemeKey = (String)this.dataMap.get("formSchemeKey");
            NpContextIdentity contextIdentity = (NpContextIdentity)this.dataMap.get("contextIdentity");
            ContextUser user = (ContextUser)this.dataMap.get("contextUser");
            String period = (String)this.dataMap.get("period");
            NpContextImpl contextImpl = new NpContextImpl();
            contextImpl.setUser(user);
            contextImpl.setIdentity((ContextIdentity)contextIdentity);
            if (object != null) {
                if (object instanceof String) {
                    String string = object.toString();
                    reminder = new Reminder();
                    reminder.setUserIds((List)this.dataMap.get("userIds"));
                    reminder.setContent(string);
                    reminder.setId(id);
                    reminder.setHandleMethod((List)this.dataMap.get("method"));
                    reminder.setCcParticipants((List)this.dataMap.get("cc"));
                    reminderList.add(reminder);
                    this.sendMsg(reminderList, contextImpl);
                } else {
                    Map content = (Map)this.dataMap.get("content");
                    String workFlowType = (String)this.dataMap.get("workFlowType");
                    if (workFlowType.equals(WorkFlowType.FORM.name()) || workFlowType.equals(WorkFlowType.GROUP.name())) {
                        List originalUserIds = (List)this.dataMap.get("userIds");
                        String originalContent = (String)this.dataMap.get("originalContent");
                        String groupRange = (String)this.dataMap.get("groupRange");
                        String formRange = (String)this.dataMap.get("formRange");
                        String taskId = (String)this.dataMap.get("taskId");
                        List<String> unitIds = this.findUnitIds(id);
                        List<String> formKeys = this.findFormKeys(id);
                        LinkedHashSet<String> hashUnitIdsSet = new LinkedHashSet<String>(unitIds);
                        ArrayList<String> unitIdsWithoutDuplicates = new ArrayList<String>(hashUnitIdsSet);
                        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(formKeys);
                        ArrayList<String> formKeysWithoutDuplicates = new ArrayList<String>(linkedHashSet);
                        Map<List<String>, Map<List<String>, String>> updateContent = new HashMap<List<String>, Map<List<String>, String>>();
                        Map<String, Set<String>> identityMaps = this.getCanFormGroupUploadIdentityMaps(originalUserIds, workFlowType, groupRange, formRange, formSchemeKey, formKeysWithoutDuplicates, contextIdentity, user);
                        try {
                            String unitId;
                            Iterator iterator = unitIdsWithoutDuplicates.iterator();
                            while (iterator.hasNext()) {
                                unitId = (String)iterator.next();
                                ArrayList<String> unitIdss = new ArrayList<String>();
                                unitIdss.add(unitId);
                                Map<String, Map<String, ArrayList<String>>> map = this.filterUserIdByWorkFlowType(originalUserIds, period, unitIdss, workFlowType, formSchemeKey, taskId, formKeysWithoutDuplicates, contextIdentity, user, identityMaps);
                                Map<String, ArrayList<String>> formMap = map.get("formKeys");
                                ArrayList<String> userList = null;
                                if (formMap != null) {
                                    userList = new ArrayList<String>(formMap.keySet());
                                }
                                Map<String, ArrayList<String>> allFilterUnitIdsMap = map.get("allFilterUnitIds");
                                List allFilterUnitIds = allFilterUnitIdsMap.get("allFilterUnitIds");
                                LinkedHashSet hashSetUnits = new LinkedHashSet(allFilterUnitIds);
                                ArrayList<String> allFilterUnitsWithoutDuplicates = new ArrayList<String>(hashSetUnits);
                                updateContent = this.msgContent(formSchemeKey, originalContent, userList, workFlowType, map, contextIdentity, user, allFilterUnitsWithoutDuplicates, updateContent);
                            }
                            iterator = unitIdsWithoutDuplicates.iterator();
                            while (iterator.hasNext()) {
                                unitId = (String)iterator.next();
                                ArrayList<String> unitIdList = new ArrayList<String>();
                                unitIdList.add(unitId);
                                Map contents = (Map)updateContent.get(unitIdList);
                                if (contents == null) continue;
                                for (Map.Entry temp : contents.entrySet()) {
                                    reminder = new Reminder();
                                    List users = (List)temp.getKey();
                                    String msgContent = (String)temp.getValue();
                                    reminder.setUserIds(users);
                                    reminder.setContent(msgContent);
                                    reminder.setId(id);
                                    reminder.setUnitId(unitId);
                                    reminder.setHandleMethod((List)this.dataMap.get("method"));
                                    reminder.setCcParticipants((List)this.dataMap.get("cc"));
                                    reminder.setFormSchemeId(formSchemeKey);
                                    reminderList.add(reminder);
                                }
                            }
                        }
                        catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    } else {
                        List<String> unitIds = this.findUnitIds(id);
                        List<String> filterUnitIds = this.filterUnitIds(formSchemeKey, unitIds, period, contextIdentity, user);
                        LinkedHashSet<String> hashUnitIdsSet = new LinkedHashSet<String>(filterUnitIds);
                        ArrayList<String> unitIdsWithoutDuplicates = new ArrayList<String>(hashUnitIdsSet);
                        for (String unitId : unitIdsWithoutDuplicates) {
                            ArrayList<String> unitIdList = new ArrayList<String>();
                            unitIdList.add(unitId);
                            Map contents = (Map)content.get(unitIdList);
                            if (contents == null) continue;
                            for (Map.Entry entry : contents.entrySet()) {
                                reminder = new Reminder();
                                List users = (List)entry.getKey();
                                String msgContent = (String)entry.getValue();
                                reminder.setUserIds(users);
                                reminder.setContent(msgContent);
                                reminder.setId(id);
                                reminder.setUnitId(unitId);
                                reminder.setHandleMethod((List)this.dataMap.get("method"));
                                reminder.setCcParticipants((List)this.dataMap.get("cc"));
                                reminder.setFormSchemeId(formSchemeKey);
                                reminderList.add(reminder);
                            }
                        }
                    }
                    this.sendMsg(reminderList, contextImpl);
                }
                Reminder currentReminder = this.find(id);
                if (currentReminder == null) {
                    log.warn("\u672a\u627e\u5230\u50ac\u62a5\u8bb0\u5f55{},{}", (Object)id, (Object)jobDataMap);
                    return;
                }
                String sendTime = null;
                String nextTime = null;
                int frequencyMode = currentReminder.getFrequencyMode();
                String regularTime = currentReminder.getRegularTime();
                int executeNums = currentReminder.getExecuteNums();
                String showSendTime = currentReminder.getShowSendTime();
                String validStartTime = currentReminder.getValidStartTime();
                String validEndTime = currentReminder.getValidEndTime();
                String periodType = currentReminder.getPeriodType();
                if (showSendTime != null && showSendTime.equals("regularTime")) {
                    if (currentReminder.getNextSendTime() != null) {
                        String sendTimeByNext;
                        sendTime = sendTimeByNext = currentReminder.getNextSendTime();
                        nextTime = this.nextTime(sendTimeByNext, frequencyMode);
                        this.updateExecuteNums(executeNums + 1, nextTime, id);
                    } else {
                        nextTime = this.nextTime(regularTime, frequencyMode);
                        sendTime = regularTime;
                        this.updateExecuteNums(executeNums + 1, nextTime, id);
                    }
                    this.updateReguLarAutoReminder(id, sendTime, nextTime, validStartTime, validEndTime, frequencyMode, periodType);
                } else {
                    String startTime = (String)this.dataMap.get("startTime");
                    String string = (String)this.dataMap.get("endTime");
                    this.updateState(id, startTime, string);
                }
            }
        }
    }
}


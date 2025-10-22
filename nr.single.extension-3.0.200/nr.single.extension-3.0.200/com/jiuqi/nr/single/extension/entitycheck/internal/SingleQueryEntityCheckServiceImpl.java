/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData
 *  nr.single.map.data.exception.SingleDataException
 */
package com.jiuqi.nr.single.extension.entitycheck.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nr.single.extension.entitycheck.SingleQueryEntityCheckService;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.exception.SingleDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleQueryEntityCheckServiceImpl
implements SingleQueryEntityCheckService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    TaskExtConfigController taskExtConfigController;

    @Override
    public List<RelationTaskAndFormScheme> getRelationTaskToFromSchemes(String taskKey, String formSchemeKey, String period) throws SingleDataException {
        List<RelationTaskAndFormScheme> list = this.getgetRelationTaskByTaskConfig(taskKey, formSchemeKey, period);
        if (list.isEmpty()) {
            list = this.getRelationTaskbyLinkTask(taskKey, formSchemeKey, period);
        }
        return list;
    }

    private List<RelationTaskAndFormScheme> getgetRelationTaskByTaskConfig(String taskKey, String formSchemeKey, String period) throws SingleDataException {
        ArrayList<RelationTaskAndFormScheme> list = new ArrayList<RelationTaskAndFormScheme>();
        Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
        if (basicCheckItems == null) {
            return list;
        }
        EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
        if (entityCheckConfigData.getEntityCheckEnable()) {
            for (int i = 0; i < entityCheckConfigData.getConfigInfos().size(); ++i) {
                FormSchemeDefine formScheme;
                ConfigInfo configInfo = (ConfigInfo)entityCheckConfigData.getConfigInfos().get(i);
                if (StringUtils.isEmpty((String)configInfo.getAssFormSchemeKey())) continue;
                Association association = configInfo.getAssociation();
                if (configInfo.getAssociation() == null) continue;
                RelationTaskAndFormScheme relationObj = new RelationTaskAndFormScheme();
                relationObj.setFormSchemeKey(configInfo.getAssFormSchemeKey());
                relationObj.setTaskKey(configInfo.getAssTaskKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(configInfo.getAssTaskKey());
                if (taskDefine != null) {
                    relationObj.setTaskTitle(taskDefine.getTitle());
                }
                if ((formScheme = this.runTimeViewController.getFormScheme(configInfo.getAssFormSchemeKey())) == null) continue;
                relationObj.setFormSchemenTitle(formScheme.getTitle());
                String contrastPeriod = this.getContrastPeriod(period, association);
                relationObj.setPeriod(contrastPeriod);
                list.add(relationObj);
            }
        }
        return list;
    }

    private List<RelationTaskAndFormScheme> getRelationTaskbyLinkTask(String taskKey, String formSchemeKey, String period) throws SingleDataException {
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<RelationTaskAndFormScheme> list = new ArrayList<RelationTaskAndFormScheme>();
        for (TaskLinkDefine taskLink : taskLinkDefines) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(taskLink.getRelatedFormSchemeKey());
            if (formSchemeDefine == null) continue;
            RelationTaskAndFormScheme relationObj = new RelationTaskAndFormScheme();
            if (StringUtils.isEmpty((String)formSchemeDefine.getTaskKey()) || StringUtils.isEmpty((String)formSchemeDefine.getTitle()) || StringUtils.isEmpty((String)formSchemeDefine.getKey())) continue;
            relationObj.setFormSchemeKey(formSchemeDefine.getKey());
            relationObj.setFormSchemenTitle(formSchemeDefine.getTitle());
            relationObj.setTaskKey(formSchemeDefine.getTaskKey());
            if (StringUtils.isEmpty((String)taskLink.getDescription())) {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
                if (taskDefine != null) {
                    relationObj.setTaskTitle(taskDefine.getTitle());
                }
            } else {
                relationObj.setTaskTitle(taskLink.getDescription());
            }
            String contrastPeriod = this.getContrastPeriod(period, taskLink);
            relationObj.setPeriod(contrastPeriod);
            list.add(relationObj);
        }
        return list;
    }

    private String getContrastPeriod(String period, TaskLinkDefine taskLink) {
        String contrastPeriod = " ";
        PeriodMatchingType periodMatchType = taskLink.getConfiguration();
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_CURRENT) {
            contrastPeriod = period;
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_NEXT) {
            periodAdapter.nextPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_OFFSET) {
            periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)taskLink.getPeriodOffset()));
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_PREVIOUS) {
            periodAdapter.priorPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_SPECIFIED) {
            contrastPeriod = taskLink.getSpecified();
        }
        return contrastPeriod;
    }

    private String getContrastPeriod(String period, Association association) {
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        String contrastPeriod = null;
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (PeriodMatchingType.PERIOD_TYPE_PREYEAR.getValue() == association.getConfiguration()) {
            periodAdapter.priorYear(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (PeriodMatchingType.PERIOD_TYPE_NEXT.getValue() == association.getConfiguration()) {
            periodAdapter.nextPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (PeriodMatchingType.PERIOD_TYPE_SPECIFIED.getValue() == association.getConfiguration()) {
            contrastPeriod = association.getSpecified();
        } else if (PeriodMatchingType.PERIOD_TYPE_OFFSET.getValue() == association.getConfiguration()) {
            periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)association.getPeriodOffset()));
            contrastPeriod = periodWrapper.toString();
        } else if (PeriodMatchingType.PERIOD_TYPE_PREVIOUS.getValue() == association.getConfiguration()) {
            periodAdapter.priorPeriod(periodWrapper);
            contrastPeriod = periodWrapper.toString();
        } else if (PeriodMatchingType.PERIOD_TYPE_CURRENT.getValue() == association.getConfiguration()) {
            contrastPeriod = period;
        }
        return contrastPeriod;
    }
}


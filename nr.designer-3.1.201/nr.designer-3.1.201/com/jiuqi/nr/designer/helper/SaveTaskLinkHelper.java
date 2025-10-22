/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.rest.vo.TaskLinkDimMappingVO;
import com.jiuqi.nr.designer.web.rest.vo.TaskLinkOrgMappingVO;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SaveTaskLinkHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;

    public void saveTaskLinkObject(TaskLinkObject[] taskLinkObj) throws JQException {
        for (TaskLinkObject taskLinkObject : taskLinkObj) {
            if (taskLinkObject.isIsNew()) {
                DesignTaskLinkDefine taskLinkDefine = this.nrDesignTimeController.createTaskLinkDefine();
                taskLinkObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                this.initTaskLinkageDefine(taskLinkObject, taskLinkDefine);
                this.nrDesignTimeController.insertTaskLinkDefine(taskLinkDefine);
                continue;
            }
            if (!StringUtils.hasText(taskLinkObject.getKey())) continue;
            if (taskLinkObject.isIsDirty()) {
                DesignTaskLinkDefine define = this.nrDesignTimeController.queryDesignByKey(taskLinkObject.getKey());
                if (null == define) continue;
                this.initTaskLinkageDefine(taskLinkObject, define);
                this.nrDesignTimeController.updateTaskLinkDefine(define);
                continue;
            }
            if (!taskLinkObject.isIsDeleted()) continue;
            this.nrDesignTimeController.deleteTaskLinkDefine(taskLinkObject.getKey());
        }
    }

    public void initTaskLinkageDefine(TaskLinkObject taskLinkObject, DesignTaskLinkDefine designTaskLinkDefine) {
        designTaskLinkDefine.setTitle(taskLinkObject.getTitle());
        if (taskLinkObject.getConfiguration() != null) {
            designTaskLinkDefine.setConfiguration(PeriodMatchingType.forValue((int)taskLinkObject.getConfiguration()));
        } else {
            designTaskLinkDefine.setConfiguration(PeriodMatchingType.PERIOD_TYPE_ALL);
        }
        designTaskLinkDefine.setCurrentFormSchemeKey(taskLinkObject.getCurrentFormSchemeKey());
        designTaskLinkDefine.setCurrentTaskFormula(taskLinkObject.getCurrentTaskFormula());
        designTaskLinkDefine.setDescription(taskLinkObject.getDescription());
        designTaskLinkDefine.setLinkAlias(taskLinkObject.getLinkAlias());
        designTaskLinkDefine.setMatching(taskLinkObject.getMatching());
        designTaskLinkDefine.setPeriodOffset(taskLinkObject.getPeriodOffset());
        designTaskLinkDefine.setRelatedFormSchemeKey(taskLinkObject.getRelatedFormSchemeKey());
        designTaskLinkDefine.setRelatedTaskFormula(taskLinkObject.getRelatedTaskFormula());
        designTaskLinkDefine.setIsHidden(taskLinkObject.getIsHidden().intValue());
        designTaskLinkDefine.setSpecified(taskLinkObject.getSpecified());
        designTaskLinkDefine.setTheoffset(taskLinkObject.getTheoffset());
        designTaskLinkDefine.setBeginTime(taskLinkObject.getBeginTime());
        designTaskLinkDefine.setEndTime(taskLinkObject.getEndTime());
        designTaskLinkDefine.setOrder(taskLinkObject.getOrder());
        designTaskLinkDefine.setOwnerLevelAndId(taskLinkObject.getOwnerLevelAndId());
        if (taskLinkObject.getMatchingType() != null) {
            designTaskLinkDefine.setMatchingType(TaskLinkMatchingType.forValue((int)taskLinkObject.getMatchingType()));
        } else {
            designTaskLinkDefine.setMatchingType(TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY);
        }
        designTaskLinkDefine.setExpressionType(TaskLinkExpressionType.forValue((int)taskLinkObject.getExpressionType()));
        if (!CollectionUtils.isEmpty(taskLinkObject.getOrgMappingRules())) {
            designTaskLinkDefine.setOrgMappingRule(taskLinkObject.getOrgMappingRules().stream().map(TaskLinkOrgMappingVO::toMappingRule).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(taskLinkObject.getDimMappingVO())) {
            designTaskLinkDefine.setDimMappingRule(this.revertRelatedDims(taskLinkObject.getDimMappingVO()));
        }
    }

    public ArrayList<TaskLinkObject> getTaskLinkObj(String formSchemeKey) {
        List defines = this.nrDesignTimeController.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<TaskLinkObject> taskLinkObjects = new ArrayList<TaskLinkObject>();
        TaskLinkObject taskLinkObject = null;
        if (defines == null) {
            Collections.unmodifiableList(taskLinkObjects);
            return (ArrayList)Collections.unmodifiableList(taskLinkObjects);
        }
        for (DesignTaskLinkDefine define : defines) {
            taskLinkObject = new TaskLinkObject();
            taskLinkObject.setTitle(define.getTitle());
            taskLinkObject.setRelatedTaskKey(define.getRelatedTaskKey());
            taskLinkObject.setCurrentFormSchemeKey(define.getCurrentFormSchemeKey());
            taskLinkObject.setRelatedFormSchemeKey(define.getRelatedFormSchemeKey());
            taskLinkObject.setCurrentTaskFormula(define.getCurrentFormula());
            taskLinkObject.setRelatedTaskFormula(define.getRelatedFormula());
            taskLinkObject.setKey(define.getKey());
            taskLinkObject.setPeriodOffset(define.getPeriodOffset());
            taskLinkObject.setMatching(define.getMatching());
            taskLinkObject.setConfiguration(define.getConfiguration().getValue());
            taskLinkObject.setLinkAlias(define.getLinkAlias());
            taskLinkObject.setDescription(define.getDescription());
            taskLinkObject.setIsHidden(define.getIsHidden());
            taskLinkObject.setSpecified(define.getSpecified());
            taskLinkObject.setTheoffset(define.getTheoffset());
            taskLinkObject.setBeginTime(define.getBeginTime());
            taskLinkObject.setOwnerLevelAndId(define.getOwnerLevelAndId());
            taskLinkObject.setMatchingType(define.getMatchingType().getValue());
            taskLinkObject.setEndTime(define.getEndTime());
            taskLinkObject.setOrder(define.getOrder());
            taskLinkObject.setExpressionType(define.getExpressionType().getValue());
            taskLinkObject.setIsNew(false);
            taskLinkObject.setIsDirty(false);
            taskLinkObject.setIsDeleted(false);
            taskLinkObject.setOrgMappingRules(define.getOrgMappingRules().stream().map(TaskLinkOrgMappingVO::new).collect(Collectors.toList()));
            taskLinkObject.setDimMappingVO(TaskLinkDimMappingVO.toMappings(define.getRelatedDims()));
            taskLinkObjects.add(taskLinkObject);
        }
        return taskLinkObjects;
    }

    private String revertRelatedDims(List<TaskLinkDimMappingVO> dimMappingVO) {
        StringBuffer string = new StringBuffer();
        for (TaskLinkDimMappingVO mapping : dimMappingVO) {
            string.append(mapping.toString());
        }
        return string.toString();
    }
}


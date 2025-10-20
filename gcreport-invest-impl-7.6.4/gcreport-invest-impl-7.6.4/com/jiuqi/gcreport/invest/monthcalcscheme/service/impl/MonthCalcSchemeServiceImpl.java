/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO
 *  com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcSchemeDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcZbMappingDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcSchemeEO;
import com.jiuqi.gcreport.invest.monthcalcscheme.service.MonthCalcSchemeService;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO;
import com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthCalcSchemeServiceImpl
implements MonthCalcSchemeService {
    @Autowired
    private MonthCalcSchemeDao monthCalcSchemeDao;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private MonthCalcZbMappingDao monthCalcZbMappingDao;

    @Override
    public void saveMonthCalcScheme(MonthCalcSchemeVO monthCalcSchemeVO) {
        MonthCalcSchemeEO monthCalcSchemeEO = new MonthCalcSchemeEO();
        BeanUtils.copyProperties(monthCalcSchemeVO, (Object)monthCalcSchemeEO);
        List taskIdsOfN = monthCalcSchemeVO.getTaskId_N();
        if (!CollectionUtils.isEmpty((Collection)taskIdsOfN)) {
            monthCalcSchemeEO.setTaskId_N(taskIdsOfN.stream().collect(Collectors.joining(";")));
        }
        if (StringUtils.isEmpty((String)monthCalcSchemeEO.getId())) {
            this.monthCalcSchemeDao.save(monthCalcSchemeEO);
        } else {
            MonthCalcSchemeEO calcSchemeEO = (MonthCalcSchemeEO)this.monthCalcSchemeDao.get((Serializable)((Object)monthCalcSchemeEO.getId()));
            if (calcSchemeEO.getTaskId_Y().equals(monthCalcSchemeEO.getTaskId_Y())) {
                this.monthCalcSchemeDao.update((BaseEntity)monthCalcSchemeEO);
            } else {
                throw new RuntimeException("\u6708\u62a5\u4efb\u52a1\u4e0d\u5141\u8bb8\u4fee\u6539");
            }
        }
    }

    @Override
    public MonthCalcSchemeVO getMonthCalcScheme(String monthCalcSchemeId) {
        return this.eoConvertVo((MonthCalcSchemeEO)this.monthCalcSchemeDao.get((Serializable)((Object)monthCalcSchemeId)));
    }

    @Override
    public List<MonthCalcSchemeVO> listMonthCalcSchemes() {
        List monthCalcSchemeEOS = this.monthCalcSchemeDao.loadAll();
        MonthCalcSchemeVO calcSchemeRootVO = new MonthCalcSchemeVO();
        calcSchemeRootVO.setId(UUIDUtils.emptyUUIDStr());
        calcSchemeRootVO.setParentId(UUIDOrderUtils.newUUIDStr());
        calcSchemeRootVO.setTitle("\u5206\u6bb5\u6295\u8d44\u65b9\u6848");
        calcSchemeRootVO.setLabel(calcSchemeRootVO.getTitle());
        calcSchemeRootVO.setLeafFlag(false);
        calcSchemeRootVO.setStartFlag(true);
        ArrayList<MonthCalcSchemeVO> monthCalcSchemeVOS = new ArrayList<MonthCalcSchemeVO>();
        monthCalcSchemeVOS.add(calcSchemeRootVO);
        calcSchemeRootVO.setChildren(monthCalcSchemeEOS.stream().map(item -> this.eoConvertVo((MonthCalcSchemeEO)((Object)item))).collect(Collectors.toList()));
        return monthCalcSchemeVOS;
    }

    @Override
    public Map<String, List<TaskVO>> getTasksOfType() {
        List taskIds = this.consolidatedTaskService.getAllBoundTasks();
        if (CollectionUtils.isEmpty((Collection)taskIds)) {
            return null;
        }
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        Map<String, List<TaskDefine>> peroidType2taskMap = allTaskDefines.stream().filter(taskDefine -> taskDefine != null && taskIds.contains(taskDefine.getKey())).collect(Collectors.groupingBy(TaskDefine::getDateTime));
        HashMap<String, List<TaskVO>> peroidType2taskVoMap = new HashMap<String, List<TaskVO>>();
        for (Map.Entry<String, List<TaskDefine>> entry : peroidType2taskMap.entrySet()) {
            String key = entry.getKey();
            List taskVOListOfType = entry.getValue().stream().map(taskDefine -> {
                TaskVO taskVO = new TaskVO();
                BeanUtils.copyProperties(taskDefine, taskVO);
                return taskVO;
            }).collect(Collectors.toList());
            peroidType2taskVoMap.put("tasksOf" + key, taskVOListOfType);
        }
        return peroidType2taskVoMap;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteMonthCalcScheme(String schemeId) {
        if (StringUtils.isEmpty((String)schemeId)) {
            return;
        }
        MonthCalcSchemeEO monthCalcSchemeEO = (MonthCalcSchemeEO)this.monthCalcSchemeDao.get((Serializable)((Object)schemeId));
        if (null == monthCalcSchemeEO) {
            return;
        }
        this.monthCalcSchemeDao.delete((BaseEntity)monthCalcSchemeEO);
        this.monthCalcZbMappingDao.delBySchemeId(schemeId);
    }

    @NotNull
    private MonthCalcSchemeVO eoConvertVo(MonthCalcSchemeEO monthCalcSchemeEO) {
        MonthCalcSchemeVO monthCalcSchemeVO = new MonthCalcSchemeVO();
        BeanUtils.copyProperties((Object)monthCalcSchemeEO, monthCalcSchemeVO);
        monthCalcSchemeVO.setParentId(UUIDUtils.emptyUUIDStr());
        monthCalcSchemeVO.setLeafFlag(true);
        monthCalcSchemeVO.setLabel(monthCalcSchemeVO.getTitle());
        monthCalcSchemeVO.setStartFlag(true);
        if (!StringUtils.isEmpty((String)monthCalcSchemeEO.getTaskId_N())) {
            monthCalcSchemeVO.setTaskId_N(Arrays.asList(monthCalcSchemeEO.getTaskId_N().split(";")));
        }
        return monthCalcSchemeVO;
    }
}


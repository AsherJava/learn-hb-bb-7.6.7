/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcSchemeDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcZbMappingDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcSchemeEO;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcZbMappingEO;
import com.jiuqi.gcreport.invest.monthcalcscheme.service.MonthCalcZbMappingService;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthCalcZbMappingServiceImpl
implements MonthCalcZbMappingService {
    @Autowired
    private MonthCalcZbMappingDao monthCalcZbMappingDao;
    @Autowired
    private MonthCalcSchemeDao monthCalcSchemeDao;

    @Override
    public PageInfo<MonthCalcZbMappingVO> listZbMappings(MonthCalcZbMappingCond cond) {
        PageInfo<MonthCalcZbMappingEO> eoPageInfo = this.monthCalcZbMappingDao.listZbMappings(cond);
        if (eoPageInfo.getSize() == 0) {
            return PageInfo.empty();
        }
        List monthCalcZbMappings = eoPageInfo.getList().stream().map(monthCalcZbMappingEO -> {
            MonthCalcZbMappingVO monthCalcZbMappingVO = new MonthCalcZbMappingVO();
            BeanUtils.copyProperties(monthCalcZbMappingEO, monthCalcZbMappingVO);
            if (StringUtils.isEmpty((String)monthCalcZbMappingEO.getZb_N())) {
                monthCalcZbMappingVO.setZb_N(new ArrayList());
            } else {
                List zbNList = (List)JsonUtils.readValue((String)monthCalcZbMappingEO.getZb_N(), (TypeReference)new TypeReference<List<String>>(){});
                monthCalcZbMappingVO.setZb_N(zbNList);
            }
            return monthCalcZbMappingVO;
        }).collect(Collectors.toList());
        return PageInfo.of(monthCalcZbMappings, (int)eoPageInfo.getSize());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveZbMappings(String monthCalcSchemeId, List<MonthCalcZbMappingVO> monthCalcZbMappingVOs) {
        List monthCalcZbMappingEOS = monthCalcZbMappingVOs.stream().map(monthCalcZbMappingVO -> {
            MonthCalcZbMappingEO monthCalcZbMappingEO = new MonthCalcZbMappingEO();
            BeanUtils.copyProperties(monthCalcZbMappingVO, (Object)monthCalcZbMappingEO);
            List zbN = monthCalcZbMappingVO.getZb_N();
            monthCalcZbMappingEO.setZb_N(JsonUtils.writeValueAsString((Object)zbN));
            return monthCalcZbMappingEO;
        }).collect(Collectors.toList());
        for (MonthCalcZbMappingEO monthCalcZbMappingEO : monthCalcZbMappingEOS) {
            if (StringUtils.isEmpty((String)monthCalcZbMappingEO.getSortOrder())) {
                monthCalcZbMappingEO.setSortOrder(OrderGenerator.newOrderShort());
            }
            if (StringUtils.isEmpty((String)monthCalcZbMappingEO.getId())) {
                monthCalcZbMappingEO.setMonthCalcSchemeId(monthCalcSchemeId);
                this.monthCalcZbMappingDao.save(monthCalcZbMappingEO);
                continue;
            }
            this.monthCalcZbMappingDao.update((BaseEntity)monthCalcZbMappingEO);
        }
    }

    @Override
    public Map<String, Object> getZbMappingCache(String taskId, int periodType) {
        MonthCalcSchemeEO monthCalcSchemeEO = this.monthCalcSchemeDao.getMonthCalcSchemeId(taskId, periodType);
        if (null == monthCalcSchemeEO) {
            return new HashMap<String, Object>();
        }
        MonthCalcZbMappingCond cond = new MonthCalcZbMappingCond();
        cond.setMonthCalcSchemeId(monthCalcSchemeEO.getId());
        cond.setPageNum(-1);
        cond.setPageSize(-1);
        PageInfo<MonthCalcZbMappingVO> pageInfo = this.listZbMappings(cond);
        List monthCalcZbMappingVOS = pageInfo.getList();
        HashMap<String, Object> zb2ZbMappingVOMap = new HashMap<String, Object>();
        monthCalcZbMappingVOS.forEach(monthCalcZbMappingVO -> {
            List zbNList;
            if (!StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_Y())) {
                zb2ZbMappingVOMap.put(monthCalcZbMappingVO.getZb_Y(), monthCalcZbMappingVO);
            }
            if (!StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_J())) {
                zb2ZbMappingVOMap.put(monthCalcZbMappingVO.getZb_J(), monthCalcZbMappingVO);
            }
            if (!StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_H())) {
                zb2ZbMappingVOMap.put(monthCalcZbMappingVO.getZb_H(), monthCalcZbMappingVO);
            }
            if (!CollectionUtils.isEmpty((Collection)(zbNList = monthCalcZbMappingVO.getZb_N()))) {
                zbNList.forEach(zbNStr -> zb2ZbMappingVOMap.put(zbNStr.split(":")[1], monthCalcZbMappingVO));
            }
        });
        return zb2ZbMappingVOMap;
    }

    @Override
    public void deleteZbMappings(List<String> monthCalcZbMappingIds) {
        if (CollectionUtils.isEmpty(monthCalcZbMappingIds)) {
            return;
        }
        this.monthCalcZbMappingDao.deleteBatchByIds(monthCalcZbMappingIds);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String opNodeId, int step) {
        MonthCalcZbMappingEO opeNode = (MonthCalcZbMappingEO)this.monthCalcZbMappingDao.get((Serializable)((Object)opNodeId));
        if (null == opeNode) {
            return;
        }
        MonthCalcZbMappingEO exeNode = step < 0 ? this.monthCalcZbMappingDao.getPreNodeBySchemeIdAndOrder(opeNode.getMonthCalcSchemeId(), opeNode.getSortOrder()) : this.monthCalcZbMappingDao.getNextNodeBySchemeIdAndOrder(opeNode.getMonthCalcSchemeId(), opeNode.getSortOrder());
        if (null == exeNode) {
            throw new BusinessRuntimeException(step < 0 ? "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u7b2c\u4e00\u6761\u4e86" : "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u6700\u540e\u4e00\u6761\u4e86");
        }
        String tempSort = opeNode.getSortOrder();
        opeNode.setSortOrder(exeNode.getSortOrder());
        exeNode.setSortOrder(tempSort);
        this.monthCalcZbMappingDao.update((BaseEntity)opeNode);
        this.monthCalcZbMappingDao.update((BaseEntity)exeNode);
    }
}


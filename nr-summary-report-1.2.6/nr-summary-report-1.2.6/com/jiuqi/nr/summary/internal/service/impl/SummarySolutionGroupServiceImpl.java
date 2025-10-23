/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionDao;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionGroupDao;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionGroupDTO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionGroupService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SummarySolutionGroupServiceImpl
implements ISummarySolutionGroupService {
    @Autowired
    private ISummarySolutionGroupDao groupDao;
    @Autowired
    private ISummarySolutionDao solutionDao;

    @Override
    public String insertSummarySolutionGroup(SummarySolutionGroupDTO groupDTO) throws SummaryCommonException {
        Assert.notNull((Object)groupDTO, "summarySolutionGroupDTO must not be null.");
        this.checkGroupTitle(groupDTO);
        this.setGroupDefaultValueForInsert(groupDTO);
        try {
            return this.groupDao.insert(Convert.summarySolutionGroupConvert.DTO2DO(groupDTO));
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_ADD_DBEXCEPTION, e.getMessage());
        }
    }

    private void checkGroupTitle(SummarySolutionGroupDTO groupDTO) throws SummaryCommonException {
        if (!StringUtils.hasLength(groupDTO.getTitle())) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_ADD_NULLTITLE);
        }
        SummarySolutionGroupDO group = this.groupDao.getByGroupAndTitle(groupDTO.getParent(), groupDTO.getTitle());
        if (group != null) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_TITLE_NOTREPEAT_CODE, groupDTO.getTitle());
        }
    }

    private void setGroupDefaultValueForInsert(SummarySolutionGroupDTO groupDTO) {
        if (!StringUtils.hasLength(groupDTO.getKey())) {
            groupDTO.setKey(UUID.randomUUID().toString());
        }
        if (groupDTO.getModifyTime() == null) {
            groupDTO.setModifyTime(Instant.now());
        }
        if (!StringUtils.hasLength(groupDTO.getOrder())) {
            groupDTO.setOrder(OrderGenerator.newOrder());
        }
    }

    @Override
    public void deleteSummarySolutionGroupByKey(String key) throws SummaryCommonException {
        Assert.notNull((Object)key, "key must not be null.");
        List<SummarySolutionDO> solutionDOS = this.solutionDao.listByGroup(key, false);
        if (!CollectionUtils.isEmpty(solutionDOS)) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_DEL_HASSOLUTION);
        }
        List<SummarySolutionGroupDO> childGroupDOS = this.groupDao.listByGroup(key);
        for (SummarySolutionGroupDO childGroupDO : childGroupDOS) {
            this.deleteSummarySolutionGroupByKey(childGroupDO.getKey());
        }
        try {
            this.groupDao.delete(key);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummarySolutionGroupByKeys(List<String> keys) throws SummaryCommonException {
        Assert.notEmpty(keys, "keys must not be null.");
        List<SummarySolutionDO> solutionDOS = this.solutionDao.listByGroups(keys, false);
        if (!CollectionUtils.isEmpty(solutionDOS)) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_DEL_HASSOLUTION);
        }
        for (String key : keys) {
            List<SummarySolutionGroupDO> childGroupDOS = this.groupDao.listByGroup(key);
            if (CollectionUtils.isEmpty(childGroupDOS)) continue;
            List<String> childGroupKeys = childGroupDOS.stream().map(SummarySolutionGroupDO::getKey).collect(Collectors.toList());
            this.deleteSummarySolutionGroupByKeys(childGroupKeys);
        }
        try {
            this.groupDao.batchDelete(keys);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void updateSummarySolutionGroup(SummarySolutionGroupDTO groupDTO) throws SummaryCommonException {
        Assert.notNull((Object)groupDTO, "summarySolutionGroupDTO must not be null.");
        Assert.notNull((Object)groupDTO.getKey(), "summarySolutionGroupDTO must not be null.");
        this.checkGroupTitle(groupDTO);
        if (groupDTO.getModifyTime() == null) {
            groupDTO.setModifyTime(Instant.now());
        }
        try {
            this.groupDao.update(Convert.summarySolutionGroupConvert.DTO2DO(groupDTO));
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SSG_UPDATE_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public SummarySolutionGroup getSummarySolutionGroupByKey(String key) {
        SummarySolutionGroupDO groupDO = this.groupDao.getByKey(key);
        return Convert.summarySolutionGroupConvert.DO2DTO(groupDO);
    }

    @Override
    public List<SummarySolutionGroupDTO> getSummarySolutionGroupsByGroup(String groupKey) {
        List<SummarySolutionGroupDO> groupDOs = this.groupDao.listByGroup(groupKey);
        return groupDOs.stream().map(groupDO -> Convert.summarySolutionGroupConvert.DO2DTO((SummarySolutionGroupDO)groupDO)).collect(Collectors.toList());
    }
}


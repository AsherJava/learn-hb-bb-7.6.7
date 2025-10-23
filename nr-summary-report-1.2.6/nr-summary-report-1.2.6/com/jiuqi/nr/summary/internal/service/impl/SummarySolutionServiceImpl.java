/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionDao;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class SummarySolutionServiceImpl
implements ISummarySolutionService {
    @Autowired
    private ISummarySolutionDao summarySolutionDao;
    @Autowired
    private IDesignSummaryReportService designReportService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    @Override
    public String insertSummarySolution(SummarySolutionDTO summarySolutionDTO) throws SummaryCommonException {
        try {
            Assert.notNull((Object)summarySolutionDTO, "summarySolutionDTO must not be null.");
            this.checkSolutionName(summarySolutionDTO);
            this.checkSolutionTitle(summarySolutionDTO);
            if (!StringUtils.hasLength(summarySolutionDTO.getKey())) {
                summarySolutionDTO.setKey(UUID.randomUUID().toString());
            }
            if (!StringUtils.hasLength(summarySolutionDTO.getOrder())) {
                summarySolutionDTO.setOrder(OrderGenerator.newOrder());
            }
            summarySolutionDTO.setModifyTime(Instant.now());
            return this.summarySolutionDao.insert(Convert.summarySolutionModelConvert.DTO2DO(summarySolutionDTO));
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_ADD_DBEXCEPTION);
        }
    }

    private void checkSolutionName(SummarySolutionDTO summarySolutionDTO) throws SummaryCommonException {
        if (!StringUtils.hasLength(summarySolutionDTO.getName())) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_ADD_NULLNAME);
        }
        SummarySolutionDO solution = this.summarySolutionDao.getByName(summarySolutionDTO.getName());
        if (solution != null) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_NAME_REPEAT, summarySolutionDTO.getName());
        }
    }

    private void checkSolutionTitle(SummarySolutionDTO summarySolutionDTO) throws SummaryCommonException {
        if (!StringUtils.hasLength(summarySolutionDTO.getTitle())) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_ADD_NULLTITLE);
        }
        SummarySolutionDO solution = this.summarySolutionDao.getByGroupAndTitle(summarySolutionDTO.getGroup(), summarySolutionDTO.getTitle());
        if (solution != null) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_TITLE_REPEAT, summarySolutionDTO.getTitle());
        }
    }

    @Override
    public void deleteSummarySolutionByKey(String key) throws SummaryCommonException {
        try {
            this.designReportService.deleteSummaryReportBySolution(key);
            this.summarySolutionDao.delete(key);
            this.designDataSchemeService.deleteDataScheme(key);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void batchDeleteSummarySolutions(List<String> keys) throws SummaryCommonException {
        for (String key : keys) {
            this.deleteSummarySolutionByKey(key);
        }
    }

    @Override
    public void updateSummarySolution(SummarySolutionDTO summarySolutionDTO) throws SummaryCommonException {
        try {
            Assert.notNull((Object)summarySolutionDTO, "summarySolutionDTO must not be null.");
            Assert.notNull((Object)summarySolutionDTO.getKey(), "summarySolutionDTO must not be null.");
            summarySolutionDTO.setModifyTime(Instant.now());
            this.summarySolutionDao.update(Convert.summarySolutionModelConvert.DTO2DO(summarySolutionDTO));
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SS_UPDATE_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public SummarySolutionDTO getSummarySolutionByKey(String key, boolean withDetail) {
        SummarySolutionDO summarySolutionDO = this.summarySolutionDao.getByKey(key, withDetail);
        return Convert.summarySolutionConvert.DO2DTO(summarySolutionDO);
    }

    @Override
    public List<SummarySolutionDTO> getSummarySolutionsByGroup(String groupKey, boolean withDetail) {
        List<SummarySolutionDO> summarySolutionDOS = this.summarySolutionDao.listByGroup(groupKey, withDetail);
        return summarySolutionDOS.stream().map(solutionDO -> Convert.summarySolutionModelConvert.DO2DTO((SummarySolutionDO)solutionDO)).collect(Collectors.toList());
    }

    @Override
    public List<SummarySolutionDTO> getSummarySolutionsByGroups(List<String> groupKeys, boolean withDetail) {
        List<SummarySolutionDO> summarySolutionDOS = this.summarySolutionDao.listByGroups(groupKeys, withDetail);
        return summarySolutionDOS.stream().map(solutionDO -> Convert.summarySolutionModelConvert.DO2DTO((SummarySolutionDO)solutionDO)).collect(Collectors.toList());
    }
}


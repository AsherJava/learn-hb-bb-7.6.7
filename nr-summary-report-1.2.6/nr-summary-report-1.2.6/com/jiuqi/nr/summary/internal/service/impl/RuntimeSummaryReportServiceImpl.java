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
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummaryReportDao;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RuntimeSummaryReportServiceImpl
implements IRuntimeSummaryReportService {
    @Autowired
    @Qualifier(value="com.jiuqi.nr.summary.internal.dao.impl.SummaryReportDaoImpl")
    private ISummaryReportDao<SummaryReportDO> summaryReportDao;

    @Override
    public String insertSummaryReport(SummaryReportDTO summaryReportDTO) throws SummaryCommonException {
        try {
            if (!StringUtils.hasLength(summaryReportDTO.getOrder())) {
                summaryReportDTO.setOrder(OrderGenerator.newOrder());
            }
            if (summaryReportDTO.getModifyTime() == null) {
                summaryReportDTO.setModifyTime(Instant.now());
            }
            return this.summaryReportDao.insert(Convert.summaryReportConvert.DTO2DO(summaryReportDTO));
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_RUNTIME_ADD_DBEXCEPTION, summaryReportDTO.getName(), e.getMessage());
        }
    }

    @Override
    public void batchInsertSummaryReports(List<SummaryReportDTO> summaryReportDTOS) {
        List reportDOS = summaryReportDTOS.stream().map(Convert.summaryReportConvert::DTO2DO).collect(Collectors.toList());
        this.summaryReportDao.batchInsert(reportDOS);
    }

    @Override
    public void deleteSummaryReportByKey(String key) throws SummaryCommonException {
        try {
            this.summaryReportDao.delete(key);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_RUNTIME_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummaryReportByKeys(List<String> keys) {
        this.summaryReportDao.batchDelete(keys);
    }

    @Override
    public void deleteSummaryReportBySolution(String solutionKey) {
    }

    @Override
    public void updateSummaryReport(SummaryReportDTO summaryReportDTO, boolean base) throws SummaryCommonException {
        try {
            this.summaryReportDao.update(Convert.summaryReportConvert.DTO2DO(summaryReportDTO), base);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_RUNTIME_UPDATE_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public SummaryReportDTO getSummaryReportByKey(String key, boolean withDetail) {
        SummaryReportDO reportDO = this.summaryReportDao.getByKey(key, withDetail);
        if (reportDO != null) {
            return Convert.summaryReportConvert.DO2DTO(reportDO);
        }
        return null;
    }

    @Override
    public List<SummaryReportDTO> getSummaryReportsByKeys(List<String> keys, boolean withDetail) {
        List<SummaryReportDO> reportDOS = this.summaryReportDao.getByKeys(keys, withDetail);
        return reportDOS.stream().map(reportDO -> Convert.summaryReportConvert.DO2DTO((SummaryReportDO)reportDO)).collect(Collectors.toList());
    }

    @Override
    public List<SummaryReportDTO> getSummaryReportsBySolution(String solutionKey, boolean withDetail) {
        List<SummaryReportDO> reportDOS = this.summaryReportDao.listBySolution(solutionKey, withDetail);
        return reportDOS.stream().map(reportDO -> Convert.summaryReportConvert.DO2DTO((SummaryReportDO)reportDO)).sorted(Comparator.comparing(SummaryReportDTO::getOrder)).collect(Collectors.toList());
    }

    @Override
    public List<SummaryReportDTO> getSummaryReportsBySolutions(List<String> solutionKeys, boolean withDetail) {
        List<SummaryReportDO> reportDOS = this.summaryReportDao.listBySolutions(solutionKeys, withDetail);
        return reportDOS.stream().map(reportDO -> Convert.summaryReportConvert.DO2DTO((SummaryReportDO)reportDO)).collect(Collectors.toList());
    }
}


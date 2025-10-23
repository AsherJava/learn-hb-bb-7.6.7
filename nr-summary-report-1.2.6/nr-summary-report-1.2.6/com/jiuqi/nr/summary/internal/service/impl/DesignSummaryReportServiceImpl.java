/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummaryReportDao;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummaryReportDTO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryReportDO;
import com.jiuqi.nr.summary.internal.entity.SummaryReportDO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignSummaryReportServiceImpl
implements IDesignSummaryReportService {
    private static final Logger logger = LoggerFactory.getLogger(DesignSummaryReportServiceImpl.class);
    @Autowired
    private ISummaryReportDao<DesignSummaryReportDO> designSummaryReportDao;
    @Autowired
    private IRuntimeSummaryReportService runtimeReportService;
    @Autowired
    private IDesignSummaryDataCellService designDataCellService;
    @Autowired
    private IRuntimeSummaryDataCellService runtimeDataCellService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insertSummaryReport(DesignSummaryReportDTO designSummaryReportDTO) throws SummaryCommonException {
        try {
            Assert.notNull((Object)designSummaryReportDTO, "designSummaryReportDTO must not be null.");
            if (!StringUtils.hasLength(designSummaryReportDTO.getKey())) {
                designSummaryReportDTO.setKey(UUID.randomUUID().toString());
            }
            if (!StringUtils.hasLength(designSummaryReportDTO.getOrder())) {
                designSummaryReportDTO.setOrder(OrderGenerator.newOrder());
            }
            this.checkReportName(designSummaryReportDTO);
            this.checkReportTitle(designSummaryReportDTO);
            designSummaryReportDTO.setModifyTime(Instant.now());
            return this.designSummaryReportDao.insert((DesignSummaryReportDO)((SummaryReportDO)Convert.designSummaryReportConvert.DTO2DO(designSummaryReportDTO)));
        }
        catch (DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.SR_ADD_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummaryReportByKey(String key) throws SummaryCommonException {
        try {
            List dataTables;
            DesignSummaryReportDO report = this.designSummaryReportDao.getByKey(key, false);
            this.designDataCellService.deleteSummaryDataCellByReport(key);
            this.designSummaryReportDao.delete(key);
            this.runtimeDataCellService.deleteSummaryDataCellByReport(key);
            this.runtimeReportService.deleteSummaryReportByKey(key);
            DesignDataGroup dataGroup = this.designDataSchemeService.getDataGroup(key);
            if (dataGroup != null && !CollectionUtils.isEmpty(dataTables = this.designDataSchemeService.getDataTableByGroup(key))) {
                List<String> dataTableCodes = dataTables.stream().map(Basic::getCode).collect(Collectors.toList());
                this.deleteReportData(dataTableCodes);
                this.designDataSchemeService.deleteDataGroup(key);
                this.dataSchemeDeployService.deployDataScheme(report.getSummarySolutionKey(), progressItem -> {}, null);
            }
        }
        catch (DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.SR_DES_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummaryReportByKeys(List<String> reportKeys) {
        if (!CollectionUtils.isEmpty(reportKeys)) {
            List<DesignSummaryReportDO> reports = this.designSummaryReportDao.getByKeys(reportKeys, false);
            Set<String> solutionKeys = reports.stream().collect(Collectors.groupingBy(SummaryReportDO::getSummarySolutionKey)).keySet();
            this.designDataCellService.deleteSummaryDataCellByReports(reportKeys);
            this.designSummaryReportDao.batchDelete(reportKeys);
            this.runtimeDataCellService.deleteSummaryDataCellByReports(reportKeys);
            this.runtimeReportService.deleteSummaryReportByKeys(reportKeys);
            List dataGroups = this.designDataSchemeService.getDataGroups(reportKeys);
            if (!CollectionUtils.isEmpty(dataGroups)) {
                ArrayList<String> dataTableCodes = new ArrayList<String>();
                for (DesignDataGroup dataGroup : dataGroups) {
                    List dataTables = this.designDataSchemeService.getDataTableByGroup(dataGroup.getKey());
                    if (CollectionUtils.isEmpty(dataTables)) continue;
                    List tableCodes = dataTables.stream().map(Basic::getCode).collect(Collectors.toList());
                    dataTableCodes.addAll(tableCodes);
                }
                this.deleteReportData(dataTableCodes);
            }
            this.designDataSchemeService.deleteDataGroups(reportKeys);
            for (String solutionKey : solutionKeys) {
                this.dataSchemeDeployService.deployDataScheme(solutionKey, progressItem -> {}, null);
            }
        }
    }

    private void deleteReportData(List<String> tableCodes) {
        String[] sqls = new String[tableCodes.size()];
        for (int i = 0; i < tableCodes.size(); ++i) {
            String sql;
            sqls[i] = sql = "delete from " + tableCodes.get(i);
        }
        this.jdbcTemplate.batchUpdate(sqls);
    }

    @Override
    public void deleteSummaryReportBySolution(String solutionKey) {
        List<DesignSummaryReportDO> reportDOS = this.designSummaryReportDao.listBySolution(solutionKey, false);
        List<String> reportkeys = reportDOS.stream().map(SummaryReportDO::getKey).collect(Collectors.toList());
        this.deleteSummaryReportByKeys(reportkeys);
    }

    @Override
    public void updateSummaryReport(DesignSummaryReportDTO designSummaryReportDTO, boolean base) throws SummaryCommonException {
        try {
            Assert.notNull((Object)designSummaryReportDTO, "designSummaryReportDTO must not be null.");
            Assert.notNull((Object)designSummaryReportDTO.getKey(), "designSummaryReportKey must not be null.");
            this.checkReportTitle(designSummaryReportDTO);
            if (designSummaryReportDTO.getModifyTime() == null) {
                designSummaryReportDTO.setModifyTime(Instant.now());
            }
            this.designSummaryReportDao.update((DesignSummaryReportDO)((SummaryReportDO)Convert.designSummaryReportConvert.DTO2DO(designSummaryReportDTO)), base);
        }
        catch (DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.SR_UPDATE_DBEXCEPTION, e.getMessage());
        }
    }

    private void checkReportName(DesignSummaryReportDTO designSummaryReportDTO) throws SummaryCommonException {
        DesignSummaryReportDO reportDOByCode = this.designSummaryReportDao.getByCode(designSummaryReportDTO.getName());
        if (reportDOByCode != null) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_NAME_ALREADY_EXISTS, designSummaryReportDTO.getName());
        }
    }

    private void checkReportTitle(DesignSummaryReportDTO designSummaryReportDTO) throws SummaryCommonException {
        DesignSummaryReportDO reportDOS = this.designSummaryReportDao.getBySolutionAndTitle(designSummaryReportDTO.getSummarySolutionKey(), designSummaryReportDTO.getTitle());
        if (reportDOS != null && !reportDOS.getKey().equals(designSummaryReportDTO.getKey())) {
            throw new SummaryCommonException(SummaryErrorEnum.SR_TITLE_NOTREPEAT_CODE, designSummaryReportDTO.getTitle());
        }
    }

    @Override
    public DesignSummaryReportDTO getSummaryReportByKey(String key, boolean withDetail) {
        DesignSummaryReportDO designSummaryReportDO = this.designSummaryReportDao.getByKey(key, withDetail);
        return Convert.designSummaryReportConvert.DO2DTO(designSummaryReportDO);
    }

    @Override
    public List<DesignSummaryReportDTO> getSummaryReportsBySolution(String solutionKey, boolean withDetail) {
        List<DesignSummaryReportDO> designSummaryReportDOS = this.designSummaryReportDao.listBySolution(solutionKey, withDetail);
        return designSummaryReportDOS.stream().map(reportDO -> Convert.designSummaryReportConvert.DO2DTO((DesignSummaryReportDO)reportDO)).sorted(Comparator.comparing(SummaryReportDTO::getOrder)).collect(Collectors.toList());
    }

    @Override
    public void moveSummaryReport(String solutionKey, String key, int direction) throws SummaryCommonException {
        List<DesignSummaryReportDTO> sumReports = this.getSummaryReportsBySolution(solutionKey, false);
        int index = sumReports.stream().map(SummaryReportDTO::getKey).collect(Collectors.toList()).indexOf(key);
        DesignSummaryReportDTO currSumReport = sumReports.get(index);
        if (direction == 0) {
            if (index > 0) {
                DesignSummaryReportDTO beforeSumReport = sumReports.get(index - 1);
                String beforeOrderTemp = beforeSumReport.getOrder();
                String currOrderTemp = currSumReport.getOrder();
                currSumReport.setOrder(beforeOrderTemp);
                beforeSumReport.setOrder(currOrderTemp);
                this.updateSummaryReport(currSumReport, true);
                this.updateSummaryReport(beforeSumReport, true);
                this.runtimeReportService.updateSummaryReport(currSumReport, true);
                this.runtimeReportService.updateSummaryReport(beforeSumReport, true);
            }
        } else if (index < sumReports.size() - 1) {
            DesignSummaryReportDTO afterSumReport = sumReports.get(index + 1);
            String afterOrderTemp = afterSumReport.getOrder();
            String currOrderTemp = currSumReport.getOrder();
            currSumReport.setOrder(afterOrderTemp);
            afterSumReport.setOrder(currOrderTemp);
            this.updateSummaryReport(currSumReport, true);
            this.updateSummaryReport(afterSumReport, true);
            this.runtimeReportService.updateSummaryReport(currSumReport, true);
            this.runtimeReportService.updateSummaryReport(afterSumReport, true);
        }
    }
}


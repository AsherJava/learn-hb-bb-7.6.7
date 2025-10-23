/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.SummaryFormula;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.internal.dao.ISummaryFormulaDao;
import com.jiuqi.nr.summary.internal.dto.SummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RuntimeSummaryFormulaServiceImpl
implements IRuntimeSummaryFormulaService {
    private static final int BATCH_SIZE = 1000;
    @Autowired
    private ISummaryFormulaDao<SummaryFormulaDO> summaryFormulaDao;

    @Override
    public String insertSummaryFormula(SummaryFormulaDTO summaryFormulaDTO) throws DBParaException {
        return this.summaryFormulaDao.insert(Convert.summaryFormulaConvert.DTO2DO(summaryFormulaDTO));
    }

    @Override
    public void batchInsertSummaryFormula(List<SummaryFormulaDTO> formulaDTOs) {
        if (!CollectionUtils.isEmpty(formulaDTOs)) {
            int count = formulaDTOs.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<SummaryFormulaDTO> subList = formulaDTOs.subList(i * 1000, i * 1000 + n);
                List formulaDOs = subList.stream().map(formulaDTO -> Convert.summaryFormulaConvert.DTO2DO((SummaryFormulaDTO)formulaDTO)).collect(Collectors.toList());
                this.summaryFormulaDao.batchInsert(formulaDOs);
            }
        }
    }

    @Override
    public void deleteSummaryFormulaByKey(String key) throws DBParaException {
        this.summaryFormulaDao.delete(key);
    }

    @Override
    public void deleteSummaryFormulaByReport(String reportKey) throws DBParaException {
        this.summaryFormulaDao.deleteByReport(reportKey);
    }

    @Override
    public void deleteSummaryFormulaBySolution(String solutionKey) throws DBParaException {
        this.summaryFormulaDao.deleteBySolution(solutionKey);
    }

    @Override
    public void updateSummaryFormula(SummaryFormulaDTO summaryFormulaDTO) throws DBParaException {
        this.summaryFormulaDao.update(Convert.summaryFormulaConvert.DTO2DO(summaryFormulaDTO));
    }

    @Override
    public SummaryFormula getSummaryFormulaByKey(String key) {
        SummaryFormulaDO formulaDO = this.summaryFormulaDao.getByKey(key);
        return Convert.summaryFormulaConvert.DO2DTO(formulaDO);
    }

    @Override
    public List<SummaryFormula> getSummaryFormulasByReport(String reportKey) {
        List<SummaryFormulaDO> formulaDOS = this.summaryFormulaDao.listByReport(reportKey);
        return formulaDOS.stream().map(formulaDO -> Convert.summaryFormulaConvert.DO2DTO((SummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }

    @Override
    public List<SummaryFormula> getBJSummaryFormulasBySolution(String solutionKey) {
        List<SummaryFormulaDO> formulaDOS = this.summaryFormulaDao.listByReport(SummaryReportUtil.getBJFormulaRptId(solutionKey));
        return formulaDOS.stream().map(formulaDO -> Convert.summaryFormulaConvert.DO2DTO((SummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }

    @Override
    public List<SummaryFormula> getSummaryFormulasBySolution(String solutionKey) {
        List<SummaryFormulaDO> formulaDOS = this.summaryFormulaDao.listBySolution(solutionKey);
        return formulaDOS.stream().map(formulaDO -> Convert.summaryFormulaConvert.DO2DTO((SummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }
}


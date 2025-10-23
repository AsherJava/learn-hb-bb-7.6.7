/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.DesignSummaryFormula;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.internal.dao.ISummaryFormulaDao;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryFormulaDO;
import com.jiuqi.nr.summary.internal.entity.SummaryFormulaDO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryFormulaService;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DesignSummaryFormulaServiceImpl
implements IDesignSummaryFormulaService {
    private static final int BATCH_SIZE = 1000;
    @Autowired
    private ISummaryFormulaDao<DesignSummaryFormulaDO> designSummaryFormulaDao;

    @Override
    public String insertSummaryFormula(DesignSummaryFormulaDTO designSummaryFormulaDTO) throws DBParaException {
        return this.designSummaryFormulaDao.insert((DesignSummaryFormulaDO)((SummaryFormulaDO)Convert.designSummaryFormulaConvert.DTO2DO(designSummaryFormulaDTO)));
    }

    @Override
    public void batchInsertSummaryFormula(List<DesignSummaryFormulaDTO> designSummaryFormulaDTOS) {
        if (!CollectionUtils.isEmpty(designSummaryFormulaDTOS)) {
            int count = designSummaryFormulaDTOS.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<DesignSummaryFormulaDTO> subList = designSummaryFormulaDTOS.subList(i * 1000, i * 1000 + n);
                List formulaDOS = subList.stream().map(formulaDTO -> Convert.designSummaryFormulaConvert.DTO2DO((DesignSummaryFormulaDTO)formulaDTO)).collect(Collectors.toList());
                this.designSummaryFormulaDao.batchInsert(formulaDOS);
            }
        }
    }

    @Override
    public void deleteSummaryFormulaByKey(String key) throws DBParaException {
        this.designSummaryFormulaDao.delete(key);
    }

    @Override
    public void deleteSummaryFormulaByKeys(List<String> keys) throws DBParaException {
        if (!CollectionUtils.isEmpty(keys)) {
            int count = keys.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<String> subList = keys.subList(i * 1000, i * 1000 + n);
                this.designSummaryFormulaDao.batchDelete(subList);
            }
        }
    }

    @Override
    public void deleteSummaryFormulaByReport(String reportKey) throws DBParaException {
        this.designSummaryFormulaDao.deleteByReport(reportKey);
    }

    @Override
    public void updateSummaryFormula(DesignSummaryFormulaDTO designSummaryFormulaDTO) throws DBParaException {
        this.designSummaryFormulaDao.update((DesignSummaryFormulaDO)((SummaryFormulaDO)Convert.designSummaryFormulaConvert.DTO2DO(designSummaryFormulaDTO)));
    }

    @Override
    public void batchUpdateSummaryFormula(List<DesignSummaryFormulaDTO> designSummaryFormulaDTOS) {
        if (!CollectionUtils.isEmpty(designSummaryFormulaDTOS)) {
            int count = designSummaryFormulaDTOS.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<DesignSummaryFormulaDTO> subList = designSummaryFormulaDTOS.subList(i * 1000, i * 1000 + n);
                List formulaDOS = subList.stream().map(formulaDTO -> Convert.designSummaryFormulaConvert.DTO2DO((DesignSummaryFormulaDTO)formulaDTO)).collect(Collectors.toList());
                this.designSummaryFormulaDao.batchUpdate(formulaDOS);
            }
        }
    }

    @Override
    public DesignSummaryFormula getSummaryFormulaByKey(String key) {
        DesignSummaryFormulaDO formulaDO = this.designSummaryFormulaDao.getByKey(key);
        return Convert.designSummaryFormulaConvert.DO2DTO(formulaDO);
    }

    @Override
    public List<DesignSummaryFormula> getSummaryFormulasByReport(String reportKey) {
        List<DesignSummaryFormulaDO> formulaDOS = this.designSummaryFormulaDao.listByReport(reportKey);
        return formulaDOS.stream().map(formulaDO -> Convert.designSummaryFormulaConvert.DO2DTO((DesignSummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }

    @Override
    public List<DesignSummaryFormula> getBJSummaryFormulasBySolution(String solutionKey) {
        List<DesignSummaryFormulaDO> formulaDOS = this.designSummaryFormulaDao.listByReport(SummaryReportUtil.getBJFormulaRptId(solutionKey));
        return formulaDOS.stream().map(formulaDO -> Convert.designSummaryFormulaConvert.DO2DTO((DesignSummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }

    @Override
    public List<DesignSummaryFormula> getSummaryFormulasBySolution(String solutionKey) {
        List<DesignSummaryFormulaDO> formulaDOS = this.designSummaryFormulaDao.listBySolution(solutionKey);
        return formulaDOS.stream().map(formulaDO -> Convert.designSummaryFormulaConvert.DO2DTO((DesignSummaryFormulaDO)formulaDO)).collect(Collectors.toList());
    }
}


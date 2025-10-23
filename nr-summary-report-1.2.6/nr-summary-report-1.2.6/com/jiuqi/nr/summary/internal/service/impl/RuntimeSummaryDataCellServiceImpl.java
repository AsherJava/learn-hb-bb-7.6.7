/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.SummaryDataCell;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummaryDataCellDao;
import com.jiuqi.nr.summary.internal.dto.SummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RuntimeSummaryDataCellServiceImpl
implements IRuntimeSummaryDataCellService {
    private static final int BATCH_SIZE = 1000;
    @Autowired
    @Qualifier(value="com.jiuqi.nr.summary.internal.dao.impl.SummaryDataCellDaoImpl")
    private ISummaryDataCellDao<SummaryDataCellDO> summaryDataCellDao;

    @Override
    public String insertSummaryDataCell(SummaryDataCellDTO summaryDataCellDTO) throws DBParaException {
        return this.summaryDataCellDao.insert(Convert.summaryDataCellConvert.DTO2DO(summaryDataCellDTO));
    }

    @Override
    public void batchInsertSummaryDataCell(List<SummaryDataCellDTO> dataCellDTOS) {
        if (!CollectionUtils.isEmpty(dataCellDTOS)) {
            int count = dataCellDTOS.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<Object> subList = new ArrayList();
                subList = (i + 1) * 1000 <= count ? dataCellDTOS.subList(i * 1000, (i + 1) * 1000) : dataCellDTOS.subList(i * 1000, i * 1000 + n);
                List dataCellDOS = subList.stream().map(dataCellDTO -> Convert.summaryDataCellConvert.DTO2DO((SummaryDataCellDTO)dataCellDTO)).collect(Collectors.toList());
                this.summaryDataCellDao.batchInsert(dataCellDOS);
            }
        }
    }

    @Override
    public void deleteSummaryDataCellByKey(String key) throws DBParaException {
        this.summaryDataCellDao.delete(key);
    }

    @Override
    public void deleteSummaryDataCellByReport(String reportKey) throws SummaryCommonException {
        try {
            this.summaryDataCellDao.deleteByReport(reportKey);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SDC_RUNTIME_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummaryDataCellByReports(List<String> reportKeys) {
        this.summaryDataCellDao.deleteByReports(reportKeys);
    }

    @Override
    public void updateSummaryDataCell(SummaryDataCellDTO summaryDataCellDTO) throws DBParaException {
        this.summaryDataCellDao.update(Convert.summaryDataCellConvert.DTO2DO(summaryDataCellDTO));
    }

    @Override
    public SummaryDataCell getSummaryDataCellByKey(String key) {
        SummaryDataCellDO dataCellDO = this.summaryDataCellDao.getByKey(key);
        return Convert.summaryDataCellConvert.DO2DTO(dataCellDO);
    }

    @Override
    public List<SummaryDataCellDTO> getSummaryDataCellsByReport(String reportKey) {
        List<SummaryDataCellDO> dataCellDOS = this.summaryDataCellDao.listByReport(reportKey);
        return dataCellDOS.stream().map(dataCellDO -> Convert.summaryDataCellConvert.DO2DTO((SummaryDataCellDO)dataCellDO)).collect(Collectors.toList());
    }
}


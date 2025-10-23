/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service.impl;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.DesignSummaryDataCell;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.internal.dao.ISummaryDataCellDao;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryDataCellDO;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DesignSummaryDataCellServiceImpl
implements IDesignSummaryDataCellService {
    private static final int BATCH_SIZE = 1000;
    @Autowired
    private ISummaryDataCellDao<DesignSummaryDataCellDO> designSummaryDataCellDao;

    @Override
    public String insertSummaryDataCell(DesignSummaryDataCellDTO designSummaryDataCellDTO) throws DBParaException {
        return this.designSummaryDataCellDao.insert((DesignSummaryDataCellDO)((SummaryDataCellDO)Convert.designSummaryDataCellConvert.DTO2DO(designSummaryDataCellDTO)));
    }

    @Override
    public void batchInsertSummaryDataCell(List<DesignSummaryDataCellDTO> designSummaryDataCellDTOS) {
        if (!CollectionUtils.isEmpty(designSummaryDataCellDTOS)) {
            int count = designSummaryDataCellDTOS.size();
            int m = count / 1000;
            int n = count % 1000;
            int groupCount = n == 0 ? m : m + 1;
            for (int i = 0; i < groupCount; ++i) {
                List<Object> subList = new ArrayList();
                subList = (i + 1) * 1000 <= count ? designSummaryDataCellDTOS.subList(i * 1000, (i + 1) * 1000) : designSummaryDataCellDTOS.subList(i * 1000, i * 1000 + n);
                List dataCellDOS = subList.stream().map(dataCellDTO -> Convert.designSummaryDataCellConvert.DTO2DO((DesignSummaryDataCellDTO)dataCellDTO)).collect(Collectors.toList());
                this.designSummaryDataCellDao.batchInsert(dataCellDOS);
            }
        }
    }

    @Override
    public void deleteSummaryDataCellByKey(String key) throws DBParaException {
        this.designSummaryDataCellDao.delete(key);
    }

    @Override
    public void deleteSummaryDataCellByReport(String reportKey) throws SummaryCommonException {
        try {
            this.designSummaryDataCellDao.deleteByReport(reportKey);
        }
        catch (DBParaException e) {
            throw new SummaryCommonException(SummaryErrorEnum.SDC_DES_DEL_DBEXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteSummaryDataCellByReports(List<String> reportKeys) {
        this.designSummaryDataCellDao.deleteByReports(reportKeys);
    }

    @Override
    public void updateSummaryDataCell(DesignSummaryDataCellDTO designSummaryDataCellDTO) throws DBParaException {
        this.designSummaryDataCellDao.update((DesignSummaryDataCellDO)((SummaryDataCellDO)Convert.designSummaryDataCellConvert.DTO2DO(designSummaryDataCellDTO)));
    }

    @Override
    public void batchUpdateSummaryDataCell(List<DesignSummaryDataCellDTO> designSummaryDataCellDTOS) {
        List dataCellDOS = designSummaryDataCellDTOS.stream().map(Convert.designSummaryDataCellConvert::DTO2DO).collect(Collectors.toList());
        this.designSummaryDataCellDao.batchUpdate(dataCellDOS);
    }

    @Override
    public DesignSummaryDataCell getSummaryDataCellByKey(String key) {
        DesignSummaryDataCellDO dataCellDO = this.designSummaryDataCellDao.getByKey(key);
        return Convert.designSummaryDataCellConvert.DO2DTO(dataCellDO);
    }

    @Override
    public List<DesignSummaryDataCellDTO> getSummaryDataCellsByReport(String reportKey) {
        List<DesignSummaryDataCellDO> dataCellDOS = this.designSummaryDataCellDao.listByReport(reportKey);
        return dataCellDOS.stream().map(dataCellDO -> Convert.designSummaryDataCellConvert.DO2DTO((DesignSummaryDataCellDO)dataCellDO)).collect(Collectors.toList());
    }
}


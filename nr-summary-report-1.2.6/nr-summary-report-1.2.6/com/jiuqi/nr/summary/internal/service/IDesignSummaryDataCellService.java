/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.DesignSummaryDataCell;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryDataCellDTO;
import java.util.List;

public interface IDesignSummaryDataCellService {
    public String insertSummaryDataCell(DesignSummaryDataCellDTO var1) throws DBParaException;

    public void batchInsertSummaryDataCell(List<DesignSummaryDataCellDTO> var1);

    public void deleteSummaryDataCellByKey(String var1) throws DBParaException;

    public void deleteSummaryDataCellByReport(String var1) throws SummaryCommonException;

    public void deleteSummaryDataCellByReports(List<String> var1);

    public void updateSummaryDataCell(DesignSummaryDataCellDTO var1) throws DBParaException;

    public void batchUpdateSummaryDataCell(List<DesignSummaryDataCellDTO> var1);

    public DesignSummaryDataCell getSummaryDataCellByKey(String var1);

    public List<DesignSummaryDataCellDTO> getSummaryDataCellsByReport(String var1);
}


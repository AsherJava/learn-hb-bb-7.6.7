/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.SummaryDataCell;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.SummaryDataCellDTO;
import java.util.List;

public interface IRuntimeSummaryDataCellService {
    public String insertSummaryDataCell(SummaryDataCellDTO var1) throws DBParaException;

    public void batchInsertSummaryDataCell(List<SummaryDataCellDTO> var1);

    public void deleteSummaryDataCellByKey(String var1) throws DBParaException;

    public void deleteSummaryDataCellByReport(String var1) throws SummaryCommonException;

    public void deleteSummaryDataCellByReports(List<String> var1);

    public void updateSummaryDataCell(SummaryDataCellDTO var1) throws DBParaException;

    public SummaryDataCell getSummaryDataCellByKey(String var1);

    public List<SummaryDataCellDTO> getSummaryDataCellsByReport(String var1);
}


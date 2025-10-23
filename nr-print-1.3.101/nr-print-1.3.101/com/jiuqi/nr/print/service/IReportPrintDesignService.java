/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 */
package com.jiuqi.nr.print.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.nr.print.dto.PrintTableGridDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IReportPrintDesignService {
    public void deployTemplate(String var1, String var2, String var3) throws Exception;

    public List<ReportLabelDTO> updateReportLabel(ReportLabelDTO[] var1, String var2);

    public Map<String, Object> getAttribute(String var1);

    public String getTableGrid(String var1, String var2) throws JsonProcessingException;

    public void updateTableGrid(PrintTableGridDTO var1) throws IOException;

    public Map<String, Object> getFormulaEditorParams(String var1);
}


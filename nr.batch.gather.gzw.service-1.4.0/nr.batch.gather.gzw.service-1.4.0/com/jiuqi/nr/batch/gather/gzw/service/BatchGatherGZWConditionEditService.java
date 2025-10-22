/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.gather.gzw.service;

import com.jiuqi.nr.batch.gather.gzw.service.entity.BatchGatherExportParam;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface BatchGatherGZWConditionEditService {
    public SXSSFWorkbook CustomCalibreExport(BatchGatherExportParam var1);

    public List<CustomCalibreRow> CustomCalibreImport(Sheet var1, SummaryScheme var2);
}


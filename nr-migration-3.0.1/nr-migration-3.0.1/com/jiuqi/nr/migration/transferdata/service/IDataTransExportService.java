/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.migration.transferdata.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.migration.syncscheme.vo.SchemeData;
import com.jiuqi.nr.migration.transferdata.bean.ExportParam;
import com.jiuqi.nr.migration.transferdata.bean.TransferExportDTO;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface IDataTransExportService {
    public void downloadJQRDataPackage(ExportParam var1, HttpServletResponse var2) throws Exception;

    public void executeSyncScheme(TransferExportDTO var1, HttpServletResponse var2);

    public void executeSyncScheme(String var1, HttpServletResponse var2) throws Exception;

    public byte[] executeSyncScheme(TransferExportDTO var1) throws Exception;

    public byte[] executeSyncScheme(String var1) throws Exception;

    public byte[] executeSyncScheme(SchemeData var1) throws Exception;

    public byte[] executeSyncScheme(String var1, Map<String, DimensionValue> var2) throws Exception;
}


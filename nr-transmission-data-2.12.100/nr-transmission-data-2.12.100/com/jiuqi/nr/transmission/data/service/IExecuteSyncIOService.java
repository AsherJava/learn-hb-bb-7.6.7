/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.ImportReturnParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface IExecuteSyncIOService {
    public ImportReturnParam checkFlowType(InputStream var1) throws Exception;

    public TransmissionResult executeOffLineImport(ImportParam var1, TransmissionMonitor var2, double var3) throws Exception;

    public void exportByHistory(SyncHistoryDTO var1, HttpServletResponse var2) throws Exception;

    public List<String> deleteFileServer(List<String> var1);
}


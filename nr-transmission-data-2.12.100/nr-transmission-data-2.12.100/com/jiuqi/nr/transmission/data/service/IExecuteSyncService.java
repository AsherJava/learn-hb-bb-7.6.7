/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.DataImportException;
import com.jiuqi.nr.transmission.data.exception.TransmissionSyncException;
import com.jiuqi.nr.transmission.data.intf.SyncFileParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.vo.ImportOtherVO;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;

public interface IExecuteSyncService {
    public TransmissionResult executeSync(SyncSchemeParamDTO var1) throws TransmissionSyncException;

    public TransmissionResult onlineImport(InputStream var1, SyncFileParam var2) throws DataImportException;

    public TransmissionResult offLineImport(InputStream var1, ImportOtherVO var2) throws Exception;

    public void offLineExport(SyncSchemeParamDTO var1, HttpServletResponse var2) throws Exception;
}


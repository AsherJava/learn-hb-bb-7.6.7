/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 */
package com.jiuqi.gcreport.archive.service;

import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import java.util.List;

public interface GcNotifyEFSService {
    public List<EFSResponseData> notifyEFSArchive(SendArchiveVO var1);
}


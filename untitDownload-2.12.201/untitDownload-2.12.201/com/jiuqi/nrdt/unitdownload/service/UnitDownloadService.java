/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgVersionDO
 */
package com.jiuqi.nrdt.unitdownload.service;

import com.jiuqi.nrdt.unitdownload.common.FMDMTransferDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.List;
import java.util.Map;

public interface UnitDownloadService {
    public Map<String, String> getTaskInfo(String var1, String var2);

    public List<FMDMTransferDTO> getUnitData(String var1, String var2, List<String> var3);

    public List<String> getOrgNotNullAttribute(String var1);

    public Map<Integer, List<Map<String, Object>>> getOrgData(String var1, String var2, List<String> var3);

    public List<OrgVersionDO> getOrgVersion(String var1);
}


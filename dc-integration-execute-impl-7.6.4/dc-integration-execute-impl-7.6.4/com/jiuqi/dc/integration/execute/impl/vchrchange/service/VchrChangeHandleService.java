/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.service;

import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDO;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeHandleResult;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.List;

public interface VchrChangeHandleService {
    public List<VchrChangeDim> queryVchrChangeDim(String var1);

    public void beforeHandle(VchrChangeDim var1);

    public VchrChangeHandleResult handle(VchrChangeDim var1);

    public String deleteVchrData();

    public void insertDirectVchrChangeDim(List<VchrChangeDO> var1);

    public List<VchrChangeDim> queryVchrChangeDimByOdsUnitId(String var1, String var2);

    public String resetEtlProcessLog(DataSchemeDTO var1, VchrChangeDim var2, List<String> var3);
}


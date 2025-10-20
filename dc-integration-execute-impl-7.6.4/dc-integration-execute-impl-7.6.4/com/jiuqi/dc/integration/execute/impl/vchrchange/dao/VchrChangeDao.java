/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.dao;

import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDO;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrDeleteDim;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;

public interface VchrChangeDao {
    public List<VchrChangeDim> queryVchrChangeDim(String var1, String var2);

    public void updateDcSrcVchrItemAssId(VchrChangeDim var1);

    public void updateDcCfSrcVchrId(VchrChangeDim var1);

    public void updateTempVchrItemAssId(VchrChangeDim var1);

    public void prepareVchrTemp(VchrChangeDim var1, List<DimensionVO> var2);

    public void insertOffsetVchrFromTemp(VchrChangeDim var1, List<DimensionVO> var2, VchrMasterDim var3);

    public void insertCfOffsetVchrFromTemp(VchrChangeDim var1, List<DimensionVO> var2, VchrMasterDim var3);

    public void updateVchrChangeInfoOffsetGroupId(VchrChangeDim var1);

    public void updateOdsVchrImpstateIfExists(VchrChangeDim var1, String var2, String var3);

    public void updateEtlProcessLog(VchrChangeDim var1, String var2, List<String> var3);

    public void updateVchrChangeInfoCreateOffsetVchrFlag(VchrChangeDim var1);

    public List<VchrMasterDim> queryVchrPeriodFromTemp();

    public List<String> querySrcVchrIdList();

    public void cleanVchrTemp();

    public List<Integer> queryDeleteVchrYearList();

    public List<VchrDeleteDim> queryUnCleanOffsetVchrGroupId(int var1);

    public void deleteVchr(String var1, int var2);

    public void updateVchrChangeInfoVchrCleanFlag();

    public void insertDirectVchrChangeDim(List<VchrChangeDO> var1);

    public void insertSrcVchrId2Temp(List<String> var1);

    public List<String> checkSrcVchrData(VchrChangeDim var1);

    public void updateCheckDataState(List<String> var1);
}


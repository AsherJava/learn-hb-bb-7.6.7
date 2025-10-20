/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustDelCondi;
import com.jiuqi.gcreport.offsetitem.vo.query.GcInputAdjustQueryCondi;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GcInputAdjustService {
    public void addInputAdjust(List<List<GcInputAdjustVO>> var1);

    public List<GcInputAdjustVO> queryDetailByMrecid(GcInputAdjustQueryCondi var1);

    public List<List<GcInputAdjustVO>> queryDetailByMrecidList(GcInputAdjustQueryCondi var1);

    public void deleteBySrcId(GcInputAdjustDelCondi var1);

    public Map<String, Map<String, Object>> queryByGroupIds(Set<String> var1);

    public GcInputAdjustVO consFormulaCalc(GcInputAdjustVO var1);

    public void exeConsFormulaCalcOneGroup(GcOffSetVchrDTO var1);

    public GcInputAdjustVO convertDTOToVO(GcOffSetVchrItemDTO var1, String var2);

    public GcInputAdjustVO convertEOToVO(GcOffSetVchrItemAdjustEO var1, String var2);

    public void exeConsFormulaCalcSingle(GcOffSetVchrItemDTO var1, List<ConsolidatedFormulaVO> var2, List<DefaultTableEntity> var3);

    public void deleteFutureMonthByOffsetGroupId(Collection<String> var1, Integer var2, GcTaskBaseArguments var3);
}


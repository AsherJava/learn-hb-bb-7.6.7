/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.dc.adjustvchr.client.service;

import com.jiuqi.dc.adjustvchr.client.dto.AdjustVchrQueryResultDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrBaseDataVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import java.util.Map;

public interface AdjustVoucherClientService {
    public String getMaxVchrNum(String var1, Integer var2);

    public List<String> listVchrNumBySize(String var1, Integer var2, int var3);

    public AdjustVchrQueryResultDTO list(AdjustVoucherQueryDTO var1);

    public AdjustVchrSysOptionVO getAdjustVchrSysOptions();

    public List<DimensionVO> listAdjustDim();

    public Map<String, List<String>> getAccountAndReclassifly();

    public List<AdjustVchrBaseDataVO> getRepCurrCodeByUnit(String var1);
}


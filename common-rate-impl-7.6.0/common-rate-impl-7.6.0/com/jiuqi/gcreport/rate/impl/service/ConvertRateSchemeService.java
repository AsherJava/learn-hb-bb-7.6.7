/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO
 *  com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.gcreport.rate.impl.service;

import com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO;
import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;

public interface ConvertRateSchemeService {
    public PageVO<ConvertRateSchemeVO> query(ConvertRateSchemeDTO var1);

    public PageVO<ConvertRateSchemeVO> save(ConvertRateSchemeDTO var1);

    public Map<String, ConvertRateSchemeVO> getBySubjectCodes(List<String> var1);

    public Map<String, ConvertRateSchemeVO> getRateSchemeBySubjectCodes(List<String> var1);
}


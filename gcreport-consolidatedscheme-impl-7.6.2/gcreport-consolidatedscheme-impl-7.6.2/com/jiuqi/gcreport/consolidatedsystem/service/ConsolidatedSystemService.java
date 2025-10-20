/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service;

import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import java.util.List;

public interface ConsolidatedSystemService {
    public List<ConsolidatedSystemEO> getConsolidatedSystemEOS();

    public ConsolidatedSystemEO getConsolidatedSystemEO(String var1);

    public List<ConsolidatedSystemVO> getConsolidatedSystemVOS(Integer var1);

    public List<ConsolidatedSystemVO> getConsolidatedSystemVOSByTaksId(String var1);

    public ConsolidatedSystemVO getConsolidatedSystemVO(String var1);

    public void save(ConsolidatedSystemEO var1);

    public String addConsolidatedSystem(ConsolidatedSystemVO var1);

    public void editConsolidatedSystem(String var1, ConsolidatedSystemVO var2);

    public void handleConsolidatedSystem(String var1, String var2);

    public void deleteConsolidatedSystem(String var1);

    public ConsolidatedSystemVO convertEO2VO(ConsolidatedSystemEO var1);

    public List<SelectOptionVO> getFormualSchemes(String var1);

    public List<ConsolidatedSystemVO> getSystemsByIds(List<String> var1);
}


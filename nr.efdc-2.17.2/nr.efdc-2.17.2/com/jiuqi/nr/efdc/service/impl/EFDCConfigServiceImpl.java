/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.dao.SoluctionsDao;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.efdc.service.SoluctionQueryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class EFDCConfigServiceImpl
implements IEFDCConfigService {
    @Resource
    SoluctionQueryService soluctionQueryServiceImpl;
    @Autowired
    private SoluctionsDao dao;

    @Override
    public FormulaSchemeDefine getSoluctionByDimensions(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        return this.soluctionQueryServiceImpl.getSoluctionByDimensions(object, dimMap, entityId);
    }

    @Override
    public Boolean existSolution(String taskKey) {
        return this.dao.countSolutionByTask(taskKey) > 0;
    }

    @Override
    public List<FormulaSchemeDefine> getRPTFormulaScheme(QueryObjectImpl object, Map<String, String> dimMap, String entityId) {
        ArrayList<FormulaSchemeDefine> formulaSchemeDefineList = new ArrayList<FormulaSchemeDefine>();
        FormulaSchemeDefine formulaScheme = this.soluctionQueryServiceImpl.getRPTFormulaScheme(object, dimMap, entityId);
        if (formulaScheme != null) {
            formulaSchemeDefineList.add(formulaScheme);
        }
        return formulaSchemeDefineList;
    }
}


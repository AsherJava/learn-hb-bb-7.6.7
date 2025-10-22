/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO
 */
package com.jiuqi.gcreport.formulaschemeconfig.service;

import com.jiuqi.gcreport.formulaschemeconfig.dto.BillFormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.BillFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;

public interface BillFormulaSchemeConfigService {
    public List<FormulaSchemeColumn> createQueryColumns(String var1);

    public BillFormulaSchemeConfigQueryResultDTO getShowTableByOrgId(BillFormulaSchemeConfigCondition var1);

    public void saveStrategyFormulaSchemeConfig(List<BillFormulaSchemeConfigTableVO> var1);

    public void saveUnitFormulaSchemeConfig(List<BillFormulaSchemeConfigTableVO> var1);

    public void importFormulaSchemeConfig(String var1, List<BillFormulaSchemeConfigTableVO> var2);

    public void recoverDefaultStrategy(BillFormulaSchemeConfigTableVO var1);

    public Map<String, Object> getFetchSchemesByBillId(String var1);

    public void deleteSelectSchemeConfig(String var1, List<String> var2);

    public BillFormulaSchemeConfigDTO getSchemeConfigByOrgId(String var1, String var2);

    public List<BillFormulaSchemeConfigTableVO> getStrategyTabSchemeConfig(String var1);

    public Map<String, List<BillFormulaSchemeConfigTableVO>> queryTabSelectOrgIds(String var1, List<String> var2, Boolean var3);
}


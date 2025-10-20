/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO
 */
package com.jiuqi.gcreport.formulaschemeconfig.service;

import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeColumn;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigCondition;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigQueryResultDTO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.NrFormulaSchemeConfigTableVO;
import java.util.List;
import java.util.Map;

public interface FormulaSchemeConfigService {
    public List<FormulaSchemeColumn> createQueryColumns(String var1, String var2);

    public NrFormulaSchemeConfigQueryResultDTO getShowTableByOrgId(NrFormulaSchemeConfigCondition var1);

    public void saveStrategyFormulaSchemeConfig(List<NrFormulaSchemeConfigTableVO> var1);

    public void saveUnitFormulaSchemeConfig(List<NrFormulaSchemeConfigTableVO> var1);

    public void importFormulaSchemeConfig(String var1, List<NrFormulaSchemeConfigTableVO> var2);

    public void recoverDefaultStrategy(NrFormulaSchemeConfigTableVO var1);

    public void deleteSelectSchemeConfig(String var1, String var2, List<String> var3);

    public Map<String, Object> getFormulaSchemesBySchemeId(String var1);

    public Map<String, Object> getFormulaSchemesBySchemeId(String var1, String var2);

    public FormulaSchemeConfigDTO getSchemeConfigByOrgAndAssistDim(String var1, String var2, Map<String, String> var3);

    public FormulaSchemeConfigDTO getSchemeConfigByOrgAndAssistDimWithCache(String var1, String var2, Map<String, String> var3);

    public List<NrFormulaSchemeConfigTableVO> getStrategyTabSchemeConfig(String var1, String var2);

    public List<String> getCurrencyByOrgId(String var1, String var2, String var3);

    public FormulaSchemeConfigDTO getConvertSchemeConfig(String var1, String var2, Map<String, String> var3);

    public Map<String, List<NrFormulaSchemeConfigTableVO>> queryTabSelectOrgIds(String var1, String var2, List<String> var3, Boolean var4);

    public int getFormulaSchemeConfigByFetchSchemeId(String var1);

    public List<FormulaSchemeConfigEO> getByTaskId(String var1);
}


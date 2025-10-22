/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package nr.single.map.configurations.service;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.ReportFormTree;
import nr.single.map.configurations.internal.bean.QueryParam;
import nr.single.map.configurations.internal.bean.RegionDictionaryData;
import nr.single.map.configurations.vo.FormulaDefineVO;

public interface FormulaSchemeService {
    public List<FormulaSchemeDefine> getFormulaSchemesByReport(String var1);

    public List<FormulaDefineVO> getAllFormulas(String var1);

    public List<FormulaDefineVO> getFormulasByForm(String var1, String var2);

    public List<ReportFormTree> buildReportTree(String var1);

    public List<FormulaDefine> searchFormulaDefine(QueryParam var1);

    public Map<String, RegionDictionaryData> queryEnumDataInFloatRegion(String var1);
}


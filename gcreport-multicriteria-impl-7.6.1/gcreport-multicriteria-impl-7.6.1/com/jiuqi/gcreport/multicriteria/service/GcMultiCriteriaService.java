/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.multicriteria.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcMultiCriteriaService {
    public FormTree queryFormTree(String var1);

    public void saveAfterForm(GcMulCriAfterFormVO var1);

    public List<String> queryMulCriAfterForms(String var1, String var2);

    public void saveSubjectMapping(List<GcMultiCriteriaVO> var1);

    public List<GcMultiCriteriaVO> querySubjectMapping(GcMultiCriteriaConditionVO var1);

    public void deleteSubjectMapping(@RequestBody List<String> var1);

    public String queryZbTitlesByCode(List<String> var1);

    public List<GcMultiCriteriaZbDataVO> queryZbData(GcMultiCriteriaConditionVO var1);

    public void saveZbData(GcMultiCriteriaConditionVO var1);

    public DimensionParamsVO getDimensionParamsVO(GcMultiCriteriaConditionVO var1);

    public Map<String, String> queryFieldMappingText(String var1, String var2);

    public ExportExcelSheet exportMultiCriteriaData(GcMultiCriteriaConditionVO var1);

    public ExportExcelSheet exportMultiCriteriaSettingData(GcMultiCriteriaConditionVO var1, boolean var2);

    public StringBuilder multiCriteriaSettingImport(String var1, String var2, List<Object[]> var3);

    public String queryFormData(String var1, String var2, Object var3);
}


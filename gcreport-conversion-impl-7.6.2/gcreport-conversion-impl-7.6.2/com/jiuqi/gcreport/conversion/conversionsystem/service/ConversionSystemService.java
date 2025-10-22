/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO
 *  com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject
 */
package com.jiuqi.gcreport.conversion.conversionsystem.service;

import com.jiuqi.gcreport.conversion.conversionsystem.executor.ConversionSystemExportExecutorImpl;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelModel;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import java.util.List;
import java.util.Set;

public interface ConversionSystemService {
    public List<ConversionSystemTaskSchemeVO> getSystemTaskSchemes();

    public ConversionSystemItemVO getSystemItemByFormIdAndIndexId(String var1, String var2);

    public List<TaskFormInfoVO> getFormList(String var1);

    public List<TaskCommonVO> getSchemeList(String var1);

    public List<TaskCommonVO> getTaskList();

    public List<FormulaSchemeObject> getFormulaSchemeByFromScheme(String var1);

    public List<ConversionSystemTaskVO> saveTaskScheme(List<ConversionSystemTaskVO> var1);

    public ConversionSystemTaskVO deleteTaskScheme(String var1);

    public List<ConversionSystemTaskVO> queryTaskSchemes(String var1);

    public ConversionSystemItemVO saveConversionSystemItemIndexInfo(ConversionSystemItemVO var1);

    public List<ConversionSystemItemVO> batchSaveConversionSystemItemIndexInfo(List<ConversionSystemItemVO> var1);

    public List<ConversonSystemFormTreeVo> queryFormTree(String var1);

    public String queryFormData(String var1);

    public List<ConversionSystemItemVO> batchGetSystemItemsByFormIdAndIndexIds(String var1, String var2, Set<String> var3);

    public List<ConversionSystemItemExcelModel> exportExcel(ConversionSystemExportExecutorImpl.ConversionSystemExportParam var1);

    public Object importExcel(List<ConversionSystemItemExcelModel> var1);
}


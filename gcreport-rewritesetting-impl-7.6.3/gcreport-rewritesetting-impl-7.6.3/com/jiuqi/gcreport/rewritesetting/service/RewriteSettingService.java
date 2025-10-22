/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.rewritesetting.service;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface RewriteSettingService {
    public void deleteRewriteSetting(@RequestBody List<String> var1);

    public List<FloatRegionTreeVO> getFloatRegionTree(String var1);

    public void saveRewriteSetting(RewriteSettingVO var1);

    public List<RewriteSettingVO> queryRewriteSettings(String var1);

    public List<ExportExcelSheet> exportRewriteSettingWorkbook(String var1);

    public StringBuilder rewriteSettingImport(String var1, String var2, String var3, List<ImportExcelSheet> var4);

    public void saveRewriteSubjectSettings(String var1, List<RewriteSubjectSettingVO> var2);

    public List<RewriteSubjectSettingVO> queryRewriteSubjectSettings(String var1);

    public List<RewriteFieldMappingVO> queryFieldMappingSettings(String var1);

    public List<RewriteFieldInfoVO> queryOffsetDataModel(String var1);
}


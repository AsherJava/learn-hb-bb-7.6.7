/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 */
package com.jiuqi.gcreport.workingpaper.service;

import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgParamVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeQuerySchemeVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import java.util.List;
import java.util.Map;

public interface ArbitrarilyMergeService {
    public String checkOrgIdSupSubRelation(WorkingPaperQueryCondition var1);

    public String getViewKeyByTaskIdAndOrgType(String var1, String var2);

    public Pagination<Map<String, Object>> getUnOffsetPentrationDatas(WorkingPaperPentrationQueryCondtion var1);

    public boolean addQueryScheme(ArbitrarilyMergeQuerySchemeVO var1);

    public List<ArbitrarilyMergeQuerySchemeVO> getQuerySchemeByResourceId(String var1);

    public ArbitrarilyMergeQuerySchemeVO getQuerySchemeById(String var1);

    public boolean updateQueryScheme(ArbitrarilyMergeQuerySchemeVO var1);

    public boolean deleteQuerySchemeById(String var1);

    public boolean addOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO var1);

    public boolean updateOrgFilterSetting(ArbitrarilyMergeOrgFilterSettingVO var1);

    public boolean deleteOrgFilterSetting(String var1);

    public ArbitrarilyMergeOrgFilterSettingVO getOrgFilterSettingById(String var1);

    public List<ArbitrarilyMergeOrgFilterSettingVO> getOrgFilterSettingList();

    public List<ArbitrarilyMergeOrgFilterSettingVO> getEnableList();

    public boolean addBatchFormulaSettingByDataId(List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> var1, String var2);

    public boolean deleteFormulaSettingByDataId(String var1);

    public List<ArbitrarilyMergeOrgFilterCustomFormulaSettingVO> getFormulaSettingByDataId(String var1);

    public ArbitrarilyMergeOrgFilterCustomFormulaSettingVO getFormulaById(String var1);

    public void addRyInputAdjust(List<List<ArbitrarilyMergeInputAdjustVO>> var1);

    public void deleteRyAdjustInputByMredid(String var1, List<String> var2, int var3, int var4, int var5, String var6, String var7, int var8, String var9);

    public List<ArbitrarilyMergeInputAdjustVO> queryRyDetailByMrecid(ArbitrarilyMergeInputAdjustQueryCondi var1);

    public ArbitrarilyMergeInputAdjustVO convertRyEOToVO(ArbitrarilyMergeOffSetVchrItemAdjustEO var1, String var2);

    public Pagination<Map<String, Object>> listRyPentrationDatasOther(WorkingPaperPentrationQueryCondtion var1);

    public Pagination<Map<String, Object>> listRyPentrationDatas(WorkingPaperPentrationQueryCondtion var1);

    public List<GcOrgCacheVO> getOrgByOrgCodes(ArbitrarilyMergeOrgParamVO var1);
}


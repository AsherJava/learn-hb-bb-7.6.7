/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.multipart.MultipartFile;

public interface PrimaryWorkpaperService {
    public PrimaryWorkpaperTypeVO addPrimaryWorkpaperType(PrimaryWorkpaperTypeVO var1);

    public List<PrimaryWorkpaperTypeVO> listPrimaryWorkpaperTypesBySystemId(String var1);

    public List<PrimaryWorkpaperTypeVO> listPrimaryWorkpaperTypeTree(String var1);

    public void deletePrimaryWorkpaperTypeById(String var1);

    public PrimaryWorkpaperTypeVO updatePrimaryWorkpaperType(PrimaryWorkpaperTypeVO var1);

    public String moveTypeTreeNode(String var1, Integer var2);

    public List<PrimaryWorkpaperSettingVO> listPrimarySettingDatas(String var1);

    public List<PrimaryWorkPaperSettingEO> listSetRecordsBySystemId(String var1);

    public Map<String, List<ConsolidatedSubjectVO>> getZbCodeToSubjectsMap(String var1);

    public void savePrimaryWorkpaperSets(List<PrimaryWorkpaperSettingVO> var1);

    public void deletePrimaryWorkpaperSetsByIds(List<String> var1);

    public ExportExcelSheet exportSet(String var1, Map<String, CellStyle> var2);

    public Object importData(MultipartFile var1, Map<String, String> var2);
}


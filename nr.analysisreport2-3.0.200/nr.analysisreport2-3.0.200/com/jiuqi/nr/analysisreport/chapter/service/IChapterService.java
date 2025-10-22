/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 */
package com.jiuqi.nr.analysisreport.chapter.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterTreeQueryVo;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterVO;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import java.util.List;

public interface IChapterService {
    public ReportChapterDefine insertChapter(ChapterVO var1) throws Exception;

    public Boolean insertChapter(ReportChapterDefine var1) throws Exception;

    public ReportChapterDefine insertDefaultChapter(String var1) throws Exception;

    public boolean deleteChapter(String var1) throws Exception;

    public boolean checkCanDelete(String var1) throws Exception;

    public boolean deleteChapterByModelId(String var1) throws Exception;

    public boolean updateChapter(ChapterVO var1) throws Exception;

    public boolean updateChapter(ReportChapterDefine var1) throws Exception;

    public ReturnObject updateChapterCheckName(ChapterVO var1) throws Exception;

    public ReportChapterDefine getChapterById(String var1) throws Exception;

    public List<ReportChapterDefine> queryChapterByModelId(String var1) throws Exception;

    public boolean changeChapterOrder(String var1, String var2) throws Exception;

    public List<ChapterVO> getChapterTree(ChapterTreeQueryVo var1) throws Exception;

    public Integer checkChapterName(String var1, String var2, String var3);

    public int clearTemplateArcData(String var1) throws Exception;

    public List<ReportChapterDefine> listOnlyArcNameAndArcKey(String var1);

    public boolean checkGenCatalogCompleted(String var1);

    public void batchInsert(ReportChapterDefine[] var1) throws DBParaException;
}


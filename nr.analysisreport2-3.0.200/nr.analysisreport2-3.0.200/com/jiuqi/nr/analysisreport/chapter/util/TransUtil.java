/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.chapter.util;

import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterVO;

public class TransUtil {
    private static final String id = "id";

    public static ChapterVO transDtoToVo(ReportChapterDefine reportChapterDefine) {
        ChapterVO chapterVO = new ChapterVO();
        chapterVO.setArcKey(reportChapterDefine.getArcKey());
        chapterVO.setArcOrder(reportChapterDefine.getArcOrder());
        chapterVO.setArcName(reportChapterDefine.getArcName());
        chapterVO.setArcParent(reportChapterDefine.getArcParent());
        chapterVO.setArcAtKey(reportChapterDefine.getArcAtKey());
        chapterVO.setArcUpdatetime(reportChapterDefine.getArcUpdatetime());
        chapterVO.setArcData(reportChapterDefine.getArcData());
        chapterVO.setArcData(reportChapterDefine.getArcData());
        chapterVO.setCatalog(reportChapterDefine.getCatalog());
        chapterVO.setCatalogUpdatetime(reportChapterDefine.getCatalogUpdatetime());
        chapterVO.setTypeSpeed(reportChapterDefine.getTypeSpeed());
        Integer typeSpeed = reportChapterDefine.getTypeSpeed();
        if (typeSpeed != null) {
            chapterVO.setIsStreamingEnabled(true);
        } else {
            chapterVO.setIsStreamingEnabled(false);
        }
        return chapterVO;
    }

    public static ReportChapterDefine transVoToDto(ChapterVO chapterVO) {
        ReportChapterDefine reportChapterDefine = new ReportChapterDefine();
        reportChapterDefine.setArcKey(chapterVO.getArcKey());
        reportChapterDefine.setArcAtKey(chapterVO.getArcAtKey());
        reportChapterDefine.setArcOrder(chapterVO.getArcOrder());
        reportChapterDefine.setArcName(chapterVO.getArcName());
        reportChapterDefine.setArcParent(chapterVO.getArcParent());
        reportChapterDefine.setArcData(chapterVO.getArcData());
        reportChapterDefine.setArcUpdatetime(chapterVO.getArcUpdatetime());
        reportChapterDefine.setCatalogUpdatetime(chapterVO.getCatalogUpdatetime());
        reportChapterDefine.setCatalog(chapterVO.getCatalog());
        reportChapterDefine.setTypeSpeed(chapterVO.getTypeSpeed());
        return reportChapterDefine;
    }
}


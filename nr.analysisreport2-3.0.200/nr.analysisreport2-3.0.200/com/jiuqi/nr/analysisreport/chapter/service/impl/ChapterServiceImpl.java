/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 */
package com.jiuqi.nr.analysisreport.chapter.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.dao.IChapterDao;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.chapter.util.TransUtil;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterTreeQueryVo;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterVO;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChapterServiceImpl
implements IChapterService {
    @Autowired
    private IChapterDao chapterDao;
    @Autowired
    private SaveAnalysis saveAnalysis;
    @Autowired
    AnalysisHelper analysisHelper;

    @Override
    public ReportChapterDefine insertChapter(ChapterVO chapterDefineVo) throws Exception {
        ReportChapterDefine chapterDefine = new ReportChapterDefine();
        chapterDefine.setArcKey(UUID.randomUUID().toString());
        chapterDefine.setArcAtKey(chapterDefineVo.getArcAtKey());
        chapterDefine.setArcName(chapterDefineVo.getArcName());
        chapterDefine.setArcOrder(OrderGenerator.newOrder());
        chapterDefine.setTypeSpeed(chapterDefineVo.getTypeSpeed());
        if (!StringUtils.hasLength(chapterDefineVo.getArcParent())) {
            chapterDefine.setArcParent("top");
        } else {
            chapterDefine.setArcParent(chapterDefineVo.getArcParent());
        }
        chapterDefine.setArcUpdatetime(new Date());
        this.chapterDao.inseartChapter(chapterDefine);
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(chapterDefineVo.getArcAtKey());
        AnalysisReportLogHelper.log("\u65b0\u589e\u7ae0\u8282", "\u65b0\u589e\u7ae0\u8282:" + analysisReportDefine.getTitle() + "-" + chapterDefineVo.getArcName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return chapterDefine;
    }

    @Override
    public Boolean insertChapter(ReportChapterDefine reportChapterDefine) throws Exception {
        int result = this.chapterDao.inseartChapter(reportChapterDefine);
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(reportChapterDefine.getArcAtKey());
        AnalysisReportLogHelper.log("\u65b0\u589e\u7ae0\u8282", "\u65b0\u589e\u7ae0\u8282:" + analysisReportDefine.getTitle() + "-" + reportChapterDefine.getArcName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return result == 1;
    }

    @Override
    public ReportChapterDefine insertDefaultChapter(String moduleId) throws Exception {
        ChapterVO chapterVO = new ChapterVO();
        chapterVO.setArcAtKey(moduleId);
        chapterVO.setArcName("\u9ed8\u8ba4\u7ae0\u8282");
        return this.insertChapter(chapterVO);
    }

    @Override
    public ReturnObject updateChapterCheckName(ChapterVO chapterVO) throws Exception {
        if (this.checkChapterName(chapterVO.getArcAtKey(), chapterVO.getArcParent(), chapterVO.getArcName()) > 1) {
            return AnaUtils.initReturn(false, "\u65b0\u589e\u5931\u8d25,\u540c\u4e00\u5c42\u7ea7\u4e2d\u7ae0\u8282\u540d\u91cd\u590d");
        }
        this.updateChapter(chapterVO);
        return AnaUtils.initReturn(true, "\u521b\u5efa\u7ae0\u8282\u6210\u529f");
    }

    @Override
    public boolean updateChapter(ChapterVO chapterVO) throws Exception {
        ReportChapterDefine newChapter = TransUtil.transVoToDto(chapterVO);
        this.chapterDao.updateChapter(newChapter);
        return true;
    }

    @Override
    public boolean updateChapter(ReportChapterDefine chapter) throws Exception {
        this.chapterDao.updateChapter(chapter);
        return true;
    }

    @Override
    public ReportChapterDefine getChapterById(String chapterId) throws Exception {
        return this.chapterDao.getBykey(chapterId);
    }

    @Override
    public List<ReportChapterDefine> queryChapterByModelId(String modelId) throws Exception {
        return this.chapterDao.getChapterByModuleId(modelId);
    }

    @Override
    public boolean deleteChapter(String chapterId) throws Exception {
        ReportChapterDefine chapter = this.chapterDao.getBykey(chapterId);
        this.chapterDao.delete(chapterId);
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(chapter.getArcAtKey());
        this.saveAnalysis.updateTempModifiedTime(chapter.getArcAtKey(), new Date());
        AnalysisReportLogHelper.log("\u5220\u9664\u7ae0\u8282", "\u5220\u9664\u7ae0\u8282:" + analysisReportDefine.getTitle() + "-" + chapter.getArcName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return true;
    }

    @Override
    public boolean checkCanDelete(String chapterId) throws Exception {
        ReportChapterDefine chapter = this.chapterDao.getBykey(chapterId);
        List<ReportChapterDefine> chapterList = this.chapterDao.getChapterByModuleId(chapter.getArcAtKey());
        return chapterList.size() == 1;
    }

    @Override
    public boolean deleteChapterByModelId(String modelId) throws Exception {
        int i = this.chapterDao.deleteByFiled(new String[]{"arcAtKey"}, new Object[]{modelId});
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(modelId);
        AnalysisReportLogHelper.log("\u5220\u9664\u7ae0\u8282", analysisReportDefine.getTitle() + " \u5220\u9664\u6240\u6709\u7ae0\u8282", AnalysisReportLogHelper.LOGLEVEL_INFO);
        return i == 1;
    }

    @Override
    public boolean changeChapterOrder(String originChapterId, String targetChapterId) throws Exception {
        ReportChapterDefine originChapter = this.chapterDao.getBykey(originChapterId);
        ReportChapterDefine targetChapter = this.chapterDao.getBykey(targetChapterId);
        String orderTemp = originChapter.getArcOrder();
        originChapter.setArcOrder(targetChapter.getArcOrder());
        targetChapter.setArcOrder(orderTemp);
        this.chapterDao.updateChapter(originChapter);
        this.chapterDao.updateChapter(targetChapter);
        this.saveAnalysis.updateTempModifiedTime(originChapter.getArcAtKey(), new Date());
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(originChapter.getArcAtKey());
        AnalysisReportLogHelper.log("\u79fb\u52a8\u7ae0\u8282", "\u79fb\u52a8\u7ae0\u8282 \u4ece" + analysisReportDefine.getTitle() + ":" + originChapter.getArcName() + " \u5230 " + analysisReportDefine.getTitle() + ":" + targetChapter.getArcName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return true;
    }

    @Override
    public List<ChapterVO> getChapterTree(ChapterTreeQueryVo chapterTreeQueryVo) throws Exception {
        ArrayList<ChapterVO> chapterVOS = new ArrayList<ChapterVO>();
        List<ReportChapterDefine> chapterList = this.chapterDao.getChapterByModuleId(chapterTreeQueryVo.getModelId());
        for (ReportChapterDefine chapterDefine : chapterList) {
            ChapterVO chapter = TransUtil.transDtoToVo(chapterDefine);
            chapter.setIsLeaf(true);
            chapter.setCatalog(null);
            chapter.setArcData(null);
            chapterVOS.add(chapter);
        }
        return chapterVOS;
    }

    @Override
    public Integer checkChapterName(String modelId, String parentId, String title) {
        parentId = "top";
        return this.chapterDao.checkChapterTitleRepeat(modelId, parentId, title);
    }

    @Override
    public int clearTemplateArcData(String templateKey) throws Exception {
        int i = this.chapterDao.clearTemplateArcData(templateKey);
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(templateKey);
        AnalysisReportLogHelper.log("\u6e05\u9664\u6a21\u677f\u6240\u6709\u7ae0\u8282\u5185\u5bb9", "\u6e05\u9664\u6a21\u677f " + analysisReportDefine.getTitle() + "\u6240\u6709\u7ae0\u8282\u5185\u5bb9", AnalysisReportLogHelper.LOGLEVEL_INFO);
        return i;
    }

    @Override
    public List<ReportChapterDefine> listOnlyArcNameAndArcKey(String key) {
        return this.chapterDao.listOnlyArcNameAndArcKey(key);
    }

    @Override
    public boolean checkGenCatalogCompleted(String templateKey) {
        return this.chapterDao.checkGenCatalogCompleted(templateKey);
    }

    @Override
    public void batchInsert(ReportChapterDefine[] reportChapterDefines) throws DBParaException {
        this.chapterDao.batchInsert(reportChapterDefines);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.analysisreport.chapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.common.ChapterErrorEnum;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.chapter.util.TransUtil;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterTreeQueryVo;
import com.jiuqi.nr.analysisreport.chapter.vo.ChapterVO;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/ar/api/chapter"})
public class ChapterController {
    private Logger logger = LoggerFactory.getLogger(ChapterController.class);
    @Autowired
    private IChapterService chapterService;
    @Autowired
    AnalysisHelper analysisHelper;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:chapter"})
    @RequestMapping(value={"/addChapter"}, method={RequestMethod.POST})
    public ReturnObject addChapter(@RequestBody ChapterVO chapterVO) throws Exception {
        AnalysisReportLogHelper.log("\u65b0\u589e\u7ae0\u8282\u5f00\u59cb", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        if (StringUtils.isEmpty((CharSequence)chapterVO.getArcName())) {
            return AnaUtils.initReturn(false, "\u672a\u586b\u5199\u7ae0\u8282\u6807\u9898");
        }
        if (StringUtils.isEmpty((CharSequence)chapterVO.getArcAtKey())) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        if (chapterVO.getArcName().length() > 150) {
            return AnaUtils.initReturn(false, "\u6807\u9898\u5b57\u6570\u8d85\u957f\uff0c\u8bf7\u4fee\u6539");
        }
        try {
            if (this.chapterService.checkChapterName(chapterVO.getArcAtKey(), chapterVO.getArcParent(), chapterVO.getArcName()) > 0) {
                ReturnObject returnObject = AnaUtils.initReturn(false, "\u6807\u9898\u91cd\u590d\uff0c\u8bf7\u4fee\u6539");
                return returnObject;
            }
            ReportChapterDefine chapterDefine = this.chapterService.insertChapter(chapterVO);
            ReturnObject returnObject = AnaUtils.initReturn(true, "\u521b\u5efa\u7ae0\u8282\u6210\u529f", chapterDefine.getArcKey());
            return returnObject;
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u65b0\u589e\u7ae0\u8282\u5f02\u5e38", e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            ReturnObject returnObject = AnaUtils.initReturn(false, ChapterErrorEnum.NRANALYSISECHAPTERRRORENUM_1101.getMessage(), e.getMessage());
            return returnObject;
        }
        finally {
            AnalysisReportLogHelper.log("\u65b0\u589e\u7ae0\u8282\u7ed3\u675f", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
    }

    @RequestMapping(value={"/getChapter/{chapterId}"}, method={RequestMethod.POST})
    public ReturnObject getChapter(@PathVariable(value="chapterId") String chapterId) throws Exception {
        try {
            ReportChapterDefine chapterDefine = this.chapterService.getChapterById(chapterId);
            return AnaUtils.initReturn(true, "\u67e5\u8be2\u7ae0\u8282\u6210\u529f", chapterDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, ChapterErrorEnum.NRANALYSISECHAPTERRRORENUM_1101.getMessage(), e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:chapter"})
    @RequestMapping(value={"/updateChapter"}, method={RequestMethod.POST})
    public ReturnObject updateChapter(@RequestBody ChapterVO chapterVO) throws Exception {
        AnalysisReportLogHelper.log("\u66f4\u65b0\u7ae0\u8282\u5f00\u59cb", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        if (StringUtils.isEmpty((CharSequence)chapterVO.getArcKey())) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u7ae0\u8282");
        }
        if (StringUtils.isEmpty((CharSequence)chapterVO.getArcName())) {
            return AnaUtils.initReturn(false, "\u672a\u586b\u5199\u7ae0\u8282\u6807\u9898");
        }
        if (chapterVO.getArcName().length() > 150) {
            return AnaUtils.initReturn(false, "\u6807\u9898\u5b57\u6570\u8d85\u957f\uff0c\u8bf7\u4fee\u6539");
        }
        try {
            ReportChapterDefine chapter = this.chapterService.getChapterById(chapterVO.getArcKey());
            if (chapter != null && !chapter.getArcName().equals(chapterVO.getArcName()) && this.chapterService.checkChapterName(chapterVO.getArcAtKey(), chapterVO.getArcParent(), chapterVO.getArcName()) > 0) {
                ReturnObject returnObject = AnaUtils.initReturn(false, "\u6807\u9898\u91cd\u590d\uff0c\u8bf7\u4fee\u6539");
                return returnObject;
            }
            chapter.setArcName(chapterVO.getArcName());
            chapter.setTypeSpeed(chapterVO.getTypeSpeed());
            this.chapterService.updateChapter(TransUtil.transDtoToVo(chapter));
            AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(chapterVO.getArcAtKey());
            AnalysisReportLogHelper.log("\u66f4\u65b0\u7ae0\u8282", "\u66f4\u65b0\u7ae0\u8282\u540d\u79f0: " + analysisReportDefine.getTitle() + "-" + chapter.getArcName() + "\u66f4\u65b0\u4e3a " + analysisReportDefine.getTitle() + "-" + chapterVO.getArcName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            ReturnObject returnObject = AnaUtils.initReturn(true, "\u66f4\u65b0\u7ae0\u8282\u540d\u79f0\u6210\u529f", chapterVO.getArcKey());
            return returnObject;
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u66f4\u65b0\u7ae0\u8282\u540d\u79f0\u5f02\u5e38", e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            ReturnObject returnObject = AnaUtils.initReturn(false, ChapterErrorEnum.NRANALYSISECHAPTERRRORENUM_1102.getMessage(), e.getMessage());
            return returnObject;
        }
        finally {
            AnalysisReportLogHelper.log("\u66f4\u65b0\u7ae0\u8282\u540d\u79f0\u7ed3\u675f", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:chapter"})
    @RequestMapping(value={"/deleteChapter/{chapterId}"}, method={RequestMethod.GET})
    public ReturnObject deleteChapter(@PathVariable(value="chapterId") String chapterId) {
        AnalysisReportLogHelper.log("\u5220\u9664\u7ae0\u8282\u5f00\u59cb", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        if (StringUtils.isEmpty((CharSequence)chapterId)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u7ae0\u8282");
        }
        try {
            if (this.chapterService.checkCanDelete(chapterId)) {
                ReturnObject returnObject = AnaUtils.initReturn(false, "\u5220\u9664\u7ae0\u8282\u5931\u8d25\uff0c\u5f53\u524d\u7ae0\u8282\u662f\u5f53\u524d\u6a21\u677f\u6700\u540e\u4e00\u4e2a\u7ae0\u8282\uff08\u6392\u9664\u5f15\u7528\u5f15\u7528\uff09");
                return returnObject;
            }
            this.chapterService.deleteChapter(chapterId);
            ReturnObject returnObject = AnaUtils.initReturn(true, "\u5220\u9664\u7ae0\u8282\u6210\u529f");
            return returnObject;
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5220\u9664\u7ae0\u8282\u5f02\u5e38", e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            ReturnObject returnObject = AnaUtils.initReturn(false, ChapterErrorEnum.NRANALYSISECHAPTERRRORENUM_1103.getMessage(), e.getMessage());
            return returnObject;
        }
        finally {
            AnalysisReportLogHelper.log("\u5220\u9664\u7ae0\u8282\u7ed3\u675f", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequestMapping(value={"/changeChapterOrder/{originChapterId}/{targetChapterId}"}, method={RequestMethod.GET})
    public ReturnObject changeChapterOrder(@PathVariable(value="originChapterId") String originChapterId, @PathVariable(value="targetChapterId") String targetChapterId) {
        AnalysisReportLogHelper.log("\u4fee\u6539\u7ae0\u8282\u987a\u5e8f\u5f00\u59cb", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        if (StringUtils.isEmpty((CharSequence)originChapterId) || StringUtils.isEmpty((CharSequence)targetChapterId)) {
            return AnaUtils.initReturn(false, "\u7ae0\u8282\u79fb\u52a8\u53c2\u6570\u5f02\u5e38");
        }
        try {
            this.chapterService.changeChapterOrder(originChapterId, targetChapterId);
            ReturnObject returnObject = AnaUtils.initReturn(true, "\u7ae0\u8282\u79fb\u52a8\u6210\u529f");
            return returnObject;
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u4fee\u6539\u7ae0\u8282\u987a\u5e8f\u5f02\u5e38", e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            ReturnObject returnObject = AnaUtils.initReturn(false, ChapterErrorEnum.NRANALYSISECHAPTERRRORENUM_1104.getMessage(), e.getMessage());
            return returnObject;
        }
        finally {
            AnalysisReportLogHelper.log("\u4fee\u6539\u7ae0\u8282\u987a\u5e8f\u7ed3\u675f", "", AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
    }

    @RequestMapping(value={"/checkIsHistoryTemplate/{modelId}"}, method={RequestMethod.GET})
    public ReturnObject checkIsHistoryTemplate(@PathVariable(value="modelId") String modelId) {
        if (StringUtils.isEmpty((CharSequence)modelId)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            List<ReportChapterDefine> reportChapterDefines = this.chapterService.queryChapterByModelId(modelId);
            return AnaUtils.initReturn(true, "\u5224\u65ad\u662f\u5426\u662f\u5386\u53f2\u6a21\u677f\u6210\u529f", reportChapterDefines.size() == 0);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, "\u5224\u65ad\u662f\u5426\u662f\u5386\u53f2\u6a21\u677f\u5931\u8d25", e.getMessage());
        }
    }

    @RequestMapping(value={"/load-chapterTree"}, method={RequestMethod.POST})
    public ReturnObject loadChapterTree(@RequestBody ChapterTreeQueryVo chapterTreeQueryVo) {
        if (StringUtils.isEmpty((CharSequence)chapterTreeQueryVo.getModelId())) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            List<ChapterVO> chapterTree = this.chapterService.getChapterTree(chapterTreeQueryVo);
            ObjectMapper mapper = new ObjectMapper();
            return AnaUtils.initReturn(mapper.valueToTree(chapterTree));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, "\u83b7\u53d6\u7ae0\u8282\u6811\u5931\u8d25", e.getMessage());
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.analysisreport.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.transfer.dto.NRARTransferDto;
import com.jiuqi.nr.analysisreport.transfer.util.TransferUtil;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class NRARModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(NRARModelTransfer.class);
    @Autowired
    private AnalysisHelper analysisHelper;
    @Autowired
    private IChapterService chapterService;

    @Transactional(rollbackFor={Exception.class})
    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            NRARTransferDto nrarTransferDto = (NRARTransferDto)objectMapper.readValue(bytes, NRARTransferDto.class);
            AnalysisReportDefine analysisReportDefine = nrarTransferDto.getAnalysisReportDefine();
            List<ReportChapterDefine> chapterDefineList = nrarTransferDto.getReportChapterDefineList();
            AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(analysisReportDefine.getKey());
            if (analysisReportDefine == null) {
                return;
            }
            Date updateTime = new Date();
            analysisReportDefine.setUpdateTime(updateTime);
            if (reportDefine != null) {
                this.analysisHelper.updateModel(analysisReportDefine);
            } else {
                this.analysisHelper.insertModel(analysisReportDefine);
            }
            if (CollectionUtils.isEmpty(chapterDefineList)) {
                return;
            }
            for (ReportChapterDefine reportChapterDefine : chapterDefineList) {
                ReportChapterDefine chapter = this.chapterService.getChapterById(reportChapterDefine.getArcKey());
                reportChapterDefine.setArcUpdatetime(updateTime);
                if (chapter != null) {
                    this.chapterService.updateChapter(reportChapterDefine);
                    continue;
                }
                this.chapterService.insertChapter(reportChapterDefine);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u5165\u5206\u6790\u62a5\u544a\u6a21\u677f\u6a21\u677f\u5931\u8d25");
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String nodeKey) throws TransferException {
        MetaExportModel exportModel = new MetaExportModel();
        try {
            nodeKey = TransferUtil.getkey(nodeKey);
            AnalysisReportDefine model = this.analysisHelper.getListByKey(nodeKey);
            List<ReportChapterDefine> chapterList = this.chapterService.queryChapterByModelId(nodeKey);
            NRARTransferDto nrarTransferDto = new NRARTransferDto();
            nrarTransferDto.setAnalysisReportDefine(model);
            nrarTransferDto.setReportChapterDefineList(chapterList);
            ObjectMapper objectMapper = new ObjectMapper();
            exportModel.setData(objectMapper.writeValueAsBytes((Object)nrarTransferDto));
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u51fa\u5206\u6790\u62a5\u544a\u6a21\u677f\u6a21\u677f\u5931\u8d25");
        }
        return exportModel;
    }
}


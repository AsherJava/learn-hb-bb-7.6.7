/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.ConsSystemParam
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.consolidatedsystem.service.reportsync;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.ConsSystemParam;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportConSystemTask
implements IReportSyncExportTask {
    @Autowired
    private ConsolidatedSystemService systemService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private UnionRuleDao ruleDao;
    @Autowired
    private InputDataSchemeCacheService inputDataSchemeCacheService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean match(ReportDataSyncParams dataSyncParam) {
        ConsSystemParam consSystemParam = dataSyncParam.getConsSystemParam();
        return null != consSystemParam && !StringUtils.isEmpty((String)consSystemParam.getSystemId());
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        ArrayList<String> msgList = new ArrayList<String>();
        String filePath = rootFolder.getPath();
        ConsSystemParam consSystemParam = dataSyncParam.getConsSystemParam();
        ConsolidatedSystemEO systemEO = this.systemService.getConsolidatedSystemEO(consSystemParam.getSystemId());
        if (null == systemEO) {
            return msgList;
        }
        msgList.add(this.exportConSystem(filePath, systemEO));
        msgList.add(this.exportRule(filePath, systemEO));
        msgList.add(this.exportSubject(filePath, systemEO));
        msgList.add(this.exportFormScheme(filePath, systemEO));
        msgList.add(this.exportOption(filePath, systemEO));
        msgList.add(this.exportInputDataScheme(filePath, systemEO));
        msgList.remove(null);
        return null;
    }

    private String exportOption(String filePath, ConsolidatedSystemEO systemEO) {
        ConsolidatedOptionVO optionVO = this.optionService.getOptionData(systemEO.getId());
        try {
            CommonReportUtil.writeFileJson((Object)optionVO, (String)(filePath + "/GC-consoption"));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return String.format("\u5408\u5e76\u4f53\u7cfb\u9009\u9879\u5bfc\u51fa\u5931\u8d25\uff1a%1$s", e.getMessage());
        }
        return null;
    }

    private String exportFormScheme(String filePath, ConsolidatedSystemEO systemEO) {
        List<ConsolidatedTaskVO> taskVOList = this.taskService.getConsolidatedTasks(systemEO.getId());
        try {
            CommonReportUtil.writeFileJson(taskVOList, (String)(filePath + "/GC-constask"));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return String.format("\u5408\u5e76\u4f53\u7cfb\u62a5\u8868\u9875\u7b7e\u5bfc\u51fa\u5931\u8d25\uff1a%1$s", e.getMessage());
        }
        return null;
    }

    private String exportSubject(String filePath, ConsolidatedSystemEO systemEO) {
        SXSSFWorkbook workbook = ExpImpUtils.getWorkbook((String)"SystemDataExportExecutor", (String)UUIDOrderUtils.newUUIDStr(), (String)("{\"systemId\":\"" + systemEO.getId() + "\"}"));
        try {
            CommonReportUtil.writeWorkbook((SXSSFWorkbook)workbook, (String)(filePath + "/GC-conssubject"));
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            return String.format("\u5408\u5e76\u79d1\u76ee\u5bfc\u51fa\u5931\u8d25\uff1a%1$s", e.getMessage());
        }
        return null;
    }

    private String exportRule(String filePath, ConsolidatedSystemEO systemEO) {
        List<UnionRuleEO> rulesWithGroup = this.ruleDao.findRuleListByReportSystemIdWithGroup(systemEO.getId());
        ArrayList<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        for (UnionRuleEO unionRuleEO : rulesWithGroup) {
            AbstractUnionRule unionRuleDTO = UnionRuleConverter.convert(unionRuleEO);
            unionRuleDTOList.add(unionRuleDTO);
        }
        try {
            CommonReportUtil.writeFileJson(unionRuleDTOList, (String)(filePath + "/GC-unionruleNew"));
        }
        catch (Exception e) {
            this.logger.error("\u5408\u5e76\u89c4\u5219\u5bfc\u51fa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage());
        }
        return null;
    }

    private String exportConSystem(String filePath, ConsolidatedSystemEO systemEO) {
        try {
            CommonReportUtil.writeFileJson((Object)((Object)systemEO), (String)(filePath + "/GC-conssystem"));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return String.format("\u5408\u5e76\u4f53\u7cfb\u5bfc\u51fa\u5931\u8d25\uff1a%1$s", e.getMessage());
        }
        return null;
    }

    private String exportInputDataScheme(String filePath, ConsolidatedSystemEO systemEO) {
        String dataSchemeKey = systemEO.getDataSchemeKey();
        InputDataSchemeVO inputDataScheme = this.inputDataSchemeCacheService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
        if (null != inputDataScheme) {
            try {
                CommonReportUtil.writeFileJson((Object)inputDataScheme, (String)(filePath + "/inputDataSchme.txt"));
            }
            catch (IOException e) {
                this.logger.error(e.getMessage(), e);
                return String.format("\u6570\u636e\u65b9\u6848\u548c\u5185\u90e8\u8868\u7684\u6620\u5c04\u5173\u7cfb\u5bfc\u51fa\u5931\u8d25\uff1a%1$s", e.getMessage());
            }
        }
        return null;
    }

    public String funcTitle() {
        return "\u5408\u5e76\u4f53\u7cfb";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}


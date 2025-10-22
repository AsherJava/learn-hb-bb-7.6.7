/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataimport.service.ImportDispatchService
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.param.InvestDataParam
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.gcreport.billcore.api.CommonBillClient
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao
 *  com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.invest.reportsync;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataimport.service.ImportDispatchService;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.param.InvestDataParam;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.gcreport.billcore.api.CommonBillClient;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.util.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ReportSyncImportInvestBillDataTask
implements IReportSyncImportTask {
    private final String INVEST_FOLDER_NAME = "GC-data-investbill";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommonBillClient commonBillClient;
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private FairValueBillDao fairValueBillDao;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"GC-data-investbill");
        ArrayList<String> logMsg = new ArrayList<String>();
        String json = CommonReportUtil.readJsonFile((String)(filePath + "/param.txt"));
        InvestDataParam param = (InvestDataParam)JsonUtils.readValue((String)json, InvestDataParam.class);
        String periodStr = param.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr) || param.getPeriodStr().charAt(4) != 'Y') {
            return logMsg;
        }
        String orgType = (String)this.commonBillClient.getOrgType("GCBILL_B_INVESTBILL").getData();
        File investFolder = new File(filePath);
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Map<String, Object> params = this.buildParams(orgType, periodStr);
        Object msg = null;
        int number = 0;
        for (File file : investFolder.listFiles()) {
            if (file.getName().equals("param.txt")) continue;
            String unitCode = file.getName();
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(unitCode);
            if (orgCacheVO == null) {
                logMsg.add("\u4ee3\u7801\u4e3a" + unitCode + "\u7684\u5355\u4f4d\u4e0d\u5b58\u5728\u3002");
                continue;
            }
            if (orgCacheVO.getOrgKind() == GcOrgKindEnum.UNIONORG || orgCacheVO.getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
                logMsg.add("\u4ee3\u7801\u4e3a" + unitCode + "\u7684\u5355\u4f4d\u4e3a\u5408\u5e76\u6237\u6216\u5dee\u989d\u6237\uff0c\u8df3\u8fc7");
                continue;
            }
            params.put("mergeUnit", unitCode);
            List<Map<String, Object>> investBills = this.investBillDao.listInvestBillsByPaging(params);
            this.deleteInvestData(investBills);
            Workbook workbook = CommonReportUtil.readWorkBook((String)file.getPath());
            msg = ((ImportDispatchService)SpringContextUtils.getBean(ImportDispatchService.class)).dataImport(workbook, "InvestBillImportExecutor", UUIDOrderUtils.newUUIDStr(), JsonUtils.writeValueAsString(params));
            ++number;
            if (msg == null || StringUtils.isEmpty((String)msg.toString())) {
                logMsg.add("\u5355\u4f4d\u3010" + orgCacheVO.getTitle() + "\u3011\u5bfc\u5165\u6210\u529f");
                continue;
            }
            logMsg.add("\u5355\u4f4d\u3010" + orgCacheVO.getTitle() + "\u3011\u5bfc\u5165\u5931\u8d25\uff1a" + msg);
        }
        if (number == 0) {
            logMsg.add(String.format("\u65e0\u7b26\u5408\u53f0\u8d26\u6570\u636e\uff0c\u8bf7\u68c0\u67e5\u4e0a\u4f20\u65b9\u6848\u5185\u5bb9", new Object[0]));
        } else {
            logMsg.add(String.format("\u6295\u8d44\u53f0\u8d26\u5bfc\u5165\u5b8c\u6210", new Object[0]));
        }
        return logMsg;
    }

    private void deleteInvestData(List<Map<String, Object>> investBills) {
        ArrayList<String> delInvestIdList = new ArrayList<String>();
        for (Map<String, Object> investBill : investBills) {
            String srcId = (String)investBill.get("SRCID");
            int period = ConverterUtils.getAsIntValue((Object)investBill.get("PERIOD"));
            List<Map<String, Object>> investBillList = this.investBillDao.listByWhere(new String[]{"SRCID"}, new Object[]{srcId});
            investBillList.sort(Comparator.comparing(item -> ConverterUtils.getAsIntValue(item.get("PERIOD"))));
            if (period == ConverterUtils.getAsIntValue((Object)investBillList.get(0).get("PERIOD"))) {
                List fvchMasterData = InvestBillTool.listByWhere((String[])new String[]{"SRCID"}, (Object[])new Object[]{srcId}, (String)"GC_FVCHBILL");
                if (!CollectionUtils.isEmpty(fvchMasterData)) {
                    List fvchMasterIdList = fvchMasterData.stream().map(v -> v.get("ID").toString()).collect(Collectors.toList());
                    for (String fvchMasterId : fvchMasterIdList) {
                        this.deleteByMasterId("GC_FVCH_FIXEDITEM", fvchMasterId);
                        this.deleteByMasterId("GC_FVCH_OTHERITEM", fvchMasterId);
                        this.deleteById("GC_FVCHBILL", fvchMasterId);
                    }
                }
                GcOffSetVchrItemInitEO entity = new GcOffSetVchrItemInitEO();
                entity.setSrcOffsetGroupId(srcId);
                int number = this.offSetVchrItemInitDao.countByEntity((BaseEntity)entity);
                this.logger.info("\u67e5\u8be2\u5230" + number + "\u6761\u5206\u5f55\u521d\u59cb\u5316\u6570\u636e");
                if (number > 0) {
                    int deleteNumber = this.offSetVchrItemInitDao.delete((BaseEntity)entity);
                    this.logger.info("\u5220\u9664\u4e86" + deleteNumber + "\u6761\u5206\u5f55\u521d\u59cb\u5316\u6570\u636e");
                }
            }
            List<String> investBillIds = this.investBillDao.getInvestIdsBySrcIdAndBeginPeriod(srcId, period);
            delInvestIdList.addAll(investBillIds);
        }
        this.investBillDao.batchDeleteByIdList(delInvestIdList);
    }

    private Map<String, Object> buildParams(String orgType, String periodStr) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("defineCode", "GCBILL_B_INVESTBILL");
        params.put("orgType", orgType);
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        params.put("acctYear", String.valueOf(yp.getYear()));
        params.put("acctPeriod", String.valueOf(yp.getPeriod()));
        params.put("periodStr", periodStr);
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        return params;
    }

    public String funcTitle() {
        return "\u6295\u8d44\u53f0\u8d26";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }

    public void deleteById(String tableName, String id) {
        if (StringUtils.isEmpty((String)id)) {
            return;
        }
        String sql = " delete from " + tableName + " where ID = ? \n";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{id});
    }

    public void deleteByMasterId(String tableName, String masterId) {
        if (StringUtils.isEmpty((String)masterId)) {
            return;
        }
        String sql = " delete from " + tableName + " where MASTERID = ? \n";
        EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{masterId});
    }
}


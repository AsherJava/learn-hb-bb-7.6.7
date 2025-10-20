/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  javax.servlet.ServletContext
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestBillModuleInitiator;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestIdDataRepair;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InvestMultipleVersionTask
implements InvestBillModuleInitiator {
    private transient Logger logger = LoggerFactory.getLogger(InvestMultipleVersionTask.class);

    @Transactional(rollbackFor={Exception.class})
    public void initWhenStarted(ServletContext context) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        this.investMultipleSplit(jdbcTemplate);
        this.investIdRepair(jdbcTemplate);
    }

    private void investIdRepair(JdbcTemplate jdbcTemplate) {
        try {
            InvestIdDataRepair investIdDataRepair = (InvestIdDataRepair)SpringContextUtils.getBean(InvestIdDataRepair.class);
            investIdDataRepair.execute();
        }
        catch (Exception e) {
            this.logger.error("\u6295\u8d44\u53f0\u8d26\u591a\u65f6\u671f\u62c6\u5206\u5f02\u5e38\u6570\u636e\u4fee\u590d\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void investMultipleSplit(JdbcTemplate jdbcTemplate) {
        boolean needBindUser = null == ShiroUtil.getUser();
        try {
            if (needBindUser) {
                this.bindUser();
            }
            int countPeriodIsNotNull = this.countPeriodIsNotNull();
            List<Map<String, Object>> periodIsNullInvestBills = this.getPeriodIsNullInvestBills();
            if (countPeriodIsNotNull > 0) {
                if (CollectionUtils.isEmpty(periodIsNullInvestBills)) {
                    this.logger.info("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5df2\u67e5\u8be2\u5230\u591a\u65f6\u671f\u6570\u636e\uff0c\u4e0d\u6267\u884c\u4fee\u590d\u3002");
                } else {
                    this.logger.error("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5b58\u5728\u672a\u62c6\u5206(\u975e\u591a\u65f6\u671f)\u6570\u636e\u548c\u591a\u65f6\u671f\u6570\u636e\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u5408\u7406\u6027\u3002");
                }
                return;
            }
            if (countPeriodIsNotNull == 0 && CollectionUtils.isEmpty(periodIsNullInvestBills)) {
                this.logger.info("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u7a7a\u5e93\u8d77\u670d\u52a1\u6216\u65e0\u53f0\u8d26\u6570\u636e\uff0c\u65e0\u9700\u4fee\u590d");
                return;
            }
            this.logger.info("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5f00\u59cb\u4fee\u590d\u5386\u53f2\u6570\u636e\u3002");
            try {
                this.repairFvchSrcId(jdbcTemplate);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5173\u8054\u516c\u5141\u4ef7\u503c\u53f0\u8d26\u4fee\u590d\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            try {
                this.repairOffsetInitSrcId(jdbcTemplate);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5173\u8054\u521d\u59cb\u5316\u5206\u5f55\u4fee\u590d\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            try {
                this.backupInvest(jdbcTemplate);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u5907\u4efd\u6295\u8d44\u53f0\u8d26\u4e3b\u5b50\u8868\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            try {
                this.repairInvestBillData(periodIsNullInvestBills);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u6295\u8d44\u53f0\u8d26\u62c6\u5206\u6210\u591a\u7248\u672c\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            this.logger.info("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u4fee\u590d\u5386\u53f2\u6570\u636e\u7ed3\u675f\u3002");
        }
        catch (Exception e) {
            this.logger.error("\u53f0\u8d26\u591a\u65f6\u671f\uff1a\u4fee\u590d\u5386\u53f2\u6570\u636e\u51fa\u73b0\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
        finally {
            if (needBindUser) {
                ShiroUtil.unbindUser();
            }
        }
    }

    private void bindUser() {
        NvwaSystemUserClient systemUserClient = (NvwaSystemUserClient)SpringContextUtils.getBean(NvwaSystemUserClient.class);
        SystemUserDTO systemUserDTO = (SystemUserDTO)systemUserClient.getUsers().get(0);
        UserLoginDTO user = new UserLoginDTO();
        user.setId(systemUserDTO.getId());
        user.setName(user.getName());
        user.setUsername(systemUserDTO.getName());
        user.setTenantName("__default_tenant__");
        user.setLoginDate(new Date());
        ShiroUtil.bindUser((UserLoginDTO)user);
    }

    private void repairInvestBillData(List<Map<String, Object>> allInvestBill) {
        ArrayList<BillModelImpl> billModels = new ArrayList<BillModelImpl>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> investBill : allInvestBill) {
            List subItems = InvestBillTool.getBillItemByMasterId((String)((String)investBill.get("ID")), (String)"GC_INVESTBILLITEM");
            List<Map<String, Object>> orderSubItems = this.orderSubItems(subItems);
            orderSubItems = orderSubItems.stream().filter(item -> {
                item.put("SRCID", UUIDOrderUtils.newUUIDStr());
                return ConverterUtils.getAsIntValue(item.get("SRCTYPE")) != OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue();
            }).collect(Collectors.toList());
            Map<Integer, List<Map<String, Object>>> month2InvestItemsMap = this.getMonth2InvestItemsMap(formatter, orderSubItems);
            ArrayList newSubItems = new ArrayList();
            String masterSrcId = (String)investBill.get("ID");
            int disposeMonth = 0;
            Object disposeDate = investBill.get("DISPOSEDATE");
            if (null != disposeDate) {
                disposeMonth = DateUtils.getDateFieldValue((Date)((Date)disposeDate), (int)2);
            }
            for (int i = 1; i <= 12; ++i) {
                if (!CollectionUtils.isEmpty((Collection)month2InvestItemsMap.get(i))) {
                    newSubItems.addAll(month2InvestItemsMap.get(i));
                }
                BillModelImpl billModel2 = this.createBillModel("GCBILL_B_INVESTBILL");
                if (i == 12) {
                    billModel2.loadByCode((String)investBill.get("BILLCODE"));
                    billModel2.edit();
                    billModel2.getRuler().getRulerExecutor().setEnable(true);
                    List subItemList = ((DataTableImpl)billModel2.getData().getTables().get("GC_INVESTBILLITEM")).getRowsData();
                    subItemList.forEach(subItem -> {
                        if (ConverterUtils.getAsIntValue(subItem.get("SRCTYPE")) == OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue()) {
                            ((DataTableImpl)billModel2.getData().getTables().get("GC_INVESTBILLITEM")).deleteRowById(subItem.get("ID"));
                        }
                    });
                    orderSubItems.forEach(item -> item.put("INPUTDATE", item.get("CHANGEDATE")));
                    ((DataTableImpl)billModel2.getData().getTables().get("GC_INVESTBILLITEM")).updateRows(orderSubItems);
                } else {
                    billModel2.add();
                    billModel2.getRuler().getRulerExecutor().setEnable(true);
                    String newBillCode = InvestBillTool.getBillCode((String)"GCBILL_B_INVESTBILL", (String)((String)investBill.get("UNITCODE")));
                    investBill.remove("ID");
                    billModel2.getMaster().setData(investBill);
                    billModel2.getMaster().setValue("BILLCODE", (Object)newBillCode);
                    if (disposeMonth > 0 && i < disposeMonth) {
                        billModel2.getMaster().setValue("DISPOSEDATE", null);
                        billModel2.getMaster().setValue("DISPOSEFLAG", (Object)InvestInfoEnum.DISPOSE_UNDO.getCode());
                    }
                    for (Map subRecord : newSubItems) {
                        DataRow dataRow = billModel2.getTable("GC_INVESTBILLITEM").appendRow();
                        subRecord.remove("ID");
                        subRecord.remove("MASTERID");
                        subRecord.remove("BILLCODE");
                        subRecord.put("INPUTDATE", subRecord.get("CHANGEDATE"));
                        dataRow.setData(subRecord);
                    }
                }
                billModel2.getMaster().setValue("PERIOD", (Object)i);
                billModel2.getMaster().setValue("SRCID", (Object)masterSrcId);
                billModels.add(billModel2);
            }
            if (!CollectionUtils.isEmpty(billModels)) {
                billModels.stream().forEach(billModel -> {
                    ActionRequest request = new ActionRequest();
                    request.setParams(new HashMap());
                    ActionResponse response = new ActionResponse();
                    SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
                    billModel.getContext().setContextValue("SKIP_SELF_MODEL", (Object)true);
                    try {
                        billModel.executeAction((Action)saveAction, request, response);
                    }
                    catch (Exception e) {
                        this.logger.error("\u4fdd\u5b58\u5931\u8d25\uff1a" + e.getMessage(), e);
                    }
                });
            }
            billModels.clear();
        }
    }

    private void repairFvchSrcId(JdbcTemplate jdbcTemplate) {
        String backupSql = "create table DYYMMDD_GC_FVCHBILL as (select * from gc_fvchbill)";
        jdbcTemplate.execute(backupSql);
        String backupSub1Sql = "create table DYYMMDD_GC_FVCH_FIXEDITEM as (select * from GC_FVCH_FIXEDITEM)";
        jdbcTemplate.execute(backupSub1Sql);
        String backupSub2Sql = "create table DYYMMDD_GC_FVCH_OTHERITEM as (select * from GC_FVCH_OTHERITEM)";
        jdbcTemplate.execute(backupSub2Sql);
        String repairSql = "UPDATE gc_fvchbill A \n SET A.srcid = ( SELECT B.id FROM gc_investbill B \n WHERE A.acctyear = B.acctyear \n AND A.unitcode = B.unitcode \n AND A.investedunit = B.investedunit) WHERE EXISTS ( \n SELECT 1 FROM gc_investbill B \n WHERE  A.acctyear = B.acctyear \n AND A.unitcode = B.unitcode \n AND A.investedunit = B.investedunit ) \n";
        jdbcTemplate.execute(repairSql);
    }

    private void repairOffsetInitSrcId(JdbcTemplate jdbcTemplate) {
        String backupSql = "create table DYYMMDD_GC_OFFSETVCHRITEM_INIT as (select * from GC_OFFSETVCHRITEM_INIT t where t.ruleid in(select u.id from gc_unionrule u where u.ruletype in('DIRECT_INVESTMENT','INDIRECT_INVESTMENT', 'PUBLIC_VALUE_ADJUSTMENT')))";
        jdbcTemplate.execute(backupSql);
        String repairSql = "UPDATE GC_OFFSETVCHRITEM_INIT A SET A.srcoffsetgroupid = (SELECT min(B.id) \n FROM gc_investbill B \n WHERE A.acctyear = B.acctyear \nAND (A.unitid = B.unitcode AND A.oppunitid = B.investedunit or A.unitid = B.investedunit AND A.oppunitid = B.unitcode) \n AND A.ruleid in(select u.id from gc_unionrule u where u.ruletype in('DIRECT_INVESTMENT','INDIRECT_INVESTMENT', 'PUBLIC_VALUE_ADJUSTMENT')) \n ) WHERE EXISTS ( \n SELECT 1 FROM gc_investbill B \n WHERE  A.acctyear = B.acctyear \n AND (A.unitid = B.unitcode AND A.oppunitid = B.investedunit or A.unitid = B.investedunit AND A.oppunitid = B.unitcode)\n AND A.ruleid in(select u.id from gc_unionrule u where u.ruletype in('DIRECT_INVESTMENT','INDIRECT_INVESTMENT', 'PUBLIC_VALUE_ADJUSTMENT')) \n ) AND NOT EXISTS ( \n SELECT 1 FROM gc_investbill C  \n where C.id=A.srcoffsetgroupid)";
        jdbcTemplate.execute(repairSql);
    }

    private void backupInvest(JdbcTemplate jdbcTemplate) {
        String backupInvestSql = "create table DYYMMDD_GC_INVESTBILL as (select * from GC_INVESTBILL)";
        jdbcTemplate.execute(backupInvestSql);
        String backupInvestItemSql = "create table DYYMMDD_GC_INVESTBILLITEM as (select * from GC_INVESTBILLITEM)";
        jdbcTemplate.execute(backupInvestItemSql);
    }

    private int countPeriodIsNotNull() {
        String sql = "select count(1) from GC_INVESTBILL t where t.period is not null";
        Integer periodNotNullCount = (Integer)EntNativeSqlDefaultDao.getInstance().selectFirst(Integer.class, sql, new Object[0]);
        return periodNotNullCount == null ? 0 : periodNotNullCount;
    }

    private List<Map<String, Object>> getPeriodIsNullInvestBills() {
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"t") + " from " + "GC_INVESTBILL" + " t where t.period is null";
        List allInvestBill = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
        return allInvestBill;
    }

    @NotNull
    private Map<Integer, List<Map<String, Object>>> getMonth2InvestItemsMap(DateTimeFormatter formatter, List<Map<String, Object>> orderSubItems) {
        List subItemsTemp = orderSubItems.stream().map(map -> new HashMap(map)).collect(Collectors.toList());
        Map<Integer, List<Map<String, Object>>> month2InvestItemsMap = subItemsTemp.stream().collect(Collectors.groupingBy(item -> {
            Object changeDate = item.get("CHANGEDATE");
            if (changeDate instanceof String) {
                LocalDateTime dateTime = LocalDateTime.parse(ConverterUtils.getAsString(changeDate), formatter);
                return dateTime.getMonth().getValue();
            }
            if (changeDate instanceof Date) {
                return DateUtils.getDateFieldValue((Date)((Date)changeDate), (int)2);
            }
            return 0;
        }));
        return month2InvestItemsMap;
    }

    private BillModelImpl createBillModel(String billDefineName) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContext, billDefineName);
        model.getRuler().getRulerExecutor().setEnable(true);
        return model;
    }

    private List sortItems(List<Map<String, Object>> subItemsMap) {
        if (CollectionUtils.isEmpty(subItemsMap)) {
            return new ArrayList();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        subItemsMap.sort((o1, o2) -> {
            Object o2ChangeDate = o2.get("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.get("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            LocalDateTime dateTime1 = LocalDateTime.parse(ConverterUtils.getAsString(o2ChangeDate), formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(ConverterUtils.getAsString(o1ChangeDate), formatter);
            dateTime1.isBefore(dateTime2);
            dateTime1.isAfter(dateTime2);
            if (dateTime1.isBefore(dateTime2)) {
                return 1;
            }
            return -1;
        });
        return subItemsMap;
    }

    private List orderSubItems(List<Map<String, Object>> subItemsMap) {
        if (CollectionUtils.isEmpty(subItemsMap)) {
            return new ArrayList();
        }
        subItemsMap.sort((o1, o2) -> {
            Object o2ChangeDate = o2.get("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.get("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            return ((Date)o1ChangeDate).compareTo((Date)o2ChangeDate);
        });
        return subItemsMap;
    }

    public void init(ServletContext context) throws Exception {
    }
}


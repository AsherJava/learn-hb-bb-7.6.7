/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.carryover.dao.CarryOverConfigDao
 *  com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.nvwa.glue.client.basedata.datainto.util.UUIDUtils
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.invest.investbillcarryover.task;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.carryover.dao.CarryOverConfigDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestBillModuleInitiator;
import com.jiuqi.gcreport.invest.investbillcarryover.dao.InvestBillCarryOverSettingDao;
import com.jiuqi.gcreport.invest.investbillcarryover.entity.InvestBillCarryOverSettingEO;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutCarryOverModeEnum;
import com.jiuqi.gcreport.invest.investbillcarryover.enums.AccoutTypeEnum;
import com.jiuqi.gcreport.invest.monthcalcscheme.task.ChangeScenarioDataTaskInit;
import com.jiuqi.nvwa.glue.client.basedata.datainto.util.UUIDUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InvestCarryOverSettingInitTask
implements InvestBillModuleInitiator {
    private transient Logger logger = LoggerFactory.getLogger(ChangeScenarioDataTaskInit.class);

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        String querySql = "select count(*) from GC_INVESTCARRYOVERSETTING";
        int investCarryOverSettingCount = EntNativeSqlDefaultDao.getInstance().count(querySql, new Object[0]);
        if (investCarryOverSettingCount > 0) {
            return;
        }
        List baseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_TZYSFILEDDJ");
        if (CollectionUtils.isEmpty((Collection)baseDataList)) {
            this.logger.info("\u6295\u8d44\u5e74\u7ed3\u8bbe\u7f6e\u8868\u4e3a\u7a7a\uff0c\u5355\u636e\u53f0\u8d26\u5e74\u7ed3\u8fd0\u7b97\u57fa\u7840\u6570\u636e\u8868\u4e3a\u7a7a\uff0c \u5224\u65ad\u4e3a\u65b0\u9879\u76ee\uff0c\u5f00\u59cb\u521d\u59cb\u5316\u5e74\u7ed3\u8bbe\u7f6e\u8868\u6570\u636e");
            this.initInvestCarryOverSetting();
            return;
        }
        this.logger.info("\u5386\u53f2\u9879\u76ee\u5347\u7ea7\uff1aMD_TZYSFILEDDJ\u57fa\u7840\u6570\u636e\u8868\u5347\u7ea7\u5f00\u59cb");
        this.initCarryOverConfig();
        this.initCarryOverSetting(baseDataList);
        this.logger.info("\u5386\u53f2\u9879\u76ee\u5347\u7ea7\uff1aMD_TZYSFILEDDJ\u57fa\u7840\u6570\u636e\u8868\u5347\u7ea7\u7ed3\u675f");
    }

    private void initCarryOverSetting(List<GcBaseData> baseDataList) {
        try {
            InvestBillCarryOverSettingDao carryOverSettingDao = (InvestBillCarryOverSettingDao)SpringContextUtils.getBean(InvestBillCarryOverSettingDao.class);
            for (GcBaseData baseData : baseDataList) {
                String accountType = (String)baseData.getFieldVal("ACCOUNTTYPE");
                String begainField = (String)baseData.getFieldVal("BEGAINFIELD");
                String endField = (String)baseData.getFieldVal("ENDFIELD");
                String currentPeriodAdd = (String)baseData.getFieldVal("CURRENTPERIODADD");
                String currentPeriodReduce = (String)baseData.getFieldVal("CURRENTPERIODREDUCE");
                String calcformula = (String)baseData.getFieldVal("CALCFORMULA");
                InvestBillCarryOverSettingEO carryOverSettingEO = new InvestBillCarryOverSettingEO();
                carryOverSettingEO.setAccountType(accountType);
                if (StringUtils.isEmpty((String)begainField)) {
                    this.setChangeZeroMode(carryOverSettingDao, accountType, currentPeriodAdd, currentPeriodReduce);
                    continue;
                }
                if (!StringUtils.isEmpty((String)calcformula)) {
                    carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.CALCFORMULA.getCode());
                    carryOverSettingEO.setTargetField(begainField);
                    carryOverSettingEO.setFormula(calcformula);
                } else if (StringUtils.isEmpty((String)endField)) {
                    carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.CHANGE.getCode());
                    carryOverSettingEO.setTargetField(begainField);
                    carryOverSettingEO.setSourceBeginField(begainField);
                    carryOverSettingEO.setSourceAddField(currentPeriodAdd);
                    carryOverSettingEO.setSourceReduceField(currentPeriodReduce);
                } else {
                    carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.END.getCode());
                    carryOverSettingEO.setTargetField(begainField);
                    carryOverSettingEO.setSourceField(endField);
                }
                carryOverSettingEO.setCarryOverSchemeId("50e76ccf-8aec-00de-6e94-48a362015a5d");
                carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
                carryOverSettingDao.save(carryOverSettingEO);
                this.setChangeZeroMode(carryOverSettingDao, accountType, currentPeriodAdd, currentPeriodReduce);
            }
        }
        catch (Exception e) {
            this.logger.error("MD_TZYSFILEDDJ\u57fa\u7840\u6570\u636e\u8868\u5347\u7ea7\u51fa\u73b0\u9519\u8bef" + e.getMessage(), e);
        }
    }

    private void initInvestCarryOverSetting() {
        InvestBillCarryOverSettingEO carryOverSettingEO;
        this.initCarryOverConfig();
        ArrayList<List<String>> endCarryOverFiled2DescList = new ArrayList<List<String>>();
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINEQUITYRATIO", "ENDEQUITYRATIO", "\u5b57\u6bb5 \u671f\u521d\u80a1\u6743\u6bd4\u4f8b(%) \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u80a1\u6743\u6bd4\u4f8b(%) \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINBOOKBALANCE", "ENDBOOKBALANCE", "\u5b57\u6bb5 \u671f\u521d\u8d26\u9762\u91d1\u989d \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u8d26\u9762\u91d1\u989d \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGININVSTDEVALUEPREP", "ENDINVSTDEVALUEPREP", "\u5b57\u6bb5 \u671f\u521d\u6295\u8d44\u51cf\u503c\u51c6\u5907 \u7684\u503c  \u7b49\u4e8e \u5b57\u6bb5 \u671f\u672b\u6295\u8d44\u51cf\u503c\u51c6\u5907 \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINGOODWILLDEVALUE", "ENDGOODWILLDEVALUE", "\u5b57\u6bb5 \u671f\u521d\u5546\u8a89\u51cf\u503c\u51c6\u5907 \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u5546\u8a89\u51cf\u503c\u51c6\u5907 \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINPROFITLOSSADJUSTMENT", "ENDPROFITLOSSADJUSTMENT", "\u5b57\u6bb5 \u671f\u521d\u635f\u76ca\u8c03\u6574 \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u635f\u76ca\u8c03\u6574 \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINOTHERCAPITALRESERVE", "ENDOTHERCAPITALRESERVE", "\u5b57\u6bb5 \u671f\u521d\u5176\u4ed6\u8d44\u672c\u516c\u79ef \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u5176\u4ed6\u8d44\u672c\u516c\u79ef \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINFAIRVALUE", "ENDFAIRVALUE", "\u5b57\u6bb5 \u671f\u521d\u516c\u5141\u4ef7\u503c\u53d8\u52a8 \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u516c\u5141\u4ef7\u503c\u53d8\u52a8 \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGININVESTMENTCOST", "ENDINVESTMENTCOST", "\u5b57\u6bb5 \u671f\u521d\u6295\u8d44\u6210\u672c \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u6295\u8d44\u6210\u672c \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        endCarryOverFiled2DescList.add(Arrays.asList("BEGINOTHERCOMPREHENSIVEINCOME", "ENDOTHERCOMPREHENSIVEINCOME", "\u5b57\u6bb5 \u671f\u521d\u5176\u4ed6\u7efc\u5408\u6536\u76ca \u7684\u503c  \u7b49\u4e8e  \u5b57\u6bb5 \u671f\u672b\u5176\u4ed6\u7efc\u5408\u6536\u76ca \u4e0a\u5e74\u6700\u540e\u4e00\u671f\u7684\u503c"));
        ArrayList<InvestBillCarryOverSettingEO> carryOverSettingList = new ArrayList<InvestBillCarryOverSettingEO>();
        for (List list : endCarryOverFiled2DescList) {
            carryOverSettingEO = new InvestBillCarryOverSettingEO();
            carryOverSettingEO.setId(UUIDOrderUtils.newUUIDStr());
            carryOverSettingEO.setAccountType(AccoutTypeEnum.INVESTMENT.getCode());
            carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.END.getCode());
            carryOverSettingEO.setTargetField((String)list.get(0));
            carryOverSettingEO.setSourceField((String)list.get(1));
            carryOverSettingEO.setDescription((String)list.get(2));
            carryOverSettingEO.setCarryOverSchemeId("50e76ccf-8aec-00de-6e94-48a362015a5d");
            carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            carryOverSettingList.add(carryOverSettingEO);
        }
        HashMap<String, String> changeCarryOverFiled2Desc = new HashMap<String, String>();
        changeCarryOverFiled2Desc.put("CHANGEBOOKBALANCE", "\u672c\u5e74\u53d8\u52a8\u8d26\u9762\u91d1\u989d  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEINVESTMENTCOST", "\u672c\u5e74\u53d8\u52a8\u6295\u8d44\u6210\u672c  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEEQUITYRATIO", "\u672c\u5e74\u53d8\u52a8\u80a1\u6743\u6bd4\u4f8b(%)  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEPROFITLOSSADJUSTMENT", "\u672c\u5e74\u53d8\u52a8\u635f\u76ca\u8c03\u6574  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEOTHERCAPITALRESERVE", "\u672c\u5e74\u53d8\u52a8\u5176\u4ed6\u8d44\u672c\u516c\u79ef  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEGOODWILLDEVALUE", "\u672c\u5e74\u53d8\u52a8\u5546\u8a89\u51cf\u503c\u51c6\u5907  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEFAIRVALUE", "\u672c\u5e74\u53d8\u52a8\u516c\u5141\u4ef7\u503c\u53d8\u52a8  \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CASHDIVIDENDS", "\u5206\u6d3e\u73b0\u91d1\u80a1\u5229 \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEINVSTDEVALUEPREP", "\u672c\u5e74\u53d8\u52a8\u6295\u8d44\u51cf\u503c\u51c6\u5907 \u7684\u503c  \u7b49\u4e8e0");
        changeCarryOverFiled2Desc.put("CHANGEOTHERCOMPREHENSIVEINCOME", "\u672c\u5e74\u53d8\u52a8\u5176\u4ed6\u7efc\u5408\u6536\u76ca \u7684\u503c  \u7b49\u4e8e0");
        for (Map.Entry entry : changeCarryOverFiled2Desc.entrySet()) {
            String filed = (String)entry.getKey();
            String desc = (String)entry.getValue();
            carryOverSettingEO = new InvestBillCarryOverSettingEO();
            carryOverSettingEO.setId(UUIDOrderUtils.newUUIDStr());
            carryOverSettingEO.setAccountType(AccoutTypeEnum.INVESTMENT.getCode());
            carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.CHANGEZERO.getCode());
            carryOverSettingEO.setTargetField(filed);
            carryOverSettingEO.setDescription(desc);
            carryOverSettingEO.setCarryOverSchemeId("50e76ccf-8aec-00de-6e94-48a362015a5d");
            carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            carryOverSettingList.add(carryOverSettingEO);
        }
        InvestBillCarryOverSettingDao investBillCarryOverSettingDao = (InvestBillCarryOverSettingDao)SpringContextUtils.getBean(InvestBillCarryOverSettingDao.class);
        investBillCarryOverSettingDao.addBatch(carryOverSettingList);
    }

    private void initCarryOverConfig() {
        try {
            CarryOverConfigDao carryOverConfigDao = (CarryOverConfigDao)SpringContextUtils.getBean(CarryOverConfigDao.class);
            CarryOverConfigEO carryOverConfigEO = new CarryOverConfigEO();
            carryOverConfigEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            carryOverConfigEO.setCreateTime(new Date());
            carryOverConfigEO.setTitle("\u6295\u8d44\u53f0\u8d26\u5e74\u7ed3");
            carryOverConfigEO.setParentId(UUIDUtils.emptyUUIDStr());
            carryOverConfigEO.setCreator("admin");
            carryOverConfigEO.setTypeCode(CarryOverTypeEnum.INVEST.getCode());
            carryOverConfigEO.setLeafFlag(Integer.valueOf(1));
            carryOverConfigEO.setId("50e76ccf-8aec-00de-6e94-48a362015a5d");
            carryOverConfigDao.save((DefaultTableEntity)carryOverConfigEO);
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u53f0\u8d26\u5e74\u7ed3\u65b9\u6848\u51fa\u9519" + e.getMessage(), e);
        }
    }

    private void setChangeZeroMode(InvestBillCarryOverSettingDao carryOverSettingDao, String accountType, String currentPeriodAdd, String currentPeriodReduce) {
        InvestBillCarryOverSettingEO carryOverSettingEO;
        if (!StringUtils.isEmpty((String)currentPeriodAdd)) {
            carryOverSettingEO = new InvestBillCarryOverSettingEO();
            carryOverSettingEO.setAccountType(accountType);
            carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.CHANGEZERO.getCode());
            carryOverSettingEO.setTargetField(currentPeriodAdd);
            carryOverSettingEO.setCarryOverSchemeId("50e76ccf-8aec-00de-6e94-48a362015a5d");
            carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            carryOverSettingDao.save(carryOverSettingEO);
        }
        if (!StringUtils.isEmpty((String)currentPeriodReduce)) {
            carryOverSettingEO = new InvestBillCarryOverSettingEO();
            carryOverSettingEO.setAccountType(accountType);
            carryOverSettingEO.setCarryOverMode(AccoutCarryOverModeEnum.CHANGEZERO.getCode());
            carryOverSettingEO.setTargetField(currentPeriodReduce);
            carryOverSettingEO.setCarryOverSchemeId("50e76ccf-8aec-00de-6e94-48a362015a5d");
            carryOverSettingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
            carryOverSettingDao.save(carryOverSettingEO);
        }
    }
}


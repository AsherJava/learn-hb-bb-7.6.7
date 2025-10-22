/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO
 *  com.jiuqi.gcreport.unionrule.dto.DirectInvestmentSegmentDTO
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO
 *  com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentSegmentDTO
 *  com.jiuqi.gcreport.unionrule.dto.InventoryRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.np.core.exception.BusinessException
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.unionrule.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO;
import com.jiuqi.gcreport.unionrule.dto.DirectInvestmentSegmentDTO;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO;
import com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentSegmentDTO;
import com.jiuqi.gcreport.unionrule.dto.InventoryRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.np.core.exception.BusinessException;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UnionRuleTableDataUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UnionRuleTableDataUpdate.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);

    public void execute(DataSource dataSource) throws Exception {
        this.upgrade();
    }

    public void upgrade() {
        List list = this.jdbcTemplate.queryForList("select c.SYSTEMID from GC_CONSTASK c");
        ArrayList<String> systemIds = new ArrayList<String>();
        for (Map constask : list) {
            String systemId2 = String.valueOf(constask.get("SYSTEMID"));
            systemIds.add(systemId2);
        }
        systemIds.forEach(systemId -> {
            List<UnionRuleEO> unionRuleEOs = this.getUnionRuleBySystemId((String)systemId);
            if (CollectionUtils.isEmpty(unionRuleEOs)) {
                return;
            }
            unionRuleEOs.forEach(unionRuleEO -> {
                try {
                    this.upgradeUnionRuleVO((UnionRuleEO)((Object)((Object)unionRuleEO)));
                    String logItem = "\u5408\u5e76\u89c4\u5219\uff1a".concat(unionRuleEO.getTitle()).concat("\u516c\u5f0f\u6570\u636e\u5347\u7ea7\u6210\u529f\u3002\n");
                    logger.info(logItem);
                }
                catch (Exception e) {
                    String logItem = "\u5408\u5e76\u89c4\u5219\uff1a".concat(unionRuleEO.getTitle()).concat("\u516c\u5f0f\u6570\u636e\u5347\u7ea7\u5931\u8d25\uff0c\u8be6\u60c5:").concat("" + e.getMessage()).concat("\u3002\n");
                    logger.error(logItem, e);
                }
            });
        });
    }

    private void upgradeUnionRuleVO(UnionRuleEO unionRuleEO) {
        String ruleType = unionRuleEO.getRuleType();
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)ruleType);
        if (ruleTypeEnum == null) {
            throw new BusinessException("\u7cfb\u7edf\u65e0\u6807\u8bc6\u4e3a[".concat(ruleType).concat("]\u7684\u89c4\u5219\u7c7b\u578b\u3002"));
        }
        String jsonString = unionRuleEO.getJsonString();
        if (StringUtils.isEmpty((String)jsonString)) {
            return;
        }
        FloatLineRuleDTO rule = null;
        switch (ruleTypeEnum) {
            case FIXED_TABLE: {
                List debitItemList;
                FixedTableRuleDTO fixedTableRuleDTO = (FixedTableRuleDTO)JsonUtils.readValue((String)jsonString, FixedTableRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)fixedTableRuleDTO, ruleTypeEnum);
                fixedTableRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)fixedTableRuleDTO, fixedTableRuleDTO.getRuleCondition()));
                fixedTableRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)fixedTableRuleDTO, fixedTableRuleDTO.getOffsetFormula()));
                List creditItemList = fixedTableRuleDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList)) {
                    creditItemList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)fixedTableRuleDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList = fixedTableRuleDTO.getDebitItemList()))) {
                    debitItemList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)fixedTableRuleDTO, item.getFetchFormula())));
                }
                rule = fixedTableRuleDTO;
                break;
            }
            case FIXED_ASSETS: {
                List debitItemList1;
                FixedAssetsRuleDTO fixedAssetsRuleDTO = (FixedAssetsRuleDTO)JsonUtils.readValue((String)jsonString, FixedAssetsRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)fixedAssetsRuleDTO, ruleTypeEnum);
                fixedAssetsRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)fixedAssetsRuleDTO, fixedAssetsRuleDTO.getRuleCondition()));
                fixedAssetsRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)fixedAssetsRuleDTO, fixedAssetsRuleDTO.getOffsetFormula()));
                List creditItemList1 = fixedAssetsRuleDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList1)) {
                    creditItemList1.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)fixedAssetsRuleDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList1 = fixedAssetsRuleDTO.getDebitItemList()))) {
                    debitItemList1.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)fixedAssetsRuleDTO, item.getFetchFormula())));
                }
                rule = fixedAssetsRuleDTO;
                break;
            }
            case INVENTORY: {
                List debitItemList2;
                InventoryRuleDTO inventoryRuleDTO = (InventoryRuleDTO)JsonUtils.readValue((String)jsonString, InventoryRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)inventoryRuleDTO, ruleTypeEnum);
                inventoryRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)inventoryRuleDTO, inventoryRuleDTO.getRuleCondition()));
                inventoryRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)inventoryRuleDTO, inventoryRuleDTO.getOffsetFormula()));
                List creditItemList2 = inventoryRuleDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList2)) {
                    creditItemList2.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inventoryRuleDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList2 = inventoryRuleDTO.getDebitItemList()))) {
                    debitItemList2.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inventoryRuleDTO, item.getFetchFormula())));
                }
                rule = inventoryRuleDTO;
                break;
            }
            case FLEXIBLE: 
            case RELATE_TRANSACTIONS: {
                FlexibleRuleDTO flexibleRuleDTO;
                ObjectMapper mapper = JsonUtils.newObjectMapper();
                mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
                try {
                    flexibleRuleDTO = (FlexibleRuleDTO)mapper.readValue(jsonString, FlexibleRuleDTO.class);
                }
                catch (JsonProcessingException e) {
                    throw new BusinessRuntimeException((Throwable)e);
                }
                this.copyProperties(unionRuleEO, (AbstractUnionRule)flexibleRuleDTO, ruleTypeEnum);
                flexibleRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, flexibleRuleDTO.getRuleCondition()));
                flexibleRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, flexibleRuleDTO.getOffsetFormula()));
                List<String> updateOffsetGroupingFields = this.updataOffsetGroupingField(flexibleRuleDTO.getOffsetGroupingField());
                flexibleRuleDTO.setOffsetGroupingField(updateOffsetGroupingFields);
                List fetchConfigList = flexibleRuleDTO.getFetchConfigList();
                if (!CollectionUtils.isEmpty((Collection)fetchConfigList)) {
                    fetchConfigList.stream().forEach(fetchConfig -> {
                        List debitConfigList;
                        fetchConfig.setFilterFormula(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, fetchConfig.getFilterFormula()));
                        fetchConfig.setManualFilterFormula(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, fetchConfig.getManualFilterFormula()));
                        List creditConfigList = fetchConfig.getCreditConfigList();
                        if (!CollectionUtils.isEmpty((Collection)creditConfigList)) {
                            creditConfigList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, item.getFetchFormula())));
                        }
                        if (!CollectionUtils.isEmpty((Collection)(debitConfigList = fetchConfig.getDebitConfigList()))) {
                            debitConfigList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)flexibleRuleDTO, item.getFetchFormula())));
                        }
                    });
                }
                rule = flexibleRuleDTO;
                break;
            }
            case DIRECT_INVESTMENT: {
                List debitItemList3;
                DirectInvestmentDTO directInvestmentDTO = (DirectInvestmentDTO)JsonUtils.readValue((String)jsonString, DirectInvestmentDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)directInvestmentDTO, ruleTypeEnum);
                directInvestmentDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)directInvestmentDTO, directInvestmentDTO.getRuleCondition()));
                directInvestmentDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentDTO, directInvestmentDTO.getOffsetFormula()));
                List creditItemList3 = directInvestmentDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList3)) {
                    creditItemList3.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList3 = directInvestmentDTO.getDebitItemList()))) {
                    debitItemList3.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentDTO, item.getFetchFormula())));
                }
                rule = directInvestmentDTO;
                break;
            }
            case DIRECT_INVESTMENT_SEGMENT: {
                List debitItemList4;
                DirectInvestmentSegmentDTO directInvestmentSegmentDTO = (DirectInvestmentSegmentDTO)JsonUtils.readValue((String)jsonString, DirectInvestmentSegmentDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)directInvestmentSegmentDTO, ruleTypeEnum);
                directInvestmentSegmentDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)directInvestmentSegmentDTO, directInvestmentSegmentDTO.getRuleCondition()));
                directInvestmentSegmentDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentSegmentDTO, directInvestmentSegmentDTO.getOffsetFormula()));
                List creditItemList4 = directInvestmentSegmentDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList4)) {
                    creditItemList4.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentSegmentDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList4 = directInvestmentSegmentDTO.getDebitItemList()))) {
                    debitItemList4.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)directInvestmentSegmentDTO, item.getFetchFormula())));
                }
                rule = directInvestmentSegmentDTO;
                break;
            }
            case INDIRECT_INVESTMENT: {
                List debitItemList5;
                InDirectInvestmentDTO inDirectInvestmentDTO = (InDirectInvestmentDTO)JsonUtils.readValue((String)jsonString, InDirectInvestmentDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)inDirectInvestmentDTO, ruleTypeEnum);
                inDirectInvestmentDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentDTO, inDirectInvestmentDTO.getRuleCondition()));
                inDirectInvestmentDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentDTO, inDirectInvestmentDTO.getOffsetFormula()));
                List creditItemList5 = inDirectInvestmentDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList5)) {
                    creditItemList5.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList5 = inDirectInvestmentDTO.getDebitItemList()))) {
                    debitItemList5.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentDTO, item.getFetchFormula())));
                }
                rule = inDirectInvestmentDTO;
                break;
            }
            case INDIRECT_INVESTMENT_SEGMENT: {
                List debitItemList6;
                InDirectInvestmentSegmentDTO inDirectInvestmentSegmentDTO = (InDirectInvestmentSegmentDTO)JsonUtils.readValue((String)jsonString, InDirectInvestmentSegmentDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)inDirectInvestmentSegmentDTO, ruleTypeEnum);
                inDirectInvestmentSegmentDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentSegmentDTO, inDirectInvestmentSegmentDTO.getRuleCondition()));
                inDirectInvestmentSegmentDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentSegmentDTO, inDirectInvestmentSegmentDTO.getOffsetFormula()));
                List creditItemList6 = inDirectInvestmentSegmentDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList6)) {
                    creditItemList6.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentSegmentDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList6 = inDirectInvestmentSegmentDTO.getDebitItemList()))) {
                    debitItemList6.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)inDirectInvestmentSegmentDTO, item.getFetchFormula())));
                }
                rule = inDirectInvestmentSegmentDTO;
                break;
            }
            case PUBLIC_VALUE_ADJUSTMENT: {
                List debitItemList7;
                PublicValueAdjustmentRuleDTO publicValueAdjustmentRuleDTO = (PublicValueAdjustmentRuleDTO)JsonUtils.readValue((String)jsonString, PublicValueAdjustmentRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)publicValueAdjustmentRuleDTO, ruleTypeEnum);
                publicValueAdjustmentRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)publicValueAdjustmentRuleDTO, publicValueAdjustmentRuleDTO.getRuleCondition()));
                publicValueAdjustmentRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)publicValueAdjustmentRuleDTO, publicValueAdjustmentRuleDTO.getOffsetFormula()));
                List creditItemList7 = publicValueAdjustmentRuleDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList7)) {
                    creditItemList7.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)publicValueAdjustmentRuleDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList7 = publicValueAdjustmentRuleDTO.getDebitItemList()))) {
                    debitItemList7.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)publicValueAdjustmentRuleDTO, item.getFetchFormula())));
                }
                rule = publicValueAdjustmentRuleDTO;
                break;
            }
            case FLOAT_LINE: {
                FloatLineRuleDTO floatLineRuleDTO = (FloatLineRuleDTO)JsonUtils.readValue((String)jsonString, FloatLineRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)floatLineRuleDTO, ruleTypeEnum);
                floatLineRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)floatLineRuleDTO, floatLineRuleDTO.getRuleCondition()));
                floatLineRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)floatLineRuleDTO, floatLineRuleDTO.getOffsetFormula()));
                rule = floatLineRuleDTO;
                break;
            }
            case LEASE: {
                List debitItemList9;
                LeaseRuleDTO leaseRuleDTO = (LeaseRuleDTO)JsonUtils.readValue((String)jsonString, LeaseRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)leaseRuleDTO, ruleTypeEnum);
                leaseRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)leaseRuleDTO, leaseRuleDTO.getRuleCondition()));
                leaseRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)leaseRuleDTO, leaseRuleDTO.getOffsetFormula()));
                List creditItemList9 = leaseRuleDTO.getCreditItemList();
                if (!CollectionUtils.isEmpty((Collection)creditItemList9)) {
                    creditItemList9.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)leaseRuleDTO, item.getFetchFormula())));
                }
                if (!CollectionUtils.isEmpty((Collection)(debitItemList9 = leaseRuleDTO.getDebitItemList()))) {
                    debitItemList9.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)leaseRuleDTO, item.getFetchFormula())));
                }
                rule = leaseRuleDTO;
                break;
            }
            case FINANCIAL_CHECK: {
                FinancialCheckRuleDTO financialCheckRuleDTO = (FinancialCheckRuleDTO)JsonUtils.readValue((String)jsonString, FinancialCheckRuleDTO.class);
                this.copyProperties(unionRuleEO, (AbstractUnionRule)financialCheckRuleDTO, ruleTypeEnum);
                financialCheckRuleDTO.setRuleCondition(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, financialCheckRuleDTO.getRuleCondition()));
                financialCheckRuleDTO.setOffsetFormula(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, financialCheckRuleDTO.getOffsetFormula()));
                List fetchConfigList1 = financialCheckRuleDTO.getFetchConfigList();
                if (!CollectionUtils.isEmpty((Collection)fetchConfigList1)) {
                    fetchConfigList1.stream().forEach(fetchConfig -> {
                        List debitConfigList;
                        fetchConfig.setFilterFormula(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, fetchConfig.getFilterFormula()));
                        fetchConfig.setManualFilterFormula(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, fetchConfig.getManualFilterFormula()));
                        List creditConfigList = fetchConfig.getCreditConfigList();
                        if (!CollectionUtils.isEmpty((Collection)creditConfigList)) {
                            creditConfigList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, item.getFetchFormula())));
                        }
                        if (!CollectionUtils.isEmpty((Collection)(debitConfigList = fetchConfig.getDebitConfigList()))) {
                            debitConfigList.stream().forEach(item -> item.setFetchFormula(this.upgradeFormula((AbstractUnionRule)financialCheckRuleDTO, item.getFetchFormula())));
                        }
                    });
                }
                rule = financialCheckRuleDTO;
                break;
            }
            default: {
                throw new BusinessException("\u6807\u8bc6\u4e3a[".concat(ruleType).concat("]\u7684\u89c4\u5219\u7c7b\u578b\u65e0\u5bf9\u5e94\u7684\u516c\u5f0f\u5347\u7ea7\u7b56\u7565\uff0c\u8df3\u8fc7\u3002"));
            }
        }
        UnionRuleEO updateUnionRuleEO = UnionRuleConverter.convertUnionRuleDTOToEO((AbstractUnionRule)rule);
        BeanUtils.copyProperties(rule, (Object)updateUnionRuleEO);
        updateUnionRuleEO.setLeafFlag(rule.getLeafFlag() != false ? 1 : 0);
        updateUnionRuleEO.setStartFlag(rule.getStartFlag() != false ? 1 : 0);
        updateUnionRuleEO.setInitTypeFlag(rule.getInitTypeFlag() != false ? 1 : 0);
        updateUnionRuleEO.setEnableToleranceFlag(rule.getEnableToleranceFlag() != false ? 1 : 0);
        this.update(updateUnionRuleEO);
    }

    private List<UnionRuleEO> getUnionRuleBySystemId(String systemId) {
        String sql = " select  *  from GC_UNIONRULE  scheme \n  where scheme.reportSystem = ? \n";
        List list = this.jdbcTemplate.queryForList(sql, new Object[]{systemId});
        ArrayList<UnionRuleEO> unionRules = new ArrayList<UnionRuleEO>();
        for (Map unionRuleObj : list) {
            UnionRuleEO unionRuleEO = new UnionRuleEO();
            unionRuleEO.setId((String)unionRuleObj.get("ID"));
            unionRuleEO.setJsonString((String)unionRuleObj.get("JSONSTRING"));
            unionRuleEO.setOffsetType((String)unionRuleObj.get("OFFSETTYPE"));
            unionRuleEO.setRuleCondition((String)unionRuleObj.get("RULECONDITION"));
            unionRuleEO.setRuleType((String)unionRuleObj.get("RULETYPE"));
            unionRuleEO.setOffsetFormula((String)unionRuleObj.get("OFFSETFORMULA"));
            unionRuleEO.setTitle((String)unionRuleObj.get("TITLE"));
            if (unionRuleObj.get("LEAFFLAG") != null) {
                if ("1".equals(String.valueOf(unionRuleObj.get("LEAFFLAG")))) {
                    unionRuleEO.setLeafFlag(1);
                } else {
                    unionRuleEO.setLeafFlag(0);
                }
            }
            unionRules.add(unionRuleEO);
        }
        return unionRules;
    }

    private void update(UnionRuleEO updateUnionRuleEO) {
        String upgradeDwSql = "UPDATE GC_UNIONRULE\nSET JSONSTRING=?, RULECONDITION=?, OFFSETFORMULA=?\nWHERE ID=?";
        EntNativeSqlDefaultDao.getInstance().execute(upgradeDwSql, Arrays.asList(updateUnionRuleEO.getJsonString(), updateUnionRuleEO.getRuleCondition(), updateUnionRuleEO.getOffsetFormula(), updateUnionRuleEO.getId()));
    }

    private void copyProperties(UnionRuleEO unionRuleEO, AbstractUnionRule rule, RuleTypeEnum ruleType) {
        BeanUtils.copyProperties((Object)unionRuleEO, rule);
        rule.setLeafFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getLeafFlag(), 1)));
        rule.setStartFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getStartFlag(), 1)));
        rule.setInitTypeFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getInitTypeFlag(), 1)));
        rule.setEnableToleranceFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getEnableToleranceFlag(), 1)));
        rule.setRuleType(ruleType.getCode());
        if (!StringUtils.isEmpty((String)unionRuleEO.getOffsetType())) {
            rule.setOffsetType(OffsetTypeEnum.codeOf((String)unionRuleEO.getOffsetType()));
        }
        if (!StringUtils.isEmpty((String)unionRuleEO.getToleranceType())) {
            rule.setToleranceType(ToleranceTypeEnum.codeOf((String)unionRuleEO.getToleranceType()));
        }
    }

    private String upgradeFormula(AbstractUnionRule rule, String formula) {
        if (StringUtils.isEmpty((String)formula)) {
            return formula;
        }
        if (formula.indexOf("[DW]") >= 0) {
            String upgradeFormula = formula.replace("\\[DW\\]", "[MDCODE]");
            if (StringUtils.isEmpty((String)upgradeFormula)) {
                logger.error("\u7531\u4e8e\u5347\u7ea7\u524d\u516c\u5f0f[".concat(formula).concat("]\u4e0d\u4e3a\u7a7a\uff0c\u5347\u7ea7\u540e\u516c\u5f0f\u83b7\u53d6\u4e3a\u7a7a\uff0c\u6240\u4ee5\u5f53\u524d\u516c\u5f0f\u4e0d\u8fdb\u884c\u5347\u7ea7\uff0c\u9700\u4eba\u5de5\u786e\u8ba4\u3002"));
                return formula;
            }
            if (!formula.equals(upgradeFormula)) {
                logger.info("\u53d1\u751f\u53d8\u5316\u7684\u516c\u5f0f\uff1a".concat(rule.getTitle().concat("\u5408\u5e76\u89c4\u5219\uff0c\u5347\u7ea7\u524d\u516c\u5f0f\u4e3a\uff1a".concat(formula).concat(", \u5347\u7ea7\u540e\u516c\u5f0f\u4e3a\uff1a").concat("" + upgradeFormula))));
            } else {
                logger.info(rule.getTitle().concat("\u5408\u5e76\u89c4\u5219\uff0c\u516c\u5f0f\u672a\u53d1\u751f\u53d8\u5316,\u516c\u5f0f\u4e3a\uff1a").concat(formula));
            }
            return upgradeFormula;
        }
        return formula;
    }

    private List<String> updataOffsetGroupingField(List<String> offsetGroupingFields) {
        if (CollectionUtils.isEmpty(offsetGroupingFields)) {
            return offsetGroupingFields;
        }
        ArrayList<String> newOffsetGroupingFields = new ArrayList<String>();
        for (String fieldCode : offsetGroupingFields) {
            if ("DW".equalsIgnoreCase(fieldCode)) {
                newOffsetGroupingFields.add("MDCODE");
                continue;
            }
            newOffsetGroupingFields.add(fieldCode);
        }
        return newOffsetGroupingFields;
    }
}


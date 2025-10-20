/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingSqlBuilder
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.dao.IPenetrateContentBaseDao;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.service.AdjustVchrDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.service.AdjustVchrPenetrateContentProvider;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingSqlBuilder;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractPenetrateContentProvider<C extends PenetrateBaseDTO, E>
implements IPenetrateContentProvider<C, E> {
    @Autowired
    private IPenetrateContentBaseDao dao;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private IFieldMappingSqlBuilderGather gather;
    @Autowired
    private AdjustVchrPenetrateContentProvider adjustVchrPenetrateContentProvider;
    @Autowired
    private AdjustVchrDetailLedgerContentProvider adjustVchrDetailLedgerContentProvider;
    @Autowired
    protected INvwaSystemOptionService optionService;
    private static final String VARIABLE_TOKEN_TMPL = "${%s}";
    private static final String FN_ZERO = "0";

    @Override
    public PageVO<E> doQuery(C condi) {
        condi.setRequestTaskId(StringUtils.isEmpty((String)condi.getRequestTaskId()) ? UUIDUtils.newHalfGUIDStr() : condi.getRequestTaskId());
        QueryParam<E> queryParam = this.getQueryParam(condi);
        Assert.isNotNull(queryParam);
        Assert.isNotEmpty((String)queryParam.getSql());
        Assert.isNotNull((Object)queryParam.getArgs());
        Assert.isNotNull(queryParam.getRse());
        ComputationModelEnum computationModel = ComputationModelEnum.getEnumByCode((String)condi.getBizModelKey());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)String.format("%1$s\u6a21\u578b\u900f\u89c6-%2$s", computationModel.getName(), this.getPenetrateType().getName()), (Object)new Object[]{queryParam.getArgs(), condi}, (String)queryParam.getSql());
        PageVO<E> queryResult = this.dao.query(condi, queryParam);
        if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
            this.getIncludeAdjustVchrData(condi, queryResult);
        }
        return this.processResult(condi, queryResult);
    }

    private void getIncludeAdjustVchrData(C condi, PageVO<E> queryResult) {
        PenetrateTypeEnum penetrateType = this.getPenetrateType();
        if (PenetrateTypeEnum.BALANCE.equals((Object)penetrateType)) {
            this.handleBalancePenetrateType(condi, queryResult);
        } else if (PenetrateTypeEnum.DETAILLEDGER.equals((Object)penetrateType)) {
            this.handleDetailLedgerPenetrateType(condi, queryResult);
        }
    }

    private void handleBalancePenetrateType(C condi, PageVO<E> queryResult) {
        String bizModelKey = condi.getBizModelKey();
        if (this.isBalanceModel(bizModelKey)) {
            List<PenetrateBalance> adjustVchrData = this.adjustVchrPenetrateContentProvider.query((PenetrateBaseDTO)condi);
            this.processBalanceData(condi, queryResult, adjustVchrData);
        } else if (ComputationModelEnum.XJLLBALANCE.getCode().equals(bizModelKey)) {
            List<PenetrateBalance> adjustVchrXjllData = this.adjustVchrPenetrateContentProvider.xjllQuery((PenetrateBaseDTO)condi);
            this.processBalanceData(condi, queryResult, adjustVchrXjllData);
        } else {
            this.markDataSource(queryResult.getRows(), "\u6838\u7b97\u6570\u636e");
        }
    }

    private void handleDetailLedgerPenetrateType(C condi, PageVO<E> queryResult) {
        if ("\u624b\u5de5\u8c03\u6574".equals(condi.getVchrSrcType())) {
            String bizModelKey = condi.getBizModelKey();
            List<PenetrateDetailLedger> adjustVchrData = this.isBalanceModel(bizModelKey) ? this.adjustVchrDetailLedgerContentProvider.query((PenetrateBaseDTO)condi) : this.adjustVchrDetailLedgerContentProvider.xjllQuery((PenetrateBaseDTO)condi);
            queryResult.setRows(adjustVchrData);
            queryResult.setTotal(adjustVchrData.size());
        }
        queryResult.getRows().forEach(row -> {
            PenetrateDetailLedger balance = (PenetrateDetailLedger)row;
            balance.put("VCHRSRCTYPE", condi.getVchrSrcType());
        });
    }

    private void processBalanceData(C condi, PageVO<E> queryResult, List<E> adjustVchrData) {
        PenetrateBalance totalData = new PenetrateBalance();
        if (!queryResult.getRows().isEmpty()) {
            totalData = (PenetrateBalance)this.getTotalData(condi, queryResult.getRows());
            this.markDataSource(queryResult.getRows(), "\u6838\u7b97\u6570\u636e");
        }
        if (adjustVchrData != null && !adjustVchrData.isEmpty()) {
            queryResult.setTotal(queryResult.getTotal() + adjustVchrData.size());
            E totalAdjustData = this.getTotalData(condi, adjustVchrData);
            adjustVchrData.add((PenetrateBalance)totalAdjustData);
            this.markDataSource(adjustVchrData, "\u624b\u5de5\u8c03\u6574");
            queryResult.getRows().addAll(adjustVchrData);
        }
        if (!totalData.isEmpty()) {
            totalData.put("VCHRSRCTYPE", "\u6838\u7b97\u6570\u636e");
            queryResult.getRows().add(totalData);
        }
    }

    private E getTotalData(C condi, List<PenetrateBalance> originalList) {
        ArrayList<PenetrateBalance> copiedList = new ArrayList<PenetrateBalance>();
        for (PenetrateBalance item : originalList) {
            PenetrateBalance newItem = new PenetrateBalance();
            BeanUtils.copyProperties(item, newItem);
            copiedList.add(newItem);
        }
        List originalTotalList = this.processResult(condi, new PageVO(copiedList, copiedList.size())).getRows();
        return originalTotalList.get(0);
    }

    private boolean isBalanceModel(String bizModelKey) {
        return ComputationModelEnum.BALANCE.getCode().equals(bizModelKey) || ComputationModelEnum.ASSBALANCE.getCode().equals(bizModelKey);
    }

    private void markDataSource(List<E> list, String vchrSrcType) {
        list.forEach(row -> {
            PenetrateBalance balance = (PenetrateBalance)row;
            balance.put("VCHRSRCTYPE", vchrSrcType);
        });
    }

    protected abstract QueryParam<E> getQueryParam(C var1);

    protected abstract PageVO<E> processResult(C var1, PageVO<E> var2);

    protected String formatOrient(BigDecimal val) {
        if (val.compareTo(BigDecimal.ZERO) > 0) {
            return GcI18nUtil.getMessage((String)"bde.accountant.debit");
        }
        if (val.compareTo(BigDecimal.ZERO) < 0) {
            return GcI18nUtil.getMessage((String)"bde.accountant.creditor");
        }
        return GcI18nUtil.getMessage((String)"bde.accountant.flat");
    }

    protected IFieldMappingSqlBuilder getFieldMappingBuilder(String ruleType) {
        return this.gather.getProvider(ruleType);
    }

    protected IDbSqlHandler getDbSqlHandler(String dataSourceCode) {
        return SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
    }

    public static void addVariable(Map<String, String> variableMap, String key, String value) {
        Assert.isNotNull(variableMap);
        Assert.isNotEmpty((String)key);
        Assert.isNotNull((Object)value);
        variableMap.put(String.format(VARIABLE_TOKEN_TMPL, key), value);
    }

    public static String replaceVariable(String orgnSql, Map<String, String> variableMap) {
        Assert.isNotNull((Object)orgnSql);
        Assert.isNotNull(variableMap);
        return VariableParseUtil.parse((String)orgnSql, variableMap);
    }

    protected static String lpadPeriod(Integer str) {
        return CommonUtil.lpad((String)String.valueOf(str), (String)FN_ZERO, (int)2);
    }

    protected String buildCfItemCondi(String tableAlias, String subjectField, String code) {
        Assert.isNotEmpty((String)tableAlias);
        Assert.isNotEmpty((String)subjectField);
        if (StringUtils.isEmpty((String)code)) {
            return "";
        }
        Assert.isNotEmpty((String)code);
        if (code.contains(":")) {
            String[] codeArr = code.split(":");
            Assert.isTrue((codeArr.length == 2 ? 1 : 0) != 0, (String)"\u5f00\u59cb\u8303\u56f4\u3001\u622a\u6b62\u8303\u56f4\u5206\u9694\u7b26\u3010:\u3011\u5bf9\u5e94\u7684\u53c2\u6570\u683c\u5f0f\u9519\u8bef", (Object[])new Object[0]);
            return String.format(" AND (%1$s.%2$s >=  '%3$s' AND %1$s.%2$s < '%4$s' ) ", tableAlias, subjectField, codeArr[0], codeArr[1]);
        }
        if (code.contains(",")) {
            return this.matchMultiple(tableAlias, subjectField, code);
        }
        return this.matchSingle(tableAlias, subjectField, code);
    }

    protected String buildSubjectCondi(String tableAlias, String subjectField, String code) {
        Assert.isNotEmpty((String)tableAlias);
        Assert.isNotEmpty((String)subjectField);
        if (StringUtils.isEmpty((String)code)) {
            return "";
        }
        Assert.isNotEmpty((String)code);
        if (code.contains(":")) {
            return this.matchRange(tableAlias, subjectField, code);
        }
        if (code.contains(",")) {
            return this.matchMultiple(tableAlias, subjectField, code);
        }
        return this.matchSingle(tableAlias, subjectField, code);
    }

    protected String buildExcludeCondi(String tableAlias, String field, String code) {
        Assert.isNotEmpty((String)tableAlias);
        if (StringUtils.isEmpty((String)code)) {
            return "";
        }
        Assert.isNotEmpty((String)code);
        if (code.contains(":")) {
            return this.excludeRange(tableAlias, field, code);
        }
        if (code.contains(",")) {
            return this.excludeMultiple(tableAlias, field, code);
        }
        return this.excludeSingle(tableAlias, field, code);
    }

    private String excludeSingle(String tableAlias, String field, String code) {
        return String.format(" AND %1$s.%2$s NOT LIKE '%3$s%%%%' ", tableAlias, field, code);
    }

    private String excludeMultiple(String tableAlias, String field, String code) {
        String[] codeArr = code.split(",");
        if (codeArr.length == 1) {
            return this.matchSingle(tableAlias, field, code);
        }
        ArrayList<String> condiList = new ArrayList<String>(codeArr.length);
        for (String eachCode : codeArr) {
            condiList.add(String.format(" %1$s.%2$s NOT LIKE '%3$s%%%%' ", tableAlias, field, eachCode));
        }
        return SqlUtil.concatCondi(condiList, (boolean)true);
    }

    private String excludeRange(String tableAlias, String field, String code) {
        String[] codeArr = code.split(":");
        Assert.isTrue((codeArr.length == 2 ? 1 : 0) != 0, (String)"\u5f00\u59cb\u8303\u56f4\u3001\u622a\u6b62\u8303\u56f4\u5206\u9694\u7b26\u3010:\u3011\u5bf9\u5e94\u7684\u53c2\u6570\u683c\u5f0f\u9519\u8bef", (Object[])new Object[0]);
        return String.format(" AND (%1$s.%2$s < '%3$s' AND %1$s.%2$s > '%4$s' ) ", tableAlias, field, codeArr[0], codeArr[1]);
    }

    private String matchSingle(String tableAlias, String subjectField, String code) {
        return String.format(" AND %1$s.%2$s LIKE '%3$s%%%%' ", tableAlias, subjectField, code);
    }

    private String matchMultiple(String tableAlias, String subjectField, String code) {
        String[] codeArr = code.split(",");
        if (codeArr.length == 1) {
            return this.matchSingle(tableAlias, subjectField, code);
        }
        ArrayList<String> condiList = new ArrayList<String>(codeArr.length);
        for (String eachCode : codeArr) {
            condiList.add(String.format(" %1$s.%2$s LIKE '%3$s%%%%' ", tableAlias, subjectField, eachCode));
        }
        return SqlUtil.concatCondi(condiList, (boolean)false);
    }

    private String matchRange(String tableAlias, String subjectField, String code) {
        String[] codeArr = code.split(":");
        Assert.isTrue((codeArr.length == 2 ? 1 : 0) != 0, (String)"\u5f00\u59cb\u8303\u56f4\u3001\u622a\u6b62\u8303\u56f4\u5206\u9694\u7b26\u3010:\u3011\u5bf9\u5e94\u7684\u53c2\u6570\u683c\u5f0f\u9519\u8bef", (Object[])new Object[0]);
        return String.format(" AND (%1$s.%2$s >=  '%3$s' AND %1$s.%2$s <= '%4$sZZ' ) ", tableAlias, subjectField, codeArr[0], codeArr[1]);
    }

    protected String matchByRule(String tableAlias, String field, String code, String rule) {
        if (StringUtils.isEmpty((String)code) || StringUtils.isEmpty((String)rule)) {
            return "";
        }
        switch (rule) {
            case "EQ": {
                return String.format(" AND %1$s.%2$s = '%3$s' ", tableAlias, field, code);
            }
        }
        return String.format(" AND %1$s.%2$s LIKE '%3$s%%%%' ", tableAlias, field, code);
    }
}


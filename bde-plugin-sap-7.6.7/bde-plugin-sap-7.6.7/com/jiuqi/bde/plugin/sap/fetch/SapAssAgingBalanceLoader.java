/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.sap.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SapAssAgingBalanceLoader
extends AbstractAgingDataLoader {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    protected IFieldMappingSqlBuilderGather gather;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    public FetchData loadData(AgingBalanceCondition condi) {
        OrgMappingDTO orgMaping = condi.getOrgMapping();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(orgMaping.getDataSourceCode()));
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider((BalanceCondition)condi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        CustSuppTypeEnum custSupplierType = CustSuppTypeEnum.CUSTSUPPLIER;
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            CustSuppTypeEnum fromCode = CustSuppTypeEnum.fromCode(assistMapping.getAssistCode());
            if (fromCode == null) continue;
            custSupplierType = fromCode;
        }
        Date effectFetchDate = this.getEffectFetchDate(condi);
        String agingfetchDate = DateUtils.format((Date)effectFetchDate, (DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE);
        Pair<String, String> bizDate = this.caclBizDate(condi, effectFetchDate);
        String beginBizDate = (String)bizDate.getFirst();
        String endBizDate = (String)bizDate.getSecond();
        int index = 0;
        StringBuilder sql = new StringBuilder();
        for (SapCustSupplierEnum sapCustSupplierEnum : SapCustSupplierEnum.values()) {
            if (0 < index++) {
                sql.append(" UNION ALL                        \n");
            }
            sql.append("SELECT                        \n");
            sql.append("       HKONT AS SUBJECTCODE,  \n");
            sql.append("       WAERS AS CURRENCYCODE,  \n");
            sql.append(String.format("       %1$s AS %2$s,  \n", sapCustSupplierEnum.getCustField(), custSupplierType.getCode()));
            String amntFieldTemplate = "CASE WHEN ##BIZDATE_FIELD## > '##BEGIN_BIZDATE##' AND ##BIZDATE_FIELD## < '##END_BIZDATE##' THEN  \n           CASE TRIM(SHKZG) WHEN 'S' THEN ##AMNT_FIELD## ELSE 0 END - CASE TRIM(SHKZG) WHEN 'H' THEN ##AMNT_FIELD## ELSE 0 END  \n       ELSE 0 END AS ##AMNT_FIELD_ALIAS##";
            String amnt = amntFieldTemplate.replace("##BIZDATE_FIELD##", sqlHandler.formatDate(sapCustSupplierEnum.getTableName() + "." + AgingAmntFieldEnum.AMNT.getDateField(), sqlHandler.serialDateFmt())).replace("##BEGIN_BIZDATE##", beginBizDate).replace("##END_BIZDATE##", endBizDate).replace("##AMNT_FIELD##", AgingAmntFieldEnum.AMNT.getAmntField()).replace("##AMNT_FIELD_ALIAS##", condi.getAgingFetchType() == AgingFetchTypeEnum.NC ? FetchTypeEnum.HXNC.getCode() : FetchTypeEnum.HXYE.getCode());
            sql.append(amnt).append(",").append("\n");
            String wamnt = amntFieldTemplate.replace("##BIZDATE_FIELD##", sqlHandler.formatDate(sapCustSupplierEnum.getTableName() + "." + AgingAmntFieldEnum.WAMNT.getDateField(), sqlHandler.serialDateFmt())).replace("##BEGIN_BIZDATE##", beginBizDate).replace("##END_BIZDATE##", endBizDate).replace("##AMNT_FIELD##", AgingAmntFieldEnum.WAMNT.getAmntField()).replace("##AMNT_FIELD_ALIAS##", condi.getAgingFetchType() == AgingFetchTypeEnum.NC ? FetchTypeEnum.WHXNC.getCode() : FetchTypeEnum.WHXYE.getCode());
            sql.append(wamnt).append(",").append("\n");
            String djAmnt = amntFieldTemplate.replace("##BIZDATE_FIELD##", sqlHandler.formatDate(sapCustSupplierEnum.getTableName() + "." + AgingAmntFieldEnum.DJAMNT.getDateField(), sqlHandler.serialDateFmt())).replace("##BEGIN_BIZDATE##", beginBizDate).replace("##END_BIZDATE##", endBizDate).replace("##AMNT_FIELD##", AgingAmntFieldEnum.DJAMNT.getAmntField()).replace("##AMNT_FIELD_ALIAS##", AgingAmntFieldEnum.DJAMNT.getFieldName());
            sql.append(djAmnt).append(",").append("\n");
            String wdjAmnt = amntFieldTemplate.replace("##BIZDATE_FIELD##", sqlHandler.formatDate(sapCustSupplierEnum.getTableName() + "." + AgingAmntFieldEnum.WDJAMNT.getDateField(), sqlHandler.serialDateFmt())).replace("##BEGIN_BIZDATE##", beginBizDate).replace("##END_BIZDATE##", endBizDate).replace("##AMNT_FIELD##", AgingAmntFieldEnum.WDJAMNT.getAmntField()).replace("##AMNT_FIELD_ALIAS##", AgingAmntFieldEnum.WDJAMNT.getFieldName());
            sql.append(wdjAmnt).append("\n");
            sql.append("  FROM ").append(sapCustSupplierEnum.getTableName());
            sql.append(" WHERE 1 = 1  \n");
            sql.append(String.format("   AND %s.BUKRS = ?  \n", sapCustSupplierEnum.getTableName()));
            sql.append(String.format("   AND %1$s <= '%2$s'  \n", sqlHandler.formatDate(String.format("%s.BUDAT", sapCustSupplierEnum.getTableName()), sqlHandler.serialDateFmt()), agingfetchDate));
            if (!sapCustSupplierEnum.clear) continue;
            sql.append(String.format("   AND %1$s > '%2$s'  \n", sqlHandler.formatDate(String.format("%s.AUGDT", sapCustSupplierEnum.getTableName()), sqlHandler.serialDateFmt()), agingfetchDate));
        }
        String executeSql = sql.toString();
        Object[] args = new Object[]{orgMaping.getAcctOrgCode(), orgMaping.getAcctOrgCode(), orgMaping.getAcctOrgCode(), orgMaping.getAcctOrgCode()};
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u8d26\u9f84\u4f59\u989d", (Object)new Object[]{args, condi}, (String)executeSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), executeSql, args, (ResultSetExtractor)new FetchDataExtractor());
    }

    private Date getEffectFetchDate(AgingBalanceCondition condi) {
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            Date effectFetchDate = DateUtils.lastDateOf((int)condi.getAcctYear(), (int)12);
            return DateUtils.nextDateOf((Date)effectFetchDate);
        }
        return DateUtils.nextDateOf((Date)DateUtils.parse((String)condi.getAgingFetchDate()));
    }

    private Pair<String, String> caclBizDate(AgingBalanceCondition condi, Date effectFetchDate) {
        switch (condi.getAgingPeriodType()) {
            case Y: {
                return Pair.of((Object)ModelExecuteUtil.decreaseYear((Date)effectFetchDate, (int)condi.getAgingEndPeriod(), (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()), (Object)ModelExecuteUtil.decreaseYear((Date)effectFetchDate, (int)condi.getAgingStartPeriod(), (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()));
            }
            case M: {
                Date endDate = ModelExecuteUtil.decreaseMonth2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod(), (condi.getAgingFetchType() == AgingFetchTypeEnum.NC ? 1 : 0) != 0);
                return Pair.of((Object)ModelExecuteUtil.decreaseMonth((Date)effectFetchDate, (int)condi.getAgingEndPeriod(), (boolean)false, (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()), (Object)DateUtils.format((Date)endDate, (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()));
            }
        }
        return Pair.of((Object)ModelExecuteUtil.decreaseDay((Date)effectFetchDate, (int)condi.getAgingEndPeriod(), (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()), (Object)ModelExecuteUtil.decreaseDay((Date)effectFetchDate, (int)condi.getAgingStartPeriod(), (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()));
    }

    static enum AgingAmntFieldEnum {
        AMNT("AMNT", "BUDAT", "DMBTR"),
        WAMNT("WAMNT", "BUDAT", "WRBTR"),
        DJAMNT("DJAMNT", "CPUDT", "DMBTR"),
        WDJAMNT("WDJAMNT", "CPUDT", "WRBTR");

        private final String fieldName;
        private final String dateField;
        private final String amntField;

        private AgingAmntFieldEnum(String fieldName, String dateField, String amntField) {
            this.fieldName = fieldName;
            this.dateField = dateField;
            this.amntField = amntField;
        }

        String getFieldName() {
            return this.fieldName;
        }

        String getDateField() {
            return this.dateField;
        }

        String getAmntField() {
            return this.amntField;
        }
    }

    static enum SapCustSupplierEnum {
        BSAD("BSAD", "KUNNR", true),
        BSID("BSID", "KUNNR", false),
        BSAK("BSAK", "LIFNR", true),
        BSIK("BSIK", "LIFNR", false);

        private final String tableName;
        private final String custField;
        private final boolean clear;

        private SapCustSupplierEnum(String tableName, String custField, boolean clear) {
            this.tableName = tableName;
            this.custField = custField;
            this.clear = clear;
        }

        String getTableName() {
            return this.tableName;
        }

        String getCustField() {
            return this.custField;
        }

        public boolean isClear() {
            return this.clear;
        }
    }

    static enum CustSuppTypeEnum {
        CUSTOMER("MD_CUSTOMER"),
        SUPPLIER("MD_SUPPLIER"),
        CUSTSUPPLIER("MD_CUSTSUPPLIER");

        private final String code;

        private CustSuppTypeEnum(String code) {
            this.code = code;
        }

        String getCode() {
            return this.code;
        }

        static CustSuppTypeEnum fromCode(String code) {
            for (CustSuppTypeEnum type : CustSuppTypeEnum.values()) {
                if (!type.getCode().equals(code)) continue;
                return type;
            }
            return null;
        }
    }
}


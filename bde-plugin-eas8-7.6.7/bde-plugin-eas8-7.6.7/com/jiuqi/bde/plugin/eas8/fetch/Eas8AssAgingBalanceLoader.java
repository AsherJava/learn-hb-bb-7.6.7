/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Eas8AssAgingBalanceLoader
extends AbstractAgingDataLoader {
    @Autowired
    private Eas8PluginType eas8PluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.eas8PluginType;
    }

    public FetchData loadData(AgingBalanceCondition condi) {
        if (condi.isIncludeUncharged() == null) {
            condi.setIncludeUncharged(Boolean.valueOf(true));
        }
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider((BalanceCondition)condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T_KM.FNUMBER SUBJECTCODE,  \n");
        sql.append("       T_HB.FNUMBER CURRENCYCODE,  \n");
        sql.append("       T_KH.FNUMBER MD_CUSTSUPPLIER,  \n");
        sql.append("       SUM(T_WL.FUNVERIFIEDAMTFOR) WHXYE,");
        sql.append("       SUM(T_WL.FUNVERIFIEDAMTLOCAL) HXYE,  \n");
        sql.append("       SUM(T_WL.FUNVERIFIEDAMTFOR) WHXNC,  \n");
        sql.append("       SUM(T_WL.FUNVERIFIEDAMTLOCAL) HXNC ${ASSFIELD}  \n");
        sql.append("  FROM T_GL_ACCTCUSSENT T_WL  \n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW T_KM  \n");
        sql.append("    ON T_WL.FACCOUNTID = T_KM.FID  \n");
        sql.append("  LEFT JOIN T_BD_CURRENCY T_HB  \n");
        sql.append("    ON T_WL.FCURRENCYID = T_HB.FID  \n");
        sql.append("  LEFT JOIN T_GL_VOUCHERASSISTRECORD T_FZPZ  \n");
        sql.append("    ON T_WL.FVCHASSISTRECORDID = T_FZPZ.FID  \n");
        sql.append("  LEFT JOIN T_ORG_Company T_ORG  \n");
        sql.append("    ON T_WL.FCOMPANYID = T_ORG.FID  \n");
        sql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS  \n");
        sql.append("    ON T_FZPZ.FASSGRPID = ASS.FID  \n");
        sql.append("  LEFT JOIN T_BD_CUSTOMER T_KH  \n");
        sql.append("    ON ASS.FCUSTOMERID = T_KH.FID ${PZJOIN} ${ASSJOIN}  \n");
        sql.append(" WHERE 1=1  \n");
        sql.append(" ${UNITCONDITION}");
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.YE) {
            sql.append(" AND " + sqlHandler.formatDate("T_FZPZ.FBIZDATE", sqlHandler.hyphenTimeStampFmt()) + " > '${STARTTIME}'  ");
            sql.append(" AND " + sqlHandler.formatDate("T_FZPZ.FBIZDATE", sqlHandler.hyphenTimeStampFmt()) + " <= '${ENDTIME}'  ");
        } else if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            sql.append(" AND " + sqlHandler.formatDate("T_FZPZ.FBIZDATE", sqlHandler.hyphenTimeStampFmt()) + " <= '${FETCHDATE}'  ");
        }
        sql.append("   ${CHARGEDWHERE}");
        sql.append(" GROUP BY T_KM.FNUMBER,T_HB.FNUMBER,T_KH.FNUMBER");
        sql.append("          ${GROUPFIELD}");
        String pzJoin = "";
        String chargedWhere = "";
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        if (!condi.isIncludeUncharged().booleanValue()) {
            pzJoin = "LEFT JOIN T_GL_VOUCHER T_PZ ON T_PZ.FID = T_FZPZ.FBILLID";
            chargedWhere = " AND (T_PZ.FBizStatus = 5 or (T_WL.FISINIT=1 and T_WL.FISINITCLOSED=1))";
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            assField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        Date effectFetchDate = this.getEffectFetchDate(condi);
        Pair<String, String> bizDate = this.caclBizDate(condi, effectFetchDate);
        String beginBizDate = (String)bizDate.getFirst();
        String endBizDate = (String)bizDate.getSecond();
        Variable variable = new Variable();
        variable.put("ASSFIELD", assField.toString());
        variable.put("FETCHDATE", DateUtils.format((Date)effectFetchDate, (String)"yyyy-MM-dd HH:mm:ss.SSS"));
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("STARTTIME", beginBizDate);
        variable.put("ENDTIME", endBizDate);
        variable.put("PZJOIN", pzJoin);
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("CHARGEDWHERE", chargedWhere);
        variable.put("UNITCONDITION", OrgSqlUtil.getOrgConditionSql((String)"T_ORG.FNUMBER", (List)unitAndBookValue.getUnitCodes()));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u8d26\u9f84\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }

    private Date getEffectFetchDate(AgingBalanceCondition condi) {
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            Calendar oc = Calendar.getInstance();
            oc.setTime(DateUtils.parse((String)condi.getAgingFetchDate()));
            oc.set(6, 1);
            oc.add(6, -1);
            return oc.getTime();
        }
        return DateUtils.parse((String)condi.getAgingFetchDate());
    }

    private Pair<String, String> caclBizDate(AgingBalanceCondition condi, Date effectFetchDate) {
        Date endDate;
        Date beginDate;
        switch (condi.getAgingPeriodType()) {
            case Y: {
                beginDate = ModelExecuteUtil.decreaseYear2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod());
                endDate = ModelExecuteUtil.decreaseYear2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod());
                break;
            }
            case M: {
                beginDate = ModelExecuteUtil.decreaseMonth2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod(), (boolean)false);
                endDate = ModelExecuteUtil.decreaseMonth2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod(), (condi.getAgingFetchType() == AgingFetchTypeEnum.NC ? 1 : 0) != 0);
                break;
            }
            default: {
                beginDate = ModelExecuteUtil.decreaseDay2Date((Date)effectFetchDate, (int)condi.getAgingEndPeriod());
                endDate = ModelExecuteUtil.decreaseDay2Date((Date)effectFetchDate, (int)condi.getAgingStartPeriod());
            }
        }
        return Pair.of((Object)DateUtils.format((Date)ModelExecuteUtil.addDays((Date)beginDate, (int)1), (String)"yyyy-MM-dd HH:mm:ss.SSS"), (Object)ModelExecuteUtil.getLastSecondInDate((Date)endDate, (SimpleDateFormat)new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.bde.base.formula;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

public class FetchFormulaUtil {
    public static String formatMessageInfo(String message) {
        if (StringUtils.isEmpty((String)message)) {
            return message;
        }
        message = message.replace("ENV", "\u4e0a\u4e0b\u6587").replace("FLOAT", "\u67e5\u8be2\u7ed3\u679c");
        return message;
    }

    public static Map<String, Object> initEnv() {
        HashMap<String, Object> params = new HashMap<String, Object>(ArgumentValueEnum.values().length);
        int year = 2022;
        int period = 1;
        params.put(ArgumentValueEnum.UNITCODE.getCode(), "T01");
        params.put(ArgumentValueEnum.YEAR.getCode(), year);
        params.put(ArgumentValueEnum.FULLPERIOD.getCode(), CommonUtil.lpad((String)String.valueOf(period), (String)"0", (int)2));
        params.put(ArgumentValueEnum.PERIOD.getCode(), period);
        params.put(ArgumentValueEnum.BOOKCODE.getCode(), "B01");
        params.put(ArgumentValueEnum.STARTDATE.getCode(), "20220101");
        params.put(ArgumentValueEnum.ENDDATE.getCode(), "20220131");
        params.put(ArgumentValueEnum.TASKID.getCode(), "4fd8a55e-f675-4719-bd18-84563852cfd6");
        params.put(ArgumentValueEnum.INCLUDEUNCHARGED.getCode(), 1);
        params.put(ArgumentValueEnum.INCLUDEADJUSTVCHR.getCode(), 0);
        params.put(ArgumentValueEnum.PERIODSCHEME.getCode(), "2022Y12");
        params.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), "GCADJTYPE");
        params.put(ArgumentValueEnum.MD_CURRENCY.getCode(), "CNY");
        params.put(ArgumentValueEnum.MD_GCORGTYPE.getCode(), "MD_GCADJTYPE");
        return params;
    }

    public static Map<String, Object> convtEnvByFetchContext(FetchTaskContext fetchTaskContext) {
        HashMap<String, Object> params = new HashMap<String, Object>(ArgumentValueEnum.values().length);
        String year = "";
        String period = "";
        if (!StringUtils.isEmpty((String)fetchTaskContext.getEndDateStr())) {
            Date endDate = DateUtils.parse((String)fetchTaskContext.getEndDateStr());
            year = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)1));
            period = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)2));
        }
        params.put(ArgumentValueEnum.UNITCODE.getCode(), fetchTaskContext.getOrgMapping().getAcctOrgCode());
        params.put(ArgumentValueEnum.RPUNITCODE.getCode(), fetchTaskContext.getOrgMapping().getReportOrgCode());
        params.put(ArgumentValueEnum.YEAR.getCode(), Integer.valueOf(year));
        params.put(ArgumentValueEnum.FULLPERIOD.getCode(), CommonUtil.lpad((String)period, (String)"0", (int)2));
        params.put(ArgumentValueEnum.PERIOD.getCode(), Integer.valueOf(period));
        params.put(ArgumentValueEnum.BOOKCODE.getCode(), fetchTaskContext.getOrgMapping().getAcctBookCode());
        params.put(ArgumentValueEnum.STARTDATE.getCode(), BdeCommonUtil.formatDateNoDash((String)fetchTaskContext.getStartDateStr()));
        params.put(ArgumentValueEnum.ENDDATE.getCode(), BdeCommonUtil.formatDateNoDash((String)fetchTaskContext.getEndDateStr()));
        params.put(ArgumentValueEnum.TASKID.getCode(), fetchTaskContext.getTaskId());
        params.put(ArgumentValueEnum.INCLUDEUNCHARGED.getCode(), fetchTaskContext.getIncludeUncharged() != false ? 1 : 0);
        params.put(ArgumentValueEnum.INCLUDEADJUSTVCHR.getCode(), fetchTaskContext.getIncludeAdjustVchr() != false ? (Comparable<Boolean>)fetchTaskContext.getIncludeAdjustVchr() : (Comparable<Boolean>)Integer.valueOf(0));
        params.put(ArgumentValueEnum.PERIODSCHEME.getCode(), fetchTaskContext.getPeriodScheme());
        if (fetchTaskContext.getOtherEntity() != null) {
            params.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCADJTYPE.getCode()));
            params.put(ArgumentValueEnum.MD_CURRENCY.getCode(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_CURRENCY.getCode()));
            params.put(ArgumentValueEnum.MD_GCORGTYPE.getCode(), fetchTaskContext.getOtherEntity().get(ArgumentValueEnum.MD_GCORGTYPE.getCode()));
        }
        if (fetchTaskContext.getExtParam() != null && !fetchTaskContext.getExtParam().isEmpty()) {
            for (Map.Entry extParamEntry : fetchTaskContext.getExtParam().entrySet()) {
                params.put((String)extParamEntry.getKey(), extParamEntry.getValue());
            }
        }
        return params;
    }

    public static Map<String, Object> convtEnvByFetchContext(FetchRequestDTO fetchRequestDTO) {
        Map extParam;
        FetchRequestContextDTO fetchContext = fetchRequestDTO.getFetchContext();
        HashMap<String, Object> params = new HashMap<String, Object>(ArgumentValueEnum.values().length);
        String year = "";
        String period = "";
        if (!StringUtils.isEmpty((String)fetchContext.getEndDateStr())) {
            Date endDate = DateUtils.parse((String)fetchContext.getEndDateStr());
            year = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)1));
            period = String.valueOf(DateUtils.getDateFieldValue((Date)endDate, (int)2));
        }
        params.put(ArgumentValueEnum.UNITCODE.getCode(), fetchRequestDTO.getOrgMapping().getAcctOrgCode());
        params.put(ArgumentValueEnum.RPUNITCODE.getCode(), fetchRequestDTO.getOrgMapping().getReportOrgCode());
        params.put(ArgumentValueEnum.YEAR.getCode(), Integer.valueOf(year));
        params.put(ArgumentValueEnum.FULLPERIOD.getCode(), CommonUtil.lpad((String)period, (String)"0", (int)2));
        params.put(ArgumentValueEnum.PERIOD.getCode(), Integer.valueOf(period));
        params.put(ArgumentValueEnum.BOOKCODE.getCode(), fetchRequestDTO.getOrgMapping().getAcctBookCode());
        params.put(ArgumentValueEnum.STARTDATE.getCode(), BdeCommonUtil.formatDateNoDash((String)fetchContext.getStartDateStr()));
        params.put(ArgumentValueEnum.ENDDATE.getCode(), BdeCommonUtil.formatDateNoDash((String)fetchContext.getEndDateStr()));
        params.put(ArgumentValueEnum.TASKID.getCode(), fetchContext.getTaskId());
        params.put(ArgumentValueEnum.INCLUDEUNCHARGED.getCode(), Boolean.TRUE.equals(fetchContext.getIncludeUncharged()) ? 1 : 0);
        params.put(ArgumentValueEnum.PERIODSCHEME.getCode(), fetchContext.getPeriodScheme());
        if (fetchContext.getOtherEntity() != null) {
            params.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), fetchContext.getOtherEntity().get(ArgumentValueEnum.MD_GCADJTYPE.getCode()));
            params.put(ArgumentValueEnum.MD_CURRENCY.getCode(), fetchContext.getOtherEntity().get(ArgumentValueEnum.MD_CURRENCY.getCode()));
            params.put(ArgumentValueEnum.MD_GCORGTYPE.getCode(), fetchContext.getOtherEntity().get(ArgumentValueEnum.MD_GCORGTYPE.getCode()));
        }
        params.putAll(MapUtils.isNotEmpty((Map)(extParam = fetchContext.getExtParam())) ? extParam : new HashMap());
        return params;
    }

    public static String formatFormulaInfo(String formula) {
        String envStartToken = "ENV[";
        String envEndToken = "]";
        HashMap<String, String> envMap = new HashMap<String, String>();
        for (ArgumentValueEnum arg : ArgumentValueEnum.values()) {
            envMap.put("ENV[" + arg.getCode() + "]", "\u62a5\u8868\u53c2\u6570[" + arg.getTitle() + "]");
        }
        String formulaInfo = null;
        formulaInfo = VariableParseUtil.parse((String)formula, envMap, (String)envStartToken, (String)envEndToken);
        formulaInfo = formulaInfo.replace("FLOAT", "\u67e5\u8be2\u7ed3\u679c");
        return formulaInfo;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.DcAdjustVchrSysOptionEnum
 *  com.jiuqi.bde.common.constant.PeriodTypeEnum
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.bde.penetrate.impl.util;

import com.jiuqi.bde.common.constant.DcAdjustVchrSysOptionEnum;
import com.jiuqi.bde.common.constant.PeriodTypeEnum;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjustVchrPenetrateUtil {
    public static final String ORIGINAL_INDEX = "\u6838\u7b97\u6570\u636e";
    public static final String ADJUST_INDEX = "\u624b\u5de5\u8c03\u6574";
    @Autowired
    private INvwaSystemOptionService sysOptionService;

    public String buildSubjectCondi(String tableAlias, String subjectField, String code) {
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

    public String buildExcludeCondi(String tableAlias, String field, String code) {
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

    public String matchByRule(String tableAlias, String field, String code, String rule) {
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

    public String buildCfItemCondi(String tableAlias, String subjectField, String code) {
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

    public String buildAdjustVchrPeriodTypeCondi(String periodSchemeStr) {
        if (StringUtils.isEmpty((String)periodSchemeStr)) {
            return "";
        }
        PeriodWrapper wrapper = new PeriodWrapper(periodSchemeStr);
        String periodType = PeriodUtil.convertType2Str((int)wrapper.getType());
        DcAdjustVchrSysOptionEnum optionEnum = DcAdjustVchrSysOptionEnum.getOptionIdByPeriodType((String)periodType);
        String optionValue = this.sysOptionService.findValueById(optionEnum.name());
        if (!StringUtils.isEmpty((String)optionValue)) {
            optionValue = optionValue.substring(1, optionValue.length() - 1);
            optionValue = optionValue.replace("\"", "");
        }
        Assert.isNotEmpty((String)optionValue, (String)String.format("\u671f\u95f4\u7c7b\u578b\u3010%1$s\u3011\u5bf9\u5e94\u7684\u8c03\u6574\u51ed\u8bc1\u7cfb\u7edf\u9009\u9879\u672a\u914d\u7f6e", periodType), (Object[])new Object[0]);
        List<String> optionPeriodTypes = Arrays.asList(optionValue.split(","));
        int periodEndMonth = AdjustVchrPenetrateUtil.getPeriodMonthScope(wrapper.getPeriod(), periodType)[1];
        StringJoiner sql = new StringJoiner(" OR ");
        for (String optionPeriodType : optionPeriodTypes) {
            PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.getByCode((String)optionPeriodType);
            sql.add(String.format(" ( A.PERIODTYPE = '%1$s' AND A.ACCTPERIOD = %2$s) \n", periodTypeEnum.getCode(), periodTypeEnum.monthToPeriod(periodEndMonth)));
        }
        return String.format(" AND (%s)", sql);
    }

    private static int[] getPeriodMonthScope(int period, String periodType) {
        return AdjustVchrPenetrateUtil.getPeriodMonthScope(period, PeriodTypeEnum.getByCode((String)periodType));
    }

    private static int[] getPeriodMonthScope(int period, PeriodTypeEnum periodTypeEnum) {
        switch (periodTypeEnum) {
            case J: {
                return new int[]{(period - 1) * 3 + 1, period * 3};
            }
            case H: {
                return new int[]{(period - 1) * 6 + 1, period * 6};
            }
            case N: {
                return new int[]{1, 12};
            }
        }
        return new int[]{period, period};
    }
}


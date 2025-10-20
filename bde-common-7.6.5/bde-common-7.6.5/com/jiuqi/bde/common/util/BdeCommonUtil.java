/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.config.BdeCommonConfig;
import com.jiuqi.bde.common.constant.DataTypes;
import com.jiuqi.bde.common.constant.SignTypeEnum;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BdeCommonUtil {
    public static final String ACCTYEAR = "acctYear";
    public static final String ACCTPERIOD = "acctPeriod";
    public static final String ORGCODE = "orgCode";
    public static final String TABLENAME = "#TABLENAME#";
    public static final String TABLEDATA_EXPIRATION = "BDE_TABLEDATA_EXPIRATION";
    public static final String IS_DEBUG = "BDE_IS_DEBUG";
    public static final String FLOATRESULT_CLEAN_ZERO_RECORDS = "CLEAN_ZERO_RECORDS";
    public static final String ENABLE_CUSTOMFETCH_AS_FLOAT = "ENABLE_CUSTOMFETCH_AS_FLOAT";
    public static final String ENABLE_CUSTOMFETCH_ROW_LIMIT = "BDE_CUSTOMFETCH_ROW_LIMIT";
    public static final String ENABLE_TEMPORARY_TABLE = "enableTemporaryTable";
    public static final String ENABLE_TEMPORARY_TABLE_DEFAULT_VALUE = "false";
    public static final String BDE_FETCH_SETTING_BASE_DATA_INPUT_SWITCH = "BDE_FETCH_SETTING_BASE_DATA_INPUT_SWITCH";
    public static final String BDE_FETCH_SETTING_BASE_DATA_SELECT_DIM = "BDE_FETCH_SETTING_BASE_DATA_SELECT_DIM";
    public static final String BDE_FETCH_SETTING_BASE_DATA_SUBJECT_CODE = "SUBJECT";
    public static final String BDE_FETCH_SETTING_BASE_DATA_XJLL_CODE = "XJLL";
    private static Logger LOGGER = LoggerFactory.getLogger(BdeCommonUtil.class);
    @Value(value="${jiuqi.bde.server.standalone:true}")
    private Boolean standaloneServer;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private BdeCommonConfig commonConfig;
    private static BdeCommonUtil commonUtil;
    private static Random random;

    @PostConstruct
    public void init() {
        commonUtil = this;
        BdeCommonUtil.commonUtil.npApplication = this.npApplication;
        BdeCommonUtil.commonUtil.standaloneServer = this.standaloneServer;
    }

    public static boolean fieldDefineTypeIsNum(Integer dataType) {
        return dataType == null || DataTypes.isNum(dataType);
    }

    public static boolean fieldDefineTypeIsDate(Integer dataType) {
        return dataType == null || dataType == 5 || dataType == 2;
    }

    public static String formatDateNoDash(String date) {
        if (StringUtils.isEmpty((String)date)) {
            return date;
        }
        return DateUtils.createFormatter((DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE).format(DateUtils.parse((String)date));
    }

    public static Map<String, Object> parseOptimizeRuleToMap(String optimizeRuleGroup) {
        if (StringUtils.isEmpty((String)optimizeRuleGroup) || "#".equals(optimizeRuleGroup)) {
            return CollectionUtils.newHashMap();
        }
        try {
            return (Map)JsonUtils.readValue((String)optimizeRuleGroup, (TypeReference)new TypeReference<Map<String, Object>>(){});
        }
        catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }

    public static void handleOptimizeDateOffset(FetchTaskContext fetchTaskContext, String val, int field) {
        if (val == null) {
            return;
        }
        Assert.isNotNull((Object)fetchTaskContext);
        if (StringUtils.isEmpty((String)val)) {
            return;
        }
        DateFormat formatter = DateUtils.createFormatter((String)DateUtils.DEFAULT_DATE_FORMAT);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(fetchTaskContext.getStartDateStr());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException(String.format("\u5b57\u7b26\u4e32\u3010%1$s\u3011\u89e3\u6790\u4e3a\u65e5\u671f\u51fa\u73b0\u9519\u8bef", fetchTaskContext.getStartDateStr()), (Throwable)e);
        }
        try {
            endDate = formatter.parse(fetchTaskContext.getEndDateStr());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException(String.format("\u5b57\u7b26\u4e32\u3010%1$s\u3011\u89e3\u6790\u4e3a\u65e5\u671f\u51fa\u73b0\u9519\u8bef", fetchTaskContext.getEndDateStr()), (Throwable)e);
        }
        Pair<Date, Date> handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(Pair.of(startDate, endDate), val, field);
        fetchTaskContext.setStartDateStr(formatter.format(handleOptimizeDateOffset.getFirst()));
        fetchTaskContext.setEndDateStr(formatter.format(handleOptimizeDateOffset.getSecond()));
        BdeCommonUtil.offsetPeriodSchmeByEndDate(fetchTaskContext, handleOptimizeDateOffset.getSecond());
    }

    private static void offsetPeriodSchmeByEndDate(FetchTaskContext fetchTaskContext, Date endDate) {
        Assert.isNotNull((Object)fetchTaskContext);
        if (endDate == null || StringUtils.isEmpty((String)fetchTaskContext.getPeriodScheme())) {
            return;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(endDate);
        String periodScheme = fetchTaskContext.getPeriodScheme();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodScheme);
        int type = periodWrapper.getType();
        PeriodWrapper offSetPeriodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)type, (int)0);
        String offsetPeriodScheme = offSetPeriodWrapper.toString();
        fetchTaskContext.setPeriodScheme(offsetPeriodScheme);
    }

    public static Pair<Date, Date> handleOptimizeDateOffset(Pair<Date, Date> dateRange, String val, int field) {
        if (val == null) {
            return dateRange;
        }
        Assert.isNotNull(dateRange);
        if (StringUtils.isEmpty((String)val)) {
            return dateRange;
        }
        if (val.startsWith(SignTypeEnum.PLUS.getSign()) || val.startsWith(SignTypeEnum.SUBTRACT.getSign())) {
            Date startDate = DateUtils.shifting((Date)dateRange.getFirst(), (int)field, (int)Integer.valueOf(val));
            Date endDate = DateUtils.shifting((Date)dateRange.getSecond(), (int)field, (int)Integer.valueOf(val));
            return Pair.of(startDate, endDate);
        }
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(dateRange.getFirst());
        Date newStartDate = DateUtils.firstDateOf((int)(field == 1 ? Integer.valueOf(val).intValue() : startDate.get(1)), (int)(field == 2 ? Integer.valueOf(val) : startDate.get(2) + 1));
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(dateRange.getSecond());
        Date newEndDate = DateUtils.lastDateOf((int)(field == 1 ? Integer.valueOf(val).intValue() : endDate.get(1)), (int)(field == 2 ? Integer.valueOf(val) : endDate.get(2) + 1));
        return Pair.of(newStartDate, newEndDate);
    }

    public static boolean valIsNum(Object col) {
        if (col == null) {
            return false;
        }
        return col instanceof BigDecimal || col instanceof Integer || col instanceof Double;
    }

    public static boolean valIsZero(Object col) {
        if (col == null) {
            return true;
        }
        if (col instanceof BigDecimal) {
            return ((BigDecimal)col).compareTo(BigDecimal.ZERO) == 0;
        }
        if (col instanceof Integer) {
            return ((Integer)col).compareTo(0) == 0;
        }
        if (col instanceof Double) {
            return NumberUtils.isZreo((Double)((Double)col));
        }
        return true;
    }

    public static void initNpUser(String userName) {
        try {
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237", (Object)userName);
            NpContext npContext = BdeCommonUtil.commonUtil.npApplication.getTempContext(userName);
            NpContextHolder.setContext((NpContext)npContext);
            LOGGER.debug("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u5b8c\u6210", (Object)npContext);
        }
        catch (Exception e) {
            LOGGER.error("\u6ce8\u5165\u53d6\u6570\u6267\u884c\u4e0a\u4e0b\u6587\u7528\u6237\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    public static boolean isStandaloneServer() {
        if (BdeCommonUtil.commonUtil.standaloneServer == null) {
            return false;
        }
        return BdeCommonUtil.commonUtil.standaloneServer;
    }

    public static int getRouteNum() {
        return random.nextInt(10) + 1;
    }

    public static Long getTaskSleepTime() {
        return BdeCommonUtil.commonUtil.commonConfig.getFetchTaskSleepTime();
    }

    public static Long getExecuteSleepTime() {
        return BdeCommonUtil.commonUtil.commonConfig.getFetchSleepTime();
    }

    static {
        random = new Random();
    }
}


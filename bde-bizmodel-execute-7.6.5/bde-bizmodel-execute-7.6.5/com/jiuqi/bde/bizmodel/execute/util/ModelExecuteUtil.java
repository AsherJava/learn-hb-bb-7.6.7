/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SignTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.base.common.enums.DataType
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService
 *  com.jiuqi.dc.datamapping.impl.service.impl.BDEDataRefConfigureServiceImpl
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.commons.lang3.time.DateUtils
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SignTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.base.common.enums.DataType;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.datamapping.impl.service.impl.BDEDataRefConfigureServiceImpl;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

public class ModelExecuteUtil {
    public static final String FN_DELIMITER_COLON = ":";
    private static final String FN_TMPL_END_SCOPE = "%1$sZZ";
    public static final String FN_DELIMITER_COMMA = ",";
    public static final String ACCTYEAR = "acctYear";
    public static final String ACCTPERIOD = "acctPeriod";
    public static final String ORGCODE = "orgCode";
    public static final String DIMCOMB = "dimComb";
    public static final String DIMTYPE = "dimType";
    public static final String AGINGFETCHTYPE = "agingFetchType";
    public static final String AGINGPERIOD = "agingRangeType";
    public static final String AGINGSTARTPERIOD = "agingRangeStart";
    public static final String AGINGENDPERIOD = "agingRangeEnd";
    public static final String AGEGROUPO = "ageGroup";
    public static final String FN_DEFAULT_OPTIMIZERULEGROUP = "{}";
    public static final String DIMRULE_EQ = "EQ";
    private static final String DIMRULE_LIKE = "LIKE";
    private static final String FN_ZERO = "0";
    private static final String TB_AGINGBALANCE_PREFIX = "DC_AGINGBALANCE_";
    private static final String FN_ASSIST_FIELDNAME_TMPL = "%s_NAME";

    private ModelExecuteUtil() {
    }

    private static <T, C> C getVal(T t, String field) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(t.getClass(), field);
        if (propertyDescriptor == null) {
            throw new FatalBeanException("Could not read property '" + field + "' from " + t.getClass());
        }
        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            throw new FatalBeanException("Could not find readMethod '" + field + "' from " + t.getClass());
        }
        try {
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                throw new FatalBeanException("Could not invoke readMethod '" + field + "' from " + t.getClass());
            }
            return (C)readMethod.invoke(t, new Object[0]);
        }
        catch (Exception ex) {
            throw new FatalBeanException("Could not read property '" + propertyDescriptor.getName() + "' from " + t.getClass(), ex);
        }
    }

    public static <T> BigDecimal getFetchVal(T t, String fetchTypeStr) {
        FetchTypeEnum fetchType = FetchTypeEnum.fromName((String)fetchTypeStr);
        if (fetchType == FetchTypeEnum.UNKNOWN) {
            throw new BusinessRuntimeException(String.format("\u53d6\u6570\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", fetchTypeStr));
        }
        if (fetchType == FetchTypeEnum.BQNUM) {
            return NumberUtils.sub((BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.JF), (BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.DF));
        }
        if (fetchType == FetchTypeEnum.LJNUM) {
            return NumberUtils.sub((BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.JL), (BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.DL));
        }
        if (fetchType == FetchTypeEnum.WBQNUM) {
            return NumberUtils.sub((BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.WJF), (BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.WDF));
        }
        if (fetchType == FetchTypeEnum.WLJNUM) {
            return NumberUtils.sub((BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.WJL), (BigDecimal)ModelExecuteUtil.getFetchVal(t, FetchTypeEnum.WDL));
        }
        return ModelExecuteUtil.getFetchVal(t, fetchType);
    }

    public static <T> BigDecimal getFetchValBySrc(T t, String fetchTypeStr) {
        FetchTypeEnum fetchType = FetchTypeEnum.fromName((String)fetchTypeStr);
        if (fetchType == FetchTypeEnum.UNKNOWN) {
            throw new BusinessRuntimeException(String.format("\u53d6\u6570\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", fetchTypeStr));
        }
        return ModelExecuteUtil.getFetchVal(t, fetchType);
    }

    private static <T> BigDecimal getFetchVal(T t, FetchTypeEnum fetchType) {
        return (BigDecimal)ModelExecuteUtil.getVal(t, fetchType.name().toLowerCase());
    }

    public static boolean match(String setting, String code) {
        Assert.isNotEmpty((String)setting);
        String string = code = code == null ? "" : code;
        if (setting.contains(FN_DELIMITER_COLON)) {
            return ModelExecuteUtil.matchRange(setting, code);
        }
        if (setting.contains(FN_DELIMITER_COMMA)) {
            return ModelExecuteUtil.matchMultiple(setting, code);
        }
        return ModelExecuteUtil.matchSingle(setting, code);
    }

    private static boolean matchSingle(String setting, String code) {
        return code.startsWith(setting);
    }

    private static boolean matchMultiple(String setting, String code) {
        String[] settingArr = setting.split(FN_DELIMITER_COMMA);
        if (settingArr.length == 1) {
            return ModelExecuteUtil.matchSingle(setting, code);
        }
        for (String settingStr : settingArr) {
            if (!ModelExecuteUtil.matchSingle(settingStr, code)) continue;
            return true;
        }
        return false;
    }

    private static boolean matchRange(String setting, String code) {
        String[] settingArr = setting.split(FN_DELIMITER_COLON);
        if (settingArr.length != 2) {
            return false;
        }
        String begin = settingArr[0];
        String end = String.format(FN_TMPL_END_SCOPE, settingArr[1]);
        return code.compareTo(begin) >= 0 && code.compareTo(end) <= 0;
    }

    public static boolean matchByRule(String setting, String code, String rule) {
        Assert.isNotEmpty((String)setting);
        Assert.isNotEmpty((String)rule);
        code = code == null ? "" : code;
        switch (rule) {
            case "EQ": {
                return setting.equals(code);
            }
            case "LIKE": {
                return code.startsWith(setting);
            }
        }
        return false;
    }

    public static final int getValByDefault(Integer val) {
        return val == null ? 0 : val;
    }

    public static final String getValByDefault(String val) {
        return StringUtils.isEmpty((String)val) ? "#" : val;
    }

    public static final Integer getIncludeUncharged(Boolean includeUncharged) {
        return Boolean.TRUE.equals(includeUncharged) ? BooleanValEnum.YES.getCode() : BooleanValEnum.NO.getCode();
    }

    public static final Integer getIncludeAdjustVoucher(Boolean includeAdjustVchr) {
        return Boolean.TRUE.equals(includeAdjustVchr) ? BooleanValEnum.YES.getCode() : BooleanValEnum.NO.getCode();
    }

    public static final String getValByDefault(Object val) {
        if (val == null) {
            return "#";
        }
        return StringUtils.isEmpty((String)((String)val)) ? "#" : (String)val;
    }

    public static final String getValByDefault(List<Dimension> assTypeList) {
        if (CollectionUtils.isEmpty(assTypeList)) {
            return "#";
        }
        StringBuilder dimBuilder = new StringBuilder();
        for (Dimension dim : assTypeList) {
            dimBuilder.append(dim.getDimCode()).append(FN_DELIMITER_COLON).append(dim.getDimRule()).append(";");
        }
        return dimBuilder.toString();
    }

    public static Object getValByDefault(Map<String, String> otherEntity) {
        if (otherEntity == null || otherEntity.isEmpty()) {
            return "#";
        }
        StringBuilder entryBuilder = new StringBuilder();
        for (Map.Entry<String, String> entity : otherEntity.entrySet()) {
            entryBuilder.append(entity.getKey()).append(FN_DELIMITER_COLON).append(entity.getValue()).append(";");
        }
        return entryBuilder.toString();
    }

    public static ModelExecuteContext convert2ExecuteContext(FetchTaskContext fetchTaskContext) {
        ModelExecuteContext executeContext = (ModelExecuteContext)((Object)BeanConvertUtil.convert((Object)fetchTaskContext, ModelExecuteContext.class, (String[])new String[0]));
        return executeContext;
    }

    public static Boolean getEnableTransTable(String dataSchemeCode) {
        Assert.isNotEmpty((String)dataSchemeCode);
        DataSchemeOptionDTO optionDTO = ((DataSchemeOptionService)ApplicationContextRegister.getBean(DataSchemeOptionService.class)).getValueByDataSchemeCode(dataSchemeCode, "enableTemporaryTable");
        if (optionDTO != null) {
            return optionDTO.getOptionValue().getBooleanValue();
        }
        DataSchemeOptionValue defaultValue = new DataSchemeOptionValue(DataType.INT, (Object)"false");
        return defaultValue.getBooleanValue();
    }

    public static Map<String, Dimension> parseDimSettingToMap(String dimensionSetting) {
        return ModelExecuteUtil.parseDimSettingToList(dimensionSetting).stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
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
        Pair<Date, Date> handleOptimizeDateOffset = ModelExecuteUtil.handleOptimizeDateOffset((Pair<Date, Date>)Pair.of((Object)startDate, (Object)endDate), val, field);
        fetchTaskContext.setStartDateStr(formatter.format((Date)handleOptimizeDateOffset.getFirst()));
        fetchTaskContext.setEndDateStr(formatter.format((Date)handleOptimizeDateOffset.getSecond()));
    }

    private static Pair<Date, Date> handleOptimizeDateOffset(Pair<Date, Date> dateRange, String val, int field) {
        if (val == null) {
            return dateRange;
        }
        Assert.isNotNull(dateRange);
        if (StringUtils.isEmpty((String)val)) {
            return dateRange;
        }
        if (val.startsWith(SignTypeEnum.PLUS.getSign()) || val.startsWith(SignTypeEnum.SUBTRACT.getSign())) {
            Date startDate = DateUtils.shifting((Date)((Date)dateRange.getFirst()), (int)field, (int)Integer.valueOf(val));
            Date endDate = DateUtils.shifting((Date)((Date)dateRange.getSecond()), (int)field, (int)Integer.valueOf(val));
            return Pair.of((Object)startDate, (Object)endDate);
        }
        Calendar startDate = Calendar.getInstance();
        startDate.setTime((Date)dateRange.getFirst());
        Date newStartDate = DateUtils.firstDateOf((int)(field == 1 ? Integer.valueOf(val).intValue() : startDate.get(1)), (int)(field == 2 ? Integer.valueOf(val) : startDate.get(2) + 1));
        Calendar endDate = Calendar.getInstance();
        endDate.setTime((Date)dateRange.getSecond());
        Date newEndDate = DateUtils.lastDateOf((int)(field == 1 ? Integer.valueOf(val).intValue() : endDate.get(1)), (int)(field == 2 ? Integer.valueOf(val) : endDate.get(2) + 1));
        return Pair.of((Object)newStartDate, (Object)newEndDate);
    }

    public static List<Dimension> parseDimSettingToList(String dimensionSetting) {
        if (StringUtils.isEmpty((String)dimensionSetting)) {
            return CollectionUtils.newArrayList();
        }
        List dimList = ((ArrayList)JsonUtils.readValue((String)dimensionSetting, (TypeReference)new TypeReference<ArrayList<Dimension>>(){})).stream().sorted(Comparator.comparing(Dimension::getDimCode)).collect(Collectors.toList());
        return dimList.stream().filter(item -> !StringUtils.isEmpty((String)item.getDimValue())).collect(Collectors.toList());
    }

    public static List<Dimension> parseAssTypeList(Object dimComb) {
        Map dimCombMap = (Map)dimComb;
        if (dimCombMap == null || dimCombMap.isEmpty()) {
            return CollectionUtils.newArrayList();
        }
        return dimCombMap.entrySet().stream().map(item -> new Dimension((String)item.getKey(), (String)item.getValue())).sorted(Comparator.comparing(Dimension::getDimCode)).collect(Collectors.toList());
    }

    public static String lpadPeriod(Integer str) {
        return CommonUtil.lpad((String)String.valueOf(str), (String)FN_ZERO, (int)2);
    }

    public static String decreaseYear(Date now, int years, String dateFormat) {
        return DateUtils.format((Date)ModelExecuteUtil.decreaseYear2Date(now, years), (String)dateFormat);
    }

    public static Date addDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays((Date)date, (int)amount);
    }

    public static Date subDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays((Date)date, (int)(-amount));
    }

    public static String getLastSecondInDate(Date date, SimpleDateFormat dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        return dateFormat.format(cal.getTime());
    }

    public static Date decreaseYear2Date(Date now, int years) {
        Calendar oc = Calendar.getInstance();
        oc.setTime(now);
        oc.add(1, -years);
        return oc.getTime();
    }

    public static String decreaseMonth(Date now, int months, boolean resetLastDay, String dateFormat) {
        return DateUtils.format((Date)ModelExecuteUtil.decreaseMonth2Date(now, months, resetLastDay), (String)dateFormat);
    }

    public static Date decreaseMonth2Date(Date now, int months, boolean resetLastDay) {
        Calendar oc = Calendar.getInstance();
        oc.setTime(now);
        oc.add(2, -months);
        if (resetLastDay) {
            oc.set(5, oc.getActualMaximum(5));
        }
        return oc.getTime();
    }

    public static String decreaseDay(Date now, int days, String dateFormat) {
        return DateUtils.format((Date)ModelExecuteUtil.decreaseDay2Date(now, days), (String)dateFormat);
    }

    public static Date decreaseDay2Date(Date now, int days) {
        Calendar oc = Calendar.getInstance();
        oc.setTime(now);
        oc.add(6, -days);
        return oc.getTime();
    }

    public static String replaceContext(String srcSql, BalanceCondition condi) {
        Assert.isNotEmpty((String)srcSql);
        Assert.isNotNull((Object)condi);
        String period = condi.getEndPeriod().toString();
        String acctYear = condi.getAcctYear().toString();
        List orgMappingItems = condi.getOrgMapping().getOrgMappingItems();
        String unitCode = "";
        if (CollectionUtils.isEmpty((Collection)orgMappingItems)) {
            unitCode = condi.getOrgMapping().getAcctOrgCode();
        } else {
            HashSet<String> orgCodeSet = new HashSet<String>();
            for (OrgMappingItem orgMappingItem : orgMappingItems) {
                if (StringUtils.isEmpty((String)orgMappingItem.getAcctOrgCode())) continue;
                orgCodeSet.add(orgMappingItem.getAcctOrgCode());
            }
            unitCode = String.join((CharSequence)"','", orgCodeSet);
        }
        String sql = srcSql.replaceAll("(?i)#orgid#", unitCode).replaceAll("(?i)#unitcode#", unitCode).replaceAll("(?i)#\\*", condi.getOrgMapping().getAcctOrgCode()).replaceAll("(?i)#yearperiod#", ModelExecuteUtil.buildPeriod(acctYear, period)).replaceAll("(?i)#year#", acctYear).replaceAll("(?i)#period#", period).replaceAll("(?i)#fullperiod#", CommonUtil.lpad((String)period, (String)FN_ZERO, (int)2)).replaceAll("(?i)#bookid#", condi.getOrgMapping().getAcctBookCode()).replaceAll("(?i)#bookcode#", condi.getOrgMapping().getAcctBookCode()).replaceAll("(?i)#includeuncharged#", condi.isIncludeUncharged() != false ? FN_ZERO : "1");
        return sql;
    }

    public static String buildPeriod(String year, String period) {
        if (FN_ZERO.equals(period)) {
            period = "1";
        }
        return String.format("%1$s-0%2$s", year, period);
    }

    public static String getAgingBalanceTableName(int acctYear) {
        return TB_AGINGBALANCE_PREFIX + acctYear;
    }

    public static String getAssistFieldName(String assistTableName) {
        return String.format(FN_ASSIST_FIELDNAME_TMPL, assistTableName);
    }

    public static List<String> getReductionData(String mappingCode, String dataSchemeCode, String dimCode) {
        HashSet<String> resultList = new HashSet<String>();
        DataRefListDTO dataIsMappingRefList = new DataRefListDTO();
        dataIsMappingRefList.setDataSchemeCode(dataSchemeCode);
        dataIsMappingRefList.setFilterType(DataRefFilterType.HASREF.getCode());
        dataIsMappingRefList.setTableName(dimCode);
        DataRefListConfigureService dataRefListConfigureService = (DataRefListConfigureService)SpringBeanUtils.getBean(BDEDataRefConfigureServiceImpl.class);
        DataRefListVO dataRefIsMappingListVO = dataRefListConfigureService.list(dataIsMappingRefList);
        List dataIsMappingRefDTOs = dataRefIsMappingListVO.getPageVo().getRows();
        ArrayList<String> MappingCodes = new ArrayList<String>();
        if (mappingCode.contains(FN_DELIMITER_COMMA)) {
            MappingCodes.addAll(Arrays.asList(mappingCode.split(FN_DELIMITER_COMMA)));
            for (String code : MappingCodes) {
                for (DataRefDTO dataRefDTO : dataIsMappingRefDTOs) {
                    if (code.compareTo(dataRefDTO.getCode()) != 0) continue;
                    resultList.add(dataRefDTO.getOdsCode());
                }
            }
        } else if (mappingCode.contains(FN_DELIMITER_COLON)) {
            MappingCodes.addAll(Arrays.asList(mappingCode.split(FN_DELIMITER_COLON)));
            for (DataRefDTO dataRefDTO : dataIsMappingRefDTOs) {
                if (((String)MappingCodes.get(0)).compareTo(dataRefDTO.getCode()) > 0 || ((String)MappingCodes.get(MappingCodes.size() - 1)).compareTo(dataRefDTO.getCode()) < 0) continue;
                resultList.add(dataRefDTO.getOdsCode());
            }
        } else {
            for (DataRefDTO dataRefDTO : dataIsMappingRefDTOs) {
                if (mappingCode.compareTo(dataRefDTO.getCode()) != 0) continue;
                resultList.add(dataRefDTO.getOdsCode());
            }
        }
        return new ArrayList<String>(resultList);
    }

    public static int getTableNameCount(String seniorSql) {
        Pattern pattern = Pattern.compile(Pattern.quote("#TABLENAME#"));
        Matcher matcher = pattern.matcher(seniorSql);
        int tableNameCount = 0;
        while (matcher.find()) {
            ++tableNameCount;
        }
        return tableNameCount;
    }
}


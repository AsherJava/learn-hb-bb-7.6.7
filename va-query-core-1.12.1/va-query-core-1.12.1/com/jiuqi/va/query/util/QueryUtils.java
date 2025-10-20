/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum
 *  com.jiuqi.va.query.sql.vo.QueryExpandField
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.QuerySortVO
 *  com.jiuqi.va.query.template.dto.print.QueryPrintPluginDTO
 *  com.jiuqi.va.query.template.enumerate.AlignEnum
 *  com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.BaseInfoPlugin
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.plugin.FormulaPlugin
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryPrintPlugin
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.plugin.ViewDesignPlugin
 *  com.jiuqi.va.query.template.plugin.ViewSetPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateContentVO
 *  com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.query.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import com.jiuqi.va.query.config.VaQueryI18nParam;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum;
import com.jiuqi.va.query.sql.vo.QueryExpandField;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.QuerySortVO;
import com.jiuqi.va.query.template.dto.print.QueryPrintPluginDTO;
import com.jiuqi.va.query.template.enumerate.AlignEnum;
import com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.FormulaPlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryPrintPlugin;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.plugin.ViewDesignPlugin;
import com.jiuqi.va.query.template.plugin.ViewSetPlugin;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.template.vo.TemplateDataSourceSetVO;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QueryUtils {
    private static final Logger logger = LoggerFactory.getLogger(QueryUtils.class);
    private static boolean isJdk8;

    private QueryUtils() {
    }

    public static TemplateContentVO QueryTemplate2TemplateContentVO(QueryTemplate queryTemplate) {
        TemplateContentVO templateContent = new TemplateContentVO();
        ViewSetPlugin pluginByName = (ViewSetPlugin)queryTemplate.getPluginByName(PluginEnum.viewSet.name(), ViewSetPlugin.class);
        TemplateInfoVO baseInfo = ((BaseInfoPlugin)queryTemplate.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo();
        Object columnLayout = pluginByName.getViewSet("columnLayout");
        if (columnLayout == null) {
            baseInfo.setColumnLayout(Integer.valueOf(0));
        } else {
            baseInfo.setColumnLayout(Integer.valueOf(Integer.parseInt(String.valueOf(columnLayout))));
        }
        baseInfo.setConditionDisplay(String.valueOf(pluginByName.getViewSet("conditionDisplay")));
        templateContent.setTemplate(baseInfo);
        DataSourcePlugin dataSourceSet = queryTemplate.getDataSourceSet();
        TemplateDataSourceSetVO dataSourceSetVO = new TemplateDataSourceSetVO();
        dataSourceSetVO.setDefineSql(dataSourceSet.getDefineSql());
        templateContent.setDataSourceSet(dataSourceSetVO);
        templateContent.setParams(dataSourceSet.getParams());
        templateContent.setFields(((QueryFieldsPlugin)queryTemplate.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields());
        templateContent.setViewSets(((ViewSetPlugin)queryTemplate.getPluginByName(PluginEnum.viewSet.name(), ViewSetPlugin.class)).getViewSets());
        templateContent.setRelateQuerys(((QueryRelatePlugin)queryTemplate.getPluginByName(PluginEnum.queryRelate.name(), QueryRelatePlugin.class)).getRelateQuerys());
        templateContent.setTools(((ToolBarPlugin)queryTemplate.getPluginByName(PluginEnum.toolBar.name(), ToolBarPlugin.class)).getTools());
        templateContent.setDesignSets(((ViewDesignPlugin)queryTemplate.getPluginByName(PluginEnum.viewDesign.name(), ViewDesignPlugin.class)).getDesignSets());
        FormulaPlugin formulaPlugin = queryTemplate.getFormula();
        templateContent.setFormulas(formulaPlugin.getFormulas());
        templateContent.setFormulaValues(formulaPlugin.getFormulaValues());
        QueryPrintPlugin printPlugin = (QueryPrintPlugin)queryTemplate.getPluginByClass(QueryPrintPlugin.class);
        if (Objects.nonNull(printPlugin)) {
            QueryPrintPluginDTO queryPrintPluginDTO = new QueryPrintPluginDTO();
            queryPrintPluginDTO.setSchemes(printPlugin.getSchemes());
            templateContent.setQueryPrintPluginDto(queryPrintPluginDTO);
        }
        return templateContent;
    }

    public static QueryTemplate TemplateContentVO2QueryTemplate(TemplateContentVO templateContentVO) {
        List schemes;
        TemplateInfoVO template = templateContentVO.getTemplate();
        TemplateDataSourceSetVO dataSourceSet = templateContentVO.getDataSourceSet();
        QueryTemplate queryTemplate = new QueryTemplate();
        queryTemplate.setCode(template.getCode());
        queryTemplate.setId(template.getId());
        ArrayList<Object> plugins = new ArrayList<Object>();
        BaseInfoPlugin baseInfoPlugin = new BaseInfoPlugin();
        baseInfoPlugin.setBaseInfo(templateContentVO.getTemplate());
        plugins.add(baseInfoPlugin);
        DataSourcePlugin dataSourcePlugin = new DataSourcePlugin();
        dataSourcePlugin.setDefineSql(dataSourceSet.getDefineSql());
        dataSourcePlugin.setParams(templateContentVO.getParams());
        plugins.add(dataSourcePlugin);
        QueryFieldsPlugin queryFieldsPlugin = new QueryFieldsPlugin();
        queryFieldsPlugin.setFields(templateContentVO.getFields());
        plugins.add(queryFieldsPlugin);
        QueryRelatePlugin queryRelatePlugin = new QueryRelatePlugin();
        queryRelatePlugin.setRelateQuerys(templateContentVO.getRelateQuerys());
        plugins.add(queryRelatePlugin);
        ToolBarPlugin toolBarPlugin = new ToolBarPlugin();
        toolBarPlugin.setTools(templateContentVO.getTools());
        plugins.add(toolBarPlugin);
        ViewSetPlugin viewSetPlugin = new ViewSetPlugin();
        Map viewSets = Optional.ofNullable(templateContentVO.getViewSets()).orElse(new HashMap());
        viewSetPlugin.setViewSets(viewSets);
        viewSetPlugin.putViewSet("conditionDisplay", (Object)template.getConditionDisplay());
        viewSetPlugin.putViewSet("columnLayout", (Object)template.getColumnLayout());
        plugins.add(viewSetPlugin);
        ViewDesignPlugin viewDesignPlugin = new ViewDesignPlugin();
        viewDesignPlugin.setDesignSets(templateContentVO.getDesignSets());
        plugins.add(viewDesignPlugin);
        FormulaPlugin formulaPlugin = new FormulaPlugin();
        formulaPlugin.setFormulas(templateContentVO.getFormulas());
        formulaPlugin.setFormulaValues(templateContentVO.getFormulaValues());
        plugins.add(formulaPlugin);
        QueryPrintPluginDTO queryPrintPluginDto = templateContentVO.getQueryPrintPluginDto();
        if (Objects.nonNull(queryPrintPluginDto) && !CollectionUtils.isEmpty(schemes = queryPrintPluginDto.getSchemes())) {
            QueryPrintPlugin queryPrintPlugin = new QueryPrintPlugin();
            queryPrintPlugin.setSchemes(schemes);
            plugins.add(queryPrintPlugin);
        }
        queryTemplate.setPlugins(plugins);
        return queryTemplate;
    }

    public static TableHeaderVO fieldToTableHeaderVO(TemplateFieldSettingVO field) {
        TableHeaderVO column = new TableHeaderVO();
        if (!DCQueryStringHandle.isEmpty(field.getAlign())) {
            column.setAlign(field.getAlign());
        } else if (ParamTypeEnum.NUMBER.getTypeName().equals(field.getDataType())) {
            column.setAlign(AlignEnum.RIGHT.getName());
        }
        if (!field.getTitle().contains("|")) {
            column.setKey(field.getName());
        } else {
            column.setAlign(AlignEnum.CENTER.getName());
        }
        column.setTitle(field.getTitle());
        if (AutoWidthTypeEnum.REGULAR.getName().equals(field.getAutoWidth())) {
            column.setWidth(field.getWidth());
        } else {
            ParamTypeEnum paramType = ParamTypeEnum.val((String)field.getDataType());
            switch (paramType) {
                case STRING: {
                    column.setWidth("200px");
                    break;
                }
                case NUMBER: {
                    column.setWidth("130px");
                    break;
                }
                case INTEGER: {
                    column.setWidth("80px");
                    break;
                }
                case BOOL: {
                    column.setWidth("70px");
                    break;
                }
                case DATE: {
                    column.setWidth("150px");
                    break;
                }
            }
        }
        column.setFieldSetting(field);
        return column;
    }

    public static void transformFrontQueryTemplate(QueryParamVO queryParamVO, boolean keepDesign) {
        TemplateDesignService designService = DCQuerySpringContextUtils.getBean(TemplateDesignService.class);
        QueryTemplate template = designService.getTemplate(queryParamVO.getQueryTemplate().getId());
        if (keepDesign) {
            DataSourcePlugin dataSourceSet = template.getDataSourceSet();
            queryParamVO.getQueryTemplate().getDataSourceSet().setDefineSql(dataSourceSet.getDefineSql());
        } else {
            QueryUtils.transformFrontQueryTemplate(queryParamVO);
        }
    }

    public static void transformFrontQueryTemplate(QueryParamVO queryParamVO) {
        TemplateDesignService designService = DCQuerySpringContextUtils.getBean(TemplateDesignService.class);
        QueryTemplate template = designService.getTemplate(queryParamVO.getQueryTemplate().getId());
        queryParamVO.setQueryTemplate(template);
        Map<String, TemplateFieldSettingVO> fieldSettingVOMap = ((QueryFieldsPlugin)template.getPluginByClass(QueryFieldsPlugin.class)).getFields().stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, f -> f, (f, v) -> v));
        if (!CollectionUtils.isEmpty(queryParamVO.getSortFields())) {
            for (QuerySortVO sortField : queryParamVO.getSortFields()) {
                if (fieldSettingVOMap.containsKey(sortField.getName())) continue;
                throw new DefinedQueryRuntimeException("\u975e\u6cd5\u7684\u6392\u5e8f\u5217\uff1a" + sortField.getName());
            }
        }
        if (!CollectionUtils.isEmpty(queryParamVO.getGroupFields())) {
            for (QueryGroupField groupField : queryParamVO.getGroupFields()) {
                if (fieldSettingVOMap.containsKey(groupField.getFieldName())) continue;
                throw new DefinedQueryRuntimeException("\u975e\u6cd5\u7684\u5206\u7ec4\u5217\uff1a" + groupField.getFieldName());
            }
        }
        if (!CollectionUtils.isEmpty(queryParamVO.getExpandFields())) {
            for (QueryExpandField expandField : queryParamVO.getExpandFields()) {
                if (fieldSettingVOMap.containsKey(expandField.getFieldName())) continue;
                throw new DefinedQueryRuntimeException("\u975e\u6cd5\u7684\u5206\u7ec4\u5217\uff1a" + expandField.getFieldName());
            }
        }
    }

    public static QueryTemplate removeSqlFromQueryTemplate(QueryTemplate queryTemplate) {
        queryTemplate.getDataSourceSet().setDefineSql("");
        return queryTemplate;
    }

    public static Object getNumberValue(Object value, TemplateFieldSettingVO field) {
        if (value == null) {
            return null;
        }
        Double sqlNumber = (Double)value;
        if (Objects.equals(field.getDataType(), "integer") && !Objects.equals(field.getGatherType(), "averge")) {
            return sqlNumber.intValue();
        }
        if (DCQueryStringHandle.isEmpty(field.getDisplayFormat()) || DisplayFormatEnum.CURRENCY.getTypeName().equals(field.getDisplayFormat())) {
            return QueryUtils.getCurrencyString(sqlNumber, field);
        }
        String number = sqlNumber.toString();
        String numberPlus = String.valueOf(sqlNumber * 100.0);
        if (field.getDecimalLength() != null) {
            String pattern = "0.00";
            DecimalFormat numberFormat = new DecimalFormat(pattern);
            numberFormat.setMinimumFractionDigits(field.getDecimalLength());
            numberFormat.setMaximumFractionDigits(field.getDecimalLength());
            number = numberFormat.format(sqlNumber);
            numberPlus = numberFormat.format(sqlNumber * 100.0);
        }
        if (DisplayFormatEnum.DEFAULT.getTypeName().equals(field.getDisplayFormat())) {
            return number;
        }
        if (DisplayFormatEnum.PERCENT.getTypeName().equals(field.getDisplayFormat())) {
            return number + "%";
        }
        if (DisplayFormatEnum.AUTO_PERCENT.getTypeName().equals(field.getDisplayFormat())) {
            return numberPlus + "%";
        }
        return number;
    }

    private static String getCurrencyString(Double sqlNumber, TemplateFieldSettingVO field) {
        String pattern = "#,##0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        if (field.getDecimalLength() != null) {
            numberFormat.setMinimumFractionDigits(field.getDecimalLength());
            numberFormat.setMaximumFractionDigits(field.getDecimalLength());
        }
        return numberFormat.format(sqlNumber);
    }

    public static <T> T[] mergeArrays(T[] array1, T[] array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("\u8f93\u5165\u6570\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        T[] mergedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        return mergedArray;
    }

    public static <T> Map<String, T> getMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return new HashMap();
    }

    public static <T> List<T> getList(Object object) {
        if (object instanceof List) {
            return (List)object;
        }
        return new ArrayList();
    }

    public static boolean isJsonStr(String str) {
        if (!str.contains("{")) {
            return false;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(str);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private static String getClientTimeZone() {
        try {
            Boolean translationEnabled = VaI18nParamUtils.getTranslationEnabled();
            if (!Boolean.TRUE.equals(translationEnabled)) {
                return null;
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return null;
            }
            return RequestContextUtil.getHeader((String)"timezone");
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Date convertTimeZone(Date date) {
        if (Objects.isNull(date)) {
            return date;
        }
        String clientTimeZone = QueryUtils.getClientTimeZone();
        if (!StringUtils.hasText(clientTimeZone)) {
            return date;
        }
        try {
            return QueryUtils.convertToZoneTime(date, clientTimeZone);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return date;
        }
    }

    private static Date convertToZoneTime(Date date, String targetZone) throws ParseException {
        Instant instant = date.toInstant();
        ZoneId defaultZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime defaultTime = instant.atZone(defaultZoneId);
        ZoneId targetZoneId = ZoneId.of(targetZone);
        ZonedDateTime targetTime = defaultTime.withZoneSameInstant(targetZoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = targetTime.format(dateTimeFormatter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(format);
    }

    public static String convertTimeToShanghaiTime(String dateTime) {
        if (!StringUtils.hasText(dateTime)) {
            return "";
        }
        String fromTimeZone = QueryUtils.getClientTimeZone();
        if (fromTimeZone == null) {
            return dateTime;
        }
        String toTimeZone = "Asia/Shanghai";
        try {
            DateTimeFormatter outputFormatter;
            ZonedDateTime fromZonedDateTime;
            if (dateTime.length() == 10) {
                LocalDate localDate = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fromZonedDateTime = localDate.atStartOfDay(ZoneId.of(fromTimeZone));
                outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            } else if (dateTime.length() == 19) {
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                fromZonedDateTime = localDateTime.atZone(ZoneId.of(fromTimeZone));
                outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            } else {
                throw new DateTimeParseException("\u65e0\u6cd5\u8bc6\u522b\u7684\u65e5\u671f\u683c\u5f0f", dateTime, 0);
            }
            ZonedDateTime toZonedDateTime = fromZonedDateTime.withZoneSameInstant(ZoneId.of(toTimeZone));
            return toZonedDateTime.format(outputFormatter);
        }
        catch (DateTimeParseException e) {
            logger.error("\u65e5\u671f\u65f6\u95f4\u89e3\u6790\u9519\u8bef: " + e.getMessage(), e);
            return null;
        }
    }

    public static String convertTimeWithTimeZone(String dateTime) {
        if (!StringUtils.hasText(dateTime)) {
            return dateTime;
        }
        String clientTimeZone = QueryUtils.getClientTimeZone();
        if (!StringUtils.hasText(clientTimeZone)) {
            return dateTime;
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime novosibirskTime = ZonedDateTime.parse(dateTime, inputFormatter.withZone(ZoneId.of("Asia/Shanghai")));
        ZoneId zoneId = ZoneId.of(clientTimeZone);
        ZonedDateTime zonedDateTime = novosibirskTime.withZoneSameInstant(zoneId);
        return zonedDateTime.format(inputFormatter);
    }

    public static String translateTemplateTitle(QueryTemplate template) {
        String title = ((BaseInfoPlugin)template.getPluginByClass(BaseInfoPlugin.class)).getBaseInfo().getTitle();
        if (Boolean.TRUE.equals(VaQueryI18nParam.getTranslationEnabled())) {
            MenuTreeService menuTreeService = DCQuerySpringContextUtils.getBean(MenuTreeService.class);
            VaI18nClient vaI18nClient = DCQuerySpringContextUtils.getBean(VaI18nClient.class);
            MenuTreeVO menuVO = menuTreeService.getMenuVO(template.getId());
            String groupCode = menuVO.getParents().replace("/", "#");
            VaI18nResourceDTO vaI18nDTO = new VaI18nResourceDTO();
            ArrayList<String> prefixFlagList = new ArrayList<String>();
            String prefixFlag = "VA#VaQuery#" + groupCode + "#" + template.getCode();
            prefixFlagList.add(prefixFlag);
            vaI18nDTO.setKey(prefixFlagList);
            List baseTitle = vaI18nClient.queryList(vaI18nDTO);
            if (!CollectionUtils.isEmpty(baseTitle) && StringUtils.hasText((String)baseTitle.get(0))) {
                return (String)baseTitle.get(0);
            }
        }
        return title;
    }

    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> func) {
        if (isJdk8) {
            V v = map.get(key);
            if (v == null) {
                v = map.computeIfAbsent((K)key, func);
            }
            return v;
        }
        return map.computeIfAbsent((K)key, func);
    }

    static {
        try {
            isJdk8 = System.getProperty("java.version").startsWith("1.8.");
        }
        catch (Exception e) {
            isJdk8 = true;
        }
    }
}


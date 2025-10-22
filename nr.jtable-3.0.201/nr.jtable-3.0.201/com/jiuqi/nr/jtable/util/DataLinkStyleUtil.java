/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.AttachObj
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.AttachObj;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataLinkStyleUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataLinkStyleUtil.class);
    private static final String[] zeroShowArray = new String[]{"\u539f\u503c", "\u7a7a\u503c", "\u2014\u2014", "-", "0", "\u96f6"};

    public static String getEnumLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        StringBuilder sb = new StringBuilder();
        EnumDisplayMode displayMode = dataLinkDefine.getDisplayMode();
        if (displayMode == null) {
            displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
        }
        sb.append(displayMode.getValue());
        if (dataLinkDefine.getAllowMultipleSelect() || fieldDefine.getAllowMultipleSelect().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(0);
        if (dataLinkDefine.getAllowNotLeafNodeRefer()) {
            sb.append("0");
        } else {
            sb.append("1");
        }
        sb.append("1");
        sb.append("1");
        sb.append("1");
        if (dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (displayMode != null && displayMode.equals((Object)EnumDisplayMode.DISPLAY_MODE_IN_CELL)) {
            sb.append(dataLinkDefine.getEnumCount());
        }
        return sb.toString();
    }

    public static String getEnumLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        StringBuilder sb = new StringBuilder();
        EnumDisplayMode displayMode = dataLinkDefine.getDisplayMode();
        if (displayMode == null) {
            displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
        }
        sb.append(displayMode.getValue());
        if (dataLinkDefine.getAllowMultipleSelect() || columnModelDefine.isMultival()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(0);
        if (dataLinkDefine.getAllowNotLeafNodeRefer()) {
            sb.append("0");
        } else {
            sb.append("1");
        }
        sb.append("1");
        sb.append("1");
        sb.append("1");
        if (dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (displayMode != null && displayMode.equals((Object)EnumDisplayMode.DISPLAY_MODE_IN_CELL)) {
            sb.append(dataLinkDefine.getEnumCount());
        }
        return sb.toString();
    }

    public static String getStringLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        int precision = fieldDefine.getSize();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(precision);
    }

    public static String getStringLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        int precision = columnModelDefine.getPrecision();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(precision);
    }

    public static String getNumberLinkStyle(NumberFormatParser numberFormatParser, FieldDefine fieldDefine, boolean noFormat, FormDefine formDefine) {
        String currencyMark;
        int decimal;
        int precision;
        StringBuilder sb = new StringBuilder();
        if (noFormat || numberFormatParser.isThousands() != null && numberFormatParser.isThousands().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(numberFormatParser.getNegativeStyle() != null ? numberFormatParser.getNegativeStyle().getValue() : "0");
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (numberFormatParser.isPercentage() != null && numberFormatParser.isPercentage().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        int n = precision = fieldDefine == null ? 0 : fieldDefine.getSize();
        if (precision < 10) {
            sb.append("0");
        }
        sb.append(precision);
        int n2 = decimal = fieldDefine == null ? 0 : fieldDefine.getFractionDigits();
        if (decimal < 10) {
            sb.append("0");
        }
        sb.append(decimal);
        sb.append("0");
        String string = currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        if (StringUtils.isEmpty((String)currencyMark)) {
            sb.append(" ");
        } else {
            sb.append(currencyMark);
        }
        sb.append("0");
        sb.append("1");
        sb.append(",");
        int displayDigits = 0;
        if (noFormat) {
            displayDigits = decimal;
        } else {
            int n3 = displayDigits = numberFormatParser.getDisplayDigits() != null ? numberFormatParser.getDisplayDigits() : decimal;
        }
        if (displayDigits < 10) {
            sb.append("0");
        }
        sb.append(displayDigits);
        sb.append(numberFormatParser.getFixMode() != null ? numberFormatParser.getFixMode().getValue() : "0");
        if (numberFormatParser.isThousandPer()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        String zeroShow = DataLinkStyleUtil.getZeroShow(formDefine);
        sb.append(zeroShow);
        return sb.toString();
    }

    public static String getNumberLinkStyle(NumberFormatParser numberFormatParser, ColumnModelDefine columnModelDefine, boolean noFormat, FormDefine formDefine) {
        String currencyMark;
        StringBuilder sb = new StringBuilder();
        if (noFormat || numberFormatParser.isThousands() != null && numberFormatParser.isThousands().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(numberFormatParser.getNegativeStyle() != null ? numberFormatParser.getNegativeStyle().getValue() : "0");
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (numberFormatParser.isPercentage() != null && numberFormatParser.isPercentage().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        int precision = columnModelDefine.getPrecision();
        if (precision < 10) {
            sb.append("0");
        }
        sb.append(precision);
        int decimal = columnModelDefine.getDecimal();
        if (decimal < 10) {
            sb.append("0");
        }
        sb.append(decimal);
        sb.append("0");
        String string = currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        if (StringUtils.isEmpty((String)currencyMark)) {
            sb.append(" ");
        } else {
            sb.append(currencyMark);
        }
        sb.append("0");
        sb.append("1");
        sb.append(",");
        int displayDigits = 0;
        if (noFormat) {
            displayDigits = decimal;
        } else {
            int n = displayDigits = numberFormatParser.getDisplayDigits() != null ? numberFormatParser.getDisplayDigits() : 0;
        }
        if (displayDigits < 10) {
            sb.append("0");
        }
        sb.append(displayDigits);
        sb.append(numberFormatParser.getFixMode() != null ? numberFormatParser.getFixMode().getValue() : "0");
        if (numberFormatParser.isThousandPer()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        String zeroShow = DataLinkStyleUtil.getZeroShow(formDefine);
        sb.append(zeroShow);
        return sb.toString();
    }

    public static String getFileLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        IRunTimeViewController runtimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        String showFormat = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, fieldDefine);
        try {
            AttachObj attachment = runtimeViewController.getAttachment(dataLinkDefine.getKey());
            if (null != attachment) {
                showFormat = JsonUtil.objectToJson(attachment);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return showFormat;
    }

    public static String getFileLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        IRunTimeViewController runtimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        String showFormat = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, columnModelDefine);
        try {
            AttachObj attachment = runtimeViewController.getAttachment(dataLinkDefine.getKey());
            if (null != attachment) {
                showFormat = JsonUtil.objectToJson(attachment);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return showFormat;
    }

    public static String getDateLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && fieldDefine != null) {
            formatProperties = fieldDefine.getFormatProperties();
        }
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.getPattern();
        }
        return showFormat;
    }

    public static String getDateLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.getPattern();
        }
        return showFormat;
    }

    public static String getOtherLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && fieldDefine != null) {
            formatProperties = fieldDefine.getFormatProperties();
        }
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.toString();
        }
        return showFormat;
    }

    public static String getOtherLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.toString();
        }
        return showFormat;
    }

    public static String getZeroShow(FormDefine formDefine) {
        FormSchemeDefine formScheme;
        IRunTimeViewController runtimeController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        ITaskOptionController systemOptionManager = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String numberZeroShow = systemOptionManager.getValue((formScheme = runtimeController.getFormScheme(formDefine.getFormScheme())).getTaskKey(), "NUMBER_ZERO_SHOW");
        if (StringUtils.isEmpty((String)numberZeroShow)) {
            return "0";
        }
        int indexOf = Arrays.asList(zeroShowArray).indexOf(numberZeroShow);
        if (indexOf != -1) {
            return String.valueOf(indexOf);
        }
        return "0";
    }
}


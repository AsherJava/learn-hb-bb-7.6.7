/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.exception.FieldDataErrorException;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(FloatLinkData.class);
    private String thoundsMark;
    private boolean showThoundMark;
    private boolean bracketNegative;
    private boolean warningNegative;
    private boolean chineseNumber;
    private boolean bigChineseChar;
    private boolean percent;
    private int precision;
    private int fraction;
    private int voidShow;
    private String currencyMark;
    private boolean showCurrencyMark;
    private boolean showPostfixZero;

    public FloatLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine, FormDefine formDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_FLOAT.getValue();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && fieldDefine != null) {
            formatProperties = fieldDefine.getFormatProperties();
        }
        NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)formatProperties);
        this.thoundsMark = ",";
        this.showThoundMark = numberFormatParser.isThousands() != null ? numberFormatParser.isThousands() : false;
        this.bracketNegative = false;
        this.warningNegative = false;
        this.chineseNumber = false;
        this.bigChineseChar = false;
        this.percent = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
        this.precision = fieldDefine.getSize();
        this.fraction = fieldDefine.getFractionDigits();
        this.voidShow = 0;
        this.currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        this.showCurrencyMark = StringUtils.isNotEmpty((String)this.currencyMark);
        this.showPostfixZero = true;
        this.style = DataLinkStyleUtil.getNumberLinkStyle(numberFormatParser, fieldDefine, formatProperties == null, formDefine);
    }

    public FloatLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine, FormDefine formDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_FLOAT.getValue();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)formatProperties);
        this.thoundsMark = ",";
        this.showThoundMark = numberFormatParser.isThousands() != null ? numberFormatParser.isThousands() : false;
        this.bracketNegative = false;
        this.warningNegative = false;
        this.chineseNumber = false;
        this.bigChineseChar = false;
        this.percent = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
        this.precision = columnModelDefine.getPrecision();
        this.fraction = columnModelDefine.getDecimal();
        this.voidShow = 0;
        this.currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        this.showCurrencyMark = StringUtils.isNotEmpty((String)this.currencyMark);
        this.showPostfixZero = true;
        this.style = DataLinkStyleUtil.getNumberLinkStyle(numberFormatParser, columnModelDefine, formatProperties == null, formDefine);
    }

    public String getThoundsMark() {
        return this.thoundsMark;
    }

    public void setThoundsMark(String thoundsMark) {
        this.thoundsMark = thoundsMark;
    }

    public boolean isShowThoundMark() {
        return this.showThoundMark;
    }

    public void setShowThoundMark(boolean showThoundMark) {
        this.showThoundMark = showThoundMark;
    }

    public boolean isBracketNegative() {
        return this.bracketNegative;
    }

    public void setBracketNegative(boolean bracketNegative) {
        this.bracketNegative = bracketNegative;
    }

    public boolean isWarningNegative() {
        return this.warningNegative;
    }

    public void setWarningNegative(boolean warningNegative) {
        this.warningNegative = warningNegative;
    }

    public boolean isChineseNumber() {
        return this.chineseNumber;
    }

    public void setChineseNumber(boolean chineseNumber) {
        this.chineseNumber = chineseNumber;
    }

    public boolean isBigChineseChar() {
        return this.bigChineseChar;
    }

    public void setBigChineseChar(boolean bigChineseChar) {
        this.bigChineseChar = bigChineseChar;
    }

    public boolean isPercent() {
        return this.percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getFraction() {
        return this.fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public int getVoidShow() {
        return this.voidShow;
    }

    public void setVoidShow(int voidShow) {
        this.voidShow = voidShow;
    }

    public String getCurrencyMark() {
        return this.currencyMark;
    }

    public void setCurrencyMark(String currencyMark) {
        this.currencyMark = currencyMark;
    }

    public boolean isShowCurrencyMark() {
        return this.showCurrencyMark;
    }

    public void setShowCurrencyMark(boolean showCurrencyMark) {
        this.showCurrencyMark = showCurrencyMark;
    }

    public boolean isShowPostfixZero() {
        return this.showPostfixZero;
    }

    public void setShowPostfixZero(boolean showPostfixZero) {
        this.showPostfixZero = showPostfixZero;
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        String taskKey;
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        ITaskOptionController systemOptionManager = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String numberZeroShow = systemOptionManager.getValue(taskKey = dataFormaterCache.getJtableContext().getTaskKey(), "NUMBER_ZERO_SHOW");
        if (numberZeroShow == null) {
            numberZeroShow = "";
        }
        try {
            double floatData = data.getAsFloat();
            if (Math.abs(floatData) < 1.0E-10 && !"0".equals(numberZeroShow)) {
                return numberZeroShow;
            }
            return floatData;
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo, boolean matchRound) {
        BigDecimal bigDecimal;
        String taskKey;
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        ITaskOptionController systemOptionManager = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String numberZeroShow = systemOptionManager.getValue(taskKey = dataFormaterCache.getJtableContext().getTaskKey(), "NUMBER_ZERO_SHOW");
        if (numberZeroShow == null) {
            numberZeroShow = "";
        }
        if (numberZeroShow.equals(value.toString())) {
            return 0;
        }
        try {
            String tempValue = value.toString();
            if (tempValue.contains(",")) {
                Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                bigDecimal = new BigDecimal(number.doubleValue());
            } else {
                bigDecimal = new BigDecimal(tempValue);
            }
        }
        catch (Exception e) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u53ea\u80fd\u8f93\u5165\u6d6e\u70b9\u6570\u3002\u9519\u8bef\u503c:" + value.toString());
            return null;
        }
        String bigDecimalStr = bigDecimal.toString();
        if (this.zbvaluetype != FieldValueType.FIELD_VALUE_INPUT_ORDER.getValue()) {
            if (bigDecimalStr.contains(".")) {
                if (this.precision < bigDecimalStr.length() - 1) {
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807" + this.zbtitle + "\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + this.precision + "\uff0c\u6570\u636e\u4e3a" + value.toString());
                    throw new FieldDataErrorException(new String[]{this.zbtitle + "\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6: " + this.precision});
                }
                if (!matchRound) {
                    if (this.fraction < bigDecimalStr.length() - bigDecimalStr.indexOf(".") - 1) {
                        saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                        saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807" + this.zbtitle + "\u6570\u636e\u5c0f\u6570\u90e8\u5206\u8d85\u51fa\u6307\u6807\u5c0f\u6570\u90e8\u5206\u4f4d\u6570:" + this.fraction + "\uff0c\u6570\u636e\u4e3a" + value.toString());
                        throw new FieldDataErrorException(new String[]{this.zbtitle + "\u5c0f\u6570\u90e8\u5206\u8d85\u51fa\u6307\u6807\u5c0f\u6570\u90e8\u5206\u4f4d\u6570: " + this.fraction});
                    }
                } else {
                    bigDecimal = bigDecimal.setScale(this.fraction, 4);
                }
            } else if (this.precision < bigDecimalStr.length()) {
                saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807" + this.zbtitle + "\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + this.precision + "\uff0c\u6570\u636e\u4e3a" + value.toString());
                throw new FieldDataErrorException(new String[]{this.zbtitle + "\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6: " + this.precision});
            }
        }
        return bigDecimal.doubleValue();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
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
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntgeterLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(IntgeterLinkData.class);
    private String thoundsMark;
    private boolean showThoundMark;
    private boolean bracketNegative;
    private boolean warningNegative;
    private boolean chineseNumber;
    private boolean bigChineseChar;
    private boolean percent;
    private boolean thousandPer;
    private int precision;
    private int voidShow;
    private String currencyMark;
    private boolean showCurrencyMark;
    private Integer formatType;

    public IntgeterLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine, FormDefine formDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_INTEGER.getValue();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && fieldDefine != null) {
            formatProperties = fieldDefine.getFormatProperties();
        }
        NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)formatProperties);
        this.formatType = numberFormatParser.getFormatType();
        this.thoundsMark = ",";
        this.showThoundMark = numberFormatParser.isThousands() != null ? numberFormatParser.isThousands() : false;
        this.bracketNegative = false;
        this.warningNegative = false;
        this.chineseNumber = false;
        this.bigChineseChar = false;
        this.percent = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
        this.thousandPer = numberFormatParser.getThousandPer() != null ? numberFormatParser.getThousandPer() : false;
        this.precision = fieldDefine.getSize();
        this.voidShow = 0;
        this.currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        this.showCurrencyMark = StringUtils.isNotEmpty((String)this.currencyMark);
        this.style = DataLinkStyleUtil.getNumberLinkStyle(numberFormatParser, fieldDefine, formatProperties == null, formDefine);
    }

    public IntgeterLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine, FormDefine formDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_INTEGER.getValue();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)formatProperties);
        this.formatType = numberFormatParser.getFormatType();
        this.thoundsMark = ",";
        this.showThoundMark = numberFormatParser.isThousands() != null ? numberFormatParser.isThousands() : false;
        this.bracketNegative = false;
        this.warningNegative = false;
        this.chineseNumber = false;
        this.bigChineseChar = false;
        this.percent = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
        this.thousandPer = numberFormatParser.getThousandPer() != null ? numberFormatParser.getThousandPer() : false;
        this.precision = columnModelDefine.getPrecision();
        this.voidShow = 0;
        this.currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        this.showCurrencyMark = StringUtils.isNotEmpty((String)this.currencyMark);
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

    public boolean isThousandPer() {
        return this.thousandPer;
    }

    public void setThousandPer(boolean thousandPer) {
        this.thousandPer = thousandPer;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
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

    public Integer getFormatType() {
        return this.formatType;
    }

    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
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
            int intValue = data.getAsInt();
            if (intValue == 0 && !"0".equals(numberZeroShow)) {
                return numberZeroShow;
            }
            return intValue;
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo, boolean matchRound) {
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
            if (matchRound) {
                String formateValue = value.toString();
                if (value.toString().contains(".")) {
                    formateValue = value.toString().substring(0, value.toString().lastIndexOf(46));
                }
                int intValue = Integer.parseInt(formateValue);
                return intValue;
            }
            int intValue = Integer.parseInt(value.toString());
            return intValue;
        }
        catch (NumberFormatException e) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            try {
                Long.parseLong(value.toString());
            }
            catch (NumberFormatException e2) {
                saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u53ea\u80fd\u8f93\u5165\u6574\u578b\u3002\u9519\u8bef\u503c:" + value.toString());
                return null;
            }
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u53ea\u80fd\u8f93\u5165\u6574\u578b\u4e14\u6700\u5927\u503c\u4e3a\uff1a" + Integer.MAX_VALUE + "\uff0c\u6700\u5c0f\u503c\u4e3a\uff1a" + Integer.MIN_VALUE + "\u3002\u9519\u8bef\u503c:" + value.toString());
            return null;
        }
    }
}


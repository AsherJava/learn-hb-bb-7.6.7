/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.FormatPropertiesParser
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.FormatPropertiesParser;
import com.jiuqi.nr.datacrud.DataValueBalanceActuatorFactory;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.common.DataTypeConvert;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.dto.FormatPropertiesDTO;
import com.jiuqi.nr.datacrud.impl.format.dto.NumberOptions;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.param.TaskDefineProxy;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class NumberTypeStrategy
implements TypeFormatStrategy {
    protected static final Logger LOGGER = LoggerFactory.getLogger(NumberTypeStrategy.class);
    protected final Map<String, DecimalFormat> patternMap = new HashMap<String, DecimalFormat>();
    protected DimensionCombination currMasterKey;
    protected IRowData currRowData;
    protected IDataAccessProvider dataAccessProvider;
    protected IExecutorContextFactory executorContextFactory;
    protected RegionRelationFactory regionRelationFactory;
    protected IRunTimeViewController runTimeViewController;
    protected MeasureService measureService;
    protected DataValueBalanceActuatorFactory dataValueBalanceActuatorFactory;
    protected IDataValueBalanceActuator balanceActuator;
    protected boolean enableBalanceFormula;
    protected MeasureData selectMeasure;
    protected Map<String, RegionRelation> relationKeyMap = new HashMap<String, RegionRelation>();

    public void setEnableBalanceFormula(boolean enableBalanceFormula) {
        this.enableBalanceFormula = enableBalanceFormula;
    }

    public void setSelectMeasure(Measure selectMeasure) {
        this.selectMeasure = this.measureService.getByMeasure(selectMeasure.getKey(), selectMeasure.getCode());
    }

    public void setDataValueBalanceActuatorFactory(DataValueBalanceActuatorFactory dataValueBalanceActuatorFactory) {
        this.dataValueBalanceActuatorFactory = dataValueBalanceActuatorFactory;
    }

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
    }

    public void setDataAccessProvider(IDataAccessProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setExecutorContextFactory(IExecutorContextFactory executorContextFactory) {
        this.executorContextFactory = executorContextFactory;
    }

    public void setRegionRelationFactory(RegionRelationFactory regionRelationFactory) {
        this.regionRelationFactory = regionRelationFactory;
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    @Override
    public String format(IMetaData metaData, AbstractData abstractData) {
        IFMDMAttribute fmAttribute;
        String balanceExpression = metaData.getBalanceExpression();
        if (this.enableBalanceFormula && this.selectMeasure != null && StringUtils.hasLength(balanceExpression)) {
            if (this.balanceActuator == null) {
                this.balanceActuator = this.dataValueBalanceActuatorFactory.getDataValueBalanceActuator();
                this.balanceActuator.setMeasure(this.selectMeasure);
                this.balanceActuator.setNumDecimalPlaces(this.getNumDecimalPlaces());
            }
            abstractData = this.balanceActuator.balanceValue(this.currRowData, metaData, abstractData);
        }
        if ((fmAttribute = metaData.getFmAttribute()) != null) {
            return this.format(metaData.getDataLinkDefine(), fmAttribute, abstractData);
        }
        return this.format(metaData.getDataLinkDefine(), metaData.getDataField(), abstractData);
    }

    @Override
    public void setRowKey(DimensionCombination masterKey) {
        this.currMasterKey = masterKey;
    }

    @Override
    public void setRowData(IRowData rowData) {
        this.currRowData = rowData;
    }

    @Override
    public String format(DataLinkDefine link, DataField field, AbstractData abstractData) {
        String default0 = this.default0(link, field);
        if (abstractData == null || abstractData.isNull) {
            if (default0 == null) {
                return "";
            }
            return default0;
        }
        NumberOptions options = new NumberOptions();
        DataFieldType dataFieldType = this.getDataFieldType(field, abstractData);
        options.setDataFieldType(dataFieldType);
        if (DataFieldType.INTEGER != dataFieldType && DataFieldType.BIGDECIMAL != dataFieldType) {
            if (default0 == null) {
                return "";
            }
            return default0;
        }
        if (abstractData.dataType == 6) {
            return abstractData.getAsString();
        }
        BigDecimal value = abstractData.getAsCurrency();
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        if (BigDecimal.ZERO.compareTo(value) == 0 && default0 != null) {
            return default0;
        }
        return this.format(link, field, value, options);
    }

    private DataFieldType getDataFieldType(DataField field, AbstractData abstractData) {
        if (field != null) {
            return field.getDataFieldType();
        }
        if (abstractData != null) {
            int dataType = abstractData.dataType;
            return DataTypeConvert.dataType2DataFieldType(dataType);
        }
        return null;
    }

    protected String format(DataLinkDefine link, DataField field, BigDecimal value, NumberOptions options) {
        DecimalFormat numberFormat = this.getDecimalFormat(link, field, value, options);
        FormatPropertiesDTO formatProperties = this.getFormatProperties(link, field);
        NegativeStyle negativeStyle = formatProperties.getNegativeStyle();
        if (formatProperties.getFormatProperties() != null && (formatProperties.getFormatProperties().getFormatType() == 2 || formatProperties.getFormatProperties().getFormatType() == 3)) {
            return numberFormat.format(value);
        }
        if (negativeStyle == NegativeStyle.NS_1) {
            value = value.abs();
        }
        return numberFormat.format(value);
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, AbstractData abstractData) {
        DataField fmDataField = null;
        if (fmAttribute != null) {
            fmDataField = TaskDefineProxy.createDataFieldProxy(fmAttribute);
        }
        return this.format(link, fmDataField, abstractData);
    }

    protected DecimalFormat getDecimalFormat(DataLinkDefine link, DataField field, BigDecimal value, NumberOptions options) {
        FormatPropertiesParser parse;
        String currency;
        FormatPropertiesDTO formatProperties;
        FormatProperties properties;
        String pattern = this.getPattern(link, field, value, options);
        DecimalFormat numberFormat = this.patternMap.get(link.getKey());
        if (numberFormat == null) {
            numberFormat = new DecimalFormat(pattern);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            this.patternMap.put(link.getKey(), numberFormat);
        }
        if ((properties = (formatProperties = this.getFormatProperties(link, field)).getFormatProperties()) != null && (properties.getFormatType() == 2 || properties.getFormatType() == 3) && StringUtils.hasLength(currency = (parse = FormatPropertiesParser.parse((FormatProperties)properties)).getCurrency())) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setCurrencySymbol(currency);
            numberFormat.setDecimalFormatSymbols(symbols);
        }
        return numberFormat;
    }

    protected String default0(DataLinkDefine link, DataField field) {
        FormatProperties properties = null;
        if (link != null) {
            properties = link.getFormatProperties();
        }
        if (properties == null && field != null) {
            properties = field.getFormatProperties();
        }
        if (properties != null && properties.getFormatType() == 3) {
            String currency = FormatPropertiesParser.parse((FormatProperties)properties).getCurrency();
            if (currency != null) {
                return currency + "-";
            }
            return "-";
        }
        return null;
    }

    protected String getPattern(DataLinkDefine link, DataField field, BigDecimal value, NumberOptions options) {
        FormatPropertiesParser parse;
        String currency;
        String pattern;
        FormatPropertiesDTO formatProperties = this.getFormatProperties(link, field);
        FormatProperties properties = formatProperties.getFormatProperties();
        NegativeStyle negativeStyle = formatProperties.getNegativeStyle();
        if (DataFieldType.INTEGER == options.getDataFieldType()) {
            pattern = "#,##0";
            if (properties != null) {
                pattern = properties.getPattern();
            }
        } else if (properties != null) {
            pattern = properties.getPattern();
        } else {
            Integer numDecimalPlaces = this.getNumDecimalPlaces(link, field, value);
            pattern = this.buildPatternByNumDecimalPlaces(numDecimalPlaces);
        }
        if (properties != null && StringUtils.hasLength(currency = (parse = FormatPropertiesParser.parse((FormatProperties)properties)).getCurrency())) {
            if (properties.getFormatType() == 2) {
                pattern = pattern.replace(currency, "");
                if (String.valueOf(FormatPropertiesParser.CURRENCY_3).equals(currency)) {
                    if (negativeStyle == NegativeStyle.NS_1) {
                        return pattern + "\u00a4;(" + pattern + "\u00a4)";
                    }
                    return pattern + "\u00a4;-" + pattern + "\u00a4";
                }
                if (negativeStyle == NegativeStyle.NS_1) {
                    return "\u00a4" + pattern + ";(\u00a4" + pattern + ")";
                }
                if (String.valueOf(FormatPropertiesParser.CURRENCY_1).equals(currency)) {
                    return "\u00a4" + pattern + ";\u00a4-" + pattern;
                }
                return "\u00a4" + pattern + ";-\u00a4" + pattern;
            }
            if (properties.getFormatType() == 3) {
                pattern = pattern.replace(currency, "");
                if (String.valueOf(FormatPropertiesParser.CURRENCY_1).equals(currency)) {
                    return "\u00a4" + pattern + ";\u00a4-" + pattern;
                }
                if (String.valueOf(FormatPropertiesParser.CURRENCY_2).equals(currency)) {
                    return "\u00a4" + pattern + ";\u00a4-" + pattern;
                }
                return pattern + "\u00a4;-" + pattern + "\u00a4";
            }
        }
        if (value.signum() < 0 && negativeStyle == NegativeStyle.NS_1) {
            return "(" + pattern + ")";
        }
        return pattern;
    }

    protected FormatPropertiesDTO getFormatProperties(DataLinkDefine link, DataField field) {
        FormatProperties properties = link.getFormatProperties();
        if (properties == null && field != null) {
            properties = field.getFormatProperties();
        }
        NegativeStyle negativeStyle = this.getNegativeStyle(properties);
        FormatPropertiesDTO formatPropertiesDTO = new FormatPropertiesDTO();
        formatPropertiesDTO.setFormatProperties(properties);
        formatPropertiesDTO.setNegativeStyle(negativeStyle);
        return formatPropertiesDTO;
    }

    protected NegativeStyle getNegativeStyle(FormatProperties properties) {
        String negativeStyleSet;
        Map propertiesMap;
        NegativeStyle negativeStyle = NegativeStyle.NS_0;
        if (properties != null && (propertiesMap = properties.getProperties()) != null && (negativeStyleSet = (String)propertiesMap.get("negativeStyle")) != null) {
            negativeStyle = NegativeStyle.getByValue((String)negativeStyleSet);
        }
        return negativeStyle;
    }

    protected Integer getNumDecimalPlaces(DataLinkDefine link, DataField field, BigDecimal value) {
        if (field != null) {
            return field.getDecimal();
        }
        if (value != null) {
            return value.scale();
        }
        return 2;
    }

    public Integer getNumDecimalPlaces() {
        return null;
    }

    protected String buildPatternByNumDecimalPlaces(Integer numDecimalPlaces) {
        StringBuilder patternBuilder = new StringBuilder("#,##0");
        if (numDecimalPlaces != null && numDecimalPlaces > 0) {
            patternBuilder.append(".");
            for (int i = 0; i < numDecimalPlaces; ++i) {
                patternBuilder.append("0");
            }
        }
        return patternBuilder.toString();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.BudI18nUtil
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.common.utils.BudI18nUtil;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import com.jiuqi.budget.dataperiod.format.DataPeriodFormatVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DataPeriodFormatCollector {
    private final Map<IDataPeriodType, List<DataPeriodFormatVO>> map = new HashMap<IDataPeriodType, List<DataPeriodFormatVO>>();
    private final Map<IDataPeriodType, BaseDataPeriodFormat> defaultMap;
    private final Map<String, BaseDataPeriodFormat> formatBeanMap;

    public DataPeriodFormatCollector(List<BaseDataPeriodFormat> dataPeriodFormats) {
        HashMap<DataPeriodType, BaseDataPeriodFormat> defaultMap = new HashMap<DataPeriodType, BaseDataPeriodFormat>();
        HashMap<String, BaseDataPeriodFormat> formatBeanMap = new HashMap<String, BaseDataPeriodFormat>();
        for (BaseDataPeriodFormat dataPeriodFormat : dataPeriodFormats) {
            DataPeriodType dataPeriodType = dataPeriodFormat.adaptType();
            List list = this.map.computeIfAbsent(dataPeriodType, k -> new ArrayList());
            DataPeriodFormatVO formatVO = new DataPeriodFormatVO();
            formatVO.setCode(dataPeriodFormat.getName());
            formatVO.setName(dataPeriodFormat.getTitle());
            list.add(formatVO);
            if (dataPeriodFormat.isDefault()) {
                defaultMap.put(dataPeriodType, dataPeriodFormat);
            }
            formatBeanMap.put(dataPeriodFormat.getName(), dataPeriodFormat);
        }
        this.defaultMap = Collections.unmodifiableMap(defaultMap);
        this.formatBeanMap = Collections.unmodifiableMap(formatBeanMap);
    }

    public Map<IDataPeriodType, List<DataPeriodFormatVO>> getMap() {
        return this.map;
    }

    public Map<IDataPeriodType, BaseDataPeriodFormat> getDefaultMap() {
        return this.defaultMap;
    }

    public String useDefaultFormat(DataPeriod dataPeriod) {
        BaseDataPeriodFormat baseDataPeriodFormat = this.defaultMap.get(dataPeriod.getType());
        if (baseDataPeriodFormat == null) {
            return dataPeriod.getTitle();
        }
        return baseDataPeriodFormat.format(BudI18nUtil.getLocale(), dataPeriod);
    }

    public String useDefaultFormat(Locale locale, DataPeriod dataPeriod) {
        BaseDataPeriodFormat baseDataPeriodFormat = this.defaultMap.get(dataPeriod.getType());
        if (baseDataPeriodFormat == null) {
            return dataPeriod.getTitle();
        }
        return baseDataPeriodFormat.format(locale == null ? BudI18nUtil.getLocale() : locale, dataPeriod);
    }

    public Map<String, BaseDataPeriodFormat> getFormatBeanMap() {
        return this.formatBeanMap;
    }
}


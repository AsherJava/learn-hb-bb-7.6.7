/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.datacrud.impl.measure;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MeasureServiceImpl
implements MeasureService {
    @Override
    public MeasureView getByMeasure(String measure) {
        if (!StringUtils.hasLength(measure)) {
            measure = "9493b4eb-6516-48a8-a878-25a63a23e63a";
        }
        MeasureView measureView = new MeasureView();
        measureView.setKey(measure);
        measureView.setCode("AMOUNT");
        measureView.setName("AMOUNT");
        measureView.setTitle("\u91d1\u989d");
        return measureView;
    }

    @Override
    public MeasureData getByMeasure(String measure, String code) {
        if (!StringUtils.hasLength(measure) || !StringUtils.hasLength(code)) {
            code = "YUAN";
        }
        MeasureData measureData = new MeasureData();
        measureData.setCode(code);
        boolean zh = this.getLanguage().equals("zh");
        switch (code) {
            case "BAIYUAN": {
                if (!zh) {
                    measureData.setTitle("Hundred");
                } else {
                    measureData.setTitle("\u767e\u5143");
                }
                measureData.setRateValue(BigDecimal.valueOf(100L));
                break;
            }
            case "QIANYUAN": {
                if (!zh) {
                    measureData.setTitle("Thousand");
                } else {
                    measureData.setTitle("\u5343\u5143");
                }
                measureData.setRateValue(BigDecimal.valueOf(1000L));
                break;
            }
            case "WANYUAN": {
                if (!zh) {
                    measureData.setTitle("Ten Thousand");
                } else {
                    measureData.setTitle("\u4e07\u5143");
                }
                measureData.setRateValue(BigDecimal.valueOf(10000L));
                break;
            }
            case "BAIWAN": {
                if (!zh) {
                    measureData.setTitle("Million");
                } else {
                    measureData.setTitle("\u767e\u4e07");
                }
                measureData.setRateValue(BigDecimal.valueOf(1000000L));
                break;
            }
            case "QIANWAN": {
                if (!zh) {
                    measureData.setTitle("Ten Million");
                } else {
                    measureData.setTitle("\u5343\u4e07");
                }
                measureData.setRateValue(BigDecimal.valueOf(10000000L));
                break;
            }
            case "YIYUAN": {
                if (!zh) {
                    measureData.setTitle("Hundred Million");
                } else {
                    measureData.setTitle("\u4ebf\u5143");
                }
                measureData.setRateValue(BigDecimal.valueOf(100000000L));
                break;
            }
            case "WANYI": {
                if (!zh) {
                    measureData.setTitle("Trillion Million");
                } else {
                    measureData.setTitle("\u4e07\u4ebf");
                }
                measureData.setRateValue(BigDecimal.valueOf(1000000000000L));
                break;
            }
            default: {
                if (!zh) {
                    measureData.setTitle("Yuan");
                } else {
                    measureData.setTitle("\u5143");
                }
                measureData.setBase(true);
                measureData.setCode("YUAN");
                measureData.setRateValue(BigDecimal.ONE);
            }
        }
        return measureData;
    }

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.isEmpty()) {
            return "zh";
        }
        return language;
    }
}


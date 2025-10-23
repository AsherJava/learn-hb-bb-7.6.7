/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 */
package com.jiuqi.nr.datascheme.i18n.aop;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.i18n.IDataSchemeI18nService;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeBasicI18n;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@ConditionalOnProperty(value={"paramlanguage.open"}, havingValue="true")
@Component
public class RuntimeDataSchemeServiceAspect {
    @Autowired
    private IDataSchemeI18nService dataSchemeI18nService;
    private final Logger logger = LoggerFactory.getLogger(RuntimeDataSchemeServiceAspect.class);

    @Around(value="execution(com.jiuqi.nr.datascheme.api.DataField com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService.get*(..))")
    public DataField afterGetDataField(ProceedingJoinPoint joinPoint) {
        try {
            DataField dataField = (DataField)joinPoint.proceed();
            if (LanguageType.enableMultiLanguage() && !LanguageType.getCurrentLanguageType().isDefault() && null != dataField) {
                DataFieldDTO dataFieldDTO = DataFieldDTO.valueOf(dataField);
                String type = LanguageType.getCurrentLanguageType().getKey();
                DataSchemeI18nDO i18n = this.dataSchemeI18nService.getByFieldKey(dataField.getDataSchemeKey(), dataField.getKey(), type);
                this.copyProperties(dataFieldDTO, i18n);
                return dataFieldDTO;
            }
            return dataField;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void copyProperties(DataFieldDTO dataField, DataSchemeBasicI18n i18n) {
        if (null != i18n && (StringUtils.hasText(i18n.getTitle()) || StringUtils.hasText(i18n.getDesc()))) {
            this.logger.debug("\u6570\u636e\u65b9\u6848\u591a\u8bed\u8a00\u670d\u52a1\uff1a\u4fee\u6539\u6307\u6807\\\u5b57\u6bb5{}[{}]\u4fe1\u606f\u3002", (Object)dataField.getTitle(), (Object)dataField.getCode());
            if (StringUtils.hasText(i18n.getTitle())) {
                dataField.setTitle(i18n.getTitle());
            }
            if (StringUtils.hasText(i18n.getDesc())) {
                dataField.setDesc(i18n.getDesc());
            }
        }
    }

    @Around(value="execution(java.util.List<com.jiuqi.nr.datascheme.api.DataField> com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService.get*(..))")
    public List<DataField> afterGetDataFields(ProceedingJoinPoint joinPoint) {
        try {
            List dataFields = (List)joinPoint.proceed();
            if (LanguageType.enableMultiLanguage() && !LanguageType.getCurrentLanguageType().isDefault() && null != dataFields && !dataFields.isEmpty()) {
                ArrayList<DataField> dataFieldList = new ArrayList<DataField>(dataFields.size());
                String type = LanguageType.getCurrentLanguageType().getKey();
                for (DataField dataField : dataFields) {
                    DataFieldDTO dataFieldDTO = DataFieldDTO.valueOf(dataField);
                    DataSchemeI18nDO i18n = this.dataSchemeI18nService.getByFieldKey(dataField.getDataSchemeKey(), dataField.getKey(), type);
                    this.copyProperties(dataFieldDTO, i18n);
                    dataFieldList.add(dataFieldDTO);
                }
                return dataFieldList;
            }
            return dataFields;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}


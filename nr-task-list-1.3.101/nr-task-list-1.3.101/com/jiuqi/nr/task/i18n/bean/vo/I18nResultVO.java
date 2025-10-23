/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.task.i18n.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nVO;
import com.jiuqi.nr.task.i18n.serializer.I18nResultDeserializer;
import java.util.List;

@JsonDeserialize(using=I18nResultDeserializer.class)
public class I18nResultVO
extends I18nVO {
    private List<I18nColsObj> i18nCols;
    private List<? extends I18nBaseDTO> datas;

    public I18nResultVO() {
    }

    public I18nResultVO(Integer languageType, Integer resourceType) {
        super.setLanguageType(String.valueOf(languageType));
        super.setResourceType(String.valueOf(resourceType));
    }

    public I18nResultVO(List<I18nColsObj> i18nCols, List<? extends I18nBaseDTO> datas) {
        this.i18nCols = i18nCols;
        this.datas = datas;
    }

    public List<I18nColsObj> getI18nCols() {
        return this.i18nCols;
    }

    public void setI18nCols(List<I18nColsObj> i18nCols) {
        this.i18nCols = i18nCols;
    }

    public List<? extends I18nBaseDTO> getDatas() {
        return this.datas;
    }

    public void setDatas(List<? extends I18nBaseDTO> datas) {
        this.datas = datas;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO
 *  com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO
 */
package com.jiuqi.nr.param.transfer.datascheme.dto;

import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;

public class DataFieldLanguageDTO {
    private String key;
    private String type;
    private String title;
    private String desc;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static DataFieldLanguageDTO valueOf(DesignDataSchemeI18nDTO designDataSchemeI18nDTO) {
        DataFieldLanguageDTO dataFieldLanguageDTO = new DataFieldLanguageDTO();
        dataFieldLanguageDTO.setKey(designDataSchemeI18nDTO.getKey());
        dataFieldLanguageDTO.setType(designDataSchemeI18nDTO.getType());
        dataFieldLanguageDTO.setDesc(designDataSchemeI18nDTO.getDesc());
        dataFieldLanguageDTO.setTitle(designDataSchemeI18nDTO.getTitle());
        return dataFieldLanguageDTO;
    }

    public static DataFieldLanguageDTO valueOf(DesignDataSchemeI18nDO designDataSchemeI18nDTO) {
        DataFieldLanguageDTO dataFieldLanguageDTO = new DataFieldLanguageDTO();
        dataFieldLanguageDTO.setKey(designDataSchemeI18nDTO.getKey());
        dataFieldLanguageDTO.setType(designDataSchemeI18nDTO.getType());
        dataFieldLanguageDTO.setDesc(designDataSchemeI18nDTO.getDesc());
        dataFieldLanguageDTO.setTitle(designDataSchemeI18nDTO.getTitle());
        return dataFieldLanguageDTO;
    }

    public void value2Define(DesignDataSchemeI18nDO designDataSchemeI18nDO) {
        designDataSchemeI18nDO.setKey(this.getKey());
        designDataSchemeI18nDO.setType(this.getType());
        designDataSchemeI18nDO.setDesc(this.getDesc());
        designDataSchemeI18nDO.setTitle(this.getTitle());
    }
}


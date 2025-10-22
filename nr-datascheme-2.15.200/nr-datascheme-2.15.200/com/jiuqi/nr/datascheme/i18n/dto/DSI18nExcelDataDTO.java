/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.dto;

import com.jiuqi.nr.datascheme.common.io.SheetUtils;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataFieldInfoDTO;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class DSI18nExcelDataDTO
implements SheetUtils.IRowData<DSI18nExcelDataDTO> {
    private String tableCode;
    private String fieldCode;
    private String fieldTitle;
    private String fieldDesc;
    private List<ILanguageType> types;
    private Map<String, String> titleMap;
    private Map<String, String> descMap;

    public DSI18nExcelDataDTO(List<ILanguageType> types) {
        this.types = types;
        this.titleMap = new HashMap<String, String>();
        this.descMap = new HashMap<String, String>();
    }

    public DSI18nExcelDataDTO(List<ILanguageType> types, DesignDataSchemeI18nDTO i18n) {
        this.types = types;
        this.titleMap = new HashMap<String, String>();
        this.descMap = new HashMap<String, String>();
        this.tableCode = i18n.getTableCode();
        this.fieldCode = i18n.getFieldCode();
        this.fieldTitle = i18n.getFieldTitle();
        this.fieldDesc = i18n.getFieldDesc();
        this.add(i18n);
    }

    public void add(DesignDataSchemeI18nDTO i18n) {
        if (StringUtils.hasLength(i18n.getType())) {
            this.titleMap.put(i18n.getType(), i18n.getTitle());
            this.descMap.put(i18n.getType(), i18n.getDesc());
        }
    }

    public String getIndex() {
        return this.tableCode + this.fieldCode;
    }

    public static String getIndex(DesignDataSchemeI18nDTO dto) {
        return dto.getTableCode() + dto.getFieldCode();
    }

    public static String getIndex(DesignDataFieldInfoDTO dto) {
        return dto.getTableCode() + dto.getFieldCode();
    }

    public List<DesignDataSchemeI18nDO> getDOs(String schemeKey, Map<String, String> fieldKeyMap) {
        ArrayList<DesignDataSchemeI18nDO> result = new ArrayList<DesignDataSchemeI18nDO>();
        if (fieldKeyMap.containsKey(this.getIndex())) {
            DesignDataSchemeI18nDO i18n = null;
            String fieldKey = fieldKeyMap.get(this.getIndex());
            for (ILanguageType iLanguageType : this.types) {
                String tStr = this.titleMap.get(iLanguageType.getKey());
                String dStr = this.descMap.get(iLanguageType.getKey());
                if (!StringUtils.hasText(tStr) && !StringUtils.hasText(dStr)) continue;
                i18n = new DesignDataSchemeI18nDO();
                i18n.setKey(fieldKey);
                i18n.setType(iLanguageType.getKey());
                i18n.setDataSchemeKey(schemeKey);
                i18n.setTitle(tStr);
                i18n.setDesc(dStr);
                result.add(i18n);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Object> read() {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 0;
        map.put(i++, this.tableCode);
        map.put(i++, this.fieldCode);
        map.put(i++, this.fieldTitle);
        map.put(i++, this.fieldDesc);
        for (ILanguageType iLanguageType : this.types) {
            if (this.titleMap.containsKey(iLanguageType.getKey())) {
                map.put(i++, this.titleMap.get(iLanguageType.getKey()));
            } else {
                map.put(i++, null);
            }
            if (this.descMap.containsKey(iLanguageType.getKey())) {
                map.put(i++, this.descMap.get(iLanguageType.getKey()));
                continue;
            }
            map.put(i++, null);
        }
        return map;
    }

    @Override
    public DSI18nExcelDataDTO write(Map<Integer, Object> data) {
        this.titleMap = new HashMap<String, String>();
        this.descMap = new HashMap<String, String>();
        int i = 0;
        this.tableCode = String.valueOf(this.getDataStr(data, i++));
        this.fieldCode = String.valueOf(this.getDataStr(data, i++));
        this.fieldTitle = String.valueOf(this.getDataStr(data, i++));
        this.fieldDesc = String.valueOf(this.getDataStr(data, i++));
        for (ILanguageType iLanguageType : this.types) {
            this.titleMap.put(iLanguageType.getKey(), String.valueOf(this.getDataStr(data, i++)));
            this.descMap.put(iLanguageType.getKey(), String.valueOf(this.getDataStr(data, i++)));
        }
        return this;
    }

    private String getDataStr(Map<Integer, Object> data, int key) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            return null == value ? "" : String.valueOf(value);
        }
        return "";
    }
}


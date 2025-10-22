/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface DataLinkDefine
extends IMetaItem {
    public String getRegionKey();

    public String getLinkExpression();

    @Deprecated
    public String getBindingExpression();

    public int getPosX();

    public int getPosY();

    public int getColNum();

    public int getRowNum();

    public DataLinkEditMode getEditMode();

    public EnumDisplayMode getDisplayMode();

    public List<String> getDataValidation();

    public String getCaptionFieldsString();

    public String getDropDownFieldsString();

    public Boolean getAllowUndefinedCode();

    public Boolean getAllowNullAble();

    public boolean getAllowMultipleSelect();

    public boolean getAllowNotLeafNodeRefer();

    public FormatProperties getFormatProperties();

    public String getUniqueCode();

    public String getEnumShowFullPath();

    public String getEnumTitleField();

    public DataLinkType getType();

    public String getEnumLinkage();

    default public String getEnumLinkageMethod() {
        String enumLinkage = this.getEnumLinkage();
        List list = JacksonUtils.toList((String)enumLinkage, HashMap.class);
        String method = null;
        if (null != list) {
            for (int i = 0; i < list.size(); ++i) {
                if (((HashMap)list.get(i)).get("method") == null) continue;
                method = (String)((HashMap)list.get(i)).get("method");
                break;
            }
        }
        return method;
    }

    default public Map<String, String> getEnumLinkageData() {
        HashMap<String, String> EnumLinkageData = new HashMap<String, String>();
        String enumLinkage = this.getEnumLinkage();
        List list = JacksonUtils.toList((String)enumLinkage, HashMap.class);
        if (null != list) {
            for (int i = 0; i < list.size(); ++i) {
                if (((HashMap)list.get(i)).get("method") != null) continue;
                HashMap map = (HashMap)list.get(i);
                for (Map.Entry entry : map.entrySet()) {
                    String mapKey = (String)entry.getKey();
                    String mapValue = (String)entry.getValue();
                    EnumLinkageData.put(mapKey, mapValue);
                }
            }
        }
        return EnumLinkageData;
    }

    public int getEnumCount();

    public String getEnumPos();

    default public Map<String, Object> getEnumPosMap() {
        Map hashMap = null;
        if (StringUtils.hasText(this.getEnumPos())) {
            hashMap = JacksonUtils.toMap((String)this.getEnumPos(), Object.class);
        }
        return hashMap;
    }

    public boolean getEnumLinkageStatus();

    public String getFilterExpression();

    public boolean isIgnorePermissions();

    public String getFilterTemplate();

    public String getMeasureUnit();
}


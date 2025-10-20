/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.front.FrontDataDefine
 *  com.jiuqi.va.biz.front.FrontDataTableDefine
 *  com.jiuqi.va.biz.front.FrontPluginDefine
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontDataTableDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataPluginI18n
implements I18nPlugin {
    private static final String I18NKEY_DEFINE_DATA = "&define#data&plugin#";
    private static final String I18NKEY_TABLE = "&table";
    private static final String I18NKEY_DATAMODEL = "VA#datamodel#";

    public String getName() {
        return "data";
    }

    public boolean isGroup() {
        return true;
    }

    public boolean isFrontTrans() {
        return true;
    }

    public boolean isBackEndTrans() {
        return true;
    }

    public List<VaI18nResourceItem> getI18nResource(PluginDefine pluginDefine, ModelDefine modelDefine, String parentId, String requestResourceType) {
        ArrayList<VaI18nResourceItem> pluginResourceList;
        block4: {
            List tableDefineList;
            block3: {
                pluginResourceList = new ArrayList<VaI18nResourceItem>();
                DataDefineImpl DataPluginDefine = (DataDefineImpl)pluginDefine;
                tableDefineList = DataPluginDefine.getTableList();
                if (tableDefineList == null || tableDefineList.isEmpty()) {
                    return pluginResourceList;
                }
                if (!parentId.endsWith("&plugin")) break block3;
                for (DataTableDefineImpl tableDefine : tableDefineList) {
                    VaI18nResourceItem tableItem = new VaI18nResourceItem();
                    tableItem.setName(tableDefine.getName() + I18NKEY_TABLE);
                    tableItem.setTitle(tableDefine.getOriginalTitle());
                    tableItem.setUniqueName(modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + I18NKEY_TABLE);
                    pluginResourceList.add(tableItem);
                }
                break block4;
            }
            if (!parentId.endsWith(I18NKEY_TABLE)) break block4;
            String[] parents = parentId.split("#");
            String tableName = parents[parents.length - 1].split("&")[0];
            for (DataTableDefineImpl tableDefine : tableDefineList) {
                if (!tableDefine.getName().equals(tableName)) continue;
                tableDefine.getFields().stream().forEach(field -> {
                    if (!DataFieldType.DATA.equals((Object)field.getFieldType())) {
                        return;
                    }
                    VaI18nResourceItem fieldItem = new VaI18nResourceItem();
                    fieldItem.setName(field.getFieldName());
                    fieldItem.setTitle(field.getOriginalTitle());
                    fieldItem.setUniqueName(modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + "&table#" + field.getFieldName());
                    pluginResourceList.add(fieldItem);
                });
            }
        }
        return pluginResourceList;
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return DataDefineImpl.class;
    }

    public String getTitle() {
        return "\u6570\u636e";
    }

    public void processForI18n(PluginDefine pluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
        DataDefineImpl DataPluginDefine = (DataDefineImpl)pluginDefine;
        List tableDefineList = DataPluginDefine.getTableList();
        if (tableDefineList == null || tableDefineList.isEmpty()) {
            return;
        }
        for (DataTableDefineImpl tableDefine : tableDefineList) {
            String tableTitleKey = modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + I18NKEY_TABLE;
            String tableTitleI18nValue = i18nValueMap.get(tableTitleKey);
            if (StringUtils.hasText(tableTitleI18nValue)) {
                tableDefine.getTitleI18nMap().put(LocaleContextHolder.getLocale().toLanguageTag(), tableTitleI18nValue);
            } else {
                String datamodelTableTitleI18nValue = i18nValueMap.get(I18NKEY_DATAMODEL + tableDefine.getTableName());
                if (StringUtils.hasText(datamodelTableTitleI18nValue)) {
                    tableDefine.getTitleI18nMap().put(LocaleContextHolder.getLocale().toLanguageTag(), datamodelTableTitleI18nValue);
                }
            }
            tableDefine.getFields().stream().forEach(field -> {
                if (!DataFieldType.DATA.equals((Object)field.getFieldType())) {
                    return;
                }
                String fieldTitleKey = modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + "&table#" + field.getFieldName();
                String fieldTitleI18nValue = (String)i18nValueMap.get(fieldTitleKey);
                if (StringUtils.hasText(fieldTitleI18nValue)) {
                    field.getTitleI18nMap().put(LocaleContextHolder.getLocale().toLanguageTag(), fieldTitleI18nValue);
                } else {
                    String datamodelFieldTitleI18nValue = (String)i18nValueMap.get(I18NKEY_DATAMODEL + tableDefine.getTableName() + "#" + field.getFieldName());
                    if (StringUtils.hasText(datamodelFieldTitleI18nValue)) {
                        field.getTitleI18nMap().put(LocaleContextHolder.getLocale().toLanguageTag(), datamodelFieldTitleI18nValue);
                    }
                }
            });
        }
    }

    public List<String> getAllI18nKeys(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ArrayList<String> keys = new ArrayList<String>();
        DataDefineImpl dataPluginDefine = (DataDefineImpl)pluginDefine;
        List tableDefineList = dataPluginDefine.getTableList();
        if (tableDefineList == null || tableDefineList.isEmpty()) {
            return keys;
        }
        for (DataTableDefineImpl tableDefine : tableDefineList) {
            keys.add(modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + I18NKEY_TABLE);
            keys.add(I18NKEY_DATAMODEL + tableDefine.getName());
            tableDefine.getFields().stream().forEach(field -> {
                if (!DataFieldType.DATA.equals((Object)field.getFieldType())) {
                    return;
                }
                keys.add(modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + "&table#" + field.getFieldName());
                keys.add(I18NKEY_DATAMODEL + tableDefine.getName() + "#" + field.getFieldName());
            });
        }
        return keys;
    }

    public void processForI18n(FrontPluginDefine frontPluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
        if (frontPluginDefine instanceof FrontDataDefine) {
            FrontDataDefine frontDataDefine = (FrontDataDefine)frontPluginDefine;
            List tableDefineList = frontDataDefine.getTableList();
            if (tableDefineList == null || tableDefineList.isEmpty()) {
                return;
            }
            for (FrontDataTableDefine tableDefine : tableDefineList) {
                String tableTitleKey = modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + I18NKEY_TABLE;
                String tableTitleI18nValue = i18nValueMap.get(tableTitleKey);
                if (StringUtils.hasText(tableTitleI18nValue)) {
                    tableDefine.setTitle(tableTitleI18nValue);
                } else {
                    String datamodelTableTitleI18nValue = i18nValueMap.get(I18NKEY_DATAMODEL + tableDefine.getTableName());
                    if (StringUtils.hasText(datamodelTableTitleI18nValue)) {
                        tableDefine.setTitle(datamodelTableTitleI18nValue);
                    }
                }
                tableDefine.getFields().stream().forEach(field -> {
                    if (!DataFieldType.DATA.equals((Object)field.getFieldType())) {
                        return;
                    }
                    String fieldTitleKey = modelDefine.getName() + I18NKEY_DEFINE_DATA + tableDefine.getName() + "&table#" + field.getFieldName();
                    String fieldTitleI18nValue = (String)i18nValueMap.get(fieldTitleKey);
                    if (StringUtils.hasText(fieldTitleI18nValue)) {
                        field.setTitle(fieldTitleI18nValue);
                    } else {
                        String datamodelFieldTitleI18nValue = (String)i18nValueMap.get(I18NKEY_DATAMODEL + tableDefine.getTableName() + "#" + field.getFieldName());
                        if (StringUtils.hasText(datamodelFieldTitleI18nValue)) {
                            field.setTitle(datamodelFieldTitleI18nValue);
                        }
                    }
                });
            }
        }
    }
}


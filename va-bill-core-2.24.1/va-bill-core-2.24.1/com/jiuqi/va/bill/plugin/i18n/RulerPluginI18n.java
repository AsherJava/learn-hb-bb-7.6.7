/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.front.FrontPluginDefine
 *  com.jiuqi.va.biz.front.FrontRulerDefine
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontRulerDefine;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class RulerPluginI18n
implements I18nPlugin {
    public String getName() {
        return "ruler";
    }

    public String getTitle() {
        return "\u89c4\u5219";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return RulerDefineImpl.class;
    }

    public boolean isBackEndTrans() {
        return true;
    }

    public boolean isFrontTrans() {
        return true;
    }

    public List<VaI18nResourceItem> getI18nResource(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        List formulas = rulerDefine.getFormulaList();
        if (formulas == null || formulas.isEmpty()) {
            return pluginResourceList;
        }
        for (FormulaImpl o : formulas) {
            if (!this.isValidFormula(o) || !StringUtils.hasText(o.getCheckMessage())) continue;
            VaI18nResourceItem controlItem = new VaI18nResourceItem();
            controlItem.setName(o.getName());
            controlItem.setUniqueName("VA#bill#" + modelDefine.getName() + "#formula#" + o.getName());
            controlItem.setTitle(o.getTitle());
            pluginResourceList.add(controlItem);
        }
        return pluginResourceList;
    }

    private boolean isValidFormula(FormulaImpl o) {
        FormulaType formulaType = o.getFormulaType();
        String objectType = o.getObjectType();
        String propertyType = o.getPropertyType();
        return FormulaType.WARN.equals((Object)formulaType) || FormulaType.CHECK.equals((Object)formulaType) || ("field".equals(objectType) || "table".equals(objectType)) && "required".equals(propertyType);
    }

    public List<String> getAllI18nKeys(PluginDefine pluginDefine, ModelDefine modelDefine) {
        ArrayList<String> keys = new ArrayList<String>();
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        List formulas = rulerDefine.getFormulaList();
        if (formulas == null || formulas.isEmpty()) {
            return keys;
        }
        for (FormulaImpl o : formulas) {
            if (!this.isValidFormula(o) || !StringUtils.hasText(o.getCheckMessage())) continue;
            keys.add("VA#bill#" + modelDefine.getName() + "#formula#" + o.getName());
        }
        return keys;
    }

    public void processForI18n(PluginDefine pluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
        RulerDefineImpl rulerDefine = (RulerDefineImpl)pluginDefine;
        List formulas = rulerDefine.getFormulaList();
        if (formulas == null || formulas.isEmpty()) {
            return;
        }
        for (FormulaImpl o : formulas) {
            String key;
            String i18nValue;
            if (!this.isValidFormula(o) || !StringUtils.hasText(o.getCheckMessage()) || !StringUtils.hasText(i18nValue = i18nValueMap.get(key = "VA#bill#" + modelDefine.getName() + "#formula#" + o.getName()))) continue;
            o.getMessageI18nMap().put(LocaleContextHolder.getLocale().toLanguageTag(), i18nValue);
        }
    }

    public void processForI18n(FrontPluginDefine frontPluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
        FrontRulerDefine rulerDefine = (FrontRulerDefine)frontPluginDefine;
        if (rulerDefine == null) {
            return;
        }
        Map props = rulerDefine.getProps();
        if (CollectionUtils.isEmpty(props)) {
            return;
        }
        ArrayList checkList = new ArrayList();
        for (Map.Entry entry : props.entrySet()) {
            Map valueMap = (Map)entry.getValue();
            if (CollectionUtils.isEmpty(valueMap)) continue;
            boolean bl = false;
            for (Map.Entry valueEntry : valueMap.entrySet()) {
                if (!"inputCheck".equals(valueEntry.getKey())) continue;
                bl = true;
            }
            if (!bl) continue;
            checkList.add(entry);
        }
        if (CollectionUtils.isEmpty(checkList)) {
            return;
        }
        RulerDefineImpl ruler = (RulerDefineImpl)modelDefine.getPlugins().get("ruler");
        Map<UUID, String> collect = ruler.getFormulas().stream().filter(o -> "inputCheck".equals(o.getPropertyType())).collect(Collectors.toMap(FormulaImpl::getObjectId, FormulaImpl::getCheckMessage));
        for (Map.Entry entry : checkList) {
            UUID key = (UUID)entry.getKey();
            String s = collect.get(key);
            if (!StringUtils.hasText(s)) continue;
            Map o2 = (Map)((Map)entry.getValue()).get("inputCheck");
            o2.put("message", s);
        }
    }
}


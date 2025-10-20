/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.impl.model.PluginDefineImpl
 *  com.jiuqi.va.biz.impl.model.PluginTypeBase
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.view.intf.ViewDefine
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.plugin.AttachmentPluginDefineImpl;
import com.jiuqi.va.bill.plugin.event.VaAttachmentDataPostEvent;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class AttachmentPluginType
extends PluginTypeBase {
    public static final String NAME = "attachment";
    public static final String TITLE = "\u9644\u4ef6";
    @Autowired
    private VaAttachmentDataPostEvent vaAttachmentDataPostEvent;

    public String getName() {
        return NAME;
    }

    public Class<? extends ModelImpl> getDependModel() {
        return BillModelImpl.class;
    }

    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return AttachmentPluginDefineImpl.class;
    }

    public String[] getDependPlugins() {
        return new String[]{"view"};
    }

    public String getTitle() {
        return TITLE;
    }

    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
        DataImpl data = (DataImpl)model.getPlugins().get(DataImpl.class);
        data.registerDataPostEvent((DataPostEvent)this.vaAttachmentDataPostEvent);
    }

    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        this.calculateAttachParams(pluginDefine, modelDefine);
    }

    private void calculateAttachParams(PluginDefine pluginDefine, ModelDefine modelDefine) {
        AttachmentPluginDefineImpl attachmentPluginDefine = (AttachmentPluginDefineImpl)pluginDefine;
        HashMap<String, Map<String, Set<String>>> schemesQuoteCode = new HashMap<String, Map<String, Set<String>>>();
        HashMap<String, Map<String, Set<String>>> schemesAttachNum = new HashMap<String, Map<String, Set<String>>>();
        attachmentPluginDefine.setQuoteCodes(schemesQuoteCode);
        attachmentPluginDefine.setAttachNums(schemesAttachNum);
        ViewDefine viewDefine = (ViewDefine)modelDefine.getPlugins().get("view");
        if (viewDefine == null) {
            return;
        }
        List schemes = viewDefine.getSchemes();
        if (CollectionUtils.isEmpty(schemes)) {
            if (viewDefine.getTemplate() == null) {
                return;
            }
            HashMap<String, Set<String>> tableFieldsQuoteCode = new HashMap<String, Set<String>>();
            HashMap<String, Set<String>> tableFieldsAttachNum = new HashMap<String, Set<String>>();
            schemesQuoteCode.put("defaultScheme", tableFieldsQuoteCode);
            schemesAttachNum.put("defaultScheme", tableFieldsAttachNum);
            this.collectQuoteFields(attachmentPluginDefine, viewDefine.getTemplate().getProps(), tableFieldsQuoteCode, tableFieldsAttachNum);
            return;
        }
        ArrayList<Integer> wizards = new ArrayList<Integer>();
        for (int i = 0; i < schemes.size(); ++i) {
            Map scheme = (Map)schemes.get(i);
            Map template = (Map)scheme.get("template");
            if (CollectionUtils.isEmpty(template)) continue;
            HashMap<String, Set<String>> tableFieldsQuoteCode = new HashMap<String, Set<String>>();
            HashMap<String, Set<String>> tableFieldsAttachNum = new HashMap<String, Set<String>>();
            schemesQuoteCode.put((String)scheme.get("code"), tableFieldsQuoteCode);
            schemesAttachNum.put((String)scheme.get("code"), tableFieldsAttachNum);
            if ("v-wizard".equals(template.get("type"))) {
                wizards.add(i);
                continue;
            }
            this.collectQuoteFields(attachmentPluginDefine, template, tableFieldsQuoteCode, tableFieldsAttachNum);
        }
        for (Integer wizard : wizards) {
            Map scheme = (Map)schemes.get(wizard);
            Map template = (Map)scheme.get("template");
            if (CollectionUtils.isEmpty(template)) continue;
            Map wizardQuoteCodes = (Map)schemesQuoteCode.get(scheme.get("code"));
            List wizardInfo = (List)template.get("wizardInfo");
            HashSet<String> views = new HashSet<String>();
            for (Map map : wizardInfo) {
                Object toView;
                Object curView = map.get("curView");
                if (curView != null) {
                    views.add(curView.toString());
                }
                if ((toView = map.get("toView")) == null) continue;
                views.add(toView.toString());
            }
            for (String view : views) {
                Map curCodes = (Map)schemesQuoteCode.get(view);
                for (String s : curCodes.keySet()) {
                    if (wizardQuoteCodes.containsKey(s)) {
                        HashSet mergedSet = new HashSet((Collection)wizardQuoteCodes.get(s));
                        mergedSet.addAll((Collection)curCodes.get(s));
                        wizardQuoteCodes.put(s, mergedSet);
                        continue;
                    }
                    wizardQuoteCodes.put(s, curCodes.get(s));
                }
            }
        }
    }

    private void collectQuoteFields(AttachmentPluginDefineImpl attachmentPluginDefine, Map<String, Object> props, Map<String, Set<String>> tableFieldsQuoteCode, Map<String, Set<String>> tableFieldsAttachNum) {
        List children;
        String type = (String)props.get("type");
        if (StringUtils.hasText(type)) {
            if ("v-upload".equals(type)) {
                String attType;
                Map binding = (Map)props.get("binding");
                if (binding == null) {
                    return;
                }
                String quoteFieldName = (String)Convert.cast(binding.get("fieldName"), String.class);
                String quoteTableName = (String)Convert.cast(binding.get("tableName"), String.class);
                if (StringUtils.hasText(quoteFieldName) && StringUtils.hasText(quoteTableName)) {
                    tableFieldsQuoteCode.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
                }
                if (!StringUtils.hasText(attType = (String)props.get("attType"))) {
                    return;
                }
                AttachmentComponentInfo attachmentComponentInfo = new AttachmentComponentInfo();
                attachmentComponentInfo.setAttType(attType);
                attachmentComponentInfo.setCodeField(quoteFieldName);
                attachmentComponentInfo.setCodeTable(quoteTableName);
                attachmentPluginDefine.addAttachmentComponentInfo(attachmentComponentInfo);
                return;
            }
            if ("v-upload-list".equals(type) || "v-file".equals(type) || "v-file-new".equals(type)) {
                List attTypes;
                Map binding = (Map)props.get("binding");
                if (binding == null) {
                    return;
                }
                String quoteFieldName = (String)Convert.cast(binding.get("fieldName"), String.class);
                String quoteTableName = (String)Convert.cast(binding.get("tableName"), String.class);
                if (StringUtils.hasText(quoteFieldName) && StringUtils.hasText(quoteTableName)) {
                    tableFieldsQuoteCode.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
                }
                if (CollectionUtils.isEmpty(attTypes = (List)props.get("attType"))) {
                    return;
                }
                Map binding2 = (Map)props.get("binding2");
                if (binding2 == null) {
                    return;
                }
                String numFieldName = (String)Convert.cast(binding2.get("fieldName"), String.class);
                String numTableName = (String)Convert.cast(binding2.get("tableName"), String.class);
                for (Map item : attTypes) {
                    String attType = (String)Convert.cast(item.get("attType"), String.class);
                    if (!StringUtils.hasText(attType)) continue;
                    String title = (String)Convert.cast(item.get("title"), String.class);
                    AttachmentComponentInfo attachmentComponentInfo = new AttachmentComponentInfo();
                    attachmentComponentInfo.setCodeField(quoteFieldName);
                    attachmentComponentInfo.setCodeTable(quoteTableName);
                    attachmentComponentInfo.setAttType(attType);
                    attachmentComponentInfo.setAttTitle(title);
                    attachmentComponentInfo.setNumFiled(numFieldName);
                    attachmentComponentInfo.setNumTable(numTableName);
                    attachmentPluginDefine.addAttachmentComponentInfo(attachmentComponentInfo);
                }
                return;
            }
            if ("v-grid".equals(type)) {
                List children2;
                Map template;
                Map designData;
                Map binding = (Map)props.get("binding");
                if (binding == null) {
                    return;
                }
                List fields = (List)binding.get("fields");
                List viewFields = (List)binding.get("viewFields");
                List cardViewFields = (List)binding.get("cardViewFields");
                List editFields = (List)binding.get("editFields");
                AttachmentPluginType.gridBindFields(tableFieldsQuoteCode, fields);
                AttachmentPluginType.gridBindFields(tableFieldsQuoteCode, viewFields);
                AttachmentPluginType.gridBindFields(tableFieldsQuoteCode, cardViewFields);
                AttachmentPluginType.gridBindFields(tableFieldsQuoteCode, editFields);
                Object cardEntry = props.get("cardEntry");
                if (cardEntry != null && ((Boolean)cardEntry).booleanValue() && (designData = (Map)props.get("designData")) != null && (template = (Map)designData.get("template")) != null && (children2 = (List)template.get("children")) != null) {
                    for (int i = 0; i < children2.size(); ++i) {
                        this.collectQuoteFields(attachmentPluginDefine, (Map)children2.get(i), tableFieldsQuoteCode, tableFieldsAttachNum);
                    }
                }
            } else if ("bill-picture".equals(type)) {
                Object bindAttachment = props.get("bindAttachment");
                if (ObjectUtils.isEmpty(bindAttachment) || !((Boolean)bindAttachment).booleanValue()) {
                    return;
                }
                Map attQuote = (Map)props.get("attQuote");
                if (attQuote == null) {
                    return;
                }
                if (this.getBindingQuotecode(attQuote, tableFieldsQuoteCode)) {
                    return;
                }
            } else if (props.get("action") != null) {
                Map action = (Map)props.get("action");
                if ("bill-attachment-manage".equals(action.get("type"))) {
                    String attType;
                    Map params = (Map)action.get("params");
                    if (params == null) {
                        return;
                    }
                    Map attachmentCode = (Map)params.get("attachmentCode");
                    if (attachmentCode == null) {
                        return;
                    }
                    Map binding = (Map)attachmentCode.get("binding");
                    if (binding == null) {
                        return;
                    }
                    String quoteFieldName = (String)Convert.cast(binding.get("fieldName"), String.class);
                    String quoteTableName = (String)Convert.cast(binding.get("tableName"), String.class);
                    if (StringUtils.hasText(quoteFieldName) && StringUtils.hasText(quoteTableName)) {
                        tableFieldsQuoteCode.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
                    }
                    if (!StringUtils.hasText(attType = (String)params.get("attType"))) {
                        return;
                    }
                    Map attachmentNum = (Map)params.get("attachmentNum");
                    if (attachmentNum == null) {
                        return;
                    }
                    Map binding2 = (Map)attachmentCode.get("binding");
                    if (binding2 == null) {
                        return;
                    }
                    String numFieldName = (String)Convert.cast(binding2.get("fieldName"), String.class);
                    String numTableName = (String)Convert.cast(binding2.get("tableName"), String.class);
                    AttachmentComponentInfo attachmentComponentInfo = new AttachmentComponentInfo();
                    attachmentComponentInfo.setAttType(attType);
                    attachmentComponentInfo.setCodeField(quoteFieldName);
                    attachmentComponentInfo.setCodeTable(quoteTableName);
                    attachmentComponentInfo.setNumFiled(numFieldName);
                    attachmentComponentInfo.setNumTable(numTableName);
                    attachmentPluginDefine.addAttachmentComponentInfo(attachmentComponentInfo);
                    return;
                }
            } else if (props.containsKey("isAttachExtend") && ((Boolean)props.get("isAttachExtend")).booleanValue() && this.getBindingQuotecode(props, tableFieldsQuoteCode)) {
                return;
            }
        }
        if ((children = (List)props.get("children")) != null) {
            for (Map child : children) {
                this.collectQuoteFields(attachmentPluginDefine, child, tableFieldsQuoteCode, tableFieldsAttachNum);
            }
        }
    }

    private static void gridBindFields(Map<String, Set<String>> tableFieldsQuoteCode, List<Map<String, Object>> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        for (Map<String, Object> field : fields) {
            Map inputTypeParamMap;
            Map bindingAtt;
            Object inputTypeParam;
            if (!"v-grid-attachment".equals(field.get("inputType")) || ObjectUtils.isEmpty(inputTypeParam = field.get("inputTypeParam")) || (bindingAtt = (Map)(inputTypeParamMap = JSONUtil.parseMap((String)inputTypeParam.toString())).get("binding")) == null) continue;
            String quoteFieldName = (String)Convert.cast(bindingAtt.get("fieldName"), String.class);
            String quoteTableName = (String)Convert.cast(bindingAtt.get("tableName"), String.class);
            if (!StringUtils.hasText(quoteFieldName) || !StringUtils.hasText(quoteTableName)) continue;
            tableFieldsQuoteCode.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
        }
    }

    private boolean getBindingQuotecode(Map<String, Object> props, Map<String, Set<String>> tableFieldsQuoteCode) {
        Map binding = (Map)props.get("binding");
        if (binding == null) {
            return true;
        }
        String quoteFieldName = (String)Convert.cast(binding.get("fieldName"), String.class);
        String quoteTableName = (String)Convert.cast(binding.get("tableName"), String.class);
        if (StringUtils.hasText(quoteFieldName) && StringUtils.hasText(quoteTableName)) {
            tableFieldsQuoteCode.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
        }
        return false;
    }
}


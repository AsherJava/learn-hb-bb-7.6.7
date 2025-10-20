/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.view.intf.ViewDefine
 */
package com.jiuqi.va.extend.plugin.reuse;

import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import com.jiuqi.va.extend.plugin.reuse.ReuseFieldPluginDefineImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaReuseFieldPluginCheck")
@ConditionalOnMissingClass(value={"com.jiuqi.cfas.fo.bill.plugin.ReuserFieldPluginCheck"})
public class ReuseFieldPluginCheck
implements PluginCheck {
    private final Set<String> displayedFieldSet = new HashSet<String>(64);

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        ArrayList checkResults = new ArrayList();
        ViewDefine view = (ViewDefine)modelDefine.getPlugins().find("view");
        if (view == null || view.getTemplate() == null) {
            pluginCheckResultVO.setCheckResults(checkResults);
            return pluginCheckResultVO;
        }
        Map tempDisplayedFieldList = view.getTemplate().getProps();
        this.displayedFieldSet.clear();
        this.listDisplayedField(tempDisplayedFieldList);
        ReuseFieldPluginDefineImpl b = (ReuseFieldPluginDefineImpl)pluginDefine;
        if (b.getReUseFields() != null) {
            b.getReUseFields().forEach(field -> {
                if (!this.displayedFieldSet.contains(field.getTableCode() + ":" + field.getFieldCode())) {
                    checkResults.add(this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u53ef\u590d\u7528\u5b57\u6bb5\u5728\u5355\u636e\u754c\u9762\u4e0a\u5fc5\u987b\u5b58\u5728" + field.getTableCode() + "[" + field.getFieldCode() + "]", field.getTableCode() + "/" + field.getFieldCode()));
                }
            });
        }
        pluginCheckResultVO.setCheckResults(checkResults);
        return pluginCheckResultVO;
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }

    private void listDisplayedField(Map<String, Object> props) {
        String tableName;
        Map binding = (Map)props.get("binding");
        if ("v-input".equals(props.get("type"))) {
            this.displayedFieldSet.add(binding.get("tableName") + ":" + binding.get("fieldName"));
        } else if ("v-grid".equals(props.get("type"))) {
            tableName = (String)binding.get("tableName");
            List fields = (List)binding.get("fields");
            fields.forEach(field -> this.displayedFieldSet.add(tableName + ":" + (field.get("fieldName") != null ? field.get("fieldName") : field.get("name"))));
        } else if ("v-target-card".equals(props.get("type")) && "FIELD".equals(props.get("textType"))) {
            tableName = (String)binding.get("tableName");
            if (StringUtils.hasText(tableName)) {
                this.displayedFieldSet.add(binding.get("tableName") + ":" + binding.get("fieldName"));
            }
        } else if ("v-indicator-card-control".equals(props.get("type"))) {
            if (binding.containsKey("cardDatas")) {
                List cardDatas = (List)binding.get("cardDatas");
                cardDatas.forEach(cardData -> {
                    if (!"TABLEFIEID".equals(cardData.get("valueSource"))) {
                        return;
                    }
                    Map valueSourceData = (Map)cardData.get("valueSourceData");
                    Map tableSourceData = (Map)valueSourceData.get("tableSourceData");
                    Map cardBind = (Map)tableSourceData.get("binding");
                    String tableName = (String)cardBind.get("tableName");
                    if (StringUtils.hasText(tableName)) {
                        this.displayedFieldSet.add(cardBind.get("tableName") + ":" + cardBind.get("fieldName"));
                    }
                });
            }
        } else if ("v-field-combination".equals(props.get("type"))) {
            this.displayedFieldSet.add(binding.get("tableName") + ":" + binding.get("fieldName"));
        } else if ("v-bill-state".equals(props.get("type"))) {
            Object o = props.get("subheading");
            this.getBillStateBindFields(o);
            Object o1 = props.get("label");
            this.getBillStateBindFields(o1);
        }
        List children = (List)props.get("children");
        if (children != null && children.size() != 0) {
            children.forEach(this::listDisplayedField);
        }
    }

    private void getBillStateBindFields(Object o1) {
        if (o1 == null) {
            return;
        }
        Map subbinding = (Map)o1;
        Map bindingField = (Map)subbinding.get("binding");
        if (bindingField == null || !StringUtils.hasText((String)bindingField.get("tableName"))) {
            return;
        }
        this.displayedFieldSet.add(bindingField.get("tableName") + ":" + bindingField.get("fieldName"));
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ReuseFieldPluginDefineImpl.class;
    }

    public String getName() {
        return "reuseFieldComponent";
    }
}


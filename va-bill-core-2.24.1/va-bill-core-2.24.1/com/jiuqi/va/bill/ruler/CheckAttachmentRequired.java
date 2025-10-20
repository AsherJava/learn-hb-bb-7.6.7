/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.view.intf.ViewDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class CheckAttachmentRequired
implements RulerItem {
    public String getName() {
        return "CheckAttachmentRequired";
    }

    public String getTitle() {
        return "\u68c0\u9a8c\u9644\u4ef6\u5fc5\u586b";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("before-save").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine modelDefine) {
        return null;
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine modelDefine) {
        return null;
    }

    public void execute(Model model, Stream<TriggerEvent> stream) {
        BillModelImpl billModel = (BillModelImpl)model;
        Object closeAttachmentCheck = billModel.getContext().getContextValue("closeAttachmentCheck");
        if (closeAttachmentCheck != null && ((Boolean)closeAttachmentCheck).booleanValue()) {
            return;
        }
        ViewDefine view = (ViewDefine)model.getDefine().getPlugins().get("view");
        Map props = view.getTemplate().getProps();
        if (props != null && props.get("type") != null && props.get("type").toString().equals("v-wizard")) {
            BillContext context = billModel.getContext();
            Object curView = context.getContextValue("curView");
            if (curView != null) {
                this.doCheck(billModel, view, curView.toString());
            }
        } else {
            DataImpl data = billModel.getData();
            this.checkType(props, data);
        }
    }

    private void doCheck(BillModelImpl billModel, ViewDefine view, String schemeCode) {
        Map scheme;
        Object template;
        Optional<Map> optional = view.getSchemes().stream().filter(o -> o.get("code").equals(schemeCode)).findFirst();
        if (optional.isPresent() && (template = (scheme = optional.get()).get("template")) != null) {
            DataImpl data = billModel.getData();
            this.checkType((Map)template, data);
        }
    }

    public void checkType(Map<String, Object> props, DataImpl data) {
        List children;
        VaAttachmentFeignClient vaAttachmentFeignClient = (VaAttachmentFeignClient)ApplicationContextRegister.getBean(VaAttachmentFeignClient.class);
        RulerDefineImpl rulerDefineImpl = (RulerDefineImpl)((Plugin)data.getModel().getPlugins().get("ruler")).getDefine();
        ListContainer formulas = rulerDefineImpl.getFormulas();
        if (formulas == null || formulas.size() == 0) {
            return;
        }
        String type = (String)props.get("type");
        if (StringUtils.hasText(type)) {
            if ("v-upload-list".equals(type)) {
                this.checkRequire(props, data, vaAttachmentFeignClient, (ListContainer<FormulaImpl>)formulas, "v-upload-list");
            } else if ("v-grid".equals(type)) {
                Map binding = (Map)props.get("binding");
                if (binding == null) {
                    return;
                }
                List fields = (List)binding.get("fields");
                if (CollectionUtils.isEmpty(fields)) {
                    return;
                }
                for (Map field : fields) {
                    Object inputTypeParam = field.get("inputTypeParam");
                    if (ObjectUtils.isEmpty(inputTypeParam)) continue;
                    Map inputTypeParamMap = JSONUtil.parseMap((String)inputTypeParam.toString());
                    this.checkRequire(inputTypeParamMap, data, vaAttachmentFeignClient, (ListContainer<FormulaImpl>)formulas, "v-grid");
                }
            }
        }
        if ((children = (List)props.get("children")) != null) {
            children.forEach(o -> this.checkType((Map<String, Object>)o, data));
        }
    }

    private void checkRequire(Map<String, Object> props, DataImpl data, VaAttachmentFeignClient vaAttachmentFeignClient, ListContainer<FormulaImpl> formulas, String type) {
        Map binding = (Map)props.get("binding");
        if (props.get("attType") instanceof String) {
            return;
        }
        List attTypes = (List)props.get("attType");
        if (binding == null) {
            return;
        }
        HashMap<String, Set> tableFields = new HashMap<String, Set>();
        String quoteFieldName = (String)Convert.cast(binding.get("fieldName"), String.class);
        String quoteTableName = (String)Convert.cast(binding.get("tableName"), String.class);
        if (StringUtils.hasText(quoteFieldName) && StringUtils.hasText(quoteTableName)) {
            tableFields.computeIfAbsent(quoteTableName, o -> new HashSet()).add(quoteFieldName);
        }
        HashSet quotes = new HashSet();
        for (Map.Entry tableField : tableFields.entrySet()) {
            String tableName = (String)tableField.getKey();
            Set fields = (Set)tableField.getValue();
            if (fields == null || fields.size() == 0) {
                return;
            }
            DataTableImpl dataTable = (DataTableImpl)data.getTables().find(tableName);
            ListContainer rows = dataTable.getRows();
            if (rows.size() == 0) {
                return;
            }
            rows.forEach((index, row) -> {
                List list = fields.stream().map(o -> row.getString(o)).collect(Collectors.toList());
                quotes.addAll(list);
            });
        }
        if (quotes.size() == 0) {
            return;
        }
        ArrayList<String> result = new ArrayList<String>();
        for (String quote : quotes) {
            if (!StringUtils.hasText(quote)) {
                if ("v-grid".equals(type)) {
                    return;
                }
                if (attTypes == null || attTypes.size() == 0) {
                    return;
                }
                ArrayList<String> list = new ArrayList<String>();
                for (Map attType : attTypes) {
                    if (attType.get("required").equals("")) {
                        String id = (String)attType.get("id");
                        List<FormulaImpl> collect = formulas.stream().filter(o -> "control".equals(o.getObjectType()) && "required".equals(o.getPropertyType())).collect(Collectors.toList());
                        collect.forEach(o -> {
                            if (o.getObjectId().toString().equals(id)) {
                                if (!o.isUsed() || ObjectUtils.isEmpty(o.getExpression())) {
                                    return;
                                }
                                try {
                                    Boolean execute = (Boolean)FormulaUtils.evaluate((String)o.getExpression(), (Model)data.getModel());
                                    if (execute.booleanValue()) {
                                        AttachmentBizDO param = new AttachmentBizDO();
                                        param.setQuotecode(quote);
                                        param.setModename(attType.get("attType").toString());
                                        boolean check = vaAttachmentFeignClient.check(quote, attType.get("attType").toString(), param);
                                        if (!check) {
                                            if (StringUtils.hasText(o.getCheckMessage())) {
                                                list.add(o.getCheckMessage());
                                            } else {
                                                list.add(attType.get("title").toString());
                                            }
                                        }
                                    }
                                }
                                catch (Exception e) {
                                    throw new BillException("[" + o.getTitle() + "]" + e.getMessage());
                                }
                            }
                        });
                        continue;
                    }
                    if (!((Boolean)attType.get("required")).booleanValue()) continue;
                    AttachmentBizDO param = new AttachmentBizDO();
                    param.setQuotecode(quote);
                    param.setModename(attType.get("attType").toString());
                    boolean check = vaAttachmentFeignClient.check(quote, attType.get("attType").toString(), param);
                    if (check) continue;
                    list.add(attType.get("title").toString());
                }
                if (list.size() > 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.attachment.not.empty", new Object[]{StringUtils.collectionToCommaDelimitedString(list)}));
                }
            }
            if (attTypes == null || attTypes.size() == 0) {
                return;
            }
            for (int i = 0; i < attTypes.size(); ++i) {
                Map map = (Map)attTypes.get(i);
                if (map.get("attType") == null) {
                    return;
                }
                if (map.get("required").equals("")) {
                    String id = (String)map.get("id");
                    FormulaImpl collect = formulas.stream().filter(o -> "control".equals(o.getObjectType()) && "required".equals(o.getPropertyType()) && o.getObjectId().toString().equals(id)).findFirst().orElse(null);
                    if (collect == null) continue;
                    String expression = collect.getExpression();
                    try {
                        Boolean execute = (Boolean)FormulaUtils.evaluate((String)expression, (Model)data.getModel());
                        if (!execute.booleanValue()) continue;
                        AttachmentBizDO param = new AttachmentBizDO();
                        param.setQuotecode(quote);
                        param.setModename(map.get("attType").toString());
                        boolean check = vaAttachmentFeignClient.check(quote, map.get("attType").toString(), param);
                        if (check) continue;
                        if (StringUtils.hasText(collect.getCheckMessage())) {
                            result.add(collect.getCheckMessage());
                            continue;
                        }
                        result.add(map.get("title").toString());
                        continue;
                    }
                    catch (Exception e) {
                        throw new BillException("[" + collect.getTitle() + "]" + e.getMessage());
                    }
                }
                if (!((Boolean)map.get("required")).booleanValue()) continue;
                AttachmentBizDO param = new AttachmentBizDO();
                param.setQuotecode(quote);
                param.setModename(map.get("attType").toString());
                boolean check = vaAttachmentFeignClient.check(quote, map.get("attType").toString(), param);
                if (check) continue;
                result.add(map.get("title").toString());
            }
        }
        if (result.size() > 0) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.attachment.not.empty", new Object[]{StringUtils.collectionToCommaDelimitedString(result)}));
        }
    }
}


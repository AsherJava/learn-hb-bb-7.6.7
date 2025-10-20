/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.plugin.event;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.plugin.AttachmentPluginDefineImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.common.R;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VaAttachmentDataPostEvent
implements DataPostEvent {
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public void beforeSave(DataImpl data) {
    }

    public void afterSave(DataImpl data) {
        Model model = data.getModel();
        BillContext context = (BillContext)model.getContext();
        String triggerOrigin = context.getTriggerOrigin();
        if (!StringUtils.hasText(triggerOrigin)) {
            return;
        }
        Map<String, Set<String>> tableFields = VaAttachmentDataPostEvent.getQuoteCodeFields(model);
        if (tableFields == null) {
            return;
        }
        HashSet quotes = new HashSet();
        for (Map.Entry<String, Set<String>> tableField : tableFields.entrySet()) {
            String tableName = tableField.getKey();
            Set<String> fields = tableField.getValue();
            if (fields == null || fields.size() == 0) continue;
            DataTableImpl dataTable = (DataTableImpl)data.getTables().find(tableName);
            ListContainer rows = dataTable.getRows();
            if (dataTable.F_MASTERID != null) {
                dataTable.getDeletedRows().forEach(row -> {
                    List list = fields.stream().map(o -> row.getString(o)).collect(Collectors.toList());
                    quotes.addAll(list);
                });
            }
            if (rows.size() == 0) continue;
            rows.forEach((index, row) -> {
                List list = fields.stream().map(o -> row.getString(o)).collect(Collectors.toList());
                quotes.addAll(list);
            });
        }
        if (quotes.size() > 0) {
            AttachmentBizDO param = new AttachmentBizDO();
            for (String quote : quotes) {
                if (!StringUtils.hasText(quote)) continue;
                param.setQuotecode(quote);
                param.setTraceId(Utils.getTraceId());
                R confirm = this.vaAttachmentFeignClient.confirm(quote, param);
                if (confirm.getCode() != 1) continue;
                throw new RuntimeException(confirm.getMsg());
            }
        }
    }

    public static Map<String, Set<String>> getQuoteCodeFields(Model model) {
        Map<String, Set<String>> tableFields;
        ViewDefineImpl viewDefine;
        AttachmentPluginDefineImpl attachment = (AttachmentPluginDefineImpl)((Plugin)model.getPlugins().get("attachment")).getDefine();
        String schemeCode = ((BillContextImpl)model.getContext()).getSchemeCode();
        if (!StringUtils.hasText(schemeCode) && !StringUtils.hasText(schemeCode = (viewDefine = (ViewDefineImpl)((Plugin)model.getPlugins().get("view")).getDefine()).getDefaultSchemeCode())) {
            schemeCode = "defaultScheme";
        }
        if ((tableFields = attachment.getQuoteCodes().get(schemeCode)) == null) {
            tableFields = attachment.getQuoteCodes().get("defautScheme");
        }
        if (tableFields == null) {
            return null;
        }
        return tableFields;
    }

    public void beforeDelete(DataImpl data) {
    }

    public void afterDelete(DataImpl data) {
    }
}


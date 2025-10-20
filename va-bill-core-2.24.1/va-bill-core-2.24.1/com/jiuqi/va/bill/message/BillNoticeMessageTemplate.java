/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.todo.QueryFromEnum
 *  com.jiuqi.va.domain.todo.VaTodoQueryConfigDTO
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.message.template.AbstractVaMessageTemplate
 *  com.jiuqi.va.message.template.VaMessageTemplateConfig
 *  com.jiuqi.va.message.template.domain.FormulaConfig
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO
 *  com.jiuqi.va.message.template.domain.WxworkTemplateConfig
 */
package com.jiuqi.va.bill.message;

import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.todo.QueryFromEnum;
import com.jiuqi.va.domain.todo.VaTodoQueryConfigDTO;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.message.template.AbstractVaMessageTemplate;
import com.jiuqi.va.message.template.VaMessageTemplateConfig;
import com.jiuqi.va.message.template.domain.FormulaConfig;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import com.jiuqi.va.message.template.domain.WxworkTemplateConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillNoticeMessageTemplate
extends AbstractVaMessageTemplate {
    @Autowired
    private TodoClient todoClient;

    public String getName() {
        return "BILLNOTICE";
    }

    public String getTitle() {
        return "\u5355\u636e\u6d88\u606f\u901a\u77e5";
    }

    public String getFunction() {
        return "VABILLNOTICE";
    }

    public List<VaMessageTemplateParamDO> getTemplateParams() {
        ArrayList<VaMessageTemplateParamDO> vaTemplateParamDOs = new ArrayList<VaMessageTemplateParamDO>();
        VaTodoQueryConfigDTO vaTodoQueryConfigDTO = new VaTodoQueryConfigDTO();
        vaTodoQueryConfigDTO.setQuerytype("DONE");
        vaTodoQueryConfigDTO.addExtInfo("apiFrom", (Object)"back");
        vaTodoQueryConfigDTO.setQueryFrom(QueryFromEnum.MSG_TEMPLATE);
        R r = this.todoClient.getQueryConfig(vaTodoQueryConfigDTO);
        if (r.getCode() == 0) {
            Map queryConfigMap = (Map)r.get((Object)"queryconfig");
            List list = (List)queryConfigMap.get("column");
            for (Map map : list) {
                VaMessageTemplateParamDO vaTemplateParamDO = new VaMessageTemplateParamDO();
                vaTemplateParamDO.setName(map.get("columnName").toString());
                vaTemplateParamDO.setTitle(map.get("columnTitle").toString());
                vaTemplateParamDO.setType(map.get("columnType") == null ? null : map.get("columnType").toString());
                vaTemplateParamDO.setMapping(map.get("mapping") == null ? null : map.get("mapping").toString());
                vaTemplateParamDO.setMappingType(map.get("mappingType") == null ? null : Integer.valueOf((Integer)map.get("mappingType")));
                vaTemplateParamDOs.add(vaTemplateParamDO);
            }
        } else {
            throw new RuntimeException(r.getMsg());
        }
        VaMessageTemplateParamDO vaTemplateParamDO = new VaMessageTemplateParamDO();
        vaTemplateParamDO.setName("SUBMITTIME");
        vaTemplateParamDO.setType(DataModelType.ColumnType.TIMESTAMP.name());
        vaTemplateParamDO.setTitle("\u63d0\u4ea4\u65f6\u95f4");
        vaTemplateParamDOs.add(vaTemplateParamDO);
        return vaTemplateParamDOs;
    }

    public VaMessageTemplateConfig getSmsTemplateDefaultConfig() {
        return null;
    }

    public VaMessageTemplateConfig getWxworkTemplateDefaultConfig() {
        WxworkTemplateConfig config = new WxworkTemplateConfig();
        FormulaConfig body = new FormulaConfig();
        body.setExpression(BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice9", new Object[]{"[SUBMITTIME]", "[BIZDEFINE]+[BIZCODE]"}));
        config.setBody(body);
        FormulaConfig linkTitle = new FormulaConfig();
        linkTitle.setExpression(BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.clickhereview"));
        config.setLinkTitle(linkTitle);
        return config;
    }

    public VaMessageTemplateConfig getEmailTemplateDefaultConfig() {
        return null;
    }
}


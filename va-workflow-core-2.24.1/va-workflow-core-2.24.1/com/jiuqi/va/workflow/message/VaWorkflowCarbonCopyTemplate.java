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
 *  com.jiuqi.va.message.template.domain.SmsTemplateConfig
 *  com.jiuqi.va.message.template.domain.SystemTemplateConfig
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO
 *  com.jiuqi.va.message.template.domain.WxworkTemplateConfig
 */
package com.jiuqi.va.workflow.message;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.todo.QueryFromEnum;
import com.jiuqi.va.domain.todo.VaTodoQueryConfigDTO;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.message.template.AbstractVaMessageTemplate;
import com.jiuqi.va.message.template.VaMessageTemplateConfig;
import com.jiuqi.va.message.template.domain.FormulaConfig;
import com.jiuqi.va.message.template.domain.SmsTemplateConfig;
import com.jiuqi.va.message.template.domain.SystemTemplateConfig;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import com.jiuqi.va.message.template.domain.WxworkTemplateConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowCarbonCopyTemplate
extends AbstractVaMessageTemplate {
    private static final Logger logger = LoggerFactory.getLogger(VaWorkflowCarbonCopyTemplate.class);
    @Autowired
    private TodoClient todoClient;

    public String getName() {
        return "CARBONCOPYMESSAGE";
    }

    public String getTitle() {
        return "\u5de5\u4f5c\u6d41\u6284\u9001\u901a\u77e5";
    }

    public String getFunction() {
        return "VAWORKFLOW";
    }

    public String getOrder() {
        return "004";
    }

    public List<VaMessageTemplateParamDO> getTemplateParams() {
        ArrayList<VaMessageTemplateParamDO> vaTemplateParamDoList = new ArrayList<VaMessageTemplateParamDO>();
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
                vaTemplateParamDoList.add(vaTemplateParamDO);
            }
        } else {
            logger.error("\u5de5\u4f5c\u6d41\u6284\u9001\u901a\u77e5\u53c2\u6570\u67e5\u8be2\u51fa\u9519\uff1a{}", (Object)r.getMsg());
            return vaTemplateParamDoList;
        }
        VaMessageTemplateParamDO vaTemplateParamDO1 = new VaMessageTemplateParamDO();
        vaTemplateParamDO1.setName("SUBMITTIME");
        vaTemplateParamDO1.setType(DataModelType.ColumnType.TIMESTAMP.name());
        vaTemplateParamDO1.setTitle("\u63d0\u4ea4\u65f6\u95f4");
        vaTemplateParamDoList.add(vaTemplateParamDO1);
        VaMessageTemplateParamDO vaTemplateParamDO2 = new VaMessageTemplateParamDO();
        vaTemplateParamDO2.setName("CARBONCOPY_REMARK");
        vaTemplateParamDO2.setTitle("\u6284\u9001\u8bf4\u660e");
        vaTemplateParamDoList.add(vaTemplateParamDO2);
        VaMessageTemplateParamDO vaTemplateParamDO3 = new VaMessageTemplateParamDO();
        vaTemplateParamDO3.setName("APPROVEUSERID");
        vaTemplateParamDO3.setTitle("\u5ba1\u6279\u4ebaID");
        vaTemplateParamDO3.setType("NVARCHAR");
        vaTemplateParamDO3.setMapping("AUTH_USER.ID");
        vaTemplateParamDO3.setMappingType(Integer.valueOf(3));
        vaTemplateParamDoList.add(vaTemplateParamDO3);
        return vaTemplateParamDoList;
    }

    public VaMessageTemplateConfig getSystemTemplateDefaultConfig() {
        SystemTemplateConfig systemTemplateConfig = new SystemTemplateConfig();
        systemTemplateConfig.setEnable(Boolean.TRUE.booleanValue());
        String stringBuilder = "'\u60a8\u6536\u5230\u7531'+[APPROVEUSERID]+'\u53d1\u9001\u7684'+[BIZDEFINE]+[BIZCODE]+'\u5ba1\u6279\u6284\u9001\u901a\u77e5'+[CARBONCOPY_REMARK]";
        FormulaConfig formulaConfig = new FormulaConfig();
        formulaConfig.setExpression(stringBuilder);
        systemTemplateConfig.setMsgTitle(formulaConfig);
        return systemTemplateConfig;
    }

    public VaMessageTemplateConfig getSmsTemplateDefaultConfig() {
        SmsTemplateConfig config = new SmsTemplateConfig();
        FormulaConfig body = new FormulaConfig();
        body.setExpression("");
        config.setBody(body);
        return config;
    }

    public VaMessageTemplateConfig getWxworkTemplateDefaultConfig() {
        WxworkTemplateConfig config = new WxworkTemplateConfig();
        FormulaConfig body = new FormulaConfig();
        String stringBuilder = "'\u60a8\u6536\u5230\u7531'+[APPROVEUSERID]+'\u53d1\u9001\u7684'+[BIZDEFINE]+[BIZCODE]+'\u5ba1\u6279\u6284\u9001\u901a\u77e5'+[CARBONCOPY_REMARK]";
        body.setExpression(stringBuilder);
        config.setBody(body);
        FormulaConfig linkTitle = new FormulaConfig();
        linkTitle.setExpression("\"\u8bf7\u70b9\u51fb\u6b64\u5904\u67e5\u770b...\"");
        config.setLinkTitle(linkTitle);
        return config;
    }

    public VaMessageTemplateConfig getEmailTemplateDefaultConfig() {
        return null;
    }
}


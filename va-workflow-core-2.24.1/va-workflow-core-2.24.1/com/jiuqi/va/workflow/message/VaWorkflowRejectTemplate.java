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
 *  com.jiuqi.va.message.template.domain.EmailTemplateConfig
 *  com.jiuqi.va.message.template.domain.FormulaConfig
 *  com.jiuqi.va.message.template.domain.SmsTemplateConfig
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateGrid
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
import com.jiuqi.va.message.template.domain.EmailTemplateConfig;
import com.jiuqi.va.message.template.domain.FormulaConfig;
import com.jiuqi.va.message.template.domain.SmsTemplateConfig;
import com.jiuqi.va.message.template.domain.VaMessageTemplateGrid;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import com.jiuqi.va.message.template.domain.WxworkTemplateConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowRejectTemplate
extends AbstractVaMessageTemplate {
    @Autowired
    private TodoClient todoClient;

    public String getName() {
        return "REJECT";
    }

    public String getTitle() {
        return "\u9a73\u56de\u5230\u63d0\u4ea4\u4eba";
    }

    public String getFunction() {
        return "VAWORKFLOW";
    }

    public String getOrder() {
        return "002";
    }

    public List<VaMessageTemplateParamDO> getTemplateParams() {
        ArrayList<VaMessageTemplateParamDO> vaTemplateParamDOs = new ArrayList<VaMessageTemplateParamDO>();
        VaTodoQueryConfigDTO vaTodoQueryConfigDTO = new VaTodoQueryConfigDTO();
        vaTodoQueryConfigDTO.setQuerytype("REJECT");
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
        vaTemplateParamDO.setTitle("\u63d0\u4ea4\u65f6\u95f4");
        vaTemplateParamDOs.add(vaTemplateParamDO);
        VaMessageTemplateParamDO vaTemplateParamDO1 = new VaMessageTemplateParamDO();
        vaTemplateParamDO1.setName("REJECTREASON");
        vaTemplateParamDO1.setTitle("\u9a73\u56de\u539f\u56e0");
        vaTemplateParamDOs.add(vaTemplateParamDO1);
        VaMessageTemplateParamDO completeUser = new VaMessageTemplateParamDO();
        completeUser.setName("PARTICIPANT");
        completeUser.setTitle("\u5904\u7406\u4eba");
        completeUser.setType(String.valueOf(DataModelType.ColumnType.NVARCHAR));
        completeUser.setMappingType(Integer.valueOf(3));
        completeUser.setMapping("AUTH_USER.ID");
        vaTemplateParamDOs.add(completeUser);
        return vaTemplateParamDOs;
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
        StringBuilder sb = new StringBuilder();
        sb.append("'\u60a8\u4e8e'");
        sb.append("+");
        sb.append("[SUBMITTIME]");
        sb.append("+");
        sb.append("'\u63d0\u4ea4\u7684'");
        sb.append("+");
        sb.append("[BIZDEFINE]");
        sb.append("+");
        sb.append("[BIZCODE]");
        sb.append("+");
        sb.append("'\u88ab\u9a73\u56de\uff1a'");
        sb.append("+");
        sb.append("[REJECTREASON]");
        body.setExpression(sb.toString());
        config.setBody(body);
        FormulaConfig linkTitle = new FormulaConfig();
        linkTitle.setExpression("\"\u8bf7\u70b9\u51fb\u6b64\u5904\u67e5\u770b...\"");
        config.setLinkTitle(linkTitle);
        return config;
    }

    public VaMessageTemplateConfig getEmailTemplateDefaultConfig() {
        ArrayList<VaMessageTemplateGrid> grid = new ArrayList<VaMessageTemplateGrid>();
        VaTodoQueryConfigDTO vaTodoQueryConfigDTO = new VaTodoQueryConfigDTO();
        vaTodoQueryConfigDTO.setQuerytype("REJECT");
        R r = this.todoClient.getQueryConfig(vaTodoQueryConfigDTO);
        vaTodoQueryConfigDTO.addExtInfo("apiFrom", (Object)"back");
        vaTodoQueryConfigDTO.setQueryFrom(QueryFromEnum.MSG_TEMPLATE);
        if (r.getCode() == 0) {
            Map queryConfigMap = (Map)r.get((Object)"queryconfig");
            List list = (List)queryConfigMap.get("column");
            for (Map map : list) {
                if (map.get("model") == null || !((Boolean)map.get("model")).booleanValue()) continue;
                String columnName = map.get("columnName").toString();
                String columnTitle = map.get("columnTitle").toString();
                VaMessageTemplateGrid gridItem = new VaMessageTemplateGrid();
                gridItem.setName(columnName);
                gridItem.setTitle(columnTitle);
                gridItem.setShowTitle(columnTitle);
                if ("BIZCODE".equals(columnName)) {
                    gridItem.setEnableLink(true);
                }
                grid.add(gridItem);
            }
        }
        EmailTemplateConfig config = new EmailTemplateConfig();
        config.setGrid(grid);
        return config;
    }
}


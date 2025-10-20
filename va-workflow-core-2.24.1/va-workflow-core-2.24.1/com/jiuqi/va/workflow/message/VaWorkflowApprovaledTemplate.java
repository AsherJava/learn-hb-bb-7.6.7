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
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowApprovaledTemplate
extends AbstractVaMessageTemplate {
    @Autowired
    private TodoClient todoClient;

    public String getName() {
        return "APPROVALED";
    }

    public String getTitle() {
        return "\u5de5\u4f5c\u6d41\u5ba1\u6279\u5b8c\u6210";
    }

    public String getFunction() {
        return "VAWORKFLOW";
    }

    public String getOrder() {
        return "003";
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
        vaTemplateParamDO.setTitle("\u63d0\u4ea4\u65f6\u95f4");
        vaTemplateParamDOs.add(vaTemplateParamDO);
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
        body.setExpression(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.approvaledmsg2", "[SUBMITTIME]", "[BIZDEFINE]+[BIZCODE]"));
        config.setBody(body);
        FormulaConfig linkTitle = new FormulaConfig();
        linkTitle.setExpression(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.clickhereview"));
        config.setLinkTitle(linkTitle);
        return config;
    }

    public VaMessageTemplateConfig getEmailTemplateDefaultConfig() {
        EmailTemplateConfig config = new EmailTemplateConfig();
        ArrayList<VaMessageTemplateGrid> grid = new ArrayList<VaMessageTemplateGrid>();
        VaTodoQueryConfigDTO vaTodoQueryConfigDTO = new VaTodoQueryConfigDTO();
        vaTodoQueryConfigDTO.setQuerytype("DONE");
        vaTodoQueryConfigDTO.addExtInfo("apiFrom", (Object)"back");
        vaTodoQueryConfigDTO.setQueryFrom(QueryFromEnum.MSG_TEMPLATE);
        R r = this.todoClient.getQueryConfig(vaTodoQueryConfigDTO);
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
        config.setGrid(grid);
        return config;
    }
}


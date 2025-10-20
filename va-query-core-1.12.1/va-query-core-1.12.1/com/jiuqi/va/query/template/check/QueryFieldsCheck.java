/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.query.sql.vo.QueryParseResultVO
 *  com.jiuqi.va.query.template.enumerate.MessageTypeEnum
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.vo.QueryCheckItemVO
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.template.check;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.query.sql.service.SqlQueryService;
import com.jiuqi.va.query.sql.vo.QueryParseResultVO;
import com.jiuqi.va.query.template.check.QueryDesignCheck;
import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="queryFieldsCheck")
public class QueryFieldsCheck
implements QueryDesignCheck {
    @Autowired
    private SqlQueryService sqlQueryService;

    @Override
    public QueryPluginCheckVO checkPlugin(QueryTemplate queryTemplate) {
        QueryPluginCheckVO queryPluginCheckVO = new QueryPluginCheckVO("queryFields");
        QueryFieldsPlugin queryFieldsPlugin = (QueryFieldsPlugin)queryTemplate.getPluginByClass(QueryFieldsPlugin.class);
        QueryParseResultVO queryParseResultVO = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            QueryTemplate cloneTemplate = (QueryTemplate)objectMapper.readValue(objectMapper.writeValueAsString((Object)queryTemplate), QueryTemplate.class);
            queryParseResultVO = this.sqlQueryService.parseSql(cloneTemplate);
            List newFields = queryParseResultVO.getFields();
            Map<String, TemplateFieldSettingVO> newFieldMap = newFields.stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, o -> o, (o1, o2) -> o2));
            List fields = queryFieldsPlugin.getFields();
            if (fields != null) {
                fields.forEach(field -> {
                    if (!newFieldMap.containsKey(field.getName())) {
                        queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u67e5\u8be2\u5217\u4e0d\u5b58\u5728:" + field.getName(), ""));
                    }
                });
            }
        }
        catch (Exception e) {
            queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u89e3\u6790SQL\u5931\u8d25,\u65e0\u6cd5\u6bd4\u5bf9\u67e5\u8be2\u5217", ""));
        }
        return queryPluginCheckVO;
    }

    @Override
    public int order() {
        return 2;
    }
}


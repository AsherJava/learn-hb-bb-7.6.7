/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.query.template.enumerate.MessageTypeEnum
 *  com.jiuqi.va.query.template.plugin.QueryRelatePlugin
 *  com.jiuqi.va.query.template.vo.QueryCheckItemVO
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 */
package com.jiuqi.va.query.template.check;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.query.template.check.QueryDesignCheck;
import com.jiuqi.va.query.template.dao.TemplateInfoDao;
import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component(value="queryRelateCheck")
public class QueryRelateCheck
implements QueryDesignCheck {
    @Autowired
    private TemplateInfoDao templateInfoDao;
    @Autowired
    private MetaDataClient metadataClient;

    @Override
    public QueryPluginCheckVO checkPlugin(QueryTemplate queryTemplate) {
        QueryPluginCheckVO queryPluginCheckVO = new QueryPluginCheckVO("queryRelate");
        QueryRelatePlugin queryRelatePlugin = (QueryRelatePlugin)queryTemplate.getPluginByClass(QueryRelatePlugin.class);
        List relateQuerys = queryRelatePlugin.getRelateQuerys();
        if (CollectionUtils.isEmpty(relateQuerys)) {
            return queryPluginCheckVO;
        }
        for (int i = 0; i < relateQuerys.size(); ++i) {
            TemplateRelateQueryVO relateQueryVO = (TemplateRelateQueryVO)relateQuerys.get(i);
            String processorName = relateQueryVO.getProcessorName();
            String relator = "\u8054\u67e5\u5904\u7406\u5668";
            if (!StringUtils.hasText(processorName)) {
                queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5173\u8054\u67e5\u8be2\u5904\u7406\u5668\u4e0d\u80fd\u4e3a\u7a7a", ""));
                continue;
            }
            if (processorName.equals("QueryBill")) {
                String uniqueCode = String.valueOf(JSONUtil.parseMap((String)relateQueryVO.getProcessorConfig()).get("uniqueCode"));
                List list = JSONUtil.parseMapArray((String)relateQueryVO.getQueryParam());
                Optional<Map> first = list.stream().filter(map -> String.valueOf(map.get("name")).equals("billCode")).findFirst();
                Object billCode = null;
                if (first.isPresent()) {
                    billCode = first.get().get("value");
                }
                if (billCode == null) {
                    queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5355\u636e\u7f16\u53f7\u5217\u53c2\u6570\u503c\u4e0d\u80fd\u4e3a\u7a7a", ""));
                }
                if (!StringUtils.hasText(uniqueCode)) {
                    queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5173\u8054\u67e5\u8be2\u5904\u7406\u5668\u914d\u7f6e\u4e0d\u80fd\u4e3a\u7a7a", ""));
                } else {
                    TenantDO param = new TenantDO();
                    param.addExtInfo("defineCode", (Object)uniqueCode);
                    R r = this.metadataClient.findMetaInfoByDefineCode(param);
                    if (r.getCode() == R.error().getCode()) {
                        queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5173\u8054\u67e5\u8be2\u5904\u7406\u5668\u5173\u8054\u7684\u5355\u636e\u5b9a\u4e49\u4e0d\u5b58\u5728", ""));
                    }
                }
            }
            if (!processorName.equals("RelateQueryUserDefined")) continue;
            String id = String.valueOf(JSONUtil.parseMap((String)relateQueryVO.getProcessorConfig()).get("id"));
            if (!StringUtils.hasText(id)) {
                queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5173\u8054\u67e5\u8be2\u5904\u7406\u5668\u914d\u7f6e\u4e0d\u80fd\u4e3a\u7a7a", ""));
                continue;
            }
            TemplateInfoVO templateInfo = this.templateInfoDao.getTemplateInfo(id);
            if (templateInfo != null) continue;
            queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, relator + (i + 1) + "\uff1a\u5173\u8054\u67e5\u8be2\u5904\u7406\u5668\u5173\u8054\u7684\u81ea\u5b9a\u4e49\u67e5\u8be2\u4e0d\u5b58\u5728", ""));
        }
        return queryPluginCheckVO;
    }

    @Override
    public int order() {
        return 3;
    }
}


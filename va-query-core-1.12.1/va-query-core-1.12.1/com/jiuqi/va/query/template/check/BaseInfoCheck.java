/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.template.enumerate.MessageTypeEnum
 *  com.jiuqi.va.query.template.plugin.BaseInfoPlugin
 *  com.jiuqi.va.query.template.vo.QueryCheckItemVO
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 */
package com.jiuqi.va.query.template.check;

import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.template.check.QueryDesignCheck;
import com.jiuqi.va.query.template.enumerate.MessageTypeEnum;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="baseInfoCheck")
public class BaseInfoCheck
implements QueryDesignCheck {
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;

    @Override
    public QueryPluginCheckVO checkPlugin(QueryTemplate queryTemplate) {
        QueryPluginCheckVO queryPluginCheckVO = new QueryPluginCheckVO("baseInfo");
        BaseInfoPlugin baseInfoPlugin = (BaseInfoPlugin)queryTemplate.getPluginByClass(BaseInfoPlugin.class);
        String name = baseInfoPlugin.getBaseInfo().getDatasourceCode();
        if (DataSourceEnum.CURRENT.getName().equalsIgnoreCase(name)) {
            return queryPluginCheckVO;
        }
        DataSourceInfoVO dataSourceInfoByCode = this.dataSourceInfoDao.getDataSourceInfoByCode(name);
        if (dataSourceInfoByCode == null) {
            queryPluginCheckVO.getResult().add(new QueryCheckItemVO(MessageTypeEnum.ERROR, "\u6570\u636e\u6e90\u4e0d\u5b58\u5728", ""));
        }
        return queryPluginCheckVO;
    }

    @Override
    public int order() {
        return 0;
    }
}


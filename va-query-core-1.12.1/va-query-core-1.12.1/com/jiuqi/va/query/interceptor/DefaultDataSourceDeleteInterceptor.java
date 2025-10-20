/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datacheck.DataSourceDeleteInterceptor
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.domain.DataCheckParam
 *  com.jiuqi.va.query.domain.DataCheckResult
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.interceptor;

import com.jiuqi.va.query.datacheck.DataSourceDeleteInterceptor;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="dafaultDataSourceDelete")
public class DefaultDataSourceDeleteInterceptor
extends DataSourceDeleteInterceptor {
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;

    public DataCheckResult preHandler(DataCheckParam dataCheckParam) {
        List<String> codes = dataCheckParam.getDataSourceInfoVOS().stream().map(DataSourceInfoVO::getCode).collect(Collectors.toList());
        List<TemplateInfoVO> templates = this.queryTemplateInfoDao.getTemplatesByDataSourceCode(codes);
        HashSet dbDataSourceCodes = new HashSet();
        if (templates != null) {
            templates.forEach(item -> dbDataSourceCodes.add(item.getDatasourceCode()));
        }
        if (CollectionUtils.isEmpty(dbDataSourceCodes)) {
            return DataCheckResult.pass();
        }
        return DataCheckResult.reject((String)("\u5b58\u5728\u4f7f\u7528\u8be5\u6570\u636e\u6e90[" + String.join((CharSequence)",", dbDataSourceCodes) + "]\u7684\u67e5\u8be2\u5b9a\u4e49\uff0c\u4e0d\u5141\u8bb8\u5220\u9664\uff01"));
    }
}


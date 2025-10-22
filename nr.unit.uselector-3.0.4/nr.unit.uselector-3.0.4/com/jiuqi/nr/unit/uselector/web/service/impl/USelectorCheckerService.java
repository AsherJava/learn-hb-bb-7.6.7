/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam;
import com.jiuqi.nr.unit.uselector.web.response.FilteringInfo;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorCheckerService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class USelectorCheckerService
implements IUSelectorCheckerService {
    @Resource
    private IRowCheckerHelper checkerHelper;
    @Resource
    private IFilterCacheSetHelper cacheSetHelper;
    @Resource
    private USelectorResultSet uSelectorResultSet;
    @Resource
    private IUSelectorDataSourceHelper sourceHelper;

    @Override
    public FilteringInfo executeFiltering(FilteringRequestParam filterPara) {
        List<String> filterSet;
        String selector = filterPara.getSelector();
        String checkerId = filterPara.getKeyword();
        IFilterCheckValuesImpl values = filterPara.getCheckValues();
        IUnitTreeContext context = this.uSelectorResultSet.getRunContext(selector);
        IUSelectorDataSource dataSource = this.sourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
        IRowChecker checker = this.checkerHelper.getChecker(checkerId);
        IRowCheckExecutor executor = checker.getExecutor(context);
        IFilterStringList cacheSetInRedis = this.cacheSetHelper.getInstance(selector);
        if (executor.clearSet()) {
            cacheSetInRedis.clear();
        }
        if (null != (filterSet = executor.executeCheck(values, dataSource.getUSelectorEntityRowProvider(context)))) {
            if (checker.isChecked()) {
                if (this.isDoInResult(checker, values)) {
                    cacheSetInRedis.retainAll(filterSet);
                } else if (!filterSet.isEmpty()) {
                    cacheSetInRedis.unionAll(filterSet);
                }
            } else if (!filterSet.isEmpty()) {
                cacheSetInRedis.removeAll(filterSet);
            }
        }
        int totalCount = entityRowProvider.getTotalCount();
        int selectCount = cacheSetInRedis.size();
        FilteringInfo filteringInfo = new FilteringInfo();
        filteringInfo.setSelectCount(selectCount);
        filteringInfo.setTotalCount(totalCount);
        return filteringInfo;
    }

    private boolean isDoInResult(IRowChecker checker, IFilterCheckValues values) {
        Map<String, String> runtimePara;
        if (null != values && null != (runtimePara = values.getRuntimePara())) {
            boolean isDoInResult = "T".equals(runtimePara.get("isInResult"));
            isDoInResult = isDoInResult && ("#check-with-bblx".equals(checker.getKeyword()) || "#check-with-workflow-status".equals(checker.getKeyword()) || "#check-with-node-tags".equals(checker.getKeyword()) || "#check-with-formula".equals(checker.getKeyword()) || "#check-with-expression".equals(checker.getKeyword()));
            return isDoInResult;
        }
        return false;
    }
}


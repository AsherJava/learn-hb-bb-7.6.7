/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.extend.BaseDataAction
 *  com.jiuqi.va.extend.BaseDataParamInterceptor
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.va.basedata.filter;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.extend.BaseDataAction;
import com.jiuqi.va.extend.BaseDataParamInterceptor;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(value=-2147483648)
@ConditionalOnProperty(name={"nvwa.basedata.selector.fullTextSearch"}, havingValue="true")
public class BaseDataSearchFilter
implements BaseDataParamInterceptor {
    private static String ignoreKey = "BaseDataSearchFilterKey";
    @Autowired
    private BaseDataClient baseDataClient;

    public void modify(BaseDataDTO param, BaseDataAction action) {
        if (action != BaseDataAction.Query || param.containsKey((Object)ignoreKey)) {
            return;
        }
        String searchKey = param.getSearchKey();
        if (!StringUtils.hasText(searchKey)) {
            return;
        }
        param.put(ignoreKey, (Object)true);
        param.put("_InterceptorDataList", this.filter(param));
    }

    private PageVO<BaseDataDO> filter(BaseDataDTO param) {
        Object searchFor = param.get((Object)"searchForColumns");
        if (searchFor == null) {
            return this.baseDataClient.list(param);
        }
        String searchKey = param.getSearchKey().trim().toUpperCase();
        param.setSearchKey(null);
        param.setPagination(Boolean.FALSE);
        PageVO page = this.baseDataClient.list(param);
        if (page.getTotal() == 0) {
            return new PageVO(true);
        }
        List cols = JSONUtil.parseArray((String)searchFor.toString(), DataModelColumn.class);
        String[] searchKeys = searchKey.split(" ");
        ArrayList<BaseDataDO> endList = new ArrayList<BaseDataDO>();
        boolean flag = false;
        Map showTitleMap = null;
        String colName = null;
        String colVal = null;
        for (BaseDataDO data : page.getRows()) {
            flag = false;
            showTitleMap = (Map)data.get((Object)"showTitleMap");
            for (DataModelColumn col : cols) {
                String jsonStr;
                colName = col.getColumnName().toLowerCase();
                if (data.get((Object)colName) == null) continue;
                colVal = null;
                if (showTitleMap != null && showTitleMap.get(colName) != null) {
                    colVal = ((String)showTitleMap.get(colName)).toUpperCase();
                } else if (data.get((Object)colName) != null && (jsonStr = JSONUtil.toJSONString((Object)data.get((Object)colName), (String)"yyyy-MM-dd HH:mm:ss")) != null) {
                    colVal = jsonStr.toUpperCase();
                }
                if (colVal == null) continue;
                for (String str : searchKeys) {
                    if (str.length() <= 0 || !colVal.contains(str)) continue;
                    flag = true;
                    break;
                }
                if (!flag) continue;
                break;
            }
            if (!flag) continue;
            endList.add(data);
        }
        int tatal = endList.size();
        page.setTotal(tatal);
        if (param.isPagination() && param.getLimit() > 0) {
            if (param.getOffset() < tatal) {
                int endNum = param.getOffset() + param.getLimit();
                endNum = endNum > tatal ? tatal : endNum;
                page.setRows(endList.subList(param.getOffset(), endNum));
            } else {
                page.setRows(new ArrayList());
            }
        } else {
            page.setRows(endList);
        }
        return page;
    }
}


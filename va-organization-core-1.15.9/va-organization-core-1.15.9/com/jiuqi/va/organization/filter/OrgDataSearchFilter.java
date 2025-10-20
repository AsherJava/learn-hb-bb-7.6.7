/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.extend.OrgDataParamInterceptor
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.extend.OrgDataParamInterceptor;
import com.jiuqi.va.feign.client.OrgDataClient;
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
@ConditionalOnProperty(name={"nvwa.organization.selector.fullTextSearch"}, havingValue="true")
public class OrgDataSearchFilter
implements OrgDataParamInterceptor {
    private static String ignoreKey = "OrgDataSearchFilterKey";
    @Autowired
    private OrgDataClient orgDataClient;

    public void modify(OrgDTO param, OrgDataAction action) {
        if (action != OrgDataAction.QUERY || param.containsKey((Object)ignoreKey)) {
            return;
        }
        String searchKey = param.getSearchKey();
        if (!StringUtils.hasText(searchKey)) {
            return;
        }
        param.put(ignoreKey, (Object)true);
        param.put("_InterceptorDataList", this.filter(param));
    }

    private PageVO<OrgDO> filter(OrgDTO param) {
        Object searchFor = param.get((Object)"searchForColumns");
        if (searchFor == null) {
            return this.orgDataClient.list(param);
        }
        String searchKey = param.getSearchKey().trim().toUpperCase();
        param.setSearchKey(null);
        param.setPagination(Boolean.FALSE);
        PageVO page = this.orgDataClient.list(param);
        if (page.getTotal() == 0) {
            return new PageVO(true);
        }
        List cols = JSONUtil.parseArray((String)searchFor.toString(), DataModelColumn.class);
        String[] searchKeys = searchKey.split(" ");
        ArrayList<OrgDO> endList = new ArrayList<OrgDO>();
        boolean flag = false;
        Map showTitleMap = null;
        String colName = null;
        String colVal = null;
        for (OrgDO data : page.getRows()) {
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


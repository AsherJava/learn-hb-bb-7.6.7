/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.portal.news2.factory;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.factory.IUnReadList;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IQueryPortalItemsFunction;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsUnReadList
implements IUnReadList {
    @Autowired
    public INews2Service news2Service;
    @Autowired
    public IQueryReadDao queryReadDao;
    public static String TYPE = "news";

    @Override
    public List<String> queryUnReadByMidAndPortalId(List<IQueryPortalItemsFunction> funList, String mid, String portalId, String design) {
        List<String> result = null;
        List collect = funList.stream().collect(Collectors.toList());
        if (collect.size() == 0) {
            List<NewsAbstractInfo> newsAbstractInfos = this.news2Service.queryNewsByMidAndPortalId(mid, portalId, "running");
            List<String> strings = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), this.getType());
            result = newsAbstractInfos.stream().filter(s -> !strings.contains(s.getId())).map(IBaseInfo::getId).collect(Collectors.toList());
        } else {
            List<IBaseInfo> newsList = ((IQueryPortalItemsFunction)collect.get(0)).queryPortalItems(mid, portalId, this.getType());
            List<String> strings = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), this.getType());
            result = newsList.stream().filter(s -> !strings.contains(s.getId())).map(IBaseInfo::getId).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}


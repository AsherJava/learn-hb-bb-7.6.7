/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.factory.UnReadListFactory;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nr.portal.news2.service.IQueryPortalItemsFunction;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import com.jiuqi.nr.portal.news2.service.IQueryUnRead;
import com.jiuqi.nr.portal.news2.service.PortalBeanUtil;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QueryUnReadImpl
implements IQueryUnRead {
    @Autowired
    public INews2Service news2Service;
    @Autowired
    public IPortalFileService portalFileService;
    @Autowired
    public IQueryReadDao queryReadDao;
    @Autowired
    private IPortalFileService fileService;

    @Override
    public List<String> queryUnReadByMidAndPortalId(String mid, String portalId, String type) {
        if (StringUtils.isEmpty(mid) || StringUtils.isEmpty(portalId) || StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u53ef\u4e3anull");
        }
        List<IQueryPortalItemsFunction> funList = PortalBeanUtil.getApplicationContext().getBeansOfType(IQueryPortalItemsFunction.class).values().stream().sorted(Comparator.comparingInt(IQueryPortalItemsFunction::getOrder)).collect(Collectors.toList());
        List<String> result = UnReadListFactory.getUnReadList(type).queryUnReadByMidAndPortalId(funList, mid, portalId, "running");
        return result;
    }

    @Override
    public Boolean quickRead(String mid, String portalId, String type) {
        if (StringUtils.isEmpty(mid) || StringUtils.isEmpty(portalId) || StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u53ef\u4e3anull");
        }
        List<String> unReadList = this.queryUnReadByMidAndPortalId(mid, portalId, type);
        this.queryReadDao.batchInsertReadItem(NpContextHolder.getContext().getIdentityId(), unReadList, type);
        if ("news".equals(type)) {
            this.news2Service.removeCache(mid, portalId, "running");
        } else if ("file".equals(type)) {
            this.fileService.removeCache(mid, portalId, "running");
        }
        return true;
    }
}


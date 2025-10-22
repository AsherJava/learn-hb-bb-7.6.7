/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.portal.news2.factory;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.factory.IUnReadList;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nr.portal.news2.service.IQueryPortalItemsFunction;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileUnReadList
implements IUnReadList {
    @Autowired
    public IPortalFileService portalFileService;
    @Autowired
    public IQueryReadDao queryReadDao;
    public static String TYPE = "file";

    @Override
    public List<String> queryUnReadByMidAndPortalId(List<IQueryPortalItemsFunction> funList, String mid, String portalId, String design) {
        List<String> result = null;
        List collectFile = funList.stream().collect(Collectors.toList());
        if (collectFile.size() == 0) {
            List<FileImpl> filesImplList = this.portalFileService.queryFileByMidAndPortalId(mid, portalId, "running");
            List<String> readList = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), this.getType());
            result = filesImplList.stream().filter(s -> !readList.contains(s.getId())).map(IBaseInfo::getId).collect(Collectors.toList());
        } else {
            List<IBaseInfo> filesList = ((IQueryPortalItemsFunction)collectFile.get(0)).queryPortalItems(mid, portalId, this.getType());
            List<String> readList = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), this.getType());
            result = filesList.stream().filter(s -> !readList.contains(s.getId())).map(IBaseInfo::getId).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.service.IGetEntityInfoService;
import com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GetEntityInfoServiceImpl
implements IGetEntityInfoService {
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDimInfoService iDimInfoService;
    @Value(value="${jiuqi.nr.dataentry.context.analysis:false}")
    private boolean analysisContext = false;

    @Override
    public List<IEntityDefine> getEntityInfoByTaskList(List<String> taskKeyList) {
        ArrayList<Object> iEntityDefineList = new ArrayList<IEntityDefine>();
        if (taskKeyList != null && taskKeyList.size() == 1) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            TaskOrgLinkListStream taskOrgLinkListStream = iRunTimeViewController.listTaskOrgLinkStreamByTask(taskKeyList.get(0));
            for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().getList()) {
                EntityDefineImpl iEntityDefine = new EntityDefineImpl();
                iEntityDefine.setId(taskOrgLinkDefine.getEntity());
                if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                    iEntityDefine.setTitle(taskOrgLinkDefine.getEntityAlias());
                } else {
                    iEntityDefine.setTitle(this.iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
                }
                iEntityDefineList.add((IEntityDefine)iEntityDefine);
            }
            if (iEntityDefineList.size() <= 1) {
                iEntityDefineList = new ArrayList();
                if (this.analysisContext) {
                    List priDimList = this.iDimInfoService.queryPriDimByTask(taskKeyList.get(0));
                    for (String priDim : priDimList) {
                        iEntityDefineList.add(this.iEntityMetaService.queryEntity(priDim));
                    }
                }
            }
        }
        if (iEntityDefineList.size() <= 1) {
            return new ArrayList<IEntityDefine>();
        }
        return iEntityDefineList;
    }
}


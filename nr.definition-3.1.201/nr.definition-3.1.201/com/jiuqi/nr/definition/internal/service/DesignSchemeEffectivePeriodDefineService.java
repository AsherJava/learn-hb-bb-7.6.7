/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignSchemePeriodLinkDao;
import com.jiuqi.nr.definition.internal.service.AbstractParamService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignSchemeEffectivePeriodDefineService
extends AbstractParamService {
    @Autowired
    private DesignSchemePeriodLinkDao designSchemePeriodLinkDao;

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) throws Exception {
        return this.designSchemePeriodLinkDao.queryByScheme(scheme);
    }

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByTask(String task) throws Exception {
        return this.designSchemePeriodLinkDao.queryByTask(task);
    }

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String period, String task) throws Exception {
        return this.designSchemePeriodLinkDao.queryLinkByPeriodAndTask(period, task);
    }

    public void deleteSchemePeriodLink(DesignSchemePeriodLinkDefine[] defines) throws Exception {
        if (null == defines || 0 == defines.length) {
            return;
        }
        this.designSchemePeriodLinkDao.delete(defines);
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.DELETE, Arrays.stream(defines).map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList()));
    }

    public void deleteByScheme(String scheme) throws Exception {
        this.designSchemePeriodLinkDao.deleteDataByScheme(scheme);
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(scheme));
    }

    public void inserSchemePeriodLink(DesignSchemePeriodLinkDefine[] defines) throws Exception {
        if (null == defines || 0 == defines.length) {
            return;
        }
        this.designSchemePeriodLinkDao.insert(defines);
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.ADD, Arrays.stream(defines).map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList()));
    }
}


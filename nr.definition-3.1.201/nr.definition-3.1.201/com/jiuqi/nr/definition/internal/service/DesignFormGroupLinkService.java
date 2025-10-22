/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormGroupLinkService {
    @Autowired
    private DesignFormGroupLinkDao formGroupLinkDao;

    public void deleteLink(DesignFormGroupLink define) throws Exception {
        this.formGroupLinkDao.deleteLink(define);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String formKey) throws Exception {
        return this.formGroupLinkDao.getFormGroupLinksByFormId(formKey);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String formGroupKey) throws Exception {
        return this.formGroupLinkDao.getFormGroupLinksByGroupId(formGroupKey);
    }

    public DesignFormGroupLink queryDesignFormGroupLink(String formKey, String formGroupKey) throws Exception {
        return this.formGroupLinkDao.queryDesignFormGroupLink(formKey, formGroupKey);
    }

    public List<DesignFormGroupLink> getFormGroupLinksByGroups(List<String> groupKeys) {
        return this.formGroupLinkDao.getFormGroupLinksByGroups(groupKeys);
    }

    public void insertLinks(DesignFormGroupLink[] designFormGroupLinks) throws Exception {
        for (DesignFormGroupLink designFormGroupLink : designFormGroupLinks) {
            this.formGroupLinkDao.insert(designFormGroupLink);
        }
    }

    public void updateLinks(DesignFormGroupLink[] designFormGroupLinks) throws Exception {
        for (DesignFormGroupLink designFormGroupLink : designFormGroupLinks) {
            this.formGroupLinkDao.updateLink(designFormGroupLink);
        }
    }

    public void deleteLinkByGroup(String formgroupKey) throws Exception {
        this.formGroupLinkDao.deleteLinkByGroup(formgroupKey);
    }

    public void deleteLinkByForm(String formKey) throws Exception {
        this.formGroupLinkDao.deleteLinkByForm(formKey);
    }
}


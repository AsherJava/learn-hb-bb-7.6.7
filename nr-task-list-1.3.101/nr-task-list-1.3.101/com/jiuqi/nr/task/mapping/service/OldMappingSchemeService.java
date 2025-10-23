/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.nr.task.mapping.service;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.mapping.dao.OldMappingSchemeDao;
import com.jiuqi.nr.task.mapping.dto.MappingSchemeDTO;
import com.jiuqi.nr.task.mapping.dto.MappingSchemeInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OldMappingSchemeService {
    @Autowired
    private OldMappingSchemeDao mappingSchemeDao;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    public MappingSchemeInfo get(String key) {
        MappingSchemeDTO schemeDTO = this.mappingSchemeDao.get(key);
        if (schemeDTO == null) {
            return null;
        }
        MappingSchemeInfo info = new MappingSchemeInfo();
        String schemeKey = schemeDTO.getSchemeKey();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(schemeKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        info.setKey(key);
        info.setTaskKey(task.getKey());
        info.setTaskTitle(task.getTitle());
        info.setFormSchemeKey(formScheme.getKey());
        info.setPeriodType(formScheme.getPeriodType().type());
        return info;
    }

    public boolean del(String key) {
        MappingSchemeDTO mappingSchemeDTO = this.mappingSchemeDao.get(key);
        this.mappingSchemeDao.del(key);
        List<MappingSchemeDTO> query = this.mappingSchemeDao.query(mappingSchemeDTO.getSchemeKey());
        return !CollectionUtils.isEmpty(query);
    }
}


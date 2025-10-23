/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.task.mapping;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import com.jiuqi.nr.task.mapping.MappingResourceProvider;
import com.jiuqi.nr.task.mapping.dao.OldMappingSchemeDao;
import com.jiuqi.nr.task.mapping.dto.MappingSchemeDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MappingResourceExt
extends AbstractFormSchemeResourceFactory {
    @Autowired
    private OldMappingSchemeDao mappingSchemeDao;
    public static final String CODE = "MAPPING_SCHEME";
    public static final String TYPE = "MAPPING_SCHEME";
    public static final String TITLE = "\u6620\u5c04\u65b9\u6848\uff08\u65e7\uff09";

    public String code() {
        return "MAPPING_SCHEME";
    }

    public String title() {
        return TITLE;
    }

    public double order() {
        return 4.0;
    }

    public ComponentDefine component() {
        return new ComponentDefine("mappingSchemeManage", "@nr", "mappingScheme");
    }

    public boolean enable(String formSchemeKey) {
        List<MappingSchemeDTO> query = this.mappingSchemeDao.query(formSchemeKey);
        return !CollectionUtils.isEmpty(query);
    }

    public IResourceDataProvider dataProvider() {
        return new MappingResourceProvider(this.mappingSchemeDao);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }
}


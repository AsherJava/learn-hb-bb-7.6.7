/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.organization.impl.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;
import com.jiuqi.gcreport.organization.impl.dao.OrgDisplaySchemeDao;
import com.jiuqi.gcreport.organization.impl.service.OrgDisplaySchemeService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgDisplaySchemeServiceImpl
implements OrgDisplaySchemeService {
    private static final String SYS_DISPLAY_SCHEME_KEY = "SYS_DEFAULT_DISPLAY_SCHEME";
    private static final String GLOBAL_USER_KEY = "out_of_user_settings";
    @Resource
    private OrgDisplaySchemeDao dao;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public OrgDisplaySchemeDO getCurrentDisplayScheme(String tableName) {
        if (StringUtils.isEmpty((String)tableName)) {
            return null;
        }
        String entityId = tableName + "@ORG";
        OrgDisplaySchemeDO displayScheme = this.dao.findByOwner(SYS_DISPLAY_SCHEME_KEY, entityId, GLOBAL_USER_KEY);
        if (displayScheme != null) {
            List<String> showFields = displayScheme.getFields();
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableName);
            List columns = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            List<String> attributes = showFields.stream().map(fdKey -> this.findIFMDMAttribute((String)fdKey, columns)).filter(Objects::nonNull).map(IModelDefineItem::getCode).collect(Collectors.toList());
            displayScheme.setFields(attributes);
        }
        return displayScheme;
    }

    private ColumnModelDefine findIFMDMAttribute(String fmdmKey, List<ColumnModelDefine> attributes) {
        return attributes.stream().filter(attr -> fmdmKey.equals(attr.getID())).findFirst().orElse(null);
    }
}


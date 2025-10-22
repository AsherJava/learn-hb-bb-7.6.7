/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.ext.version.IVersionQueryService;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class EntityAssistImpl
implements IEntityAssist {
    private static final String MINUSTYPE = "1";
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IVersionQueryService versionQueryService;
    @Autowired
    private AdapterService adapterService;

    @Override
    public String getMinusEntity(IContext executorContext, EntityViewDefine entityViewDefine, String parentKey, String periodCode) {
        try {
            IEntityQuery entityQuery = this.iEntityDataService.newEntityQuery();
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityViewDefine.getEntityId());
            IEntityAttribute minusField = entityModel.getBblxField();
            if (minusField == null) {
                return null;
            }
            entityQuery.setAuthorityOperations(AuthorityType.None);
            entityQuery.setEntityView(entityViewDefine);
            DimensionValueSet masterKeys = new DimensionValueSet();
            if (!StringUtils.isEmpty((CharSequence)periodCode)) {
                masterKeys.setValue("DATATIME", (Object)periodCode);
            }
            IEntityTable entityTable = entityQuery.executeReader(executorContext);
            List<IEntityRow> childRows = entityTable.getChildRows(parentKey);
            IEntityItem minusRow = null;
            for (IEntityRow childRow : childRows) {
                if (!this.isMinusRow(childRow, minusField)) continue;
                minusRow = childRow;
                break;
            }
            if (minusRow == null) {
                return null;
            }
            return minusRow.getEntityKeyData();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isMinusRow(IEntityRow childRow, IEntityAttribute attribute) throws RuntimeException, DataTypeException {
        String minusCode = childRow.getAsString(attribute.getCode());
        return StringUtils.equalsIgnoreCase((CharSequence)minusCode, (CharSequence)MINUSTYPE);
    }

    @Override
    public boolean judgementExpression(IContext executorContext, EntityViewDefine entityViewDefine, String entityKeyData) {
        IEntityTable iEntityTable;
        Assert.notNull((Object)entityViewDefine, "\u5b9e\u4f53\u89c6\u56fe\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)entityKeyData, "\u5b9e\u4f53\u6570\u636e\u884c\u4e0d\u80fd\u4e3a\u7a7a");
        IEntityQuery entityQuery = this.iEntityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        try {
            iEntityTable = entityQuery.executeReader(executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u6784\u5efa\u5b9e\u4f53\u6570\u636e\u67e5\u8be2\u5668\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return iEntityTable.findByEntityKey(entityKeyData) != null;
    }

    @Override
    public Map<Boolean, Set<String>> filterExpression(IContext executorContext, EntityViewDefine entityViewDefine, Set<String> entityKeyDatas) {
        IEntityTable iEntityTable;
        Assert.notNull((Object)entityViewDefine, "\u5b9e\u4f53\u89c6\u56fe\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!CollectionUtils.isEmpty(entityKeyDatas), "\u5b9e\u4f53\u6570\u636e\u884c\u4e0d\u80fd\u4e3a\u7a7a");
        IEntityQuery entityQuery = this.iEntityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        try {
            iEntityTable = entityQuery.executeReader(executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u6784\u5efa\u5b9e\u4f53\u6570\u636e\u67e5\u8be2\u5668\u5f02\u5e38\uff1a" + e.getMessage());
        }
        Map<String, IEntityRow> queryMap = iEntityTable.findByEntityKeys(entityKeyDatas);
        HashMap<Boolean, Set<String>> result = new HashMap<Boolean, Set<String>>(2);
        for (String entityKeyData : entityKeyDatas) {
            IEntityRow entityRow = queryMap.get(entityKeyData);
            if (entityRow == null) {
                result.computeIfAbsent(Boolean.FALSE, key -> new HashSet()).add(entityKeyData);
                continue;
            }
            result.computeIfAbsent(Boolean.TRUE, key -> new HashSet()).add(entityKeyData);
        }
        return result;
    }

    @Override
    public boolean isDBModel(String entityId, String dataTime, String periodView) {
        DimensionValueSet masterKey = new DimensionValueSet();
        masterKey.setValue("DATATIME", (Object)dataTime);
        Date versionDate = this.versionQueryService.getVersionDate(masterKey, periodView);
        return this.adapterService.getEntityAdapter(entityId).isDBMode(EntityUtils.getId((String)entityId), versionDate);
    }

    @Override
    public boolean isEntity(String entityId) {
        return this.adapterService.getEntityAdapter(entityId) != null;
    }
}


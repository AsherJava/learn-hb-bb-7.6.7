/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDefineQuery
 *  com.jiuqi.nr.entity.adapter.provider.DefineDTO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.subdatabase.org.ext;

import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDefineQuery;
import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.subdatabase.facade.SubDataBaseEntityIdProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.List;
import org.springframework.util.StringUtils;

public class OrgDefineSubTableQueryProvider
extends OrgDefineQuery {
    private final SubDataBaseEntityIdProvider subDataBaseEntityIdProvider;

    public OrgDefineSubTableQueryProvider(OrgCategoryClient orgCategoryClient, DataModelClient vaDataModelClient, DataModelService dataModelService, SubDataBaseEntityIdProvider subDataBaseEntityIdProvider) {
        super(orgCategoryClient, vaDataModelClient, dataModelService);
        this.subDataBaseEntityIdProvider = subDataBaseEntityIdProvider;
    }

    public List<IEntityDefine> query(DefineDTO defineDTO) {
        DefineDTO query = new DefineDTO();
        if (defineDTO != null) {
            query.setTableName(this.getEntityId(defineDTO.getTableName()));
            query.setKeyWords(defineDTO.getKeyWords());
            query.setGroup(defineDTO.getGroup());
        }
        return super.query(query);
    }

    public IEntityDefine get(String tableName) {
        return super.get(this.getEntityId(tableName));
    }

    public DataModelDO getModel(String tableName) {
        return super.getModel(this.getEntityId(tableName));
    }

    public TableModelDefine getNvwaModel(String tableName) {
        return super.getNvwaModel(this.getEntityId(tableName));
    }

    private String getEntityId(String entityId) {
        if (this.subDataBaseEntityIdProvider == null) {
            return entityId;
        }
        String subDataBaseEntityId = this.subDataBaseEntityIdProvider.getSubDataBaseEntityId(entityId, null);
        if (!StringUtils.hasText(subDataBaseEntityId) || subDataBaseEntityId.equals(entityId)) {
            return entityId;
        }
        return subDataBaseEntityId;
    }
}


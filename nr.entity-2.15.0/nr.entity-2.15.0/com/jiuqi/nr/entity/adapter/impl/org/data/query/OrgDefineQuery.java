/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.entity.adapter.impl.org.data.query;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.CommonAdapter;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgConvertUtil;
import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class OrgDefineQuery
extends CommonAdapter
implements IDefineQueryProvider {
    protected final OrgCategoryClient orgCategoryClient;
    protected final DataModelClient vaDataModelClient;
    protected final DataModelService dataModelService;

    public OrgDefineQuery(OrgCategoryClient orgCategoryClient, DataModelClient vaDataModelClient, DataModelService dataModelService) {
        super(dataModelService);
        this.orgCategoryClient = orgCategoryClient;
        this.vaDataModelClient = vaDataModelClient;
        this.dataModelService = dataModelService;
    }

    @Override
    public List<IEntityDefine> query(DefineDTO defineDTO) {
        ArrayList<IEntityDefine> defines = new ArrayList<IEntityDefine>();
        if (null != defineDTO && StringUtils.hasText(defineDTO.getTableName())) {
            PageVO<OrgCategoryDO> list = this.getOrgDefine(defineDTO.getTableName());
            if (list.getTotal() > 0) {
                list.getRows().forEach(v -> defines.add(OrgConvertUtil.entityConvert(v)));
            }
        } else {
            PageVO list;
            OrgCategoryDO dto = new OrgCategoryDO();
            Locale locale = NpContextHolder.getContext().getLocale();
            if (locale != null && !Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
                dto.addExtInfo("languageTransFlag", (Object)true);
            }
            if ((list = this.orgCategoryClient.list(dto)).getTotal() > 0) {
                list.getRows().forEach(v -> defines.add(OrgConvertUtil.entityConvert(v)));
            }
        }
        return defines;
    }

    @Override
    public IEntityDefine get(String tableName) {
        PageVO<OrgCategoryDO> list = this.getOrgDefine(tableName);
        if (list.getTotal() != 1) {
            return null;
        }
        return OrgConvertUtil.entityConvert((OrgCategoryDO)list.getRows().get(0));
    }

    private PageVO<OrgCategoryDO> getOrgDefine(String tableName) {
        OrgCategoryDO orgCategory = new OrgCategoryDO();
        orgCategory.setName(tableName);
        Locale locale = NpContextHolder.getContext().getLocale();
        if (locale != null && !Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            orgCategory.addExtInfo("languageTransFlag", (Object)true);
        }
        return this.orgCategoryClient.list(orgCategory);
    }

    @Override
    public DataModelDO getModel(String tableName) {
        DataModelDTO param = new DataModelDTO();
        param.setName(tableName);
        return this.vaDataModelClient.get(param);
    }

    @Override
    public TableModelDefine getNvwaModel(String tableName) {
        return this.dataModelService.getTableModelDefineByCode(tableName);
    }

    @Override
    public Map<String, IEntityAttribute> getAttributes(String tableName) {
        TableModelDefine table = this.getNvwaModel(tableName);
        PageVO<OrgCategoryDO> orgDefine = this.getOrgDefine(tableName);
        if (orgDefine.getTotal() != 1) {
            return Collections.emptyMap();
        }
        Map<String, String> i18nMap = ((OrgCategoryDO)orgDefine.getRows().get(0)).getAllZbs().stream().collect(Collectors.toMap(ZB::getName, ZB::getTitle, (z1, z2) -> z2));
        List<IEntityAttribute> columns = super.getNvwaAttributes(table.getID());
        List attributes = columns.stream().map(v -> OrgConvertUtil.fieldConvert(v, table.getBizKeys(), i18nMap)).collect(Collectors.toList());
        LinkedHashMap<String, IEntityAttribute> linkedHashMap = new LinkedHashMap<String, IEntityAttribute>();
        for (IEntityAttribute attribute : attributes) {
            linkedHashMap.put(attribute.getCode(), attribute);
        }
        return linkedHashMap;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.data;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.CommonAdapter;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.CommonBaseDataQuery;
import com.jiuqi.nr.entity.adapter.impl.basedata.data.DefineQueryHelper;
import com.jiuqi.nr.entity.adapter.provider.DefineDTO;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class BaseDataDefineQuery
extends CommonAdapter
implements IDefineQueryProvider {
    protected final BaseDataDefineClient baseDataDefineClient;
    protected final DataModelClient vaDataModelClient;
    protected final DataModelService dataModelService;

    public BaseDataDefineQuery(BaseDataDefineClient baseDataDefineClient, DataModelClient vaDataModelClient, DataModelService dataModelService) {
        super(dataModelService);
        this.baseDataDefineClient = baseDataDefineClient;
        this.vaDataModelClient = vaDataModelClient;
        this.dataModelService = dataModelService;
    }

    @Override
    public List<IEntityDefine> query(DefineDTO defineDTO) {
        ArrayList<IEntityDefine> defines = new ArrayList<IEntityDefine>();
        if (null != defineDTO && StringUtils.hasText(defineDTO.getTableName())) {
            IEntityDefine entityDefine = this.get(defineDTO.getTableName());
            if (entityDefine != null) {
                defines.add(entityDefine);
            }
        } else {
            PageVO list;
            BaseDataDefineDTO dto = new BaseDataDefineDTO();
            Locale locale = NpContextHolder.getContext().getLocale();
            if (locale != null && !Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
                dto.addExtInfo("languageTransFlag", (Object)true);
            }
            if (null != defineDTO) {
                dto.setGroupname(defineDTO.getGroup());
                dto.setSearchKey(defineDTO.getKeyWords());
            }
            if ((list = this.baseDataDefineClient.list(dto)).getTotal() > 0) {
                list.getRows().forEach(v -> {
                    if (!CommonBaseDataQuery.isDummy(v.getDummyflag())) {
                        defines.add(CommonBaseDataQuery.convertDefine2EntityDefine(v));
                    }
                });
            }
        }
        return defines;
    }

    @Override
    public IEntityDefine get(String tableName) {
        if (!StringUtils.hasText(tableName)) {
            return null;
        }
        BaseDataDefineDO baseDataDefineDO = this.getDefineDO(tableName);
        if (baseDataDefineDO != null && CommonBaseDataQuery.isDummy(baseDataDefineDO.getDummyflag())) {
            return null;
        }
        return CommonBaseDataQuery.convertDefine2EntityDefine(baseDataDefineDO);
    }

    private BaseDataDefineDO getDefineDO(String tableName) {
        BaseDataDefineDTO dto = new BaseDataDefineDTO();
        dto.setName(tableName);
        Locale locale = NpContextHolder.getContext().getLocale();
        if (locale != null && !Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            dto.addExtInfo("languageTransFlag", (Object)true);
        }
        return this.baseDataDefineClient.get(dto);
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
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableName);
        BaseDataDefineDO baseDataDefine = this.getDefineDO(tableName);
        if (tableModelDefine == null || baseDataDefine == null) {
            return null;
        }
        Map<String, DefineQueryHelper.BaseDataField> baseDataShowFields = DefineQueryHelper.getBaseDataShowFields(baseDataDefine.getDefine());
        List<IEntityAttribute> attributes = this.getNvwaAttributes(tableModelDefine.getID());
        Optional<IEntityAttribute> finBizField = attributes.stream().filter(e -> e.getCode().equals("OBJECTCODE")).findFirst();
        List entityAttributes = attributes.stream().map(e -> DefineQueryHelper.convertColumn2Attribute(e, ((IEntityAttribute)finBizField.get()).getID(), baseDataShowFields)).collect(Collectors.toList());
        LinkedHashMap<String, IEntityAttribute> linkedHashMap = new LinkedHashMap<String, IEntityAttribute>();
        for (IEntityAttribute attribute : entityAttributes) {
            linkedHashMap.put(attribute.getCode(), attribute);
        }
        return linkedHashMap;
    }
}


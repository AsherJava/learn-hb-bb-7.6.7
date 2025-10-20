/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider
 *  com.jiuqi.dc.mappingscheme.impl.define.IOrgMappingTypeProvider
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.plugin.common.adaptor;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IOrgMappingTypeProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMappingProvider
implements IFieldMappingProvider,
IOrgMappingTypeProvider {
    @Autowired
    protected DataSourceService dataSourceService;

    public abstract IPluginType getPluginType();

    public String getCode() {
        return this.getPluginType().getSymbol() + "|" + MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public String getName() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getName();
    }

    public String getEffectTable() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public List<FieldDTO> listOdsField(DataSchemeDTO dataSchemeDTO) {
        return CollectionUtils.newArrayList();
    }

    public Integer showOrder() {
        return 100;
    }
}


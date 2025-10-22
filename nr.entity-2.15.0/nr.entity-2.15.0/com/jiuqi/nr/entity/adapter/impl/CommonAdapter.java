/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.entity.adapter.impl;

import com.jiuqi.nr.entity.exception.NotExistNvwaColumnException;
import com.jiuqi.nr.entity.internal.model.impl.EntityAttributeImpl;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class CommonAdapter {
    protected DataModelService dataModelService;

    public CommonAdapter(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public List<IEntityAttribute> getNvwaAttributes(String tableId) {
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        if (CollectionUtils.isEmpty(columns)) {
            throw new NotExistNvwaColumnException("\u5973\u5a32\u5b9a\u4e49\u4e3a\uff1a" + tableId);
        }
        return columns.stream().sorted(Comparator.comparing(ColumnModelDefine::getOrder)).map(e -> EntityAttributeImpl.transferFromColumn(e)).collect(Collectors.toList());
    }
}


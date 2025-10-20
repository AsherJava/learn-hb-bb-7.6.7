/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConstTransferFieldMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Override
    public RuleType getRuleType() {
        return RuleType.CONST_TRANSFER;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String fieldTemplate = "'%1$s' AS %2$s, \n";
        return String.format(fieldTemplate, item.getOdsFieldName(), item.getFieldName());
    }

    @Override
    public String buildOdsFieldSql(FieldMappingDefineDTO item) {
        return String.format("'%1$s'", item.getOdsFieldName());
    }

    @Override
    public String buildJoinSql(FieldMappingDefineDTO item) {
        return "";
    }

    @Override
    public String buildGroupSql(FieldMappingDefineDTO item) {
        return "";
    }
}


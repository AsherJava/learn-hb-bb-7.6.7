/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityDefaultValueFilter
implements RowFilter {
    private RegionRelation relation;

    public EntityDefaultValueFilter(RegionRelation relation) {
        this.relation = relation;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean supportFormula() {
        return true;
    }

    @Override
    public String toFormula() {
        List entityDefaultValue;
        RegionSettingDefine regionSettingDefine = this.relation.getRegionSettingDefine();
        if (regionSettingDefine != null && !CollectionUtils.isEmpty(entityDefaultValue = regionSettingDefine.getEntityDefaultValue())) {
            ArrayList<String> filter = new ArrayList<String>();
            for (EntityDefaultValue defaultValue : entityDefaultValue) {
                List deploys;
                String fieldKey = defaultValue.getFieldKey();
                String itemValue = defaultValue.getItemValue();
                if (!StringUtils.hasLength(itemValue) || CollectionUtils.isEmpty(deploys = this.relation.getRuntimeDataSchemeService().getDeployInfoByDataFieldKeys(new String[]{fieldKey}))) continue;
                DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)deploys.get(0);
                String tableName = dataFieldDeployInfo.getTableName();
                String fieldName = dataFieldDeployInfo.getFieldName();
                filter.add(tableName + "[" + fieldName + "] = '" + itemValue + "'");
            }
            return String.join((CharSequence)" AND ", filter);
        }
        return null;
    }

    @Override
    public boolean filter(String formula, IContext context) throws SyntaxException {
        return true;
    }
}


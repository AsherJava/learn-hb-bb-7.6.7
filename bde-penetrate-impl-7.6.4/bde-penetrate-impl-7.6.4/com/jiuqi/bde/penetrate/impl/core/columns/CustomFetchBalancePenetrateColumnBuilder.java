/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.Assert
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateColumnBuilder;
import com.jiuqi.common.base.util.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchBalancePenetrateColumnBuilder
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;
    @Autowired
    private BizModelServiceGather bizModelServiceGather;

    @Override
    public String getBizModel() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        String fetchSourceCode = condi.getFetchSourceCode();
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode()).getByCode(fetchSourceCode);
        List selectFieldList = customBizModelDTO.getSelectFieldList();
        Assert.isNotEmpty((Collection)selectFieldList, (String)"\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u7684\u53d6\u6570\u5b57\u6bb5\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        List customConditions = customBizModelDTO.getCustomConditions();
        if (CollectionUtils.isEmpty((Collection)customConditions)) {
            this.addSelectFieldToColumnList(selectFieldList, columns);
        } else {
            Set fileCodeSet = selectFieldList.stream().map(SelectField::getFieldCode).collect(Collectors.toSet());
            for (CustomCondition customCondition : customConditions) {
                String paramsCode = customCondition.getParamsCode();
                if (CollectionUtils.containsAny(fileCodeSet, (Object[])new String[]{paramsCode})) continue;
                PenetrateColumn cloumn = this.createStringCloumn(paramsCode, customCondition.getParamsName());
                cloumn.setWidth(null);
                columns.add(cloumn);
            }
            this.addSelectFieldToColumnList(selectFieldList, columns);
        }
        return columns;
    }

    private void addSelectFieldToColumnList(List<SelectField> selectFieldList, List<PenetrateColumn> columns) {
        for (SelectField selectField : selectFieldList) {
            AggregateFuncEnum func = AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode());
            PenetrateColumn penetrateColumn = AggregateFuncEnum.ORIGINAL == func ? this.createStringCloumn(selectField.getFieldCode(), selectField.getFieldName()) : (AggregateFuncEnum.COUNT == func ? this.createIntCloumn(selectField.getFieldCode(), selectField.getFieldName()) : this.createNumericCloumn(selectField.getFieldCode(), selectField.getFieldName()));
            penetrateColumn.setWidth(null);
            columns.add(penetrateColumn);
        }
    }
}


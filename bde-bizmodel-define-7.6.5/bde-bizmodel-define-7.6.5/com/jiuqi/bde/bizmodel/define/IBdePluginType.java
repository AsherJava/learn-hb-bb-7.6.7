/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 */
package com.jiuqi.bde.bizmodel.define;

import com.jiuqi.bde.bizmodel.define.adaptor.option.EnableTemporaryTableOption;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class IBdePluginType
implements IPluginType {
    @Autowired
    private EnableTemporaryTableOption enableTransTableOption;

    public boolean supportPenetrateShowMultiOrg() {
        return false;
    }

    public List<SelectOptionVO> isolationStrategyList() {
        ArrayList<SelectOptionVO> isolationStrategyList = new ArrayList<SelectOptionVO>();
        isolationStrategyList.add(new SelectOptionVO(IsolationStrategy.SHARE.getCode(), IsolationStrategy.SHARE.getName()));
        return isolationStrategyList;
    }

    public final List<IDataSchemeOption> getOptionList() {
        ArrayList optionList = CollectionUtils.newArrayList();
        optionList.add(this.enableTransTableOption);
        List<IDataSchemeOption> externalOptionList = this.getExternalOptionList();
        if (!CollectionUtils.isEmpty(externalOptionList)) {
            optionList.addAll(externalOptionList);
        }
        return optionList;
    }

    protected List<IDataSchemeOption> getExternalOptionList() {
        return CollectionUtils.newArrayList();
    }

    protected FieldDTO buildBasicField(String name, String title) {
        FieldDTO basicField = new FieldDTO();
        basicField.setName(name);
        basicField.setTitle(title);
        basicField.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
        basicField.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
        basicField.setIsolationStrategyFixedFlag(Boolean.valueOf(false));
        basicField.setOdsFieldFixedFlag(Boolean.valueOf(true));
        return basicField;
    }
}


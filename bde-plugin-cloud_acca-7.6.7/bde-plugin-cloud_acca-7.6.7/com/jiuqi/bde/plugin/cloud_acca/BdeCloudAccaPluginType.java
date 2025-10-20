/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.cloud_acca;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistPojo;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistProvider;
import com.jiuqi.bde.plugin.cloud_acca.option.BdeCloudAccaSsoAppIdOption;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaPluginType
extends IBdePluginType {
    @Autowired
    private CloudAccaAssistProvider assistProvider;
    @Autowired
    private BdeCloudAccaSsoAppIdOption egasSsoAppIdOption;
    private static final String SYMBOL = "CLOUD_ACCA";
    private static final String TITLE = "\u3010\u4e45\u5176\u3011\u4e91\u6838\u7b97";

    protected List<IDataSchemeOption> getExternalOptionList() {
        ArrayList optionList = CollectionUtils.newArrayList();
        optionList.add(this.egasSsoAppIdOption);
        return optionList;
    }

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.JIUQI.getSymbol();
    }

    public Integer getOrder() {
        return 240;
    }

    public String storageType() {
        return StorageType.CODE.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        Map<String, CloudAccaAssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1));
        for (CloudAccaAssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = this.buildBasicField(assistPojo.getCode(), assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getCode());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(String.format("SELECT ID AS ID,CODE AS CODE,NAME AS NAME FROM %1$s", assistPojo.getCode()));
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}


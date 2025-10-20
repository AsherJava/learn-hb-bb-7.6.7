/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.SourceDataTypeEnum
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.base.common.enums.SourceDataTypeEnum;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import java.util.ArrayList;
import java.util.List;

public interface IPluginType {
    public String getSymbol();

    public String getTitle();

    public String getLicenceSymbol();

    default public List<SelectOptionVO> sourceDataType() {
        ArrayList<SelectOptionVO> selectOptionVOList = new ArrayList<SelectOptionVO>();
        selectOptionVOList.add(new SelectOptionVO(SourceDataTypeEnum.DIRECT_TYPE.getCode(), SourceDataTypeEnum.DIRECT_TYPE.getName()));
        return selectOptionVOList;
    }

    default public Boolean needEtlJob() {
        return false;
    }

    public List<SelectOptionVO> isolationStrategyList();

    default public List<IDataSchemeOption> getOptionList() {
        return null;
    }

    public Integer getOrder();

    public String storageType();

    public FieldDTO subjectField(DataSchemeDTO var1);

    public FieldDTO currencyField(DataSchemeDTO var1);

    public FieldDTO cfItemField(DataSchemeDTO var1);

    public List<FieldDTO> listAssistField(DataSchemeDTO var1);

    default public List<SelectOptionVO> getBizDataConvertList() {
        ArrayList<SelectOptionVO> list = new ArrayList<SelectOptionVO>();
        list.add(new SelectOptionVO("BizBalanceInitConvert", "\u671f\u521d"));
        list.add(new SelectOptionVO("BizVoucherConvert", "\u51ed\u8bc1"));
        return list;
    }

    default public boolean assistFieldFlag() {
        return true;
    }

    default public String getCfSource() {
        return null;
    }
}


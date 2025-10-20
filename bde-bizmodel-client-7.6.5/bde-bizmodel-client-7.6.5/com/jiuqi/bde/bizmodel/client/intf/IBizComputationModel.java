/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.bde.bizmodel.client.intf;

import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.ArrayList;
import java.util.List;

public interface IBizComputationModel {
    public String getCode();

    public String getName();

    public String getIcon();

    public String getBizDataCode();

    public int getOrder();

    public List<SelectOptionVO> getFetchTypes();

    public List<SelectOptionVO> getDimensions();

    public List<ColumnDefineVO> getFixedFields();

    public List<SelectOptionVO> getOptionItems();

    public String getOptimizeRuleGroup(FetchSettingVO var1);

    public String getMemo(FetchSettingVO var1);

    default public ColumnDefineVO buildSignColumn() {
        ArrayList<SelectOptionVO> signSelectOptions = new ArrayList<SelectOptionVO>();
        signSelectOptions.add(new SelectOptionVO("+", "+"));
        signSelectOptions.add(new SelectOptionVO("-", "-"));
        ColumnDefineVO column = new ColumnDefineVO(FetchFixedFieldEnum.SIGN.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.sign"), true, "SINGLE", "SELECT", "+", signSelectOptions);
        column.setWidth(Integer.valueOf(80));
        return column;
    }

    default public String buildGenericMemo(String label, String value) {
        if (StringUtils.isEmpty((String)value)) {
            return "";
        }
        if (value.contains(":") || value.contains(",")) {
            return String.format("%1$s\"%2$s\",", label, value);
        }
        return String.format("%1$s%2$s,", label, value);
    }

    default public List<SelectOptionVO> getOptionItemsByOptionItemEnums(List<OptionItemEnum> optionItemEnums) {
        if (CollectionUtils.isEmpty(optionItemEnums)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<SelectOptionVO> optionsItems = new ArrayList<SelectOptionVO>();
        optionItemEnums.forEach(optionItemEnum -> optionsItems.add(new SelectOptionVO(optionItemEnum.getCode(), optionItemEnum.getName())));
        return optionsItems;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.OptionItemEnum
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.OptionItemEnum;
import java.util.ArrayList;
import java.util.List;

public class BizModelParameterConvertUtils {
    public static List<SelectOptionVO> getFetchTypesByFetchTtypeEnums(List<FetchTypeEnum> fetchTypeEnums) {
        if (CollectionUtils.isEmpty(fetchTypeEnums)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<SelectOptionVO> fetchTypes = new ArrayList<SelectOptionVO>();
        fetchTypeEnums.forEach(fetchTypeEnum -> fetchTypes.add(new SelectOptionVO(fetchTypeEnum.getCode(), fetchTypeEnum.getName())));
        return fetchTypes;
    }

    public static List<SelectOptionVO> getOptionItemsByOptionItemEnums(List<OptionItemEnum> optionItemEnums) {
        if (CollectionUtils.isEmpty(optionItemEnums)) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<SelectOptionVO> optionsItems = new ArrayList<SelectOptionVO>();
        optionItemEnums.forEach(optionItemEnum -> optionsItems.add(new SelectOptionVO(optionItemEnum.getCode(), optionItemEnum.getName())));
        return optionsItems;
    }
}


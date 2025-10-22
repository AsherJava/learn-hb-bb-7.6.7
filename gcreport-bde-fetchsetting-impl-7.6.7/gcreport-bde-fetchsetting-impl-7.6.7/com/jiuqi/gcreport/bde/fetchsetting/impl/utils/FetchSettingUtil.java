/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.vo.Dimension
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FetchSettingUtil {
    public static List<FixedAdaptSettingVO> convertFixedSettingDataStr(String fixedSettingDataStr) {
        if (StringUtils.isEmpty((String)fixedSettingDataStr)) {
            return new ArrayList<FixedAdaptSettingVO>();
        }
        return (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FixedAdaptSettingVO>>(){});
    }

    public static List<String> getDimensions(BizModelAllPropsDTO bizModelDTO) {
        if (CollectionUtils.isEmpty((Collection)bizModelDTO.getDimensions())) {
            return new ArrayList<String>();
        }
        return bizModelDTO.getDimensions().stream().map(Dimension::getDimensionCode).collect(Collectors.toList());
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.relate.handler.IQueryRelateHandler
 */
package com.jiuqi.va.query.relate.plugin;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.relate.constants.SSORelateConst;
import com.jiuqi.va.query.relate.dto.SSORelateParamDTO;
import com.jiuqi.va.query.relate.handler.IQueryRelateHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SSORelateHandler
implements IQueryRelateHandler {
    public static final String PROCESSOR_NAME = "SSORelate";

    public String getName() {
        return PROCESSOR_NAME;
    }

    public String getTitle() {
        return "\u5355\u70b9\u8054\u67e5";
    }

    public List getQueryParams(String relateConfigStr) {
        ArrayList list = new ArrayList();
        if (!StringUtils.hasText(relateConfigStr)) {
            return list;
        }
        SSORelateParamDTO ssoRelateParamDTO = (SSORelateParamDTO)JSONUtil.parseObject((String)relateConfigStr, SSORelateParamDTO.class);
        if (Objects.isNull(ssoRelateParamDTO)) {
            return list;
        }
        return SSORelateConst.getRelateQueryBillParamMap();
    }
}


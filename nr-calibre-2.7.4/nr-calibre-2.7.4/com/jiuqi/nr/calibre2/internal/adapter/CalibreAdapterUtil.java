/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;

public class CalibreAdapterUtil {
    public static String getEntityIdByCalibreId(String calibreDefineKey) {
        ICalibreDefineService calibreDefineService = (ICalibreDefineService)SpringBeanUtils.getBean(ICalibreDefineService.class);
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setKey(calibreDefineKey);
        Result<CalibreDefineDTO> result = calibreDefineService.get(calibreDefineDTO);
        if (result.getCode() == 0) {
            throw new RuntimeException(String.format("\u6839\u636e%s\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u53e3\u5f84\u5b9a\u4e49", calibreDefineKey));
        }
        return CalibreAdapterUtil.getEntityIdByCalibreCode(result.getData().getCode());
    }

    public static String getEntityIdByCalibreCode(String calibreDefineCode) {
        return calibreDefineCode + "@" + "CB";
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.file.dto.CommonFileClearDTO
 */
package com.jiuqi.common.file.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.file.dto.CommonFileClearDTO;
import com.jiuqi.common.file.service.CommonFileService;
import java.util.List;

public class CommonFileClearUtils {
    public static void deleteFileToOss(List<CommonFileClearDTO> commonFileClearDTOS) {
        if (CollectionUtils.isEmpty(commonFileClearDTOS)) {
            return;
        }
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        commonFileClearDTOS.forEach(commonFileClearDTO -> {
            String ossBucket = commonFileClearDTO.getOssBucket();
            String ossFileKey = commonFileClearDTO.getOssFileKey();
            if (StringUtils.isEmpty((String)ossBucket) || StringUtils.isEmpty((String)ossFileKey)) {
                return;
            }
            commonFileService.deleteOssFile(ossBucket, ossFileKey);
        });
    }
}


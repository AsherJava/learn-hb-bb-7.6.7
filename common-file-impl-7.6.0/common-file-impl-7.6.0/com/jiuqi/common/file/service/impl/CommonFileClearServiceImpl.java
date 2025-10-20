/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.file.dto.CommonFileClearDTO
 */
package com.jiuqi.common.file.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.file.dao.CommonFileClearDao;
import com.jiuqi.common.file.dto.CommonFileClearDTO;
import com.jiuqi.common.file.entity.CommonFileClearEO;
import com.jiuqi.common.file.service.CommonFileClearService;
import com.jiuqi.common.file.util.CommonFileClearUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonFileClearServiceImpl
implements CommonFileClearService {
    @Autowired
    private CommonFileClearDao fileClearDao;

    @Override
    public boolean clearExpiredFiles() {
        List<CommonFileClearEO> fileClearEOS = this.fileClearDao.queryExpiredFiles();
        if (CollectionUtils.isEmpty(fileClearEOS)) {
            return true;
        }
        List<CommonFileClearDTO> commonFileClearDTOS = fileClearEOS.stream().map(fileClearEO -> {
            CommonFileClearDTO commonFileClearDTO = new CommonFileClearDTO();
            return commonFileClearDTO;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        CommonFileClearUtils.deleteFileToOss(commonFileClearDTOS);
        this.fileClearDao.deleteBatch(fileClearEOS);
        return true;
    }

    @Override
    public boolean addFileClearData(List<CommonFileClearEO> clearEOS) {
        if (CollectionUtils.isEmpty(clearEOS)) {
            return true;
        }
        this.fileClearDao.addBatch(clearEOS);
        return true;
    }
}


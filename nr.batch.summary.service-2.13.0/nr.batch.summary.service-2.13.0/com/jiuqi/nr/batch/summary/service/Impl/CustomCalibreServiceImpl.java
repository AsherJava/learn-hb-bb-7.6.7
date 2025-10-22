/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.CustomCalibreDao
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.Impl;

import com.jiuqi.nr.batch.summary.service.CustomCalibreService;
import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CustomCalibreServiceImpl
implements CustomCalibreService {
    @Resource
    private CustomCalibreDao customCalibreDao;

    @Override
    public SchemeServiceState saveCustomCalibreRows(SummarySchemeDefine scheme, List<CustomCalibreRow> rows) {
        List<CustomCalibreRow> rowDefines = rows.stream().filter(CustomCalibreRow::isValidRow).collect(Collectors.toList());
        if (rowDefines.isEmpty()) {
            return SchemeServiceState.INVALID_TARGET_CONDITION_DIM;
        }
        rowDefines.forEach(r -> {
            CustomCalibreRowDefine rw = (CustomCalibreRowDefine)r;
            rw.setKey(UUID.randomUUID().toString());
            rw.setScheme(scheme.getKey());
            rw.setOrdinal(System.currentTimeMillis() + "");
        });
        int[] lens = this.customCalibreDao.insertRows((SummaryScheme)scheme, rowDefines);
        return lens.length > 0 ? SchemeServiceState.SUCCESS : SchemeServiceState.FAIL;
    }

    @Override
    public SchemeServiceState modifyCustomCalibreRows(SummarySchemeDefine scheme, List<CustomCalibreRow> rows) {
        this.deleteCustomCalibreRows((SummaryScheme)scheme);
        return this.saveCustomCalibreRows(scheme, rows);
    }

    @Override
    public SchemeServiceState deleteCustomCalibreRows(SummaryScheme scheme) {
        int lens = this.customCalibreDao.deleteRows(scheme.getKey());
        return lens > 0 ? SchemeServiceState.SUCCESS : SchemeServiceState.FAIL;
    }
}


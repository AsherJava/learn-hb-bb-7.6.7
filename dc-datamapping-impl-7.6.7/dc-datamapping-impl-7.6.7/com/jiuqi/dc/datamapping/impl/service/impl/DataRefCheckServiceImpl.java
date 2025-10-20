/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataRefCheckService
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;
import com.jiuqi.dc.datamapping.impl.gather.IDataRefCheckerGather;
import com.jiuqi.dc.datamapping.impl.intf.IDataRefChecker;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.impl.service.DataRefCheckService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRefCheckServiceImpl
implements DataRefCheckService {
    @Autowired
    private IsolateRefDefineCacheProvider cacheProvider;
    @Autowired
    private IDataRefCheckerGather checkerGather;

    public void orgRefCheck(String dataSchemeCode) {
        List<DataRefDTO> orgList = this.cacheProvider.getBaseMappingCache(new IsolationParamContext(dataSchemeCode), "MD_ORG");
        if (CollectionUtils.isEmpty(orgList)) {
            return;
        }
        IDataRefChecker checker = this.checkerGather.getByTableName("MD_ORG");
        if (Objects.isNull(checker)) {
            return;
        }
        for (DataRefDTO org : orgList) {
            RefChangeDTO refChangeDTO = new RefChangeDTO();
            refChangeDTO.setOldRefData(org);
            Pair<String, Integer> checkResult = checker.checkHasRef(refChangeDTO);
            if (checkResult == null) continue;
            throw new BusinessRuntimeException(String.format("\u5220\u9664\u5931\u8d25\uff0c\u5f53\u524d\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0b\u5b58\u5728\u5df2\u6620\u5c04\u5355\u4f4d\u3010%1$s\u3011\uff0c\u4e14\u8be5\u5355\u4f4d\u5728\u3010%2$d\u3011\u5e74\u6709\u4e1a\u52a1\u6570\u636e\uff0c\u8bf7\u5148\u6e05\u7a7a\u5bf9\u5e94\u7684\u4e1a\u52a1\u6570\u636e\u3002", checkResult.getFirst(), checkResult.getSecond()));
        }
    }
}


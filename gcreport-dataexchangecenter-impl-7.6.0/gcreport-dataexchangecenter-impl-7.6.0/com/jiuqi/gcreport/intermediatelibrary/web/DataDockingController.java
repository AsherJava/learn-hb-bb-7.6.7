/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.intermediatelibrary.api.DataDockingApi
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse
 *  com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.intermediatelibrary.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.intermediatelibrary.api.DataDockingApi;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingService;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingResponse;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataDockingController
implements DataDockingApi {
    @Autowired
    private DataDockingService dataDockingService;

    public BusinessResponseEntity<String> saveData(DataDockingVO dataDockingVO) {
        DataDockingResponse dataDockingResponse = this.dataDockingService.saveData(dataDockingVO);
        if (Objects.isNull(dataDockingResponse)) {
            return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6570\u636e\u6210\u529f");
        }
        BusinessResponseEntity error = BusinessResponseEntity.error();
        error.data((Object)JsonUtils.writeValueAsString((Object)dataDockingResponse));
        return error;
    }

    public BusinessResponseEntity<DataDockingVO> queryData(DataDockingQueryVO dataDockingQueryVO) {
        return BusinessResponseEntity.ok((Object)this.dataDockingService.queryData(dataDockingQueryVO));
    }
}


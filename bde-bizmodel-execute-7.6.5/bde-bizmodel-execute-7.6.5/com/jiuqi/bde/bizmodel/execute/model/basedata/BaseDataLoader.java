/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.bizmodel.execute.model.basedata;

import com.jiuqi.bde.bizmodel.execute.model.basedata.BaseDataDataCondi;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataLoader {
    @Autowired
    private BaseDataService baseDataService;

    public String getBizModelCode() {
        return "BASEDATA";
    }

    public IPluginType getPluginType() {
        return null;
    }

    public Map<String, BaseDataDO> loadData(BaseDataDataCondi baseDataDataCondi) {
        String baseDataDefine = baseDataDataCondi.getBaseDataDefine();
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(baseDataDefine);
        baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        baseDataDTO.setBaseDataCodes(baseDataDataCondi.getBaseDataCodeList());
        BdeLogUtil.recordLog((String)baseDataDataCondi.getFetchTaskContext().getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u57fa\u7840\u6570\u636e", (Object)new Object[]{baseDataDataCondi}, (String)baseDataDTO.toString());
        PageVO pageVO = this.baseDataService.list(baseDataDTO);
        HashMap<String, BaseDataDO> baseDataMap = new HashMap<String, BaseDataDO>(pageVO.getTotal());
        for (BaseDataDO row : pageVO.getRows()) {
            baseDataMap.put(row.getCode(), row);
        }
        return baseDataMap;
    }
}


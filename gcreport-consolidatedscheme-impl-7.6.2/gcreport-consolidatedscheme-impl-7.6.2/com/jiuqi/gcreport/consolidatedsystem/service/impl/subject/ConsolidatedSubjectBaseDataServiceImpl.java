/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.enums.RangeType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO
 *  com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.subject;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.enums.RangeType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectBaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsolidatedSubjectBaseDataServiceImpl
implements ConsolidatedSubjectBaseDataService {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String systemId, String code, AuthType authType, boolean filterStopNode) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        if (null == code || code.length() == 0 || "-".equals(code)) {
            baseDataDTO.setParentid("-");
        } else {
            baseDataDTO.setParentid(code);
        }
        baseDataDTO.setLazyLoad(true);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(systemId, baseDataDTO, filterStopNode);
    }

    @Override
    public List<GcBaseData> queryBasedataItemsBySearch(String systemId, String searchText, AuthType authType, boolean filterStopNode) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setSearchKey(searchText);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(systemId, baseDataDTO, filterStopNode);
    }

    @Override
    public GcBaseData queryBasedataByCode(String systemId, String code, boolean filterStopNode) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setCode(code);
        List<GcBaseData> result = this.listBaseDataObj(systemId, baseDataDTO, filterStopNode);
        if (result == null || result.size() < 1) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<GcBaseData> queryAllWithSelfItemsByParentid(String systemId, String parentCode, AuthType authType, boolean filterStopNode) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setCode(parentCode);
        baseDataDTO.setRangeType(RangeType.ALL_CHILDREN_WITH_SELF);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(systemId, baseDataDTO, filterStopNode);
    }

    public List<GcBaseData> listBaseDataObj(String systemId, GcBaseDataDTO baseDataObjDTO, boolean filterStopNode) {
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert((GcBaseDataDO)baseDataObjDTO);
        vaBaseDataDTO.setTableName("MD_GCSUBJECT");
        vaBaseDataDTO.put("systemid", (Object)systemId);
        vaBaseDataDTO.setLeafFlag(Boolean.valueOf(true));
        vaBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO list = this.baseDataClient.list(vaBaseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List rows = list.getRows();
        if (filterStopNode) {
            rows = rows.stream().filter(item -> ConverterUtils.getAsBoolean((Object)item.get((Object)"consolidationflag"), (Boolean)false)).collect(Collectors.toList());
        }
        rows.sort(Comparator.comparing(BaseDataDO::getOrdinal));
        return rows.stream().map(vabasedataDO -> BaseDataObjConverter.convert((BaseDataDO)vabasedataDO)).collect(Collectors.toList());
    }
}


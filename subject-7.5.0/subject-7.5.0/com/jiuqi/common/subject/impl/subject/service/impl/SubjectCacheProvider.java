/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$OrderType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.common.subject.impl.subject.service.impl;

import com.jiuqi.common.subject.impl.subject.cache.intf.ICacheDefine;
import com.jiuqi.common.subject.impl.subject.cache.intf.impl.CacheDefine;
import com.jiuqi.common.subject.impl.subject.cache.proider.ConcurrentMapCacheProvider;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.BooleanValEnum;
import com.jiuqi.common.subject.impl.subject.utils.SubjectConverter;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectCacheProvider
extends ConcurrentMapCacheProvider<SubjectDTO> {
    @Autowired
    private BaseDataService baseDataService;
    private final AcctSubjectComparator comparator = new AcctSubjectComparator();

    @Override
    public ICacheDefine getCacheDefine() {
        return new CacheDefine("AcctSubject", "\u79d1\u76ee");
    }

    @Override
    public List<SubjectDTO> loadCache() {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setLeafFlag(Boolean.valueOf(true));
        dto.setTableName("MD_ACCTSUBJECT");
        dto.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL_WITH_REF);
        dto.setStopflag(Integer.valueOf(-1));
        ArrayList<BaseDataSortDTO> sortList = new ArrayList<BaseDataSortDTO>();
        BaseDataSortDTO sortDTO = new BaseDataSortDTO("CODE", BaseDataOption.OrderType.ASC);
        sortList.add(sortDTO);
        dto.setOrderBy(sortList);
        PageVO pageVo = this.baseDataService.list(dto);
        BaseDataDTO currencyCondi = new BaseDataDTO();
        currencyCondi.setTableName("MD_CURRENCY");
        SubjectConverter converter = new SubjectConverter();
        ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>(pageVo.getRows().size());
        List baseDataList = pageVo.getRows().stream().filter(item -> BooleanValEnum.NO.getCode().compareTo(item.getRecoveryflag()) == 0).collect(Collectors.toList());
        for (BaseDataDO baseData : baseDataList) {
            subjects.add(converter.convert(baseData));
        }
        return subjects;
    }

    @Override
    public Comparator<SubjectDTO> getComparator() {
        return this.comparator;
    }

    class AcctSubjectComparator
    implements Comparator<SubjectDTO> {
        AcctSubjectComparator() {
        }

        @Override
        public int compare(SubjectDTO o1, SubjectDTO o2) {
            return o1.getCode().compareTo(o2.getCode());
        }
    }
}


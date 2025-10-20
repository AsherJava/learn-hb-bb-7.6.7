/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.va.domain.common.PageVO;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="HasRefDataRefConfigureService")
public class HasRefDataRefConfigureServiceImpl
implements DataRefListConfigureService {
    @Autowired
    private DataRefConfigureDao dao;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;

    @Override
    public DataRefListVO list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefListDTO dto) {
        Assert.isNotEmpty((Collection)dto.getDataSchemeCodeList(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        HashSet isolationList = CollectionUtils.newHashSet();
        if (DataRefFilterType.HASREF.getCode().equals(dto.getFilterType())) {
            BaseDataMappingDefineDTO define = new BaseDataMappingDefineDTO();
            define.setCode(dto.getTableName());
            HashSet fields = CollectionUtils.newHashSet();
            for (String dataSchemeCode : dto.getDataSchemeCodeList()) {
                BaseDataMappingDefineDTO defineDTO = this.baseDataRefDefineService.findByCode(dataSchemeCode, dto.getTableName());
                if (Objects.isNull(defineDTO)) continue;
                define.setRelTableName(defineDTO.getRelTableName());
                isolationList.add(defineDTO.getIsolationStrategy());
                if (CollectionUtils.isEmpty((Collection)defineDTO.getItems())) continue;
                fields.addAll(defineDTO.getItems());
            }
            define.setItems((List)CollectionUtils.newArrayList());
            define.getItems().addAll(fields);
            int count = this.dao.countMultiSchemeHasref(define, dto, isolationList);
            List<DataRefDTO> dataRefList = this.dao.selectMultiSchemeHasref(define, dto, isolationList);
            DataRefListVO vo = new DataRefListVO();
            vo.setPageVo(new PageVO(dataRefList, count));
            return vo;
        }
        throw new BusinessRuntimeException("\u8bf7\u67e5\u8be2\u5df2\u6620\u5c04\u6570\u636e");
    }
}


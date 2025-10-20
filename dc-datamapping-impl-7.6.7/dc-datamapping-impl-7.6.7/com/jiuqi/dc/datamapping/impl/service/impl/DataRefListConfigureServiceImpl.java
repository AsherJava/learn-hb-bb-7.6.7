/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.StringJoiner;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="DataRefConfigureService")
public class DataRefListConfigureServiceImpl
implements DataRefListConfigureService {
    @Autowired
    private DataRefConfigureDao dao;
    @Autowired
    private BaseDataRefDefineService defineService;

    @Override
    public DataRefListVO list(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") DataRefListDTO dto) {
        List<DataRefDTO> dataRefList;
        int count;
        DataRefListVO vo = new DataRefListVO();
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        DataRefFilterType filterType = DataRefFilterType.fromCode((String)dto.getFilterType());
        switch (filterType) {
            case ALL: {
                count = this.dao.countAll(define, dto);
                dataRefList = this.dao.selectAll(define, dto);
                break;
            }
            case UNREF: {
                count = this.dao.countUnref(define, dto);
                dataRefList = this.dao.selectUnref(define, dto);
                break;
            }
            case HASREF: {
                count = this.dao.countHasref(define, dto);
                dataRefList = this.dao.selectHasref(define, dto);
                break;
            }
            default: {
                count = this.dao.countAutoMatch(define, dto);
                dataRefList = this.dao.selectAutoMatch(define, dto);
            }
        }
        vo.setPageVo(new PageVO(dataRefList, count));
        StringJoiner title = new StringJoiner("-");
        title.add("\u67e5\u8be2");
        title.add(dto.getDataSchemeCode());
        title.add(define.getName());
        if (dto.getFilterParam().containsKey("ODS_CODE") && !StringUtils.isEmpty((String)((String)dto.getFilterParam().get("ODS_CODE")))) {
            title.add("\u6e90\u7cfb\u7edf\u4ee3\u7801" + (String)dto.getFilterParam().get("ODS_CODE"));
        }
        if (dto.getFilterParam().containsKey("CODE") && !StringUtils.isEmpty((String)((String)dto.getFilterParam().get("CODE")))) {
            title.add("\u4e00\u672c\u8d26\u4ee3\u7801" + (String)dto.getFilterParam().get("CODE"));
        }
        LogHelper.info((String)DcFunctionModuleEnum.DATAMAPPING.getFullModuleName(), (String)title.toString(), (String)JsonUtils.writeValueAsString((Object)dto));
        return vo;
    }
}


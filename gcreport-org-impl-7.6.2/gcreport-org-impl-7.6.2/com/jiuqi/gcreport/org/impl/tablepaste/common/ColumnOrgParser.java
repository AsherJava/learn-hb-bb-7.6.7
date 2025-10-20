/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.gcreport.org.impl.tablepaste.common;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnValueParser;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ColumnOrgParser
implements ColumnValueParser {
    @Override
    public Object parse(PasteDataVO source) {
        String orgVersionCode;
        YearPeriodDO yp;
        if (source == null || StringUtils.isEmpty((String)source.getColumnValue())) {
            return new HashMap();
        }
        String[] columnValues = source.getColumnValue().split("\\|");
        if (columnValues.length == 0) {
            return new HashMap();
        }
        String code = columnValues[0].trim();
        if (StringUtils.isEmpty((String)code)) {
            return new HashMap();
        }
        String orgType = source.getOrgType();
        List<OrgToJsonVO> orgToJsonVOS = this.getOrgToJsonVOS(code, orgType, yp = YearPeriodUtil.transform(null, (String)(orgVersionCode = source.getOrgVerCode())));
        if (CollectionUtils.isEmpty(orgToJsonVOS) || orgToJsonVOS.size() > 1) {
            return new HashMap();
        }
        return orgToJsonVOS.get(0);
    }

    private List<OrgToJsonVO> getOrgToJsonVOS(String code, String orgType, YearPeriodDO yp) {
        OrgDTO orgDTO;
        OrgDataService orgDataService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        PageVO list = orgDataService.list(orgDTO = this.createOrgDTO(code, orgType, yp));
        if (CollectionUtils.isEmpty((Collection)list.getRows())) {
            orgDTO.setCode(null);
            orgDTO.setName(code);
            list = orgDataService.list(orgDTO);
        }
        return this.convertOrgDOsToJsonVOs(list.getRows());
    }

    private OrgDTO createOrgDTO(String code, String orgType, YearPeriodDO yp) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        orgDTO.setCode(code);
        orgDTO.setCategoryname(orgType);
        orgDTO.setVersionDate(yp.getEndDate());
        orgDTO.setPagination(Boolean.valueOf(false));
        return orgDTO;
    }

    private List<OrgToJsonVO> convertOrgDOsToJsonVOs(List<OrgDO> orgDOs) {
        ArrayList<OrgToJsonVO> orgToJsonVOS = new ArrayList<OrgToJsonVO>();
        for (OrgDO orgDO : orgDOs) {
            OrgToJsonVO orgToJsonVO = OrgParse.toGcJsonVo(orgDO);
            orgToJsonVO.setLeaf(((Boolean)orgDO.get((Object)"isLeaf")).booleanValue());
            orgToJsonVOS.add(orgToJsonVO);
        }
        return orgToJsonVOS;
    }
}


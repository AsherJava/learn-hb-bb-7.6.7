/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.dc.base.impl.ncell.common;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.impl.ncell.common.ColumnValueParser;
import com.jiuqi.dc.base.impl.ncell.org.OrgParse;
import com.jiuqi.dc.base.impl.ncell.org.vo.OrgToJsonVO;
import com.jiuqi.dc.base.impl.ncell.vo.PasteDataVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColumnOrgParser
implements ColumnValueParser {
    @Autowired
    private OrgDataService orgDataService;

    @Override
    public Object parse(PasteDataVO source) {
        if (StringUtils.isEmpty((String)source.getColumnValue())) {
            return new HashMap();
        }
        String refTableName = source.getColumnDefine().getRefTableName();
        ArrayList<OrgToJsonVO> orgToJsonVOS = new ArrayList<OrgToJsonVO>();
        if (refTableName.equalsIgnoreCase("MD_ORG")) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setSearchKey(source.getColumnValue());
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setPagination(Boolean.valueOf(false));
            ((OrgDataService)SpringContextUtils.getBean(OrgDataService.class)).list(orgDTO).getRows().forEach(orgDO -> orgToJsonVOS.add(OrgParse.toGcJsonVo(orgDO)));
        } else {
            String orgType = source.getOrgType();
            String orgVersionCode = source.getOrgVerCode();
            String code = source.getColumnValue().split("\\|")[0].trim();
            if (StringUtils.isEmpty((String)orgType) || StringUtils.isEmpty((String)orgVersionCode)) {
                throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u590d\u5236\u7f3a\u5c11\u5fc5\u8981\u6761\u4ef6");
            }
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setName(code);
            orgDTO.setCategoryname(orgType);
            orgDTO.setPagination(Boolean.valueOf(false));
            PageVO list = this.orgDataService.list(orgDTO);
            if (!CollectionUtils.isEmpty((Collection)list.getRows()) && list.getRows().size() == 1) {
                OrgDO orgDO2 = (OrgDO)list.getRows().get(0);
                orgToJsonVOS.add(OrgParse.toGcJsonVo(orgDO2));
            }
        }
        if (CollectionUtils.isEmpty(orgToJsonVOS)) {
            return new HashMap();
        }
        if (orgToJsonVOS.size() > 1) {
            return new HashMap();
        }
        return orgToJsonVOS.get(0);
    }
}


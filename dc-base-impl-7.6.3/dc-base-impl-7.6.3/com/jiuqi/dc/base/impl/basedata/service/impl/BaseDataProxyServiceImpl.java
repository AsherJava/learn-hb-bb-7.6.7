/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO
 *  com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO
 *  com.jiuqi.dc.base.common.env.EnvCenter
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.va.basedata.service.BaseDataDefineService
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.TreeVO
 */
package com.jiuqi.dc.base.impl.basedata.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.basedata.dto.BaseDataProxyDTO;
import com.jiuqi.dc.base.client.basedata.vo.BaseDataDefineProxyVO;
import com.jiuqi.dc.base.common.env.EnvCenter;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.impl.basedata.service.BaseDataProxyService;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.TreeVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BaseDataProxyServiceImpl
implements BaseDataProxyService {
    @Autowired
    private BaseDataDefineService defineService;
    @Autowired
    private BaseDataQueryService queryService;
    @Value(value="${acct.basedata.async-limit:3000}")
    private Integer asyncLimit;
    private static final int FN_DEFAULT_PAGE_LIMIT = 50;
    private static final String FN_HAS_CHILDREN = "hasChildren";

    @Override
    public BaseDataDefineProxyVO getDefine(BaseDataDefineDTO defineDto) {
        BaseDataDefineDO baseDataDefineDO = this.defineService.get(defineDto);
        if (baseDataDefineDO == null) {
            return null;
        }
        BaseDataDTO dto = new BaseDataDTO();
        dto.setPagination(Boolean.valueOf(true));
        dto.setLimit(Integer.valueOf(1));
        dto.setTableName(defineDto.getName());
        dto.setUnitcode(EnvCenter.getContextOrgCode());
        PageVO pageVo = this.queryService.list(dto);
        BaseDataDefineProxyVO vo = (BaseDataDefineProxyVO)BeanConvertUtil.convert((Object)baseDataDefineDO, BaseDataDefineProxyVO.class, (String[])new String[0]);
        vo.setLazyLoad(Boolean.valueOf(this.isLazyLoad(pageVo.getTotal())));
        vo.setLimit(Integer.valueOf(50));
        return vo;
    }

    @Override
    public PageVO<BaseDataDO> list(BaseDataProxyDTO dto) {
        if (StringUtils.isEmpty((String)dto.getUnitcode())) {
            dto.setUnitcode(EnvCenter.getContextOrgCode());
        }
        if (!StringUtils.isEmpty((String)dto.getSelVal()) && dto.isPagination() && dto.getLimit() > 0) {
            return this.paginationBySelVal(dto);
        }
        return this.queryService.list((BaseDataDTO)dto);
    }

    private PageVO<BaseDataDO> paginationBySelVal(BaseDataProxyDTO dto) {
        BaseDataDO baseData;
        dto.setPagination(Boolean.valueOf(false));
        PageVO pageVo = this.queryService.list((BaseDataDTO)dto);
        int index = 0;
        Iterator iterator = pageVo.getRows().iterator();
        while (iterator.hasNext() && !(baseData = (BaseDataDO)iterator.next()).getCode().equals(dto.getSelVal())) {
            ++index;
        }
        int pageNo = index / dto.getLimit();
        dto.setPagination(Boolean.valueOf(true));
        dto.setOffset(Integer.valueOf(pageNo * dto.getLimit()));
        return this.queryService.list((BaseDataDTO)dto);
    }

    protected PageVO<TreeVO<BaseDataDO>> asyncTree(BaseDataProxyDTO dto) {
        dto.setPagination(Boolean.valueOf(false));
        PageVO pageVo = this.queryService.list((BaseDataDTO)dto);
        if (CollectionUtils.isEmpty(pageVo.getRows())) {
            return new PageVO();
        }
        ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
        HashMap<String, Comparable<Boolean>> attributes = new HashMap<String, Comparable<Boolean>>();
        for (BaseDataDO map : pageVo.getRows()) {
            TreeVO node = new TreeVO();
            node.setId(map.getCode());
            node.setParentid(map.getParentcode());
            node.setText(map.getName());
            node.setHasChildren(((Boolean)map.get((Object)FN_HAS_CHILDREN)).booleanValue());
            attributes = new HashMap();
            attributes.put("opened", Boolean.valueOf(false));
            attributes.put("stopflag", map.getStopflag());
            node.setAttributes(attributes);
            nodes.add(node);
        }
        PageVO vo = new PageVO();
        vo.setRows(nodes);
        return vo;
    }

    protected boolean isLazyLoad(int total) {
        return total > this.asyncLimit;
    }
}


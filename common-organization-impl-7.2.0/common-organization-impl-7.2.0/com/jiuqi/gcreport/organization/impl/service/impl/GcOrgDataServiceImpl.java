/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.api.vo.tree.INode
 *  com.jiuqi.gcreport.organization.api.vo.tree.ITree
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilter
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.gcreport.organization.impl.service.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.api.vo.tree.INode;
import com.jiuqi.gcreport.organization.api.vo.tree.ITree;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;
import com.jiuqi.gcreport.organization.impl.bean.PeriodDO;
import com.jiuqi.gcreport.organization.impl.extend.StaticSourceProvider;
import com.jiuqi.gcreport.organization.impl.extend.UnitTreeNodeBuilder;
import com.jiuqi.gcreport.organization.impl.service.GcOrgDataService;
import com.jiuqi.gcreport.organization.impl.service.OrgDisplaySchemeService;
import com.jiuqi.gcreport.organization.impl.service.impl.PeriodProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOrgDataServiceImpl
implements GcOrgDataService {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgDataServiceImpl.class);
    @Autowired
    private OrgDataClient vaOrgDataService;
    @Autowired
    private PeriodProvider periodProvider;
    @Autowired
    private StaticSourceProvider staticSourceProvider;
    @Autowired
    private OrgDisplaySchemeService orgDisplaySchemeService;
    @Autowired
    private UnitTreeNodeBuilder UnitTreeNodeBuilder;

    @Override
    public List<OrgDataDO> list(OrgDataParam param) {
        OrgDTO orgDTO = this.convert(param);
        if (!StringUtils.isEmpty((String)param.getOrgCode())) {
            orgDTO.setCode(param.getOrgCode());
        }
        if (!StringUtils.isEmpty((String)param.getSearchText())) {
            orgDTO.setSearchKey(param.getSearchText());
        }
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        PageVO list = this.vaOrgDataService.list(orgDTO);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> this.convert((OrgDO)v)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrgDataDO> listBySearch(OrgDataParam param) {
        PageVO page;
        OrgDTO orgDTO = this.convert(param);
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        OrgDisplaySchemeDO displayScheme = this.getOrgDisplayScheme(param);
        if (displayScheme == null && !StringUtils.isEmpty((String)param.getSearchText())) {
            orgDTO.setSearchKey(param.getSearchText());
        }
        if (CollectionUtils.isEmpty((Collection)(page = this.vaOrgDataService.list(orgDTO)).getRows())) {
            return Collections.emptyList();
        }
        return page.getRows().stream().map(this::convert).filter(orgDataDO -> this.filterBySearchText((OrgDataDO)orgDataDO, param.getSearchText(), displayScheme)).collect(Collectors.toList());
    }

    private boolean filterBySearchText(OrgDataDO orgDataDO, String searchText, OrgDisplaySchemeDO displayScheme) {
        if (displayScheme == null) {
            return true;
        }
        return displayScheme.getFields().stream().anyMatch(field -> {
            Object fieldVal = orgDataDO.getFieldVal((String)field);
            return fieldVal != null && fieldVal.toString().contains(searchText);
        });
    }

    @Override
    public List<OrgDataDO> listDirectChildren(OrgDataParam param) {
        OrgDTO orgDTO = this.convert(param);
        if (StringUtils.isEmpty((String)param.getOrgParentCode())) {
            orgDTO.setParentcode("-");
        } else {
            orgDTO.setParentcode(param.getOrgParentCode());
        }
        orgDTO.setLazyLoad(Boolean.valueOf(true));
        PageVO list = this.vaOrgDataService.list(orgDTO);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> this.convert((OrgDO)v)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrgDataDO> listAllChildren(OrgDataParam param) {
        OrgDTO orgDTO = this.convert(param);
        orgDTO.setCode(param.getOrgParentCode());
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        PageVO list = this.vaOrgDataService.list(orgDTO);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> this.convert((OrgDO)v)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrgDataDO> listAllChildrenWithSelf(OrgDataParam param) {
        OrgDTO orgDTO = this.convert(param);
        orgDTO.setCode(param.getOrgParentCode());
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        orgDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        PageVO list = this.vaOrgDataService.list(orgDTO);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> this.convert((OrgDO)v)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<OrgDataDO> listSuperior(OrgDataParam param) {
        OrgDTO orgDTO = this.convert(param);
        orgDTO.setCode(param.getOrgCode());
        PageVO list = this.vaOrgDataService.listSuperior(orgDTO);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> this.convert((OrgDO)v)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private OrgDTO convert(OrgDataParam orgDataParam) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setTenantName(NpContextHolder.getContext().getTenant());
        if (!StringUtils.isEmpty((String)orgDataParam.getOrgType())) {
            orgDTO.setCategoryname(orgDataParam.getOrgType());
        }
        if (!StringUtils.isEmpty((String)orgDataParam.getOrgVerCode())) {
            PeriodDO periodDO = this.periodProvider.transformByPeriodStr(orgDataParam.getFormSchemeKey(), orgDataParam.getOrgVerCode());
            orgDTO.setValidtime(periodDO.getBeginDate());
            orgDTO.setInvalidtime(periodDO.getEndDate());
            orgDTO.setVersionDate(periodDO.getEndDate());
        }
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        if (!StringUtils.isEmpty((String)orgDataParam.getExpression())) {
            BiSyntaxOrganizationFilter.applyFilter((OrgDTO)orgDTO, (String)orgDataParam.getExpression());
        }
        if (orgDataParam.getPageSize() != null && orgDataParam.getPageSize() > 0) {
            orgDTO.setPagination(Boolean.valueOf(true));
            orgDTO.setLimit(orgDataParam.getPageSize());
            orgDTO.setOffset(Integer.valueOf(orgDataParam.getPageSize() * orgDataParam.getPageNum()));
        }
        if (!StringUtils.isEmpty((String)orgDataParam.getAuthType())) {
            String authType;
            switch (authType = orgDataParam.getAuthType().toUpperCase()) {
                case "NONE": {
                    orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
                    break;
                }
                case "READ": {
                    orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
                    break;
                }
                case "MODIFY": {
                    orgDTO.setAuthType(OrgDataOption.AuthType.MANAGE);
                    break;
                }
                default: {
                    orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
                }
            }
        }
        return orgDTO;
    }

    private OrgDataDO convert(OrgDO orgDO) {
        Object value;
        OrgDataDO orgDataDO = new OrgDataDO();
        orgDataDO.setCategoryname(orgDO.getCategoryname());
        orgDataDO.setCode(orgDO.getCode());
        orgDataDO.setName(orgDO.getLocalizedName());
        orgDataDO.setKey(orgDO.getCode());
        orgDataDO.setOrdinal(orgDO.getOrdinal());
        orgDataDO.setShortname(orgDO.getShortname());
        orgDataDO.setStopflag(Integer.valueOf(1).equals(orgDO.getStopflag()));
        orgDataDO.setParentcode(orgDO.getParentcode());
        orgDataDO.setParents(orgDO.getParents());
        orgDataDO.setRecoveryflag(Integer.valueOf(1).equals(orgDO.getRecoveryflag()));
        for (Map.Entry entry : orgDO.entrySet()) {
            value = entry.getValue();
            if (value instanceof UUID) {
                value = UUIDUtils.toString36((UUID)orgDO.getId());
            }
            orgDataDO.addFieldValue(((String)entry.getKey()).toUpperCase(), entry.getValue());
        }
        if (null != orgDO.getExtInfo()) {
            for (Map.Entry entry : orgDO.getExtInfo().entrySet()) {
                value = entry.getValue();
                if (value instanceof UUID) {
                    value = UUIDUtils.toString36((UUID)orgDO.getId());
                }
                orgDataDO.addFieldValue((String)entry.getKey(), entry.getValue());
            }
        }
        if (orgDataDO.getFieldVal("ISLEAF") != null) {
            orgDataDO.setLeaf(ConverterUtils.getAsBooleanValue((Object)orgDataDO.getFieldVal("ISLEAF"), (boolean)false));
        } else if (orgDataDO.getFieldVal("HASCHILDREN") != null) {
            orgDataDO.setLeaf(!ConverterUtils.getAsBooleanValue((Object)orgDataDO.getFieldVal("HASCHILDREN"), (boolean)false));
        }
        return orgDataDO;
    }

    @Override
    public OrgDataVO convert(OrgDataDO orgDataDO) {
        OrgDataVO orgDataVO = new OrgDataVO();
        orgDataVO.setKey(orgDataDO.getKey());
        orgDataVO.setCode(orgDataDO.getCode());
        orgDataVO.setTitle(orgDataDO.getName());
        orgDataVO.setLeaf(orgDataDO.isLeaf());
        orgDataVO.setParentcode(orgDataDO.getParentcode());
        orgDataVO.setParents(orgDataDO.getParents());
        orgDataVO.setOrdinal(orgDataDO.getParents());
        return orgDataVO;
    }

    @Override
    public Map<String, Object> loadStaticResource(OrgDataParam param) {
        HashMap<String, Object> staticResource = new HashMap<String, Object>();
        staticResource.put("iconSource", this.staticSourceProvider.getBase64IconMap(param));
        return staticResource;
    }

    private OrgDisplaySchemeDO getOrgDisplayScheme(OrgDataParam param) {
        try {
            OrgDisplaySchemeDO displayScheme = this.orgDisplaySchemeService.getCurrentDisplayScheme(param.getOrgType());
            if (displayScheme == null || CollectionUtils.isEmpty(displayScheme.getFields())) {
                return null;
            }
            return displayScheme;
        }
        catch (Exception e) {
            logger.info("\u4e0d\u652f\u6301\u5355\u4f4d\u663e\u793a\u65b9\u6848\u8bbe\u7f6e");
            return null;
        }
    }

    @Override
    public List<ITree<OrgDataVO>> buildTreeNode(List<OrgDataDO> datas, OrgDataParam param) {
        OrgDisplaySchemeDO displayScheme = this.getOrgDisplayScheme(param);
        List<ITree<OrgDataVO>> nodes = datas.stream().map(v -> new ITree((INode)this.UnitTreeNodeBuilder.buildTreeNode(param, (OrgDataDO)v, displayScheme))).collect(Collectors.toList());
        return nodes;
    }

    @Override
    public List<OrgDataVO> buildListNode(List<OrgDataDO> datas, OrgDataParam param) {
        OrgDisplaySchemeDO displayScheme = this.getOrgDisplayScheme(param);
        List<OrgDataVO> nodes = datas.stream().map(v -> this.UnitTreeNodeBuilder.buildTreeNode(param, (OrgDataDO)v, displayScheme)).collect(Collectors.toList());
        return nodes;
    }
}


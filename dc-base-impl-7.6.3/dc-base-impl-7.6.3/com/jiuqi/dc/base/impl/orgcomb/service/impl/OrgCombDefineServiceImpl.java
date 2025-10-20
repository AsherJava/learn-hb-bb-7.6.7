/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombItemDefineVO
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.utils.BeanCopyUtil
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.base.impl.orgcomb.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombItemDefineVO;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.utils.BeanCopyUtil;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombItemDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombDefineDTO;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombDefineMapper;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombGroupMapper;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombItemDefineMapper;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgCombDefineServiceImpl
implements OrgCombDefineService {
    @Autowired
    private OrgCombDefineMapper orgCombDefineMapper;
    @Autowired
    private OrgCombItemDefineMapper orgCombItemDefineMapper;
    @Autowired
    private OrgCombGroupMapper orgCombGroupMapper;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public OrgCombDefineVO add(OrgCombDefineVO schemeVO) {
        OrgCombDefineDO schemeDO = (OrgCombDefineDO)((Object)BeanCopyUtil.copyObj(OrgCombDefineDO.class, (Object)schemeVO));
        schemeDO.setVer(0L);
        schemeDO.setId(UUIDUtils.newUUIDStr());
        schemeDO.setGroupId(schemeVO.getGroupId());
        int sortNum = 0;
        List<String> schemeIdList = this.orgCombDefineMapper.getSchemeIdByGroupId(schemeDO);
        if (schemeIdList != null && schemeIdList.size() > 0) {
            sortNum = this.orgCombDefineMapper.getOrderNumByGroupId(schemeDO);
        }
        schemeDO.setSortOrder(sortNum + 1);
        List schemeItemDOList = BeanCopyUtil.copyObj(OrgCombItemDefineDO.class, (List)schemeVO.getSchemeItems(), schemeItem -> {
            schemeItem.setId(UUIDUtils.newUUIDStr());
            schemeItem.setVer(System.currentTimeMillis());
            schemeItem.setSchemeId(schemeDO.getId());
        });
        this.orgCombDefineMapper.insert((Object)schemeDO);
        schemeItemDOList.forEach(schemeItemDO -> this.orgCombItemDefineMapper.insert(schemeItemDO));
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", "\u5355\u4f4d\u7ec4\u5408", schemeVO.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.UNITCOMBINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)schemeVO));
        OrgCombDefineVO resultSchemeVO = (OrgCombDefineVO)BeanCopyUtil.copyObj(OrgCombDefineVO.class, (Object)((Object)schemeDO));
        resultSchemeVO.setSchemeItems(BeanCopyUtil.copyObj(OrgCombItemDefineVO.class, (List)schemeItemDOList));
        return resultSchemeVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public OrgCombDefineVO update(OrgCombDefineVO schemeVO) {
        OrgCombDefineDO schemeDO = (OrgCombDefineDO)((Object)BeanCopyUtil.copyObj(OrgCombDefineDO.class, (Object)schemeVO));
        schemeDO.setVer(System.currentTimeMillis());
        List schemeItemDOList = BeanCopyUtil.copyObj(OrgCombItemDefineDO.class, (List)schemeVO.getSchemeItems(), schemeItem -> {
            schemeItem.setId(UUIDUtils.newUUIDStr());
            schemeItem.setVer(System.currentTimeMillis());
            schemeItem.setSchemeId(schemeDO.getId());
        });
        this.orgCombItemDefineMapper.delete((Object)new OrgCombItemDefineDO(schemeVO.getId()));
        this.orgCombDefineMapper.updateByPrimaryKeySelective((Object)schemeDO);
        schemeItemDOList.forEach(schemeItemDO -> this.orgCombItemDefineMapper.insert(schemeItemDO));
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", "\u5355\u4f4d\u7ec4\u5408", schemeVO.getName()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.UNITCOMBINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)schemeVO));
        OrgCombDefineVO resultSchemeVO = (OrgCombDefineVO)BeanCopyUtil.copyObj(OrgCombDefineVO.class, (Object)((Object)schemeDO));
        resultSchemeVO.setSchemeItems(BeanCopyUtil.copyObj(OrgCombItemDefineVO.class, (List)schemeItemDOList));
        return resultSchemeVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(String id) {
        this.orgCombItemDefineMapper.delete((Object)new OrgCombItemDefineDO(id));
        this.orgCombDefineMapper.delete((Object)new OrgCombDefineDO(id));
    }

    @Override
    public PageVO<OrgCombDefineListVO> listData(OrgCombDefineDTO orgCombDefineDTO) {
        PageVO pageVO = new PageVO();
        if (!StringUtils.isEmpty((String)orgCombDefineDTO.getUnitCode())) {
            ArrayList<String> selectedIdList = new ArrayList<String>();
            for (OrgCombItemDefineDO orgCombItemDefineDO : this.filterOrgCombByUnitCode(orgCombDefineDTO.getUnitCode())) {
                selectedIdList.add(orgCombItemDefineDO.getId());
            }
            orgCombDefineDTO.setItemDefineIdList(selectedIdList);
            if (orgCombDefineDTO.getItemDefineIdList() == null || orgCombDefineDTO.getItemDefineIdList().size() == 0) {
                pageVO.setTotal(0);
                return pageVO;
            }
        }
        pageVO.setTotal(this.orgCombDefineMapper.getTableDataTotal(orgCombDefineDTO).intValue());
        if (pageVO.getTotal() > 0) {
            pageVO.setRows(this.orgCombDefineMapper.getTableData(orgCombDefineDTO));
        }
        return pageVO;
    }

    @Override
    public List<OrgCombDefineVO> listData() {
        ArrayList<OrgCombDefineVO> schemeVOList = new ArrayList<OrgCombDefineVO>();
        List schemeDOList = this.orgCombDefineMapper.select((Object)new OrgCombDefineDO());
        if (schemeDOList == null || schemeDOList.size() == 0) {
            return schemeVOList;
        }
        schemeDOList.forEach(schemeDO -> schemeVOList.add((OrgCombDefineVO)BeanCopyUtil.copyObj(OrgCombDefineVO.class, (Object)schemeDO)));
        return schemeVOList;
    }

    @Override
    public OrgCombDefineVO findData(String id) {
        OrgCombDefineDO schemeDO = (OrgCombDefineDO)((Object)this.orgCombDefineMapper.selectOne((Object)new OrgCombDefineDO(id)));
        if (schemeDO == null) {
            return null;
        }
        List schemeItemDOList = this.orgCombItemDefineMapper.select((Object)new OrgCombItemDefineDO(id));
        OrgCombDefineVO schemeVO = (OrgCombDefineVO)BeanCopyUtil.copyObj(OrgCombDefineVO.class, (Object)((Object)schemeDO));
        schemeVO.setSchemeItems(BeanCopyUtil.copyObj(OrgCombItemDefineVO.class, (List)schemeItemDOList));
        return schemeVO;
    }

    @Override
    public List<String> findOrgCombCodes(String unitCode) {
        ArrayList<String> orgCombSchemeIdList = new ArrayList<String>();
        for (OrgCombItemDefineDO orgCombItemDefineDO : this.filterOrgCombByUnitCode(unitCode)) {
            orgCombSchemeIdList.add(orgCombItemDefineDO.getSchemeId());
        }
        ArrayList<String> orgCombCodeList = new ArrayList<String>();
        if (orgCombSchemeIdList.size() > 0) {
            for (String orgCombSchemeId : orgCombSchemeIdList) {
                orgCombCodeList.add(((OrgCombDefineDO)((Object)this.orgCombDefineMapper.selectOne((Object)new OrgCombDefineDO(orgCombSchemeId)))).getCode());
            }
        }
        return orgCombCodeList;
    }

    @Override
    public Boolean updateGroupName(OrgCombGroupVO orgCombGroupVO) {
        Assert.isNotEmpty((String)orgCombGroupVO.getTitle(), (String)"\u4fee\u6539\u7684\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        OrgCombGroupDO orgCombGroupDO = new OrgCombGroupDO();
        orgCombGroupDO.setId(orgCombGroupVO.getId());
        orgCombGroupDO.setTitle(orgCombGroupVO.getTitle());
        this.orgCombGroupMapper.updateGroupName(orgCombGroupDO);
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", "\u5206\u7ec4", orgCombGroupVO.getTitle()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.ONLINEPERIOD.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)orgCombGroupVO));
        return true;
    }

    private List<OrgCombItemDefineDO> filterOrgCombByUnitCode(String unitCode) {
        List orgCombItemDefineDOList = this.orgCombItemDefineMapper.select((Object)new OrgCombItemDefineDO());
        if (orgCombItemDefineDOList == null || orgCombItemDefineDOList.size() == 0) {
            return new ArrayList<OrgCombItemDefineDO>();
        }
        HashMap<String, String> orgParentsMap = new HashMap<String, String>();
        List orgDOList = DataCenterUtil.getOrgData();
        for (OrgDO orgDO : orgDOList) {
            orgParentsMap.put(orgDO.getCode(), orgDO.getParents());
        }
        Set orgParentsSet = Arrays.stream(((String)orgParentsMap.get(unitCode)).split("/")).collect(Collectors.toSet());
        ArrayList<OrgCombItemDefineDO> orgCombList = new ArrayList<OrgCombItemDefineDO>();
        block1: for (OrgCombItemDefineDO orgCombItemDefineDO : orgCombItemDefineDOList) {
            if (!orgParentsSet.contains(orgCombItemDefineDO.getOrgCode())) continue;
            if (StringUtils.isEmpty((String)orgCombItemDefineDO.getExcludeOrgCodes())) {
                orgCombList.add(orgCombItemDefineDO);
                continue;
            }
            for (String excludeOrgCode : orgCombItemDefineDO.getExcludeOrgCodes().split(",")) {
                if (excludeOrgCode.equals(unitCode) || orgParentsSet.contains(orgParentsMap.get(excludeOrgCode))) continue block1;
            }
            orgCombList.add(orgCombItemDefineDO);
        }
        return orgCombList;
    }
}


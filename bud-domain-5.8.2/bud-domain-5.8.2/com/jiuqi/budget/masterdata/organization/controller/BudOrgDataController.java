/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.annotations.QueryRequest
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.ResultUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.masterdata.organization.controller;

import com.jiuqi.budget.common.annotations.QueryRequest;
import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.ResultUtil;
import com.jiuqi.budget.components.ProductNameUtil;
import com.jiuqi.budget.components.SysDim;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.domain.BaseDataNode;
import com.jiuqi.budget.domain.SimpleObject;
import com.jiuqi.budget.iview.LightWightTreeItemVO;
import com.jiuqi.budget.masterdata.basedata.BaseDataTreeUtil;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.OrgDataCenter;
import com.jiuqi.budget.masterdata.organization.OrgCategoryDTO;
import com.jiuqi.budget.masterdata.organization.OrgDataObjAttrVO;
import com.jiuqi.budget.masterdata.organization.OrgDataObjDTO;
import com.jiuqi.budget.masterdata.organization.OrgDataObjParam;
import com.jiuqi.budget.masterdata.organization.OrgSearchKeyVO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/param/org"})
public class BudOrgDataController {
    private final OrgDataCenter orgDataCenter;

    @Autowired
    public BudOrgDataController(OrgDataCenter orgDataCenter) {
        this.orgDataCenter = orgDataCenter;
    }

    @PostMapping(value={"/budOrgTree"})
    public ResultVO<List<LightWightTreeItemVO>> getBudOrgTree() {
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        orgDataObjDTO.setTableName("MD_ORG_BUD");
        return ResultUtil.ok(BaseDataTreeUtil.buildFullLightTree(this.orgDataCenter.getOrgDataList(orgDataObjDTO)));
    }

    @PostMapping(value={"/orgTree"})
    public ResultVO<List<ITree<BaseDataNode>>> getOrgTree() {
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        orgDataObjDTO.setTableName("MD_ORG");
        List<ITree<BaseDataNode>> iTrees = BaseDataTreeUtil.buildNRLightTree(this.orgDataCenter.getOrgDataList(orgDataObjDTO), BaseDataNode::newBaseDataNode);
        return ResultUtil.ok(iTrees);
    }

    @PostMapping(value={"/search"})
    @QueryRequest
    public List<FBaseDataObj> search(@RequestBody OrgSearchKeyVO orgSearchKeyVO) {
        String searchKey = orgSearchKeyVO.getSearchKey();
        if (!StringUtils.hasText(searchKey)) {
            return Collections.emptyList();
        }
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        String orgType = orgSearchKeyVO.getOrgType();
        if (!StringUtils.hasText(orgType)) {
            orgType = "MD_ORG";
        }
        orgDataObjDTO.setTableName(orgType);
        List<FBaseDataObj> orgDataList = this.orgDataCenter.getOrgDataList(orgDataObjDTO);
        int maxResultCount = 10;
        int index = 0;
        ArrayList<FBaseDataObj> searchResult = new ArrayList<FBaseDataObj>();
        for (FBaseDataObj fBaseDataObj : orgDataList) {
            if (fBaseDataObj.getCode().contains(searchKey) || fBaseDataObj.getName().contains(searchKey)) {
                searchResult.add(fBaseDataObj);
                ++index;
            }
            if (index != maxResultCount) continue;
            break;
        }
        return searchResult;
    }

    @PostMapping(value={"/orgTreeByTask"})
    public ResultVO<List<ITree<BaseDataNode>>> getOrgTree(@RequestBody OrgDataObjDTO orgDataObjDTO) {
        if (!StringUtils.hasLength(orgDataObjDTO.getCategoryName())) {
            orgDataObjDTO.setTableName("MD_ORG");
        } else {
            orgDataObjDTO.setTableName(orgDataObjDTO.getCategoryName());
        }
        List<ITree<BaseDataNode>> iTrees = BaseDataTreeUtil.buildNRLightTree(this.orgDataCenter.getOrgDataList(orgDataObjDTO), BaseDataNode::newBaseDataNode);
        return ResultUtil.ok(iTrees);
    }

    @PostMapping(value={"/multiOrgTree"})
    public ResultVO<List<LightWightTreeItemVO>> multiOrgTree(@RequestBody OrgDataObjParam orgDataObjDTO) {
        String datatime = orgDataObjDTO.getDatatime();
        DataPeriod dataPeriod = DataPeriodFactory.valueOf(datatime);
        orgDataObjDTO.setVersionPeriod(dataPeriod);
        return ResultUtil.ok(BaseDataTreeUtil.buildFullLightTree(this.orgDataCenter.getOrgDataList(orgDataObjDTO)));
    }

    @PostMapping(value={"/find"})
    @QueryRequest
    public FBaseDataObj find(@RequestBody OrgDataObjDTO orgDataObjDTO) {
        return this.orgDataCenter.findOrgByKey(orgDataObjDTO.getCategoryName(), orgDataObjDTO.getKey());
    }

    @PostMapping(value={"/get"})
    @QueryRequest
    public FBaseDataObj get(@RequestBody OrgDataObjDTO orgDataObjDTO) {
        FBaseDataObj org = this.orgDataCenter.findOrgByKey(orgDataObjDTO.getCategoryName(), orgDataObjDTO.getKey(), orgDataObjDTO.getAuthType());
        if (org == null) {
            org = this.orgDataCenter.findOrgByKey(orgDataObjDTO.getCategoryName(), orgDataObjDTO.getKey(), AuthType.NONE);
            if (org == null) {
                throw new BudgetException("\u672a\u627e\u5230\u6807\u8bc6\u4e3a[" + orgDataObjDTO.getKey() + "]\u7684" + orgDataObjDTO.getCategoryName() + "\u7ec4\u7ec7");
            }
            throw new BudgetException("\u5f53\u524d\u7528\u6237\u5bf9\u3010" + org.getName() + "\u3011\u65e0\u8bbf\u95ee\u6743\u9650");
        }
        return org;
    }

    @PostMapping(value={"/findByCode"})
    @QueryRequest
    public FBaseDataObj findByCode(@RequestBody OrgDataObjParam orgDataObjDTO) {
        String datatime = orgDataObjDTO.getDatatime();
        DataPeriod dataPeriod = DataPeriodFactory.valueOf(datatime);
        orgDataObjDTO.setVersionPeriod(dataPeriod);
        return this.orgDataCenter.getOrgData(orgDataObjDTO);
    }

    @PostMapping(value={"/attr"})
    public ResultVO<Object> getBaseData(@RequestBody OrgDataObjAttrVO orgDataObjAttrVO) {
        FBaseDataObj org = this.orgDataCenter.findBaseDataObjAllFieldByKey(orgDataObjAttrVO.getCategoryName(), orgDataObjAttrVO.getKey());
        return ResultUtil.ok((String)"", (Object)org.getFieldVal(orgDataObjAttrVO.getFieldName()));
    }

    @GetMapping(value={"/category/list"})
    public ResultVO<?> listCategory() {
        List<OrgCategoryDTO> orgCategoryList = this.orgDataCenter.getOrgCategoryList();
        List simpleObjects = orgCategoryList.stream().map(orgCategory -> {
            SimpleObject simpleObject = new SimpleObject();
            if (SysDim.MDCODE.alias().equals(orgCategory.getCode())) {
                simpleObject.setCode("ORG");
                simpleObject.setName(orgCategory.getName());
            } else {
                simpleObject.setCode(orgCategory.getCode());
                simpleObject.setName(orgCategory.getName());
            }
            return simpleObject;
        }).collect(Collectors.toList());
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setCode(SysDim.MDCODE.alias());
        simpleObject.setName("\u540c" + ProductNameUtil.getProductName() + "\u4efb\u52a1\u5173\u8054\u673a\u6784\u7c7b\u578b\u4e00\u81f4");
        simpleObjects.add(0, simpleObject);
        return ResultUtil.ok(simpleObjects);
    }
}


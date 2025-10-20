/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.annotations.QueryRequest
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.BeanCopyUtil
 *  com.jiuqi.budget.common.utils.ResultUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.basedata.common.DummyObjType
 *  com.jiuqi.va.basedata.service.BaseDataDefineService
 *  com.jiuqi.va.basedata.service.BaseDataGroupService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.masterdata.basedata.controller;

import com.jiuqi.budget.common.annotations.QueryRequest;
import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.BeanCopyUtil;
import com.jiuqi.budget.common.utils.ResultUtil;
import com.jiuqi.budget.domain.BaseDataNode;
import com.jiuqi.budget.domain.BudTableModelDefineVO;
import com.jiuqi.budget.masterdata.basedata.BaseDataAttrVO;
import com.jiuqi.budget.masterdata.basedata.BaseDataDefine;
import com.jiuqi.budget.masterdata.basedata.BaseDataObj;
import com.jiuqi.budget.masterdata.basedata.BaseDataObjDTO;
import com.jiuqi.budget.masterdata.basedata.BaseDataTreeUtil;
import com.jiuqi.budget.masterdata.basedata.VerDateQueryParam;
import com.jiuqi.budget.masterdata.intf.BaseDataCenter;
import com.jiuqi.budget.masterdata.intf.FBaseDataDefine;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.SummaryBaseDataObject;
import com.jiuqi.budget.masterdata.intf.VersionDateConverter;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.basedata.common.DummyObjType;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/param"})
public class BudBaseDataController {
    private final BaseDataCenter baseDataCenter;
    private final DataModelService dataModelService;

    @Autowired
    public BudBaseDataController(BaseDataCenter baseDataCenter, DataModelService dataModelService) {
        this.baseDataCenter = baseDataCenter;
        this.dataModelService = dataModelService;
    }

    @PostMapping(value={"/masterData/getVersionDate"})
    @QueryRequest
    private Date getVersionDate(@RequestBody VerDateQueryParam verDateQueryParam) {
        VersionDateConverter dateConverter = (VersionDateConverter)ApplicationContextRegister.getBean(VersionDateConverter.class);
        return dateConverter.getMasterDataVerDate(verDateQueryParam.getDataPeriod(), verDateQueryParam.getMainTaskId(), verDateQueryParam.getTableName());
    }

    @GetMapping(value={"/baseDataDefine/list"})
    public ResultVO<List<FBaseDataDefine>> getBaseDataDefineList() {
        List<FBaseDataDefine> defineList = this.baseDataCenter.getBaseDataDefineList();
        return ResultUtil.ok(defineList);
    }

    @GetMapping(value={"/baseDataDefine/tree"})
    public ResultVO<List<TreeVO<BaseDataGroupDO>>> getBaseDataDefineTree() {
        BaseDataDefineService baseDataDefineService = (BaseDataDefineService)ApplicationContextRegister.getBean(BaseDataDefineService.class);
        BaseDataGroupService baseDataGroupService = (BaseDataGroupService)ApplicationContextRegister.getBean(BaseDataGroupService.class);
        PageVO list = baseDataGroupService.list(new BaseDataGroupDTO());
        List BaseDataGroupDoList = list.getRows();
        PageVO list1 = baseDataDefineService.list(new BaseDataDefineDTO());
        List baseDataDefineDOList = list1.getRows();
        TreeVO root = new TreeVO();
        root.setId("-");
        root.setParentid("#");
        root.setText("\u5168\u90e8");
        root.setHasParent(false);
        root.setHasChildren(true);
        HashMap<String, BaseDataGroupDO> attributes = new HashMap<String, BaseDataGroupDO>();
        BaseDataGroupDO rootGroup = new BaseDataGroupDO();
        rootGroup.setName("-");
        rootGroup.setTitle("\u5168\u90e8");
        attributes.put("param", rootGroup);
        root.setAttributes(attributes);
        ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
        for (BaseDataGroupDO group : BaseDataGroupDoList) {
            if ("root".equals(group.getParentname())) {
                group.setParentname("-");
            }
            TreeVO node = new TreeVO();
            node.setId(group.getName());
            node.setParentid(group.getParentname());
            node.setText(group.getTitle());
            nodes.add(node);
        }
        for (BaseDataDefineDO baseDataDefineDO : baseDataDefineDOList) {
            BaseDataGroupDO baseDataGroupDO = new BaseDataGroupDO();
            TreeVO node = new TreeVO();
            node.setId(baseDataDefineDO.getName());
            node.setParentid(baseDataDefineDO.getGroupname());
            node.setText(baseDataDefineDO.getTitle() + " " + baseDataDefineDO.getName());
            baseDataDefineDO.setDefine("");
            nodes.add(node);
        }
        TreeVO tree = BuildTreeUtil.build(nodes, (TreeVO)root);
        ArrayList<TreeVO> rows = new ArrayList<TreeVO>();
        rows.add(tree);
        return ResultUtil.ok(rows);
    }

    @GetMapping(value={"/fieldDefine"})
    public ResultVO<ColumnModelDefine> getFieldDefine(String key) throws Exception {
        return ResultUtil.ok((Object)this.dataModelService.getColumnModelDefineByID(key));
    }

    @GetMapping(value={"/baseDataDefine/find"})
    public ResultVO<FBaseDataDefine> findBaseDataDefine(String codeOrId) {
        if (codeOrId.length() == 36) {
            return ResultUtil.ok((Object)this.baseDataCenter.findBaseDataDefineById(codeOrId));
        }
        return ResultUtil.ok((Object)this.baseDataCenter.findBaseDataDefineByCode(codeOrId));
    }

    @PostMapping(value={"/baseDataDefine/getFields"})
    public ResultVO<List<ColumnModelDefine>> getBaseDataFields(@RequestBody BaseDataDefine baseDataDefine) throws Exception {
        String tableName = baseDataDefine.getCode();
        if (!StringUtils.hasText(tableName)) {
            return ResultUtil.ok(Collections.emptyList());
        }
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode(tableName);
        if (tableDefine == null) {
            FBaseDataDefine define = this.baseDataCenter.findBaseDataDefineByCode(tableName);
            DummyObjType dummyObjType = define.getDummyObjType();
            if (dummyObjType != null) {
                if (dummyObjType == DummyObjType.SQLDEFINE) {
                    throw new BudgetException("SQL\u6570\u636e\u6e90\u865a\u62df\u57fa\u7840\u6570\u636e\u4e0d\u652f\u6301\u83b7\u53d6\u5b57\u6bb5\u4fe1\u606f");
                }
                tableDefine = this.dataModelService.getTableModelDefineByCode(define.getDummySource());
            }
            if (tableDefine == null) {
                throw new BudgetException("NVWA_TABLEMODEL\u8868\u4e2d\u672a\u627e\u5230\u7ef4\u5ea6[" + tableName + "]\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e\uff0c\u8bf7\u5230\u57fa\u7840\u6570\u636e\u7ba1\u7406\u4e2d\u8bbe\u8ba1\u5e76\u4fdd\u5b58");
            }
        }
        List allFieldsInTable = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        return ResultUtil.ok((Object)allFieldsInTable);
    }

    @GetMapping(value={"/baseData/list"})
    public ResultVO<List<FBaseDataObj>> getBaseDataList(BaseDataObjDTO baseDataObjDTO) {
        return ResultUtil.ok(this.baseDataCenter.listBaseDataObject(baseDataObjDTO));
    }

    @PostMapping(value={"/baseData/list"})
    public ResultVO<List<FBaseDataObj>> getBaseDataList2(@RequestBody BaseDataObjDTO baseDataObjDTO) {
        return ResultUtil.ok(this.baseDataCenter.listBaseDataObject(baseDataObjDTO));
    }

    @GetMapping(value={"/baseData/find"})
    public ResultVO<FBaseDataObj> findBaseData(BaseDataObjDTO baseDataObjDTO) {
        if ("00000000".equals(baseDataObjDTO.getKey())) {
            return ResultUtil.ok((Object)SummaryBaseDataObject.instance);
        }
        return ResultUtil.ok((Object)this.baseDataCenter.getBaseDataObject(baseDataObjDTO));
    }

    @PostMapping(value={"/baseData/get"})
    @QueryRequest
    public FBaseDataObj getBaseData(@RequestBody BaseDataObjDTO baseDataObjDTO) {
        if ("00000000".equals(baseDataObjDTO.getKey())) {
            return SummaryBaseDataObject.instance;
        }
        BaseDataObjDTO baseParam = new BaseDataObjDTO();
        baseParam.setTableName(baseDataObjDTO.getTableName());
        baseParam.setVersionDate(baseDataObjDTO.getVersionDate());
        baseParam.setVersionPeriod(baseDataObjDTO.getVersionPeriod());
        if (StringUtils.hasLength(baseDataObjDTO.getKey())) {
            baseParam.setKey(baseDataObjDTO.getKey());
        } else {
            baseParam.setCode(baseDataObjDTO.getCode());
        }
        FBaseDataObj baseDataObj = this.baseDataCenter.getBaseDataObject(baseParam);
        if (baseDataObj == null) {
            throw new BudgetException("\u57fa\u7840\u6570\u636e" + baseDataObjDTO.getTableName() + "\u4e0b\u6ca1\u6709\u6570\u636e\u9879" + baseDataObjDTO.getKey());
        }
        return baseDataObj;
    }

    @PostMapping(value={"/baseData/attr"})
    public ResultVO<Object> getBaseData(@RequestBody BaseDataAttrVO baseDataAttrVO) {
        FBaseDataObj baseDataObj = baseDataAttrVO.getKey() != null && !"".equals(baseDataAttrVO.getKey()) ? this.baseDataCenter.findBaseDataObjByKey(baseDataAttrVO.getTableName(), baseDataAttrVO.getKey()) : this.baseDataCenter.findBaseDataObjByCode(baseDataAttrVO.getTableName(), baseDataAttrVO.getCode(), baseDataAttrVO.getUnitCode());
        if (baseDataObj == null) {
            throw new BudgetException("\u57fa\u7840\u6570\u636e" + baseDataAttrVO.getTableName() + "\u4e0b\u6ca1\u6709\u6570\u636e\u9879" + baseDataAttrVO.getKey());
        }
        return ResultUtil.ok((String)"", (Object)baseDataObj.getFieldVal(baseDataAttrVO.getFieldName()));
    }

    @PostMapping(value={"/baseData/tree"})
    public ResultVO<List<ITree<BaseDataNode>>> getBaseDataTree(@RequestBody BaseDataObjDTO baseDataObjDTO) {
        FBaseDataDefine define = this.baseDataCenter.findBaseDataDefineByCode(baseDataObjDTO.getTableName());
        Assert.notNull((Object)define, "\u672a\u627e\u5230\u6807\u8bc6\u4e3a" + baseDataObjDTO.getTableName() + "\u7684\u57fa\u7840\u6570\u636e\u5b9a\u4e49");
        List<FBaseDataObj> baseDataObjs = this.baseDataCenter.listBaseDataObject(baseDataObjDTO);
        List<ITree<BaseDataNode>> iTrees = BaseDataTreeUtil.buildNRLightTree(baseDataObjs, BaseDataNode::newBaseDataNode);
        return ResultUtil.ok(iTrees);
    }

    @GetMapping(value={"/baseData/columnInfo/{referTableId}"})
    public BudTableModelDefineVO getColumnInfoByTableId(@PathVariable String referTableId) {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTableId);
        return (BudTableModelDefineVO)BeanCopyUtil.copyObj((Object)tableModelDefine, BudTableModelDefineVO.class);
    }

    @GetMapping(value={"/adjustVersion/tree"})
    public ResultVO<List<ITree<BaseDataNode>>> getAdjustVersionDataTree() {
        ArrayList<FBaseDataObj> baseDataObjs = new ArrayList<FBaseDataObj>();
        for (int i = 1; i <= 5; ++i) {
            BaseDataObj baseDataObj = new BaseDataObj();
            baseDataObj.setKey("00000000-0000-0000-0000-00000000ADJ" + i);
            baseDataObj.setCode("00000000-0000-0000-0000-00000000ADJ" + i);
            baseDataObj.setObjectCode("00000000-0000-0000-0000-00000000ADJ" + i);
            StringBuilder name = new StringBuilder();
            name.append("\u7b2c");
            if (i == 1) {
                name.append("\u4e00");
            } else if (i == 2) {
                name.append("\u4e8c");
            } else if (i == 3) {
                name.append("\u4e09");
            } else if (i == 4) {
                name.append("\u56db");
            } else {
                name.append("\u4e94");
            }
            name.append("\u6b21\u8c03\u6574");
            baseDataObj.setName(name.toString());
            baseDataObj.setParent("-");
            baseDataObj.setUnitCode("-");
            baseDataObj.setParents("-");
            baseDataObjs.add(baseDataObj);
        }
        List<ITree<BaseDataNode>> iTrees = BaseDataTreeUtil.buildNRLightTree(baseDataObjs, BaseDataNode::newBaseDataNode);
        return ResultUtil.ok(iTrees);
    }
}


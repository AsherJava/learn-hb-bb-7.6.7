/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.facade.BusinessError
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter
 *  com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter
 *  com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.dataresource.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.TreeSearchQuery;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.service.IDataResourceTreeService;
import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.dataresource.web.param.DataResourceTreeQuery;
import com.jiuqi.nr.dataresource.web.param.DimNodeFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.facade.BusinessError;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/dataresource/"})
@Api(tags={"\u6570\u636e\u8d44\u6e90\uff1a\u6570\u636e\u8d44\u6e90\u6811\u6811\u5f62API"})
public class DataResourceTreeController {
    private final Logger logger = LoggerFactory.getLogger(DataResourceTreeController.class);
    @Autowired
    private IDataResourceTreeService<DataResourceNode> treeService;
    @Autowired
    private IDataSchemeTreeService<RuntimeDataSchemeNode> runRimeTreeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private SceneUtilService sceneUtil;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private DataSchemeTreeRestController dataSchemeTreeRestController;

    @ApiOperation(value="\u8d44\u6e90\u6811\u6839\u8282\u70b9")
    @PostMapping(value={"/tree/root"})
    public List<ITree<DataResourceNode>> queryResourceTreeRoot(@RequestBody DataResourceTreeQuery param) {
        String defineKey = param.getDefineKey();
        if (StringUtils.hasLength(defineKey)) {
            return this.treeService.getRootTree(null, defineKey);
        }
        return this.treeService.getRootTree(NodeType.TREE_GROUP);
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u4e0b\u7ea7\u8282\u70b9")
    @PostMapping(value={"/tree/children"})
    public List<ITree<DataResourceNode>> queryResourceTreeChildren(@RequestBody DataResourceTreeQuery param) {
        String defineKey = param.getDefineKey();
        DataResourceNodeDTO dataResourceNode = param.getDataResourceNode();
        Assert.notNull((Object)dataResourceNode, "parent must not be null");
        if (StringUtils.hasLength(defineKey)) {
            return this.treeService.getChildTree(null, dataResourceNode);
        }
        return this.treeService.getGroupChildTree(NodeType.TREE_GROUP, dataResourceNode);
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u5b9a\u4f4d")
    @PostMapping(value={"/tree/positioning"})
    public List<ITree<DataResourceNode>> queryResourceTreePath(@RequestBody DataResourceTreeQuery param) {
        String defineKey = param.getDefineKey();
        DataResourceNodeDTO dataResourceNode = param.getDataResourceNode();
        Assert.notNull((Object)dataResourceNode, "parent must not be null");
        if (StringUtils.hasLength(defineKey)) {
            return this.treeService.getSpecifiedTree(null, dataResourceNode, defineKey);
        }
        return this.treeService.getGroupSpecifiedTree(NodeType.TREE_GROUP, dataResourceNode);
    }

    @ApiOperation(value="\u8d44\u6e90\u6811\u641c\u7d22")
    @PostMapping(value={"/tree/search"})
    public List<DataResourceNode> filterResourceTree(@RequestBody DataResourceTreeQuery param) {
        String defineKey = param.getDefineKey();
        String filter = param.getFilter();
        Assert.notNull((Object)filter, "filter must not be null");
        if (StringUtils.hasLength(defineKey)) {
            TreeSearchQuery treeSearchQuery = new TreeSearchQuery();
            treeSearchQuery.setDefineKey(defineKey);
            treeSearchQuery.setKeyword(filter);
            treeSearchQuery.setSearchType(NodeType.TREE.getValue() | NodeType.DIM_GROUP.getValue() | NodeType.TABLE_DIM_GROUP.getValue() | NodeType.RESOURCE_GROUP.getValue());
            return this.treeService.search(treeSearchQuery);
        }
        throw new UnsupportedOperationException();
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u5b9a\u4f4d,\u6570\u636e\u65b9\u6848\u5206\u7ec4\u6811\u5f62")
    @PostMapping(value={"r-tree/path"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreePath(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        List res = this.dataSchemeTreeRestController.queryResourceTreePath(param);
        this.detailTableFilter(res);
        return res;
    }

    private void detailTableFilter(List<ITree<RuntimeDataSchemeNode>> res) {
        res.removeIf(r -> ((RuntimeDataSchemeNode)r.getData()).getType() == com.jiuqi.nr.datascheme.api.core.NodeType.DETAIL_TABLE.getValue());
        for (ITree<RuntimeDataSchemeNode> r2 : res) {
            if (CollectionUtils.isEmpty(r2.getChildren())) continue;
            this.detailTableFilter(r2.getChildren());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u76f4\u63a5\u5b50\u8282\u70b9")
    @PostMapping(value={"r-tree/children/{linkZb}"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeChildren(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param, @PathVariable boolean linkZb) throws JQException {
        RuntimeDataSchemeNodeDTO parent = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        Assert.notNull((Object)parent, "parent must not be null.");
        this.logger.debug("\u83b7\u53d6\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9 \u8282\u70b9 {}", (Object)parent);
        String dimKey = param.getDimKey();
        DimNodeFilter filter = null;
        if (StringUtils.hasLength(dimKey)) {
            filter = StringUtils.hasText(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, true, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
        }
        DimNodeFilter dfilter = new DimNodeFilter(this.sceneUtil);
        filter = filter == null ? dfilter : filter.and((Predicate)((Object)dfilter));
        int checkboxOptional = this.allNode();
        try {
            List res = this.runRimeTreeService.getCheckBoxSchemeGroupChildTree(com.jiuqi.nr.datascheme.api.core.NodeType.FIELD, (INode)parent, checkboxOptional, (NodeFilter)filter);
            this.rebuildByDWXXB(res);
            if (linkZb) {
                res = res.stream().filter(r -> ((RuntimeDataSchemeNode)r.getData()).getType() != com.jiuqi.nr.datascheme.api.core.NodeType.DETAIL_TABLE.getValue()).collect(Collectors.toList());
            }
            return res;
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    private void rebuildByDWXXB(List<ITree<RuntimeDataSchemeNode>> res) {
        if (!CollectionUtils.isEmpty(res)) {
            ITree<RuntimeDataSchemeNode> fmdmTB = null;
            int fmdmIndex = 0;
            int lastDimIndex = 0;
            for (int i = 0; i < res.size(); ++i) {
                ITree<RuntimeDataSchemeNode> node = res.get(i);
                if (((RuntimeDataSchemeNode)node.getData()).getType() == com.jiuqi.nr.datascheme.api.core.NodeType.DIM.getValue()) {
                    lastDimIndex = i;
                }
                if (fmdmTB != null || ((RuntimeDataSchemeNode)node.getData()).getType() != com.jiuqi.nr.datascheme.api.core.NodeType.MD_INFO.getValue()) continue;
                fmdmIndex = i;
                fmdmTB = node;
            }
            if (fmdmTB != null) {
                if (fmdmIndex < lastDimIndex) {
                    --lastDimIndex;
                }
                res.remove(fmdmIndex);
                res.add(lastDimIndex + 1, fmdmTB);
            }
        }
    }

    private int allNode() {
        int checkboxOptional = 0;
        for (com.jiuqi.nr.datascheme.api.core.NodeType value : com.jiuqi.nr.datascheme.api.core.NodeType.values()) {
            checkboxOptional |= value.getValue();
        }
        return checkboxOptional;
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u5355\u4f4d\u4fe1\u606f\u8868\u4e0b\u7684\u5b57\u6bb5")
    @GetMapping(value={"r-tree/mdinfo"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeChildren(String key) {
        ArrayList<ITree<RuntimeDataSchemeNode>> res = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        DataTable table = this.runtimeDataSchemeService.getDataTable(key);
        if (table != null) {
            RuntimeDataSchemeNodeDTO dto = new RuntimeDataSchemeNodeDTO(table);
            ITree root = new ITree((INode)dto);
            root.setIcons(NodeIconGetter.getIconByType((int)dto.getType()));
            root.setExpanded(true);
            res.add((ITree<RuntimeDataSchemeNode>)root);
            List fields = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(key, new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
            if (!CollectionUtils.isEmpty(fields)) {
                for (DataField f : fields) {
                    RuntimeDataSchemeNodeDTO fdto = new RuntimeDataSchemeNodeDTO(f);
                    ITree fNode = new ITree((INode)fdto);
                    fNode.setIcons(NodeIconGetter.getIconByType((int)fdto.getType()));
                    fNode.setLeaf(true);
                    root.appendChild(fNode);
                }
            }
        }
        return res;
    }
}


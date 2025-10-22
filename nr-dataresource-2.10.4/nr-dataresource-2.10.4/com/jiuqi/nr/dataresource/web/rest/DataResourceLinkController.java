/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDataFieldViewService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter
 *  com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.dao.DuplicateKeyException
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.dataresource.web.rest;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.dataresource.DataLinkKind;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dto.SearchDataFieldDTO;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.IDataSchemeNode2ResourceService;
import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.dataresource.web.param.CheckedParam;
import com.jiuqi.nr.dataresource.web.param.DataResourceQuery;
import com.jiuqi.nr.dataresource.web.param.DimNodeFilter;
import com.jiuqi.nr.dataresource.web.param.MatchAddParam;
import com.jiuqi.nr.dataresource.web.vo.DelLinkVO;
import com.jiuqi.nr.dataresource.web.vo.DimAttributeVO;
import com.jiuqi.nr.dataresource.web.vo.LinkOrderVO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDataFieldViewService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/dataresource/"})
@Api(tags={"\u6570\u636e\u8d44\u6e90\uff1a\u6307\u6807\u5173\u8054API"})
public class DataResourceLinkController {
    @Autowired
    private IDataLinkService linkService;
    @Autowired
    private IDataSchemeNode2ResourceService iDataSchemeNode2ResourceService;
    @Autowired
    private IDataFieldViewService dataFieldViewService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private DataSchemeTreeRestController dataSchemeTreeRestController;
    @Autowired
    private SceneUtilService sceneUtil;
    private final Logger logger = LoggerFactory.getLogger(DataResourceLinkController.class);

    @ApiOperation(value="\u5206\u7ec4\u4e0b\u8d44\u6e90\u67e5\u8be2")
    @GetMapping(value={"fd/get"})
    public List<Map<String, Object>> queryDataField(@RequestParam String groupKey, @RequestParam String period) {
        List<DataField> byGroup = this.linkService.getByGroupNoPeriod(groupKey);
        return this.dataFieldViewService.getFieldViewData(byGroup, period);
    }

    @ApiOperation(value="\u5206\u7ec4\u4e0b\u7ef4\u5ea6\u5c5e\u6027\u67e5\u8be2")
    @GetMapping(value={"dim/get"})
    public List<DimAttributeVO> queryDimAttr(@RequestParam String groupKey) {
        ArrayList<DimAttributeVO> list = new ArrayList<DimAttributeVO>();
        List<DimAttribute> dimAttributeByGroup = this.linkService.getDimAttributeByGroup(groupKey);
        if (dimAttributeByGroup == null) {
            return Collections.emptyList();
        }
        for (DimAttribute dimAttribute : dimAttributeByGroup) {
            list.add(new DimAttributeVO(dimAttribute));
        }
        return list;
    }

    @ApiOperation(value="\u5206\u7ec4\u4e0b\u7ef4\u5ea6\u5c5e\u6027\u67e5\u8be2")
    @GetMapping(value={"dim/fm/get"})
    @Deprecated
    public List<DimAttributeVO> queryFmDimAttr(@RequestParam String tableKey, @RequestParam String defineKey, String dimKey) {
        ArrayList<DimAttributeVO> list = new ArrayList<DimAttributeVO>();
        List<DimAttribute> dimAttributeByGroup = this.linkService.getFmDimAttribute(defineKey, tableKey, dimKey);
        if (dimAttributeByGroup == null) {
            return Collections.emptyList();
        }
        for (DimAttribute dimAttribute : dimAttributeByGroup) {
            list.add(new DimAttributeVO(dimAttribute));
        }
        return list;
    }

    @ApiOperation(value="\u590d\u9009\u6dfb\u52a0\u5173\u8054")
    @PostMapping(value={"link/add"})
    public void addLink(@RequestBody CheckedParam checkedVO) throws JQException {
        try {
            this.iDataSchemeNode2ResourceService.add(checkedVO);
        }
        catch (DataResourceException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u590d\u9009\u6dfb\u52a0\u5173\u8054")
    @PostMapping(value={"link/match-add"})
    public List<DataField> matchAddLink(@RequestBody MatchAddParam matchAddParam) throws JQException {
        DataResource dataResource = this.resourceService.get(matchAddParam.getGroup());
        if (dataResource == null) {
            DataResourceDefine dataResourceDefine = this.defineService.get(matchAddParam.getGroup());
            if (dataResourceDefine != null) {
                throw new DataResourceException("\u6307\u6807\u53ea\u80fd\u6dfb\u52a0\u5230\u76ee\u5f55\u4e0b");
            }
            return Collections.emptyList();
        }
        String defineKey = dataResource.getResourceDefineKey();
        DataSchemeTreeQuery param = new DataSchemeTreeQuery();
        List filters = Arrays.stream(matchAddParam.getMatchValue().split("[;,]")).filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
        param.setPeriod(matchAddParam.getPeriod());
        param.setDimKey(matchAddParam.getDimKey());
        param.setDataSchemeKey(matchAddParam.getDataSchemeKey());
        param.setFilters(filters);
        param.setPrecise(Boolean.valueOf(matchAddParam.isExactMatch()));
        ArrayList dataSchemeNodes = this.dataSchemeTreeRestController.preciseFilterResourceSchemeTree(param);
        HashSet uniqueDataSchemeNodes = new HashSet(dataSchemeNodes);
        dataSchemeNodes = new ArrayList(uniqueDataSchemeNodes);
        List<DataResourceLink> links = dataSchemeNodes.stream().map(z -> this.initLink((ITree<RuntimeDataSchemeNode>)z, defineKey, matchAddParam.getGroup())).collect(Collectors.toList());
        List<DataField> allLinks = this.linkService.getByGroupNoPeriod(matchAddParam.getGroup());
        ArrayList<DataField> repeatLinks = new ArrayList<DataField>();
        for (int i = links.size() - 1; i > -1; --i) {
            int finalI = i;
            List repeats = allLinks.stream().filter(l -> l.getKey().equals(((DataResourceLink)links.get(finalI)).getDataFieldKey())).collect(Collectors.toList());
            if (repeats.isEmpty()) continue;
            repeatLinks.addAll(repeats);
            links.remove(i);
        }
        if (!links.isEmpty()) {
            try {
                this.linkService.insert(links);
            }
            catch (DuplicateKeyException e) {
                this.logger.error(e.getMessage(), e);
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DR_1_2.getMessage());
            }
        }
        if (!repeatLinks.isEmpty()) {
            return repeatLinks;
        }
        return Collections.emptyList();
    }

    private DataResourceLink initLink(ITree<RuntimeDataSchemeNode> zbInfo, String defineKey, String group) {
        DataResourceLink init = this.linkService.init();
        init.setGroupKey(group);
        init.setOrder(OrderGenerator.newOrder());
        init.setDataFieldKey(((RuntimeDataSchemeNode)zbInfo.getData()).getKey());
        if (NodeType.FIELD.getValue() == ((RuntimeDataSchemeNode)zbInfo.getData()).getType()) {
            init.setKind(DataLinkKind.FIELD_LINK);
        } else {
            init.setKind(DataLinkKind.FIELD_ZB_LINK);
        }
        init.setResourceDefineKey(defineKey);
        return init;
    }

    private DataResourceLink initLink(DataField dataField, String defineKey, String group) {
        DataResourceLink init = this.linkService.init();
        init.setGroupKey(group);
        init.setOrder(OrderGenerator.newOrder());
        init.setDataFieldKey(dataField.getKey());
        if (NodeType.FIELD.getValue() == dataField.getDataFieldType().getValue()) {
            init.setKind(DataLinkKind.FIELD_LINK);
        } else {
            init.setKind(DataLinkKind.FIELD_ZB_LINK);
        }
        init.setResourceDefineKey(defineKey);
        return init;
    }

    @ApiOperation(value="\u66f4\u65b0\u5173\u8054")
    @PostMapping(value={"link/update"})
    public void updateLinkOrder(@RequestBody List<LinkOrderVO> linkOrderVO) throws JQException {
        try {
            ArrayList<DataResourceLink> list = new ArrayList<DataResourceLink>(linkOrderVO.size());
            for (LinkOrderVO orderVO : linkOrderVO) {
                DataResourceLink init = this.linkService.init();
                init.setOrder(orderVO.getOrder());
                init.setDataFieldKey(orderVO.getFieldKey());
                init.setGroupKey(orderVO.getGroupKey());
                list.add(init);
            }
            this.linkService.update(list);
        }
        catch (DataResourceException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u5173\u8054")
    @PostMapping(value={"link/del"})
    public void removeLink(@RequestBody DelLinkVO delVo) {
        String groupKey = delVo.getGroupKey();
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        if (delVo.getFiledKeys() == null) {
            this.linkService.delete(groupKey);
            return;
        }
        Set<String> filedKeys = delVo.getFiledKeys();
        ArrayList<String> list = new ArrayList<String>(filedKeys);
        this.linkService.delete(groupKey, list);
    }

    @ApiOperation(value="\u5206\u7ec4\u4e0b\u7ef4\u5ea6\u5c5e\u6027\u8bbe\u7f6e\u5c5e\u6027")
    @PostMapping(value={"dim/set"})
    public void updateDimAttr(@RequestBody List<DimAttributeVO> attributes) {
        ArrayList<DimAttribute> list = new ArrayList<DimAttribute>();
        for (DimAttributeVO attribute : attributes) {
            list.add(attribute.toDm(this.linkService));
        }
        this.linkService.setAttribute(list);
    }

    @ApiOperation(value="\u6570\u636e\u65b9\u6848\u5217\u8868")
    @PostMapping(value={"data-scheme"})
    public List<DataScheme> queryDataSchemes(@RequestParam String dimKey, @RequestParam(required=false) String period) {
        DataSchemeTreeQuery param = new DataSchemeTreeQuery();
        param.setDimKey(dimKey);
        param.setPeriod(period);
        SchemeNodeFilter schemeNodeFilter = new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
        return schemeNodeFilter.getSchemesData();
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u5b50\u8282\u70b9\u7684key")
    @PostMapping(value={"r-tree/all-children"})
    public Set<RuntimeDataSchemeNodeDTO> queryAllChildren(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        try {
            DimNodeFilter filter = new DimNodeFilter(this.sceneUtil);
            return this.iDataSchemeNode2ResourceService.queryAllChildren(param, filter);
        }
        catch (DataResourceException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u65b9\u6848(\u5206\u7ec4/\u6307\u6807)\u641c\u7d22\u6570\u636e\u6307\u6807")
    @PostMapping(value={"fd/search"})
    public List<SearchDataFieldDTO> filterDataField(@RequestBody DataResourceQuery param) {
        if (StringUtils.hasText(param.getPeriod())) {
            return this.linkService.searchByPeriod(param);
        }
        return this.linkService.searchByDefineKey(param.getDefineKey(), param.getKeyword());
    }

    @ApiOperation(value="\u83b7\u53d6\u8868\u683c\u5217")
    @GetMapping(value={"table/column/{containExtProp}"})
    public Map<String, String> getTableColumn(@PathVariable boolean containExtProp) {
        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        Map cols = this.dataFieldViewService.getAllFieldViewColumns(containExtProp);
        for (Map.Entry col : cols.entrySet()) {
            if ("zbType".equals(col.getKey()) || "order".equals(col.getKey())) continue;
            res.put((String)col.getKey(), (String)col.getValue());
        }
        return res;
    }
}


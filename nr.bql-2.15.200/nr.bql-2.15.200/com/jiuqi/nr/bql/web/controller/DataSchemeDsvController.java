/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.adhoc3.extend.nvwa.metadata.ModelMetadata
 *  com.jiuqi.bi.adhoc3.extend.nvwa.metadata.TableFolder
 *  com.jiuqi.bi.adhoc3.extend.nvwa.node.BeanNode
 *  com.jiuqi.bi.adhoc3.extend.nvwa.node.FolderNode
 *  com.jiuqi.bi.adhoc3.extend.nvwa.node.TreeNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.bql.web.controller;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.adhoc3.extend.nvwa.metadata.ModelMetadata;
import com.jiuqi.bi.adhoc3.extend.nvwa.metadata.TableFolder;
import com.jiuqi.bi.adhoc3.extend.nvwa.node.BeanNode;
import com.jiuqi.bi.adhoc3.extend.nvwa.node.FolderNode;
import com.jiuqi.bi.adhoc3.extend.nvwa.node.TreeNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.web.DSVErrorEnum;
import com.jiuqi.nr.bql.web.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/zbquery-engine/dsv/"})
@Api(tags={"\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u6a21\u578b\u670d\u52a1"})
public class DataSchemeDsvController {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    private DSVAdapter dsvAdapter;

    @ApiOperation(value="\u83b7\u53d6\u4e0b\u7ea7\u8282\u70b9")
    @GetMapping(value={"dsv/model/childrens"})
    public List<TreeNode> getChildrens(@RequestParam(name="folderGuid", required=false) String folderGuid) throws JQException {
        ArrayList<TreeNode> childrens = new ArrayList<TreeNode>();
        try {
            if (StringUtils.isEmpty((String)folderGuid)) {
                FolderNode folder = this.createRootFolder();
                childrens.add((TreeNode)folder);
            } else {
                List dataSchemes;
                List<? extends DataGroup> groups = this.getSchemeGroupsByParent(folderGuid);
                if (groups != null) {
                    for (DataGroup dataGroup : groups) {
                        if (!this.dataSchemeAuthService.canReadGroup(dataGroup.getKey())) continue;
                        FolderNode folder = this.dataGroupToFolder(dataGroup);
                        childrens.add((TreeNode)folder);
                    }
                }
                if ((dataSchemes = this.dataSchemeService.getDataSchemeByParent(folderGuid)) != null) {
                    for (DataScheme scheme : dataSchemes) {
                        if (!this.dataSchemeAuthService.canReadScheme(scheme.getKey())) continue;
                        BeanNode bean = this.dataSchemeToBean(scheme);
                        childrens.add((TreeNode)bean);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_CHILDRENS, (Throwable)e);
        }
        return childrens;
    }

    @ApiOperation(value="\u83b7\u53d6\u4e0b\u7ea7\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"dsv/model/subdsvs"})
    public List<TreeNode> getSubDsvs(@RequestParam(name="folderGuid", required=false) String folderGuid, @RequestParam(name="isAllSub", required=false) boolean isAllSub) throws JQException {
        ArrayList<TreeNode> childrens = new ArrayList<TreeNode>();
        try {
            List dataSchemes = null;
            if (StringUtils.isEmpty((String)folderGuid) || "00000000-0000-0000-0000-000000000000".equals(folderGuid)) {
                dataSchemes = isAllSub ? this.dataSchemeService.getAllDataScheme() : this.dataSchemeService.getDataSchemeByParent(null);
            } else if (isAllSub) {
                dataSchemes = new ArrayList();
                dataSchemes.addAll(this.dataSchemeService.getDataSchemeByParent(folderGuid));
                List<? extends DataGroup> groups = this.getSchemeGroupsByParent(folderGuid);
                this.addSchemesbyGroups(dataSchemes, groups);
            } else {
                dataSchemes = this.dataSchemeService.getDataSchemeByParent(folderGuid);
            }
            if (dataSchemes != null) {
                for (DataScheme scheme : dataSchemes) {
                    if (!this.dataSchemeAuthService.canReadScheme(scheme.getKey())) continue;
                    BeanNode bean = this.dataSchemeToBean(scheme);
                    childrens.add((TreeNode)bean);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_CHILDRENS, (Throwable)e);
        }
        return childrens;
    }

    private void addSchemesbyGroups(List<DataScheme> dataSchemes, List<? extends DataGroup> groups) {
        if (groups != null && groups.size() > 0) {
            for (DataGroup dataGroup : groups) {
                if (!this.dataSchemeAuthService.canReadGroup(dataGroup.getKey())) continue;
                List schemes = this.dataSchemeService.getDataSchemeByParent(dataGroup.getKey());
                if (schemes != null) {
                    dataSchemes.addAll(schemes);
                }
                List<? extends DataGroup> subGroups = this.getSchemeGroupsByParent(dataGroup.getKey());
                this.addSchemesbyGroups(dataSchemes, subGroups);
            }
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8282\u70b9\u4fe1\u606f")
    @GetMapping(value={"dsv/model/bean/{guidOrName}"})
    public BeanNode getBean(@PathVariable String guidOrName) throws JQException {
        try {
            DataScheme dataScheme = this.getDataScheme(guidOrName);
            return this.dataSchemeToBean(dataScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_BEAN, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8282\u70b9\u4fe1\u606f")
    @GetMapping(value={"dsv/model/search"})
    public List<BeanNode> search(@RequestParam(name="keyword") String keyword) throws JQException {
        try {
            ArrayList<BeanNode> nodes = new ArrayList<BeanNode>();
            List dataSchemes = this.dataSchemeService.getAllDataScheme();
            for (DataScheme scheme : dataSchemes) {
                if (!this.dataSchemeAuthService.canReadScheme(scheme.getKey()) || !scheme.getCode().contains(keyword.toUpperCase()) && !scheme.getTitle().contains(keyword)) continue;
                BeanNode bean = this.dataSchemeToBean(scheme);
                ArrayList<String> guidPaths = new ArrayList<String>();
                ArrayList<String> titlePaths = new ArrayList<String>();
                this.addSchemeGroupPaths(scheme.getDataGroupKey(), guidPaths, titlePaths);
                Collections.reverse(guidPaths);
                Collections.reverse(titlePaths);
                bean.setGuidPath(guidPaths.toArray(new String[guidPaths.size()]));
                bean.setTitlePath(titlePaths.toArray(new String[titlePaths.size()]));
                nodes.add(bean);
            }
            return nodes;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_SEARCH, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u5143\u6570\u636e\u4fe1\u606f")
    @GetMapping(value={"dsv/model/metadata/{guidOrName}"})
    public String getMetaData(@PathVariable String guidOrName) throws JQException {
        ModelMetadata metadata = null;
        try {
            String dsvName = guidOrName;
            DataScheme dataScheme = this.dataSchemeService.getDataScheme(guidOrName);
            if (dataScheme != null) {
                dsvName = DSVAdapter.getDSVName(dataScheme);
            } else {
                dataScheme = this.dataSchemeService.getDataSchemeByCode(DSVAdapter.getDataSchemeCodebyDSVName(guidOrName));
            }
            if (dataScheme == null) {
                throw new Exception("\u6839\u636e" + guidOrName + "\u672a\u627e\u5230\u6570\u636e\u65b9\u6848");
            }
            metadata = new ModelMetadata();
            DSV dsv = this.dsvAdapter.getDSV(dsvName);
            TableFolder dimTableFolder = this.createTableFolder("NR.dimTableGroup", "\u7ef4\u5ea6\u8868");
            metadata.getFolders().add(dimTableFolder);
            for (TableInfo tableInfo : dsv.getTables()) {
                if (tableInfo.getType() == TableType.DATA) {
                    metadata.getTable2folderMap().put(tableInfo.getGuid(), (String)tableInfo.getPropMap().get("NR.dataTableGroup"));
                    continue;
                }
                if (tableInfo.getType() != TableType.DIM) continue;
                metadata.getTable2folderMap().put(tableInfo.getGuid(), dimTableFolder.getGuid());
            }
            metadata.setDsv(dsv);
            List groups = this.dataSchemeService.getAllDataGroup(dataScheme.getKey());
            if (groups != null) {
                for (DataGroup group : groups) {
                    TableFolder folder = this.dataGroupToTableFolder(group);
                    metadata.getFolders().add(folder);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DSVErrorEnum.DSV_METADATA, (Throwable)e);
        }
        return metadata.toJson().toString();
    }

    private void addSchemeGroupPaths(String groupKey, List<String> guidPaths, List<String> titlePaths) throws JQException {
        if (StringUtils.isNotEmpty((String)groupKey) && !"00000000-0000-0000-0000-000000000000".equals(groupKey)) {
            DesignDataGroup group = this.designDataSchemeService.getDataGroup(groupKey);
            if (group == null) {
                throw new JQException((ErrorEnum)DSVErrorEnum.DSV_SEARCH, "\u6839\u636egroupKey:" + groupKey + "\u6ca1\u6709\u627e\u5230\u6570\u636e\u65b9\u6848\u5206\u7ec4");
            }
            guidPaths.add(group.getKey());
            titlePaths.add(group.getTitle());
            while (StringUtils.isNotEmpty((String)group.getParentKey())) {
                DesignDataGroup parentGroup = this.designDataSchemeService.getDataGroup(group.getParentKey());
                if (parentGroup == null) {
                    guidPaths.add("00000000-0000-0000-0000-000000000000");
                    titlePaths.add("\u5168\u90e8\u6570\u636e\u65b9\u6848");
                    break;
                }
                guidPaths.add(parentGroup.getKey());
                titlePaths.add(parentGroup.getTitle());
                group = parentGroup;
            }
        } else {
            guidPaths.add("00000000-0000-0000-0000-000000000000");
            titlePaths.add("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        }
    }

    private List<? extends DataGroup> getSchemeGroupsByParent(String folderGuid) {
        DataGroup parentGroup = this.dataSchemeService.getDataGroup(folderGuid);
        List groups = null;
        if (parentGroup == null || parentGroup.getDataGroupKind() != DataGroupKind.TABLE_GROUP) {
            groups = this.designDataSchemeService.getDataGroupByParent(folderGuid);
        }
        return groups;
    }

    private FolderNode createRootFolder() {
        FolderNode folder = new FolderNode();
        folder.setGuid("00000000-0000-0000-0000-000000000000");
        folder.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        return folder;
    }

    private FolderNode dataGroupToFolder(DataGroup group) {
        FolderNode folder = new FolderNode();
        folder.setGuid(group.getKey());
        folder.setTitle(group.getTitle());
        folder.setParent(group.getParentKey());
        folder.setDescription(group.getDesc());
        return folder;
    }

    private TableFolder dataGroupToTableFolder(DataGroup group) {
        TableFolder folder = new TableFolder();
        folder.setGuid(group.getKey());
        folder.setTitle(group.getTitle());
        folder.setParent(group.getParentKey());
        folder.setDescription(group.getDesc());
        return folder;
    }

    private TableFolder createTableFolder(String key, String title) {
        TableFolder folder = new TableFolder();
        folder.setGuid(key);
        folder.setTitle(title);
        return folder;
    }

    private BeanNode dataSchemeToBean(DataScheme scheme) {
        DataSchemeNode bean = new DataSchemeNode();
        bean.setGuid(scheme.getKey());
        bean.setName(DSVAdapter.getDSVName(scheme));
        bean.setTitle(scheme.getTitle());
        bean.setParent(scheme.getDataGroupKey());
        bean.setDescription(scheme.getDesc());
        return bean;
    }

    private DataScheme getDataScheme(String guidOrName) {
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(guidOrName);
        if (dataScheme == null) {
            dataScheme = this.dataSchemeService.getDataSchemeByCode(DSVAdapter.getDataSchemeCodebyDSVName(guidOrName));
        }
        return dataScheme;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.zb.scheme.web.controller;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.PropLink;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.exception.JQException;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeErrorEnum;
import com.jiuqi.nr.zb.scheme.internal.dto.PropInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.PropLinkDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupSearchParam;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbQueryParam;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeVersionDTO;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nr.zb.scheme.internal.tree.TreeNodeQueryParam;
import com.jiuqi.nr.zb.scheme.service.IZbIOService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeSyncProvider;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeTreeService;
import com.jiuqi.nr.zb.scheme.utils.ZbInfoUtils;
import com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert;
import com.jiuqi.nr.zb.scheme.web.vo.BaseVO;
import com.jiuqi.nr.zb.scheme.web.vo.BatchUpdateZbInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.MoveParam;
import com.jiuqi.nr.zb.scheme.web.vo.PageVO;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.PropLinkVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbGroupVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeGroupVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbSchemeVersionVO;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/zb_scheme/"})
public class ZbSchemeRestController {
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IZbSchemeTreeService zbSchemeTreeService;
    @Autowired
    private IZbIOService zbIOService;
    @Autowired
    private IZbSchemeSyncProvider zbSchemeSyncProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(ZbSchemeRestController.class);

    @PostMapping(value={"add_zs_group"})
    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    public String insertZbSchemeGroup(@RequestBody ZbSchemeGroupDTO groupDTO) throws JQException {
        try {
            return this.zbSchemeService.insertZbSchemeGroup(groupDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @PostMapping(value={"update_zs_group"})
    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    public void updateZbSchemeGroup(@RequestBody ZbSchemeGroupDTO groupDTO) throws JQException {
        try {
            this.zbSchemeService.updateZbSchemeGroup(groupDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @Deprecated
    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u6811")
    @GetMapping(value={"query_zs_group_tree"})
    public List<ITree<INode>> queryZbSchemeGroupTree(@RequestParam(required=false) String location, @RequestParam(required=false) NodeType type) {
        TreeNodeQueryParam param = new TreeNodeQueryParam();
        param.setType(type == null ? NodeType.ZB_SCHEME_GROUP : type);
        List<ITree<INode>> trees = this.zbSchemeTreeService.queryZbSchemeGroupTree(param);
        if (!trees.isEmpty()) {
            trees.get(0).setDisabled(true);
        }
        return trees;
    }

    @ApiOperation(value="\u542f\u7528\u5206\u6790\u6307\u6807")
    @GetMapping(value={"enable_analysis_zb"})
    public Boolean enableAnalyseZb() {
        return this.zbSchemeService.enableAnalyseZb();
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @GetMapping(value={"get_zs_group"})
    public ZbSchemeGroupVO getZbSchemeGroup(@RequestParam String key) {
        ZbSchemeGroup zbSchemeGroup = this.zbSchemeService.getZbSchemeGroup(key);
        return ZbSchemeConvert.cvo(zbSchemeGroup);
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @GetMapping(value={"query_zs_group"})
    public List<ZbSchemeGroupVO> queryZbSchemeGroup(String keyword) {
        if (StringUtils.hasText(keyword)) {
            List<ZbSchemeGroup> list = this.zbSchemeService.listAllZbSchemeGroup();
            String upperCase = keyword.toUpperCase();
            return list.stream().filter(groupDO -> groupDO.getTitle().toUpperCase().contains(upperCase)).map(ZbSchemeConvert::cvo).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @GetMapping(value={"delete_zs_group/{key}"})
    public void deleteZbSchemeGroup(@PathVariable String key) throws JQException {
        try {
            this.zbSchemeService.deleteZbSchemeGroup(key);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u4f53\u7cfb\u6570\u636e")
    @PostMapping(value={"add_zs_data"})
    public String insertZbScheme(@RequestBody ZbSchemeDTO zbSchemeDTO) throws JQException {
        try {
            return this.zbSchemeService.insertZbSchemeAndVersion(zbSchemeDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u4f53\u7cfb\u6570\u636e")
    @PostMapping(value={"update_zs_data"})
    public void updateZbScheme(@RequestBody ZbSchemeDTO zbSchemeDTO) throws JQException {
        try {
            this.zbSchemeService.updateZbScheme(zbSchemeDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u6570\u636e")
    @GetMapping(value={"query_zs_data"})
    public ZbSchemeVO getZbScheme(@RequestParam String key) {
        ZbScheme zbScheme = this.zbSchemeService.getZbScheme(key);
        return ZbSchemeConvert.cvo(zbScheme);
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u6570\u636e\u5217\u8868")
    @GetMapping(value={"query_zs_data_list"})
    public List<ZbSchemeVO> listZbScheme(@RequestParam String parent) {
        List<ZbScheme> zbSchemes = this.zbSchemeService.listZbSchemeByParent(parent);
        return zbSchemes.stream().map(ZbSchemeConvert::cvo).collect(Collectors.toList());
    }

    @ApiOperation(value="\u641c\u7d22\u6307\u6807\u4f53\u7cfb\u6570\u636e\u5217\u8868")
    @GetMapping(value={"search_zs_data_list"})
    public List<ZbSchemeVO> searchZbScheme(@RequestParam String keyword) {
        if (StringUtils.hasText(keyword)) {
            List<ZbScheme> schemes = this.zbSchemeService.listAllZbScheme();
            String finalKeyword = keyword.trim().toUpperCase();
            return schemes.stream().filter(schemeDO -> schemeDO.getCode().toUpperCase().contains(finalKeyword) || schemeDO.getTitle().toUpperCase().contains(finalKeyword)).map(ZbSchemeConvert::cvo).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u4f53\u7cfb\u6570\u636e")
    @GetMapping(value={"delete_zs_data"})
    public void deleteZbScheme(@RequestParam String key) throws JQException {
        try {
            this.zbSchemeService.deleteZbScheme(key);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u6307\u6807\u5c5e\u6027")
    @GetMapping(value={"query_all_zb_prop"})
    public List<PropInfoVO> queryAllZbProp() {
        List<PropInfo> propInfos = this.zbSchemeService.listAllPropInfo();
        return propInfos.stream().map(ZbSchemeConvert::cvo).collect(Collectors.toList());
    }

    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u5c5e\u6027")
    @PostMapping(value={"insert_zb_prop"})
    public void insertZbProp(@RequestBody PropInfoDTO zbPropDTO) throws JQException {
        try {
            this.zbSchemeService.insertPropInfo(zbPropDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u5c5e\u6027")
    @PostMapping(value={"update_zb_prop"})
    public void updateZbProp(@RequestBody PropInfoDTO zbPropDTO) throws JQException {
        try {
            this.zbSchemeService.updatePropInfo(zbPropDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u5c5e\u6027")
    @GetMapping(value={"delete_zb_prop"})
    public void deleteZbProp(@RequestParam String key) throws JQException {
        try {
            this.zbSchemeService.deletePropInfo(key);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6307\u6807\u5c5e\u6027")
    @PostMapping(value={"move_zb_prop"})
    public void moveZbProp(@RequestBody MoveParam param) throws JQException {
        try {
            this.zbSchemeService.moveZbProp(param.getMove(), param.getKey());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5224\u65ad\u6307\u6807\u4f53\u7cfb\u6570\u636e\u662f\u5426\u5b58\u5728")
    @GetMapping(value={"is_exist_data"})
    public Boolean isExistData(@RequestParam NodeType type, @RequestParam String key, @RequestParam(required=false) String version) throws JQException {
        try {
            return this.zbSchemeService.isExistData(type, key, version);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @GetMapping(value={"query_zs_version"})
    public List<ZbSchemeVersionVO> queryZbSchemeVersion(@RequestParam String schemeKey) throws JQException {
        try {
            List<ZbSchemeVersion> zbSchemeVersions = this.zbSchemeService.listZbSchemeVersionByScheme(schemeKey);
            zbSchemeVersions.sort(Comparator.reverseOrder());
            return zbSchemeVersions.stream().map(ZbSchemeConvert::cvo).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @PostMapping(value={"insert_zs_version"})
    public void insertZbSchemeVersion(@RequestBody ZbSchemeVersionDTO versionDTO) throws JQException {
        try {
            this.zbSchemeService.insertZbSchemeVersion(versionDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @PostMapping(value={"update_zs_version"})
    public void updateZbSchemeVersion(@RequestBody ZbSchemeVersionDTO versionDTO) throws JQException {
        try {
            this.zbSchemeService.updateZbSchemeVersion(versionDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @GetMapping(value={"delete_zs_version"})
    public void deleteZbSchemeVersion(@RequestParam String key) throws JQException {
        try {
            this.zbSchemeService.deleteZbSchemeVersion(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.DELETE_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @GetMapping(value={"get_zs_version"})
    public ZbSchemeVersionVO getZbSchemeVersion(@RequestParam String key) throws JQException {
        try {
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(key);
            return ZbSchemeConvert.cvo(zbSchemeVersion);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u641c\u7d22\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @PostMapping(value={"search_zb_group"})
    public List<ZbGroupVO> searchZbGroup(@RequestBody ZbGroupSearchParam param) throws JQException {
        try {
            if (StringUtils.hasText(param.getKeyword())) {
                List<ZbGroup> list = this.zbSchemeService.listZbGroupBySchemeAndPeriod(param.getSchemeKey(), param.getPeriod());
                String upperCase = param.getKeyword().toUpperCase();
                return list.stream().filter(group -> group.getTitle().toUpperCase().contains(upperCase)).map(ZbSchemeConvert::cvo).collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u6811")
    @PostMapping(value={"query_zb_group_tree"})
    public List<ITree<INode>> queryZbGroupTree(@RequestBody TreeNodeQueryParam queryParam) throws JQException {
        try {
            queryParam.setType(NodeType.ZB_GROUP);
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(queryParam.getSchemeKey(), queryParam.getPeriod());
            queryParam.setVersionKey(zbSchemeVersion.getKey());
            return this.zbSchemeTreeService.queryZbGroupTree(queryParam);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @GetMapping(value={"get_zb_group"})
    public ZbGroupVO getZbGroup(@RequestParam String key) throws JQException {
        try {
            ZbGroup zbGroup = this.zbSchemeService.getZbGroup(key);
            return ZbSchemeConvert.cvo(zbGroup);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @PostMapping(value={"check_zb_group"})
    public void checkZbGroup(@RequestBody ZbGroupVO zbGroup) throws JQException {
        ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(zbGroup.getSchemeKey(), zbGroup.getPeriod());
        List<ZbGroup> zbGroups = this.zbSchemeService.listZbGroupByVersionAndParent(zbSchemeVersion.getKey(), zbGroup.getParentKey());
        if (zbGroup.getKey() == null) {
            zbGroup.setKey(UUID.randomUUID().toString());
        }
        for (ZbGroup group : zbGroups) {
            if (zbGroup.getKey().equals(group.getKey()) || !zbGroup.getTitle().equals(group.getTitle())) continue;
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, "\u6307\u6807\u5206\u7ec4\u540d\u79f0\u91cd\u590d");
        }
    }

    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @PostMapping(value={"insert_zb_group"})
    public String insertZbGroup(@RequestBody ZbGroupVO zbGroup) throws JQException {
        try {
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(zbGroup.getSchemeKey(), zbGroup.getPeriod());
            ZbGroup cto = ZbSchemeConvert.cto(zbGroup);
            cto.setVersionKey(zbSchemeVersion.getKey());
            this.zbSchemeService.insertZbGroup(cto);
            return cto.getKey();
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @PostMapping(value={"update_zb_group"})
    public void updateZbGroup(@RequestBody ZbGroupDTO zbGroup) throws JQException {
        try {
            this.zbSchemeService.updateZbGroup(zbGroup);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u4f53\u7cfb\u5206\u7ec4")
    @GetMapping(value={"delete_zb_group"})
    public void deleteZbGroup(@RequestParam String key) throws JQException {
        try {
            this.zbSchemeService.deleteZbGroup(key);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u4fe1\u606f")
    @GetMapping(value={"get_zb_info"})
    public ZbInfoVO getZbInfoByKey(@RequestParam String key) throws JQException {
        try {
            ZbInfo info = this.zbSchemeService.getZbInfo(key);
            ZbInfoVO cvo = ZbSchemeConvert.cvo(info);
            List<String> keys = this.zbSchemeSyncProvider.listSyncZbCode(info.getSchemeKey());
            HashSet<String> set = new HashSet<String>(keys);
            cvo.setHasRefer(set.contains(info.getCode()));
            this.initSelectRows(cvo);
            return cvo;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    private void initSelectRows(ZbInfoVO cvo) throws Exception {
        Map<String, PropInfoVO> propData = cvo.getPropData();
        if (!CollectionUtils.isEmpty(propData)) {
            for (PropInfoVO value : propData.values()) {
                this.initSelectRows(value);
            }
        }
    }

    private void initSelectRows(PropInfoVO value) throws Exception {
        if (value.getDataType() == PropDataType.STRING && StringUtils.hasText(value.getReferEntityId())) {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(value.getReferEntityId());
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.sorted(true);
            entityQuery.setEntityView(entityViewDefine);
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            List allRows = iEntityTable.getAllRows();
            ArrayList<BaseVO> rowVOS = new ArrayList<BaseVO>(allRows.size());
            for (IEntityRow row : allRows) {
                rowVOS.add(new BaseVO(row.getCode(), row.getTitle()));
            }
            value.setSelectRows(rowVOS);
        } else {
            value.setSelectRows(Collections.emptyList());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u9ed8\u8ba4\u6307\u6807\u5c5e\u6027\u6570\u636e")
    @GetMapping(value={"get_default_prop_data"})
    public Map<String, PropInfoVO> getDefaultPropData(@RequestParam String schemeKey) throws JQException {
        try {
            List<PropInfo> propInfos = this.zbSchemeService.listPropInfoLinkedByScheme(schemeKey);
            LinkedHashMap<String, PropInfoVO> res = new LinkedHashMap<String, PropInfoVO>(propInfos.size());
            for (PropInfo propInfo : propInfos) {
                propInfo.setValue(propInfo.getDefaultValue());
                PropInfoVO infoVO = ZbSchemeConvert.cvo(propInfo);
                this.initSelectRows(infoVO);
                res.put(propInfo.getKey(), infoVO);
            }
            return res;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"query_zb_info"})
    public PageVO<ZbInfoVO> queryZbInfo(@RequestBody ZbQueryParam param) throws JQException {
        try {
            List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoByParent(param.getParentKey());
            HashSet<String> codes = new HashSet<String>(this.zbSchemeSyncProvider.listSyncZbCode(param.getSchemeKey()));
            PageVO<ZbInfoVO> pageVO = new PageVO<ZbInfoVO>(param.getCurrentPage(), param.getPageSize(), zbInfos.size());
            int index = pageVO.getSkip();
            ArrayList<ZbInfoVO> data = new ArrayList<ZbInfoVO>(pageVO.getLimit());
            pageVO.setData(data);
            List page = zbInfos.stream().skip(pageVO.getSkip().intValue()).limit(pageVO.getLimit().intValue()).collect(Collectors.toList());
            ZbInfoUtils zbInfoUtils = new ZbInfoUtils(this.entityMetaService, this.dataDefinitionRuntimeController, this.entityDataService, this.entityViewRunTimeController);
            for (ZbInfo dto : page) {
                ZbInfoVO infoVO = zbInfoUtils.cvo(dto);
                data.add(infoVO);
                infoVO.setIndex(++index);
                infoVO.setHasRefer(codes.contains(infoVO.getCode()));
                List<PropInfoVO> cvo = zbInfoUtils.cvo(dto.getExtProp());
                infoVO.setPropList(cvo);
                if (infoVO.getPropData() == null) continue;
                for (PropInfoVO value : infoVO.getPropData().values()) {
                    this.initSelectRows(value);
                }
            }
            return pageVO;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u641c\u7d22\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"search_zb_info"})
    public List<ZbInfoVO> searchZbInfo(@RequestBody ZbQueryParam param) throws JQException {
        try {
            if (StringUtils.hasText(param.getKeyword())) {
                ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(param.getSchemeKey(), param.getPeriod());
                List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoBySchemeAndVersion(param.getSchemeKey(), zbSchemeVersion.getKey());
                String upperCase = param.getKeyword().toUpperCase();
                ArrayList<ZbInfoVO> res = new ArrayList<ZbInfoVO>(zbInfos.size());
                Map listMap = zbInfos.stream().collect(Collectors.groupingBy(ZbInfo::getParentKey, LinkedHashMap::new, Collectors.toList()));
                for (Map.Entry entry : listMap.entrySet()) {
                    List list = (List)entry.getValue();
                    for (int i = 0; i < list.size(); ++i) {
                        ZbInfo zbInfo = (ZbInfo)list.get(i);
                        if (!zbInfo.getCode().contains(upperCase) && !zbInfo.getTitle().toUpperCase().contains(upperCase)) continue;
                        ZbInfoVO vo = ZbSchemeConvert.cvo(zbInfo);
                        vo.setIndex(i + 1);
                        res.add(vo);
                    }
                }
                return res;
            }
            return Collections.emptyList();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6dfb\u52a0\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"insert_zb_info"})
    public String insertZbInfo(@RequestBody ZbInfoVO zbInfoVO) throws JQException {
        try {
            Boolean useOld = zbInfoVO.isUseOld();
            ZbInfoDTO zbInfoDTO = ZbSchemeConvert.cto(zbInfoVO);
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(zbInfoVO.getSchemeKey(), zbInfoVO.getPeriod());
            zbInfoDTO.setVersionKey(zbSchemeVersion.getKey());
            return this.zbSchemeService.insertZbInfo(zbInfoDTO, useOld);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"update_zb_info"})
    public void updateZbInfo(@RequestBody ZbInfoVO zbInfoVO) throws JQException {
        try {
            ZbInfoDTO zbInfoDTO = ZbSchemeConvert.cto(zbInfoVO);
            this.zbSchemeService.updateZbInfo(zbInfoDTO);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u6279\u91cf\u4fee\u6539\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"batch/update_zb_info"})
    public void updateZbInfo(@RequestBody BatchUpdateZbInfoVO batchUpdateZbInfoVO) throws JQException {
        try {
            List<String> keys = batchUpdateZbInfoVO.getKeys();
            if (CollectionUtils.isEmpty(keys)) {
                return;
            }
            List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoByKeys(keys);
            for (ZbInfo zbInfo : zbInfos) {
                ZbSchemeConvert.uto(zbInfo, batchUpdateZbInfoVO);
            }
            this.zbSchemeService.updateZbInfo(zbInfos);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"delete_zb_info"})
    public Integer deleteZbInfo(@RequestBody List<String> keys) throws JQException {
        try {
            return this.zbSchemeService.deleteZbInfo(keys);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"move_zb_info"})
    public PageVO<ZbInfoVO> moveZbInfo(@RequestBody MoveParam moveParam) throws JQException {
        try {
            return this.zbSchemeService.moveZbInfo(moveParam);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u4f53\u7cfb\u5c5e\u6027\u5173\u8054")
    @GetMapping(value={"query_zs_prop_link"})
    public List<PropLinkVO> queryZbSchemePropList(@RequestParam String schemeKey) throws JQException {
        try {
            List<PropInfo> propInfos = this.zbSchemeService.listAllPropInfo();
            List<PropInfo> infos = this.zbSchemeService.listPropInfoLinkedByScheme(schemeKey);
            Set selectKeys = infos.stream().map(MetaItem::getKey).collect(Collectors.toSet());
            ArrayList<PropLinkVO> links = new ArrayList<PropLinkVO>(propInfos.size());
            for (PropInfo propInfo : propInfos) {
                PropLinkVO vo = new PropLinkVO();
                vo.setTitle(propInfo.getTitle());
                vo.setKey(propInfo.getKey());
                vo.setDataType(propInfo.getDataType());
                vo.setSelected(selectKeys.contains(propInfo.getKey()));
                links.add(vo);
            }
            return links;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u6307\u6807\u4f53\u7cfb\u5c5e\u6027\u5173\u8054")
    @PostMapping(value={"save_zs_prop_link"})
    public void saveZbSchemePropList(@RequestParam String schemeKey, @RequestBody List<PropLinkVO> propList) throws JQException {
        ArrayList<PropLink> list = new ArrayList<PropLink>();
        if (!CollectionUtils.isEmpty(propList)) {
            for (PropLinkVO propVO : propList) {
                PropLinkDTO dto = new PropLinkDTO();
                dto.setKey(UUID.randomUUID().toString());
                dto.setSchemeKey(schemeKey);
                dto.setPropKey(propVO.getKey());
                list.add(dto);
            }
        }
        try {
            this.zbSchemeService.saveZbSchemeProp(schemeKey, list);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u53d1\u5e03\u6307\u6807\u4f53\u7cfb\u7248\u672c")
    @GetMapping(value={"publish_version"})
    public void publishVersion(@RequestParam String versionKey, @RequestParam String period, @RequestParam VersionStatus changeStatus) throws JQException {
        try {
            this.zbSchemeService.publishVersion(versionKey, period, changeStatus);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZS_ERROR_001.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"export_zb_info"})
    public void exportZbInfo(HttpServletResponse response, @RequestParam String schemeKey, @RequestParam String versionKey) throws Exception {
        ServletOutputStream outputStream = null;
        ZbScheme zbScheme = this.zbSchemeService.getZbScheme(schemeKey);
        ZbSchemeVersion zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion(schemeKey, versionKey);
        String fileName = zbScheme.getTitle() + "_" + zbSchemeVersion.getTitle();
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            Sheet sheet = workbook.createSheet();
            this.zbIOService.exportZbInfo(schemeKey, zbSchemeVersion.getKey(), sheet);
            fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /*
     * Exception decompiling
     */
    @ApiOperation(value="\u5bfc\u5165\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"import_zb_info"})
    public Result<String> importZbInfo(@RequestParam MultipartFile file, @RequestParam String schemeKey, @RequestParam String versionKey) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 6 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @ApiOperation(value="\u5224\u65ad\u5b9e\u4f53\u6570\u636e\u662f\u5426\u5b58\u5728")
    @GetMapping(value={"is_entity_data"})
    public Boolean checkEntityData(@RequestParam String refDataEntityKey, @RequestParam String value) throws JQException {
        if (refDataEntityKey == null || value == null) {
            return false;
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(refDataEntityKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            List allRows = iEntityTable.getAllRows();
            for (IEntityRow row : allRows) {
                if (!row.getCode().equals(value)) continue;
                return true;
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
        return false;
    }

    @ApiOperation(value="\u662f\u5426\u4e3a\u65e7\u7248\u672c")
    @GetMapping(value={"in_old_version"})
    public Boolean isInOldVersion(@RequestParam String schemeKey, @RequestParam String period, @RequestParam String code) throws JQException {
        try {
            return this.zbSchemeService.isInOldVersion(schemeKey, period, code);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6307\u6807\u65b9\u6848")
    @PostMapping(value={"move_zb_scheme"})
    public void moveZbScheme(@RequestBody MoveParam vo) throws JQException {
        try {
            this.zbSchemeService.moveZbScheme(vo.getKey(), vo.getMove());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6307\u6807\u5206\u7ec4")
    @PostMapping(value={"move_zb_group"})
    public void moveZbGroup(@RequestBody MoveParam vo) throws JQException {
        try {
            this.zbSchemeService.moveZbGroup(vo.getParentKey(), vo.getKey(), vo.getMove());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6307\u6807\u5230\u5206\u7ec4")
    @PostMapping(value={"move_zb_to_group"})
    public void moveZbToGroup(@RequestBody MoveParam vo) throws JQException {
        try {
            this.zbSchemeService.moveZbToGroup(vo.getSelectedKeys(), vo.getParentKey());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZS_ERROR_001, e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u6307\u6807\u9519\u8bef\u4fe1\u606f\u6587\u4ef6")
    @PostMapping(value={"/table/info/export"})
    public void exportInfoExcel(HttpServletResponse response, @RequestParam String fileKey) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            FileAreaService area = this.fileService.area("ZB_SCHEME");
            FileInfo info = area.getInfo(fileKey);
            if (info == null) {
                response.setStatus(410);
                return;
            }
            byte[] download = area.download(fileKey);
            ZbSchemeRestController.extracted(response, "\u8be6\u7ec6\u4fe1\u606f");
            outputStream = response.getOutputStream();
            outputStream.write(download);
            area.delete(fileKey, Boolean.valueOf(false));
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}


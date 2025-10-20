/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombItemDefineVO
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.enums.ImportModuleTypeEnum
 *  com.jiuqi.dc.base.common.enums.TreeNodeDataTypeEnum
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgDO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.base.impl.orgcomb.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombItemDefineVO;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.enums.ImportModuleTypeEnum;
import com.jiuqi.dc.base.common.enums.TreeNodeDataTypeEnum;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombInfoDTO;
import com.jiuqi.dc.base.impl.orgcomb.impexp.OrgCombSchemeExpExcelCellStyleStrategy;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombDefineMapper;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombGroupMapper;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombGroupService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgCombGroupServiceImpl
implements OrgCombGroupService {
    @Autowired
    private OrgCombGroupMapper orgCombGroupMapper;
    @Autowired
    private OrgCombDefineMapper orgCombDefineMapper;
    @Autowired
    private OrgCombDefineService orgCombDefineService;
    private static final String EMPTYORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String REPEATORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u91cd\u590d\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String MULTIORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String UNDEFINEDORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u4e2d\u7684\u6570\u636e\u5728\u4e00\u672c\u8d26\u7ec4\u7ec7\u673a\u6784\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String LEAFORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u4e3a\u672b\u7ea7\u5355\u4f4d\uff0c\u6392\u9664\u8303\u56f4\u4e0d\u5141\u8bb8\u5f55\u5165\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String UNSUPERIORORGCODEMSG = "\u6392\u9664\u8303\u56f4\u5fc5\u987b\u662f\u5f53\u524d\u884c\u5355\u4f4d\u8303\u56f4\u7684\u4e0b\u7ea7\u5355\u4f4d\uff0c\u7b2c%1$s\u884c%2$s\u6392\u9664\u8303\u56f4\u6570\u636e\u6709\u8bef\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String UNDEFINEDEXCLUDEORGCODEMSG = "\u6392\u9664\u8303\u56f4\u7b2c%1$s\u884c%2$s\u5728\u4e00\u672c\u8d26\u7ec4\u7ec7\u673a\u6784\u4e2d\u4e0d\u5b58\u5728\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String SAMEEXCLUDEORGCODEMSG = "\u7b2c%1$s\u884c\u5355\u4f4d\u8303\u56f4\u4e0e\u6392\u9664\u8303\u56f4\u76f8\u540c\uff0c\u8bf7\u4fee\u6539\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public List<OrgCombGroupTreeNodeVO> getTreeData() {
        ArrayList<OrgCombGroupTreeNodeVO> orgCombGroupTreeNodeVOList = new ArrayList<OrgCombGroupTreeNodeVO>();
        OrgCombGroupTreeNodeVO orgCombGroupTreeNodeVO = new OrgCombGroupTreeNodeVO();
        orgCombGroupTreeNodeVO.setId(UUIDUtils.emptyUUIDStr());
        orgCombGroupTreeNodeVO.setTitle("\u5355\u4f4d\u7ec4\u5408");
        orgCombGroupTreeNodeVO.setNodeType(TreeNodeDataTypeEnum.DATATYPE_ROOT.getValue());
        List<OrgCombGroupDO> orgCombGroupDOList = this.orgCombGroupMapper.getAllTreeNodes(new OrgCombGroupDO());
        if (orgCombGroupDOList.size() == 0) {
            orgCombGroupTreeNodeVO.setChildren(new ArrayList());
        } else {
            orgCombGroupTreeNodeVO.setChildren(this.getCombinationChildren(orgCombGroupDOList));
        }
        orgCombGroupTreeNodeVOList.add(orgCombGroupTreeNodeVO);
        return orgCombGroupTreeNodeVOList;
    }

    public List<OrgCombGroupDTO> getCombinationChildren(List<OrgCombGroupDO> orgCombGroupDOList) {
        ArrayList<OrgCombGroupDTO> combinationDTOList = new ArrayList<OrgCombGroupDTO>();
        for (OrgCombGroupDO combinationDO : orgCombGroupDOList) {
            if (combinationDO.getTitle().equals("\u5355\u4f4d\u7ec4")) continue;
            OrgCombGroupDTO combinationDTO = new OrgCombGroupDTO();
            combinationDTO.setId(combinationDO.getId());
            combinationDTO.setTitle(combinationDO.getTitle());
            combinationDTO.setNodeType(TreeNodeDataTypeEnum.DATATYPE_FOLDER.getValue());
            combinationDTO.setTitle(combinationDO.getTitle());
            OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
            orgCombDefineDO.setGroupId(combinationDO.getId());
            combinationDTO.setChildren(this.getSchemeChildren(orgCombDefineDO));
            combinationDTOList.add(combinationDTO);
        }
        return combinationDTOList;
    }

    public List<OrgCombDefineVO> getSchemeChildren(OrgCombDefineDO orgCombDefineDO) {
        ArrayList<OrgCombDefineVO> orgCombDefineVOList = new ArrayList<OrgCombDefineVO>();
        List<String> ids = this.orgCombDefineMapper.getSchemeIdByGroupId(orgCombDefineDO);
        for (String id : ids) {
            OrgCombDefineVO orgCombDefineVO = this.orgCombDefineService.findData(id);
            orgCombDefineVOList.add(orgCombDefineVO);
        }
        return orgCombDefineVOList;
    }

    @Override
    public Boolean updateTreeNode(OrgCombGroupVO orgCombGroupVO) {
        OrgCombGroupDO orgCombGroupDO = new OrgCombGroupDO();
        orgCombGroupDO.setId(orgCombGroupVO.getId());
        String title = this.orgCombGroupMapper.getOrgCombinationTitleById(orgCombGroupDO);
        if (StringUtils.isEmpty((String)title)) {
            throw new RuntimeException("\u8282\u70b9\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (title.equals(orgCombGroupVO.getTitle())) {
            throw new RuntimeException("\u8be5\u5206\u7ec4\u4e0b\u5df2\u7ecf\u5b58\u5728\u8be5\u540d\u79f0\u7684\u8282\u70b9\uff0c\u8bf7\u91cd\u65b0\u547d\u540d\u3002");
        }
        this.orgCombGroupMapper.updateByPrimaryKey((Object)orgCombGroupDO);
        return true;
    }

    @Override
    public Boolean deleteTreeNode(OrgCombGroupTreeNodeVO treeNodeVO) {
        String title = "";
        OrgCombGroupDO orgCombGroupDO = new OrgCombGroupDO();
        if (treeNodeVO.getNodeType() == null) {
            this.orgCombDefineService.delete(treeNodeVO.getId());
            title = StringUtils.join((Object[])new String[]{"\u5220\u9664", "\u5355\u4f4d\u7ec4\u5408", treeNodeVO.getTitle()}, (String)"-");
        } else if (treeNodeVO.getNodeType().equals(TreeNodeDataTypeEnum.DATATYPE_FOLDER.getValue())) {
            orgCombGroupDO.setId(treeNodeVO.getId());
            this.orgCombGroupMapper.delete((Object)orgCombGroupDO);
            title = StringUtils.join((Object[])new String[]{"\u5220\u9664", "\u5206\u7ec4", treeNodeVO.getTitle()}, (String)"-");
        } else {
            throw new RuntimeException("\u6839\u8282\u70b9\u4e0d\u80fd\u88ab\u5220\u9664\u3002");
        }
        LogHelper.info((String)DcFunctionModuleEnum.UNITCOMBINE.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)treeNodeVO));
        return true;
    }

    @Override
    public Boolean addTreeNode(String title) {
        OrgCombGroupDO orgCombGroupDO = new OrgCombGroupDO();
        orgCombGroupDO.setVer(0L);
        orgCombGroupDO.setTitle(title);
        orgCombGroupDO.setId(UUIDUtils.newUUIDStr());
        orgCombGroupDO.setNodeType(TreeNodeDataTypeEnum.DATATYPE_FOLDER.getValue());
        this.orgCombGroupMapper.insert((Object)orgCombGroupDO);
        String logTitle = StringUtils.join((Object[])new String[]{"\u65b0\u589e", "\u5206\u7ec4", title}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.UNITCOMBINE.getFullModuleName(), (String)logTitle, (String)JsonUtils.writeValueAsString((Object)title));
        return true;
    }

    @Override
    public List<String> searchUnitData(List<String> codes) {
        OrgCombGroupDTO orgCombGroupDTO = new OrgCombGroupDTO();
        orgCombGroupDTO.setCodes(codes);
        return this.orgCombGroupMapper.getSchemeIdById(orgCombGroupDTO);
    }

    @Override
    public Object checkOrgCombImportData(String importRepeatType, String importGroupId, List<Object[]> excelDatas) {
        List<Object[]> orgCombGroupList = excelDatas.subList(0, 3);
        List<Object[]> orgCombDefineList = excelDatas.subList(4, excelDatas.size());
        if (orgCombGroupList.get(0)[1] == null || StringUtils.isEmpty((String)String.valueOf(orgCombGroupList.get(0)[1]))) {
            return "\u5206\u7ec4\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002";
        }
        if (orgCombGroupList.get(1)[1] == null || StringUtils.isEmpty((String)String.valueOf(orgCombGroupList.get(1)[1]))) {
            return "\u5206\u7ec4\u540d\u79f0\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002";
        }
        HashMap<String, String> orgParentsMap = new HashMap<String, String>();
        HashMap<String, List> parentMap = new HashMap<String, List>();
        List orgDOList = DataCenterUtil.getOrgData();
        for (OrgDO orgDO : orgDOList) {
            orgParentsMap.put(orgDO.getCode(), orgDO.getParents());
            parentMap.computeIfAbsent(orgDO.getParentcode(), key -> new ArrayList());
            ((List)parentMap.get(orgDO.getParentcode())).add(orgDO.getCode());
        }
        OrgCombDefineVO orgCombDefineVO = new OrgCombDefineVO();
        orgCombDefineVO.setCode(orgCombGroupList.get(0)[1] != null ? String.valueOf(orgCombGroupList.get(0)[1]) : "");
        orgCombDefineVO.setName(orgCombGroupList.get(1)[1] != null ? String.valueOf(orgCombGroupList.get(1)[1]) : "");
        orgCombDefineVO.setRemark(orgCombGroupList.get(2)[1] != null ? String.valueOf(orgCombGroupList.get(2)[1]) : "");
        orgCombDefineVO.setGroupId(importGroupId);
        ArrayList<OrgCombItemDefineVO> itemDefineVOList = new ArrayList<OrgCombItemDefineVO>();
        StringBuilder errorCheckImportMsg = new StringBuilder();
        ArrayList<Integer> emptyOrgCodeIndex = new ArrayList<Integer>();
        HashSet<String> orgCodeSet = new HashSet<String>();
        ArrayList<Integer> repeatOrgCodeIndex = new ArrayList<Integer>();
        ArrayList<Integer> multiOrgCodeIndex = new ArrayList<Integer>();
        ArrayList<Integer> undefinedOrgCodeIndex = new ArrayList<Integer>();
        ArrayList<Integer> leafOrgCodeIndex = new ArrayList<Integer>();
        ArrayList<Integer> sameExcludeOrgCodeIndex = new ArrayList<Integer>();
        StringBuilder excludeOrgCodeCheckMsg = new StringBuilder();
        for (int i = 0; i < orgCombDefineList.size(); ++i) {
            if (orgCombDefineList.get(i)[0] == null || StringUtils.isEmpty((String)String.valueOf(orgCombDefineList.get(i)[0]))) {
                emptyOrgCodeIndex.add(i + 5);
                continue;
            }
            if (orgCodeSet.contains(String.valueOf(orgCombDefineList.get(i)[0]))) {
                repeatOrgCodeIndex.add(i + 5);
            } else {
                orgCodeSet.add(String.valueOf(orgCombDefineList.get(i)[0]));
            }
            if (String.valueOf(orgCombDefineList.get(i)[0]).contains(",") || String.valueOf(orgCombDefineList.get(i)[0]).contains("\uff0c")) {
                multiOrgCodeIndex.add(i + 5);
            }
            if (!orgParentsMap.containsKey(String.valueOf(orgCombDefineList.get(i)[0]))) {
                undefinedOrgCodeIndex.add(i + 5);
            } else if (orgCombDefineList.get(i)[1] != null && !StringUtils.isEmpty((String)String.valueOf(orgCombDefineList.get(i)[1])) && !parentMap.containsKey(String.valueOf(orgCombDefineList.get(i)[0]))) {
                leafOrgCodeIndex.add(i + 5);
            }
            String tempExcludeOrgCodes = "";
            if (orgCombDefineList.get(i)[1] != null && !StringUtils.isEmpty((String)String.valueOf(orgCombDefineList.get(i)[1]))) {
                tempExcludeOrgCodes = String.valueOf(orgCombDefineList.get(i)[1]).replace("\uff0c", ",");
                ArrayList<String> undefinedExcludeOrgCodes = new ArrayList<String>();
                ArrayList<String> unSuperiorOrgCodes = new ArrayList<String>();
                for (String excludeOrgCode : tempExcludeOrgCodes.split(",")) {
                    if (!orgParentsMap.containsKey(excludeOrgCode)) {
                        undefinedExcludeOrgCodes.add("\u3010" + excludeOrgCode + "\u3011");
                        continue;
                    }
                    if (excludeOrgCode.equals(String.valueOf(orgCombDefineList.get(i)[0]))) {
                        sameExcludeOrgCodeIndex.add(i + 5);
                        break;
                    }
                    if (((String)orgParentsMap.get(excludeOrgCode)).contains(String.valueOf(orgCombDefineList.get(i)[0]))) continue;
                    unSuperiorOrgCodes.add("\u3010" + excludeOrgCode + "\u3011");
                }
                if (undefinedExcludeOrgCodes.size() > 0) {
                    excludeOrgCodeCheckMsg.append(String.format(UNDEFINEDEXCLUDEORGCODEMSG, i + 5, undefinedExcludeOrgCodes.stream().collect(Collectors.joining("\uff0c"))));
                }
                if (unSuperiorOrgCodes.size() > 0) {
                    excludeOrgCodeCheckMsg.append(String.format(UNSUPERIORORGCODEMSG, i + 5, unSuperiorOrgCodes.stream().collect(Collectors.joining("\uff0c"))));
                }
            }
            OrgCombItemDefineVO itemDefineVO = new OrgCombItemDefineVO();
            itemDefineVO.setOrgCode(orgCombDefineList.get(i)[0] != null ? String.valueOf(orgCombDefineList.get(i)[0]) : "");
            itemDefineVO.setExcludeOrgCodes(tempExcludeOrgCodes);
            itemDefineVOList.add(itemDefineVO);
        }
        if (emptyOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(EMPTYORGCODEMSG, emptyOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (repeatOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(REPEATORGCODEMSG, repeatOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (multiOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(MULTIORGCODEMSG, multiOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (undefinedOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(UNDEFINEDORGCODEMSG, undefinedOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (leafOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(LEAFORGCODEMSG, leafOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (sameExcludeOrgCodeIndex.size() > 0) {
            errorCheckImportMsg.append(String.format(SAMEEXCLUDEORGCODEMSG, sameExcludeOrgCodeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        errorCheckImportMsg.append((CharSequence)excludeOrgCodeCheckMsg);
        if (!StringUtils.isEmpty((String)String.valueOf(errorCheckImportMsg))) {
            return String.format("\u201c%1$s %2$s\u201d\u5206\u7ec4\u6570\u636e\u5bfc\u5165\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a\n %3$s", orgCombDefineVO.getCode(), orgCombDefineVO.getName(), String.valueOf(errorCheckImportMsg));
        }
        orgCombDefineVO.setSchemeItems(itemDefineVOList);
        OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
        orgCombDefineDO.setCode(orgCombDefineVO.getCode());
        List<String> orgCombIdList = this.orgCombDefineMapper.checkOrgCombGroupCode(orgCombDefineDO);
        if (orgCombIdList.size() > 0 && importRepeatType.equals(ImportModuleTypeEnum.SKIP_REPEAT.getCode())) {
            return String.format("\u5bfc\u5165\u6587\u4ef6\u4ee3\u7801 %1$s \u4e0e\u7cfb\u7edf\u4e2d\u4ee3\u7801\u91cd\u590d\uff0c\u81ea\u52a8\u8df3\u8fc7\uff0c\u5bfc\u5165\u7ed3\u675f\u3002", orgCombDefineVO.getCode());
        }
        if (orgCombIdList.size() > 0) {
            this.orgCombDefineService.delete(orgCombIdList.get(0));
        }
        this.orgCombDefineService.add(orgCombDefineVO);
        return String.format("\u201c%1$s %2$s\u201d\u5206\u7ec4\u6570\u636e\u5bfc\u5165\u6210\u529f\uff0c\u5171\u5bfc\u5165%3$d\u6761\u3002", orgCombDefineVO.getCode(), orgCombDefineVO.getName(), orgCombDefineList.size());
    }

    @Override
    public void exportSchemes(String schemeId, boolean exportTemplate, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "";
        try {
            fileName = URLEncoder.encode(String.format("\u5355\u4f4d\u7ec4\u5408%1$s", sdf.format(new Date())), StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u540d\u79f0\u751f\u6210\u51fa\u73b0\u5f02\u5e38", (Throwable)e);
        }
        try {
            OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
            orgCombDefineDO.setId(schemeId);
            List<OrgCombInfoDTO> exportSchemeList = this.orgCombDefineMapper.getOrgCombDefineBySchemeId(orgCombDefineDO);
            List<List<Object>> expDataList = exportTemplate ? this.getExpTemplateData() : this.getExpData(exportSchemeList);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            ((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new OrgCombSchemeExpExcelCellStyleStrategy(exportTemplate))).registerWriteHandler((WriteHandler)new SimpleColumnWidthStyleStrategy(Integer.valueOf(25)))).sheet("\u5355\u4f4d\u7ec4\u5408").doWrite(expDataList);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u51fa\u5931\u8d25", (Throwable)e);
        }
    }

    private List<List<Object>> getExpTemplateData() {
        ArrayList<List<Object>> expTemplateDatas = new ArrayList<List<Object>>();
        ArrayList<String> row1 = new ArrayList<String>();
        row1.add("\u5206\u7ec4\u4ee3\u7801");
        row1.add("\u5fc5\u586b\uff0c\u4f8b\u5982\uff1a001");
        row1.add("");
        expTemplateDatas.add(row1);
        ArrayList<String> row2 = new ArrayList<String>();
        row2.add("\u5206\u7ec4\u540d\u79f0");
        row2.add("\u5fc5\u586b\uff0c\u4f8b\u5982\uff1a\u7b2c\u4e00\u6279\u4e0a\u7ebf\u5355\u4f4d\u4ee3\u7801");
        row2.add("");
        expTemplateDatas.add(row2);
        ArrayList<String> row3 = new ArrayList<String>();
        row3.add("\u5907\u6ce8");
        row3.add("\u975e\u5fc5\u586b");
        row3.add("");
        expTemplateDatas.add(row3);
        ArrayList<String> row4 = new ArrayList<String>();
        row4.add("\u5355\u4f4d\u8303\u56f4");
        row4.add("\u6392\u9664\u8303\u56f4");
        row4.add("");
        expTemplateDatas.add(row4);
        ArrayList<String> row5 = new ArrayList<String>();
        row5.add("\u5fc5\u586b\uff0c\u5355\u4f4d\u8303\u56f4\uff0c\u5f55\u51651\u4e2a\u975e\u672b\u7ea7\u6216\u8005\u672b\u7ea7\u5355\u4f4d\u4ee3\u7801");
        row5.add("\u975e\u5fc5\u586b\uff0c\u5f53\u5355\u4f4d\u8303\u56f4\u4e3a\u975e\u672b\u7ea7\u5355\u4f4d\u4e14\u5b58\u5728\u6392\u9664\u5355\u4f4d\u65f6\uff0c\u5f55\u5165\u5355\u4f4d\u4ee3\u7801\uff0c\u6709\u591a\u4e2a\u6392\u9664\u5355\u4f4d\u65f6\uff0c\u9017\u53f7\u9694\u5f00");
        row5.add("\u89c4\u5219\u8bf4\u660e\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        expTemplateDatas.add(row5);
        ArrayList<String> row6 = new ArrayList<String>();
        row6.add("1001");
        row6.add("100107,100197,10019901");
        row6.add("\u793a\u4f8b\u884c\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        expTemplateDatas.add(row6);
        return expTemplateDatas;
    }

    private List<List<Object>> getExpData(List<OrgCombInfoDTO> orgCombInfoDTOList) {
        ArrayList<List<Object>> expDatas = new ArrayList<List<Object>>();
        ArrayList<String> row1 = new ArrayList<String>();
        row1.add("\u5206\u7ec4\u4ee3\u7801");
        row1.add(orgCombInfoDTOList.get(0).getCode());
        expDatas.add(row1);
        ArrayList<String> row2 = new ArrayList<String>();
        row2.add("\u5206\u7ec4\u540d\u79f0");
        row2.add(orgCombInfoDTOList.get(0).getName());
        expDatas.add(row2);
        ArrayList<String> row3 = new ArrayList<String>();
        row3.add("\u5907\u6ce8");
        row3.add(orgCombInfoDTOList.get(0).getRemark());
        expDatas.add(row3);
        ArrayList<String> row4 = new ArrayList<String>();
        row4.add("\u5355\u4f4d\u8303\u56f4");
        row4.add("\u6392\u9664\u8303\u56f4");
        expDatas.add(row4);
        for (OrgCombInfoDTO orgCombInfoDTO : orgCombInfoDTOList) {
            ArrayList<String> orgCombInfoRow = new ArrayList<String>();
            orgCombInfoRow.add(orgCombInfoDTO.getOrgCode());
            orgCombInfoRow.add(orgCombInfoDTO.getExcludeOrgCodes());
            expDatas.add(orgCombInfoRow);
        }
        return expDatas;
    }
}


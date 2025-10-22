/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.convert.Convert
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.zb.scheme.common.ZbApplyType
 *  com.jiuqi.nr.zb.scheme.common.ZbDataType
 *  com.jiuqi.nr.zb.scheme.common.ZbGatherType
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 *  com.jiuqi.nr.zb.scheme.core.ZbGroup
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao
 *  com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao
 *  com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  com.jiuqi.nr.zb.scheme.utils.JsonUtils
 *  com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.system.check2.zbScheme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.system.check2.zbScheme.ZbSchemeReverseParam;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class ZbSchemeReverseSchemeVisitor
implements SchemeNodeVisitor<ZbSchemeReverseParam> {
    private static final Logger log = LoggerFactory.getLogger(ZbSchemeReverseSchemeVisitor.class);
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IZbInfoDao zbInfoDao;
    @Autowired
    private IZbGroupDao zbGroupDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    private static final int TABLE_TYPE = NodeType.TABLE.getValue() | NodeType.MD_INFO.getValue();

    public VisitorResult preVisitNode(SchemeNode<ZbSchemeReverseParam> ele) {
        return null;
    }

    public ZbSchemeReverseParam visitRootIsSchemeNode(DesignDataScheme scheme) {
        return null;
    }

    public ZbSchemeReverseParam visitRootIsGroupNode(DesignDataGroup group) {
        return null;
    }

    public ZbSchemeReverseParam visitRootIsTableNode(DesignDataTable table) {
        return null;
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, ZbSchemeReverseParam> visitSchemeGroupNode(SchemeNode<ZbSchemeReverseParam> next, List<DG> groups, List<DS> schemes) {
        return Collections.emptyMap();
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, ZbSchemeReverseParam> visitSchemeNode(SchemeNode<ZbSchemeReverseParam> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ZbSchemeReverseParam zbSchemeReverseParam = (ZbSchemeReverseParam)ele.getOther();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(zbSchemeReverseParam.getDataSchemeKey());
        HashMap<String, ZbSchemeReverseParam> paramMap = new HashMap<String, ZbSchemeReverseParam>(16);
        ArrayList<ZbGroup> groups = new ArrayList<ZbGroup>(16);
        ZbGroup rootGroup = this.zbSchemeService.initZbGroup();
        rootGroup.setParentKey("00000000-0000-0000-0000-000000000000");
        rootGroup.setTitle(dataScheme.getTitle());
        rootGroup.setSchemeKey(zbSchemeReverseParam.getZbSchemeKey());
        rootGroup.setVersionKey(zbSchemeReverseParam.getZbSchemeVersionKey());
        rootGroup.setKey(((ZbSchemeReverseParam)ele.getOther()).getKey());
        log.debug("\u521b\u5efa\u5206\u7ec4: {}, key: {}", (Object)rootGroup.getTitle(), (Object)rootGroup.getKey());
        groups.add(rootGroup);
        ZbGroup publicGroup = this.zbSchemeService.initZbGroup();
        publicGroup.setParentKey(rootGroup.getKey());
        publicGroup.setTitle("\u516c\u5171\u7ef4\u5ea6\u5206\u7ec4");
        publicGroup.setSchemeKey(zbSchemeReverseParam.getZbSchemeKey());
        publicGroup.setVersionKey(zbSchemeReverseParam.getZbSchemeVersionKey());
        groups.add(publicGroup);
        SchemeNode rootNode = new SchemeNode(rootGroup.getKey(), NodeType.GROUP.getValue());
        rootNode.setOther((Object)new ZbSchemeReverseParam(rootGroup.getKey(), (ZbSchemeReverseParam)ele.getOther()));
        paramMap.put(dataScheme.getKey(), (ZbSchemeReverseParam)rootNode.getOther());
        this.copyGroup(groups, (SchemeNode<ZbSchemeReverseParam>)rootNode, dataGroups, paramMap);
        this.copyTable(groups, (SchemeNode<ZbSchemeReverseParam>)rootNode, dataTables, paramMap, publicGroup);
        this.zbGroupDao.insert(groups.stream().map(ZbSchemeConvert::cdo).collect(Collectors.toList()));
        log.debug("\u63d2\u5165\u5206\u7ec4: {}", (Object)groups.size());
        return paramMap;
    }

    private <DG extends DesignDataGroup> void copyGroup(List<ZbGroup> groups, SchemeNode<ZbSchemeReverseParam> ele, List<DG> dataGroups, Map<String, ZbSchemeReverseParam> paramMap) {
        ZbSchemeReverseParam param = (ZbSchemeReverseParam)ele.getOther();
        for (DesignDataGroup dataGroup : dataGroups) {
            ZbGroup zbGroup = this.zbSchemeService.initZbGroup();
            groups.add(zbGroup);
            zbGroup.setTitle(dataGroup.getTitle());
            zbGroup.setSchemeKey(param.getZbSchemeKey());
            zbGroup.setParentKey(ele.getType() != NodeType.SCHEME.getValue() ? ((ZbSchemeReverseParam)ele.getOther()).getKey() : "00000000-0000-0000-0000-000000000000");
            zbGroup.setVersionKey(param.getZbSchemeVersionKey());
            paramMap.put(dataGroup.getKey(), new ZbSchemeReverseParam(zbGroup.getKey(), (ZbSchemeReverseParam)ele.getOther()));
        }
    }

    private <DT extends DesignDataTable> void copyTable(List<ZbGroup> groups, SchemeNode<ZbSchemeReverseParam> ele, List<DT> dataTables, Map<String, ZbSchemeReverseParam> paramMap, ZbGroup publicGroup) {
        ZbSchemeReverseParam other = (ZbSchemeReverseParam)ele.getOther();
        for (DesignDataTable dataTable : dataTables) {
            if (dataTable.getDataTableType() == DataTableType.TABLE || dataTable.getDataTableType() == DataTableType.MD_INFO) {
                ZbGroup zbGroup = this.zbSchemeService.initZbGroup();
                groups.add(zbGroup);
                zbGroup.setTitle(dataTable.getTitle());
                zbGroup.setSchemeKey(other.getZbSchemeKey());
                if (dataTable.getDataTableType() == DataTableType.TABLE) {
                    zbGroup.setParentKey(ele.getType() != NodeType.SCHEME.getValue() ? ((ZbSchemeReverseParam)ele.getOther()).getKey() : "00000000-0000-0000-0000-000000000000");
                } else {
                    zbGroup.setParentKey(publicGroup != null ? publicGroup.getKey() : "00000000-0000-0000-0000-000000000000");
                }
                zbGroup.setVersionKey(other.getZbSchemeVersionKey());
                paramMap.put(dataTable.getKey(), new ZbSchemeReverseParam(zbGroup.getKey(), (ZbSchemeReverseParam)ele.getOther()));
                continue;
            }
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u7684\u8868\u7c7b\u578b");
        }
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<ZbSchemeReverseParam> ele, List<DA> attributes) {
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, ZbSchemeReverseParam> visitGroupNode(SchemeNode<ZbSchemeReverseParam> ele, List<DG> dataGroups, List<DT> dataTables) {
        HashMap<String, ZbSchemeReverseParam> paramMap = new HashMap<String, ZbSchemeReverseParam>(16);
        ArrayList<ZbGroup> groups = new ArrayList<ZbGroup>(16);
        this.copyGroup(groups, ele, dataGroups, paramMap);
        this.copyTable(groups, ele, dataTables, paramMap, null);
        this.zbGroupDao.insert(groups.stream().map(ZbSchemeConvert::cdo).collect(Collectors.toList()));
        log.debug("\u63d2\u5165\u5206\u7ec4: {}", (Object)groups.size());
        return paramMap;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<ZbSchemeReverseParam> ele, List<DF> dataFields) {
        if (dataFields.isEmpty()) {
            return;
        }
        if ((ele.getType() & TABLE_TYPE) > 0) {
            ZbSchemeReverseParam other = (ZbSchemeReverseParam)ele.getOther();
            ArrayList<ZbInfo> zbInfos = new ArrayList<ZbInfo>(dataFields.size());
            ArrayList<DesignDataFieldDO> fields = new ArrayList<DesignDataFieldDO>(dataFields.size());
            for (DesignDataField dataField : dataFields) {
                if (dataField.getDataFieldKind() != DataFieldKind.FIELD_ZB) continue;
                ZbInfo zbInfo = this.zbSchemeService.initZbInfo();
                zbInfos.add(zbInfo);
                zbInfo.setSchemeKey(other.getZbSchemeKey());
                zbInfo.setVersionKey(other.getZbSchemeVersionKey());
                zbInfo.setParentKey(((ZbSchemeReverseParam)ele.getOther()).getKey());
                this.updateProperties(zbInfo, dataField);
                dataField.setZbSchemeVersion(((ZbSchemeReverseParam)ele.getOther()).getZbSchemeVersionKey());
                dataField.setUpdateTime(Instant.now());
                fields.add(Convert.iDf2Do((DesignDataField)dataField));
            }
            try {
                this.zbInfoDao.insert(zbInfos.stream().map(ZbSchemeConvert::cdo).collect(Collectors.toList()));
                this.dataFieldDao.batchUpdate(fields);
                log.debug("\u63d2\u5165\u6307\u6807: {}", (Object)zbInfos.size());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private <DF extends DesignDataField> void updateProperties(ZbInfo zbInfo, DF dataField) {
        String json;
        List validationRules;
        ZbApplyType zbApplyType;
        DataFieldApplyType dataFieldApplyType;
        ZbGatherType zbGatherType;
        ZbDataType zbDataType;
        zbInfo.setTitle(dataField.getTitle());
        zbInfo.setDesc(dataField.getDesc());
        zbInfo.setCode(dataField.getCode());
        zbInfo.setType(ZbType.GENERAL_ZB);
        DataFieldType dataFieldType = dataField.getDataFieldType();
        if (dataFieldType == null || (zbDataType = ZbDataType.valueOf((int)dataFieldType.getValue())) == null) {
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b");
        }
        zbInfo.setDataType(zbDataType);
        zbInfo.setRefEntityId(dataField.getRefDataEntityKey());
        DataFieldGatherType dataFieldGatherType = dataField.getDataFieldGatherType();
        if (dataFieldGatherType != null && (zbGatherType = ZbGatherType.forValue((int)dataFieldGatherType.getValue())) != null) {
            zbInfo.setGatherType(zbGatherType);
        }
        if ((dataFieldApplyType = dataField.getDataFieldApplyType()) != null && (zbApplyType = ZbApplyType.forValue((int)dataFieldApplyType.getValue())) != null) {
            zbInfo.setApplyType(zbApplyType);
        }
        zbInfo.setPrecision(dataField.getPrecision());
        zbInfo.setDecimal(dataField.getDecimal());
        zbInfo.setMeasureUnit(dataField.getMeasureUnit());
        zbInfo.setNullable(Boolean.valueOf(dataField.isNullable()));
        zbInfo.setDefaultValue(dataField.getDefaultValue());
        zbInfo.setAllowUndefinedCode(Boolean.valueOf(dataField.isAllowUndefinedCode()));
        zbInfo.setAllowMultipleSelect(Boolean.valueOf(dataField.isAllowMultipleSelect()));
        zbInfo.setFormatProperties(dataField.getFormatProperties());
        if (!CollectionUtils.isEmpty(dataField.getValidationRules()) && (validationRules = (List)JsonUtils.fromJson((String)(json = JsonUtils.toJson((Object)dataField.getValidationRules())), (TypeReference)new TypeReference<List<ValidationRuleDTO>>(){})) != null) {
            zbInfo.setValidationRules(new ArrayList(validationRules));
        }
    }
}


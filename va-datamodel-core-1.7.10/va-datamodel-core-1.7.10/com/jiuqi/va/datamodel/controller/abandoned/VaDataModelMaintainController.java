/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.datamodel.controller.abandoned;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.domain.DataModelMaintainDO;
import com.jiuqi.va.datamodel.domain.DataModelPublishDTO;
import com.jiuqi.va.datamodel.service.VaDataModelMaintainService;
import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.datamodel.service.impl.help.VaDataModelBizTypeService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="deprecatedDataModelMaintainController")
@Deprecated
@ConditionalOnProperty(name={"va.datamodel.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/dataModel/maintain"})
public class VaDataModelMaintainController {
    private static Logger logger = LoggerFactory.getLogger(VaDataModelMaintainController.class);
    private String successOperate = "datamodel.success.common.operate";
    @Autowired
    private VaDataModelMaintainService vaDataModelMaintainService;
    @Autowired
    private VaDataModelTemplateService vaDataModelTemplateService;
    @Autowired
    private VaDataModelBizTypeService bizTypeService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody DataModelDTO param) {
        PageVO res = new PageVO(true);
        if (!StringUtils.hasText(param.getName())) {
            return MonoVO.just((Object)res);
        }
        DataModelMaintainDO data = this.vaDataModelMaintainService.get(param);
        if (data != null) {
            ArrayList<DataModelMaintainDO> list = new ArrayList<DataModelMaintainDO>();
            list.add(data);
            res.setTotal(list.size());
            res.setRows(list);
        }
        return MonoVO.just((Object)res);
    }

    @PostMapping(value={"/listAll"})
    Object listAll(@RequestBody DataModelDTO param) {
        boolean isPage = param.isPagination();
        param.setPagination(false);
        ArrayList list = this.vaDataModelMaintainService.list(param);
        list = list == null ? new ArrayList() : list;
        PageVO res = new PageVO();
        res.setTotal(list.size());
        res.setRs(R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        if (isPage) {
            int endIndex = param.getOffset() + param.getLimit();
            endIndex = endIndex < list.size() ? endIndex : list.size();
            res.setRows(list.subList(param.getOffset(), endIndex));
        } else {
            res.setRows(list);
        }
        return MonoVO.just((Object)res);
    }

    @PostMapping(value={"/listMaintain"})
    Object listMaintain(@RequestBody DataModelDTO param) {
        param.setPagination(false);
        List<DataModelPublishDTO> list = this.vaDataModelMaintainService.listMaintain(param);
        PageVO res = new PageVO(true);
        if (list != null && !list.isEmpty()) {
            res.getRows().addAll(list);
            res.setTotal(list.size());
        }
        return MonoVO.just((Object)res);
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object add(@RequestBody DataModelDTO param) {
        return MonoVO.just((Object)this.vaDataModelMaintainService.add(param));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object update(@RequestBody DataModelDTO param) {
        return MonoVO.just((Object)this.vaDataModelMaintainService.update(param));
    }

    @Deprecated
    @PostMapping(value={"/publish"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object publish(@RequestBody List<DataModelDTO> dataList) {
        return MonoVO.just((Object)this.vaDataModelMaintainService.publishModel(dataList));
    }

    @PostMapping(value={"/publish/new"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object publishNew(@RequestBody List<DataModelPublishDTO> dataList) {
        return MonoVO.just((Object)this.vaDataModelMaintainService.publish(dataList));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object remove(@RequestBody DataModelDTO param) {
        if (param.getName() == null) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.parameter.missing", new Object[0])));
        }
        DataModelMaintainDO delParam = new DataModelMaintainDO();
        delParam.setName(param.getName());
        if (this.vaDataModelMaintainService.remove(delParam) > 0) {
            return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        }
        return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.delete", new Object[0])));
    }

    @PostMapping(value={"/fieldImport"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object fieldImport(@RequestParam(name="multipartFile") MultipartFile file, @RequestParam(name="colData") String colData) {
        List<String> invalidRelateList;
        List dataModelColumns = JSONUtil.parseArray((String)colData, DataModelColumn.class);
        ArrayList<DataModelColumn> impColList = new ArrayList<DataModelColumn>();
        try (InputStream fileInputStream = file.getInputStream();){
            CsvReader reader = new CsvReader(fileInputStream, ',', Charset.forName("GBK"));
            reader.readHeaders();
            while (reader.readRecord()) {
                String[] values = reader.getValues();
                DataModelColumn col = new DataModelColumn();
                if (!StringUtils.hasText(values[0])) continue;
                col.setColumnName(values[0].trim());
                if (!StringUtils.hasText(values[1])) continue;
                col.setColumnTitle(values[1].trim());
                if (!StringUtils.hasText(values[2])) continue;
                col.setColumnType(DataModelType.ColumnType.valueOf((String)values[2].trim()));
                Integer[] lengths = null;
                if (StringUtils.hasText(values[3])) {
                    lengths = StringUtils.hasText(values[4]) ? new Integer[2] : new Integer[1];
                    lengths[0] = Integer.parseInt(values[3].trim());
                }
                if (lengths != null && StringUtils.hasText(values[4])) {
                    lengths[1] = Integer.parseInt(values[4].trim());
                }
                col.setLengths(lengths);
                if (StringUtils.hasText(values[5])) {
                    col.setNullable(Boolean.valueOf(values[5].trim()));
                } else {
                    col.setNullable(null);
                }
                if (StringUtils.hasText(values[6])) {
                    col.setDefaultVal(values[6].trim());
                } else {
                    col.setDefaultVal(null);
                }
                if (StringUtils.hasText(values[7])) {
                    col.setMappingType(Integer.valueOf(Integer.parseInt(values[7].trim())));
                } else {
                    col.setMappingType(null);
                }
                if (StringUtils.hasText(values[8])) {
                    col.setMapping(values[8].trim());
                } else {
                    col.setMapping(null);
                }
                if (StringUtils.hasText(values[9])) {
                    col.setPkey(Boolean.valueOf(values[9].trim()));
                } else {
                    col.setPkey(null);
                }
                if (StringUtils.hasText(values[10])) {
                    col.setColumnAttr(DataModelType.ColumnAttr.valueOf((String)values[10].trim()));
                } else {
                    col.setColumnAttr(null);
                }
                impColList.add(col);
            }
            reader.close();
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5efa\u6a21\u5b57\u6bb5\u5bfc\u5165\u5f02\u5e38", e);
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.column.import.exception", new Object[0]));
        }
        if (!impColList.isEmpty() && !(invalidRelateList = this.vaDataModelMaintainService.invalidRelateList(impColList)).isEmpty()) {
            StringBuilder invalidRelate = new StringBuilder();
            for (String s : invalidRelateList) {
                invalidRelate.append(s).append("\u3001");
            }
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.column.import.reference.invalid", invalidRelate.substring(0, invalidRelate.length() - 1)));
        }
        LinkedHashMap<String, Object> currModelMap = new LinkedHashMap<String, Object>();
        for (Object col : dataModelColumns) {
            currModelMap.put(col.getColumnName(), col);
        }
        DataModelColumn currCol = null;
        for (DataModelColumn col : impColList) {
            currCol = (DataModelColumn)currModelMap.get(col.getColumnName());
            if (currCol == null) {
                currModelMap.put(col.getColumnName(), col);
                continue;
            }
            if ((DataModelType.ColumnAttr.SYSTEM == currCol.getColumnAttr() || DataModelType.ColumnAttr.FIXED == currCol.getColumnAttr()) && currCol.getColumnType() != col.getColumnType()) continue;
            if (DataModelType.ColumnAttr.SYSTEM == currCol.getColumnAttr()) {
                currCol.setColumnTitle(col.getColumnTitle());
                currCol.setLengths(col.getLengths());
                currCol.setNullable(col.getNullable());
                currCol.setDefaultVal(col.getDefaultVal());
                continue;
            }
            currModelMap.put(col.getColumnName(), col);
        }
        R rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
        rs.put("columns", currModelMap.values());
        return MonoVO.just((Object)rs);
    }

    @PostMapping(value={"/fieldExport"})
    void fieldExport() {
        String colData = RequestContextUtil.getParameter((String)"colData");
        String dataModelTitle = RequestContextUtil.getParameter((String)"dataModelTitle");
        String dataModelName = RequestContextUtil.getParameter((String)"dataModelName");
        List dataModelColumns = JSONUtil.parseArray((String)colData, DataModelColumn.class);
        OutputStream os = null;
        try {
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            RequestContextUtil.setResponseCharacterEncoding((String)"GBK");
            try {
                RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(dataModelName + " " + dataModelTitle + ".csv", "UTF-8").replace("+", " ")));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            os = RequestContextUtil.getOutputStream();
            CsvWriter writer = new CsvWriter(os, ',', Charset.forName("GBK"));
            if ("zh-cn".equalsIgnoreCase(RequestContextUtil.getParameter((String)"language"))) {
                writer.writeRecord(new String[]{"\u6807\u8bc6", "\u540d\u79f0", "\u503c\u7c7b\u578b", "\u957f\u5ea6", "\u5c0f\u6570\u4f4d", "\u662f\u5426\u4e3a\u7a7a", "\u9ed8\u8ba4\u503c", "\u503c\u5f15\u7528\u7c7b\u578b", "\u503c\u5f15\u7528", "\u662f\u5426\u4e3a\u4e3b\u952e", "\u5b57\u6bb5\u5c5e\u6027"});
            } else {
                writer.writeRecord(new String[]{"Name", "Title", "ValueType", "Length", "DecimalPlaces", "IsNull", "DefaultValue", "ReferenceType", "Relation", "IsPrimaryKey", "FieldProperties"});
            }
            String mappingType = "";
            String mapping = "";
            String length = "";
            String decimalLength = "";
            String nullable = "";
            String defaultVal = "";
            String isPkey = "";
            String columnAttr = "";
            for (DataModelColumn col : dataModelColumns) {
                mappingType = col.getMappingType() == null ? "" : col.getMappingType().toString();
                String string = mapping = !StringUtils.hasText(col.getMapping()) ? "" : col.getMapping();
                if (col.getLengths() != null) {
                    length = col.getLengths().length > 0 ? col.getLengths()[0].toString() : "";
                    decimalLength = col.getLengths().length > 1 ? col.getLengths()[1].toString() : "";
                } else {
                    length = "";
                    decimalLength = "";
                }
                nullable = col.isNullable() == null ? "" : col.isNullable().toString();
                defaultVal = !StringUtils.hasText(col.getDefaultVal()) ? "" : col.getDefaultVal();
                isPkey = col.isPkey() == null ? "" : col.isPkey().toString();
                columnAttr = col.getColumnAttr() == null ? "" : col.getColumnAttr().toString();
                writer.writeRecord(new String[]{col.getColumnName(), col.getColumnTitle(), col.getColumnType().toString(), length, decimalLength, nullable, defaultVal, mappingType, mapping, isPkey, columnAttr});
            }
            writer.close();
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5efa\u6a21\u5b57\u6bb5\u5bfc\u51fa\u5f02\u5e38", e);
        }
    }

    @GetMapping(value={"/getSubBizTypes/{bizType}"})
    Object getSubBizTypes(@PathVariable(value="bizType") String bizType) {
        List<DataModelTemplate> list = this.vaDataModelTemplateService.getAllSubTemplate(bizType);
        ArrayList subBizTypes = new ArrayList();
        for (DataModelTemplate template : list) {
            if (!template.isShowSubBizType()) continue;
            HashMap<String, Object> billTypeMap = new HashMap<String, Object>();
            billTypeMap.put("name", template.getName());
            billTypeMap.put("title", template.getTitle());
            billTypeMap.put("ordinal", template.getOrdinal());
            billTypeMap.put("subBizType", template.getSubBizType());
            subBizTypes.add(billTypeMap);
        }
        return MonoVO.just(subBizTypes);
    }

    @GetMapping(value={"/getBizTypes"})
    Object getBizTypes() {
        return MonoVO.just(this.bizTypeService.listBizTypeSimple());
    }
}


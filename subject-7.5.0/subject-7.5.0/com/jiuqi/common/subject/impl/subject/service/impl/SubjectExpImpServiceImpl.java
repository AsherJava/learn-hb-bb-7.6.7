/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.ExcelReader
 *  com.alibaba.excel.read.builder.ExcelReaderSheetBuilder
 *  com.alibaba.excel.read.listener.ReadListener
 *  com.alibaba.excel.read.metadata.ReadSheet
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.builder.ExcelWriterSheetBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.openxml4j.util.ZipSecureFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.subject.impl.subject.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.data.SubjectTreeNodeType;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.expimp.SubjectContentExporter;
import com.jiuqi.common.subject.impl.subject.expimp.SubjectImpExcelListener;
import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.BaseDataField;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectFieldDefineHolder;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpExpDefaultColumnEnum;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpMode;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpParsePojo;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpSavePojo;
import com.jiuqi.common.subject.impl.subject.expimp.style.SubjectExpColumnWidthStyleStrategy;
import com.jiuqi.common.subject.impl.subject.expimp.style.SubjectExpExcelCellStyleStrategy;
import com.jiuqi.common.subject.impl.subject.service.SubjectExpImpService;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubjectExpImpServiceImpl
implements SubjectExpImpService {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectContentExporter subjectContentExporter;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String exportExcel(HttpServletResponse response, boolean templateExportFlag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            String fileName = URLEncoder.encode(String.format("\u79d1\u76ee%1$s", sdf.format(new Date())), StandardCharsets.UTF_8.name());
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            SubjectFieldDefineHolder fieldDefineHolder = new SubjectFieldDefineHolder(Arrays.asList(SubjectImpExpDefaultColumnEnum.values()), new ArrayList<ISubjectExpImpFieldDefine>());
            List<List<String>> headerColumns = this.getHeadColumns(fieldDefineHolder);
            List<List<Object>> expDatas = templateExportFlag ? this.getExportRemarkRow(fieldDefineHolder) : this.subjectContentExporter.doExport(fieldDefineHolder);
            ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new SubjectExpExcelCellStyleStrategy(fieldDefineHolder, templateExportFlag))).registerWriteHandler((WriteHandler)new SubjectExpColumnWidthStyleStrategy(fieldDefineHolder))).sheet("\u79d1\u76ee").head(headerColumns)).doWrite(expDatas);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return "\u5bfc\u51fa\u5b8c\u6210";
    }

    private List<List<Object>> getExportRemarkRow(SubjectFieldDefineHolder fieldDefineHolder) {
        ArrayList<List<Object>> remarkContentList = new ArrayList<List<Object>>();
        ArrayList<String> remarkContent = new ArrayList<String>();
        ArrayList<String> exampleContent = new ArrayList<String>();
        for (ISubjectExpImpFieldDefine fieldDefine : fieldDefineHolder.getDefineList()) {
            remarkContent.add(fieldDefine.getRemark());
            exampleContent.add(fieldDefine.getExample());
        }
        remarkContent.add("\u89c4\u5219\u8bf4\u660e\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        exampleContent.add("\u793a\u4f8b\u884c\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        remarkContentList.add(remarkContent);
        remarkContentList.add(exampleContent);
        return remarkContentList;
    }

    @Override
    public String importExcel(MultipartFile importFile, SubjectImpMode impMode) {
        SubjectFieldDefineHolder fieldDefineHolder = new SubjectFieldDefineHolder(Arrays.asList(SubjectImpExpDefaultColumnEnum.values()), new ArrayList<ISubjectExpImpFieldDefine>());
        BufferedInputStream inputStream = null;
        ExcelReader excelReader = null;
        try {
            inputStream = new BufferedInputStream(importFile.getInputStream());
            ZipSecureFile.setMinInflateRatio((double)-1.0);
            SubjectImpExcelListener listener = new SubjectImpExcelListener(fieldDefineHolder);
            excelReader = EasyExcel.read((InputStream)inputStream).build();
            List<List<String>> headerColumns = this.getHeadColumns(fieldDefineHolder);
            ReadSheet readSheet = ((ExcelReaderSheetBuilder)((ExcelReaderSheetBuilder)EasyExcel.readSheet((Integer)0).head(headerColumns)).registerReadListener((ReadListener)listener)).build();
            excelReader.read(new ReadSheet[]{readSheet});
            List<SubjectDTO> impDatas = listener.getImpParseData();
            SubjectImpParsePojo impParseVo = listener.getImpParseVo();
            List<SubjectDTO> subjectDTOS = this.subjectService.list();
            Map<String, UUID> savedCodeMap = subjectDTOS.stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getId, (k1, k2) -> k2));
            Set parentCodeSet = subjectDTOS.stream().map(BaseDataDO::getCode).collect(Collectors.toSet());
            ArrayList<SubjectDTO> filteredImpDatas = new ArrayList<SubjectDTO>(impDatas.size());
            for (SubjectDTO impSubject : impDatas) {
                if (!SubjectTreeNodeType.ROOT.getCode().equals(impSubject.getParentcode()) && !parentCodeSet.contains(impSubject.getParentcode())) {
                    impParseVo.putSkipData((Integer)impSubject.get("IDX"), String.format("\u79d1\u76ee\u3010%s\u3011\u7236\u7ea7\u4ee3\u7801\u4e0d\u5b58\u5728\uff0c\u81ea\u52a8\u8df3\u8fc7", impSubject.getCode()));
                    continue;
                }
                if (SubjectImpMode.SKIP_REPEAT.equals((Object)impMode)) {
                    if (savedCodeMap.containsKey(impSubject.getCode())) {
                        impSubject.put("Ignored", (Object)true);
                        impParseVo.putSkipData((Integer)impSubject.get("IDX"), String.format("\u79d1\u76ee\u3010%s\u3011\u5df2\u5b58\u5728\uff0c\u81ea\u52a8\u8df3\u8fc7", impSubject.getCode()));
                        continue;
                    }
                    filteredImpDatas.add(impSubject);
                    parentCodeSet.add(impSubject.getCode());
                    continue;
                }
                if (savedCodeMap.containsKey(impSubject.getCode())) {
                    impSubject.setId(savedCodeMap.get(impSubject.getCode()));
                }
                filteredImpDatas.add(impSubject);
                parentCodeSet.add(impSubject.getCode());
            }
            impDatas = filteredImpDatas;
            SubjectImpSavePojo impSaveVo = new SubjectImpSavePojo();
            ArrayList<SubjectDTO> createList = new ArrayList<SubjectDTO>();
            ArrayList<SubjectDTO> modifyList = new ArrayList<SubjectDTO>();
            for (SubjectDTO subjectDTO : impDatas) {
                if (savedCodeMap.containsKey(subjectDTO.getCode())) {
                    modifyList.add(subjectDTO);
                    continue;
                }
                createList.add(subjectDTO);
            }
            try {
                this.subjectService.batchCreate(createList);
                impSaveVo.setCreate(createList.size());
                this.subjectService.batchModify(modifyList);
                impSaveVo.setModify(modifyList.size());
            }
            catch (Exception e) {
                impSaveVo.putFailData(0, String.format("\u79d1\u76ee\u5bfc\u5165\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a%1$s", e.getMessage()));
            }
            String string = this.formatMsg(impParseVo, impSaveVo);
            return string;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            if (inputStream != null) {
                try {
                    ((InputStream)inputStream).close();
                }
                catch (IOException e) {
                    throw new BusinessRuntimeException((Throwable)e);
                }
            }
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    private String formatMsg(SubjectImpParsePojo impVo, SubjectImpSavePojo impSaveVo) {
        int create = impSaveVo.getCreate();
        int modify = impSaveVo.getModify();
        int skip = impVo.getSkipData().size();
        int fail = impVo.getFailData().size() + impSaveVo.getFailData().size();
        int total = create + modify + skip + fail;
        StringBuilder message = new StringBuilder(String.format("\u5bfc\u5165\u5b8c\u6210\uff0c\u5171\u5bfc\u5165%1$d\u6761\u6570\u636e\uff0c\u5176\u4e2d\u65b0\u589e%2$d\u6761\uff0c\u66f4\u65b0%3$d\u6761\uff0c\u8df3\u8fc7%4$d\u6761\uff0c\u5931\u8d25%5$d\u6761\r\n", total, create, modify, skip, fail));
        ArrayList<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer, String>>(total);
        list.addAll(impVo.getFailData().entrySet());
        list.addAll(impVo.getSkipData().entrySet());
        list.addAll(impSaveVo.getFailData().entrySet());
        ArrayList chkMessage = new ArrayList(total);
        list.sort(new Comparator<Map.Entry<Integer, String>>(){

            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        list.forEach(entry -> chkMessage.add(String.format("\u7b2c%1$d\u884c\uff0c%2$s", entry.getKey(), entry.getValue())));
        if (!chkMessage.isEmpty()) {
            message.append("\u5931\u8d25\u6570\u636e\u8be6\u7ec6\u4fe1\u606f\uff1a\r\n").append(StringUtils.join((Object[])chkMessage.toArray(), (String)"\r\n"));
        }
        return message.toString();
    }

    private List<List<String>> getHeadColumns(SubjectFieldDefineHolder fieldDefineHolder) {
        ArrayList<List<String>> headerColumns = new ArrayList<List<String>>();
        ArrayList<String> headerColumn = null;
        for (ISubjectExpImpFieldDefine fieldDefine : fieldDefineHolder.getDefineList()) {
            headerColumn = new ArrayList<String>();
            headerColumn.add(fieldDefine.getName());
            headerColumns.add(headerColumn);
        }
        return headerColumns;
    }

    private Set<String> getInnerFieldSet() {
        Set<String> innerFieldSet = BaseDataStorageUtil.getTemplateFields().stream().map(DataModelColumn::getColumnName).collect(Collectors.toSet());
        innerFieldSet.add("generaltype");
        innerFieldSet.add("orient");
        innerFieldSet.add("asstype");
        innerFieldSet.add("remark");
        return innerFieldSet;
    }

    private List<BaseDataField> getDefineColumns() {
        List showFields = CollectionUtils.newArrayList();
        BaseDataDefineDO baseDataDefine = this.subjectService.findDefineByName(new BaseDataDefineDTO());
        String define = baseDataDefine.getDefine();
        if (StringUtils.isEmpty((String)define)) {
            return showFields;
        }
        try {
            JsonNode defineObj = JsonUtils.readTree((String)define);
            if (defineObj == null) {
                return showFields;
            }
            JsonNode showFieldsNode = defineObj.get("showFields");
            if (showFieldsNode == null) {
                return showFields;
            }
            ArrayNode showFieldsArray = showFieldsNode instanceof ArrayNode ? (ArrayNode)showFieldsNode : (ArrayNode)JsonUtils.readTree((String)showFieldsNode.textValue());
            showFields = (List)JsonUtils.readValue((String)showFieldsArray.toString(), (TypeReference)new TypeReference<List<BaseDataField>>(){});
        }
        catch (Exception e) {
            this.logger.error("\u89e3\u6790\u57fa\u7840\u6570\u636e\u5217\u5b9a\u4e49\u5931\u8d25", e);
        }
        return showFields;
    }
}


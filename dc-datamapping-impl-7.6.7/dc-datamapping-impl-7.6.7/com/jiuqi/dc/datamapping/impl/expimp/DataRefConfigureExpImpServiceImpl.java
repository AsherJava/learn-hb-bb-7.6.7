/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.builder.ExcelWriterSheetBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.enums.RefDynamicField;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefConfigureCellStyleStrategy;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefConfigureExpImpService;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefConfigureTempCellStyleStrategy;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefImpParsePojo;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.datamapping.impl.service.RefChangeService;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataRefConfigureExpImpServiceImpl
implements DataRefConfigureExpImpService {
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private DataRefConfigureService refConfigureService;
    @Autowired
    private ServiceConfigProperties prop;
    @Autowired
    private DataRefConfigureServiceGather dataRefConfigureServiceGather;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;
    @Autowired
    private RefChangeService refChangeService;
    @Autowired
    private OrgAuthService orgAuthService;

    /*
     * Exception decompiling
     */
    @Override
    public String importExcel(MultipartFile importFile, DataRefListDTO dto) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    private String formatMsg(DataRefSaveVO saveVo, DataRefImpParsePojo impVo) {
        int total = impVo.getSuccess() + impVo.getFailData().size();
        int create = saveVo.getCreateData().size();
        int update = saveVo.getUpdateData().size();
        int fail = impVo.getFailData().size() + saveVo.getErrorMessage().size();
        int skip = impVo.getSkipData().size();
        StringBuilder message = new StringBuilder(String.format("\u5bfc\u5165\u5b8c\u6210\uff0c\u5bfc\u5165\u6587\u4ef6\u5171%1$d\u6761\u6570\u636e\uff0c\u5176\u4e2d\u65b0\u589e%2$d\u6761\uff0c\u66f4\u65b0%3$d\u6761\uff0c\u5931\u8d25%4$d\u6761\uff0c\u8df3\u8fc7%5$d\u6761</br>", total, create, update, fail, skip));
        ArrayList failList = new ArrayList(total);
        failList.addAll(saveVo.getErrorMessage().entrySet());
        failList.addAll(impVo.getFailData().entrySet());
        failList.sort(new Comparator<Map.Entry<Integer, String>>(){

            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        if (!failList.isEmpty()) {
            message.append("\u5931\u8d25\u6570\u636e\u8be6\u7ec6\u4fe1\u606f\uff1a</br>").append(StringUtils.join((Object[])failList.stream().map(entry -> String.format("\u7b2c%1$d\u884c\uff0c%2$s", entry.getKey(), entry.getValue())).collect(Collectors.toList()).toArray(), (String)"</br>"));
        }
        ArrayList<Map.Entry<Integer, String>> skipList = new ArrayList<Map.Entry<Integer, String>>(total);
        skipList.addAll(impVo.getSkipData().entrySet());
        skipList.sort(new Comparator<Map.Entry<Integer, String>>(){

            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        if (!skipList.isEmpty()) {
            message.append("</br>\u8df3\u8fc7\u6570\u636e\u8be6\u7ec6\u4fe1\u606f\uff1a</br>").append(StringUtils.join((Object[])skipList.stream().map(entry -> String.format("\u7b2c%1$d\u884c\uff0c%2$s", entry.getKey(), entry.getValue())).collect(Collectors.toList()).toArray(), (String)"</br>"));
        }
        return message.toString();
    }

    private List<List<String>> getImpExpHeader(BaseDataMappingDefineDTO baseDataDefine, boolean templateExportFlag) {
        String label1;
        ArrayList<List<String>> headerColumns = new ArrayList<List<String>>();
        if (!templateExportFlag) {
            headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{"\u6570\u636e\u6620\u5c04\u65b9\u6848"}));
        }
        List<RefDynamicField> refDynamicFields = IsolationUtil.listDynamicRefFieldAndTitle(baseDataDefine);
        for (RefDynamicField refDynamicField : refDynamicFields) {
            headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{refDynamicField.getFieldTitle()}));
        }
        String symbol = templateExportFlag ? "*" : "";
        String string = label1 = StringUtils.isEmpty((String)baseDataDefine.getName()) ? "\u7cfb\u7edf" : baseDataDefine.getName();
        String label2 = "MD_ORG".equals(baseDataDefine.getCode()) ? "\u673a\u6784" : (StringUtils.isEmpty((String)baseDataDefine.getName()) ? "" : baseDataDefine.getName());
        headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{String.format("%1$s\u6e90%2$s\u4ee3\u7801", symbol, label1)}));
        headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{String.format("%1$s\u6e90%2$s\u540d\u79f0", symbol, label1)}));
        String PREFIX_LABEL = "DC".equals(this.prop.getServiceName()) ? "\u4e00\u672c\u8d26" : ("MD_ORG".equals(baseDataDefine.getCode()) ? "\u62a5\u8868" : this.prop.getServiceName());
        headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{String.format("%1$s%2$s%3$s\u4ee3\u7801", symbol, PREFIX_LABEL, label2)}));
        headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{String.format("%1$s%2$s\u540d\u79f0", PREFIX_LABEL, label2)}));
        if (!templateExportFlag) {
            headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{"\u5904\u7406\u72b6\u6001"}));
            headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{"\u64cd\u4f5c\u4eba"}));
            headerColumns.add(CollectionUtils.newArrayList((Object[])new String[]{"\u64cd\u4f5c\u65f6\u95f4"}));
        }
        return headerColumns;
    }

    @Override
    public String exportExcel(HttpServletResponse response, boolean templateExportFlag, DataRefListDTO dto) {
        BaseDataMappingDefineDTO define = this.defineService.getByCode(dto.getDataSchemeCode(), dto.getTableName());
        try {
            String fileName = URLEncoder.encode(String.format("%1$s-%2$s-\u6620\u5c04\u6a21\u677f", dto.getDataSchemeCode(), dto.getTableName()), StandardCharsets.UTF_8.name());
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            List<List<String>> headerColumns = this.getImpExpHeader(define, templateExportFlag);
            if (templateExportFlag) {
                List<List<Object>> expDatas = this.getTempExpData(define, dto);
                ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new DataRefConfigureTempCellStyleStrategy())).registerWriteHandler((WriteHandler)new SimpleColumnWidthStyleStrategy(Integer.valueOf(35)))).sheet(define.getName()).head(headerColumns)).doWrite(expDatas);
            } else {
                List<List<Object>> expDatas = this.getExpData(define, dto);
                ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)((ExcelWriterBuilder)EasyExcel.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new DataRefConfigureCellStyleStrategy())).registerWriteHandler((WriteHandler)new SimpleColumnWidthStyleStrategy(Integer.valueOf(35)))).sheet(define.getName()).head(headerColumns)).doWrite(expDatas);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
        return "\u5bfc\u51fa\u5b8c\u6210";
    }

    private List<List<Object>> getExpData(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        DataRefListVO listVo = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto);
        ArrayList<List<Object>> expRefDatas = new ArrayList<List<Object>>(listVo.getPageVo().getRows().size());
        for (DataRefDTO refDto : listVo.getPageVo().getRows()) {
            ArrayList<Object> row = new ArrayList<Object>(define.getItems().size());
            row.add(refDto.getDataSchemeCode());
            for (String field : IsolationUtil.listDynamicField(define)) {
                row.add(refDto.get((Object)field));
            }
            row.add(refDto.getOdsCode());
            row.add(refDto.getOdsName());
            row.add(refDto.getCode());
            row.add(refDto.getName());
            row.add(RefHandleStatus.getNameByCode(refDto.getHandleStatus()));
            row.add(refDto.getOperator());
            row.add(Objects.nonNull(refDto.getOperateTime()) ? DateUtils.format((Date)refDto.getOperateTime(), (String)"yyyy-MM-dd HH:mm:ss") : null);
            expRefDatas.add(row);
        }
        return expRefDatas;
    }

    private List<List<Object>> getTempExpData(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        DataRefListVO listVo = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto);
        ArrayList<List<Object>> expRefDatas = new ArrayList<List<Object>>(listVo.getPageVo().getRows().size());
        ArrayList<String> firstRow = new ArrayList<String>(define.getItems().size() + 2);
        ArrayList<String> secondRow = new ArrayList<String>(define.getItems().size() + 2);
        List<RefDynamicField> refDynamicFields = IsolationUtil.listDynamicRefFieldAndTitle(define);
        for (RefDynamicField refDynamicField : refDynamicFields) {
            String sysStr = "DC_UNITCODE".equals(refDynamicField.getFieldName()) ? "\u4e00\u672c\u8d26" : "\u6e90\u7cfb\u7edf";
            firstRow.add("\u975e\u5fc5\u586b\uff0c" + sysStr + "\u4e2d\u5bf9\u5e94\u6570\u636e\u7684" + refDynamicField.getFieldTitle());
            secondRow.add("\u793a\u4f8b\u6570\u636e");
        }
        firstRow.add("\u5fc5\u586b\uff0c\u6e90\u7cfb\u7edf\u4e2d\u5bf9\u5e94\u6570\u636e\u7684\u4ee3\u7801");
        secondRow.add("SL0102");
        firstRow.add("\u5fc5\u586b\uff0c\u6e90\u7cfb\u7edf\u4e2d\u5bf9\u5e94\u6570\u636e\u7684\u540d\u79f0");
        secondRow.add("\u793a\u4f8b\u6570\u636e");
        String PREFIX_LABEL = "DC".equals(this.prop.getServiceName()) ? "\u4e00\u672c\u8d26" : ("MD_ORG".equals(define.getCode()) ? "\u62a5\u8868" : this.prop.getServiceName());
        firstRow.add("\u5fc5\u586b\uff0c" + PREFIX_LABEL + "\u4e2d\u5bf9\u5e94\u6570\u636e\u7684\u4ee3\u7801");
        firstRow.add("\u975e\u5fc5\u586b\uff0c" + PREFIX_LABEL + "\u4e2d\u5bf9\u5e94\u6570\u636e\u7684\u540d\u79f0");
        firstRow.add("\u89c4\u5219\u8bf4\u660e\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        secondRow.add("SLDW01");
        secondRow.add("\u793a\u4f8b\u6570\u636e");
        secondRow.add("\u793a\u4f8b\u884c\uff0c\u5bfc\u5165\u65f6\u9700\u5220\u9664\u6b64\u884c");
        expRefDatas.add(firstRow);
        expRefDatas.add(secondRow);
        return expRefDatas;
    }

    private static /* synthetic */ DataRefDTO lambda$importExcel$5(DataRefDTO existing, DataRefDTO replacement) {
        return existing;
    }

    private static /* synthetic */ DataRefDTO lambda$importExcel$4(DataRefDTO item) {
        return item;
    }

    private static /* synthetic */ IsolationParamContext lambda$importExcel$3(BaseDataMappingDefineDTO define, DataRefDTO item) {
        return IsolationUtil.buildIsolationParam(item, define);
    }

    private static /* synthetic */ DataRefDTO lambda$importExcel$2(DataRefDTO existing, DataRefDTO replacement) {
        return existing;
    }

    private static /* synthetic */ DataRefDTO lambda$importExcel$1(DataRefDTO item) {
        return item;
    }

    private static /* synthetic */ IsolationParamContext lambda$importExcel$0(BaseDataMappingDefineDTO define, DataRefDTO item) {
        return IsolationUtil.buildIsolationParam(item, define);
    }
}


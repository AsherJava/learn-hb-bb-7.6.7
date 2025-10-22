/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  io.swagger.annotations.Api
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.io.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.params.input.QueryParms;
import com.jiuqi.nr.io.params.output.Datablocks;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.params.output.ExportJsonData;
import com.jiuqi.nr.io.service.ExportService;
import com.jiuqi.nr.io.util.ParamUtil;
import io.swagger.annotations.Api;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"ExportController-\u5bfc\u51fa"})
public class ExportController {
    private static final Logger log = LoggerFactory.getLogger(ExportController.class);
    @Autowired
    ExportService exportService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value={"/io/exportTxt"})
    public void exportTxt(@Valid @RequestBody QueryParms params, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TableContext tbContext = ParamUtil.getRealParam(params);
        String agent = request.getHeader("User-Agent").toLowerCase();
        String suffix = ".txt";
        String zipName = "ExportTxtDatas.zip";
        try {
            zipName = agent.contains("MSIE") || agent.contains("Trident") ? URLEncoder.encode(zipName, "UTF-8") : new String(zipName.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (Exception e) {
            log.info("\u6587\u4ef6\u540dencode\u51fa\u9519{}", e);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        response.setContentType("application/octet-stream");
        try (ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream((OutputStream)response.getOutputStream()));){
            List forms;
            zipos.setMethod(8);
            if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
                forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.initTxtZip(params, response, result, suffix, zipos);
                }
            } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
                forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.initTxtZip(params, response, result, suffix, zipos);
                }
            } else {
                List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                this.initTxtZip(params, response, result, suffix, zipos);
            }
        }
    }

    private void initTxtZip(QueryParms params, HttpServletResponse response, List<IRegionDataSet> result, String suffix, ZipOutputStream zipos) {
        DataOutputStream os = null;
        for (IRegionDataSet item : result) {
            String fileName = "";
            try {
                List<ExportFieldDefine> def = item.getFieldDataList();
                if (def.size() == 0) continue;
                RegionData region = item.getRegionData();
                fileName = region.getFormCode();
                if (region.getType() == 3) {
                    fileName = fileName + "_F" + region.getRegionTop();
                }
                fileName = fileName + suffix;
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipos.putNextEntry(zipEntry);
                os = new DataOutputStream(zipos);
                StringBuffer fieldDefineHead = new StringBuffer();
                for (ExportFieldDefine fieldDefine : def) {
                    fieldDefineHead.append(fieldDefine.getCode()).append(params.getSplit());
                }
                os.writeBytes(fieldDefineHead.deleteCharAt(fieldDefineHead.length() - 1).append("\r\n").toString());
                while (item.hasNext()) {
                    ArrayList dataRows = (ArrayList)item.next();
                    if (dataRows.size() == 0) continue;
                    StringBuffer formatResul = new StringBuffer();
                    Arrays.asList(dataRows.toArray()).stream().forEach(x -> formatResul.append(x).append(params.getSplit()));
                    os.writeBytes(formatResul.deleteCharAt(formatResul.length() - 1).append("\r\n").toString());
                }
                response.flushBuffer();
                zipos.closeEntry();
            }
            catch (IOException e) {
                log.info("\u6570\u636e\u5199\u5165\u51fa\u9519{}", e);
            }
        }
    }

    @PostMapping(value={"/io/exportJson"})
    public void exportJson(@Valid @RequestBody QueryParms params, HttpServletResponse response, HttpServletRequest request) {
        TableContext tbContext = ParamUtil.getRealParam(params);
        String agent = request.getHeader("User-Agent").toLowerCase();
        String zipName = "ExportJsonDatas.zip";
        try {
            zipName = agent.contains("MSIE") || agent.contains("Trident") ? URLEncoder.encode(zipName, "UTF-8") : new String(zipName.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch (Exception e) {
            log.info("\u6587\u4ef6\u540dencode\u51fa\u9519{}", e);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        response.setContentType("application/octet-stream");
        try (ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream((OutputStream)response.getOutputStream()));){
            List forms;
            zipos.setMethod(8);
            if (tbContext.getOptType().equals((Object)OptTypes.TASK)) {
                forms = this.runTimeViewController.queryAllFormDefinesByTask(tbContext.getTaskKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.generalZip(result, zipos, tbContext);
                    response.flushBuffer();
                }
            } else if (tbContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) {
                forms = this.runTimeViewController.queryAllFormDefinesByFormScheme(tbContext.getFormSchemeKey());
                tbContext.setOptType(OptTypes.FORM);
                for (FormDefine item : forms) {
                    tbContext.setFormKey(item.getKey());
                    List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                    this.generalZip(result, zipos, tbContext);
                    response.flushBuffer();
                }
            } else {
                List<IRegionDataSet> result = this.exportService.getRegionsDatas(tbContext);
                this.generalZip(result, zipos, tbContext);
                response.flushBuffer();
            }
        }
        catch (Exception e) {
            log.error("\u6267\u884c\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage(), e);
            try {
                response.sendError(500, "\u6267\u884c\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage());
            }
            catch (IOException ex) {
                response.setStatus(500, "\u6267\u884c\u8fc7\u7a0b\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage());
            }
        }
    }

    private void generalZip(List<IRegionDataSet> result, ZipOutputStream zipos, TableContext context) throws Exception {
        if (null == result || result.size() == 0) {
            return;
        }
        DataOutputStream os = null;
        String suffix = ".json";
        ExportJsonData ejd = new ExportJsonData();
        List<ExportEntity> entitys = this.exportService.getEntitys(context);
        String fileName = "";
        ArrayList<Datablocks> datablocks = new ArrayList<Datablocks>();
        for (IRegionDataSet item : result) {
            List<ExportFieldDefine> def = item.getFieldDataList();
            if (def.size() == 0) continue;
            Datablocks datablock = new Datablocks();
            RegionData region = item.getRegionData();
            if (null == ejd.getFormCode() || fileName.equals("")) {
                fileName = region.getFormCode();
                ejd.setFormCode(fileName);
                fileName = fileName + suffix;
            }
            datablock.setFields(def);
            datablock.setIsFloat(item.isFloatRegion());
            datablock.setRegionTop(region.getRegionTop());
            ArrayList<Object> datas = new ArrayList<Object>();
            boolean zipEntryFlag = false;
            ArrayList firstRow = null;
            if (item.hasNext()) {
                firstRow = (ArrayList)item.next();
                datablock.setTotalCount(item.getTotalCount());
            }
            ArrayList<ZipEntry> zipEntrys = new ArrayList<ZipEntry>();
            int zipEntryRowsCount = 0;
            if (null != firstRow && firstRow.size() > 0 && item.getTotalCount() > 20000) {
                ArrayList<String> dataFilesName = new ArrayList<String>();
                for (int i = 0; i < (int)Math.ceil((double)item.getTotalCount() * 1.0 / 10000.0); ++i) {
                    String dataFileName = region.getFormCode() + "_ROWDATAS" + (region.getType() == 3 ? "_F" : "") + region.getRegionTop() + "_" + i + suffix;
                    ZipEntry zipEntry = new ZipEntry(dataFileName);
                    dataFilesName.add(dataFileName);
                    zipEntrys.add(zipEntry);
                }
                zipos.putNextEntry((ZipEntry)zipEntrys.get(0));
                zipEntryFlag = true;
                datablock.setLinkDatasFilesName(dataFilesName);
                os = new DataOutputStream(zipos);
                os.writeBytes("[" + this.objectMapper.writeValueAsString((Object)firstRow) + "\r\n");
                ++zipEntryRowsCount;
            } else {
                datas.add(firstRow);
            }
            while (item.hasNext()) {
                ArrayList dataRow = (ArrayList)item.next();
                if (zipEntryFlag) {
                    if (zipEntryRowsCount % 10000 == 0) {
                        os.writeBytes("]");
                        zipos.putNextEntry((ZipEntry)zipEntrys.get(zipEntryRowsCount / 10000));
                        os = new DataOutputStream(zipos);
                        os.writeBytes("[".concat(this.objectMapper.writeValueAsString((Object)dataRow)));
                        ++zipEntryRowsCount;
                        continue;
                    }
                    os.writeBytes(",".concat(this.objectMapper.writeValueAsString((Object)dataRow)));
                    ++zipEntryRowsCount;
                    continue;
                }
                datas.add(dataRow);
            }
            if (item.getTotalCount() > 1000) {
                zipos.closeEntry();
            }
            datablock.setDatas(datas);
            datablocks.add(datablock);
        }
        ejd.setDatablocks(datablocks);
        ejd.setEntitys(entitys);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipos.putNextEntry(zipEntry);
        os = new DataOutputStream(zipos);
        os.write(this.objectMapper.writeValueAsBytes((Object)ejd));
        zipos.closeEntry();
    }
}


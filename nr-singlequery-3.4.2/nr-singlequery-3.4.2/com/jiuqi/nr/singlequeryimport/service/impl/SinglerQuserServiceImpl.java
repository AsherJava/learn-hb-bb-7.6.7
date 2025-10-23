/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.ncell.GridDataConverter
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.syntax.excel.ExcelExportor
 *  com.jiuqi.bi.syntax.excel.ISheetDescriptor
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.designer.service.StepSaveService
 *  com.jiuqi.nr.designer.web.treebean.TaskLinkObject
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.zbquery.util.ZBQueryLogHelper
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.ncell.GridDataConverter;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.syntax.excel.ExcelExportor;
import com.jiuqi.bi.syntax.excel.ISheetDescriptor;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.singlequeryimport.bean.BatchQueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.dao.SingleModleDao;
import com.jiuqi.nr.singlequeryimport.service.QueryByCustomService;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.singlequeryimport.service.SingleQueryService;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SinglerQuserServiceImpl
implements SingleQueryService {
    Logger logger = LoggerFactory.getLogger(SinglerQuserServiceImpl.class);
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    IEntityMetaService iEntityMetaService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    QueryModleService queryModleService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    BaseDataClient baseDataClient;
    @Autowired
    SingleModleDao singleModleDao;
    @Autowired
    QueryByCustomService queryByCustom;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private StepSaveService stepSaveService;

    @Override
    public void export(final QueryConfigInfo queryConfigInfo, HttpServletResponse response) {
        try {
            Collection headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                HtmlUtils.validateHeaderValue((String)response.getHeader(headerName));
            }
            GridData gridData = null;
            queryConfigInfo.setPageInfo(null);
            QueryResult queryResultVO = this.queryByCustom.query(queryConfigInfo);
            long searchTime = System.currentTimeMillis();
            JSONObject nCellData = new JSONObject(queryResultVO.getGridData());
            gridData = GridDataConverter.buildGridData((JSONObject)nCellData);
            long converTime = System.currentTimeMillis();
            this.logger.info("\u6570\u636e\u8f6cgridData\u65f6\u95f4--->{}", (Object)(converTime - searchTime));
            ArrayList<1> sheetDescriptors = new ArrayList<1>();
            final GridData finalGridData = gridData;
            sheetDescriptors.add(new ISheetDescriptor(){

                public String name() {
                    return queryConfigInfo.getTitle();
                }

                public GridData grid() {
                    return finalGridData;
                }

                public String getExpression(int arg0, int arg1) {
                    return null;
                }

                public void setExpression(int arg0, int arg1, String arg2) {
                }
            });
            ExcelExportor exportor = new ExcelExportor(sheetDescriptors.iterator(), true);
            String attachName = queryConfigInfo.getTitle() + ".xlsx";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachName, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            exportor.export((OutputStream)outputStream);
            ZBQueryLogHelper.info((String)"\u5bfc\u51fa\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfigInfo.getTitle()));
            response.flushBuffer();
            long streamTime = System.currentTimeMillis();
            this.logger.info("\u6570\u636e\u6d41\u5bfc\u51fa\u65f6\u95f4--->{}", (Object)(streamTime - converTime));
        }
        catch (Exception e) {
            ZBQueryLogHelper.error((String)"\u5bfc\u51fa\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfigInfo.getTitle()));
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(502);
            JSONObject resultdata = new JSONObject();
            resultdata.put("success", false);
            resultdata.put("data", (Object)e.getMessage());
            try {
                response.getWriter().write(resultdata.toString());
            }
            catch (IOException e1) {
                e1.getMessage();
            }
        }
    }

    @Override
    public void batchExport(BatchQueryConfigInfo queryConfigInfos, HttpServletResponse response) {
        ArrayList<String> errList = new ArrayList<String>();
        String fileLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + "JSExport" + BatchExportConsts.SEPARATOR + UUID.randomUUID().toString() + BatchExportConsts.SEPARATOR;
        File folder = new File(fileLocation);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDateFolder = dateFormatForFolder.format(date);
        String zipFileName = fileLocation + formatDateFolder + ".zip";
        try {
            this.compressedModelData(zipFileName, queryConfigInfos, errList);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            String fileName = URLEncoder.encode(zipFileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            try (FileInputStream input = new FileInputStream(zipFileName);){
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = ((InputStream)input).read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    void compressedModelData(String zipFileName, BatchQueryConfigInfo queryConfigInfos, List<String> errList) throws Exception {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(zipFileName));
             ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
            for (String modelId : queryConfigInfos.getModelIds()) {
                QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
                queryConfigInfo.setModelId(modelId);
                QueryModel queryModel = null;
                try {
                    queryModel = this.queryModleService.getQueryModelByKey(modelId);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage());
                }
                queryConfigInfo.setTitle(queryModel == null ? modelId : queryModel.getItemTitle());
                queryConfigInfo.setCacheId(queryConfigInfos.getCacheId());
                queryConfigInfo.setConditionValues(queryConfigInfos.getConditionValues());
                this.exportSingleModel(queryConfigInfo, zos, errList);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void templateExport(List<String> keyList, HttpServletResponse response) throws Exception {
        String fileLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + "JSExport" + BatchExportConsts.SEPARATOR + UUID.randomUUID().toString() + BatchExportConsts.SEPARATOR;
        File folder = new File(fileLocation);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDateFolder = dateFormatForFolder.format(date);
        String zipFileName = fileLocation + formatDateFolder + ".zip";
        PathUtils.validatePathManipulation((String)zipFileName);
        try {
            this.compressedModel(zipFileName, keyList);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            String fileName = URLEncoder.encode(zipFileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            try (FileInputStream input = new FileInputStream(zipFileName);){
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = ((InputStream)input).read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    void compressedModel(String zipFileName, List<String> keyList) throws Exception {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(zipFileName));
             ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
            List<QueryModel> modelList = this.queryModeleDao.getMdoelByKyes(keyList);
            for (QueryModel queryModel : modelList) {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryModel.getTaskKey());
                DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                String prefix = dataScheme.getPrefix();
                List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(queryModel.getTaskKey());
                ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
                String relatePrefix = "";
                if (!taskLinkObjBySchemeKey.isEmpty()) {
                    FormSchemeDefine relateFormScheme = this.iRunTimeViewController.getFormScheme(((TaskLinkObject)taskLinkObjBySchemeKey.get(0)).getRelatedFormSchemeKey());
                    TaskDefine relateTaskDefine = this.iRunTimeViewController.queryTaskDefine(relateFormScheme.getTaskKey());
                    DataScheme relateDataScheme = this.iRuntimeDataSchemeService.getDataScheme(relateTaskDefine.getDataScheme());
                    relatePrefix = relateDataScheme.getPrefix();
                }
                this.logger.info("\u8be5\u4efb\u52a1\u5173\u8054\u7684\u4efb\u52a1\u524d\u7f00\u662f--\u300b{}", (Object)relatePrefix);
                JSONObject item = new JSONObject(queryModel.getItem());
                item.put("order", (Object)queryModel.getOrder());
                String group = queryModel.getGroup();
                String itemTitle = queryModel.getItemTitle();
                JSONObject filter = item.getJSONObject("filter");
                if (!filter.isEmpty()) {
                    String code;
                    if (filter.has("filterCondition")) {
                        code = this.changZb(filter.getString("filterCondition"), relatePrefix, prefix);
                        filter.put("filterCondition", (Object)code);
                    }
                    if (filter.has("formulaContent")) {
                        code = this.changZb(filter.getString("formulaContent"), relatePrefix, prefix);
                        filter.put("formulaContent", (Object)code);
                    }
                }
                JSONObject model = item.getJSONObject("model");
                item.put("group", (Object)group);
                item.put("title", (Object)itemTitle);
                JSONArray data = model.getJSONArray("data");
                JSONArray zbList = data.getJSONArray(data.length() - 2);
                for (int index = 0; index < zbList.length(); ++index) {
                    String zb = zbList.getJSONObject(index).getString("v");
                    String code = this.changZb(zb, relatePrefix, prefix);
                    zbList.getJSONObject(index).put("v", (Object)code);
                }
                byte[] bufs = item.toString().getBytes();
                zos.putNextEntry(new ZipEntry(itemTitle + ".json"));
                zos.write(bufs, 0, bufs.length);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    String changZb(String code, String relatePrefix, String prefix) {
        if (code.equals("@")) {
            this.logger.info("\u6307\u6807{}\u4e2d\u542b\u6709@\u7b26\u5408\uff0c\u786e\u8ba4\u8be5\u6a21\u677f\u662f\u5426\u53ef\u4ee5\u4f7f\u7528", (Object)code);
            return code;
        }
        if (code.contains(relatePrefix)) {
            code = code.replace(relatePrefix, "RELATE");
        }
        if (code.contains(prefix)) {
            code = code.replace(prefix, "MAIN");
        }
        return code;
    }

    private void exportSingleModel(final QueryConfigInfo queryConfigInfo, ZipOutputStream zos, List<String> errList) {
        try {
            GridData gridData = null;
            queryConfigInfo.setPageInfo(null);
            QueryResult queryResultVO = this.queryByCustom.query(queryConfigInfo);
            JSONObject nCellData = new JSONObject(queryResultVO.getGridData());
            gridData = GridDataConverter.buildGridData((JSONObject)nCellData);
            ArrayList<2> sheetDescriptors = new ArrayList<2>();
            final GridData finalGridData = gridData;
            sheetDescriptors.add(new ISheetDescriptor(){

                public String name() {
                    return queryConfigInfo.getTitle();
                }

                public GridData grid() {
                    return finalGridData;
                }

                public String getExpression(int arg0, int arg1) {
                    return null;
                }

                public void setExpression(int arg0, int arg1, String arg2) {
                }
            });
            ExcelExportor exportor = new ExcelExportor(sheetDescriptors.iterator(), true);
            String attachName = queryConfigInfo.getTitle() + ".xlsx";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exportor.export((OutputStream)outputStream);
            byte[] bufs = outputStream.toByteArray();
            zos.putNextEntry(new ZipEntry(attachName));
            zos.write(bufs, 0, bufs.length);
        }
        catch (Exception e) {
            ZBQueryLogHelper.error((String)"\u5bfc\u51fa\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfigInfo.getTitle()));
            this.logger.error(e.getMessage());
            errList.add(e.getMessage());
        }
    }
}


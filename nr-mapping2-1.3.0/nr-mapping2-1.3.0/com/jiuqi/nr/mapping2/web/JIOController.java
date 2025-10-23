/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.transfer.engine.service.ITransferFileHandler
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping2.web;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.transfer.engine.service.ITransferFileHandler;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.mapping2.bean.MappingConfig;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.JIOStateDTO;
import com.jiuqi.nr.mapping2.dto.JIOUploadParam;
import com.jiuqi.nr.mapping2.dto.NVWADataUploadParam;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.JIOProvider;
import com.jiuqi.nr.mapping2.service.NvdataMappingService;
import com.jiuqi.nr.mapping2.util.NvMappingInsertRule;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;
import com.jiuqi.nr.mapping2.util.NvdataMappingContext;
import com.jiuqi.nr.mapping2.util.OSSUtils;
import com.jiuqi.nr.mapping2.web.vo.EntityVO;
import com.jiuqi.nr.mapping2.web.vo.LabelVO;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/mapping2/"})
public class JIOController {
    protected final Logger logger = LoggerFactory.getLogger(JIOController.class);
    @Autowired
    JIOConfigService jioService;
    @Autowired
    JIOProvider jioProvider;
    @Autowired
    ITransferFileHandler transferFileHandler;
    @Autowired
    IMappingSchemeService service;
    @Autowired(required=false)
    NvdataMappingService nvdataMappingService;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"jio/upload/{msKey}"})
    @ApiOperation(value="\u4e0a\u4f20")
    public Result uploadJIO(@PathVariable String msKey, JIOUploadParam param, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        msKey = HtmlUtils.cleanUrlXSS((String)msKey);
        try (InputStream in = file.getInputStream();){
            String oldFileKey = this.jioService.getJIOFileByMs(msKey);
            if (StringUtils.hasText(oldFileKey) && OSSUtils.exist(oldFileKey)) {
                OSSUtils.delete(oldFileKey);
            }
            String fileKey = UUID.randomUUID().toString();
            OSSUtils.upload(fileKey, in, file.getSize());
            Result result = this.jioProvider.execute(fileKey, file.getBytes(), msKey, param);
            return result;
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping(value={"jio/content/{msKey}"})
    @ApiOperation(value="\u83b7\u53d6JIO\u4fe1\u606f")
    public JIOContent getJIOContent(@PathVariable String msKey) {
        return this.jioService.getJIOContentByMs(msKey);
    }

    @GetMapping(value={"jio/download/{msKey}"})
    @ApiOperation(value="\u4e0b\u8f7dJIO\u6587\u4ef6")
    public void export(@PathVariable String msKey, HttpServletResponse response) throws Exception {
        String fileKey = this.jioService.getJIOFileByMs(msKey);
        InputStream fileStream = OSSUtils.download(fileKey);
        byte[] jioFile = IOUtils.toByteArray(fileStream);
        String fileName = "jio\u6587\u4ef6.jio";
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        try (BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            ((OutputStream)outputStream).write(jioFile, 0, jioFile.length);
            ((OutputStream)outputStream).flush();
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"jio/state/{msKey}"})
    @ApiOperation(value="\u5224\u65adJIO\u7684\u72b6\u6001")
    public JIOStateDTO isJIOScheme(@PathVariable String msKey) {
        return this.jioService.isJIOSchemeWithFile(this.service.getSchemeByKey(msKey));
    }

    @GetMapping(value={"config/query-config/{mappingSchemeId}"})
    @ApiOperation(value="\u83b7\u53d6\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public MappingConfig queryConfig(@PathVariable String mappingSchemeId) throws JQException {
        return this.jioService.queryMappingConfig(mappingSchemeId);
    }

    @PostMapping(value={"config/update-config/{mappingSchemeId}"})
    @ApiOperation(value="\u66f4\u65b0\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public void updateConfig(@PathVariable String mappingSchemeId, @Valid @RequestBody MappingConfig mappingConfig) throws JQException {
        mappingSchemeId = HtmlUtils.cleanUrlXSS((String)mappingSchemeId);
        this.jioService.updateMappingConfig(mappingSchemeId, mappingConfig);
    }

    @GetMapping(value={"config/query-attribute/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u7ef4\u5ea6\u7684\u5c5e\u6027")
    public List<EntityVO> queryEntityAttribute(@PathVariable String formSchemekey) throws Exception {
        return this.jioService.queryEntityAttribute(formSchemekey);
    }

    @GetMapping(value={"config/query-formula-scheme/{reportKey}"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u65b9\u6848")
    public List<FormulaSchemeDefine> querySchemeByReport(@PathVariable(value="reportKey") String reportKey) {
        return this.jioService.getFormulaSchemesByReport(reportKey);
    }

    @GetMapping(value={"config/query-entityId/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u7ef4\u5ea6\u5b9e\u4f53id")
    public String queryEntityIdByFormSchemeKey(@PathVariable String formSchemekey) throws Exception {
        return this.jioService.queryEntityIdByFormSchemeKey(formSchemekey);
    }

    @GetMapping(value={"data/upload/enum"})
    @ApiOperation(value="\u83b7\u53d6\u5339\u914d\u89c4\u5219\u3001\u751f\u6210\u65b9\u5f0f\u679a\u4e3e")
    public Map<String, List<LabelVO>> getEnum() {
        HashMap<String, List<LabelVO>> map = new HashMap<String, List<LabelVO>>();
        ArrayList<LabelVO> matchList = new ArrayList<LabelVO>();
        for (NvMappingMatchRule rule : NvMappingMatchRule.values()) {
            LabelVO label = new LabelVO();
            label.setCode(rule.name());
            label.setTitle(rule.getTitle());
            matchList.add(label);
        }
        map.put("matchRuleList", matchList);
        ArrayList<LabelVO> insertList = new ArrayList<LabelVO>();
        for (NvMappingInsertRule rule : NvMappingInsertRule.values()) {
            LabelVO label = new LabelVO();
            label.setCode(rule.name());
            label.setTitle(rule.getTitle());
            insertList.add(label);
        }
        map.put("insertRuleList", insertList);
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"data/upload/{msKey}"})
    @ApiOperation(value="\u5973\u5a32\u53c2\u6570\u5305\u4e0a\u4f20")
    @Transactional
    public Result uploadData(@PathVariable String msKey, NVWADataUploadParam nvwaDataUploadParam, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        Result result = new Result();
        msKey = HtmlUtils.cleanUrlXSS((String)msKey);
        if (this.nvdataMappingService == null) {
            result.setSuccess(false);
            result.setMessage("\u63a5\u53e3\u672a\u5b9e\u73b0\uff01");
            return result;
        }
        try (InputStream fileInputStream = file.getInputStream();){
            Map<String, List<String>> factoryIdMap = this.nvdataMappingService.getMappingParams(nvwaDataUploadParam);
            Map param = this.transferFileHandler.read(fileInputStream, factoryIdMap, NpContextHolder.getContext().getUserName());
            NvdataMappingContext context = new NvdataMappingContext();
            MappingScheme scheme = this.service.getSchemeByKey(msKey);
            NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam(scheme);
            if (nrMappingParam == null) {
                result.setSuccess(false);
                result.setMessage("\u65b9\u6848\u672a\u914d\u7f6eNR\u6620\u5c04\u53c2\u6570\uff01");
                Result result3 = result;
                return result3;
            }
            context.setMappingSchemeKey(msKey);
            context.setFormSchemeSchemeKey(nrMappingParam.getFormSchemeKey());
            context.setNvMappingInsertRule(nvwaDataUploadParam.getInsertRule());
            context.setNvMappingMatchRule(nvwaDataUploadParam.getMatchRule());
            this.nvdataMappingService.saveNvdataMapping(param, context);
            result.setSuccess(true);
            result.setMessage("\u4e0a\u4f20\u6210\u529f");
            Result result2 = result;
            return result2;
        }
        catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("\u53c2\u6570\u5305\u89e3\u6790\u51fa\u73b0\u9519\u8bef\uff01");
            this.logger.error(e.getMessage(), e);
            return result;
        }
    }
}


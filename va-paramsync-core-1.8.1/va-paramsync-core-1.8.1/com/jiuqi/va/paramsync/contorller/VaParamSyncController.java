/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.paramsync.common.VaParamSyncCacheUtil
 *  com.jiuqi.va.paramsync.common.VaParamSyncModulesProvider
 *  com.jiuqi.va.paramsync.domain.VaParamSyncImpGroupsDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  com.jiuqi.va.paramsync.domain.VaParamSyncParamDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResultDO
 *  com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.paramsync.contorller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.paramsync.common.VaParamSyncCacheUtil;
import com.jiuqi.va.paramsync.common.VaParamSyncModulesProvider;
import com.jiuqi.va.paramsync.domain.VaParamSyncImpGroupsDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResultDO;
import com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value={"/paramsync"})
public class VaParamSyncController {
    private static final Logger logger = LoggerFactory.getLogger(VaParamSyncController.class);
    private static final String REQ_ID = "id";
    private static final String REQ_MULTIPARTFILE = "multipartFile";
    private static final String REQUEST_ID = "requestID";

    @PostMapping(value={"/export"})
    void export(@RequestBody VaParamSyncParamDO param) {
        VaParamSyncModuleEnum paramType = param.getParamType();
        VaParamSyncFeignClient feignClient = VaParamSyncModulesProvider.getClient((String)paramType.getName());
        if (feignClient == null) {
            return;
        }
        VaParamSyncResponseDO rexportData = feignClient.export(param);
        RequestContextUtil.setResponseContentType((String)"application/zip;charset=utf-8");
        RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(RequestContextUtil.getOutputStream(), StandardCharsets.UTF_8);){
            zipOutputStream.putNextEntry(new ZipEntry("mainfest.json"));
            zipOutputStream.write(rexportData.getMainfest().getBytes(StandardCharsets.UTF_8));
            Map allData = rexportData.getAllData();
            Map data = null;
            for (Map.Entry allEntry : allData.entrySet()) {
                data = (Map)allEntry.getValue();
                if (data == null || data.isEmpty()) continue;
                for (Map.Entry entry : data.entrySet()) {
                    zipOutputStream.putNextEntry(new ZipEntry((String)allEntry.getKey() + "/" + (String)entry.getKey() + ".json"));
                    zipOutputStream.write(((String)entry.getValue()).getBytes(StandardCharsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u5bfc\u51fa" + paramType.getTitle() + "\u751f\u6210\u6587\u4ef6\u5f02\u5e38", e);
        }
    }

    @GetMapping(value={"/catche/{id}/exit"})
    R checkCatcheExit(@PathVariable(value="id") String id) {
        if (!VaParamSyncCacheUtil.refreshFileCacheTime((String)id)) {
            return R.error();
        }
        return R.ok();
    }

    @PostMapping(value={"/import/groups"})
    R getImportGroups(@RequestParam(name="multipartFile", required=false) MultipartFile multipartFile, VaParamSyncImpGroupsDO params) {
        String id = params.getId();
        byte[] cache = VaParamSyncCacheUtil.getFileCache((String)id);
        VaParamSyncMainfestDO mainfestDO = null;
        if (cache == null) {
            if (multipartFile.isEmpty()) {
                return R.error((String)"\u4e0a\u4f20\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a");
            }
            try {
                VaParamSyncCacheUtil.setFileCache((String)id, (MultipartFile)multipartFile);
            }
            catch (IOException e) {
                logger.error("\u53c2\u6570\u5bfc\u5165\u7f13\u5b58\u5f02\u5e38", e);
            }
        }
        try (ZipInputStream zis = cache == null ? new ZipInputStream(multipartFile.getInputStream()) : new ZipInputStream(new ByteArrayInputStream(cache));){
            byte[] buffer = new byte[2048];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                if (entry.getName().equals("mainfest.json")) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        baos.write(buffer, 0, len);
                    }
                    baos.close();
                    String mainfestStr = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                    mainfestDO = (VaParamSyncMainfestDO)JSONUtil.parseObject((String)mainfestStr, VaParamSyncMainfestDO.class);
                    break;
                }
                entry = zis.getNextEntry();
            }
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u89e3\u6790\u5bfc\u5165\u6587\u4ef6\u5f02\u5e38", e);
        }
        if (mainfestDO == null) {
            return R.error((String)"\u89e3\u6790mainfest\u5931\u8d25");
        }
        VaParamSyncModuleEnum paramType = params.getParamType();
        VaParamSyncFeignClient feignClient = VaParamSyncModulesProvider.getClient((String)paramType.getName());
        if (feignClient == null) {
            return R.error((String)("\u83b7\u53d6\u53c2\u6570\u540c\u6b65\u670d\u52a1\u5931\u8d25\uff1a" + paramType.getTitle()));
        }
        return feignClient.getImportGroups(mainfestDO, params.getMetaType());
    }

    @PostMapping(value={"/import"})
    void importParam(@RequestBody VaParamSyncParamDO param) {
        String requestID = param.getRequestID();
        String fileID = param.getFileID();
        if (!StringUtils.hasText(fileID)) {
            throw new RuntimeException("\u53c2\u6570fileID\u4e0d\u80fd\u4e3a\u7a7a");
        }
        byte[] cache = VaParamSyncCacheUtil.getFileCache((String)fileID);
        if (cache == null) {
            throw new RuntimeException("\u6587\u4ef6\u7f13\u5b58\u5df2\u5931\u6548\uff0c\u8bf7\u91cd\u65b0\u5bfc\u5165");
        }
        VaParamSyncModuleEnum paramType = param.getParamType();
        VaParamSyncFeignClient feignClient = VaParamSyncModulesProvider.getClient((String)paramType.getName());
        if (feignClient == null) {
            throw new RuntimeException(String.format("\u672a\u77e5\u7684\u670d\u52a1\u3010%s\u3011", paramType.getTitle()));
        }
        VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile(REQ_MULTIPARTFILE, fileID, "multipart/form-data; charset=ISO-8859-1", cache);
        String key = ShiroUtil.getTenantName() + ":PARAMSYNC:RESULT:" + requestID;
        VaParamSyncCacheUtil.setImportResult((String)key, (String)"{}");
        feignClient.importParam((MultipartFile)multipartFile, JSONUtil.toJSONString((Object)param));
    }

    @GetMapping(value={"/import/result/{requestID}"})
    R getImportResult(@PathVariable(value="requestID") String requestID) {
        String key = ShiroUtil.getTenantName() + ":PARAMSYNC:RESULT:" + requestID;
        String string = VaParamSyncCacheUtil.getImportResult((String)key);
        if (!StringUtils.hasText(string)) {
            return R.error();
        }
        VaParamSyncResultDO result = (VaParamSyncResultDO)JSONUtil.parseObject((String)string, VaParamSyncResultDO.class);
        Set resultRange = result.getResultRange();
        if (resultRange == null) {
            return R.error();
        }
        Map resultSet = result.getResultSet();
        if (resultSet == null) {
            return R.error();
        }
        for (VaParamSyncModuleEnum range : resultRange) {
            if (resultSet.containsKey(range)) continue;
            return R.error();
        }
        ArrayList<Map<String, Object>> sunccess = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> faileds = new ArrayList<Map<String, Object>>();
        for (VaParamSyncModuleEnum range : resultRange) {
            List failedInfos;
            Map r = (Map)resultSet.get(range);
            List successInfos = (List)r.get("success");
            if (successInfos != null) {
                successInfos.stream().forEach(o -> {
                    String path = String.valueOf(o.get("path"));
                    String[] split = path.split("\\/");
                    String type = split[split.length - 1];
                    o.put("type", range.getOrder() + "#" + range.getName() + "#" + type);
                });
                sunccess.addAll(successInfos);
            }
            if ((failedInfos = (List)r.get("faileds")) == null) continue;
            failedInfos.stream().forEach(o -> {
                String path = String.valueOf(o.get("path"));
                String[] split = path.split("\\/");
                String type = split[split.length - 1];
                o.put("type", range.getOrder() + "#" + range.getName() + "#" + type);
            });
            faileds.addAll(failedInfos);
        }
        VaParamSyncCacheUtil.removeImportResult((String)key);
        R rs = R.ok();
        this.sortResult(sunccess);
        this.sortResult(faileds);
        rs.put("success", sunccess);
        rs.put("faileds", faileds);
        return rs;
    }

    private void sortResult(List<Map<String, Object>> results) {
        Collections.sort(results, (o1, o2) -> {
            String o1Type = (String)o1.get("type");
            String o2Type = (String)o2.get("type");
            return o1Type.compareTo(o2Type);
        });
    }
}


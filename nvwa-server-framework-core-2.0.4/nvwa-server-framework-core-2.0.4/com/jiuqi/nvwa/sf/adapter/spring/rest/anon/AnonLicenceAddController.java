/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.util.StringUtils
 *  org.apache.commons.compress.archivers.zip.ZipArchiveEntry
 *  org.apache.commons.compress.archivers.zip.ZipFile
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.RemoteServiceException;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.SFService;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import com.jiuqi.nvwa.sf.adapter.spring.util.CheckFileTypeUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonLicenceAddController {
    Logger logger = LoggerFactory.getLogger(AnonLicenceAddController.class);
    @Autowired
    private SFRemoteResourceManage remoteResourceManage;
    private static final String ZIP_HEADER = "504B0304";

    @PostMapping(value={"/licence/add"})
    public Response licenceAdd(@RequestParam(value="file") MultipartFile file, String serviceName) {
        Map<SFService, IServiceManager> serviceMap = this.remoteResourceManage.loadProductInfo();
        File tmpFile = null;
        try {
            String header = CheckFileTypeUtil.getFileHeaderByFileInputStream(file.getInputStream());
            if (!ZIP_HEADER.equalsIgnoreCase(header)) {
                return this.singleLicence(file, serviceName, serviceMap);
            }
            tmpFile = this.transferToFile(file);
            boolean installed = false;
            try (ZipFile zipFile = new ZipFile(tmpFile);){
                Enumeration entries = zipFile.getEntries();
                while (entries.hasMoreElements()) {
                    ZipArchiveEntry entry = (ZipArchiveEntry)entries.nextElement();
                    if (entry.isDirectory()) continue;
                    InputStream inputStream = zipFile.getInputStream(entry);
                    Throwable throwable = null;
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Throwable throwable2 = null;
                        try {
                            byte[] buf = new byte[1024];
                            int length = 0;
                            while ((length = inputStream.read(buf)) != -1) {
                                baos.write(buf, 0, length);
                            }
                            if (!this.processAuthFileFromBaos(baos, serviceName, serviceMap)) continue;
                            installed = true;
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (baos == null) continue;
                            if (throwable2 != null) {
                                try {
                                    baos.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            baos.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (inputStream == null) continue;
                        if (throwable != null) {
                            try {
                                inputStream.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        inputStream.close();
                    }
                }
            }
            if (installed) {
                return Response.ok("\u5b89\u88c5\u6388\u6743\u6210\u529f");
            }
            return Response.ok("");
        }
        catch (Exception e) {
            this.logger.error("\u5b89\u88c5\u6388\u6743\u5931\u8d25");
            return Response.error(e.getMessage());
        }
    }

    private boolean processAuthFileFromBaos(ByteArrayOutputStream baos, String serviceName, Map<SFService, IServiceManager> serviceMap) throws LicenceException, RemoteServiceException {
        byte[] bytes = baos.toByteArray();
        LicenceInfo licenceInfo = LicenceManager.analyzeLicence((InputStream)new ByteArrayInputStream(bytes));
        String productId = licenceInfo.getProductId();
        String productVersion = licenceInfo.getProductVersion();
        this.logger.info("productId {}", (Object)productId);
        this.logger.info("productVersion {}", (Object)productVersion);
        boolean installed = false;
        if (StringUtils.isEmpty((String)serviceName)) {
            for (Map.Entry<SFService, IServiceManager> entry : serviceMap.entrySet()) {
                if (!entry.getKey().getProductId().equalsIgnoreCase(licenceInfo.getProductId())) continue;
                entry.getValue().addLicenceInfo(bytes);
                installed = true;
            }
        } else {
            for (Map.Entry<SFService, IServiceManager> entry : serviceMap.entrySet()) {
                if (!entry.getKey().getServiceName().equalsIgnoreCase(serviceName) || !entry.getKey().getProductId().equalsIgnoreCase(licenceInfo.getProductId())) continue;
                entry.getValue().addLicenceInfo(bytes);
                installed = true;
            }
        }
        return installed;
    }

    private File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1] + ".");
            multipartFile.transferTo(file);
            file.deleteOnExit();
        }
        catch (IOException e) {
            this.logger.error("transferToFile\u5f02\u5e38", e);
        }
        return file;
    }

    private Response singleLicence(MultipartFile file, String serviceName, Map<SFService, IServiceManager> serviceMap) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        }
        catch (IOException e) {
            this.logger.error("\u4e0a\u4f20\u6388\u6743\u5931\u8d25", e);
            return Response.error(e.getMessage());
        }
        try {
            LicenceInfo licenceInfo = LicenceManager.analyzeLicence((InputStream)new ByteArrayInputStream(bytes));
            if (StringUtils.isEmpty((String)serviceName)) {
                boolean installed = false;
                for (Map.Entry<SFService, IServiceManager> entry : serviceMap.entrySet()) {
                    if (!entry.getKey().getProductId().equalsIgnoreCase(licenceInfo.getProductId())) continue;
                    entry.getValue().addLicenceInfo(bytes);
                    installed = true;
                }
                if (!installed) {
                    return Response.error("\u6388\u6743\u6587\u4ef6\u4e0e\u670d\u52a1\u4ea7\u54c1ID\u4e0d\u7b26");
                }
            } else {
                for (Map.Entry<SFService, IServiceManager> entry : serviceMap.entrySet()) {
                    if (!entry.getKey().getServiceName().equalsIgnoreCase(serviceName)) continue;
                    if (entry.getKey().getProductId().equalsIgnoreCase(licenceInfo.getProductId())) {
                        entry.getValue().addLicenceInfo(bytes);
                        break;
                    }
                    return Response.error("\u6388\u6743\u6587\u4ef6\u4e0e\u670d\u52a1\u4ea7\u54c1ID\u4e0d\u7b26");
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u5b89\u88c5\u6388\u6743\u5931\u8d25" + e.getMessage(), e);
            return Response.error("\u5b89\u88c5\u6388\u6743\u5931\u8d25" + e.getMessage());
        }
        return Response.ok("\u6388\u6743\u5b89\u88c5\u6210\u529f");
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.CodeEnum
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.hshdcheck.DescribeParam
 *  com.jiuqi.nr.hshdcheck.DescribeQueryParam
 *  com.jiuqi.nr.hshdcheck.service.HshdDescribeService
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.np.core.model.CodeEnum;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.hshdcheck.DescribeParam;
import com.jiuqi.nr.hshdcheck.DescribeQueryParam;
import com.jiuqi.nr.hshdcheck.service.HshdDescribeService;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.internal.file.ParamsExportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.internal.file.ParamsImportMappingNrdImpl;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HshdDescribeData
implements ITransmissionDataGather {
    private static final Logger logger = LoggerFactory.getLogger(HshdDescribeData.class);
    @Autowired
    private HshdDescribeService hshdDescribeService;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public String getCode() {
        return "HSHD_DESCRIBE";
    }

    @Override
    public String getTitle() {
        return "\u6237\u6570\u6838\u5bf9";
    }

    @Override
    public DataImportResult dataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        IExecuteParam executeParam = context.getExecuteParam();
        ILogHelper logHelper = context.getLogHelper();
        String tempPath = ZipUtils.newTempDir();
        Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
        try {
            DescribeParam describeParam = new DescribeParam();
            DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(context.getContextExpendParam().getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
            describeParam.setMasterKey(collection);
            describeParam.setFormSchemeKey(executeParam.getFormSchemeKey());
            if (context.getMappingImportParam() != null) {
                ParamsImportMappingNrdImpl mappingParam = new ParamsImportMappingNrdImpl(context.getMappingImportParam());
                describeParam.setParamMapping((ParamsMapping)mappingParam);
            }
            for (ZipUtils.ZipSubFile value : zipFiles.values()) {
                File file = FileHelper.getTempFile(value, tempPath);
                Result importResult = this.hshdDescribeService.importDescribe(describeParam, file);
                if (StringUtils.hasLength(importResult.getMessage())) {
                    logHelper.appendLog(importResult.getMessage());
                }
                if (CodeEnum.SUCCESS.getCode().equals(importResult.getCode())) continue;
                throw new Exception(String.format("\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165%s\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s", this.getTitle(), importResult.getMessage()));
            }
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165{}\u62a5\u8868\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff1a{}", (Object)this.getTitle(), (Object)e.getMessage());
            throw new Exception(MultilingualLog.businessDataImportMessage(2, e.getMessage()), e);
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempPath);
        }
        logger.info("\u591a\u7ea7\u90e8\u7f72{}\u6570\u636e\u5bfc\u5165\u6210\u529f", (Object)this.getTitle());
        return null;
    }

    @Override
    public void dataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        block19: {
            DescribeQueryParam param = new DescribeQueryParam();
            IExecuteParam executeParam = context.getExecuteParam();
            DimensionCollection collection = this.dimensionBuildUtil.getDimensionCollection(context.getContextExpendParam().getDimensionValueSetWithAllDim(), executeParam.getFormSchemeKey());
            param.setMasterKey(collection);
            param.setFormSchemeKey(executeParam.getFormSchemeKey());
            if (context.getMappingParam() != null) {
                ParamsExportMappingNrdImpl mappingParam = new ParamsExportMappingNrdImpl(context.getMappingParam());
                param.setParamMapping((ParamsMapping)mappingParam);
            }
            Result result = this.hshdDescribeService.exportDescribe(param);
            if (CodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);){
                    File formSchemeFile = (File)result.getDatas();
                    zipOut.putNextEntry(new ZipEntry(formSchemeFile.getName()));
                    FileUtils.copyFile(formSchemeFile, zipOut);
                    FileUtils.deleteQuietly(formSchemeFile);
                    break block19;
                }
                catch (Exception e) {
                    throw e;
                }
                finally {
                    Utils.deleteAllFilesOfDir((File)result.getDatas());
                }
            }
            String errorMessage = String.format("\u591a\u7ea7\u90e8\u7f72\u5bfc\u51fa%s\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff1a%s", this.getTitle(), result.getMessage());
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }
    }
}


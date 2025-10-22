/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.core.model.CodeEnum
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.data.common.service.FileFinder
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.data.common.service.dto.ResultDTO
 *  com.jiuqi.nr.io.common.ExtConstants
 *  com.jiuqi.nr.tds.Costs
 */
package com.jiuqi.nr.integritycheck.service.impl;

import com.jiuqi.bi.util.Version;
import com.jiuqi.np.core.model.CodeEnum;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.data.common.service.dto.ResultDTO;
import com.jiuqi.nr.integritycheck.common.ExpErrDesFileParam2;
import com.jiuqi.nr.integritycheck.common.ImpErrDesFileParam2;
import com.jiuqi.nr.integritycheck.service.IErrDesIOService;
import com.jiuqi.nr.integritycheck.utils.FileUtil;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.tds.Costs;
import java.io.File;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=30)
public class ICDesDataFactory
implements TaskDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(ICDesDataFactory.class);
    @Autowired
    private IErrDesIOService errDesIOService;
    public static final String CODE = "EMPTY_TABLE_DESC";
    public static final String NAME = "\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e";

    public String getCode() {
        return CODE;
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return NAME;
    }

    public Version getVersion() {
        return new Version("1.0");
    }

    public void exportTaskData(TransferContext context, FileWriter writer) {
        ExpErrDesFileParam2 param = new ExpErrDesFileParam2();
        param.setDims(context.getMasterKeys());
        param.setFormSchemeKey(context.getFormSchemeKey());
        param.setFormKeys(context.getFormKeys());
        param.setParamsMapping(context.getParamsMapping());
        Result<String> expFilePath = this.errDesIOService.exportErrorDes(param);
        String expPath = (String)expFilePath.getDatas();
        try {
            List<File> files = FileUtil.getFiles(expPath, null);
            if (null == files || files.isEmpty()) {
                return;
            }
            for (File file : files) {
                writer.addFile(file.getName(), file);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bfc\u51fa\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
        }
        finally {
            FileUtil.deleteFiles(expPath);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void importTaskData(TransferContext context, FileFinder finder) {
        ImpSettings importSettings = context.getImportSettings();
        ImpErrDesFileParam2 param = new ImpErrDesFileParam2();
        param.setMapping(context.getParamsMapping());
        param.setProviderStore(context.getProviderStore());
        param.setDims(importSettings.getMasterKeys());
        param.setFormKeys(context.getFormKeys());
        FilterDim filterDims = importSettings.getFilterDims();
        param.setFilterDims(filterDims);
        CompletionDim completionDims = importSettings.getCompletionDims();
        param.setCompletionDims(completionDims);
        String impPath = ExtConstants.UPLOADDIR + File.separator + "ICRErrDes" + LocalDate.now() + File.separator + UUID.randomUUID() + File.separator;
        try {
            Throwable throwable;
            Costs.createPathIfNotExists((Path)new File(impPath).toPath());
            try {
                throwable = null;
                try (InputStream jsonFileInputStream = finder.getFileInputStream("PARAMINFO.json");){
                    Files.copy(jsonFileInputStream, Paths.get(impPath + "PARAMINFO.json", new String[0]), new CopyOption[0]);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            }
            try {
                throwable = null;
                try (InputStream csvFileInputStream = finder.getFileInputStream("ERRORDESDATA.csv");){
                    Files.copy(csvFileInputStream, Paths.get(impPath + "ERRORDESDATA.csv", new String[0]), new CopyOption[0]);
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            }
            try {
                Result<Void> res = this.errDesIOService.importErrorDes(impPath, param);
                ResultDTO csvResult = new ResultDTO();
                if (CodeEnum.SUCCESS.getCode().equals(res.getCode())) {
                    csvResult.setMessage("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u6210\u529f");
                } else {
                    csvResult.setMessage(res.getMessage());
                }
                context.setResult(this.getCode(), (com.jiuqi.nr.data.common.service.Result)csvResult);
            }
            catch (Exception e) {
                throw new RuntimeException("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            }
        }
        finally {
            FileUtil.deleteFiles(impPath);
        }
    }
}


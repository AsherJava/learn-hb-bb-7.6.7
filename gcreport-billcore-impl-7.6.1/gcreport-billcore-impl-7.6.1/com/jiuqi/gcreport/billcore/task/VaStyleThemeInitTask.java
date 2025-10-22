/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.style.domain.styletheme.VaStyleThemeExportVO
 *  com.jiuqi.va.style.domain.styletheme.VaStyleThemeImportDTO
 *  com.jiuqi.va.style.service.VaStyleThemeService
 *  org.springframework.mock.web.MockMultipartFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.billcore.task;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.style.domain.styletheme.VaStyleThemeExportVO;
import com.jiuqi.va.style.domain.styletheme.VaStyleThemeImportDTO;
import com.jiuqi.va.style.service.VaStyleThemeService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class VaStyleThemeInitTask
implements CustomClassExecutor {
    private Logger logger = LoggerFactory.getLogger(VaStyleThemeInitTask.class);

    public void execute(DataSource dataSource) throws Exception {
        try {
            MockMultipartFile mockMultipartFile;
            ClassPathResource resource = new ClassPathResource("config/vastyletheme/investStyleTheme.zip");
            try (InputStream inputStream = resource.getInputStream();){
                mockMultipartFile = new MockMultipartFile("investStyleTheme.zip", "investStyleTheme.zip", "text/plain", FileCopyUtils.copyToByteArray(inputStream));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            String id = UUIDUtils.newUUIDStr();
            VaStyleThemeService vaStyleThemeService = (VaStyleThemeService)SpringContextUtils.getBean(VaStyleThemeService.class);
            R r = vaStyleThemeService.uploadStyleTheme((MultipartFile)mockMultipartFile, id);
            if (r.getCode() == 0) {
                VaStyleThemeImportDTO vaStyleThemeImportDTO = (VaStyleThemeImportDTO)r.get((Object)"data");
                ArrayList<UUID> uuidList = new ArrayList<UUID>();
                List styleThemeImportList = vaStyleThemeImportDTO.getStyleThemeImportList();
                for (VaStyleThemeExportVO vo : styleThemeImportList) {
                    uuidList.add(vo.getVaStyleThemeVO().getVaStyleThemeInfo().getId());
                }
                vaStyleThemeImportDTO.setStyleThemeInfoIds(uuidList);
                vaStyleThemeImportDTO.setCoverFlag(true);
                vaStyleThemeImportDTO.setImportControlFlag(true);
                vaStyleThemeService.importExecuteStyleTheme(vaStyleThemeImportDTO);
            } else {
                this.logger.error("\u521d\u59cb\u5316\u6295\u8d44VA\u4e3b\u9898 \u6837\u5f0f\u5bfc\u5165\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25" + r.getMsg());
            }
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u6295\u8d44VA\u4e3b\u9898\u5931\u8d25" + e.getMessage(), e);
        }
    }
}


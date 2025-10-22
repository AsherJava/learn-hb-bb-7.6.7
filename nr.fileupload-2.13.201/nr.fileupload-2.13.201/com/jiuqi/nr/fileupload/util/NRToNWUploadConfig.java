/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nvwa.sf.adapter.spring.multipart.dto.GlobalCheckerPropertiesDto
 *  com.jiuqi.nvwa.sf.adapter.spring.multipart.service.IMultipartCheckerService
 *  com.jiuqi.nvwa.systemoption.bean.SystemOption
 *  com.jiuqi.nvwa.systemoption.dao.ISystemOptionDao
 */
package com.jiuqi.nr.fileupload.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.fileupload.util.FileUploadUtils;
import com.jiuqi.nvwa.sf.adapter.spring.multipart.dto.GlobalCheckerPropertiesDto;
import com.jiuqi.nvwa.sf.adapter.spring.multipart.service.IMultipartCheckerService;
import com.jiuqi.nvwa.systemoption.bean.SystemOption;
import com.jiuqi.nvwa.systemoption.dao.ISystemOptionDao;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.util.unit.DataSize;

public class NRToNWUploadConfig
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        IMultipartCheckerService iMultipartCheckerService = (IMultipartCheckerService)SpringBeanUtils.getBean(IMultipartCheckerService.class);
        ISystemOptionDao iSystemOptionDao = (ISystemOptionDao)SpringBeanUtils.getBean(ISystemOptionDao.class);
        GlobalCheckerPropertiesDto checkerProperties = iMultipartCheckerService.getGlobalCheckerProperties();
        List systemOptions = iSystemOptionDao.querys(FileUploadUtils.getOptionItemKeys());
        boolean isBlackList = false;
        boolean isWhiteList = false;
        block12: for (SystemOption systemOption : systemOptions) {
            String id;
            switch (id = systemOption.getId()) {
                case "BLACK_LIST_AND_WHITE_LIST": {
                    isBlackList = systemOption.getValue().equals("0");
                    isWhiteList = systemOption.getValue().equals("1");
                    break;
                }
                case "BLACK_LIST_INFO": {
                    if (!isBlackList) break;
                    String blackListStr = systemOption.getValue();
                    if (!StringUtils.isNotEmpty((String)blackListStr)) continue block12;
                    blackListStr = blackListStr.replace("-", ".");
                    String[] blackListArray = blackListStr.split(";");
                    List<String> blackList = Arrays.asList(blackListArray);
                    checkerProperties.setDeniedFileTypes(blackList);
                    break;
                }
                case "WHITE_LIST_INFO": {
                    if (!isWhiteList) break;
                    String whiteListStr = systemOption.getValue();
                    if (!StringUtils.isNotEmpty((String)whiteListStr)) continue block12;
                    whiteListStr = whiteListStr.replace("-", ".");
                    String[] whiteListArray = whiteListStr.split(";");
                    List<String> whiteList = Arrays.asList(whiteListArray);
                    checkerProperties.setAllowedFileTypes(whiteList);
                    break;
                }
                case "FILE_UPLOAD_MAX_SIZE": {
                    String maxFileSizeStr = systemOption.getValue();
                    if (!StringUtils.isNotEmpty((String)maxFileSizeStr)) break;
                    Long maxFileSizeLong = Long.valueOf(maxFileSizeStr);
                    DataSize maxFileSize = DataSize.ofKilobytes(maxFileSizeLong);
                    checkerProperties.setMaxFileSize(maxFileSize);
                    break;
                }
            }
        }
        if (isBlackList) {
            checkerProperties.setAllowedFileTypes(null);
        }
        if (isWhiteList) {
            checkerProperties.setDeniedFileTypes(null);
        }
        iMultipartCheckerService.updateGlobalCheckerProperties(checkerProperties);
    }
}


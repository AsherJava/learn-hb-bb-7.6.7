/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.utils.FileStatus
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static List<com.jiuqi.nr.file.FileInfo> getFileInfos(JtableContext jtableContext, String groupKey) {
        ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        FileOperationService fileOperationService = (FileOperationService)BeanUtil.getBean(FileOperationService.class);
        CommonParamsDTO commonParamsDTO = FileUtil.getCommonParamsDTO(jtableContext.getTaskKey());
        List files = fileOperationService.getFileOrPicInfoByGroup(groupKey, commonParamsDTO);
        ArrayList<com.jiuqi.nr.file.FileInfo> fileInfos = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        SecretLevelInfo secretLevel = secretLevelService.getSecretLevel(jtableContext);
        boolean secretLevelEnable = secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
        try {
            if (files == null || files.size() == 0) {
                return fileInfos;
            }
            for (FileInfo file : files) {
                boolean canAccess = true;
                if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                    SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(file.getSecretlevel());
                    boolean bl = canAccess = secretLevelService.canAccess(secretLevelItem) && secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem);
                }
                if (!canAccess) continue;
                String path = fileOperationService.getFilePath(file.getKey(), NpContextHolder.getContext().getTenant(), commonParamsDTO);
                byte[] textByte = path.getBytes("UTF-8");
                FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo((String)file.getKey(), (String)file.getArea(), (String)file.getName(), (String)file.getExtension(), (long)file.getSize(), null, (String)file.getCreater(), (Date)file.getCreateTime(), (String)file.getLastModifier(), (Date)file.getLastModifyTime(), (int)file.getVersion(), (String)file.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
                ff.setSecretlevel(file.getSecretlevel());
                if (com.jiuqi.nr.attachment.utils.FileStatus.UNKNOW.equals((Object)file.getStatus())) {
                    ff.setStatus(FileStatus.UNKNOW);
                } else if (com.jiuqi.nr.attachment.utils.FileStatus.AVAILABLE.equals((Object)file.getStatus())) {
                    ff.setStatus(FileStatus.AVAILABLE);
                } else if (com.jiuqi.nr.attachment.utils.FileStatus.RECOVERABLE.equals((Object)file.getStatus())) {
                    ff.setStatus(FileStatus.RECOVERABLE);
                } else if (com.jiuqi.nr.attachment.utils.FileStatus.DELETED.equals((Object)file.getStatus())) {
                    ff.setStatus(FileStatus.DELETED);
                }
                fileInfos.add((com.jiuqi.nr.file.FileInfo)ff);
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileInfos;
    }

    public static byte[] downFile(String fileKey, String taskKey) {
        FileOperationService fileOperationService = (FileOperationService)BeanUtil.getBean(FileOperationService.class);
        CommonParamsDTO commonParamsDTO = FileUtil.getCommonParamsDTO(taskKey);
        byte[] downloadFile = fileOperationService.downloadFile(fileKey, commonParamsDTO);
        return downloadFile;
    }

    private static CommonParamsDTO getCommonParamsDTO(String taskKey) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(taskKey);
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(taskDefine.getDataScheme());
        commonParamsDTO.setTaskKey(taskDefine.getKey());
        return commonParamsDTO;
    }

    @Deprecated
    private static String getArea(JtableContext jtableContext) {
        return "JTABLEAREA";
    }
}


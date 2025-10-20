/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.OperateType
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.ZipFileRecorder
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferProgressMonitor
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.common.systemparam.util;

import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.OperateType;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.ZipFileRecorder;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.systemparam.dto.EntTransferProgressDTO;
import com.jiuqi.common.systemparam.enums.EntInitMsgType;
import com.jiuqi.common.systemparam.util.EntParamInitFileUtil;
import com.jiuqi.common.systemparam.util.EntTransferProgressMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferProgressMonitor;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntLocalFileParamImportUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntLocalFileParamImportUtil.class);
    private static final String PROGRESS_ID = "import00";

    public static void importParam(String filePath, Consumer<EntTransferProgressDTO> progressLogConsumer) {
        EntLocalFileParamImportUtil.importParam(filePath, false, false, progressLogConsumer);
    }

    public static void importParam(String filePath, boolean allCommit, Consumer<EntTransferProgressDTO> progressLogConsumer) {
        EntLocalFileParamImportUtil.importParam(filePath, allCommit, false, progressLogConsumer);
    }

    public static void importParam(String filePath, boolean allCommit, boolean allDataCommit, Consumer<EntTransferProgressDTO> progressLogConsumer) {
        NpContext context = NpContextHolder.getContext();
        try (InputStream resourceInputStream = EntParamInitFileUtil.getResourceInputStream(filePath);){
            if (resourceInputStream == null) {
                progressLogConsumer.accept(new EntTransferProgressDTO(PROGRESS_ID, "\u3010" + filePath + "\u3011\u8d44\u6e90\u4e0d\u53ef\u7528\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u5305\u4e2d\u662f\u5426\u5b58\u5728\u8be5\u6587\u4ef6", 1.0, EntInitMsgType.ERROR));
                logger.info("\u3010" + filePath + "\u3011\u8d44\u6e90\u4e0d\u53ef\u7528\uff0c\u8df3\u8fc7\u8be5\u8d44\u6e90\u521d\u59cb\u5316");
            }
            if (context == null || StringUtils.isEmpty((String)context.getIdentityId()) || context.getUser() == null) {
                EntLocalFileParamImportUtil.initUserInfo();
            }
            EntLocalFileParamImportUtil.uploadAndImport(resourceInputStream, allCommit, allDataCommit, progressLogConsumer);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private static void uploadAndImport(InputStream resourceInputStream, boolean allCommit, boolean allDataCommit, Consumer<EntTransferProgressDTO> progressLogConsumer) throws Exception {
        TransferContext context = new TransferContext(NpContextHolder.getContext().getUserId());
        EntTransferProgressMonitor progressMonitor = new EntTransferProgressMonitor(context, progressLogConsumer);
        context.setProgressId(PROGRESS_ID);
        context.setProgressMonitor((TransferProgressMonitor)progressMonitor);
        ZipFileRecorder recorder = new ZipFileRecorder((ITransferContext)context, resourceInputStream);
        TransferEngine engine = new TransferEngine();
        Desc desc = engine.getDescInfo((IFileRecorder)recorder);
        JSONArray treeTableData = TransferUtils.buildTreeTableData((ITransferContext)context, (Desc)desc);
        JSONArray dataInfoArray = EntLocalFileParamImportUtil.converterToDataInfoArray(treeTableData, allCommit, allDataCommit);
        Map changeInfos = TransferUtils.readImportSelections((Desc)desc, (JSONArray)dataInfoArray);
        try {
            engine.importProcess((ITransferContext)context, (IFileRecorder)recorder, desc, changeInfos);
        }
        catch (TransferException e) {
            progressMonitor.error(e.getMessage());
        }
        progressMonitor.success("\u521d\u59cb\u5316\u5b8c\u6210");
    }

    private static JSONArray converterToDataInfoArray(JSONArray treeTableData, boolean allCommit, boolean allDataCommit) {
        JSONArray dataInfoArray = new JSONArray();
        for (int i = 0; i < treeTableData.length(); ++i) {
            JSONObject jsonObject = treeTableData.getJSONObject(i);
            if (!jsonObject.has("children") || jsonObject.getJSONArray("children").length() == 0) {
                if (jsonObject.getBoolean("isFolder")) continue;
                if (allCommit) {
                    jsonObject.put("operatetype", OperateType.SUBMIT.value());
                }
                if (allDataCommit && "BaseDataDefine".equalsIgnoreCase(jsonObject.getString("type")) && !StringUtils.isEmpty((String)jsonObject.getString("name")) && !jsonObject.getString("name").contains("MD_BBLX")) {
                    jsonObject.put("dataMode", DataMode.DATA.value());
                }
                dataInfoArray.put((Object)jsonObject);
                continue;
            }
            dataInfoArray.putAll(EntLocalFileParamImportUtil.converterToDataInfoArray(jsonObject.getJSONArray("children"), allCommit, allDataCommit));
        }
        return dataInfoArray;
    }

    private static void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = EntLocalFileParamImportUtil.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)EntLocalFileParamImportUtil.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private static NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private static NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }
}


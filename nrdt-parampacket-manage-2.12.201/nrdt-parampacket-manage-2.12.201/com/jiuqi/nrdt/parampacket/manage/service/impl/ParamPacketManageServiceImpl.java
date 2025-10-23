/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.transfer.engine.intf.IViewNode
 *  com.jiuqi.bi.transfer.engine.viewnode.SimpleViewNode
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.param.transfer.definition.TransferGuid
 *  com.jiuqi.nr.param.transfer.definition.TransferGuidParse
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferProgressMonitor
 *  com.jiuqi.nvwa.transfer.TransferServerExportUtils
 *  com.jiuqi.nvwa.transfer.dao.DO.TransferFolderDO
 *  com.jiuqi.nvwa.transfer.dao.DO.TransferSchemeDO
 *  com.jiuqi.nvwa.transfer.dao.DO.TransferSchemeItemDO
 *  com.jiuqi.nvwa.transfer.dao.ITransferFolderDao
 *  com.jiuqi.nvwa.transfer.dao.ITransferSchemeDao
 *  com.jiuqi.nvwa.transfer.manager.TransferSchemeManager
 *  com.jiuqi.nvwa.transfer.web.ServicesTransferUtil
 *  com.jiuqi.nvwa.transfer.web.TransferSchemeUtil
 *  com.jiuqi.xlib.utils.io.FilenameUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nrdt.parampacket.manage.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.transfer.engine.intf.IViewNode;
import com.jiuqi.bi.transfer.engine.viewnode.SimpleViewNode;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.param.transfer.definition.TransferGuid;
import com.jiuqi.nr.param.transfer.definition.TransferGuidParse;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketQuery;
import com.jiuqi.nrdt.parampacket.manage.bean.ResponseObj;
import com.jiuqi.nrdt.parampacket.manage.config.ParamPacketSuffixConfig;
import com.jiuqi.nrdt.parampacket.manage.dao.IParamPacketDao;
import com.jiuqi.nrdt.parampacket.manage.dao.IParamPacketGroupDao;
import com.jiuqi.nrdt.parampacket.manage.file.service.ParamPacketUploadService;
import com.jiuqi.nrdt.parampacket.manage.i18n.ParamPacketManageI18nHelper;
import com.jiuqi.nrdt.parampacket.manage.i18n.ParamPacketManageI18nKeys;
import com.jiuqi.nrdt.parampacket.manage.monitor.ParamPacketManageProgressMonitor;
import com.jiuqi.nrdt.parampacket.manage.service.IParamPacketManageService;
import com.jiuqi.nrdt.parampacket.manage.util.DateUtil;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferProgressMonitor;
import com.jiuqi.nvwa.transfer.TransferServerExportUtils;
import com.jiuqi.nvwa.transfer.dao.DO.TransferFolderDO;
import com.jiuqi.nvwa.transfer.dao.DO.TransferSchemeDO;
import com.jiuqi.nvwa.transfer.dao.DO.TransferSchemeItemDO;
import com.jiuqi.nvwa.transfer.dao.ITransferFolderDao;
import com.jiuqi.nvwa.transfer.dao.ITransferSchemeDao;
import com.jiuqi.nvwa.transfer.manager.TransferSchemeManager;
import com.jiuqi.nvwa.transfer.web.ServicesTransferUtil;
import com.jiuqi.nvwa.transfer.web.TransferSchemeUtil;
import com.jiuqi.xlib.utils.io.FilenameUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ParamPacketManageServiceImpl
implements IParamPacketManageService {
    @Autowired
    private IParamPacketDao paramPacketDao;
    @Autowired
    private IParamPacketGroupDao paramPacketGroupDao;
    @Autowired
    private ITransferFolderDao transferFolderDao;
    @Autowired
    private ITransferSchemeDao transferSchemeDao;
    @Autowired
    private ParamPacketManageI18nHelper i18nHelper;
    @Autowired
    private TransferSchemeManager transferSchemeManager;
    @Autowired
    private ParamPacketUploadService paramPacketUploadService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private ParamPacketSuffixConfig paramPacketSuffixConfig;
    private static final Logger logger = LoggerFactory.getLogger(ParamPacketManageServiceImpl.class);
    private static final String TASK_TYPE = "TASK";
    private static final String PATH_TITLE = "pathTitles";
    private static final String PATH_ID = "pathGuids";

    @Override
    public List<ParamPacket> queryParamPacketByParent(String parent) {
        List<ParamPacket> paramPackets = this.paramPacketDao.queryParamPacketByParent(parent);
        return paramPackets;
    }

    @Override
    public ParamPacket queryParanPacket(String guid) {
        return this.paramPacketDao.queryParamPacket(guid);
    }

    @Override
    public ParamPacket addParamPacket(ParamPacket paramPacket) {
        paramPacket.setGuid(Guid.newGuid());
        ParamPacketGroup paramPacketGroup = this.paramPacketGroupDao.queryParamPacketGroup(paramPacket.getParent());
        if (paramPacketGroup == null) {
            paramPacketGroup = new ParamPacketGroup();
            paramPacketGroup.setSchemeKey("97970000000000000000000000000000");
        }
        TransferSchemeDO transferSchemeDO = new TransferSchemeDO();
        transferSchemeDO.setGuid(GUID.newGUID());
        paramPacket.setSchemeKey(transferSchemeDO.getGuid());
        transferSchemeDO.setName(paramPacket.getTitle());
        transferSchemeDO.setTitle(paramPacket.getTitle());
        transferSchemeDO.setParent(paramPacketGroup.getSchemeKey());
        this.transferSchemeDao.save(transferSchemeDO);
        this.paramPacketDao.addParamPacket(paramPacket);
        return paramPacket;
    }

    @Override
    public ParamPacket updateParamPacket(ParamPacket paramPacket) {
        TransferSchemeDO transferSchemeDO = new TransferSchemeDO();
        transferSchemeDO.setGuid(paramPacket.getSchemeKey());
        transferSchemeDO.setName(paramPacket.getTitle());
        transferSchemeDO.setTitle(paramPacket.getTitle());
        transferSchemeDO.setParent(transferSchemeDO.getParent());
        this.transferSchemeDao.update(transferSchemeDO);
        this.paramPacketDao.updateParamPacket(paramPacket);
        return paramPacket;
    }

    @Override
    public boolean deleteParamPacket(String guid) {
        ParamPacket paramPacket = this.paramPacketDao.queryParamPacket(guid);
        this.transferSchemeDao.remove(paramPacket.getSchemeKey());
        this.paramPacketDao.deleteParamPacket(guid);
        return true;
    }

    @Override
    public AsyncTaskInfo saveResource(String nodeItems, String guid, Boolean needPacked) {
        ParamPacket paramPacket = this.paramPacketDao.queryParamPacket(guid);
        if (paramPacket == null) {
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setDetail((Object)this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_NOT_FOUND.key, ParamPacketManageI18nKeys.PARAMPACKET_NOT_FOUND.title));
            return asyncTaskInfo;
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(Guid.newGuid());
        asyncTaskInfo.setDetail((Object)this.i18nHelper.getMessage(ParamPacketManageI18nKeys.RESOURCE_SAVE_START.key, ParamPacketManageI18nKeys.RESOURCE_SAVE_START.title));
        asyncTaskInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
        ParamPacketManageProgressMonitor asyncTaskMonitor = new ParamPacketManageProgressMonitor(asyncTaskInfo.getId(), this.cacheObjectResourceRemote);
        this.npApplication.asyncRun(() -> {
            try {
                asyncTaskMonitor.startTask("\u4fdd\u5b58\u53c2\u6570\u5305\u8d44\u6e90", new int[]{2, 5, 1, 1, 1});
                this.saveConfigResource(nodeItems, paramPacket.getSchemeKey(), asyncTaskMonitor);
                if (needPacked.booleanValue()) {
                    this.packedParamFile(nodeItems, paramPacket, asyncTaskMonitor);
                }
                this.saveParamPacketTasks(nodeItems, paramPacket, asyncTaskMonitor);
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_START.key, ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_START.title));
                this.paramPacketDao.updateParamPacket(paramPacket);
                asyncTaskMonitor.stepIn();
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_FINISH.title));
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.RESOURCE_SAVE_FINISH.key, ParamPacketManageI18nKeys.RESOURCE_SAVE_FINISH.title));
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                asyncTaskMonitor.finishTask();
            }
        });
        return asyncTaskInfo;
    }

    @Override
    public AsyncTaskInfo packedParamPacket(String guid) {
        ParamPacket paramPacket = this.paramPacketDao.queryParamPacket(guid);
        if (paramPacket == null) {
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setDetail((Object)this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_NOT_FOUND.key, ParamPacketManageI18nKeys.PARAMPACKET_NOT_FOUND.title));
            return asyncTaskInfo;
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(Guid.newGuid());
        asyncTaskInfo.setDetail((Object)this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_BUILD_START.key, ParamPacketManageI18nKeys.PARAMPACKET_BUILD_START.title));
        asyncTaskInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
        ParamPacketManageProgressMonitor asyncTaskMonitor = new ParamPacketManageProgressMonitor(asyncTaskInfo.getId(), this.cacheObjectResourceRemote);
        String nodeItems = this.getParamPacketResource(paramPacket.getSchemeKey());
        this.npApplication.asyncRun(() -> {
            try {
                asyncTaskMonitor.startTask("\u53c2\u6570\u5305\u751f\u6210", new int[]{1, 5, 2, 2});
                asyncTaskMonitor.stepIn();
                this.packedParamFile(nodeItems, paramPacket, asyncTaskMonitor);
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_START.key, ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_START.title));
                this.paramPacketDao.updateParamPacket(paramPacket);
                asyncTaskMonitor.stepIn();
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKETINFO_UPDATE_FINISH.title));
                asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_BUILD_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKET_BUILD_FINISH.title));
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                asyncTaskMonitor.finishTask();
            }
        });
        return asyncTaskInfo;
    }

    private String getParamPacketResource(String schemeKey) {
        JSONArray itemData = new JSONArray();
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        try {
            List schemeItems = this.transferSchemeManager.getSchemeConfig(schemeKey);
            for (TransferSchemeItemDO schemeItemDO : schemeItems) {
                JSONObject jsonObject = TransferSchemeUtil.buildResourceItem((ITransferContext)context, (TransferSchemeItemDO)schemeItemDO);
                if (jsonObject == null) continue;
                itemData.put((Object)jsonObject);
            }
        }
        catch (TransferException e) {
            logger.error("\u83b7\u53d6\u53c2\u6570\u5305\u8d44\u6e90\u5931\u8d25", e);
        }
        return itemData.toString();
    }

    private void saveParamPacketTasks(String nodeItems, ParamPacket paramPacket, ParamPacketManageProgressMonitor asyncTaskMonitor) {
        asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_RELATION_BUILD_START.key, ParamPacketManageI18nKeys.PARAMPACKET_RELATION_BUILD_START.title));
        JSONArray nodeItemArr = new JSONArray(nodeItems);
        ArrayList<String> taskKeys = new ArrayList<String>();
        for (int i = 0; i < nodeItemArr.length(); ++i) {
            JSONObject nodeItemJSON = nodeItemArr.getJSONObject(i);
            TransferSchemeItemDO schemeItemDO = new TransferSchemeItemDO();
            schemeItemDO.fromJson(nodeItemJSON, paramPacket.getSchemeKey());
            if (!TASK_TYPE.equals(schemeItemDO.getType())) continue;
            String businessGuid = schemeItemDO.getBusinessGuid();
            try {
                TransferGuid transferGuid = TransferGuidParse.parseId((String)businessGuid);
                taskKeys.add(transferGuid.getKey());
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!CollectionUtils.isEmpty(taskKeys)) {
            String tasks = taskKeys.stream().map(String::valueOf).collect(Collectors.joining(";"));
            paramPacket.setTasks(tasks);
        }
        asyncTaskMonitor.stepIn();
        asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_RELATION_BUILD_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKET_RELATION_BUILD_FINISH.title));
    }

    @Override
    public ParamPacket queryParamPacketByCode(String code) {
        ParamPacket paramPacket = this.paramPacketDao.queryParamPacketByCode(code);
        return paramPacket;
    }

    @Override
    public List<ParamPacket> queryParamPacketByUser(Boolean isEnabled) {
        ArrayList<ParamPacket> userParamPackets = new ArrayList<ParamPacket>();
        List allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        if (CollectionUtils.isEmpty(allTaskDefines)) {
            return userParamPackets;
        }
        List authTaskKeys = allTaskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        List<ParamPacket> paramPackets = this.paramPacketDao.queryAllParamPacket();
        for (int i = 0; i < paramPackets.size(); ++i) {
            ParamPacket paramPacket = paramPackets.get(i);
            if (paramPacket.getEnable() != isEnabled) continue;
            String tasks = paramPacket.getTasks();
            if (!StringUtils.isEmpty((String)tasks)) {
                List paramPacketTaskKeys = Arrays.stream(tasks.split(";")).collect(Collectors.toList());
                if (paramPacketTaskKeys.size() > authTaskKeys.size() || !authTaskKeys.containsAll(paramPacketTaskKeys)) continue;
                userParamPackets.add(paramPacket);
                continue;
            }
            userParamPackets.add(paramPacket);
        }
        return userParamPackets;
    }

    @Override
    public Object downloadParamPacket(String fileKey) {
        return this.paramPacketUploadService.download(fileKey);
    }

    @Override
    public List<ParamPacketGroup> queryParamPacketGroupByParent(String parent) {
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketGroupDao.queryParamPacketGroupByParent(parent);
        return paramPacketGroups;
    }

    @Override
    public ParamPacketGroup addParamPacketGroup(ParamPacketGroup paramPacketGroup) {
        paramPacketGroup.setGuid(GUID.newGUID());
        ParamPacketGroup parentPacketGroup = this.paramPacketGroupDao.queryParamPacketGroup(paramPacketGroup.getParent());
        if (parentPacketGroup == null) {
            parentPacketGroup = new ParamPacketGroup();
            parentPacketGroup.setSchemeKey("97970000000000000000000000000000");
        }
        TransferFolderDO transferFolderDO = new TransferFolderDO();
        transferFolderDO.setGuid(GUID.newGUID());
        transferFolderDO.setTitle(paramPacketGroup.getTitle());
        transferFolderDO.setParent(parentPacketGroup.getSchemeKey());
        this.transferFolderDao.save(transferFolderDO);
        paramPacketGroup.setSchemeKey(transferFolderDO.getGuid());
        this.paramPacketGroupDao.addParamPacketGroup(paramPacketGroup);
        return paramPacketGroup;
    }

    @Override
    public ParamPacketGroup updateParamPacketGroup(ParamPacketGroup paramPacketGroup) {
        ParamPacketGroup parentPacketGroup = this.paramPacketGroupDao.queryParamPacketGroup(paramPacketGroup.getParent());
        if (parentPacketGroup == null) {
            parentPacketGroup = new ParamPacketGroup();
            parentPacketGroup.setSchemeKey("97970000000000000000000000000000");
        }
        TransferFolderDO transferFolderDO = new TransferFolderDO();
        transferFolderDO.setGuid(GUID.newGUID());
        transferFolderDO.setTitle(paramPacketGroup.getTitle());
        transferFolderDO.setParent(parentPacketGroup.getSchemeKey());
        this.transferFolderDao.update(transferFolderDO);
        this.paramPacketGroupDao.updateParamPacketGroup(paramPacketGroup);
        return paramPacketGroup;
    }

    @Override
    public boolean deleteParamPacketGroup(String guid) {
        ParamPacketGroup paramPacketGroup = this.paramPacketGroupDao.queryParamPacketGroup(guid);
        List<ParamPacket> paramPackets = this.paramPacketDao.queryParamPacketByParent(guid);
        if (!CollectionUtils.isEmpty(paramPackets)) {
            return false;
        }
        List<ParamPacketGroup> paramPacketGroups = this.paramPacketGroupDao.queryParamPacketGroupByParent(guid);
        if (!CollectionUtils.isEmpty(paramPacketGroups)) {
            return false;
        }
        this.transferFolderDao.remove(paramPacketGroup.getSchemeKey());
        this.paramPacketGroupDao.deleteParamPacketGroup(guid);
        return true;
    }

    @Override
    public ResponseObj getResourcetree(ParamPacketQuery paramPacketQuery) throws Exception {
        String userId = NpContextHolder.getContext().getUserId();
        TransferEngine engine = new TransferEngine();
        TransferContext context = new TransferContext(userId);
        try {
            List nodes = engine.getResourceTreeNodes((ITransferContext)context, paramPacketQuery.getGuid(), paramPacketQuery.getType(), paramPacketQuery.getFactoryId(), paramPacketQuery.getFactoryIds());
            JSONArray array = new JSONArray();
            this.dealResourceItem(nodes, array, paramPacketQuery.getFactoryId(), engine, (ITransferContext)context, paramPacketQuery.getFactoryIds(), paramPacketQuery);
            return ResponseObj.SUCCESS(array.toList(), "\u67e5\u8be2\u6210\u529f\uff01");
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u8282\u70b9\u5217\u8868\u51fa\u9519", e);
            return ResponseObj.FAIL(null, e.getMessage());
        }
    }

    private void dealResourceItem(List<IViewNode> nodes, JSONArray array, String factoryId, TransferEngine engine, ITransferContext context, List<String> needShowFactoryIds, ParamPacketQuery paramPacketQuery) throws TransferException {
        for (IViewNode node : nodes) {
            if (node instanceof SimpleViewNode || node instanceof FolderNode) {
                try {
                    JSONObject jsonObject = node.toJSON();
                    if ("TRANSFER_CATEGORYVIEW".equals(node.getType())) {
                        factoryId = node.getGuid();
                        jsonObject.put(PATH_TITLE, (Object)(paramPacketQuery.getRootTitle() + " / " + node.getTitle()));
                        jsonObject.put(PATH_ID, (Object)(paramPacketQuery.getGuid() + "," + node.getGuid()));
                    } else {
                        TransferFactory factory = TransferFactoryManager.getInstance().getFactory(factoryId);
                        List pathFolders = factory.getBusinessManager(context).getPathFolders(node.getGuid());
                        if (pathFolders == null) {
                            throw new TransferException(String.format("\u67e5\u8be2\u8d44\u6e90\u8282\u70b9\u3010%s\u3011\u7684\u8def\u5f84\u4e3a\u7a7a", node.getTitle()));
                        }
                        List<String> folderPath = this.getFolderPath(factory, pathFolders);
                        jsonObject.put(PATH_TITLE, (Object)Html.encodeText((String)folderPath.get(0)));
                        jsonObject.put(PATH_ID, (Object)folderPath.get(1));
                    }
                    jsonObject.put("factoryId", (Object)factoryId);
                    array.put((Object)jsonObject);
                    if (node.getType().equals("\u62a5\u8868\u65b9\u6848")) continue;
                    List childrenNodes = engine.getResourceTreeNodes(context, node.getGuid(), node.getType(), factoryId, needShowFactoryIds);
                    this.dealResourceItem(childrenNodes, array, factoryId, engine, context, needShowFactoryIds, paramPacketQuery);
                    continue;
                }
                catch (TransferException e) {
                    logger.error("\u83b7\u53d6\u8282\u70b9\u5217\u8868\u51fa\u9519", e);
                    throw new TransferException("\u83b7\u53d6\u8282\u70b9\u5217\u8868\u51fa\u9519");
                }
            }
            if (!(node instanceof BusinessNode)) continue;
            List resItems = engine.selectBusinesses(context, node.getGuid(), node.getType(), factoryId, needShowFactoryIds);
            for (ResItem resItem : resItems) {
                array.put((Object)TransferUtils.convertRestItemToViewJSON((ResItem)resItem, (ITransferContext)context));
            }
        }
    }

    private List<String> getFolderPath(TransferFactory factory, List<FolderNode> pathFolders) {
        ArrayList<String> list = new ArrayList<String>();
        TransferModule module = TransferFactoryManager.getInstance().getModule(factory.getModuleId());
        StringBuilder pathTitleSB = new StringBuilder(module.getTitle());
        StringBuilder pathGuidSB = new StringBuilder(module.getId());
        pathTitleSB.append(" / ");
        pathGuidSB.append(",");
        if (factory.getViewGroup() != null) {
            pathTitleSB.append(factory.getViewGroup().getTitle());
            pathTitleSB.append(" / ");
            pathGuidSB.append(factory.getViewGroup().getId());
            pathGuidSB.append(",");
        }
        pathTitleSB.append(factory.getTitle());
        pathGuidSB.append(factory.getId());
        if (pathFolders == null || pathFolders.isEmpty()) {
            list.add(pathTitleSB.toString());
            list.add(pathGuidSB.toString());
            return list;
        }
        pathTitleSB.append(" / ");
        pathGuidSB.append(",");
        for (int i = 0; i < pathFolders.size(); ++i) {
            pathTitleSB.append(pathFolders.get(i).getTitle());
            pathGuidSB.append(pathFolders.get(i).getGuid());
            if (i == pathFolders.size() - 1) continue;
            pathTitleSB.append(" / ");
            pathGuidSB.append(",");
        }
        list.add(pathTitleSB.toString());
        list.add(pathGuidSB.toString());
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void packedParamFile(String nodeItems, ParamPacket paramPacket, ParamPacketManageProgressMonitor asyncTaskMonitor) throws IOException {
        String userId = NpContextHolder.getContext().getUserId();
        String suffixName = this.paramPacketSuffixConfig.getSuffixName();
        TransferContext context = new TransferContext(userId);
        context.setProgressMonitor((TransferProgressMonitor)asyncTaskMonitor);
        context.setProgressId(asyncTaskMonitor.progressId);
        context.setRecordRelationMappings(true);
        JSONArray dataInfoArray = new JSONArray(nodeItems);
        File tempFile = this.createTempFile(paramPacket);
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             FileInputStream inputStream = new FileInputStream(tempFile);){
            asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_BUILD_START.key, ParamPacketManageI18nKeys.PARAMPACKET_BUILD_START.title));
            TransferEngine engine = new TransferEngine();
            List distinctResItems = ServicesTransferUtil.buildResItems((JSONArray)dataInfoArray, (ITransferContext)context);
            List relatedResItems = engine.getRelatedBusinesses((ITransferContext)context, distinctResItems);
            distinctResItems.addAll(relatedResItems);
            distinctResItems = ServicesTransferUtil.getDistinctResItems((List)distinctResItems);
            for (ResItem distinctResItem : relatedResItems) {
                TransferFactory factory = TransferFactoryManager.getInstance().getFactory(distinctResItem.getFactoryId());
                if (factory == null || !factory.supportExportData(distinctResItem.getGuid())) continue;
                distinctResItem.setDataMode(DataMode.DATA);
            }
            TransferServerExportUtils.export((OutputStream)fileOutputStream, (TransferContext)context, (List)distinctResItems, (boolean)false, (TransferProgressMonitor)asyncTaskMonitor, null);
            asyncTaskMonitor.stepIn();
            asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_BUILD_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKET_BUILD_FINISH.title));
            String fileName = paramPacket.getTitle() + "-" + DateUtil.curDate() + suffixName;
            asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_SAVE_START.key, ParamPacketManageI18nKeys.PARAMPACKET_SAVE_START.title));
            FileInfo fileInfo = this.paramPacketUploadService.upload(fileName, suffixName, inputStream);
            asyncTaskMonitor.stepIn();
            asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAMPACKET_SAVE_FINISH.key, ParamPacketManageI18nKeys.PARAMPACKET_SAVE_FINISH.title));
            paramPacket.setFileKey(fileInfo.getKey());
            paramPacket.setUpdateTime(new Date());
        }
        catch (TransferException | IOException e) {
            asyncTaskMonitor.error(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.ERROR_PARAMPACKET_EXPORT.key, ParamPacketManageI18nKeys.ERROR_PARAMPACKET_EXPORT.title), e.getMessage());
            logger.error(e.getMessage(), e);
        }
        finally {
            try {
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private File createTempFile(ParamPacket paramPacket) throws IOException {
        String suffixName = this.paramPacketSuffixConfig.getSuffixName();
        String sysTempPath = System.getProperty(FilenameUtils.normalize((String)"java.io.tmpdir"));
        if (!sysTempPath.endsWith(File.separator)) {
            sysTempPath = sysTempPath + File.separator;
        }
        String path = sysTempPath + "parampacket" + File.separator;
        String tempFilePath = path + paramPacket.getTitle() + "-" + DateUtil.getDate() + suffixName;
        try {
            PathUtils.validatePathManipulation((String)tempFilePath);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File tempFile = new File(tempFilePath);
        File parentFile = tempFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        return tempFile;
    }

    private void saveConfigResource(String nodeItems, String schemeGuid, ParamPacketManageProgressMonitor asyncTaskMonitor) {
        asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAM_FORMSCHEME_SAVE_START.key, ParamPacketManageI18nKeys.PARAM_FORMSCHEME_SAVE_START.title));
        JSONArray nodeItemArr = new JSONArray(nodeItems);
        ArrayList<TransferSchemeItemDO> schemeItems = new ArrayList<TransferSchemeItemDO>();
        for (int i = 0; i < nodeItemArr.length(); ++i) {
            JSONObject nodeItemJSON = nodeItemArr.getJSONObject(i);
            TransferSchemeItemDO schemeItemDO = new TransferSchemeItemDO();
            schemeItemDO.fromJson(nodeItemJSON, schemeGuid);
            schemeItems.add(schemeItemDO);
        }
        this.transferSchemeManager.saveSchemeConfig(schemeItems, schemeGuid);
        asyncTaskMonitor.stepIn();
        asyncTaskMonitor.prompt(this.i18nHelper.getMessage(ParamPacketManageI18nKeys.PARAM_FORMSCHEME_SAVE_FINISH.key, ParamPacketManageI18nKeys.PARAM_FORMSCHEME_SAVE_FINISH.title));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.authorize.service.AuthorizeService
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  io.swagger.annotations.Api
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.tomcat.util.codec.binary.Base64
 *  org.json.JSONObject
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.authorize.service.AuthorizeService;
import com.jiuqi.nr.bi.integration.BIApiFactory;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.query.block.BlockInfo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalGroupDao;
import com.jiuqi.nr.query.deserializer.BlockInfoDeserializer;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import com.jiuqi.nr.query.querymodal.QueryModelExtension;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.serializer.BlockInfoSerializer;
import com.jiuqi.nr.query.service.QueryCacheManager;
import com.jiuqi.nr.query.service.QueryGridFactory;
import com.jiuqi.nr.query.service.QueryModalServiceHelper;
import com.jiuqi.nr.query.service.impl.DataQueryHelper;
import com.jiuqi.nr.query.service.impl.QueryAuthorityImpl;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nr.query.service.impl.QueryFileSystemServices;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/query-Manager"})
@Api(tags={"\u67e5\u8be2\u6a21\u677f"})
public class IQueryModalController {
    private static final Logger log = LoggerFactory.getLogger(IQueryModalController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QueryModalServiceHelper servicesHelper;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private IQueryModalGroupDao groupDao;
    @Autowired
    private IQueryBlockDefineDao blockDao;
    @Autowired
    private Environment env;
    @Autowired
    private QueryAuthorityImpl authority;
    @Autowired
    QueryCacheManager queryCacheManager;
    @Autowired
    BIApiFactory biApi;
    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    NpApplication npApplication;
    @Autowired
    private SystemUserService sysUserService;
    @Autowired
    private QueryDimensionHelper dimensionHelper;
    private static final Logger logger = LoggerFactory.getLogger(IQueryModalController.class);
    @Resource
    private FileService fileService;
    @Resource
    private QueryFileSystemServices addressProperties;

    @RequestMapping(value={"/query-all-getQueryModelDefine"}, method={RequestMethod.GET})
    public QueryModalDefine getQueryModelDefine(String groupId, String type) {
        QueryModelType modelType = QueryModelType.valueOf(type);
        QueryModalDefine queryModalDefine = new QueryModalDefine();
        queryModalDefine.setId(UUID.randomUUID().toString());
        ContextUser user = NpContextHolder.getContext().getUser();
        queryModalDefine.setCreator(NpContextHolder.getContext().getUserId().toString());
        queryModalDefine.setLayout("a");
        queryModalDefine.setUpdateTime(new Date());
        queryModalDefine.setQueryModelExtension(new QueryModelExtension());
        queryModalDefine.setModelType(modelType);
        if (!StringUtils.isEmpty((String)groupId)) {
            queryModalDefine.setGroupId(groupId);
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u6a21\u677f", (String)"\u65b0\u5efa\u6a21\u677f\u6210\u529f");
        return queryModalDefine;
    }

    @RequestMapping(value={"/query-all-getQueryModelDefineById"}, method={RequestMethod.GET})
    public QueryModalDefine getQueryModelDefineById(String modalId) {
        QueryModalDefine queryModalDefine = this.modelDao.getQueryModalDefineById(modalId);
        return queryModalDefine;
    }

    @RequestMapping(value={"/query-all-getQueryBlockDefineById"}, method={RequestMethod.GET})
    public String getQueryBlockDefineById(String blockId) {
        try {
            blockId = HtmlUtils.cleanUrlXSS((String)blockId);
            ObjectMapper mapper = new ObjectMapper();
            QueryBlockDefine blockDefine = this.blockDao.GetQueryBlockDefineById(blockId);
            return HtmlUtils.cleanUrlXSS((String)mapper.writeValueAsString((Object)blockDefine));
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    @RequestMapping(value={"/query-updateModal"}, method={RequestMethod.POST})
    public String updateQueryModal(@RequestBody QueryModalDefine queryModal) {
        try {
            QueryModelType modelType = QueryModelType.OWNER;
            if (queryModal.getModelType() != null) {
                modelType = queryModal.getModelType();
            }
            String typeCondition = " and QMD_TYPE in ('" + (Object)((Object)modelType) + "'" + ')';
            List<Object> modals = new ArrayList();
            modals = queryModal.getGroupId() != null && queryModal.getGroupId().toString().length() > 0 ? this.modelDao.getModalsByCondition("QMD_GROUPID = '" + queryModal.getGroupId() + "' and " + "QMD_TITLE" + " = '" + queryModal.getTitle() + "' and " + "QMD_ID" + " != '" + queryModal.getId().toString() + "'" + typeCondition, null) : this.modelDao.getModalsByCondition("QMD_GROUPID is null and QMD_TITLE = '" + queryModal.getTitle() + "' and " + "QMD_ID" + " != '" + queryModal.getId().toString() + "'" + typeCondition, null);
            if (null == modals || modals.size() == 0) {
                if (queryModal.getModelType().equals((Object)QueryModelType.OWNER) || queryModal.getModelType().equals((Object)QueryModelType.DASHOWNER) || queryModal.getModelType().equals((Object)QueryModelType.SIMPLEOWER) || queryModal.getModelType().equals((Object)QueryModelType.DATASET) || this.authority.canWriteModal(queryModal.getId(), queryModal.getModelType())) {
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u6a21\u677f", (String)("\u7f16\u8f91\u6a21\u677f\u6210\u529f\uff0cid:" + queryModal.getId() + ",title:" + queryModal.getTitle()));
                    return this.modelDao.updateQueryModalDefine(queryModal);
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u6a21\u677f", (String)("\u7f16\u8f91\u6a21\u677f\uff1a\u5f53\u524d\u7528\u6237\u6ca1\u6709\u4fee\u6539\u6a21\u677f\u7684\u6743\u9650\uff0cid:" + queryModal.getId() + ",title:" + queryModal.getTitle()));
                return "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u4fee\u6539\u6a21\u677f\u7684\u6743\u9650";
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u6a21\u677f", (String)("\u7f16\u8f91\u6a21\u677f\uff1a\u6a21\u677f\u540d\u79f0\u91cd\u590d\uff0cid:" + queryModal.getId() + ",title:" + queryModal.getTitle()));
            return "duplicate_template_title";
        }
        catch (Exception e) {
            Log.error((Exception)e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u6a21\u677f\u5f02\u5e38", (String)("\u7f16\u8f91\u6a21\u677f\u5f02\u5e38\u4fe1\u606f\uff1a" + e));
            return "false_system";
        }
    }

    @RequestMapping(value={"/query-deleteModal"}, method={RequestMethod.POST})
    public String deleteQueryModal(@RequestBody Map<String, String> modelObject) {
        try {
            String modelId = modelObject.get("modelId");
            QueryModelType type = QueryModelType.valueOf(modelObject.get("type"));
            if (type.equals((Object)QueryModelType.OWNER) || type.equals((Object)QueryModelType.DASHOWNER) || type.equals((Object)QueryModelType.SIMPLEOWER) || type.equals((Object)QueryModelType.DATASET) || this.authority.canDeleteModal(modelId, type)) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f", (String)("\u5220\u9664\u6a21\u677f\u6210\u529f\uff0c\u6a21\u677fid\uff1a" + modelObject.get("modelId")));
                return this.modelDao.deleteQueryModalDefineById(modelId);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f\u5f02\u5e38", (String)("\u5220\u9664\u6a21\u677f\u5f02\u5e38\u4fe1\u606f\uff1a" + ex));
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f", (String)"\u5220\u9664\u6a21\u677f\u5931\u8d25\uff1a\u8bb8\u53ef\u65e0\u6548");
        return "false_permission";
    }

    private QueryModelType getType(String subject) {
        switch (subject) {
            case "query": {
                return QueryModelType.OWNER;
            }
            case "dashboard": {
                return QueryModelType.DASHOWNER;
            }
            case "simple": {
                return QueryModelType.SIMPLEOWER;
            }
        }
        return QueryModelType.OWNER;
    }

    @RequestMapping(value={"/query-all-querytree"}, method={RequestMethod.POST})
    public String queryAllModel(@RequestBody Map<String, List<String>> param, String fromPage) throws Exception {
        try {
            List<String> groupIds = null;
            List<String> modalIds = null;
            List<String> editGroupId = null;
            QueryModelType type = null;
            if (param.containsKey("groupId")) {
                groupIds = param.get("groupId");
            }
            if (param.containsKey("modalId")) {
                modalIds = param.get("modalId");
            }
            if (param.containsKey("type") && param.get("type") != null) {
                type = QueryModelType.valueOf(param.get("type").get(0));
            }
            if (param.containsKey("editGroupId")) {
                editGroupId = param.get("editGroupId");
            }
            boolean isPortalParam = param.containsKey("From");
            List<Object> groups = new ArrayList();
            if (groupIds == null) {
                if (modalIds != null) {
                    List<QueryModalDefine> modals = this.getQueryModalDefines(modalIds, type);
                    if (modals != null) {
                        List<ITree<QueryModalTreeNode>> modalNodes = this.getModalNodes(modals, modalIds);
                        groups.addAll(modalNodes);
                    }
                } else {
                    groups = this.getAllModalTree(type);
                }
            } else {
                for (String groupId : groupIds) {
                    List<QueryModalDefine> list;
                    if (groupId.equals("FirstLevel")) {
                        groups = this.getAllModalTree(type);
                        if (editGroupId == null) continue;
                        for (String string : editGroupId) {
                            ITree<QueryModalTreeNode> path;
                            if ("b8079ac0-dc15-11e8-969b-64006a6432d8".equals(string) || (path = this.getGroupPath(string, null)) == null) continue;
                            Iterator<Object> iterator = groups.iterator();
                            int index = 0;
                            int itemIndex = 0;
                            while (iterator.hasNext()) {
                                ++index;
                                ITree next = (ITree)iterator.next();
                                if (!path.getKey().equals(next.getKey())) continue;
                                itemIndex = index;
                                iterator.remove();
                            }
                            groups.add(itemIndex, path);
                        }
                        continue;
                    }
                    List<QueryModalGroup> group = this.authority.getModalGroupByParentId(groupId, type, "query_model_resource_read");
                    if (group != null && !isPortalParam) {
                        for (QueryModalGroup group1 : group) {
                            QueryModalTreeNode node1 = QueryModalTreeNode.buildTreeNodeData(group1);
                            ITree tree1 = new ITree((INode)node1);
                            tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                            tree1.setLeaf(false);
                            groups.add(tree1);
                        }
                    }
                    if ((list = this.authority.getModalsByGroupId(groupId, type, "query_model_resource_read")) == null) continue;
                    List<ITree<QueryModalTreeNode>> modalNodes = this.getModalNodes(list, modalIds);
                    groups.addAll(modalNodes);
                }
            }
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (JsonProcessingException jE) {
            return null;
        }
        catch (DataTypeException e) {
            return null;
        }
    }

    private ITree<QueryModalTreeNode> getGroupPath(String groupId, ITree<QueryModalTreeNode> paramNode) throws Exception {
        QueryModalGroup group = this.groupDao.GetQueryModalGroupById(groupId);
        String parentGroupId = group.getParentGroupId();
        if (parentGroupId != null && "b8079ac0-dc15-11e8-969b-64006a6432d8".equals(parentGroupId)) {
            return paramNode;
        }
        QueryModalGroup parent = this.groupDao.GetQueryModalGroupById(parentGroupId);
        QueryModalTreeNode parentNode = QueryModalTreeNode.buildTreeNodeData(parent);
        ITree tree1 = new ITree((INode)parentNode);
        tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
        tree1.setLeaf(false);
        if (paramNode != null) {
            ArrayList<ITree<QueryModalTreeNode>> list = new ArrayList<ITree<QueryModalTreeNode>>();
            list.add(paramNode);
            tree1.setChildren(list);
        } else {
            QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(group);
            ITree tree = new ITree((INode)node);
            tree.setIcons(new String[]{"#icon-_ModalTfujiedian"});
            tree.setLeaf(false);
            ArrayList<ITree> list = new ArrayList<ITree>();
            list.add(tree);
            tree1.setChildren(list);
        }
        return this.getGroupPath(parentGroupId, (ITree<QueryModalTreeNode>)tree1);
    }

    private List<QueryModalDefine> getQueryModalDefines(List<String> modalIds, QueryModelType type) {
        StringBuilder sb = new StringBuilder();
        int size = modalIds.size();
        for (int i = 0; i < modalIds.size(); ++i) {
            if (i == size - 1) {
                sb.append("'" + modalIds.get(i) + "'");
                continue;
            }
            sb.append("'" + modalIds.get(i) + "'").append(",");
        }
        String Condition = "QMD_ID in(" + sb.toString() + ")";
        String typeCondition = null;
        typeCondition = this.getTypeCondition(type, "model");
        return this.authority.getModalsByCondition(Condition + typeCondition, null, type, "query_model_resource_read");
    }

    private String getTypeCondition(QueryModelType type, String MorG) {
        String typeCondition = null;
        String conType = null;
        String creator = null;
        String userId = NpContextHolder.getContext().getUserId();
        if (MorG == "model") {
            conType = "QMD_TYPE";
            creator = "QMD_CREATOR";
        } else {
            conType = "QMG_TYPE";
            creator = "QMG_CREATOR";
        }
        switch (type) {
            case PUBLIC: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)QueryModelType.PUBLIC) + "')";
                break;
            }
            case OWNER: 
            case DASHOWNER: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)type) + "')";
                break;
            }
            case SIMPLEOWER: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)type) + "')";
                break;
            }
            case SHARED: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)QueryModelType.SHARED) + "')";
                break;
            }
            case DASHSHARED: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)QueryModelType.DASHSHARED) + "')";
                break;
            }
            case CUSTOMINPUT: {
                typeCondition = " and " + conType + " in ('" + (Object)((Object)QueryModelType.CUSTOMINPUT) + "')";
                break;
            }
            default: {
                typeCondition = null;
            }
        }
        return typeCondition;
    }

    private List<ITree<QueryModalTreeNode>> getModalNodes(List<QueryModalDefine> modals, List<String> modalIds) {
        ArrayList<ITree<QueryModalTreeNode>> groups = new ArrayList<ITree<QueryModalTreeNode>>();
        try {
            for (QueryModalDefine modal : modals) {
                if (modalIds != null && !modalIds.contains(modal.getId().toString())) continue;
                QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(modal);
                ITree tree = new ITree((INode)node);
                tree.setIcons(new String[]{"#icon-_GJZshuxingzhongji"});
                tree.setLeaf(true);
                groups.add((ITree<QueryModalTreeNode>)tree);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return groups;
    }

    private List<ITree<QueryModalTreeNode>> getAllModalTree(QueryModelType type) {
        ArrayList<ITree<QueryModalTreeNode>> groups = new ArrayList<ITree<QueryModalTreeNode>>();
        try {
            String modelTypeCondition = this.getTypeCondition(type, "model");
            String groupTypeCondition = this.getTypeCondition(type, "group");
            String groupCondition = "QMG_PARENTID in ( 'b8079ac0-dc15-11e8-969b-64006a6432d8')";
            String modelCondition = " (QMD_GROUPID is null or QMD_GROUPID ='b8079ac0-dc15-11e8-969b-64006a6432d8' )";
            List<QueryModalDefine> modals = this.authority.getModalsByCondition(modelCondition + modelTypeCondition, null, type, "query_model_resource_read");
            List<QueryModalGroup> group = this.authority.getGroupsByCondition(groupCondition + groupTypeCondition, null, type, "query_model_resource_read");
            if (group != null) {
                for (QueryModalGroup group1 : group) {
                    QueryModalTreeNode node1 = QueryModalTreeNode.buildTreeNodeData(group1);
                    ITree tree1 = new ITree((INode)node1);
                    tree1.setIcons(new String[]{"#icon-_ModalTfujiedian"});
                    tree1.setLeaf(false);
                    groups.add((ITree<QueryModalTreeNode>)tree1);
                }
            }
            if (modals != null) {
                for (QueryModalDefine modal : modals) {
                    QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(modal);
                    ITree tree = new ITree((INode)node);
                    tree.setIcons(new String[]{"#icon-_GJZshuxingzhongji"});
                    tree.setLeaf(true);
                    groups.add((ITree<QueryModalTreeNode>)tree);
                }
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return groups;
    }

    @RequestMapping(value={"/query-allmodals"}, method={RequestMethod.GET})
    public String getAllModals() throws JsonProcessingException {
        List<QueryModalDefine> allModals = this.modelDao.getAllQueryModalDefines();
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f", (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u6210\u529f");
        return this.objectMapper.writeValueAsString(allModals);
    }

    @RequestMapping(value={"/query-modal-querymodalsbygroup"}, method={RequestMethod.POST})
    public String queryModelsByGroup(@RequestBody Map<String, String> param) throws Exception {
        String groupId = null;
        String modelId = null;
        QueryModelType type = null;
        String defaultParentId = "b8079ac0-dc15-11e8-969b-64006a6432d8";
        if (param.containsKey("groupId")) {
            groupId = param.get("groupId");
        }
        if (param.containsKey("modelId")) {
            modelId = param.get("modelId");
        }
        if (param.containsKey("type")) {
            type = QueryModelType.valueOf(param.get("type"));
        }
        ArrayList<ITree<QueryModalTreeNode>> groups = new ArrayList<ITree<QueryModalTreeNode>>();
        this.queryModelsByGroup(defaultParentId, groupId == null ? defaultParentId : groupId, modelId, groups, type);
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u5206\u7ec4\u67e5\u8be2\u6a21\u677f", (String)("\u901a\u8fc7\u5206\u7ec4\u67e5\u8be2\u6a21\u677f\u6210\u529f\uff0c" + groupId + "\u4e0b\u7684\u6a21\u677f\u3002"));
        return this.objectMapper.writeValueAsString(groups);
    }

    private void queryModelsByGroup(String loadGroup, String groupId, String modelId, List<ITree<QueryModalTreeNode>> groups, QueryModelType type) {
        try {
            String defaultParentId = "b8079ac0-dc15-11e8-969b-64006a6432d8";
            String condition = null;
            String groupCondition = this.getTypeCondition(type, "group");
            String modalCondition = this.getTypeCondition(type, "model");
            condition = "QMG_PARENTID in ('" + loadGroup + "')";
            List<QueryModalGroup> group = this.authority.getGroupsByCondition(condition + groupCondition, null, type, "query_model_resource_read");
            if (group != null) {
                Optional<QueryModalGroup> g = group.stream().filter(item -> item.getGroupId().equals(groupId)).findFirst();
                boolean hasCurGroup = g.isPresent();
                for (QueryModalGroup group1 : group) {
                    QueryModalTreeNode node1 = QueryModalTreeNode.buildTreeNodeData(group1);
                    ITree tree1 = new ITree((INode)node1);
                    tree1.setLeaf(false);
                    groups.add((ITree<QueryModalTreeNode>)tree1);
                    if (hasCurGroup && !group1.getGroupId().equals(groupId)) continue;
                    this.queryModelsByGroup(group1.getGroupId(), groupId, modelId, groups, type);
                }
            }
            if (loadGroup.equals(defaultParentId)) {
                condition = " ( QMD_GROUPID is null or QMD_GROUPID in ('" + defaultParentId + "')) ";
                List<QueryModalDefine> modals = this.authority.getModalsByCondition(condition + modalCondition, null, type, "query_model_resource_read");
                if (modals != null) {
                    for (QueryModalDefine modal : modals) {
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(modal);
                        ITree tree = new ITree((INode)node);
                        tree.setLeaf(true);
                        groups.add((ITree<QueryModalTreeNode>)tree);
                    }
                }
            } else {
                condition = "QMD_GROUPID in ('" + loadGroup + "') ";
                List<QueryModalDefine> models = this.authority.getModalsByCondition(condition + modalCondition, null, type, "query_model_resource_read");
                if (models != null) {
                    ITree modeltree = new ITree();
                    for (QueryModalDefine model : models) {
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(model);
                        ITree nodetree = new ITree((INode)node);
                        if (model.getId().equals(modelId)) {
                            nodetree.setSelected(true);
                        }
                        modeltree.appendChild(nodetree);
                        nodetree.setLeaf(true);
                        groups.add((ITree<QueryModalTreeNode>)nodetree);
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @RequestMapping(value={"/query-all-queryBlockDefines"}, method={RequestMethod.POST})
    public List<QueryBlockDefine> queryBlockDefinesInModel(String modelId) {
        try {
            ArrayList<QueryBlockDefine> blocks = new ArrayList();
            blocks = this.blockDao.GetQueryBlockDefinesByModelId(modelId);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6a21\u677f\u67e5\u8be2\u5757\u6570\u636e", (String)("\u901a\u8fc7\u6a21\u677f\u67e5\u8be2\u5757\u6570\u636e\u6210\u529f,modelId:" + modelId));
            return blocks;
        }
        catch (Exception e) {
            Log.error((Exception)e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6a21\u677f\u67e5\u8be2\u5757\u6570\u636e\u5f02\u5e38", (String)("\u901a\u8fc7\u6a21\u677f\u67e5\u8be2\u5757\u6570\u636e\u5f02\u5e38\u4fe1\u606f\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/query-all-addModalGroup"}, method={RequestMethod.POST})
    public String addModelGroup(@RequestBody Map<String, String> grouptitleJson) {
        try {
            String grouptitle = null;
            String groupParentId = null;
            QueryModelType type = null;
            QueryModalGroup group = new QueryModalGroup();
            if (grouptitleJson.containsKey("groupTitle")) {
                grouptitle = grouptitleJson.get("groupTitle");
            }
            if (grouptitleJson.containsKey("groupParentId") && (groupParentId = grouptitleJson.get("groupParentId")).length() <= 0) {
                groupParentId = "b8079ac0-dc15-11e8-969b-64006a6432d8";
            }
            if (grouptitleJson.containsKey("type")) {
                type = QueryModelType.valueOf(!grouptitleJson.get("type").equals("") ? grouptitleJson.get("type") : QueryModelType.OWNER.toString());
            }
            group.setParentGroupId(groupParentId);
            List<Object> groups = new ArrayList();
            groups = this.groupDao.getModalGroupByTitle(grouptitle, groupParentId);
            QueryModelType finalType = type;
            boolean groupTypeMatch = groups.stream().anyMatch(item -> item.getModelType() == finalType);
            if (null == groups || groups.size() == 0 || !groupTypeMatch) {
                group.setGroupId(UUID.randomUUID().toString());
                group.setGroupName(grouptitle);
                group.setUpdateTime(new Date());
                group.setCreator(NpContextHolder.getContext().getUserId().toString());
                group.setModelType(type);
                if (this.groupDao.InsertQueryModalGroup(group).booleanValue()) {
                    this.authority.grantAllPrivilegesForQueryModelGroup(group.getGroupId(), group.getModelType());
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u5206\u7ec4", (String)("\u65b0\u5efa\u5206\u7ec4\u6210\u529f\uff0c\u5206\u7ec4\u6807\u9898\uff1a" + grouptitle));
                    QueryModalTreeNode node1 = QueryModalTreeNode.buildTreeNodeData(group);
                    ITree tree1 = new ITree((INode)node1);
                    tree1.setLeaf(false);
                    return this.objectMapper.writeValueAsString((Object)tree1);
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u5206\u7ec4", (String)"\u65b0\u5efa\u5206\u7ec4\u5931\u8d25");
                return null;
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u5206\u7ec4", (String)"\u65b0\u5efa\u5206\u7ec4\uff1a\u6a21\u677f\u5206\u7ec4\u5df2\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\uff01");
            return "\u6a21\u677f\u5206\u7ec4\u5df2\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\uff01";
        }
        catch (JsonProcessingException jE) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u5206\u7ec4\u5f02\u5e38", (String)("\u65b0\u5efa\u5206\u7ec4\u5f02\u5e38\u4fe1\u606f\uff1a" + (Object)((Object)jE)));
            return null;
        }
        catch (DataTypeException e) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u5206\u7ec4\u5f02\u5e38", (String)("\u65b0\u5efa\u5206\u7ec4\u5f02\u5e38\u4fe1\u606f\uff1a" + (Object)((Object)e)));
            return null;
        }
    }

    @RequestMapping(value={"/query-all-deleteModalGroup"}, method={RequestMethod.POST})
    public String deleteModelGroup(@RequestBody Map<String, String> groupObject) {
        String groupId = groupObject.get("groupId");
        QueryModelType type = QueryModelType.valueOf(groupObject.get("type"));
        String groupCondition = "QMG_PARENTID in ('" + groupId + "') " + this.getTypeCondition(type, "group");
        String modalCondition = "QMD_GROUPID in ('" + groupId + "') " + this.getTypeCondition(type, "model");
        try {
            List<QueryModalGroup> childGroups = this.groupDao.getModalGroupByParentId(groupId, type);
            if (childGroups != null && !childGroups.isEmpty()) {
                return "has_child";
            }
            List<QueryModalDefine> childModals = this.modelDao.getModalsByGroupId(groupId, type);
            if (childModals != null && !childModals.isEmpty()) {
                return "has_child";
            }
            if (type.equals((Object)QueryModelType.OWNER) || type.equals((Object)QueryModelType.DASHOWNER) || type.equals((Object)QueryModelType.SIMPLEOWER) || this.authority.canDeleteModalGroup(groupId, type)) {
                List<QueryModalDefine> modals = this.authority.getModalsByCondition(modalCondition, null, type, "query_model_resource_delete");
                List<QueryModalGroup> groups = this.authority.getGroupsByCondition(groupCondition, null, type, "query_model_resource_delete");
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f\u5206\u7ec4", (String)("\u5220\u9664\u6a21\u677f\u5206\u7ec4\u6210\u529f,groupId:" + groupId));
                return this.groupDao.DeleteQueryModalGroupById(groupId, groups, modals).toString();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f\u5206\u7ec4\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5220\u9664\u6a21\u677f\u5206\u7ec4", (String)("\u5220\u9664\u5206\u7ec4\uff1a\u5931\u8d25\uff0cgroupId" + groupId));
        return String.valueOf(false);
    }

    @GetMapping(value={"/get-query-authorize"})
    public String getAuthorize() {
        ObjectMapper mapper = new ObjectMapper();
        String packageName = "com.jiuqi.nr.query";
        List<String> list = Arrays.asList(QueryConst.QUERY_FUNCPOINTS);
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            for (String funcPoint : list) {
                map.put(funcPoint, this.authorizeService.findAuthorizeConfig(packageName, packageName + "." + funcPoint));
            }
            return mapper.writeValueAsString(map);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/query-all-updateModalGroup"}, method={RequestMethod.POST})
    public String updateModelGroup(@RequestBody Map<String, String> groupObject) {
        try {
            String groupId = groupObject.get("groupId");
            String groupTitle = groupObject.get("groupTitle");
            String groupParentId = groupObject.get("groupParentId");
            List<Object> groups = new ArrayList();
            groups = this.groupDao.getModalGroupByTitle(groupTitle, groupParentId);
            if (null == groups || groups.size() == 0) {
                QueryModalGroup group = new QueryModalGroup();
                group = this.groupDao.GetQueryModalGroupById(groupId);
                group.setGroupName(groupTitle);
                if (group.getModelType().equals((Object)QueryModelType.OWNER) || group.getModelType().equals((Object)QueryModelType.DASHOWNER) || group.getModelType().equals((Object)QueryModelType.SIMPLEOWER) || this.authority.canWriteModalGroup(group.getGroupId(), group.getModelType())) {
                    if (this.groupDao.UpdateQueryModalGroup(group).booleanValue()) {
                        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4", (String)("\u7f16\u8f91\u5206\u7ec4\uff0c\u5206\u7ec4Id:" + groupId));
                        QueryModalTreeNode node1 = QueryModalTreeNode.buildTreeNodeData(group);
                        ITree tree1 = new ITree((INode)node1);
                        tree1.setLeaf(false);
                        return this.objectMapper.writeValueAsString((Object)tree1);
                    }
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4", (String)"\u7f16\u8f91\u5206\u7ec4\uff1a\u7f16\u8f91\u5931\u8d25");
                    return "\u66f4\u65b0\u5931\u8d25";
                }
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4", (String)"\u7f16\u8f91\u5206\u7ec4\u5931\u8d25\uff1a\u5f53\u524d\u7528\u6237\u6ca1\u6709\u4fee\u6539\u6a21\u677f\u7684\u6743\u9650");
                return "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u4fee\u6539\u6a21\u677f\u7684\u6743\u9650";
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4", (String)"\u7f16\u8f91\u5206\u7ec4\u5931\u8d25\uff1a\u6a21\u677f\u5206\u7ec4\u5df2\u5b58\u5728");
            return "\u6a21\u677f\u5206\u7ec4\u5df2\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\uff01";
        }
        catch (JsonProcessingException jE) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4\u5f02\u5e38", (String)("\u7f16\u8f91\u5206\u7ec4\u5f02\u5e38\u4fe1\u606f\uff1a" + (Object)((Object)jE)));
            return null;
        }
        catch (DataTypeException e) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u7f16\u8f91\u5206\u7ec4\u5f02\u5e38", (String)("\u7f16\u8f91\u5206\u7ec4\u5f02\u5e38\u4fe1\u606f\uff1a" + (Object)((Object)e)));
            return null;
        }
    }

    @RequestMapping(value={"/query-all-addQueryModal"}, method={RequestMethod.POST})
    public String addQueryModal(@RequestBody QueryModalDefine modal) {
        return this.servicesHelper.insertModal(modal);
    }

    @RequestMapping(value={"/query-change"}, method={RequestMethod.POST})
    public String changeModelPosition(@RequestBody Map<String, String> params) {
        try {
            return this.servicesHelper.changeModelOrder(params.get("curNodeCode"), params.get("firstCode"), params.get("secondCode"), params.get("groupId"));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    @RequestMapping(value={"/query-changeModelTitle"}, method={RequestMethod.POST})
    public String changeModelTitle(@RequestBody Map<String, String> params) {
        try {
            String modelId = params.get("modelId");
            String title = params.get("title");
            String rtn = this.servicesHelper.changeModelTitle(modelId, title);
            return rtn;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return "false";
        }
    }

    private QueryBlockDefine setDefaultPeriod(QueryBlockDefine block) {
        FormSchemeDefine scheme = this.dimensionHelper.getScheme(block.getBlockInfo().getFormSchemeKey());
        JSONObject dPeriod = new JSONObject();
        QueryDimensionDefine periodDim = null;
        try {
            Optional<QueryDimensionDefine> dims = block.getQueryDimensions().stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_ENTITY && idx.getLayoutType() == QueryLayoutType.LYT_CONDITION && idx.isPeriodDim()).findFirst();
            if (dims.isPresent() && (periodDim = dims.get()) != null) {
                boolean isCustomPeriod;
                List<QuerySelectItem> items = periodDim.getDefaultItems();
                boolean bl = isCustomPeriod = 8 == scheme.getPeriodType().type();
                if (items.size() >= 2 || isCustomPeriod) {
                    String period = QueryHelper.getCurrentPeriod(scheme.getPeriodType().type(), scheme.getPeriodOffset(), isCustomPeriod ? null : items.get(0).getCode(), isCustomPeriod ? null : items.get(1).getCode(), periodDim.getViewId());
                    dPeriod = new JSONObject(period);
                } else if (items.size() > 0) {
                    String period = QueryHelper.getCurrentPeriod(scheme.getPeriodType().type(), scheme.getPeriodOffset(), isCustomPeriod ? null : items.get(0).getCode(), isCustomPeriod ? null : items.get(0).getCode(), periodDim.getViewId());
                    dPeriod = new JSONObject(period);
                }
            }
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u9ed8\u8ba4\u65f6\u671f\u83b7\u53d6\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex.getMessage()));
        }
        if (periodDim != null && dPeriod.length() > 0) {
            ArrayList<QuerySelectItem> selitems = new ArrayList<QuerySelectItem>();
            QuerySelectItem item = new QuerySelectItem();
            item.setCode(dPeriod.getString("code"));
            item.setTitle(dPeriod.getString("title"));
            selitems.add(item);
            if (periodDim.getSelectType() == QuerySelectionType.RANGE) {
                selitems.add(item);
            }
            periodDim.setSelectItems(selitems);
        }
        return block;
    }

    /*
     * WARNING - void declaration
     */
    @RequestMapping(value={"/query-all-queryBlocksByModalId"}, method={RequestMethod.GET})
    public QueryModalDefine QueryBlocksByModalId(String modal, String behavior) {
        try {
            QueryModalDefine queryModalDefine = new QueryModalDefine();
            queryModalDefine = this.modelDao.getQueryModalDefineById(modal);
            if (queryModalDefine == null) {
                return null;
            }
            if (queryModalDefine.getModelExtension() != null) {
                queryModalDefine.JsonToModelExtension(queryModalDefine.getModelExtension());
            }
            if (queryModalDefine.getConditions() != null) {
                queryModalDefine.jsonTomodelCondition(queryModalDefine.getConditions());
            }
            List<Object> blocks = new ArrayList();
            ArrayList<QueryBlockDefine> gridDateBlocks = new ArrayList<QueryBlockDefine>();
            blocks = this.blockDao.GetQueryBlockDefinesByModelId(modal);
            for (QueryBlockDefine queryBlockDefine : blocks) {
                void var7_8;
                queryBlockDefine.setBlockInfoStr(new String(queryBlockDefine.getBlockInfoBlob()));
                SimpleModule module = new SimpleModule();
                module.addDeserializer(BlockInfo.class, (JsonDeserializer)new BlockInfoDeserializer());
                module.addSerializer(BlockInfo.class, (JsonSerializer)new BlockInfoSerializer());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule((Module)module);
                BlockInfo blockinfo = (BlockInfo)objectMapper.readValue(queryBlockDefine.getBlockInfoBlob(), BlockInfo.class);
                queryBlockDefine.setBlockInfoStr(objectMapper.writeValueAsString((Object)blockinfo));
                DataQueryHelper queryHelper = new DataQueryHelper();
                queryBlockDefine.setQueryType(queryModalDefine.getQueryType());
                if (queryBlockDefine.getBlockType() == QueryBlockType.QBT_CUSTOMENTRY) {
                    this.setDefaultPeriod(queryBlockDefine);
                }
                if (queryModalDefine.getQueryModelExtension() != null && queryModalDefine.getQueryModelExtension().isAutoQuery() || behavior.equals("view")) {
                    HashMap<String, String> block = new HashMap<String, String>();
                    block.put("blockId", queryBlockDefine.getId());
                    this.resetCache(block);
                    QueryBlockDefine queryBlockDefine2 = queryHelper.getQueryDataGrid(queryBlockDefine, queryModalDefine.getModelType(), false);
                } else {
                    QueryBlockDefine queryBlockDefine3 = queryHelper.getQueryFormGrid(queryBlockDefine, null, null);
                }
                gridDateBlocks.add((QueryBlockDefine)var7_8);
            }
            queryModalDefine.setBlocks(gridDateBlocks);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f", (String)("\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f\u6210\u529f\uff0c\u6a21\u677fid:" + modal));
            return queryModalDefine;
        }
        catch (Exception e) {
            Log.error((Exception)e);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f\u5f02\u5e38", (String)("\u901a\u8fc7\u6a21\u677fid\u67e5\u8be2\u6a21\u677f\u5f02\u5e38\u4fe1\u606f\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/template-allsubjects"}, method={RequestMethod.GET})
    public String getAllSubjects() {
        return "[{\"subjectId\":\"dashboard\",\"subjecttitle\":\"\u4eea\u8868\u76d8\"},{\"subjectId\":\"query\",\"subjecttitle\":\"\u591a\u7ef4\u67e5\u8be2\"},{\"subjectId\":\"simple\",\"subjecttitle\":\"\u8fc7\u5f55\u67e5\u8be2\"}]";
    }

    @RequestMapping(value={"/query-modal-queryallsimplegroups"}, method={RequestMethod.GET})
    public List<QueryModalGroup> queryAllSimpleModalGroups() {
        return this.queryAllModalGroups("simple");
    }

    @RequestMapping(value={"/query-modal-queryalldashboardgroups"}, method={RequestMethod.GET})
    public List<QueryModalGroup> queryAllDashboardModalGroups() {
        return this.queryAllModalGroups("dashboard");
    }

    @RequestMapping(value={"/query-modal-queryallgroups"}, method={RequestMethod.GET})
    public List<QueryModalGroup> queryAllModalGroups(String type) {
        if (type == null) {
            type = "query";
        }
        QueryModelType modelType = this.getType(type);
        try {
            List<QueryModalGroup> groups = this.groupDao.GetAllQueryModalGroups(modelType);
            List<QueryModalGroup> authGroups = this.authority.groupCheckAuthority(groups, modelType, "query_model_resource_read");
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4", (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5206\u7ec4\u6210\u529f");
            return authGroups;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/query-modal-queryallsimplemodals"}, method={RequestMethod.POST})
    public List<QueryModalDefine> queryAllSimpleModals(@RequestBody Map<String, String[]> groupIds) {
        if (!groupIds.isEmpty()) {
            groupIds.put("groupId", groupIds.get("simpleQueryGroupId"));
        }
        return this.queryAllModals(groupIds, "simple");
    }

    @RequestMapping(value={"/query-modal-queryalldashboardmodals"}, method={RequestMethod.POST})
    public List<QueryModalDefine> queryAllDashboardModals(@RequestBody Map<String, String[]> groupIds) {
        if (!groupIds.isEmpty()) {
            groupIds.put("groupId", groupIds.get("dashboardGroupId"));
        }
        return this.queryAllModals(groupIds, "dashboard");
    }

    @RequestMapping(value={"/query-modal-queryallmodals"}, method={RequestMethod.POST})
    public List<QueryModalDefine> queryAllModals(@RequestBody Map<String, String[]> groupIds, String type) {
        String[] keys = groupIds.get("groupId");
        String[] subjects = groupIds.get("subjectId");
        if (type == null) {
            type = "query";
        }
        QueryModelType modelType = this.getType(type);
        if (groupIds.isEmpty() || !groupIds.containsKey("groupId") || keys.length == 0) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f", (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u6210\u529f");
            return this.modelDao.getAllModalsByModelType(modelType);
        }
        ArrayList<QueryModalDefine> modals = new ArrayList<QueryModalDefine>();
        try {
            for (String key : keys) {
                List<QueryModalDefine> modalsInGroup = this.modelDao.getModalsByGroupId(key);
                if (modalsInGroup == null || modalsInGroup.isEmpty()) continue;
                modals.addAll(modalsInGroup);
            }
            this.authority.modelCheckAuthority(modals, modelType, "query_model_resource_read");
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f", (String)"\u67e5\u8be2\u6240\u6709\u6a21\u677f\u5931\u8d25");
        return modals;
    }

    @RequestMapping(value={"/query-modal-queryallblocks"}, method={RequestMethod.POST})
    public List<QueryBlockDefine> queryAllBlocks(@RequestBody Map<String, String[]> modalId) {
        if (modalId.isEmpty()) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757", (String)"\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757\uff0cmodalId\u4e3a\u7a7a");
            return null;
        }
        int n = 0;
        String[] keys = modalId.get("tmlId");
        String[] stringArray = keys;
        int n2 = stringArray.length;
        if (n < n2) {
            String key = stringArray[n];
            List<QueryBlockDefine> queryBlocks = this.blockDao.GetQueryBlockDefinesByModelId(key);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757", (String)("\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757\uff0cmodelId\uff1a" + key));
            return queryBlocks;
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757", (String)"\u67e5\u8be2\u6a21\u677f\u4e0b\u6240\u6709\u5757\uff1anull");
        return null;
    }

    @RequestMapping(value={"/query-modal-getShortestPeriod"}, method={RequestMethod.GET})
    public String getShortestPeriod(String periods) {
        String[] periodTypes = new String[]{"DAY", "WEEK", "TENDAY", "MONTH", "SEASON", "HALFYEAR", "YEAR", "CUSTOM"};
        try {
            periods = periods.toUpperCase();
            for (String p : periodTypes) {
                if (periods.indexOf(p) < 0) continue;
                return p;
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return "DEFAULT";
    }

    @RequestMapping(value={"/query-getBItoken"}, method={RequestMethod.GET})
    public String getBiToken(HttpServletRequest request) {
        try {
            String tokenStr = this.biApi.getToken("query-getBItoken");
            return tokenStr;
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Deprecated
    public static Thread findThread(long threadId) {
        for (ThreadGroup group = Thread.currentThread().getThreadGroup(); group != null; group = group.getParent()) {
            Thread[] threads = new Thread[(int)((double)group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for (int i = 0; i < count; ++i) {
                if (threadId != threads[i].getId()) continue;
                return threads[i];
            }
        }
        return null;
    }

    @RequestMapping(value={"/query-block-resetCache"}, method={RequestMethod.POST})
    public String resetCache(@RequestBody Map<String, String> param) {
        try {
            String blockKey = param.get("blockId");
            String gridKey = QueryGridFactory.buildKey(blockKey, false);
            QueryGridFactory queryGridCache = new QueryGridFactory();
            queryGridCache.remove(gridKey);
            String userKey = QueryHelper.getCacheKey(NpContextHolder.getContext().getTenant().toString(), NpContextHolder.getContext().getUserId());
            boolean res = this.queryCacheManager.reSetCache(userKey, blockKey, "PAGEINFO");
            if (!res) {
                throw new RuntimeException("\u5206\u9875\u4fe1\u606f\u91cd\u7f6e\u5931\u8d25!");
            }
        }
        catch (Exception ex) {
            logger.error("\u91cd\u7f6e\u7f13\u5b58\u8bf7\u6c42\u5f02\u5e38" + ex.getMessage(), ex);
        }
        return null;
    }

    @RequestMapping(value={"/query-block-clearCache"}, method={RequestMethod.GET})
    public String clearCache() {
        try {
            QueryGridFactory queryGridCache = new QueryGridFactory();
            queryGridCache.removeAllLocalCache();
            this.queryCacheManager.clearCache();
        }
        catch (Exception ex) {
            logger.error("\u6e05\u7a7a\u7f13\u5b58\u5f02\u5e38:" + ex.getMessage());
        }
        return null;
    }

    @PostMapping(value={"/query-block-uploadimg"})
    public String uploadBlockImg(@RequestParam(value="file") MultipartFile file, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        return this.uploadImg(file, headers, request, "block");
    }

    @PostMapping(value={"/query-modal-uploadimg"})
    public String uploadModalImg(@RequestParam(value="file") MultipartFile file, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        return this.uploadImg(file, headers, request, "modal");
    }

    @PostMapping(value={"/query-block-deleteimg"})
    public FileInfo deleteBlockImg(@RequestBody Map<String, String> param) {
        try {
            String fileKey = param.get("fileKey");
            if (fileKey == null) {
                return null;
            }
            String[] urls = fileKey.split("/");
            String fi = urls[urls.length - 2];
            FileInfo fileInfo = this.deleteImg(fi, "block");
            return fileInfo;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @PostMapping(value={"/query-modal-deleteimg"})
    public FileInfo deleteModalImg(@RequestBody Map<String, String> param) {
        try {
            String fileKey = param.get("fileKey");
            if (fileKey == null) {
                return null;
            }
            String[] urls = fileKey.split("/");
            String fi = urls[urls.length - 2];
            FileInfo fileInfo = this.deleteImg(fi, "modal");
            return fileInfo;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/nvwa-dataset-creatQueryModelByDataSet"}, method={RequestMethod.GET})
    public QueryModalDefine getQueryModelByDataSet(String modelName) {
        QueryModalDefine queryModalDefine = new QueryModalDefine();
        queryModalDefine.setId(UUID.randomUUID().toString());
        queryModalDefine.setTitle(modelName);
        ContextUser user = NpContextHolder.getContext().getUser();
        queryModalDefine.setCreator(NpContextHolder.getContext().getUserId().toString());
        queryModalDefine.setLayout("a");
        queryModalDefine.setUpdateTime(new Date());
        queryModalDefine.setQueryModelExtension(new QueryModelExtension());
        queryModalDefine.setModelType(QueryModelType.DATASET);
        queryModalDefine.setGroupId("b8079ac0-dc15-11e8-969b-64006a6432d8");
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u65b0\u5efa\u6a21\u677f", (String)"\u65b0\u5efa\u6a21\u677f\u6210\u529f");
        return queryModalDefine;
    }

    public String uploadImg(MultipartFile file, HttpHeaders headers, HttpServletRequest request, String areaName) {
        String url = "";
        try {
            url = this.uploadFile(file, headers, url, areaName);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    private String uploadFile(MultipartFile file, HttpHeaders headers, String url, String areaName) throws IOException {
        String realKey = UUID.randomUUID().toString();
        FileInfo upFile = this.fileService.area(areaName).uploadByKey(file.getOriginalFilename(), realKey, null, file.getBytes());
        String path = this.fileService.area(areaName).getPath(upFile.getKey(), NpContextHolder.getContext().getTenant());
        byte[] textByte = path.getBytes("UTF-8");
        upFile = FileInfoBuilder.newFileInfo((FileInfo)upFile, (String)upFile.getFileGroupKey(), (String)Base64.encodeBase64String((byte[])textByte));
        String origin = "";
        String fileServiceAddress = this.addressProperties.getFileService();
        if (fileServiceAddress != null) {
            origin = headers.getOrigin().substring(0, headers.getOrigin().indexOf("//"));
            origin = origin + "//" + fileServiceAddress + "/";
        }
        url = origin + "api/file/downloadImg/{path}/".replace("{path}", upFile.getKey()) + areaName;
        return url;
    }

    private FileInfo deleteImg(String fileKey, String areaName) {
        try {
            FileInfo fileInfo = this.fileService.area(areaName).delete(fileKey);
            return fileInfo;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    static enum Changes {
        SELECTFIELDS,
        CONDITIONITEM,
        SHOWNULL,
        SHOWZERO,
        FILTER;

    }
}


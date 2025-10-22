/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo
 *  com.jiuqi.nr.attachment.input.AttachmentQueryInfo
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.input.FilePoolAllFileContext
 *  com.jiuqi.nr.attachment.input.FilePoolContext
 *  com.jiuqi.nr.attachment.input.SearchContext
 *  com.jiuqi.nr.attachment.message.FileCategoryInfo
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.message.RowDataInfo
 *  com.jiuqi.nr.attachment.output.FilePoolFiles
 *  com.jiuqi.nr.attachment.output.ReferencesInfo
 *  com.jiuqi.nr.attachment.output.RowDataValues
 *  com.jiuqi.nr.attachment.service.FileCategoryRelService
 *  com.jiuqi.nr.attachment.service.FileCategoryService
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.utils.FileOperationUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.jtable.params.base.FileLinkData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.dataentry.attachment.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.attachment.input.AttachmentQueryByFieldInfo;
import com.jiuqi.nr.attachment.input.AttachmentQueryInfo;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FilePoolAllFileContext;
import com.jiuqi.nr.attachment.input.FilePoolContext;
import com.jiuqi.nr.attachment.input.SearchContext;
import com.jiuqi.nr.attachment.message.FileCategoryInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import com.jiuqi.nr.attachment.output.FilePoolFiles;
import com.jiuqi.nr.attachment.output.ReferencesInfo;
import com.jiuqi.nr.attachment.output.RowDataValues;
import com.jiuqi.nr.attachment.service.FileCategoryRelService;
import com.jiuqi.nr.attachment.service.FileCategoryService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataentry.attachment.enums.ColumnsEnum;
import com.jiuqi.nr.dataentry.attachment.enums.FileTypeEnum;
import com.jiuqi.nr.dataentry.attachment.enums.ToolbarEnum;
import com.jiuqi.nr.dataentry.attachment.intf.AttachmentReferencesContext;
import com.jiuqi.nr.dataentry.attachment.intf.FileGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.FilePoolAtachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataContext;
import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentGridDataPageContext;
import com.jiuqi.nr.dataentry.attachment.intf.QueryFileInfoParam;
import com.jiuqi.nr.dataentry.attachment.message.AttachmentDetails;
import com.jiuqi.nr.dataentry.attachment.message.ColumnsInfo;
import com.jiuqi.nr.dataentry.attachment.message.FileCountInfo;
import com.jiuqi.nr.dataentry.attachment.message.FileDetails;
import com.jiuqi.nr.dataentry.attachment.message.FileNode;
import com.jiuqi.nr.dataentry.attachment.message.FileTypeInfo;
import com.jiuqi.nr.dataentry.attachment.message.FormDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.GridDataInfo;
import com.jiuqi.nr.dataentry.attachment.message.JurisdictionResult;
import com.jiuqi.nr.dataentry.attachment.message.RowDatasInfo;
import com.jiuqi.nr.dataentry.attachment.message.ToolBarInfo;
import com.jiuqi.nr.dataentry.attachment.service.IAttachmentService;
import com.jiuqi.nr.dataentry.attachment.util.AuthorityJudgmentUtil;
import com.jiuqi.nr.dataentry.attachment.util.ConstantInformation;
import com.jiuqi.nr.dataentry.attachment.util.RowDataUtil;
import com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl
implements IAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private ReadWriteAccessProvider accessProvider;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FileCategoryService fileCategoryService;
    @Autowired(required=false)
    private FileCategoryRelService fileCategoryRelService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public List<ITree<FileNode>> loadFieldGroups(IAttachmentContext context) {
        ITree rootNode;
        String language;
        JtableContext jtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        ArrayList<ITree<FileNode>> parentNode = new ArrayList<ITree<FileNode>>();
        ArrayList<ITree<FileNode>> childNodes = new ArrayList<ITree<FileNode>>();
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (!openFilepool) {
            ITree<FileNode> allAttachmentNode;
            language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                allAttachmentNode = this.constructionNode("0", "ALL_ATTACHMENT", "\u5168\u90e8\u9644\u4ef6", null, "allAttachmentNode", ConstantInformation.PARENT_NODE_ICONS, false, null);
                allAttachmentNode.setSelected(true);
                allAttachmentNode.setExpanded(true);
                ((FileNode)allAttachmentNode.getData()).setReadable(true);
                ((FileNode)allAttachmentNode.getData()).setWriteable(true);
                allAttachmentNode.setChildren(childNodes);
                parentNode.add(allAttachmentNode);
            } else {
                allAttachmentNode = this.constructionNode("0", "ALL_ATTACHMENT", "All attachments", null, "allAttachmentNode", ConstantInformation.PARENT_NODE_ICONS, false, null);
                allAttachmentNode.setSelected(true);
                allAttachmentNode.setExpanded(true);
                ((FileNode)allAttachmentNode.getData()).setReadable(true);
                ((FileNode)allAttachmentNode.getData()).setWriteable(true);
                allAttachmentNode.setChildren(childNodes);
                parentNode.add(allAttachmentNode);
            }
        } else {
            ITree<FileNode> unreferAttachmentNode;
            ITree<FileNode> allReferAttachmentNode;
            language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                allReferAttachmentNode = this.constructionNode("00", "ALL_REFERENCED_ATTACHMENT", "\u5168\u90e8\u88ab\u5f15\u7528\u9644\u4ef6", null, "allReferencedAttachmentNode", ConstantInformation.FILE_POOL_NODE_ICONS, false, null);
                allReferAttachmentNode.setSelected(true);
                allReferAttachmentNode.setExpanded(true);
                ((FileNode)allReferAttachmentNode.getData()).setReadable(true);
                ((FileNode)allReferAttachmentNode.getData()).setWriteable(true);
                allReferAttachmentNode.setChildren(childNodes);
                parentNode.add(allReferAttachmentNode);
                if (!"dataentryActionEvent".equals(context.getFrom())) {
                    unreferAttachmentNode = this.constructionNode("000", "UNREFERENCED_ATTACHMENT", "\u672a\u88ab\u5f15\u7528\u9644\u4ef6", null, "UnreferencedAttachmentNode", ConstantInformation.FILE_POOL_NODE_ICONS, true, null);
                    unreferAttachmentNode.setSelected(false);
                    ((FileNode)unreferAttachmentNode.getData()).setReadable(true);
                    ((FileNode)unreferAttachmentNode.getData()).setWriteable(true);
                    parentNode.add(unreferAttachmentNode);
                }
            } else {
                allReferAttachmentNode = this.constructionNode("00", "ALL_REFERENCED_ATTACHMENT", "All referenced attachments", null, "allReferencedAttachmentNode", ConstantInformation.FILE_POOL_NODE_ICONS, false, null);
                allReferAttachmentNode.setSelected(true);
                allReferAttachmentNode.setExpanded(true);
                ((FileNode)allReferAttachmentNode.getData()).setReadable(true);
                ((FileNode)allReferAttachmentNode.getData()).setWriteable(true);
                allReferAttachmentNode.setChildren(childNodes);
                parentNode.add(allReferAttachmentNode);
                if (!"dataentryActionEvent".equals(context.getFrom())) {
                    unreferAttachmentNode = this.constructionNode("000", "UNREFERENCED_ATTACHMENT", "Unreferenced attachment", null, "UnreferencedAttachmentNode", ConstantInformation.FILE_POOL_NODE_ICONS, true, null);
                    unreferAttachmentNode.setSelected(false);
                    ((FileNode)unreferAttachmentNode.getData()).setReadable(true);
                    ((FileNode)unreferAttachmentNode.getData()).setWriteable(true);
                    parentNode.add(unreferAttachmentNode);
                }
            }
        }
        if (!this.judgeSecretLevel(jtableContext)) {
            rootNode = (ITree)parentNode.get(0);
            if (null == rootNode.getChildren() || rootNode.getChildren().isEmpty()) {
                rootNode.setLeaf(true);
            }
            return parentNode;
        }
        try {
            List<FormDefine> formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey());
            formDefineList = this.filterForm(formDefineList);
            ArrayList<Consts.FormAccessLevel> formAccessLevels = new ArrayList<Consts.FormAccessLevel>();
            formAccessLevels.add(Consts.FormAccessLevel.FORM_READ);
            formAccessLevels.add(Consts.FormAccessLevel.FORM_DATA_READ);
            formAccessLevels.add(Consts.FormAccessLevel.FORM_DATA_WRITE);
            AuthorityJudgmentUtil authorityJudgmentUtil = new AuthorityJudgmentUtil(jtableContext, formDefineList, formAccessLevels);
            for (FormDefine formDefine : formDefineList) {
                JurisdictionResult visibleResult = authorityJudgmentUtil.visible(formDefine.getKey(), jtableContext, this.jtableParamService, this.dimensionValueProvider, this.accessProvider);
                if (!visibleResult.isHaveAccess()) continue;
                ITree<FileNode> formDefineNode = this.constructionNode(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle(), formDefine.getKey(), "formDefineNode", ConstantInformation.CHILD_NODE_ICONS, true, null);
                SecretLevelInfo secretLevelInfo = this.getSecretLevelInfo(context, formDefine);
                JurisdictionResult readableResult = authorityJudgmentUtil.readable(formDefine.getKey(), jtableContext, this.jtableParamService, this.dimensionValueProvider, this.accessProvider);
                if (readableResult.isHaveAccess()) {
                    ((FileNode)formDefineNode.getData()).setReadable(true);
                    JurisdictionResult writeableResult = authorityJudgmentUtil.writeable(formDefine.getKey(), jtableContext, this.jtableParamService, this.dimensionValueProvider, this.accessProvider);
                    ((FileNode)formDefineNode.getData()).setWriteable(writeableResult.isHaveAccess());
                    ((FileNode)formDefineNode.getData()).setMessage(writeableResult.getMessage());
                    ArrayList<ITree<FileNode>> fieldDefineNodes = new ArrayList<ITree<FileNode>>();
                    this.getIndicatorNode(formDefine, fieldDefineNodes, authorityJudgmentUtil, jtableContext, readableResult, writeableResult, null != secretLevelInfo ? secretLevelInfo.getSecretLevelItem() : null);
                    formDefineNode.setChildren(fieldDefineNodes);
                    if (formDefineNode.getChildren().isEmpty()) continue;
                    ((FileNode)formDefineNode.getData()).setSecretLevelItem(null != secretLevelInfo ? secretLevelInfo.getSecretLevelItem() : null);
                    childNodes.add(formDefineNode);
                    continue;
                }
                ((FileNode)formDefineNode.getData()).setReadable(false);
                ((FileNode)formDefineNode.getData()).setWriteable(false);
                ((FileNode)formDefineNode.getData()).setMessage(visibleResult.getMessage());
                ArrayList<ITree<FileNode>> fieldDefineNodes = new ArrayList<ITree<FileNode>>();
                this.getIndicatorNode(formDefine, fieldDefineNodes, authorityJudgmentUtil, jtableContext, readableResult, null, null != secretLevelInfo ? secretLevelInfo.getSecretLevelItem() : null);
                formDefineNode.setChildren(fieldDefineNodes);
                if (fieldDefineNodes.isEmpty()) continue;
                ((FileNode)formDefineNode.getData()).setSecretLevelItem(null != secretLevelInfo ? secretLevelInfo.getSecretLevelItem() : null);
                childNodes.add(formDefineNode);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        rootNode = (ITree)parentNode.get(0);
        if (null == rootNode.getChildren() || rootNode.getChildren().isEmpty()) {
            rootNode.setLeaf(true);
        }
        return parentNode;
    }

    private SecretLevelInfo getSecretLevelInfo(IAttachmentContext context, FormDefine formDefine) {
        JtableContext secretLevelJtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        secretLevelJtableContext.setFormKey(formDefine.getKey());
        SecretLevelInfo secretLevelInfo = this.secretLevelService.getSecretLevel(secretLevelJtableContext);
        return secretLevelInfo;
    }

    private List<FormDefine> filterForm(List<FormDefine> formDefineList) {
        ArrayList<FormDefine> formDefines = new ArrayList<FormDefine>();
        try {
            block2: for (FormDefine formDefine : formDefineList) {
                List allFieldKeys = this.iRunTimeViewController.getFieldKeysInForm(formDefine.getKey());
                List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)allFieldKeys);
                for (FieldDefine fieldDefine : fieldDefines) {
                    if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                    formDefines.add(formDefine);
                    continue block2;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return formDefines;
    }

    private boolean judgeSecretLevel(JtableContext jtableContext) {
        if (this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
            SecretLevelInfo secretLevelInfo = this.secretLevelService.getSecretLevel(jtableContext);
            return this.secretLevelService.canAccess(secretLevelInfo.getSecretLevelItem());
        }
        return true;
    }

    private void getIndicatorNode(FormDefine formDefine, List<ITree<FileNode>> fieldDefineNodes, AuthorityJudgmentUtil authorityJudgmentUtil, JtableContext jtableContext, JurisdictionResult readableResult, JurisdictionResult writeable, SecretLevelItem secretLevelItem) {
        List dataRegionDefineList = this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey());
        if (readableResult.isHaveAccess()) {
            if (writeable.isHaveAccess()) {
                for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                    List<FieldDefine> fieldDefines = this.getReport(dataRegionDefine.getKey());
                    for (FieldDefine fieldDefine : fieldDefines) {
                        if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                        try {
                            ITree<FileNode> fieldDefineNode = this.constructionNode(fieldDefine.getKey(), fieldDefine.getCode(), fieldDefine.getTitle(), formDefine.getKey(), "fieldDefineNode", ConstantInformation.GRANDSON_NODE_ICONS, true, dataRegionDefine);
                            fieldDefineNode.setLevel(1);
                            ((FileNode)fieldDefineNode.getData()).setReadable(true);
                            ((FileNode)fieldDefineNode.getData()).setWriteable(writeable.isHaveAccess());
                            ((FileNode)fieldDefineNode.getData()).setMessage(writeable.getMessage());
                            ((FileNode)fieldDefineNode.getData()).setSecretLevelItem(secretLevelItem);
                            fieldDefineNodes.add(fieldDefineNode);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            } else {
                for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                    List<FieldDefine> fieldDefines = this.getReport(dataRegionDefine.getKey());
                    for (FieldDefine fieldDefine : fieldDefines) {
                        if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                        ITree<FileNode> fieldDefineNode = this.constructionNode(fieldDefine.getKey(), fieldDefine.getCode(), fieldDefine.getTitle(), formDefine.getKey(), "fieldDefineNode", ConstantInformation.GRANDSON_NODE_ICONS, true, dataRegionDefine);
                        fieldDefineNode.setLevel(1);
                        ((FileNode)fieldDefineNode.getData()).setReadable(true);
                        ((FileNode)fieldDefineNode.getData()).setWriteable(false);
                        ((FileNode)fieldDefineNode.getData()).setMessage(writeable.getMessage());
                        fieldDefineNodes.add(fieldDefineNode);
                    }
                }
            }
        } else {
            for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                List<FieldDefine> fieldDefines = this.getReport(dataRegionDefine.getKey());
                for (FieldDefine fieldDefine : fieldDefines) {
                    if (!fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FILE)) continue;
                    ITree<FileNode> fieldDefineNode = this.constructionNode(fieldDefine.getKey(), fieldDefine.getCode(), fieldDefine.getTitle(), formDefine.getKey(), "fieldDefineNode", ConstantInformation.GRANDSON_NODE_ICONS, true, dataRegionDefine);
                    fieldDefineNode.setLevel(1);
                    ((FileNode)fieldDefineNode.getData()).setReadable(false);
                    ((FileNode)fieldDefineNode.getData()).setWriteable(false);
                    ((FileNode)fieldDefineNode.getData()).setMessage(readableResult.getMessage());
                    fieldDefineNodes.add(fieldDefineNode);
                }
            }
        }
    }

    private List<FieldDefine> getReport(String dataRegionDefineKey) {
        try {
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefineKey);
            List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeys);
            return fieldDefines;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private ITree<FileNode> constructionNode(String key, String code, String title, String formKey, String type, String[] icons, boolean isLeaf, DataRegionDefine dataRegionDefine) {
        FileNode fileNode = new FileNode(key, code, title, formKey, type);
        if (dataRegionDefine != null) {
            List links = this.jtableParamService.getLinks(dataRegionDefine.getKey());
            for (LinkData linkData : links) {
                if (!linkData.getZbcode().equals(code)) continue;
                FileLinkData fileLinkData = (FileLinkData)linkData;
                fileNode.setMaxSize(fileLinkData.getSize());
                fileNode.setMinSize(fileLinkData.getMinSize());
                fileNode.setTypes(fileLinkData.getTypes());
                fileNode.setNum(fileLinkData.getNum());
                fileNode.setDataLinkKey(linkData.getKey());
                break;
            }
        }
        ITree allAttachmentNode = new ITree((INode)fileNode);
        allAttachmentNode.setIcons(icons);
        allAttachmentNode.setLeaf(isLeaf);
        return allAttachmentNode;
    }

    @Override
    public AttachmentDetails loadDetails(IAttachmentGridDataContext context) {
        JtableContext jtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        AttachmentDetails attachmentDetails = new AttachmentDetails();
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemByUserId(identityId)) {
            attachmentDetails.setSystemUser(true);
        } else {
            attachmentDetails.setSystemUser(false);
        }
        ArrayList<ToolBarInfo> toolbar = new ArrayList<ToolBarInfo>();
        this.getToolbar(toolbar, context.getKey(), context.getType(), context.isWriteable(), context.getFrom());
        attachmentDetails.setToolbar(toolbar);
        List confidential = this.secretLevelService.getSecretLevelItems(jtableContext);
        attachmentDetails.setConfidential(confidential);
        FileCountInfo fileCountInfo = new FileCountInfo();
        GridDataInfo gridData = new GridDataInfo();
        gridData.setPageSize(30);
        gridData.setCurrentPage(1);
        boolean openSecretLevel = this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
        attachmentDetails.setOpenSecretLevel(openSecretLevel);
        List<ColumnsInfo> columns = this.getColumnsInfos(openSecretLevel);
        gridData.setColumns(columns);
        this.constructionAttachmentInformation(context, jtableContext, attachmentDetails, fileCountInfo, gridData);
        this.getFileTypeList(attachmentDetails);
        if (this.filePoolService.isOpenFileCategory()) {
            this.getFileCategoryList(attachmentDetails, context);
        }
        return attachmentDetails;
    }

    private void getFileCategoryList(AttachmentDetails attachmentDetails, IAttachmentGridDataContext context) {
        String selected = "";
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFrom()) && "dataentry".equals(context.getFrom()) && null != this.fileCategoryRelService) {
            selected = this.fileCategoryRelService.getSelectedCategoryCode(context.getKey());
        }
        ArrayList<com.jiuqi.nr.dataentry.attachment.message.FileCategoryInfo> fileCategoryInfos = new ArrayList<com.jiuqi.nr.dataentry.attachment.message.FileCategoryInfo>();
        List fileCategoryInfoLists = this.fileCategoryService.getFileCategoryMap();
        for (FileCategoryInfo fileCategoryInfoList : fileCategoryInfoLists) {
            com.jiuqi.nr.dataentry.attachment.message.FileCategoryInfo fileCategoryInfo = new com.jiuqi.nr.dataentry.attachment.message.FileCategoryInfo(fileCategoryInfoList.getCode(), fileCategoryInfoList.getTitle());
            if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)selected) && selected.equals(fileCategoryInfo.getCode())) {
                fileCategoryInfo.setSelected(true);
            }
            fileCategoryInfos.add(fileCategoryInfo);
        }
        attachmentDetails.setFileCategoryInfos(fileCategoryInfos);
    }

    private void getFileTypeList(AttachmentDetails attachmentDetails) {
        ArrayList<FileTypeInfo> fileTypeInfos = new ArrayList<FileTypeInfo>();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            FileTypeInfo fileTypeInfo = new FileTypeInfo(FileTypeEnum.WORD.getCode(), FileTypeEnum.WORD.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.EXCEL.getCode(), FileTypeEnum.EXCEL.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PPT.getCode(), FileTypeEnum.PPT.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PDF.getCode(), FileTypeEnum.PDF.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.TXT.getCode(), FileTypeEnum.TXT.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PICTURE.getCode(), FileTypeEnum.PICTURE.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.AUDIO.getCode(), FileTypeEnum.AUDIO.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.VIDEO.getCode(), FileTypeEnum.VIDEO.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.COMPRESS.getCode(), FileTypeEnum.COMPRESS.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.OTHER.getCode(), FileTypeEnum.OTHER.getTitle());
            fileTypeInfos.add(fileTypeInfo);
        } else {
            FileTypeInfo fileTypeInfo = new FileTypeInfo(FileTypeEnum.WORD_EN.getCode(), FileTypeEnum.WORD_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.EXCEL_EN.getCode(), FileTypeEnum.EXCEL_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PPT_EN.getCode(), FileTypeEnum.PPT_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PDF_EN.getCode(), FileTypeEnum.PDF_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.TXT_EN.getCode(), FileTypeEnum.TXT_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.PICTURE_EN.getCode(), FileTypeEnum.PICTURE_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.AUDIO_EN.getCode(), FileTypeEnum.AUDIO_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.VIDEO_EN.getCode(), FileTypeEnum.VIDEO_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.COMPRESS_EN.getCode(), FileTypeEnum.COMPRESS_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
            fileTypeInfo = new FileTypeInfo(FileTypeEnum.OTHER_EN.getCode(), FileTypeEnum.OTHER_EN.getTitle());
            fileTypeInfos.add(fileTypeInfo);
        }
        attachmentDetails.setFileTypeInfos(fileTypeInfos);
    }

    private void constructionAttachmentInformation(IAttachmentGridDataContext context, JtableContext jtableContext, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        if (this.judgeSecretLevel(jtableContext)) {
            boolean openFilepool = this.filePoolService.isOpenFilepool();
            if (!openFilepool) {
                attachmentDetails.setOpenFilepool(false);
                attachmentDetails.setOpenFileCategory(false);
                if (context.getType().equals("allAttachmentNode")) {
                    this.getAllAttachmentNodes(context, attachmentDetails, fileCountInfo, gridData);
                } else if (context.getType().equals("formDefineNode")) {
                    this.getFormNode(context, attachmentDetails, fileCountInfo, gridData);
                } else if (context.getType().equals("fieldDefineNode")) {
                    this.getIndicatorNode(context, attachmentDetails, fileCountInfo, gridData);
                }
            } else {
                attachmentDetails.setOpenFilepool(true);
                attachmentDetails.setOpenFileCategory(this.filePoolService.isOpenFileCategory());
                if (context.getType().equals("allReferencedAttachmentNode")) {
                    this.getAllReferAttachments(context, attachmentDetails, fileCountInfo, gridData);
                } else if (context.getType().equals("formDefineNode")) {
                    this.getFormReferAttachments(context, attachmentDetails, fileCountInfo, gridData);
                } else if (context.getType().equals("fieldDefineNode")) {
                    this.getIndicatorReferAttachments(context, attachmentDetails, fileCountInfo, gridData);
                } else if (context.getType().equals("UnreferencedAttachmentNode")) {
                    this.getUnreferAttachments(context, attachmentDetails, fileCountInfo, gridData);
                }
            }
        } else {
            this.lowSecurityLevel(attachmentDetails, fileCountInfo, gridData);
        }
    }

    private void lowSecurityLevel(AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        fileCountInfo.setTotalNumber(0);
        fileCountInfo.setTotalSize("0M");
        gridData.setTotalSize(0);
        gridData.setTotalFileSize("0M");
        gridData.setRowDatas(null);
        attachmentDetails.setFileCountInfo(fileCountInfo);
        attachmentDetails.setGridData(gridData);
    }

    private void getIndicatorNode(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        AttachmentQueryByFieldInfo attachmentQueryByFieldInfo = new AttachmentQueryByFieldInfo();
        attachmentQueryByFieldInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryByFieldInfo.setDataSchemeKey(taskDefine.getDataScheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryByFieldInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryByFieldInfo.setFormscheme(context.getFormscheme());
        attachmentQueryByFieldInfo.setFormKey(context.getFormKey());
        attachmentQueryByFieldInfo.setFieldKey(context.getKey());
        attachmentQueryByFieldInfo.setPage(true);
        attachmentQueryByFieldInfo.setPageSize(context.getPageSize());
        attachmentQueryByFieldInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryByFieldInfo.setOrder(context.getOrder());
        attachmentQueryByFieldInfo.setSortBy(context.getSortBy());
        attachmentQueryByFieldInfo.setGroupKey(context.getGroupKey());
        RowDataValues rowDataValues = this.fileOperationService.searchFileByField(attachmentQueryByFieldInfo);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getFormNode(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        AttachmentQueryInfo attachmentQueryInfo = new AttachmentQueryInfo();
        attachmentQueryInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryInfo.setDataSchemeKey(taskDefine.getDataScheme());
        attachmentQueryInfo.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryInfo.setFormKey(Arrays.asList(context.getKey()));
        attachmentQueryInfo.setPage(true);
        attachmentQueryInfo.setPageSize(context.getPageSize());
        attachmentQueryInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryInfo.setOrder(context.getOrder());
        attachmentQueryInfo.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.fileOperationService.searchFile(attachmentQueryInfo);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getAllAttachmentNodes(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        AttachmentQueryInfo attachmentQueryInfo = new AttachmentQueryInfo();
        attachmentQueryInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryInfo.setDataSchemeKey(taskDefine.getDataScheme());
        attachmentQueryInfo.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryInfo.setPage(true);
        attachmentQueryInfo.setPageSize(context.getPageSize());
        attachmentQueryInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryInfo.setOrder(context.getOrder());
        attachmentQueryInfo.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.fileOperationService.searchFile(attachmentQueryInfo);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getAllReferAttachments(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getFormReferAttachments(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getKey());
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getIndicatorReferAttachments(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getFormKey());
        searchContext.setFieldKey(context.getKey());
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        searchContext.setGroupKey(context.getGroupKey());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void getUnreferAttachments(IAttachmentGridDataContext context, AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getFormKey());
        searchContext.setFieldKey(context.getKey());
        searchContext.setNotReferences(true);
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.collateResult(attachmentDetails, fileCountInfo, gridData, rowDataValues);
    }

    private void collateResult(AttachmentDetails attachmentDetails, FileCountInfo fileCountInfo, GridDataInfo gridData, RowDataValues rowDataValues) {
        ArrayList<RowDatasInfo> rowDatasInfos = new ArrayList<RowDatasInfo>();
        for (RowDataInfo rowData : rowDataValues.getRowDatas()) {
            RowDatasInfo rowDatasInfo = new RowDatasInfo();
            rowDatasInfo.setId(rowData.getFileKey());
            rowDatasInfo.setTitle(rowData.getName());
            rowDatasInfo.setCreatetime(null == rowData.getCreatetime() ? "" : RowDataUtil.dateToString(rowData.getCreatetime()));
            String sizeString = RowDataUtil.conversion(rowData.getSize());
            rowDatasInfo.setSize(sizeString);
            rowDatasInfo.setCreator(com.jiuqi.np.definition.common.StringUtils.isEmpty((String)rowData.getCreator()) ? "" : rowData.getCreator());
            rowDatasInfo.setConfidential(rowData.getConfidential());
            rowDatasInfo.setFileCategory(rowData.getCategory());
            rowDatasInfo.setSizeLong(rowData.getSize());
            rowDatasInfo.setWriteable(rowData.isWriteable());
            rowDatasInfo.setGroupKey(rowData.getGroupKey());
            rowDatasInfo.setDw(rowData.getDw());
            rowDatasInfo.setFormKey(rowData.getFormKey());
            rowDatasInfo.setIndex(rowData.getIndex());
            rowDatasInfo.setFilepool(rowData.isFilepool());
            rowDatasInfos.add(rowDatasInfo);
        }
        this.getTheTotalInformationOfAttachments(fileCountInfo, gridData, rowDatasInfos, rowDataValues.getTotalNumber(), rowDataValues.getTotalSize());
        attachmentDetails.setFileCountInfo(fileCountInfo);
        attachmentDetails.setGridData(gridData);
    }

    private void getTheTotalInformationOfAttachments(FileCountInfo fileCountInfo, GridDataInfo gridData, List<RowDatasInfo> rowDatas, Integer totalNumber, long totalSize) {
        if (totalNumber > 0) {
            String sizeString = RowDataUtil.conversion(totalSize);
            fileCountInfo.setTotalNumber(totalNumber);
            fileCountInfo.setTotalSize(sizeString);
            gridData.setTotalSize(totalNumber);
            gridData.setTotalFileSize(sizeString);
            gridData.setRowDatas(rowDatas);
        } else {
            fileCountInfo.setTotalNumber(0);
            fileCountInfo.setTotalSize("0M");
            gridData.setTotalSize(0);
            gridData.setTotalFileSize("0M");
            gridData.setRowDatas(null);
        }
    }

    private List<ColumnsInfo> getColumnsInfos(boolean openSecretLevel) {
        ArrayList<ColumnsInfo> columns = new ArrayList<ColumnsInfo>();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            ColumnsInfo columnsInfo = new ColumnsInfo(ColumnsEnum.INDEX.getKey(), ColumnsEnum.INDEX.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.TITLE.getKey(), ColumnsEnum.TITLE.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.SIZE.getKey(), ColumnsEnum.SIZE.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATOR.getKey(), ColumnsEnum.CREATOR.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATETIME.getKey(), ColumnsEnum.CREATETIME.getTitle());
            columns.add(columnsInfo);
            if (this.filePoolService.isOpenFilepool() && this.filePoolService.isOpenFileCategory()) {
                columnsInfo = new ColumnsInfo(ColumnsEnum.FILECATEGORY.getKey(), ColumnsEnum.FILECATEGORY.getTitle());
                columns.add(columnsInfo);
            }
            if (openSecretLevel) {
                columnsInfo = new ColumnsInfo(ColumnsEnum.CONFIDENTIAL.getKey(), ColumnsEnum.CONFIDENTIAL.getTitle());
                columns.add(columnsInfo);
            }
        } else {
            ColumnsInfo columnsInfo = new ColumnsInfo(ColumnsEnum.INDEX_EN.getKey(), ColumnsEnum.INDEX_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.TITLE_EN.getKey(), ColumnsEnum.TITLE_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.SIZE_EN.getKey(), ColumnsEnum.SIZE_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATOR_EN.getKey(), ColumnsEnum.CREATOR_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATETIME_EN.getKey(), ColumnsEnum.CREATETIME_EN.getTitle());
            columns.add(columnsInfo);
            if (this.filePoolService.isOpenFilepool() && this.filePoolService.isOpenFileCategory()) {
                columnsInfo = new ColumnsInfo(ColumnsEnum.FILECATEGORY_EN.getKey(), ColumnsEnum.FILECATEGORY_EN.getTitle());
                columns.add(columnsInfo);
            }
            if (openSecretLevel) {
                columnsInfo = new ColumnsInfo(ColumnsEnum.CONFIDENTIAL_EN.getKey(), ColumnsEnum.CONFIDENTIAL_EN.getTitle());
                columns.add(columnsInfo);
            }
        }
        return columns;
    }

    private void getToolbar(List<ToolBarInfo> toolbar, String nodeKey, String nodeType, boolean writeable, String from) {
        boolean openFilepool = this.filePoolService.isOpenFilepool();
        if (!openFilepool) {
            String language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETE.getCode(), ToolbarEnum.DELETE.getTitle(), ToolbarEnum.DELETE.getIcon());
                if (nodeType.equals("fieldDefineNode") && !writeable) {
                    toolBarInfo.setDisable(false);
                }
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD.getCode(), ToolbarEnum.DOWNLOAD.getTitle(), ToolbarEnum.DOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                String identityId = NpContextHolder.getContext().getIdentityId();
                if (this.systemIdentityService.isSystemByUserId(identityId) && com.jiuqi.np.definition.common.StringUtils.isEmpty((String)from)) {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHDELETE.getCode(), ToolbarEnum.BATCHDELETE.getTitle(), ToolbarEnum.BATCHDELETE.getIcon());
                    toolbar.add(toolBarInfo);
                }
            } else {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETE_EN.getCode(), ToolbarEnum.DELETE_EN.getTitle(), ToolbarEnum.DELETE_EN.getIcon());
                if (nodeType.equals("fieldDefineNode") && !writeable) {
                    toolBarInfo.setDisable(false);
                }
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD_EN.getCode(), ToolbarEnum.DOWNLOAD_EN.getTitle(), ToolbarEnum.DOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                String identityId = NpContextHolder.getContext().getIdentityId();
                if (this.systemIdentityService.isSystemByUserId(identityId) && com.jiuqi.np.definition.common.StringUtils.isEmpty((String)from)) {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHDELETE_EN.getCode(), ToolbarEnum.BATCHDELETE_EN.getTitle(), ToolbarEnum.BATCHDELETE_EN.getIcon());
                    toolbar.add(toolBarInfo);
                }
            }
        } else if ("dataentry".equals(from)) {
            String language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                ToolBarInfo toolBarInfo = null;
                String chooseFromFilepool = this.i18nHelper.getMessage(ToolbarEnum.CHOOSEFROMFILEPOOL.getCode());
                if (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)chooseFromFilepool)) {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.CHOOSEFROMFILEPOOL.getCode(), ToolbarEnum.CHOOSEFROMFILEPOOL.getTitle(), ToolbarEnum.CHOOSEFROMFILEPOOL.getIcon());
                    if (!writeable) {
                        toolBarInfo.setDisable(false);
                    }
                    toolbar.add(toolBarInfo);
                } else {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.CHOOSEFROMFILEPOOL.getCode(), chooseFromFilepool, ToolbarEnum.CHOOSEFROMFILEPOOL.getIcon());
                    if (!writeable) {
                        toolBarInfo.setDisable(false);
                    }
                    toolbar.add(toolBarInfo);
                }
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD.getCode(), ToolbarEnum.DOWNLOAD.getTitle(), ToolbarEnum.DOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETE.getCode(), ToolbarEnum.DELETE.getTitle(), ToolbarEnum.DELETE.getIcon());
                if (nodeType.equals("fieldDefineNode") && !writeable) {
                    toolBarInfo.setDisable(false);
                }
                toolbar.add(toolBarInfo);
            } else {
                ToolBarInfo toolBarInfo = null;
                String chooseFromFilepool = this.i18nHelper.getMessage(ToolbarEnum.CHOOSEFROMFILEPOOL.getCode());
                if (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)chooseFromFilepool)) {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.CHOOSEFROMFILEPOOL_EN.getCode(), ToolbarEnum.CHOOSEFROMFILEPOOL_EN.getTitle(), ToolbarEnum.CHOOSEFROMFILEPOOL_EN.getIcon());
                    if (!writeable) {
                        toolBarInfo.setDisable(false);
                    }
                    toolbar.add(toolBarInfo);
                } else {
                    toolBarInfo = new ToolBarInfo(ToolbarEnum.CHOOSEFROMFILEPOOL_EN.getCode(), chooseFromFilepool, ToolbarEnum.CHOOSEFROMFILEPOOL_EN.getIcon());
                    if (!writeable) {
                        toolBarInfo.setDisable(false);
                    }
                    toolbar.add(toolBarInfo);
                }
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD_EN.getCode(), ToolbarEnum.DOWNLOAD_EN.getTitle(), ToolbarEnum.DOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETE_EN.getCode(), ToolbarEnum.DELETE_EN.getTitle(), ToolbarEnum.DELETE_EN.getIcon());
                if (nodeType.equals("fieldDefineNode") && !writeable) {
                    toolBarInfo.setDisable(false);
                }
                toolbar.add(toolBarInfo);
            }
        } else if ("dataentryActionEvent".equals(from)) {
            String language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD.getCode(), ToolbarEnum.DOWNLOAD.getTitle(), ToolbarEnum.DOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
            } else {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD_EN.getCode(), ToolbarEnum.DOWNLOAD_EN.getTitle(), ToolbarEnum.DOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
            }
        } else {
            String language = DataEntryUtil.getLanguage();
            if ("zh".equals(language)) {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD.getCode(), ToolbarEnum.DOWNLOAD.getTitle(), ToolbarEnum.DOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD.getIcon());
                toolbar.add(toolBarInfo);
                String identityId = NpContextHolder.getContext().getIdentityId();
                if (this.systemIdentityService.isSystemByUserId(identityId) && com.jiuqi.np.definition.common.StringUtils.isEmpty((String)from)) {
                    if ("00".equals(nodeKey)) {
                        toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETEHISTORICL.getCode(), ToolbarEnum.DELETEHISTORICL.getTitle(), ToolbarEnum.DELETEHISTORICL.getIcon());
                        toolbar.add(toolBarInfo);
                    } else if ("000".equals(nodeKey)) {
                        toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETEALL.getCode(), ToolbarEnum.DELETEALL.getTitle(), ToolbarEnum.DELETEALL.getIcon());
                        toolbar.add(toolBarInfo);
                    }
                }
            } else {
                ToolBarInfo toolBarInfo = new ToolBarInfo(ToolbarEnum.DOWNLOAD_EN.getCode(), ToolbarEnum.DOWNLOAD_EN.getTitle(), ToolbarEnum.DOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                toolBarInfo = new ToolBarInfo(ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getCode(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getTitle(), ToolbarEnum.BATCHATTACHMENTDOWNLOAD_EN.getIcon());
                toolbar.add(toolBarInfo);
                String identityId = NpContextHolder.getContext().getIdentityId();
                if (this.systemIdentityService.isSystemByUserId(identityId) && com.jiuqi.np.definition.common.StringUtils.isEmpty((String)from)) {
                    if ("00".equals(nodeKey)) {
                        toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETEHISTORICL_EN.getCode(), ToolbarEnum.DELETEHISTORICL_EN.getTitle(), ToolbarEnum.DELETEHISTORICL_EN.getIcon());
                        toolbar.add(toolBarInfo);
                    } else if ("000".equals(nodeKey)) {
                        toolBarInfo = new ToolBarInfo(ToolbarEnum.DELETEALL_EN.getCode(), ToolbarEnum.DELETEALL_EN.getTitle(), ToolbarEnum.DELETEALL_EN.getIcon());
                        toolbar.add(toolBarInfo);
                    }
                }
            }
        }
    }

    @Override
    public GridDataInfo loadGridPageData(IAttachmentGridDataPageContext context) {
        JtableContext jtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        GridDataInfo gridDataInfo = new GridDataInfo();
        if (this.judgeSecretLevel(jtableContext)) {
            boolean openFilepool = this.filePoolService.isOpenFilepool();
            if (!openFilepool) {
                if (context.getType().equals("allAttachmentNode")) {
                    gridDataInfo = this.pagingAllAttachmentNodes(context, jtableContext);
                } else if (context.getType().equals("formDefineNode")) {
                    gridDataInfo = this.pagingFormNode(context, jtableContext);
                } else if (context.getType().equals("fieldDefineNode")) {
                    this.pagingIndicatorNode(context, jtableContext, gridDataInfo);
                }
            } else if (context.getType().equals("allReferencedAttachmentNode")) {
                gridDataInfo = this.pagingAllReferAttachments(context, jtableContext);
            } else if (context.getType().equals("formDefineNode")) {
                gridDataInfo = this.pagingReferFormAttachments(context, jtableContext);
            } else if (context.getType().equals("fieldDefineNode")) {
                this.pagingReferIndicatorAttachments(context, jtableContext, gridDataInfo);
            } else if (context.getType().equals("UnreferencedAttachmentNode")) {
                this.pagingUnreferAttachments(context, jtableContext, gridDataInfo);
            }
        } else {
            gridDataInfo.setTotalSize(0);
            gridDataInfo.setPageSize(context.getPageSize());
            gridDataInfo.setCurrentPage(context.getCurrentPage());
            boolean openSecretLevel = this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
            List<ColumnsInfo> columns = this.getColumnsInfos(openSecretLevel);
            gridDataInfo.setColumns(columns);
            gridDataInfo.setRowDatas(null);
            gridDataInfo.setTotalSize(0);
        }
        return gridDataInfo;
    }

    private GridDataInfo pagingAllAttachmentNodes(IAttachmentGridDataPageContext context, JtableContext jtableContext) {
        GridDataInfo gridDataInfo = new GridDataInfo();
        AttachmentQueryInfo attachmentQueryInfo = new AttachmentQueryInfo();
        attachmentQueryInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryInfo.setDataSchemeKey(taskDefine.getDataScheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryInfo.setFormscheme(context.getFormscheme());
        attachmentQueryInfo.setPage(true);
        attachmentQueryInfo.setPageSize(context.getPageSize());
        attachmentQueryInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryInfo.setOrder(context.getOrder());
        attachmentQueryInfo.setSortBy(context.getSortBy());
        attachmentQueryInfo.setSearchInfo(context.getSearchInfo());
        RowDataValues rowDataValues = this.fileOperationService.searchFile(attachmentQueryInfo);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        return gridDataInfo;
    }

    private GridDataInfo pagingFormNode(IAttachmentGridDataPageContext context, JtableContext jtableContext) {
        GridDataInfo gridDataInfo = new GridDataInfo();
        AttachmentQueryInfo attachmentQueryInfo = new AttachmentQueryInfo();
        attachmentQueryInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryInfo.setDataSchemeKey(taskDefine.getDataScheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryInfo.setFormscheme(context.getFormscheme());
        attachmentQueryInfo.setFormKey(Arrays.asList(context.getKey()));
        attachmentQueryInfo.setPage(true);
        attachmentQueryInfo.setPageSize(context.getPageSize());
        attachmentQueryInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryInfo.setOrder(context.getOrder());
        attachmentQueryInfo.setSortBy(context.getSortBy());
        attachmentQueryInfo.setSearchInfo(context.getSearchInfo());
        RowDataValues rowDataValues = this.fileOperationService.searchFile(attachmentQueryInfo);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        return gridDataInfo;
    }

    private String pagingIndicatorNode(IAttachmentGridDataPageContext context, JtableContext jtableContext, GridDataInfo gridDataInfo) {
        AttachmentQueryByFieldInfo attachmentQueryByFieldInfo = new AttachmentQueryByFieldInfo();
        attachmentQueryByFieldInfo.setTask(context.getTask());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        attachmentQueryByFieldInfo.setDataSchemeKey(taskDefine.getDataScheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        attachmentQueryByFieldInfo.setDimensionCollection(dimensionValueCollection);
        attachmentQueryByFieldInfo.setFormscheme(context.getFormscheme());
        attachmentQueryByFieldInfo.setFormKey(context.getFormKey());
        attachmentQueryByFieldInfo.setFieldKey(context.getKey());
        attachmentQueryByFieldInfo.setPage(true);
        attachmentQueryByFieldInfo.setPageSize(context.getPageSize());
        attachmentQueryByFieldInfo.setCurrentPage(context.getCurrentPage());
        attachmentQueryByFieldInfo.setOrder(context.getOrder());
        attachmentQueryByFieldInfo.setSortBy(context.getSortBy());
        attachmentQueryByFieldInfo.setSearchInfo(context.getSearchInfo());
        attachmentQueryByFieldInfo.setGroupKey(context.getGroupKey());
        RowDataValues rowDataValues = this.fileOperationService.searchFileByField(attachmentQueryByFieldInfo);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        String duplicateFileKey = "";
        if (context.getFileName() != null) {
            for (RowDataInfo rowDataInfo : rowDataValues.getRowDatas()) {
                if (!context.getFileName().equals(rowDataInfo.getName())) continue;
                duplicateFileKey = rowDataInfo.getName();
                break;
            }
        }
        return duplicateFileKey;
    }

    private GridDataInfo pagingAllReferAttachments(IAttachmentGridDataPageContext context, JtableContext jtableContext) {
        GridDataInfo gridDataInfo = new GridDataInfo();
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFileTypeCode())) {
            searchContext.setTypes(FileTypeEnum.getFileTypeByCode(context.getFileTypeCode()));
        }
        searchContext.setCategory(context.getFileCategoryCode());
        searchContext.setSearchInfo(context.getSearchInfo());
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        return gridDataInfo;
    }

    private GridDataInfo pagingReferFormAttachments(IAttachmentGridDataPageContext context, JtableContext jtableContext) {
        GridDataInfo gridDataInfo = new GridDataInfo();
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getKey());
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFileTypeCode())) {
            searchContext.setTypes(FileTypeEnum.getFileTypeByCode(context.getFileTypeCode()));
        }
        searchContext.setCategory(context.getFileCategoryCode());
        searchContext.setSearchInfo(context.getSearchInfo());
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        return gridDataInfo;
    }

    private void pagingReferIndicatorAttachments(IAttachmentGridDataPageContext context, JtableContext jtableContext, GridDataInfo gridDataInfo) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getFormKey());
        searchContext.setFieldKey(context.getKey());
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFileTypeCode())) {
            searchContext.setTypes(FileTypeEnum.getFileTypeByCode(context.getFileTypeCode()));
        }
        searchContext.setCategory(context.getFileCategoryCode());
        searchContext.setSearchInfo(context.getSearchInfo());
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        searchContext.setGroupKey(context.getGroupKey());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
    }

    private void pagingUnreferAttachments(IAttachmentGridDataPageContext context, JtableContext jtableContext, GridDataInfo gridDataInfo) {
        SearchContext searchContext = new SearchContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        searchContext.setDataSchemeKey(taskDefine.getDataScheme());
        searchContext.setTaskKey(context.getTask());
        searchContext.setFormscheme(context.getFormscheme());
        DimensionCollection dimensionValueCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormscheme());
        searchContext.setDimensionCollection(dimensionValueCollection);
        searchContext.setFormKey(context.getFormKey());
        searchContext.setFieldKey(context.getKey());
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFileTypeCode())) {
            searchContext.setTypes(FileTypeEnum.getFileTypeByCode(context.getFileTypeCode()));
        }
        searchContext.setCategory(context.getFileCategoryCode());
        searchContext.setSearchInfo(context.getSearchInfo());
        searchContext.setNotReferences(true);
        searchContext.setPage(true);
        searchContext.setPageSize(context.getPageSize());
        searchContext.setCurrentPage(context.getCurrentPage());
        searchContext.setOrder(context.getOrder());
        searchContext.setSortBy(context.getSortBy());
        RowDataValues rowDataValues = this.filePoolService.search(searchContext);
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
    }

    private void constructGridDataInfo(IAttachmentGridDataPageContext context, JtableContext jtableContext, GridDataInfo gridDataInfo, RowDataValues rowDataValues) {
        boolean openSecretLevel = this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey());
        List<ColumnsInfo> columns = this.getColumnsInfos(openSecretLevel);
        gridDataInfo.setColumns(columns);
        gridDataInfo.setPageSize(context.getPageSize());
        gridDataInfo.setCurrentPage(context.getCurrentPage());
        if (rowDataValues.getTotalNumber() > 0) {
            gridDataInfo.setTotalSize(rowDataValues.getTotalNumber());
            ArrayList<RowDatasInfo> rowDatasInfos = new ArrayList<RowDatasInfo>();
            for (RowDataInfo rowData : rowDataValues.getRowDatas()) {
                RowDatasInfo rowDatasInfo = new RowDatasInfo();
                rowDatasInfo.setId(rowData.getFileKey());
                rowDatasInfo.setTitle(rowData.getName());
                rowDatasInfo.setCreatetime(null == rowData.getCreatetime() ? "" : RowDataUtil.dateToString(rowData.getCreatetime()));
                String sizeString = RowDataUtil.conversion(rowData.getSize());
                rowDatasInfo.setSize(sizeString);
                rowDatasInfo.setCreator(com.jiuqi.np.definition.common.StringUtils.isEmpty((String)rowData.getCreator()) ? "" : rowData.getCreator());
                rowDatasInfo.setConfidential(rowData.getConfidential());
                rowDatasInfo.setFileCategory(rowData.getCategory());
                rowDatasInfo.setSizeLong(rowData.getSize());
                rowDatasInfo.setWriteable(rowData.isWriteable());
                rowDatasInfo.setGroupKey(rowData.getGroupKey());
                rowDatasInfo.setDw(rowData.getDw());
                rowDatasInfo.setFormKey(rowData.getFormKey());
                rowDatasInfo.setIndex(rowData.getIndex());
                rowDatasInfo.setFilepool(rowData.isFilepool());
                rowDatasInfos.add(rowDatasInfo);
            }
            gridDataInfo.setRowDatas(rowDatasInfos);
        } else {
            gridDataInfo.setTotalSize(0);
            gridDataInfo.setRowDatas(null);
        }
        gridDataInfo.setTotalFileSize(RowDataUtil.conversion(rowDataValues.getTotalSize()));
    }

    @Override
    public GridDataInfo searchByFilename(IAttachmentGridDataPageContext context) {
        return this.loadGridPageData(context);
    }

    @Override
    public List<FormDataInfo> getFormData(IAttachmentContext context) {
        ArrayList<FormDataInfo> formDataInfos = new ArrayList<FormDataInfo>();
        JtableContext jtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        if (this.judgeSecretLevel(jtableContext)) {
            try {
                List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey());
                ArrayList<Consts.FormAccessLevel> formAccessLevels = new ArrayList<Consts.FormAccessLevel>();
                formAccessLevels.add(Consts.FormAccessLevel.FORM_READ);
                formAccessLevels.add(Consts.FormAccessLevel.FORM_DATA_WRITE);
                AuthorityJudgmentUtil authorityJudgmentUtil = new AuthorityJudgmentUtil(jtableContext, formDefineList, formAccessLevels);
                FormReadWriteAccessData formReadWriteAccessData = authorityJudgmentUtil.batchVisible(formDefineList.stream().map(formDefine -> formDefine.getKey()).collect(Collectors.toList()), jtableContext, this.accessProvider);
                formDefineList = this.iRunTimeViewController.queryFormsById(formReadWriteAccessData.getFormKeys());
                for (FormDefine formDefine2 : formDefineList) {
                    ArrayList<ITree<FileNode>> fieldDefineNodes = new ArrayList<ITree<FileNode>>();
                    JurisdictionResult writeableResult = new JurisdictionResult();
                    writeableResult.setHaveAccess(true);
                    this.getIndicatorNode(formDefine2, fieldDefineNodes, authorityJudgmentUtil, jtableContext, writeableResult, writeableResult, null);
                    if (fieldDefineNodes.isEmpty()) continue;
                    FormDataInfo formDataInfo = new FormDataInfo(formDefine2.getKey(), formDefine2.getTitle());
                    formDataInfos.add(formDataInfo);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return formDataInfos;
    }

    private JtableContext constructJtablecontext(String taskKey, String formSchemeKey, Map<String, DimensionValue> dimensionSet) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(taskKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setDimensionSet(dimensionSet);
        return jtableContext;
    }

    @Override
    public FilesUploadInfo uploadVerification(FilesUploadInfo filesUploadInfo) {
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)filesUploadInfo.getGroupKey())) {
            boolean flag = false;
            boolean openFilepool = this.filePoolService.isOpenFilepool();
            if (!openFilepool) {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(filesUploadInfo.getContext().getTaskKey());
                CommonParamsDTO params = new CommonParamsDTO();
                params.setDataSchemeKey(taskDefine.getDataScheme());
                params.setTaskKey(taskDefine.getKey());
                List fileInfoByGroup = this.filePoolService.getFileInfoByGroup(filesUploadInfo.getGroupKey(), params);
                Map<String, AttachmentInfo> fileUploadInfoMap = filesUploadInfo.getFileUploadInfoMap();
                for (FileInfo fileInfo : fileInfoByGroup) {
                    for (String fileName : fileUploadInfoMap.keySet()) {
                        if (!fileName.equals(fileInfo.getName())) continue;
                        flag = true;
                        AttachmentInfo attachmentInfo = fileUploadInfoMap.get(fileName);
                        attachmentInfo.setCovered(true);
                        attachmentInfo.setFileKey(fileInfo.getKey());
                    }
                }
                if (flag) {
                    filesUploadInfo.setCovered(true);
                    return filesUploadInfo;
                }
            } else {
                FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
                filePoolAllFileContext.setTaskKey(filesUploadInfo.getContext().getTaskKey());
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                Map dimensionSet = filesUploadInfo.getContext().getDimensionSet();
                for (String key : dimensionSet.keySet()) {
                    dimensionCombinationBuilder.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
                }
                DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
                filePoolAllFileContext.setDimensionCombination(dimensionCombination);
                List filePoolFiles = this.filePoolService.getFilePoolFiles(filePoolAllFileContext);
                Map<String, AttachmentInfo> fileUploadInfoMap = filesUploadInfo.getFileUploadInfoMap();
                for (String fileName : fileUploadInfoMap.keySet()) {
                    AttachmentInfo attachmentInfo = fileUploadInfoMap.get(fileName);
                    ObjectInfo info = this.fileUploadOssService.getInfo(attachmentInfo.getOssFileKey());
                    for (FilePoolFiles filePoolFile : filePoolFiles) {
                        if (filePoolFile.getFileName().equals(info.getName()) && (filePoolFile.getSize() != info.getSize() || !filePoolFile.getMd5().equals(info.getMd5()))) {
                            flag = true;
                            attachmentInfo.setCovered(true);
                            attachmentInfo.setFileKey(filePoolFile.getFileKey());
                            filesUploadInfo.setCovered(true);
                            boolean rs = this.filePoolService.judgeFileOverwritten(filePoolFile.getFileKey(), filesUploadInfo.getContext().getTaskKey());
                            if (rs) continue;
                            attachmentInfo.setUploadType(-2);
                            continue;
                        }
                        if (filePoolFile.getFileName().equals(info.getName()) && filePoolFile.getSize() == info.getSize() && filePoolFile.getMd5().equals(info.getMd5()) && (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)attachmentInfo.getFileSecret()) || com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileSecret()) && attachmentInfo.getFileSecret().equals(filePoolFile.getSecretCode())) && (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)attachmentInfo.getFileCategory()) || com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileCategory()) && attachmentInfo.getFileCategory().equals(filePoolFile.getCategoryCode())) && 2 == attachmentInfo.getUploadType()) {
                            flag = true;
                            attachmentInfo.setFileKey(filePoolFile.getFileKey());
                            attachmentInfo.setUploadType(6);
                            continue;
                        }
                        if (filePoolFile.getFileName().equals(info.getName()) && filePoolFile.getSize() == info.getSize() && filePoolFile.getMd5().equals(info.getMd5()) && (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)attachmentInfo.getFileSecret()) || com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileSecret()) && attachmentInfo.getFileSecret().equals(filePoolFile.getSecretCode())) && (com.jiuqi.np.definition.common.StringUtils.isEmpty((String)attachmentInfo.getFileCategory()) || com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileCategory()) && attachmentInfo.getFileCategory().equals(filePoolFile.getCategoryCode())) && 3 == attachmentInfo.getUploadType()) {
                            flag = true;
                            attachmentInfo.setFileKey(filePoolFile.getFileKey());
                            attachmentInfo.setUploadType(7);
                            continue;
                        }
                        if (!filePoolFile.getFileName().equals(info.getName()) || filePoolFile.getSize() != info.getSize() || !filePoolFile.getMd5().equals(info.getMd5()) || (!com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileSecret()) || attachmentInfo.getFileSecret().equals(filePoolFile.getSecretCode())) && (!com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)attachmentInfo.getFileCategory()) || attachmentInfo.getFileCategory().equals(filePoolFile.getCategoryCode())) || 2 != attachmentInfo.getUploadType() && 3 != attachmentInfo.getUploadType()) continue;
                        flag = true;
                        attachmentInfo.setFileKey(filePoolFile.getFileKey());
                        filesUploadInfo.setCovered(true);
                        attachmentInfo.setUploadType(5);
                    }
                }
                if (flag) {
                    return filesUploadInfo;
                }
            }
        }
        return null;
    }

    @Override
    public List<ReferencesInfo> getAttachmentReferences(AttachmentReferencesContext context) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        return this.filePoolService.getReferences(context.getFileKey(), taskDefine.getDataScheme());
    }

    @Override
    public GridDataInfo getFilePoolAttachment(FilePoolAtachmentContext context) {
        FilePoolContext filePoolContext = new FilePoolContext();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask());
        filePoolContext.setDataSchemeKey(taskDefine.getDataScheme());
        filePoolContext.setTaskKey(context.getTask());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        filePoolContext.setDimensionCombination(dimensionCombination);
        if (com.jiuqi.np.definition.common.StringUtils.isNotEmpty((String)context.getFileTypeCode())) {
            filePoolContext.setTypes(FileTypeEnum.getFileTypeByCode(context.getFileTypeCode()));
        }
        filePoolContext.setCategory(context.getFileCategoryCode());
        filePoolContext.setSearchInfo(context.getSearchInfo());
        filePoolContext.setNotReferences(context.isNotReferences());
        filePoolContext.setPage(true);
        filePoolContext.setPageSize(context.getPageSize());
        filePoolContext.setCurrentPage(context.getCurrentPage());
        filePoolContext.setOrder(context.getOrder());
        filePoolContext.setSortBy(context.getSortBy());
        filePoolContext.setGroupKey(context.getGroupKey());
        RowDataValues rowDataValues = this.filePoolService.getFileInfoByFilePool(filePoolContext);
        JtableContext jtableContext = this.constructJtablecontext(context.getTask(), context.getFormscheme(), context.getDimensionSet());
        GridDataInfo gridDataInfo = new GridDataInfo();
        this.constructGridDataInfo(context, jtableContext, gridDataInfo, rowDataValues);
        return gridDataInfo;
    }

    @Override
    public List<FilePoolFiles> getAllFilePoolFiles(IAttachmentGridDataPageContext context) {
        FilePoolAllFileContext filePoolAllFileContext = new FilePoolAllFileContext();
        filePoolAllFileContext.setTaskKey(context.getTask());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        filePoolAllFileContext.setDimensionCombination(dimensionCombination);
        return this.filePoolService.getFilePoolFiles(filePoolAllFileContext);
    }

    @Override
    public List<FileInfo> getFileInfoByGroupKey(String taskKey, String groupKey) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(taskDefine.getDataScheme());
        params.setTaskKey(taskDefine.getKey());
        return this.fileOperationService.getFileInfoByGroup(groupKey, params);
    }

    @Override
    public FileDetails loadDetails(FileGridDataContext fileGridDataContext) {
        FileDetails fileDetails = new FileDetails();
        GridDataInfo gridData = new GridDataInfo();
        gridData.setPageSize(fileGridDataContext.getPageSize());
        gridData.setCurrentPage(fileGridDataContext.getCurrentPage());
        List<ColumnsInfo> columns = this.getColumnsInfos();
        gridData.setColumns(columns);
        this.getFiles(fileGridDataContext, fileDetails, gridData);
        return fileDetails;
    }

    @NotNull
    private List<ColumnsInfo> getColumnsInfos() {
        ArrayList<ColumnsInfo> columns = new ArrayList<ColumnsInfo>();
        String language = DataEntryUtil.getLanguage();
        if ("zh".equals(language)) {
            ColumnsInfo columnsInfo = new ColumnsInfo(ColumnsEnum.INDEX.getKey(), ColumnsEnum.INDEX.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.TITLE.getKey(), ColumnsEnum.TITLE.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.SIZE.getKey(), ColumnsEnum.SIZE.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATOR.getKey(), ColumnsEnum.CREATOR.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATETIME.getKey(), ColumnsEnum.CREATETIME.getTitle());
            columns.add(columnsInfo);
        } else {
            ColumnsInfo columnsInfo = new ColumnsInfo(ColumnsEnum.INDEX_EN.getKey(), ColumnsEnum.INDEX_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.TITLE_EN.getKey(), ColumnsEnum.TITLE_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.SIZE_EN.getKey(), ColumnsEnum.SIZE_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATOR_EN.getKey(), ColumnsEnum.CREATOR_EN.getTitle());
            columns.add(columnsInfo);
            columnsInfo = new ColumnsInfo(ColumnsEnum.CREATETIME_EN.getKey(), ColumnsEnum.CREATETIME_EN.getTitle());
            columns.add(columnsInfo);
        }
        return columns;
    }

    private void getFiles(FileGridDataContext fileGridDataContext, FileDetails fileDetails, GridDataInfo gridData) {
        ArrayList<FileInfo> allFileInfos = new ArrayList<FileInfo>();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(fileGridDataContext.getDataSchemeCode());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataScheme.getKey());
        List fileInfos = this.filePoolService.getFileInfoByGroup(fileGridDataContext.getGroupKey(), params);
        if (null != fileInfos && !fileInfos.isEmpty()) {
            allFileInfos.addAll(fileInfos);
        }
        if (!allFileInfos.isEmpty()) {
            RowDataValues rowDataValues = this.searchFile(fileGridDataContext, allFileInfos);
            this.collateResult(fileDetails, gridData, rowDataValues);
        }
    }

    private void collateResult(FileDetails fileDetails, GridDataInfo gridData, RowDataValues rowDataValues) {
        ArrayList<RowDatasInfo> rowDatasInfos = new ArrayList<RowDatasInfo>();
        for (RowDataInfo rowData : rowDataValues.getRowDatas()) {
            RowDatasInfo rowDatasInfo = new RowDatasInfo();
            rowDatasInfo.setId(rowData.getFileKey());
            rowDatasInfo.setTitle(rowData.getName());
            rowDatasInfo.setCreatetime(null == rowData.getCreatetime() ? "" : RowDataUtil.dateToString(rowData.getCreatetime()));
            String sizeString = RowDataUtil.conversion(rowData.getSize());
            rowDatasInfo.setSize(sizeString);
            rowDatasInfo.setCreator(com.jiuqi.np.definition.common.StringUtils.isEmpty((String)rowData.getCreator()) ? "" : rowData.getCreator());
            rowDatasInfo.setConfidential(rowData.getConfidential());
            rowDatasInfo.setSizeLong(rowData.getSize());
            rowDatasInfo.setWriteable(rowData.isWriteable());
            rowDatasInfo.setGroupKey(rowData.getGroupKey());
            rowDatasInfo.setIndex(rowData.getIndex());
            rowDatasInfos.add(rowDatasInfo);
        }
        FileCountInfo fileCountInfo = new FileCountInfo();
        this.getTheTotalInformationOfAttachments(fileCountInfo, gridData, rowDatasInfos, rowDataValues.getTotalNumber(), rowDataValues.getTotalSize());
        fileDetails.setFileCountInfo(fileCountInfo);
        fileDetails.setGridData(gridData);
    }

    private RowDataValues searchFile(FileGridDataContext fileGridDataContext, List<FileInfo> fileInfos) {
        RowDataValues rowDataValues = new RowDataValues();
        ArrayList<RowDataInfo> rowDataInfos = new ArrayList<RowDataInfo>();
        for (FileInfo fileInfo : fileInfos) {
            RowDataInfo rowDataInfo = new RowDataInfo();
            rowDataInfo.setFileKey(fileInfo.getKey());
            rowDataInfo.setName(fileInfo.getName());
            rowDataInfo.setCreatetime(fileInfo.getCreateTime());
            rowDataInfo.setSize(fileInfo.getSize());
            rowDataInfo.setCreator(fileInfo.getCreater());
            rowDataInfo.setWriteable(false);
            rowDataInfos.add(rowDataInfo);
        }
        this.filterFiles(rowDataValues, rowDataInfos, fileGridDataContext.getSearchInfo(), fileGridDataContext.getOrder(), fileGridDataContext.getSortBy(), fileGridDataContext.getCurrentPage(), fileGridDataContext.getPageSize());
        return rowDataValues;
    }

    private void filterFiles(RowDataValues rowDataValues, List<RowDataInfo> rowDataInfos, String searchInfo, String order, String sortBy, Integer currentPage, Integer pageSize) {
        if (StringUtils.isNotEmpty((String)searchInfo)) {
            ArrayList<RowDataInfo> rowDatasCopy = new ArrayList<RowDataInfo>();
            for (RowDataInfo rowDataInfo : rowDataInfos) {
                if (rowDataInfo.getName().indexOf(searchInfo) == -1) continue;
                rowDatasCopy.add(rowDataInfo);
            }
            rowDataInfos.clear();
            rowDataInfos.addAll(rowDatasCopy);
        }
        FileOperationUtils.sortFiles((String)order, (String)sortBy, rowDataInfos);
        long totalSize = 0L;
        for (RowDataInfo rowDataInfo : rowDataInfos) {
            rowDataInfo.setIndex(rowDataInfos.indexOf(rowDataInfo) + 1);
            totalSize += rowDataInfo.getSize();
        }
        int totalNumber = rowDataInfos.size();
        if (totalNumber > 0) {
            rowDataInfos = totalNumber >= currentPage * pageSize ? rowDataInfos.subList((currentPage - 1) * pageSize, currentPage * pageSize) : (totalNumber == (currentPage - 1) * pageSize ? (currentPage == 1 ? rowDataInfos.subList((currentPage - 1) * pageSize, totalNumber) : rowDataInfos.subList((currentPage - 2) * pageSize, totalNumber)) : rowDataInfos.subList((currentPage - 1) * pageSize, totalNumber));
        }
        rowDataValues.setRowDatas(rowDataInfos);
        rowDataValues.setTotalNumber(Integer.valueOf(totalNumber));
        rowDataValues.setTotalSize(totalSize);
    }

    @Override
    public int queryFileCount(QueryFileInfoParam param) {
        return this.queryFileList(param).size();
    }

    @Override
    public List<FileInfo> queryFileList(QueryFileInfoParam param) {
        try {
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(param.getFormScheme());
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), param.getFormScheme());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(param.getDimensionSet());
            DimensionCombination dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, param.getFormScheme());
            IAccessResult readable = dataAccessService.readable(dimensionCombination, param.getFormKey());
            if (!readable.haveAccess()) {
                return Collections.emptyList();
            }
            List groupKeys = this.fileOperationService.getGroupKeys(param.getFormScheme(), dimensionValueSet, param.getFormKey(), param.getFieldKey());
            CommonParamsDTO params = new CommonParamsDTO();
            params.setDataSchemeKey(taskDefine.getDataScheme());
            List fileInfoByGroup = this.filePoolService.getFileInfoByGroup(groupKeys, params);
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            fileInfoByGroup.forEach(t -> fileInfos.addAll(t.getFileInfos()));
            return fileInfos;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}


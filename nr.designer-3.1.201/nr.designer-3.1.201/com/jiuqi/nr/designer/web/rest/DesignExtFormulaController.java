/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IExtFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IExtFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployPrivateFormulaService
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFCell
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFFont
 *  org.apache.poi.xssf.usermodel.XSSFRichTextString
 *  org.apache.poi.xssf.usermodel.XSSFRow
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IExtFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IExtFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployPrivateFormulaService;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter;
import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.web.facade.ExtNode;
import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.facade.PrivateFormulaEntityTree;
import com.jiuqi.nr.designer.web.facade.PrivateUserInfo;
import com.jiuqi.nr.designer.web.facade.UnitSelectorObj;
import com.jiuqi.nr.designer.web.rest.vo.FormulaDesCreateParam;
import com.jiuqi.nr.designer.web.service.FormulaMonitor;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class DesignExtFormulaController {
    private static final Logger logger = LoggerFactory.getLogger(DesignExtFormulaController.class);
    @Autowired
    private IExtFormulaDesignTimeController iExtFormulaDesignTimeController;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DeployPrivateFormulaService deployPrivateFormulaService;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IFormulaDesignTimeController iFormulaDesignTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private IExtFormulaRunTimeController iExtFormulaRunTimeController;
    @Autowired
    private IFormulaUnitGroupGetter iFormulaUnitGroupGetter;
    @Autowired
    private IDataDefinitionDesignTimeController npController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private OrgIdentityService orgIdentityService;
    private static String FORMULA_CODE_REPEAT = "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d!";
    private static String FORMULA_BIAOJIAN = "number_formulas";
    private static String FORMULA_TYPE_NULL = "\u516c\u5f0f\u7c7b\u578b\u4e3a\u7a7a\uff01";
    private static final String XLS = "XLS";
    private static final String XLSX = "XLSX";
    private static final String BJFORMULA = "\u8868\u95f4\u516c\u5f0f";
    private static final String REPEATCODEINFO = "\u91cd\u590d\u7f16\u53f7\u4fe1\u606f";
    private static final String NUMBER = "\u7f16\u53f7";
    private static final String EXPRESSION = "\u8868\u8fbe\u5f0f";
    private static final String DESCRIPTION = "\u8bf4\u660e";
    private static final String AUDITTYPE = "\u5ba1\u6838\u7c7b\u578b";
    private static final String OWNERLEVELANDID = "ownerLevelAndIds";
    private static final String PARENTFORMULAS = "parentFormula";
    private static final String CODENORMAL = "-";

    @ApiOperation(value="\u662f\u5426\u5f00\u542f\u79c1\u6709/\u81ea\u5b9a\u516c\u5f0f")
    @GetMapping(value={"existPrivateFormula"})
    @ResponseBody
    public boolean existPrivateFormula() throws Exception {
        return this.iExtFormulaDesignTimeController.existPrivateFormula();
    }

    @ApiOperation(value="\u79c1\u6709\u516c\u5f0f\u4e2d\u5355\u4f4d\u9009\u62e9\u5668\u53c2\u6570")
    @PostMapping(value={"getUnitSelectorParam"})
    @ResponseBody
    public PrivateFormulaEntityTree getUnitTreeParam(@RequestBody UnitSelectorObj unitSelectorObj) throws Exception {
        DesignTaskDefine designTaskDefine;
        DesignFormSchemeDefine formSchemeDefine;
        if (null == unitSelectorObj) {
            return null;
        }
        String org = null;
        if ("designer".equals(unitSelectorObj.getRunType())) {
            if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormSchemeKey()) && null != (formSchemeDefine = this.iDesignTimeViewController.queryFormSchemeDefine(unitSelectorObj.getFormSchemeKey())) && null != (designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
                org = designTaskDefine.getDw();
            }
        } else if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormSchemeKey()) && null != (formSchemeDefine = this.iRunTimeViewController.getFormScheme(unitSelectorObj.getFormSchemeKey())) && null != (designTaskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
            org = designTaskDefine.getDw();
        }
        if (!org.springframework.util.StringUtils.hasText(org)) {
            return null;
        }
        RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
        viewDefine.setEntityId(org);
        String userName = NpContextHolder.getContext().getUserName();
        ArrayList<ExtNode> resTree = new ArrayList<ExtNode>();
        PrivateFormulaEntityTree resObj = new PrivateFormulaEntityTree();
        if (this.systemIdentityService.isAdmin()) {
            resObj.setCurrOrg("");
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
            ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
            contextChildren.setPeriodView(org);
            iEntityQuery.sorted(true);
            IEntityTable iEntityTable = iEntityQuery.executeFullBuild((IContext)contextChildren);
            List childRows = iEntityTable.getRootRows();
            if (null != childRows && childRows.size() != 0) {
                for (IEntityRow childRow : childRows) {
                    List childRows1 = iEntityTable.getChildRows(childRow.getCode());
                    if (childRows1.size() != 0) {
                        this.initTree(resTree, "", childRow, "", false);
                        for (IEntityRow childRow1 : childRows1) {
                            List childRows2 = iEntityTable.getChildRows(childRow1.getCode());
                            if (childRows2.size() != 0) {
                                this.initTree(resTree, childRow.getCode(), childRow1, "", false);
                                continue;
                            }
                            this.initTree(resTree, childRow.getCode(), childRow1, "", true);
                        }
                        continue;
                    }
                    this.initTree(resTree, "", childRow, "", true);
                }
                resObj.setCurrOrg(((IEntityRow)childRows.get(0)).getCode());
                resObj.setCurrOrgTitle(((IEntityRow)childRows.get(0)).getTitle());
            }
        } else {
            final String code = NpContextHolder.getContext().getOrganization().getCode();
            if (null == code) {
                return null;
            }
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
            iEntityQuery.sorted(true);
            ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
            context.setPeriodView(org);
            IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
            IEntityRow row = iEntityTable.findByEntityKey(code);
            if (row == null) {
                return null;
            }
            resObj.setCurrOrg(row.getCode());
            resObj.setCurrOrgTitle(NpContextHolder.getContext().getOrganization().getName());
            String lastParent = PARENTFORMULAS;
            String[] parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath();
            if (null != parentsEntityKeyDataPath && parentsEntityKeyDataPath.length != 0) {
                HashSet<String> set = new HashSet<String>();
                for (String s : parentsEntityKeyDataPath) {
                    set.add(s);
                }
                Map byEntityKeys = iEntityTable.findByEntityKeys(set);
                if (null != byEntityKeys && byEntityKeys.size() != 0) {
                    resTree.add(this.createParentTreeNode(lastParent, "\u4e0a\u7ea7\u516c\u5f0f"));
                }
            }
            IEntityQuery iEntityQueryChildren = this.entityDataService.newEntityQuery();
            viewDefine.setFilterRowByAuthority(true);
            iEntityQueryChildren.setEntityView((EntityViewDefine)viewDefine);
            iEntityQueryChildren.sorted(true);
            iEntityQueryChildren.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
            contextChildren.setPeriodView(org);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey((List)new ArrayList<String>(){
                {
                    this.add(code);
                }
            });
            IEntityTable iEntityTableChildren = iEntityQueryChildren.executeRangeBuild((IContext)contextChildren, (RangeQuery)treeRangeQuery);
            IEntityRow byCode = iEntityTableChildren.findByEntityKey(code);
            List childRows = iEntityTableChildren.getChildRows(code);
            this.initTree(resTree, "", byCode, byCode.getCode(), childRows.size() == 0);
            if (null != childRows && childRows.size() != 0) {
                Collection grantedCode = this.orgIdentityService.getGrantedOrg(NpContextHolder.getContext().getUserId());
                Collection<String> grantedOrg = this.codeToOrgCode(iEntityTableChildren, grantedCode);
                if (null != grantedOrg && grantedOrg.size() != 0) {
                    this.recursionIEntityRows(resTree, childRows, byCode.getCode(), iEntityTableChildren, grantedOrg);
                } else {
                    this.recursionIEntityRows(resTree, childRows, byCode.getCode(), iEntityTableChildren);
                }
            }
        }
        resObj.setTreeObjs(resTree);
        return resObj;
    }

    @ApiOperation(value="\u79c1\u6709\u516c\u5f0f\u4e2d\u5355\u4f4d\u9009\u62e9\u5668\u5b50\u8282\u70b9\u67e5\u8be2")
    @PostMapping(value={"getUnitSelectorChildrenParam"})
    @ResponseBody
    public List<ExtNode> getUnitChildrenTreeParam(final @RequestBody UnitSelectorObj unitSelectorObj) throws Exception {
        ArrayList<ExtNode> resTree;
        block12: {
            DesignTaskDefine designTaskDefine;
            DesignFormSchemeDefine formSchemeDefine;
            if (null == unitSelectorObj) {
                return null;
            }
            String org = null;
            if ("designer".equals(unitSelectorObj.getRunType())) {
                if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormSchemeKey()) && null != (formSchemeDefine = this.iDesignTimeViewController.queryFormSchemeDefine(unitSelectorObj.getFormSchemeKey())) && null != (designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
                    org = designTaskDefine.getDw();
                }
            } else if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormSchemeKey()) && null != (formSchemeDefine = this.iRunTimeViewController.getFormScheme(unitSelectorObj.getFormSchemeKey())) && null != (designTaskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
                org = designTaskDefine.getDw();
            }
            if (!org.springframework.util.StringUtils.hasText(org)) {
                return null;
            }
            resTree = new ArrayList<ExtNode>();
            RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
            viewDefine.setEntityId(org);
            IEntityQuery iEntityQueryChildren = this.entityDataService.newEntityQuery();
            viewDefine.setFilterRowByAuthority(true);
            iEntityQueryChildren.setEntityView((EntityViewDefine)viewDefine);
            iEntityQueryChildren.sorted(true);
            iEntityQueryChildren.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
            contextChildren.setPeriodView(org);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey((List)new ArrayList<String>(){
                {
                    this.add(unitSelectorObj.getUnit());
                }
            });
            IEntityTable iEntityTableChildren = iEntityQueryChildren.executeRangeBuild((IContext)contextChildren, (RangeQuery)treeRangeQuery);
            List childRows = iEntityTableChildren.getChildRows(unitSelectorObj.getUnit());
            if (null == childRows || childRows.size() == 0) break block12;
            Collection grantedCode = this.orgIdentityService.getGrantedOrg(NpContextHolder.getContext().getUserId());
            Collection<String> grantedOrg = this.codeToOrgCode(iEntityTableChildren, grantedCode);
            if (null != grantedOrg && grantedOrg.size() != 0) {
                for (IEntityRow childRow : childRows) {
                    if (grantedOrg.contains(childRow.getCode())) continue;
                    List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
                    if (childRows1.size() != 0) {
                        resTree.add(this.transTreeObj(childRow, unitSelectorObj.getUnit(), "", false));
                        continue;
                    }
                    resTree.add(this.transTreeObj(childRow, unitSelectorObj.getUnit(), "", true));
                }
            } else {
                for (IEntityRow childRow : childRows) {
                    List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
                    if (childRows1.size() != 0) {
                        resTree.add(this.transTreeObj(childRow, unitSelectorObj.getUnit(), "", false));
                        continue;
                    }
                    resTree.add(this.transTreeObj(childRow, unitSelectorObj.getUnit(), "", true));
                }
            }
        }
        return resTree;
    }

    private Collection<String> codeToOrgCode(IEntityTable iEntityTable, Collection<String> grantedOrg) {
        ArrayList<String> collection = new ArrayList<String>();
        for (String s : grantedOrg) {
            IEntityRow byEntityKey = iEntityTable.findByEntityKey(s);
            if (null == byEntityKey) continue;
            collection.add(byEntityKey.getCode());
        }
        return collection;
    }

    @ApiOperation(value="\u6839\u636e\u5355\u4f4d/\u516c\u5f0f\u65b9\u6848/\u62a5\u8868\u67e5\u8be2\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"getPrivateFormula"})
    @ResponseBody
    public List<FormulaObj> getPrivateFormula(@RequestBody UnitSelectorObj unitSelectorObj) throws Exception {
        if (!(org.springframework.util.StringUtils.hasText(unitSelectorObj.getFormulaSchemeKey()) && org.springframework.util.StringUtils.hasText(unitSelectorObj.getCurrentFormId()) && org.springframework.util.StringUtils.hasText(unitSelectorObj.getUnit()))) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0100);
        }
        List formulaByUnit = new ArrayList();
        if (PARENTFORMULAS.equals(unitSelectorObj.getUnit())) {
            String userName = NpContextHolder.getContext().getUserName();
            if (!this.systemIdentityService.isAdmin()) {
                DesignTaskDefine designTaskDefine;
                String orgId = NpContextHolder.getContext().getOrganization().getCode();
                String dw = "";
                DesignFormulaSchemeDefine designFormulaSchemeDefine = this.iFormulaDesignTimeController.queryFormulaSchemeDefine(unitSelectorObj.getFormulaSchemeKey());
                DesignFormSchemeDefine formSchemeDefine = this.iDesignTimeViewController.queryFormSchemeDefine(designFormulaSchemeDefine.getFormSchemeKey());
                if (null != formSchemeDefine && null != (designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
                    dw = designTaskDefine.getDw();
                }
                if (org.springframework.util.StringUtils.hasLength(orgId) && org.springframework.util.StringUtils.hasLength(dw)) {
                    RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
                    viewDefine.setEntityId(dw);
                    IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                    iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
                    ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
                    context.setPeriodView(dw);
                    IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
                    IEntityRow row = iEntityTable.findByEntityKey(orgId);
                    if (row != null) {
                        String[] parentsEntityKeyDataPath;
                        for (String parentUnit : parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath()) {
                            String formKey = FORMULA_BIAOJIAN.equals(unitSelectorObj.getCurrentFormId()) ? null : unitSelectorObj.getCurrentFormId();
                            List parentFormulas = this.iExtFormulaDesignTimeController.getFormulaByUnit(unitSelectorObj.getFormulaSchemeKey(), formKey, parentUnit);
                            if (parentFormulas.size() == 0) continue;
                            formulaByUnit.addAll(parentFormulas);
                        }
                    }
                }
            }
        } else {
            String formKey = FORMULA_BIAOJIAN.equals(unitSelectorObj.getCurrentFormId()) ? null : unitSelectorObj.getCurrentFormId();
            formulaByUnit = this.iExtFormulaDesignTimeController.getFormulaByUnit(unitSelectorObj.getFormulaSchemeKey(), formKey, unitSelectorObj.getUnit());
        }
        return formulaByUnit.stream().map(define -> this.initFormulaObj((DesignFormulaDefine)define)).collect(Collectors.toList());
    }

    @ApiOperation(value="\u4fdd\u5b58\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"savePrivateFormulas"})
    @ResponseBody
    public void savePrivateFormula(@RequestBody UnitSelectorObj unitSelectorObj) throws JQException {
        try {
            this.saveFormulas(unitSelectorObj);
            this.publishFormula(unitSelectorObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0101, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"savePrivateFormulas_rsa"})
    @ResponseBody
    public void savePrivateFormulaRsa(@RequestBody @SFDecrypt UnitSelectorObj unitSelectorObj) throws JQException {
        this.savePrivateFormula(unitSelectorObj);
    }

    private void publishFormula(UnitSelectorObj unitSelectorObj) throws Exception {
        FormulaSchemeDefine formulaSchemeDefine;
        if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormulaSchemeKey()) && org.springframework.util.StringUtils.hasLength(unitSelectorObj.getUnit()) && null != (formulaSchemeDefine = this.iFormulaRunTimeController.queryFormulaSchemeDefine(unitSelectorObj.getFormulaSchemeKey()))) {
            this.deployPrivateFormulaService.deploy(unitSelectorObj.getFormulaSchemeKey(), unitSelectorObj.getCurrentFormId(), unitSelectorObj.getUnit());
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveFormulas(UnitSelectorObj unitSelectorObj) throws Exception {
        FormulaObj[] formulas = unitSelectorObj.getFormulaObjs();
        if (formulas == null || !org.springframework.util.StringUtils.hasText(unitSelectorObj.getUnit())) {
            return;
        }
        ArrayList<DesignFormulaDefine> needCreateFormula = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> needCreateFormulaCode = new ArrayList<String>();
        ArrayList<DesignFormulaDefine> needUpdateFormula = new ArrayList<DesignFormulaDefine>();
        ArrayList<DesignFormulaDefine> needDeleteFormulas = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> needUpdateFormulaCode = new ArrayList<String>();
        ArrayList<String> needDeleteFormula = new ArrayList<String>();
        ArrayList<String> needDeleteFormulaCode = new ArrayList<String>();
        HashSet<String> formSet = new HashSet<String>();
        HashMap<String, DesignFormulaDefine> formulaMap = new HashMap<String, DesignFormulaDefine>();
        for (FormulaObj formulaObj : formulas) {
            if (formulaObj == null) continue;
            boolean taskCanEdit = this.taskPlanPublishExternalService.formulaSchemeCanEdit(formulaObj.getSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String formulaID = formulaObj.getId();
            if (formulaObj.isIsDeleted()) {
                if (formulaID == null) continue;
                needDeleteFormula.add(formulaID);
                needDeleteFormulaCode.add(formulaObj.getCode());
                DesignFormulaDefine deleteTemp = this.iExtFormulaDesignTimeController.createFormulaDefine();
                deleteTemp.setKey(formulaID);
                deleteTemp.setCode(formulaObj.getCode());
                needDeleteFormulas.add(deleteTemp);
                continue;
            }
            if (formulaObj.isIsNew()) {
                DesignFormulaDefine designFormulaDefine = this.iExtFormulaDesignTimeController.createFormulaDefine();
                designFormulaDefine.setKey(formulaID);
                this.initFormulaDefine(designFormulaDefine, formulaObj, unitSelectorObj);
                needCreateFormula.add(designFormulaDefine);
                needCreateFormulaCode.add(designFormulaDefine.getCode());
                continue;
            }
            if (!formulaObj.isIsDirty()) continue;
            String formKey = org.springframework.util.StringUtils.hasLength(formulaObj.getFormKey()) ? formulaObj.getFormKey() : unitSelectorObj.getCurrentFormId();
            String string = formKey = formKey == null ? "BJ" : formKey;
            if (formSet.add(formKey)) {
                if (formKey.equals("BJ") || formKey.equals(FORMULA_BIAOJIAN)) {
                    formKey = null;
                }
                List formulasInForm = this.iExtFormulaDesignTimeController.getFormulaByUnit(unitSelectorObj.getFormulaSchemeKey(), formKey, unitSelectorObj.getUnit());
                for (DesignFormulaDefine formula : formulasInForm) {
                    formulaMap.put(formula.getKey(), formula);
                }
            }
            DesignFormulaDefine designFormulaDefine = (DesignFormulaDefine)formulaMap.get(formulaObj.getId());
            this.initFormulaDefine(designFormulaDefine, formulaObj, unitSelectorObj);
            designFormulaDefine.setUpdateTime(new Date());
            needUpdateFormula.add(designFormulaDefine);
            needUpdateFormulaCode.add(designFormulaDefine.getCode());
        }
        if (needDeleteFormula.size() > 0) {
            this.iExtFormulaDesignTimeController.deleteFormulaDefines(needDeleteFormula.toArray(new String[0]));
        }
        List<String> checkFormulaCode = this.checkFormulaCode(needCreateFormula, needUpdateFormula);
        if (needDeleteFormula.size() > 0) {
            String logTitle = "\u5220\u9664\u516c\u5f0f";
            if (checkFormulaCode.size() > 0) {
                NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needDeleteFormulas), NrDesignLogHelper.LOGLEVEL_ERROR);
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_023, checkFormulaCode);
            }
            NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needDeleteFormulas), NrDesignLogHelper.LOGLEVEL_INFO);
        }
        if (checkFormulaCode.size() > 0) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_023, checkFormulaCode);
        }
        if (needUpdateFormula.size() > 0) {
            String logTitle = "\u4fee\u6539\u516c\u5f0f";
            try {
                this.iExtFormulaDesignTimeController.updateFormulaDefines(needUpdateFormula.toArray(new DesignFormulaDefine[0]));
                NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needUpdateFormula), NrDesignLogHelper.LOGLEVEL_INFO);
            }
            catch (JQException e) {
                NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needUpdateFormula), NrDesignLogHelper.LOGLEVEL_ERROR);
                throw e;
            }
        }
        if (needCreateFormula.size() > 0) {
            String logTitle = "\u65b0\u589e\u516c\u5f0f";
            try {
                this.iExtFormulaDesignTimeController.insertFormulaDefines(needCreateFormula.toArray(new DesignFormulaDefine[0]));
                NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needCreateFormula), NrDesignLogHelper.LOGLEVEL_INFO);
            }
            catch (JQException e) {
                NrDesignLogHelper.log(logTitle, this.calcFormulaLog(needCreateFormula), NrDesignLogHelper.LOGLEVEL_ERROR);
                throw e;
            }
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u79c1\u6709\u516c\u5f0f\u7f16\u53f7")
    @RequestMapping(value={"private_formula_code_check"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> formulaCodeCheck(@RequestBody FormulaObj formula) throws JQException {
        FormulaCheckObj formuCodeCheck = new FormulaCheckObj();
        ArrayList<FormulaCheckObj> formuCodeChecks = new ArrayList<FormulaCheckObj>();
        if (formula.getCode() != null && formula.getFormKey() != null && formula.getSchemeKey() != null) {
            ArrayList<DesignFormulaDefine> findFormulaDefineInFormulaSchemes = new ArrayList<DesignFormulaDefine>();
            if (formula.getFormKey().equals(FORMULA_BIAOJIAN)) {
                DesignFormulaDefine formulaDefineInFormulaScheme = this.iFormulaDesignTimeController.findFormulaDefineInFormulaScheme(formula.getCode(), formula.getSchemeKey());
                if (null != formulaDefineInFormulaScheme) {
                    findFormulaDefineInFormulaSchemes.add(formulaDefineInFormulaScheme);
                }
                List repeatFormulaDefineFormOutSchemes = this.iExtFormulaDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), null, formula.getSchemeKey());
                findFormulaDefineInFormulaSchemes.addAll(repeatFormulaDefineFormOutSchemes);
            } else {
                DesignFormulaDefine formulaDefineInFormulaScheme = this.iFormulaDesignTimeController.findFormulaDefineInFormulaScheme(formula.getCode(), formula.getSchemeKey());
                if (null != formulaDefineInFormulaScheme) {
                    findFormulaDefineInFormulaSchemes.add(formulaDefineInFormulaScheme);
                }
                List repeatFormulaDefineFormOutSchemes = this.iExtFormulaDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), formula.getFormKey(), formula.getSchemeKey());
                findFormulaDefineInFormulaSchemes.addAll(repeatFormulaDefineFormOutSchemes);
            }
            if (findFormulaDefineInFormulaSchemes.size() > 0) {
                formuCodeCheck.setCode(((DesignFormulaDefine)findFormulaDefineInFormulaSchemes.get(0)).getCode());
                formuCodeCheck.setErrorMsg(FORMULA_CODE_REPEAT);
                formuCodeChecks.add(formuCodeCheck);
                return formuCodeChecks;
            }
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u79c1\u6709\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"private_formula_parse"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> checkFormula(@RequestBody List<FormulaCheckObj> formuObjList, @RequestParam String schemeKey) throws JQException {
        if (formuObjList != null) {
            List<FormulaCheckObj> useCheckList = formuObjList;
            FormulaMonitor formulaMonitor = new FormulaMonitor();
            if (useCheckList.size() > 0) {
                ArrayList<Formula> useCheckLists = new ArrayList<Formula>(useCheckList);
                this.parseFormulas(schemeKey, useCheckLists, DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            }
            Map<String, FormulaCheckObj> checkResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkResultMap.values();
            ArrayList<FormulaCheckObj> checkResultList = new ArrayList<FormulaCheckObj>(values);
            if (formuObjList.size() == 1) {
                this.realFormulaCodeCheck(checkResultList, formuObjList);
            }
            this.checkFormulaOrder(checkResultList);
            return checkResultList;
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u79c1\u6709\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"private_formula_parse_rsa"}, method={RequestMethod.POST})
    public List<FormulaCheckObj> checkFormulaRsa(@RequestBody @SFDecrypt FormulaDesCreateParam param) throws JQException {
        return this.checkFormula(param.getFormuObj(), param.getSchemeKey());
    }

    @RequestMapping(value={"export_All_Private_Formulas"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5bfc\u51fa\u516c\u5f0f\u65b9\u6848\u4e0b\u5168\u90e8\u79c1\u6709\u516c\u5f0f")
    public void exportAllPrivateFormulas(@RequestBody String formulasInfo, HttpServletResponse res) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(formulasInfo);
        String scheme = jsonNode.get("schemeKey").toString();
        String schemeKey = (String)mapper.readValue(scheme, String.class);
        String unit = jsonNode.get("unit").toString();
        String unitCode = (String)mapper.readValue(unit, String.class);
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(schemeKey);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        List formDefines = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        List<String> formKeyList = formDefines.stream().map(t -> t.getKey()).collect(Collectors.toList());
        this.exportAllFormulas(schemeKey, formKeyList, res, isEfdc, unitCode);
    }

    @ApiOperation(value="\u5bfc\u5165\u79c1\u6709\u516c\u5f0f")
    @PostMapping(value={"privateFormulaImport"})
    public Map<String, List> importFormulaExcel(String unit, String formulaScheme, String importMethod, @RequestParam(value="file") MultipartFile file) throws JQException, ParseException {
        LinkedHashMap<String, List> formulasSheetMap = new LinkedHashMap<String, List>();
        boolean isFullImport = importMethod.equalsIgnoreCase("all_import");
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (null == designFormulaSchemeDefine || !org.springframework.util.StringUtils.hasLength(unit)) {
            return formulasSheetMap;
        }
        List formDefines = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(designFormulaSchemeDefine.getFormSchemeKey());
        String[] formId = (String[])formDefines.stream().map(t -> t.getKey()).toArray(String[]::new);
        this.paraExcelFormulas(file, formId, formulasSheetMap, formulaScheme, isFullImport, unit);
        if (((List)formulasSheetMap.get("repeatCode")).size() == 0) {
            if (isFullImport) {
                this.allImportFormula(formId, formulaScheme, formulasSheetMap, unit);
            } else {
                this.addImportFormula(formId, formulaScheme, formulasSheetMap, unit);
            }
        } else {
            return formulasSheetMap;
        }
        return formulasSheetMap;
    }

    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u7528\u6237\u7684\u4fe1\u606f")
    @RequestMapping(value={"getCurrentUserInfo"}, method={RequestMethod.POST})
    public PrivateUserInfo getCurrentUserInfo(@RequestBody UnitSelectorObj unitSelectorObj) throws Exception {
        DesignTaskDefine designTaskDefine;
        DesignFormSchemeDefine formSchemeDefine;
        String formSchemeKey;
        String userName = NpContextHolder.getContext().getUserName();
        String org = "";
        if (null != NpContextHolder.getContext().getOrganization() && null != unitSelectorObj && org.springframework.util.StringUtils.hasLength(formSchemeKey = unitSelectorObj.getFormSchemeKey()) && null != (formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formSchemeKey)) && null != (designTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey()))) {
            RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
            viewDefine.setEntityId(designTaskDefine.getDw());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
            ExecutorContext contextChildren = new ExecutorContext(this.definitionRuntimeController);
            contextChildren.setPeriodView(designTaskDefine.getDw());
            IEntityTable iEntityTable = iEntityQuery.executeFullBuild((IContext)contextChildren);
            String code = NpContextHolder.getContext().getOrganization().getCode();
            IEntityRow byEntityKey = iEntityTable.findByEntityKey(code);
            if (null != byEntityKey) {
                org = byEntityKey.getCode();
            }
        }
        PrivateUserInfo info = new PrivateUserInfo(userName, org);
        return info;
    }

    @ApiOperation(value="\u5224\u65ad\u4efb\u52a1\u662f\u5426\u62e5\u6709\u5efa\u6a21\u6743\u9650")
    @RequestMapping(value={"existDesignTask/{taskKey}"}, method={RequestMethod.GET})
    public boolean existDesignTask(@PathVariable(value="taskKey") String taskKey) throws JQException {
        boolean auth = false;
        if (org.springframework.util.StringUtils.hasLength(taskKey)) {
            auth = this.definitionAuthority.canModeling(taskKey);
        }
        return auth;
    }

    @ApiOperation(value="\u79c1\u6709\u516c\u5f0forder\u521b\u5efa")
    @RequestMapping(value={"createPrivateOrder"}, method={RequestMethod.POST})
    public String createPrivateOrder(@RequestBody UnitSelectorObj unitSelectorObj) throws JQException {
        if (org.springframework.util.StringUtils.hasLength(unitSelectorObj.getFormulaSchemeKey())) {
            try {
                List formulaDefines = this.iExtFormulaDesignTimeController.getFormulaBySchemeAndForm(unitSelectorObj.getFormulaSchemeKey(), unitSelectorObj.getCurrentFormId());
                formulaDefines.stream().sorted(new Comparator<DesignFormulaDefine>(){

                    @Override
                    public int compare(DesignFormulaDefine o1, DesignFormulaDefine o2) {
                        return o1.getCode().compareTo(o2.getCode());
                    }
                }).collect(Collectors.toList());
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0102, (Throwable)e);
            }
        }
        return null;
    }

    public synchronized void addImportFormula(String[] formIds, String formulaScheme, Map<String, List> formulasSheetMap, String unit) throws ParseException, JQException {
        if (formulasSheetMap.get("repeatCode").size() > 0) {
            return;
        }
        int count = 0;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        List formulas = this.iExtFormulaDesignTimeController.searchFormulaIgnorePrivate(formulaScheme);
        ArrayList<FormulaObj> formulaList = new ArrayList<FormulaObj>();
        HashSet<String> formCodeSet = new HashSet<String>();
        for (int i = 0; i < formIds.length; ++i) {
            DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formIds[i]);
            formCodeSet.add(formDefine.getFormCode());
        }
        if (!isEfdc) {
            formCodeSet.add(BJFORMULA);
        }
        int formCodeSetSize = formCodeSet.size();
        for (Map.Entry entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = (String)entry.getKey();
            List formulasMapValue = (List)entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode")) continue;
            formCodeSet.add(sheetCode);
            for (Object formula : formulasMapValue) {
                FormulaObj addFormulaObj;
                Boolean isDirty;
                String formulaCode = ((FormulaCheckObj)((Object)formula)).getCode();
                FormulaObj formulaObj = new FormulaObj();
                List needAddFormulas = formulas.stream().filter(item -> item.getCode().equals(formulaCode)).collect(Collectors.toList());
                if (needAddFormulas.size() == 1) {
                    ++count;
                    isDirty = true;
                    addFormulaObj = this.objectToFormulaObj(formula, formulaObj, (DesignFormulaDefine)needAddFormulas.get(0), isDirty);
                    addFormulaObj.setIsNew(false);
                    addFormulaObj.setIsDirty(true);
                    addFormulaObj.setIsDeleted(false);
                    formulaList.add(addFormulaObj);
                    continue;
                }
                if (needAddFormulas.size() == 0) {
                    isDirty = false;
                    addFormulaObj = this.objectToFormulaObj(formula, formulaObj, null, isDirty);
                    addFormulaObj.setIsNew(true);
                    addFormulaObj.setIsDirty(false);
                    addFormulaObj.setIsDeleted(false);
                    formulaList.add(addFormulaObj);
                    continue;
                }
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_034);
            }
        }
        int formCodeSetSizeNew = formCodeSet.size();
        if (formCodeSetSizeNew - formCodeSetSize == formulasSheetMap.size() - 1) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_036);
        }
        FormulaObj[] formulaObjArray = new FormulaObj[formulaList.size()];
        UnitSelectorObj obj = new UnitSelectorObj();
        obj.setUnit(unit);
        obj.setFormulaSchemeKey(formulaScheme);
        obj.setFormulaObjs(formulaList.toArray(formulaObjArray));
        try {
            this.saveFormulas(obj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_034, (Throwable)e);
        }
    }

    private FormulaObj objectToFormulaObj(Object formula, FormulaObj formulaObj, DesignFormulaDefine needAddFormulas, Boolean isDirty) {
        FormulaCheckObj formulaCheckObj = (FormulaCheckObj)((Object)formula);
        formulaObj.setCode(formulaCheckObj.getCode());
        String formulaId = null;
        String formulaOrder = null;
        if (isDirty.booleanValue()) {
            formulaId = needAddFormulas.getKey().toString();
            formulaOrder = needAddFormulas.getOrder();
        } else {
            formulaId = ((FormulaCheckObj)((Object)formula)).getId();
            formulaOrder = ((FormulaCheckObj)((Object)formula)).getOrder();
        }
        formulaObj.setId(formulaId);
        formulaObj.setExpression(formulaCheckObj.getFormula());
        if (!StringUtils.isEmpty((String)formulaOrder)) {
            formulaObj.setOrder(formulaOrder);
        }
        int checkType = null == formulaCheckObj.getChecktype() ? 4 : formulaCheckObj.getChecktype();
        formulaObj.setCheckType(checkType);
        formulaObj.setDescription(formulaCheckObj.getDescription());
        formulaObj.setUseCalculate(false);
        formulaObj.setUseCheck(true);
        formulaObj.setUseBalance(false);
        if (!StringUtils.isEmpty((String)formulaCheckObj.getSchemeKey())) {
            formulaObj.setSchemeKey(formulaCheckObj.getSchemeKey());
        }
        formulaObj.setFormKey(formulaCheckObj.getFormKey() == null ? null : formulaCheckObj.getFormKey().toString());
        return formulaObj;
    }

    public void allImportFormula(String[] formId, String formulaScheme, Map<String, List> formulasSheetMap, String unit) throws JQException, ParseException {
        if (formulasSheetMap.get("repeatCode").size() > 0) {
            return;
        }
        boolean count = false;
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        ArrayList<FormulaObj> formulaList = new ArrayList<FormulaObj>();
        ArrayList deleteFormulas = new ArrayList();
        HashMap<String, String> formCodeMap = new HashMap<String, String>();
        List formDefineList = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        for (Object designFormDefine : formDefineList) {
            formCodeMap.put(designFormDefine.getFormCode(), designFormDefine.getKey());
        }
        if (!isEfdc) {
            formCodeMap.put(BJFORMULA, null);
        }
        boolean isNeedDelete = false;
        for (Map.Entry entry : formulasSheetMap.entrySet()) {
            String formulasMapKey = (String)entry.getKey();
            List formulasMapValue = (List)entry.getValue();
            String sheetCode = formulasMapKey.split(" ")[0];
            if (formulasMapKey.equals("repeatCode") || !formCodeMap.containsKey(sheetCode)) continue;
            List allSoftFormulasInForm = this.iExtFormulaDesignTimeController.getFormulaByUnit(formulaScheme, (String)formCodeMap.get(sheetCode), unit);
            deleteFormulas.addAll(allSoftFormulasInForm);
            for (Object formula : formulasMapValue) {
                String formulaCode = ((FormulaCheckObj)((Object)formula)).getCode();
                FormulaObj formulaObj = new FormulaObj();
                Boolean isDirty = false;
                FormulaObj addFormulaObj = this.objectToFormulaObj(formula, formulaObj, null, isDirty);
                addFormulaObj.setIsNew(true);
                addFormulaObj.setIsDirty(false);
                addFormulaObj.setIsDeleted(false);
                formulaList.add(addFormulaObj);
            }
            isNeedDelete = true;
        }
        if (!isNeedDelete) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_036);
        }
        if (deleteFormulas.size() > 0) {
            this.iExtFormulaDesignTimeController.deleteFormulaDefines(deleteFormulas.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()).toArray(new String[0]));
        }
        FormulaObj[] formulaArray = new FormulaObj[formulaList.size()];
        UnitSelectorObj unitSelectorObj = new UnitSelectorObj();
        unitSelectorObj.setUnit(unit);
        unitSelectorObj.setFormulaSchemeKey(formulaScheme);
        unitSelectorObj.setFormulaObjs(formulaList.toArray(formulaArray));
        try {
            this.saveFormulas(unitSelectorObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_034);
        }
    }

    private void checkFormulaCode(FormulaCheckObj excelFormula, Set<String> excelFormulaCodeSet, Set<String> formulaCodeSet, List<String> formulaRepeatCode, boolean isFullImport, int r, String sheetName, Map<String, List<String>> form_formulasMap) {
        if (isFullImport) {
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (formulaCodeSet.contains(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5df2\u6709\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        } else {
            Boolean bjFlag = sheetName.equals(BJFORMULA);
            String formKey = excelFormula.getFormKey();
            formulaCodeSet.clear();
            for (Map.Entry<String, List<String>> formulasFormKey : form_formulasMap.entrySet()) {
                List<String> formulaCodes;
                String formKeyByFormula = formulasFormKey.getKey();
                if (bjFlag.booleanValue() && formKeyByFormula == null || formKeyByFormula != null && formKeyByFormula.equals(formKey) || (formulaCodes = formulasFormKey.getValue()) == null) continue;
                formulaCodeSet.addAll(formulaCodes);
            }
            String formulaCode = excelFormula.getCode();
            if (!excelFormulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("excel\u5185\u90e8\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
            if (!formulaCodeSet.add(formulaCode)) {
                formulaRepeatCode.add("\u4e0e\u5176\u4ed6\u8868\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:sheet\u540d\u79f0:" + sheetName + ";\u91cd\u590d\u7f16\u53f7:" + formulaCode + ";\u91cd\u590d\u7f16\u53f7\u4f4d\u7f6e:A" + r);
            }
        }
    }

    private String paraFormulaCode(DesignFormDefine form, int row) {
        String formulaNum = "";
        for (int i = String.valueOf(row).length(); i < 4; ++i) {
            formulaNum = formulaNum + "0";
        }
        String cValue = form == null ? "BJ" + formulaNum + row : form.getFormCode() + formulaNum + row;
        return cValue;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        cellValue = cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : (cell.getCellType() == CellType.STRING ? String.valueOf(cell.getStringCellValue()) : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()) : (cell.getCellType() == CellType.FORMULA ? String.valueOf(cell.getCellFormula()) : (cell.getCellType() == CellType.BLANK ? "" : (cell.getCellType() == CellType.ERROR ? "\u975e\u6cd5\u5b57\u7b26" : "\u672a\u77e5\u7c7b\u578b")))));
        return cellValue;
    }

    private void validateHeader(Sheet sheet) throws JQException {
        Row firstRow = sheet.getRow(0);
        if (!NUMBER.equals(DesignExtFormulaController.getCellValue(firstRow.getCell(0)))) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_035);
        }
    }

    private void getForm_formulasMap(Map<String, List<String>> form_formulasMap, List<DesignFormulaDefine> formulas, Map<String, String> formulasCodeMap) {
        for (DesignFormulaDefine formula : formulas) {
            formulasCodeMap.put(formula.getCode(), formula.getKey());
            List<String> formulaCodeList = form_formulasMap.get(formula.getFormKey());
            if (null == formulaCodeList) {
                formulaCodeList = new ArrayList<String>();
                form_formulasMap.put(formula.getFormKey(), formulaCodeList);
            }
            formulaCodeList.add(formula.getCode());
        }
    }

    private void getAllFormulaCodeSet(List<DesignFormulaDefine> formulas, Set<String> excelFormCodeSet, Set<String> formulaCodeSet, Map<String, String> formulasCodeMap) {
        if (formulas.size() == 0) {
            return;
        }
        HashMap<String, Boolean> importFormMap = new HashMap<String, Boolean>();
        boolean hasBJ = excelFormCodeSet.contains(BJFORMULA);
        for (int i = 0; i < formulas.size(); ++i) {
            DesignFormulaDefine formula = formulas.get(i);
            formulasCodeMap.put(formula.getCode(), formula.getKey());
            String formKey = formula.getFormKey();
            String formulaCode = formula.getCode();
            if (StringUtils.isEmpty((String)formKey)) {
                if (hasBJ) continue;
                formulaCodeSet.add(formulaCode);
                continue;
            }
            Boolean isImportForm = (Boolean)importFormMap.get(formKey);
            if (isImportForm == null) {
                DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formKey);
                if (formDefine == null) {
                    isImportForm = true;
                    importFormMap.put(formKey, isImportForm);
                } else {
                    isImportForm = excelFormCodeSet.contains(formDefine.getFormCode());
                    importFormMap.put(formKey, isImportForm);
                }
            }
            if (isImportForm.booleanValue()) continue;
            formulaCodeSet.add(formulaCode);
        }
    }

    private void paraExcel(Workbook workbook, String[] formId, Map<String, List> formulasMap, String formulaScheme, boolean isFullImport, String unit) throws IOException, JQException {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaScheme);
        if (formulaSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_019);
        }
        boolean isEfdc = formulaSchemeDefine.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        Map<String, Integer> auditTypeMap = this.getAuditTypeMap();
        int sheetCount = workbook.getNumberOfSheets();
        List formulas = this.iExtFormulaDesignTimeController.searchFormulaIgnorePrivate(formulaScheme);
        HashMap<String, String> formulaCodeKeyMap = new HashMap<String, String>();
        HashMap<String, List<String>> form_formulasMap = new HashMap<String, List<String>>();
        HashSet<String> formulaCodeSet = new HashSet<String>();
        if (isFullImport) {
            HashSet<String> excelFormCodeSet = new HashSet<String>();
            for (int i = 0; i < sheetCount; ++i) {
                excelFormCodeSet.add(workbook.getSheetAt(i).getSheetName().split(" ")[0]);
            }
            this.getAllFormulaCodeSet(formulas, excelFormCodeSet, formulaCodeSet, formulaCodeKeyMap);
        } else {
            this.getForm_formulasMap(form_formulasMap, formulas, formulaCodeKeyMap);
        }
        Map<Object, Object> formCodeDefineMap = new HashMap();
        if (formId != null) {
            formCodeDefineMap = Arrays.stream(formId).map(formKey -> this.nrDesignTimeController.querySoftFormDefine(formKey)).filter(f -> f != null).collect(Collectors.toMap(form -> form.getFormCode(), form -> form, (oldValue, newValue) -> newValue));
        }
        HashSet<String> excelFormulaCodeSet = new HashSet<String>();
        ArrayList<String> excelFormulaLength = new ArrayList<String>();
        ArrayList<String> formulaRepeatCode = new ArrayList<String>();
        for (int s = 0; s < sheetCount; ++s) {
            String sheetName;
            Sheet sheet = workbook.getSheetAt(s);
            if (sheet == null || (sheetName = sheet.getSheetName()).equals(REPEATCODEINFO)) continue;
            this.validateHeader(sheet);
            String sheetCode = sheetName.split(" ")[0];
            DesignFormDefine form2 = (DesignFormDefine)formCodeDefineMap.get(sheetCode);
            int rowCount = sheet.getLastRowNum();
            ArrayList<FormulaCheckObj> formulaList = new ArrayList<FormulaCheckObj>();
            int headerColCount = sheet.getRow(0).getLastCellNum();
            ArrayList<String> headerList = new ArrayList<String>();
            for (int c = 0; c < headerColCount; ++c) {
                headerList.add(DesignExtFormulaController.getCellValue(sheet.getRow(0).getCell(c)));
            }
            this.checkHeader(headerList);
            for (int r = 1; r <= rowCount; ++r) {
                boolean hasExpression = true;
                Row row = sheet.getRow(r);
                int colCount = row.getLastCellNum();
                FormulaCheckObj formula = new FormulaCheckObj();
                block16: for (int c = 0; c < colCount && hasExpression; ++c) {
                    String cellValue = (String)headerList.get(c);
                    String cValue = DesignExtFormulaController.getCellValue(row.getCell(c));
                    switch (cellValue) {
                        case "\u7f16\u53f7": {
                            String excelCode = StringUtils.isEmpty((String)cValue) ? this.paraFormulaCode(form2, r) : cValue;
                            formula.setCode(this.addPreFix(excelCode, unit));
                            continue block16;
                        }
                        case "\u8868\u8fbe\u5f0f": {
                            hasExpression = StringUtils.isNotEmpty((String)cValue);
                            formula.setFormula(cValue);
                            continue block16;
                        }
                        case "\u8bf4\u660e": {
                            if (cValue.length() > 1000) {
                                excelFormulaLength.add("excel\u516c\u5f0f\u8bf4\u660e\u957f\u5ea6\u8d85\u957f:sheet\u540d\u79f0:" + sheetName + ";\u957f\u5ea6\u9650\u5236\u4e3a:1000;\u8d85\u957f\u4f4d\u7f6e:C" + (r + 1));
                            }
                            formula.setDescription(cValue);
                            continue block16;
                        }
                        case "\u5ba1\u6838\u7c7b\u578b": {
                            if (isEfdc) continue block16;
                            Integer code = auditTypeMap.get(cValue);
                            formula.setChecktype(code);
                            continue block16;
                        }
                    }
                }
                if (!hasExpression) continue;
                formula.setOrder(OrderGenerator.newOrder());
                formula.setSchemeKey(formulaScheme);
                formula.setFormKey(sheetName.equals(BJFORMULA) ? null : form2.getKey());
                formula.setReportName(form2 == null ? "" : form2.getFormCode());
                formula.setId(formulaCodeKeyMap.get(formula.getCode()) == null ? UUIDUtils.getKey() : (String)formulaCodeKeyMap.get(formula.getCode()));
                if (null == formula.getFormula()) {
                    formula.setFormula("");
                }
                if (null == formula.getDescription()) {
                    formula.setDescription("");
                }
                formulaList.add(formula);
                this.checkFormulaCode(formula, excelFormulaCodeSet, formulaCodeSet, formulaRepeatCode, isFullImport, r, sheetName, form_formulasMap);
            }
            formulasMap.put(sheetName, formulaList);
        }
        formulasMap.put("repeatCode", formulaRepeatCode);
        formulasMap.get("repeatCode").addAll(excelFormulaLength);
    }

    private void checkHeader(List<String> headerList) throws JQException {
        if (!(headerList.contains(NUMBER) && headerList.contains(EXPRESSION) && headerList.contains(AUDITTYPE))) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0103);
        }
    }

    private Workbook getWorkBook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toUpperCase();
        HSSFWorkbook workbook = null;
        try (InputStream is = file.getInputStream();){
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return workbook;
    }

    private void checkFile(MultipartFile file) throws JQException {
        if (null == file) {
            logger.error("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_020);
        }
        String fileName = file.getOriginalFilename().toUpperCase();
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "\u4e0d\u662fexcel\u6587\u4ef6");
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_021, "[" + fileName + "]" + NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_021);
        }
    }

    public void paraExcelFormulas(MultipartFile file, String[] formId, Map<String, List> formulasMap, String formulaScheme, boolean isFullImport, String unit) throws JQException {
        this.checkFile(file);
        try {
            Workbook workBook = this.getWorkBook(file);
            this.paraExcel(workBook, formId, formulasMap, formulaScheme, isFullImport, unit);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_022);
        }
    }

    private Map<String, Integer> getAuditTypeMap() throws JQException {
        HashMap<String, Integer> auditTypeMap = new HashMap<String, Integer>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            for (int i = 0; i < auditTypes.size(); ++i) {
                auditTypeMap.put(((AuditType)auditTypes.get(i)).getTitle(), ((AuditType)auditTypes.get(i)).getCode());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_025, (Throwable)e);
        }
        return auditTypeMap;
    }

    private String addPreFix(String code, String unit) {
        if (org.springframework.util.StringUtils.hasLength(code) && org.springframework.util.StringUtils.hasLength(unit)) {
            if (code.length() >= unit.length() && code.substring(0, unit.length() + 1) == unit.concat(CODENORMAL)) {
                return code;
            }
            code = unit.concat(CODENORMAL).concat(code);
        }
        return code;
    }

    private String removePreFix(String code, String unit) {
        if (org.springframework.util.StringUtils.hasLength(code) && org.springframework.util.StringUtils.hasLength(unit) && code.length() > unit.length() && code.substring(0, unit.length() + 1).equals(unit.concat(CODENORMAL))) {
            code = code.substring(unit.length() + 1, code.length());
        }
        return code;
    }

    public void exportAllFormulas(String formulaScheme, List<String> formKeyList, HttpServletResponse res, boolean isEfdc, String unit) throws JQException {
        try {
            int sheetCount = 0;
            HashSet<String> sheetTitleSet = new HashSet<String>();
            String fileName = "\u5168\u90e8\u516c\u5f0f";
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            ServletOutputStream out = res.getOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            String[] summary = new String[]{NUMBER, EXPRESSION, DESCRIPTION, "\u7c7b\u578b", AUDITTYPE};
            if (!isEfdc) {
                List BJforms = this.iExtFormulaDesignTimeController.getFormulaByUnit(formulaScheme, null, unit);
                String BJSheetTitle = BJFORMULA;
                this.exportAll(workbook, sheetCount, BJSheetTitle, summary, BJforms, (OutputStream)out, isEfdc, unit);
            }
            if (!isEfdc) {
                ++sheetCount;
            }
            for (int i = 0; i < formKeyList.size(); ++i) {
                String formKey = formKeyList.get(i);
                List form = this.iExtFormulaDesignTimeController.getFormulaByUnit(formulaScheme, formKey, unit);
                DesignFormDefine formDefine = this.nrDesignTimeController.querySoftFormDefine(formKey);
                String sheetTitle = formDefine.getFormCode() + " " + formDefine.getTitle();
                if (!sheetTitleSet.add(sheetTitle)) continue;
                this.exportAll(workbook, sheetCount, sheetTitle, summary, form, (OutputStream)out, isEfdc, unit);
                ++sheetCount;
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_101);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void exportAll(XSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<DesignFormulaDefine> result, OutputStream out, boolean isEfdc, String unit) throws Exception {
        List queryAllAuditType = this.auditTypeDefineService.queryAllAuditType();
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        sheet.setDefaultColumnWidth(15);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont((Font)font);
        style.setWrapText(true);
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            XSSFCell cell = row.createCell((int)((short)i));
            cell.setCellStyle((CellStyle)style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        if (result != null) {
            int index = 1;
            for (int r = 0; r < result.size(); ++r) {
                XSSFCell cell;
                row = sheet.createRow(index);
                int cellIndex = 0;
                DesignFormulaDefine resultData = result.get(r);
                String cellValue = null;
                if (resultData.getCode() != null && cellIndex == 0) {
                    cellValue = resultData.getCode() != null ? resultData.getCode() : "";
                    cell = row.createCell(cellIndex);
                    cell.setCellValue(this.removePreFix(cellValue.toString(), unit));
                    ++cellIndex;
                }
                if (resultData.getExpression() != null && cellIndex == 1) {
                    cellValue = resultData.getExpression() != null ? resultData.getExpression() : "";
                    cell = row.createCell(cellIndex);
                    cell.setCellValue(cellValue.toString());
                    ++cellIndex;
                }
                if (cellIndex == 2) {
                    cellValue = resultData.getDescription() != null ? resultData.getDescription() : "";
                    cell = row.createCell(cellIndex);
                    cell.setCellValue(cellValue.toString());
                    ++cellIndex;
                }
                if (cellIndex == 3) {
                    cell = row.createCell(cellIndex);
                    cell.setCellValue("\u5ba1\u6838\u516c\u5f0f");
                    ++cellIndex;
                }
                if (cellIndex == 4) {
                    cellValue = Integer.toString(resultData.getCheckType());
                    String cellVal = "";
                    if (cellValue != null && !cellValue.equals("0")) {
                        for (AuditType auditType : queryAllAuditType) {
                            if (!auditType.getCode().toString().equals(cellValue)) continue;
                            cellVal = auditType.getTitle();
                        }
                    } else {
                        cellVal = "";
                    }
                    XSSFCell cell2 = row.createCell(cellIndex);
                    cell2.setCellValue(cellVal.toString());
                    ++cellIndex;
                }
                ++index;
            }
        }
    }

    private String calcFormulaLog(List<DesignFormulaDefine> formulas) {
        String codeStr = "\u516c\u5f0f\u7f16\u53f7\uff1a";
        String keyStr = "\u516c\u5f0f\u4e3b\u952e\uff1a";
        ArrayList<String> logs = new ArrayList<String>();
        for (DesignFormulaDefine formula : formulas) {
            StringBuffer sbf = new StringBuffer(codeStr);
            sbf.append(formula.getCode()).append(",");
            sbf.append(keyStr).append(formula.getKey());
            logs.add(sbf.toString());
        }
        return com.jiuqi.util.StringUtils.join(logs.iterator(), (String)";");
    }

    private Map<String, DesignFormulaDefine> getKeyDefineMap(String formulaSchemeKey) throws JQException {
        List formulaDefines = this.iExtFormulaDesignTimeController.getAllFormulasInScheme(formulaSchemeKey);
        HashMap<String, DesignFormulaDefine> map = new HashMap<String, DesignFormulaDefine>();
        for (DesignFormulaDefine formulaDefine : formulaDefines) {
            map.put(formulaDefine.getKey(), formulaDefine);
        }
        return map;
    }

    private List<String> checkFormulaCode(List<DesignFormulaDefine> needCreateFormula, List<DesignFormulaDefine> needUpdateFormula) throws JQException {
        ArrayList<String> errorFormulaCode = new ArrayList<String>();
        String formulaSchemeKey = needCreateFormula.size() > 0 ? needCreateFormula.get(0).getFormulaSchemeKey() : (needUpdateFormula.size() > 0 ? needUpdateFormula.get(0).getFormulaSchemeKey() : null);
        Map codeCountMap = this.iExtFormulaDesignTimeController.getFormulaCodeCountByScheme(formulaSchemeKey);
        Map<String, DesignFormulaDefine> keyDefineMap = this.getKeyDefineMap(formulaSchemeKey);
        for (DesignFormulaDefine updateFormula : needUpdateFormula) {
            if (updateFormula.getCode() == null) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_024);
            }
            DesignFormulaDefine formula = keyDefineMap.get(updateFormula.getKey());
            Integer oldf = (Integer)codeCountMap.get(formula.getCode());
            oldf = oldf - 1;
            codeCountMap.put(formula.getCode(), oldf);
            this.addCodeValue(codeCountMap, updateFormula);
        }
        if (needCreateFormula.size() > 0) {
            for (DesignFormulaDefine createFormula : needCreateFormula) {
                if (createFormula.getCode() == null) {
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_024);
                }
                this.addCodeValue(codeCountMap, createFormula);
            }
        }
        ArrayList<DesignFormulaDefine> allFormulas = new ArrayList<DesignFormulaDefine>();
        allFormulas.addAll(needCreateFormula);
        allFormulas.addAll(needUpdateFormula);
        for (DesignFormulaDefine formula : allFormulas) {
            Integer i = (Integer)codeCountMap.get(formula.getCode());
            if (i <= 1) continue;
            errorFormulaCode.add(formula.getCode());
        }
        return errorFormulaCode;
    }

    private void addCodeValue(Map<String, Integer> m, DesignFormulaDefine define) {
        Integer newf = m.get(define.getCode());
        if (newf == null) {
            newf = 0;
        }
        newf = newf + 1;
        m.put(define.getCode(), newf);
    }

    private DesignFormulaDefine initFormulaDefine(DesignFormulaDefine designFormulaDefine, FormulaObj formulaObj, UnitSelectorObj unitSelectorObj) {
        designFormulaDefine.setCode(formulaObj.getCode());
        designFormulaDefine.setExpression(formulaObj.getExpression());
        if (!com.jiuqi.util.StringUtils.isEmpty((String)formulaObj.getOrder())) {
            designFormulaDefine.setOrder(formulaObj.getOrder());
        }
        designFormulaDefine.setCheckType(formulaObj.getCheckType());
        designFormulaDefine.setDescription(formulaObj.getDescription());
        designFormulaDefine.setUseCalculate(false);
        designFormulaDefine.setUseCheck(true);
        designFormulaDefine.setUseBalance(false);
        designFormulaDefine.setFormulaSchemeKey(unitSelectorObj.getFormulaSchemeKey());
        if (org.springframework.util.StringUtils.hasLength(formulaObj.getFormKey())) {
            if (FORMULA_BIAOJIAN.equals(formulaObj.getFormKey())) {
                designFormulaDefine.setFormKey(null);
            } else {
                designFormulaDefine.setFormKey(formulaObj.getFormKey());
            }
        } else if (!FORMULA_BIAOJIAN.equals(unitSelectorObj.getCurrentFormId())) {
            designFormulaDefine.setFormKey(unitSelectorObj.getCurrentFormId());
        }
        designFormulaDefine.setIsPrivate(true);
        designFormulaDefine.setUnit(unitSelectorObj.getUnit());
        return designFormulaDefine;
    }

    private FormulaObj initFormulaObj(DesignFormulaDefine designFormulaDefine) {
        FormulaObj formulaObj = new FormulaObj();
        formulaObj.setId(designFormulaDefine.getKey());
        formulaObj.setCode(designFormulaDefine.getCode());
        formulaObj.setExpression(designFormulaDefine.getExpression());
        formulaObj.setDescription(designFormulaDefine.getDescription());
        formulaObj.setCheckType(designFormulaDefine.getCheckType());
        formulaObj.setOrder(designFormulaDefine.getOrder());
        formulaObj.setUseCalculate(designFormulaDefine.getUseCalculate());
        formulaObj.setUseCheck(designFormulaDefine.getUseCheck());
        formulaObj.setUseBalance(designFormulaDefine.getUseBalance());
        formulaObj.setSchemeKey(designFormulaDefine.getFormulaSchemeKey());
        formulaObj.setFormKey(designFormulaDefine.getFormKey());
        formulaObj.setOwnerLevelAndId(designFormulaDefine.getOwnerLevelAndId());
        formulaObj.setBalanceZBExp(designFormulaDefine.getBalanceZBExp());
        formulaObj.setSameServeCode(true);
        return formulaObj;
    }

    private void recursionAdminData(List<ExtNode> tree, List<IEntityRow> childRows, String parent, IEntityTable iEntityTableChildren) {
        for (IEntityRow childRow : childRows) {
            List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
            if (childRows1.size() != 0) {
                this.initTree(tree, parent, childRow, "", false);
                this.recursionAdminData(tree, childRows1, childRow.getCode(), iEntityTableChildren);
                continue;
            }
            this.initTree(tree, parent, childRow, "", true);
        }
    }

    private void recursionIEntityRows(List<ExtNode> tree, List<IEntityRow> childRows, String parent, IEntityTable iEntityTableChildren) {
        for (IEntityRow childRow : childRows) {
            List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
            if (childRows1.size() != 0) {
                this.initTree(tree, parent, childRow, "", false);
                continue;
            }
            this.initTree(tree, parent, childRow, "", true);
        }
    }

    private void recursionIEntityRows(List<ExtNode> tree, List<IEntityRow> childRows, String parent, IEntityTable iEntityTableChildren, Collection<String> grantedOrg) {
        for (IEntityRow childRow : childRows) {
            if (grantedOrg.contains(childRow.getCode())) continue;
            List childRows1 = iEntityTableChildren.getChildRows(childRow.getCode());
            if (childRows1.size() != 0) {
                this.initTree(tree, parent, childRow, "", false);
                continue;
            }
            this.initTree(tree, parent, childRow, "", true);
        }
    }

    private List<ExtNode> initTree(List<ExtNode> tree, String parent, IEntityRow row, String currCode, boolean isLeaf) {
        if (org.springframework.util.StringUtils.hasText(parent)) {
            for (ExtNode treeObj : tree) {
                if (treeObj.getCode().equals(parent)) {
                    if (treeObj.getChildren() == null) {
                        treeObj.setChildren(new ArrayList<ExtNode>());
                    }
                    treeObj.getChildren().add(this.transTreeObj(row, parent, currCode, isLeaf));
                    continue;
                }
                if (treeObj.getChildren() == null || treeObj.getChildren().size() == 0) continue;
                this.initTree(treeObj.getChildren(), parent, row, currCode, isLeaf);
            }
        } else {
            tree.add(this.transTreeObj(row, "", currCode, isLeaf));
        }
        return tree;
    }

    private ExtNode createParentTreeNode(String code, String title) {
        ExtNode obj = new ExtNode();
        obj.setKey(code);
        obj.setTitle(title);
        obj.setCode(code);
        Data data = new Data();
        data.setKey(code);
        data.setCode(code);
        data.setParentKey("");
        data.setTitle(title);
        obj.setData(data);
        return obj;
    }

    private ExtNode transTreeObj(IEntityRow row, String parent, String currCode, boolean isLeaf) {
        ExtNode obj = new ExtNode();
        obj.setKey(row.getCode());
        obj.setTitle(row.getTitle());
        obj.setCode(row.getCode());
        if (row.getCode().equals(currCode)) {
            obj.setSelected(true);
            obj.setExpanded(true);
        }
        if (isLeaf) {
            obj.setIsLeaf(true);
        } else {
            obj.setChildren(new ArrayList<ExtNode>());
        }
        Data data = new Data();
        data.setKey(row.getCode());
        data.setCode(row.getCode());
        data.setParentKey(parent);
        data.setTitle(row.getTitle());
        obj.setData(data);
        return obj;
    }

    private void realFormulaCodeCheck(List<FormulaCheckObj> checkResultList, List<FormulaCheckObj> formuObjList) throws JQException {
        FormulaObj formula = new FormulaObj();
        formula.setFormKey(formuObjList.get(0).getFormKey());
        formula.setCode(formuObjList.get(0).getCode());
        formula.setSchemeKey(formuObjList.get(0).getSchemeKey());
        List<FormulaCheckObj> formulaCodeCheck = this.formulaCodeCheck(formula);
        checkResultList.addAll(formulaCodeCheck);
    }

    private List<IParsedExpression> parseFormulas(String formSchemeKey, List<Formula> formulaList, DataEngineConsts.FormulaType formulaType, IMonitor formulaMonitor) throws JQException {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList();
        try {
            com.jiuqi.np.dataengine.executors.ExecutorContext context = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.definitionRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.iFormulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.iDesignTimeViewController, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)formulaType, (IMonitor)formulaMonitor);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017, (Throwable)e);
        }
        return parsedExpressions;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    private void checkFormulaOrder(List<FormulaCheckObj> formulaCheckObjs) {
        String regex = "^[a-zA-Z][\\w-]{0,25}$";
        String errMsg = "\u516c\u5f0f\u7f16\u53f7\u4e0d\u7b26\u5408\u5b57\u6bcd\u5f00\u5934,\u5b57\u6bcd\u52a0\u6570\u5b57\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u9650\u523625\u8981\u6c42!";
        for (FormulaCheckObj fco : formulaCheckObjs) {
            if (Pattern.matches(regex, fco.getCode())) continue;
            if (com.jiuqi.util.StringUtils.isEmpty((String)fco.getErrorMsg())) {
                fco.setErrorMsg(errMsg);
                continue;
            }
            fco.setErrorMsg(fco.getErrorMsg() + errMsg);
        }
    }
}


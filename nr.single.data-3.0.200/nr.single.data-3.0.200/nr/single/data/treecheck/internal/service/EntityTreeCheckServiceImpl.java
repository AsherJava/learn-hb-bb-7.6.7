/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityTableException
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  javax.annotation.Resource
 *  nr.single.map.param.service.SingleParamFileService
 */
package nr.single.data.treecheck.internal.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.NotFoundEntityTableException;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import nr.single.data.bean.CheckNodeList;
import nr.single.data.bean.CheckResultNode;
import nr.single.data.bean.EnityTreeCheckContext;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import nr.single.map.param.service.SingleParamFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityTreeCheckServiceImpl
implements IEntityTreeCheckService {
    private static final Logger logger = LoggerFactory.getLogger(EntityTreeCheckServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private SingleParamFileService singleParamService;

    @Override
    public List<CheckResultNode> CheckTreeNodeByTask(String taskKey, String periodCode, AsyncTaskMonitor monitor) throws Exception {
        return this.CheckTreeNodeByTask(taskKey, periodCode, false, monitor);
    }

    @Override
    public List<CheckResultNode> CheckTreeNodeByTask(String taskKey, String periodCode, boolean readAuth, AsyncTaskMonitor monitor) throws Exception {
        List formSchems = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        String formSchemeKey = null;
        if (formSchems != null && formSchems.size() > 0) {
            formSchemeKey = ((FormSchemeDefine)formSchems.get(0)).getKey();
        }
        return this.CheckTreeNodeByTask(taskKey, formSchemeKey, periodCode, readAuth, monitor);
    }

    @Override
    public List<CheckResultNode> CheckTreeNodeByTask(String taskKey, String formSchemeKey, String periodCode, boolean readAuth, AsyncTaskMonitor monitor) throws Exception {
        ArrayList<CheckResultNode> errorList = new ArrayList<CheckResultNode>();
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
        }
        EnityTreeCheckContext checkContext = new EnityTreeCheckContext();
        this.readSingleFMDMInfo(checkContext, taskKey, formSchemeKey);
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(taskKey);
        EntityViewDefine entityViewDefine = null;
        String entityId = null;
        if (task != null) {
            entityId = task.getDw();
            DsContext dsContext = DsContextHolder.getDsContext();
            if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
                entityId = dsContext.getContextEntityId();
            }
            if (StringUtils.isNotEmpty((String)entityId)) {
                entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
            } else {
                List dimList = this.dataSchemeSevice.getDataSchemeDimension(task.getDataScheme());
                for (DataDimension dim : dimList) {
                    if (dim.getDimensionType() != DimensionType.UNIT) continue;
                    entityId = dim.getDimKey();
                }
                if (StringUtils.isNotEmpty((String)entityId)) {
                    entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
                }
            }
        }
        if (monitor != null) {
            monitor.progressAndMessage(0.2, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
            if (monitor.isCancel()) {
                monitor.canceled(null, null);
                return errorList;
            }
        }
        if (StringUtils.isNotEmpty((String)entityId)) {
            CheckResultNode node;
            IEntityRow entityRow;
            int i;
            HashMap<String, IEntityAttribute> fieldMap = new HashMap<String, IEntityAttribute>();
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            Iterator iterator = entityModel.getAttributes();
            while (iterator.hasNext()) {
                IEntityAttribute next = (IEntityAttribute)iterator.next();
                fieldMap.put(next.getCode(), next);
            }
            checkContext.setFieldMap(fieldMap);
            if (monitor != null) {
                monitor.progressAndMessage(0.3, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            IEntityQuery dataQuery = this.entityDataService.newEntityQuery();
            logger.info("\u68c0\u67e5\u6811\u5f62\u8282\u70b9\uff1a\u52a0\u8f7d\u5355\u4f4d\u6570\u636e");
            dataQuery.setEntityView(entityViewDefine);
            if (readAuth) {
                dataQuery.setAuthorityOperations(readAuth ? AuthorityType.Read : AuthorityType.None);
            }
            dataQuery.setMasterKeys(new DimensionValueSet());
            try {
                GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)periodCode);
                dataQuery.setQueryVersionDate(new Date(gregorianCalendar.getTimeInMillis()));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new NotFoundEntityTableException(new String[]{"\u672a\u627e\u5230" + entityId + "\u7684table"});
            }
            HashMap<String, IEntityRow> entityCodesMap = new HashMap<String, IEntityRow>();
            HashMap<String, CheckResultNode> entityNodesMap = new HashMap<String, CheckResultNode>();
            CheckNodeList entityNodes = new CheckNodeList();
            boolean hasSjdmField = fieldMap.containsKey(checkContext.getSJDMField());
            ExecutorContext exeContext = new ExecutorContext(this.dataRuntimeController);
            IEntityTable dataTable = dataQuery.executeFullBuild((IContext)exeContext);
            logger.info("\u8bfb\u53d6\u5355\u4f4d\u6570\u636e\uff1a");
            if (monitor != null) {
                monitor.progressAndMessage(0.5, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            List allRows = dataTable.getAllRows();
            for (i = 0; i < allRows.size(); ++i) {
                entityRow = (IEntityRow)allRows.get(i);
                entityCodesMap.put(entityRow.getCode(), entityRow);
            }
            for (i = 0; i < allRows.size(); ++i) {
                entityRow = (IEntityRow)allRows.get(i);
                CheckResultNode node2 = this.getNodeInfo(checkContext, entityRow, entityCodesMap, fieldMap);
                node2.setSjdmField(hasSjdmField);
                entityNodes.add(node2);
                entityNodesMap.put(node2.getUnitCode(), node2);
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.6, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            if (readAuth) {
                IEntityRow entityRow2;
                int i2;
                IEntityQuery allDataQuery = this.entityDataService.newEntityQuery();
                logger.info("\u68c0\u67e5\u6811\u5f62\u8282\u70b9\uff1a\u52a0\u8f7d\u5355\u4f4d\u6570\u636e");
                allDataQuery.setEntityView(entityViewDefine);
                allDataQuery.setMasterKeys(new DimensionValueSet());
                try {
                    GregorianCalendar gregorianCalendar = PeriodUtil.period2Calendar((String)periodCode);
                    allDataQuery.setQueryVersionDate(new Date(gregorianCalendar.getTimeInMillis()));
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new NotFoundEntityTableException(new String[]{"\u672a\u627e\u5230" + entityId + "\u7684table"});
                }
                ExecutorContext allExeContext = new ExecutorContext(this.dataRuntimeController);
                IEntityTable allDataTable = allDataQuery.executeFullBuild((IContext)allExeContext);
                HashMap<String, IEntityRow> entityCodesMapOld = new HashMap<String, IEntityRow>();
                entityCodesMapOld.putAll(entityCodesMap);
                List allEntityRows = allDataTable.getAllRows();
                for (i2 = 0; i2 < allEntityRows.size(); ++i2) {
                    entityRow2 = (IEntityRow)allEntityRows.get(i2);
                    if (!entityCodesMap.containsKey(entityRow2.getCode())) {
                        entityCodesMap.put(entityRow2.getCode(), entityRow2);
                    }
                    if (monitor == null || !monitor.isCancel()) continue;
                    monitor.canceled(null, null);
                    return errorList;
                }
                for (i2 = 0; i2 < allEntityRows.size(); ++i2) {
                    entityRow2 = (IEntityRow)allEntityRows.get(i2);
                    if (!entityCodesMapOld.containsKey(entityRow2.getCode())) {
                        entityCodesMapOld.put(entityRow2.getCode(), entityRow2);
                        CheckResultNode node3 = this.getNodeInfo(checkContext, entityRow2, entityCodesMap, fieldMap);
                        node3.setSjdmField(hasSjdmField);
                        entityNodes.addOther(node3);
                        entityNodesMap.put(node3.getUnitCode(), node3);
                    }
                    if (monitor == null || !monitor.isCancel()) continue;
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.7, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            for (i = 0; i < entityNodes.getEntiyNodes().size(); ++i) {
                node = entityNodes.getEntiyNodes().get(i);
                if (StringUtils.isNotEmpty((String)node.getParentCode()) && entityNodesMap.containsKey(node.getParentCode())) {
                    this.writeNodeParentByFjd(node, (CheckResultNode)entityNodesMap.get(node.getParentCode()));
                }
                if (monitor == null || !monitor.isCancel()) continue;
                monitor.canceled(null, null);
                return errorList;
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.8, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    return errorList;
                }
            }
            for (i = 0; i < entityNodes.getEntiyNodes().size(); ++i) {
                node = entityNodes.getEntiyNodes().get(i);
                this.checkNode(checkContext, node, fieldMap, errorList, entityNodes);
                if (monitor == null || !monitor.isCancel()) continue;
                monitor.canceled(null, null);
                return errorList;
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.9, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
            }
            this.checkParentNodes(checkContext, fieldMap, errorList, entityNodes, monitor);
        }
        if (errorList.size() > 0) {
            for (CheckResultNode node : errorList) {
                logger.info(node.getErrorMsg());
            }
            logger.info("\u6811\u5f62\u68c0\u67e5\u9519\u8bef \u4e2a\u6570\uff1a" + errorList.size());
        }
        if (monitor != null) {
            if (monitor.isCancel()) {
                monitor.canceled(null, null);
                return errorList;
            }
            monitor.progressAndMessage(1.0, "\u6b63\u5728\u8fdb\u884c\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
        }
        return errorList;
    }

    private void checkNode(EnityTreeCheckContext checkContext, CheckResultNode node, Map<String, IEntityAttribute> fieldMap, List<CheckResultNode> errorList, CheckNodeList entityNodes) {
        CheckResultNode errorNode;
        Map<String, CheckResultNode> entityCodesMap = entityNodes.getEntityCodesMap();
        Map<String, CheckResultNode> allEntityCodesMap = entityNodes.getAllEntityCodesMap();
        Map<String, String> fjdMap = entityNodes.getFjdMap();
        Map<String, CheckResultNode> fjdFjdList = entityNodes.getFjdFjdList();
        boolean fjdExit = StringUtils.isNotEmpty((String)node.getParentCode());
        if (StringUtils.isNotEmpty((String)node.getParentCode()) && !"-".equalsIgnoreCase(node.getParentCode())) {
            boolean fjdValid = true;
            if (!fjdMap.containsKey(node.getParentCode()) && !entityCodesMap.containsKey(node.getParentCode())) {
                if (!allEntityCodesMap.containsKey(node.getParentCode())) {
                    errorNode = new CheckResultNode();
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7236\u8282\u70b9\u4ee3\u7801\u3010%s\u3011\u4e0d\u5b58\u5728", node.getUnitTitle(), node.getOrgCode(), node.getParentFJDCode()));
                    errorList.add(errorNode);
                    fjdValid = false;
                    fjdExit = false;
                }
            } else if (StringUtils.isEmpty((String)node.getParentBblx())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7236\u8282\u70b9\u62a5\u8868\u7c7b\u578b\u3010%s\u3011\u4e3a\u7a7a", node.getUnitTitle(), node.getOrgCode(), node.getParentBblx()));
                errorList.add(errorNode);
                fjdValid = false;
            } else if (checkContext.getYBHZBBLX().equalsIgnoreCase(node.getParentBblx()) && !checkContext.getYBHZBBLX().equalsIgnoreCase(node.getBblxCode())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7236\u8282\u70b9\u3010%s%s\u3011\u7c7b\u578b\u975e\u6cd5", node.getUnitTitle(), node.getOrgCode(), node.getParentQydm(), node.getParentBblx()));
                errorList.add(errorNode);
                fjdValid = false;
            } else if (!checkContext.getBZHZBBLX().equalsIgnoreCase(node.getParentBblx()) && !checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7236\u8282\u70b9\u3010%s%s\u3011\u7c7b\u578b\u975e\u6cd5", node.getUnitTitle(), node.getOrgCode(), node.getParentQydm(), node.getParentBblx()));
                errorList.add(errorNode);
                fjdValid = false;
            } else if (checkContext.getBZHZBBLX().equalsIgnoreCase(node.getBblxCode()) && checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u7236\u8282\u70b9\u4e0d\u80fd\u662f\u96c6\u56e2\u6c47\u603b\u8868\u3010%s\u3011", node.getUnitTitle(), node.getOrgCode(), node.getParentFJDCode()));
                errorList.add(errorNode);
                fjdValid = false;
            }
            if (!(!fjdValid || checkContext.getJTCEBBLX().equalsIgnoreCase(node.getBblxCode()) || checkContext.getBZCEBBLX().equalsIgnoreCase(node.getBblxCode()) || checkContext.getYBHZBBLX().equalsIgnoreCase(node.getBblxCode()) || checkContext.isFJBBLX(node.getBblxCode()) || entityNodes.getHasChild().containsKey(node.getParentCode()))) {
                entityNodes.getHasChild().put(node.getParentCode(), node);
            }
            if (fjdValid && checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx()) && (checkContext.getJCFHBBLX().equalsIgnoreCase(node.getBblxCode()) || checkContext.isJCHBBLX(node.getBblxCode())) && node.getQydmCode().equalsIgnoreCase(node.getParentQydm()) && !entityNodes.getHasBBRec().containsKey(node.getParentCode())) {
                entityNodes.getHasBBRec().put(node.getParentCode(), node);
            }
        }
        if (node.isSjdmField()) {
            String sjdmCode = node.getSjdmCode();
            if (StringUtils.isNotEmpty((String)sjdmCode) && "0000000000000000000000000000000000000".substring(0, sjdmCode.length()).equalsIgnoreCase(sjdmCode)) {
                sjdmCode = null;
            }
            if (StringUtils.isEmpty((String)node.getQydmCode())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u4f01\u4e1a\u4ee3\u7801\u3010%s\u3011\u4e3a\u7a7a", node.getUnitTitle(), node.getOrgCode(), node.getQydmCode()));
                errorList.add(errorNode);
            } else if (checkContext.getJTCEBBLX().equalsIgnoreCase(node.getBblxCode())) {
                if (!node.getQydmCode().equalsIgnoreCase(node.getSjdmCode())) {
                    errorNode = new CheckResultNode();
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u96c6\u56e2\u5dee\u989d\u8868\u7684\u4e0a\u7ea7\u4ee3\u7801\u3010%s\u3011\u4e0e\u4e3b\u4ee3\u7801\u4e0d\u5339\u914d", node.getUnitTitle(), node.getOrgCode(), node.getSjdmCode()));
                    errorList.add(errorNode);
                }
                if (fjdExit && !node.getQydmCode().equalsIgnoreCase(node.getParentQydm())) {
                    errorNode = new CheckResultNode();
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u96c6\u56e2\u5dee\u989d\u8868\u7684\u7236\u8282\u70b9\u3010%s\u3011\u4e0d\u662f\u5bf9\u5e94\u7684\u96c6\u56e2\u6c47\u603b\u8868", node.getUnitTitle(), node.getOrgCode(), node.getParentQydm() + node.getParentBblx()));
                    errorList.add(errorNode);
                }
            }
            if (StringUtils.isNotEmpty((String)node.getSjdmCode())) {
                if (StringUtils.isEmpty((String)node.getParentCode()) || !node.getSjdmCode().equalsIgnoreCase(node.getParentQydm())) {
                    errorNode = new CheckResultNode();
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u4e0a\u7ea7\u4ee3\u7801\u3010%s\u3011\u4e0e\u7236\u8282\u70b9\u3010%s\u3011\u4e0d\u5339\u914d", node.getUnitTitle(), node.getOrgCode(), node.getSjdmCode(), node.getParentQydm() + node.getParentBblx()));
                    errorList.add(errorNode);
                } else if (!checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx())) {
                    errorNode = new CheckResultNode();
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u4e0a\u7ea7\u4ee3\u7801\u3010%s\u3011\u4e0e\u7236\u8282\u70b9\u3010%s\u3011\u4e0d\u5339\u914d\uff0c\u7236\u8282\u70b9\u7c7b\u578b\u975e\u6cd5", node.getUnitTitle(), node.getOrgCode(), node.getSjdmCode(), node.getParentQydm() + node.getParentBblx()));
                    errorList.add(errorNode);
                }
            } else if (StringUtils.isEmpty((String)node.getSjdmCode()) && checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx())) {
                errorNode = new CheckResultNode();
                errorNode.copyFrom(node);
                errorNode.setErrorType(1);
                errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u4e0a\u7ea7\u4ee3\u7801\u4e3a\u7a7a\uff0c\u7236\u8282\u70b9\u3010%s\u3011\u4e0d\u80fd\u662f\u96c6\u56e2\u6c47\u603b\u8868", node.getUnitTitle(), node.getOrgCode(), node.getParentQydm() + node.getParentBblx()));
                errorList.add(errorNode);
            }
        }
        if (fieldMap.containsKey(checkContext.getZBDMField())) {
            if (StringUtils.isEmpty((String)node.getZbdmCode()) && StringUtils.isNotEmpty((String)node.getSjdmCode())) {
                CheckResultNode errorNode2 = new CheckResultNode();
                errorNode2.copyFrom(node);
                errorNode2.setErrorType(1);
                errorNode2.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u4e0a\u7ea7\u4ee3\u7801\u4e0d\u4e3a\u7a7a\u800c\u603b\u90e8\u4ee3\u7801\u4e3a\u7a7a", node.getUnitTitle(), node.getOrgCode()));
                errorList.add(errorNode2);
            } else if (StringUtils.isNotEmpty((String)node.getZbdmCode()) && !allEntityCodesMap.containsKey(node.getZbdmCode() + checkContext.getJTHZBBLX())) {
                CheckResultNode errorNode3 = new CheckResultNode();
                errorNode3.copyFrom(node);
                errorNode3.setErrorType(1);
                errorNode3.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7684\u7684\u603b\u90e8\u4ee3\u7801\u3010%s\u3011\u4e0d\u5b58\u5728", node.getUnitTitle(), node.getOrgCode(), node.getZbdmCode()));
                errorList.add(errorNode3);
            }
        }
        if (StringUtils.isNotEmpty((String)node.getBblxCode())) {
            if (checkContext.getJTCEBBLX().equalsIgnoreCase(node.getBblxCode()) && !entityCodesMap.containsKey(node.getQydmCode() + checkContext.getJTHZBBLX())) {
                if (!allEntityCodesMap.containsKey(node.getQydmCode() + checkContext.getJTHZBBLX())) {
                    CheckResultNode errorNode4 = new CheckResultNode();
                    errorNode4.copyFrom(node);
                    errorNode4.setErrorType(1);
                    errorNode4.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u96c6\u56e2\u5dee\u989d\u8868\u5bf9\u5e94\u7684\u96c6\u56e2\u6c47\u603b\u8868\u3010%s%s\u3011\u4e0d\u5b58\u5728", node.getUnitTitle(), node.getOrgCode(), node.getQydmCode(), checkContext.getJTHZBBLX()));
                    errorList.add(errorNode4);
                }
            } else if (checkContext.getBZCEBBLX().equals(node.getBblxCode())) {
                if (StringUtils.isNotEmpty((String)node.getQydmCode()) && node.getQydmCode().equalsIgnoreCase(node.getParentQydm())) {
                    CheckResultNode errorNode5 = new CheckResultNode();
                    errorNode5.copyFrom(node);
                    errorNode5.setErrorType(1);
                    errorNode5.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u6807\u51c6\u5dee\u989d\u8868\u7684\u7236\u8282\u70b9\u3010%s\u3011\u4e0d\u662f\u5bf9\u5e94\u7684\u6807\u51c6\u6c47\u603b\u8868", node.getUnitTitle(), node.getOrgCode(), node.getParentFJDCode()));
                    errorList.add(errorNode5);
                }
                if (!allEntityCodesMap.containsKey(node.getQydmCode() + checkContext.getBZHZBBLX())) {
                    CheckResultNode errorNode6 = new CheckResultNode();
                    errorNode6.copyFrom(node);
                    errorNode6.setErrorType(1);
                    errorNode6.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u6807\u51c6\u5dee\u989d\u8868\u5bf9\u5e94\u7684\u6807\u51c6\u6c47\u603b\u8868\u3010%s7\u3011\u4e0d\u5b58\u5728", node.getUnitTitle(), node.getOrgCode(), node.getQydmCode(), checkContext.getBZHZBBLX()));
                    errorList.add(errorNode6);
                }
            } else if (checkContext.getBZHZBBLX().equalsIgnoreCase(node.getBblxCode()) || checkContext.getJTHZBBLX().equalsIgnoreCase(node.getBblxCode())) {
                if (fjdMap.containsKey(node.getUnitCode())) {
                    CheckResultNode errorNode7 = new CheckResultNode();
                    errorNode7.copyFrom(node);
                    errorNode7.setErrorType(1);
                    errorNode7.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u4ee3\u7801\u91cd\u590d", node.getUnitTitle(), node.getOrgCode()));
                    errorList.add(errorNode7);
                } else {
                    fjdMap.put(node.getUnitCode(), node.getParentCode());
                    fjdFjdList.put(node.getParentCode(), entityCodesMap.get(node.getParentCode()));
                    if (checkContext.getJTHZBBLX().equalsIgnoreCase(node.getBblxCode()) && !allEntityCodesMap.containsKey(node.getQydmCode() + checkContext.getJTCEBBLX())) {
                        CheckResultNode errorNode8 = new CheckResultNode();
                        errorNode8.copyFrom(node);
                        errorNode8.setErrorType(1);
                        errorNode8.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u96c6\u56e2\u6c47\u603b\u8868\u5bf9\u5e94\u7684\u96c6\u56e2\u5dee\u989d\u8868\u3010%s%s\u3011\u4e0d\u5b58\u5728", node.getUnitTitle(), node.getOrgCode(), node.getQydmCode(), checkContext.getJTCEBBLX()));
                        errorList.add(errorNode8);
                    }
                }
            }
        }
    }

    private void checkParentNodes(EnityTreeCheckContext checkContext, Map<String, IEntityAttribute> fieldMap, List<CheckResultNode> errorList, CheckNodeList entityNodes, AsyncTaskMonitor monitor) {
        CheckResultNode errorNode;
        Map<String, String> fjdMap = entityNodes.getFjdMap();
        boolean isDoJTTreeMaintain = checkContext.getDoJTTreeMaintain();
        for (String nodeCode : fjdMap.keySet()) {
            CheckResultNode node = entityNodes.getEntityCodesMap().get(nodeCode);
            if (isDoJTTreeMaintain && checkContext.getJTHZBBLX().equalsIgnoreCase(node.getBblxCode()) && !entityNodes.getHasBBRec().containsKey(nodeCode)) {
                errorNode = new CheckResultNode();
                if (entityNodes.getEntityCodesMap().containsKey(nodeCode)) {
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s\u5355\u4f4d%s  \u7f3a\u5c11\u672c\u90e8\u5355\u4f4d", node.getUnitTitle(), node.getOrgCode()));
                    errorList.add(errorNode);
                } else {
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s  \u7f3a\u5c11\u672c\u90e8\u5355\u4f4d", nodeCode));
                    errorList.add(errorNode);
                    logger.info("\u6570\u636e\u5f02\u5e38\u8bf7\u68c0\u67e5\uff1a" + nodeCode);
                }
            } else if (!entityNodes.getHasChild().containsKey(nodeCode)) {
                errorNode = new CheckResultNode();
                if (entityNodes.getEntityCodesMap().containsKey(nodeCode)) {
                    errorNode.copyFrom(node);
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s   \u7f3a\u5c11\u5b50\u8282\u70b9", nodeCode));
                    errorList.add(errorNode);
                } else {
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s   \u7f3a\u5c11\u5b50\u8282\u70b9", nodeCode));
                    errorList.add(errorNode);
                    logger.info("\u6570\u636e\u5f02\u5e38\u8bf7\u68c0\u67e5\uff1a" + nodeCode);
                }
            }
            if (monitor == null || !monitor.isCancel()) continue;
            monitor.canceled(null, null);
            return;
        }
        Iterator<String> iterator = fjdMap.keySet().iterator();
        while (iterator.hasNext()) {
            String nodeCode;
            String sTmp = nodeCode = iterator.next();
            if (StringUtils.isNotEmpty((String)(sTmp = this.checkExistsRing(sTmp, entityNodes)))) {
                errorNode = new CheckResultNode();
                if (entityNodes.getEntityCodesMap().containsKey(nodeCode)) {
                    errorNode.copyFrom(entityNodes.getEntityCodesMap().get(nodeCode));
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s   \u5f62\u6210\u73af\u94fe", nodeCode));
                    errorList.add(errorNode);
                    fjdMap.put(nodeCode, "");
                } else {
                    errorNode.setErrorType(1);
                    errorNode.setErrorMsg(String.format("%s   \u5f62\u6210\u73af\u94fe", nodeCode));
                    errorList.add(errorNode);
                    logger.info("\u6570\u636e\u5f02\u5e38\u8bf7\u68c0\u67e5\uff1a" + nodeCode);
                }
            }
            if (monitor == null || !monitor.isCancel()) continue;
            monitor.canceled(null, null);
            return;
        }
        if (isDoJTTreeMaintain) {
            for (int i = 0; i < entityNodes.getEntiyNodes().size(); ++i) {
                CheckResultNode node = entityNodes.getEntiyNodes().get(i);
                String sFjd = node.getParentCode();
                if (StringUtils.isEmpty((String)sFjd) || !checkContext.getJTHZBBLX().equalsIgnoreCase(node.getParentBblx())) {
                    sFjd = "";
                } else {
                    String sTmp;
                    while (true) {
                        if (!fjdMap.containsKey(sFjd)) {
                            sFjd = "";
                            break;
                        }
                        sTmp = fjdMap.get(sFjd);
                        if (StringUtils.isEmpty((String)sTmp) || !entityNodes.getEntityCodesMap().containsKey(sTmp) || !checkContext.getJTHZBBLX().equalsIgnoreCase(entityNodes.getEntityCodesMap().get(sTmp).getBblxCode())) break;
                        sFjd = sTmp;
                    }
                    if (StringUtils.isNotEmpty((String)sFjd)) {
                        sTmp = node.getZbdmCode();
                        if ("0000000000000000000000000000000000000".substring(0, sTmp.length()).equalsIgnoreCase(sTmp)) {
                            sTmp = "";
                        }
                        if (!sFjd.substring(0, sFjd.length() - 1).equalsIgnoreCase(sTmp)) {
                            CheckResultNode errorNode2 = new CheckResultNode();
                            errorNode2.copyFrom(node);
                            errorNode2.setErrorType(1);
                            errorNode2.setErrorMsg(String.format("%s\u5355\u4f4d%s   \u603b\u90e8\u4ee3\u7801\u3010%s\u3011\u4e0e\u6811\u5f62\u7ed3\u6784\u5f97\u5230\u7684\u603b\u90e8\u4ee3\u7801\u3010%s\u3011\u4e0d\u4e00\u81f4", node.getUnitTitle(), node.getOrgCode(), sTmp, sFjd));
                            errorList.add(errorNode2);
                        }
                    }
                }
                if (monitor == null || !monitor.isCancel()) continue;
                monitor.canceled(null, null);
                return;
            }
        }
    }

    private String checkExistsRing(String sNode, CheckNodeList entityNodes) {
        String result = "";
        while (StringUtils.isNotEmpty((String)sNode)) {
            result = result + sNode + " , ";
            sNode = entityNodes.getFjdMap().containsKey(sNode) ? entityNodes.getFjdMap().get(sNode) : (entityNodes.getEntityCodesMap().containsKey(sNode) ? entityNodes.getEntityCodesMap().get(sNode).getParentCode() : "");
            if (!StringUtils.isNotEmpty((String)sNode) || (" , " + result).indexOf(" , " + sNode + " , ") < 0) continue;
            result = result + sNode;
            return result;
        }
        result = "";
        return result;
    }

    private CheckResultNode getNodeInfo(EnityTreeCheckContext checkContext, IEntityRow entityRow, Map<String, IEntityRow> entityCodesMap, Map<String, IEntityAttribute> fieldMap) {
        CheckResultNode node = new CheckResultNode();
        this.writeNodeInfo(checkContext, node, entityRow, fieldMap);
        if (StringUtils.isNotEmpty((String)node.getParentCode()) && entityCodesMap.containsKey(node.getParentCode())) {
            this.writeNodeParent(checkContext, node, entityCodesMap.get(node.getParentCode()), fieldMap);
        }
        return node;
    }

    private void writeNodeInfo(EnityTreeCheckContext checkContext, CheckResultNode node, IEntityRow entityRow, Map<String, IEntityAttribute> fieldMap) {
        node.setUnitKey(entityRow.getEntityKeyData());
        node.setUnitCode(entityRow.getEntityKeyData());
        node.setOrgCode(entityRow.getCode());
        node.setUnitTitle(entityRow.getTitle());
        node.setUnitZdm(entityRow.getCode());
        if (fieldMap.containsKey(checkContext.getBBLXField())) {
            node.setBblxCode(entityRow.getAsString(checkContext.getBBLXField()));
        }
        if (fieldMap.containsKey(checkContext.getDWDMField()) && StringUtils.isNotEmpty((String)entityRow.getAsString(checkContext.getDWDMField()))) {
            node.setQydmCode(entityRow.getAsString(checkContext.getDWDMField()));
        } else if (fieldMap.containsKey(checkContext.getDWDMField2()) && StringUtils.isNotEmpty((String)entityRow.getAsString(checkContext.getDWDMField2()))) {
            node.setQydmCode(entityRow.getAsString(checkContext.getDWDMField2()));
        }
        if (fieldMap.containsKey(checkContext.getSJDMField())) {
            node.setSjdmCode(entityRow.getAsString(checkContext.getSJDMField()));
        }
        if (fieldMap.containsKey(checkContext.getZBDMField())) {
            node.setZbdmCode(entityRow.getAsString(checkContext.getZBDMField()));
        }
        if (fieldMap.containsKey("orgcode")) {
            node.setOrgCode(entityRow.getAsString("orgcode"));
        }
        if ("-".equalsIgnoreCase(entityRow.getParentEntityKey())) {
            node.setParentCode(null);
        } else {
            node.setParentCode(entityRow.getParentEntityKey());
        }
        node.setParentKey(entityRow.getParentEntityKey());
        if (StringUtils.isEmpty((String)node.getOrgCode())) {
            node.setOrgCode(node.getUnitCode());
        }
    }

    private void writeNodeParent(EnityTreeCheckContext checkContext, CheckResultNode node, IEntityRow entityRow, Map<String, IEntityAttribute> fieldMap) {
        node.setParentKey(entityRow.getEntityKeyData());
        node.setParentCode(entityRow.getEntityKeyData());
        node.setParentOrgCode(entityRow.getCode());
        node.setParentTitle(entityRow.getTitle());
        node.setParentZdm(entityRow.getCode());
        if (fieldMap.containsKey(checkContext.getBBLXField())) {
            node.setParentBblx(entityRow.getAsString(checkContext.getBBLXField()));
        }
        if (fieldMap.containsKey(checkContext.getDWDMField()) && StringUtils.isNotEmpty((String)entityRow.getAsString(checkContext.getDWDMField()))) {
            node.setParentQydm(entityRow.getAsString(checkContext.getDWDMField()));
        } else if (fieldMap.containsKey(checkContext.getDWDMField2()) && StringUtils.isNotEmpty((String)entityRow.getAsString(checkContext.getDWDMField2()))) {
            node.setParentQydm(entityRow.getAsString(checkContext.getDWDMField2()));
        }
        if (fieldMap.containsKey(checkContext.getSJDMField())) {
            node.setParentSjdm(entityRow.getAsString(checkContext.getSJDMField()));
        }
        if (fieldMap.containsKey(checkContext.getZBDMField())) {
            node.setParentZbdm(entityRow.getAsString(checkContext.getZBDMField()));
        }
        if (fieldMap.containsKey("orgcode")) {
            node.setParentOrgCode(entityRow.getAsString("orgcode"));
        }
        if (StringUtils.isEmpty((String)node.getParentOrgCode())) {
            node.setParentOrgCode(node.getParentCode());
        }
    }

    private void writeNodeParentByFjd(CheckResultNode node, CheckResultNode fjdNode) {
        node.setParentKey(fjdNode.getUnitKey());
        node.setParentCode(fjdNode.getUnitCode());
        node.setParentTitle(fjdNode.getUnitTitle());
        node.setParentZdm(fjdNode.getUnitZdm());
        node.setParentBblx(fjdNode.getBblxCode());
        node.setParentQydm(fjdNode.getQydmCode());
        node.setParentSjdm(fjdNode.getSjdmCode());
        node.setParentZbdm(fjdNode.getZbdmCode());
        node.setParentOrgCode(fjdNode.getOrgCode());
        if (StringUtils.isEmpty((String)node.getParentOrgCode())) {
            node.setParentOrgCode(node.getParentCode());
        }
    }

    private void readSingleFMDMInfo(EnityTreeCheckContext checkContext, String taskKey, String formSchemeKey) {
        FMRepInfo fmdmInfo = this.singleParamService.getSingleFMDMInfo(taskKey, formSchemeKey);
        if (fmdmInfo != null) {
            checkContext.setFmdmInfo(fmdmInfo);
        }
    }
}


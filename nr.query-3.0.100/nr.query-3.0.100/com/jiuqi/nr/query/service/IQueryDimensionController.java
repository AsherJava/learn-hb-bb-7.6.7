/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.facade.TableGroupDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.bpm.impl.common.NrParameterUtils
 *  com.jiuqi.nr.bpm.impl.upload.dao.TableConstant
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.facade.TableGroupDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryEntityData;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.UploadStateDimension;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.EntityDataObject;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/query-Manager"})
public class IQueryDimensionController {
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private QueryDimensionHelper dimensionHelper;
    @Autowired
    TreeState flowWorkState;
    @Autowired
    private QueryEntityUtil queryEntityUtil;
    private static final Logger logger = LoggerFactory.getLogger(IQueryDimensionController.class);

    @Deprecated
    @RequestMapping(value={"/query-treebymasterkey"}, method={RequestMethod.GET})
    public String getTreeByMasterKey(String EntityViewKey) {
        try {
            return this.dimensionHelper.getContentByMasterKey(EntityViewKey);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-periodListByMasterKey"}, method={RequestMethod.GET})
    public String getPeriodListByMasterKey(String EntityViewKey) {
        try {
            return this.dimensionHelper.getPeriodListByMasterKey(EntityViewKey);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-inittree"}, method={RequestMethod.POST})
    public String getEntityTree(@RequestBody(required=false) List<QueryDimensionDefine> dimensions, @RequestParam(value="defaultStartDate") String start, @RequestParam(value="defaultEndDate") String end, @RequestParam(value="pType") String ptype) {
        try {
            QueryHelper helper = new QueryHelper();
            List<Object> periods = new ArrayList();
            DimensionValueSet dateValSet = new DimensionValueSet();
            try {
                periods = helper.queryPeriodsInDim(dimensions, start, end, ptype);
                if (periods.size() > 0) {
                    dateValSet.setValue("DATATIME", periods.get(periods.size() - 1));
                }
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            return this.dimensionHelper.initEntityTree(dimensions.get(0), dateValSet);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-getSelectScop"}, method={RequestMethod.POST})
    public String getFieldSelectScop(@RequestBody QuerySelectField[] fields) {
        String scop = "OneTable";
        try {
            LinkedHashMap<String, Integer> tableFields = new LinkedHashMap<String, Integer>();
            for (QuerySelectField field : fields) {
                if (field.getIsMaster() == "true") continue;
                String tableName = field.getTableName();
                if (!tableFields.containsKey(tableName)) {
                    tableFields.put(tableName, 0);
                }
                int count = (Integer)tableFields.get(tableName) + 1;
                tableFields.put(tableName, count);
            }
            int flag = 0;
            for (String tn : tableFields.keySet()) {
                int selectFieldCount = (Integer)tableFields.get(tn);
                TableDefine tableDefine = this.nrParameterUtils.getTableDefineByCode(tn);
                List tfs = this.nrParameterUtils.getFieldsByTable(tableDefine.getKey());
                String[] bizFields = tableDefine.getBizKeyFieldsID();
                int tableFieldCount = tfs.size() - bizFields.length - 1;
                if (selectFieldCount < tableFieldCount) {
                    scop = tableFields.size() > 1 ? "MoreTable" : "OneTable";
                    return scop;
                }
                ++flag;
            }
            if (flag == tableFields.size()) {
                scop = tableFields.size() > 1 ? "MoreTableAll" : "OneTableAll";
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return scop;
    }

    @RequestMapping(value={"/query-treechildren"}, method={RequestMethod.POST})
    public List<QueryEntityData> getchilderenByEntityKey(@RequestBody(required=false) List<QueryDimensionDefine> dimensions, @RequestParam String EntityViewKey, @RequestParam(value="defaultStartDate") String start, @RequestParam(value="defaultEndDate") String end, @RequestParam(value="pType") String ptype, @RequestParam(value="entityCode") String entityCode) {
        try {
            QueryHelper helper = new QueryHelper();
            List<String> periods = helper.queryPeriodsInDim(dimensions, start, end, ptype);
            DimensionValueSet dateValSet = new DimensionValueSet();
            if (periods.size() > 0) {
                dateValSet.setValue("DATATIME", (Object)periods.get(periods.size() - 1));
            }
            String[] args = EntityViewKey.split(";");
            int startEntityIndex = 0;
            if (entityCode.indexOf(";laznode") >= 0) {
                args = entityCode.split(";");
                startEntityIndex = Integer.parseInt(args[2]);
            }
            return this.dimensionHelper.getChildren(args[0], args[1], dateValSet, startEntityIndex);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-treeparentpath"}, method={RequestMethod.POST})
    public QueryEntityData getParentPathNodesByCurNode(@RequestBody(required=false) List<QueryDimensionDefine> dimensions, @RequestParam String EntityViewKey, @RequestParam(value="defaultStartDate") String start, @RequestParam(value="defaultEndDate") String end, @RequestParam(value="pType") String ptype) {
        try {
            QueryHelper helper = new QueryHelper();
            List<String> periods = helper.queryPeriodsInDim(dimensions, start, end, ptype);
            DimensionValueSet dateValSet = new DimensionValueSet();
            if (periods.size() > 0) {
                dateValSet.setValue("DATATIME", (Object)periods.get(periods.size() - 1));
            }
            String[] args = EntityViewKey.split(";");
            return this.dimensionHelper.getParentPath(args[0], args[1], dateValSet);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-getmasterentity"}, method={RequestMethod.POST})
    public String getMasterEntity(@RequestBody Map<String, String> masterKeys) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String dimensionsKeys = masterKeys.get("dimensionsKeys");
            List<JSONObject> result = this.dimensionHelper.getMasterEntity(dimensionsKeys);
            return result == null ? "" : result.toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping(value={"/enum/query-getAllGroup"})
    public List<TableGroupDefine> getAllGroup() {
        List<TableGroupDefine> allGroups = null;
        try {
            allGroups = this.dimensionHelper.getAllEnumGroups();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allGroups;
    }

    @GetMapping(value={"/enum/query-getEnumTree"})
    public List<ITree<EntityDataObject>> getEnumTree() {
        List<ITree<EntityDataObject>> allGroups = null;
        try {
            allGroups = this.dimensionHelper.getEnumTree();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allGroups;
    }

    @RequestMapping(value={"/query-getdimensionbylink"}, method={RequestMethod.GET})
    public String getDimensionByLink(String linkKey) {
        try {
            linkKey = HtmlUtils.cleanUrlXSS((String)linkKey);
            QueryHelper helper = new QueryHelper();
            ObjectMapper mapper = new ObjectMapper();
            String dimension = mapper.writeValueAsString((Object)helper.createDimensionByLink(linkKey));
            return HtmlUtils.cleanUrlXSS((String)dimension);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u94fe\u63a5\u67e5\u8be2\u7ef4\u5ea6\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-getdimensionbyentityfieldkey"}, method={RequestMethod.GET})
    public String getDimensionByEntityFieldKey(String fieldKey) {
        try {
            fieldKey = HtmlUtils.cleanUrlXSS((String)fieldKey);
            QueryHelper helper = new QueryHelper();
            ObjectMapper mapper = new ObjectMapper();
            String dimension = mapper.writeValueAsString((Object)helper.createDimensionByEntityFieldKey(fieldKey));
            return HtmlUtils.cleanUrlXSS((String)dimension);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4e3b\u4f53\u6307\u6807\u67e5\u8be2\u7ef4\u5ea6\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-getuploaddimension"}, method={RequestMethod.GET})
    public QueryDimensionDefine getUploadStateDimension(String task, String scheme) {
        try {
            UploadStateDimension upDim = new UploadStateDimension();
            upDim.setTask(task);
            upDim.setScheme(scheme);
            FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(upDim.getScheme());
            TableDefine tableDefine = this.nrParameterUtils.getTableDefineByCode(TableConstant.getSysUploadStateTableName((FormSchemeDefine)formScheme));
            String key = tableDefine.getKey();
            TableModelDefine tableModel = this.iEntityMetaService.getTableModel(key);
            upDim.setTableName(tableModel.getName());
            QueryDimensionDefine qdim = new QueryDimensionDefine();
            qdim.setDimensionType(QueryDimensionType.QDT_UPLOADSTATUS);
            qdim.setLayoutType(QueryLayoutType.LYT_CONDITION);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String updimStr = objectMapper.writeValueAsString((Object)upDim);
                qdim.setExtend(updimStr);
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
            return qdim;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value={"/query-getallstates"}, method={RequestMethod.GET})
    public List<WorkFlowTreeState> getAllStatus(String scheme) {
        ArrayList<WorkFlowTreeState> states = new ArrayList<WorkFlowTreeState>();
        if (StringUtil.isNullOrEmpty((String)scheme)) {
            logger.info("\u65e0\u65b9\u6848\u52a0\u8f7d\u72b6\u6001\u5217\u8868");
            states.add(this.newstate(UploadState.ORIGINAL_UPLOAD, "\u672a\u4e0a\u62a5"));
            states.add(this.newstate(UploadState.ORIGINAL_SUBMIT, "\u672a\u9001\u5ba1"));
            states.add(this.newstate(UploadState.SUBMITED, "\u5df2\u9001\u5ba1"));
            states.add(this.newstate(UploadState.RETURNED, "\u5df2\u9000\u5ba1"));
            states.add(this.newstate(UploadState.UPLOADED, "\u5df2\u4e0a\u62a5"));
            states.add(this.newstate(UploadState.CONFIRMED, "\u5df2\u786e\u8ba4"));
            states.add(this.newstate(UploadState.REJECTED, "\u5df2\u9000\u56de"));
        } else {
            List statesTemp = this.flowWorkState.getWorkFlowActions(scheme);
            for (WorkFlowTreeState s : statesTemp) {
                states.add(this.newstate(UploadState.valueOf((String)s.getCode()), s.getTitle()));
            }
            logger.info("\u83b7\u53d6\u65b9\u6848\u72b6\u6001\u5217\u8868\uff1a" + scheme);
        }
        return states;
    }

    private WorkFlowTreeState newstate(UploadState state, String title) {
        WorkFlowTreeState states = new WorkFlowTreeState();
        String name = state.name();
        states.setCode(name);
        states.setTitle(title);
        return states;
    }

    @RequestMapping(value={"/query-getDefaultPeriod"}, method={RequestMethod.GET})
    public String getDefaultPeriod(String schemeKey, String viewKey, String fromPeriod, String toPeriod) {
        FormSchemeDefine scheme = this.dimensionHelper.getScheme(schemeKey);
        try {
            String period = QueryHelper.getCurrentPeriod(scheme.getPeriodType().type(), scheme.getPeriodOffset(), fromPeriod, toPeriod, viewKey);
            return period;
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u9ed8\u8ba4\u65f6\u671f\u83b7\u53d6\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex.getMessage()));
            JSONObject periods = new JSONObject();
            PeriodWrapper currentPeriod = new PeriodWrapper(scheme.getFromPeriod());
            periods.put("code", (Object)currentPeriod.toString());
            periods.put("title", (Object)currentPeriod.toTitleString());
            return periods.toString();
        }
    }

    class uploadstates {
        public String name;
        public String title;

        uploadstates() {
        }
    }
}


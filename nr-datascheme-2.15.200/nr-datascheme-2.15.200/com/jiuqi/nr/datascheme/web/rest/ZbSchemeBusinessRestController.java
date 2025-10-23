/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.zb.scheme.internal.tree.INode
 *  com.jiuqi.nr.zb.scheme.internal.tree.ITree
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IZbSchemeBusinessService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.base.DataFieldVO;
import com.jiuqi.nr.datascheme.web.base.FieldTreeNode;
import com.jiuqi.nr.datascheme.web.facade.ProgressVO;
import com.jiuqi.nr.datascheme.web.facade.StepVO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6307\u6807\u4f53\u7cfb"})
public class ZbSchemeBusinessRestController {
    private static final Logger log = LoggerFactory.getLogger(ZbSchemeBusinessRestController.class);
    @Autowired
    private IZbSchemeBusinessService zbSchemeBusinessService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    private IRuntimeDataSchemeTreeService runtimeDataSchemeTreeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDesignDataSchemeTreeService designDataSchemeTreeService;

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u5206\u7ec4\u6811\u5f62")
    @GetMapping(value={"query_zb_group_tree"})
    public List<ITree<INode>> queryZbGroupTree(@RequestParam String tableKey, @RequestParam(required=false) String location) throws JQException {
        try {
            if ("undefined".equals(location)) {
                location = null;
            }
            return this.zbSchemeBusinessService.queryZbGroupTree(tableKey, location);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u5173\u952e\u5b57\u641c\u7d22\u6307\u6807")
    @GetMapping(value={"filter_zb_group_tree"})
    public List<ITree<INode>> filterZbInfo(@RequestParam String tableKey, @RequestParam(required=false) String keyword) throws JQException {
        try {
            return this.zbSchemeBusinessService.filterZbInfo(tableKey, keyword);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u7684\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"query_sync_context"})
    public List<DesignDataScheme> querySyncContext(@RequestParam String zbSchemeKey, @RequestParam String zbSchemePeriod) throws JQException {
        try {
            return this.zbSchemeBusinessService.getSyncDataScheme(zbSchemeKey, zbSchemePeriod);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u540c\u6b65\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"sync_datascheme"})
    public void syncDataScheme(@RequestBody List<String> keys) throws JQException {
        try {
            if (CollectionUtils.isEmpty(keys)) {
                return;
            }
            this.zbSchemeBusinessService.pullZbInfo(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5f15\u7528\u6307\u6807")
    @PostMapping(value={"refer_zb"})
    public void referZbInfo(@RequestParam String tableKey, String ... codes) throws JQException {
        try {
            if (codes != null) {
                this.zbSchemeBusinessService.referZbInfo(tableKey, Arrays.asList(codes));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u62c9\u53d6\u6307\u6807")
    @GetMapping(value={"pull_zb_info"})
    public void pullZbInfo(@RequestParam String dataSchemeKey) throws JQException {
        try {
            if (dataSchemeKey != null) {
                this.zbSchemeBusinessService.pullZbInfo(Collections.singletonList(dataSchemeKey));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5904\u7406\u8ba1\u7b97\u6307\u6807")
    @GetMapping(value={"execute_cal_formula"})
    public String executeCalFormula(@RequestParam String dataSchemeKey, @RequestParam(required=false) String startPeriod, @RequestParam(required=false) String endPeriod) throws JQException {
        try {
            DataSchemeCalcTask task = new DataSchemeCalcTask(dataSchemeKey);
            task.setStartPeriod(startPeriod);
            task.setEndPeriod(endPeriod);
            return this.zbSchemeBusinessService.executeCalFormula(task);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, (Throwable)e);
        }
    }

    @GetMapping(value={"cal_zb_progress/{key}"})
    public ProgressVO queryCalZbProgress(@PathVariable String key) throws JQException {
        try {
            ProgressItem progress = this.zbSchemeBusinessService.queryCalZbProgress(key);
            ProgressVO progressVO = new ProgressVO();
            if (progress != null) {
                progressVO.setProgressId(progress.getProgressId());
                progressVO.setMessage(progress.getMessage());
                progressVO.setOver(progress.isFinished());
                progressVO.setCurStep(progress.getCurrentStep());
                progressVO.setFail(progress.isFailed());
                ArrayList<StepVO> infos = new ArrayList<StepVO>();
                progressVO.setInfos(infos);
                List titles = progress.getStepTitles();
                if (titles != null) {
                    for (int i = 0; i < titles.size(); ++i) {
                        StepVO step = ZbSchemeBusinessRestController.getStepVO(titles, i, progress);
                        infos.add(step);
                    }
                }
            }
            return progressVO;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, (Throwable)e);
        }
    }

    @NotNull
    private static StepVO getStepVO(List<String> titles, int i, ProgressItem progress) {
        StepVO step = new StepVO();
        String title = titles.get(i);
        step.setTitle(title);
        if (i == progress.getCurrentStep()) {
            step.setMessage(progress.getMessage());
            step.setPercentage(progress.getCurrentProgess());
            step.setFail(progress.isFailed());
        } else if (i < progress.getCurrentStep()) {
            step.setMessage(title + "\u6210\u529f!");
            step.setPercentage(100.0);
        } else {
            step.setMessage(title);
        }
        return step;
    }

    @PostMapping(value={"tree/field_tree/init"})
    public List<com.jiuqi.nr.datascheme.api.core.ITree<DataSchemeNode>> runtimeFieldTreeInitData(@RequestBody FieldTreeNode node) {
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(node.getKey());
        if (dataScheme == null) {
            log.debug("\u672a\u627e\u5230\u6570\u636e\u65b9\u6848\uff1a{}", (Object)node.getKey());
            return Collections.emptyList();
        }
        if (!this.dataSchemeAuthService.canReadScheme(dataScheme.getKey())) {
            log.debug("\u65e0\u6743\u9650\u67e5\u770b\u6570\u636e\u65b9\u6848\uff1a{}", (Object)dataScheme.getKey());
            return Collections.emptyList();
        }
        RunTaskSchemeNodeFilter filter = new RunTaskSchemeNodeFilter(dataScheme.getKey());
        int interestType = this.noField();
        List<com.jiuqi.nr.datascheme.api.core.ITree<DataSchemeNode>> rootTree = this.designDataSchemeTreeService.getRootTree(dataScheme.getKey(), interestType, filter);
        if (CollectionUtils.isEmpty(rootTree)) {
            return Collections.emptyList();
        }
        rootTree.get(0).setSelected(true);
        rootTree.get(0).setExpanded(true);
        return rootTree;
    }

    @PostMapping(value={"tree/field_tree/loadGroup"})
    public List<com.jiuqi.nr.datascheme.api.core.ITree<DataSchemeNode>> runtimeFieldTreeLoadDataGroup(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO parent = param.getDataSchemeNode();
        int interestType = this.noField();
        return this.designDataSchemeTreeService.getChildTree(parent, interestType, null);
    }

    private int noField() {
        int interestType = 0;
        block3: for (NodeType value : NodeType.values()) {
            switch (value) {
                case DIM: 
                case GROUP: 
                case SCHEME: 
                case ACCOUNT_TABLE: 
                case TABLE: 
                case DETAIL_TABLE: 
                case MUL_DIM_TABLE: 
                case MD_INFO: 
                case FMDM_TABLE: {
                    interestType |= value.getValue();
                    continue block3;
                }
            }
        }
        return interestType;
    }

    @PostMapping(value={"tree/field_tree/loadFields"})
    public List<DataFieldVO> fieldTreeLoadFields(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO dataSchemeNode = param.getDataSchemeNode();
        ArrayList<DataFieldVO> cildren = new ArrayList<DataFieldVO>();
        int interestType = this.allField();
        List<com.jiuqi.nr.datascheme.api.core.ITree<DataSchemeNode>> childTree = this.designDataSchemeTreeService.getChildTree(dataSchemeNode, interestType, null);
        HashSet<String> infoKeys = new HashSet<String>();
        List allDataTable = this.designDataSchemeService.getAllDataTable();
        for (DesignDataTable designDataTable : allDataTable) {
            if (!DataTableType.MD_INFO.equals((Object)designDataTable.getDataTableType())) continue;
            infoKeys.add(designDataTable.getKey());
        }
        for (com.jiuqi.nr.datascheme.api.core.ITree iTree : childTree) {
            if (!iTree.isLeaf()) continue;
            DataFieldVO field = new DataFieldVO();
            field.setKey(((DataSchemeNode)iTree.getData()).getKey());
            field.setCode(((DataSchemeNode)iTree.getData()).getCode());
            field.setTitle(((DataSchemeNode)iTree.getData()).getTitle());
            field.setTableCode(dataSchemeNode.getCode());
            DesignDataFieldDO data = null;
            try {
                if (((DataSchemeNode)iTree.getData()).getData() instanceof DesignDataFieldDO) {
                    data = (DesignDataFieldDO)((DataSchemeNode)iTree.getData()).getData();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (((DataSchemeNode)iTree.getData()).getType() == NodeType.ENTITY_ATTRIBUTE.getValue() || null != data && infoKeys.contains(data.getDataTableKey())) {
                field.setReferFieldKey("entity");
            } else {
                field.setReferFieldKey("field");
            }
            cildren.add(field);
        }
        return cildren;
    }

    private int allField() {
        int interestType = 0;
        for (NodeType value : NodeType.values()) {
            if (value.getValue() <= NodeType.SCHEME_GROUP.getValue() || value.getValue() == NodeType.FMDM_TABLE.getValue()) continue;
            interestType |= value.getValue();
        }
        return interestType;
    }

    @GetMapping(value={"zb_scheme/zb_refer"})
    public Boolean checkZbRefer(@RequestParam String zbScheme, @RequestParam String zbCode) throws JQException {
        try {
            return this.zbSchemeBusinessService.checkZbRefer(zbScheme, zbCode);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @GetMapping(value={"query_cal_result"})
    public DataSchemeCalResult queryCalResult(@RequestParam String dataSchemeKey) throws JQException {
        try {
            return this.zbSchemeBusinessService.queryCalResult(dataSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    class RunTaskSchemeNodeFilter
    implements NodeFilter {
        private final String dataSchemeKey;
        private final Set<String> dims = new HashSet<String>();

        public boolean test(ISchemeNode t) {
            int type = t.getType();
            Object data = t.getData();
            if (NodeType.DIM.getValue() == type) {
                if (!t.getKey().startsWith(this.dataSchemeKey + ":")) {
                    return false;
                }
                if (data instanceof DataDimension) {
                    return ((DataDimension)data).getDimensionType() != DimensionType.PERIOD;
                }
            }
            return true;
        }

        public RunTaskSchemeNodeFilter(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }
    }
}


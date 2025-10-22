/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.monitor.api.common.MonitorExcelScopeEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorExcelTypeEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorOrgTypeEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorExportTableVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.monitor.impl.service.impl;

import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.monitor.api.common.MonitorExcelScopeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorExcelTypeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorOrgTypeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorExportTableVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO;
import com.jiuqi.gcreport.monitor.impl.config.MonitorSceneCollectUtils;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorNrSchemeDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailDao;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailItemDao;
import com.jiuqi.gcreport.monitor.impl.dao.execute.MonitorExeDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailItemEO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorNrSchemeEO;
import com.jiuqi.gcreport.monitor.impl.process.MonitorSceneProcess;
import com.jiuqi.gcreport.monitor.impl.service.MonitorShowService;
import com.jiuqi.gcreport.monitor.impl.util.MonitorEcxelUtil;
import com.jiuqi.gcreport.monitor.impl.util.MonitorUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorShowServiceImpl
implements MonitorShowService {
    @Autowired
    private MonitorExeDao monitorExeDao;
    @Autowired
    private MonitorNrSchemeDao monitorNrSchemeDao;
    @Autowired
    private MonitorSchemeConfigDetailDao monitorSchemeConfigDetailDao;
    @Autowired
    private MonitorSchemeConfigDetailItemDao monitorSchemeConfigDetailItemDao;
    private Integer NOT_USE_NUM = -1;
    private String COMMON_PLACEHOLDER = "--";
    private String COMMON_COLOR = "#727272";
    private String DEFAULT_OK_COLOR = "#19C919";
    private String DEFAULT_NOT_COLOR = "#EA1A1A";

    @Override
    public List<MonitorTableVO> monitorShowResult_V2(ConditionVO vo) {
        MonitorArgument argument = MonitorUtil.getMonitorArgsFromVO(vo);
        MonitorNrSchemeEO nrScheme = this.monitorNrSchemeDao.findByFormSchemeId(vo.getSchemeId());
        if (null == nrScheme) {
            throw new RuntimeException("\u672a\u627e\u5230\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848\u5bf9\u5e94\u7684\u7b56\u7565");
        }
        List<MonitorConfigDetailEO> strategys = this.monitorSchemeConfigDetailDao.findConfigDetailByMonitorId(nrScheme.getMonitorId());
        if (strategys.isEmpty()) {
            throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u76f8\u5173\u7b56\u7565\u914d\u7f6e");
        }
        List<String> tableNodes = this.getAllTableColumns(vo.getExeTemplate(), strategys);
        if (tableNodes.isEmpty()) {
            throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u76f8\u5339\u914d\u7684\u5173\u952e\u5217\uff01");
        }
        Map<String, MonitorConfigDetailEO> detailEoMap = strategys.stream().distinct().collect(Collectors.toMap(MonitorConfigDetailEO::getNodeCode, configDetailEo -> configDetailEo));
        HashMap<String, List<MonitorConfigDetailItemEO>> detailItemEoMap = new HashMap<String, List<MonitorConfigDetailItemEO>>(16);
        for (String s : tableNodes) {
            MonitorConfigDetailEO configDetailEo2 = detailEoMap.get(s);
            if (configDetailEo2 == null) continue;
            List<MonitorConfigDetailItemEO> configItems = this.monitorSchemeConfigDetailItemDao.getConfigDetailItemByConfigId(configDetailEo2.getId());
            detailItemEoMap.put(s, configItems);
        }
        HashMap<String, Map<String, MonitorStateEnum>> nodeDatas = new HashMap(16);
        YearPeriodObject yp = new YearPeriodObject(null, argument.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)argument.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List<GcOrgCacheVO> orgList = instance.listAllOrgByParentIdContainsSelf(argument.getOrgId());
        orgList = orgList.stream().filter(o -> !o.isStopFlag()).collect(Collectors.toList());
        List<String> list = orgList.stream().map(v -> v.getId()).collect(Collectors.toList());
        ForkJoinPool pool = new ForkJoinPool();
        NpContext context = NpContextHolder.getContext();
        MonitorSceneProcess processTask = new MonitorSceneProcess(tableNodes, detailEoMap, argument, list, context);
        nodeDatas = pool.invoke(processTask);
        List<MonitorTableVO> currentOrgTree = this.getCurrentOrgTree(orgList);
        this.setTableVO(currentOrgTree, tableNodes, nodeDatas, detailEoMap, detailItemEoMap, new MonitorTableVO(currentOrgTree.get(0).getOrgCacheVO()));
        return currentOrgTree;
    }

    private void setTableVO(List<MonitorTableVO> tree, List<String> nodes, Map<String, Map<String, MonitorStateEnum>> nodeDatas, Map<String, MonitorConfigDetailEO> detailEoMap, Map<String, List<MonitorConfigDetailItemEO>> detailItemEoMap, MonitorTableVO pTableVO) {
        for (MonitorTableVO monitorTableVO : tree) {
            if (monitorTableVO.getChildren().isEmpty()) {
                GcOrgKindEnum orgKind = monitorTableVO.getOrgCacheVO().getOrgKind();
                for (String s : nodes) {
                    MonitorConfigDetailEO detailEO = detailEoMap.get(s);
                    if (null != detailEO.getNodeOrgType() && detailEO.getNodeOrgType().intValue() == MonitorOrgTypeEnum.PARENT_ORG.getCode()) {
                        this.setDefaultData(monitorTableVO, s, nodeDatas, detailItemEoMap, pTableVO);
                        continue;
                    }
                    if (orgKind.equals((Object)GcOrgKindEnum.DIFFERENCE) && "dataPick".equals(detailEO.getNodeCode())) {
                        this.setDefaultData(monitorTableVO, s, nodeDatas, detailItemEoMap, pTableVO);
                        continue;
                    }
                    this.setCellData(monitorTableVO, s, nodeDatas, detailEoMap, detailItemEoMap, pTableVO);
                }
                continue;
            }
            this.setTableVO(monitorTableVO.getChildren(), nodes, nodeDatas, detailEoMap, detailItemEoMap, monitorTableVO);
            for (String s : nodes) {
                MonitorConfigDetailEO detailEO = detailEoMap.get(s);
                if (null != detailEO.getNodeOrgType() && detailEO.getNodeOrgType().intValue() == MonitorOrgTypeEnum.LEAF_ORG.getCode()) {
                    Map pDoneNumMap = pTableVO.getDoneNumMap();
                    Map pTotalNumMap = pTableVO.getTotalNumMap();
                    Map pDirectDoneNumMap = pTableVO.getDirectDoneNumMap();
                    Map pDirectTotalNumMap = pTableVO.getDirectTotalNumMap();
                    pDoneNumMap.put(s, this.getKey(monitorTableVO.getDoneNumMap(), s, 0) + this.getKey(pDoneNumMap, s, 0));
                    pTotalNumMap.put(s, this.getKey(monitorTableVO.getTotalNumMap(), s, 0) + this.getKey(pTotalNumMap, s, 0));
                    pDirectDoneNumMap.put(s, this.getKey(pDirectDoneNumMap, s, 0));
                    pDirectTotalNumMap.put(s, this.getKey(pDirectTotalNumMap, s, 0));
                    this.setDefaultData(monitorTableVO, s, nodeDatas, detailItemEoMap, pTableVO);
                    continue;
                }
                this.setHBOrgCellData(monitorTableVO, s, nodeDatas, detailEoMap, detailItemEoMap, pTableVO);
            }
        }
    }

    private void setDefaultData(MonitorTableVO monitorTableVO, String nodeCode, Map<String, Map<String, MonitorStateEnum>> nodeDatas, Map<String, List<MonitorConfigDetailItemEO>> detailItemEoMap, MonitorTableVO pTableVO) {
        Map stateMap = monitorTableVO.getStateMap();
        Map colorMap = monitorTableVO.getColorMap();
        Map titleMap = monitorTableVO.getTitleMap();
        stateMap.put(nodeCode, this.NOT_USE_NUM);
        colorMap.put(nodeCode, this.COMMON_COLOR);
        titleMap.put(nodeCode, this.COMMON_PLACEHOLDER);
        Map pTotalNumMap = pTableVO.getTotalNumMap();
        Map pDoneNumMap = pTableVO.getDoneNumMap();
        Map pDirectDoneNumMap = pTableVO.getDirectDoneNumMap();
        Map pDirectTotalNumMap = pTableVO.getDirectTotalNumMap();
        this.getKey(pDoneNumMap, nodeCode, 0);
        this.getKey(pTotalNumMap, nodeCode, 0);
        this.getKey(pDirectDoneNumMap, nodeCode, 0);
        this.getKey(pDirectTotalNumMap, nodeCode, 0);
    }

    private void setHBOrgCellData(MonitorTableVO monitorTableVO, String nodeCode, Map<String, Map<String, MonitorStateEnum>> nodeDatas, Map<String, MonitorConfigDetailEO> detailEoMap, Map<String, List<MonitorConfigDetailItemEO>> detailItemEoMap, MonitorTableVO pTableVO) {
        Map doneNumMap;
        Map totalNumMap;
        Map stateMap = monitorTableVO.getStateMap();
        Map colorMap = monitorTableVO.getColorMap();
        Map titleMap = monitorTableVO.getTitleMap();
        Map isLinkMap = monitorTableVO.getIsLinkMap();
        Map<String, MonitorStateEnum> idMap = nodeDatas.get(nodeCode);
        MonitorStateEnum stateEnum = idMap.get(monitorTableVO.getOrgId());
        Map pTotalNumMap = pTableVO.getTotalNumMap();
        Map pDoneNumMap = pTableVO.getDoneNumMap();
        Map pDirectDoneNumMap = pTableVO.getDirectDoneNumMap();
        Map pDirectTotalNumMap = pTableVO.getDirectTotalNumMap();
        Integer code = stateEnum.getCode();
        List<MonitorConfigDetailItemEO> itemEOS = detailItemEoMap.get(nodeCode);
        MonitorConfigDetailItemEO itemEO = itemEOS.stream().filter(eo -> eo.getNodeStateCode().split("/")[0].equals(String.valueOf(code))).findFirst().get();
        colorMap.put(nodeCode, itemEO.getcNodeColor());
        titleMap.put(nodeCode, itemEO.getcNodeNewName());
        stateMap.put(nodeCode, code);
        int plusNum = code == 1 ? 1 : 0;
        MonitorConfigDetailEO detailEO = detailEoMap.get(nodeCode);
        if (null != detailEO.getNodeOrgType() && detailEO.getNodeOrgType().intValue() == MonitorOrgTypeEnum.PARENT_ORG.getCode()) {
            pTotalNumMap.put(nodeCode, (Integer)monitorTableVO.getTotalNumMap().get(nodeCode) + 1 + this.getKey(pTotalNumMap, nodeCode, 0));
            pDoneNumMap.put(nodeCode, (Integer)monitorTableVO.getDoneNumMap().get(nodeCode) + this.getKey(pDoneNumMap, nodeCode, 0) + plusNum);
            totalNumMap = monitorTableVO.getTotalNumMap();
            doneNumMap = monitorTableVO.getDoneNumMap();
            totalNumMap.put(nodeCode, this.getKey(monitorTableVO.getTotalNumMap(), nodeCode, 0) + 1);
            doneNumMap.put(nodeCode, this.getKey(monitorTableVO.getDoneNumMap(), nodeCode, 0) + plusNum);
        } else {
            totalNumMap = monitorTableVO.getTotalNumMap();
            doneNumMap = monitorTableVO.getDoneNumMap();
            totalNumMap.put(nodeCode, this.getKey(monitorTableVO.getTotalNumMap(), nodeCode, 0) + 1);
            doneNumMap.put(nodeCode, this.getKey(monitorTableVO.getDoneNumMap(), nodeCode, 0) + plusNum);
            pTotalNumMap.put(nodeCode, this.getKey(monitorTableVO.getTotalNumMap(), nodeCode, 0) + this.getKey(pTotalNumMap, nodeCode, 0));
            pDoneNumMap.put(nodeCode, this.getKey(monitorTableVO.getDoneNumMap(), nodeCode, plusNum) + this.getKey(pDoneNumMap, nodeCode, 0));
        }
        Map directTotalNumMap = monitorTableVO.getDirectTotalNumMap();
        Map directDoneNumMap = monitorTableVO.getDirectDoneNumMap();
        directTotalNumMap.put(nodeCode, this.getKey(monitorTableVO.getDirectTotalNumMap(), nodeCode, 0) + 1);
        directDoneNumMap.put(nodeCode, this.getKey(monitorTableVO.getDirectDoneNumMap(), nodeCode, 0) + plusNum);
        pDirectDoneNumMap.put(nodeCode, this.getKey(pDirectDoneNumMap, nodeCode, 0) + plusNum);
        pDirectTotalNumMap.put(nodeCode, 1 + this.getKey(pDirectTotalNumMap, nodeCode, 0));
    }

    private void setCellData(MonitorTableVO monitorTableVO, String nodeCode, Map<String, Map<String, MonitorStateEnum>> nodeDatas, Map<String, MonitorConfigDetailEO> detailEoMap, Map<String, List<MonitorConfigDetailItemEO>> detailItemEoMap, MonitorTableVO pTableVO) {
        Map pTotalNumMap = pTableVO.getTotalNumMap();
        Map pDoneNumMap = pTableVO.getDoneNumMap();
        Map pDirectDoneNumMap = pTableVO.getDirectDoneNumMap();
        Map pDirectTotalNumMap = pTableVO.getDirectTotalNumMap();
        Map stateMap = monitorTableVO.getStateMap();
        Map colorMap = monitorTableVO.getColorMap();
        Map titleMap = monitorTableVO.getTitleMap();
        Map isLinkMap = monitorTableVO.getIsLinkMap();
        Map<String, MonitorStateEnum> idMap = nodeDatas.get(nodeCode);
        MonitorStateEnum stateEnum = idMap.get(monitorTableVO.getOrgId());
        if (null != stateEnum) {
            Integer code = stateEnum.getCode();
            List<MonitorConfigDetailItemEO> itemEOS = detailItemEoMap.get(nodeCode);
            MonitorConfigDetailItemEO itemEO = null;
            try {
                itemEO = itemEOS.stream().filter(eo -> eo.getNodeStateCode().split("/")[0].equals(String.valueOf(code))).findFirst().get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            colorMap.put(nodeCode, itemEO.getcNodeColor());
            titleMap.put(nodeCode, itemEO.getcNodeNewName());
            stateMap.put(nodeCode, code);
            Integer plusNum = code == 1 ? 1 : 0;
            pDoneNumMap.put(nodeCode, this.getKey(pDoneNumMap, nodeCode, 0) + plusNum);
            pTotalNumMap.put(nodeCode, this.getKey(pTotalNumMap, nodeCode, 0) + 1);
            pDirectDoneNumMap.put(nodeCode, this.getKey(pDirectDoneNumMap, nodeCode, 0) + plusNum);
            pDirectTotalNumMap.put(nodeCode, this.getKey(pDirectTotalNumMap, nodeCode, 0) + 1);
        } else {
            stateMap.put(nodeCode, this.NOT_USE_NUM);
            colorMap.put(nodeCode, this.COMMON_COLOR);
            titleMap.put(nodeCode, this.COMMON_PLACEHOLDER);
            pDoneNumMap.put(nodeCode, this.getKey(pDoneNumMap, nodeCode, 0));
            pTotalNumMap.put(nodeCode, this.getKey(pTotalNumMap, nodeCode, 0));
            pDirectDoneNumMap.put(nodeCode, this.getKey(pDirectDoneNumMap, nodeCode, 0));
            pDirectTotalNumMap.put(nodeCode, this.getKey(pDirectTotalNumMap, nodeCode, 0));
        }
    }

    private Integer getKey(Map<String, Integer> map, String key, Integer defult) {
        Integer o = map.get(key);
        if (null == o) {
            map.put(key, defult);
            return defult;
        }
        return o;
    }

    private List<MonitorTableVO> getCurrentOrgTree(List<GcOrgCacheVO> list) {
        Map<String, MonitorTableVO> datas = list.stream().collect(Collectors.toMap(GcOrgCacheVO::getId, MonitorTableVO::new));
        ArrayList<MonitorTableVO> tree = new ArrayList<MonitorTableVO>();
        for (GcOrgCacheVO orgToJsonVO : list) {
            MonitorTableVO pobj = datas.get(orgToJsonVO.getParentId());
            if (pobj != null) {
                pobj.getChildren().add(datas.get(orgToJsonVO.getId()));
                continue;
            }
            tree.add(datas.get(orgToJsonVO.getId()));
        }
        return tree;
    }

    private List<String> getAllTableColumns(String exeTemplateID, List<MonitorConfigDetailEO> strategys) {
        Map<String, Integer> integerMap = MonitorSceneCollectUtils.getAllMonitorScene().stream().collect(Collectors.toMap(scene -> scene.getMonitorScene().getCode(), MonitorScene::getOrder));
        List strategyNode = strategys.stream().filter(eo -> eo.getChecked() == 1).map(MonitorConfigDetailEO::getNodeCode).collect(Collectors.toList());
        return strategyNode.stream().sorted(Comparator.comparingInt(integerMap::get)).collect(Collectors.toList());
    }

    @Override
    public List<MonitorTableColumnVO> getMonitorColumns(ConditionVO vo) {
        ArrayList<MonitorTableColumnVO> columnVOList = new ArrayList<MonitorTableColumnVO>();
        columnVOList.add(new MonitorTableColumnVO("orgTitle", "\u7ec4\u7ec7\u673a\u6784"));
        MonitorArgument argument = MonitorUtil.getMonitorArgsFromVO(vo);
        MonitorNrSchemeEO nrScheme = this.monitorNrSchemeDao.findByFormSchemeId(vo.getSchemeId());
        if (null == nrScheme) {
            throw new RuntimeException("\u672a\u627e\u5230\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848\u5bf9\u5e94\u7684\u7b56\u7565");
        }
        List<MonitorConfigDetailEO> strategys = this.monitorSchemeConfigDetailDao.findConfigDetailByMonitorId(nrScheme.getMonitorId());
        if (strategys.isEmpty()) {
            throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u76f8\u5173\u7b56\u7565\u914d\u7f6e");
        }
        List<String> tableNodes = this.getAllTableColumns(vo.getExeTemplate(), strategys);
        if (tableNodes.isEmpty()) {
            throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u76f8\u5339\u914d\u7684\u5173\u952e\u5217\uff01");
        }
        Map<String, MonitorConfigDetailEO> allMap = strategys.stream().collect(Collectors.toMap(MonitorConfigDetailEO::getNodeCode, monitorConfigDetailEO -> monitorConfigDetailEO));
        columnVOList.addAll(tableNodes.stream().map(s -> {
            MonitorConfigDetailEO detailEO = (MonitorConfigDetailEO)((Object)((Object)allMap.get(s)));
            return new MonitorTableColumnVO(detailEO.getNodeCode(), detailEO.getReNodeTitle());
        }).collect(Collectors.toList()));
        return columnVOList;
    }

    @Override
    public void exportExcel(HttpServletResponse response, ConditionVO conditionVO) {
        List<MonitorTableVO> monitorTreeVO = this.monitorShowResult_V2(conditionVO);
        List<MonitorTableColumnVO> monitorColumns = this.getMonitorColumns(conditionVO);
        List<Map<String, Object>> exportTableVOS = this.convertMonitorTableVo2ExcelVO(monitorTreeVO, monitorColumns, conditionVO);
        try {
            this.exportExecl(response, exportTableVOS, monitorColumns, conditionVO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> convertMonitorTableVo2ExcelVO(List<MonitorTableVO> monitorTableVOS, List<MonitorTableColumnVO> monitorColumns, ConditionVO conditionVO) {
        MonitorExcelTypeEnum typeEnum = conditionVO.getExportType();
        MonitorExcelScopeEnum scopeEnum = conditionVO.getExportScope();
        ArrayList<MonitorTableVO> list = new ArrayList<MonitorTableVO>();
        this.tree2List(monitorTableVOS, list, scopeEnum, monitorTableVOS.get(0).getOrgCacheVO().getParents().length);
        ArrayList<Map<String, Object>> ret2 = new ArrayList<Map<String, Object>>();
        list.forEach(monitorTableVO -> {
            MonitorExportTableVO row = new MonitorExportTableVO();
            HashMap rowMap = new HashMap(16);
            monitorColumns.forEach(columnVO -> {
                String prop = columnVO.getProp();
                if ("orgTitle".equals(prop)) {
                    row.setOrgTitle(monitorTableVO.getOrgTitle());
                    rowMap.put("orgTitle", monitorTableVO.getOrgTitle());
                } else if (monitorTableVO.getTotalNumMap().isEmpty() || (Integer)monitorTableVO.getTotalNumMap().get(prop) == 0) {
                    rowMap.put(prop + "Num", "--");
                    rowMap.put(prop + "Percent", "--");
                    rowMap.put(prop + "State", "" + (typeEnum.equals((Object)MonitorExcelTypeEnum.DATASTYLE) ? (Serializable)monitorTableVO.getStateMap().get(prop) : (Serializable)monitorTableVO.getTitleMap().get(prop)));
                } else {
                    Integer doneNum = (Integer)monitorTableVO.getDoneNumMap().get(prop);
                    Integer total = (Integer)monitorTableVO.getTotalNumMap().get(prop);
                    rowMap.put(prop + "Num", doneNum + "/" + total);
                    rowMap.put(prop + "Percent", NumberUtils.parsePercent((double)NumberUtils.div((double)doneNum.intValue(), (double)total.intValue(), (int)4)));
                    rowMap.put(prop + "State", "" + (typeEnum.equals((Object)MonitorExcelTypeEnum.DATASTYLE) ? (Serializable)monitorTableVO.getStateMap().get(prop) : (Serializable)monitorTableVO.getTitleMap().get(prop)));
                }
            });
            ret2.add(rowMap);
        });
        return ret2;
    }

    private void tree2List(List<MonitorTableVO> monitorTableVOS, List<MonitorTableVO> list, MonitorExcelScopeEnum scopeEnum, int length) {
        int monitorTableVOSSize = monitorTableVOS.size();
        for (int i = 0; i < monitorTableVOSSize; ++i) {
            MonitorTableVO monitorTableVO = monitorTableVOS.get(i);
            List children = monitorTableVO.getChildren();
            this.setOrgTitle(list, monitorTableVO, length);
            if (scopeEnum.equals((Object)MonitorExcelScopeEnum.DIRECTCHILDREN) && i > 0 || children.size() <= 0) continue;
            this.tree2List(monitorTableVO.getChildren(), list, scopeEnum, length);
        }
    }

    private void setOrgTitle(List<MonitorTableVO> list, MonitorTableVO monitorTableVO, int plength) {
        GcOrgCacheVO orgCacheVO = monitorTableVO.getOrgCacheVO();
        int length = orgCacheVO.getParents().length;
        StringBuilder blank = new StringBuilder();
        for (int i = 0; i < length - plength; ++i) {
            blank.append("   ");
        }
        monitorTableVO.setOrgTitle(blank + monitorTableVO.getOrgTitle());
        list.add(monitorTableVO);
    }

    private void exportExecl(HttpServletResponse response, List<Map<String, Object>> monitorTableVOS, List<MonitorTableColumnVO> monitorColumns, ConditionVO conditionVO) throws Exception {
        ArrayList<String> valueColumns1 = new ArrayList<String>();
        ArrayList<String> valueColumns2 = new ArrayList<String>();
        ArrayList<Integer[]> list3 = new ArrayList<Integer[]>();
        for (int i = 0; i < monitorColumns.size(); ++i) {
            Object array = new Integer[4];
            if ("orgTitle".equals(monitorColumns.get(i).getProp())) {
                valueColumns1.add("\u7ec4\u7ec7\u673a\u6784");
                valueColumns2.add(monitorColumns.get(i).getLabel());
                array = new Integer[]{0, 1, 0, 0};
            } else {
                valueColumns1.add(monitorColumns.get(i).getLabel());
                valueColumns1.add(monitorColumns.get(i).getLabel());
                valueColumns1.add(monitorColumns.get(i).getLabel());
                valueColumns2.add("\u6570\u91cf");
                valueColumns2.add("\u8fdb\u5ea6");
                valueColumns2.add("\u72b6\u6001");
                array = new Integer[]{0, 0, 1 + (i - 1) * 3, 3 + (i - 1) * 3};
            }
            list3.add((Integer[])array);
        }
        ArrayList<List<String>> list1 = new ArrayList<List<String>>();
        for (Map map : monitorTableVOS) {
            ArrayList list2 = new ArrayList();
            monitorColumns.forEach(columnVO -> {
                String prop = columnVO.getProp();
                if ("orgTitle".equals(prop)) {
                    list2.add(exportTableVO.get("orgTitle").toString());
                } else {
                    list2.add(String.valueOf(ReflectionUtils.getFieldValue((Object)exportTableVO, (String)(prop + "Num"))));
                    list2.add(String.valueOf(ReflectionUtils.getFieldValue((Object)exportTableVO, (String)(prop + "Percent"))));
                    list2.add(String.valueOf(ReflectionUtils.getFieldValue((Object)exportTableVO, (String)(prop + "State"))));
                }
            });
            list1.add(list2);
        }
        String periodStr = conditionVO.getPeriodStr();
        HSSFWorkbook hSSFWorkbook = MonitorEcxelUtil.exportCell("\u5408\u5e76\u76d1\u63a7-" + periodStr, valueColumns1, valueColumns2, list1, list3);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream");
        String fileName = URLEncoder.encode("\u5408\u5e76\u76d1\u63a7.xls", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.flushBuffer();
        hSSFWorkbook.write((OutputStream)response.getOutputStream());
    }
}


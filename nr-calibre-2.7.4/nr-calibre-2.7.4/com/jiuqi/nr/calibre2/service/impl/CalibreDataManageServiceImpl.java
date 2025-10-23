/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.common.Utils;
import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreExpressionDO;
import com.jiuqi.nr.calibre2.domain.EntityQueryParam;
import com.jiuqi.nr.calibre2.exception.CalibreDataException;
import com.jiuqi.nr.calibre2.exception.CalibreDataUpdateException;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreSubListDao;
import com.jiuqi.nr.calibre2.internal.domain.BatchCalibreSubListDO;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import com.jiuqi.nr.calibre2.internal.service.IEntityQueryService;
import com.jiuqi.nr.calibre2.service.ICalibreDataManageService;
import com.jiuqi.nr.calibre2.vo.BatchCalibreDataOptionsVO;
import com.jiuqi.nr.calibre2.vo.CalibreBatchBuildVO;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.calibre2.vo.SelectedEntityVO;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CalibreDataManageServiceImpl
implements ICalibreDataManageService {
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private IEntityQueryService entityQueryService;
    @Autowired
    private ICalibreSubListDao calibreSubListDao;

    public List<CalibreDataVO> getRoot(CalibreDataVO vo) throws JQException {
        List<CalibreDataDTO> roots = this.getRootData(vo);
        return this.queryCalibreData(roots, vo);
    }

    public List<CalibreDataVO> getChildren(CalibreDataVO vo) throws JQException {
        List<CalibreDataDTO> childrenList = this.getChildrenData(vo);
        return this.queryCalibreData(childrenList, vo);
    }

    @Override
    public List<ITree<CalibreDataVO>> search(CalibreDataVO vo) throws JQException {
        ArrayList<ITree<CalibreDataVO>> search = new ArrayList<ITree<CalibreDataVO>>();
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(vo);
        if (list.getCode() == 0) {
            throw new JQException((ErrorEnum)CalibreDataException.SEARCH_ERROR, list.getMessage());
        }
        List<CalibreDataVO> dataVOS = this.queryCalibreData(list.getData(), vo);
        for (CalibreDataVO dataVO : dataVOS) {
            ITree root = new ITree((INode)dataVO);
            root.setLeaf(true);
            search.add((ITree<CalibreDataVO>)root);
        }
        return search;
    }

    @Override
    public List<CalibreDataVO> easysearch(CalibreDataVO vo) throws JQException {
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(vo);
        if (list.getCode() == 0) {
            throw new JQException((ErrorEnum)CalibreDataException.SEARCH_ERROR, list.getMessage());
        }
        List<CalibreDataVO> calibreDataVOS = CalibreDataVO.getListInstance(list.getData());
        return calibreDataVOS;
    }

    @Override
    public List<CalibreDataVO> searchData(CalibreDataVO vo) throws JQException {
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(vo);
        if (list.getCode() == 0) {
            throw new JQException((ErrorEnum)CalibreDataException.SEARCH_ERROR, list.getMessage());
        }
        List<CalibreDataVO> dataVOS = this.queryCalibreData(list.getData(), vo);
        return dataVOS;
    }

    @Override
    public Boolean existCalibreData(String code) {
        CalibreDataVO calibreDataVO = new CalibreDataVO();
        calibreDataVO.setCalibreCode(code);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataVO);
        List<CalibreDataDTO> data = list.getData();
        boolean b = data.size() != 0;
        int size = data.size();
        return b;
    }

    @Override
    public List<UpdateResult> copyCalibreData(String sourceDefineKey, String targetDefineCode) throws JQException {
        CalibreDataVO vo = new CalibreDataVO();
        vo.setDefineKey(sourceDefineKey);
        List<CalibreDataVO> dataVOS = this.searchData(vo);
        ArrayList<CalibreDataDTO> dataDTOS = new ArrayList<CalibreDataDTO>();
        for (CalibreDataVO dataVO : dataVOS) {
            dataDTOS.add(dataVO);
        }
        BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO = new BatchCalibreDataOptionsDTO();
        batchCalibreDataOptionsDTO.setCalibreDataDTOS(dataDTOS);
        batchCalibreDataOptionsDTO.setDefineCode(targetDefineCode);
        Result<List<UpdateResult>> listResult = this.calibreDataService.batchAdd(batchCalibreDataOptionsDTO);
        return listResult.getData();
    }

    @Override
    public CalibreDataVO insert(CalibreDataVO vo) throws JQException {
        try {
            Result<UpdateResult> add = this.calibreDataService.add(vo);
            if (add.getCode() != 1) {
                throw new JQException((ErrorEnum)CalibreDataException.INSERT_ERROR, add.getMessage());
            }
        }
        catch (CalibreDataUpdateException e) {
            throw new JQException((ErrorEnum)CalibreDataException.INSERT_ERROR, e.getMessage());
        }
        CalibreDataDTO dto = new CalibreDataDTO();
        dto.setDefineKey(vo.getDefineKey());
        dto.setCode(vo.getCode());
        dto.setCalibreCode(vo.getCalibreCode());
        Result<CalibreDataDTO> calibreDataDTOResult = this.calibreDataService.get(dto);
        ArrayList<CalibreDataDTO> calibreDataDTOS = new ArrayList<CalibreDataDTO>();
        calibreDataDTOS.add(calibreDataDTOResult.getData());
        vo.setSetContent(true);
        List<CalibreDataVO> dataVOS = this.queryCalibreData(calibreDataDTOS, vo);
        return dataVOS.get(0);
    }

    @Override
    public CalibreDataVO update(CalibreDataVO vo) throws JQException {
        try {
            Result<UpdateResult> update = this.calibreDataService.update(vo);
            if (update.getCode() != 1) {
                throw new JQException((ErrorEnum)CalibreDataException.UPDATE_ERROR, update.getMessage());
            }
        }
        catch (CalibreDataUpdateException e) {
            throw new JQException((ErrorEnum)CalibreDataException.UPDATE_ERROR, e.getMessage());
        }
        Result<CalibreDataDTO> calibreDataDTOResult = this.calibreDataService.get(vo);
        ArrayList<CalibreDataDTO> calibreDataDTOS = new ArrayList<CalibreDataDTO>();
        calibreDataDTOS.add(calibreDataDTOResult.getData());
        vo.setSetContent(true);
        List<CalibreDataVO> dataVOS = this.queryCalibreData(calibreDataDTOS, vo);
        return dataVOS.get(0);
    }

    @Override
    public UpdateResult delete(CalibreDataVO vo) throws JQException {
        UpdateResult result;
        try {
            Result<UpdateResult> delete = this.calibreDataService.delete(vo);
            result = delete.getData();
        }
        catch (CalibreDataUpdateException e) {
            throw new JQException((ErrorEnum)CalibreDataException.DELETE_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public List<UpdateResult> batchMove(BatchCalibreDataOptionsVO batchOptions) throws JQException {
        List<UpdateResult> results;
        try {
            Result<List<UpdateResult>> result = this.calibreDataService.batchChangeOrder(batchOptions);
            if (result.getCode() != 1) {
                throw new JQException((ErrorEnum)CalibreDataException.UPDATE_ERROR, result.getMessage());
            }
            results = result.getData();
        }
        catch (CalibreDataUpdateException e) {
            throw new JQException((ErrorEnum)CalibreDataException.UPDATE_ERROR, e.getMessage());
        }
        return results;
    }

    @Override
    public List<UpdateResult> batchDelete(BatchCalibreDataOptionsVO vo, Boolean isDeleteCalibreDefine) throws JQException {
        List<UpdateResult> result;
        boolean all = vo.isAll();
        if (all) {
            CalibreDataDTO calibreDataDto = new CalibreDataDTO();
            calibreDataDto.setDefineKey(vo.getDefineKey());
            calibreDataDto.setCalibreCode(vo.getDefineCode());
            Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDto);
            if (list.getCode() == 1) {
                List<String> exclude = vo.getExclude();
                List<CalibreDataDTO> deleteCalibre = list.getData();
                if (exclude != null && exclude.size() > 0 && !isDeleteCalibreDefine.booleanValue()) {
                    deleteCalibre = list.getData().stream().filter(e -> !exclude.contains(e.getCode())).collect(Collectors.toList());
                }
                vo.setCalibreDataDTOS(deleteCalibre);
            }
        }
        try {
            Result<List<UpdateResult>> listResult = this.calibreDataService.batchDelete(vo, isDeleteCalibreDefine);
            result = listResult.getData();
        }
        catch (CalibreDataUpdateException e2) {
            throw new JQException((ErrorEnum)CalibreDataException.DELETE_ERROR, e2.getMessage());
        }
        return result;
    }

    @Override
    public List<ITree<CalibreDataVO>> initTree(CalibreDataVO dataVO) throws JQException {
        ArrayList<ITree<CalibreDataVO>> calibreTree = new ArrayList<ITree<CalibreDataVO>>();
        List<CalibreDataVO> calibreRootVos = this.getRoot(dataVO);
        boolean flag = true;
        for (CalibreDataVO rootData : calibreRootVos) {
            ITree root = new ITree((INode)rootData);
            calibreTree.add((ITree<CalibreDataVO>)root);
            rootData.setSetContent(dataVO.isSetContent());
            List<ITree<CalibreDataVO>> childrenTree = this.loadChildren(rootData);
            root.setLeaf(CollectionUtils.isEmpty(childrenTree));
            if (!flag) continue;
            root.setExpanded(true);
            root.setChildren(childrenTree);
            flag = false;
        }
        return calibreTree;
    }

    @Override
    public List<ITree<CalibreDataVO>> loadChildren(CalibreDataVO dataVO) throws JQException {
        ArrayList<ITree<CalibreDataVO>> childrenTree = new ArrayList<ITree<CalibreDataVO>>();
        List<CalibreDataVO> children = this.getChildren(dataVO);
        for (CalibreDataVO childrenData : children) {
            ITree childrenNode = new ITree((INode)childrenData);
            childrenTree.add((ITree<CalibreDataVO>)childrenNode);
            childrenData.setSetContent(false);
            childrenNode.setLeaf(CollectionUtils.isEmpty(this.getChildren(childrenData)));
        }
        return childrenTree;
    }

    @Override
    public List<ITree<CalibreDataVO>> locationTree(CalibreDataVO dataVO) throws JQException {
        ArrayList<ITree<CalibreDataVO>> tree = new ArrayList<ITree<CalibreDataVO>>();
        ArrayList<String> path = new ArrayList<String>();
        path.add(dataVO.getCode());
        this.getPath(dataVO, path);
        Collections.reverse(path);
        List<CalibreDataVO> root = this.getRoot(dataVO);
        int index = 0;
        for (CalibreDataVO rootData : root) {
            rootData.setSetContent(dataVO.isSetContent());
            ITree rootNode = new ITree((INode)rootData);
            List<ITree<CalibreDataVO>> childrenNodes = this.loadChildren(rootData);
            rootNode.setLeaf(CollectionUtils.isEmpty(childrenNodes));
            tree.add((ITree<CalibreDataVO>)rootNode);
            if (!rootData.getCode().equals(path.get(index))) continue;
            if (index == path.size() - 1) {
                rootNode.setSelected(true);
                continue;
            }
            rootNode.setExpanded(true);
            this.expandChildren((ITree<CalibreDataVO>)rootNode, path, ++index, childrenNodes);
        }
        return tree;
    }

    @Override
    public CalibreDataVO getDataPath(CalibreDataVO dataVO) {
        ArrayList<String> path = new ArrayList<String>();
        this.getPath(dataVO, path);
        dataVO.setParents(path);
        return dataVO;
    }

    @Override
    public CalibreDataVO queryCalibre(CalibreDataVO dataVO) {
        CalibreDataDTO dto = new CalibreDataDTO();
        dto.setCode(dataVO.getCode());
        dto.setCalibreCode(dataVO.getCalibreCode());
        dto.setDefineKey(dataVO.getDefineKey());
        Result<CalibreDataDTO> queryResult = this.calibreDataService.get(dataVO);
        if (queryResult.getCode() == 1) {
            return CalibreDataVO.getInstance(queryResult.getData());
        }
        return null;
    }

    @Override
    public List<UpdateResult> batchAdd(BatchCalibreDataOptionsVO vo) throws JQException {
        Result<List<UpdateResult>> addResult = this.calibreDataService.batchAdd(vo);
        if (addResult.getCode() == 1) {
            return addResult.getData();
        }
        return null;
    }

    @Override
    public List<CalibreDataVO> listAll(String code) {
        Result<List<CalibreDataDO>> listAll = this.calibreDataService.listAll(code);
        return CalibreDataVO.toListInstance(listAll.getData());
    }

    @Override
    public List<CalibreDataVO> batchBuild(CalibreBatchBuildVO batchBuildVO) {
        List<SelectedEntityVO> selectedEntityVOS;
        ArrayList<CalibreDataVO> vos = new ArrayList<CalibreDataVO>();
        EntityQueryParam queryParam = new EntityQueryParam();
        queryParam.setEntityId(batchBuildVO.getEntityId());
        queryParam.setEntityKeys(batchBuildVO.getSelectedEntity());
        try {
            selectedEntityVOS = this.entityQueryService.querySelectedEntity(queryParam);
        }
        catch (JQException e2) {
            throw new RuntimeException("\u65e0\u6cd5\u67e5\u8be2\u9009\u4e2d\u7684\u5b9e\u4f53\u6570\u636e");
        }
        CalibreDataDTO calibreData = new CalibreDataDTO();
        calibreData.setCalibreCode(batchBuildVO.getCalibreCode());
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreData);
        List<CalibreDataDTO> queryCalibreData = list.getData();
        HashMap<String, CalibreDataDTO> calibreDataDTOMap = new HashMap<String, CalibreDataDTO>(queryCalibreData.size());
        for (CalibreDataDTO dataDTO : queryCalibreData) {
            String entityKey = dataDTO.getValue().getEntityKey();
            if (!StringUtils.hasText(entityKey)) continue;
            calibreDataDTOMap.put(entityKey, dataDTO);
        }
        Map<String, SelectedEntityVO> selectedEntityVOMap = selectedEntityVOS.stream().collect(Collectors.toMap(SelectedEntityVO::getKey, e -> e));
        List<SelectedEntityVO> sortedList = this.sortList(selectedEntityVOS, selectedEntityVOMap);
        for (SelectedEntityVO selectedEntityVO : sortedList) {
            CalibreDataDTO selectedParent;
            CalibreDataVO calibreDataVO = new CalibreDataVO();
            calibreDataVO.setKey(selectedEntityVO.getKey());
            calibreDataVO.setCode(selectedEntityVO.getCode());
            calibreDataVO.setName(selectedEntityVO.getTitle());
            String parent = selectedEntityVO.getParent();
            if (StringUtils.hasText(parent)) {
                SelectedEntityVO parentSelected = selectedEntityVOMap.get(parent);
                if (parentSelected != null) {
                    calibreDataVO.setParent(parentSelected.getCode());
                    calibreDataVO.setParentName(parentSelected.getTitle());
                    calibreDataVO.setVirtualParent(true);
                } else {
                    CalibreDataDTO parentCalibre = (CalibreDataDTO)calibreDataDTOMap.get(parent);
                    if (parentCalibre != null) {
                        calibreDataVO.setParent(parentCalibre.getCode());
                        calibreDataVO.setParentName(parentCalibre.getName());
                    }
                }
            }
            if (!StringUtils.hasText(calibreDataVO.getParent()) && StringUtils.hasText(batchBuildVO.getSelectedCalibre()) && (selectedParent = (CalibreDataDTO)calibreDataDTOMap.get(batchBuildVO.getSelectedCalibre())) != null) {
                calibreDataVO.setParent(selectedParent.getCode());
                calibreDataVO.setParentName(selectedParent.getName());
            }
            vos.add(calibreDataVO);
        }
        return vos;
    }

    private List<SelectedEntityVO> sortList(List<SelectedEntityVO> selectedEntityList, Map<String, SelectedEntityVO> selectedEntityMap) {
        ArrayList<SelectedEntityVO> sortedList = new ArrayList<SelectedEntityVO>();
        selectedEntityList.sort(Comparator.comparing(SelectedEntityVO::getOrder));
        HashMap<String, List<SelectedEntityVO>> childrenMap = new HashMap<String, List<SelectedEntityVO>>();
        ArrayList<SelectedEntityVO> rootRows = new ArrayList<SelectedEntityVO>();
        for (SelectedEntityVO selectedEntityVO : selectedEntityList) {
            String parent = selectedEntityVO.getParent();
            if (!StringUtils.hasText(parent) || selectedEntityMap.get(parent) == null) {
                rootRows.add(selectedEntityVO);
                continue;
            }
            childrenMap.computeIfAbsent(parent, key -> new ArrayList()).add(selectedEntityVO);
        }
        for (SelectedEntityVO rootRow : rootRows) {
            sortedList.add(rootRow);
            this.sortChildren(sortedList, rootRow.getKey(), childrenMap);
        }
        return sortedList;
    }

    public void sortChildren(List<SelectedEntityVO> sortedList, String key, Map<String, List<SelectedEntityVO>> childrenMap) {
        List<SelectedEntityVO> childrenRows = childrenMap.get(key);
        if (!CollectionUtils.isEmpty(childrenRows)) {
            for (SelectedEntityVO childrenRow : childrenRows) {
                sortedList.add(childrenRow);
                this.sortChildren(sortedList, childrenRow.getKey(), childrenMap);
            }
        }
    }

    private void getPath(CalibreDataVO dataVO, List<String> path) {
        Result<CalibreDataDTO> calibreDataDTOResult = this.calibreDataService.get(dataVO);
        if (calibreDataDTOResult.getCode() == 0) {
            return;
        }
        String parent = calibreDataDTOResult.getData().getParent();
        if (StringUtils.hasText(parent)) {
            path.add(parent);
            dataVO.setCode(parent);
            this.getPath(dataVO, path);
        }
    }

    private void expandChildren(ITree<CalibreDataVO> node, List<String> path, int index, List<ITree<CalibreDataVO>> childrenNodes) throws JQException {
        node.setChildren(childrenNodes);
        if (!CollectionUtils.isEmpty(childrenNodes)) {
            for (ITree<CalibreDataVO> childNode : childrenNodes) {
                if (!childNode.getCode().equals(path.get(index))) continue;
                if (index == path.size() - 1) {
                    childNode.setSelected(true);
                    break;
                }
                ((CalibreDataVO)childNode.getData()).setSetContent(true);
                List<ITree<CalibreDataVO>> nextChildren = this.loadChildren((CalibreDataVO)childNode.getData());
                childNode.setLeaf(CollectionUtils.isEmpty(nextChildren));
                childNode.setExpanded(true);
                this.expandChildren(childNode, path, ++index, nextChildren);
            }
        }
    }

    private List<CalibreDataDTO> getRootData(CalibreDataVO vo) throws JQException {
        CalibreDataDTO dto = new CalibreDataDTO();
        dto.setDefineKey(vo.getDefineKey());
        dto.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
        dto.setCalibreCode(vo.getCalibreCode());
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(dto);
        if (list.getCode() == 0) {
            throw new JQException((ErrorEnum)CalibreDataException.QUERY_ROOT_ERROR, list.getMessage());
        }
        return list.getData();
    }

    private List<CalibreDataDTO> getChildrenData(CalibreDataVO vo) throws JQException {
        vo.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(vo);
        if (list.getCode() == 0) {
            throw new JQException((ErrorEnum)CalibreDataException.QUERY_CHILDREN_ERROR, list.getMessage());
        }
        return list.getData();
    }

    private List<CalibreDataDTO> queryCalibreValueData(List<CalibreDataDTO> calibreDataDTOS, CalibreDataVO vo) throws JQException {
        boolean needSetEntity;
        if (CollectionUtils.isEmpty(calibreDataDTOS)) {
            return calibreDataDTOS;
        }
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(vo.getDefineKey());
        boolean bl = needSetEntity = calibreDefine.getType() == 0;
        if (needSetEntity) {
            List<CalibreSubListDO> subListDOS = this.querySubList(calibreDataDTOS, calibreDefine);
            if (CollectionUtils.isEmpty(subListDOS)) {
                return calibreDataDTOS;
            }
            Map<Object, Object> calibreKeyMap = new HashMap();
            calibreKeyMap = subListDOS.stream().collect(Collectors.groupingBy(CalibreSubListDO::getCode));
            for (CalibreDataDTO dataVO : calibreDataDTOS) {
                List subListDOList = (List)calibreKeyMap.get(dataVO.getCode());
                List list = subListDOList.stream().map(CalibreSubListDO::getValue).collect(Collectors.toList());
                CalibreExpressionDO calibreExpressionDO = new CalibreExpressionDO();
                calibreExpressionDO.setExpression(list);
                dataVO.setValue(calibreExpressionDO);
            }
        }
        return calibreDataDTOS;
    }

    private List<CalibreDataVO> queryCalibreData(List<CalibreDataDTO> calibreDataDTOS, CalibreDataVO vo) throws JQException {
        boolean sumExpressionType;
        List<CalibreDataVO> calibreDataVOS = CalibreDataVO.getListInstance(calibreDataDTOS);
        if (CollectionUtils.isEmpty(calibreDataVOS)) {
            return calibreDataVOS;
        }
        HashMap<String, String> entityKeyToTitleMap = new HashMap<String, String>();
        Map<Object, Object> calibreKeyMap = new HashMap();
        Map<Object, Object> referEntityTitleMap = new HashMap();
        CalibreDefineDTO calibreDefine = this.getCalibreDefine(vo.getDefineKey());
        boolean listType = calibreDefine.getType().intValue() == CalibreDataOption.DataType.LIST.getCode();
        boolean bl = sumExpressionType = calibreDefine.getType().intValue() == CalibreDataOption.DataType.SUM_EXPRESS.getCode();
        if (listType) {
            List<CalibreSubListDO> subListDOS = this.querySubList(calibreDataDTOS, calibreDefine);
            if (CollectionUtils.isEmpty(subListDOS)) {
                return calibreDataVOS;
            }
            calibreKeyMap = subListDOS.stream().collect(Collectors.groupingBy(CalibreSubListDO::getCode));
            if (vo.isSetContent()) {
                List<String> selectedKeys = subListDOS.stream().map(CalibreSubListDO::getValue).distinct().collect(Collectors.toList());
                List<SelectedEntityVO> selectedEntity = this.getCalibreSelectedEntity(calibreDefine.getEntityId(), selectedKeys);
                for (SelectedEntityVO selectedEntityVO : selectedEntity) {
                    entityKeyToTitleMap.put(selectedEntityVO.getKey(), selectedEntityVO.getTitle());
                }
            }
        } else if (sumExpressionType) {
            List entityKeys = calibreDataDTOS.stream().filter(e -> StringUtils.hasText(e.getValue().getEntityKey())).map(e -> e.getValue().getEntityKey()).collect(Collectors.toList());
            List<SelectedEntityVO> selectedEntity = this.getCalibreSelectedEntity(calibreDefine.getEntityId(), entityKeys);
            referEntityTitleMap = selectedEntity.stream().collect(Collectors.toMap(SelectedEntityVO::getKey, SelectedEntityVO::getTitle));
        }
        for (CalibreDataVO dataVO : calibreDataVOS) {
            String entityKey;
            if (listType) {
                List subListDOS = (List)calibreKeyMap.get(dataVO.getCode());
                if (CollectionUtils.isEmpty(subListDOS)) continue;
                List<String> list = subListDOS.stream().map(CalibreSubListDO::getValue).collect(Collectors.toList());
                if (vo.isSetContent()) {
                    dataVO.setContent(Utils.buildEntityTitle(list, entityKeyToTitleMap));
                }
                CalibreExpressionDO calibreExpressionDO = new CalibreExpressionDO();
                calibreExpressionDO.setExpression(list);
                dataVO.setValue(calibreExpressionDO);
                continue;
            }
            if (vo.isSetContent() && dataVO.getValue().getExpression() != null) {
                dataVO.setContent(dataVO.getValue().getExpression().toString());
            }
            if (!sumExpressionType || !StringUtils.hasText(entityKey = dataVO.getValue().getEntityKey())) continue;
            dataVO.setRefEntityName((String)referEntityTitleMap.get(entityKey));
        }
        if (StringUtils.hasText(vo.getKeyWords()) && calibreDefine.getStructType() == 1) {
            List<String> parentCodes = calibreDataVOS.stream().map(CalibreDataDO::getParent).collect(Collectors.toList());
            CalibreDataVO calibreDataVO = new CalibreDataVO();
            calibreDataVO.setCodes(parentCodes);
            calibreDataVO.setDefineKey(calibreDefine.getKey());
            calibreDataVO.setCalibreCode(calibreDefine.getCode());
            List<CalibreDataDTO> parentDatas = this.calibreDataService.list(calibreDataVO).getData();
            Map<String, String> parentCodeToName = parentDatas.stream().collect(Collectors.toMap(CalibreDataDO::getCode, CalibreDataDO::getName, (k1, k2) -> k1));
            for (CalibreDataVO calibreDataVO1 : calibreDataVOS) {
                calibreDataVO1.setParentName(parentCodeToName.get(calibreDataVO1.getParent()));
            }
        }
        return calibreDataVOS;
    }

    private List<CalibreSubListDO> querySubList(List<CalibreDataDTO> calibreDataDTOS, CalibreDefineDTO calibreDefine) {
        BatchCalibreSubListDO batchQuery = new BatchCalibreSubListDO();
        batchQuery.setCalibreDefine(calibreDefine.getCode());
        for (CalibreDataDTO calibreDataDTO : calibreDataDTOS) {
            CalibreSubListDO subListDO = new CalibreSubListDO();
            subListDO.setCalibreCode(calibreDataDTO.getCalibreCode());
            subListDO.setCode(calibreDataDTO.getCode());
            batchQuery.addCalibreSubListDO(subListDO);
        }
        return this.calibreSubListDao.batchQuery(batchQuery);
    }

    private CalibreDefineDTO getCalibreDefine(String key) {
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        calibreDefine.setKey(key);
        try {
            Result<CalibreDefineDTO> define = this.calibreDefineService.get(calibreDefine);
            calibreDefine = define.getData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return calibreDefine;
    }

    private List<SelectedEntityVO> getCalibreSelectedEntity(String entityId, List<String> unitKeys) throws JQException {
        CalibreDataVO dataVO = new CalibreDataVO();
        CalibreExpressionDO calibreExpressionDO = new CalibreExpressionDO();
        calibreExpressionDO.setExpression(unitKeys);
        dataVO.setValue(calibreExpressionDO);
        dataVO.setEntityId(entityId);
        return this.entityQueryService.querySelectedEntity(dataVO);
    }
}


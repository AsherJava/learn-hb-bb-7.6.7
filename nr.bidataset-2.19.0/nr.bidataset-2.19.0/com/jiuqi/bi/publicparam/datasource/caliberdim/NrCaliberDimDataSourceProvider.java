/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.common.CalibreDataOption$DataTreeType
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.calibre2.internal.adapter.CalibreTreeData
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext$PageInfo
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.publicparam.datasource.caliberdim;

import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreTreeData;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class NrCaliberDimDataSourceProvider
implements IParameterDataSourceProvider {
    private NrCaliberDimDataSourceModel dataSourceModel;
    private ICalibreDataService calibreDataService;
    private DataModelService dataModelService;
    private ICalibreDefineService calibreDefineService;

    public NrCaliberDimDataSourceProvider(NrCaliberDimDataSourceModel dataSourceModel, ICalibreDataService calibreDataService, DataModelService dataModelService, ICalibreDefineService calibreDefineService) {
        this.dataSourceModel = dataSourceModel;
        this.calibreDataService = calibreDataService;
        this.dataModelService = dataModelService;
        this.calibreDefineService = calibreDefineService;
    }

    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        try {
            AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
            String mode = cfg.getDefaultValueMode();
            if (mode.equals("none")) {
                return new ParameterResultset();
            }
            String calibreDefineKey = this.getCalibreDefineKey();
            ArrayList<CalibreDataDTO> rows = new ArrayList<CalibreDataDTO>();
            List<CalibreDataDTO> result = null;
            if (mode.equals("appoint")) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
                List keys = cfg.getDefaultValue().getKeysAsString(valueFormat);
                if (keys == null || keys.isEmpty()) {
                    return new ParameterResultset();
                }
                result = this.getCalibreDataList(calibreDefineKey, keys);
                rows.addAll(result);
            } else if (mode.equals("first")) {
                result = this.getCalibreDataByTree(calibreDefineKey, CalibreDataOption.DataTreeType.ROOT, null);
                if (result.size() > 0) {
                    rows.add(result.get(0));
                }
            } else if (mode.equals("firstChild")) {
                result = this.getCalibreDataByTree(calibreDefineKey, null, null);
                if (result.size() > 0) {
                    CalibreDataDTO parent = result.get(0);
                    rows.add(parent);
                    List<CalibreDataDTO> childs = this.getCalibreDataByTree(calibreDefineKey, CalibreDataOption.DataTreeType.DIRECT_CHILDREN, parent.getCode());
                    if (childs != null && childs.size() > 0) {
                        rows.addAll(childs);
                    }
                }
            } else if (mode.equals("firstAllChild")) {
                // empty if block
            }
            return this.getParameterResultset(context, rows, null);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        try {
            ArrayList<CalibreDataDTO> rows = new ArrayList<CalibreDataDTO>();
            List<CalibreDataDTO> allRows = this.getCandidateCalibreDataList(context);
            CalibreTreeData treeData = new CalibreTreeData(allRows);
            String parent = null;
            boolean isAllSub = true;
            if (hierarchyFilter != null) {
                parent = hierarchyFilter.getParent();
                isAllSub = hierarchyFilter.isAllSub();
            }
            if (StringUtils.isEmpty(parent)) {
                if (isAllSub) {
                    rows.addAll(allRows);
                } else {
                    rows.addAll(treeData.getRootRows());
                }
            } else if (isAllSub) {
                rows.addAll(treeData.getAllChildRows(parent));
            } else {
                rows.addAll(treeData.getChildRows(parent));
            }
            return this.getParameterResultset(context, rows, treeData);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        try {
            ArrayList<CalibreDataDTO> rows = new ArrayList<CalibreDataDTO>();
            List<CalibreDataDTO> allRows = this.getCandidateCalibreDataList(context);
            CalibreTreeData treeData = new CalibreTreeData(allRows);
            IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)context.getModel().getDatasource());
            List keys = value.getKeysAsString(valueFormat);
            if (keys != null && keys.size() > 0) {
                HashSet keySet = new HashSet(keys);
                for (CalibreDataDTO row : allRows) {
                    if (!keySet.contains(row.getCode())) continue;
                    rows.add(row);
                }
            } else {
                rows.addAll(allRows);
            }
            return this.getParameterResultset(context, rows, treeData);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        try {
            ArrayList<CalibreDataDTO> rows = new ArrayList<CalibreDataDTO>();
            List<CalibreDataDTO> allRows = this.getCandidateCalibreDataList(context);
            CalibreTreeData treeData = new CalibreTreeData(allRows);
            for (CalibreDataDTO row : allRows) {
                boolean matched = true;
                for (String searchValue : searchValues) {
                    matched = row.getCode().contains(searchValue) || row.getName().contains(searchValue);
                    if (matched) continue;
                    break;
                }
                if (!matched) continue;
                rows.add(row);
            }
            return this.getParameterResultset(context, rows, treeData);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        try {
            String tableKey = this.dataSourceModel.getEntityViewId();
            List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableKey);
            Iterator attributes = columnModels.iterator();
            ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>();
            while (attributes.hasNext()) {
                ColumnModelDefine attribute = (ColumnModelDefine)attributes.next();
                DataSourceCandidateFieldInfo info = new DataSourceCandidateFieldInfo(attribute.getCode(), attribute.getTitle());
                list.add(info);
            }
            return list;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private List<CalibreDataDTO> getCalibreDataList(String calibreDefineKey, List<String> codes) throws Exception {
        CalibreDataDTO dto = new CalibreDataDTO();
        dto.setDefineKey(calibreDefineKey);
        dto.setCodes(codes);
        Result result = this.calibreDataService.list(dto);
        if (result != null && result.getData() != null) {
            return (List)result.getData();
        }
        return new ArrayList<CalibreDataDTO>(0);
    }

    private List<CalibreDataDTO> getCalibreDataByTree(String calibreDefineKey, CalibreDataOption.DataTreeType dataTreeType, String parent) throws Exception {
        CalibreDataDTO dto = new CalibreDataDTO();
        dto.setDefineKey(calibreDefineKey);
        dto.setCode(parent);
        dto.setDataTreeType(dataTreeType);
        Result result = this.calibreDataService.list(dto);
        if (result != null && result.getData() != null) {
            return (List)result.getData();
        }
        return new ArrayList<CalibreDataDTO>(0);
    }

    private List<CalibreDataDTO> getCandidateCalibreDataList(ParameterDataSourceContext context) throws Exception {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        ParameterCandidateValueMode mode = cfg.getCandidateMode();
        List keys = null;
        if (mode == ParameterCandidateValueMode.APPOINT) {
            keys = cfg.getCandidateValue();
        }
        return this.getCalibreDataList(this.getCalibreDefineKey(), keys);
    }

    private ParameterResultset getParameterResultset(ParameterDataSourceContext context, List<CalibreDataDTO> rows, CalibreTreeData treeData) {
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>(rows.size());
        if (rows != null && rows.size() > 0) {
            ParameterDataSourceContext.PageInfo pageInfo;
            if (context.getModel().isOrderReverse()) {
                Collections.reverse(rows);
            }
            if ((pageInfo = context.getPageInfo()) != null) {
                int startIndex = pageInfo.startRow;
                if (startIndex > rows.size() - 1) {
                    return new ParameterResultset();
                }
                int toIndex = startIndex + pageInfo.recordSize;
                if (toIndex > rows.size()) {
                    toIndex = rows.size();
                }
                rows = rows.subList(startIndex, toIndex);
            }
            for (CalibreDataDTO obj : rows) {
                String key = obj.getCode();
                String name = obj.getName();
                ParameterResultItem item = new ParameterResultItem((Object)key, name);
                if (obj.getParent() != null) {
                    item.setParent(obj.getParent());
                }
                if (treeData != null) {
                    item.setLeaf(treeData.getDirectChildCount(obj.getCode()) == 0);
                } else {
                    item.setLeaf(true);
                }
                items.add(item);
            }
        }
        return new ParameterResultset(items);
    }

    private String getCalibreDefineKey() {
        String code = this.dataSourceModel.getEntityViewId();
        CalibreDefineDTO dto = new CalibreDefineDTO();
        dto.setCode(code);
        Result result = this.calibreDefineService.get(dto);
        if (result != null && result.getData() != null) {
            return ((CalibreDefineDTO)result.getData()).getKey();
        }
        return code;
    }
}


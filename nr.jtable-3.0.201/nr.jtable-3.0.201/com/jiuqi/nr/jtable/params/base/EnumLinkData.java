/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.exception.FieldDataErrorException;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(EnumLinkData.class);
    private String entityKey;
    private String entityRowFilter;
    private boolean entityAuth;
    private String entityTitle;
    private Map<String, String> enumFieldPosMap;
    private String titleField;
    private List<String> capnames;
    private List<String> dropnames;
    private String pname;
    private EnumLink enumLink;
    private EnumDisplayMode displayMode;
    private int enumCount;
    private boolean multipleSelect;
    private int maxCount;
    private boolean onlyLeaf;
    private boolean undefinedCode;
    private int precision;
    private String treeStruct;
    private int maxDepth;
    private boolean codeMatch;
    private EntityDataLoader dataLoader;
    private Map<String, Map<String, EntityData>> entityDataInfoMap = null;
    private static IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
    private static IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
    private IEntityModel entityModel;

    public EnumLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine, List<DataLinkMappingDefine> queryDataLinkMapping) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE\uff3fENUM.getValue();
        this.entityKey = fieldDefine.getEntityKey();
        this.initDataLinkDefine(dataLinkDefine, queryDataLinkMapping);
        this.initEntityInfo();
        this.multipleSelect = fieldDefine.getAllowMultipleSelect();
        this.precision = fieldDefine.getSize();
        this.entityRowFilter = dataLinkDefine.getFilterExpression();
        this.entityAuth = !dataLinkDefine.isIgnorePermissions();
        this.style = DataLinkStyleUtil.getEnumLinkStyle(dataLinkDefine, fieldDefine);
    }

    public EnumLinkData(DataLinkDefine dataLinkDefine, IFMDMAttribute fmdmAttribute, List<DataLinkMappingDefine> queryDataLinkMapping) {
        super(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
        this.type = LinkType.LINK_TYPE\uff3fENUM.getValue();
        this.entityKey = fmdmAttribute.getReferEntityId();
        this.initDataLinkDefine(dataLinkDefine, queryDataLinkMapping);
        this.initEntityInfo();
        this.entityRowFilter = dataLinkDefine.getFilterExpression();
        this.entityAuth = !dataLinkDefine.isIgnorePermissions();
        this.multipleSelect = fmdmAttribute.isMultival();
        this.precision = fmdmAttribute.getPrecision();
        this.style = DataLinkStyleUtil.getEnumLinkStyle(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
    }

    private void initEntityInfo() {
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        IEntityDefine entityDefine = entityMetaService.queryEntity(this.entityKey);
        this.entityTitle = entityDefine.getTitle();
        if (entityDefine.getTreeStruct() != null && StringUtils.isNotEmpty((String)entityDefine.getTreeStruct().getLevelCode())) {
            String treeStructStr = entityDefine.getTreeStruct().getLevelCode();
            char[] charArray = treeStructStr.toCharArray();
            ArrayList<String> treeStructArrat = new ArrayList<String>();
            for (char treeStructchar : charArray) {
                treeStructArrat.add(String.valueOf(treeStructchar));
            }
            this.treeStruct = StringUtils.join((Object[])treeStructArrat.toArray(), (String)";");
        }
    }

    private void initDataLinkDefine(DataLinkDefine dataLinkDefine, List<DataLinkMappingDefine> queryDataLinkMapping) {
        String enumLinkageMethod;
        Map enumLinkageData;
        IEntityAttribute nameField;
        String[] fields;
        this.type = LinkType.LINK_TYPE\uff3fENUM.getValue();
        this.entityAuth = !dataLinkDefine.isIgnorePermissions();
        this.entityModel = iEntityMetaService.getEntityModel(this.entityKey);
        Map enumPosMap = dataLinkDefine.getEnumPosMap();
        if (enumPosMap != null && enumPosMap.size() > 0) {
            this.enumFieldPosMap = new HashMap<String, String>();
            for (String[] fieldKey : enumPosMap.keySet()) {
                this.enumFieldPosMap.put((String)fieldKey, enumPosMap.get(fieldKey).toString());
            }
        }
        this.titleField = dataLinkDefine.getEnumTitleField();
        if (StringUtils.isNotEmpty((String)dataLinkDefine.getCaptionFieldsString())) {
            this.capnames = new ArrayList<String>();
            for (String fieldCode : fields = dataLinkDefine.getCaptionFieldsString().split(";")) {
                this.capnames.add(fieldCode);
            }
        } else if (this.entityModel != null && (nameField = this.entityModel.getNameField()) != null) {
            this.capnames = new ArrayList<String>();
            this.capnames.add(nameField.getCode());
        }
        if (StringUtils.isNotEmpty((String)dataLinkDefine.getDropDownFieldsString())) {
            this.dropnames = new ArrayList<String>();
            for (String fieldCode : fields = dataLinkDefine.getDropDownFieldsString().split(";")) {
                this.dropnames.add(fieldCode);
            }
        } else if (this.entityModel != null) {
            IEntityAttribute codeField = this.entityModel.getCodeField();
            IEntityAttribute nameField2 = this.entityModel.getNameField();
            if (codeField != null && nameField2 != null) {
                this.dropnames = new ArrayList<String>();
                this.dropnames.add(codeField.getCode());
                this.dropnames.add(nameField2.getCode());
            }
        }
        this.pname = dataLinkDefine.getEnumShowFullPath();
        if (queryDataLinkMapping != null && !queryDataLinkMapping.isEmpty()) {
            for (DataLinkMappingDefine dataLinkMappingDefine : queryDataLinkMapping) {
                String leftDataLinkKey = dataLinkMappingDefine.getLeftDataLinkKey();
                String rightDataLinkKey = dataLinkMappingDefine.getRightDataLinkKey();
                if (!leftDataLinkKey.equals(dataLinkDefine.getKey()) && !rightDataLinkKey.equals(dataLinkDefine.getKey())) continue;
                if (this.enumLink == null) {
                    this.enumLink = new EnumLink();
                }
                this.enumLink.setType("1");
                this.enumLink.setLink(dataLinkDefine.getKey());
                if (leftDataLinkKey.equals(dataLinkDefine.getKey()) && !this.enumLink.getNextLinks().contains(rightDataLinkKey)) {
                    this.enumLink.getNextLinks().add(rightDataLinkKey);
                }
                if (!rightDataLinkKey.equals(dataLinkDefine.getKey()) || this.enumLink.getPreLinks().contains(leftDataLinkKey)) continue;
                this.enumLink.getPreLinks().add(leftDataLinkKey);
            }
            if (this.enumLink != null && this.enumLink.getPreLinks().isEmpty()) {
                this.enumLink.setLevel("1");
            }
        }
        if ((enumLinkageData = dataLinkDefine.getEnumLinkageData()).size() > 0 && null != (enumLinkageMethod = dataLinkDefine.getEnumLinkageMethod())) {
            if (this.enumLink == null) {
                this.enumLink = new EnumLink();
            }
            if (enumLinkageMethod.equals("2")) {
                for (String Key : enumLinkageData.keySet()) {
                    String preLink;
                    if (!((String)enumLinkageData.get(Key)).equals(this.key)) continue;
                    this.enumLink.setType("2");
                    this.enumLink.setLevel(Key);
                    this.enumLink.setLink(this.key);
                    String nextLink = (String)enumLinkageData.get(Integer.parseInt(Key) + 1 + "");
                    if (StringUtils.isNotEmpty((String)nextLink)) {
                        this.enumLink.getNextLinks().add(nextLink);
                    }
                    if (StringUtils.isNotEmpty((String)(preLink = (String)enumLinkageData.get(Integer.parseInt(Key) - 1 + "")))) {
                        this.enumLink.getPreLinks().add(preLink);
                    }
                    if (!this.enumLink.getNextLinks().isEmpty() || !this.enumLink.getPreLinks().isEmpty() || this.enumLink.getLink() == null) continue;
                    ReturnInfo returnInfo = new ReturnInfo();
                    ObjectMapper mapper = new ObjectMapper();
                    returnInfo.setMessage("\u6570\u636e\u4e0d\u5408\u6cd5" + enumLinkageData);
                    try {
                        String retStr = mapper.writeValueAsString((Object)returnInfo);
                        LogHelper.error((String)"\u679a\u4e3e\u8054\u52a8", (String)retStr);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
            }
        }
        this.displayMode = dataLinkDefine.getDisplayMode();
        this.enumCount = dataLinkDefine.getEnumCount();
        this.onlyLeaf = !dataLinkDefine.getAllowNotLeafNodeRefer();
        this.undefinedCode = dataLinkDefine.getAllowUndefinedCode();
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getEntityRowFilter() {
        return this.entityRowFilter;
    }

    public void setEntityRowFilter(String entityRowFilter) {
        this.entityRowFilter = entityRowFilter;
    }

    public boolean isEntityAuth() {
        return this.entityAuth;
    }

    public void setEntityAuth(boolean entityAuth) {
        this.entityAuth = entityAuth;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public Map<String, String> getEnumFieldPosMap() {
        return this.enumFieldPosMap;
    }

    public void setEnumFieldPosMap(Map<String, String> enumFieldPosMap) {
        this.enumFieldPosMap = enumFieldPosMap;
    }

    public List<String> getCapnames() {
        return this.capnames;
    }

    public void setCapnames(List<String> capnames) {
        this.capnames = capnames;
    }

    public List<String> getDropnames() {
        return this.dropnames;
    }

    public void setDropnames(List<String> dropnames) {
        this.dropnames = dropnames;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public EnumLink getEnumLink() {
        return this.enumLink;
    }

    public void setEnumLink(EnumLink enumLink) {
        this.enumLink = enumLink;
    }

    public EnumDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(EnumDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public int getEnumCount() {
        return this.enumCount;
    }

    public void setEnumCount(int enumCount) {
        this.enumCount = enumCount;
    }

    public boolean isMultipleSelect() {
        return this.multipleSelect;
    }

    public void setMultipleSelect(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public boolean isOnlyLeaf() {
        return this.onlyLeaf;
    }

    public void setOnlyLeaf(boolean onlyLeaf) {
        this.onlyLeaf = onlyLeaf;
    }

    public boolean isUndefinedCode() {
        return this.undefinedCode;
    }

    public void setUndefinedCode(boolean undefinedCode) {
        this.undefinedCode = undefinedCode;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getTreeStruct() {
        return this.treeStruct;
    }

    public void setTreeStruct(String treeStruct) {
        this.treeStruct = treeStruct;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean isCodeMatch() {
        return this.codeMatch;
    }

    public void setCodeMatch(boolean codeMatch) {
        this.codeMatch = codeMatch;
    }

    public String getTitleField() {
        return this.titleField;
    }

    public void setTitleField(String titleField) {
        this.titleField = titleField;
    }

    @Override
    public String getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData.toString();
        }
        if (dataFormaterCache.isJsonData()) {
            return this.getFormatJsonData(data.getAsString(), dataFormaterCache);
        }
        return this.getFormatData(data.getAsString(), dataFormaterCache);
    }

    private String getFormatData(String dataStr, DataFormaterCache dataFormaterCache) {
        StringBuffer returnStr = new StringBuffer();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] entityCodeArr;
            for (String entityCode : entityCodeArr = dataStr.split(";")) {
                EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache, true);
                String entityID = entityCode;
                if (entityDataInfo != null) {
                    entityID = entityDataInfo.getId();
                }
                returnStr.append(returnStr.length() > 0 ? ";" + entityID : entityID);
            }
        }
        return returnStr.toString();
    }

    public String getFormatJsonData(String dataStr, DataFormaterCache dataFormaterCache) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] split;
            for (String entityCode : split = dataStr.split(";")) {
                EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache, true);
                List<String> cells = null;
                if (this.dataLoader != null) {
                    cells = this.dataLoader.getCells();
                }
                JSONObject jsonObject = new JSONObject();
                if (entityDataInfo == null) {
                    jsonObject.put("code", (Object)entityCode);
                    jsonObject.put("title", (Object)entityCode);
                    jsonObject.put("codevalue", (Object)entityCode);
                } else {
                    jsonObject.put("code", (Object)entityDataInfo.getId());
                    jsonObject.put("title", (Object)entityDataInfo.getRowCaption());
                    if (cells != null && cells.size() > 0) {
                        for (int cellIndex = 0; cellIndex < cells.size(); ++cellIndex) {
                            jsonObject.put(cells.get(cellIndex), (Object)entityDataInfo.getData().get(cellIndex));
                        }
                    }
                    jsonObject.put("codevalue", (Object)entityDataInfo.getCode());
                }
                jsonArray.put((Object)jsonObject);
            }
        }
        return jsonArray.toString();
    }

    private EntityData getEntityDataInfo(String keyOrCode, DataFormaterCache dataFormaterCache, boolean ignoreIsolate) {
        Map<String, EntityReturnInfo> entityDataMap;
        EntityData entityDataInfo;
        Map<String, EntityData> entityMap;
        if (null == this.entityDataInfoMap) {
            this.entityDataInfoMap = dataFormaterCache.getEntityDataInfoMap();
        }
        if ((entityMap = this.entityDataInfoMap.get(this.entityKey.toString() + ";" + this.key)) == null) {
            entityMap = new HashMap<String, EntityData>(1000);
            this.entityDataInfoMap.put(this.entityKey.toString() + ";" + this.key, entityMap);
        }
        if ((entityDataInfo = entityMap.get(keyOrCode)) != null && (entityDataMap = dataFormaterCache.getEntityDataMap()).containsKey(this.entityKey)) {
            EntityReturnInfo entityReturnInfo = entityDataMap.get(this.entityKey);
            this.resetRowCaption(entityReturnInfo.getCells(), entityDataInfo, dataFormaterCache, ignoreIsolate);
            return entityDataInfo;
        }
        this.initTable(dataFormaterCache, ignoreIsolate);
        EntityByKeyReturnInfo returnInfo = jtableEntityService.queryEntityDataByKey(keyOrCode, this.dataLoader);
        entityDataInfo = returnInfo.getEntity();
        if (entityDataInfo == null || entityDataInfo.getCode() == null) {
            return null;
        }
        entityMap.put(keyOrCode, entityDataInfo);
        List<String> parents = entityDataInfo.getParents();
        ArrayList<String> currParents = new ArrayList<String>();
        for (String parent : parents) {
            EntityData parentEntityDataInfo = this.getEntityDataInfo(parent, dataFormaterCache, ignoreIsolate);
            if (parentEntityDataInfo == null) continue;
            currParents.add(parent);
        }
        entityDataInfo.setParents(currParents);
        this.resetRowCaption(returnInfo.getCells(), entityDataInfo, dataFormaterCache, ignoreIsolate);
        Map<String, EntityReturnInfo> entityDataMap2 = dataFormaterCache.getEntityDataMap();
        if (entityDataMap2 != null) {
            EntityReturnInfo entityData = entityDataMap2.get(this.entityKey);
            if (entityData == null) {
                List<String> cells = returnInfo.getCells();
                entityData = new EntityReturnInfo();
                entityData.setCells(cells);
                if (this.entityModel != null) {
                    ArrayList<String> dataMaskFields = new ArrayList<String>();
                    for (String cell : cells) {
                        IEntityAttribute attribute = this.entityModel.getAttribute(cell);
                        if (!StringUtils.isNotEmpty((String)attribute.masked())) continue;
                        dataMaskFields.add(cell);
                    }
                    entityData.setDataMaskFields(dataMaskFields);
                }
                entityData.getEntitys().add(entityDataInfo);
                entityDataMap2.put(this.entityKey, entityData);
            } else {
                entityData.getEntitys().add(entityDataInfo);
            }
        }
        return entityDataInfo;
    }

    private void resetRowCaption(List<String> cells, EntityData entityDataInfo, DataFormaterCache dataFormaterCache, boolean ignoreIsolate) {
        if (this.capnames != null && this.capnames.size() > 0) {
            StringBuilder rowCaption = new StringBuilder();
            for (String captionField : this.capnames) {
                if (!cells.contains(captionField)) continue;
                int captionFieldIndex = cells.indexOf(captionField);
                if (rowCaption.length() > 0) {
                    rowCaption.append("|");
                }
                if (StringUtils.isNotEmpty((String)this.pname) && captionField.equals(this.pname)) {
                    StringBuilder pCaption = new StringBuilder();
                    for (String parent : entityDataInfo.getParents()) {
                        EntityData parentEntityDataInfo = this.getEntityDataInfo(parent, dataFormaterCache, ignoreIsolate);
                        if (pCaption.length() > 0) {
                            pCaption.append("/");
                        }
                        pCaption.append(parentEntityDataInfo.getData().get(captionFieldIndex));
                    }
                    if (pCaption.length() > 0) {
                        pCaption.append("/");
                    }
                    pCaption.append(entityDataInfo.getData().get(captionFieldIndex));
                    rowCaption.append((CharSequence)pCaption);
                    continue;
                }
                rowCaption.append(entityDataInfo.getData().get(captionFieldIndex));
            }
            if (rowCaption.length() > 0) {
                entityDataInfo.setRowCaption(rowCaption.toString());
            }
        }
    }

    private void initTable(DataFormaterCache dataFormaterCache, boolean ignoreIsolate) {
        this.initTable(dataFormaterCache, true, ignoreIsolate);
    }

    private void initTable(DataFormaterCache dataFormaterCache, boolean isLazy, boolean ignoreIsolate) {
        if (this.dataLoader == null) {
            this.doInitTable(dataFormaterCache, isLazy, ignoreIsolate);
        } else if (!isLazy && this.dataLoader.IsLazy()) {
            this.doInitTable(dataFormaterCache, isLazy, ignoreIsolate);
        }
    }

    private void doInitTable(DataFormaterCache dataFormaterCache, boolean isLazy, boolean ignoreIsolate) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(dataFormaterCache.getJtableContext());
        entityQueryByKeyInfo.setEntityViewKey(this.entityKey);
        entityQueryByKeyInfo.setDataLinkKey(this.key);
        Map<String, Set<String>> entityCaptionFields = dataFormaterCache.getEntityCaptionFields();
        if (entityCaptionFields != null && entityCaptionFields.containsKey(this.entityKey)) {
            entityQueryByKeyInfo.setCaptionFields(entityCaptionFields.get(this.entityKey));
        }
        entityQueryByKeyInfo.setIgnoreIsolate(ignoreIsolate);
        entityQueryByKeyInfo.setDesensitized(dataFormaterCache.isDesensitized());
        this.dataLoader = jtableEntityService.getEntityDataLoader(entityQueryByKeyInfo, isLazy);
        this.dataLoader.setQueryChildrenCount(dataFormaterCache.isQueryChildrenCount());
    }

    @Override
    public String getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        String stringVaule;
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        StringBuffer entityStr = new StringBuffer();
        String dataStr = value.toString();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] split = null;
            boolean single = false;
            if (dataStr.contains(";")) {
                if (dataStr.contains("|")) {
                    String[] valueArr = dataStr.split(";");
                    split = new String[valueArr.length];
                    for (int i = 0; i < valueArr.length; ++i) {
                        split[i] = null == valueArr[i] ? null : valueArr[i].split("\\|")[0];
                    }
                } else {
                    split = dataStr.split(";");
                }
            } else {
                split = dataStr.split("\\|");
                single = true;
            }
            HashSet<String> choosedEntity = new HashSet<String>();
            for (String entityCode : split) {
                IJtableParamService jtableParamService;
                RegionData region;
                List<String> fillLinks;
                EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache, false);
                if (entityDataInfo == null) {
                    entityDataInfo = this.searchEntityDataInfo(entityCode, dataFormaterCache);
                }
                if (!(entityDataInfo == null || entityDataInfo.isLeaf() || !this.onlyLeaf || (fillLinks = (region = (jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class)).getRegion(this.regionKey)).getFillLinks()) != null && fillLinks.contains(this.key))) {
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo("\u4e0d\u5141\u8bb8\u9009\u62e9\u975e\u53f6\u5b50\u8282\u70b9\u3002\u9519\u8bef\u503c\uff1a" + dataStr);
                    return entityStr.toString();
                }
                if (entityDataInfo != null) {
                    if (choosedEntity.add(entityDataInfo.getId())) {
                        if (entityStr.length() == 0) {
                            entityStr.append(entityDataInfo.getId());
                        } else {
                            entityStr.append(";" + entityDataInfo.getId());
                        }
                    }
                } else if (this.undefinedCode) {
                    if (choosedEntity.add(entityCode)) {
                        if (entityStr.length() == 0) {
                            entityStr.append(entityCode);
                        } else {
                            entityStr.append(";" + entityCode);
                        }
                    }
                } else {
                    saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
                    saveErrorDataInfo.getDataError().setErrorInfo("\u672a\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u6216\u8005\u627e\u5230\u7684\u679a\u4e3e\u9879\u4e0d\u7b26\u5408\u6761\u4ef6\u3002\u9519\u8bef\u503c\uff1a" + entityCode);
                }
                if (single) break;
            }
        }
        if (this.precision > 0 && this.precision < (stringVaule = entityStr.toString()).length()) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + this.precision + "\u3002\u9519\u8bef\u503c\uff1a" + stringVaule);
            throw new FieldDataErrorException(new String[]{this.zbtitle + "\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6: " + this.precision});
        }
        return entityStr.toString();
    }

    private EntityData searchEntityDataInfo(String search, DataFormaterCache dataFormaterCache) {
        Map<String, EntityReturnInfo> entityDataMap;
        String entityDataInfoMapKey = this.entityKey.toString() + ";" + this.key;
        String searchEntityDataKey = this.entityKey.toString() + ";" + this.key;
        Map<String, EntityData> entityDataInfoMap = dataFormaterCache.getEntityDataInfoMap().get(entityDataInfoMapKey);
        if (entityDataInfoMap == null) {
            entityDataInfoMap = new HashMap<String, EntityData>();
            dataFormaterCache.getEntityDataInfoMap().put(entityDataInfoMapKey, entityDataInfoMap);
        }
        if (entityDataInfoMap.containsKey(search)) {
            return entityDataInfoMap.get(search);
        }
        EntityReturnInfo searchData = dataFormaterCache.getSearchDataMap().get(searchEntityDataKey);
        if (searchData == null) {
            Map<String, Set<String>> entityCaptionFields;
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setContext(dataFormaterCache.getJtableContext());
            entityQueryInfo.setEntityViewKey(this.entityKey);
            entityQueryInfo.setDataLinkKey(this.key);
            entityQueryInfo.setSearchLeaf(this.onlyLeaf);
            if (StringUtils.isNotEmpty((String)this.pname)) {
                entityQueryInfo.setFullPath(true);
            }
            if (dataFormaterCache.getEntityMatchAll() != null) {
                entityQueryInfo.setMatchAll(dataFormaterCache.getEntityMatchAll());
            }
            if ((entityCaptionFields = dataFormaterCache.getEntityCaptionFields()) != null && entityCaptionFields.containsKey(this.entityKey)) {
                entityQueryInfo.setCaptionFields(entityCaptionFields.get(this.entityKey));
            }
            this.initTable(dataFormaterCache, false);
            List<EntityData> entityDatas = this.dataLoader.getEntityDatas(entityQueryInfo);
            searchData = new EntityReturnInfo();
            searchData.getEntitys().addAll(entityDatas);
            searchData.getCells().addAll(this.dataLoader.getCells());
            dataFormaterCache.getSearchDataMap().put(searchEntityDataKey, searchData);
        }
        EntityData entityDataInfo = new EntityData();
        entityDataInfo = dataFormaterCache.getEntityMatchAll() == null ? this.matchEntityData(searchData, search, false) : this.matchEntityData(searchData, search, (boolean)dataFormaterCache.getEntityMatchAll());
        entityDataInfoMap.put(search, entityDataInfo);
        if (entityDataInfo != null && (entityDataMap = dataFormaterCache.getEntityDataMap()) != null) {
            EntityReturnInfo entityData = entityDataMap.get(this.entityKey.toString());
            if (entityData == null) {
                entityData = new EntityReturnInfo();
                entityData.setCells(searchData.getCells());
                entityData.getEntitys().add(entityDataInfo);
                entityDataMap.put(this.entityKey.toString(), entityData);
            } else {
                entityData.getEntitys().add(entityDataInfo);
            }
        }
        return entityDataInfo;
    }

    private EntityData matchEntityData(EntityReturnInfo queryEntityData, String search, boolean entityMatchAll) {
        if (queryEntityData.getEntitys().isEmpty()) {
            return null;
        }
        if (StringUtils.isNotEmpty((String)search)) {
            for (EntityData entityData : queryEntityData.getEntitys()) {
                EntityData matchEntityData = this.matchEntityData(entityData, search, entityMatchAll);
                if (matchEntityData == null) continue;
                return matchEntityData;
            }
        }
        return null;
    }

    private EntityData matchEntityData(EntityData parentEntityData, String search, boolean entityMatchAll) {
        if (this.accept(parentEntityData, search, entityMatchAll)) {
            return parentEntityData;
        }
        for (EntityData entityData : parentEntityData.getChildren()) {
            EntityData matchEntityData = this.matchEntityData(entityData, search, entityMatchAll);
            if (matchEntityData == null) continue;
            return matchEntityData;
        }
        return null;
    }

    public boolean accept(EntityData parentEntityData, String search, boolean entityMatchAll) {
        if (this.filterFieldValue(parentEntityData.getRowCaption(), search, entityMatchAll)) {
            return true;
        }
        if (this.filterFieldValue(parentEntityData.getCode(), search, entityMatchAll)) {
            return true;
        }
        return this.filterFieldValue(parentEntityData.getTitle(), search, entityMatchAll);
    }

    private boolean filterFieldValue(String fieldValue, String search, boolean entityMatchAll) {
        if (StringUtils.isNotEmpty((String)fieldValue)) {
            if (entityMatchAll) {
                if (fieldValue.equalsIgnoreCase(search)) {
                    return true;
                }
                return fieldValue.equalsIgnoreCase(search.trim());
            }
            if (fieldValue.toUpperCase().contains(search.toUpperCase())) {
                return true;
            }
            return fieldValue.toUpperCase().contains(search.trim().toUpperCase());
        }
        return false;
    }

    public EntityReturnInfo getFillEntityData(String parentKey, String parentViewKey, DataFormaterCache dataFormaterCache) {
        EnumLink enumLink = this.getEnumLink();
        boolean allChildRen = true;
        if (enumLink != null && enumLink.getType().equals("2")) {
            allChildRen = false;
        }
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setContext(dataFormaterCache.getJtableContext());
        entityQueryInfo.setEntityViewKey(this.entityKey);
        entityQueryInfo.setDataLinkKey(this.getKey());
        entityQueryInfo.setAllChildren(allChildRen);
        entityQueryInfo.setSearchLeaf(this.onlyLeaf);
        if (StringUtils.isNotEmpty((String)parentKey)) {
            entityQueryInfo.setParentKey(parentKey);
        }
        if (StringUtils.isNotEmpty((String)parentViewKey)) {
            entityQueryInfo.setParentViewKey(parentViewKey);
        }
        if (dataFormaterCache.getEntityCaptionFields() != null && dataFormaterCache.getEntityCaptionFields().containsKey(this.entityKey)) {
            entityQueryInfo.setCaptionFields(dataFormaterCache.getEntityCaptionFields().get(this.entityKey));
        }
        if (StringUtils.isNotEmpty((String)this.pname)) {
            entityQueryInfo.setFullPath(true);
        }
        EntityReturnInfo queryEntityData = jtableEntityService.queryEntityData(entityQueryInfo);
        Map<String, EntityData> entityMap = dataFormaterCache.getEntityDataInfoMap().get(this.entityKey.toString() + ";" + this.key);
        if (entityMap == null) {
            entityMap = new HashMap<String, EntityData>(1000);
            dataFormaterCache.getEntityDataInfoMap().put(this.entityKey.toString() + ";" + this.key, entityMap);
        }
        for (EntityData entityDataInfo : queryEntityData.getEntitys()) {
            if (entityMap.containsKey(entityDataInfo.getId())) continue;
            entityMap.put(entityDataInfo.getId(), entityDataInfo);
        }
        if (dataFormaterCache.getEntityDataMap() != null) {
            EntityReturnInfo entityData = dataFormaterCache.getEntityDataMap().get(this.entityKey.toString());
            if (entityData == null) {
                dataFormaterCache.getEntityDataMap().put(this.entityKey.toString(), queryEntityData);
            } else {
                entityData.getEntitys().clear();
                entityData.getEntitys().addAll(entityMap.values());
            }
        }
        return queryEntityData;
    }

    @Override
    public boolean checkFuzzyValue(AbstractData value, Object formatValue, DataFormaterCache dataFormaterCache, String fuzzyValue) {
        Object superData = super.getFormatData(value, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return formatValue.toString().contains(fuzzyValue);
        }
        String dataStr = value.getAsString();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] split = dataStr.split(";");
            String entityCode = split[0];
            EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache, false);
            if (entityDataInfo == null) {
                return entityCode.contains(fuzzyValue);
            }
            return entityDataInfo.getId().contains(fuzzyValue) || entityDataInfo.getRowCaption().contains(fuzzyValue);
        }
        return formatValue.toString().contains(fuzzyValue);
    }

    public boolean checkFuzzyValue2(String value, DataFormaterCache dataFormaterCache, String fuzzyValue) {
        if (StringUtils.isEmpty((String)value) || StringUtils.isEmpty((String)fuzzyValue)) {
            return false;
        }
        if (StringUtils.isNotEmpty((String)value)) {
            String[] split = value.split(";");
            String entityCode = split[0];
            EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache, false);
            if (entityDataInfo == null) {
                return entityCode.contains(fuzzyValue);
            }
            return entityDataInfo.getId().contains(fuzzyValue) || entityDataInfo.getRowCaption().contains(fuzzyValue);
        }
        return false;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.datamodel.service.impl;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.dao.VaDataModelMaintainDao;
import com.jiuqi.va.datamodel.dao.VaDataModelPublishedDao;
import com.jiuqi.va.datamodel.domain.DataModelMaintainDO;
import com.jiuqi.va.datamodel.domain.DataModelPublishDTO;
import com.jiuqi.va.datamodel.domain.DataModelPublishedDO;
import com.jiuqi.va.datamodel.service.VaDataModelMaintainService;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.datamodel.template.OtherTemplate;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.util.LogUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaDataModelMaintainServiceImpl
implements VaDataModelMaintainService {
    private static Logger logger = LoggerFactory.getLogger(VaDataModelMaintainServiceImpl.class);
    @Autowired
    private VaDataModelMaintainDao dataModelMaintainDao;
    @Autowired
    private VaDataModelPublishedDao dataModelDao;
    @Autowired
    private VaDataModelPublishedService vaDataModelPublishedService;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private EnumDataClient enumDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private VaDataModelTemplateService templateService;
    @Autowired
    private OtherTemplate otherTemplate;

    @Override
    public DataModelMaintainDO get(DataModelDTO param) {
        DataModelMaintainDO param1 = new DataModelMaintainDO();
        param1.setName(param.getName());
        DataModelMaintainDO res = (DataModelMaintainDO)((Object)this.dataModelMaintainDao.selectOne((Object)param1));
        if (res == null) {
            DataModelDTO param2 = new DataModelDTO();
            param2.setName(param.getName());
            DataModelDO define = this.vaDataModelPublishedService.get(param2);
            if (define != null) {
                res = this.getDataModelMaintainDOByDataModelDO(define);
            }
        } else {
            DataModelMaintainDO build = (DataModelMaintainDO)((Object)JSONUtil.parseObject((String)res.getDefinedata(), DataModelMaintainDO.class));
            res.setTenantName(param.getTenantName());
            res.setColumns(build.getColumns());
            res.setSubBiztype(build.getSubBiztype());
            res.setIndexConsts(build.getIndexConsts());
            res.setDefinedata(null);
        }
        return res;
    }

    @Override
    public int count(DataModelDTO param) {
        return this.dataModelMaintainDao.count(param);
    }

    @Override
    public List<DataModelMaintainDO> list(DataModelDTO param) {
        List<DataModelMaintainDO> res = this.listAll(param);
        if (res != null && res.size() > 1) {
            Collections.sort(res, (o1, o2) -> {
                int stateOrder;
                if (o1.getState() != null && o2.getState() != null && (stateOrder = o1.getState().compareTo(o2.getState())) != 0) {
                    return stateOrder;
                }
                if (o1.getState() != 2 && o1.getModifytime() != null && o2.getModifytime() != null) {
                    return o2.getModifytime().compareTo(o1.getModifytime());
                }
                return o1.getName().compareTo(o2.getName());
            });
        }
        return res;
    }

    private List<DataModelMaintainDO> listAll(DataModelDTO param) {
        ArrayList<DataModelMaintainDO> res = new ArrayList<DataModelMaintainDO>();
        if (param.getName() != null) {
            DataModelMaintainDO dataModelMaintainDO = this.get(param);
            if (dataModelMaintainDO != null) {
                res.add(dataModelMaintainDO);
            }
            return res;
        }
        HashSet groupSets = null;
        if (param.getGroupCodes() != null && param.getGroupCodes().length > 0) {
            groupSets = new HashSet();
            Collections.addAll(groupSets, param.getGroupCodes());
        }
        HashMap<String, DataModelMaintainDO> dataMap = new HashMap<String, DataModelMaintainDO>();
        DataModelDTO defineParam = new DataModelDTO();
        defineParam.setBiztype(param.getBiztype());
        defineParam.setSearchKey(param.getSearchKey());
        List list = this.vaDataModelPublishedService.list(defineParam).getRows();
        DataModelMaintainDO maintainDefine = null;
        for (DataModelDO data : list) {
            if (groupSets != null && !groupSets.contains(data.getGroupcode())) continue;
            maintainDefine = this.getDataModelMaintainDOByDataModelDO(data);
            maintainDefine.setState(2);
            dataMap.put(maintainDefine.getName(), maintainDefine);
        }
        List<DataModelMaintainDO> defineMaintainlist = this.dataModelMaintainDao.list(defineParam);
        if (defineMaintainlist != null && !defineMaintainlist.isEmpty()) {
            DataModelMaintainDO build = null;
            for (DataModelMaintainDO dmdo : defineMaintainlist) {
                if (groupSets != null && !groupSets.contains(dmdo.getGroupcode())) continue;
                dmdo.setTenantName(param.getTenantName());
                if (dmdo.getDefinedata().endsWith(":}")) {
                    String defineString = dmdo.getDefinedata();
                    defineString = defineString.substring(0, defineString.length() - 1) + "\"\"}";
                    dmdo.setDefinedata(defineString);
                }
                build = (DataModelMaintainDO)((Object)JSONUtil.parseObject((String)dmdo.getDefinedata(), DataModelMaintainDO.class));
                dmdo.setColumns(build.getColumns());
                dmdo.setIndexConsts(build.getIndexConsts());
                dmdo.setBiztype(build.getBiztype());
                dmdo.setSubBiztype(build.getSubBiztype());
                if (dmdo.getBiztype() != null && dmdo.getBiztype() != DataModelType.BizType.BASEDATA && dmdo.getSubBiztype() == null) {
                    if (dmdo.getBiztype() != DataModelType.BizType.BILL) {
                        dmdo.setSubBiztype(dmdo.getBiztype().toString().hashCode());
                    } else if (dmdo.getDefinedata().contains("MASTERID")) {
                        dmdo.setSubBiztype(2);
                    } else {
                        dmdo.setSubBiztype(1);
                    }
                }
                dmdo.setDefinedata(null);
                if (dataMap.containsKey(dmdo.getName())) {
                    dmdo.setState(1);
                } else {
                    dmdo.setState(0);
                }
                dataMap.put(dmdo.getName(), dmdo);
            }
        }
        res.addAll(dataMap.values());
        return res;
    }

    private DataModelMaintainDO getDataModelMaintainDOByDataModelDO(DataModelDO param) {
        DataModelMaintainDO res = new DataModelMaintainDO();
        res.setName(param.getName());
        res.setTitle(param.getTitle());
        res.setTenantName(param.getTenantName());
        res.setBiztype(param.getBiztype());
        res.setSubBiztype(param.getSubBiztype());
        res.setGroupcode(param.getGroupcode());
        res.setRemark(param.getRemark());
        res.setColumns(param.getColumns());
        res.setIndexConsts(param.getIndexConsts());
        return res;
    }

    @Override
    public R add(DataModelDTO param) {
        String bizTypeName;
        DataModelTemplate template;
        DataModelDTO param1 = new DataModelDTO();
        param1.setName(param.getName());
        if (this.get(param1) != null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.add.existed", new Object[0]));
        }
        String modelName = param.getName();
        DataModelMaintainDO data = new DataModelMaintainDO();
        data.setTenantName(ShiroUtil.getTenantName());
        data.setName(modelName);
        data.setTitle(param.getTitle());
        data.setBiztype(param.getBiztype());
        data.setGroupcode(param.getGroupcode());
        data.setRemark(param.getRemark());
        StringBuilder defineJson = new StringBuilder();
        defineJson.append("{");
        defineJson.append(this.getBaseDefineInfo(param));
        DataModelType.BizType bizType = param.getBiztype();
        if (DataModelType.BizType.BILL == bizType && param.getSubBiztype() == null) {
            param.setSubBiztype(Integer.valueOf(1));
        }
        if ((template = this.templateService.getSubTemplate(bizTypeName = bizType.toString(), param.getSubBiztype())) == null) {
            template = this.templateService.getTemplate(bizTypeName);
        }
        List indexs = null;
        if (template != null) {
            indexs = template.getTemplateIndexs(modelName);
        }
        defineJson.append(",\"columns\":");
        if (param.getColumns() == null) {
            if (template != null) {
                defineJson.append(JSONUtil.toJSONString((Object)template.getTemplateFields()));
            } else {
                defineJson.append(JSONUtil.toJSONString(this.otherTemplate.getTemplateFields()));
            }
        } else {
            defineJson.append(JSONUtil.toJSONString((Object)param.getColumns()));
        }
        defineJson.append(",\"indexConsts\":");
        if (param.getIndexConsts() == null) {
            if (indexs != null) {
                defineJson.append(JSONUtil.toJSONString((Object)indexs));
            } else {
                defineJson.append("[]");
            }
        } else {
            defineJson.append(JSONUtil.toJSONString((Object)param.getIndexConsts()));
        }
        defineJson.append("}");
        data.setDefinedata(defineJson.toString());
        if (this.modify(data) > 0) {
            return R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0]));
        }
        return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.save", new Object[0]));
    }

    @Override
    public R update(DataModelDTO param) {
        if (param.getName() == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.parameter.missing", new Object[0]));
        }
        DataModelMaintainDO oldData = this.get(param);
        if (oldData != null && oldData.getVer() != null && param.getVer() != null && oldData.getVer().compareTo(param.getVer()) > 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.update.version.changed", new Object[0]));
        }
        DataModelMaintainDO data = new DataModelMaintainDO();
        data.setTenantName(ShiroUtil.getTenantName());
        data.setName(param.getName());
        data.setTitle(param.getTitle());
        data.setBiztype(param.getBiztype());
        data.setGroupcode(param.getGroupcode());
        data.setRemark(param.getRemark());
        StringBuilder defineJson = new StringBuilder();
        defineJson.append("{" + this.getBaseDefineInfo(param) + ",\"columns\":");
        defineJson.append(JSONUtil.toJSONString((Object)param.getColumns()));
        if (param.getIndexConsts() != null) {
            defineJson.append(",\"indexConsts\":");
            defineJson.append(JSONUtil.toJSONString((Object)param.getIndexConsts()));
        }
        defineJson.append("}");
        data.setDefinedata(defineJson.toString());
        if (this.modify(data) > 0) {
            return R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0]));
        }
        return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.save", new Object[0]));
    }

    private String getBaseDefineInfo(DataModelDTO param) {
        return "\"name\":\"" + param.getName() + "\",\"title\":\"" + param.getTitle() + "\",\"biztype\":\"" + param.getBiztype() + "\",\"subBiztype\":\"" + param.getSubBiztype() + "\",\"groupcode\":\"" + param.getGroupcode() + "\",\"remark\":\"" + param.getRemark() + "\"";
    }

    @Override
    public int modify(DataModelMaintainDO param) {
        DataModelMaintainDO oldParam = new DataModelMaintainDO();
        oldParam.setTenantName(param.getTenantName());
        oldParam.setName(param.getName());
        DataModelMaintainDO old = (DataModelMaintainDO)((Object)this.dataModelMaintainDao.selectOne((Object)oldParam));
        LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)(old == null ? "\u521b\u5efa" : "\u66f4\u65b0"), (String)param.getTitle(), (String)param.getName(), (String)param.getDefinedata());
        int cnt = 0;
        param.setModifyuser(ShiroUtil.getUser().getId());
        param.setModifytime(new Date());
        if (old == null) {
            DataModelDTO queryDefineParam = new DataModelDTO();
            queryDefineParam.setName(param.getName());
            DataModelDO oldDefine = this.vaDataModelPublishedService.get(queryDefineParam);
            if (oldDefine != null && !oldDefine.getGroupcode().equals(param.getGroupcode())) {
                oldDefine.setGroupcode(param.getGroupcode());
                this.vaDataModelPublishedService.push(oldDefine);
            }
            param.setId(UUID.randomUUID());
            param.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            param.setCreatetime(param.getModifytime());
            cnt = this.dataModelMaintainDao.insert((Object)param);
        } else {
            param.setId(old.getId());
            param.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            cnt = this.dataModelMaintainDao.updateByPrimaryKeySelective((Object)param);
        }
        return cnt;
    }

    @Override
    public int remove(DataModelMaintainDO param) {
        DataModelMaintainDO old = (DataModelMaintainDO)((Object)this.dataModelMaintainDao.selectOne((Object)param));
        if (old == null) {
            return 0;
        }
        int res = this.dataModelMaintainDao.delete((Object)param);
        if (res > 0) {
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21\u7ef4\u62a4\u8868", (String)"\u5220\u9664", (String)old.getTitle(), (String)old.getName(), (String)old.getDefinedata());
        }
        return res;
    }

    @Override
    public List<String> invalidRelateList(List<DataModelColumn> impColList) {
        ArrayList<String> invalidRelateList = new ArrayList<String>();
        List baseDataDefineNameList = null;
        List enumDataBiztypeList = null;
        List organizationNameList = null;
        String mapping = "";
        for (DataModelColumn column : impColList) {
            if (!StringUtils.hasText(column.getMapping())) continue;
            if (column.getMappingType() == 1) {
                mapping = column.getMapping().split(".OBJECTCODE")[0];
                if (baseDataDefineNameList == null) {
                    BaseDataDefineDTO baseDataDefineParam = new BaseDataDefineDTO();
                    baseDataDefineParam.setDeepClone(Boolean.valueOf(false));
                    PageVO pageVO = this.baseDataDefineClient.list(baseDataDefineParam);
                    List baseDataDefineList = pageVO.getRows();
                    baseDataDefineNameList = baseDataDefineList.stream().map(item -> item.getName()).collect(Collectors.toList());
                }
                this.verifyRelate(mapping, baseDataDefineNameList, invalidRelateList);
                continue;
            }
            if (column.getMappingType() == 2) {
                mapping = column.getMapping().split(".VAL")[0];
                if (enumDataBiztypeList == null) {
                    List enumDataList = this.enumDataClient.listBiztype(new EnumDataDTO());
                    enumDataBiztypeList = enumDataList.stream().map(item -> item.getBiztype()).collect(Collectors.toList());
                }
                this.verifyRelate(mapping, enumDataBiztypeList, invalidRelateList);
                continue;
            }
            if (column.getMappingType() == 3) {
                mapping = column.getMapping().split(".ID")[0];
                if ("auth_user".equalsIgnoreCase(mapping)) continue;
                invalidRelateList.add(mapping);
                continue;
            }
            if (column.getMappingType() != 4) continue;
            mapping = column.getMapping().split(".CODE")[0];
            if (organizationNameList == null) {
                PageVO pageVO = this.orgCategoryClient.list(new OrgCategoryDO());
                List orgCategoryList = pageVO.getRows();
                organizationNameList = orgCategoryList.stream().map(item -> item.getName()).collect(Collectors.toList());
            }
            this.verifyRelate(mapping, organizationNameList, invalidRelateList);
        }
        return invalidRelateList;
    }

    private void verifyRelate(String mapping, List<String> nameList, List<String> invalidRelateList) {
        if (!nameList.contains(mapping)) {
            invalidRelateList.add(mapping);
        }
    }

    @Override
    public List<DataModelPublishDTO> listMaintain(DataModelDTO param) {
        DataModelDTO defineParam = new DataModelDTO();
        defineParam.setBiztype(param.getBiztype());
        defineParam.setSearchKey(param.getSearchKey());
        List<DataModelMaintainDO> defineMaintainlist = this.dataModelMaintainDao.list(defineParam);
        if (defineMaintainlist == null || defineMaintainlist.isEmpty()) {
            return null;
        }
        HashSet groupSets = null;
        if (param.getGroupCodes() != null && param.getGroupCodes().length > 0) {
            groupSets = new HashSet();
            Collections.addAll(groupSets, param.getGroupCodes());
        }
        ArrayList<DataModelPublishDTO> endList = new ArrayList<DataModelPublishDTO>();
        HashMap<String, DataModelPublishDTO> dataMap = new HashMap<String, DataModelPublishDTO>();
        for (DataModelMaintainDO dmdo : defineMaintainlist) {
            if (groupSets != null && !groupSets.contains(dmdo.getGroupcode())) continue;
            DataModelPublishDTO data = new DataModelPublishDTO();
            data.setBiztype(dmdo.getBiztype());
            data.setGroupcode(dmdo.getGroupcode());
            data.setName(dmdo.getName());
            data.setTitle(dmdo.getTitle());
            data.setState(0);
            endList.add(data);
            dataMap.put(dmdo.getName(), data);
        }
        List list = this.vaDataModelPublishedService.list(defineParam).getRows();
        for (DataModelPublishDTO data : list) {
            if (!dataMap.containsKey(data.getName())) continue;
            ((DataModelPublishDTO)dataMap.get(data.getName())).setState(1);
        }
        Collections.sort(endList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return endList;
    }

    @Override
    public R publishModel(List<DataModelDTO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.publish.empty", new Object[0]));
        }
        ArrayList<String> successData = new ArrayList<String>();
        LinkedHashMap<String, String> failedData = new LinkedHashMap<String, String>();
        for (DataModelDTO param : dataList) {
            if (!StringUtils.hasText(param.getName())) continue;
            R res = this.merge(param);
            if (res.getCode() == 0) {
                successData.add(param.getName());
                continue;
            }
            failedData.put(param.getName(), res.get((Object)"msg").toString());
        }
        R res = R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0]));
        res.put("successData", successData);
        res.put("failedData", failedData);
        return res;
    }

    @Override
    public R publish(List<DataModelPublishDTO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.publish.empty", new Object[0]));
        }
        ArrayList<String> successData = new ArrayList<String>();
        LinkedHashMap<String, String> failedData = new LinkedHashMap<String, String>();
        DataModelDTO param = new DataModelDTO();
        for (DataModelPublishDTO publishDTO : dataList) {
            param.setName(publishDTO.getName());
            param.addExtInfo("needCompare", (Object)(publishDTO.getFullComplete() == null || publishDTO.getFullComplete() == false ? 1 : 0));
            R res = this.merge(param);
            if (res.getCode() == 0) {
                successData.add(param.getName());
                continue;
            }
            failedData.put(param.getName(), res.getMsg());
        }
        R res = R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0]));
        res.put("successData", successData);
        res.put("failedData", failedData);
        return res;
    }

    @Override
    public R merge(DataModelDTO param) {
        String name = param.getName();
        DataModelMaintainDO oldParam = new DataModelMaintainDO();
        oldParam.setName(name);
        DataModelMaintainDO old = (DataModelMaintainDO)((Object)this.dataModelMaintainDao.selectOne((Object)oldParam));
        if (old == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.merge.not.exist", new Object[0]));
        }
        BigDecimal ver = old.getVer();
        DataModelPublishedDO dmpParam = new DataModelPublishedDO();
        dmpParam.setName(name);
        DataModelPublishedDO publishDefine = (DataModelPublishedDO)((Object)this.dataModelDao.selectOne((Object)dmpParam));
        if (ver != null && publishDefine != null && publishDefine.getVer() != null && publishDefine.getVer().compareTo(ver) > 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.merge.version.changed", new Object[0]));
        }
        old = (DataModelMaintainDO)((Object)JSONUtil.parseObject((String)old.getDefinedata(), DataModelMaintainDO.class));
        old.setId(null);
        old.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        old.setModifyuser(ShiroUtil.getUser().getId());
        old.setModifytime(new Date());
        old.putAll(param.getExtInfo());
        R res = this.vaDataModelPublishedService.pushComplete(old);
        int del = -1;
        if (res.getCode() == 0) {
            try {
                del = this.dataModelMaintainDao.delete((Object)oldParam);
            }
            catch (Exception e) {
                del = 0;
                logger.error(DataModelCoreI18nUtil.getMessage("datamodel.delete.failed", new Object[0]), e);
            }
        }
        if (del == 0) {
            res = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.success.definition.publish.with.error", new Object[0]));
        } else if (del > 0) {
            res = R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.definition.publish", new Object[0]));
        }
        return res;
    }
}


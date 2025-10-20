/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.conversion.conversionsystem.api.ConversionSystemClient
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoPageVo
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemBatchIndexVo
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.conversion.conversionsystem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.conversionsystem.api.ConversionSystemClient;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionLogInfoService;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoPageVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemBatchIndexVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ConversionSystemController
implements ConversionSystemClient {
    @Autowired
    private ConversionSystemService service;
    @Autowired
    private ConversionLogInfoService conversionLogInfoService;

    public BusinessResponseEntity<List<ConversionSystemTaskSchemeVO>> getSystemTaskSchemes() {
        return BusinessResponseEntity.ok(this.service.getSystemTaskSchemes());
    }

    public BusinessResponseEntity<List<ConversionSystemTaskVO>> saveTaskScheme(@RequestBody List<ConversionSystemTaskVO> itemVoList) {
        return BusinessResponseEntity.ok(this.service.saveTaskScheme(itemVoList));
    }

    public BusinessResponseEntity<ConversionSystemTaskVO> deleteTaskScheme(@PathVariable(value="id") String id) {
        return BusinessResponseEntity.ok((Object)this.service.deleteTaskScheme(id));
    }

    public BusinessResponseEntity<ConversionLogInfoPageVo> getConversionLoginfos(@RequestParam(value="pageSize") Integer pageSize, @RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="showQueryCount") boolean showQueryCount) {
        long count = this.conversionLogInfoService.count();
        List<ConversionLogInfoVo> conversionLogInfoVos = this.conversionLogInfoService.queryLogInfos(pageSize, pageNum - 1);
        ConversionLogInfoPageVo conversionLogInfoPageVo = new ConversionLogInfoPageVo(pageSize, pageNum, count, conversionLogInfoVos);
        return BusinessResponseEntity.ok((Object)conversionLogInfoPageVo);
    }

    public BusinessResponseEntity<ConversionSystemItemVO> getSystemItemByFormIdAndIndexId(String formId, String indexId) {
        ConversionSystemItemVO conversionSystemItemIndexVO = this.service.getSystemItemByFormIdAndIndexId(formId, indexId);
        return BusinessResponseEntity.ok((Object)conversionSystemItemIndexVO);
    }

    public BusinessResponseEntity<List<ConversionSystemItemVO>> batchGetSystemItemsByFormIdAndIndexIds(ConversionSystemItemBatchIndexVo batchIndexVo) {
        List<ConversionSystemItemVO> itemVOs = this.service.batchGetSystemItemsByFormIdAndIndexIds(batchIndexVo.getTaskSchemeId(), batchIndexVo.getFormId(), batchIndexVo.getIndexIds());
        return BusinessResponseEntity.ok(itemVOs);
    }

    public BusinessResponseEntity<ConversionSystemItemVO> saveConversionSystemItemIndexInfo(ConversionSystemItemVO vo) {
        ConversionSystemItemVO conversionSystemItemIndexVO = this.service.saveConversionSystemItemIndexInfo(vo);
        return BusinessResponseEntity.ok((Object)conversionSystemItemIndexVO);
    }

    public BusinessResponseEntity<List<ConversionSystemItemVO>> batchSaveConversionSystemItemIndexInfo(List<ConversionSystemItemVO> vos) {
        List<ConversionSystemItemVO> conversionSystemItemIndexVOs = this.service.batchSaveConversionSystemItemIndexInfo(vos);
        return BusinessResponseEntity.ok(conversionSystemItemIndexVOs);
    }

    public BusinessResponseEntity<List<ConversonSystemFormTreeVo>> queryFormTree(String formSchemeKey) {
        return BusinessResponseEntity.ok(this.service.queryFormTree(formSchemeKey));
    }

    public BusinessResponseEntity<String> queryFormData(String formKey) {
        return BusinessResponseEntity.ok((Object)this.service.queryFormData(formKey));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.impl.JtableResourceServiceImpl
 *  com.jiuqi.nr.jtable.util.RegionDataFactory
 *  javax.annotation.Resource
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.impl.JtableResourceServiceImpl;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import java.util.List;
import javax.annotation.Resource;
import nr.single.client.bean.JIODeleteResultObject;
import nr.single.client.service.export.IBatchDeleteRegionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchDeleteRegionDataServiceImpl
implements IBatchDeleteRegionDataService {
    private static final Logger logger = LoggerFactory.getLogger(JtableResourceServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Resource
    private IEntityMetaService metaService;

    @Override
    public JIODeleteResultObject clearFormdata(JtableContext jtableContext, String formKey) {
        return this.clearRegiondata(jtableContext, formKey, null);
    }

    @Override
    public JIODeleteResultObject clearRegiondata(JtableContext jtableContext, String formKey, String regionKey) {
        FormData formData = this.jtableParamService.getReport(formKey, jtableContext.getFormSchemeKey());
        List regions = this.jtableParamService.getRegions(formKey);
        if (regions == null || regions.size() == 0) {
            JIODeleteResultObject returnInfo = new JIODeleteResultObject();
            returnInfo.setSuccess(false);
            returnInfo.setMessage(formData.getCode() + "\uff1a\u62a5\u8868\u6ca1\u6709\u533a\u57df");
            return returnInfo;
        }
        if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType())) {
            JIODeleteResultObject returnInfo = new JIODeleteResultObject();
            returnInfo.setSuccess(false);
            returnInfo.setMessage(formData.getCode() + "\uff1a\u5c01\u9762\u4ee3\u7801\u4e0d\u80fd\u5220\u9664");
            return returnInfo;
        }
        try {
            for (RegionData region : regions) {
                if (StringUtils.isNotEmpty((String)regionKey) && !regionKey.equalsIgnoreCase(region.getKey())) continue;
                RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
                regionQueryInfo.setContext(jtableContext);
                RegionDataFactory factory = new RegionDataFactory();
                factory.clearRegionDataSet(region, regionQueryInfo);
            }
        }
        catch (Exception e) {
            JTableException jtableException;
            JIODeleteResultObject returnInfo = new JIODeleteResultObject();
            String message = "";
            message = e instanceof JTableException ? ((jtableException = (JTableException)e).getDatas().length > 0 ? formData.getCode() + "\uff1a" + jtableException.getDatas()[0] : formData.getCode() + "\uff1a" + e.getMessage()) : formData.getCode() + "\uff1a" + e.getMessage();
            if (message.length() > 50) {
                message = message.substring(0, 50) + "...";
            }
            returnInfo.setMessage(message);
            returnInfo.setSuccess(false);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage() + "message", e);
            return returnInfo;
        }
        JIODeleteResultObject returnInfo = new JIODeleteResultObject();
        returnInfo.setSuccess(true);
        returnInfo.setMessage("success");
        return returnInfo;
    }
}


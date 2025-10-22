/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package nr.single.data.system;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleDataOptionsService {
    private static final Logger logger = LoggerFactory.getLogger(SingleDataOptionsService.class);
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    public boolean isUploadChangeUpper() {
        String value = this.systemOptionService.get("single_para_option_id", "singledata_option_changeupper");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "1".equalsIgnoreCase(value);
    }

    public int getUploadTreadStartOrgCount() {
        String value = this.systemOptionService.get("single_para_option_id", "singledata_option_upload_thread_orgcount");
        int num = 10000;
        if (StringUtils.isNotEmpty((String)value)) {
            try {
                num = Integer.parseInt(value);
            }
            catch (Exception e) {
                num = 10000;
                logger.info("\u7cfb\u7edf\u914d\u7f6e\u9519\u8bef\uff1a\u7ec4\u7ec7\u673a\u6784\u8fbe\u5230\u6307\u5b9a\u6570\u91cf\u65f6\u5f00\u542f\u5e76\u53d1\u4f18\u5316=" + value);
                logger.error(e.getMessage(), e);
            }
        }
        return num;
    }

    public int getUploadTreadCount() {
        String value = this.systemOptionService.get("single_para_option_id", "singledata_option_upload_threadcount");
        int num = 3;
        if (StringUtils.isNotEmpty((String)value)) {
            try {
                num = Integer.parseInt(value);
            }
            catch (Exception e) {
                num = 3;
                logger.info("\u7cfb\u7edf\u914d\u7f6e\u9519\u8bef\uff1a\u5f00\u542f\u5e76\u53d1\u4f18\u5316\u540e\u7684\u5e76\u53d1\u7ebf\u7a0b\u6570=" + value);
                logger.error(e.getMessage(), e);
            }
        }
        return num;
    }

    public boolean isUploadCheckZdm() {
        String value = this.systemOptionService.get("single_para_option_id", "singledata_option_upload_checkzdm");
        if (StringUtils.isEmpty((String)value)) {
            return true;
        }
        return "1".equalsIgnoreCase(value);
    }
}


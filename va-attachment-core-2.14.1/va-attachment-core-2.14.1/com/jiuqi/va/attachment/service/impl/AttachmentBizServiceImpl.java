/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.CopyFileCache
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizTemplateFileDO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO
 *  com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO
 *  com.jiuqi.va.attachment.domain.CopyProgressDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.config.CopyFileCache;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.dao.VaAttachmentBizRemarksDao;
import com.jiuqi.va.attachment.dao.VaAttachmentBizTemplateFileDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO;
import com.jiuqi.va.attachment.domain.AttachmentBizTemplateFileDO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO;
import com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO;
import com.jiuqi.va.attachment.domain.CopyProgressDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.service.AttachmentBizRecycleBinService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.attachment.service.AttachmentSchemeService;
import com.jiuqi.va.attachment.service.VaAttachmentBizRefService;
import com.jiuqi.va.attachment.service.impl.handle.AttachmentDefultHandleIntfImpl;
import com.jiuqi.va.attachment.storage.AttachmentBizStorage;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service(value="vaContentBizAttachmentServiceImpl")
public class AttachmentBizServiceImpl
implements AttachmentBizService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizServiceImpl.class);
    private static final String ATT_DELETE_KEY_PREFIX = "ATT_DELETE_";
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;
    @Autowired
    private AttachmentModeService attachmentModeService;
    @Autowired
    private AttachmentSchemeService attachmentSchemeService;
    @Autowired
    private VaAttachmentBizTemplateFileDao vaAttachmentBizTemplateFileDao;
    @Autowired
    private VaAttachmentBizRemarksDao vaAttachmentBizRemarksDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private List<AttachmentHandleIntf> attachmentHandleIntfList;
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private VaAttachmentBizRefService bizRefService;
    @Autowired
    private AttachmentBizConfirmService attachmentBizConfirmService;
    @Autowired
    private AttachmentBizRecycleBinService attachmentBizRecycleBinService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Value(value="${upload.path}")
    private String localpath;

    @Override
    public R ftpUpload(MultipartFile file, AttachmentBizDTO bizAttachmentDTO) {
        R r;
        UserLoginDTO user;
        String qcode = bizAttachmentDTO.getQuotecode();
        String atttype = bizAttachmentDTO.getAtttype();
        bizAttachmentDTO.setModename(atttype);
        if (bizAttachmentDTO.getCreateuser() == null && (user = ShiroUtil.getUser()) != null) {
            bizAttachmentDTO.setCreateuser(user.getId());
        }
        if ((r = this.bizHelpService.checkQtcode(qcode)) != null) {
            return r;
        }
        AttachmentModeDO attParm = new AttachmentModeDO();
        attParm.setName(atttype);
        AttachmentModeDTO attmode = this.attachmentModeService.get(attParm);
        SchemeEntity schemeEntity = this.getMessageFromMode(atttype, attmode);
        Integer storemode = schemeEntity.getStoremode();
        for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
            if (attachment.getStoremode() != storemode.intValue()) continue;
            return attachment.upload(file, bizAttachmentDTO, schemeEntity, attmode);
        }
        return R.error();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void downloadAll(AttachmentBizDTO param) {
        Object quotecodes = param.getExtInfo("quotecodes");
        if (param.getQuotecode() == null && quotecodes == null) {
            return;
        }
        List<Object> list = null;
        if (quotecodes != null) {
            list = new ArrayList();
            if (quotecodes instanceof List) {
                for (Iterator obj : (List)quotecodes) {
                    param.setQuotecode(obj.toString());
                    List<AttachmentBizDTO> listSub = this.list((AttachmentBizDO)param);
                    if (listSub == null || listSub.isEmpty()) continue;
                    list.addAll(listSub);
                }
            } else {
                List codes = JSONUtil.parseArray((String)quotecodes.toString(), String.class);
                for (String code : codes) {
                    param.setQuotecode(code);
                    List<AttachmentBizDTO> listSub = this.list((AttachmentBizDO)param);
                    if (listSub == null || listSub.isEmpty()) continue;
                    list.addAll(listSub);
                }
            }
        } else {
            list = this.list((AttachmentBizDO)param);
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        HashSet<String> ids = new HashSet<String>();
        if (param.getFileIds() != null) {
            for (Iterator iterator : param.getFileIds().trim().split("\\,")) {
                ids.add(((String)((Object)iterator)).trim());
            }
        }
        if (!ids.isEmpty()) {
            AttachmentBizDTO attachmentBizDTO = null;
            Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                attachmentBizDTO = (AttachmentBizDTO)iterator.next();
                if (ids.contains(attachmentBizDTO.getId().toString())) continue;
                iterator.remove();
            }
        }
        if (list.isEmpty()) {
            return;
        }
        AttachmentBizDTO downPram = new AttachmentBizDTO();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Throwable throwable = null;
            try (ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)baos, StandardCharsets.UTF_8);){
                HashMap<String, Integer> atts = new HashMap<String, Integer>();
                for (AttachmentBizDTO attachmentBizDTO : list) {
                    String fileName;
                    downPram.setQuotecode(attachmentBizDTO.getQuotecode());
                    downPram.setId(attachmentBizDTO.getId());
                    byte[] tytes = this.getFile(downPram);
                    if (tytes == null) continue;
                    String name = attachmentBizDTO.getName();
                    if (atts.containsKey(name)) {
                        Integer integer = (Integer)atts.get(name);
                        String[] split = name.split("\\.");
                        fileName = split.length == 2 ? split[0] + " (" + (integer + 1) + ")." + split[1] : name + " (" + (integer + 1) + ")";
                        atts.put(name, integer + 1);
                        atts.put(fileName, 0);
                    } else {
                        atts.put(name, 0);
                        fileName = name;
                    }
                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    zipOutputStream.write(tytes);
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.closeEntry();
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            LOGGER.error(" \u6279\u91cf\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
        }
        finally {
            try {
                baos.close();
            }
            catch (IOException e) {
                LOGGER.error(" \u6279\u91cf\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
            }
        }
        try {
            String name = param.getName();
            if (name == null) {
                name = System.currentTimeMillis() + ".zip";
            } else if (!name.endsWith(".zip")) {
                name = name + ".zip";
            }
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, "UTF-8")));
            OutputStream outputStream = RequestContextUtil.getOutputStream();
            outputStream.write(baos.toByteArray());
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e) {
            LOGGER.error(" \u6279\u91cf\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
        }
    }

    private byte[] getFile(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        if (attachmentBizDO.getSchemename() != null && !attachmentBizDO.getSchemename().trim().equals("")) {
            String schemename = attachmentBizDO.getSchemename();
            SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(schemename);
            Integer storemode = schemeEntity.getStoremode();
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != storemode.intValue()) continue;
                return attachment.getFile(param);
            }
        } else {
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != 3) continue;
                return attachment.getFile(param);
            }
        }
        return null;
    }

    @Override
    public R upload(MultipartFile file, AttachmentBizDTO bizAttachmentDTO) {
        R r;
        UserLoginDTO user;
        String qcode = bizAttachmentDTO.getQuotecode();
        if (!StringUtils.hasText(bizAttachmentDTO.getAtttype())) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.attachment.type.not.empty", new Object[0]));
        }
        String atttype = bizAttachmentDTO.getAtttype();
        bizAttachmentDTO.setModename(atttype);
        if (bizAttachmentDTO.getCreateuser() == null && (user = ShiroUtil.getUser()) != null) {
            bizAttachmentDTO.setCreateuser(user.getId());
        }
        if ((r = this.bizHelpService.checkQtcode(qcode)) != null) {
            return r;
        }
        SchemeEntity schemeEntity = new SchemeEntity();
        AttachmentModeDTO attmode = null;
        AttachmentModeDO attParm = new AttachmentModeDO();
        attParm.setName(atttype);
        attmode = this.attachmentModeService.get(attParm);
        if (attmode == null) {
            if (!"DEFAULT".equals(atttype)) {
                return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.attachment.type.not.exist", new Object[0]));
            }
            schemeEntity.setSchemename("DEFAULTSCHEME");
        } else {
            schemeEntity = this.getMessageFromMode(atttype, attmode);
        }
        Integer storemode = schemeEntity.getStoremode();
        LogUtil.add((String)"\u9644\u4ef6", (String)"\u4e0a\u4f20", (String)bizAttachmentDTO.getQuotecode(), (String)file.getOriginalFilename(), (String)bizAttachmentDTO.getModename());
        if ("DEFAULTSCHEME".equals(schemeEntity.getSchemename())) {
            for (AttachmentHandleIntf attachmentHandleIntf : this.attachmentHandleIntfList) {
                if (attachmentHandleIntf.getStoremode() != 3) continue;
                return attachmentHandleIntf.upload(file, bizAttachmentDTO, schemeEntity, attmode);
            }
        } else {
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != storemode.intValue()) continue;
                return attachment.upload(file, bizAttachmentDTO, schemeEntity, attmode);
            }
        }
        return R.error();
    }

    @Override
    public SchemeEntity getMessageFromMode(String atttype, AttachmentModeDTO attmode) {
        AttachmentSchemeDO attschemeParam = new AttachmentSchemeDO();
        attschemeParam.setName(attmode.getSchemename());
        AttachmentSchemeDO attscheme = this.attachmentSchemeService.get(attschemeParam);
        if (this.attachmentHandleIntfList != null) {
            for (AttachmentHandleIntf handle : this.attachmentHandleIntfList) {
                if (handle.getStoremode() != attscheme.getStoremode().intValue()) continue;
                handle.parseSchemeConfig(attscheme);
                break;
            }
        }
        SchemeEntity schemeEntity = new SchemeEntity();
        schemeEntity.setSchemename(attmode.getSchemename());
        schemeEntity.setFilePath(attmode.getAttpath());
        schemeEntity.setDegree(attscheme.getDegree());
        schemeEntity.setStoremode(attscheme.getStoremode());
        schemeEntity.setSchemeConfig(attscheme.getConfig());
        schemeEntity.setModeConfig(attmode.getConfig());
        return schemeEntity;
    }

    @Override
    public void download(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return;
        }
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        if (attachmentBizDO.getSchemename() != null && !attachmentBizDO.getSchemename().trim().equals("")) {
            String schemename = attachmentBizDO.getSchemename();
            SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(schemename);
            Integer storemode = schemeEntity.getStoremode();
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != storemode.intValue()) continue;
                attachment.download(param);
            }
        } else {
            for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                if (attachment.getStoremode() != 3) continue;
                attachment.download(param);
            }
        }
    }

    @Override
    public AttachmentBizDO getAtt(AttachmentBizDO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        return this.bizHelpService.get(param);
    }

    @Override
    public List<AttachmentBizDTO> listAtt(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        return this.list((AttachmentBizDO)param);
    }

    @Override
    public Map<String, List<AttachmentBizDTO>> listAttAndSchemeTitle(List<String> quotecodes, Boolean flag) {
        HashMap<String, List<AttachmentBizDTO>> atts = new HashMap<String, List<AttachmentBizDTO>>();
        AttachmentBizDO param = new AttachmentBizDO();
        for (int i = 0; i < quotecodes.size(); ++i) {
            String qcode = quotecodes.get(i);
            R r = this.bizHelpService.checkQtcode(qcode);
            if (r != null) continue;
            param.setQuotecode(qcode);
            param.setSuffix(qcode.substring(0, 6));
            List<AttachmentBizDTO> list = this.list(param, flag);
            atts.put(qcode, list);
        }
        return atts;
    }

    @Override
    public List<AttachmentBizDTO> list(AttachmentBizDO param, Boolean flag) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        if (!AttachmentBizStorage.hasTable(param.getTenantName(), param.getSuffix())) {
            return null;
        }
        List<AttachmentBizDTO> attlist = null;
        attlist = flag != false ? this.bizAttachmentDao.listAttsAndSchemeTitle(param) : this.bizAttachmentDao.list(param);
        HashMap<String, String> map = new HashMap<String, String>();
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(param.getTenantName());
        String createuser = null;
        for (AttachmentBizDTO att : attlist) {
            createuser = att.getCreateuser();
            if (StringUtils.hasText(createuser)) {
                if (map.containsKey(createuser)) {
                    att.setUsername((String)map.get(att.getCreateuser()));
                    continue;
                }
                userDTO.setId(createuser);
                UserDO user = this.authUserClient.get(userDTO);
                if (user != null && StringUtils.hasText(user.getName())) {
                    att.setUsername(user.getName());
                    map.put(att.getCreateuser(), user.getName());
                    continue;
                }
                att.setUsername("");
                map.put(att.getCreateuser(), "");
                continue;
            }
            att.setUsername("");
        }
        return attlist;
    }

    @Override
    public List<AttachmentBizDTO> list(AttachmentBizDO param) {
        List<AttachmentBizDTO> attlist = this.queryAttList(param);
        if (attlist == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(param.getTenantName());
        String createuser = null;
        for (AttachmentBizDTO att : attlist) {
            createuser = att.getCreateuser();
            if (StringUtils.hasText(createuser)) {
                if (map.containsKey(createuser)) {
                    att.setUsername((String)map.get(att.getCreateuser()));
                } else {
                    userDTO.setId(createuser);
                    UserDO user = this.authUserClient.get(userDTO);
                    if (user != null && StringUtils.hasText(user.getName())) {
                        att.setUsername(user.getName());
                        map.put(att.getCreateuser(), user.getName());
                    } else {
                        att.setUsername("");
                        map.put(att.getCreateuser(), "");
                    }
                }
            } else {
                att.setUsername("");
            }
            UUID id = att.getId();
            AttachmentBizRemarksDO attachmentBizRemarksDO = new AttachmentBizRemarksDO();
            attachmentBizRemarksDO.setMasterid(id);
            AttachmentBizRemarksDO remarksDO = (AttachmentBizRemarksDO)this.vaAttachmentBizRemarksDao.selectOne(attachmentBizRemarksDO);
            if (remarksDO == null) continue;
            att.setRemarks(remarksDO.getRemarks());
        }
        return attlist;
    }

    @Override
    public List<AttachmentBizDTO> queryAttList(AttachmentBizDO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        if (!AttachmentBizStorage.hasTable(param.getTenantName(), param.getSuffix())) {
            return null;
        }
        return this.bizAttachmentDao.list(param);
    }

    @Override
    public R updateAtt(List<AttachmentBizDO> param, String qcode) {
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return r;
        }
        String suffix = qcode.substring(0, 6);
        for (AttachmentBizDO bizAttachmentDO : param) {
            bizAttachmentDO.setSuffix(suffix);
            this.update(bizAttachmentDO);
        }
        return R.ok();
    }

    @Override
    public int update(AttachmentBizDO attachment) {
        if (attachment == null) {
            return 0;
        }
        if (attachment.getSuffix() == null) {
            if (attachment.getQuotecode() == null) {
                return 0;
            }
            attachment.setSuffix(attachment.getQuotecode().substring(0, 6));
        }
        if (attachment.getQuotecode() == null && attachment.getId() == null) {
            return 0;
        }
        if (attachment.getId() == null) {
            return this.bizAttachmentDao.updateAllAtt(attachment);
        }
        return this.bizAttachmentDao.update(attachment);
    }

    @Override
    public R removeAtt(List<AttachmentBizDTO> param, String qcode) {
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return r;
        }
        String suffix = qcode.substring(0, 6);
        if (param.isEmpty()) {
            AttachmentBizDO byQtcode = new AttachmentBizDO();
            byQtcode.setSuffix(suffix);
            byQtcode.setQuotecode(qcode);
            param = this.list(byQtcode);
        }
        for (AttachmentBizDTO bizAttachmentDO : param) {
            bizAttachmentDO.setSuffix(suffix);
            try {
                this.insertRecycleBin(bizAttachmentDO);
            }
            catch (Exception e) {
                LOGGER.error("\u9644\u4ef6\u5220\u9664\u5931\u8d25", e);
                return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.attachment.delete.error", new Object[0]));
            }
        }
        return R.ok();
    }

    @Override
    public R deleteFile(AttachmentBizDTO bizAttachmentDO) {
        boolean copyQ = CopyFileCache.isCopy((String)bizAttachmentDO.getQuotecode());
        if (copyQ) {
            LOGGER.error("\u9644\u4ef6\u6b63\u5728\u590d\u5236\u4e2d\uff0c\u65e0\u6cd5\u5220\u9664\uff01" + bizAttachmentDO.getId());
            return R.error();
        }
        R execute = RedisLockUtil.execute(() -> {
            LogUtil.add((String)"\u9644\u4ef6", (String)"\u5220\u9664", (String)bizAttachmentDO.getQuotecode(), (String)bizAttachmentDO.getName(), (String)String.valueOf(bizAttachmentDO.getId()));
            UUID sourceID = this.getSourceID((AttachmentBizDO)bizAttachmentDO);
            boolean isRef = this.bizRefService.checkRef(sourceID);
            if (!isRef) {
                AttachmentBizDTO attachmentBizDTO = this.bizHelpService.get((AttachmentBizDO)bizAttachmentDO);
                if (attachmentBizDTO == null) {
                    return;
                }
                if (attachmentBizDTO.getSchemename() != null && !attachmentBizDTO.getSchemename().trim().isEmpty()) {
                    SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
                    int stroemode = schemeEntity.getStoremode();
                    for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                        if (attachment.getStoremode() != stroemode) continue;
                        R remove = attachment.remove(attachmentBizDTO);
                        if (remove.getCode() != 0) {
                            throw new RuntimeException(remove.getMsg());
                        }
                        break;
                    }
                } else {
                    for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                        if (attachment.getStoremode() != 3) continue;
                        R remove = attachment.remove(attachmentBizDTO);
                        if (remove.getCode() != 0) {
                            throw new RuntimeException(remove.getMsg());
                        }
                        break;
                    }
                }
            }
            UUID id = bizAttachmentDO.getId();
            AttachmentBizRemarksDO attachmentBizRemarksDO = new AttachmentBizRemarksDO();
            attachmentBizRemarksDO.setMasterid(id);
            this.vaAttachmentBizRemarksDao.delete(attachmentBizRemarksDO);
            int remove = this.remove((AttachmentBizDO)bizAttachmentDO);
            if (remove == 0) {
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.attachment.delete.db.error", new Object[0]));
            }
            boolean b = this.bizRefService.refMoveFile(sourceID);
            if (!b) {
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.attachment.delete.ref.error", new Object[0]));
            }
        }, (String)(ATT_DELETE_KEY_PREFIX + bizAttachmentDO.getId()), (long)10000L, (boolean)true);
        if (execute.getCode() != 0) {
            return execute;
        }
        return R.ok();
    }

    private UUID getSourceID(AttachmentBizDO bizAttachmentDO) {
        UUID sourceID;
        if (bizAttachmentDO.getSourceid() != null) {
            sourceID = bizAttachmentDO.getSourceid();
        } else {
            if (bizAttachmentDO.getId() == null) {
                return null;
            }
            AttachmentBizDO attachmentBiz = new AttachmentBizDO();
            attachmentBiz.setId(bizAttachmentDO.getId());
            if (bizAttachmentDO.getSuffix() != null) {
                attachmentBiz.setSuffix(bizAttachmentDO.getSuffix());
            } else {
                if (bizAttachmentDO.getQuotecode() == null) {
                    return null;
                }
                attachmentBiz.setSuffix(bizAttachmentDO.getQuotecode().split("-")[0]);
            }
            attachmentBiz.setQuotecode(bizAttachmentDO.getQuotecode());
            AttachmentBizDTO attachmentBizDTO = this.bizAttachmentDao.get(attachmentBiz);
            sourceID = attachmentBizDTO != null && attachmentBizDTO.getSourceid() != null ? attachmentBizDTO.getSourceid() : bizAttachmentDO.getId();
        }
        return sourceID;
    }

    @Override
    public int remove(AttachmentBizDO attachment) {
        return this.bizAttachmentDao.remove(attachment);
    }

    @Override
    public R confim(AttachmentBizDO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return r;
        }
        param.setSuffix(qcode.substring(0, 6));
        List<AttachmentBizDTO> list = this.list(param);
        if (list == null || list.isEmpty()) {
            return R.ok();
        }
        for (AttachmentBizDTO bizAttachmentDTO : list) {
            bizAttachmentDTO.setSuffix(param.getSuffix());
            if (bizAttachmentDTO.getStatus() == 2) {
                try {
                    this.insertRecycleBin(bizAttachmentDTO);
                    continue;
                }
                catch (Exception e) {
                    LOGGER.error("\u9644\u4ef6\u5220\u9664\u5931\u8d25", e);
                    return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.attachment.delete.error", new Object[0]));
                }
            }
            if (bizAttachmentDTO.getStatus() != 0) continue;
            bizAttachmentDTO.setStatus(Integer.valueOf(1));
            this.update((AttachmentBizDO)bizAttachmentDTO);
        }
        boolean b = this.attachmentBizConfirmService.deleteConfirm(qcode);
        if (!b) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R reset(AttachmentBizDO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return r;
        }
        param.setSuffix(qcode.substring(0, 6));
        List<AttachmentBizDTO> list = this.list(param);
        if (list != null && !list.isEmpty()) {
            for (AttachmentBizDO attachmentBizDO : list) {
                attachmentBizDO.setSuffix(param.getSuffix());
                if (attachmentBizDO.getStatus() == 0) {
                    boolean copyQ = CopyFileCache.isCopy((String)attachmentBizDO.getQuotecode());
                    if (copyQ) {
                        LOGGER.error("\u53d6\u6d88\u4e0a\u4f20\u5931\u8d25,\u6b63\u5728\u590d\u5236\uff1a" + attachmentBizDO.getId());
                        return R.error();
                    }
                    R execute = RedisLockUtil.execute(() -> {
                        int remove;
                        LogUtil.add((String)"\u9644\u4ef6", (String)"\u91cd\u7f6e", (String)bizAttachmentDTO.getQuotecode(), (String)bizAttachmentDTO.getName(), (String)bizAttachmentDTO.getModename());
                        UUID sourceId = this.getSourceID(bizAttachmentDTO);
                        boolean isRef = this.bizRefService.checkRef(sourceId);
                        if (!isRef && bizAttachmentDTO.getFilepath() != null) {
                            try {
                                FileUtil.deleteFile(this.localpath + bizAttachmentDTO.getFilepath());
                            }
                            catch (Exception e) {
                                LOGGER.error(" \u53d6\u6d88\u4e0a\u4f20\u5931\u8d25\uff1a" + e);
                                throw new RuntimeException("\u53d6\u6d88\u4e0a\u4f20\u5931\u8d25");
                            }
                        }
                        if ((remove = this.remove(bizAttachmentDTO)) == 0) {
                            LOGGER.error("\u9644\u4ef6\u5220\u9664\u6570\u636e\u5e93\u4fe1\u606f\u5931\u8d25");
                            throw new RuntimeException("\u9644\u4ef6\u5220\u9664\u6570\u636e\u5e93\u4fe1\u606f\u5931\u8d25");
                        }
                        boolean b = this.bizRefService.refMoveFile(sourceId);
                        if (!b) {
                            LOGGER.error("\u9644\u4ef6\u5f15\u7528\u6570\u91cf\u66f4\u65b0\u5931\u8d25\u5931\u8d25");
                            throw new RuntimeException("\u9644\u4ef6\u5f15\u7528\u6570\u91cf\u66f4\u65b0\u5931\u8d25\u5931\u8d25");
                        }
                    }, (String)(ATT_DELETE_KEY_PREFIX + attachmentBizDO.getId()), (long)10000L, (boolean)true);
                    if (execute.getCode() == 0) continue;
                    return execute;
                }
                if (attachmentBizDO.getStatus() != 2) continue;
                attachmentBizDO.setStatus(Integer.valueOf(1));
                this.update(attachmentBizDO);
            }
        }
        return R.ok();
    }

    @Override
    public R getAttNumByDO(AttachmentBizDO param) {
        R r = R.ok();
        int num = 0;
        String quotecode = param.getQuotecode();
        String suffix = quotecode.substring(0, 6);
        if (AttachmentBizStorage.hasTable(param.getTenantName(), suffix)) {
            AttachmentBizDO attachmentBizDTO = new AttachmentBizDO();
            attachmentBizDTO.setQuotecode(quotecode);
            attachmentBizDTO.setSuffix(suffix);
            num = this.getAttNumByQuotecode(attachmentBizDTO);
        }
        r.put("attnum", (Object)num);
        return r;
    }

    @Override
    public R getQuotecode() {
        R r = R.ok();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM-");
        String dateString = formatter.format(new Date());
        r.put("quotecode", (Object)(dateString + UUID.randomUUID().toString()));
        return r;
    }

    @Override
    public void clean(CleanUselessAttachmentDTO param) {
        if (!AttachmentBizStorage.hasTable(param.getTenantName(), param.getSuffix())) {
            return;
        }
        List<AttachmentBizDTO> list = this.bizAttachmentDao.listUseless(param);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (AttachmentBizDTO attachmentBizDTO : list) {
            LOGGER.info("\u9644\u4ef6\u8fd8\u539f\u5f00\u59cb\uff1a{}", (Object)attachmentBizDTO.getName());
            attachmentBizDTO.setStatus(Integer.valueOf(1));
            this.update((AttachmentBizDO)attachmentBizDTO);
        }
    }

    public void insertRecycleBin(AttachmentBizDTO attachmentBizDTO) {
        TransactionStatus transaction = this.platformTransactionManager.getTransaction((TransactionDefinition)new DefaultTransactionDefinition());
        try {
            int count;
            attachmentBizDTO.setStatus(Integer.valueOf(-1));
            int update = this.update((AttachmentBizDO)attachmentBizDTO);
            if (update == 0) {
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.attachment.delete.error", new Object[0]));
            }
            BizAttachmentRecycleBinDTO insert = new BizAttachmentRecycleBinDTO();
            insert.setQuoteid(attachmentBizDTO.getId());
            insert.setQuotecode(attachmentBizDTO.getQuotecode());
            if (attachmentBizDTO.getDeleteType() == null) {
                insert.setDeletetype(AttachmentConst.DELETE_STATUS_NORMAL);
            } else {
                insert.setDeletetype(attachmentBizDTO.getDeleteType());
            }
            insert.setFilename(attachmentBizDTO.getName());
            insert.setFilepath(attachmentBizDTO.getFilepath());
            BizAttachmentConfirmDTO bizAttachmentConfirmDTO = new BizAttachmentConfirmDTO();
            bizAttachmentConfirmDTO.setQuotecode(attachmentBizDTO.getQuotecode());
            BizAttachmentConfirmDTO query = this.attachmentBizConfirmService.query(bizAttachmentConfirmDTO);
            if (query != null) {
                insert.setSourceinfo(query.getExtdata());
            }
            if ((count = this.attachmentBizRecycleBinService.insert(insert)) == 0) {
                throw new RuntimeException("\u9644\u4ef6\u5220\u9664\u8bb0\u5f55\u63d2\u5165\u5931\u8d25");
            }
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            this.platformTransactionManager.rollback(transaction);
            throw e;
        }
    }

    @Override
    public boolean checkAttachment(AttachmentBizDO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return false;
        }
        param.setSuffix(qcode.substring(0, 6));
        List<AttachmentBizDTO> list = this.list(param);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        List collect = list.stream().filter(o -> o.getStatus() != 2).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(collect);
    }

    @Override
    public int getAttNumByQuotecode(AttachmentBizDO attachment) {
        int num = 0;
        num = this.bizAttachmentDao.getAttNumByQuotecode(attachment);
        return num;
    }

    @Override
    public void downloadTemplate(UUID masterid) {
        AttachmentBizTemplateFileDO templateFile = new AttachmentBizTemplateFileDO();
        templateFile.setMasterId(masterid);
        AttachmentBizTemplateFileDO attachmentBizTemplateFileDO = (AttachmentBizTemplateFileDO)this.vaAttachmentBizTemplateFileDao.selectOne(templateFile);
        if (attachmentBizTemplateFileDO == null) {
            return;
        }
        byte[] bytes = attachmentBizTemplateFileDO.getTemplateFile();
        if (bytes == null) {
            return;
        }
        OutputStream outPutStream = null;
        try {
            RequestContextUtil.setResponseContentType((String)"application/x-msdownload");
            RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
            try {
                RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(attachmentBizTemplateFileDO.getName(), "UTF-8")));
            }
            catch (Exception e) {
                LOGGER.error(" \u6587\u4ef6\u9ed8\u8ba4\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
            }
            outPutStream = RequestContextUtil.getOutputStream();
            outPutStream.write(bytes);
            outPutStream.close();
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void syncOrdinal(List<AttachmentBizDO> attachmentBizDOS, String quotecode) {
        R r = this.bizHelpService.checkQtcode(quotecode);
        if (r != null) {
            throw new RuntimeException(r.getMsg());
        }
        String suffix = quotecode.substring(0, 6);
        for (AttachmentBizDO attachmentBizDO : attachmentBizDOS) {
            attachmentBizDO.setSuffix(suffix);
            this.bizAttachmentDao.updateOrdinal(attachmentBizDO);
        }
    }

    @Override
    public Map<String, Object> copyFiles(List<AttachmentBizDO> params) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (params == null || params.isEmpty()) {
            return result;
        }
        HashMap<String, String> quoteCodes = new HashMap<String, String>();
        ArrayList<AttachmentBizDTO> list = new ArrayList<AttachmentBizDTO>();
        for (AttachmentBizDO param : params) {
            String quotecode = param.getQuotecode();
            String newQuoteCode = quotecode.split("-")[0] + "-" + UUID.randomUUID();
            quoteCodes.put(quotecode, newQuoteCode);
            AttachmentBizDO query = new AttachmentBizDO();
            query.setQuotecode(quotecode);
            query.setSuffix(quotecode.split("-")[0]);
            list.addAll(this.bizAttachmentDao.list(query));
        }
        if (list.isEmpty()) {
            return result;
        }
        String key = "COPY_FILE_" + UUID.randomUUID();
        CopyProgressDO copyProgressDO = new CopyProgressDO();
        copyProgressDO.setTotal(list.size());
        copyProgressDO.setCur(0);
        this.bizHelpService.syncCopyFileProgress(key, copyProgressDO);
        this.bizHelpService.asyncCopyFile(quoteCodes, this.attachmentHandleIntfList, list, key);
        result.put("key", key);
        result.put("quoteCodes", quoteCodes);
        return result;
    }

    @Override
    public R copyFilesRef(AttachmentBizDTO param) {
        if (param.getQuotecode() == null) {
            return R.error();
        }
        HashMap<String, String> quoteCodes = new HashMap<String, String>();
        String quotecode = param.getQuotecode();
        String newQuoteCode = param.getNewQuoteCode() == null ? quotecode.split("-")[0] + "-" + UUID.randomUUID() : param.getNewQuoteCode();
        quoteCodes.put(quotecode, newQuoteCode);
        try {
            LogUtil.add((String)"\u9644\u4ef6", (String)"\u590d\u5236\u5f15\u7528", (String)quotecode, (String)newQuoteCode, (String)"");
            this.bizHelpService.copyFileRef(quoteCodes, param);
        }
        catch (Exception e) {
            return R.error((String)("\u590d\u5236\u5931\u8d25" + e.getMessage()));
        }
        return R.ok().put("quoteCode", quoteCodes);
    }

    @Override
    public void cleanMarkAsDeletedAttachment(CleanUselessAttachmentDTO param) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        List tableNames = jDialect.getTableNames(param.getTenantName());
        if (tableNames == null || tableNames.isEmpty()) {
            return;
        }
        for (String tableName : tableNames) {
            String suffix;
            if (!tableName.toUpperCase().startsWith("BIZATTACHMENT_") || !this.isNumeric(suffix = tableName.substring(14))) continue;
            param.setSuffix(suffix);
            List<AttachmentBizDO> list = this.bizAttachmentDao.listConfirmeDeleted(param);
            if (CollectionUtils.isEmpty(list)) continue;
            block1: for (AttachmentBizDO attachmentBizDTO : list) {
                attachmentBizDTO.setSuffix(suffix);
                if (attachmentBizDTO.getFilepath() == null) {
                    this.remove(attachmentBizDTO);
                    continue;
                }
                AttachmentBizDTO bizAttachmentBizDTO = new AttachmentBizDTO();
                bizAttachmentBizDTO.setId(attachmentBizDTO.getId());
                bizAttachmentBizDTO.setFilepath(attachmentBizDTO.getFilepath());
                bizAttachmentBizDTO.setSchemename(attachmentBizDTO.getSchemename());
                bizAttachmentBizDTO.setId(attachmentBizDTO.getId());
                bizAttachmentBizDTO.setQuotecode(attachmentBizDTO.getQuotecode());
                if (!StringUtils.hasText(attachmentBizDTO.getSchemename())) {
                    AttachmentDefultHandleIntfImpl defaultHandle = (AttachmentDefultHandleIntfImpl)ApplicationContextRegister.getBean(AttachmentDefultHandleIntfImpl.class);
                    defaultHandle.remove(bizAttachmentBizDTO);
                    this.remove(attachmentBizDTO);
                    continue;
                }
                SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
                for (AttachmentHandleIntf attachment : this.attachmentHandleIntfList) {
                    if (attachment.getStoremode() != schemeEntity.getStoremode().intValue()) continue;
                    attachment.remove(bizAttachmentBizDTO);
                    this.remove(attachmentBizDTO);
                    continue block1;
                }
            }
        }
    }

    private boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isDigit(cs.charAt(i))) continue;
            return false;
        }
        return true;
    }
}


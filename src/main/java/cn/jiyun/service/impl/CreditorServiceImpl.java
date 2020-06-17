package cn.jiyun.service.impl;


import cn.jiyun.mapper.CreditorInfoMapper;
import cn.jiyun.model.CreditorInfo;
import cn.jiyun.service.CreditorService;
import cn.jiyun.utils.FastDFSUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CreditorServiceImpl implements CreditorService {
    @Resource
    private CreditorInfoMapper creditorInfoMapper;
    @Override
    public List<CreditorInfo> selectAll() {
        List<CreditorInfo> list=creditorInfoMapper.selectAll();
        return list;
    }

    @Override
    public CreditorInfo selectById(Integer id) {
        return creditorInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateFileInfo(CreditorInfo creditorInfo) {
        creditorInfoMapper.updateByPrimaryKeySelective(creditorInfo);
    }

    @Override
    public void deleteFileById(Integer id) {
        CreditorInfo creditorInfo=creditorInfoMapper.selectByPrimaryKey(id);
        FastDFSUtil.delete(creditorInfo.getGroupName(),creditorInfo.getRemoteFilePath());
        creditorInfo.setRemoteFilePath("");
        creditorInfo.setGroupName("");
        creditorInfo.setOldFilename("");
        creditorInfo.setFileType("");
        creditorInfo.setFileSize(0L);
        creditorInfoMapper.updateByPrimaryKeySelective(creditorInfo);
    }
}

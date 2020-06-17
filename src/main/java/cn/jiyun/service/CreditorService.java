package cn.jiyun.service;



import cn.jiyun.model.CreditorInfo;

import java.util.List;

public interface CreditorService {
    List<CreditorInfo> selectAll();

    CreditorInfo selectById(Integer id);

    void updateFileInfo(CreditorInfo creditorInfo);

    void deleteFileById(Integer id);
}

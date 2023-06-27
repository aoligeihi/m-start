package com.yy.star.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    /**
     * 文件上传
     * @return
     */
    String fileUpload(MultipartFile file);

    /**
     * 文件下载
     */
    String fileDownload();

    /**
     * 返回问价访问地址
     */
    String fileView();
}

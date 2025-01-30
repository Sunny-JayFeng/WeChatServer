package com.dolphin.wechatserver.controller;

import com.dolphin.wechatserver.constant.FilePathEnum;
import com.dolphin.wechatserver.response.ResponseData;
import com.dolphin.wechatserver.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 controller
 *
 * @author dolphin
 * @date 2025-01-21
 */
@RestController
@RequestMapping("/wechat/file")
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileService;

    /**
     * 上传文件
     *
     * @param request 请求
     * @return 返回文件在服务器上的地址
     */
    @PostMapping("/uploadAvatarFile")
    public Map<Object, Object> uploadAvatarFile(HttpServletRequest request) {
        log.info("uploadFile 上传文件");
        return fileService.uploadFile(request, FilePathEnum.USER_AVATAR_FILE_PATH.basicFilePath()).responseData;
    }
//
//    /**
//     * 删除文件
//     * @param fileName 文件名
//     * @return 返回删除结果
//     */
//    @DeleteMapping("/deleteFile")
//    public ResponseMessage deleteFile(@RequestParam("fileName") String fileName) {
//        log.info("deleteFile 删除文件 fileName: {}", fileName);
//        return requestSuccess(fileService.deleteFile(FilePathEnum.USER_BASIC_FILE_PATH.basicFilePath(), fileName));
//    }
//
//    /**
//     * 批量删除文件
//     * @param fileNameList 文件名列表
//     * @return 返回删除结果
//     */
//    @DeleteMapping("/batchDeleteFile")
//    public ResponseMessage batchDeleteFile(@RequestBody List<String> fileNameList) {
//        log.info("batchDeleteFile 批量删除文件");
//        return requestSuccess(fileService.batchDeleteFile(FilePathEnum.USER_BASIC_FILE_PATH.basicFilePath(), fileNameList));
//    }

}

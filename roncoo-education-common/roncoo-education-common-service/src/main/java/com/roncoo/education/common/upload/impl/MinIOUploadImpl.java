package com.roncoo.education.common.upload.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.roncoo.education.common.tools.JsonUtil;
import com.roncoo.education.common.upload.Upload;
import com.roncoo.education.common.upload.UploadFace;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengyw
 */
@Slf4j
@Component(value = "minio")
public class MinIOUploadImpl implements UploadFace {

    /**
     * 公共读的文件都存入该目录
     */
    private static final String PUBLIC = "public";
    private static final String PRIVATE = "private";

    @Override
    public String uploadPic(MultipartFile file, Upload upload) {
        try {
            String fileName = IdUtil.simpleUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
            String filePath = uploadForMinio(upload, PUBLIC, fileName, file.getName(), file.getContentType(), file.getInputStream());
            return getMinioFileUrl(upload.getMinioDomain(), filePath);
        } catch (Exception e) {
            log.error("MinIO上传错误", e);
        }
        return "";
    }

    @Override
    public String uploadDoc(MultipartFile file, Upload upload, Boolean isPublicRead) {
        try {
            String fileName = IdUtil.simpleUUID() + "." + FileUtil.getSuffix(file.getOriginalFilename());
            String filePath = uploadForMinio(upload, isPublicRead ? PUBLIC : PRIVATE, fileName, file.getName(), file.getContentType(), file.getInputStream());
            return getMinioFileUrl(upload.getMinioDomain(), filePath);
        } catch (Exception e) {
            log.error("MinIO上传错误", e);
        }
        return "";
    }

    @Override
    public String getDownloadUrl(String docUrl, int expireSeconds, Upload upload) {
        MinioClient minioClient = getMinioClient(upload);
        String key = docUrl.replace(upload.getMinioDomain() + upload.getMinioBucket() + StrUtil.SLASH, "");
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(upload.getMinioBucket()).object(key).expiry(expireSeconds).build());
        } catch (Exception e) {
            log.error("获取失败", e);
            return "";
        }
        url = url.substring(url.indexOf("/") + 1);
        url = url.substring(url.indexOf("/") + 1);
        url = url.substring(url.indexOf("/") + 1);
        return upload.getMinioDomain() + url;
    }

    @Override
    public String getPreviewConfig(String docUrl, int expireSeconds, Upload upload) {
        if (StringUtils.hasText(upload.getMinioPreviewUrl())) {
            String previewUrl = getMinioFileUrl(upload.getMinioPreviewUrl(), "onlinePreview?url=");
            try {
                Map<String, String> map = new HashMap<>();
                map.put("previewUrl", previewUrl + URLEncoder.encode(Base64.encode(getDownloadUrl(docUrl, expireSeconds, upload)), "utf-8"));
                return JsonUtil.toJsonString(map);
            } catch (UnsupportedEncodingException e) {
                log.error("编码失败", e);
            }
        }
        return "";
    }

    /**
     * 上传文件到MiniIO
     *
     * @param storageConfig bucket名称
     * @param fileDir       文件目录
     * @param fileName      文件名称
     * @param stream        文件流
     * @return 上传后的文件地址
     * @throws Exception 上传异常
     */
    private String uploadForMinio(Upload storageConfig, String fileDir, String fileName, String fileOriginalName, String contentType, InputStream stream) throws Exception {
        MinioClient minioClient = getMinioClient(storageConfig);

        // 处理前缀目录
        String objectName = StringUtils.hasText(fileDir) ? fileDir + "/" + fileName : fileName;

        // 设置文件下载名称
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileOriginalName, "UTF-8"));
        if (StringUtils.hasText(contentType)) {
            headerMap.put("Content-Type", contentType);
        }

        minioClient.putObject(PutObjectArgs.builder().bucket(storageConfig.getMinioBucket()).object(objectName).headers(headerMap).stream(stream, stream.available(), -1).build());
        return storageConfig.getMinioBucket() + "/" + objectName;
    }

    private String getMinioFileUrl(String miniFileDomain, String filePath) {
        if (miniFileDomain.endsWith("/")) {
            return miniFileDomain + filePath;
        } else {
            return miniFileDomain + "/" + filePath;
        }
    }

    /**
     * 创建MinioClient对象
     *
     * @param upload
     * @return
     */
    private MinioClient getMinioClient(Upload upload) {
        MinioClient minioClient = MinioClient.builder().endpoint(upload.getMinioEndpoint()).credentials(upload.getMinioAccessKey(), upload.getMinioSecretKey()).build();
        if (StringUtils.hasText(upload.getMinioBucket())) {
            try {
                if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(upload.getMinioBucket()).build())) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(upload.getMinioBucket()).build());
                    // 设置访问策略
                    String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\"],\"Resource\":[\"arn:aws:s3:::{bucket}\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::{bucket}\"],\"Condition\":{\"StringEquals\":{\"s3:prefix\":[\"{public}/**\"]}}},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::{bucket}/{public}/***\"]}]}\n";
                    minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(upload.getMinioBucket()).config(policy.replace("{bucket}", upload.getMinioBucket()).replace("{public}", PUBLIC)).build());
                }
            } catch (Exception e) {
                throw new RuntimeException("初始化MinIo错误", e);
            }
        }
        return minioClient;
    }

}

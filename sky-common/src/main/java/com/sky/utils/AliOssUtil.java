package com.sky.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;
//public class AliOssUtil {
//
//    private String endpoint;
//    private String accessKeyId;
//    private String accessKeySecret;
//    private String bucketName;
//
//    /**
//     * 文件上传
//     *
//     * @param bytes
//     * @param objectName
//     * @return
//     */
//    public String upload(byte[] bytes, String objectName) {
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        try {
//            // 创建PutObject请求。
//            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//
//        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
//        StringBuilder stringBuilder = new StringBuilder("https://");
//        stringBuilder
//                .append(bucketName)
//                .append(".")
//                .append(endpoint)
//                .append("/")
//                .append(objectName);
//
//        log.info("文件上传到:{}", stringBuilder.toString());
//
//        return stringBuilder.toString();
//    }
//}



/**
 * 阿里云 OSS 工具类
 * 因为不属于controller层，也不属于service层，所以用component注解来存放到IOC容器里
 */
@Component
public class AliOssUtil {

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 从环境变量读取
    String accessKeyId = System.getenv("ALIYUN_ACCESS_KEY_ID");
    String accessKeySecret = System.getenv("ALIYUN_ACCESS_KEY_SECRET");
    private String bucketName = "takeout--sky";

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

}
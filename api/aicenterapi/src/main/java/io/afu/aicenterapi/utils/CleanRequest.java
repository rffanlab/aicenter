package io.afu.aicenterapi.utils;



import io.afu.aicenterapi.enums.sys.BaseErr;
import io.afu.aicenterapi.exceptions.BaseException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 干净的请求
 * 不含任何第三方依赖，纯Java包实现
 * 此方法没有实现线程池，因此不适合在大并发的时候使用，建议使用OKHttp等第三方库
 */
@Slf4j
public class CleanRequest {


    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String PUT = "PUT";

    public static final String DELETE = "DELETE";

    public static final String HEAD = "HEAD";

    public static final String OPTIONS = "OPTIONS";

    public static final String TRACE = "TRACE";

    public static final String PATCH = "PATCH";

    public static final String CONNECT = "CONNECT";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String DEFAULT_CONTENT_TYPE_JSON = "application/json";

    public static final String DEFAULT_CONTENT_TYPE_XML = "application/xml";

    public static final String DEFAULT_CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String DEFAULT_CONTENT_TYPE_MULTIPART = "multipart/form-data";

    public static final String DEFAULT_CONTENT_TYPE_TEXT = "text/plain";

    public static final String DEFAULT_CONTENT_TYPE_STREAM = "application/octet-stream";

    public static final String DEFAULT_CONTENT_TYPE_HTML = "text/html";

    public static final String USER_AGENT = "User-Agent";

    private String CONTENT_TYPE = DEFAULT_CONTENT_TYPE;

    private Map<String,String> headers;

    private Map<String,String> params;

    private Map<String,String> files;

    private Map<String,FileBytes> fileBytes;

    private Map<String,String> cookies;

    private String url;

    private String location;


    private Boolean allowRedirect = true;


    public CleanRequest addCookie(String name,String value) {
        if (cookies == null) {
            cookies = new HashMap<>();
        }
        cookies.put(name,value);
        return this;
    }

    public CleanRequest addCookies(Map<String,String> cookies) {
        if (this.cookies != null) {
            this.cookies.putAll(cookies);
        }else {
            this.cookies = cookies;
        }
        return this;
    }



    public CleanRequest disAllowRedirect() {
        this.allowRedirect = false;
        return this;
    }

    private CleanResponse response;


    public static CleanRequest getInstance() {
        return (new CleanRequest()).addHeader("accept","*/*")
                .addHeader("Connection","Keep-Alive")
                .addHeader("Charset","UTF-8")
                ;
    }

    /**
     * 添加请求头
     * @param key 请求头的key
     * @param value 请求头的value
     * @return 返回当前对象
     */
    public CleanRequest addHeader(String key, String value) {
        // 先进行判空 判断headers是否为空
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    /**
     * 添加User-Agent
     */
    public CleanRequest addUserAgent(String userAgent) {
        return addHeader("User-Agent", userAgent);
    }


    /**
     * 添加请求参数
     * @param key 请求参数的key
     * @param value 请求参数的value
     * @return 返回当前对象
     */
    public CleanRequest addParam(String key, String value) {
        // 先进行判空 判断params是否为空
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    /**
     * 设置参数
     */
    public CleanRequest setParams(Map<String, String> params) {
        if (this.params != null) {
            this.params.putAll(params);
        }else {
            this.params = params;
        }
        return this;
    }

    /**
     * 设置请求头
     */
    public CleanRequest setHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }else {
            this.headers = headers;
        }
        return this;
    }

    /**
     * 添加文件(文件上传设置仅在post请求中有效)
     * @param fileKey 文件的key
     * @param filePath  文件的路径
     * @return 返回当前对象
     */
    public CleanRequest addFile(String fileKey, String filePath) {
        // 先进行判空 判断files是否为空
        if (files == null) {
            files = new HashMap<>();
        }
        files.put(fileKey, filePath);
        return this;
    }

    /**
     * 添加文件的字节流(文件上传设置仅在post请求中有效)
     * @param fileKey
     * @param fileBytes
     * @return
     */
    public CleanRequest addFileByte(String fileKey,String fileName,byte[] fileBytes) {
        // 先进行判空 判断files是否为空
        if (this.fileBytes == null) {
            this.fileBytes = new HashMap<>();
        }
        FileBytes fileByte = new FileBytes();
        fileByte.setFileName(fileName);
        fileByte.setFileBytes(fileBytes);
        this.fileBytes.put(fileKey, fileByte);
        return this;
    }



    private static File checkAndMakeParentDir(File file) throws BaseException {
        if (!file.getParentFile().exists()){
            if (!file.getParentFile().mkdirs()){
                throw new BaseException("文件夹无法创建");
            }
        }
        return file;
    }

    private void makeFullGetUrl() throws UnsupportedEncodingException {
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.displayName())).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            if (url.contains("?")) {
                url = url + "&" + sb.toString();
            } else {
                url = url + "?" + sb.toString();
            }
        }
    }

    private String makeFormUrlEncoded() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void setupResponse(String result,HttpURLConnection connection) throws IOException {
        response = new CleanResponse();
        response.setBody(result);
        for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
            if (entry.getKey() != null) {
                if (entry.getKey().equals("Location")) {
                    log.info("重定向的地址为：{}",entry.getValue().get(0));
                    location = entry.getValue().get(0);
                }
                response.getHeaders().put(entry.getKey(), entry.getValue().get(0));
            }
        }
        response.setCode(connection.getResponseCode());
    }

    private void setupCookies(HttpURLConnection httpURLConnection) {
        if (null != cookies && !cookies.isEmpty()) {
            StringBuilder cookieStr = new StringBuilder();
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                cookieStr.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
            // 将最后一个字符串的分号去掉
            cookieStr.deleteCharAt(cookieStr.length() - 1);
            httpURLConnection.setRequestProperty("Cookie", cookieStr.toString());
        }
    }

    /**
     * 使用multipart/FormData 来进行表单提交
     * @return
     */
    public CleanRequest useMultipart() {
        this.CONTENT_TYPE = DEFAULT_CONTENT_TYPE_MULTIPART;
        return this;
    }

    /**
     * 使用JSON 来进行表单提交
     * @return
     */
    public CleanRequest useJson() {
        this.CONTENT_TYPE = DEFAULT_CONTENT_TYPE_JSON;
        return this;
    }

    public CleanRequest useFormUrlEncoded() {
        this.CONTENT_TYPE = DEFAULT_CONTENT_TYPE_FORM;
        return this;
    }

    /**
     * get请求
     * @param url
     */
    public String get(String url) {
        this.url = url;
        String result = null;
        try {
            makeFullGetUrl();
        } catch (Exception e) {
            log.error("组装完整的URL失败了。",e);
        }
        log.info("请求的URL为：{}",url);
        log.info("请求的参数为：{}",params);
        try {
            URL urlConn = new URL(this.url);
            HttpURLConnection connection =(HttpURLConnection) urlConn.openConnection();
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(allowRedirect);
            setupCookies(connection);
            int respCode = connection.getResponseCode();
            if (respCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(),DEFAULT_CHARSET));
                StringBuilder stringBuilder = new StringBuilder();
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();
                result = stringBuilder.toString();
            }
            log.info("请求返回的状态码为：{}",respCode);
            setupResponse(result,connection);
        }catch (Exception e){
            log.error("get请求失败了。",e);
            throw new BaseException(BaseErr.THIRD_PARTY_CALL_ERR);
        }
        log.info("请求的结果为：{}",result);
        return result;
    }



    public String getWithLocation(String url){
        this.url = url;
        String result = null;
        try {
            makeFullGetUrl();
        } catch (Exception e) {
            log.error("组装完整的URL失败了。",e);
        }
        log.info("请求的URL为：{}",url);
        log.info("请求的参数为：{}",params);
        try {
            URL urlConn = new URL(this.url);
            HttpURLConnection connection =(HttpURLConnection) urlConn.openConnection();
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(allowRedirect);
            setupCookies(connection);
            int respCode = connection.getResponseCode();
            if (respCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(),DEFAULT_CHARSET));
                StringBuilder stringBuilder = new StringBuilder();
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    stringBuilder.append(decodedString);
                }
                in.close();
                result = stringBuilder.toString();
            }
            log.info("请求返回的状态码为：{}",respCode);
            setupResponse(result,connection);
            result = this.location;
        }catch (Exception e){
            log.error("get请求失败了。",e);
            throw new BaseException(BaseErr.THIRD_PARTY_CALL_ERR);
        }
        log.info("请求的结果为：{}",result);
        return result;
    }

    public static byte[] readFileToByteArr(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] readByNIO(File file) throws IOException {
        //1、定义一个File管道，打开文件输入流，并获取该输入流管道。
        //2、定义一个ByteBuffer，并分配指定大小的内存空间
        //3、while循环读取管道数据到byteBuffer，直到管道数据全部读取
        //4、将byteBuffer转换为字节数组返回
        FileChannel fileChannel = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            fileChannel = in.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());

            while (fileChannel.read(buffer) > 0) {
            }
            return buffer.array();
        } finally {
            closeChannel(fileChannel);
            closeInputStream(in);
        }
    }

    private static void checkFileExists(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            System.err.println("file is not null or exist !");
            throw new FileNotFoundException(file.getName());
        }
    }

    private static void closeChannel(FileChannel channel) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeOutputStream(OutputStream bos) {
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeInputStream(InputStream in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * post请求
     * POST 请求参数类型为 application/x-www-form-urlencoded
     * 此外，formdata 和 form-urlencoded 对于Java的接收端来说都是一样的，因此暂时只做一个处理。
     * 将不会同步将文件上传上去。
     * @param url
     */
    public String post(String url) {
        this.url = url;
        log.info("请求的URL为：{}",url);
        log.info("请求的参数为：{}",params);
        String result = null;
        String bodyString = null;
        Integer respCode = 200;
        if (CONTENT_TYPE.equals(DEFAULT_CONTENT_TYPE_FORM)) {
            this.addHeader("Content-Type", DEFAULT_CONTENT_TYPE_FORM);
            bodyString = makeFormUrlEncoded();
            addHeader("Content-Length", String.valueOf(bodyString.length()));
        }else if (CONTENT_TYPE.equals(DEFAULT_CONTENT_TYPE_JSON)) {
            this.addHeader("Content-Type", DEFAULT_CONTENT_TYPE_JSON);
            bodyString = JsonUtil.stringify(params);
        }
        try {
            URL urlConn = new URL(this.url);
            HttpURLConnection connection =(HttpURLConnection) urlConn.openConnection();
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(allowRedirect);
            setupCookies(connection);
            if (bodyString != null){
                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = bodyString.getBytes(DEFAULT_CHARSET);
                    os.write(input, 0, input.length);
                }
            }
            if (CONTENT_TYPE.equals(DEFAULT_CONTENT_TYPE_MULTIPART)) {
                String boundary = CommonUtils.uuid().replace("-","");
                connection.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE_MULTIPART+";boundary="+boundary);
                try(OutputStream os = connection.getOutputStream()) {
                    if (this.files != null && this.files.size()>0){
                        for (Map.Entry<String, String> entry : files.entrySet()) {
                            File file = new File(entry.getValue());
                            String fileName = file.getName();
                            String contentType = URLConnection.guessContentTypeFromName(fileName);
                            if (contentType == null || contentType.length() == 0) {
                                contentType = "application/octet-stream";
                            }
                            os.write(("--" + boundary + "\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\"\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(("Content-Type: " + contentType + "\r\n\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(readByNIO(new File(entry.getValue())));
                            os.write("\r\n".getBytes(DEFAULT_CHARSET));
                            os.flush();
                        }
                    }
                    if (this.fileBytes != null && this.fileBytes.size()>0) {
                        for (Map.Entry<String, FileBytes> entry : fileBytes.entrySet()) {
                            String fileName = entry.getValue().getFileName();
                            String contentType = URLConnection.guessContentTypeFromName(fileName);
                            if (contentType == null || contentType.length() == 0) {
                                contentType = "application/octet-stream";
                            }
                            os.write(("--" + boundary + "\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\"\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(("Content-Type: " + contentType + "\r\n\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(entry.getValue().getFileBytes());
                            os.write("\r\n".getBytes(DEFAULT_CHARSET));
                            os.flush();
                        }
                    }
                    // 开始进行参数的传入
                    if (this.params != null && this.params.size()>0){
                        for (Map.Entry<String,String> entry:params.entrySet()){
                            os.write(("--" + boundary + "\r\n").getBytes(DEFAULT_CHARSET));
                            os.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n").getBytes(DEFAULT_CHARSET));
                            os.write((entry.getValue() + "\r\n").getBytes(DEFAULT_CHARSET));
                        }
                    }
                    os.write(("--" + boundary + "--\r\n").getBytes(DEFAULT_CHARSET));
                    os.flush();
                }
            }
            respCode = connection.getResponseCode();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), DEFAULT_CHARSET))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }
        }catch (Exception e){
            log.info("请求返回的状态码为：{}",respCode);
            log.error("post请求失败了。",e);
            throw new BaseException(BaseErr.THIRD_PARTY_CALL_ERR);
        }
        log.info("请求的结果为：{}",result);
        return result;
    }


    public String options(String url) {
        String result = null;


        return result;
    }

    public String head(String url) {
        String result = null;


        return result;
    }

    public String put(String url) {
        String result = null;


        return result;
    }

    public String delete(String url) {
        String result = null;


        return result;
    }

    public String trace(String url) {
        String result = null;


        return result;
    }

    public String connect(String url) {
        String result = null;


        return result;
    }


    @Data
    public class CleanResponse {
        private int code;
        private String body;
        private Map<String, String> headers;

        public CleanResponse() {
            headers = new HashMap<>();
        }
    }

    @Data
    public class FileBytes {
        private String fileName;
        private byte[] fileBytes;
    }








    public static void main(String[] args) {
//        // Get 示例
//        String result = CleanRequest.getInstance()
//                .get("http://localhost:20008/check/alive");
//        System.out.printf("result = {}" + result);
//        // POST JSON 示例
//        String result2 = CleanRequest.getInstance()
//                .useJson()
//                .addParam("loginName","rffanlab")
//                .addParam("passwd","123456")
//                .post("http://localhost:20008/api/v1/user/login");
//        log.info(result2);
//        // POST FORM 示例
//        String result3 = CleanRequest.getInstance()
//                .useFormUrlEncoded()
//                .addParam("loginName","rffanlab")
//                .post("http://localhost:20008/api/v1/test/deleteByChapterId");
//        log.info(result3);
        // POST 上传文件示例
//        String result4 = CleanRequest.getInstance()
//                .useMultipart()
//                .addParam("loginName","rffanlab")
//                .addParam("passwd","123456")
//                .addFile("file","D:/interview.png")
//                .post("http://localhost:20008/api/v1/test/testupload");
//        log.info(result4);
        // POST 上传云存储示例


    }
}

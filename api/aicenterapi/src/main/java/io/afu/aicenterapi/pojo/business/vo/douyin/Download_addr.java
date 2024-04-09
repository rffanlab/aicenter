/**
  * Copyright 2024 bejson.com 
  */
package io.afu.aicenterapi.pojo.business.vo.douyin;
import java.util.List;

/**
 * Auto-generated: 2024-03-31 16:45:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Download_addr {

    private long data_size;
    private String file_cs;
    private int height;
    private String uri;
    private List<String> url_list;
    private int width;
    public void setData_size(long data_size) {
         this.data_size = data_size;
     }
     public long getData_size() {
         return data_size;
     }

    public void setFile_cs(String file_cs) {
         this.file_cs = file_cs;
     }
     public String getFile_cs() {
         return file_cs;
     }

    public void setHeight(int height) {
         this.height = height;
     }
     public int getHeight() {
         return height;
     }

    public void setUri(String uri) {
         this.uri = uri;
     }
     public String getUri() {
         return uri;
     }

    public void setUrl_list(List<String> url_list) {
         this.url_list = url_list;
     }
     public List<String> getUrl_list() {
         return url_list;
     }

    public void setWidth(int width) {
         this.width = width;
     }
     public int getWidth() {
         return width;
     }

}
/**
  * Copyright 2024 bejson.com 
  */
package io.afu.aicenterapi.pojo.business.vo.douyin;
import java.util.List;

/**
 * Auto-generated: 2024-03-28 10:51:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class VideoInfoRes {

    private Extra extra;
    private List<String> filter_list;
    private int status_code;
    private List<Item_list> item_list;
    public void setExtra(Extra extra) {
         this.extra = extra;
     }
     public Extra getExtra() {
         return extra;
     }

    public void setFilter_list(List<String> filter_list) {
         this.filter_list = filter_list;
     }
     public List<String> getFilter_list() {
         return filter_list;
     }

    public void setStatus_code(int status_code) {
         this.status_code = status_code;
     }
     public int getStatus_code() {
         return status_code;
     }

    public void setItem_list(List<Item_list> item_list) {
         this.item_list = item_list;
     }
     public List<Item_list> getItem_list() {
         return item_list;
     }

}
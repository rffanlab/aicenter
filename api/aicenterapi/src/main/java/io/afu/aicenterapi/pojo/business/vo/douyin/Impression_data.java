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
public class Impression_data {

    private List<String> group_id_list_a;
    private List<String> group_id_list_b;
    private List<String> group_id_list_c;
    private String similar_id_list_a;
    private List<Long> similar_id_list_b;
    public void setGroup_id_list_a(List<String> group_id_list_a) {
         this.group_id_list_a = group_id_list_a;
     }
     public List<String> getGroup_id_list_a() {
         return group_id_list_a;
     }

    public void setGroup_id_list_b(List<String> group_id_list_b) {
         this.group_id_list_b = group_id_list_b;
     }
     public List<String> getGroup_id_list_b() {
         return group_id_list_b;
     }

    public void setGroup_id_list_c(List<String> group_id_list_c) {
         this.group_id_list_c = group_id_list_c;
     }
     public List<String> getGroup_id_list_c() {
         return group_id_list_c;
     }

    public void setSimilar_id_list_a(String similar_id_list_a) {
         this.similar_id_list_a = similar_id_list_a;
     }
     public String getSimilar_id_list_a() {
         return similar_id_list_a;
     }

    public void setSimilar_id_list_b(List<Long> similar_id_list_b) {
         this.similar_id_list_b = similar_id_list_b;
     }
     public List<Long> getSimilar_id_list_b() {
         return similar_id_list_b;
     }

}
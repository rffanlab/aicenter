/**
  * Copyright 2024 bejson.com 
  */
package io.afu.aicenterapi.pojo.business.vo.douyin;

/**
 * Auto-generated: 2024-03-31 16:45:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Comment_permission_info {

    private boolean can_comment;
    private int comment_permission_status;
    private boolean item_detail_entry;
    private boolean press_entry;
    private boolean toast_guide;
    public void setCan_comment(boolean can_comment) {
         this.can_comment = can_comment;
     }
     public boolean getCan_comment() {
         return can_comment;
     }

    public void setComment_permission_status(int comment_permission_status) {
         this.comment_permission_status = comment_permission_status;
     }
     public int getComment_permission_status() {
         return comment_permission_status;
     }

    public void setItem_detail_entry(boolean item_detail_entry) {
         this.item_detail_entry = item_detail_entry;
     }
     public boolean getItem_detail_entry() {
         return item_detail_entry;
     }

    public void setPress_entry(boolean press_entry) {
         this.press_entry = press_entry;
     }
     public boolean getPress_entry() {
         return press_entry;
     }

    public void setToast_guide(boolean toast_guide) {
         this.toast_guide = toast_guide;
     }
     public boolean getToast_guide() {
         return toast_guide;
     }

}
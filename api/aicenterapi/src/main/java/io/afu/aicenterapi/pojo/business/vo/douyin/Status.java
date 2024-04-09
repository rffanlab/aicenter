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
public class Status {

    private boolean allow_friend_recommend;
    private boolean allow_friend_recommend_guide;
    private boolean allow_self_recommend_to_friend;
    private boolean allow_share;
    private String aweme_id;
    private boolean in_reviewing;
    private boolean is_delete;
    private boolean is_prohibited;
    private int listen_video_status;
    private int part_see;
    private int private_status;
    private Review_result review_result;
    public void setAllow_friend_recommend(boolean allow_friend_recommend) {
         this.allow_friend_recommend = allow_friend_recommend;
     }
     public boolean getAllow_friend_recommend() {
         return allow_friend_recommend;
     }

    public void setAllow_friend_recommend_guide(boolean allow_friend_recommend_guide) {
         this.allow_friend_recommend_guide = allow_friend_recommend_guide;
     }
     public boolean getAllow_friend_recommend_guide() {
         return allow_friend_recommend_guide;
     }

    public void setAllow_self_recommend_to_friend(boolean allow_self_recommend_to_friend) {
         this.allow_self_recommend_to_friend = allow_self_recommend_to_friend;
     }
     public boolean getAllow_self_recommend_to_friend() {
         return allow_self_recommend_to_friend;
     }

    public void setAllow_share(boolean allow_share) {
         this.allow_share = allow_share;
     }
     public boolean getAllow_share() {
         return allow_share;
     }

    public void setAweme_id(String aweme_id) {
         this.aweme_id = aweme_id;
     }
     public String getAweme_id() {
         return aweme_id;
     }

    public void setIn_reviewing(boolean in_reviewing) {
         this.in_reviewing = in_reviewing;
     }
     public boolean getIn_reviewing() {
         return in_reviewing;
     }

    public void setIs_delete(boolean is_delete) {
         this.is_delete = is_delete;
     }
     public boolean getIs_delete() {
         return is_delete;
     }

    public void setIs_prohibited(boolean is_prohibited) {
         this.is_prohibited = is_prohibited;
     }
     public boolean getIs_prohibited() {
         return is_prohibited;
     }

    public void setListen_video_status(int listen_video_status) {
         this.listen_video_status = listen_video_status;
     }
     public int getListen_video_status() {
         return listen_video_status;
     }

    public void setPart_see(int part_see) {
         this.part_see = part_see;
     }
     public int getPart_see() {
         return part_see;
     }

    public void setPrivate_status(int private_status) {
         this.private_status = private_status;
     }
     public int getPrivate_status() {
         return private_status;
     }

    public void setReview_result(Review_result review_result) {
         this.review_result = review_result;
     }
     public Review_result getReview_result() {
         return review_result;
     }

}
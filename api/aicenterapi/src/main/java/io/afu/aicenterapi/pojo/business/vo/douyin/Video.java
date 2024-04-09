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
public class Video {

    private List<Big_thumbs> big_thumbs;
    private List<Bit_rate> bit_rate;
    private String bit_rate_audio;
    private long cdn_url_expired;
    private Cover cover;
    private Cover_original_scale cover_original_scale;
    private Download_addr download_addr;
    private Download_suffix_logo_addr download_suffix_logo_addr;
    private long duration;
    private Dynamic_cover dynamic_cover;
    private boolean has_download_suffix_logo_addr;
    private boolean has_watermark;
    private int height;
    private int horizontal_type;
    private int is_h265;
    private int is_long_video;
    private int is_source_HDR;
    private String meta;
    private Origin_cover origin_cover;
    private Play_addr play_addr;
    private Play_addr_265 play_addr_265;
    private Play_addr_h264 play_addr_h264;
    private String ratio;
    private String video_model;
    private int width;
    public void setBig_thumbs(List<Big_thumbs> big_thumbs) {
         this.big_thumbs = big_thumbs;
     }
     public List<Big_thumbs> getBig_thumbs() {
         return big_thumbs;
     }

    public void setBit_rate(List<Bit_rate> bit_rate) {
         this.bit_rate = bit_rate;
     }
     public List<Bit_rate> getBit_rate() {
         return bit_rate;
     }

    public void setBit_rate_audio(String bit_rate_audio) {
         this.bit_rate_audio = bit_rate_audio;
     }
     public String getBit_rate_audio() {
         return bit_rate_audio;
     }

    public void setCdn_url_expired(long cdn_url_expired) {
         this.cdn_url_expired = cdn_url_expired;
     }
     public long getCdn_url_expired() {
         return cdn_url_expired;
     }

    public void setCover(Cover cover) {
         this.cover = cover;
     }
     public Cover getCover() {
         return cover;
     }

    public void setCover_original_scale(Cover_original_scale cover_original_scale) {
         this.cover_original_scale = cover_original_scale;
     }
     public Cover_original_scale getCover_original_scale() {
         return cover_original_scale;
     }

    public void setDownload_addr(Download_addr download_addr) {
         this.download_addr = download_addr;
     }
     public Download_addr getDownload_addr() {
         return download_addr;
     }

    public void setDownload_suffix_logo_addr(Download_suffix_logo_addr download_suffix_logo_addr) {
         this.download_suffix_logo_addr = download_suffix_logo_addr;
     }
     public Download_suffix_logo_addr getDownload_suffix_logo_addr() {
         return download_suffix_logo_addr;
     }

    public void setDuration(long duration) {
         this.duration = duration;
     }
     public long getDuration() {
         return duration;
     }

    public void setDynamic_cover(Dynamic_cover dynamic_cover) {
         this.dynamic_cover = dynamic_cover;
     }
     public Dynamic_cover getDynamic_cover() {
         return dynamic_cover;
     }

    public void setHas_download_suffix_logo_addr(boolean has_download_suffix_logo_addr) {
         this.has_download_suffix_logo_addr = has_download_suffix_logo_addr;
     }
     public boolean getHas_download_suffix_logo_addr() {
         return has_download_suffix_logo_addr;
     }

    public void setHas_watermark(boolean has_watermark) {
         this.has_watermark = has_watermark;
     }
     public boolean getHas_watermark() {
         return has_watermark;
     }

    public void setHeight(int height) {
         this.height = height;
     }
     public int getHeight() {
         return height;
     }

    public void setHorizontal_type(int horizontal_type) {
         this.horizontal_type = horizontal_type;
     }
     public int getHorizontal_type() {
         return horizontal_type;
     }

    public void setIs_h265(int is_h265) {
         this.is_h265 = is_h265;
     }
     public int getIs_h265() {
         return is_h265;
     }

    public void setIs_long_video(int is_long_video) {
         this.is_long_video = is_long_video;
     }
     public int getIs_long_video() {
         return is_long_video;
     }

    public void setIs_source_HDR(int is_source_HDR) {
         this.is_source_HDR = is_source_HDR;
     }
     public int getIs_source_HDR() {
         return is_source_HDR;
     }

    public void setMeta(String meta) {
         this.meta = meta;
     }
     public String getMeta() {
         return meta;
     }

    public void setOrigin_cover(Origin_cover origin_cover) {
         this.origin_cover = origin_cover;
     }
     public Origin_cover getOrigin_cover() {
         return origin_cover;
     }

    public void setPlay_addr(Play_addr play_addr) {
         this.play_addr = play_addr;
     }
     public Play_addr getPlay_addr() {
         return play_addr;
     }

    public void setPlay_addr_265(Play_addr_265 play_addr_265) {
         this.play_addr_265 = play_addr_265;
     }
     public Play_addr_265 getPlay_addr_265() {
         return play_addr_265;
     }

    public void setPlay_addr_h264(Play_addr_h264 play_addr_h264) {
         this.play_addr_h264 = play_addr_h264;
     }
     public Play_addr_h264 getPlay_addr_h264() {
         return play_addr_h264;
     }

    public void setRatio(String ratio) {
         this.ratio = ratio;
     }
     public String getRatio() {
         return ratio;
     }

    public void setVideo_model(String video_model) {
         this.video_model = video_model;
     }
     public String getVideo_model() {
         return video_model;
     }

    public void setWidth(int width) {
         this.width = width;
     }
     public int getWidth() {
         return width;
     }

}
package io.afu.aicenterapi.config.sys;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.handlers.CompositeEnumTypeHandler;
import io.afu.aicenterapi.components.ApiLog;
import io.afu.aicenterapi.dao.entity.Aitask;
import io.afu.aicenterapi.pojo.business.dto.SharedUrlDTO;
import io.afu.aicenterapi.pojo.business.dto.TaskResultDTO;
import io.afu.aicenterapi.pojo.business.dto.wx.WxVerifyReq;
import io.afu.aicenterapi.pojo.business.vo.ShortVVO;
import io.afu.aicenterapi.pojo.business.vo.douyin.Admire_auth;
import io.afu.aicenterapi.pojo.business.vo.douyin.Ancestor_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Anchor_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.App;
import io.afu.aicenterapi.pojo.business.vo.douyin.Author;
import io.afu.aicenterapi.pojo.business.vo.douyin.Avatar_large;
import io.afu.aicenterapi.pojo.business.vo.douyin.Avatar_medium;
import io.afu.aicenterapi.pojo.business.vo.douyin.Avatar_thumb;
import io.afu.aicenterapi.pojo.business.vo.douyin.Aweme_control;
import io.afu.aicenterapi.pojo.business.vo.douyin.Aweme_detail;
import io.afu.aicenterapi.pojo.business.vo.douyin.Big_thumbs;
import io.afu.aicenterapi.pojo.business.vo.douyin.Bit_rate;
import io.afu.aicenterapi.pojo.business.vo.douyin.Comment_permission_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Comment_words_recommend;
import io.afu.aicenterapi.pojo.business.vo.douyin.CommonContext;
import io.afu.aicenterapi.pojo.business.vo.douyin.Cover;
import io.afu.aicenterapi.pojo.business.vo.douyin.Cover_hd;
import io.afu.aicenterapi.pojo.business.vo.douyin.Cover_large;
import io.afu.aicenterapi.pojo.business.vo.douyin.Cover_medium;
import io.afu.aicenterapi.pojo.business.vo.douyin.Cover_original_scale;
import io.afu.aicenterapi.pojo.business.vo.douyin.Distribute_circle;
import io.afu.aicenterapi.pojo.business.vo.douyin.DouyinBean;
import io.afu.aicenterapi.pojo.business.vo.douyin.DouyinVideoDataVO;
import io.afu.aicenterapi.pojo.business.vo.douyin.Download_addr;
import io.afu.aicenterapi.pojo.business.vo.douyin.Download_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Download_suffix_logo_addr;
import io.afu.aicenterapi.pojo.business.vo.douyin.Duet_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Dynamic_cover;
import io.afu.aicenterapi.pojo.business.vo.douyin.Entertainment_product_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Extra;
import io.afu.aicenterapi.pojo.business.vo.douyin.Fail_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Fall_card_struct;
import io.afu.aicenterapi.pojo.business.vo.douyin.Feed_comment_config;
import io.afu.aicenterapi.pojo.business.vo.douyin.Friend_recommend_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Guide_scene_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Icon;
import io.afu.aicenterapi.pojo.business.vo.douyin.Image_album_music_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Image_comment;
import io.afu.aicenterapi.pojo.business.vo.douyin.Impression_data;
import io.afu.aicenterapi.pojo.business.vo.douyin.Item_list;
import io.afu.aicenterapi.pojo.business.vo.douyin.Item_warn_notification;
import io.afu.aicenterapi.pojo.business.vo.douyin.Limit_free;
import io.afu.aicenterapi.pojo.business.vo.douyin.Log_pb;
import io.afu.aicenterapi.pojo.business.vo.douyin.Market_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Music;
import io.afu.aicenterapi.pojo.business.vo.douyin.Origin_cover;
import io.afu.aicenterapi.pojo.business.vo.douyin.Photo_search_entrance;
import io.afu.aicenterapi.pojo.business.vo.douyin.Play_addr;
import io.afu.aicenterapi.pojo.business.vo.douyin.Play_addr_265;
import io.afu.aicenterapi.pojo.business.vo.douyin.Play_addr_h264;
import io.afu.aicenterapi.pojo.business.vo.douyin.Play_url;
import io.afu.aicenterapi.pojo.business.vo.douyin.Query;
import io.afu.aicenterapi.pojo.business.vo.douyin.Review_result;
import io.afu.aicenterapi.pojo.business.vo.douyin.Risk_infos;
import io.afu.aicenterapi.pojo.business.vo.douyin.Search_impr;
import io.afu.aicenterapi.pojo.business.vo.douyin.Series_paid_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Share_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Share_qrcode_url;
import io.afu.aicenterapi.pojo.business.vo.douyin.Show_follow_button;
import io.afu.aicenterapi.pojo.business.vo.douyin.Statistics;
import io.afu.aicenterapi.pojo.business.vo.douyin.Status;
import io.afu.aicenterapi.pojo.business.vo.douyin.Style_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Suggest_words;
import io.afu.aicenterapi.pojo.business.vo.douyin.Text_extra;
import io.afu.aicenterapi.pojo.business.vo.douyin.Video;
import io.afu.aicenterapi.pojo.business.vo.douyin.VideoInfoRes;
import io.afu.aicenterapi.pojo.business.vo.douyin.Video_control;
import io.afu.aicenterapi.pojo.business.vo.douyin.Video_game_data_channel_config;
import io.afu.aicenterapi.pojo.business.vo.douyin.Video_tag;
import io.afu.aicenterapi.pojo.business.vo.douyin.Visual_search_info;
import io.afu.aicenterapi.pojo.business.vo.douyin.Words;
import io.afu.aicenterapi.pojo.business.vo.douyin.Xigua_base_info;
import io.afu.aicenterapi.pojo.business.vo.wx.WxCode2SessionResp;
import io.afu.aicenterapi.pojo.business.vo.wx.WxCommonResp;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
// 请将需要反射的类注册在下方中。
@ImportRuntimeHints({
        ReflectionHintsConfig.ReflectionHintsConfigRuntimeHints.class,
        ReflectionHintsConfig.AspectRuntimeHints.class
})
public class ReflectionHintsConfig {


    public static class ReflectionHintsConfigRuntimeHints implements RuntimeHintsRegistrar {


        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            // 开始注册MyBatisPlus 相关的反射
            hints.reflection().registerType(com.baomidou.mybatisplus.core.toolkit.Assert.class, MemberCategory.values());
            hints.reflection().registerType(CompositeEnumTypeHandler.class, MemberCategory.values());
            hints.reflection().registerType(io.afu.aicenterapi.pojo.business.dto.AllowIPPortDTO.class,MemberCategory.values());
            hints.reflection().registerType(io.afu.aicenterapi.pojo.sys.dto.CommonBody.class,MemberCategory.values());
            hints.reflection().registerType(TaskResultDTO.Task.class,MemberCategory.values());
            hints.reflection().registerType(TaskResultDTO.Data.class,MemberCategory.values());
            hints.reflection().registerType(TaskResultDTO.class,MemberCategory.values());
            hints.reflection().registerType(WxCode2SessionResp.class,MemberCategory.values());
            hints.reflection().registerType(WxCommonResp.class,MemberCategory.values());
            hints.reflection().registerType(WxVerifyReq.class,MemberCategory.values());
            hints.reflection().registerType(SharedUrlDTO.class,MemberCategory.values());
            hints.reflection().registerType(ShortVVO.class,MemberCategory.values());
            hints.reflection().registerType(DouyinBean.class,MemberCategory.values());
            hints.reflection().registerType(Author.class,MemberCategory.values());
            hints.reflection().registerType(Avatar_medium.class,MemberCategory.values());
            hints.reflection().registerType(Avatar_thumb.class,MemberCategory.values());
            hints.reflection().registerType(CommonContext.class,MemberCategory.values());
            hints.reflection().registerType(Cover.class,MemberCategory.values());
            hints.reflection().registerType(Extra.class,MemberCategory.values());
            hints.reflection().registerType(Item_list.class,MemberCategory.values());
            hints.reflection().registerType(Play_addr.class,MemberCategory.values());
            hints.reflection().registerType(Aweme_detail.class,MemberCategory.values());
            hints.reflection().registerType(Music.class,MemberCategory.values());
            hints.reflection().registerType(Play_url.class,MemberCategory.values());
            hints.reflection().registerType(Text_extra.class,MemberCategory.values());
            hints.reflection().registerType(Video.class,MemberCategory.values());
            hints.reflection().registerType(VideoInfoRes.class,MemberCategory.values());
            hints.reflection().registerType(Aitask.class,MemberCategory.values());
            // 注册fastjson的相关实体类
            hints.reflection().registerType(JSONObject.class,MemberCategory.values());
            hints.reflection().registerType(JSONArray.class,MemberCategory.values());
            // 开始注册抖音相关的实体类
            hints.reflection().registerType(DouyinBean.class,MemberCategory.values());
            hints.reflection().registerType(Admire_auth.class,MemberCategory.values());
            hints.reflection().registerType(Ancestor_info.class,MemberCategory.values());
            hints.reflection().registerType(Anchor_info.class,MemberCategory.values());
            hints.reflection().registerType(App.class,MemberCategory.values());
            hints.reflection().registerType(Author.class,MemberCategory.values());
            hints.reflection().registerType(Avatar_large.class,MemberCategory.values());
            hints.reflection().registerType(Avatar_medium.class,MemberCategory.values());
            hints.reflection().registerType(Avatar_thumb.class,MemberCategory.values());
            hints.reflection().registerType(Aweme_control.class,MemberCategory.values());
            hints.reflection().registerType(Aweme_detail.class,MemberCategory.values());
            hints.reflection().registerType(Big_thumbs.class,MemberCategory.values());
            hints.reflection().registerType(Bit_rate.class,MemberCategory.values());
            hints.reflection().registerType(Comment_permission_info.class,MemberCategory.values());
            hints.reflection().registerType(Comment_words_recommend.class,MemberCategory.values());
            hints.reflection().registerType(CommonContext.class,MemberCategory.values());
            hints.reflection().registerType(Cover.class,MemberCategory.values());
            hints.reflection().registerType(Cover_hd.class,MemberCategory.values());
            hints.reflection().registerType(Cover_large.class,MemberCategory.values());
            hints.reflection().registerType(Cover_medium.class,MemberCategory.values());
            hints.reflection().registerType(Cover_original_scale.class,MemberCategory.values());
            hints.reflection().registerType(Distribute_circle.class,MemberCategory.values());
            hints.reflection().registerType(DouyinVideoDataVO.class,MemberCategory.values());
            hints.reflection().registerType(Download_addr.class,MemberCategory.values());
            hints.reflection().registerType(Download_info.class,MemberCategory.values());
            hints.reflection().registerType(Download_suffix_logo_addr.class,MemberCategory.values());
            hints.reflection().registerType(Duet_info.class,MemberCategory.values());
            hints.reflection().registerType(Dynamic_cover.class,MemberCategory.values());
            hints.reflection().registerType(Entertainment_product_info.class,MemberCategory.values());
            hints.reflection().registerType(Extra.class,MemberCategory.values());
            hints.reflection().registerType(Fail_info.class,MemberCategory.values());
            hints.reflection().registerType(Fall_card_struct.class,MemberCategory.values());
            hints.reflection().registerType(Feed_comment_config.class,MemberCategory.values());
            hints.reflection().registerType(Friend_recommend_info.class,MemberCategory.values());
            hints.reflection().registerType(Guide_scene_info.class,MemberCategory.values());
            hints.reflection().registerType(Icon.class,MemberCategory.values());
            hints.reflection().registerType(Image_album_music_info.class,MemberCategory.values());
            hints.reflection().registerType(Image_comment.class,MemberCategory.values());
            hints.reflection().registerType(Impression_data.class,MemberCategory.values());
            hints.reflection().registerType(Item_list.class,MemberCategory.values());
            hints.reflection().registerType(Item_warn_notification.class,MemberCategory.values());
            hints.reflection().registerType(Limit_free.class,MemberCategory.values());
            hints.reflection().registerType(Log_pb.class,MemberCategory.values());
            hints.reflection().registerType(Market_info.class,MemberCategory.values());
            hints.reflection().registerType(Music.class,MemberCategory.values());
            hints.reflection().registerType(Origin_cover.class,MemberCategory.values());
            hints.reflection().registerType(Photo_search_entrance.class,MemberCategory.values());
            hints.reflection().registerType(Play_addr.class,MemberCategory.values());
            hints.reflection().registerType(Play_addr_265.class,MemberCategory.values());
            hints.reflection().registerType(Play_addr_h264.class,MemberCategory.values());
            hints.reflection().registerType(Play_url.class,MemberCategory.values());
            hints.reflection().registerType(Query.class,MemberCategory.values());
            hints.reflection().registerType(Review_result.class,MemberCategory.values());
            hints.reflection().registerType(Risk_infos.class,MemberCategory.values());
            hints.reflection().registerType(Search_impr.class,MemberCategory.values());
            hints.reflection().registerType(Series_paid_info.class,MemberCategory.values());
            hints.reflection().registerType(Share_info.class,MemberCategory.values());
            hints.reflection().registerType(Share_qrcode_url.class,MemberCategory.values());
            hints.reflection().registerType(Show_follow_button.class,MemberCategory.values());
            hints.reflection().registerType(Statistics.class,MemberCategory.values());
            hints.reflection().registerType(Status.class,MemberCategory.values());
            hints.reflection().registerType(Style_info.class,MemberCategory.values());
            hints.reflection().registerType(Suggest_words.class,MemberCategory.values());
            hints.reflection().registerType(Text_extra.class,MemberCategory.values());
            hints.reflection().registerType(Video.class,MemberCategory.values());
            hints.reflection().registerType(Video_control.class,MemberCategory.values());
            hints.reflection().registerType(Video_game_data_channel_config.class,MemberCategory.values());
            hints.reflection().registerType(Video_tag.class,MemberCategory.values());
            hints.reflection().registerType(VideoInfoRes.class,MemberCategory.values());
            hints.reflection().registerType(Visual_search_info.class,MemberCategory.values());
            hints.reflection().registerType(Words.class,MemberCategory.values());
            hints.reflection().registerType(Xigua_base_info.class,MemberCategory.values());
            // 开始给ApiListItemVO注册反射

            // 完成反射的注册。


        }
    }

    static class AspectRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerResourceBundle("org.aspectj.weaver.weaver-messages");
            hints.reflection().registerType(
                    ApiLog.class,
                    builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS)
            );
            hints.proxies().registerJdkProxy(FactoryBean.class, BeanClassLoaderAware.class, ApplicationListener.class);
            hints.proxies().registerJdkProxy(ApplicationAvailability.class, ApplicationListener.class);
        }
    }



}

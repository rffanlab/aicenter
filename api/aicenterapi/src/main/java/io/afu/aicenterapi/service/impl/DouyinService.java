package io.afu.aicenterapi.service.impl;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.afu.aitaskservice.exceptions.BaseException;
import io.afu.aitaskservice.pojo.business.vo.ShortVVO;
import io.afu.aitaskservice.pojo.business.vo.douyin.DouyinBean;
import io.afu.aitaskservice.service.ShortVService;
import io.afu.aitaskservice.utils.CleanRequest;
import io.afu.aitaskservice.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service(value = "DouyinService")
@Slf4j
public class DouyinService implements ShortVService {

    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1 Edg/122.0.0.0";


    @Override
    public ShortVVO parseShortV(String url) {
        return parseShortV(url, false);
    }

    private String getPageContent(String url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate(url);
            return page.content();
        }
    }
    

    @Override
    public ShortVVO parseShortV(String url, Boolean downloadLocal) {
        ShortVVO shortVVO = new ShortVVO();
        String locationUrl = CleanRequest.getInstance().disAllowRedirect().getWithLocation(url.trim());
        // 样例重定向地址： https://www.iesdouyin.com/share/video/7347903392527830272/?region=CN&mid=0&u_code=0&did=MS4wLjABAAAAg8P8muGhosNxUBA9GCC65n8NvLMdj5x8uPXhRQn7ly0jqeAQ-iG1yF6jjP8Vrw3z&iid=MS4wLjABAAAANwkJuWIRFOzg5uCpDRpMj4OX-QryoDgn-yYlXQnRwQQ&with_sec_did=1&titleType=title&share_sign=l4fjMx5Ji3o6qjepEMOSdBb_h2hSZcE1CnWrMyMixu4-&share_version=170400&ts=1711590222&from_aid=6383&from_ssr=1&from=web_code_link
        // 提取出视频id 位置是位于/video/后面的数字
        String[] split = locationUrl.split("/");
        String videoId = null;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("video")) {
                videoId = split[i + 1];
                break;
            }
        }
        if (videoId == null) {
            throw new BaseException("解析抖音视频失败");
        }
        shortVVO.setKey(videoId);
        // 组装完整的地址：https://www.iesdouyin.com/share/video/%s %s为视频id
        String videoUrl = String.format("https://www.douyin.com/aweme/v1/web/aweme/detail/?aweme_id=%s&aid=1128&version_name=23.5.0&device_platform=android&os_version=2333", videoId);
        String result = CleanRequest.getInstance()
                .addHeader("authority", "www.douyin.com")
                .addHeader("referer", "https://www.douyin.com/search/%E7%83%AD%E9%97%A8?publish_time=0&sort_type=0&source=switch_tab&type=video")
                .addCookie("msToken", "V4P6ea3qDjNDf5oKQphId2Gt81HpMot_lD40oUxaMjNIo48Abi6ZBZh-nlbkcq1EwwJEZA6g1ZC8XiLncrQD0OcRoHw0Dy8ASA-RXE2oWpG25bDXr7jB")
                .addCookie("ttwid", "1%7Cv5zaNpd9874bD2fgo9Rrayb20-bs1udz6rURIRcGg6g%7C1710152080%7Ce2f03de636fe660dc0149c59169cf4562ee182bb750c85dcbff62c8e5dfcad39")
                .addUserAgent(DEFAULT_USER_AGENT).get(videoUrl.trim());
//        String result = getPageContent(videoUrl);
        log.info("抖音视频解析结果：{}", result);
//        Document douyinHtml = Jsoup.parse(result);
//        // 查找"#RENDER_DATA#"的标签
//        String renderData = douyinHtml.select("#RENDER_DATA").html();
//        log.info("抖音视频解析结果：{}", renderData);
//        // jdk 自带的urlencode 解码
//        String decode = "";
//        try {
//            decode = URLDecoder.decode(renderData, "utf-8");
//        } catch (Exception e) {
//            log.error("解码失败,采用gbk解码", e);
//            try {
//                decode = URLDecoder.decode(renderData, "gbk");
//            } catch (Exception e1) {
//                log.error("gbk解码失败", e1);
//                throw new BaseException("解析抖音视频失败");
//            }
//        }
//        log.info(decode);
        DouyinBean douyinVideoDataVO = JsonUtil.parse(result, DouyinBean.class);
        if (douyinVideoDataVO == null) {
            throw new BaseException("解析抖音视频失败");
        }
        shortVVO.setAudioUrl(douyinVideoDataVO.getAweme_detail().getMusic().getPlay_url().getUrl_list().getFirst());
        shortVVO.setFullUrl(douyinVideoDataVO.getAweme_detail().getVideo().getPlay_addr().getUrl_list().getFirst());
        shortVVO.setCoverUrl(douyinVideoDataVO.getAweme_detail().getVideo().getCover().getUrl_list().getFirst());
        shortVVO.setTitle(douyinVideoDataVO.getAweme_detail().getDesc());
        // 需要将fullUrl里的路径替换一下，将里面的playwm替换为play
        shortVVO.setFullUrl(shortVVO.getFullUrl().replace("playwm", "play"));
        if (downloadLocal) {

        }
        return shortVVO;
    }
}

package io.afu.aicenterapi.service.impl;


import io.afu.aitaskservice.pojo.business.dto.SharedUrlDTO;
import io.afu.aitaskservice.pojo.business.vo.ShortVVO;
import io.afu.aitaskservice.service.ShortVService;
import io.afu.aitaskservice.service.VideoService;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {


    @Override
    public ShortVVO parseShortV(String url) {
        ShortVService shortVService = null;
        // 里面的url可能是包含文字的url 所以需要处理一下提取出来。
        // url 样例如下：
        // 2.35 12/31 y@g.BG TyG:/ 一口气看完爆爽复仇韩剧《金字塔游戏》 女孩新来到学校，却发现班里被划分等级，最低F等级的同学就会成为被霸凌的对象# 韩剧 # 金字塔游戏 # 韩剧推荐  https://v.douyin.com/iF3pteTo/ 复制此链接，打开Dou音搜索，直接观看视频！
        // 开始根据上面的字符串提取里面的url
        // 从字符串中提取url
        String[] split = url.split(" ");
        for (String s : split) {
            if (s.startsWith("http")) {
                url = s;
                break;
            }
        }
        // 根据url的不同选择不同的解析服务
        if (url.contains("douyin")) {
            shortVService = new DouyinService();
        } else if (url.contains("kuaishou")) {
            shortVService = new KuaishouService();
        } else if (url.contains("acfun")) {
            shortVService = new AcfunService();
        }
        if (shortVService == null) {
            return null;
        }
        return shortVService.parseShortV(url);
    }

    @Override
    public ShortVVO parseShortV(SharedUrlDTO dto, Boolean downloadLocal) {
        // 将入参处理一下提取出里面的url
        String url = dto.getSharedurl();
        return parseShortV(url);
    }

    @Override
    public ShortVVO parseShortV(SharedUrlDTO dto) {
        return parseShortV(dto, false);
    }
}

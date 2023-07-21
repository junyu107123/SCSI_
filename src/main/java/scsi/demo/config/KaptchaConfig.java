package scsi.demo.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
	
    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties properties = new Properties();
        // 是否显示边框
        properties.setProperty("kaptcha.border","yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color","105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","blue");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size","25");
        // 图片宽度
        properties.setProperty("kaptcha.image.width","300");
        // 图片高度
        properties.setProperty("kaptcha.image.height","60");
        // 文字个数
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //文字大小
        properties.setProperty("kaptcha.textproducer.font.size","55");
        //文字随机字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体");
        //文字距离
        properties.setProperty("kaptcha.textproducer.char.space","10");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color","blue");
        // 文本内容 从设置字符中随机抽取
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(new Config(properties));
        return kaptcha;
    }
}
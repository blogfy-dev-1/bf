package net.blogfy;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.setBannerMode(Banner.Mode.LOG);
		springApplication.run(args);

//		Thumbnails.of("C:\\Users\\zhangzhenwei\\Desktop\\12148171.jpeg")
//				.size(50, 50)
//				.outputQuality(1)
//				.toFile("C:\\Users\\zhangzhenwei\\Desktop\\2.jpeg");
	}

}

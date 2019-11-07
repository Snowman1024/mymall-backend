package com.snowman.mymall;

import com.snowman.mymall.common.jpa.BaseRepositoryFactoryBean;
import com.snowman.mymall.interceptor.CacheKeyGenerator;
import com.snowman.mymall.interceptor.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = {"com.snowman.mymall.repository"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication
public class MymallBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallBackendApplication.class, args);
    }


    @Bean
    public CacheKeyGenerator cacheKeyGenerator(){
        return new LockKeyGenerator();
    }

}

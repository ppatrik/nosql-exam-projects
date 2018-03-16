package sk.htsys;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    /*static String HOSTNAME = "nosql.gursky.sk";
    static String PASSWORD = "dh38fhw0238923df89djkd93la9fjs0mq9gjflv9jkddj934df90rj";
    static int PORT = 6379;*/

    static String HOSTNAME = "localhost";
    static String PASSWORD = null;
    static int PORT = 6379;

    private static String[] CLUSTER_NODES = {
            "nosql.gursky.sk:6380",
            "nosql2.gursky.sk:6380",
            "nosql3.gursky.sk:6380"
    };

    @Bean
    public RedisConnectionFactory getRedisConnectionFactory() {
        RedisClusterConfiguration rcc = new RedisClusterConfiguration(Arrays.asList(CLUSTER_NODES));
        return new LettuceConnectionFactory(rcc);
    }

    @Bean
    public RedisConnection getRedisConnection(RedisConnectionFactory factory) {
        return factory.getConnection();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(getRedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}

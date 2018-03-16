import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sk.gursky.nosql.aislike.entity.SimpleStudent;

public enum RedisFactory {

    INSTANCE;

    /*static String HOSTNAME = "nosql.gursky.sk";
    static String PASSWORD = "dh38fhw0238923df89djkd93la9fjs0mq9gjflv9jkddj934df90rj";
    static int PORT = 6379;*/

    static String HOSTNAME = "localhost";
    static String PASSWORD = null;
    static int PORT = 6379;

    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(HOSTNAME, PORT);
        if (PASSWORD != null) {
            rsc.setPassword(RedisPassword.of(PASSWORD));
        }
        return new JedisConnectionFactory(rsc);
    }

    public RedisTemplate<String, SimpleStudent> getSimpleStudentTemplate() {
        RedisTemplate<String, SimpleStudent> template = new RedisTemplate<String, SimpleStudent>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}

import com.mycompany.cache.RedisBackedCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

// 演示 Redis
public class RedisBackedCacheIntTestStep0 {

    private RedisBackedCache underTest;

    @Rule
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6-alpine"))
            .withExposedPorts(6379);

    @Before
    public void setUp() {
        // Assume that we have Redis running locally?
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();
        System.out.println("[setUp]: address=" + address + ";port=" + port);

        // Now we have an address and port for Redis, no matter where it is running
        underTest = new RedisBackedCache(address, port);
    }

    @Test
    public void testSimplePutAndGet() {
        String hashKey = "testSimplePutAndGet";
        String value = "curTime-" + System.currentTimeMillis();
        underTest.put(hashKey, value);

        Optional<String> optional = underTest.get(hashKey, String.class);
        Assert.assertTrue(optional.isPresent());

        //
        System.out.println("[testSimplePutAndGet]: hashKey=" + hashKey + "; value=" + value);

        String retrieved = optional.get();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(retrieved, value);
    }
}

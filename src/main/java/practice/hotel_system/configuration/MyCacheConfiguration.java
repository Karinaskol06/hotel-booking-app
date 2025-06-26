package practice.hotel_system.configuration;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.entity.Apartments;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages = "practice.hotel_system")
public class MyCacheConfiguration {

    @Bean
    public CacheManager getCacheManager() {
        // Get the cache manager from the provider
        var cacheProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cacheProvider.getCacheManager();

        // Cache for apartment class by ID
        CacheConfiguration<Long, ApartmentClasses> apartClassIdConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        Long.class,
                        ApartmentClasses.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(60)))
                .build();
        cacheManager.createCache("apartClassId",
                Eh107Configuration.fromEhcacheCacheConfiguration(apartClassIdConfig));

        // Cache for apartment class lists
        CacheConfiguration<String, List> apartClassStrConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        String.class,
                        List.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(5, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();
        cacheManager.createCache("apartClassStr",
                Eh107Configuration.fromEhcacheCacheConfiguration(apartClassStrConfig));

        // Cache for apartments by ID
        CacheConfiguration<Long, Apartments> apartmentIdConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        Long.class,
                        Apartments.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(60)))
                .build();
        cacheManager.createCache("apartmentId",
                Eh107Configuration.fromEhcacheCacheConfiguration(apartmentIdConfig));

        // Cache for apartment lists
        CacheConfiguration<String, List> apartmentStrConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        String.class,
                        List.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(5, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();
        cacheManager.createCache("apartmentStr",
                Eh107Configuration.fromEhcacheCacheConfiguration(apartmentStrConfig));

        // Cache for apartments by class ID
        CacheConfiguration<Long, List> apartmentByClassConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        Long.class,
                        List.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(5, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();
        cacheManager.createCache("apartmentByClass",
                Eh107Configuration.fromEhcacheCacheConfiguration(apartmentByClassConfig));

        return cacheManager;
    }
}
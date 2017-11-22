package com.criteo.demo.e2e;


import com.criteo.demo.common.dao.ProductViewRepository;
import com.criteo.demo.common.model.ProductView;
import com.criteo.demo.common.utils.HttpUtils;
import com.datastax.driver.core.Session;
import com.google.common.collect.Lists;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AdvertiserTrackerScenario {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Autowired
    ProductViewRepository productViewRepository;

    @Configuration
    @EnableCassandraRepositories("com.criteo.demo.common.dao")
    @EnableKafka
    static class ContextConfiguration extends AbstractCassandraConfiguration {

        @Override
        public String getKeyspaceName() {
            return "stats";
        }

        @Override
        protected String getContactPoints() {
            return "cassandra";
        }

        @Bean
        public CassandraTemplate cassandraTemplate(Session session) {
            return new CassandraTemplate(session);
        }
    }

    @Given("^A user id \"([^\"]*)\" visits the product id \"([^\"]*)\"$")
    public void a_user_id_visits_the_product_id(String userId, String productId) throws Throwable {
        String url = "http://localhost:8080/api/advertiser-tracker/view?userid=" +
                userId + "&productid=" + productId;

        int send = HttpUtils.send(url);

        System.out.println(send);
        assertEquals("Response code should be 200", 200, send);

    }

    @Then("^my system should store the following statistics$")
    public void i_my_system_should_store_the_following_statistics(DataTable table) throws Throwable {
        List<Map<String, Integer>> maps = table.asMaps(String.class, Integer.class);
        Map<String, Integer> map = maps.get(0);
        Integer userId = map.get("userId");
        Integer productId = map.get("productId");

        CompletableFuture<ArrayList<ProductView>> result = new CompletableFuture<>();

        Runnable task = () -> {

            Iterable<ProductView> productViews = productViewRepository.findAll();
            ArrayList<ProductView> productList = Lists.newArrayList(productViews);

            result.complete(productList);
            executor.shutdown();
        };
        executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);

        try {
            ArrayList<ProductView> productList = result.get(30, TimeUnit.SECONDS);

            Optional<ProductView> product = productList.stream().filter(x -> x.getKey().getUserId().equals(userId)).findAny();

            assertTrue("Product should have the correct values", product.isPresent());
            ProductView productView = product.get();
            assertEquals("Product should have the correct product id", productId, productView.getKey().getProductId());
        } catch (TimeoutException | InterruptedException ex) {
            fail("Engine didn't write anything in Cassandra.");
        }
    }
}

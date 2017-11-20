package com.criteo.demo.e2e;


import com.criteo.demo.common.utils.HttpUtils;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class AdvertiserTrackerScenario {

    @Given("^A user id \"([^\"]*)\" visits the product id \"([^\"]*)\"$")
    public void a_user_id_visits_the_product_id(String userId, String productId) throws Throwable {
        String url = "http://localhost:8080/api/advertiser-tracker/view?userid=" +
                userId + "&productid=" + productId;

        int send = HttpUtils.send(url);

        System.out.println(send);
        assertEquals("Response code should be 200", 200, send);

    }

    @Then("^my system should store the following statistics$")
    public void i_my_system_should_store_the_following_statistics(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        throw new PendingException();
    }
}

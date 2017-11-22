package com.criteo.demo.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class})
public class AdvertiserTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertiserTrackerApplication.class, args);
	}
}

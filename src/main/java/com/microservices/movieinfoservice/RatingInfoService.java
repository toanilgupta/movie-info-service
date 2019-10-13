package com.microservices.movieinfoservice;

import com.microservices.movieinfoservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@EnableCircuitBreaker
public class RatingInfoService {

    @Autowired
    private RestTemplate restTemplate;


    /*@HystrixCommand(fallbackMethod = "getFallbackMovieRating",
            threadPoolKey = "ratingDataPool",
            threadPoolProperties = {
                @HystrixProperty(name="coreSize",value="5"),
                    @HystrixProperty(name="maxQueueSize",value="5"),
            },
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
            })*/
    public UserRating getMovieRating(String movieName){
        System.out.println(" getMovieRating input " +movieName);

        //String username = "hello";
        //String password = "password";
        HttpHeaders headers = new HttpHeaders();
        //headers.setBasicAuth(username, password);
        //headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE1NjgxOTY5ODcsImlhdCI6MTU2ODE3ODk4N30.VRYGtAPkWlm8AukxZtagRP9zjYAl1_2NoydZHRpS2q3MShcC2thxWy78Ux3aRz7ta1TJLKAy4fnt3y04h7pzwA");

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<UserRating> response = restTemplate.exchange("http://rating-data-service:8082/rating/1", HttpMethod.GET, request, UserRating.class);
        UserRating rating = response.getBody();

        //RatingItem rating = restTemplate.getForObject("https://rating-data-service:8443/rating/"+movieName, RatingItem.class);
        System.out.println(" getMovieRating output " +rating);
        return rating;
    }

    @Autowired
    private DiscoveryClient discoveryClient;


    //@Async("asyncExecutor")
    public UserRating getMovieRatingUsingZuul(String movieName) throws RestClientException, IOException, ExecutionException, InterruptedException {

        List<ServiceInstance> instances = discoveryClient.getInstances("zuul-gateway");
        ServiceInstance serviceInstance = instances.get(0);

        System.out.println(" base URL "+instances.get(0));

        String baseUrl = serviceInstance.getUri().toString();

        baseUrl = baseUrl + "/zuulservice/movie/Super30/ratings";

        //baseUrl = baseUrl + "/zuulservice/Async/Super30";

        System.out.println(" base URL "+baseUrl);

        //RestTemplate restTemplate = new RestTemplate();
       /* ResponseEntity<CompletableFuture<RatingItem>> response = null;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), CompletableFuture.class);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        List<UserRating> rating = response.getBody();*/

       //CompletableFuture<RatingItem> future = restTemplate.getForObject(baseUrl,CompletableFuture.class);
        //DeferredResult<RatingItem> future = restTemplate.getForObject(baseUrl,DeferredResult.class);

        //r//ating.thenAccept(rating -> rating.getMovieName(System.out::println) );

        //RatingItem ratingItem =
        //System.out.println(" movieName = "+future.getResult());

        //RatingItem rating = restTemplate.getForObject("https://rating-data-service:8443/rating/"+movieName, RatingItem.class);
       // System.out.println(" getMovieRating output " +ratingItem.getMovieName());

        Thread.sleep(1000);
        //Thread.sleep(2000);
       return   new UserRating(1l, "Super30","2019","4*");
        //return (RatingItem)future.getResult();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


   /* public RatingItem getFallbackMovieRating(String movieName){
        System.out.println(" getFallbackMovieRating input " +movieName);
        return new RatingItem("Article 15","2019","4*");
    }*/
}

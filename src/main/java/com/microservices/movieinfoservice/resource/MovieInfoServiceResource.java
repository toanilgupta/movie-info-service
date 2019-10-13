package com.microservices.movieinfoservice.resource;

import com.microservices.movieinfoservice.MovieInfoService;
import com.microservices.movieinfoservice.RatingInfoService;
import com.microservices.movieinfoservice.model.MovieInfo;
import com.microservices.movieinfoservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

@RefreshScope
@RestController
@RequestMapping("/api")
public class MovieInfoServiceResource {

    @Autowired
    private RatingInfoService ratingInfoService;

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private KafkaTemplate<String, MovieInfo> kafkaTemplate;

    private static final String TOPIC = "Kafka_UserRating";

    @Value("${message: Default Hello}")
    private String propValue;

    public MovieInfoServiceResource() {
    }

    @RequestMapping("/checkRefresh")
    public void getRatingInfo() {
        System.out.println(" get Movie Info Service call  " + propValue);
    }

    @RequestMapping("/{movieId}")
    public UserRating getRatingInfo(@PathVariable("movieId") String movieId) throws IOException, ExecutionException, InterruptedException {

        LocalTime startTime = LocalTime.now();

        System.out.println(startTime+" get Movie Info Service call  "+movieId);

        //String movieName = movieInfoService.getMovieNameFromInfo(movieId);

        //LocalTime endTime = LocalTime.now();

       // System.out.println(Duration.between(endTime,startTime)+" get Movie Info Service call  "+movieId);

 /*        UserRating ratingItem = ratingInfoService.getMovieRatingUsingZuul( "Super30");
        System.out.println(ratingItem.getMovieName());*/


        MovieInfo info = new MovieInfo(movieId, movieId,"2019","dfsdf");
        //Also Sends message to MovieRatingService
        //kafkaTemplate.send(TOPIC,info);

        return ratingInfoService.getMovieRating( "Super30");

        //return new UserRating("SuperUser","Article15","4*");
    }

    //@Autowired
   // private DiscoveryClient discoveryClient;

     /*MovieSummary movieSummay = restTemplate.getForObject(
                "https://api.themoviedb.org/3/movie/"+movieName+"?api_key="+apiKey,
                MovieSummary.class
        );*/
        /*ServiceInstance instance = discoveryClient.getInstances("movie-zuul-service").get(0);
        String baseUrl = instance.getUri().toString();
        System.out.println(baseUrl+ "  ");*/

         /*try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
        } catch (Exception ex) {
            System.out.println(ex);
        }*/

    //return ratingDataService.getRatingData().get(movieName);
    //return new RatingItem("Article 15","2019","4*");
    // return ratingDataService.get(movieName);
}

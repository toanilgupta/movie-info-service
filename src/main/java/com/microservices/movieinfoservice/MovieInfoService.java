package com.microservices.movieinfoservice;

import com.microservices.movieinfoservice.model.MovieDetail;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Properties;

@Service
//@EnableCircuitBreaker
public class MovieInfoService {

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

   /* @HystrixCommand(fallbackMethod = "getFallbackMovieNameFromInfo",
            commandProperties = {
                @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
            },
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value="5"),
                    @HystrixProperty(name="maxQueueSize",value="5"),
            }
        )*/



    public void sendMessage(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

/*        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("a090b2e3e20992");
        mailSender.setPassword("9c5ac45dbcb134");*/

        /*spring.mail.host=smtp.gmail.com
        spring.mail.port=587
        spring.mail.username=toanilgupta@gmail.com
                spring.mail.password=Welcome!234*/


        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("toanilgupta@gmail.com");
        mailSender.setPassword("Welcome!234");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.properties.mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.properties.mail.smtp.starttls.required", "true");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        mailSender.setJavaMailProperties(props);





        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("order@abc.com");
        msg.setSubject("gfgfdg");
        msg.setTo("to_anilgupta@rediffmail.com");
        msg.setText(
                "Dear  thank you for placing order. Your order number is "
                        );
        try{
        mailSender.send(msg);
        }
        catch (MailException ex) {
        // simply log it and go on...
        System.err.println(ex.getMessage());
    }

    }

   public String getMovieNameFromInfo(String movieId){

       //sendMessage();

       //MovieDetail movieDetail =

               webClientBuilder.build()
               .get()
               .uri("https://api.themoviedb.org/3/movie/550?api_key=30750f513093dc9ea416711d0679431e")
               .retrieve()
               .bodyToMono(MovieDetail.class)
               .subscribe(abc -> System.out.println(Thread.currentThread()+" ID Printed "+abc.getId()));
               //.block();
       System.out.println(Thread.currentThread()+" out of thread ");
       return "";
   }

    public String getMovieNameFromInfo1(String movieId){
        System.out.println(" getMovieNameFromInfo input " +movieId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

      String data = restTemplate.exchange("https://api.themoviedb.org/3/movie/550?api_key=30750f513093dc9ea416711d0679431e",
                HttpMethod.GET, entity, String.class).getBody();

       // MovieDetail  movieDetail = restTemplate.getForObject("https://api.themoviedb.org/3/movie/550?api_key=30750f513093dc9ea416711d0679431e",MovieDetail.class);

       // System.out.println(" getMovieNameFromInfo output " +movieDetail);
        //return  movieDetail.getTitle();

        System.out.println(" getMovieNameFromInfo output " +data);
        return  data;
    }

    public String getFallbackMovieNameFromInfo(@PathVariable("movieInfo") String movieName){
        System.out.println("fallback Method for MovieInfo Service  "+movieName);
        return "Article 15";
    }
}

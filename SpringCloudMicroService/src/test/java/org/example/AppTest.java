package org.example;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.dev;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppTest {

    @Test
    public void test(){

        RestTemplate req = new RestTemplate();

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Integer> data = new LinkedMultiValueMap<>();
        data.add("id",100);

        HttpEntity<MultiValueMap<String, Integer>> entity = new HttpEntity<>(data,headers);

        ResponseEntity<dev[]> response = req.exchange(
                    "https://trekerserver.ru/test_sel.php",
                   HttpMethod.POST,
                   entity,
                   dev[].class
        );

        List<dev> res = Arrays.stream(response.getBody()).toList();
        Assert.assertEquals(10,res.size());
        for(dev d : res){
            assertTrue(d.getImei()!=null);
        }




        /* //пример Get запроса
        RestTemplate req = new RestTemplate();
        ResponseEntity<dev[]> response = req.getForEntity("https://trekerserver.ru/test_sel.php",dev[].class);
        List<dev> res = Arrays.stream(response.getBody()).toList();

        Assert.assertEquals(10,res.size());
        for(dev d : res){
            assertTrue(d.getImei()!=null);
        }

         */
/*

       Flux<String> stream = Flux.just("str1","str2","str3");
       Flux<Integer> stream2 = Flux.just(1,2,3);

       Flux.zip(stream,stream2).flatMap(d-> {
           //List<String> arr = new ArrayList<>();
           //d.forEach(el->arr.add(String.valueOf(el)));
           return Flux.just(d.toArray());
       }).subscribe(System.out::println);
*/

       //stream.subscribe(System.out::println);
    }
}

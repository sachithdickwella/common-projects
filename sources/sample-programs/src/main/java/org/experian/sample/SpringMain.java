package org.experian.sample;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Sachith Dickwella
 */
//@ComponentScan("org.experian.sample.ws")
//@EnableAutoConfiguration
public class SpringMain {

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(SpringMain.class, args);

        SpringMain s = new SpringMain();
        s.n();
    }

    public List<String> ls() throws Exception {
        Thread.sleep(5000);
        return Arrays.asList("First Name", "Last Name");
    }

    public CompletableFuture<ResponseEntity<List<String>>> m() throws Exception {

        return CompletableFuture.supplyAsync(() -> {
            try {
                return ResponseEntity.accepted().body(ls());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.noContent().build();
        });
    }

    void n() throws ExecutionException, InterruptedException, Exception {
        CompletableFuture<ResponseEntity<List<String>>> s = m();
        System.out.println("Response came in");
        System.out.println(s.get().getBody());
    }
}

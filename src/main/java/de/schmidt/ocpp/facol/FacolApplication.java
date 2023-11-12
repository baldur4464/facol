package de.schmidt.ocpp.facol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacolApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FacolApplication.class, args);

        while(true) {
            System.out.println("unendliche Schleife");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

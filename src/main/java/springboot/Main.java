package springboot;

@org.springframework.web.bind.annotation.RestController
@org.springframework.boot.autoconfigure.EnableAutoConfiguration
public class Main
{
    public static void main(String[] args)
    {
        org.springframework.boot.SpringApplication.run(Example.class, args);
    }
}

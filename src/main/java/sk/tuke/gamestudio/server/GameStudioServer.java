package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sk.tuke.gamestudio.server.repo.PlayerRepository;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.gamestudio.entity")
@ComponentScan({"sk.tuke.gamestudio.service", "sk.tuke.gamestudio.server"})
@EnableJpaRepositories(basePackageClasses = PlayerRepository.class)
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService2() {
        return new ScoreServiceJPA();
    }

    @Bean
    public RatingService ratingService2() {
        return new RatingServiceJPA();
    }

    @Bean
    public CommentService commentService2() {
        return new CommentServiceJPA();
    }
}

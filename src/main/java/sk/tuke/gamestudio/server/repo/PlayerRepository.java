package sk.tuke.gamestudio.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByNickname(String nickname);
}

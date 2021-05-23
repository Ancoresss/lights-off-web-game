package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.server.repo.PlayerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public Player getPlayerByNickname(String nickname) {
        return playerRepository.findByNickname(nickname);
    }

    @Override
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }
}

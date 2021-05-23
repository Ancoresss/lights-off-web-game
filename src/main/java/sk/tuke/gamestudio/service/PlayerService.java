package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

public interface PlayerService {
    void addPlayer(Player player);
    Player getPlayerByNickname(String nickname);
    void deletePlayer(Player player);
}

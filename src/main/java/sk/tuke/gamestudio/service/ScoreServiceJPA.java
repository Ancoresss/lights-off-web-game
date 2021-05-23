package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws ScoreException {
        try {
            Score scoreUpdate = (Score) entityManager.createNamedQuery("Score.addScore")
                    .setParameter("game", score.getGame()).setParameter("player", score.getPlayer()).getSingleResult();
        } catch (Exception e) {
            entityManager.persist(score);
        }
        entityManager.merge(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return entityManager.createNamedQuery("Score.getTopScores")
                .setParameter("game", game).setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws ScoreException {
        entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    }
}

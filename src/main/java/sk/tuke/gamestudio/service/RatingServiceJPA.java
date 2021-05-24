package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            Rating ratingUpdate = (Rating) entityManager.createNamedQuery("Rating.getRating")
                    .setParameter("game", rating.getGame()).setParameter("player", rating.getPlayer()).getSingleResult();
        } catch (Exception e) {
            entityManager.persist(rating);
        }
        entityManager.merge(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        double rating;
        try {
            rating = (double) entityManager.createNamedQuery("Rating.getAverageRating")
                    .setParameter("game", game).getSingleResult();
        } catch (NullPointerException e) {
            return 0;
        }
        return (int) Math.round(rating);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Rating rating = (Rating) entityManager.createNamedQuery("Rating.getRating")
                .setParameter("game", game).setParameter("player", player).getSingleResult();
        return rating.getRating();
    }

    @Override
    public int getAllRatings(String game) throws RatingException {
        Rating rating = (Rating) entityManager.createNamedQuery("Rating.getAllRating")
                .setParameter("game", game).getSingleResult();
        return rating.getRating();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
    }
}

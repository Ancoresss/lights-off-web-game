package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT rating FROM rating WHERE game = ? and player = ?";
    public static final String AVERAGE = "SELECT avg(rating) FROM rating WHERE game = ?";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String DELETE = "DELETE FROM rating";
    public static final String SELECT_ALL = "SELECT count(*) FROM rating WHERE game = ?";

    @Override
    public void setRating(Rating rating) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem set rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int rating;
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(AVERAGE)) {
            statement.setString(1, game);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rating = rs.getInt(1);
        } catch (SQLException e) {
            throw new RatingException("Problem get average rating", e);
        }
        return rating;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating;
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setString(1, game);
            statement.setString(2, player);

            ResultSet rs = statement.executeQuery();
            rs.next();
            rating = rs.getInt(1);
        } catch (SQLException e) {
            throw new RatingException("Problem get rating", e);
        }
        return rating;
    }

    @Override
    public int getAllRatings(String game) throws RatingException {
        int count;
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            statement.setString(1, game);

            ResultSet rs = statement.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            throw new RatingException("Problem get all rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}

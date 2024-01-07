package org.maxim.crud.repository.impl;


import org.maxim.crud.model.Post;
import org.maxim.crud.repository.PostRepository;
import org.maxim.crud.Utilc.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostRepository implements PostRepository {

    private final static String GET_POST_BY_ID = "SELECT * FROM post " + " p " +
                                    "LEFT JOIN label pl ON p.id = pl.id " +
                                    "LEFT JOIN label l on pl.id = l.id " +
                                    "WHERE p.PostStatus = 'ACTIVE' AND p.id = ? ";
    private final static String GET_POST_ALL = "SELECT * FROM  post  " + " p " +
                                            "LEFT JOIN label pl ON p.id = pl.id " +
                                            "LEFT JOIN label l on pl.id = l.id " +
                                            "WHERE PostStatus = 'ACTIVE' ";
    private final static String POST_SAVE = "INSERT INTO post(writer_id, post) VALUES (?, ?)";
    private final static String POST_UPDATE = "UPDATE + post " +
            " SET  Content = ?, Created = ?, Updated = ?, PostStatus = ? WHERE id = ?";
    private final static String DELETE_BY_ID = "UPDATE post " +
            " SET PostStatus = ? WHERE id = ?";

    @Override
    public Post getById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(GET_POST_BY_ID)){

            preparedStatement.setLong( 1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return convertResultSetToPost(resultSet);

        } catch (SQLException e) {
            System.out.println("Error in Post getId" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> postsList = new ArrayList<>();

        try(Statement statement = JDBCUtils.getConnection().createStatement()){

            ResultSet resultSet = statement.executeQuery(GET_POST_ALL);
            while(resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getLong("id"));
                post.setContent(resultSet.getString("Content"));
                post.setCreated(resultSet.getString("Created"));
                post.setUpdated(resultSet.getString("Updated"));
                postsList.add(post);
                List<String> postList = new ArrayList<>();
                postList.add(resultSet.getString("post_name"));
                System.out.println("id: " + post.getId());
                System.out.println("content: " + post.getContent());
                System.out.println("created: " + post.getCreated());
                System.out.println("updated: " + post.getUpdated());
                System.out.println("created: " + post.getCreated());
            }

        } catch (SQLException e) {
            System.out.println("Error in Post getAll" + e.getMessage());
        }
        return postsList;
    }

    @Override
    public Post save(Post post) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement ps = JDBCUtils.getPreparedStatement(POST_SAVE)) {
            ps.setString(1, post.getContent());
            ps.setString(2, post.getCreated());
            ps.setString(3, post.getUpdated());
            ps.setString(4, post.getPostStatus().toString());
            ps.setLong(5, 1);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                Long id = generatedKeys.getLong(1);
                post.setId(id);
            }
            try {
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(POST_UPDATE)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getCreated());
            preparedStatement.setString(3, post.getUpdated());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in Post update: " + e.getMessage());
        }
        return post;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in postId delete: " + e.getMessage());
        }
    }

    public Post convertResultSetToPost(ResultSet resultSet) {
        Post post = null;
        try {
            while(resultSet.next()) {
                post = new Post();
                post.setId(resultSet.getLong("id"));
                post.setContent(resultSet.getString("Content"));
                post.setCreated(resultSet.getString("Created_name"));
                post.setUpdated(resultSet.getString("Updated_name"));
            }
        }catch (SQLException e) {
            System.out.println("IN convertResultSetPost error: " + e.getMessage());
        }

        return post;
    }
}



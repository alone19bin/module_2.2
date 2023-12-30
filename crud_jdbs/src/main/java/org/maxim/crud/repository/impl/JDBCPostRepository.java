package org.maxim.crud.repository.impl;


import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.PostRepository;
import org.maxim.crud.service.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostRepository implements PostRepository {

    private final static String GET_POST_BY_ID = "SELECT * FROM WHERE post_id = ?";
    private final static String GET_POST_ALL = "SELECT * FROM post";
    private final static String POST_SAVE = "INSERT INTO post(writer_id, post_name) VALUES (?, ?)";
    private final static String POST_UPDATE = "UPDATE post set post_name = ? where post_id = ?";
    private final static String DELETE_BY_ID = "DELETE FROM post WHERE post_id = ?";

    @Override
    public Post getById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(GET_POST_BY_ID)){

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

        try(Statement statement = JDBCUtils.getConnectJDBC().createStatement()){

            ResultSet resultSet = statement.executeQuery(GET_POST_ALL);

            while(resultSet.next()) {
                Post post = new Post();

                post.setId(resultSet.getLong("post_id"));
                post.setContent(resultSet.getString("content_name"));
                post.setCreated(resultSet.getDate("created_name"));
                post.setUpdated(resultSet.getDate("updated_name"));
                post.setCreated(resultSet.getDate("crated_name"));

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
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(POST_SAVE)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setDate(2, post.getCreated());
            preparedStatement.setDate(3, post.getUpdated());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in Post save: " + e.getMessage());
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(POST_UPDATE)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setDate(2, post.getCreated());
            preparedStatement.setDate(3, post.getUpdated());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in Post update: " + e.getMessage());
        }
        return post;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(DELETE_BY_ID)) {
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
                post.setId(resultSet.getLong("post_id"));
                post.setContent(resultSet.getString("content_name"));
                post.setCreated(resultSet.getDate("created_name"));
                post.setUpdated(resultSet.getDate("updated_name"));
                post.setCreated(resultSet.getDate("crated_name"));
            }
        }catch (SQLException e) {
            System.out.println("IN convertResultSetPost error: " + e.getMessage());
        }

        return post;
    }
}



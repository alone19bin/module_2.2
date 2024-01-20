package org.maxim.crud.repository.impl;


import org.maxim.crud.enums.LabelStatus;
import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.repository.PostRepository;
import org.maxim.crud.Utilc.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Post getById(Long idl) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(GET_POST_BY_ID)){
            preparedStatement.setLong(1, idl);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, Post> postMap = new HashMap<>();

            while (resultSet.next()) {

                Long id = resultSet.getLong("p.id");
                if (!postMap.containsKey(id)) {
                    postMap.put(Math.toIntExact(id), mapToPost(resultSet));
                }
                String status = resultSet.getString("status");
                if (status != null && status.equals("ACTIVE")) {
                    List<Label> postLabels = postMap.get(id).getLabels();
                    postLabels.add(mapToLabel(resultSet));
                }
            }

            return postMap.get(idl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try(PreparedStatement preparedStatement = JDBCUtils.getPreparedStatement(GET_POST_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Post> postMap = new HashMap<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("p.id");
                if (!postMap.containsKey(id)) {
                    Post post = mapToPost(resultSet);
                    postMap.put(id, post);
                    posts.add(post);
                }

                String status = resultSet.getString("status");
                if (status != null && status.equals("ACTIVE")) {
                    List<Label> postLabels = postMap.get(id).getLabels();
                    postLabels.add(mapToLabel(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getSQLState());
        }
        return posts;
    }

    @Override
    public Post save(Post post) {
        try {
            Connection connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(POST_SAVE, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, post.getContent());
                preparedStatement.setString(2, post.getCreated());
                preparedStatement.setString(3, post.getUpdated());
                preparedStatement.setString(4, post.getPostStatus().toString());
                preparedStatement.setLong(5, post.getId());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    post.setId(id);
                }
            }
            try {
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
                throwables.printStackTrace();
            }
        }

        catch (SQLException throwables) {
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

    private  Post mapToPost(ResultSet resultSet) throws SQLException {
        List<Label> postLabels = new ArrayList<>();
        Long id = resultSet.getLong("p.id");
        String content = resultSet.getString("content");
        String created = resultSet.getString("created");
        String updated = resultSet.getString("updated");
        String postStatus = resultSet.getString("post_status");

        return Post.builder()
                .id(id)
                .content(content)
                .created(created)
                .updated(updated)
                .postStatus(PostStatus.valueOf(postStatus))
                .labels(postLabels)
                .build();
    }
    private Label mapToLabel(ResultSet resultSet) throws SQLException {
        Long labelId = resultSet.getLong("label_id");
        String labelName = resultSet.getString("name");
        String status = resultSet.getString("status");

        return Label.builder()
                .id(labelId)
                .name(labelName)
                .labelStatus(LabelStatus.valueOf(status))
                .build();
    }
}



package org.maxim.crud.repository.impl;



import org.maxim.crud.enums.PostStatus;
import org.maxim.crud.enums.WriterStatus;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.WriterRepository;
import org.maxim.crud.Utilc.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCWriterRepository implements WriterRepository {


    private final static String GET_BY_ID = "SELECT * FROM  writer " +  " wrt " +
                                    "LEFT JOIN Post wpr ON wrt.id = wpr.id " +
                                    "LEFT JOIN Post p on wpr.id = p.id " +
                                    "WHERE w.WriterStatus = 'ACTIVE' AND w.id = ? ";
    private final static String GET_WRITER_ALL =  "SELECT * FROM  writer " +  " wrt " +
                                            "LEFT JOIN Post wpr ON wrt.id = wpr.id " +
                                            "LEFT JOIN Post p on wpr.id = p.id " +
                                            "WHERE w.WriterStatus = 'ACTIVE' ";
    private final static String GET_WRITER_SAVE = "INSERT INTO writer(FirstName, LastName) VALUES (?, ?)";
    private final static String UPDATE_WRITER = "UPDATE  writer " +
            " SET  FirstName = ?, LastName = ?, " +
            "WriterStatus = ? WHERE id = ?";
    private final static String DELETE_WRITER = "UPDATE  writer " +
            " SET WriterStatus = ? WHERE id = ?";


    @Override
    public Writer getById(Long Long) {
        Writer writer = null;
        try{
            PreparedStatement statement = JDBCUtils.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, Long);
            ResultSet rs = statement.executeQuery();
            Map<Long,Writer> writerMap = new HashMap<>();

            while (rs.next()){
                Long id = rs.getLong("wrt.id");
                if(!writerMap.containsKey(id)){
                    List<Post> writerPosts = new ArrayList<>();

                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String status = rs.getString("WriterStatus");

                    writer = Writer.builder()
                            .id(id)
                            .firstName(firstName)
                            .lastName(lastName)
                            .writerStatus(WriterStatus.valueOf(status))
                            .posts(writerPosts)
                            .build();

                    writerMap.put(id, writer);
                }

            }

            return writer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Writer> getAll() {
        return getAllStr(GET_WRITER_ALL);
    }



        public List<Writer> getAllStr(String getAll) {
            List<Writer> writers = new ArrayList<>();
            try{
                Connection connection = JDBCUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAll);
                connection.setAutoCommit(false);

                ResultSet rs = preparedStatement.executeQuery();
                Map<Long, Writer> writerMap = new HashMap<>();

                while(rs.next()){
                    Long id = rs.getLong("wrt.id");
                    if(!writerMap.containsKey(id)){

                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String status = rs.getString("status");

                        Writer writer = Writer.builder()
                                .id(id)
                                .firstName(firstName)
                                .lastName(lastName)
                                .writerStatus(WriterStatus.valueOf(status))
                                .build();

                        writerMap.put(id, writer);
                        writers.add(writer);
                    }

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return writers;
            }



        @Override
        public Writer save (Writer writer){
            try{
                Connection connection = JDBCUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_WRITER_SAVE, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, writer.getFirstName());
                preparedStatement.setString(2, writer.getLastName());
                preparedStatement.setString(1, writer.getWriterStatus().toString());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    Long id = generatedKeys.getLong(1);
                    writer.setId(id);
                }
                try {
                    connection.commit();
                } catch (SQLException throwables) {
                    connection.rollback();
                    throwables.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return writer;

        }

        @Override
        public Writer update (Writer writer){
            try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(UPDATE_WRITER)) {

                preparedStatement.setString(1, writer.getFirstName());
                preparedStatement.setString(2, writer.getLastName());
                preparedStatement.setLong(3, writer.getId());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error in update: " + e.getMessage());
            }
            return writer;
        }

        @Override
        public void deleteById (Long id){
            try (PreparedStatement preparedStatement = JDBCUtils.getConnection().prepareStatement(DELETE_WRITER)) {

                preparedStatement.setLong(1, id);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error in delete: " + e.getMessage());
            }
        }
        public Writer convertResultSetToWriter (ResultSet resultSet){
            Writer writer = null;
            try {
                while (resultSet.next()) {
                    writer = new Writer();
                    writer.setId(resultSet.getLong("id"));
                    writer.setFirstName(resultSet.getString("FirstName"));
                    writer.setLastName(resultSet.getString("LastName"));
                }
            } catch (SQLException e) {
                System.out.println("IN convertResultSetToWriter error: " + e.getMessage());
            }

            return writer;
        }

        private Writer mapResultSetToWriter(ResultSet resultSet) {
            try {
                Long writer_id = resultSet.getLong("id");
                String writer_first_name = resultSet.getString("FirstName");
                String writer_last_name = resultSet.getString("LastName");
                WriterStatus writer_status = WriterStatus.valueOf(resultSet.getString("WriterStatus"));

                List<Post> posts = new ArrayList<>();
                Post post = new Post();
                Long post_id = resultSet.getLong("id");
                String post_content = resultSet.getString("Content");
                Date post_created = resultSet.getDate("Created");
                Date post_updated = resultSet.getDate("Updated");
                String post_status = resultSet.getString("PostStatus");

                if (post_content != null && post_created != null && post_updated != null) {
                    post.setId(post_id);
                    post.setContent(post_content);
                    post.getCreated();
                    post.getUpdated();
                    post.setPostStatus(PostStatus.valueOf(post_status));
                    posts.add(post);
                }


                return new Writer(
                        writer_id,
                        writer_first_name,
                        writer_last_name,
                        posts, writer_status);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }
    }


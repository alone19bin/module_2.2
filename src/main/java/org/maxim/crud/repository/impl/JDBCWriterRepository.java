package org.maxim.crud.repository.impl;



import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.WriterRepository;
import org.maxim.crud.service.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCWriterRepository implements WriterRepository {


    private final static String GET_BY_ID = "SELECT * FROM writer LEFT JOIN post s on writer.post_id = s.post_id WHERE writer_id = ?";
    private final static String GET_WRITER_ALL = "SELECT * FROM writer LEFT JOIN post s on writer.post_id = s.post_id";
    private final static String GET_WRITER_SAVE = "INSERT INTO writer(first_name, last_name) VALUES (?, ?)";
    private final static String UPDATE_WRITER = "UPDATE writer SET first_name = ?, last_name = ? WHERE writer_id = ?";
    private final static String DELETE_WRITER = "DELETE FROM writer WHERE writer_id = ?";


    @Override
    public Writer getById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(GET_BY_ID)){

            preparedStatement.setLong( 1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return convertResultSetToWriter(resultSet);

        } catch (SQLException e) {
            System.out.println("IN get by id exception: " + e.getMessage());
            return null;
        }
    }


@Override
public List<Writer> getAll() {

    List<Writer> writerList = new ArrayList<>();

    try(Statement statement = JDBCUtils.getConnectJDBC().createStatement()){

        ResultSet resultSet = statement.executeQuery(GET_WRITER_ALL);

        while(resultSet.next()) {
            Writer writer = new Writer();

            writer.setId(resultSet.getLong("writer_id"));
            writer.setFirstName(resultSet.getString("first_name"));
            writer.setLastName(resultSet.getString("last_name"));

            writerList.add(writer);

            List<String> postList = new ArrayList<>();

            postList.add(resultSet.getString("post_name"));

            System.out.println("id: " + writer.getId());
            System.out.println("firstName: " + writer.getFirstName());
            System.out.println("lastName: " + writer.getLastName());
        }

    } catch (SQLException e) {
        System.out.println("Error in writer getAll" + e.getMessage());
    }
    return writerList;
}

@Override
public Writer save(Writer writer) {

        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(GET_WRITER_SAVE)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in save: " + e.getMessage());
        }
        return writer;

}

@Override
public Writer update(Writer writer) {
    try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(UPDATE_WRITER)) {

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
public void deleteById(Long id) {
    try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(DELETE_WRITER)) {

        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Error in delete: " + e.getMessage());
    }
}
    public Writer convertResultSetToWriter(ResultSet resultSet) {
        Writer writer = null;
        try {
            while(resultSet.next()) {
                writer = new Writer();
                writer.setId(resultSet.getLong("writer_id"));
                writer.setFirstName(resultSet.getString("first_name"));
                writer.setLastName(resultSet.getString("last_name"));
            }
        }catch (SQLException e) {
            System.out.println("IN convertResultSetToWriter error: " + e.getMessage());
        }

        return writer;
    }
}

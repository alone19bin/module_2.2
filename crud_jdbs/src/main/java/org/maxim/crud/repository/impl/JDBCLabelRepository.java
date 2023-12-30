package org.maxim.crud.repository.impl;

import org.maxim.crud.model.Label;
import org.maxim.crud.model.Post;
import org.maxim.crud.model.Writer;
import org.maxim.crud.repository.LabelRepository;
import org.maxim.crud.service.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCLabelRepository implements LabelRepository {
    private final static String GET_LABEL_BY_ID = "SELECT * FROM WHERE label_id = ?";
    private final static String GET_LABEL_ALL = "SELECT * FROM label";
    private final static String LABEL_SAVE = "INSERT INTO label(writer_id, post_name) VALUES (?, ?)";
    private final static String LABEL_UPDATE = "UPDATE label set label_name = ? where label_id = ?";
    private final static String DELETE_BY_ID = "DELETE FROM label WHERE post_id = ?";

    @Override
    public Label getById(Long id) {
            try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(GET_LABEL_BY_ID)){

                preparedStatement.setLong( 1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                return convertResultSetToLabel(resultSet);

            } catch (SQLException e) {
                System.out.println("Error in Label getId" + e.getMessage());
                return null;
            }
    }

    @Override
    public List<Label> getAll() {
        List<Label> labelsList = new ArrayList<>();

        try(Statement statement = JDBCUtils.getConnectJDBC().createStatement()){

            ResultSet resultSet = statement.executeQuery(GET_LABEL_ALL);

            while(resultSet.next()) {
                Label label = new Label();

                label.setId(resultSet.getLong("label_id"));
                label.setName(resultSet.getString("label_name"));

                labelsList.add(label);

                List<String> labelList = new ArrayList<>();

                labelList.add(resultSet.getString("label_name"));

                System.out.println("id: " + label.getId());
                System.out.println("name: " + label.getName());
            }

        } catch (SQLException e) {
            System.out.println("Error in Label getAll" + e.getMessage());
        }
        return labelsList;
    }

    @Override
    public Label save(Label label) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(LABEL_SAVE)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in Label save: " + e.getMessage());
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(LABEL_UPDATE)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setLong(2, label.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in label update: " + e.getMessage());
        }
        return label;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = JDBCUtils.getConnectJDBC().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error in label delete: " + e.getMessage());
        }
    }
    public Label convertResultSetToLabel(ResultSet resultSet) {
    Label label = null;
        try {
        while(resultSet.next()) {
            label = new Label();
            label.setId(resultSet.getLong("label_id"));
            label.setName(resultSet.getString("lable_name"));
        }
    }catch (SQLException e) {
        System.out.println("IN convertResultSetToLable error: " + e.getMessage());
    }

        return label;
}
}

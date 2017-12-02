package com.rocket.database;

import com.baseclasses.ResultClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultDao implements Dao {

    private Connection connection;

    public ResultDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ResultClass> listAll() throws Exception {
        List<ResultClass> resultSet = new ArrayList<>();
        String sqlStatement = "SELECT * FROM rocket_schema.results_set";
        if (connection != null) {
            try (
                    Statement statementObj = connection.createStatement();
                    ResultSet resultsSet = statementObj.executeQuery(sqlStatement)) {

                String name;
                long budget;
                int explosions;

                while (resultsSet.next()) {
                    name = resultsSet.getString("rocket_name");
                    budget = resultsSet.getInt("budget_needed");
                    explosions = resultsSet.getInt("num_of_explosion");
                    resultSet.add(new ResultClass(name, Math.toIntExact(budget), explosions));
                }
            }
        } else {
            System.out.println("ResultClassDao: No connection!");
        }

        return resultSet;
    }

    @Override
    public void createResultClass(ResultClass resultClass) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("INSERT INTO rocket_schema.results_set VALUES('")
                .append(resultClass.getName())
                .append("', ")
                .append(resultClass.getTotalBudget())
                .append(", ")
                .append(resultClass.getNumOfExplosion())
                .append(")");
        String sqlStatement = stringBuilder.toString();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlStatement);
        }
    }

    @Override
    public void updateResultClass(ResultClass resultClass) throws Exception {
        String sqlStatement = "UPDATE rocket_schema.results_set set num_of_explosion = ? , budget_needed = ? WHERE rocket_name = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(3, resultClass.getName());
            preparedStatement.setInt(2, Math.toIntExact(resultClass.getTotalBudget()));
            preparedStatement.setInt(1, resultClass.getNumOfExplosion());

            preparedStatement.executeUpdate();
        }
    }
}

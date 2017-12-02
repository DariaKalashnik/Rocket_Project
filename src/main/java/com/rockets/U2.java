package com.rockets;

import com.baseclasses.Rocket;
import com.baseclasses.RocketClass;
import com.rocket.database.DBWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class U2 extends Rocket {

    private RocketClass rocketClass = new RocketClass();
    private Random random = new Random();
    private double chance = random.nextDouble();

    public U2() {
        // Take data to from DB and store it in variables
        DBWorker dbWorker = new DBWorker();
        String query = "select * from spaceship  where id = 2";

        try {
            Statement statement = dbWorker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                rocketClass.setName(resultSet.getString("name"));
                rocketClass.setWeight(resultSet.getInt("weight"));
                rocketClass.setRocketCost(resultSet.getInt("rocketCost"));
                rocketClass.setWeightLimit(resultSet.getInt("weightLimit"));
                rocketClass.setPercentLaunch(resultSet.getDouble("percentLaunch"));
                rocketClass.setPercentLand(resultSet.getDouble("percentLand"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rocketName = rocketClass.getName();
        weightLimit = rocketClass.getWeightLimit();
        rocketCost = rocketClass.getRocketCost();
        rocketWeight = rocketClass.getWeight();
        allWeightWithCargo = rocketWeight + currentRocketWeight;
    }

    @Override
    public boolean launch() {
        // Chance of rocket launch explosion = 4% * (cargo carried / cargo limit)
        percent = rocketClass.getPercentLaunch();
        double chanceOfExplosion = percent * ((double) allWeightWithCargo / (double) weightLimit);
        return !(chanceOfExplosion >= chance);
    }

    @Override
    public boolean land() {
        // Chance of rocket landing crash = 8% * (cargo carried / cargo limit)
        percent = rocketClass.getPercentLand();
        double chanceOfCrash = percent * ((double) allWeightWithCargo / (double) weightLimit);
        return !(chanceOfCrash >= chance);
    }
}

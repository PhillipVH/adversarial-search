package za.ac.sun.cs.adversarial.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import za.ac.sun.cs.adversarial.agent.*;

public class AgentProperties {

    String fileName;

    public AgentProperties(String fileName) {
        this.fileName = fileName;
    }

    public Agent parseProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        boolean useTT = false;
        int player = 0;

        try {
            input = new FileInputStream(fileName);
            // Load the properties file.
            prop.load(input);

            // Parse the properties and build the agent
            int m = Integer.parseInt(prop.getProperty("m"));
            int n = Integer.parseInt(prop.getProperty("n"));
            int k = Integer.parseInt(prop.getProperty("k"));
            int depth = Integer.parseInt(prop.getProperty("depth"));
            String strategy = prop.getProperty("Strategy");
            String useTable = prop.getProperty("TranspositionTable");

            if (useTable.equals("True")) {
                useTT = true;
            }

            if (strategy.equals("Negamax-F1")) {
                return new NegamaxAgent(m, n, k, depth, player, "F1");
            } else if (strategy.equals("Negamax-F2")) {
                return new NegamaxAgent(m, n, k, depth, player, "F2");
            } else if (strategy.equals("Negamax-F3")) {
                if (useTT) {
                    return new NegaDeepAgent(m, n, k, depth, player, true);
                } else {
                    return new NegaDeepAgent(m, n, k, depth, player, false);
                }
            } else if (strategy.equals("Negascout")) {
                if (useTT) {
                    return new NegascoutAgent(m, n, k, depth, player);
                } else {
                    return new NegascoutAgent(m, n, k, depth, player);
                }
            } else if (strategy.equals("Random")) {
                return new RandomAgent(m, n, k, player);
            } else {
                return new RandomAgent(m, n, k, player);
            }
        } catch (IOException e) {
            System.out.println("Could not find properties file.");
        }

        return null;

    }
}
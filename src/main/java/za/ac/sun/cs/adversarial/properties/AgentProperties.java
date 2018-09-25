package za.ac.sun.cs.adversarial.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import za.ac.sun.cs.adversarial.agent.*;

/**
 * The class representing Properties for an agent.
 */
public class AgentProperties {

    private Properties prop = new Properties();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public AgentProperties(Properties prop) {
        this.prop = prop;
    }

    public AgentProperties(String filename) {
        InputStream input = null;
        try {
            input = new FileInputStream(filename);
            // Load the properties file.
            this.prop.load(input);
        } catch (IOException e) {
            logger.info("Could not find properties file.");
        }

    }

    /**
     * @param player - Player number.
     * @return The agent for the player.
     */
    public Agent parseProperties(int player) {
        boolean useTT = false;

        // Parse the properties and build the agent
        int m = Integer.parseInt(prop.getProperty("m", "3"));
        int n = Integer.parseInt(prop.getProperty("n", "3"));
        int k = Integer.parseInt(prop.getProperty("k", "3"));
        int depth = Integer.parseInt(prop.getProperty("depth", "3"));
        String strategy = prop.getProperty("Strategy", "Negamax-F2");
        String useTable = prop.getProperty("TranspositionTable", "False");

        if (useTable.equals("True")) {
            useTT = true;
        }

        switch (strategy) {
            case "Negamax-F1":
                return new NegamaxAgent(m, n, k, depth, player, "F1");
            case "Negamax-F2":
                return new NegamaxAgent(m, n, k, depth, player, "F2");
            case "Negamax-F3":
                if (useTT) {
                    return new NegaDeepAgent(m, n, k, depth, player, true);
                } else {
                    return new NegaDeepAgent(m, n, k, depth, player, false);
                }
            case "Negascout":
                if (useTT) {
                    return new NegascoutAgent(m, n, k, depth, player, true);
                } else {
                    return new NegascoutAgent(m, n, k, depth, player, false);
                }
            case "Random":
                return new RandomAgent(m, n, k, player);
            default:
                return new RandomAgent(m, n, k, player);
        }
    }
}
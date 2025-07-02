package edu.kis.powp.jobs2d.command.manager;


import edu.kis.powp.jobs2d.command.entries.CommandEntry;
import edu.kis.powp.jobs2d.command.strategy.ParsingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CommandParsingStrategiesManager {
    private final List<ParsingStrategy> availableStrategies;
    private ParsingStrategy currentStrategy;

    public CommandParsingStrategiesManager() {
        this.availableStrategies = new ArrayList<>();
    }

    public CommandParsingStrategiesManager(List<ParsingStrategy> availableStrategies) {
        this.availableStrategies = availableStrategies;
    }

    public void registerStrategy(ParsingStrategy strategy) {
        availableStrategies.add(strategy);
    }

    public void setStrategy(ParsingStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public ParsingStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    public List<ParsingStrategy> getAvailableStrategies() {
        return new ArrayList<>(availableStrategies);
    }

    public List<CommandEntry> parseCommands(String input) {
        if (currentStrategy != null) {
            return currentStrategy.parseCommands(input);
        }

        // Auto-detect strategy if none is set
        ParsingStrategy detectedStrategy = detectBestStrategy(input);
        if (detectedStrategy != null) {
            setStrategy(detectedStrategy);
            List<CommandEntry> commandEntryList = detectedStrategy.parseCommands(input);
            currentStrategy = null;
            return commandEntryList;
        }


        throw new IllegalArgumentException("No suitable parsing strategy found for input format");
    }

    public ParsingStrategy detectBestStrategy(String input) {
        for (ParsingStrategy strategy : availableStrategies) {
            if (strategy.canParse(input)) {
                return strategy;
            }
        }
        return null;
    }

    public String getCurrentStrategyName() {
        return currentStrategy != null ? currentStrategy.getFormatName() : "Auto-detect";
    }
}

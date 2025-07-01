package edu.kis.powp.jobs2d.command;


import edu.kis.powp.jobs2d.command.entries.CommandEntry;
import edu.kis.powp.jobs2d.command.strategy.JsonParsingStrategy;
import edu.kis.powp.jobs2d.command.strategy.ParsingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CommandParsingContext {
    private List<ParsingStrategy> availableStrategies;
    private ParsingStrategy currentStrategy;

    public CommandParsingContext() {
        this.availableStrategies = new ArrayList<>();
        // Register default strategies
        registerStrategy(new JsonParsingStrategy());
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
            return detectedStrategy.parseCommands(input);
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

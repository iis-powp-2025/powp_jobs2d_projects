package edu.kis.powp.jobs2d.plugin;

import edu.kis.powp.appbase.Application;

import java.util.*;

public class FeatureManager {
    private static final List<FeaturePlugin> plugins = new ArrayList<>();

    public static void registerFeature(FeaturePlugin plugin) {
        plugins.add(plugin);
    }

    public static void initializeAll(Application app) throws DependencyException {
        System.out.println("Starting feature initialization with dependency checking...");

        List<FeaturePlugin> orderedPlugins = resolveDependencies();

        System.out.print("Initializing order: ");
        int i = 0;
        for (FeaturePlugin featurePlugin : orderedPlugins) {
            i++;
            System.out.print(featurePlugin.getClass().getSimpleName());
            if (orderedPlugins.size() != i) System.out.print(" -> ");
        }
        System.out.println();

        for (FeaturePlugin plugin : orderedPlugins) {
            String featureName = plugin.getClass().getSimpleName();
            System.out.println("Initializing feature: " + featureName);

            try {
                plugin.setup(app);
                System.out.println("Successfully initialized: " + featureName);
            } catch (Exception e) {
                System.out.println("Failed to initialize " + featureName + ": " + e.getMessage());
                throw new DependencyException("Feature initialization failed: " + featureName);
            }
        }

        System.out.println("All features initialized successfully!");
    }

    private static List<FeaturePlugin> resolveDependencies() throws DependencyException {
        Map<String, FeaturePlugin> featureMap = createFeatureMap();
        Map<String, Set<String>> dependencies = buildFeaturesDependencies(featureMap);

        validateDependencies(featureMap, dependencies);

        return sortFeatures(featureMap, dependencies);
    }

    private static Map<String, FeaturePlugin> createFeatureMap() {
        Map<String, FeaturePlugin> featureMap = new HashMap<>();
        for (FeaturePlugin plugin : plugins) {
            String featureName = plugin.getFeatureName();
            featureMap.put(featureName, plugin);
        }
        return featureMap;
    }

    private static Map<String, Set<String>> buildFeaturesDependencies(Map<String, FeaturePlugin> featureMap) {
        Map<String, Set<String>> dependencies = new HashMap<>();

        for (FeaturePlugin plugin : featureMap.values()) {
            String featureName = plugin.getFeatureName();
            String[] featureDependencies = plugin.getDependencies();

            Set<String> dependencySet = new HashSet<>(Arrays.asList(featureDependencies));

            dependencies.put(featureName, dependencySet);
        }

        System.out.println("Built Features dependenceies: " + dependencies);
        return dependencies;
    }

    private static void validateDependencies(Map<String, FeaturePlugin> featureMap,
                                             Map<String, Set<String>> dependencies) throws DependencyException {
        for (Map.Entry<String, Set<String>> entry : dependencies.entrySet()) {
            String featureName = entry.getKey();
            Set<String> featureDeps = entry.getValue();

            for (String dependency : featureDeps) {
                if (!featureMap.containsKey(dependency)) {
                    throw new DependencyException(
                            String.format("Feature '%s' depends on '%s' but '%s' is not registered",
                                    featureName, dependency, dependency));
                }
            }
        }
    }

    private static List<FeaturePlugin> sortFeatures(Map<String, FeaturePlugin> featureMap,
                                                    Map<String, Set<String>> dependencies) throws DependencyException {
        Map<String, Integer> dependencyCounts = new HashMap<>();
        for (String feature : featureMap.keySet()) {
            dependencyCounts.put(feature, 0);
        }

        for (Set<String> deps : dependencies.values()) {
            for (String dep : deps) {
                dependencyCounts.put(dep, dependencyCounts.get(dep) + 1);
            }
        }

        Queue<String> readyQueue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : dependencyCounts.entrySet()) {
            if (entry.getValue() == 0) {
                readyQueue.add(entry.getKey());
            }
        }

        List<FeaturePlugin> initializationOrder = new ArrayList<>();

        while (!readyQueue.isEmpty()) {
            String current = readyQueue.poll();
            initializationOrder.add(featureMap.get(current));

            for (String dependent : dependencies.getOrDefault(current, Collections.emptySet())) {
                int newCount = dependencyCounts.get(dependent) - 1;
                dependencyCounts.put(dependent, newCount);

                if (newCount == 0) {
                    readyQueue.add(dependent);
                }
            }
        }

        return initializationOrder;
    }

    public static class DependencyException extends RuntimeException {
        public DependencyException(String message) {
            super(message);
        }
    }
}
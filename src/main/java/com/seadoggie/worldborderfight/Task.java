package com.seadoggie.worldborderfight;

import net.minecraft.scoreboard.ScoreboardCriterion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task {

    /**
     * Indicates how difficult a task is. Probably from 1-10 inclusive.
     */
    public int Difficulty;
    /**
     * Change in ScoreBoardCriterion that will complete the task
     * <p>Note: this is a change, not the total expected</p>
     */
    public int Goal;
    /**
     * Name displayed to the user
     */
    public String Name;
    /**
     * Depends on the biome
     */
    public boolean BiomeDependent = false;
    /**
     * Valid biomes for this task. Empty list if not {@link #BiomeDependent}
     * <p>Use vanilla names (lowercase w/ underscores. see: {@link net.minecraft.world.biome.BiomeKeys})</p>
     */
    public List<String> BiomeNames = new ArrayList<>();
    /**
     * An associated statistic to measure
     */
    public ScoreboardCriterion ScoreboardCriterion;

    public Task(String name, int goal, int difficulty){
        Name = String.format(name, goal);
        Goal = goal;
        Difficulty = difficulty;
    }

    public boolean isValidWithSettings(int minDifficulty, String[] biomes){
        if(Difficulty > minDifficulty){
            if(BiomeDependent){
                ArrayList<String> biomeList = new ArrayList<>();
                Collections.addAll(biomeList, biomes);
                return BiomeNames.stream().anyMatch(biomeList::contains);
            }else{
                return true;
            }
        }
        return false;
    }

    public static void ExampleTask(){
        Task saplingTask = new Task("Collect %s Oak Saplings", 10, 1);
        // Only valid in some biomes
        saplingTask.BiomeDependent = true;
        // Add Valid biomes
        saplingTask.BiomeNames.addAll(Arrays.asList("bamboo_jungle", "dark_forest", "flower_forest", "forest", "frozen_river", "jungle", "meadow", "plains", "river", "savanna", "savanna_plateau", "sparse_jungle", "sunflower_plains", "swamp", "windswept_forest", "windswept_savanna", "wooded_badlands"));
        // ToDo: Find a way to make a ScoreboardCriterion and use that to track the task status
    }
}
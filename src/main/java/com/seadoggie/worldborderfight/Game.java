package com.seadoggie.worldborderfight;

import com.seadoggie.worldborderfight.event.ServerPlayerDeathCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {

    /**
     * The number of deaths before the relaxation
     */
    public int DeathCountReset = 10;
    private int Deaths;
    private final WorldBorder OverWorldBorder;

    private double RelaxationAmount = 0.25;

    private static final List<Task> TASKS = new ArrayList<>(){};
    final private ServerPlayerEntity player;

    public Game(ServerPlayerEntity player, boolean WorldBorderIncreasing){
        this.player = player;
        OverWorldBorder = Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getWorld(World.OVERWORLD)).getWorldBorder();
        ServerPlayerDeathCallback.EVENT.register(this::onDeath);
        if(WorldBorderIncreasing){
            setWorldBorderSize(20);
        }else{
            setWorldBorderSize(6969);
        }
    }

    public int getDeaths() { return Deaths; }

    public void addDeath() {
        Deaths++;
        if(Deaths > DeathCountReset){
            Deaths = 0;
            Relaxation();
        }
    }

    public ActionResult onDeath(ServerPlayerEntity player, DamageSource damageSource){
        addDeath();
        return ActionResult.PASS;
    }

    /**
     * Called when the player has died too many times and the game has gotten too difficult
     */
    public void Relaxation(){
        player.sendMessage(Text.literal("This is going on your permanent record, sir"));
        increaseWorldBorder(getWorldBorderSize() * RelaxationAmount);
    }

    public double getWorldBorderSize() { return OverWorldBorder.getSize(); }

    public void setWorldBorderSize(double size) { OverWorldBorder.setSize(size); }

    public void increaseWorldBorder(double size){ OverWorldBorder.setSize(OverWorldBorder.getSize() + size); }

    public Task getNextTask(int minDifficulty, String biome){
        List<Task> currentlyValidTasks = new ArrayList<>();
        for(Task task : TASKS){
            if(task.isValidWithSettings(minDifficulty, new String[]{ biome })){
                currentlyValidTasks.add(task);
            }
        }
        return currentlyValidTasks.get(new Random().nextInt(currentlyValidTasks.size()));
    }
}

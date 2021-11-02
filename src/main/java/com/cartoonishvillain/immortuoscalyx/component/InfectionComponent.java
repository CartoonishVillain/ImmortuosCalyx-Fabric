package com.cartoonishvillain.immortuoscalyx.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;

public class InfectionComponent implements ComponentV3 {

    protected int infectionProgress = 0;
    protected int infectionTimer = 0;
    protected double resistance = 1;
    protected boolean follower = false;


    public int getInfectionProgress() { return this.infectionProgress; } //grabs the infection %

    public void setInfectionProgress(int infectionProgress) { this.infectionProgress = infectionProgress; } //sets infection %. Maybe for a command later or something.


    public void setInfectionProgressIfLower(int infectionProgress) {
        if(this.infectionProgress < infectionProgress){
            this.infectionProgress = infectionProgress;
        }
    }


    public void addInfectionProgress(int infectionProgress) { this.infectionProgress += infectionProgress; } //ticks infection % up

    public int getInfectionTimer() {return infectionTimer; }

    public void addInfectionTimer(int Time) { this.infectionTimer += Time; }


    public void setInfectionTimer(int Time) { infectionTimer = Time; }


    public double getResistance() { return resistance; }


    public void addResistance(double resistance) { this.resistance += resistance;}


    public void setResistance(double resistance) { this.resistance = resistance;}


    public void setFollower(boolean isFollower) {
        follower = isFollower;
    }


    public boolean isFollower() {
        return follower;
    }

    @Override
    public void readFromNbt(CompoundTag nbt) {
        infectionProgress = nbt.getInt("infectionProgression");
        infectionTimer = nbt.getInt("infectionTimer");
        resistance = nbt.getFloat("infectionResistance");
        follower = nbt.getBoolean("infectionFollower");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("infectionProgression", infectionProgress);
        tag.putInt("infectionTimer", infectionTimer);
        tag.putDouble("infectionResistance", resistance);
        tag.putBoolean("infectionFollower", follower);
    }
}
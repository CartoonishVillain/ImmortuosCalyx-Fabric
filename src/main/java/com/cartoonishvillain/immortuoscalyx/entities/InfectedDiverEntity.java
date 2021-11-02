package com.cartoonishvillain.immortuoscalyx.entities;

import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

public class InfectedDiverEntity extends Drowned implements InfectedEntity {


    public InfectedDiverEntity(EntityType<InfectedDiverEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38F)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0D);
    }


    @Override
    public boolean okTarget(@Nullable LivingEntity entity) {
        if(entity != null){
            InfectionComponent h = INFECTION.get(entity);
            if(h.getInfectionProgress() >= 50) return false;
            return !this.level.isDay() || entity.isInWater();

       }else return false;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 5 + this.level.random.nextInt(7);
    }

    @Override
    protected SoundEvent getAmbientSound() { return Register.HUMANAMBIENT; }

    @Override
    protected SoundEvent getDeathSound() {return Register.HUMANDEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.HUMANHURT; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }
    @Override
    public void setBaby(boolean childZombie) {}
    @Override
    protected boolean isSunSensitive() {return false;}
    @Override
    public void aiStep() {
        super.aiStep();
        InfectionComponent h = INFECTION.get(this);
        if (h.getInfectionProgress() < 100) h.setInfectionProgress(100);
    }

}

package com.cartoonishvillain.immortuoscalyx.entities;

import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.immortuoscalyx.mixin.MobAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Set;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

public class InfectedIGEntity extends IronGolem implements InfectedEntity {


    public InfectedIGEntity(EntityType<InfectedIGEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 125D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Pillager.class, 16, true, false,  this::shouldAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, 16, true, false,  this::shouldAttackMonster));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 16, true, false,  this::shouldAttack));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 16, true, false,  this::shouldAttack));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, 16, true, false,  this::shouldAttackMonster));
    }

    public boolean shouldAttack(@Nullable LivingEntity entity) {
        if(entity != null){
            InfectionComponent h = INFECTION.get(entity);
            return h.getInfectionProgress() < 50;

        }else return false;
    }

    public boolean shouldAttackMonster(@Nullable LivingEntity entity) {
        if(entity != null){
            return !(entity instanceof InfectedEntity);
        }else return false;
    }


    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 5 + this.level.random.nextInt(5);
    }

    @Override
    protected SoundEvent getDeathSound() {return Register.IGDEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.IGHURT; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        InfectionComponent h = INFECTION.get(this);
            if(h.getInfectionProgress() < 100) h.setInfectionProgress(100);
    }
}

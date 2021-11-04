package com.cartoonishvillain.immortuoscalyx.component;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx.rawItem;
import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

public class InfectionHandler {

    //known infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity victim, float infectChance, int amount){
        InfectionComponent h = INFECTION.get(victim);
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.contagionConfig.ARMORRESISTMULTIPLIER);
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
    }


    //unknown infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity aggressor, LivingEntity victim, int amount){
            int infectChance = 0;
            InfectionComponent h = INFECTION.get(aggressor);
            infectChance = h.getInfectionProgress();

            h = INFECTION.get(victim);
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.contagionConfig.ARMORRESISTMULTIPLIER);
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
    }

    //Infected player attacks a non-player entity.
    public static void infectEntityByPlayer(Player aggressor, LivingEntity victim, int amount){
            AtomicInteger infectChance = new AtomicInteger(0);
            InfectionComponent h = INFECTION.get(aggressor);
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.PLAYERINFECTIONTHRESHOLD) infectChance.set(h.getInfectionProgress());

             h = INFECTION.get(victim);
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.contagionConfig.ARMORRESISTMULTIPLIER);
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance.get() - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
    }

    //PVP Contagion
    public static void infectPlayerByPlayer(Player aggressor, Player victim, int amount) {
        if (ImmortuosCalyx.config.playerToggles.PVPCONTAGION && (!ImmortuosCalyx.DimensionExclusion.contains(victim.level.dimension().location()) || !ImmortuosCalyx.config.dimensionsAndSpawnDetails.PLAYERINFECTIONINCLEANSE)) {
                AtomicInteger infectChance = new AtomicInteger(0);
                InfectionComponent h = INFECTION.get(aggressor);
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.PLAYERINFECTIONTHRESHOLD)
                    infectChance.set(h.getInfectionProgress());

                AtomicBoolean successfulTransfer = new AtomicBoolean(false);
                 h = INFECTION.get(victim);
                int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.contagionConfig.ARMORRESISTMULTIPLIER);
                double bioResist = h.getResistance();
                int InfectThreshold = (int) ((infectChance.get() - armorProtection) / bioResist);
                if (InfectThreshold > victim.getRandom().nextInt(100)) {
                    if(h.getInfectionProgress() == 0) successfulTransfer.set(true);
                    h.setInfectionProgressIfLower(amount);
                }

            if(successfulTransfer.get()){
                    h = INFECTION.get(aggressor);
                    h.addInfectionProgress(-ImmortuosCalyx.config.otherDetails.PVPCONTAGIONRELIEF);
                if(!aggressor.level.isClientSide)aggressor.level.playSound(null, aggressor.blockPosition(), Register.HUMANHURT, SoundSource.PLAYERS, 1f, 1.2f);
            }
        }
    }

    //Guaranteed Contagion.
    public static void staticInfect(LivingEntity entity, int amount){
        InfectionComponent h = INFECTION.get(entity);
            h.setInfectionProgressIfLower(amount);
    }


    public static void bioInfectCheck(ItemStack itemStack, Level level, LivingEntity livingEntity){
        if(!level.isClientSide && livingEntity instanceof Player && itemStack.isEdible() && rawItem.contains(itemStack.getItem()) && (!ImmortuosCalyx.DimensionExclusion.contains(livingEntity.level.dimension().location()) || !ImmortuosCalyx.config.dimensionsAndSpawnDetails.RAWFOODINFECTIONINCLEANSE)){
            InfectionHandler.bioInfect(livingEntity, ImmortuosCalyx.config.contagionConfig.RAWFOODINFECTIONVALUE, 1);
        }
    }

    //Non-Attack Vector infection. Armor ignored.
    public static void bioInfect(LivingEntity victim, float infectChance, int amount){
        InfectionComponent h = INFECTION.get(victim);
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
    }

    //Non-Attack Vector infection. Unknown chance.
    public static void commonAerosol(LivingEntity victim, LivingEntity transmissionVector, int amount){
        AtomicInteger infectChance = new AtomicInteger(0);
        InfectionComponent h = INFECTION.get(transmissionVector);
            infectChance.set(h.getInfectionProgress());

            h = INFECTION.get(victim);
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance).get()/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
    }
}

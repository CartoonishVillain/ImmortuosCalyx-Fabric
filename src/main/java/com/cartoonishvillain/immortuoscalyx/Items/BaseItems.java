package com.cartoonishvillain.immortuoscalyx.Items;

import com.cartoonishvillain.immortuoscalyx.component.ItemUsages;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseItems extends Item{
    String lore1;
    String lore2;
    String lore3;
    String lore4;
    ItemFunctionality itemFunctionality;
    public BaseItems(Properties properties, String lore1, String lore2, String lore3, String lore4, ItemFunctionality itemFunctionality) {
        super(properties);
        this.lore1 = lore1;
        this.lore2 = lore2;
        this.lore3 = lore3;
        this.lore4 = lore4;
        this.itemFunctionality = itemFunctionality;

    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(player.isCrouching() && !itemFunctionality.equals(ItemFunctionality.NONE) && interactionHand.equals(InteractionHand.MAIN_HAND) && !level.isClientSide && player.getMainHandItem().getItem() instanceof BaseItems){
            if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.ANTIBIOTIC)){
                ItemUsages.useAntiParasitic(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.CALYXIDE)){
                ItemUsages.useCalyxide(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.EGGS)){
                ItemUsages.useImmortuosCalyxEggs(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.SCANNER)){
                ItemUsages.useScanner(player, player);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(lore1 != ""){
            tooltip.add(new TextComponent(lore1));
            if(lore2 != ""){
                tooltip.add(new TextComponent(lore2));
                if(lore3 != ""){
                    tooltip.add(new TextComponent(lore3));
                    if(lore4 != ""){
                        tooltip.add(new TextComponent(lore4));
                    }
                }
            }
        }
    }

    public ItemFunctionality getItemFunctionality(){
        return itemFunctionality;
    }
}

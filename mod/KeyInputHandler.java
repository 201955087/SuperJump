package net.ledestudio.example.mod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

import static net.ledestudio.example.mod.ExampleMod.MODID;
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class KeyInputHandler {
    public static final KeyMapping SUPER_JUMP = new KeyMapping(
            "key.superjump.charge",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_SPACE,
            "key.categories.movement"
    );
    private static boolean isCharging = false;
    private static long chargeStartTime = 0;
    private static final long MAX_CHARGE_TIME = 2000;

    @SubscribeEvent
    private static void onKeyInput(InputEvent.Key event) {
        if (SUPER_JUMP.isDown() && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_LSHIFT)) {
            if (!isCharging) {
                isCharging = true;
                chargeStartTime = System.currentTimeMillis();
                //Minecraft.getInstance().setScreen(new Charging());
            }
        } else if (isCharging) {
            isCharging = false;
            long chargeTime = System.currentTimeMillis() - chargeStartTime;
            if (chargeTime > MAX_CHARGE_TIME) {
                chargeTime = MAX_CHARGE_TIME;
            }
            double jumpHeight = 1.0 + (chargeTime / (double) MAX_CHARGE_TIME) * 4.0; // 1 to 5 blocks
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.jumpFromGround();
                player.setDeltaMovement(player.getDeltaMovement().x, jumpHeight, player.getDeltaMovement().z);
                player.fallDistance = 0; // Prevent fall damage
            }
            Minecraft.getInstance().setScreen(null);
        }
    }
    @SubscribeEvent
    public static void onTick(ClientTickEvent event) {
        if (isCharging) {
            long chargeTime = System.currentTimeMillis() - chargeStartTime;
            if (chargeTime > MAX_CHARGE_TIME) {
                chargeTime = MAX_CHARGE_TIME;
            }
            // Update UI with the charge time
            //Minecraft.getInstance().setScreen(new Charging(chargeTime / (float) MAX_CHARGE_TIME));
        }
    }
}

package net.ledestudio.example.mod;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static net.ledestudio.example.mod.ExampleMod.MODID;

public class Charging extends Screen {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(MODID, "textures/gui/widgets.png");
    private final float chargeProgress;

    public Charging() {
        super(Component.translatable("charging"));
        this.chargeProgress = 0.0f;
    }

    public Charging(float chargeProgress) {
        super(Component.translatable("charging"));
        this.chargeProgress = chargeProgress;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int width = 182;
        int left = (this.width - width) / 2;
        int top = this.height / 2 - 10;
        int filled = (int) (this.chargeProgress * (float) width);
        graphics.blit(WIDGETS_LOCATION, left, top, 0, 84, width, 5);
        if(filled > 0) {
            graphics.blit(WIDGETS_LOCATION, left, top, 0, 89, filled, 5);
        }
        RenderSystem.disableBlend();
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

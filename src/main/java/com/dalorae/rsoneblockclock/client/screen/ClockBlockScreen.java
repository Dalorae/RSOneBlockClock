package com.dalorae.rsoneblockclock.client.screen;


import com.dalorae.rsoneblockclock.container.ClockBlockContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClockBlockScreen extends ContainerScreen<ClockBlockContainer> {
    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("textures/gui/rsobc.png");

    public ClockBlockScreen(ClockBlockContainer cbcCon, PlayerInventory playerInv, ITextComponent itComp) {
        super(cbcCon, playerInv, itComp);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void render(MatrixStack matrixStack, int matX, int matY, float p_230430_4_) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, matX, matY, p_230430_4_);
        this.renderTooltip(matrixStack, matX, matY);
    }

    protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(CONTAINER_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}

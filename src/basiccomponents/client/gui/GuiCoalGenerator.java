package basiccomponents.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;
import basiccomponents.common.BasicComponents;
import basiccomponents.common.container.ContainerCoalGenerator;
import basiccomponents.common.tileentity.TileEntityCoalGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends GuiContainer
{
	private static final ResourceLocation coalGeneratorTexture = new ResourceLocation(BasicComponents.TEXTURE_DOMAIN, "textures/gui/coal_generator.png");

	private TileEntityCoalGenerator tileEntity;

	private int containerWidth;
	private int containerHeight;

	public GuiCoalGenerator(InventoryPlayer par1InventoryPlayer, TileEntityCoalGenerator tileEntity)
	{
		super(new ContainerCoalGenerator(par1InventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(this.tileEntity.getInvName(), 55, 6, 4210752);
        String displayText = "Generating";
		this.fontRenderer.drawString(displayText, 122 - this.fontRenderer.getStringWidth(displayText) / 2, 33, 4210752);

		if (this.tileEntity.generateWatts <= 0)
		{
			displayText = "Not Generating";
		}
		else if (this.tileEntity.generateWatts < TileEntityCoalGenerator.MIN_GENERATE_WATTS)
		{
			displayText = "Hull Heat: " + (int) (this.tileEntity.generateWatts / TileEntityCoalGenerator.MIN_GENERATE_WATTS * 100) + "%";
		}
		else
		{
			displayText = ElectricityDisplay.getDisplay(tileEntity.generateWatts * 20, ElectricUnit.WATT);
		}

		this.fontRenderer.drawString(displayText, 122 - this.fontRenderer.getStringWidth(displayText) / 2, 45, 4210752);
		displayText = "Voltage: " + (int) (this.tileEntity.getVoltage() * 1000);
		this.fontRenderer.drawString(displayText, 122 - this.fontRenderer.getStringWidth(displayText) / 2, 60, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.func_110577_a(coalGeneratorTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
	}
}

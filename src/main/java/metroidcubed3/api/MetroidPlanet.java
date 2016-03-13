package metroidcubed3.api;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import metroidcubed3.api.entity.IEntityShip;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

/** Don't forget to register the planet using
 *  {@link MetroidPlanetRegistry#addPlanet},
 *  otherwise it won't show up in the ship GUI*/
public abstract class MetroidPlanet
{
	/** NOT REQUIRED
	 * a single planet can reference multiple dimensions, and vice-versa */
	private final int dimension;
	/** NOT REQUIRED
	 *  A planet's name can be dynamic */
	private final String name; 
	/** this planet's ID.
	 * MUST BE UNIQUE, AND THE SAME FOR BOTH CLIENT AND SERVER.
	 * a good practice is to prefix it with your mod ID, e.g. mc3.talloniv*/
	public final String id;
	
	/**args: planet {@link #id}, planet {@link #name}, planet {@link #dimension} */
	public MetroidPlanet(String id, String name, int dimension)
	{
		this.id = id;
		this.name = name;
		this.dimension = dimension;
	}
	
	/**args: planet {@link #id}, planet {@link #dimension} */
	public MetroidPlanet(String id, int dimension)
	{
		this(id, null, dimension);
	}
	
	/**args: planet {@link #id}, planet {@link #name} */
	public MetroidPlanet(String id, String name)
	{
		this(id, name, 0);
	}
	
	/**args: planet {@link #id} */
	public MetroidPlanet(String id)
	{
		this(id, 0);
	}
	
	/** is the player at this planet? */
	@SideOnly(Side.CLIENT)
	public boolean playerHere()
	{
		return (Minecraft.getMinecraft().thePlayer.dimension == dimension);
	}
	
	/** can this planet be visited? */
	@SideOnly(Side.CLIENT)
	public boolean canBeVisted()
	{
		return (Minecraft.getMinecraft().thePlayer.dimension != dimension);
	}
	
	/** the planet's name. */
	@SideOnly(Side.CLIENT)
	public String planetName()
	{
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	/** add any extra planet information here. */
	public void addPlanetInfo(ArrayList<String> list) {}

	/** run every time the GUI is ticked.
	 * Useful for GUI effects. */
	@SideOnly(Side.CLIENT)
	public void guiTick() {}
	
	/** run when the GUI is created.
	 * Useful for GUI effects. */
	@SideOnly(Side.CLIENT)
	public void guiInit() {}
	
	/** teleport the player (and their ship) to the planet. */
	public void teleportPlayerHere(EntityPlayerMP player)
	{
		Entity ridden = player.ridingEntity;
		MinecraftServer server = player.mcServer;
		ServerConfigurationManager manager = server.getConfigurationManager();
		WorldServer world = server.worldServerForDimension(dimension);
		BasicTeleporter teleporter = new BasicTeleporter(world);
		manager.transferPlayerToDimension(player, dimension, teleporter);
		if (ridden instanceof IEntityShip)
		{
			int j = ridden.chunkCoordX;
			int l = ridden.chunkCoordZ;
			if (ridden.addedToChunk)
			{
				ridden.worldObj.getChunkFromChunkCoords(j, l).removeEntity(ridden);
			}
			ridden.worldObj.loadedEntityList.remove(ridden);
			ridden.worldObj.onEntityRemoved(ridden);
			
			manager.transferEntityToWorld(ridden, dimension, (WorldServer) ridden.worldObj, world, teleporter);
			
			player.mountEntity(ridden);
			((IEntityShip) ridden).setNeedsMounting();
		}
	}

	/** position of the left edge of this planet's icon on the planet screen.
	 *  values are 0 to 1, with  0 being far left, and 1 being far right. */
	@SideOnly(Side.CLIENT)
	public abstract double startx();
	/** position of the top edge of this planet's icon on the planet screen.
	 *  values are 0 to 1, with  0 being top, and 1 being bottom. */
	@SideOnly(Side.CLIENT)
	public abstract double starty();
	/** length of this planet's icon on the planet screen.
	 *  a value of 0 is no length, and a value of 1 is the length of the planet screen. */
	@SideOnly(Side.CLIENT)
	public abstract double size();
	/** this planet's icon */
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation planetIcon();
}

package metroidcubed3.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class BasicTeleporter extends Teleporter
{
private final LongHashMap destinationCoordinateCache = new LongHashMap();
private final List destinationCoordinateKeys = new ArrayList();
public BasicTeleporter(WorldServer par1WorldServer)
{
         super(par1WorldServer);
}
public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
{
	par1Entity.setLocationAndAngles((double)par2, (double)255, (double)par6, par1Entity.rotationYaw, 0.0F);
	par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
}
public void removeStalePortalLocations(long par1)
{
         if (par1 % 100L == 0L)
         {
                 Iterator iterator = this.destinationCoordinateKeys.iterator();
                 long j = par1 - 600L;
                 while (iterator.hasNext())
                 {
                         Long olong = (Long)iterator.next();
                         PortalPosition portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(olong.longValue());
                         if (portalposition == null || portalposition.lastUpdateTime < j)
                         {
                                 iterator.remove();
                                 this.destinationCoordinateCache.remove(olong.longValue());
                         }
                 }
         }
}
}
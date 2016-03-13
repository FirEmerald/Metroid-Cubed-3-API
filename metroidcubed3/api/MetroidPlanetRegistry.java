package metroidcubed3.api;

import java.util.ArrayList;

public class MetroidPlanetRegistry
{
	private static ArrayList<MetroidPlanet> planets = new ArrayList<MetroidPlanet>();
	/** adds a planet to the list. */
	public static void addPlanet(MetroidPlanet planet)
	{
		planets.add(planet);
	}
	/** returns the number of registered planets. */
	public static int registeredPlanets()
	{
		return planets.size();
	}
	/** gets a planet by it's index. */
	public static MetroidPlanet getPlanet(int index)
	{
		if (index < 0 || index >= planets.size()) return null;
		return planets.get(index);
	}
	/** gets a planet by it's ID. */
	public static MetroidPlanet getPlanet(String id)
	{
		for (MetroidPlanet planet : planets)
		{
			if (planet.id.equals(id)) return planet;
		}
		return null;
	}
	/** Removes the planet from the list. */
	public static void removePlanet(MetroidPlanet planet)
	{
		planets.remove(planet);
	}
	/** returns if the planet is registered in the list. */
	public static boolean hasPlanet(MetroidPlanet planet)
	{
		return planets.contains(planet);
	}
}
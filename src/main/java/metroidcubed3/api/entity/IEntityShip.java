package metroidcubed3.api.entity;

/** ONLY USED TO ENSURE THE SHIP IS MOUNTED ON THE CLIENT AFTER TRAVELING BETWEEN PLANETS */
public interface IEntityShip
{
	public void setNeedsMounting();
	
	public void setMounted();
}
package de.budschie.bmorph.capabilities.phantom_glide;

public class GlideCapability implements IGlideCapability
{
	private GlideStatus glideStatus = GlideStatus.STANDARD;
	private int chargeTime = 0;
	private int maxChargeTime;
	private int transitionTime;
	private int maxTransitionTime;
	private ChargeDirection chargeDir = null;
	
	@Override
	public GlideStatus getGlideStatus()
	{
		return glideStatus;
	}

	@Override
	public void setGlideStatus(GlideStatus glideStatus)
	{
		this.glideStatus = glideStatus;
	}

	@Override
	public int getChargeTime()
	{
		return chargeTime;
	}

	@Override
	public void setChargeTime(int time)
	{
		this.chargeTime = time;
	}

	@Override
	public ChargeDirection getChargeDirection()
	{
		return chargeDir;
	}

	@Override
	public void setChargeDirection(ChargeDirection direction)
	{
		this.chargeDir = direction;
	}

	@Override
	public int getMaxChargeTime()
	{
		return maxChargeTime;
	}

	@Override
	public void setMaxChargeTime(int time)
	{
		this.maxChargeTime = time;
	}

	@Override
	public int getTransitionTime()
	{
		return transitionTime;
	}

	@Override
	public void setTransitionTime(int time)
	{
		this.transitionTime = time;
	}

	@Override
	public int getMaxTransitionTime()
	{
		return maxTransitionTime;
	}

	@Override
	public void setMaxTransitionTime(int time)
	{
		this.maxTransitionTime = time;
	}	
}

/**
 * @author Unic
 * "hw01_classLoading" project, 2014.
 * GPL v3: http://gnu.org/licenses
 */

package classloading.core;

public class ClassInfo
{
	private Class<?> claŝŝ;
	private Object instance;
	private boolean nonInstantiable = false;
	
	public ClassInfo(Class<?> claŝŝ)
	{
		this.claŝŝ = claŝŝ;
	}
	
	public void setInstance(Object instance)
	{
		this.instance = instance;
	}
	
	public Object getInstance()
	{
		return instance;
	}
	
	public Class<?> getClaŝŝ()
	{
		return claŝŝ;
	}
	
	public void setNonInstantiable(boolean nonInstantiable)
	{
		this.nonInstantiable = nonInstantiable;
	}
	
	@Override
	public String toString()
	{
		String v = " ? ";
		
		if (instance != null)
		{
			v = instance.toString();
			
			if (v.length() > 0 && v.charAt(0) == 'ĉ')
				v = v.substring(1);
			else
				v = " ? ";
		}
		
		return "[" + (instance == null ? nonInstantiable ? "x " : "  " : "V ") + v + " " + claŝŝ;
	}
}
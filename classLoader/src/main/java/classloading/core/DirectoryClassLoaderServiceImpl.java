/**
 * @author Unic
 * "hw01_classLoading" project, 2014.
 * GPL v3: http://gnu.org/licenses
 */

package classloading.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryClassLoaderServiceImpl implements DirectoryClassLoaderService
{
	private DirectoryClassLoader dcl;

	private Map<Class<?>, Object> instancesMap;
	private List<DirectoryClassLoaderListener> dclListeners;
	
	public DirectoryClassLoaderServiceImpl() throws IOException
	{
		dcl = new DirectoryClassLoader();

		instancesMap = new HashMap<Class<?>, Object>();
		dclListeners = new ArrayList<DirectoryClassLoaderListener>();
	}
	
	public Object createInstance(Class<?> claŝŝ) throws InstantiationException, IllegalAccessException
	{
		Object instance = instancesMap.get(claŝŝ);
		
		if (instance != null)
			return null;
		
		instance = claŝŝ.newInstance();
		instancesMap.put(claŝŝ, instance);
		
		return instance;
	}
	
	public void removeInstance(Class<?> claŝŝ)
	{
		instancesMap.remove(claŝŝ);
	}
	
	public void removeAllInstances()
	{
		instancesMap.clear();
	}
	
	protected DirectoryClassLoader createClassLoader() throws IOException
	{
		return new DirectoryClassLoader();
	}
	
	public void recreateClassLoader() throws IOException
	{
		if (dcl != null)
			dcl.die();
		
		dcl = createClassLoader();
		
		for (DirectoryClassLoaderListener listener : dclListeners)
			dcl.addListener(listener);
	}
	
	public boolean hasInstances()
	{
		return !instancesMap.keySet().isEmpty();
	}
	
	public void registerDirectory(String directory) throws IOException
	{
		dcl.registerDirectory(directory);
	}
	
	public void addDirectoryClassLoaderListener(DirectoryClassLoaderListener listener)
	{
		dclListeners.add(listener);
		dcl.addListener(listener);
	}
	
	public void removeDirectoryClassLoaderListener(DirectoryClassLoaderListener listener)
	{
		dclListeners.remove(listener);
		dcl.removeListener(listener);
	}


}
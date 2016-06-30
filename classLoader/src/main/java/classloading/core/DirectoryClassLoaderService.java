/**
 * @author Unic
 * "hw01_classLoading" project, 2014.
 * GPL v3: http://gnu.org/licenses
 */

package classloading.core;

import java.io.IOException;

public interface DirectoryClassLoaderService
{
	Object createInstance(Class<?> claŝŝ) throws InstantiationException, IllegalAccessException;
	void recreateClassLoader() throws IOException;
	boolean hasInstances();
	void removeInstance(Class<?> claŝŝ);
	void removeAllInstances();
	void registerDirectory(String directory) throws IOException;
	void addDirectoryClassLoaderListener(DirectoryClassLoaderListener listener);
	void removeDirectoryClassLoaderListener(DirectoryClassLoaderListener listener);
}
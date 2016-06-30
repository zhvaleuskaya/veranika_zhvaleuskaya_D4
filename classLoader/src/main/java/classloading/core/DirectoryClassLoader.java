/**
 * @author Unic
 * "hw01_classLoading" project, 2014.
 * GPL v3: http://gnu.org/licenses
 */

package classloading.core;

import org.apache.log4j.Logger;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



public class DirectoryClassLoader extends ClassLoader
{
	private static final Logger LOG = Logger.getLogger(DirectoryClassLoader.class);
	private static final long CHECK_PERIOD = 500;
	
	private final ThisDirectoryVisitor directoryVisitor = new ThisDirectoryVisitor();
	private WatchService watcher;
	private Map<WatchKey, Path> paths;
	private List<DirectoryClassLoaderListener> listeners;
	private boolean alive = true;


	public DirectoryClassLoader() throws IOException
	{
		super();
		
		paths = new HashMap<WatchKey, Path>();
		watcher = FileSystems.getDefault().newWatchService();
		listeners = new ArrayList<DirectoryClassLoaderListener>();
	}
	
	public void registerDirectory(String directory) throws IOException
	{
		File file = new File(directory);
		if (!file.exists())
			throw new IOException("Directory doesn't exist.");
		
		Path path = Paths.get(directory);
		registerDirectory(path);
	}
	
	protected void registerDirectory(Path path) throws IOException
	{
		Files.walkFileTree(path, directoryVisitor);
	}

	protected void processEvents()
	{
		while (alive)
		{
			WatchKey key;
			
			try
			{
				key = watcher.take();
			}
			catch (InterruptedException e)
			{
				return;
			}

			Path path = paths.get(key);

			for (WatchEvent<?> event : key.pollEvents())
			{
				Kind<?> kind = event.kind();
				Path name = (Path)event.context();
				Path child = path.resolve(name);
				
				LOG.trace( "event: k=" + kind.name() + " n=" + name + " c=" + child );

				if (kind == ENTRY_CREATE)
				{
					try
					{
						if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS))
						{
							registerDirectory(child);
						}
						else
						{
							if (child.toString().endsWith(".jar"))
								loadJar(child.toString());
						}
					}
					catch (IOException e){}
				}
			}

			if ( !key.reset() )
			{
				paths.remove(key);

				if (paths.isEmpty())
					break;
			}
			
			sleep(CHECK_PERIOD);
		}
		
		listeners.clear();
	}
	
	protected class ThisDirectoryVisitor extends SimpleFileVisitor<Path>
	{
		@Override
		public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException
		{
			WatchKey watchKey = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			paths.put(watchKey, path);
			
			LOG.info("Registered: " + path.toString());
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
		{
			if (file.toString().endsWith(".jar"))
				loadJar( file.toString() );
			
			return FileVisitResult.CONTINUE;
		}
	}
	
	protected void loadJar(String jarPath) throws IOException
	{
		LOG.info("Loading \"" + jarPath + "\"...");
		
		JarFile jarFile = new JarFile(jarPath);
		Enumeration<?> jarEntries = jarFile.entries();

		while (jarEntries.hasMoreElements())
		{

			JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
			
			if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class"))
				continue;
			
			String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.');
			Class<?> claŝŝ = findLoadedClass(className);

			if (claŝŝ == null)
			{
				try
				{
					byte[] array = new byte[1024];
					InputStream in = jarFile.getInputStream(jarEntry);
					ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
					int length = in.read(array);
					
					while (length > 0)
					{
						out.write(array, 0, length);
						length = in.read(array);
					}

					claŝŝ = defineClass(className, out.toByteArray(), 0, out.size());

					if (claŝŝ.getDeclaredFields().length > 0) {

						LOG.info("  Class should not be loaded, but removed \"" + claŝŝ.getName() + "\"");
					} else {
						LOG.info(" Loaded class with Fields \"" + claŝŝ.getDeclaredFields().length + "\"" + "in calss" + className);

					}
					onClassCreated(claŝŝ);


				}
				catch (Exception e)
				{
					LOG.error( "  Not loaded \"" + className + "\": " + e.getClass().getSimpleName() + ": " + e.getMessage() );
				}
			}
			else
			{
				LOG.warn("  Already loaded: " + className);
			}
		}
		
		jarFile.close();
	}

	private void sleep(long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e){}
	}
	
	public void addListener(DirectoryClassLoaderListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(DirectoryClassLoaderListener listener)
	{
		listeners.remove(listener);
	}
	
	protected void onClassCreated(Class<?> claŝŝ)
	{
		for (DirectoryClassLoaderListener listener : listeners) {
			listener.classLoaded(claŝŝ);
		}
	}
	
	public void die()
	{
		alive = false;
	}
}
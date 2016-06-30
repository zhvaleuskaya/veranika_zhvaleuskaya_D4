/**
 * @author Unic
 * "hw01_classLoading" project, 2014.
 * GPL v3: http://gnu.org/licenses
 */

package classloading.gui;

import classloading.core.ClassInfo;
import classloading.core.DirectoryClassLoaderListener;
import classloading.core.DirectoryClassLoaderService;
import classloading.core.DirectoryClassLoaderServiceImpl;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class MainForm implements DirectoryClassLoaderListener
{
	private static final Logger LOG = Logger.getLogger(MainForm.class);
	
	private JFrame formMain;
	private DefaultListModel<ClassInfo> listClassesModel;
	private JList<ClassInfo> listClasses;
	private JButton bReload;
	private JLabel lPath;
	private JTextField tfPath;
	private JButton bLoad;
	private DirectoryClassLoaderService dclService;
	private JButton bCreateInstance;
	private JButton bRemoveInstance;
	private JButton bRemoveAllInstances;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainForm window = new MainForm();
					window.formMain.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm()
	{
		try
		{
			dclService = new DirectoryClassLoaderServiceImpl();
			dclService.addDirectoryClassLoaderListener(this);
		}
		catch (IOException e)
		{
			LOG.fatal("Can't create DirectoryClassLoaderService: " + e.getMessage());
			System.exit(0);
		}
		
		listClassesModel = new DefaultListModel<ClassInfo>();
		
		initialize();
	}

	private void initialize()
	{
		formMain = new JFrame();
		formMain.setResizable(false);
		formMain.setTitle("HW01 - Dynamic class loading");
		formMain.setBounds(100, 100, 603, 382);
		formMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		formMain.getContentPane().setLayout(null);
		
		JLabel lClasses = new JLabel("Loaded classes:");
		lClasses.setBounds(10, 37, 89, 14);
		formMain.getContentPane().add(lClasses);
		
		JScrollPane scrollClasses = new JScrollPane();
		scrollClasses.setBounds(10, 62, 577, 281);
		formMain.getContentPane().add(scrollClasses);
		
		listClasses = new JList<ClassInfo>();
		listClasses.setModel(listClassesModel);
		scrollClasses.setViewportView(listClasses);
		
		bReload = new JButton("Reload");
		bReload.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String directory = tfPath.getText().trim();
				
				if (directory.length() > 0)
					reloadClasses(directory);
			}
		});
		bReload.setBounds(498, 7, 89, 23);
		formMain.getContentPane().add(bReload);
		
		lPath = new JLabel("Watch for:");
		lPath.setBounds(10, 11, 89, 14);
		formMain.getContentPane().add(lPath);
		
		tfPath = new JTextField("ext");
		tfPath.setBounds(109, 7, 295, 23);
		formMain.getContentPane().add(tfPath);
		tfPath.setColumns(10);
		
		bLoad = new JButton("Load");
		bLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String directory = tfPath.getText().trim();
				
				if (directory.length() > 0)
					addClasses(directory);
			}
		});
		bLoad.setBounds(414, 7, 74, 23);
		formMain.getContentPane().add(bLoad);
		
		bCreateInstance = new JButton("Create instance");
		bCreateInstance.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = listClasses.getSelectedIndex();
				
				if (index != -1)
				{
					ClassInfo classInfo = listClassesModel.getElementAt(index);
					
					try
					{
						Object instance = dclService.createInstance( classInfo.getClaŝŝ() );
						
						if (instance != null)
							classInfo.setInstance(instance);
					}
					catch (Exception ex)
					{
						LOG.error("Can't create instance of " + classInfo.getClaŝŝ() + ":" + ex.getMessage());
						classInfo.setNonInstantiable(true);
					}
					
					listClasses.repaint();
				}
			}
		});
		bCreateInstance.setBounds(446, 33, 141, 23);
		formMain.getContentPane().add(bCreateInstance);
		
		bRemoveInstance = new JButton("Remove instance");
		bRemoveInstance.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int index = listClasses.getSelectedIndex();
				
				if (index != -1)
				{
					ClassInfo classInfo = listClassesModel.getElementAt(index);
					removeInstance(classInfo);
					listClasses.repaint();
				}
			}
		});
		bRemoveInstance.setBounds(281, 33, 155, 23);
		formMain.getContentPane().add(bRemoveInstance);
		
		bRemoveAllInstances = new JButton("Remove all instances");
		bRemoveAllInstances.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dclService.removeAllInstances();
				
				for (int i = 0; i < listClassesModel.getSize(); ++i)
					removeInstance( listClassesModel.getElementAt(i) );
				
				listClasses.repaint();
			}
		});
		bRemoveAllInstances.setBounds(109, 33, 162, 23);
		formMain.getContentPane().add(bRemoveAllInstances);
	}
	
	protected void recreateClassloader()
	{
		listClassesModel.removeAllElements();
		
		try
		{
			dclService.recreateClassLoader();
		}
		catch (IOException e)
		{
			LOG.error("Can't recreate class loader: " + e.getMessage());
		}
	}
	
	protected void reloadClasses(String directory)
	{
		recreateClassloader();
		addClasses(directory);
	}
	
	protected void addClasses(String directory)
	{
		try
		{
			dclService.registerDirectory(directory);
		}
		catch (IOException e)
		{
			LOG.error("Can't load: " + e.getMessage());
		}
	}
	
	protected void removeInstance(ClassInfo classInfo)
	{
		dclService.removeInstance( classInfo.getClaŝŝ() );
		classInfo.setInstance(null);
	}

	@Override
	public void classLoaded(Class<?> claŝŝ)
	{
		listClassesModel.addElement( new ClassInfo(claŝŝ) );
	}
}
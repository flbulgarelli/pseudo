package org.uqbarproject.pseudo.ui.wizard;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.wizard.IProjectInfo;
import org.eclipse.xtext.ui.wizard.XtextNewProjectWizard;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import com.google.inject.Inject;

public class PseudoNewProjectWizard extends XtextNewProjectWizard {

  @Inject
  private IImageHelper imageHelper;
	private WizardNewProjectCreationPage mainPage;

	@Inject
	public PseudoNewProjectWizard(IProjectCreator projectCreator) {
		super(projectCreator);
		setWindowTitle("New Pseudo Project");
	}

	/**
	 * Use this method to add pages to the wizard.
	 * The one-time generated version of this class will add a default new project page to the wizard.
	 */
	public void addPages() {
		mainPage = new WizardNewProjectCreationPage("basicNewProjectPage");
		mainPage.setTitle("Pseudo Project");
		mainPage.setDescription("Create a new Pseudo project.");
		mainPage.setImageDescriptor(createLogo());
		addPage(mainPage);
	}

  private ImageDescriptor createLogo() {
    return ImageDescriptor.createFromImage(imageHelper.getImage("Logo.png"));
  }

	/**
	 * Use this method to read the project settings from the wizard pages and feed them into the project info class.
	 */
	@Override
	protected IProjectInfo getProjectInfo() {
		PseudoProjectInfo projectInfo = new PseudoProjectInfo();
		projectInfo.setProjectName(mainPage.getProjectName());
		return projectInfo;
	}
	

}

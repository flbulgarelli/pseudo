package org.uqbarproject.pseudo.ui.wizard;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtend.type.impl.java.JavaBeansMetaModel;
import org.eclipse.xpand2.XpandExecutionContextImpl;
import org.eclipse.xpand2.XpandFacade;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.OutputImpl;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.util.PluginProjectFactory;
import org.eclipse.xtext.ui.wizard.AbstractPluginProjectCreator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class PseudoProjectCreator extends AbstractPluginProjectCreator {

	protected static final String DSL_GENERATOR_PROJECT_NAME = "org.uqbarproject.pseudo";

	protected static final String SRC_ROOT = "src";
	protected static final String SRC_GEN_ROOT = "src-gen";
	protected final List<String> SRC_FOLDER_LIST = ImmutableList.of(SRC_ROOT, SRC_GEN_ROOT);

  protected String[] getProjectNatures() {
    return new String[] {
      "org.eclipse.jdt.core.javanature",
      XtextProjectHelper.NATURE_ID
    };
  }
  
  protected String[] getBuilders() {
    return new String[]{
      "org.eclipse.jdt.core.javabuilder",
      XtextProjectHelper.BUILDER_ID
    };
  }
	
  @Override
  protected PluginProjectFactory createProjectFactory() {
    PluginProjectFactory projectFactory = super.createProjectFactory();
    return projectFactory;
  }
  
	@Override
	protected PseudoProjectInfo getProjectInfo() {
		return (PseudoProjectInfo) super.getProjectInfo();
	}
	
	protected String getModelFolderName() {
		return SRC_ROOT;
	}
	
	@Override
	protected List<String> getAllFolders() {
        return SRC_FOLDER_LIST;
    }

    @Override
	protected List<String> getRequiredBundles() {
		List<String> result = Lists.newArrayList(super.getRequiredBundles());
		result.add(DSL_GENERATOR_PROJECT_NAME);
		return result;
	}

	protected void enhanceProject(final IProject project, final IProgressMonitor monitor) throws CoreException {
		OutputImpl output = new OutputImpl();
		output.addOutlet(new Outlet(false, getEncoding(), null, true, project.getLocation().makeAbsolute().toOSString()));

		XpandExecutionContextImpl execCtx = new XpandExecutionContextImpl(output, null);
		execCtx.getResourceManager().setFileEncoding("UTF-8");
		execCtx.registerMetaModel(new JavaBeansMetaModel());

		XpandFacade facade = XpandFacade.create(execCtx);
		facade.evaluate("org::uqbarproject::pseudo::ui::wizard::PseudoNewProject::main", getProjectInfo());

		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

}
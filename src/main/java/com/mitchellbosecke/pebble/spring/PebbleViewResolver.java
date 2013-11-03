package com.mitchellbosecke.pebble.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Loader;

public class PebbleViewResolver extends AbstractTemplateViewResolver implements ViewResolver, InitializingBean {
	
	private Loader templateLoader;
	
	private PebbleEngine pebbleEngine;

	public PebbleViewResolver() {
		setViewClass(requiredViewClass());
	}
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		PebbleView view = (PebbleView) super.buildView(viewName);
		view.setTemplateName(viewName);
		view.setPebbleEngine(pebbleEngine);
		
		return view;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		templateLoader.setPrefix(this.getPrefix());
		templateLoader.setSuffix(this.getSuffix());
		pebbleEngine.setLoader(templateLoader);
	}
	
	@Required
	public void setPebbleEngine(PebbleEngine pebbleEngine){
		this.pebbleEngine = pebbleEngine;
	}
	
	@Required
	public void setTemplateLoader(Loader templateLoader){
		this.templateLoader = templateLoader;
	}
	
	@Override
	protected Class<?> requiredViewClass() {
		return PebbleView.class;
	}

}

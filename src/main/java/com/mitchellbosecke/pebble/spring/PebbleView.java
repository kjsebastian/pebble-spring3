package com.mitchellbosecke.pebble.spring;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class PebbleView extends AbstractTemplateView {

	private PebbleTemplate template;

	protected PebbleTemplate getTemplate() throws Exception {
		return template;
	}

	public void setTemplate(PebbleTemplate template) {
		this.template = template;
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");

		final Writer writer = response.getWriter();
		try {
			writer.write(template.render(model));
		} finally {
			writer.flush();
		}
	}

}

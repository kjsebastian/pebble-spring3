/*******************************************************************************
 * Copyright (c) 2013 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.spring;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class PebbleView extends AbstractTemplateView {

	private String templateName;

	private PebbleEngine engine;

	public void setTemplateName(String name) {
		this.templateName = name;
	}

	public void setPebbleEngine(PebbleEngine engine) {
		this.engine = engine;
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType(getContentType());
		response.setCharacterEncoding("UTF-8");

		PebbleTemplate template = engine.getTemplate(templateName);

		final Writer writer = response.getWriter();
		try {
			template.evaluate(writer, model, request.getLocale());
		} finally {
			writer.flush();
			writer.close();
		}
	}

}

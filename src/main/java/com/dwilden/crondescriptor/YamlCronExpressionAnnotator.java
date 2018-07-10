package com.dwilden.crondescriptor;

import org.jetbrains.yaml.psi.impl.YAMLQuotedTextImpl;

public class YamlCronExpressionAnnotator extends BaseCronExpressionAnnotator<YAMLQuotedTextImpl> {

	public YamlCronExpressionAnnotator() {
		super(YAMLQuotedTextImpl.class);
	}

	@Override
	protected String extractText(YAMLQuotedTextImpl element) {
		return element.getText().replaceAll(QUOTES, "").replaceAll(DOUBLE_QUOTES, "");
	}
}

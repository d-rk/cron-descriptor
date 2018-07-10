package com.dwilden.crondescriptor;

import com.intellij.psi.PsiLiteralExpression;

public class JavaCronExpressionAnnotator extends BaseCronExpressionAnnotator<PsiLiteralExpression> {

	public JavaCronExpressionAnnotator() {
		super(PsiLiteralExpression.class);
	}

	@Override
	protected String extractText(PsiLiteralExpression element) {
		String rawText = element.getText();
		if (rawText.contains(DOUBLE_QUOTES)) {
			return element.getText().replace(DOUBLE_QUOTES, "");
		} else {
			return null;
		}
	}
}

package com.dwilden.crondescriptor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.lexer.KtTokens;

public class KotlinCronExpressionAnnotator extends BaseCronExpressionAnnotator<LeafPsiElement> {

	public KotlinCronExpressionAnnotator() {
		super(LeafPsiElement.class);
	}

	@Override
	protected boolean canHandleType(@NotNull PsiElement element) {

		if (!super.canHandleType(element)) {
			return false;
		}

		return KtTokens.REGULAR_STRING_PART.equals(((LeafPsiElement) element).getElementType());
	}

	@Override
	protected String extractText(LeafPsiElement element) {
		return element.getText();
	}
}

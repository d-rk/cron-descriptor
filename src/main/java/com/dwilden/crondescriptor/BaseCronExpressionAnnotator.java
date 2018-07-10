package com.dwilden.crondescriptor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import net.redhogs.cronparser.CronExpressionDescriptor;
import net.redhogs.cronparser.Options;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.regex.Pattern;

public abstract class BaseCronExpressionAnnotator<T> implements Annotator {

	protected static final Pattern INVALID_CHARS_REGEX = Pattern.compile("[^\\d|A-Z|? \\-,\\/*#]");

	protected static final String DOUBLE_QUOTES = "\"";
	protected static final String QUOTES = "'";
	protected static final String WHITESPACE = " ";

	private final Class<T> valueClass;

	private final Options parserOptions;

	public BaseCronExpressionAnnotator(Class<T> valueClass) {
		this.valueClass = valueClass;
		this.parserOptions = Options.twentyFourHour();
		this.parserOptions.setThrowExceptionOnParseError(false);
	}

	@Override
	public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {

		if (!canHandleType(element)) {
			return;
		}

		String cronExpression = extractText(valueClass.cast(element));

		if (!isCronExpression(cronExpression)) {
			return;
		}

		TextRange range = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());

		try {
			String description = CronExpressionDescriptor.getDescription(cronExpression, parserOptions, Locale.getDefault());
			holder.createInfoAnnotation(range, description).setTextAttributes(DefaultLanguageHighlighterColors.LABEL);
		} catch (Throwable e) {
		}
	}

	static boolean isCronExpression(String expression) {

		if (StringUtils.isBlank(expression)) {
			return false;
		}

		if (expression.startsWith(WHITESPACE) || expression.endsWith(WHITESPACE)) {
			return false;
		}

		int expressionParts = expression.split(WHITESPACE, 8).length;

		if (expressionParts < 5 || expressionParts > 7) {
			return false;
		}

		return !INVALID_CHARS_REGEX.matcher(expression).find();
	}

	protected boolean canHandleType(@NotNull PsiElement element) {
		return valueClass.isAssignableFrom(element.getClass());
	}

	protected abstract String extractText(T element);
}

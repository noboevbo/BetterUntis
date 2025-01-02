package com.sapuseven.untis.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.sapuseven.untis.helpers.TextUtils.annotateUrls

@Composable
fun ClickableUrlText(
	text: String,
	modifier: Modifier = Modifier,
	style: TextStyle = TextStyle.Default.copy(LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
	softWrap: Boolean = true,
	overflow: TextOverflow = TextOverflow.Clip,
	maxLines: Int = Int.MAX_VALUE,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	onClick: (String) -> Unit
) {
	val annotatedText = annotateUrls(text)

	BasicText(
		text = annotatedText,
		modifier = modifier.clickable(
			onClick = {
				annotatedText.getStringAnnotations(tag = "URL", start = 0, end = annotatedText.length)
					.firstOrNull()
					?.let { onClick(it.item) }
			}
		),
		style = style,
		softWrap = softWrap,
		overflow = overflow,
		maxLines = maxLines,
		onTextLayout = onTextLayout
	)
}

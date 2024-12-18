import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.saved_birthdays.components.DayBadge
import com.thezayin.saved_birthdays.utils.calculateDaysLeft
import com.thezayin.saved_birthdays.utils.getMonthName
import com.thezayin.saved_birthdays.utils.getZodiacSign
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteCard(
    birthday: BirthdayModel,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val swipeThreshold = 0.75f // 75% of card width for delete
    val coroutineScope = rememberCoroutineScope()

    // State for swipe offset
    var offsetX by remember { mutableFloatStateOf(0f) }
    var cardWidth by remember { mutableIntStateOf(0) }

    // **Text Shaking Animation**
    val shakeAnimation = rememberInfiniteTransition()
    val shakeOffset by shakeAnimation.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .onGloballyPositioned { coordinates ->
                cardWidth = coordinates.size.width
            }  .clickable { onClick() }
    ) {
        // Background with swipe text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Red),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "<-- Swipe left to delete",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(x = shakeOffset.dp) // Apply smooth shaking animation
            )
        }

        // Foreground card (swipeable)
        Card(
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_background)),
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .clip(RoundedCornerShape(8.dp))
                .height(100.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = offsetX + dragAmount
                            offsetX = newOffset.coerceIn(-cardWidth.toFloat(), 0f)
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                if (offsetX <= -cardWidth * swipeThreshold) {
                                    // Trigger delete
                                    onDelete()
                                } else {
                                    // Snap back to position
                                    offsetX = 0f
                                }
                            }
                        }
                    )
                }
                .zIndex(1f), // Ensure card stays above the background
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.sdp, vertical = 10.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = birthday.name,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(R.color.text_color),
                        fontSize = 10.ssp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${birthday.day} ${getMonthName(birthday.month)} ${birthday.year ?: ""}",
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(R.color.text_color),
                        fontSize = 8.ssp
                    )
                    // Dynamic Zodiac Sign
                    Text(
                        text = getZodiacSign(birthday.day, birthday.month),
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                        color = colorResource(R.color.text_color),
                        fontSize = 8.ssp
                    )
                }
                DayBadge(daysLeft = calculateDaysLeft(birthday))
            }
        }
    }
}

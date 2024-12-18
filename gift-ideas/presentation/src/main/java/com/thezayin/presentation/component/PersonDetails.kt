package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PersonDetails(
    budgetTitle: String,
    interestTitle: String,
    dislikesTitle: String,
    relationshipTitle: String,
    budgetPlaceholder: String,
    dislikesPlaceholder: String,
    interestPlaceholder: String,
    relationshipPlaceholder: String,
    onBudgetChange: (String) -> Unit,
    onDislikesChange: (String) -> Unit,
    onInterestChange: (String) -> Unit,
    onRelationshipChange: (String) -> Unit,
) {
    var interestText by remember { mutableStateOf(TextFieldValue("")) }
    var dislikeText by remember { mutableStateOf(TextFieldValue("")) }
    var relationshipText by remember { mutableStateOf(TextFieldValue("")) }
    var budgetText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp, horizontal = 15.sdp)
    ) {
        //Interest Section
        Text(
            text = interestTitle,
            fontSize = 8.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = interestText,
            onValueChange = {
                interestText = it
                onInterestChange(it.text)
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = interestPlaceholder,
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.txt_field),
                unfocusedContainerColor = colorResource(id = R.color.txt_field),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.sdp)
        )

        Spacer(modifier = Modifier.height(4.sdp))

        //Dislike Section
        Text(
            text = dislikesTitle,
            fontSize = 8.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = dislikeText,
            onValueChange = {
                dislikeText = it
                onDislikesChange(it.text)
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = dislikesPlaceholder,
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.txt_field),
                unfocusedContainerColor = colorResource(id = R.color.txt_field),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.sdp)
        )

        Spacer(modifier = Modifier.height(4.sdp))

        //Relationship Section
        Text(
            text = relationshipTitle,
            fontSize = 8.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = relationshipText,
            onValueChange = {
                relationshipText = it
                onRelationshipChange(it.text)
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = relationshipPlaceholder,
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.txt_field),
                unfocusedContainerColor = colorResource(id = R.color.txt_field),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.sdp)
        )

        Spacer(modifier = Modifier.height(4.sdp))

        //Budget Section
        Text(
            text = budgetTitle,
            fontSize = 8.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
        )

        TextField(
            value = budgetText,
            onValueChange = {
                budgetText = it
                onBudgetChange(it.text)
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text_color),
                fontSize = 8.ssp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
            ),
            placeholder = {
                Text(
                    text = budgetPlaceholder,
                    fontSize = 8.ssp,
                    color = colorResource(R.color.text_color),
                    fontFamily = FontFamily(Font(R.font.noto_sans_regular))
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.txt_field),
                unfocusedContainerColor = colorResource(id = R.color.txt_field),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.sdp)
        )
    }
}
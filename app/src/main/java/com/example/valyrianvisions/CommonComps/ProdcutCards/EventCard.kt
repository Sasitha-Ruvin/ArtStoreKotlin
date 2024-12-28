package com.example.valyrianvisions.CommonComps.ProdcutCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valyrianvisions.model.Events


@Composable
fun EventCard(event: Events){
ElevatedCard(modifier = Modifier
    .fillMaxWidth()
    .padding(16.dp),
    shape = RoundedCornerShape(16.dp))
{
    Column (modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(16.dp))
    {
        // Title and Button Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Title of the event
            Text(
                text = stringResource(id = event.title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            // Buy Tickets Button
            Button(
                onClick = { /* Handle ticket purchase */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = Modifier.height(40.dp)
            ) {
                Text(text = "Buy Tickets", color = Color.White)
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = event.type),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Event Date
        Text(
            text = stringResource(id = event.dateRange),
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray)
        ) {

            Image(
                painter = painterResource(event.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }

}

}

@Composable
fun SwipeableEventSlideshow(eventList: List<Events>) {
    var currentPage by remember { mutableStateOf(0) }

    // Box containing the swipeable slideshow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    // Detect swipe direction and update page
                    if (delta > 0) {
                        currentPage = (currentPage - 1).coerceAtLeast(0) // Swipe left
                    } else if (delta < 0) {
                        currentPage = (currentPage + 1).coerceAtMost(eventList.size - 1) // Swipe right
                    }
                }
            )
    ) {
        EventCard(event = eventList[currentPage])
    }
}



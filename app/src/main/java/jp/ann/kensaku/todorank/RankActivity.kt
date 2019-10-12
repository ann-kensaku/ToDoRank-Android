package jp.ann.kensaku.todorank

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        val intent = getIntent()

        val dragTextView: TextView = findViewById(R.id.drag_text)
        val compareText: TextView = findViewById(R.id.compare_text)

        val target_text = intent.getStringExtra("NEWITEM")
        val compare_text = intent.getStringExtra("COMPAREITEM")
        dragTextView.setText(target_text)
        compareText.setText(compare_text)

        //画面のサイズを取得する
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels


        val listener = View.OnTouchListener(function = { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.getRawY() - view.height/2
                view.x = motionEvent.getRawX() - view.width/2
            }

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= width * 2 / 3) {
                    val rankIntent = Intent()
                    rankIntent.putExtra("ANSWER", 1)
                    setResult(RESULT_OK, rankIntent)
                    finish()
                } else if (motionEvent.getRawX() <= width / 3) {
                    val rankIntent = Intent()
                    rankIntent.putExtra("ANSWER", -1)
                    setResult(RESULT_OK, rankIntent)
                    finish()
                }

            }
            true
        })

        dragTextView.setOnTouchListener(listener)
    }
}


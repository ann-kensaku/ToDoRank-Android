package jp.ann.kensaku.todorank

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import jp.ann.kensaku.todorank.databinding.ActivityRankBinding

class RankActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRankBinding>(
            this,
            R.layout.activity_rank
        )

        intent?.apply {
            binding.targetString = getStringExtra("NEWITEM")
            binding.compareString = getStringExtra("COMPAREITEM")
        }

        //画面のサイズを取得する
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels

        binding.targetText.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.getRawY() - view.height / 2
                view.x = motionEvent.getRawX() - view.width / 2
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
        }
    }
}


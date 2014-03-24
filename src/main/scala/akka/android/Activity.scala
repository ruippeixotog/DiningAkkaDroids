package akka.android

import akka.actor.ActorSystem
import android.app.Activity
import android.os.Bundle
import android.view.View.{INVISIBLE, VISIBLE}
import android.widget.ImageView
import TypedResource._

case class Hakker(name: String,
                  activity: MainActivity,
                  left: TypedResource[ImageView],
                  right: TypedResource[ImageView]) {

  private def onUI(block: => Unit) = activity.runOnUiThread(new Runnable { def run() = block })

  def showLeft() = onUI { activity.findView(left).setVisibility(VISIBLE) }
  def showRight() = onUI { activity.findView(right).setVisibility(VISIBLE) }
  def hideLeft() = onUI { activity.findView(left).setVisibility(INVISIBLE) }
  def hideRight() = onUI { activity.findView(right).setVisibility(INVISIBLE) }

  def showBoth() = onUI {
    activity.findView(right).setVisibility(VISIBLE)
    activity.findView(left).setVisibility(VISIBLE)
  }

  def hideBoth() = onUI {
    activity.findView(right).setVisibility(INVISIBLE)
    activity.findView(left).setVisibility(INVISIBLE)
  }
}

class MainActivity extends Activity {
  var actorSystem = Option.empty[ActorSystem]

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.mainlayout)

    val hakkerInfo = List(
      ("Jonas", TR.hakker1left, TR.hakker1right),
      ("Viktor", TR.hakker2left, TR.hakker2right),
      ("Debaish", TR.hakker5left, TR.hakker5right),
      ("Martin", TR.hakker3left, TR.hakker3right),
      ("Irmo", TR.hakker4left, TR.hakker4right))

    val hakkers = hakkerInfo.map { case (name, left, right) =>
      Hakker(name, this, left, right)
    }

    actorSystem = Some(DiningHakkersOnFsm.run(hakkers))
  }

  override def onStop() = {
    actorSystem.foreach(_.shutdown())
    super.onStop()
  }
}

import com.yellowsquash.automata.{Boundary, Cellular}

object Main extends App {
    case class Config(width: Int = 1
      , height: Int = 1
      , generations: Int = 1
      , initialization: Int = 1
      , output: Int = 1)

    override def main(args: Array[String]): Unit = {

        val parser = new scopt.OptionParser[Config]("cellular") {
    	    head("cellular", "0.1")

            opt[Int]('w', "width")
                .withFallback(() => 4)
                .action((x, c) => c.copy(width = x))
                .text("The desired width of the cellular automata")

            opt[Int]('h', "height")
                .withFallback(() => 4)
                .action((x, c) => c.copy(height = x))
                .text("The desired height of the cellular automata")

            opt[Int]('g', "generations")
                .withFallback(() => 4)
                .action((x, c) => c.copy(generations = x))
                .text("The number of updates to run on the cellular automata")

            opt[Int]('i', "initialization")
                .withFallback(() => 5)
                .action((x, c) => c.copy(initialization = x))
                .text("The percentage chance a cell is initially on")

            opt[Int]('o', "output")
                .withFallback(() => 5)
                .action((x, c) => c.copy(output = x))
                .text("The output type")

            opt[Int]('n', "neighborhood")
                .withFallback(() => 5)
                .action((x, c) => c.copy(output = x))
                .text("The neighborhood type, VON_NEUMANN or MOORE")

            opt[Int]('t', "threshold")
                .withFallback(() => 5)
                .action((x, c) => c.copy(output = x))
                .text("The neighborhood threshold")


            help("help").text("prints this usage text")
      	}

        parser.parse(args, Config()) match {
          case Some(input) => doEverything(
                                input.width
                                , input.height
                                , input.generations
                                , input.initialization
                              )
          case _ =>
        } 
    }

  def doEverything(width: Int, height: Int, generations: Int, initialization: Int): Unit = {
      val cellular = new Cellular(width, height, initialization)

      (1 to generations) foreach (_ => cellular.update())
      Cellular.print(cellular.matrix)
      Cellular.toBitmap(cellular)
  }
}
